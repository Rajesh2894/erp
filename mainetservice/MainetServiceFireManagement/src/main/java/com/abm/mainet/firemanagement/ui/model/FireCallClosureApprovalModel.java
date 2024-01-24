package com.abm.mainet.firemanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.service.IFireCallRegisterService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;


@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class FireCallClosureApprovalModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	@Autowired
	private IFireCallRegisterService fireCallRegisterService;
	

	@Autowired
	private IFileUploadService fileUpload;

	private FireCallRegisterDTO entity = new FireCallRegisterDTO();

	private String saveMode;

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	
	private List<AttachDocs> attachDocs = new ArrayList<>();
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private IEmployeeService iEmployeeService;


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

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}
	
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public List<AttachDocs> getAttachDocs() {
		return attachDocs;
	}

	public void setAttachDocs(List<AttachDocs> attachDocs) {
		this.attachDocs = attachDocs;
	}

	public boolean saveFireCallClosureApprovalDetails(String ApplicationId, Long orgId, String task) {
		RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName("FM");
        requestDTO.setServiceId(getServiceId());
        
        List<FireCallRegisterDTO> closerDto = fireCallRegisterService.findByCloserCompId(Long.valueOf(ApplicationId), orgId);
        entity.setClosureId(closerDto.get(0).getClosureId());
        entity.setCmplntNo(closerDto.get(0).getCmplntNo());
        entity.setCmplntId(closerDto.get(0).getCmplntId());
        
        getWorkflowActionDto().setReferenceId(getWorkflowActionDto().getReferenceId());
        getWorkflowActionDto().setApplicationId(entity.getClosureId());
		getWorkflowActionDto().setDecision(entity.getCallRegClosureStatus());
        
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        
        getWorkflowActionDto().setReferenceId(getWorkflowActionDto().getReferenceId());
              
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode("FCC",UserSession.getCurrent().getOrganisation().getOrgid());
        
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());

        prepareWorkFlowTaskAction(getWorkflowActionDto());
		
		fireCallRegisterService.updateWorkFlowFireService(getWorkflowActionDto());
	
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(null, entity.getCmplntNo()+Constants.str+entity.getClosureId().toString(), UserSession.getCurrent().getOrganisation().getOrgid());
		
		int size = workflowRequest.getWorkFlowTaskList().size();
		
		//boolean lastLevel = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
				//.isLastTaskInCheckerTaskListGroup(getWorkflowActionDto().getTaskId());  #82093
		
		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {  
			
			fireCallRegisterService.updateComplainStatus(String.valueOf(entity.getClosureId()), "SB");
			fireCallRegisterService.updateFireApproveStatus(entity, null, workflowRequest.getLastDecision());
			fireCallRegisterService.updatecomplaintStatusInSB(entity.getCmplntId(), orgId, "SB");
			if(entity.getCallRegClosureRemark()!=null) {
				fireCallRegisterService.updateFinalCallClosureComment(entity.getCmplntNo(), entity.getCallRegClosureRemark(), orgId);
			}
	    }
		
	    if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
	    		&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
	    	
	    	fireCallRegisterService.updateFireApproveStatus(entity, null, workflowRequest.getLastDecision());
	    	
	    	//Current Task Name
	    	String taskName = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
	    	//Previous Task Name
	    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
	    	if (!taskName.equals(taskNamePrevious)) {
	    		fireCallRegisterService.updateFireWorkFlowStatus(entity.getClosureId(), MainetConstants.WorkFlow.Status.PENDING, orgId);
	    		entity.setFireWfStatus(taskNamePrevious); 
	    	}	
	    }
	    
	    //if (lastLevel == true) {  #82093
			if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
					&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
				
				fireCallRegisterService.updateComplainStatus(String.valueOf(entity.getClosureId()), MainetConstants.FlagA); //new line
				fireCallRegisterService.updateFireApproveStatus(entity, workflowRequest.getLastDecision(), workflowRequest.getStatus()); 
				fireCallRegisterService.updateFireWorkFlowStatus(entity.getClosureId(), MainetConstants.WorkFlow.Status.CLOSED, orgId); 
				entity.setFireWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName()); 
				fireCallRegisterService.getcloserDataAfterAprovalAndSaveInCallRegister(entity.getClosureId(), orgId);
				if(entity.getCallRegClosureRemark()!=null) {
					fireCallRegisterService.updateFinalCallClosureComment(entity.getCmplntNo(), entity.getCallRegClosureRemark(), orgId);
				}
				sendSmsAndMail(closerDto);		
			}
		//}
		return true;
	}
	
	public void sendSmsAndMail(List<FireCallRegisterDTO> closerDto) {
		
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		Long depId = departmentService.getDepartmentIdByDeptCode(Constants.FM);
		List<Long> employeIds = fireCallRegisterService.getEmpId(depId, organisation.getOrgid());
		String[] ids=closerDto.get(0).getFireStationsAttendCall().split(MainetConstants.operator.COMMA);
		String fullFireStation = new String() ;
		for(String id:ids) {
			String fireStation=CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(id), organisation).getLookUpDesc();
			fullFireStation+=fireStation+MainetConstants.WHITE_SPACE;
		}
		StringBuffer  builder=new StringBuffer ();
		builder.append(this.getEntity().getCallerMobileNo());
		String mobileList = iEmployeeService.getEmpDetailListByEmpIdList(employeIds, organisation.getOrgid()).stream()
				.map(Employee::getEmpmobno).collect(Collectors.joining(","));
		builder.append(MainetConstants.operator.COMMA+mobileList);
		String[] mobileNos=builder.toString().split(MainetConstants.operator.COMMA);
		
		for(String num:mobileNos) {
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
		
		smdto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		smdto.setAppNo(entity.getCmplntNo());
		smdto.setDate(this.getEntity().getCallAttendDate());
		
		smdto.setAgencyName(fullFireStation);
		
			smdto.setMobnumber(num);
			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
					Constants.FM, "FireCallClosureApproval.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, UserSession.getCurrent().getLanguageId());
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
	        //workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
	        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
	        workflowActionDto.setApplicationId(getWorkflowActionDto().getApplicationId());
	        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
	        workflowActionDto.setIsFinalApproval(false);
	        workflowActionDto.setComments(entity.getCallRegClosureRemark());
	        workflowActionDto.setDecision(entity.getCallRegClosureStatus());
	        return workflowActionDto;
	    }
	
	
	

}
