package com.abm.mainet.adh.ui.model;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationRequestDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.dto.LicenseLetterDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.ui.validator.AdverstiserMasterValidator;
import com.abm.mainet.adh.ui.validator.AgencyValidatorOwner;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.loi.ui.model.LoiGenerationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author cherupelli.srikanth
 * @since 08 august 2019
 */
@Component
@Scope("session")
public class AgencyRegistrationModel extends AbstractFormModel {

    private static final long serialVersionUID = 872314752923182124L;

    @Resource
    IFileUploadService fileUpload;

    @Autowired
    private IAdvertiserMasterService avertiserMasterService;

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private TbLoiMasService loiService;

    @Autowired
    private ICFCApplicationMasterService cfcApplicationService;

    private AgencyRegistrationRequestDto agencyRequestDto = new AgencyRegistrationRequestDto();

    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

    private List<DocumentDetailsVO> checkList = new ArrayList<>();

    private List<DocumentDetailsVO> approvalDocumentAttachment = new ArrayList<>();

    private List<CFCAttachment> documentList = new ArrayList<>();

    private ServiceMaster serviceMaster = new ServiceMaster();

    private List<AdvertiserMasterDto> masterDtoList = new ArrayList<>();

    private List<String[]> agencyLicNoNameList = new ArrayList<>();

    private LookUp agenctCategoryLookUp = new LookUp();

    private AdvertiserMasterDto advertiserMasterDto = new AdvertiserMasterDto();

    private List<LicenseLetterDto> licenseDto = new ArrayList<>();

    private List<TbLoiDet> loiDetail = new ArrayList<>();

    private Long orgId;

    private double amountToPay;

    private String checkListApplFlag;

    private String applicationchargeApplFlag;

    private Long applicationId;

    private String applicantName;

    private String formDisplayFlag;

    private Long licMaxTenureDays;

    private Long licMinTenureDays;

    private String successFlag;

    private String payableFlag;

    private Double totalLoiAmount;
    private String ownershipPrefix;

    private String viewMode;

    private String openMode;

    private String ownershipTypeValue;

    private List<CFCAttachment> documentListAtchdAtApprovalTm = new ArrayList<>();

    private List<TbApprejMas> remarkList;

    private List<String> loiRemarkList;

    public List<CFCAttachment> getDocumentListAtchdAtApprovalTm() {
        return documentListAtchdAtApprovalTm;
    }

    public void setDocumentListAtchdAtApprovalTm(List<CFCAttachment> documentListAtchdAtApprovalTm) {
        this.documentListAtchdAtApprovalTm = documentListAtchdAtApprovalTm;
    }

    private LookUp OwnerCategoryLookUp = new LookUp();

    public LookUp getOwnerCategoryLookUp() {
        return OwnerCategoryLookUp;
    }

    public void setOwnerCategoryLookUp(LookUp ownerCategoryLookUp) {
        OwnerCategoryLookUp = ownerCategoryLookUp;
    }

