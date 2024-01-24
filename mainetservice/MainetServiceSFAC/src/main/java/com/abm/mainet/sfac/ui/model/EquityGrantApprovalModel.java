package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

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
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.EquityGrantMasterDto;
import com.abm.mainet.sfac.service.EquityGrantRequestMasterService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class EquityGrantApprovalModel extends AbstractFormModel {

	@Autowired
	private EquityGrantRequestMasterService equityGrantRequestMasterService;

	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5121063868789977285L;

	List<DesignationBean> designlist = new ArrayList<>();

	EquityGrantMasterDto dto = new EquityGrantMasterDto();

	private EquityGrantMasterDto newEquityGrantMasterDto = new EquityGrantMasterDto();

	List<EquityGrantMasterDto> equityGrantMasterDtos = new ArrayList<>();

	List<EquityGrantMasterDto> changedEquityGrantMasterDtos = new ArrayList<>();

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

	/**
	 * @return the equityGrantMasterDto
	 */


	/**
	 * @return the inactiveBlockDtoList
	 */


	/**
	 * @return the changedEquityGrantMasterDtos
	 */
	public List<EquityGrantMasterDto> getchangedEquityGrantMasterDtos() {
		return changedEquityGrantMasterDtos;
	}



	public EquityGrantMasterDto getDto() {
		return dto;
	}



	public void setDto(EquityGrantMasterDto dto) {
		this.dto = dto;
	}



	public EquityGrantMasterDto getNewEquityGrantMasterDto() {
		return newEquityGrantMasterDto;
	}

	public void setNewEquityGrantMasterDto(EquityGrantMasterDto newEquityGrantMasterDto) {
		this.newEquityGrantMasterDto = newEquityGrantMasterDto;
	}

	public List<EquityGrantMasterDto> getEquityGrantMasterDtos() {
		return equityGrantMasterDtos;
	}

	public void setEquityGrantMasterDtos(List<EquityGrantMasterDto> equityGrantMasterDtos) {
		this.equityGrantMasterDtos = equityGrantMasterDtos;
	}

	public List<EquityGrantMasterDto> getChangedEquityGrantMasterDtos() {
		return changedEquityGrantMasterDtos;
	}

	public void setChangedEquityGrantMasterDtos(List<EquityGrantMasterDto> changedEquityGrantMasterDtos) {
		this.changedEquityGrantMasterDtos = changedEquityGrantMasterDtos;
	}

	/**
	 * @param changedEquityGrantMasterDtos the changedEquityGrantMasterDtos to set
	 */
	public void setchangedEquityGrantMasterDtos(List<EquityGrantMasterDto> changedEquityGrantMasterDtos) {
		this.changedEquityGrantMasterDtos = changedEquityGrantMasterDtos;
	}

	/**
	 * @return the orgList
	 */
	public List<TbOrganisation> getOrgList() {
		return orgList;
	}

	/**
	 * @param orgList the orgList to set
	 */
	public void setOrgList(List<TbOrganisation> orgList) {
		this.orgList = orgList;
	}

	/**
	 * @return the departmentList
	 */
	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}

	/**
	 * @param departmentList the departmentList to set
	 */
	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	/**
	 * @return the faYears
	 */
	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	/**
	 * @param faYears the faYears to set
	 */
	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	/**
	 * @return the documentList
	 */
	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	/**
	 * @param documentList the documentList to set
	 */
	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	/**
	 * @return the attachDocsList
	 */
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	/**
	 * @param attachDocsList the attachDocsList to set
	 */
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	/**
	 * @return the commonMasterDtoList
	 */
	public List<CommonMasterDto> getCommonMasterDtoList() {
		return commonMasterDtoList;
	}

	/**
	 * @param commonMasterDtoList the commonMasterDtoList to set
	 */
	public void setCommonMasterDtoList(List<CommonMasterDto> commonMasterDtoList) {
		this.commonMasterDtoList = commonMasterDtoList;
	}

	/**
	 * @return the newEquityGrantMasterDto
	 */
	public EquityGrantMasterDto getnewEquityGrantMasterDto() {
		return newEquityGrantMasterDto;
	}

	/**
	 * @param newEquityGrantMasterDto the newEquityGrantMasterDto to set
	 */
	public void setnewEquityGrantMasterDto(EquityGrantMasterDto newEquityGrantMasterDto) {
		this.newEquityGrantMasterDto = newEquityGrantMasterDto;
	}

	/**
	 * @return the stateList
	 */
	public List<LookUp> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<LookUp> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the districtList
	 */
	public List<LookUp> getDistrictList() {
		return districtList;
	}

	/**
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}

	/**
	 * @return the blockList
	 */
	public List<LookUp> getBlockList() {
		return blockList;
	}

	/**
	 * @param blockList the blockList to set
	 */
	public void setBlockList(List<LookUp> blockList) {
		this.blockList = blockList;
	}

	/**
	 * @return the allocationCatgList
	 */
	public List<LookUp> getAllocationCatgList() {
		return allocationCatgList;
	}

	/**
	 * @param allocationCatgList the allocationCatgList to set
	 */
	public void setAllocationCatgList(List<LookUp> allocationCatgList) {
		this.allocationCatgList = allocationCatgList;
	}

	/**
	 * @return the allocationSubCatgList
	 */
	public List<LookUp> getAllocationSubCatgList() {
		return allocationSubCatgList;
	}

	/**
	 * @param allocationSubCatgList the allocationSubCatgList to set
	 */
	public void setAllocationSubCatgList(List<LookUp> allocationSubCatgList) {
		this.allocationSubCatgList = allocationSubCatgList;
	}

	/**
	 * @return the allcSubCatgTargetList
	 */
	public List<LookUp> getAllcSubCatgTargetList() {
		return allcSubCatgTargetList;
	}

	/**
	 * @param allcSubCatgTargetList the allcSubCatgTargetList to set
	 */
	public void setAllcSubCatgTargetList(List<LookUp> allcSubCatgTargetList) {
		this.allcSubCatgTargetList = allcSubCatgTargetList;
	}

	/**
	 * @return the cbboMasterList
	 */
	public List<CBBOMasterDto> getCbboMasterList() {
		return cbboMasterList;
	}

	/**
	 * @param cbboMasterList the cbboMasterList to set
	 */
	public void setCbboMasterList(List<CBBOMasterDto> cbboMasterList) {
		this.cbboMasterList = cbboMasterList;
	}

	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		EquityGrantMasterDto oldMasDto = getDto();

		if (oldMasDto.getAppStatus().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			oldMasDto.getEquityGrantDetailDto().forEach(dto -> {
				dto.setUpdatedBy(createdBy);
				dto.setUpdatedDate(newDate);
				dto.setOrgId(orgId);
				dto.setLgIpMac(lgIp);

			});
		} else {
			oldMasDto.getEquityGrantDetailDto().forEach(e -> {
				e.setUpdatedBy(createdBy);
				e.setUpdatedDate(newDate);
				e.setOrgId(orgId);
				e.setLgIpMac(lgIp);

			});


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
		taskAction.setApplicationId(oldMasDto.getAppNumber());
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
			iWorkflowExecutionService.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}

		oldMasDto.setAuthRemark(getWorkflowActionDto().getComments());

		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowRequestService.class).findByApplicationId(Long.valueOf(oldMasDto.getAppNumber()));
		int size = workflowRequest.getWorkFlowTaskList().size();
		if (workflowRequest != null&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {

			equityGrantRequestMasterService.updateApprovalStatusAndRemark(oldMasDto, null, workflowRequest.getLastDecision());

		}
		if (workflowRequest != null&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
			equityGrantRequestMasterService.updateApprovalStatusAndRemark(oldMasDto, workflowRequest.getLastDecision(),
					workflowRequest.getStatus());

			String taskName = workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName();

			String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size - 2).getTaskName();

			if (!taskName.equals(taskNamePrevious)) {

			}
		}
		if (workflowRequest != null&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
			equityGrantRequestMasterService.updateApprovalStatusAndRemark(oldMasDto, workflowRequest.getLastDecision(), workflowRequest.getStatus());
			String taskName = workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName();

			String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size - 2).getTaskName();
			if (!taskName.equals(taskNamePrevious)) {}
		}
		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			equityGrantRequestMasterService.updateApprovalStatusAndRemark(oldMasDto, workflowRequest.getLastDecision(), workflowRequest.getStatus());

		}


		this.setSuccessMessage(
				(getAppSession().getMessage("sfac.change.block.approved.msg")) + " " + oldMasDto.getAppNumber());
		return true;

	}



	public List<DesignationBean> getDesignlist() {
		return designlist;
	}



	public void setDesignlist(List<DesignationBean> designlist) {
		this.designlist = designlist;
	}



	public String getViewMode() {
		return viewMode;
	}



	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}






}


