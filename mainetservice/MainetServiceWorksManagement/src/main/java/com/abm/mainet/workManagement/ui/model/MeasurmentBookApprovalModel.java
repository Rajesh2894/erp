package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.workManagement.dto.ApprovalTermsConditionDto;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.ApprovalTermsConditionService;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;

@Component
@Scope("session")
public class MeasurmentBookApprovalModel extends AbstractFormModel {
	@Autowired
	private ApprovalTermsConditionService termsConditionService;
	
	@Autowired
	MeasurementBookService mbService;
	
	@Autowired
	private IWorkFlowTypeService workFlowTypeService;


	private List<WorkOrderDto> workOrderDtoList = new ArrayList<>();
	private WorkOrderDto workOrderDto = new WorkOrderDto();
	private List<WorkEstimateMasterDto> estimateMasDtoList = new ArrayList<>();
	private List<WorkDefinitionDto> workList = new ArrayList<>();
	private WorkDefinitionDto workDefinitionDto;
	private List<TbDepartment> departmentsList;
	private List<ApprovalTermsConditionDto> termsConditionDtosList = new ArrayList<>();
	private String removeTermsById;
	private Long actualTaskId;
	private Long workMbId;
	private String completedFlag;
	
	private MeasurementBookMasterDto measurementBookMasterDto = new MeasurementBookMasterDto();
	private List<MeasurementBookMasterDto> measurementBookMasterList = new ArrayList<>();
	private String estimateMode;
	private List<WorkflowTaskActionWithDocs> actionWithDocs;
	
	public List<TbDepartment> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<TbDepartment> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public void setParentOrgId(long orgid) {
		

	}

	public Object getParentOrgId() {
		
		return null;
	}

	public List<WorkOrderDto> getWorkOrderDtoList() {
		return workOrderDtoList;
	}

	public void setWorkOrderDtoList(List<WorkOrderDto> workOrderDtoList) {
		this.workOrderDtoList = workOrderDtoList;
	}

	public WorkOrderDto getWorkOrderDto() {
		return workOrderDto;
	}

	public void setWorkOrderDto(WorkOrderDto workOrderDto) {
		this.workOrderDto = workOrderDto;
	}

	public List<WorkEstimateMasterDto> getEstimateMasDtoList() {
		return estimateMasDtoList;
	}

	public void setEstimateMasDtoList(List<WorkEstimateMasterDto> estimateMasDtoList) {
		this.estimateMasDtoList = estimateMasDtoList;
	}

	public List<WorkDefinitionDto> getWorkList() {
		return workList;
	}

	public void setWorkList(List<WorkDefinitionDto> workList) {
		this.workList = workList;
	}

	public WorkDefinitionDto getWorkDefinitionDto() {
		return workDefinitionDto;
	}

	public void setWorkDefinitionDto(WorkDefinitionDto workDefinitionDto) {
		this.workDefinitionDto = workDefinitionDto;
	}

	public String getRemoveTermsById() {
		return removeTermsById;
	}

	public void setRemoveTermsById(String removeTermsById) {
		this.removeTermsById = removeTermsById;
	}

	public ApprovalTermsConditionService getTermsConditionService() {
		return termsConditionService;
	}

	public void setTermsConditionService(ApprovalTermsConditionService termsConditionService) {
		this.termsConditionService = termsConditionService;
	}

	public List<ApprovalTermsConditionDto> getTermsConditionDtosList() {
		return termsConditionDtosList;
	}

	public void setTermsConditionDtosList(List<ApprovalTermsConditionDto> termsConditionDtosList) {
		this.termsConditionDtosList = termsConditionDtosList;
	}
	

	public MeasurementBookService getMbService() {
		return mbService;
	}

	public void setMbService(MeasurementBookService mbService) {
		this.mbService = mbService;
	}
	

	public Long getWorkMbId() {
		return workMbId;
	}

	public void setWorkMbId(Long workMbId) {
		this.workMbId = workMbId;
	}
	

	public MeasurementBookMasterDto getMeasurementBookMasterDto() {
		return measurementBookMasterDto;
	}

	public void setMeasurementBookMasterDto(MeasurementBookMasterDto measurementBookMasterDto) {
		this.measurementBookMasterDto = measurementBookMasterDto;
	}

	public List<MeasurementBookMasterDto> getMeasurementBookMasterList() {
		return measurementBookMasterList;
	}

	public void setMeasurementBookMasterList(List<MeasurementBookMasterDto> measurementBookMasterList) {
		this.measurementBookMasterList = measurementBookMasterList;
	}
	

	public String getEstimateMode() {
		return estimateMode;
	}

	public void setEstimateMode(String estimateMode) {
		this.estimateMode = estimateMode;
	}
	

	public List<WorkflowTaskActionWithDocs> getActionWithDocs() {
		return actionWithDocs;
	}

	public void setActionWithDocs(List<WorkflowTaskActionWithDocs> actionWithDocs) {
		this.actionWithDocs = actionWithDocs;
	}

	public boolean prepareSaveSanctionDetails() {

		
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
		requestDTO.setServiceId(getServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.WorksManagement.MCS, UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
		
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		
		boolean lastSecondLevel =workFlowTypeService.isLastTaskInCheckerTaskListGroup(actualTaskId);
		
		
		ApplicationContextProvider.getApplicationContext().getBean(WorksWorkFlowService.class)
				.updateWorkFlowWorksService(getWorkflowActionDto(), service.getSmServiceId(), UserSession.getCurrent().getOrganisation().getOrgid());
	WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(null, getWorkflowActionDto().getReferenceId(), UserSession.getCurrent().getOrganisation().getOrgid());

		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			 mbService.updateMeasureMentMode(getWorkMbId(),MainetConstants.WorksManagement.send_for_approval );
			
		}

		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			 mbService.updateMeasureMentMode(getWorkMbId(),MainetConstants.WorksManagement.Send_for_RA_Bill );
		}

	

		workDefinitionDto.setWorkStatus(workflowRequest.getLastDecision());
		return true;

	}

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

	public String getCompletedFlag() {
		return completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}
}