package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.model.AbstractModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.CreditGuaranteeCGFMasterDto;
import com.abm.mainet.sfac.dto.EquityGrantMasterDto;
import com.abm.mainet.sfac.service.CreditGuaranteeRequestMasterService;

public class CreditGrantApprovalModel extends AbstractFormModel{
	
	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;
	
	@Autowired CreditGuaranteeRequestMasterService creditGuaranteeRequestMasterService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5121063868789977285L;

	List<DesignationBean> designlist = new ArrayList<>();

	CreditGuaranteeCGFMasterDto dto = new CreditGuaranteeCGFMasterDto();

	private CreditGuaranteeCGFMasterDto newCreditGuaranteeCGFMasterDto = new CreditGuaranteeCGFMasterDto();

	List<CreditGuaranteeCGFMasterDto> creditGuaranteeCGFMasterDtos = new ArrayList<>();

	List<CreditGuaranteeCGFMasterDto> changedCreditGuaranteeCGFMasterDtos = new ArrayList<>();

	List<TbOrganisation> orgList = new ArrayList<>();

	List<LookUp> stateList = new ArrayList<>();

	List<LookUp> districtList = new ArrayList<>();
	List<LookUp> blockList = new ArrayList<>();

	private List<TbDepartment> departmentList = new ArrayList<>();

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();

	private List<AttachDocs> attachDocsList = new ArrayList<>();
	List<CommonMasterDto> commonMasterDtoList = new ArrayList<>();

	List<LookUp> allocationCatgList = new ArrayList<>();
	List<LookUp> allocationSubCatgList = new ArrayList<>();
	List<LookUp> allcSubCatgTargetList = new ArrayList<>();

	List<CBBOMasterDto> cbboMasterList = new ArrayList<>();
	private String viewMode;
	public CreditGuaranteeRequestMasterService getCreditGuaranteeRequestMasterService() {
		return creditGuaranteeRequestMasterService;
	}
	public void setCreditGuaranteeRequestMasterService(
			CreditGuaranteeRequestMasterService creditGuaranteeRequestMasterService) {
		this.creditGuaranteeRequestMasterService = creditGuaranteeRequestMasterService;
	}
	public List<DesignationBean> getDesignlist() {
		return designlist;
	}
	public void setDesignlist(List<DesignationBean> designlist) {
		this.designlist = designlist;
	}
	
	public CreditGuaranteeCGFMasterDto getDto() {
		return dto;
	}
	public void setDto(CreditGuaranteeCGFMasterDto dto) {
		this.dto = dto;
	}
	public List<CreditGuaranteeCGFMasterDto> getCreditGuaranteeCGFMasterDtos() {
		return creditGuaranteeCGFMasterDtos;
	}
	public void setCreditGuaranteeCGFMasterDtos(List<CreditGuaranteeCGFMasterDto> creditGuaranteeCGFMasterDtos) {
		this.creditGuaranteeCGFMasterDtos = creditGuaranteeCGFMasterDtos;
	}
	public List<CreditGuaranteeCGFMasterDto> getChangedCreditGuaranteeCGFMasterDtos() {
		return changedCreditGuaranteeCGFMasterDtos;
	}
	public void setChangedCreditGuaranteeCGFMasterDtos(
			List<CreditGuaranteeCGFMasterDto> changedCreditGuaranteeCGFMasterDtos) {
		this.changedCreditGuaranteeCGFMasterDtos = changedCreditGuaranteeCGFMasterDtos;
	}
	public List<TbOrganisation> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<TbOrganisation> orgList) {
		this.orgList = orgList;
	}
	public List<LookUp> getStateList() {
		return stateList;
	}
	public void setStateList(List<LookUp> stateList) {
		this.stateList = stateList;
	}
	public List<LookUp> getDistrictList() {
		return districtList;
	}
	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}
	public List<LookUp> getBlockList() {
		return blockList;
	}
	public void setBlockList(List<LookUp> blockList) {
		this.blockList = blockList;
	}
	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}
	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}
	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}
	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}
	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}
	public List<CommonMasterDto> getCommonMasterDtoList() {
		return commonMasterDtoList;
	}
	public void setCommonMasterDtoList(List<CommonMasterDto> commonMasterDtoList) {
		this.commonMasterDtoList = commonMasterDtoList;
	}
	public List<LookUp> getAllocationCatgList() {
		return allocationCatgList;
	}
	public void setAllocationCatgList(List<LookUp> allocationCatgList) {
		this.allocationCatgList = allocationCatgList;
	}
	public List<LookUp> getAllocationSubCatgList() {
		return allocationSubCatgList;
	}
	public void setAllocationSubCatgList(List<LookUp> allocationSubCatgList) {
		this.allocationSubCatgList = allocationSubCatgList;
	}
	public List<LookUp> getAllcSubCatgTargetList() {
		return allcSubCatgTargetList;
	}
	public void setAllcSubCatgTargetList(List<LookUp> allcSubCatgTargetList) {
		this.allcSubCatgTargetList = allcSubCatgTargetList;
	}
	public List<CBBOMasterDto> getCbboMasterList() {
		return cbboMasterList;
	}
	public void setCbboMasterList(List<CBBOMasterDto> cbboMasterList) {
		this.cbboMasterList = cbboMasterList;
	}
	public String getViewMode() {
		return viewMode;
	}
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		CreditGuaranteeCGFMasterDto oldMasDto = getDto();

		if (oldMasDto.getAppStatus().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			
		} else {
			
		}
		/*	getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
		getWorkflowActionDto().setDecision(oldMasDto.getAppStatus());*/

		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setIsFinalApproval(true);
		taskAction.setComments(getWorkflowActionDto().getComments());
		taskAction.setDecision(getWorkflowActionDto().getDecision());
		taskAction.setApplicationId(oldMasDto.getApplicationNumber());
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
			iWorkflowExecutionService.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}

		oldMasDto.setAuthRemark(getWorkflowActionDto().getComments());

		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowRequestService.class).findByApplicationId(Long.valueOf(oldMasDto.getApplicationNumber()));
		int size = workflowRequest.getWorkFlowTaskList().size();
		if (workflowRequest != null&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {

			creditGuaranteeRequestMasterService.updateApprovalStatusAndRemark(oldMasDto, null, workflowRequest.getLastDecision());

		}
		if (workflowRequest != null&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
			creditGuaranteeRequestMasterService.updateApprovalStatusAndRemark(oldMasDto, workflowRequest.getLastDecision(),
					workflowRequest.getStatus());

			String taskName = workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName();

			String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size - 2).getTaskName();

			if (!taskName.equals(taskNamePrevious)) {

			}
		}
		if (workflowRequest != null&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
			creditGuaranteeRequestMasterService.updateApprovalStatusAndRemark(oldMasDto, workflowRequest.getLastDecision(), workflowRequest.getStatus());
			String taskName = workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName();

			String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size - 2).getTaskName();
			if (!taskName.equals(taskNamePrevious)) {}
		}
		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			creditGuaranteeRequestMasterService.updateApprovalStatusAndRemark(oldMasDto, workflowRequest.getLastDecision(), workflowRequest.getStatus());

		}


		this.setSuccessMessage(
				(getAppSession().getMessage("sfac.change.block.approved.msg")) + " " + oldMasDto.getApplicationNumber());
		return true;

	}


}
