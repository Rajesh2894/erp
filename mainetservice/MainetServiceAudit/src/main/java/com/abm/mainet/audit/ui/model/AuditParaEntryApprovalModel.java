package com.abm.mainet.audit.ui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.audit.constant.IAuditConstants;
import com.abm.mainet.audit.dto.AuditParaEntryDto;
import com.abm.mainet.audit.service.IAuditParaEntryService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;

import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;



@Component
@Scope("session")
public class AuditParaEntryApprovalModel extends AbstractFormModel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2017487791631847244L;
	private String saveMode;
	private AuditParaEntryDto approvalAuditParaDto = new AuditParaEntryDto();
	String forwardToDept;
	private Date auditDate;
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<AttachDocs> attachDocumentList = new ArrayList<>();
	private List<TbDepartment> depAppList = new ArrayList<>();
	private String resolutionComments;
	private String keyTest = "N";
	private Integer subUnitClosed;
	private String subUnitCompDone;
	private String subUnitCompPending;
	private Boolean isEditable;
	
	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}
	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}
	public String getForwardToDept() {
		return forwardToDept;
	}
	public void setForwardToDept(String forwardToDept) {
		this.forwardToDept = forwardToDept;
	}

	public List<AttachDocs> getAttachDocumentList() {
		return attachDocumentList;
	}
	public void setAttachDocumentList(List<AttachDocs> attachDocumentList) {
		this.attachDocumentList = attachDocumentList;
	}


	@Autowired
	private IAuditParaEntryService auditService;
	
	@Autowired
	private IOrganisationService orgService;
	
	@Autowired
    private IEmployeeService iEmployeeService;
	
	@Autowired
    public IFileUploadService fileUpload;
	
	public String getSaveMode() {
		return saveMode;
	}
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	public AuditParaEntryDto getApprovalAuditParaDto() {
		return approvalAuditParaDto;
	}
	public void setApprovalAuditParaDto(AuditParaEntryDto e) {
		this.approvalAuditParaDto = e;
	}
	
	public boolean saveAuditApprovalDetails(String auditParaCode, Long orgId, String task)
	{		
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		if(isEditable.equals(true)) {
			approvalAuditParaDto.setAuditAppendix(resolutionComments);
			auditService.saveAuditParaEntryService(approvalAuditParaDto,saveMode);
		}
		approvalAuditParaDto = auditService.getAuditParaEntryByAuditParaId(Long.valueOf(auditParaCode));
		Organisation org=orgService.getOrganisationById(orgId);
		prepareDocumentsData(approvalAuditParaDto); 
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
			long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
			requestDTO.setOrgId(orgid);
			requestDTO.setDepartmentName("AD");
			requestDTO.setServiceId(getServiceId());
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(IAuditConstants.AUDIT_PARA_APPROVAL_SERVICE_CODE, UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
			List<DocumentDetailsVO> docs = new ArrayList<>();
			DocumentDetailsVO document = new DocumentDetailsVO();
			document.setDocumentSerialNo(1L);
			docs.add(document);
			setAttachments(fileUpload.prepareFileUpload(docs));
	        fileUpload.doFileUpload(getAttachments(), requestDTO);
			List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext().getBean(IChecklistVerificationService.class).fetchAllAttachIdByReferenceId(getWorkflowActionDto().getReferenceId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			getWorkflowActionDto().setAttachementId(attacheMentIds);
		auditService.updateWorkFlowAuditService(getWorkflowActionDto());
		approvalAuditParaDto.setCloserRemarks(getWorkflowActionDto().getComments());
		approvalAuditParaDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		approvalAuditParaDto.setUpdatedDate(new Date());
		approvalAuditParaDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
		auditService.updateRemarks(approvalAuditParaDto);
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
	                .getBean(IWorkflowRequestService.class)
	                .getWorkflowRequestByAppIdOrRefId(null, getApprovalAuditParaDto().getAuditParaCode(),
	                        UserSession.getCurrent().getOrganisation().getOrgid());
		int size = workflowRequest.getWorkFlowTaskList().size();
		approvalAuditParaDto.setAuditDate(getAuditDate());
		if(subUnitClosed != null) {
			approvalAuditParaDto.setSubUnitClosed(getSubUnitClosed());
			auditService.updateAuditParaSubUnitWithID(Long.valueOf(auditParaCode), approvalAuditParaDto.getSubUnitClosed());
			
		}
		
		if(subUnitCompDone != null && subUnitCompPending != null) {
			approvalAuditParaDto.setSubUnitCompDone(getSubUnitCompDone());
			approvalAuditParaDto.setSubUnitCompDone(getSubUnitCompPending());
			auditService.updateAuditParaSubDoneAndPendingWithID(Long.valueOf(auditParaCode), subUnitCompDone, subUnitCompPending);
		}
		
		Boolean subUnitCondition = (approvalAuditParaDto.getSubUnit() == approvalAuditParaDto.getSubUnitClosed());
		if (workflowRequest != null
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) 
		{
	    	 auditService.updateAuditParaStatusAndDatewithID(Long.valueOf(auditParaCode), auditService.loadStatus(IAuditConstants.AUDIT_PARA_STATUS_CLOSE,IAuditConstants.AUDIT_PARA_STATUS_PREFIX), getAuditDate());
			 approvalAuditParaDto.setAuditParaStatus(auditService.loadStatus(IAuditConstants.AUDIT_PARA_STATUS_CLOSE,IAuditConstants.AUDIT_PARA_STATUS_PREFIX));
			 auditService.updateHistoryforWorkflow(approvalAuditParaDto, MainetConstants.MODE_EDIT);		 	
	    }

	    if ((workflowRequest != null 
	    && (workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)||
	    		workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE))
	    && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) && (subUnitCondition == false)) 
	    {
	    	auditService.updateAuditParaStatusAndDatewithID(Long.valueOf(auditParaCode), auditService.loadStatus(IAuditConstants.AUDIT_PARA_STATUS_INTERIM,IAuditConstants.AUDIT_PARA_STATUS_PREFIX), getAuditDate());
	    	approvalAuditParaDto.setAuditParaStatus(auditService.loadStatus(IAuditConstants.AUDIT_PARA_STATUS_INTERIM,IAuditConstants.AUDIT_PARA_STATUS_PREFIX));
	      
	    	//Current Task Name
	    	String taskName = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
	    	// Previous Task Name
	    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
	    	
	    	if (!taskName.equals(taskNamePrevious))
	    	{
	    		auditService.updateAuditWfStatusWithParaID(Long.valueOf(auditParaCode),taskNamePrevious);	
	    		approvalAuditParaDto.setAuditWfStatus(taskNamePrevious);
	    	}	
	    	
	    	auditService.updateHistoryforWorkflow(approvalAuditParaDto, MainetConstants.MODE_EDIT);
	    	//SMS Mail integration
	    	smsAndEmail( approvalAuditParaDto, org);
	    } 
		
	    if ((workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED))) 
	     {	  
	    	auditService.updateAuditParaStatusAndDatewithID(Long.valueOf(auditParaCode), auditService.loadStatus(IAuditConstants.AUDIT_PARA_STATUS_CLOSE,IAuditConstants.AUDIT_PARA_STATUS_PREFIX), getAuditDate());
	    	 auditService.updateAuditParaStatusById(Long.valueOf(auditParaCode), auditService.loadStatus(IAuditConstants.AUDIT_PARA_STATUS_CLOSE,IAuditConstants.AUDIT_PARA_STATUS_PREFIX));
	    	 approvalAuditParaDto.setAuditWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
	    	 auditService.updateAuditWfStatusWithParaID(Long.valueOf(auditParaCode),workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
	    	 auditService.updateAuditParaSubUnitWithID(Long.valueOf(auditParaCode), approvalAuditParaDto.getSubUnitClosed());
	    	 auditService.updateHistoryforWorkflow(approvalAuditParaDto, MainetConstants.MODE_EDIT);
	     }
	     
	   
		return true;
		
	}
	
	private void smsAndEmail(AuditParaEntryDto auditParaEntryDto,Organisation organisation)
	{
		List<Object[]> workFlowTask= auditService.findWorkFlowTaskByRefId(auditParaEntryDto.getAuditParaCode(), auditParaEntryDto.getOrgId());		
		if(workFlowTask!=null) {
		for(Object[] file:workFlowTask) {
			Object obj=file[18];	
			String[] ids=obj.toString().split(MainetConstants.operator.COMMA);			
			for(String id:ids) {	
				Employee emp=iEmployeeService.findEmployeeByIdAndOrgId(Long.parseLong(id), auditParaEntryDto.getOrgId());			    
				SMSAndEmailDTO smdto = new SMSAndEmailDTO();
				smdto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				smdto.setAppNo(auditParaEntryDto.getAuditParaCode());	
				smdto.setMobnumber(emp.getEmpmobno());
				smdto.setEmail(emp.getEmpemail());
				ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
						IAuditConstants.AUDIT_DEPT_CODE, IAuditConstants.AUDIT_PARA_ENTRY_URL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, UserSession.getCurrent().getLanguageId());

			}
			
		}
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
	 public void prepareDocumentsData(AuditParaEntryDto entity) {
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setDepartmentName("AD");
			requestDTO.setIdfId("AUDIT_PARA_ENTRY" + MainetConstants.WINDOWS_SLASH + entity.getAuditParaId());
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

			setCommonFileAttachment(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
					.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
			ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
					.doMasterFileUpload(getCommonFileAttachment(), requestDTO);
		}
	 
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
	public List<TbDepartment> getDepAppList() {
		return depAppList;
	}
	public void setDepAppList(List<TbDepartment> depAppList) {
		this.depAppList = depAppList;
	}
	public String getResolutionComments() {
		return resolutionComments;
	}
	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}
	public Integer getSubUnitClosed() {
		return subUnitClosed;
	}
	public void setSubUnitClosed(Integer subUnitClosed) {
		this.subUnitClosed = subUnitClosed;
	}
	public String getKeyTest() {
		return keyTest;
	}
	public void setKeyTest(String keyTest) {
		this.keyTest = keyTest;
	}
	public String getSubUnitCompDone() {
		return subUnitCompDone;
	}
	public void setSubUnitCompDone(String subUnitCompDone) {
		this.subUnitCompDone = subUnitCompDone;
	}
	public String getSubUnitCompPending() {
		return subUnitCompPending;
	}
	public void setSubUnitCompPending(String subUnitCompPending) {
		this.subUnitCompPending = subUnitCompPending;
	}
	public Boolean getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	

}
