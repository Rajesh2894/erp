/*package com.abm.mainet.firemanagement.ui.model;

import java.math.BigDecimal;
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
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
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
import com.abm.mainet.firemanagement.dto.PetrolRequisitionDTO;
import com.abm.mainet.firemanagement.service.IFireCallRegisterService;
import com.abm.mainet.firemanagement.service.IPetrolRequisitionService;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class PetrolRequisitionApprovalModel extends AbstractFormModel {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IPetrolRequisitionService iPetrolRequisitionService;

	PetrolRequisitionDTO entity = new PetrolRequisitionDTO();
	
	PetrolRequisitionDTO petrolRequisitionDTO1 = new PetrolRequisitionDTO();

	private List<PetrolRequisitionDTO> petroList;
	
	private List<GenVehicleMasterDTO> genVehicleMasterList;

	private String vehiclesList;

	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	private BigDecimal amountToPaid;
	
	//private FireCallRegisterDTO entity = new FireCallRegisterDTO();
	
	private List<PetrolRequisitionDTO> petrolRequisitionDTO = new ArrayList<>();

	@Autowired
	private IFireCallRegisterService fireCallRegisterService;
	

	@Override
	public boolean saveForm() {

		return prepareSaveSanctionDetails();
	}
	
	public boolean prepareSaveSanctionDetails() {

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgid);
		requestDTO.setDepartmentName("FM");
		requestDTO.setServiceId(getServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("PRF", orgid);
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());

		List<DocumentDetailsVO> docs = new ArrayList<>();
		DocumentDetailsVO document = new DocumentDetailsVO();
		document.setDocumentSerialNo(1L);
		docs.add(document);
		
		//setAttachments(fileUpload.prepareFileUpload(docs));
		
		//fileUpload.doFileUpload(getAttachments(), requestDTO);
		
		List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext().getBean(IChecklistVerificationService.class).fetchAllAttachIdByReferenceId(getWorkflowActionDto().getReferenceId(),orgid);
		
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
				.getWorkflowRequestByAppIdOrRefId(null, null, orgid);

		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			fireCallRegisterService.updateComplainStatus(getWorkflowActionDto().getReferenceId(),MainetConstants.FlagR);
		}
			
		if (lastLevel == true) {
			if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED) && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
				fireCallRegisterService.updateComplainStatus(getWorkflowActionDto().getReferenceId(),MainetConstants.FlagA);
			}
		}

		setSuccessMessage(getAppSession().getMessage("FireCallRegisterDTO.form.save" + "", 	new Object[] { fireCallRegisterDTO.getCmplntNo() }));
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
		workflowActionDto.setApplicationId(getWorkflowActionDto().getApplicationId());
		workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		workflowActionDto.setDecision(entity.getPetrolRegstatus());
		return workflowActionDto;

	}
	

	public IPetrolRequisitionService getiPetrolRequisitionService() {
		return iPetrolRequisitionService;
	}

	public void setiPetrolRequisitionService(IPetrolRequisitionService iPetrolRequisitionService) {
		this.iPetrolRequisitionService = iPetrolRequisitionService;
	}

	public List<PetrolRequisitionDTO> getPetroList() {
		return petroList;
	}

	public void setPetroList(List<PetrolRequisitionDTO> petroList) {
		this.petroList = petroList;
	}

	public String getVehiclesList() {
		return vehiclesList;
	}

	public void setVehiclesList(String vehiclesList) {
		this.vehiclesList = vehiclesList;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public BigDecimal getAmountToPaid() {
		return amountToPaid;
	}

	public void setAmountToPaid(BigDecimal amountToPaid) {
		this.amountToPaid = amountToPaid;
	}

	public List<GenVehicleMasterDTO> getGenVehicleMasterList() {
		return genVehicleMasterList;
	}

	public void setGenVehicleMasterList(List<GenVehicleMasterDTO> genVehicleMasterList) {
		this.genVehicleMasterList = genVehicleMasterList;
	}
	
	public PetrolRequisitionDTO getEntity() {
		return entity;
	}

	public void setEntity(PetrolRequisitionDTO entity) {
		this.entity = entity;
	}
	
	public List<PetrolRequisitionDTO> getPetrolRequisitionDTO() {
		return petrolRequisitionDTO;
	}

	public void setPetrolRequisitionDTO(List<PetrolRequisitionDTO> petrolRequisitionDTO) {
		this.petrolRequisitionDTO = petrolRequisitionDTO;
	}

	public void savePetrolCallClosureApprovalDetails(Long requestId, long orgid, String taskName) {

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgid);
		requestDTO.setDepartmentName("FM");
		requestDTO.setServiceId(getServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		
		

		
		
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("PRF", orgid);
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
		
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		
		iPetrolRequisitionService.updateWorkFlowFireService(getWorkflowActionDto());
		//fireCallRegisterService.updateWorkFlowFireService(getWorkflowActionDto());
		
		//ApplicationContextProvider.getApplicationContext().getBean(IFireCallRegisterService.class).updateWorkFlowWorksService(prepareWorkFlowTaskAction(this.getWorkflowActionDto()));
		//ApplicationContextProvider.getApplicationContext().getBean(IPetrolRequisitionService.class).updateWorkFlowWorksService(prepareWorkFlowTaskAction(this.getWorkflowActionDto()));

		boolean lastLevel = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class).isLastTaskInCheckerTaskListGroup(getWorkflowActionDto().getTaskId());
		
		
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(entity.getClosureId(), null, UserSession.getCurrent().getOrganisation().getOrgid());
                
		
		//PetrolRequisitionDTO PetrolRegisterDTO = ApplicationContextProvider.getApplicationContext().getBean(IPetrolRequisitionService.class).findByComplainNo(getWorkflowActionDto().getReferenceId(), orgid);
		
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(entity.getRequestId(), null, orgid);
		
		int size = workflowRequest.getWorkFlowTaskList().size();	
		
		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {     
			//certificateNo=iDeathRegistrationService.updateDeathApproveStatus(entity.getRequestId(),null,workflowRequest.getLastDecision());
			//fireCallRegisterService.updateComplainStatus(getWorkflowActionDto().getReferenceId(), MainetConstants.FlagR);
			iPetrolRequisitionService.updatePetrolApproveStatus(entity ,null,workflowRequest.getLastDecision());
			iPetrolRequisitionService.updateComplainStatus(entity.getRequestId(), MainetConstants.FlagR); //(getWorkflowActionDto().getReferenceId(), MainetConstants.FlagR);
	    }
		
	    if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED) && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
	    	//certificateNo=iDeathRegistrationService.updateDeathApproveStatus(tbDeathregDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	//Current Task Name
	    	String taskName1 = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
	    	//Previous Task Name
	    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
	    	if (!taskName1.equals(taskNamePrevious)) {
	    	   //iDeathRegistrationService.updateBirthWorkFlowStatus(entity.getRequestId(), MainetConstants.WorkFlow.Decision.PENDING, orgId);
	    	   iPetrolRequisitionService.updatePetrolWorkFlowStatus(entity.getRequestId(), MainetConstants.WorkFlow.Decision.PENDING, orgId);
	    	   entity.setPetrolWfStatus(taskNamePrevious);
	    	}	
	    } 
	    if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED) && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {	
	    	//certificateNo=iDeathRegistrationService.updateDeathApproveStatus(tbDeathregDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	iPetrolRequisitionService.updatePetrolWorkFlowStatus(entity.getRequestId(),MainetConstants.WorkFlow.Status.CLOSED, orgId); 
	    	entity.setPetrolWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
	    }
	    
	    if (lastLevel == true) {
			if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED) && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
				//fireCallRegisterService.updateComplainStatus(getWorkflowActionDto().getReferenceId(),MainetConstants.FlagA);
				iPetrolRequisitionService.updateComplainStatus(entity.getRequestId(), MainetConstants.FlagA);
				iPetrolRequisitionService.updatePetrolStatus(getWorkflowActionDto().getApplicationId(),MainetConstants.FlagA);
			}
		}
	    
		
	}


	
	
	
}
*/