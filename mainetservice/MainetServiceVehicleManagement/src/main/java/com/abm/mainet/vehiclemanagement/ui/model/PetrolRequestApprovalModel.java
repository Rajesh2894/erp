package com.abm.mainet.vehiclemanagement.ui.model;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.PetrolRequisitionDTO;
import com.abm.mainet.vehiclemanagement.service.IPetrolRequisitionService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class PetrolRequestApprovalModel extends AbstractFormModel {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IPetrolRequisitionService iPetrolRequisitionService;

	PetrolRequisitionDTO entity = new PetrolRequisitionDTO();
	
	private List<PetrolRequisitionDTO> petroList;
	
	private List<GenVehicleMasterDTO> genVehicleMasterList;

	private String vehiclesList;

	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	private BigDecimal amountToPaid;
	
	private List<DocumentDetailsVO> documents = new ArrayList<>();

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
	private List<PetrolRequisitionDTO> petrolRequisitionDTO = new ArrayList<>();

	@Autowired
	private IPetrolRequisitionService   petrolRequisitionService;
	
	
	@Autowired
	IFileUploadService fileUpload;
	
	@Resource
	private TbAcVendormasterService tbAcVendormasterService;


	@Override
	public boolean saveForm() {

		return prepareSaveSanctionDetail();
	}
	
	public boolean prepareSaveSanctionDetail() {

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgid);
		//requestDTO.setDepartmentName("FM");
		requestDTO.setServiceId(getServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		

		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("FRF", orgid);
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());

		List<DocumentDetailsVO> docs = new ArrayList<>();
		DocumentDetailsVO document = new DocumentDetailsVO();
		document.setDocumentSerialNo(1L);
		docs.add(document);
		
		List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext().getBean(IChecklistVerificationService.class).fetchAllAttachIdByReferenceId(getWorkflowActionDto().getReferenceId(),orgid);
		
		prepareWorkFlowTaskActions(getWorkflowActionDto());
		
		getWorkflowActionDto().setAttachementId(attacheMentIds);

		ApplicationContextProvider.getApplicationContext().getBean(IPetrolRequisitionService.class)
				.updateWorkFlowWorksService(prepareWorkFlowTaskActions(this.getWorkflowActionDto()));

		boolean lastLevel = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
				.isLastTaskInCheckerTaskListGroup(getWorkflowActionDto().getTaskId());

		PetrolRequisitionDTO petrolRequisitionDTO = ApplicationContextProvider.getApplicationContext()
				.getBean(IPetrolRequisitionService.class)
				.findByComplainNo(getWorkflowActionDto().getReferenceId(), orgid);
	
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(null, null, orgid);

		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			petrolRequisitionService.updatePetrolStatus(getWorkflowActionDto().getReferenceId(),MainetConstants.FlagR);
		}
			
		if (lastLevel == true) {
			if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED) && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
				petrolRequisitionService.updatePetrolStatus(getWorkflowActionDto().getReferenceId(),MainetConstants.FlagA);
			}
		}

		setSuccessMessage(getAppSession().getMessage("PetrolRequisitionDTO.form.save" + "", 	new Object[] { petrolRequisitionDTO.getRequestId() }));
		return true;

	}
	

	private WorkflowTaskAction prepareWorkFlowTaskActions(WorkflowTaskAction workflowActionDto) {
		
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
		 if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
		workflowActionDto.setComments(entity.getPetrolRegRemark());
		workflowActionDto.setDecision(entity.getPetrolRegstatus());
		 }
		return workflowActionDto;

	}
	
	public void savePetrolCallClosureApprovalDetails(Long requestId, long orgid, String taskName) {

		RequestDTO requestDTO = new RequestDTO();
		
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgid);
	//	requestDTO.setDepartmentName("FM");
		requestDTO.setServiceId(getServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		
		requestDTO.setLangId((long) UserSession.getCurrent().getLanguageId());
		
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("FRF", orgid);
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
		entity.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		entity.setLangId((long) UserSession.getCurrent().getLanguageId());
		entity.seteName(UserSession.getCurrent().getEmployee().getEmpname());
		
		
		prepareWorkFlowTaskActions(getWorkflowActionDto());
		
		iPetrolRequisitionService.updateWorkFlowPetrolService(getWorkflowActionDto());
		
		boolean lastLevel = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class).isLastTaskInCheckerTaskListGroup(getWorkflowActionDto().getTaskId());
		
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(null, entity.getFuelReqNo(), orgid);
		
		int size = workflowRequest.getWorkFlowTaskList().size();	
		
		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {     
			iPetrolRequisitionService.updatePetrolApproveStatus(entity ,null,workflowRequest.getLastDecision());
			iPetrolRequisitionService.updateComplainStatus(entity.getRequestId(), MainetConstants.FlagR);
		
			
	    }
		
	    if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED) && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
	    	//Current Task Name
	    	String taskName1 = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
	    	//Previous Task Name
	    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
	    	if (!taskName1.equals(taskNamePrevious)) {
	    		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
	    			iPetrolRequisitionService.updatePetrolWorkFlowStatus(entity.getRequestId(), MainetConstants.WorkFlow.Decision.PENDING, orgId, entity.getPuId());
	    		}
	    		else{
	    			iPetrolRequisitionService.updatePetrolWorkFlowStatus(entity.getRequestId(), MainetConstants.WorkFlow.Decision.PENDING, orgId);
	    		}
	    	    entity.setPetrolWfStatus(taskNamePrevious);
	    	}	
	    } 
	    
	    if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED) && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {	
	    	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
	    		iPetrolRequisitionService.updatePetrolWorkFlowStatus(entity.getRequestId(),MainetConstants.WorkFlow.Status.CLOSED, orgId, entity.getPuId()); 
	    	}
	    	else{
	    		iPetrolRequisitionService.updatePetrolWorkFlowStatus(entity.getRequestId(),MainetConstants.WorkFlow.Status.CLOSED, orgId); 
	    	}
	    	entity.setPetrolWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
	    }
	    
	    if (lastLevel == true) {
			if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED) && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
				iPetrolRequisitionService.updateComplainStatus(entity.getRequestId(), MainetConstants.FlagA);
				iPetrolRequisitionService.updatePetrolStatus(getWorkflowActionDto().getApplicationId(),MainetConstants.FlagA);
			}
		}
		
	}
	
	public void uploadPetrolRequestDoc() {
		List<Long> removeFileById = null;
		String fileId = this.getEntity().getRemoveFileById();
		if (fileId != null && !fileId.isEmpty()) {
			removeFileById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				removeFileById.add(Long.valueOf(fields));
			}
		}
		if (removeFileById != null && !removeFileById.isEmpty()) {
			tbAcVendormasterService.updateUploadedFileDeleteRecords(removeFileById, this.getEntity().getUpdatedBy());
		}
		FileUploadDTO requestDTO = new FileUploadDTO();
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setStatus(MainetConstants.FlagA);
		requestDTO.setIdfId(Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH + this.getEntity().getRequestId().toString());
		requestDTO.setDepartmentName(Constants.SHORT_CODE);
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		List<DocumentDetailsVO> dto = getDocuments();
		setDocuments(fileUpload.setFileUploadMethod(getDocuments()));
		this.setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
		fileUpload.doMasterFileUpload(this.getAttachments(), requestDTO);
		int i = 0;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			getDocuments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
			i++;
		}		
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

	public List<DocumentDetailsVO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDetailsVO> documents) {
		this.documents = documents;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}
	
	
}
