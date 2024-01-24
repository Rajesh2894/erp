/**
 * 
 */
package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.IHoardingMasterService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.service.INewHoardingApplicationService;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author anwarul.hassan
 * @since 17-Oct-2019
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class NewHoardingApplicationModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private INewAdvertisementApplicationService newAdvApplicationService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private TbServicesMstService servicesMstService;

    @Autowired
    private ILocationMasService locationMasService;
    @Autowired
    private IChallanService challanService;

    @Autowired
    private INewHoardingApplicationService newHoardingApplicationService;

    @Autowired
    private IHoardingMasterService hoardingMasterService;

    private NewAdvertisementReqDto advertisementReqDto = new NewAdvertisementReqDto();
    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    private List<TbLocationMas> locationMasList;
    private String enableSubmit;
    private String scrutinyViewMode;
    private List<HoardingMasterDto> hoardingMasterDtoList = new ArrayList<>();
    // private HoardingMasterDto hoardingMasterDto = new HoardingMasterDto();
    private List<String[]> hoardingNumberList = new ArrayList<>();
    private double amountToPay;
    private boolean payable;
    private Long licMaxTenureDays;
    private Long licMinTenureDays;

    @Override
    public boolean saveForm() {
        boolean status = true;

        final CommonChallanDTO offline = getOfflineDTO();
        String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
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
            // advertisementReqDto.setFree(true);
            if (!advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().isEmpty()) {
                advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().forEach(newAdvApp -> {
                    newAdvApp.setCreatedBy(createdBy);
                    newAdvApp.setCreatedDate(newDate);
                    newAdvApp.setOrgId(orgId);
                    newAdvApp.setLgIpMac(lgIp);
                    /*
                     * if (hoardingMasterDto.getHoardingTypeId1() != null) {
                     * newAdvApp.setAdhTypeId1(hoardingMasterDto.getHoardingTypeId1()); } if
                     * (hoardingMasterDto.getHoardingTypeId2() != null) {
                     * newAdvApp.setAdhTypeId2(hoardingMasterDto.getHoardingTypeId2()); } if
                     * (hoardingMasterDto.getHoardingTypeId3() != null) {
                     * newAdvApp.setAdhTypeId3(hoardingMasterDto.getHoardingTypeId3()); } if
                     * (hoardingMasterDto.getHoardingTypeId4() != null) {
                     * newAdvApp.setAdhTypeId4(hoardingMasterDto.getHoardingTypeId4()); } if
                     * (hoardingMasterDto.getHoardingTypeId5() != null) {
                     * newAdvApp.setAdhTypeId5(hoardingMasterDto.getHoardingTypeId5()); } if (hoardingMasterDto.getDisplayIdDesc()
                     * != null) { newAdvApp.setDisplayIdDesc(hoardingMasterDto.getDisplayIdDesc()); } if
                     * (hoardingMasterDto.getHoardingId() != null || hoardingMasterDto.getHoardingId() > 0) {
                     * newAdvApp.setHoardingId(hoardingMasterDto.getHoardingId()); }
                     */
                });
            }
        }

        // hoardingMasterDtoList.add(hoardingMasterDto);
        NewAdvertisementReqDto newAdvertisementReqDto = newHoardingApplicationService
                .saveNewHoardingApplication(advertisementReqDto);
        if (newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId() != null && !newAdvertisementReqDto.isFree()) {
            setAndSaveChallanDto(offline, newAdvertisementReqDto);
        }
        setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.AdvertisingAndHoarding.YOUR_APPLICATION_NO)
                + MainetConstants.WHITE_SPACE + newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId());
        return status;
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

    @Override
    public void populateApplicationData(long applicationId) {

        setApmApplicationId(applicationId);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // make data for hoarding details
        NewAdvertisementReqDto advReqDto = newHoardingApplicationService.getDataForHoardingApplication(applicationId, orgId);
        this.setHoardingNumberList(hoardingMasterService
                .getHoardingNumberAndIdListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        ServiceMaster master = servicesMstService.findShortCodeByOrgId(
                MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.setLocationMasList(locationMasService.getlocationByDeptId(master.getTbDepartment().getDpDeptid(),
                orgId));
        this.setScrutinyViewMode(MainetConstants.FlagV);
        this.setAdvertisementReqDto(advReqDto);
    }

    public boolean validateInputs() {
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    private void setAndSaveChallanDto(CommonChallanDTO offline, NewAdvertisementReqDto advertisementReqDto) {
        offline.setApplNo(advertisementReqDto.getApplicationId());
        offline.setAmountToPay(Double.toString(getAmountToPay()));
        offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        offline.setLangId(UserSession.getCurrent().getLanguageId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        ServiceMaster hoardindServiceMaster = servicesMstService.findShortCodeByOrgId("HBP",
                UserSession.getCurrent().getOrganisation().getOrgid());
        offline.setServiceId(hoardindServiceMaster.getSmServiceId());
        String applicantName = getAdvertisementReqDto().getApplicantDetailDTO().getApplicantFirstName()
                + MainetConstants.WHITE_SPACE;
        applicantName += getAdvertisementReqDto().getApplicantDetailDTO().getApplicantMiddleName() == null ? MainetConstants.BLANK
                : getAdvertisementReqDto().getApplicantDetailDTO().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
        applicantName += getAdvertisementReqDto().getApplicantDetailDTO().getApplicantLastName();
        offline.setApplicantName(applicantName);
        offline.setApplicantAddress(getAdvertisementReqDto().getApplicantDetailDTO().getAreaName() + ","
                + getAdvertisementReqDto().getApplicantDetailDTO().getVillageTownSub());
        offline.setMobileNumber(getAdvertisementReqDto().getApplicantDetailDTO().getMobileNo());
        offline.setEmailId(getAdvertisementReqDto().getApplicantDetailDTO().getEmailId());
        offline.setEmpType(UserSession.getCurrent().getEmplType());

        for (ChargeDetailDTO chargeDetailDTO : getChargesInfo()) {
            offline.getFeeIds().put(chargeDetailDTO.getChargeCode(), chargeDetailDTO.getChargeAmount());
        }
        if (CollectionUtils.isNotEmpty(getCheckList())) {
            offline.setDocumentUploaded(true);
        } else {
            offline.setDocumentUploaded(false);
        }
        offline.setLgIpMac(getClientIpAddress());
        offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
        offline.setDeptId(advertisementReqDto.getDeptId());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());
        if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

            final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

            offline.setChallanNo(responseChallan.getChallanNo());
            offline.setChallanValidDate(responseChallan.getChallanValiDate());

            setOfflineDTO(offline);
        } else if ((offline.getOnlineOfflineCheck() != null)
                && (offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER))) {
            ChallanReceiptPrintDTO challanReceiptPrintDTO = challanService.savePayAtUlbCounter(offline,
                    advertisementReqDto.getServiceName());
            setReceiptDTO(challanReceiptPrintDTO);
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

    public List<HoardingMasterDto> getHoardingMasterDtoList() {
        return hoardingMasterDtoList;
    }

    public void setHoardingMasterDtoList(List<HoardingMasterDto> hoardingMasterDtoList) {
        this.hoardingMasterDtoList = hoardingMasterDtoList;
    }

    /*
     * public HoardingMasterDto getHoardingMasterDto() { return hoardingMasterDto; } public void
     * setHoardingMasterDto(HoardingMasterDto hoardingMasterDto) { this.hoardingMasterDto = hoardingMasterDto; }
     */

    public List<String[]> getHoardingNumberList() {
        return hoardingNumberList;
    }

    public void setHoardingNumberList(List<String[]> hoardingNumberList) {
        this.hoardingNumberList = hoardingNumberList;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
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

}
