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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
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
import com.abm.mainet.rts.dto.BirthCertificateDTO;
import com.abm.mainet.rts.service.IBirthCertificateService;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class BirthCertificateApprovalModel extends AbstractFormModel{
	private static final long serialVersionUID = 1L;
	
	BirthCertificateDTO birthCertificateDto = new BirthCertificateDTO();
	
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
	
	@Resource
	IFileUploadService fileUpload;
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();

	
	private String saveMode;
	
	

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

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	@Autowired
	IBirthCertificateService birthCer;
	
	@Override
	public boolean saveForm() {

		
				Employee employee = getUserSession().getEmployee();
				int langId = UserSession.getCurrent().getLanguageId();
				birthCertificateDto.setUpdatedBy(employee.getEmpId());
				birthCertificateDto.setUpdatedDate(new Date());
				birthCertificateDto.setLmoddate(new Date());;
				birthCertificateDto.setLgIpMac(employee.getEmppiservername());
				birthCertificateDto.setLgIpMacUpd(employee.getEmppiservername());
				birthCertificateDto.setOrgId(employee.getOrganisation().getOrgid());
				birthCertificateDto.setLangId(langId);
				//ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				//		.prepareFileUpload(checkList);
				//birthCertificateDto.setUploadDocument(checkList);
				birthCer.saveBirthRegDet(birthCertificateDto);
				this.setSuccessMessage(
						"Birth Registration Details Saved Successfully. Birth Registration Application No. is "
								+ birthCertificateDto.getApmApplicationId());
		
		return true;

	}
	
	public boolean saveBirthApprovalDetails(String ApplicationId, Long orgId, String task)
	{
		
		Boolean checkFinalAproval = birthCer.checkEmployeeRole(UserSession.getCurrent());
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
				uploadDTO.setIdfId(ApplicationId);

				birthCertificateDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

				fileUpload.doMasterFileUpload(getAttachments(), uploadDTO);
			}

		}
		RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(RtsConstants.RTS);
        requestDTO.setServiceId(getServiceId());
        getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));//change vi
        getWorkflowActionDto().setDecision(birthCertificateDto.getBirthRegstatus());
        getWorkflowActionDto().setComments(birthCertificateDto.getBirthRegremark());
        birthCertificateDto.setApplicationId(getWorkflowActionDto().getReferenceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(RtsConstants.RBC,UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
        birthCertificateDto.setSmServiceId(service.getSmServiceId());
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		birthCer.updateWorkFlowDeathService(getWorkflowActionDto());
		
		/*
		 * FileUploadDTO uploadDTO = new FileUploadDTO();
		 * uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		 * uploadDTO.setStatus(MainetConstants.FlagA);
		 * uploadDTO.setDepartmentName(RtsConstants.RTS);
		 * uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		 * uploadDTO.setIdfId(birthCertificateDto.getApplicationId());
		 * 
		 * birthCertificateDto.setOrgId(UserSession.getCurrent().getOrganisation().
		 * getOrgid());
		 * setAttachments(ApplicationContextProvider.getApplicationContext().getBean(
		 * IFileUploadService.class) .setFileUploadMethod(new
		 * ArrayList<DocumentDetailsVO>()));
		 * 
		 * fileUpload.doMasterFileUpload(getAttachments(), uploadDTO);
		 */
		
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
	                .getBean(IWorkflowRequestService.class)
	                .getWorkflowRequestByAppIdOrRefId(Long.valueOf(birthCertificateDto.getApplicationId()),null,
	                        UserSession.getCurrent().getOrganisation().getOrgid());
		int size = workflowRequest.getWorkFlowTaskList().size();		
		if (workflowRequest != null
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) 
		{     
			birthCer.updateBirthApproveStatusBR(birthCertificateDto,null,workflowRequest.getLastDecision());
			birthCer.updateBirthWorkFlowStatusBR(birthCertificateDto.getBrRId(), workflowRequest.getLastDecision(), orgId,birthCertificateDto.getBrStatus());
			birthCer.smsAndEmailApproval(birthCertificateDto,workflowRequest.getLastDecision());
	    }
		
	    if (workflowRequest != null 
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
	    && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) 
	    {
	    	birthCer.updateBirthApproveStatusBR(birthCertificateDto,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	birthCer.updateBirthWorkFlowStatusBR(birthCertificateDto.getBrRId(),  workflowRequest.getStatus(), orgId,birthCertificateDto.getBrStatus()); 
	    	//Current Task Name
	    	String taskName = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
	    	//Previous Task Name
	    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
	    	if (!taskName.equals(taskNamePrevious))
	    	{
	    		birthCer.updateBirthWorkFlowStatusBR(birthCertificateDto.getBrRId(), workflowRequest.getStatus(), orgId,birthCertificateDto.getBrStatus());
	    		birthCertificateDto.setBirthWfStatus(taskNamePrevious);
	    	    
	    	}	
	    } 
	    if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) 
	     {	
	    	birthCer.updateBirthApproveStatusBR(birthCertificateDto,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	birthCertificateDto.setBirthWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
	    	birthCertificateDto.setBrStatus(RtsConstants.BRSTATUSAPPROVED);
	    	 birthCer.updateBirthWorkFlowStatusBR(birthCertificateDto.getBrRId(),workflowRequest.getLastDecision(), orgId,birthCertificateDto.getBrStatus());
	    	   birthCer.smsAndEmailApproval(birthCertificateDto,workflowRequest.getLastDecision());
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
        workflowActionDto.setComments(birthCertificateDto.getBirthRegremark());
        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
        workflowActionDto.setIsFinalApproval(false);
       // workflowActionDto.setComments(workflowActionDto.getComments());
        return workflowActionDto;
    }
	
	public BirthCertificateDTO getBirthCertificateDto() {
		return birthCertificateDto;
	}

	public void setBirthCertificateDto(BirthCertificateDTO birthCertificateDto) {
		this.birthCertificateDto = birthCertificateDto;
	}

}
