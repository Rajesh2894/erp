package com.abm.mainet.property.ui.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.ui.model.LoiGenerationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.repository.TbTaxMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.MutationDto;
import com.abm.mainet.property.dto.MutationIntimationDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.PublicNoticeDto;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.ui.validator.NewPropertyRegistrationValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class MutationModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(MutationModel.class);

    private String ownershipPrefix;

    private String ownershipTypeValue;

    private String ownershipPrefixNew;

    private String saveButFlag;

    private String appliChargeFlag;

    private String mutationLevelFlag; // M-mutation A-Authorization

    private Long deptId;

    private Long orgId;

    private Long serviceId;

    private String serviceName;

    private Integer ownerDetailTableCount;

    private boolean isLastAuthorizer;

    private boolean isBeforeLastAuthorizer;

    private MutationDto mutationdto = new MutationDto();

    private List<LookUp> location = new ArrayList<>(0);

    private List<DocumentDetailsVO> checkList = new ArrayList<>();

    private List<BillDisplayDto> charges = new ArrayList<>();

    private PropertyTransferMasterDto propTransferDto = null;

    private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = null;// Main DTO to Bind Data

    private List<CFCAttachment> documentList = new ArrayList<>();

    private PublicNoticeDto publicNoticeDto = new PublicNoticeDto();

    private String noOfDaysAuthFlag;

    @Resource
    private IFileUploadService fileUpload;

    @Autowired
    private MutationService mutationService;

    @Autowired
    private IChallanService iChallanService;
    @Autowired
    private IChecklistSearchService checklistSearchService;
    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;
    @Autowired
    private ServiceMasterService serviceMaster;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ILocationMasService locationMasService;
    @Autowired
    private IOrganisationService organisationService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;
    
    @Autowired
    private TbTaxMasJpaRepository tbTaxMasJpaRepository;
    
    @Autowired
    private com.abm.mainet.common.service.IReceiptEntryService receiptEntryService;

    /* private ChecklistStatusView applicationDetails; */
    private long currentServiceId;
    private List<CFCAttachment> attachmentList = new ArrayList<>(0);
    private boolean newApplication;
    private String[] listOfChkboxStatus;
    /* For Land details integration with revenue department */
    private ArrayOfKhasraDetails arrayOfKhasraDetails = new ArrayOfKhasraDetails();

    private ArrayOfPlotDetails arrayOfPlotDetails = new ArrayOfPlotDetails();

    private ArrayOfDiversionPlotDetails arrayOfDiversionPlotDetails = new ArrayOfDiversionPlotDetails();

    MutationIntimationDto mutationIntimationDto = new MutationIntimationDto();

    MutationIntimationDto mutationIntimationViewDto = new MutationIntimationDto();

    private List<LookUp> district = new ArrayList<>(0);

    private List<LookUp> tehsil = new ArrayList<>(0);

    private List<LookUp> village = new ArrayList<>(0);

    private List<LookUp> khasraNo = new ArrayList<>(0);

    private List<LookUp> plotNo = new ArrayList<>(0);

    private List<LookUp> mohalla = new ArrayList<>(0);

    private List<LookUp> blockStreet = new ArrayList<>(0);

    private String landTypePrefix;

    private String knownKhaNo;

    private String enteredKhasraNo;

    private String enteredPlotNo;

    private String serviceShortCode;

    private String mutIntimationFlag; // Mutation Intimation Flag :Y-if mutation file through Mutation Intimation

    private String approvalFlag;// Y -Approval task

    private Map<Long, Double> loiCharges = null;

    List<String> displayOwnerNames = new ArrayList<>();

    private List<TbLoiDet> loiDetail = new ArrayList<>();

    private String loiChargeApplFlag = MainetConstants.FlagN;

    private String showFlag = MainetConstants.FlagN;

    private Double totalLoiAmount;
    private String path;
    private String checkListVrfyFlag;;
    private int fileSize;
    private List<LookUp> documentsList = new ArrayList<>();
    private Long curentCheckerLevel;
    private Long applicationNo;
    private String bypassPublicNotice;
    private String kdmcMutFlag;
    private String referenceNo;
    private List<String> flatNoList;
    private String billingMethod;
    private String errorMsg;
    
    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
    
    private boolean sendbackToEmpFisrtLevel;
    private boolean authLevel;
    private boolean hideUserAction;
    
    private Long ownerType;
    
    private String date;
    private String dueDate;
	private String finYear;
	private String time;
	private ServiceMaster serviceMast = new ServiceMaster();
	private String deptName;// to print of acknowledgement
	private String applicantName;
	
	private String loiNo;
	private double applicationFee;
	private double totalAmntIncApplFee;
	private String formType;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    public void setDocumentsList(List<LookUp> documentsList) {
        this.documentsList = documentsList;
    }

    public List<LookUp> getDocumentsList() {
        final List<LookUp> documentDetailsList = new ArrayList<>(0);
        setPath(attachmentList.get(0).getAttPath());
        LookUp lookUp = null;
        for (final CFCAttachment temp : attachmentList) {
            lookUp = new LookUp(temp.getAttId(), temp.getAttPath());
            lookUp.setDescLangFirst(temp.getClmDescEngl());
            lookUp.setDescLangSecond(temp.getClmDesc());
            lookUp.setLookUpId(temp.getClmId());
            lookUp.setDefaultVal(temp.getAttPath());
            lookUp.setLookUpCode(temp.getAttFname());
            lookUp.setLookUpType(temp.getDmsDocId());
            lookUp.setLookUpParentId(temp.getAttId());
            lookUp.setLookUpExtraLongOne(temp.getClmSrNo());
            lookUp.setOtherField(temp.getMandatory());
            lookUp.setExtraStringField1(temp.getClmRemark());// reject message
            documentDetailsList.add(lookUp);
        }
        fileSize = 0;
        if (!documentDetailsList.isEmpty()) {
            fileSize = documentDetailsList.size();
        }
        return documentDetailsList;
    }

    @Override
    public boolean saveForm() {
        setCustomViewName("MutationForm");
        if (mutIntimationFlag == null) {
            List<DocumentDetailsVO> docs = getCheckList();
            docs = fileUpload.prepareFileUpload(docs);
            propTransferDto.setDocs(docs);
        }
        Organisation org = UserSession.getCurrent().getOrganisation();
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.MUT,
                org.getOrgid());
        ProvisionalAssesmentMstDto assMstDto = new ProvisionalAssesmentMstDto();
        List<DocumentDetailsVO> docs = getCheckList();
        docs = fileUpload.prepareFileUpload(docs);
        assMstDto.setDocs(docs);
        propTransferDto.setDocs(docs);
        propTransferDto.setDeptId(getDeptId());
        propTransferDto.setLocationId(UserSession.getCurrent().getLoggedLocId());
        propTransferDto.setLangId(UserSession.getCurrent().getLanguageId());
        propTransferDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        propTransferDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        propTransferDto.setMutIntiFlag(mutIntimationFlag);
        validateBean(assMstDto, NewPropertyRegistrationValidator.class);
        final CommonChallanDTO offline = getOfflineDTO();
        if (MainetConstants.Y_FLAG.equals(appliChargeFlag) && mutIntimationFlag == null) {
            if (propTransferDto.getCharges().isEmpty()) {
                addValidationError("Chargeable service. Charges not found");
            } else {
                validateBean(offline, CommonOfflineMasterValidator.class);
            }
        }

        if (hasValidationErrors()) {
            if (mutIntimationFlag != null) {
                setCustomViewName("MutationIntimationView");
                return false;
            }
            return false;
        }
        if (Utility.isEnvPrefixAvailable(org, MainetConstants.Property.MUT)) {
            ApplicantDetailDTO applicationDto = new ApplicantDetailDTO();
            setApplicatDetails(applicationDto, propTransferDto);
//            String appNo = propertyService.createApplicationNumberForSKDCL(applicationDto);
//            offline.setReferenceNo(appNo);
//            setReferenceNo(appNo);
            setApplicantName(propTransferDto.getPropTransferOwnerList().get(0).getOwnerName());
            offline.setApplicantName(propTransferDto.getPropTransferOwnerList().get(0).getOwnerName());
//            propTransferDto.setReferenceNo(appNo);
            SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
            String appDate = sm.format(new Date());
            Date date = Utility.stringToDate(appDate, MainetConstants.DATE_FRMAT);
            // current date
            offline.setFromDateStr(appDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (service != null && service.getSmServiceDuration() > 0) {
                Long slaDuration = service.getSmServiceDuration();
                calendar.add(Calendar.DATE, slaDuration.intValue());
            }
            Date dueDate = calendar.getTime();
            String dueDateStr = sm.format(dueDate);
            // due date
            offline.setToDateStr(dueDateStr);
            final SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
            final String time = localDateFormat.format(new Date());
            offline.setTime(time);
            if (service != null && service.getSmServiceNameMar() != null) {
                offline.setServiceNameMar(service.getSmServiceNameMar());
            }
            Organisation organisation = organisationService.getOrganisationById(org.getOrgid());
            if (organisation != null && organisation.getONlsOrgnameMar() != null) {
                offline.setOrgNameMar(organisation.getONlsOrgnameMar());
            }
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)
        		||Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        	propTransferDto.setApplicantDetailDto(getApplicantDetailDto());
        }
        mutationService.saveMutation(propTransferDto);
        
        setReferenceNo(String.valueOf(propTransferDto.getApmApplicationId()));
        if (MainetConstants.Y_FLAG.equals(appliChargeFlag) && mutIntimationFlag == null) {
            setChallanDToandSaveChallanData(offline, charges, propTransferDto, provisionalAssesmentMstDto);
        } else {
            propTransferDto.setLocationId(provisionalAssesmentMstDto.getLocId());
            propTransferDto.setAssesmentMstDto(provisionalAssesmentMstDto);
            mutationService.callWorkFlowForFreeService(propTransferDto);
            setSuccessMessage(getAppSession().getMessage("property.mutation.save") + ":" + propTransferDto.getApmApplicationId());
        }
        // SMS Defect#134244
        PropertyTransferOwnerDto ownDtlDto = propTransferDto.getPropTransferOwnerList().get(0);
        SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(ownDtlDto.geteMail());
        dto.setMobnumber(ownDtlDto.getMobileno());
        dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        dto.setAppNo(propTransferDto.getApmApplicationId().toString());
        ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "MutationForm.html",
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());

        return true;
    }

    private void setApplicatDetails(ApplicantDetailDTO dto, PropertyTransferMasterDto propTranMstDto) {
        dto.setOrgId(propTranMstDto.getOrgId());
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
        LocationOperationWZMapping wzMapping = locationMasService.findbyLocationAndDepartment(
                propTranMstDto.getLocationId(), deptId);
        if (wzMapping != null) {
            if (wzMapping.getCodIdOperLevel1() != null) {
                dto.setDwzid1(wzMapping.getCodIdOperLevel1());
            }
            if (wzMapping.getCodIdOperLevel2() != null) {
                dto.setDwzid2(wzMapping.getCodIdOperLevel2());
            }
            if (wzMapping.getCodIdOperLevel3() != null) {
                dto.setDwzid3(wzMapping.getCodIdOperLevel3());
            }
            if (wzMapping.getCodIdOperLevel4() != null) {
                dto.setDwzid4(wzMapping.getCodIdOperLevel4());
            }
            if (wzMapping.getCodIdOperLevel5() != null) {
                dto.setDwzid5(wzMapping.getCodIdOperLevel5());
            }
        }
    }

    private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final List<BillDisplayDto> charges,
            PropertyTransferMasterDto asseMstDto, ProvisionalAssesmentMstDto prevAssessDetail) {
        final UserSession session = UserSession.getCurrent();
        PropertyTransferOwnerDto ownDtlDto = asseMstDto.getPropTransferOwnerList().get(0);
        // offline.setAmountToPay(Double.toString(asseMstDto.getBillTotalAmt()));

        offline.setUserId(session.getEmployee().getEmpId());
        offline.setOrgId(session.getOrganisation()
                .getOrgid());
        offline.setLangId(session.getLanguageId());
        offline.setLgIpMac(session.getEmployee().getEmppiservername());
        StringBuilder jointOwnerName = new StringBuilder();// task 92282 changes by Arun
        LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(asseMstDto.getOwnerType(),
                UserSession.getCurrent().getOrganisation());
        if(Utility.isEnvPrefixAvailable(session.getOrganisation(), MainetConstants.ENV_ASCL) || 
        		Utility.isEnvPrefixAvailable(session.getOrganisation(), MainetConstants.ENV_PSCL)) {
        	offline.setApplicantFullName(prevAssessDetail.getOwnerFullName());
        	offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
        }else {
        	if (lookUp.getLookUpCode().equals(MainetConstants.Property.JO)) {
                asseMstDto.getPropTransferOwnerList().forEach(data -> {
                    if (StringUtils.isEmpty(jointOwnerName.toString())) {
                        jointOwnerName.append(data.getOwnerName() + MainetConstants.WHITE_SPACE + data.getGuardianName());
                    } else {
                        jointOwnerName.append(MainetConstants.BLANK_WITH_SPACE + data.getOwnerName()
                                + MainetConstants.WHITE_SPACE + data.getGuardianName());
                    }
                });
                offline.setApplicantFullName(jointOwnerName.toString());
            } else {
                offline.setApplicantFullName(
                        ownDtlDto.getOwnerName() + MainetConstants.WHITE_SPACE + ownDtlDto.getGuardianName());
            }
        }
        
        offline.setTransferType(asseMstDto.getTransferTypeDesc());
        /*
         * task: 26817 UsageType set value by Sharvan
         */
        Double totalAMout = 0d;
        for (BillDisplayDto charge : asseMstDto.getCharges()) {
            if (offline.getFeeIds().containsKey(charge.getTaxId())) {
                Double value = offline.getFeeIds().get(charge.getTaxId());
                offline.getFeeIds().put(charge.getTaxId(), charge.getTotalTaxAmt().doubleValue() + value);
            } else {
                offline.getFeeIds().put(charge.getTaxId(), charge.getTotalTaxAmt().doubleValue());
            }
            totalAMout += charge.getTotalTaxAmt().doubleValue();
        }
        offline.setAmountToPay(totalAMout.toString());
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setEmailId(prevAssessDetail.getAssEmail());
        offline.setApplicantName(ownDtlDto.getOwnerName());
        offline.setApplNo(asseMstDto.getApmApplicationId());
        offline.setApplicantAddress(prevAssessDetail.getAssAddress());
        offline.setUniquePrimaryId(asseMstDto.getProAssNo());
        offline.setMobileNumber(ownDtlDto.getMobileno());
        offline.setServiceId(getServiceId());
        offline.setDeptId(getDeptId());
        offline.setPropNoConnNoEstateNoV(prevAssessDetail.getAssNo());
        offline.setUniquePropertyId(prevAssessDetail.getUniquePropertyId());
        offline.setAssParshadWard1(prevAssessDetail.getAssParshadWard1());
        offline.setAssParshadWard2(prevAssessDetail.getAssParshadWard2());
        offline.setAssParshadWard3(prevAssessDetail.getAssParshadWard3());
        offline.setAssParshadWard4(prevAssessDetail.getAssParshadWard4());
        offline.setAssParshadWard5(prevAssessDetail.getAssParshadWard5());
        
        if (Utility.isEnvPrefixAvailable(session.getOrganisation(), MainetConstants.Property.MUT)
                && asseMstDto.getReferenceNo() != null) {
            offline.setReferenceNo(asseMstDto.getReferenceNo());
        } else {
            offline.setReferenceNo(prevAssessDetail.getAssOldpropno());
        }
        offline.setPlotNo(prevAssessDetail.getTppPlotNo());

        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        // Task#92835
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.MUT, session.getOrganisation()
                .getOrgid());
        String checkListRequiredFlag = service.getSmCheckListReq();
        LookUp checkListVerificationyFlag = null;
        try {
            checkListVerificationyFlag = CommonMasterUtility.getValueFromPrefixLookUp("CVF", "ENV", session.getOrganisation());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if ((checkListVerificationyFlag != null) && (checkListRequiredFlag != null)) {
            if (StringUtils.equals(checkListVerificationyFlag.getOtherField(), MainetConstants.FlagY)) {
                if (getCheckList() != null && !getCheckList().isEmpty() && checkListRequiredFlag.equals(MainetConstants.Y_FLAG)) {
                    offline.setDocumentUploaded(true);
                } else if (getCheckList() != null && !getCheckList().isEmpty()
                        && checkListRequiredFlag.equals(MainetConstants.N_FLAG)) {
                    offline.setDocumentUploaded(false);
                }
            }
        } else {
            if (getCheckList() != null && !getCheckList().isEmpty()) {
                offline.setDocumentUploaded(true);
            }
        }
        // Task#92835 END
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());
        
        if ((prevAssessDetail.getAssParshadWard1() != null) && (prevAssessDetail.getAssParshadWard1() > 0)) {
        	offline.setParshadWard1(CommonMasterUtility.getHierarchicalLookUp(prevAssessDetail.getAssParshadWard1(), UserSession.getCurrent().getOrganisation())
                    .getLookUpDesc());
        }
        if ((prevAssessDetail.getAssParshadWard2() != null) && (prevAssessDetail.getAssParshadWard2() > 0)) {
        	offline.setParshadWard2(CommonMasterUtility.getHierarchicalLookUp(prevAssessDetail.getAssParshadWard2(), UserSession.getCurrent().getOrganisation())
                    .getLookUpDesc());
        }
        if ((prevAssessDetail.getAssParshadWard3() != null) && (prevAssessDetail.getAssParshadWard3() > 0)) {
        	offline.setParshadWard3(CommonMasterUtility.getHierarchicalLookUp(prevAssessDetail.getAssParshadWard3(), UserSession.getCurrent().getOrganisation())
                    .getLookUpDesc());
        }
        if ((prevAssessDetail.getAssParshadWard4() != null) && (prevAssessDetail.getAssParshadWard4() > 0)) {
        	offline.setParshadWard4(CommonMasterUtility.getHierarchicalLookUp(prevAssessDetail.getAssParshadWard4(), UserSession.getCurrent().getOrganisation())
                    .getLookUpDesc());
        }
        if ((prevAssessDetail.getAssParshadWard5() != null) && (prevAssessDetail.getAssParshadWard5() > 0)) {
        	offline.setParshadWard5(CommonMasterUtility.getHierarchicalLookUp(prevAssessDetail.getAssParshadWard5(), UserSession.getCurrent().getOrganisation())
                    .getLookUpDesc());
        }
        
        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(
                        MainetConstants.PAYMENT_TYPE.OFFLINE)) {
            final ChallanMaster master = iChallanService
                    .InvokeGenerateChallan(offline);
            offline.setChallanValidDate(master
                    .getChallanValiDate());
            offline.setChallanNo(master.getChallanNo());
            setSuccessMessage(getAppSession().getMessage("property.mutation.save") + asseMstDto.getApmApplicationId());
        } else if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
            final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
                    serviceName);
            setReceiptDTO(printDto);

            // US#102200 // pushing document to DMS
            String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "="
                    + ApplicationSession.getInstance().getMessage("property.birtName.mutationReceipt")
                    + "&__format=pdf&RP_ORGID=" + offline.getOrgId() + "&RP_RCPTNO=" + printDto.getReceiptId();
            Utility.pushDocumentToDms(URL, prevAssessDetail.getAssNo(), MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                    fileUpload);
            setSuccessMessage(getAppSession().getMessage("property.mutation.save") + asseMstDto.getApmApplicationId());
        }
        setOfflineDTO(offline);
    }

    public void setDropDownValues(ProvisionalAssesmentMstDto assMst, Organisation org) {
        assMst.setProAssOwnerTypeName(CommonMasterUtility
                .getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation())
                .getDescLangFirst());
        if (assMst.getPropLvlRoadType() != null) {
            assMst.setProAssdRoadfactorDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(assMst.getPropLvlRoadType(), UserSession.getCurrent().getOrganisation())
                    .getDescLangFirst());
        }
        if (assMst.getAssWard1() != null) {
            assMst.setAssWardDesc1(
                    CommonMasterUtility.getHierarchicalLookUp(assMst.getAssWard1(), UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
        }

        if (assMst.getAssWard2() != null) {
            assMst.setAssWardDesc2(
                    CommonMasterUtility
                            .getHierarchicalLookUp(assMst.getAssWard2(), UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
        }

        if (assMst.getAssWard3() != null) {
            assMst.setAssWardDesc3(
                    CommonMasterUtility
                            .getHierarchicalLookUp(assMst.getAssWard3(), UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
        }

        if (assMst.getAssWard4() != null) {
            assMst.setAssWardDesc4(
                    CommonMasterUtility
                            .getHierarchicalLookUp(assMst.getAssWard4(), UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
        }

        if (assMst.getAssWard5() != null) {
            assMst.setAssWardDesc5(
                    CommonMasterUtility
                            .getHierarchicalLookUp(assMst.getAssWard5(), UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
        }
        String ownerTypeCode = CommonMasterUtility
                .getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), org).getLookUpCode();
        if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
            for (ProvisionalAssesmentOwnerDtlDto dto : assMst.getProvisionalAssesmentOwnerDtlDtoList()) {
                if (dto.getGenderId() != null) {
                    dto.setProAssGenderId(
                            CommonMasterUtility
                                    .getNonHierarchicalLookUpObject(dto.getGenderId(), UserSession.getCurrent().getOrganisation())
                                    .getDescLangFirst());
                } else {
                    dto.setProAssGenderId(MainetConstants.CommonConstants.NA);
                }
                if (dto.getRelationId() != null) {
                    dto.setProAssRelationId(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(),
                                    UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                } else {
                    dto.setProAssRelationId(MainetConstants.CommonConstants.NA);
                }
            }
        } else {
            ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
            ownerDto.setGenderId(null);
            ownerDto.setRelationId(null);
            ownerDto.setAssoAddharno(null);
        }

        this.getDistrict().forEach(dis -> {
            if (Long.toString(dis.getLookUpId()).equals(assMst.getAssDistrict())) {
                assMst.setAssDistrictDesc(dis.getDescLangFirst());
            }
        });

        this.getTehsil().forEach(teh -> {
            if (teh.getLookUpCode().equals(assMst.getAssTahasil())) {
                assMst.setAssTahasilDesc(teh.getDescLangFirst());
            }
        });

        this.getVillage().forEach(vil -> {
            if (vil.getLookUpCode().equals(assMst.getTppVillageMauja())) {
                assMst.setTppVillageMaujaDesc(vil.getDescLangFirst());
            }
        });

        for (LookUp moh : this.getMohalla()) {
            if (moh.getLookUpCode().equals(assMst.getMohalla())) {
                assMst.setMohallaDesc(moh.getDescLangFirst());
                break;
            }
        }

        for (LookUp sheet : this.getBlockStreet()) {
            if (sheet.getLookUpCode().equals(assMst.getAssStreetNo())) {
                assMst.setAssStreetNoDesc(sheet.getDescLangFirst());
                break;
            }
        }
    }

    public void setlandTypeDetails() {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(getLandTypePrefix());
        dto.setDistrictId(getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(getProvisionalAssesmentMstDto().getMohalla());
        setDistrict(dataEntrySuiteService.findDistrictByLandType(dto));
        setTehsil(dataEntrySuiteService.getTehsilListByDistrict(dto));
        setVillage(dataEntrySuiteService.getVillageListByTehsil(dto));
        if (getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)
                || getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            setMohalla(dataEntrySuiteService.getMohallaListByVillageId(dto));
            setBlockStreet(dataEntrySuiteService.getStreetListByMohallaId(dto));
        }
    }

    // T#90050
    public boolean saveLoiData() {
        boolean status = false;

        final UserSession session = UserSession.getCurrent();
        PropertyTransferMasterDto tranDto = getPropTransferDto();
        ProvisionalAssesmentMstDto prvDto = getProvisionalAssesmentMstDto();

        tranDto.setProAssNo(prvDto.getAssNo());
        tranDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        tranDto.setSmServiceId(getServiceId());
        tranDto.setDeptId(getDeptId());
        tranDto.setScrutinyChargeFlag(MainetConstants.FlagY);
        PropertyTransferMasterDto fetchChargesForMuatation = mutationService.fetchChargesForMuatation(tranDto, prvDto);
        Map<Long, Double> loiCharges = null;
        if (fetchChargesForMuatation != null && CollectionUtils.isNotEmpty(fetchChargesForMuatation.getCharges())) {
            loiCharges = new HashMap<Long, Double>();
            for (BillDisplayDto billDto : fetchChargesForMuatation.getCharges()) {
                loiCharges.put(billDto.getTaxId(), billDto.getTotalTaxAmt().doubleValue());
            }
        }

        if (MapUtils.isNotEmpty(loiCharges)) {
        	TbLoiMas tbLoiMas = ApplicationContextProvider.getApplicationContext().getBean(LoiGenerationModel.class).saveLOIAppData(
                    loiCharges, getServiceId(), loiDetail, false/* approvalLetterGenerationApplicable */,
                    getWorkflowActionDto());
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
        		TbServiceReceiptMasEntity receiptDetailsByAppId = receiptEntryService.getReceiptDetailsByAppId(getPropTransferDto().getApmApplicationId(), orgId);
        		if(receiptDetailsByAppId != null) {
        		receiptDetailsByAppId.getReceiptFeeDetail().forEach(receiptDet ->{
        			Long taxDescId = tbTaxMasJpaRepository.getTaxDescIdByTaxId(receiptDet.getTaxId());
        			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(taxDescId, UserSession.getCurrent().getOrganisation());
        			if(lookUp != null && StringUtils.equals(lookUp.getLookUpCode(), "APC")) {
        				setApplicationFee(receiptDet.getRfFeeamount().doubleValue());
        			}
        		});
        	}
        	}
            setLoiDetail(loiDetail);
            if(StringUtils.isNotBlank(tbLoiMas.getLoiNo())) {
            	setLoiNo(tbLoiMas.getLoiNo());
            }
            status = true;
        }
        return status;

    }
    
    public  Date dueDate(Date date) {
		LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(getServiceMast().getSmDurationUnit(),
				UserSession.getCurrent().getOrganisation());
		Date dueDate = null;
		if(lookUp != null) {
			if(StringUtils.equals(MainetConstants.FlagD, lookUp.getLookUpCode())) {
				dueDate = Utility.getAddedDateBy2(date, getServiceMast().getSmServiceDuration().intValue());
			}
			if(StringUtils.equals(MainetConstants.FlagM, lookUp.getLookUpCode())) {
				dueDate = Utility.getAddedMonthsBy(date, getServiceMast().getSmServiceDuration().intValue());
			}
			if(StringUtils.equals(MainetConstants.FlagY, lookUp.getLookUpCode())) {
				dueDate = Utility.getAddedYearsBy(date, getServiceMast().getSmServiceDuration().intValue());
			}
			if(StringUtils.equals(MainetConstants.FlagH, lookUp.getLookUpCode())) {
				Calendar calendar = Calendar.getInstance();
			    calendar.setTime(date);
			    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + getServiceMast().getSmServiceDuration().intValue());
				dueDate = calendar.getTime();
			}
		}
		return dueDate;
	}

    public MutationDto getMutationdto() {
        return mutationdto;
    }

    public void setMutationdto(MutationDto mutationdto) {
        this.mutationdto = mutationdto;
    }

    public String getOwnershipPrefix() {
        return ownershipPrefix;
    }

    public void setOwnershipPrefix(String ownershipPrefix) {
        this.ownershipPrefix = ownershipPrefix;
    }

    public String getOwnershipTypeValue() {
        return ownershipTypeValue;
    }

    public void setOwnershipTypeValue(String ownershipTypeValue) {
        this.ownershipTypeValue = ownershipTypeValue;
    }

    public List<LookUp> getLocation() {
        return location;
    }

    public void setLocation(List<LookUp> location) {
        this.location = location;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public List<BillDisplayDto> getCharges() {
        return charges;
    }

    public void setCharges(List<BillDisplayDto> charges) {
        this.charges = charges;
    }

    public String getSaveButFlag() {
        return saveButFlag;
    }

    public void setSaveButFlag(String saveButFlag) {
        this.saveButFlag = saveButFlag;
    }

    public String getAppliChargeFlag() {
        return appliChargeFlag;
    }

    public void setAppliChargeFlag(String appliChargeFlag) {
        this.appliChargeFlag = appliChargeFlag;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getOwnerDetailTableCount() {
        return ownerDetailTableCount;
    }

    public void setOwnerDetailTableCount(Integer ownerDetailTableCount) {
        this.ownerDetailTableCount = ownerDetailTableCount;
    }

    public PropertyTransferMasterDto getPropTransferDto() {
        return propTransferDto;
    }

    public void setPropTransferDto(PropertyTransferMasterDto propTransferDto) {
        this.propTransferDto = propTransferDto;
    }

    public boolean isLastAuthorizer() {
        return isLastAuthorizer;
    }

    public void setLastAuthorizer(boolean isLastAuthorizer) {
        this.isLastAuthorizer = isLastAuthorizer;
    }

    public boolean isBeforeLastAuthorizer() {
        return isBeforeLastAuthorizer;
    }

    public void setBeforeLastAuthorizer(boolean isBeforeLastAuthorizer) {
        this.isBeforeLastAuthorizer = isBeforeLastAuthorizer;
    }

    public String getMutationLevelFlag() {
        return mutationLevelFlag;
    }

    public void setMutationLevelFlag(String mutationLevelFlag) {
        this.mutationLevelFlag = mutationLevelFlag;
    }

    public String getOwnershipPrefixNew() {
        return ownershipPrefixNew;
    }

    public void setOwnershipPrefixNew(String ownershipPrefixNew) {
        this.ownershipPrefixNew = ownershipPrefixNew;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public PublicNoticeDto getPublicNoticeDto() {
        return publicNoticeDto;
    }

    public void setPublicNoticeDto(PublicNoticeDto publicNoticeDto) {
        this.publicNoticeDto = publicNoticeDto;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
        return provisionalAssesmentMstDto;
    }

    public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
        this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
    }

    public ArrayOfKhasraDetails getArrayOfKhasraDetails() {
        return arrayOfKhasraDetails;
    }

    public void setArrayOfKhasraDetails(ArrayOfKhasraDetails arrayOfKhasraDetails) {
        this.arrayOfKhasraDetails = arrayOfKhasraDetails;
    }

    public ArrayOfPlotDetails getArrayOfPlotDetails() {
        return arrayOfPlotDetails;
    }

    public void setArrayOfPlotDetails(ArrayOfPlotDetails arrayOfPlotDetails) {
        this.arrayOfPlotDetails = arrayOfPlotDetails;
    }

    public ArrayOfDiversionPlotDetails getArrayOfDiversionPlotDetails() {
        return arrayOfDiversionPlotDetails;
    }

    public void setArrayOfDiversionPlotDetails(ArrayOfDiversionPlotDetails arrayOfDiversionPlotDetails) {
        this.arrayOfDiversionPlotDetails = arrayOfDiversionPlotDetails;
    }

    public List<LookUp> getDistrict() {
        return district;
    }

    public void setDistrict(List<LookUp> district) {
        this.district = district;
    }

    public List<LookUp> getTehsil() {
        return tehsil;
    }

    public void setTehsil(List<LookUp> tehsil) {
        this.tehsil = tehsil;
    }

    public List<LookUp> getVillage() {
        return village;
    }

    public void setVillage(List<LookUp> village) {
        this.village = village;
    }

    public List<LookUp> getKhasraNo() {
        return khasraNo;
    }

    public void setKhasraNo(List<LookUp> khasraNo) {
        this.khasraNo = khasraNo;
    }

    public List<LookUp> getPlotNo() {
        return plotNo;
    }

    public void setPlotNo(List<LookUp> plotNo) {
        this.plotNo = plotNo;
    }

    public List<LookUp> getMohalla() {
        return mohalla;
    }

    public void setMohalla(List<LookUp> mohalla) {
        this.mohalla = mohalla;
    }

    public List<LookUp> getBlockStreet() {
        return blockStreet;
    }

    public void setBlockStreet(List<LookUp> blockStreet) {
        this.blockStreet = blockStreet;
    }

    public String getLandTypePrefix() {
        return landTypePrefix;
    }

    public void setLandTypePrefix(String landTypePrefix) {
        this.landTypePrefix = landTypePrefix;
    }

    public String getKnownKhaNo() {
        return knownKhaNo;
    }

    public void setKnownKhaNo(String knownKhaNo) {
        this.knownKhaNo = knownKhaNo;
    }

    public String getEnteredKhasraNo() {
        return enteredKhasraNo;
    }

    public void setEnteredKhasraNo(String enteredKhasraNo) {
        this.enteredKhasraNo = enteredKhasraNo;
    }

    public String getEnteredPlotNo() {
        return enteredPlotNo;
    }

    public void setEnteredPlotNo(String enteredPlotNo) {
        this.enteredPlotNo = enteredPlotNo;
    }

    public String getServiceShortCode() {
        return serviceShortCode;
    }

    public void setServiceShortCode(String serviceShortCode) {
        this.serviceShortCode = serviceShortCode;
    }

    public MutationIntimationDto getMutationIntimationDto() {
        return mutationIntimationDto;
    }

    public void setMutationIntimationDto(MutationIntimationDto mutationIntimationDto) {
        this.mutationIntimationDto = mutationIntimationDto;
    }

    public MutationIntimationDto getMutationIntimationViewDto() {
        return mutationIntimationViewDto;
    }

    public void setMutationIntimationViewDto(MutationIntimationDto mutationIntimationViewDto) {
        this.mutationIntimationViewDto = mutationIntimationViewDto;
    }

    public String getMutIntimationFlag() {
        return mutIntimationFlag;
    }

    public void setMutIntimationFlag(String mutIntimationFlag) {
        this.mutIntimationFlag = mutIntimationFlag;
    }

    public String getApprovalFlag() {
        return approvalFlag;
    }

    public void setApprovalFlag(String approvalFlag) {
        this.approvalFlag = approvalFlag;
    }

    public Map<Long, Double> getLoiCharges() {
        return loiCharges;
    }

    public void setLoiCharges(Map<Long, Double> loiCharges) {
        this.loiCharges = loiCharges;
    }

    public List<String> getDisplayOwnerNames() {
        return displayOwnerNames;
    }

    public void setDisplayOwnerNames(List<String> displayOwnerNames) {
        this.displayOwnerNames = displayOwnerNames;
    }

    public List<TbLoiDet> getLoiDetail() {
        return loiDetail;
    }

    public void setLoiDetail(List<TbLoiDet> loiDetail) {
        this.loiDetail = loiDetail;
    }

    public String getLoiChargeApplFlag() {
        return loiChargeApplFlag;
    }

    public void setLoiChargeApplFlag(String loiChargeApplFlag) {
        this.loiChargeApplFlag = loiChargeApplFlag;
    }

    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

    public Double getTotalLoiAmount() {
        return totalLoiAmount;
    }

    public void setTotalLoiAmount(Double totalLoiAmount) {
        this.totalLoiAmount = totalLoiAmount;
    }

    public String getNoOfDaysAuthFlag() {
        return noOfDaysAuthFlag;
    }

    public void setNoOfDaysAuthFlag(String noOfDaysAuthFlag) {
        this.noOfDaysAuthFlag = noOfDaysAuthFlag;
    }

    /*
     * public ChecklistStatusView getApplicationDetails() { return applicationDetails; } public void
     * setApplicationDetails(ChecklistStatusView applicationDetails) { this.applicationDetails = applicationDetails; }
     */

    public long getCurrentServiceId() {
        return currentServiceId;
    }

    public void setCurrentServiceId(long currentServiceId) {
        this.currentServiceId = currentServiceId;
    }

    public List<CFCAttachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<CFCAttachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public boolean isNewApplication() {
        return newApplication;
    }

    public void setNewApplication(boolean newApplication) {
        this.newApplication = newApplication;
    }

    public String[] getListOfChkboxStatus() {
        return listOfChkboxStatus;
    }

    public void setListOfChkboxStatus(String[] listOfChkboxStatus) {
        this.listOfChkboxStatus = listOfChkboxStatus;
    }

    public String getCheckListVrfyFlag() {
        return checkListVrfyFlag;
    }

    public void setCheckListVrfyFlag(String checkListVrfyFlag) {
        this.checkListVrfyFlag = checkListVrfyFlag;
    }

    public Long getCurentCheckerLevel() {
        return curentCheckerLevel;
    }

    public void setCurentCheckerLevel(Long curentCheckerLevel) {
        this.curentCheckerLevel = curentCheckerLevel;
    }

    public Long getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(Long applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getBypassPublicNotice() {
        return bypassPublicNotice;
    }

    public void setBypassPublicNotice(String bypassPublicNotice) {
        this.bypassPublicNotice = bypassPublicNotice;
    }

    public String getKdmcMutFlag() {
        return kdmcMutFlag;
    }

    public void setKdmcMutFlag(String kdmcMutFlag) {
        this.kdmcMutFlag = kdmcMutFlag;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public List<String> getFlatNoList() {
        return flatNoList;
    }

    public void setFlatNoList(List<String> flatNoList) {
        this.flatNoList = flatNoList;
    }

    public String getBillingMethod() {
        return billingMethod;
    }

    public void setBillingMethod(String billingMethod) {
        this.billingMethod = billingMethod;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public boolean isSendbackToEmpFisrtLevel() {
		return sendbackToEmpFisrtLevel;
	}

	public void setSendbackToEmpFisrtLevel(boolean sendbackToEmpFisrtLevel) {
		this.sendbackToEmpFisrtLevel = sendbackToEmpFisrtLevel;
	}

	public boolean isAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(boolean authLevel) {
		this.authLevel = authLevel;
	}

	public boolean isHideUserAction() {
		return hideUserAction;
	}

	public void setHideUserAction(boolean hideUserAction) {
		this.hideUserAction = hideUserAction;
	}

	public Long getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Long ownerType) {
		this.ownerType = ownerType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ServiceMaster getServiceMast() {
		return serviceMast;
	}

	public void setServiceMast(ServiceMaster serviceMast) {
		this.serviceMast = serviceMast;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getLoiNo() {
		return loiNo;
	}

	public void setLoiNo(String loiNo) {
		this.loiNo = loiNo;
	}

	public double getApplicationFee() {
		return applicationFee;
	}

	public void setApplicationFee(double applicationFee) {
		this.applicationFee = applicationFee;
	}

	public double getTotalAmntIncApplFee() {
		return totalAmntIncApplFee;
	}

	public void setTotalAmntIncApplFee(double totalAmntIncApplFee) {
		this.totalAmntIncApplFee = totalAmntIncApplFee;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}
	
}