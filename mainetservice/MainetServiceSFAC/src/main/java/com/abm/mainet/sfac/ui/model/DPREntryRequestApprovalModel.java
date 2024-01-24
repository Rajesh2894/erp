package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.DPREntryMasterDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.service.DPREntryRequestService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DPREntryRequestApprovalModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4442386078746035829L;
	
	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;
	
	@Autowired DPREntryRequestService dprEntryRequestService;
	
	DPREntryMasterDto dto = new DPREntryMasterDto();
	
	private CBBOMasterDto cbboMasterDto;

	private List<CBBOMasterDto> cbboMasterDtos = new ArrayList<>();
	
	
	
	private List<FPOMasterDto> fpoMasterDtos = new ArrayList<>();
	
	private List<DPREntryMasterDto> dprEntryMasterDtos = new ArrayList<DPREntryMasterDto>();
	
	private String viewMode;

	public DPREntryMasterDto getDto() {
		return dto;
	}

	public void setDto(DPREntryMasterDto dto) {
		this.dto = dto;
	}

	public List<DPREntryMasterDto> getDprEntryMasterDtos() {
		return dprEntryMasterDtos;
	}

	public void setDprEntryMasterDtos(List<DPREntryMasterDto> dprEntryMasterDtos) {
		this.dprEntryMasterDtos = dprEntryMasterDtos;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}
	
	
	public boolean saveForm() {

		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		DPREntryMasterDto oldMasDto = getDto();

		if (oldMasDto.getStatus().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			oldMasDto.setUpdatedBy(createdBy);
			oldMasDto.setUpdatedDate(newDate);
			oldMasDto.setLgIpMac(lgIp);
			
		
		} else {
			oldMasDto.setUpdatedBy(createdBy);
			oldMasDto.setUpdatedDate(newDate);
			oldMasDto.setLgIpMac(lgIp);
			
		}
		/*	getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
		getWorkflowActionDto().setDecision(oldMasDto.getAppStatus());*/
		

		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		//taskAction.setOrgId(orgCbboId);
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
		if (workflowRequest != null&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {

			dprEntryRequestService.updateApprovalStatusAndRemark(oldMasDto, null, workflowRequest.getLastDecision());

		}
		if (workflowRequest != null&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
			dprEntryRequestService.updateApprovalStatusAndRemark(oldMasDto, workflowRequest.getLastDecision(),
					workflowRequest.getStatus());

			
		}
		if (workflowRequest != null&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
			dprEntryRequestService.updateApprovalStatusAndRemark(oldMasDto, workflowRequest.getLastDecision(), workflowRequest.getStatus());
			
			
		}
		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			dprEntryRequestService.updateApprovalStatusAndRemark(oldMasDto, workflowRequest.getLastDecision(), workflowRequest.getStatus());

		}


		this.setSuccessMessage(
				(getAppSession().getMessage("sfac.change.block.approved.msg")) + " " + oldMasDto.getApplicationNumber());
		return true;

	}

	public CBBOMasterDto getCbboMasterDto() {
		return cbboMasterDto;
	}

	public void setCbboMasterDto(CBBOMasterDto cbboMasterDto) {
		this.cbboMasterDto = cbboMasterDto;
	}

	public List<CBBOMasterDto> getCbboMasterDtos() {
		return cbboMasterDtos;
	}

	public void setCbboMasterDtos(List<CBBOMasterDto> cbboMasterDtos) {
		this.cbboMasterDtos = cbboMasterDtos;
	}

	public List<FPOMasterDto> getFpoMasterDtos() {
		return fpoMasterDtos;
	}

	public void setFpoMasterDtos(List<FPOMasterDto> fpoMasterDtos) {
		this.fpoMasterDtos = fpoMasterDtos;
	}
	
	

}
