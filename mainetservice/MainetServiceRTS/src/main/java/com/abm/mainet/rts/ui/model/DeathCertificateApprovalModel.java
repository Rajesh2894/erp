package com.abm.mainet.rts.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

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
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.dto.DeathCertificateDTO;
import com.abm.mainet.rts.service.IDeathCertificateApprovalService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DeathCertificateApprovalModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IDeathCertificateApprovalService iDeathCertificateApprovalService;
	
	private  DeathCertificateDTO deathCertificateDTO = new DeathCertificateDTO();
	
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	@Resource
	IFileUploadService fileUpload;
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();

	
	public DeathCertificateDTO getDeathCertificateDTO() {
		return deathCertificateDTO;
	}

	public void setDeathCertificateDTO(DeathCertificateDTO deathCertificateDTO) {
		this.deathCertificateDTO = deathCertificateDTO;
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

	public boolean saveDeathApprovalDetails(String applicationNo, Long orgId, String task) {
		
		Boolean checkFinalAproval = iDeathCertificateApprovalService.checkEmployeeRole(UserSession.getCurrent());
		if (checkFinalAproval == true) {
			setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
					.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

			if (attachments.isEmpty() || attachments == null) {
				addValidationError(ApplicationSession.getInstance().getMessage("rts.validation.document"));
				return false;
			}

			else {
				FileUploadDTO uploadDTO = new FileUploadDTO();

				uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				uploadDTO.setStatus(MainetConstants.FlagA);
				uploadDTO.setDepartmentName(RtsConstants.RTS);
				uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				uploadDTO.setIdfId(String.valueOf(deathCertificateDTO.getApplicationNo()));

				deathCertificateDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

				fileUpload.doMasterFileUpload(getAttachments(), uploadDTO);
			}

		}
		
		RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(RtsConstants.RTS);
        requestDTO.setServiceId(getServiceId());
        getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
        getWorkflowActionDto().setDecision(deathCertificateDTO.getDeathRegstatus());
        deathCertificateDTO.setApplicationNo(Long.valueOf(applicationNo));
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(RtsConstants.RDC,UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
        deathCertificateDTO.setSmServiceId(service.getSmServiceId());
        prepareWorkFlowTaskAction(getWorkflowActionDto());
        getWorkflowActionDto().setReferenceId(null);
        iDeathCertificateApprovalService.updateWorkFlowDeathService(getWorkflowActionDto());
        
		/*
		 * FileUploadDTO uploadDTO = new FileUploadDTO();
		 * uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		 * uploadDTO.setStatus(MainetConstants.FlagA);
		 * uploadDTO.setDepartmentName(RtsConstants.RTS);
		 * uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		 * uploadDTO.setIdfId(String.valueOf(deathCertificateDTO.getApplicationNo()));
		 * 
		 * deathCertificateDTO.setOrgId(UserSession.getCurrent().getOrganisation().
		 * getOrgid());
		 * setAttachments(ApplicationContextProvider.getApplicationContext().getBean(
		 * IFileUploadService.class) .setFileUploadMethod(new
		 * ArrayList<DocumentDetailsVO>()));
		 * 
		 * fileUpload.doMasterFileUpload(getAttachments(), uploadDTO);
		 */
		
        
        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(deathCertificateDTO.getApplicationNo(),null,
                        UserSession.getCurrent().getOrganisation().getOrgid());
	int size = workflowRequest.getWorkFlowTaskList().size();		
	if (workflowRequest != null
    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) 
	{     
		iDeathCertificateApprovalService.updateDeathApproveStatus(deathCertificateDTO,null,workflowRequest.getLastDecision());
		iDeathCertificateApprovalService.updateDeathWorkFlowStatus(deathCertificateDTO.getDrRId(), workflowRequest.getLastDecision(), orgId,deathCertificateDTO.getDrStatus());
		iDeathCertificateApprovalService.smsAndEmailApproval(deathCertificateDTO, workflowRequest.getLastDecision());
    }
    if (workflowRequest != null 
    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
    && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) 
    {
    	iDeathCertificateApprovalService.updateDeathApproveStatus(deathCertificateDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
    	iDeathCertificateApprovalService.updateDeathWorkFlowStatus(deathCertificateDTO.getDrRId(), workflowRequest.getStatus(), orgId,deathCertificateDTO.getDrStatus());
    	//Current Task Name
    	String taskName = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
    	//Previous Task Name
    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
    	if (!taskName.equals(taskNamePrevious))
    	{ 
    		iDeathCertificateApprovalService.updateDeathWorkFlowStatus(deathCertificateDTO.getDrRId(), workflowRequest.getStatus(), orgId,deathCertificateDTO.getDrStatus());
    		deathCertificateDTO.setWfStatus(taskNamePrevious);
    	}	
    } 
    if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) 
     {	
    	iDeathCertificateApprovalService.updateDeathApproveStatus(deathCertificateDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
    	deathCertificateDTO.setWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
    	   String drStatus=RtsConstants.BRSTATUSAPPROVED;
    	   iDeathCertificateApprovalService.updateDeathWorkFlowStatus(deathCertificateDTO.getDrRId(),workflowRequest.getLastDecision(), orgId,drStatus); 
    	   iDeathCertificateApprovalService.smsAndEmailApproval(deathCertificateDTO, workflowRequest.getLastDecision());
     }
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
        workflowActionDto.setComments(deathCertificateDTO.getAuthRemark());
        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
        workflowActionDto.setIsFinalApproval(false);
        return workflowActionDto;
    }

	
	
}
