package com.abm.mainet.firemanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.service.IFireCallRegisterService;
import com.abm.mainet.firemanagement.ui.validator.FireCallClosureValidator;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class FireCallClosureModel extends AbstractFormModel {

	private static final long serialVersionUID = 3108541764227266478L;

	@Autowired
	private IFireCallRegisterService fireCallRegisterService;
	
	@Resource
	IFileUploadService fileUpload;

	private FireCallRegisterDTO entity = new FireCallRegisterDTO();

	private String saveMode;

	private List<AttachDocs> attachDocsList = new ArrayList<>();
	
	 private List<DocumentDetailsVO> attachments = new ArrayList<>();
	    private List<AttachDocs> attachDocs = new ArrayList<>();

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

	/**
	 * @return the attachDocsList
	 */
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	/**
	 * @param attachDocsList the attachDocsList to set
	 */
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachDocs> getAttachDocs() {
		return attachDocs;
	}

	public void setAttachDocs(List<AttachDocs> attachDocs) {
		this.attachDocs = attachDocs;
	}

	@Override
	public boolean saveForm() {
		validateBean(entity, FireCallClosureValidator.class);
		if (this.hasValidationErrors()) {
			return false;
		}
		entity.setFireStationsAttendCall(
				entity.getFireStationsAttendCallList().stream().collect(Collectors.joining(",")));
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
   
		entity.setUpdatedBy(createdBy);
		entity.setUpdatedDate(createdDate);
		entity.setLgIpMacUpd(lgIpMac);
		
		FireCallRegisterDTO dto = fireCallRegisterService.saveFireCallcloser(entity);
		
		FileUploadDTO requestDTO = new FileUploadDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setIdfId(Constants.FIRE_CALL_CLOSURE + MainetConstants.WINDOWS_SLASH +dto.getClosureId()+MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(Constants.FIRE_DRPT_CODE);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
        fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
		
		//initiate workflow process start
		getWorkflowActionDto().setApplicationId(dto.getClosureId());
		getWorkflowActionDto().setReferenceId(entity.getCmplntNo()+Constants.str+dto.getClosureId().toString());

		ServiceMaster service = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class).getServiceMasterByShortCode("FCC", orgId);

		WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
						service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null, null, null, null,
						null);
		ApplicationContextProvider.getApplicationContext().getBean(IFireCallRegisterService.class)
				.initiateWorkFlowWorksService(this.prepareWorkFlowTaskAction(), workFlowMas,
						"FireCallClosure.html", MainetConstants.FlagA);
		//initiate workflow process end
		
		setSuccessMessage(getAppSession().getMessage("FireCallRegisterDTO.form.closed" + "",
				new Object[] { entity.getCmplntNo()+Constants.str+entity.getClosureId().toString() }));

 		return true;
	}
	
	public WorkflowTaskAction prepareWorkFlowTaskAction() {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setReferenceId(entity.getCmplntNo()+Constants.str+entity.getClosureId().toString());
	//	taskAction.setApplicationId(entity.getClosureId());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setDecision("SUBMITED");
		return taskAction;
	}	
	
	public void prepareContractDocumentsData(FireCallRegisterDTO entity) {
		RequestDTO requestDTO = new RequestDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgId);
		requestDTO.setStatus(MainetConstants.FlagA);

		requestDTO.setDepartmentName("FM");

		requestDTO.setIdfId("FIRE_CALL_REGISTER" + MainetConstants.WINDOWS_SLASH + entity.getCmplntNo());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		setCommonFileAttachment(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.doMasterFileUpload(getCommonFileAttachment(), requestDTO);
	}
	
	
}






