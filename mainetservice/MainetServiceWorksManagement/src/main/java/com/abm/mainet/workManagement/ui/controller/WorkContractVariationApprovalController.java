package com.abm.mainet.workManagement.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkDefinationWardZoneDetailsDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.ApprovalTermsConditionService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.ui.model.WorkContractVariationModel;
import com.abm.mainet.workManagement.ui.model.WorkEstimateApprovalModel;

/**
 * @author vishwajeet.kumar
 * @since 19 June 2018
 */
@Controller
@RequestMapping("/WorkContractVariationApproval.html")
public class WorkContractVariationApprovalController extends AbstractFormController<WorkContractVariationModel> {

    @ResponseBody
    @RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
    public ModelAndView workContractVariationForm(
            @RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String contractNo,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
            final HttpServletRequest httpServletRequest, final Model model) {
        sessionCleanup(httpServletRequest);

        ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();

        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long parentOrgId = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
                .findById(workflowId).getCurrentOrgId();

        WorkContractVariationModel approvalModel = this.getModel();

        approvalModel.setParentOrgId(parentOrgId);
        approvalModel.setServiceId(serviceId);
        approvalModel.setDepartmentsList(
                ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                        .findMappedDepartments(currentOrgId));
        approvalModel
                .setEmployeeList(ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
                        .getAllEmployeeNames(currentOrgId));
        ContractAgreementSummaryDTO summaryDTO = ApplicationContextProvider.getApplicationContext()
                .getBean(IContractAgreementService.class)
                .findByContractNo(parentOrgId, contractNo);
        BigDecimal totalEstimate = ApplicationContextProvider.getApplicationContext().getBean(WorkEstimateService.class)
                .calculateTotalWorkEstimate(summaryDTO.getContId(), currentOrgId);
        TenderWorkDto tenderWorkDto = ApplicationContextProvider.getApplicationContext().getBean(TenderInitiationService.class)
                .findWorkByWorkId(summaryDTO.getContId());
        approvalModel.setWorkId(tenderWorkDto.getWorkId());
        approvalModel.setVariationAmount(totalEstimate);
        approvalModel.setContractAgreementMastDTO(summaryDTO);
        approvalModel.getWorkflowActionDto().setReferenceId(contractNo);
        approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
        approvalModel.setActualTaskId(actualTaskId);
        approvalModel.setContractNo(contractNo);
        approvalModel.setContractAmount(summaryDTO.getContAmount());
        approvalModel.setDeptName(summaryDTO.getContDept());
        approvalModel.setContractId(summaryDTO.getContId());
        approvalModel.setContractTaskName(taskName);

        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(null, contractNo, parentOrgId);

        if (approvalModel.getContractTaskName().equals(MainetConstants.WorksManagement.INITIATOR)
                && workflowRequest.getLastDecision().equals(MainetConstants.WorksManagement.SEND_BACK)) {
            approvalModel.setContractMode(MainetConstants.MENU.E);
            approvalModel.setFlagForSendBack(workflowRequest.getLastDecision());
        } else {
            approvalModel.setContractMode(MainetConstants.FlagV);
        }
        approvalModel.setTermsConditionDtosList(
                ApplicationContextProvider.getApplicationContext().getBean(ApprovalTermsConditionService.class)
                        .getTermsList(contractNo, parentOrgId));
        return new ModelAndView(MainetConstants.WorksManagement.CONTRACT_VARIATION_APPROVAL, MainetConstants.FORM_NAME,
                this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.VIEW_WORK_CONTRACT_VARIATION, method = {
            RequestMethod.POST, RequestMethod.GET })
    public ModelAndView getWorkEstimate(@RequestParam(MainetConstants.WorksManagement.CONTRACT_ID) Long contId,
            @RequestParam(MainetConstants.WorksManagement.CONT_MODE) final String contractMode,
            final HttpServletRequest request) {
        getModel().bind(request);
        request.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE,
                MainetConstants.WorksManagement.WCV);
        request.getSession().setAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID,
                this.getModel().getParentOrgId());
        return new ModelAndView(MainetConstants.WorksManagement.REDIRECT_REVISEDESTIMATE + contId
                + MainetConstants.WorksManagement.AND_MODE + contractMode + MainetConstants.WorksManagement.ESTMATE_TYPE
                + MainetConstants.FlagS);
    }

    @RequestMapping(params = MainetConstants.WorksManagement.SHOW_APPROVAL_CURRENT_FORM, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView showCurrentpage(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        final WorkContractVariationModel contractVariationModel = this.getModel();
        return new ModelAndView(MainetConstants.WorksManagement.CONTRACT_VARIATION_APPROVAL, MainetConstants.FORM_NAME,
                contractVariationModel);
    }
    
    @Override
    @RequestMapping(params = "viewRefNoDetails")
    public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
    		@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
    		final HttpServletRequest request) throws Exception {
    	
    	sessionCleanup(request);
    	ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
    	Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
    	Organisation org = UserSession.getCurrent().getOrganisation();
    	WorkflowRequest dto = ApplicationContextProvider.getApplicationContext()
    			.getBean(IWorkflowRequestService.class)
    			.getWorkflowRequestByAppIdOrRefId(null, applicationId,
    					UserSession.getCurrent().getOrganisation().getOrgid());
    	this.getModel().setCompletedFlag(MainetConstants.FlagY);
    	Long parentOrgId = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
    			.findById(dto.getWorkflowTypeId()).getCurrentOrgId();
    	WorkContractVariationModel approvalModel = this.getModel();
    	approvalModel.setParentOrgId(parentOrgId);
    	approvalModel.setServiceId(serviceId);
    	approvalModel.setDepartmentsList(
    			ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
    			.findMappedDepartments(currentOrgId));
    	approvalModel
    	.setEmployeeList(ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
    			.getAllEmployeeNames(currentOrgId));
    	ContractAgreementSummaryDTO summaryDTO = ApplicationContextProvider.getApplicationContext()
    			.getBean(IContractAgreementService.class)
    			.findByContractNo(parentOrgId, applicationId);
    	BigDecimal totalEstimate = ApplicationContextProvider.getApplicationContext().getBean(WorkEstimateService.class)
    			.calculateTotalWorkEstimate(summaryDTO.getContId(), currentOrgId);
    	TenderWorkDto tenderWorkDto = ApplicationContextProvider.getApplicationContext().getBean(TenderInitiationService.class)
    			.findWorkByWorkId(summaryDTO.getContId());
    	approvalModel.setWorkId(tenderWorkDto.getWorkId());
    	approvalModel.setVariationAmount(totalEstimate);
    	approvalModel.setContractAgreementMastDTO(summaryDTO);
    	approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
    	approvalModel.getWorkflowActionDto().setTaskId(taskId);
    	approvalModel.setActualTaskId(taskId);
    	approvalModel.setContractNo(applicationId);
    	approvalModel.setContractAmount(summaryDTO.getContAmount());
    	approvalModel.setDeptName(summaryDTO.getContDept());
    	approvalModel.setContractId(summaryDTO.getContId());
    	// approvalModel.setContractTaskName(taskName);
    	
    	WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
    			.getBean(IWorkflowRequestService.class)
    			.getWorkflowRequestByAppIdOrRefId(null, applicationId, parentOrgId);

    	approvalModel.setTermsConditionDtosList(
    			ApplicationContextProvider.getApplicationContext().getBean(ApprovalTermsConditionService.class)
    			.getTermsList(applicationId, parentOrgId));
    	return new ModelAndView(MainetConstants.WorksManagement.CONTRACT_VARIATION_APPROVAL, MainetConstants.FORM_NAME,
    			this.getModel());

    }
}
