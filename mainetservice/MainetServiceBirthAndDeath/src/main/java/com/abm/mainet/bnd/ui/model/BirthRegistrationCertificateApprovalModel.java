package com.abm.mainet.bnd.ui.model;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class BirthRegistrationCertificateApprovalModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	@Autowired
	private IBirthRegService iBirthRegSevice;

	BirthRegistrationDTO birthRegDto = new BirthRegistrationDTO();


	@Override
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		birthRegDto.setUpdatedBy(employee.getEmpId());
		birthRegDto.setUpdatedDate(new Date());
		birthRegDto.setLgIpMac(employee.getEmppiservername());
		birthRegDto.setLgIpMacUpd(employee.getEmppiservername());
		birthRegDto.setOrgId(employee.getOrganisation().getOrgid());
		birthRegDto.setLangId(langId);
		birthRegDto.setRegAplDate(new Date());
		Long ApplicationNo = issuenceOfBirthCertificateService.saveIssuanceOfBirtCert(birthRegDto);
		birthRegDto.setApplicationId(ApplicationNo.toString());
		this.setSuccessMessage(getAppSession().getMessage("BirthRegDto.SuccessMsgBrCert") + ApplicationNo);
		return true;

	}
	
	
	public String saveBirthApprovalDetails(String ApplicationId, Long orgId, String task)
	{
		RequestDTO requestDTO = new RequestDTO();
		String certificateno=null;
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(BndConstants.BIRTH_DEATH);
        requestDTO.setServiceId(getServiceId());
        getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));//change vi
        getWorkflowActionDto().setDecision(birthRegDto.getBirthRegstatus());
        birthRegDto.setApplicationId(getWorkflowActionDto().getReferenceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(BndConstants.IBC,UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
        birthRegDto.setServiceId(service.getSmServiceId());
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		issuenceOfBirthCertificateService.updateWorkFlowDeathService(getWorkflowActionDto());
		iBirthRegSevice.updateBirthRemark(birthRegDto.getBrId(), birthRegDto.getAuthRemark(),orgId);
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
	                .getBean(IWorkflowRequestService.class)
	                .findByApplicationId(Long.valueOf(birthRegDto.getApplicationId()),getWorkflowActionDto().getWorkflowId());//#143391
		int size = workflowRequest.getWorkFlowTaskList().size();		
		if (workflowRequest != null
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) 
		{     
	        issuenceOfBirthCertificateService.updateBirthWorkFlowStatus(birthRegDto.getBrId(),MainetConstants.WorkFlow.Decision.REJECTED, orgId,birthRegDto.getBrStatus());
	        issuenceOfBirthCertificateService.smsAndEmailApproval(birthRegDto,workflowRequest.getLastDecision());

	    }
	    if (workflowRequest != null 
	    && (workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED) || workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.SUBMITTED))
	    && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) 
	    {
	    	certificateno=issuenceOfBirthCertificateService.updateBirthApproveStatus(birthRegDto,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	//Current Task Name
	    	String taskName = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
	    	//Previous Task Name
	    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
	    	if (!taskName.equals(taskNamePrevious))
	    	{
	    	  issuenceOfBirthCertificateService.updateBirthWorkFlowStatus(birthRegDto.getBrId(), MainetConstants.WorkFlow.Decision.PENDING, orgId,birthRegDto.getBrStatus());
	    	   birthRegDto.setBirthWfStatus(taskNamePrevious);
	    	}	
	    } 
	    if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) 
	     {	
	    	certificateno=issuenceOfBirthCertificateService.updateBirthApproveStatus(birthRegDto,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	birthRegDto.setBirthWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
	    	issuenceOfBirthCertificateService.updateBirthWorkFlowStatus(birthRegDto.getBrId(),MainetConstants.WorkFlow.Decision.APPROVED, orgId,birthRegDto.getBrStatus());
	    	issuenceOfBirthCertificateService.updatNoOfcopyStatus(birthRegDto.getBrId(), orgId, birthRegDto.getBrId(), birthRegDto.getNoOfCopies());
	    	birthRegDto.setBrCertNo(certificateno);
	    	issuenceOfBirthCertificateService.smsAndEmailApproval(birthRegDto,workflowRequest.getLastDecision());
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
	        workflowActionDto.setComments(birthRegDto.getAuthRemark());
	        workflowActionDto.setCreatedDate(new Date());
	        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
	        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
	        workflowActionDto.setIsFinalApproval(false);
	        return workflowActionDto;
	    }

	public BirthRegistrationDTO getBirthRegDto() {
		return birthRegDto;
	}

	public void setBirthRegDto(BirthRegistrationDTO birthRegDto) {
		this.birthRegDto = birthRegDto;
	}

}
