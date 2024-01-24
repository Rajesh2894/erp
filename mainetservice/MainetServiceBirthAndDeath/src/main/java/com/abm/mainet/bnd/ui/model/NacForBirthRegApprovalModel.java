package com.abm.mainet.bnd.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.service.IBirthCertificateService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class NacForBirthRegApprovalModel extends AbstractFormModel {

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	IBirthCertificateService birthCer;

	@Autowired
	IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	private static final long serialVersionUID = 1L;
	private BirthCertificateDTO nacForBirthRegDTO = new BirthCertificateDTO();
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private String saveMode;

	public String saveBirthApprovalDetails(String ApplicationId, Long orgId, String task) {
		String certificateno = MainetConstants.BLANK;
		Boolean checkFinalAproval = birthCer.checkEmployeeRole(UserSession.getCurrent());
		if (checkFinalAproval == true) {
			setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
					.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

			if (attachments.isEmpty() || attachments == null) {
				/*addValidationError(ApplicationSession.getInstance().getMessage("bnd.validation.document"));
				return false;*/
			} else {
				FileUploadDTO uploadDTO = new FileUploadDTO();

				uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				uploadDTO.setStatus(MainetConstants.FlagA);
				uploadDTO.setDepartmentName(BndConstants.BIRTH_DEATH);
				uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				uploadDTO.setIdfId(ApplicationId);

				nacForBirthRegDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

				fileUpload.doMasterFileUpload(getAttachments(), uploadDTO);
			}

		}
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDepartmentName(BndConstants.BIRTH_DEATH);
		requestDTO.setServiceId(getServiceId());
		getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));// change vi
		getWorkflowActionDto().setDecision(nacForBirthRegDTO.getBirthRegstatus());
		getWorkflowActionDto().setComments(nacForBirthRegDTO.getBirthRegremark());
		nacForBirthRegDTO.setApplicationId(getWorkflowActionDto().getReferenceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(BndConstants.NBR, UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
		nacForBirthRegDTO.setSmServiceId(service.getSmServiceId());
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		birthCer.updateWorkFlowDeathService(getWorkflowActionDto());

		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(Long.valueOf(nacForBirthRegDTO.getApplicationId()), null,
						UserSession.getCurrent().getOrganisation().getOrgid());
		int size = workflowRequest.getWorkFlowTaskList().size();
		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			birthCer.updateBirthApproveStatusBR(nacForBirthRegDTO, null, workflowRequest.getLastDecision());
			birthCer.updateBirthWorkFlowStatus(nacForBirthRegDTO.getBrRId(), workflowRequest.getLastDecision(), orgId,
					nacForBirthRegDTO.getBrStatus());
			 birthCer.smsAndEmailApproval(nacForBirthRegDTO,workflowRequest.getLastDecision());
		}

		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
				&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
			birthCer.updateBirthApproveStatusBR(nacForBirthRegDTO, workflowRequest.getLastDecision(),
					workflowRequest.getStatus());
			birthCer.updateBirthWorkFlowStatus(nacForBirthRegDTO.getBrRId(), workflowRequest.getStatus(), orgId,
					nacForBirthRegDTO.getBrStatus());
			// Current Task Name
			String taskName = workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName();
			// Previous Task Name
			String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size - 2).getTaskName();
			if (!taskName.equals(taskNamePrevious)) {
				birthCer.updateBirthWorkFlowStatus(nacForBirthRegDTO.getBrRId(), workflowRequest.getStatus(), orgId,
						nacForBirthRegDTO.getBrStatus());
				nacForBirthRegDTO.setBirthWfStatus(taskNamePrevious);

			}
		}
		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			birthCer.updateBirthApproveStatusBR(nacForBirthRegDTO, workflowRequest.getLastDecision(),
					workflowRequest.getStatus());
			nacForBirthRegDTO.setBirthWfStatus(workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName());
			nacForBirthRegDTO.setBrStatus(BndConstants.BRSTATUSAPPROVED);
			birthCer.updateBirthWorkFlowStatus(nacForBirthRegDTO.getBrRId(), workflowRequest.getLastDecision(), orgId,
					nacForBirthRegDTO.getBrStatus());
			certificateno = issuenceOfBirthCertificateService.generateCertificate(nacForBirthRegDTO, workflowRequest.getLastDecision(), workflowRequest.getStatus());
			nacForBirthRegDTO.setBrCertNo(certificateno); 
			birthCer.smsAndEmailApprovalNacBirth(nacForBirthRegDTO,workflowRequest.getLastDecision());
		}
		return certificateno;
	}

	private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
		getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		workflowActionDto.setDateOfAction(new Date());
		workflowActionDto.setCreatedDate(new Date());
		workflowActionDto.setComments(nacForBirthRegDTO.getBirthRegremark());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		// workflowActionDto.setComments(workflowActionDto.getComments());
		return workflowActionDto;
	}

	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}

	public BirthCertificateDTO getNacForBirthRegDTO() {
		return nacForBirthRegDTO;
	}

	public void setNacForBirthRegDTO(BirthCertificateDTO nacForBirthRegDTO) {
		this.nacForBirthRegDTO = nacForBirthRegDTO;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

}
