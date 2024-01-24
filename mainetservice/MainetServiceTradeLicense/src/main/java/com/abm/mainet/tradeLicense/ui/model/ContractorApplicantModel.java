package com.abm.mainet.tradeLicense.ui.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

import com.abm.mainet.tradeLicense.service.IContractorApplicantService;

@Component
@Scope("session")
public class ContractorApplicantModel extends AbstractFormModel{

	/**
	 * 
	 */
	
	 @Autowired
	 private IContractorApplicantService contractorApplicantService;
	 
	 
	private static final long serialVersionUID = 6308029484716969085L;
	
	private TbCfcApplicationMst tbCfcApplicationMst;
	
	private RequestDTO requestDto;
	
	private ApplicantDetailDTO applicantDetailDTO;
	
	private TbAcVendormaster tbAcVendormaster;
	
	private String saveMode;
	
	private Long taskId;
	
	private boolean contractorLicense;
	
	private Map<Long, String> bankList;
	
	private List<LookUp> vendorStatus1;
	
	private List<LookUp> vendorType1;
	
	private List<LookUp> vendorClass1;

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	
	public void saveContractorApplicantApproval(Long requestId, Long orgid, String taskName) {

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setApplicationId(requestId);
		//long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgid);
		
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("VLP", orgid);
		
		requestDTO.setServiceId(service.getSmServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());

		//prepareWorkFlowTaskActions(getWorkflowActionDto());
		contractorApplicantService.updateWorkFlowSecurityService(prepareWorkFlowTaskActions(getWorkflowActionDto()));
		/*
		 * staffService.updateWorkFlowSecurityService(getWorkflowActionDto());
		 * 
		 * boolean lastLevel =
		 * ApplicationContextProvider.getApplicationContext().getBean(
		 * IWorkFlowTypeService.class)
		 * .isLastTaskInCheckerTaskListGroup(getWorkflowActionDto().getTaskId());
		 * 
		 * WorkflowRequest workflowRequest =
		 * ApplicationContextProvider.getApplicationContext()
		 * .getBean(IWorkflowRequestService.class)
		 * .getWorkflowRequestByAppIdOrRefId(null, deploymentOfStaffDTO.getDeplSeq(),
		 * orgid);
		 * 
		 * int size = workflowRequest.getWorkFlowTaskList().size();
		 * 
		 * if (workflowRequest != null &&
		 * workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.
		 * REJECTED)) {
		 * 
		 * staffService.updateDeploymentStaffApproveStatus(deploymentOfStaffDTO,
		 * null,workflowRequest.getLastDecision());
		 * staffService.updateWfStatus(deploymentOfStaffDTO.getDeplSeq(),workflowRequest
		 * .getLastDecision()); }
		 * 
		 * if (workflowRequest != null &&
		 * workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.
		 * APPROVED) &&
		 * workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)
		 * ) {
		 * 
		 * staffService.updateDeploymentStaffApproveStatus(deploymentOfStaffDTO,
		 * null,workflowRequest.getLastDecision());
		 * ////staffService.updateWfStatus(deploymentOfStaffDTO.getDeplSeq(),
		 * workflowRequest.getStatus()); // Current Task Name String taskName1 =
		 * workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName(); //
		 * Previous Task Name String taskNamePrevious =
		 * workflowRequest.getWorkFlowTaskList().get(size - 2).getTaskName(); if
		 * (!taskName1.equals(taskNamePrevious)) {
		 * 
		 * staffService.updateWfStatusDepl(deploymentOfStaffDTO.getDeplId(),
		 * MainetConstants.WorkFlow.Decision.PENDING, orgId);
		 * deploymentOfStaffDTO.setWfStatus(taskNamePrevious); } }
		 * 
		 * if (workflowRequest != null &&
		 * workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED) &&
		 * workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.
		 * APPROVED)) {
		 * staffService.updateDeploymentStaffApproveStatus(deploymentOfStaffDTO,
		 * null,workflowRequest.getLastDecision());
		 * staffService.updateWfStatusDepl(deploymentOfStaffDTO.getDeplId(),
		 * MainetConstants.WorkFlow.Decision.APPROVED, orgId);
		 * staffService.saveOrUpdateAfterWfApproval(requestId,UserSession.getCurrent().
		 * getOrganisation().getOrgid());
		 * deploymentOfStaffDTO.setWfStatus(workflowRequest.getWorkFlowTaskList().get(
		 * size - 1).getTaskName()); }
		 */
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
		workflowActionDto.setIsObjectionAppealApplicable(false);
		workflowActionDto.setComments(requestDto.getRemark());
		workflowActionDto.setDecision(requestDto.getStatus());

		return workflowActionDto;

	}
	
	@Override
	public boolean saveForm() {
		return true;
	}

	public boolean isContractorLicense() {
		return contractorLicense;
	}

	public void setContractorLicense(boolean contractorLicense) {
		this.contractorLicense = contractorLicense;
	}

	public ApplicantDetailDTO getApplicantDetailDTO() {
		return applicantDetailDTO;
	}

	public void setApplicantDetailDTO(ApplicantDetailDTO applicantDetailDTO) {
		this.applicantDetailDTO = applicantDetailDTO;
	}

	public TbAcVendormaster getTbAcVendormaster() {
		return tbAcVendormaster;
	}

	public void setTbAcVendormaster(TbAcVendormaster tbAcVendormaster) {
		this.tbAcVendormaster = tbAcVendormaster;
	}

	public TbCfcApplicationMst getTbCfcApplicationMst() {
		return tbCfcApplicationMst;
	}

	public void setTbCfcApplicationMst(TbCfcApplicationMst tbCfcApplicationMst) {
		this.tbCfcApplicationMst = tbCfcApplicationMst;
	}

	public RequestDTO getRequestDto() {
		return requestDto;
	}

	public void setRequestDto(RequestDTO requestDto) {
		this.requestDto = requestDto;
	}

	public Map<Long, String> getBankList() {
		return bankList;
	}

	public void setBankList(Map<Long, String> custBankList) {
		this.bankList = custBankList;
	}

	public List<LookUp> getVendorStatus1() {
		return vendorStatus1;
	}

	public void setVendorStatus1(List<LookUp> vendorStatus) {
		this.vendorStatus1 = vendorStatus;
	}

	public List<LookUp> getVendorType1() {
		return vendorType1;
	}

	public void setVendorType1(List<LookUp> vendorType) {
		this.vendorType1 = vendorType;
	}

	public List<LookUp> getVendorClass1() {
		return vendorClass1;
	}

	public void setVendorClass1(List<LookUp> vendorClass) {
		this.vendorClass1 = vendorClass;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
}
