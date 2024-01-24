package com.abm.mainet.securitymanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.securitymanagement.dto.DeploymentOfStaffDTO;
import com.abm.mainet.securitymanagement.service.IDeploymentOfStaffService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DeploymentOfStaffApprovalModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	IDeploymentOfStaffService staffService;

	private String saveMode;

	DeploymentOfStaffDTO deploymentOfStaffDTO = new DeploymentOfStaffDTO();

	private List<DeploymentOfStaffDTO> employeeList = new ArrayList<>();
	
	private String approvalView;

	public void saveDeploymentCallClosureApprovalDetails(Long requestId, Long orgid, String taskName) {

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgid);
		requestDTO.setServiceId(getServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("DOS", orgid);
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());

		prepareWorkFlowTaskActions(getWorkflowActionDto());

		staffService.updateWorkFlowSecurityService(getWorkflowActionDto());

		boolean lastLevel = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
				.isLastTaskInCheckerTaskListGroup(getWorkflowActionDto().getTaskId());

		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(null, deploymentOfStaffDTO.getDeplSeq(), orgid);

		int size = workflowRequest.getWorkFlowTaskList().size();

		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			
			staffService.updateDeploymentStaffApproveStatus(deploymentOfStaffDTO, null,workflowRequest.getLastDecision());
			staffService.updateWfStatus(deploymentOfStaffDTO.getDeplSeq(),workflowRequest.getLastDecision());
		}

		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
				&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
			
			staffService.updateDeploymentStaffApproveStatus(deploymentOfStaffDTO, null,workflowRequest.getLastDecision());
			////staffService.updateWfStatus(deploymentOfStaffDTO.getDeplSeq(), workflowRequest.getStatus());
			// Current Task Name
			String taskName1 = workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName();
			// Previous Task Name
			String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size - 2).getTaskName();
			if (!taskName1.equals(taskNamePrevious)) {
				
				staffService.updateWfStatusDepl(deploymentOfStaffDTO.getDeplId(), MainetConstants.WorkFlow.Decision.PENDING, orgId);
				deploymentOfStaffDTO.setWfStatus(taskNamePrevious);
			}
		}

		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			staffService.updateDeploymentStaffApproveStatus(deploymentOfStaffDTO, null,workflowRequest.getLastDecision());
			staffService.updateWfStatusDepl(deploymentOfStaffDTO.getDeplId(), MainetConstants.WorkFlow.Decision.APPROVED, orgId);
			staffService.saveOrUpdateAfterWfApproval(requestId,UserSession.getCurrent().getOrganisation().getOrgid());
			deploymentOfStaffDTO.setWfStatus(workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName());
		}
	}

	private WorkflowTaskAction prepareWorkFlowTaskActions(WorkflowTaskAction workflowActionDto) {

		getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		workflowActionDto.setDateOfAction(new Date());
		workflowActionDto.setCreatedDate(new Date());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setApplicationId(getWorkflowActionDto().getApplicationId());
		workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		workflowActionDto.setComments(deploymentOfStaffDTO.getRemarkApproval());
		workflowActionDto.setDecision(deploymentOfStaffDTO.getStatusApproval());

		return workflowActionDto;

	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public DeploymentOfStaffDTO getDeploymentOfStaffDTO() {
		return deploymentOfStaffDTO;
	}

	public void setDeploymentOfStaffDTO(DeploymentOfStaffDTO deploymentOfStaffDTO) {
		this.deploymentOfStaffDTO = deploymentOfStaffDTO;
	}

	public List<DeploymentOfStaffDTO> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<DeploymentOfStaffDTO> employeeList) {
		this.employeeList = employeeList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getApprovalView() {
		return approvalView;
	}

	public void setApprovalView(String approvalView) {
		this.approvalView = approvalView;
	}

}
