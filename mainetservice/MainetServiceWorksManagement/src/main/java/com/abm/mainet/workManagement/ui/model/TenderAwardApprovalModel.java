package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbOrgDesignation;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class TenderAwardApprovalModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TenderMasterDto initiationDto;
    private List<TbDepartment> departmentList;
    private String mode;
    List<TbOrgDesignation> designationList;
    private WorkflowTaskAction workflowActionDto = new WorkflowTaskAction();
	
	@Autowired
    private TenderInitiationService tenderInitiationService;
	
	 public boolean saveTenderApproval() {

	        RequestDTO requestDTO = new RequestDTO();
	        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
	        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
	        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
	                .getServiceMasterByShortCode("TDA",
	                        UserSession.getCurrent().getOrganisation().getOrgid());
	        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
	        requestDTO.setServiceId(service.getSmServiceId());
	        prepareWorkFlowTaskAction(getWorkflowActionDto());
	        ApplicationContextProvider.getApplicationContext().getBean(WorksWorkFlowService.class)
	                .updateWorkFlowWorksService(getWorkflowActionDto(), service.getSmServiceId(),
	                        UserSession.getCurrent().getOrganisation().getOrgid());

	        if (getWorkflowActionDto().getDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
	            tenderInitiationService.updateTenderStatus(UserSession.getCurrent().getOrganisation().getOrgid(),
	                    getWorkflowActionDto().getReferenceId(), MainetConstants.FlagR);
	        } else if (getWorkflowActionDto().getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
	            tenderInitiationService.updateTenderStatus(UserSession.getCurrent().getOrganisation().getOrgid(),
	                    getWorkflowActionDto().getReferenceId(), MainetConstants.FlagA);
	        }
	        return true;
	    }
	 
	 private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {

	        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	        workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
	        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
	        workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmpname() + " "
	                        + UserSession.getCurrent().getEmployee().getEmplname());
	        workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
	        workflowActionDto.setDateOfAction(new Date());
	        workflowActionDto.setCreatedDate(new Date());
	        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
	        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
	        workflowActionDto.setIsFinalApproval(false);
	        return workflowActionDto;
	    }

    
	public TenderMasterDto getInitiationDto() {
		return initiationDto;
	}
	public void setInitiationDto(TenderMasterDto initiationDto) {
		this.initiationDto = initiationDto;
	}
	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public List<TbOrgDesignation> getDesignationList() {
		return designationList;
	}
	public void setDesignationList(List<TbOrgDesignation> designationList) {
		this.designationList = designationList;
	}

	public WorkflowTaskAction getWorkflowActionDto() {
		return workflowActionDto;
	}

	public void setWorkflowActionDto(WorkflowTaskAction workflowActionDto) {
		this.workflowActionDto = workflowActionDto;
	}
    
}
