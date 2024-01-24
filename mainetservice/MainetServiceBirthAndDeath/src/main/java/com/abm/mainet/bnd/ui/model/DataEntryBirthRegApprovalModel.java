package com.abm.mainet.bnd.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.ui.validator.BirthRegValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
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
public class DataEntryBirthRegApprovalModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IBirthRegService iBirthRegSevice;

	BirthRegistrationDTO birthRegDto = new BirthRegistrationDTO();

	private List<HospitalMasterDTO> hospitalMasterDTOList;

	private String hospitalList;

	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	private BigDecimal amountToPaid;
	
	public boolean saveBirthApprovalDetails(String ApplicationId, Long orgId, String task)
	{
		RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(BndConstants.BIRTH_DEATH);
        requestDTO.setServiceId(getServiceId());
        getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));//change vi
        getWorkflowActionDto().setDecision(birthRegDto.getBirthRegstatus());
        getWorkflowActionDto().setComments(birthRegDto.getBirthRegremark());
        birthRegDto.setApplicationId(getWorkflowActionDto().getReferenceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(BndConstants.BR,UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
        birthRegDto.setServiceId(service.getSmServiceId());
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		iBirthRegSevice.updateWorkFlowDeathService(getWorkflowActionDto());
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
	                .getBean(IWorkflowRequestService.class)
	                .getWorkflowRequestByAppIdOrRefId(Long.valueOf(birthRegDto.getApplicationId()),null,
	                        UserSession.getCurrent().getOrganisation().getOrgid());
		int size = workflowRequest.getWorkFlowTaskList().size();	
		 
		if (workflowRequest != null
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) 
		{    
		  iBirthRegSevice.updateBirthApproveStatusBR(birthRegDto,null,workflowRequest.getLastDecision());
	      iBirthRegSevice.updateBirthWorkFlowStatusBR(birthRegDto.getBrId(), workflowRequest.getLastDecision(), orgId,BndConstants.BIRTH_STATUS_APPROVED);

	    }
	    if (workflowRequest != null 
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
	    && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) 
	    {
	    	iBirthRegSevice.updateBirthApproveStatusBR(birthRegDto,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	iBirthRegSevice.updateBirthWorkFlowStatusBR(birthRegDto.getBrId(),  MainetConstants.WorkFlow.Decision.APPROVED, orgId,"A"); 
	    	//Current Task Name
	    	String taskName = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
	    	//Previous Task Name
	    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
	    	if (!taskName.equals(taskNamePrevious))
	    	{
	    	    iBirthRegSevice.updateBirthWorkFlowStatusBR(birthRegDto.getBrId(), workflowRequest.getStatus(), orgId,birthRegDto.getBrStatus());
	    	    birthRegDto.setBirthWfStatus(taskNamePrevious);
	    	    
	    	}	
	    } 
	    if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) 
	     {	
	    	   iBirthRegSevice.updateBirthApproveStatusBR(birthRegDto,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	   birthRegDto.setBirthWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
	    	   birthRegDto.setBrStatus("A");
	    	   iBirthRegSevice.updateBirthWorkFlowStatusBR(birthRegDto.getBrId(),workflowRequest.getLastDecision(), orgId,birthRegDto.getBrStatus()); 
	    	   BirthRegistrationDTO saveOnApproval = iBirthRegSevice.saveBirthRegTempDetOnApproval(birthRegDto);
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
	        workflowActionDto.setComments(birthRegDto.getBirthRegremark());
	        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
	        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
	        workflowActionDto.setIsFinalApproval(false);
	       // workflowActionDto.setComments(workflowActionDto.getComments());
	        return workflowActionDto;
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

	public BirthRegistrationDTO getBirthRegDto() {
		return birthRegDto;
	}

	public void setBirthRegDto(BirthRegistrationDTO birthRegDto) {
		this.birthRegDto = birthRegDto;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public BigDecimal getAmountToPaid() {
		return amountToPaid;
	}

	public void setAmountToPaid(BigDecimal amountToPaid) {
		this.amountToPaid = amountToPaid;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

}
