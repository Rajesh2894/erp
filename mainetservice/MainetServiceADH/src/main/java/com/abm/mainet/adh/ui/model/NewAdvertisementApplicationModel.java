package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author vishwajeet.kumar
 * @since 23 Sept 2019
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class NewAdvertisementApplicationModel extends AbstractFormModel {

    private static final long serialVersionUID = -6887754933686463475L;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private INewAdvertisementApplicationService newAdvApplicationService;

    @Autowired
    private IChallanService challanService;

    private NewAdvertisementReqDto advertisementReqDto;
    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    private List<TbLocationMas> locationMasList;
    private String enableSubmit;
    private String scrutinyViewMode;
    private double amountToPay;
    private List<ChargeDetailDTO> chargesInfo;
    private boolean payable;
    private Long licMaxTenureDays;
    private Long licMinTenureDays;
    private List<NewAdvertisementApplicationDto> licenseDataList;
    private String saveMode;
    private List<String> remarkList;
    private Long deptId;
    private String ulbOwned;

    public boolean saveApplication() {
        boolean status = true;

        final CommonChallanDTO offline = getOfflineDTO();
        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        if (getAmountToPay() > 0d) {
            validateBean(offline, CommonOfflineMasterValidator.class);
        }

        fileUpload.validateUpload(getBindingResult());
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
        advertisementReqDto.setDocumentList(docs);
        advertisementReqDto.setNewOrRenewalApp(MainetConstants.AdvertisingAndHoarding.NEW);
        if (advertisementReqDto.getAdvertisementDto().getCreatedBy() == null) {
            advertisementReqDto.setOrgId(orgId);
            setGender(advertisementReqDto.getApplicantDetailDTO());
            advertisementReqDto.setLangId((long) UserSession.getCurrent().getLanguageId());
            advertisementReqDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            advertisementReqDto.getAdvertisementDto().setOrgId(orgId);
            advertisementReqDto.getAdvertisementDto().setLgIpMac(lgIp);
            advertisementReqDto.getAdvertisementDto().setCreatedBy(createdBy);
            advertisementReqDto.getAdvertisementDto().setCreatedDate(newDate);
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
            	advertisementReqDto.getAdvertisementDto().setUlbOwned(ulbOwned);
            if (!advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().isEmpty()) {
                advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().forEach(newAdvApp -> {
                    newAdvApp.setCreatedBy(createdBy);
                    newAdvApp.setCreatedDate(newDate);
                    newAdvApp.setOrgId(orgId);
                    newAdvApp.setLgIpMac(lgIp);
                });
            }
        }
        NewAdvertisementReqDto newAdvertisementReqDto = newAdvApplicationService
                .saveNewAdvertisementApplication(advertisementReqDto);
        this.setApmApplicationId(newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId());
        if (newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId() != null
                && !newAdvertisementReqDto.isFree()) {
            setAndSaveChallanDto(offline, newAdvertisementReqDto);
        }
        if (newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId() != null) {
            sendAdhSmsAndEmail(newAdvertisementReqDto);
        }
        setSuccessMessage(
                ApplicationSession.getInstance().getMessage(MainetConstants.AdvertisingAndHoarding.YOUR_APPLICATION_NO)
                        + MainetConstants.WHITE_SPACE
                        + newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId());
        return status;
    }

    /**
     * Send Mail after ADH Save
     * @param newAdvertisementReqDto
     */
    private void sendAdhSmsAndEmail(NewAdvertisementReqDto newAdvertisementReqDto) {

        SMSAndEmailDTO smsAndEmailDTO = new SMSAndEmailDTO();
        smsAndEmailDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        smsAndEmailDTO.setLangId(UserSession.getCurrent().getLanguageId());
        smsAndEmailDTO.setAppNo(String.valueOf(newAdvertisementReqDto.getApplicationId()));
        smsAndEmailDTO.setServName(newAdvertisementReqDto.getServiceName());
        smsAndEmailDTO.setMobnumber(advertisementReqDto.getApplicantDetailDTO().getMobileNo());
        smsAndEmailDTO.setAppName(advertisementReqDto.getApplicantDetailDTO().getApplicantFirstName() + " "
                + advertisementReqDto.getApplicantDetailDTO().getApplicantLastName());
        smsAndEmailDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        if (StringUtils.isNotBlank(advertisementReqDto.getApplicantDetailDTO().getEmailId())) {
            smsAndEmailDTO.setEmail(advertisementReqDto.getApplicantDetailDTO().getEmailId());
        }
        String ADHSmsUrl = "NewAdvertisementApplication.html";
        ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
                MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, ADHSmsUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED,
                smsAndEmailDTO,
                UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

    }

    /**
     * This method is used for set Gender from Hirerichal prefix
     * 
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

    @Override
    public void populateApplicationData(long applicationId) {

        setApmApplicationId(applicationId);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        NewAdvertisementReqDto advReqDto = newAdvApplicationService.getNewAdvertisementApplicationByAppId(applicationId,
                orgId);
        ServiceMaster master = ApplicationContextProvider.getApplicationContext().getBean(TbServicesMstService.class)
                .findShortCodeByOrgId(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgId);

        this.setLocationMasList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .getlocationByDeptId(master.getTbDepartment().getDpDeptid(), orgId));
        this.setScrutinyViewMode(MainetConstants.FlagV);
        this.setAdvertisementReqDto(advReqDto);
    }

    public boolean validateInputs() {
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    /**
     * set And Save Challan Dto
     * @param offline
     * @param newAdvertisementReqDto
     */
    private void setAndSaveChallanDto(CommonChallanDTO offline, NewAdvertisementReqDto newAdvertisementReqDto) {
        offline.setApplNo(newAdvertisementReqDto.getApplicationId());
        offline.setAmountToPay(Double.toString(getAmountToPay()));
        offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        offline.setLangId(UserSession.getCurrent().getLanguageId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
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
        offline.setLgIpMac(getClientIpAddress());
        offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
        offline.setDeptId(newAdvertisementReqDto.getDeptId());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());

        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

            final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

            offline.setChallanNo(responseChallan.getChallanNo());
            offline.setChallanValidDate(responseChallan.getChallanValiDate());

            setOfflineDTO(offline);
        } else if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

            final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
                    newAdvertisementReqDto.getServiceName());
            setReceiptDTO(printDto);
            setSuccessMessage(getAppSession().getMessage("adh.receipt"));
        }
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

    public List<TbLocationMas> getLocationMasList() {
        return locationMasList;
    }

    public void setLocationMasList(List<TbLocationMas> locationMasList) {
        this.locationMasList = locationMasList;
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

    public List<String> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<String> remarkList) {
        this.remarkList = remarkList;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

	public String getUlbOwned() {
		return ulbOwned;
	}

	public void setUlbOwned(String ulbOwned) {
		this.ulbOwned = ulbOwned;
	}

}