    public boolean saveForm() {
        CommonChallanDTO offline = getOfflineDTO();
        boolean status = false;
        List<DocumentDetailsVO> documents = getCheckList();
        documents = fileUpload.prepareFileUpload(documents);
        AgencyRegistrationRequestDto requestDto = getAgencyRequestDto();
        validateInputs(documents);
        if (!requestDto.isFree()) {
            validateBean(offline, CommonOfflineMasterValidator.class);
        }
        getAgencyRequestDto().getMasterDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        
        LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix( getAgencyRequestDto().getMasterDto().getAgencyCategory(), "AGC", UserSession.getCurrent().getOrganisation().getOrgid());
        requestDto.getMasterDto().setAgencyCategory(lookUp.getLookUpId());
        if (status == true) {
            validateBean(getAgencyRequestDto().getMasterDto(), AdverstiserMasterValidator.class);
        } else {
            validateBean(getAgencyRequestDto().getMasterDto(), AgencyValidatorOwner.class);
        }
        if (hasValidationErrors()) {
            return false;
        }

        requestDto.setDocumentList(documents);
        requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
        requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDto.getMasterDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDto.getMasterDto().setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDto.getMasterDto().setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
        requestDto.setServiceId(getServiceMaster().getSmServiceId());
        requestDto.getMasterDto().setAgencyStatus(MainetConstants.AdvertisingAndHoarding.TRANSIENT_STATUS);
        requestDto.getMasterDto().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        requestDto.getMasterDto().setCreatedDate(new Date());
        requestDto.getMasterDto().setLgIpMac(getClientIpAddress());
        requestDto.setDeptId(getServiceMaster().getTbDepartment().getDpDeptid());
        requestDto.setApplicantDetailDto(getApplicantDetailDto());

        AgencyRegistrationResponseDto responseDto = avertiserMasterService.saveAgencyRegistrationData(requestDto);
        requestDto.setApplicationId(responseDto.getApplicationId());

        if (responseDto.getApplicationId() != null && responseDto.getApplicationId() != 0) {

            setApmApplicationId(responseDto.getApplicationId());

            String menuUrl = null;
            if (StringUtils.equalsIgnoreCase(getServiceMaster().getSmShortdesc(),
                    MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE)) {
                menuUrl = MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_SMS_URL;
            } else if (StringUtils.equalsIgnoreCase(getServiceMaster().getSmShortdesc(),
                    MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE)) {
                menuUrl = MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_REN_SMS_URL;
            }

            if (!requestDto.isFree()) {
                setAndSaveChallanDto(offline, requestDto.getMasterDto());
            }

            sendSmsEmail(this, menuUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED);
            setSuccessMessage(ApplicationSession.getInstance()
                    .getMessage("Your application No." + MainetConstants.WHITE_SPACE + requestDto.getApplicationId()
                            + MainetConstants.WHITE_SPACE + "has been submitted successfully."));

            status = true;

        }

        return status;
    }

    @Override
    public void populateApplicationData(final long applicationId) {

        ApplicantDetailDTO applicantDetail = new ApplicantDetailDTO();
        applicantDetail.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        populateApplicantionDetails(applicantDetail, applicationId);
        List<AdvertiserMasterDto> agencyRegistrationByAppIdAndOrgId = ApplicationContextProvider.getApplicationContext()
                .getBean(IAdvertiserMasterService.class).getAgencyRegistrationByAppIdAndOrgId(applicationId,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        
        AdvertiserMasterDto dto=ApplicationContextProvider.getApplicationContext()
                .getBean(IAdvertiserMasterService.class).getAgencyRegistrationByAppIdByOrgId(applicationId,
                        UserSession.getCurrent().getOrganisation().getOrgid());
       getAgencyRequestDto().setMasterDto(dto);
        

        getAgencyRequestDto().setMasterDtolist(agencyRegistrationByAppIdAndOrgId);
        // getAgencyRequestDto().setMasterDto(agencyRegistrationByAppIdAndOrgId.get(0));
        setDocumentList(ApplicationContextProvider.getApplicationContext().getBean(IChecklistVerificationService.class)
                .getDocumentUploaded(applicationId, UserSession.getCurrent().getOrganisation().getOrgid()));
        setApplicantDetailDto(applicantDetail);

    }

    public boolean AgencyRegistrationApprovalAction() {
        WorkflowTaskAction taskAction = getWorkflowActionDto();
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
        taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        taskAction.setCreatedDate(new Date());
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setDateOfAction(new Date());
        taskAction.setIsFinalApproval(MainetConstants.FAILED);
        taskAction.setIsObjectionAppealApplicable(MainetConstants.FAILED);
        taskAction.setDecision(getWorkflowActionDto().getDecision());
        taskAction.setApplicationId(getApmApplicationId());
        taskAction.setTaskId(getWorkflowActionDto().getTaskId());

        return avertiserMasterService.executeApprovalWorkflowAction(taskAction, getServiceMaster().getSmShortdesc(),
                getServiceMaster().getSmServiceId(), getServiceMaster().getSmShortdesc());

    }

    public boolean validateInputs(final List<DocumentDetailsVO> dto) {
        boolean flag = false;
        if ((dto != null) && !dto.isEmpty()) {
            for (final DocumentDetailsVO doc : dto) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
                    if (doc.getDocumentByteCode() == null) {
                        addValidationError(
                                ApplicationSession.getInstance().getMessage("adh.upload.mandatory.documents"));
                        flag = true;
                        break;
                    }
                }
            }

        }

