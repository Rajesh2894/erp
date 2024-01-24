package com.abm.mainet.workManagement.ui.model;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillDedDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.VigilanceDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WmsRaBillTaxDetailsDto;
import com.abm.mainet.workManagement.dto.WorkDefinationYearDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.dto.WorksRABillDto;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.VigilanceService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.service.WorksRABillService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;
import com.abm.mainet.workManagement.ui.validator.WorkEstimateApprovalValidator;

/**
 * @author vishwajeet.kumar
 * @since 30 May 2018
 */
@Component
@Scope("session")
public class WorkMBApprovalModel extends AbstractFormModel {

    private static final long serialVersionUID = -6001871625851726773L;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private IChecklistVerificationService checkListService;

    @Autowired
    public IFileUploadService fileUpload;

    @Autowired
    private VigilanceService vigilanceService;

    @Autowired
    private ILocationMasService locMasService;

    @Autowired
    private TenderInitiationService tenderInitiationService;

    @Autowired
    private WorkDefinitionService definitionService;

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private IWorkflowRequestService workflowRequestService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MeasurementBookService bookService;

    @Autowired
    private WmsProjectMasterService projectMasterService;

    @Autowired
    private WorksRABillService raBillService;
    
    @Autowired
	private IWorkflowTaskService iWorkflowTaskService;

    private List<MeasurementBookMasterDto> measureMentBookMastDtosList = new ArrayList<>();
    private List<MeasurementBookMasterDto> measureMentBookList = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private MeasurementBookMasterDto masterDto;
    private List<TbDepartment> departmentsList;
    private List<Object[]> employeeList;
    // private List<ApprovalTermsConditionDto> termsConditionDtosList = new ArrayList<>();

    private Long actualTaskId;
    private boolean mbfinalApproval;
    private boolean finalApproval;
    private String saveMode;
    private Long workMbId;
    private String estimateMode;
    private VigilanceDto vigilanceDto;
    private Long parentOrgId;
    private VendorBillApprovalDTO approvalDTO = new VendorBillApprovalDTO();
    private List<VendorBillExpDetailDTO> expDetListDto = new ArrayList<>();
    private VendorBillExpDetailDTO billExpDetailDTO = new VendorBillExpDetailDTO();
    private WorkOrderDto workOrder = null;
    private TenderWorkDto tenderWorkDto = null;
    private WorkDefinitionDto definitionDto = null;
    private WorkDefinitionDto mbDefinitionDtoEmployee = new WorkDefinitionDto();
    private boolean raBill;
    private WmsProjectMasterDto projectMasterDto = new WmsProjectMasterDto();
    private TbDepartment department = new TbDepartment();
    private WorksRABillDto raBillDto = new WorksRABillDto();
    private WorksRABillDto previousRaBillDto = new WorksRABillDto();
    private List<WorkflowTaskActionWithDocs> workflowTaskAction = new ArrayList<>();
    private List<VigilanceDto> vigilanceDtoList;
    private Long workFlowId;
    private String flagForSendBack;
    private String removeFileByIds;
    private Long workId;
    private String workMbCode;
    private String workBillNumber;
    private String mbTaskName;
    private String raBillDateStr;
    private String workRaCode;
    private String contractFromDate;
    private String contractToDate;
    private BigDecimal totalRaAmount;
    private BigDecimal totalGrandRaAmount;
    private String flag;
    private String memoType;
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<Long> fileCountInspectionUpload;
    private List<DocumentDetailsVO> inspectionAttachment = new ArrayList<>();
    private String workFlag;
    private String completedFlag;
    private String tenderPercentValueFlag;

