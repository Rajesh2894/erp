package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.workManagement.dto.MeasurementBookDetailsDto;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.VigilanceDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WmsRaBillTaxDetailsDto;
import com.abm.mainet.workManagement.dto.WorkDefinationWardZoneDetailsDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.dto.WorksRABillDto;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.VigilanceService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.service.WorksRABillService;
import com.abm.mainet.workManagement.ui.model.WorkEstimateApprovalModel;
import com.abm.mainet.workManagement.ui.model.WorkMBApprovalModel;

/**
 * Object: this controller is used for MB Approval.
 * 
 * @author vishwajeet.kumar
 * @since 30 May 2018
 */
@Controller
@RequestMapping("/WorkMBApproval.html")
public class WorkMBApprovalController extends AbstractFormController<WorkMBApprovalModel> {

    @Autowired
    private MeasurementBookService mbService;
    
    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private WorkDefinitionService workDefinitionService;

    @Autowired
    private WorksRABillService raBillService;

    @Autowired
    private IFileUploadService fileUploadService;

    /**
     * This Method is used for show Inspection page
     * @param raCode
     * @param actualTaskId
     * @param serviceId
     * @param workflowId
     * @param taskName
     * @param httpServletRequest
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
    public ModelAndView workorder(@RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String raCode,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
            final HttpServletRequest httpServletRequest, final Model model) {
        sessionCleanup(httpServletRequest);

        fileUploadService.sessionCleanUpForFileUpload();
        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long parentOrgid = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
                .findById(workflowId).getCurrentOrgId();
        WorkMBApprovalModel approvalModel = this.getModel();
        ServiceMaster master = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMaster(serviceId, parentOrgid);
        approvalModel.setParentOrgId(parentOrgid);
        approvalModel.setSaveMode(MainetConstants.WorksManagement.APPROVAL);
        approvalModel.setServiceId(serviceId);
        WorksRABillDto billDto = raBillService.getRABillDetailsByRaCode(raCode, parentOrgid);
        approvalModel.setRaBillDto(raBillService.getRaBillByRaId(billDto.getRaId()));
        approvalModel.setDefinitionDto(workDefinitionService.findAllWorkDefinitionById(billDto.getWorkId()));
        approvalModel
                .setMeasureMentBookMastDtosList(mbService.getAllMbDeatilsByListOfMBId(billDto.getMbId(), parentOrgid));
        approvalModel.setContractFromDate(this.getModel().getMeasureMentBookMastDtosList().get(0).getAgreeFromDate());
        approvalModel.setContractToDate(this.getModel().getMeasureMentBookMastDtosList().get(0).getAgreeToDate());

        approvalModel.setDepartment(
                ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                        .findById(master.getTbDepartment().getDpDeptid()));
        approvalModel
                .setEmployeeList(ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
                        .getAllEmployeeNames(currentOrgId));
        approvalModel.getWorkflowActionDto().setReferenceId(raCode);
        approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
        approvalModel.setActualTaskId(actualTaskId);
        approvalModel.setWorkRaCode(raCode);
        approvalModel.setMbTaskName(taskName);
        approvalModel.setWorkFlowId(workflowId);
        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(null, raCode, parentOrgid);

        if (approvalModel.getMbTaskName().equals(MainetConstants.WorksManagement.INITIATOR)
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
        		approvalModel.setEstimateMode(MainetConstants.WorksManagement.VIEW);
                approvalModel.setFlagForSendBack(workflowRequest.getLastDecision());
        	}
        	else{
        		approvalModel.setEstimateMode(MainetConstants.WorksManagement.EDIT);
                approvalModel.setFlagForSendBack(workflowRequest.getLastDecision());
        	}
            
        } else {
            approvalModel.setEstimateMode(MainetConstants.FlagV);
        }
        List<VigilanceDto> dtos = ApplicationContextProvider.getApplicationContext().getBean(VigilanceService.class)
                .getVigilanceByReferenceNo(raCode,
                        parentOrgid);
        List<VigilanceDto> list = new ArrayList<VigilanceDto>();
        if (dtos != null) {
            dtos.forEach(vigLance -> {
                if (vigLance.getCreatedBy().equals(UserSession.getCurrent().getEmployee().getEmpId())
                        && !workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
                    if (vigLance.getMemoType().isEmpty()) {
                        vigLance.setInspRequire(MainetConstants.FlagN);
                        approvalModel.setFlag(MainetConstants.FlagN);
                    } else {
                        vigLance.setInspRequire(MainetConstants.FlagY);
                        approvalModel.setFlag(MainetConstants.FlagY);
                    }
                    approvalModel.setMemoType(vigLance.getMemoType());
                    this.getModel().setVigilanceDto(vigLance);
                } else {
                    list.add(vigLance);
                }
            });
            this.getModel().setVigilanceDtoList(list);
        }
        final List<AttachDocs> attachDocsList = ApplicationContextProvider.getApplicationContext()
                .getBean(IAttachDocsService.class).findByCode(parentOrgid,
                        raCode);
        this.getModel().setAttachDocsList(attachDocsList);
        return new ModelAndView(MainetConstants.WorksManagement.WORK_MB_APPROVAL, MainetConstants.FORM_NAME,
                this.getModel());

    }

    /**
     * Method is used for get Work MB Abstract Sheet
     * @param workMbId
     * @param request
     * @return modelAndView
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_WORKMB_ABSTRACT_SHEET, method = RequestMethod.POST)
    public ModelAndView viewWorkMBAbstractSheetForm(
            @RequestParam(MainetConstants.WorksManagement.WORK_MB_ID) final Long workMbId,
            final HttpServletRequest request) {
        getModel().bind(request);
        request.getSession().setAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID,
                this.getModel().getParentOrgId());
        this.getModel().setWorkMbId(workMbId);
        this.getModel().setMeasureMentBookList(mbService.findAbstractSheetReport(workMbId));
        return new ModelAndView(MainetConstants.WorksManagement.WORK_MB_ABSTRACT_SHEETFORM, MainetConstants.FORM_NAME,
                this.getModel());

    }

    /**
     * Methos iS used for Edit And View MeasureMent Book
     * @param workMbId
     * @param mode
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(params = MainetConstants.WorksManagement.EditMb, method = RequestMethod.POST)
    public ModelAndView getWorkEstimate(@RequestParam(MainetConstants.WorksManagement.WORK_MB_ID) Long workMbId,
            @RequestParam(MainetConstants.WorksManagement.MODE) String mode, final HttpServletRequest request) {
        getModel().bind(request);
        request.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE,
                MainetConstants.WorksManagement.APPROVAL);
        request.getSession().setAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID,
                this.getModel().getParentOrgId());
        if (mode.equals(MainetConstants.WorksManagement.EDIT)) {
            MeasurementBookMasterDto bookMaster = mbService.getMBById(workMbId);
            workMbId = bookMaster.getWorkOrId();
        }
        return new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_MB + workMbId
                + MainetConstants.WorksManagement.AND_MODE + mode);
    }

    /**
     * show Approval Current Form
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = MainetConstants.WorksManagement.SHOW_APPROVAL_CURRENT_FORM, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView showCurrentpage(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        WorkMBApprovalModel approvalModel = this.getModel();
        return new ModelAndView(MainetConstants.WorksManagement.WORK_MB_APPROVAL, MainetConstants.FORM_NAME,
                approvalModel);
    }

    /**
     * show Current Form
     * @param httpServletRequest
     * @return ModelAndView
     */
    @RequestMapping(params = MainetConstants.WorksManagement.SHOW_CURRENT_FORM, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView showCurrentForm(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        return new ModelAndView(MainetConstants.WorksManagement.WORK_MB_APPROVAL, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * Method is Used For payment
     * @param request
     * @return Model And View
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.PAYMENT_ORDER, method = RequestMethod.POST)
    public ModelAndView paymentOrder(final HttpServletRequest request) {
        getModel().bind(request);
        WorkMBApprovalModel model = this.getModel();
        BigDecimal totalAmount = new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO);
        if (model.isRaBill()) {
            for (MeasurementBookMasterDto mbDto : model.getMeasureMentBookMastDtosList()) {
                List<MeasurementBookMasterDto> bookMasterDtos = mbService.findAbstractSheetReport(mbDto.getWorkMbId());
                for (MeasurementBookMasterDto measurementBookMasterDto : bookMasterDtos) {
                    totalAmount = totalAmount.add(measurementBookMasterDto.getWorkActualAmt());
                }
                model.getMeasureMentBookList().addAll(bookMasterDtos);
            }
            this.getModel().setTotalRaAmount(totalAmount);
            TenderWorkDto tenderDto = model.getTenderWorkDto();
            if (tenderDto.getTenderValue() != null) {
                model.getTenderWorkDto()
                        .setTenderAmount((totalAmount.multiply(tenderDto.getTenderValue())
                                .divide(new BigDecimal(MainetConstants.WorksManagement.Hundred_Per))).setScale(2,
                                        BigDecimal.ROUND_FLOOR));
            }
            //Defect #84646
            WorksRABillDto billDtoCalculate = model.getRaBillDto();
            WorkDefinitionDto defDto = workDefinitionService.findAllWorkDefinitionById(this.getModel().getTenderWorkDto().getWorkId());
            LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(defDto.getWorkType());
            this.getModel().getRaBillDto().getRaBillTaxDto().setRaTaxValue(billDtoCalculate.getRaBillTaxDtoWith().get(0).getRaTaxValue());
            if(lookUp.getLookUpDesc().equalsIgnoreCase("Legacy Works")) {
            	this.getModel().setWorkFlag(MainetConstants.FlagY);        	
                this.getModel().getRaBillDto().setRaTaxAmt(billDtoCalculate.getRaBillTaxDto().getRaTaxValue());
                for (MeasurementBookMasterDto mbDto : model.getMeasureMentBookMastDtosList()) {          	
                	this.getModel().setTotalRaAmount(mbDto.getMbTotalAmt());           	
                }               
            }
            return new ModelAndView(MainetConstants.WorksManagement.PAYMENT_ORDER, MainetConstants.FORM_NAME,
                    this.getModel());
        } else {
            return new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_ADMIN);
        }

    }

