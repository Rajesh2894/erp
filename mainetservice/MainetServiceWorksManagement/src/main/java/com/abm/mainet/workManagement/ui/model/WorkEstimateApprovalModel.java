package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.workManagement.dto.ApprovalTermsConditionDto;
import com.abm.mainet.workManagement.dto.WorkDefinationYearDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionSancDetDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.service.ApprovalTermsConditionService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;
import com.abm.mainet.workManagement.ui.validator.WorkEstimateApprovalValidator;

/**
 * @author vishwajeet.kumar
 * @since 8 March 2018
 */
@Component
@Scope("session")
public class WorkEstimateApprovalModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	public IFileUploadService fileUpload;

	@Autowired
	private WorkDefinitionService workDefinationService;

	@Autowired
	private IWorkFlowTypeService workFlowTypeService;

	@Autowired
	private ApprovalTermsConditionService termsConditionService;

	private WorkDefinitionDto workDefinitionDto;

	private WorkEstimateMasterDto estimateMasterDto;

	private List<TbDepartment> departmentsList;

	private List<Object[]> employeeList;

	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	private List<ApprovalTermsConditionDto> termsConditionDtosList = new ArrayList<>();

	List<WorkDefinationYearDetDto> yearDetDtosList = new ArrayList<>();

	private ApprovalTermsConditionDto conditionDto;

	private WorkDefinitionSancDetDto definitionSancDetDto;

	private String workEstimateNo;

	private String saveMode;

	private Long workId;

	private String reportType;
	
	private String completedFlag;

	private String estimateMode;

	private Long actualTaskId;

	private Long serviceId;

	private boolean finalApproval;

	private Long workflowId;

	private Long parentOrgId;

	private String taskName;

	private String removeTermsById;
	private String workCode;
	private Long projId;

	private String sanctionNumber;
	private String empName;
	private String sancDate;
	private String flagForSendBack;

	private List<TbFinancialyear> faYears;
	private String cpdMode;
	private List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList;

	private List<WorkflowTaskActionWithDocs> actionWithDocs;
	private String sanctionFlag;
	
	public String getSanctionFlag() {
		return sanctionFlag;
	}

	public void setSanctionFlag(String sanctionFlag) {
		this.sanctionFlag = sanctionFlag;
	}

	public List<WorkflowTaskActionWithDocs> getActionWithDocs() {
		return actionWithDocs;
	}

	public void setActionWithDocs(List<WorkflowTaskActionWithDocs> actionWithDocs) {
		this.actionWithDocs = actionWithDocs;
	}

	public List<AccountHeadSecondaryAccountCodeMasterEntity> getBudgetList() {
		return budgetList;
	}

	public void setBudgetList(List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList) {
		this.budgetList = budgetList;
	}

	public String getCpdMode() {
		return cpdMode;
	}

	public void setCpdMode(String cpdMode) {
		this.cpdMode = cpdMode;
	}

	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	public boolean prepareSaveSanctionDetails() {

		boolean isStatus = validate();
		if (isStatus == false) {
			return false;
		}
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
		requestDTO.setServiceId(getServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.WorksManagement.WOA, getParentOrgId());
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
		List<DocumentDetailsVO> docs = new ArrayList<>();
		DocumentDetailsVO document = new DocumentDetailsVO();
		document.setDocumentSerialNo(1L);
		docs.add(document);
		setAttachments(fileUpload.prepareFileUpload(docs));
		fileUpload.doFileUpload(getAttachments(), requestDTO);
		List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext()
				.getBean(IChecklistVerificationService.class).fetchAllAttachIdByReferenceId(
						getWorkflowActionDto().getReferenceId(), UserSession.getCurrent().getOrganisation().getOrgid());
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setAttachementId(attacheMentIds);
		//boolean lastSecondLevel = workFlowTypeService.isLastTaskInCheckerTaskListGroup(actualTaskId);
		/*
		 * ApplicationContextProvider.getApplicationContext().getBean(
		 * WorksWorkFlowService.class).initiateWorkFlowWorksService(
		 * getWorkflowActionDto(), actualTaskId,
		 * MainetConstants.WorksManagement.WORK_EST_APPROVAL, MainetConstants.FlagU);
		 */
		ApplicationContextProvider.getApplicationContext().getBean(WorksWorkFlowService.class)
				.updateWorkFlowWorksService(getWorkflowActionDto(), service.getSmServiceId(), getParentOrgId());
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(null, getWorkCode(), getParentOrgId());

		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			workDefinationService.updateWorkDefinationMode(getWorkId(), MainetConstants.FlagR);
		}

		if (getWorkDefinitionDto().getYearDtos() != null && !getWorkDefinitionDto().getYearDtos().isEmpty()) {
			workDefinationService.saveWorkDefinationYesrDet(getWorkDefinitionDto(),
					getWorkDefinitionDto().getYearDtos().get(0));
		}
		if (getWorkDefinitionDto().getWardZoneDto() != null && !getWorkDefinitionDto().getWardZoneDto().isEmpty()) {
			workDefinationService.saveWardZoneDto(getWorkDefinitionDto(), getWorkDefinitionDto().getWardZoneDto());
		}

		String sanctionNumber = null;
		// Defect #72893-->to save Both TS and AS Approval details
		/* if (isFinalApproval() || lastSecondLevel) { */
		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			String sanctionDept = null;
			FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
					.getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
			String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDeptCode(service.getTbDepartment().getDpDeptid());
			String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
			final Long sequence = ApplicationContextProvider.getApplicationContext()
					.getBean(SeqGenFunctionUtility.class)
					.generateSequenceNo(MainetConstants.WorksManagement.WORKS_MANAGEMENT,
							MainetConstants.WorksManagement.TB_WMS_SANC_DET, MainetConstants.WorksManagement.WORK_SANNO,
							getParentOrgId(), MainetConstants.FlagC, financiaYear.getFaYear());
			if (taskName.startsWith(MainetConstants.FlagA)) {
				sanctionDept = MainetConstants.WorksManagement.ADMIN_SANCTION;
			} else if (taskName.startsWith(MainetConstants.WorksManagement.FLAG_T)) {
				sanctionDept = MainetConstants.WorksManagement.TECH_SANCTION;
			}
			if (sanctionDept != null) {
				sanctionNumber = sanctionDept + MainetConstants.WINDOWS_SLASH + deptCode + MainetConstants.WINDOWS_SLASH
						+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence)
						+ MainetConstants.WINDOWS_SLASH + finacialYear;
				Long deptId = workFlowTypeService.findDepartmentIdBytaskName(taskName, getParentOrgId(),
						getWorkflowId());
				if (sanctionDept.equals(MainetConstants.WorksManagement.TECH_SANCTION)) {
					workDefinationService.updateWorkDefinationMode(getWorkId(),
							MainetConstants.WorksManagement.TECH_SANCTION);
				} else if (sanctionDept.equals(MainetConstants.WorksManagement.ADMIN_SANCTION)) {
					workDefinationService.updateWorkDefinationMode(getWorkId(),
							MainetConstants.WorksManagement.ADMIN_SANCTION);
				}
				Date date = new Date();
				WorkDefinitionSancDetDto detDto = new WorkDefinitionSancDetDto();
				detDto.setOrgid(getParentOrgId());
				detDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				detDto.setCreatedDate(date);
				detDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				detDto.setDeptId(deptId);
				detDto.setWorkId(getWorkId());
				detDto.setWorkSancNo(sanctionNumber);
				detDto.setWorkSancBy(UserSession.getCurrent().getEmployee().getEmpname()+" "+UserSession.getCurrent().getEmployee().getEmplname());
				detDto.setWorkSancDate(date);
				detDto.setWorkDesignBy(UserSession.getCurrent().getEmployee().getDesignation().getDsgname());

				workDefinationService.saveSanctionDetailsApproval(detDto);
				/*
				 * workDefinationService.updateSanctionNumber(sanctionNumber, getWorkId(),
				 * getParentOrgId(), deptId,
				 * UserSession.getCurrent().getEmployee().getEmpname(),
				 * UserSession.getCurrent().getEmployee().getDesignation().getDsgname());
				 */
				
				//Defect #85072
				termsConditionDtosList.forEach(dtos -> {
					dtos.setWorkSancNo(detDto.getWorkSancNo());
				});
				createTermsAndApplication(termsConditionDtosList);
				//termsConditionService.updateSancNoInTermsAndCodition(sanctionNumber, getWorkId(), getWorkEstimateNo());	
				workDefinitionDto.setWorkId(getWorkId());
				workDefinitionDto.setProjId(getProjId());
				workDefinitionDto.setSanctionNumber(sanctionNumber);
			}
		}
		/* } */

		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			workDefinationService.updateWorkDefinationMode(getWorkId(), MainetConstants.FlagA);
		}

		workDefinitionDto.setWorkStatus(workflowRequest.getLastDecision());
		return true;

	}

	private void createTermsAndApplication(List<ApprovalTermsConditionDto> termsConditionDtos) {
		List<ApprovalTermsConditionDto> conditionDtos = new ArrayList<>();

		if (termsConditionDtos != null) {
			termsConditionDtos.forEach(dtos -> {
				dtos.setOrgId(getParentOrgId());
				dtos.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				dtos.setCreatedDate(new Date());
				dtos.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				dtos.setWorkId(getWorkId());
				dtos.setRefId(getWorkEstimateNo());
				conditionDtos.add(dtos);
			});
			List<Long> deletedTermsId = removeTermsCondByIdAsList();
			termsConditionService.saveTermsCondition(conditionDtos, deletedTermsId);
		}
	
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

	// Deleted Terms And Condition with the help of Id's
	private List<Long> removeTermsCondByIdAsList() {
		List<Long> removeTermsList = null;
		String termsIdList = getRemoveTermsById();
		if (termsIdList != null && !termsIdList.isEmpty()) {
			removeTermsList = new ArrayList<>();
			String termsArray[] = termsIdList.split(MainetConstants.operator.COMMA);
			for (String termsCondId : termsArray) {
				removeTermsList.add(Long.valueOf(termsCondId));
			}
		}
		return removeTermsList;
	}

	public boolean validate() {
		boolean status = true;
		final WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
		validateBean(workFlowActionDto, WorkEstimateApprovalValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		return status;
	}

	public WorkEstimateMasterDto getEstimateMasterDto() {
		return estimateMasterDto;
	}

	public void setEstimateMasterDto(WorkEstimateMasterDto estimateMasterDto) {
		this.estimateMasterDto = estimateMasterDto;
	}

	public List<TbDepartment> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<TbDepartment> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public List<Object[]> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Object[]> employeeList) {
		this.employeeList = employeeList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public WorkDefinitionDto getWorkDefinitionDto() {
		return workDefinitionDto;
	}

	public void setWorkDefinitionDto(WorkDefinitionDto workDefinitionDto) {
		this.workDefinitionDto = workDefinitionDto;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getEstimateMode() {
		return estimateMode;
	}

	public void setEstimateMode(String estimateMode) {
		this.estimateMode = estimateMode;
	}

	public Long getActualTaskId() {
		return actualTaskId;
	}

	public void setActualTaskId(Long actualTaskId) {
		this.actualTaskId = actualTaskId;
	}

	@Override
	public Long getServiceId() {
		return serviceId;
	}

	@Override
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public boolean isFinalApproval() {
		return finalApproval;
	}

	public void setFinalApproval(boolean finalApproval) {
		this.finalApproval = finalApproval;
	}

	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public List<ApprovalTermsConditionDto> getTermsConditionDtosList() {
		return termsConditionDtosList;
	}

	public void setTermsConditionDtosList(List<ApprovalTermsConditionDto> termsConditionDtosList) {
		this.termsConditionDtosList = termsConditionDtosList;
	}

	public String getRemoveTermsById() {
		return removeTermsById;
	}

	public void setRemoveTermsById(String removeTermsById) {
		this.removeTermsById = removeTermsById;
	}

	public ApprovalTermsConditionDto getConditionDto() {
		return conditionDto;
	}

	public void setConditionDto(ApprovalTermsConditionDto conditionDto) {
		this.conditionDto = conditionDto;
	}

	public String getWorkEstimateNo() {
		return workEstimateNo;
	}

	public void setWorkEstimateNo(String workEstimateNo) {
		this.workEstimateNo = workEstimateNo;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String getSanctionNumber() {
		return sanctionNumber;
	}

	public void setSanctionNumber(String sanctionNumber) {
		this.sanctionNumber = sanctionNumber;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getSancDate() {
		return sancDate;
	}

	public void setSancDate(String sancDate) {
		this.sancDate = sancDate;
	}

	public List<WorkDefinationYearDetDto> getYearDetDtosList() {
		return yearDetDtosList;
	}

	public void setYearDetDtosList(List<WorkDefinationYearDetDto> yearDetDtosList) {
		this.yearDetDtosList = yearDetDtosList;
	}

	public String getFlagForSendBack() {
		return flagForSendBack;
	}

	public void setFlagForSendBack(String flagForSendBack) {
		this.flagForSendBack = flagForSendBack;
	}

	public WorkDefinitionSancDetDto getDefinitionSancDetDto() {
		return definitionSancDetDto;
	}

	public void setDefinitionSancDetDto(WorkDefinitionSancDetDto definitionSancDetDto) {
		this.definitionSancDetDto = definitionSancDetDto;
	}

	public String getCompletedFlag() {
		return completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}

}
