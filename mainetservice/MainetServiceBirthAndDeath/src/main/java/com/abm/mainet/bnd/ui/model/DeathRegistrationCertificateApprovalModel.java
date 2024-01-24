package com.abm.mainet.bnd.ui.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.bnd.service.IssuenceOfDeathCertificateService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
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
@Scope(value =WebApplicationContext.SCOPE_SESSION)
public class DeathRegistrationCertificateApprovalModel extends AbstractFormModel{


	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IssuenceOfDeathCertificateService iDeathRegistrationService;
	
	@Autowired
	private IDeathRegistrationService iDeathregService;
	
	@Autowired
	private IdeathregCorrectionService ideathregCorrectionService;
	
	TbDeathregDTO tbDeathregDTO = new  TbDeathregDTO();
	
	private List<HospitalMasterDTO> hospitalMasterDTOList;
	
	private String hospitalList;
	
	@Override
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		
		tbDeathregDTO.setUpdatedBy(employee.getEmpId());
		tbDeathregDTO.setUpdatedDate(new Date());
		tbDeathregDTO.setLmoddate(new Date());
		tbDeathregDTO.setLgIpMac(employee.getEmppiservername());
		tbDeathregDTO.setLgIpMacUpd(employee.getEmppiservername());
		tbDeathregDTO.setOrgId(employee.getOrganisation().getOrgid());
		tbDeathregDTO.setLangId(langId);
		tbDeathregDTO.setCpdAgeperiodId(10L);
		tbDeathregDTO.setDrRegdate(new Date());
		tbDeathregDTO.setCpdDeathcauseId(1L);
		tbDeathregDTO.setRegAplDate(new Date());
		
		Map<String,Object> applicationNo = iDeathRegistrationService.saveIssuanceDeathCertificateDetail(tbDeathregDTO);
		tbDeathregDTO.setApplicationNo(applicationNo.get("ApplicationId").toString());
		this.setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.SuccessMsgDrCert") + applicationNo.get("ApplicationId"));
		return true;
	}
	public String saveDeathhApprovalDetails(String ApplicationId, Long orgId, String task)
	{
		RequestDTO requestDTO = new RequestDTO();
		String certificateNo=null;
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(BndConstants.BIRTH_DEATH);
        requestDTO.setServiceId(getServiceId());
        getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));//change vi
        getWorkflowActionDto().setDecision(tbDeathregDTO.getDeathRegstatus());
        tbDeathregDTO.setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(BndConstants.IDC,UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
        tbDeathregDTO.setServiceId(service.getSmServiceId());
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		iDeathRegistrationService.updateWorkFlowDeathService(getWorkflowActionDto());
		ideathregCorrectionService.updateDeathRemark(tbDeathregDTO.getDrId(), tbDeathregDTO.getAuthRemark(),orgId);
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
	                .getBean(IWorkflowRequestService.class)
	                .getWorkflowRequestByAppIdOrRefId(Long.valueOf(tbDeathregDTO.getApplicationId()),null,
	                        UserSession.getCurrent().getOrganisation().getOrgid());
		int size = workflowRequest.getWorkFlowTaskList().size();		
		if (workflowRequest != null
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) 
		{     
	   
	   iDeathRegistrationService.updateBirthWorkFlowStatus(tbDeathregDTO.getDrId(), MainetConstants.WorkFlow.Decision.REJECTED, orgId);
	   iDeathRegistrationService.smsAndEmailApproval(tbDeathregDTO,workflowRequest.getLastDecision());

	    }
	    if (workflowRequest != null 
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
	    && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) 
	    {
	    	certificateNo=iDeathRegistrationService.updateDeathApproveStatus(tbDeathregDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	//Current Task Name
	    	String taskName = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
	    	//Previous Task Name
	    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
	    	if (!taskName.equals(taskNamePrevious))
	    	{
	    	   iDeathRegistrationService.updateBirthWorkFlowStatus(tbDeathregDTO.getDrId(), MainetConstants.WorkFlow.Decision.PENDING, orgId);
	    	  //birthRegDto.setBirthWfStatus(taskNamePrevious);
	    	}	
	    } 
	    if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) 
	     {	
	    	certificateNo=iDeathRegistrationService.updateDeathApproveStatus(tbDeathregDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	 //birthRegDto.setBirthWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
	       iDeathRegistrationService.updateBirthWorkFlowStatus(tbDeathregDTO.getDrId(),MainetConstants.WorkFlow.Decision.APPROVED, orgId);  
	       iDeathregService.updatNoOfcopyStatus(tbDeathregDTO.getDrId(), orgId, tbDeathregDTO.getDrId(), tbDeathregDTO.getNumberOfCopies());
	       tbDeathregDTO.setDrCertNo(certificateNo);
	       iDeathRegistrationService.smsAndEmailApproval(tbDeathregDTO,workflowRequest.getLastDecision());
	     }
		return certificateNo;
 }

	
	 private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
	        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	        workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
	        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
	        workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
	        workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
	        workflowActionDto.setDateOfAction(new Date());
	        workflowActionDto.setCreatedDate(new Date());
	        workflowActionDto.setComments(tbDeathregDTO.getAuthRemark());
	        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
	        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
	        workflowActionDto.setIsFinalApproval(false);
	        return workflowActionDto;
	    }
	
	
	
	
	
	
	
	
	
	
	
	/*public IDeathRegistrationService getiDeathRegistrationService() {
		return iDeathRegistrationService;
	}

	public void setiDeathRegistrationService(IDeathRegistrationService iDeathRegistrationService) {
		this.iDeathRegistrationService = iDeathRegistrationService;
	}*/

	public TbDeathregDTO getTbDeathregDTO() {
		return tbDeathregDTO;
	}

	public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
		this.tbDeathregDTO = tbDeathregDTO;
	}

	public List<HospitalMasterDTO> getHospitalMasterDTOList() {
		return hospitalMasterDTOList;
	}

	public List<HospitalMasterDTO> setHospitalMasterDTOList(List<HospitalMasterDTO> hospitalMasterDTOList) {
		return this.hospitalMasterDTOList = hospitalMasterDTOList;
	}

	public String getHospitalList() {
		return hospitalList;
	}

	public void setHospitalList(String hospitalList) {
		this.hospitalList = hospitalList;
	}

	
	
}