    /**
     * Used For Print RA Bill
     * @param workId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.PRINT_RA_BILL, method = RequestMethod.POST)
    public ModelAndView printRABill(@RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final Long workId,
            final HttpServletRequest request) {
        sessionCleanup(request);
        WorkMBApprovalModel model = this.getModel();
        BigDecimal totalAmount = new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO);
        model.setSaveMode(MainetConstants.WorksManagement.RATE_TYPE);
        WorksRABillDto billDto = raBillService.getRaBillByRaId(workId);
        BigDecimal raAmount = new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO);
   	 for (WmsRaBillTaxDetailsDto dto : billDto.getRaBillTaxDtoWith()) {
   		 raAmount = raAmount.add(dto.getRaTaxValue());
        } 
   	   billDto.getRaBillTaxDto().setRaTaxValue(raAmount);
        model.setRaBillDto(billDto);
        model.setWorkBillNumber(billDto.getRaBillno());

        model.setMeasureMentBookMastDtosList(mbService.getAllMbDeatilsByListOfMBId(billDto.getMbId(),
                UserSession.getCurrent().getOrganisation().getOrgid()));
        for (MeasurementBookMasterDto mbDto : model.getMeasureMentBookMastDtosList()) {
            List<MeasurementBookMasterDto> bookMasterDtos = mbService.findAbstractSheetReport(mbDto.getWorkMbId());
            for (MeasurementBookMasterDto measurementBookMasterDto : bookMasterDtos) {
                totalAmount = totalAmount.add(measurementBookMasterDto.getWorkActualAmt());
            }
            model.getMeasureMentBookList().addAll(bookMasterDtos);
                    
        }
        this.getModel().setTotalRaAmount(totalAmount);
        model.setMasterDto(model.getMeasureMentBookMastDtosList().get(0));
        model.setContractFromDate(model.getMasterDto().getAgreeFromDate());
        model.setContractToDate(model.getMasterDto().getAgreeToDate());
        model.setMbDefinitionDtoEmployee((workDefinitionService.findAllWorkDefinitionById(billDto.getWorkId())));

        WorkOrderDto workOrder = ApplicationContextProvider.getApplicationContext().getBean(WorkOrderService.class)
                .getWorkOredrByOrderId(this.getModel().getMasterDto().getWorkOrId());
        model.setWorkOrder(workOrder);
        model.setTenderWorkDto(ApplicationContextProvider.getApplicationContext().getBean(TenderInitiationService.class)
                .findWorkByWorkId(workOrder.getContractMastDTO().getContId()));

        TenderWorkDto tenderDto = model.getTenderWorkDto();
        if (tenderDto.getTenderValue() != null) {
            model.getTenderWorkDto()
                    .setTenderAmount((totalAmount.multiply(tenderDto.getTenderValue())
                            .divide(new BigDecimal(MainetConstants.WorksManagement.Hundred_Per))).setScale(2,
                                    BigDecimal.ROUND_FLOOR));
        }
        WorkDefinitionDto workDefination = workDefinitionService
                .findAllWorkDefinitionById(this.getModel().getTenderWorkDto().getWorkId());
        if (workDefination != null) {
            model.setDefinitionDto(workDefination);
            WmsProjectMasterDto projectMasetr = ApplicationContextProvider.getApplicationContext()
                    .getBean(WmsProjectMasterService.class)
                    .getProjectMasterByProjId(workDefination.getProjId());
            if (projectMasetr != null && projectMasetr.getRsoDate() != null) {
                projectMasetr.setRsoDateDesc(Utility.dateToString(projectMasetr.getRsoDate()));
                this.getModel().setProjectMasterDto(projectMasetr);
            }
        }

        WorksRABillDto billDtoCalculate = model.getRaBillDto();
        if (model.getTenderAmount() != null && billDtoCalculate.getRaBillTaxDto().getRaTaxValue() != null) {
            billDtoCalculate.setSanctionAmount(
                    model.getTenderAmount().subtract(billDtoCalculate.getRaBillTaxDto().getRaTaxValue()));
        }
        if (model.getTenderAmount() != null && billDtoCalculate.getRaBillTaxDto().getRaTaxValue() != null
                && billDtoCalculate.getTotalTaxAmount() != null) {
            /*
             * billDtoCalculate.setNetValue((model.getTenderAmount().add(billDtoCalculate.getRaBillTaxDto().getRaTaxValue()))
             * .subtract(billDtoCalculate.getRaTaxAmt()));
             */
            billDtoCalculate.setNetValue(billDtoCalculate.getRaBillAmt());
            model.getRaBillDto().setRaBillAmtStr(Utility.convertBigNumberToWord(billDtoCalculate.getNetValue()));
        }

        WorksRABillDto previousRaBillDto = raBillService.getPreviousRaBillDetails(billDto.getWorkId(),
                UserSession.getCurrent().getOrganisation().getOrgid(), billDto.getRaId());
        if (previousRaBillDto != null && previousRaBillDto.getRaTaxAmt() == null) {
            previousRaBillDto.setRaTaxAmt(new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO));
            if (previousRaBillDto.getRaBillAmt() == null)
                previousRaBillDto.setRaBillAmt(new BigDecimal(MainetConstants.WorksManagement.BIG_ZERO));
        }
        this.getModel().setPreviousRaBillDto(previousRaBillDto);

        model.setRaBillDateStr(Utility.dateToString(new Date()));
        //Defect #84646
        WorkDefinitionDto defDto = workDefinitionService.findAllWorkDefinitionById(this.getModel().getTenderWorkDto().getWorkId());
        LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(defDto.getWorkType());
        if(lookUp.getLookUpDesc().equalsIgnoreCase("Legacy Works")) {
        	this.getModel().setWorkFlag(MainetConstants.FlagY);        	
            this.getModel().getRaBillDto().setRaTaxAmt(billDtoCalculate.getRaBillTaxDto().getRaTaxValue());
            for (MeasurementBookMasterDto mbDto : model.getMeasureMentBookMastDtosList()) {          	
            	totalAmount = totalAmount.add(mbDto.getMbTotalAmt());           	
            }
            this.getModel().setTotalRaAmount(totalAmount); 
        }
        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(null, billDto.getRaCode(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
        List<WorkflowTaskActionWithDocs> actionHistory = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowActionService.class)
                .getActionLogByUuidAndWorkflowId(billDto.getRaCode(), workflowRequest.getId(),
                        (short) UserSession.getCurrent().getLanguageId());
        actionHistory.forEach(dto->{
        	EmployeeBean emp = employeeService.findById(dto.getEmpId());
        	dto.setEmpName(emp.getFullName());
        });
        model.setWorkflowTaskAction(actionHistory);
        
        return new ModelAndView(MainetConstants.WorksManagement.PAYMENT_ORDER, MainetConstants.FORM_NAME, model);
    }

    /**
     * Method is used for Show User Action Form
     * @param request
     * @return
     */
    @RequestMapping(params = "showUserAction", method = RequestMethod.POST)
    public ModelAndView showUserActionApproval(HttpServletRequest request) {
        bindModel(request);
        fileUploadService.sessionCleanUpForFileUpload();
        return new ModelAndView("WorkRaUserApproval", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * Method is used for Save Inspection Details
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "saveInspectionData", method = RequestMethod.POST)
    public Map<String, Object> saveInspectionDetails(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        this.getModel().saveInspectionDetails();
        object.put("succesMessage", this.getModel().getSuccessMessage());
        return object;
    }

    @RequestMapping(params = "fileCountInspectionUpload")
    public ModelAndView fileCountUpload(final HttpServletRequest request) {

        bindModel(request);
        this.getModel().getFileCountInspectionUpload().clear();
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            this.getModel().getFileCountInspectionUpload().add(entry.getKey());
        }
        int fileCount = FileUploadUtility.getCurrent().getFileMap().entrySet().size();
        this.getModel().getFileCountInspectionUpload().add(fileCount + 1L);
        List<DocumentDetailsVO> attachments = new ArrayList<>();
        for (int i = 0; i <= this.getModel().getInspectionAttachment().size(); i++)
            attachments.add(new DocumentDetailsVO());
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            attachments.get(entry.getKey().intValue()).setDoc_DESC_ENGL(
                    this.getModel().getInspectionAttachment().get(entry.getKey().intValue()).getDoc_DESC_ENGL());
        }
        if (attachments.get(this.getModel().getInspectionAttachment().size()).getDoc_DESC_ENGL() == null)
            attachments.get(this.getModel().getInspectionAttachment().size()).setDoc_DESC_ENGL(MainetConstants.BLANK);
        else {
            DocumentDetailsVO ob = new DocumentDetailsVO();
            ob.setDoc_DESC_ENGL(MainetConstants.BLANK);
            attachments.add(ob);
        }
        this.getModel().setInspectionAttachment(attachments);
        return new ModelAndView("WorkRaFileUploadData", MainetConstants.FORM_NAME, this.getModel());
    }
    
    @Override
    @RequestMapping(params = "viewRefNoDetails")
    public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
    		@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
    		final HttpServletRequest request) throws Exception {
    	
    	sessionCleanup(request);
    	fileUploadService.sessionCleanUpForFileUpload();
    	Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
    	Organisation org = UserSession.getCurrent().getOrganisation();
    	WorkflowRequest dto = ApplicationContextProvider.getApplicationContext()
    			.getBean(IWorkflowRequestService.class)
    			.getWorkflowRequestByAppIdOrRefId(null, applicationId,
    					UserSession.getCurrent().getOrganisation().getOrgid());
    	this.getModel().setCompletedFlag(MainetConstants.FlagY);
    	Long parentOrgid = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
    			.findById(dto.getWorkflowTypeId()).getCurrentOrgId();
    	WorkMBApprovalModel approvalModel = this.getModel();
    	ServiceMaster master = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
    			.getServiceMaster(serviceId, parentOrgid);
    	approvalModel.setParentOrgId(parentOrgid);
    	approvalModel.setSaveMode(MainetConstants.FlagV);
    	approvalModel.setServiceId(serviceId);
    	WorksRABillDto billDto = raBillService.getRABillDetailsByRaCode(applicationId, parentOrgid);
    	approvalModel.setRaBillDto(raBillService.getRaBillByRaId(billDto.getRaId()));
    	approvalModel.setDefinitionDto(workDefinitionService.findAllWorkDefinitionById(billDto.getWorkId()));
    	approvalModel
    	.setMeasureMentBookMastDtosList(mbService.getAllMbDeatilsByListOfMBId(billDto.getMbId(), parentOrgid));
    	approvalModel.setContractFromDate(this.getModel().getMeasureMentBookMastDtosList().get(0).getAgreeFromDate());
    	approvalModel.setContractToDate(this.getModel().getMeasureMentBookMastDtosList().get(0).getAgreeToDate());

    	approvalModel.setDepartment(
    			ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
    			.findById(master.getTbDepartment().getDpDeptid()));
    	approvalModel
    	.setEmployeeList(ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
    			.getAllEmployeeNames(currentOrgId));
    	approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
    	approvalModel.getWorkflowActionDto().setTaskId(taskId);
    	approvalModel.setActualTaskId(taskId);
    	approvalModel.setWorkRaCode(applicationId);
    	//approvalModel.setMbTaskName(taskName);
    	approvalModel.setWorkFlowId(dto.getWorkflowTypeId());
    	WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
    			.getBean(IWorkflowRequestService.class)
    			.getWorkflowRequestByAppIdOrRefId(null, applicationId, parentOrgid);

    	List<VigilanceDto> dtos = ApplicationContextProvider.getApplicationContext().getBean(VigilanceService.class)
    			.getVigilanceByReferenceNo(applicationId,
    					parentOrgid);
    	List<VigilanceDto> list = new ArrayList<VigilanceDto>();
    	if (dtos != null) {
    		dtos.forEach(vigLance -> {
    			if (vigLance.getCreatedBy().equals(UserSession.getCurrent().getEmployee().getEmpId())
    					&& !workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
    				if (vigLance.getMemoType().isEmpty()) {
    					vigLance.setInspRequire(MainetConstants.FlagN);
    					approvalModel.setFlag(MainetConstants.FlagN);
    				} else {
    					vigLance.setInspRequire(MainetConstants.FlagY);
    					approvalModel.setFlag(MainetConstants.FlagY);
    				}
    				approvalModel.setMemoType(vigLance.getMemoType());
    				this.getModel().setVigilanceDto(vigLance);
    			} else {
    				list.add(vigLance);
    			}
    		});
    		this.getModel().setVigilanceDtoList(list);
    	}
    	return new ModelAndView(MainetConstants.WorksManagement.WORK_MB_APPROVAL, MainetConstants.FORM_NAME,
    			this.getModel());

    }

}
