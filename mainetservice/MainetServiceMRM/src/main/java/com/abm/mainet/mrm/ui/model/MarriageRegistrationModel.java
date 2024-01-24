package com.abm.mainet.mrm.ui.model;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.LoiDetail;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.mrm.dto.AppointmentDTO;
import com.abm.mainet.mrm.dto.HusbandDTO;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.dto.WifeDTO;
import com.abm.mainet.mrm.dto.WitnessDetailsDTO;
import com.abm.mainet.mrm.service.IMarriageService;
import com.abm.mainet.mrm.ui.validator.MarriageValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class MarriageRegistrationModel extends AbstractFormModel {

    private static final long serialVersionUID = 7013176894774579712L;
    private MarriageDTO marriageDTO = new MarriageDTO();
    private List<MarriageDTO> marriageDTOs = new ArrayList<MarriageDTO>();
    private WitnessDetailsDTO witnessDTO = new WitnessDetailsDTO();
    private String modeType;
    private String witnessModeType;
    private String checkListApplFlag;
    private String applicationChargeApplFlag;
    private String payableFlag;
    private double amountToPay;
    private String successFlag;
    private String approvalProcess = "N";
    private String status = "P";
    private String printBT;// click from summary Screen ,based on this hide BT
    private Double totalLoiAmount;
    private ServiceMaster serviceMaster = new ServiceMaster();

    private Map<Long, String> uploadedfile = new HashMap<>(0);
    private Long photoId;
    private Long thumbId;
    private String uploadType;
    private Map<String, File> UploadMap = new HashMap<>();

    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    private List<CFCAttachment> documentList = new ArrayList<>();// use at approval page to display documents
    private List<DocumentDetailsVO> approvalDocumentAttachment = new ArrayList<>();// use for approval document save
    private List<TbLoiDet> loiDetail = new ArrayList<>();

    // for search filter at summary screens
    private List<HusbandDTO> husbandList = new ArrayList<>();
    private List<WifeDTO> wifeList = new ArrayList<>();
    private ChecklistStatusView applicationDetails;
    private String mobNo;
    private String email;

    private Boolean applicableENV;
    private String photoThumbDisp = "N";
    private String conditionFlag = "N";
    private String hideMarSaveBT = "N";

    @Autowired
    IMarriageService marriageService;

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private IWorkFlowTypeService workFlowTypeService;

    @Autowired
    private TbLoiMasService iTbLoiMasService;

    public MarriageDTO getMarriageDTO() {
        return marriageDTO;
    }

    public void setMarriageDTO(MarriageDTO marriageDTO) {
        this.marriageDTO = marriageDTO;
    }

    public List<MarriageDTO> getMarriageDTOs() {
        return marriageDTOs;
    }

    public void setMarriageDTOs(List<MarriageDTO> marriageDTOs) {
        this.marriageDTOs = marriageDTOs;
    }

    public WitnessDetailsDTO getWitnessDTO() {
        return witnessDTO;
    }

    public void setWitnessDTO(WitnessDetailsDTO witnessDTO) {
        this.witnessDTO = witnessDTO;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getWitnessModeType() {
        return witnessModeType;
    }

    public void setWitnessModeType(String witnessModeType) {
        this.witnessModeType = witnessModeType;
    }

    public String getCheckListApplFlag() {
        return checkListApplFlag;
    }

    public void setCheckListApplFlag(String checkListApplFlag) {
        this.checkListApplFlag = checkListApplFlag;
    }

    public String getApplicationChargeApplFlag() {
        return applicationChargeApplFlag;
    }

    public void setApplicationChargeApplFlag(String applicationChargeApplFlag) {
        this.applicationChargeApplFlag = applicationChargeApplFlag;
    }

    public String getPayableFlag() {
        return payableFlag;
    }

    public void setPayableFlag(String payableFlag) {
        this.payableFlag = payableFlag;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(String successFlag) {
        this.successFlag = successFlag;
    }

    public String getApprovalProcess() {
        return approvalProcess;
    }

    public void setApprovalProcess(String approvalProcess) {
        this.approvalProcess = approvalProcess;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrintBT() {
        return printBT;
    }

    public void setPrintBT(String printBT) {
        this.printBT = printBT;
    }

    public Double getTotalLoiAmount() {
        return totalLoiAmount;
    }

    public void setTotalLoiAmount(Double totalLoiAmount) {
        this.totalLoiAmount = totalLoiAmount;
    }

    public Map<Long, String> getUploadedfile() {
        return uploadedfile;
    }

    public void setUploadedfile(Map<Long, String> uploadedfile) {
        this.uploadedfile = uploadedfile;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Long getThumbId() {
        return thumbId;
    }

    public void setThumbId(Long thumbId) {
        this.thumbId = thumbId;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public List<DocumentDetailsVO> getApprovalDocumentAttachment() {
        return approvalDocumentAttachment;
    }

    public void setApprovalDocumentAttachment(List<DocumentDetailsVO> approvalDocumentAttachment) {
        this.approvalDocumentAttachment = approvalDocumentAttachment;
    }

    public List<TbLoiDet> getLoiDetail() {
        return loiDetail;
    }

    public void setLoiDetail(List<TbLoiDet> loiDetail) {
        this.loiDetail = loiDetail;
    }

    public List<HusbandDTO> getHusbandList() {
        return husbandList;
    }

    public void setHusbandList(List<HusbandDTO> husbandList) {
        this.husbandList = husbandList;
    }

    public List<WifeDTO> getWifeList() {
        return wifeList;
    }

    public void setWifeList(List<WifeDTO> wifeList) {
        this.wifeList = wifeList;
    }

    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    public Map<String, File> getUploadMap() {
        return UploadMap;
    }

    public void setUploadMap(Map<String, File> uploadMap) {
        UploadMap = uploadMap;
    }

    public ChecklistStatusView getApplicationDetails() {
        return applicationDetails;
    }

    public void setApplicationDetails(ChecklistStatusView applicationDetails) {
        this.applicationDetails = applicationDetails;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getApplicableENV() {
        return applicableENV;
    }

    public void setApplicableENV(Boolean applicableENV) {
        this.applicableENV = applicableENV;
    }

    public String getPhotoThumbDisp() {
        return photoThumbDisp;
    }

    public void setPhotoThumbDisp(String photoThumbDisp) {
        this.photoThumbDisp = photoThumbDisp;
    }

    public String getConditionFlag() {
        return conditionFlag;
    }

    public void setConditionFlag(String conditionFlag) {
        this.conditionFlag = conditionFlag;
    }

    public String getHideMarSaveBT() {
        return hideMarSaveBT;
    }

    public void setHideMarSaveBT(String hideMarSaveBT) {
        this.hideMarSaveBT = hideMarSaveBT;
    }

    public boolean validatePayment() {

        validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);

        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    // D#113261
    public boolean validateInputs() {
        validateBean(this, MarriageValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    public boolean saveForm(List<WitnessDetailsDTO> witnessDetailsDTOs) {
        CommonChallanDTO offline = getOfflineDTO();
        boolean status = false;
        MarriageDTO marriageDTO = getMarriageDTO();
        List<DocumentDetailsVO> documents = getCheckList();
        documents = fileUpload.prepareFileUpload(documents);
        validateInputs(documents);
        if (!marriageDTO.isFree()) {
            validateBean(offline, CommonOfflineMasterValidator.class);
        }
        if (hasValidationErrors()) {
            return false;
        }

        marriageDTO.setDocumentList(documents);
        marriageDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        marriageDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
        marriageDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        marriageDTO.setServiceId(getServiceMaster().getSmServiceId());
        marriageDTO.setStatus(getStatus());

        marriageDTO.setDeptId(getServiceMaster().getTbDepartment().getDpDeptid());

        // save in TB_MRM_WITNESS_DET
        marriageDTO.setWitnessDetailsDTO(witnessDetailsDTOs);
        marriageService.saveWitnessDetails(marriageDTO);

        if (marriageDTO.getApplicationId() != null && !getStatus().equalsIgnoreCase("APPROVED")) {

            // setApmApplicationId(responseDto.getApplicationId());
            if (!marriageDTO.isFree()) {
                setAndSaveChallanDto(offline, marriageDTO);
            }

            setSuccessMessage(ApplicationSession.getInstance().getMessage("mrm.save.marriage.success", new Object[] {
                    String.valueOf(marriageDTO.getApplicationId()) }));

            status = true;

        }

        return status;
    }

    public boolean validateInputs(final List<DocumentDetailsVO> dto) {
        boolean flag = false;
        if ((dto != null) && !dto.isEmpty()) {
            for (final DocumentDetailsVO doc : dto) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
                    if (doc.getDocumentByteCode() == null) {
                        addValidationError(
                                ApplicationSession.getInstance().getMessage("mrm.upload.mandatory.documents"));
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    public void setAndSaveChallanDto(CommonChallanDTO offline, MarriageDTO marriageDTO) {

        offline.setApplNo(marriageDTO.getApplicationId());
        offline.setAmountToPay(Double.toString(getAmountToPay()));
        offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        offline.setLangId(UserSession.getCurrent().getLanguageId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setServiceId(getServiceMaster().getSmServiceId());
        String aplicantName = marriageDTO.getApplicantName();
        offline.setApplicantName(aplicantName);

        offline.setMobileNumber(getMarriageDTO().getApplicantDetailDto().getMobileNo());
        offline.setEmailId(getMarriageDTO().getApplicantDetailDto().getEmailId());

        offline.setApplicantAddress(marriageDTO.getHusbandDTO().getFullAddrEng());
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

            // ChallanReceiptPrintDTO printDto = new ChallanReceiptPrintDTO();
            setReceiptDTO(printDto);

            setSuccessMessage(getAppSession().getMessage("mrm.receipt"));
        }

    }

    public String saveAppointmentAndDecision(AppointmentDTO appointmentDTO) {
        RequestDTO requestDto = new RequestDTO();
        requestDto.setApplicationId(getWorkflowActionDto().getApplicationId());
        requestDto.setDepartmentName(MainetConstants.MRM.MRM_DEPT_CODE);
        requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDto.setServiceId(getServiceId());
        requestDto.setDeptId(getServiceMaster().getTbDepartment().getDpDeptid());
        requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

        List<DocumentDetailsVO> docs = new ArrayList<>();
        DocumentDetailsVO document = new DocumentDetailsVO();
        document.setDocumentSerialNo(1L);
        docs.add(document);
        setApprovalDocumentAttachment(fileUpload.prepareFileUpload(docs));
        List<Long> documentIds = ApplicationContextProvider.getApplicationContext()
                .getBean(IChecklistVerificationService.class)
                .fetchAttachmentIdByAppid(getApmApplicationId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());

        getWorkflowActionDto().setAttachementId(documentIds);
        boolean lastApproval = workFlowTypeService
                .isLastTaskInCheckerTaskList(getWorkflowActionDto().getTaskId());

        String message = "";
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

        if (lastApproval
                && StringUtils.equalsIgnoreCase(getWorkflowActionDto().getDecision(),
                        MainetConstants.WorkFlow.Decision.APPROVED)
                && StringUtils.equalsIgnoreCase(getServiceMaster().getSmScrutinyChargeFlag(),
                        MainetConstants.FlagY)) {

            boolean approvalFlag = marriageService.saveAppointmentAndDecision(taskAction, requestDto,
                    getApprovalDocumentAttachment(), getServiceMaster(), appointmentDTO);

            if (approvalFlag) {
                message = ApplicationSession.getInstance().getMessage("mrm.loi.generate.success", new Object[] {
                        getWorkflowActionDto().getLoiDetails().get(0).getLoiNumber() });

            } else {
                message = ApplicationSession.getInstance().getMessage("Loi charges not found. Please define rule");
            }

        } else if (StringUtils.equalsIgnoreCase(getWorkflowActionDto().getDecision(),
                MainetConstants.WorkFlow.Decision.APPROVED)) {

            boolean approvalFlag = marriageService.saveAppointmentAndDecision(taskAction, requestDto,
                    getApprovalDocumentAttachment(), getServiceMaster(), appointmentDTO);
            if (approvalFlag) {
                message = ApplicationSession.getInstance().getMessage("mrm.application.approval.success.message");
                // send appointment schedule MSG
                sendSmsEmail(this, MainetConstants.MRM.MAR_REG_URL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL);
            } else {
                message = ApplicationSession.getInstance().getMessage("mrm.application.approval.fail.message");
            }

        } else if (StringUtils.equalsIgnoreCase(getWorkflowActionDto().getDecision(),
                MainetConstants.WorkFlow.Decision.REJECTED)) {
            boolean approvalFlag = marriageService.saveAppointmentAndDecision(taskAction, requestDto,
                    getApprovalDocumentAttachment(), getServiceMaster(), appointmentDTO);
            if (approvalFlag) {
                // Ask to sir for Mobile no because we not store in DB
                /*
                 * model.sendSmsEmail(model, MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_AUTH_SMS_URL,
                 * MainetConstants.AdvertisingAndHoarding.REJECTED_MESSAGE);
                 */
                message = "Application rejected successfully";
            } else {
                message = "Application rejected Failed";
            }
        }

        return message;
    }

    public boolean saveLoiData() {
        boolean status = false;
        Map<Long, Double> loiCharges = marriageService.getLoiCharges(UserSession.getCurrent().getOrganisation().getOrgid(),
                getServiceMaster().getSmServiceId(), getServiceMaster().getSmShortdesc(), getMarriageDTO().getMarDate(),
                getMarriageDTO().getAppDate());

        if (MapUtils.isNotEmpty(loiCharges)) {
            // D#110852
            /*
             * ApplicationContextProvider.getApplicationContext().getBean(LoiGenerationModel.class).saveLOIAppData(chargeMap,
             * getServiceMaster().getSmServiceId(), loiDetail, true, getWorkflowActionDto());
             */
            saveLOIAppData(loiCharges, getServiceMaster().getSmServiceId(), loiDetail, true, getWorkflowActionDto());

            setLoiDetail(loiDetail);
            status = true;

        }

        return status;

    }

    public TbLoiMas saveLOIAppData(Map<Long, Double> loiCharges, Long serviceId, List<TbLoiDet> loiDetList,
            Boolean approvalLetterGenerationApplicable, WorkflowTaskAction wfActionDto) {
        TbLoiMas loiMasDto = new TbLoiMas();
        final UserSession session = UserSession.getCurrent();
        final Long paymentType = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagC,
                PrefixConstants.LookUpPrefix.LPT, session.getOrganisation()).getLookUpId();

        TbLoiDet loiDetails = new TbLoiDet();
        loiMasDto.setOrgid(session.getOrganisation().getOrgid());
        loiMasDto.setUserId(session.getEmployee().getEmpId());
        loiMasDto.setLgIpMac(session.getEmployee().getLgIpMac());
        loiMasDto.setLmoddate(new Date());
        loiMasDto.setLoiPaid(MainetConstants.Common_Constant.NO);
        loiMasDto.setLoiStatus(MainetConstants.FlagA);
        loiMasDto.setLoiDate(new Date());
        loiMasDto.setLoiYear(Calendar.getInstance().get(Calendar.YEAR));
        loiMasDto.setLoiServiceId(serviceId);
        loiMasDto.setLoiRefId(wfActionDto.getApplicationId());
        loiMasDto.setLoiApplicationId(wfActionDto.getApplicationId());

        Long taxId = null;
        Double taxAmount = new Double(0);
        Double totalAmount = new Double(0);
        for (final Entry<Long, Double> loiCharge : loiCharges.entrySet()) {
            taxId = loiCharge.getKey();
            taxAmount = loiCharge.getValue();
            loiDetails.setLoiAmount(BigDecimal.valueOf(taxAmount));
            loiDetails.setOrgid(session.getOrganisation().getOrgid());
            loiDetails.setLoiChrgid(taxId);
            loiDetails.setLgIpMac(session.getEmployee().getLgIpMac());
            loiDetails.setUserId(session.getEmployee().getEmpId());
            loiDetails.setLoiCharge(MainetConstants.Common_Constant.YES);
            loiDetails.setLoiPaytype(paymentType);
            loiDetails.setLmoddate(new Date());
            loiDetList.add(loiDetails);
            totalAmount = totalAmount + taxAmount;
        }

        loiMasDto.setLoiAmount(BigDecimal.valueOf(totalAmount));
        iTbLoiMasService.saveLoiDetails(loiMasDto, loiDetList, null);
        LoiDetail loiDetail = new LoiDetail();
        loiDetail.setLoiNumber(loiMasDto.getLoiNo());
        // D#112445
        if (totalAmount != 0) {
            loiDetail.setLoiPaymentApplicable(true);
        } else {
            loiDetail.setLoiPaymentApplicable(false);
        }

        loiDetail.setIsComplianceApplicable(false);
        loiDetail.setIsApprovalLetterGenerationApplicable(approvalLetterGenerationApplicable);
        wfActionDto.setLoiDetails(new ArrayList<>());
        wfActionDto.getLoiDetails().add(loiDetail);
        return loiMasDto;
    }

    public void sendSmsEmail(MarriageRegistrationModel model, String menuUrl, String msgType) {
        SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
        smsDto.setOrgId(model.getOrgId());
        smsDto.setLangId(UserSession.getCurrent().getLanguageId());
        smsDto.setAppNo(String.valueOf(model.getMarriageDTO().getApplicationId()));
        smsDto.setServName(getServiceMaster().getSmServiceName());

        smsDto.setMobnumber(model.getMarriageDTO().getApplicantDetailDto().getMobileNo());
        smsDto.setAppName(model.getMarriageDTO().getApplicantDetailDto().getApplicantFirstName() + " "
                + model.getMarriageDTO().getApplicantDetailDto().getApplicantLastName());
        // smsDto.setUserId(model.getMarriageDTO().getEmpId());
        smsDto.setUserId(model.getUserSession().getEmployee().getEmpId());
        if (StringUtils.isNotBlank(model.getMarriageDTO().getApplicantDetailDto().getEmailId())) {
            smsDto.setEmail(model.getMarriageDTO().getApplicantDetailDto().getEmailId());
        }
        if (model.getMarriageDTO().getAppointmentDTO().getAppointmentDate() != null) {
            smsDto.setFrmDt(Utility.dateToString(model.getMarriageDTO().getAppointmentDTO().getAppointmentDate()));
            smsDto.setAppDate(model.getMarriageDTO().getAppointmentDTO().getAppointmentTime());
            smsDto.setPlace(UserSession.getCurrent().getOrganisation().getOrgAddress());
        }

        smsDto.setRegNo(model.getMarriageDTO().getSerialNo());

        ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
                MainetConstants.MRM.MRM_DEPT_CODE, menuUrl, msgType, smsDto,
                UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

    }

    public Map<Long, String> getCachePathUpload(final String uploadType) {
        uploadedfile.clear();
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            Long count = 0l;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                if ((getPhotoId() != null && (entry.getKey() != null) && (entry.getKey().longValue() == getPhotoId()))
                        || (getThumbId() != null && (entry.getKey() != null) && (entry.getKey().longValue() == getThumbId()))) {
                    if ((entry.getKey() != null) && (entry.getKey().longValue() == getThumbId())) {
                        count = 1l;
                    }
                    for (final File file : entry.getValue()) {
                        final String mapKey = entry.getKey().toString() + uploadType;
                        UploadMap.put(mapKey, file);
                        String fileName = null;
                        final String path = file.getPath().replace("\\", "/");
                        fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
                        uploadedfile.put(count, fileName);
                        count = 0l;
                    }
                }
            }
        }
        return uploadedfile;
    }

    public void getDownloadFile(final String path1, final String name, final String val, final String guidRan,
            final String uploadType) {
        final String uploadKey = val + uploadType;
        final Map<Long, Set<File>> fileMap = FileUploadUtility.getCurrent().getFileMap();
        final String uidPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                + MainetConstants.FILE_PATH_SEPARATOR + guidRan + MainetConstants.FILE_PATH_SEPARATOR;
        FileUploadUtility.getCurrent().setExistingFolderPath(uidPath);

        final String folderPath = FileUploadUtility.getCurrent().getExistingFolderPath();
        if (folderPath != null)
            Utility.downloadedFileUrl(path1 + MainetConstants.FILE_PATH_SEPARATOR + name, folderPath,
                    FileNetApplicationClient.getInstance());

        final String path = Filepaths.getfilepath() + FileUploadUtility.getCurrent().getExistingFolderPath()
                + MainetConstants.FILE_PATH_SEPARATOR + name;
        final File file = new File(path);
        UploadMap.put(uploadKey, file);
        boolean flag = true;

        for (final Map.Entry<Long, Set<File>> entry : fileMap.entrySet()) {
            if (entry.getKey().toString().equals(val)) {
                final Set<File> set = entry.getValue();
                set.add(file);
                fileMap.put(Long.valueOf(val), set);
                flag = false;
            }
        }
        if (flag) {
            final Set<File> fileDetails = new LinkedHashSet<>();
            fileDetails.add(file);
            fileMap.put(Long.valueOf(val), fileDetails);
        }

    }

    public Map<Long, String> deleteSingleUpload(final Long photoId, final Long id) {
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {

            uploadedfile.remove(id);
            UploadMap.remove(photoId + getUploadType());

            final Iterator<File> fileItr = FileUploadUtility.getCurrent().getFileMap().get(photoId).iterator();

            while (fileItr.hasNext()) {
                final File file = fileItr.next();
                file.delete();
                fileItr.remove();
                FileUploadUtility.getCurrent().getFileMap().remove(photoId);
            }

        }
        return uploadedfile;
    }

    public void populateModel(MarriageRegistrationModel marriageModel, Long marId, Long applicationId, String mode) {
        // set applicableENV
        List<LookUp> envLookUpList = CommonMasterUtility.getLookUps(MainetConstants.ENV,
                UserSession.getCurrent().getOrganisation());
        Boolean envPresent = envLookUpList.stream().anyMatch(
                env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.SKDCL)
                        && StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
        if (envPresent) {
            marriageModel.setApplicableENV(true);
        } else {
            marriageModel.setApplicableENV(false);
        }
        marriageModel.getMarriageDTO().setServiceId(marriageModel.getServiceMaster().getSmServiceId());
        if (mode.equals(MainetConstants.MODE_CREATE)) {
            marriageModel.setMarriageDTO(new MarriageDTO());
            marriageModel.setModeType(MainetConstants.MODE_CREATE);
        } else {
            final MarriageDTO marriageDTO = marriageService.getMarriageDetailsById(marId, applicationId, null, null,
                    marriageModel.getOrgId(), MainetConstants.MRM.hitFrom);
            marriageDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            marriageModel.setMarriageDTO(marriageDTO);
            if (mode.equals(MainetConstants.MODE_VIEW)) {
                marriageModel.setModeType(MainetConstants.MODE_VIEW);
            } else {
                marriageModel.setModeType(MainetConstants.MRM.STATUS.FORM_STATUS_DRAFT);
            }
            ApplicantDetailDTO applicantDetail = new ApplicantDetailDTO();
            if (marriageDTO.getApplicationId() != null) {
                applicantDetail = marriageService.getApplicantData(marriageDTO.getApplicationId(), marriageDTO.getOrgId());
                String middleName = applicantDetail.getApplicantMiddleName() != null ? applicantDetail.getApplicantMiddleName()
                        : "";
                marriageModel.getMarriageDTO()
                        .setApplicantName(applicantDetail.getApplicantFirstName() + " " + middleName + " "
                                + applicantDetail.getApplicantLastName());
            }
            // image fetch
            final String guidRanDNum = Utility.getGUIDNumber();
            if (StringUtils.isNotEmpty(marriageDTO.getHusbandDTO().getCapturePhotoPath()))
                getDownloadFile(marriageDTO.getHusbandDTO().getCapturePhotoPath(),
                        marriageDTO.getHusbandDTO().getCapturePhotoName(), "90", guidRanDNum, "H");

            if (StringUtils.isNotEmpty(marriageDTO.getHusbandDTO().getCaptureFingerprintPath()))
                getDownloadFile(marriageDTO.getHusbandDTO().getCaptureFingerprintPath(),
                        marriageDTO.getHusbandDTO().getCaptureFingerprintName(), "91", guidRanDNum, "H");

            if (StringUtils.isNotEmpty(marriageDTO.getWifeDTO().getCapturePhotoPath()))
                getDownloadFile(marriageDTO.getWifeDTO().getCapturePhotoPath(),
                        marriageDTO.getWifeDTO().getCapturePhotoName(), "990", guidRanDNum, "W");

            if (StringUtils.isNotEmpty(marriageDTO.getWifeDTO().getCaptureFingerprintPath()))
                getDownloadFile(marriageDTO.getWifeDTO().getCaptureFingerprintPath(),
                        marriageDTO.getWifeDTO().getCaptureFingerprintName(), "991", guidRanDNum, "W");

            // D#133934 Witness photo & thumb download
            marriageDTO.getWitnessDetailsDTO().stream().forEach(witn -> {
                if (StringUtils.isNotEmpty(witn.getCapturePhotoPath()))
                    getDownloadFile(witn.getCapturePhotoPath(), witn.getCapturePhotoName(), witn.getWitnessId() + "0",
                            guidRanDNum, "WT");

                if (StringUtils.isNotEmpty(witn.getCaptureFingerprintPath()))
                    getDownloadFile(witn.getCaptureFingerprintPath(), witn.getCaptureFingerprintName(), witn.getWitnessId() + "1",
                            guidRanDNum, "WT");
            });

            // code for fetch checklist documents
            /*
             * marriageModel.setDocumentList(
             * ApplicationContextProvider.getApplicationContext().getBean(IChecklistVerificationService.class)
             * .getDocumentUploadedForAppId(marriageDTO.getApplicationId(),
             * UserSession.getCurrent().getOrganisation().getOrgid()));
             */
            List<CFCAttachment> docList = ApplicationContextProvider.getApplicationContext()
                    .getBean(IChecklistVerificationService.class)
                    .getDocumentUploadedForAppId(marriageDTO.getApplicationId(),
                            UserSession.getCurrent().getOrganisation().getOrgid());

            // set only approve document
            List<CFCAttachment> filterDocList = docList.stream()
                    .filter(doc -> doc.getClmAprStatus() != null && doc.getClmAprStatus().equalsIgnoreCase(MainetConstants.FlagY))
                    .collect(Collectors.toList());
            if (filterDocList.isEmpty()) {
                marriageModel.setDocumentList(docList);
            } else {
                marriageModel.setDocumentList(filterDocList);
            }

            marriageModel.getMarriageDTO().setApplicantDetailDto(applicantDetail);
        }
    }

    // D#134156
    public boolean updateApprovalFlag(Long orgId, Long applicationId) {

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
        taskAction.setApplicationId(applicationId);
        taskAction.setTaskId(getWorkflowActionDto().getTaskId());

        return marriageService.executeApprovalWorkflowAction(taskAction, getServiceMaster().getSmServiceId(),
                getWorkflowActionDto().getDecision());
    }

}