    /**
     * Save Final User Action And Generate RA Bill
     */
    @Override
    public boolean saveForm() {
        boolean status = true;
        final WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
        validateBean(workFlowActionDto, WorkEstimateApprovalValidator.class);

        if (hasValidationErrors()) {
            return false;
        }
        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setServiceId(getServiceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

        ServiceMaster service = serviceMaster.getServiceMasterByShortCode(MainetConstants.WorksManagement.MBA,
                getParentOrgId());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());

        List<DocumentDetailsVO> docs = new ArrayList<>();
        DocumentDetailsVO document = new DocumentDetailsVO();
        document.setDocumentSerialNo(1L);
        docs.add(document);

        setAttachments(fileUpload.prepareFileUpload(docs));
        fileUpload.doFileUpload(getAttachments(), requestDTO);

        List<Long> attacheMentIds = checkListService.fetchAllAttachIdByReferenceId(
                getWorkflowActionDto().getReferenceId(), UserSession.getCurrent().getOrganisation().getOrgid());

        prepareWorkFlowTaskAction(getWorkflowActionDto());
        getWorkflowActionDto().setAttachementId(attacheMentIds);
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        String flag = applicationContext.getBean(WorksWorkFlowService.class).updateWorkFlowWorksService(getWorkflowActionDto(),
                service.getSmServiceId(), UserSession.getCurrent().getOrganisation().getOrgid());
        WorkflowRequest workflowRequest = workflowRequestService.getWorkflowRequestByAppIdOrRefId(null, getWorkRaCode(),
                getParentOrgId());
        if (workflowRequest != null
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            raBillService.updateStatusByRaId(getRaBillDto().getRaId(), MainetConstants.FlagR);
            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL))
            	raBillService.updateRaBillTypeById(getRaBillDto().getRaId(), MainetConstants.FlagR);
        }
        if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
            raBillService.updateStatusByRaId(getRaBillDto().getRaId(), MainetConstants.FlagA);
            saveBillApproval(getRaBillDto());
            setRaBill(true);
        }

        if (workflowRequest != null
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("work.mb.approval.rejected.successfully"));
        } else if (workflowRequest != null
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
        	
        	 Long level=null;
             level=iWorkflowTaskService.findByTaskId(workFlowActionDto.getTaskId()).getCurentCheckerLevel();
            
             if(level!=null) {
            	 raBillDto.setLevelCheck(level);
             }
             
             if(raBillDto.getLevelCheck()!=null && raBillDto.getLevelCheck()>1)
             {
            	 workFlowActionDto.setSendBackToGroup((raBillDto.getLevelCheck().intValue()-1));
            	 workFlowActionDto.setSendBackToLevel((raBillDto.getLevelCheck().intValue()-1));
             }else {
            	 workFlowActionDto.setSendBackToGroup(0);
            	 workFlowActionDto.setSendBackToLevel(0);
            	 
             }
             Long parentOrgid = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
                     .findById(workflowRequest.getWorkflowTypeId()).getCurrentOrgId();
             String processName = "";
             if(parentOrgid != null)
                processName = serviceMaster.getProcessName(requestDTO.getServiceId(), parentOrgid);
             WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
             workflowProcessParameter.setProcessName(processName);
             
             workflowProcessParameter.setWorkflowTaskAction(workFlowActionDto);

            setSuccessMessage(ApplicationSession.getInstance().getMessage("work.mb.approval.send.back"));
        } else {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("work.mb.approval.creation.success"));
        }

        return status;
    }

    /**
     * prepare workFlow Task to workFlow approval
     * @param workflowActionDto
     * @return
     */
    private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {

        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
        workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());

        workflowActionDto.setDateOfAction(new Date());
        workflowActionDto.setCreatedDate(new Date());
        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
        workflowActionDto.setIsFinalApproval(false);
        return workflowActionDto;

    }

    /**
     * Save RA Bill And Push data in Account Module to generate RA Bill
     * @param raBillDto
     */
    public void saveBillApproval(WorksRABillDto raBillDto) {
        String mbNumbers = MainetConstants.BLANK;
        ResponseEntity<?> responseEntity = null;
        List<VendorBillDedDetailDTO> deductionDetList = new ArrayList<>();
        VendorBillDedDetailDTO billDedDetailDTO = new VendorBillDedDetailDTO();
        workOrder = workOrderService.getWorkOredrByOrderId(getMeasureMentBookMastDtosList().get(0).getWorkOrId());
        tenderWorkDto = tenderInitiationService.findWorkByWorkId(workOrder.getContractMastDTO().getContId());
        definitionDto = definitionService.findAllWorkDefinitionById(tenderWorkDto.getWorkId());
        WorksRABillDto previousRaBillDto = raBillService.getPreviousRaBillDetails(raBillDto.getWorkId(),
                UserSession.getCurrent().getOrganisation().getOrgid(), raBillDto.getRaId());
        if (previousRaBillDto != null && previousRaBillDto.getRaTaxAmt() == null) {
            previousRaBillDto.setRaTaxAmt(new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO));
            if (previousRaBillDto.getRaBillAmt() == null)
                previousRaBillDto.setRaBillAmt(new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO));
            /*
             * if(getTenderAmount()!=null) previousRaBillDto.setRaBillAmtStr(Utility.convertBigNumberToWord(getTenderAmount()));
             */
        }
        setPreviousRaBillDto(previousRaBillDto);

        BigDecimal totalAmount = new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO);
        for (MeasurementBookMasterDto mbDto : getMeasureMentBookMastDtosList()) {
            List<MeasurementBookMasterDto> bookMasterDtos = bookService.findAbstractSheetReport(mbDto.getWorkMbId());
            for (MeasurementBookMasterDto measurementBookMasterDto : bookMasterDtos) {
                totalAmount = totalAmount.add(measurementBookMasterDto.getWorkActualAmt());
            }      
			//D103469
            if (bookMasterDtos.isEmpty()) {
				totalAmount=totalAmount.add(mbDto.getMbTotalAmt());
			}
        }
        setTotalRaAmount(totalAmount);

        if (getTenderAmount() != null && getRaBillDto().getRaBillTaxDtoWith() != null) {
        	BigDecimal raAmount = new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO);
        	 for (WmsRaBillTaxDetailsDto dto : getRaBillDto().getRaBillTaxDtoWith()) {
        		 raAmount = raAmount.add(dto.getRaTaxValue());
             } 
            getRaBillDto().setSanctionAmount(getTenderAmount().subtract(raAmount));
        }
        if (getTenderAmount() != null && getRaBillDto().getRaBillTaxDtoWith()!= null
                && getRaBillDto().getTotalTaxAmount() != null) {
            /*
             * getRaBillDto().setNetValue((getTenderAmount().add(getRaBillDto().getRaTaxAmt()))
             * .subtract(getRaBillDto().getRaBillTaxDto().getRaTaxValue()));
             */
            getRaBillDto().setNetValue(getRaBillDto().getRaBillAmt());
            getRaBillDto().setRaBillAmtStr(Utility.convertBigNumberToWord(getRaBillDto().getNetValue()));
        }

        if (definitionDto != null) {
            WmsProjectMasterDto projectMasetr = projectMasterService
                    .getProjectMasterByProjId(definitionDto.getProjId());
            if (projectMasetr != null && projectMasetr.getRsoDate() != null) {
                projectMasetr.setRsoDateDesc(Utility.dateToString(projectMasetr.getRsoDate()));
                setProjectMasterDto(projectMasetr);
            }
        }

        setRaBillDateStr(Utility.dateToString(new Date()));

        for (MeasurementBookMasterDto mbDto : getMeasureMentBookMastDtosList()) {
        	//#148734 -to show MB number on Bill / Invoice Entry form in Accounting entry
            /*if (mbDto.isCheckBox()) {*/ 
                if (mbDto.getWorkMbNo() != null)
                    mbNumbers += String.join(MainetConstants.operator.COMMA, mbDto.getWorkMbNo());
            /*}*/
        }

        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(null, raBillDto.getRaCode(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
        List<WorkflowTaskActionWithDocs> actionHistory = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowActionService.class)
                .getActionLogByUuidAndWorkflowId(raBillDto.getRaCode(), workflowRequest.getId(),
                        (short) UserSession.getCurrent().getLanguageId()); //D#131599-set langId to get task status
        setWorkflowTaskAction(actionHistory);

        approvalDTO.setBillEntryDate(Utility.dateToString(new Date()));

        // On 20/02/2020 New Changes behalf of Samadhan Sir And Rajesh Sir and tested by both

        final List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(AccountPrefix.ABT.toString(),
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : billTypeLookupList) {
           // if (lookUp.getDefaultVal() != null && !lookUp.getDefaultVal().isEmpty()) {
              //  if (lookUp.getDefaultVal().equals("Y")) {
        	// On 31/05/2022 New Changes behalf of Samadhan Sir and Ajay
                if (lookUp.getLookUpCode() != null && !lookUp.getLookUpCode().isEmpty()) {
                    if (lookUp.getLookUpCode().equals("W")) {
                    approvalDTO.setBillTypeId(lookUp.getLookUpId());
                }
            }
        }
        // approvalDTO.setBillTypeId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.WorksManagement.LI,
        // MainetConstants.ABT, UserSession.getCurrent().getOrganisation().getOrgid()));
        approvalDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        approvalDTO.setNarration(MainetConstants.WorksManagement.BILL_AGASINST + mbNumbers);
        approvalDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        approvalDTO.setCreatedDate(Utility.dateToString(new Date()));
        approvalDTO.setLgIpMacAddress(UserSession.getCurrent().getEmployee().getEmppiservername());

        approvalDTO.setVendorId(tenderWorkDto.getVenderId());
        approvalDTO.setInvoiceAmount(getRaBillDto().getSanctionAmount());
        WorkDefinationYearDetDto dto = definitionDto.getYearDtos().get(0);
        billExpDetailDTO.setBudgetCodeId(dto.getSacHeadId());

        billExpDetailDTO.setAmount(getRaBillDto().getSanctionAmount());

        billExpDetailDTO.setSanctionedAmount(getRaBillDto().getSanctionAmount());
        expDetListDto.add(billExpDetailDTO);
        approvalDTO.setExpDetListDto(expDetListDto);

        Organisation org = new Organisation();
        org.setOrgid(getParentOrgId());
        final Long currDemandId = CommonMasterUtility.getValueFromPrefixLookUp("CYR", "DMC", org).getLookUpId();

        VendorBillDedDetailDTO dedDetailDTO = null;
        if(!raBillDto.getRaBillTaxDetails().isEmpty()) {
        for (WmsRaBillTaxDetailsDto taxDeduction : raBillDto.getRaBillTaxDetails()) {
        	if(taxDeduction.getRaTaxValue() != null) {
            dedDetailDTO = new VendorBillDedDetailDTO();          
            Long sacHeadId = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
                    .fetchSacHeadIdForReceiptDetByDemandClass(getParentOrgId(),
                            taxDeduction.getTaxId(), MainetConstants.STATUS.ACTIVE, currDemandId);
            dedDetailDTO.setDeductionAmount(taxDeduction.getRaTaxValue());
            dedDetailDTO.setBudgetCodeId(sacHeadId);
            dedDetailDTO.setBchId(dto.getSacHeadId());
            deductionDetList.add(dedDetailDTO);
            }     
        else {
        	 approvalDTO.setDedDetListDto(null);
        }
       }
      }
        
        //Defect #83156
     /*   if (deductionDetList.isEmpty()) {
            deductionDetList.add(billDedDetailDTO);
        }*/
    
       
       approvalDTO.setDedDetListDto(deductionDetList);
       

        long fieldId = 0;
				
        if (UserSession.getCurrent().getLoggedLocId() != null) {
            final TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
            if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
                fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
            }
        }
        if (fieldId == 0) {
            throw new NullPointerException("fieldId is not linked with Location Master for[locId="
                    + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
                    + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
        }
        if (UserSession.getCurrent().getLoggedLocId() != null) {
        approvalDTO.setFieldId(UserSession.getCurrent().getLoggedLocId());
        }

        Department entities = departmentService.getDepartment(MainetConstants.WorksManagement.WORKS_MANAGEMENT,
                MainetConstants.CommonConstants.ACTIVE);
        approvalDTO.setDepartmentId(entities.getDpDeptid());
        if(getRaBillDto().getRaCode()!=null) {
        approvalDTO.setInvoiceNumber(getRaBillDto().getRaCode());}
        if(workOrder.getOrderDate()!=null) {
        approvalDTO.setOrderDate(workOrder.getOrderDate().toString());}
        if(workOrder.getWorkOrderNo()!=null) {
        approvalDTO.setOrderNumber(workOrder.getWorkOrderNo());}
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (defaultVal != null && defaultVal.getLookUpCode().equals(MainetConstants.FlagL)) {
            try {
                responseEntity = RestClient.callRestTemplateClient(approvalDTO, ServiceEndpoints.SALARY_POSTING);
                if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    for (Long raDto : getRaBillDto().getMbId()) {
                    	String[] split = responseEntity.getBody().toString().split(":");
                        bookService.updateBillNumberByMbId(split[1], raDto);
                        setWorkBillNumber(responseEntity.getBody().toString());
                        // #121860
                        raBillService.updateBillDetails(raBillDto.getRaId(), split[1]);
                    }
                }
            } catch (Exception exception) {
                throw new FrameworkException("error occured while bill posting to account module ", exception);
            }
        }
    }

    /**
     * This methos is used for get tender Amount
     * @return
     */
    public BigDecimal getTenderAmount() {

        BigDecimal grandTotal = null;
        List<LookUp> list = getLevelData(MainetConstants.WorksManagement.VTY);
        for (LookUp payType : list) {
            if (payType.getLookUpCode().equals(MainetConstants.WorksManagement.PER)
                    && (payType.getLookUpId() == tenderWorkDto.getTenderType())) {
                if (getTotalRaAmount() != null && getTenderWorkDto().getTenderValue() != null) {
                	if (getTenderWorkDto().getTendTypePercent() != null && getRaBillDto().getRaMbAmount() != null) {
            			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(getTenderWorkDto().getTendTypePercent(),
            					UserSession.getCurrent().getOrganisation());
            			if(lookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
            				grandTotal = getTotalRaAmount().add(getTotalRaAmount().multiply(getTenderWorkDto().getTenderValue())
                                    .divide(new BigDecimal(MainetConstants.WorksManagement.Hundred_Per))
                                    .setScale(2, BigDecimal.ROUND_FLOOR));
            				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL))
            					setTenderPercentValueFlag(MainetConstants.FlagA);
                            break;
            			}else {
            				grandTotal = getTotalRaAmount().subtract(getTotalRaAmount().multiply(getTenderWorkDto().getTenderValue())
                                    .divide(new BigDecimal(MainetConstants.WorksManagement.Hundred_Per))
                                    .setScale(2, BigDecimal.ROUND_FLOOR));
            				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL))
            					setTenderPercentValueFlag(MainetConstants.FlagB);
                            break;
            			}
                	}
                    
                }
            } else if (payType.getLookUpCode().equals(MainetConstants.WorksManagement.AMT)
                    && (payType.getLookUpId() == tenderWorkDto.getTenderType())) {
                grandTotal = getTotalRaAmount();
                break;
            }
        }
        return grandTotal;
    }

    /**
     * save Inspection Details
     * 
     */
    public boolean saveInspectionDetails() {
        boolean status = true;
        VigilanceDto vDto = getVigilanceDto();
        setFlag(vDto.getInspRequire());
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);

        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        List<DocumentDetailsVO> documentDescription = getInspectionAttachment();
        setInspectionAttachment(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

        /**
         * used for set document description in commonFileAttachment
         */
        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            for (int value = 0; value <= entry.getValue().size() - 1; value++) {
                getInspectionAttachment().get(i)
                        .setDoc_DESC_ENGL(documentDescription.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
                i++;
            }
        }

        setMemoType(vDto.getMemoType());
        if (vDto.getCreatedBy() == null) {
            vDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            vDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            vDto.setCreatedDate(new Date());
            vDto.setMemoDate(new Date());
            vDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            vDto.setReferenceNumber(getWorkRaCode());
            vDto.setStatus(MainetConstants.FlagY);
            vigilanceService.saveVigilance(vDto, requestDTO, getInspectionAttachment());
            setSuccessMessage(ApplicationSession.getInstance().getMessage("work.rabill.inspection.save"));
        } else {
            if (vDto.getInspRequire().equals("N")) {
                setMemoType(null);
            }
            List<Long> removeFileId = removeFileByIdAsList();
            vDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            vDto.setUpdatedDate(new Date());
            vDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            vigilanceService.updateVigilance(vDto, getInspectionAttachment(), requestDTO, removeFileId);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("work.rabill.inspection.update"));
        }

        return status;

    }

    private List<Long> removeFileByIdAsList() {
        List<Long> removeFileIdList = null;
        String fileIdList = getRemoveFileByIds();
        if (fileIdList != null && !fileIdList.isEmpty()) {
            removeFileIdList = new ArrayList<>();
            String fileArray[] = fileIdList.split(MainetConstants.operator.COMMA);
            for (String fileId : fileArray) {
                removeFileIdList.add(Long.valueOf(fileId));
            }
        }
        return removeFileIdList;
    }

    public List<MeasurementBookMasterDto> getMeasureMentBookMastDtosList() {
        return measureMentBookMastDtosList;
    }

    public void setMeasureMentBookMastDtosList(List<MeasurementBookMasterDto> measureMentBookMastDtosList) {
        this.measureMentBookMastDtosList = measureMentBookMastDtosList;
    }

    public boolean isMbfinalApproval() {
        return mbfinalApproval;
    }

    public void setMbfinalApproval(boolean mbfinalApproval) {
        this.mbfinalApproval = mbfinalApproval;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public Long getActualTaskId() {
        return actualTaskId;
    }

    public void setActualTaskId(Long actualTaskId) {
        this.actualTaskId = actualTaskId;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public Long getWorkMbId() {
        return workMbId;
    }

    public void setWorkMbId(Long workMbId) {
        this.workMbId = workMbId;
    }

    public String getEstimateMode() {
        return estimateMode;
    }

    public void setEstimateMode(String estimateMode) {
        this.estimateMode = estimateMode;
    }

    public MeasurementBookMasterDto getMasterDto() {
        return masterDto;
    }

    public void setMasterDto(MeasurementBookMasterDto masterDto) {
        this.masterDto = masterDto;
    }

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public List<Object[]> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Object[]> employeeList) {
        this.employeeList = employeeList;
    }

    public VigilanceDto getVigilanceDto() {
        return vigilanceDto;
    }

    public void setVigilanceDto(VigilanceDto vigilanceDto) {
        this.vigilanceDto = vigilanceDto;
    }

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public boolean isFinalApproval() {
        return finalApproval;
    }

    public void setFinalApproval(boolean finalApproval) {
        this.finalApproval = finalApproval;
    }

    public WorkOrderDto getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrderDto workOrder) {
        this.workOrder = workOrder;
    }

    public TenderWorkDto getTenderWorkDto() {
        return tenderWorkDto;
    }

    public void setTenderWorkDto(TenderWorkDto tenderWorkDto) {
        this.tenderWorkDto = tenderWorkDto;
    }

    public WorkDefinitionDto getDefinitionDto() {
        return definitionDto;
    }

    public void setDefinitionDto(WorkDefinitionDto definitionDto) {
        this.definitionDto = definitionDto;
    }

    public String getRemoveFileByIds() {
        return removeFileByIds;
    }

    public void setRemoveFileByIds(String removeFileByIds) {
        this.removeFileByIds = removeFileByIds;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public String getWorkMbCode() {
        return workMbCode;
    }

    public void setWorkMbCode(String workMbCode) {
        this.workMbCode = workMbCode;
    }

    public String getWorkBillNumber() {
        return workBillNumber;
    }

    public void setWorkBillNumber(String workBillNumber) {
        this.workBillNumber = workBillNumber;
    }

    public boolean isRaBill() {
        return raBill;
    }

    public void setRaBill(boolean raBill) {
        this.raBill = raBill;
    }

    public String getMbTaskName() {
        return mbTaskName;
    }

    public void setMbTaskName(String mbTaskName) {
        this.mbTaskName = mbTaskName;
    }

    public String getFlagForSendBack() {
        return flagForSendBack;
    }

    public void setFlagForSendBack(String flagForSendBack) {
        this.flagForSendBack = flagForSendBack;
    }

    public WmsProjectMasterDto getProjectMasterDto() {
        return projectMasterDto;
    }

    public void setProjectMasterDto(WmsProjectMasterDto projectMasterDto) {
        this.projectMasterDto = projectMasterDto;
    }

    public String getRaBillDateStr() {
        return raBillDateStr;
    }

    public void setRaBillDateStr(String raBillDateStr) {
        this.raBillDateStr = raBillDateStr;
    }

    public TbDepartment getDepartment() {
        return department;
    }

    public void setDepartment(TbDepartment department) {
        this.department = department;
    }

    public String getWorkRaCode() {
        return workRaCode;
    }

    public void setWorkRaCode(String workRaCode) {
        this.workRaCode = workRaCode;
    }

    public String getContractFromDate() {
        return contractFromDate;
    }

    public void setContractFromDate(String contractFromDate) {
        this.contractFromDate = contractFromDate;
    }

    public String getContractToDate() {
        return contractToDate;
    }

    public void setContractToDate(String contractToDate) {
        this.contractToDate = contractToDate;
    }

    public WorksRABillDto getRaBillDto() {
        return raBillDto;
    }

    public void setRaBillDto(WorksRABillDto raBillDto) {
        this.raBillDto = raBillDto;
    }

    public List<MeasurementBookMasterDto> getMeasureMentBookList() {
        return measureMentBookList;
    }

    public void setMeasureMentBookList(List<MeasurementBookMasterDto> measureMentBookList) {
        this.measureMentBookList = measureMentBookList;
    }

    public BigDecimal getTotalRaAmount() {
        return totalRaAmount;
    }

    public void setTotalRaAmount(BigDecimal totalRaAmount) {
        this.totalRaAmount = totalRaAmount;
    }

    public BigDecimal getTotalGrandRaAmount() {
        return totalGrandRaAmount;
    }

    public void setTotalGrandRaAmount(BigDecimal totalGrandRaAmount) {
        this.totalGrandRaAmount = totalGrandRaAmount;
    }

    public WorksRABillDto getPreviousRaBillDto() {
        return previousRaBillDto;
    }

    public void setPreviousRaBillDto(WorksRABillDto previousRaBillDto) {
        this.previousRaBillDto = previousRaBillDto;
    }

    public WorkDefinitionDto getMbDefinitionDtoEmployee() {
        return mbDefinitionDtoEmployee;
    }

    public void setMbDefinitionDtoEmployee(WorkDefinitionDto mbDefinitionDtoEmployee) {
        this.mbDefinitionDtoEmployee = mbDefinitionDtoEmployee;
    }

    public List<WorkflowTaskActionWithDocs> getWorkflowTaskAction() {
        return workflowTaskAction;
    }

    public void setWorkflowTaskAction(List<WorkflowTaskActionWithDocs> workflowTaskAction) {
        this.workflowTaskAction = workflowTaskAction;
    }

    public List<VigilanceDto> getVigilanceDtoList() {
        return vigilanceDtoList;
    }

    public void setVigilanceDtoList(List<VigilanceDto> vigilanceDtoList) {
        this.vigilanceDtoList = vigilanceDtoList;
    }

    public Long getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Long workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMemoType() {
        return memoType;
    }

    public void setMemoType(String memoType) {
        this.memoType = memoType;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<Long> getFileCountInspectionUpload() {
        return fileCountInspectionUpload;
    }

    public void setFileCountInspectionUpload(List<Long> fileCountInspectionUpload) {
        this.fileCountInspectionUpload = fileCountInspectionUpload;
    }

    public List<DocumentDetailsVO> getInspectionAttachment() {
        return inspectionAttachment;
    }

    public void setInspectionAttachment(List<DocumentDetailsVO> inspectionAttachment) {
        this.inspectionAttachment = inspectionAttachment;
    }

	public String getWorkFlag() {
		return workFlag;
	}

	public void setWorkFlag(String workFlag) {
		this.workFlag = workFlag;
	}

	public String getCompletedFlag() {
		return completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}

	public String getTenderPercentValueFlag() {
		return tenderPercentValueFlag;
	}

	public void setTenderPercentValueFlag(String tenderPercentValueFlag) {
		this.tenderPercentValueFlag = tenderPercentValueFlag;
	}
	

}
