package com.abm.mainet.firemanagement.ui.model;

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
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.service.IFireCallRegisterService;
import com.abm.mainet.firemanagement.ui.validator.FireCallRegisterValidator;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class FireCallRegisterApprovalModel extends AbstractFormModel {

	private static final long serialVersionUID = 3108541764227266478L;

	@Autowired
	private IFireCallRegisterService fireCallRegisterService;

	@Autowired
	private IFileUploadService fileUpload;

	private FireCallRegisterDTO entity = new FireCallRegisterDTO();

	private String saveMode;

	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	/**
	 * @return the entity
	 */
	public FireCallRegisterDTO getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(FireCallRegisterDTO entity) {
		this.entity = entity;
	}

	/**
	 * @return the saveMode
	 */
	public String getSaveMode() {
		return saveMode;
	}

	/**
	 * @param saveMode the saveMode to set
	 */
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	@Override
	public boolean saveForm() {

		return prepareSaveSanctionDetails();
	}

	public boolean prepareSaveSanctionDetails() {

		validateBean(entity, FireCallRegisterValidator.class);
		if (this.hasValidationErrors()) {
			return false;
		}

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgid);
		requestDTO.setDepartmentName("fm");
		requestDTO.setServiceId(getServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("FCR", orgid);
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());

		List<DocumentDetailsVO> docs = new ArrayList<>();
		DocumentDetailsVO document = new DocumentDetailsVO();
		document.setDocumentSerialNo(1L);
		docs.add(document);
		setAttachments(fileUpload.prepareFileUpload(docs));
		fileUpload.doFileUpload(getAttachments(), requestDTO);
		List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext()
				.getBean(IChecklistVerificationService.class)
				.fetchAllAttachIdByReferenceId(getWorkflowActionDto().getReferenceId(), orgid);
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setAttachementId(attacheMentIds);

		ApplicationContextProvider.getApplicationContext().getBean(IFireCallRegisterService.class)
				.updateWorkFlowWorksService(prepareWorkFlowTaskAction(this.getWorkflowActionDto()));

		boolean lastLevel = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
				.isLastTaskInCheckerTaskListGroup(getWorkflowActionDto().getTaskId());

		FireCallRegisterDTO fireCallRegisterDTO = ApplicationContextProvider.getApplicationContext()
				.getBean(IFireCallRegisterService.class)
				.findByComplainNo(getWorkflowActionDto().getReferenceId(), orgid);

	
			WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowRequestService.class)
					.getWorkflowRequestByAppIdOrRefId(null, getWorkflowActionDto().getReferenceId(), orgid);

			if (workflowRequest != null
					&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
				fireCallRegisterService.updateComplainStatus(getWorkflowActionDto().getReferenceId(),
						MainetConstants.FlagR);
			}
			
		if (lastLevel == true) {
			
			if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
					&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
				fireCallRegisterService.updateComplainStatus(getWorkflowActionDto().getReferenceId(),MainetConstants.FlagA);
			}
		}

		setSuccessMessage(getAppSession().getMessage("FireCallRegisterDTO.form.save" + "",
				new Object[] { fireCallRegisterDTO.getCmplntNo() }));
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
}