        if (getAgencyRequestDto().getMasterDto().getAgencyLicToDate() != null) {
            LocalDate licMaxDate;
            licMaxDate = LocalDate.now().plusDays(getLicMaxTenureDays());
            if (getLicMinTenureDays() != null) {
                licMaxDate = LocalDate.now().plusDays((getLicMinTenureDays() + getLicMaxTenureDays()) - 1);
            }

            if (Utility.compareDate(Date.from(licMaxDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    getAgencyRequestDto().getMasterDto().getAgencyLicToDate())) {
                addValidationError(ApplicationSession.getInstance()
                        .getMessage("License to date cannot be greater than " + licMaxDate));
            }
        }

        return flag;
    }

    private void setAndSaveChallanDto(CommonChallanDTO offline, AdvertiserMasterDto advertiserDto) {

        offline.setApplNo(advertiserDto.getApplicationId());
        offline.setAmountToPay(Double.toString(getAmountToPay()));
        offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        offline.setLangId(UserSession.getCurrent().getLanguageId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setServiceId(getServiceMaster().getSmServiceId());
        String aplicantName = getApplicantDetailDto().getApplicantFirstName() + MainetConstants.WHITE_SPACE;
        aplicantName += getApplicantDetailDto().getApplicantMiddleName() == null ? MainetConstants.BLANK
                : getApplicantDetailDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
        aplicantName += getApplicantDetailDto().getApplicantLastName();
        offline.setApplicantName(aplicantName);
        offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
        offline.setEmailId(getApplicantDetailDto().getEmailId());
        offline.setApplicantAddress(getApplicantDetailDto().getAreaName() + "," + getApplicantDetailDto().getVillageTownSub());
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());

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
        offline.setDeptId(getServiceMaster().getTbDepartment().getDpDeptid());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());

        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

            final ChallanMaster responseChallan = iChallanService.InvokeGenerateChallan(offline);

            offline.setChallanNo(responseChallan.getChallanNo());
            offline.setChallanValidDate(responseChallan.getChallanValiDate());

