package com.abm.mainet.additionalservices.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.additionalservices.constant.NOCForBuildPermissionConstant;
import com.abm.mainet.additionalservices.dto.NOCforOtherGovtDeptDto;
import com.abm.mainet.additionalservices.service.NOCForOtherGovtDeptService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class NOCForOtherGovtDeptApprovalModel extends AbstractFormModel {

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	private IWorkFlowTypeService workFlowTypeService;

	@Autowired
	NOCForOtherGovtDeptService nocForOtherGovtDeptService;

	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	private String saveMode;

	private TbCfcApplicationMst cfcApplicationMst;
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private NOCforOtherGovtDeptDto noCforOtherGovtDeptDto;

	public NOCforOtherGovtDeptDto getNoCforOtherGovtDeptDto() {
		return noCforOtherGovtDeptDto;
	}

	public void setNoCforOtherGovtDeptDto(NOCforOtherGovtDeptDto noCforOtherGovtDeptDto) {
		this.noCforOtherGovtDeptDto = noCforOtherGovtDeptDto;
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

	public TbCfcApplicationMst getCfcApplicationMst() {
		return cfcApplicationMst;
	}

	public void setCfcApplicationMst(TbCfcApplicationMst cfcApplicationMst) {
		this.cfcApplicationMst = cfcApplicationMst;
	}

	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public boolean saveApprovalDetails(String valueOf, long orgid, String taskName,Long taskId) {

		// Boolean checkFinalAproval =
		// nocForOtherGovtDeptService.checkEmployeeRole(UserSession.getCurrent());
		boolean lastApproval = workFlowTypeService.isLastTaskInCheckerTaskList(taskId);
		/*
		 * if (lastApproval == true) {
		 * setAttachments(ApplicationContextProvider.getApplicationContext().getBean(
		 * IFileUploadService.class) .setFileUploadMethod(new
		 * ArrayList<DocumentDetailsVO>()));
		 * 
		 * if (attachments.isEmpty() || attachments == null) { addValidationError(
		 * ApplicationSession.getInstance().getMessage(
		 * "NOCBuildingPermission.validation.document")); return false; }
		 * 
		 * else { FileUploadDTO uploadDTO = new FileUploadDTO();
		 * uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		 * uploadDTO.setStatus(MainetConstants.FlagA);
		 * uploadDTO.setDepartmentName(NOCForBuildPermissionConstant.COM);
		 * uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		 * uploadDTO.setIdfId(valueOf);
		 * noCforOtherGovtDeptDto.setOrgId(UserSession.getCurrent().getOrganisation().
		 * getOrgid());
		 * 
		 * fileUpload.doMasterFileUpload(getAttachments(), uploadDTO); }
		 * 
		 * }
		 */

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDepartmentName(NOCForBuildPermissionConstant.COM);
		requestDTO.setServiceId(getServiceId());
		getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));// change vi
		getWorkflowActionDto().setDecision(noCforOtherGovtDeptDto.getBirthRegstatus());
		getWorkflowActionDto().setComments(noCforOtherGovtDeptDto.getBirthRegremark());
		noCforOtherGovtDeptDto.setApmApplicationId(Long.parseLong(getWorkflowActionDto().getReferenceId()));
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept,
						UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
		noCforOtherGovtDeptDto.setSmServiceId(service.getSmServiceId());
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		nocForOtherGovtDeptService.updateWorkFlowService(getWorkflowActionDto());

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
		workflowActionDto.setComments(noCforOtherGovtDeptDto.getBirthRegremark());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		// workflowActionDto.setComments(workflowActionDto.getComments());
		return workflowActionDto;
	}

}
