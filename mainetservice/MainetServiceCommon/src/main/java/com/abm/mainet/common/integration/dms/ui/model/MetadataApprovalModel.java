package com.abm.mainet.common.integration.dms.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.service.IDmsMetadataService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class MetadataApprovalModel extends AbstractFormModel {

	@Autowired
	private IDmsMetadataService metadataService;

	private static final long serialVersionUID = 1L;

	private String saveMode;

	private Long deptId;

	private List<DmsDocsMetadataDto> dmsDocsMetadataDto = new ArrayList<DmsDocsMetadataDto>();

	private List<LookUp> departmentList = new ArrayList<>();

	private DmsDocsMetadataDto dmsDto = new DmsDocsMetadataDto();

	public void saveCallClosureApprovalDetails(String requestId, Long orgid, String taskName) {
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgId);
		requestDTO.setServiceId(getServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.Dms.MTD, orgid);
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());

		prepareWorkFlowTaskActions(getWorkflowActionDto());
		metadataService.updateWorkFlowMetadataService(getWorkflowActionDto());

		boolean lastLevel = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
				.isLastTaskInCheckerTaskListGroup(getWorkflowActionDto().getTaskId());

		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class).getWorkflowRequestByAppIdOrRefId(null, requestId, orgid);

		int size = workflowRequest.getWorkFlowTaskList().size();
		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			metadataService.updateWfStatus(requestId, dmsDto.getRemark(), workflowRequest.getLastDecision(), orgId);
		}

		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
				&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {

			// Current Task Name
			String taskName1 = workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName();
			// Previous Task Name
			String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size - 2).getTaskName();
			if (!taskName1.equals(taskNamePrevious)) {
				metadataService.updateWfStatus(requestId, dmsDto.getRemark(), MainetConstants.WorkFlow.Decision.PENDING,
						orgId);
				dmsDto.setWfStatus(taskNamePrevious);
			}
		}
		if (lastLevel) {
			if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
					&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
				metadataService.updateWfStatus(requestId, dmsDto.getRemark(),
						MainetConstants.WorkFlow.Decision.APPROVED, orgId);
				dmsDto.setWfStatus(workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName());
			}
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
		workflowActionDto.setComments(dmsDto.getRemark());
		workflowActionDto.setDecision(dmsDto.getStatusApproval());
		return workflowActionDto;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<DmsDocsMetadataDto> getDmsDocsMetadataDto() {
		return dmsDocsMetadataDto;
	}

	public void setDmsDocsMetadataDto(List<DmsDocsMetadataDto> dmsDocsMetadataDto) {
		this.dmsDocsMetadataDto = dmsDocsMetadataDto;
	}

	public List<LookUp> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<LookUp> departmentList) {
		this.departmentList = departmentList;
	}

	public DmsDocsMetadataDto getDmsDto() {
		return dmsDto;
	}

	public void setDmsDto(DmsDocsMetadataDto dmsDto) {
		this.dmsDto = dmsDto;
	}

}
