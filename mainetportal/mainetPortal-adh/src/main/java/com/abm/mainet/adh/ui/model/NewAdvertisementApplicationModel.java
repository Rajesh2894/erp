package com.abm.mainet.adh.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;

/**
 * @author vishwajeet.kumar
 * @since 14 October 2019
 */
@Component
@Scope("session")
public class NewAdvertisementApplicationModel extends AbstractFormModel {

    private static final long serialVersionUID = -6887754933686463475L;

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private INewAdvertisementApplicationService advertisementApplicationService;

    @Autowired
    private IChallanService challanService;
    
    @Autowired
	private IServiceMasterService serviceMaster;


    private NewAdvertisementReqDto advertisementReqDto;
    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    private String enableSubmit;
    private String scrutinyViewMode;
    List<LookUp> listOfLookUp;
    private double amountToPay;
    private List<ChargeDetailDTO> chargesInfo;
    private boolean payable;
    private Long licMaxTenureDays;
    private Long licMinTenureDays;
    private List<NewAdvertisementApplicationDto> licenseDataList;
    private String saveMode="C";
    private String checkListApplFlag;
    private String chargeApplFlag;
    private String paymentMode;
    
    private PortalService service = new PortalService();

    public boolean saveApplication() {

        boolean status = true;
        final CommonChallanDTO offline = getOfflineDTO();
        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        if (getAmountToPay() > 0d) {
            validateBean(offline, CommonOfflineMasterValidator.class);
        }
         fileUpload. validateUpload(getBindingResult());
        if (hasValidationErrors()) {
            return false;
        }
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
        String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
        Date newDate = new Date();
        List<DocumentDetailsVO> docs = getCheckList();
        if (docs != null) {
            docs = fileUpload.prepareFileUpload(docs);
        }
        NewAdvertisementReqDto advertisementReqDto = getAdvertisementReqDto();
        advertisementReqDto.setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent()
                .getFileMap()));
        advertisementReqDto.setNewOrRenewalApp("NEW");
        if (advertisementReqDto.getAdvertisementDto().getCreatedBy() == null) {
            advertisementReqDto.setOrgId(orgId);
            setGender(advertisementReqDto.getApplicantDetailDTO());
            advertisementReqDto.setLangId((long) UserSession.getCurrent().getLanguageId());
            advertisementReqDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            advertisementReqDto.getAdvertisementDto().setOrgId(orgId);
            advertisementReqDto.getAdvertisementDto().setLgIpMac(lgIp);
            advertisementReqDto.getAdvertisementDto().setCreatedBy(createdBy);
            advertisementReqDto.getAdvertisementDto().setCreatedDate(newDate);
            advertisementReqDto.setServiceId(getServiceId());
            advertisementReqDto.setApplicantDetailDTO(getAdvertisementReqDto().getApplicantDetailDTO());
            final PortalService serviceMasterData = serviceMaster.getPortalServiceMaster(
    				MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgId);
    		setService(serviceMasterData);
    		advertisementReqDto.setServiceId(serviceMasterData.getServiceId());
            // advertisementReqDto.setFree(true);
            if (!advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().isEmpty()) {
                advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().forEach(newAdvApp -> {
                    newAdvApp.setCreatedBy(createdBy);
                    newAdvApp.setCreatedDate(newDate);
                    newAdvApp.setOrgId(orgId);
                    newAdvApp.setLgIpMac(lgIp);
                });
            }
        }
        
        NewAdvertisementReqDto newAdvertisementReqDto = advertisementApplicationService
                .saveNewAdvertisementApplication(advertisementReqDto);
        this.setApmApplicationId(newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId());

        if (newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId() != null
                && !newAdvertisementReqDto.isFree()) {
            setAndSaveChallanDto(offline, newAdvertisementReqDto);
        }

        return status;
    }

    private void setAndSaveChallanDto(CommonChallanDTO offline, NewAdvertisementReqDto newAdvertisementReqDto) {
        offline.setApplNo(newAdvertisementReqDto.getApplicationId());
        offline.setAmountToPay(Double.toString(getAmountToPay()));
        offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        offline.setLangId(UserSession.getCurrent().getLanguageId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        // offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        // offline.setFinYearStartDate(UserSession.getCurrent().);
        // offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setServiceId(newAdvertisementReqDto.getServiceId());
        String aplicantName = getAdvertisementReqDto().getApplicantDetailDTO().getApplicantFirstName()
                + MainetConstants.WHITE_SPACE;
        aplicantName += getAdvertisementReqDto().getApplicantDetailDTO().getApplicantMiddleName() == null
                ? MainetConstants.BLANK
                : getAdvertisementReqDto().getApplicantDetailDTO().getApplicantMiddleName()
                        + MainetConstants.WHITE_SPACE;
        aplicantName += getAdvertisementReqDto().getApplicantDetailDTO().getApplicantLastName();
        offline.setApplicantName(aplicantName);
        offline.setMobileNumber(getAdvertisementReqDto().getApplicantDetailDTO().getMobileNo());
        offline.setEmailId(getAdvertisementReqDto().getApplicantDetailDTO().getEmailId());
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setApplicantAddress(getAdvertisementReqDto().getApplicantDetailDTO().getAreaName() + ","
                + getAdvertisementReqDto().getApplicantDetailDTO().getVillageTownSub());

        for (ChargeDetailDTO dto : getChargesInfo()) {
            offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
        }

        if (CollectionUtils.isNotEmpty(getCheckList())) {
            offline.setDocumentUploaded(true);
        } else {
            offline.setDocumentUploaded(false);
        }
        // offline.setLgIpMac(getClientIpAddress());
        // offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
        offline.setDeptId(newAdvertisementReqDto.getDeptId());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());

        if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals("Y")) {
            setPaymentMode("Online");
        }
        if ((offline.getOnlineOfflineCheck() != null) &&
                offline.getOnlineOfflineCheck().equals("N")) {
            setPaymentMode(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(),
                    UserSession.getCurrent().getOrganisation()).getLookUpDesc());
            offline = challanService.generateChallanNumber(offline);
            setOfflineDTO(offline);
        }

    }
    @Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		payURequestDTO.setUdf1(getService().getServiceId().toString());
		payURequestDTO.setUdf2(getOfflineDTO().getApplNo().toString());
		final ApplicantDetailDTO applicant = getAdvertisementReqDto().getApplicantDetailDTO();
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
		payURequestDTO.setUdf7(getOfflineDTO().getApplNo().toString());
		payURequestDTO.setServiceId(getService().getServiceId());
		if (getApmApplicationId() != 0) {
			payURequestDTO.setApplicationId(getOfflineDTO().getApplNo().toString());
		}

		if (getOfflineDTO().getAmountToShow() != null) {
			payURequestDTO.setDueAmt(new BigDecimal(getOfflineDTO().getAmountToShow()));
		}
		payURequestDTO.setEmail(applicant.getEmailId());
		payURequestDTO.setMobNo(applicant.getMobileNo());
		String userName = (applicant.getApplicantFirstName() == null ? MainetConstants.BLANK
				: applicant.getApplicantFirstName().trim() + MainetConstants.WHITE_SPACE);
		userName += applicant.getApplicantMiddleName() == null ? MainetConstants.BLANK
				: applicant.getApplicantMiddleName().trim() + MainetConstants.WHITE_SPACE;
		userName += applicant.getApplicantLastName() == null ? MainetConstants.BLANK
				: applicant.getApplicantLastName().trim();
		payURequestDTO.setApplicantName(userName.trim());
		payURequestDTO.setUdf10(getService().getPsmDpDeptid().toString());
		if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
			payURequestDTO.setServiceName(getService().getServiceName());
		} else {
			payURequestDTO.setServiceName(getService().getServiceNameReg());
		}
		//Added for The Urban Pay Issue
		//payURequestDTO.setOrgId(applicant.getOrgId());
	}

    /**
     * This method is used for set Gender from Hirerichal prefix
     * @param applicant
     */
    private void setGender(final ApplicantDetailDTO applicant) {
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : lookUps) {
            if ((applicant.getGender() != null) && !applicant.getGender().isEmpty()) {
                if (lookUp.getLookUpId() == Long.parseLong(applicant.getGender())) {
                    applicant.setGender(lookUp.getLookUpCode());
                    break;
                }
            }

        }
    }

    public boolean validateInputs() {
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    public NewAdvertisementReqDto getAdvertisementReqDto() {
        return advertisementReqDto;
    }

    public void setAdvertisementReqDto(NewAdvertisementReqDto advertisementReqDto) {
        this.advertisementReqDto = advertisementReqDto;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public String getEnableSubmit() {
        return enableSubmit;
    }

    public void setEnableSubmit(String enableSubmit) {
        this.enableSubmit = enableSubmit;
    }

    public String getScrutinyViewMode() {
        return scrutinyViewMode;
    }

    public void setScrutinyViewMode(String scrutinyViewMode) {
        this.scrutinyViewMode = scrutinyViewMode;
    }

    public List<LookUp> getListOfLookUp() {
        return listOfLookUp;
    }

    public void setListOfLookUp(List<LookUp> listOfLookUp) {
        this.listOfLookUp = listOfLookUp;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public List<ChargeDetailDTO> getChargesInfo() {
        return chargesInfo;
    }

    public void setChargesInfo(List<ChargeDetailDTO> chargesInfo) {
        this.chargesInfo = chargesInfo;
    }

    public boolean isPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
    }

    public Long getLicMaxTenureDays() {
        return licMaxTenureDays;
    }

    public void setLicMaxTenureDays(Long licMaxTenureDays) {
        this.licMaxTenureDays = licMaxTenureDays;
    }

    public Long getLicMinTenureDays() {
        return licMinTenureDays;
    }

    public void setLicMinTenureDays(Long licMinTenureDays) {
        this.licMinTenureDays = licMinTenureDays;
    }

    public List<NewAdvertisementApplicationDto> getLicenseDataList() {
        return licenseDataList;
    }

    public void setLicenseDataList(List<NewAdvertisementApplicationDto> licenseDataList) {
        this.licenseDataList = licenseDataList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getCheckListApplFlag() {
        return checkListApplFlag;
    }

    public void setCheckListApplFlag(String checkListApplFlag) {
        this.checkListApplFlag = checkListApplFlag;
    }

    public String getChargeApplFlag() {
        return chargeApplFlag;
    }

    public void setChargeApplFlag(String chargeApplFlag) {
        this.chargeApplFlag = chargeApplFlag;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

	public PortalService getService() {
		return service;
	}

	public void setService(PortalService service) {
		this.service = service;
	}

}