            setOfflineDTO(offline);
        } else if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

            final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
                    getServiceMaster().getSmServiceName());
            setReceiptDTO(printDto);

            setSuccessMessage(getAppSession().getMessage("adh.receipt"));
        }

    }

    // T#90050
    public boolean saveLoiData() {
        boolean status = false;

        final UserSession session = UserSession.getCurrent();
        getAgencyRequestDto().getMasterDto().setOrgId(session.getOrganisation().getOrgid());
        Map<Long, Double> loiCharges = avertiserMasterService.getLoiCharges(getAgencyRequestDto().getMasterDto(),
                getServiceMaster().getSmServiceId(), getServiceMaster().getSmShortdesc());

        if (MapUtils.isNotEmpty(loiCharges)) {
            ApplicationContextProvider.getApplicationContext().getBean(LoiGenerationModel.class).saveLOIAppData(
                    loiCharges,
                    getServiceMaster().getSmServiceId(), loiDetail, true/* approvalLetterGenerationApplicable */,
                    getWorkflowActionDto());
            setLoiDetail(loiDetail);
            status = true;
        }

        return status;

    }

    public ApplicantDetailDTO populateApplicantionDetails(ApplicantDetailDTO detailDto, Long applicationNo) {

        TbCfcApplicationMstEntity masterEntity = cfcApplicationService.getCFCApplicationByApplicationId(applicationNo,
                detailDto.getOrgId());

        if (masterEntity != null) {
            detailDto.setApplicantTitle(masterEntity.getApmTitle());
            detailDto.setApplicantFirstName(masterEntity.getApmFname());
            detailDto.setApplicantLastName(masterEntity.getApmLname());
            if (StringUtils.isNotBlank(masterEntity.getApmMname())) {
                detailDto.setApplicantMiddleName(masterEntity.getApmMname());
            }
            if (StringUtils.isNotBlank(masterEntity.getApmBplNo())) {
                detailDto.setIsBPL(MainetConstants.YES);
                detailDto.setBplNo(masterEntity.getApmBplNo());
            } else {
                detailDto.setIsBPL(MainetConstants.NO);
            }

            if (masterEntity.getApmUID() != null && masterEntity.getApmUID() != 0) {
                detailDto.setAadharNo(String.valueOf(masterEntity.getApmUID()));
            }
        }

        CFCApplicationAddressEntity addressEntity = cfcApplicationService.getApplicantsDetails(applicationNo);
        if (addressEntity != null) {
            detailDto.setMobileNo(addressEntity.getApaMobilno());
            detailDto.setEmailId(addressEntity.getApaEmail());
            detailDto.setAreaName(addressEntity.getApaAreanm());
            detailDto.setPinCode(String.valueOf(addressEntity.getApaPincode()));
            if (addressEntity.getApaZoneNo() != null && addressEntity.getApaZoneNo() != 0) {
                detailDto.setDwzid1(addressEntity.getApaZoneNo());
            }
            if (addressEntity.getApaWardNo() != null && addressEntity.getApaWardNo() != 0) {
                detailDto.setDwzid2(addressEntity.getApaWardNo());
            }
            if (StringUtils.isNotBlank(addressEntity.getApaBlockno())) {
                detailDto.setDwzid3(Long.valueOf(addressEntity.getApaBlockno()));
            }
            if (StringUtils.isNotBlank(addressEntity.getApaCityName())) {
                detailDto.setVillageTownSub(addressEntity.getApaCityName());
            }
            if (StringUtils.isNotBlank(addressEntity.getApaRoadnm())) {
                detailDto.setRoadName(addressEntity.getApaRoadnm());
            }
        }
        return detailDto;
    }

    public void sendSmsEmail(AgencyRegistrationModel model, String menuUrl, String msgType) {
        SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
        smsDto.setOrgId(model.getOrgId());
        smsDto.setLangId(UserSession.getCurrent().getLanguageId());
        smsDto.setAppNo(String.valueOf(model.getApmApplicationId()));
        smsDto.setServName(getServiceMaster().getSmServiceName());
        smsDto.setMobnumber(model.getAgencyRequestDto().getMasterDto().getAgencyContactNo());
        smsDto.setAppName(model.getAgencyRequestDto().getMasterDto().getAgencyOwner());
        smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        if (StringUtils.isNotBlank(model.getAgencyRequestDto().getMasterDto().getAgencyEmail())) {
            smsDto.setEmail(model.getAgencyRequestDto().getMasterDto().getAgencyEmail());
        }

        ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
                MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, menuUrl, msgType, smsDto,
                UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

    }

    public List<CFCAttachment> preparePreviewOfFileUpload(final List<CFCAttachment> downloadDocs,
            List<DocumentDetailsVO> docs) {
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            long count = 1;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        CFCAttachment c = new CFCAttachment();
                        String path = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
                                file.getPath());
                        c.setAttPath(path);
                        c.setAttFname(file.getName());
                        c.setClmSrNo(count);
                        docs.stream().filter(doc -> doc.getDocumentSerialNo().equals(entry.getKey() + 1))
                                .forEach(doc -> {
                                    c.setClmDesc(doc.getDoc_DESC_ENGL());
                                    c.setClmId(doc.getDocumentId());
                                    c.setClmDesc(doc.getDoc_DESC_Mar());
                                    c.setClmDesc(doc.getDoc_DESC_ENGL());

                                });
                        count++;
                        downloadDocs.add(c);
                    } catch (final Exception e) {
                        // LOGGER.error("Exception has been occurred in file byte to string
                        // conversions", e);
                    }
                }
            }
        }

        return downloadDocs;
    }

    public boolean closeWorkFlowTask() {
        boolean status = false;
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setIsFinalApproval(true);
        taskAction.setIsObjectionAppealApplicable(false);
        if (StringUtils.isNotBlank(UserSession.getCurrent().getEmployee().getEmpemail())) {
            taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
        }
        taskAction.setApplicationId(getApmApplicationId());
        taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        taskAction.setTaskId(getWorkflowActionDto().getTaskId());

        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        LookUp workProcessLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
                getServiceMaster().getSmProcessId(), UserSession.getCurrent().getOrganisation());
        workflowProcessParameter.setProcessName(workProcessLookUp.getDescLangFirst());
        workflowProcessParameter.setWorkflowTaskAction(taskAction);
        try {
            ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
                    .updateWorkflow(workflowProcessParameter);
            status = true;
        } catch (Exception exception) {
            throw new FrameworkException("Exception occured while updating work flow", exception);
        }
        return status;
    }

    public AgencyRegistrationRequestDto getAgencyRequestDto() {
        return agencyRequestDto;
    }

    public void setAgencyRequestDto(AgencyRegistrationRequestDto agencyRequestDto) {
        this.agencyRequestDto = agencyRequestDto;
    }

    public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public List<DocumentDetailsVO> getApprovalDocumentAttachment() {
        return approvalDocumentAttachment;
    }

    public void setApprovalDocumentAttachment(List<DocumentDetailsVO> approvalDocumentAttachment) {
        this.approvalDocumentAttachment = approvalDocumentAttachment;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    public List<AdvertiserMasterDto> getMasterDtoList() {
        return masterDtoList;
    }

    public void setMasterDtoList(List<AdvertiserMasterDto> masterDtoList) {
        this.masterDtoList = masterDtoList;
    }

    public List<LicenseLetterDto> getLicenseDto() {
        return licenseDto;
    }

    public void setLicenseDto(List<LicenseLetterDto> licenseDto) {
        this.licenseDto = licenseDto;
    }

    public List<TbLoiDet> getLoiDetail() {
        return loiDetail;
    }

    public void setLoiDetail(List<TbLoiDet> loiDetail) {
        this.loiDetail = loiDetail;
    }

    public List<String[]> getAgencyLicNoNameList() {
        return agencyLicNoNameList;
    }

    public void setAgencyLicNoNameList(List<String[]> agencyLicNoNameList) {
        this.agencyLicNoNameList = agencyLicNoNameList;
    }

    public LookUp getAgenctCategoryLookUp() {
        return agenctCategoryLookUp;
    }

    public void setAgenctCategoryLookUp(LookUp agenctCategoryLookUp) {
        this.agenctCategoryLookUp = agenctCategoryLookUp;
    }

    public AdvertiserMasterDto getAdvertiserMasterDto() {
        return advertiserMasterDto;
    }

    public void setAdvertiserMasterDto(AdvertiserMasterDto advertiserMasterDto) {
        this.advertiserMasterDto = advertiserMasterDto;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getCheckListApplFlag() {
        return checkListApplFlag;
    }

    public void setCheckListApplFlag(String checkListApplFlag) {
        this.checkListApplFlag = checkListApplFlag;
    }

    public String getApplicationchargeApplFlag() {
        return applicationchargeApplFlag;
    }

    public void setApplicationchargeApplFlag(String applicationchargeApplFlag) {
        this.applicationchargeApplFlag = applicationchargeApplFlag;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getFormDisplayFlag() {
        return formDisplayFlag;
    }

    public void setFormDisplayFlag(String formDisplayFlag) {
        this.formDisplayFlag = formDisplayFlag;
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

    public String getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(String successFlag) {
        this.successFlag = successFlag;
    }

    public String getPayableFlag() {
        return payableFlag;
    }

    public void setPayableFlag(String payableFlag) {
        this.payableFlag = payableFlag;
    }

    public Double getTotalLoiAmount() {
        return totalLoiAmount;
    }

    public void setTotalLoiAmount(Double totalLoiAmount) {
        this.totalLoiAmount = totalLoiAmount;
    }

    public String getOwnershipPrefix() {
        return ownershipPrefix;
    }

    public void setOwnershipPrefix(String ownershipPrefix) {
        this.ownershipPrefix = ownershipPrefix;
    }

    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    public String getOpenMode() {
        return openMode;
    }

    public void setOpenMode(String openMode) {
        this.openMode = openMode;
    }

    public String getOwnershipTypeValue() {
        return ownershipTypeValue;
    }

    public void setOwnershipTypeValue(String ownershipTypeValue) {
        this.ownershipTypeValue = ownershipTypeValue;
    }

    public List<TbApprejMas> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<TbApprejMas> remarkList) {
        this.remarkList = remarkList;
    }

    public List<String> getLoiRemarkList() {
        return loiRemarkList;
    }

    public void setLoiRemarkList(List<String> loiRemarkList) {
        this.loiRemarkList = loiRemarkList;
    }

}
