package com.abm.mainet.additionalservices.ui.model;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.additionalservices.constant.NOCForBuildPermissionConstant;
import com.abm.mainet.additionalservices.dto.CFCNursingHomeInfoDTO;
import com.abm.mainet.additionalservices.dto.CFCSonographyMastDto;
import com.abm.mainet.additionalservices.service.CFCNursingHomeService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class NursingHomeApprovalModel extends AbstractFormModel {

	@Autowired
	private CFCNursingHomeService cfcNursingHomeService;

	@Resource
	IFileUploadService fileUpload;
	
	@Autowired
	private TbCfcApplicationMstService tbCFCApplicationMst;

	@Resource
	private TbServicesMstService tbServicesMstService;

	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	private String saveMode;
	private CFCNursingHomeInfoDTO cfcNuringHomeInfoDto;
	
	private CFCSonographyMastDto cfcSonographyMastDto;

	private TbCfcApplicationMst cfcApplicationMst;

	private String serviceCode;

	public TbCfcApplicationMst getCfcApplicationMst() {
		return cfcApplicationMst;
	}

	public void setCfcApplicationMst(TbCfcApplicationMst cfcApplicationMst) {
		this.cfcApplicationMst = cfcApplicationMst;
	}

	public CFCNursingHomeInfoDTO getCfcNuringHomeInfoDto() {
		return cfcNuringHomeInfoDto;
	}

	public void setCfcNuringHomeInfoDto(CFCNursingHomeInfoDTO cfcNuringHomeInfoDto) {
		this.cfcNuringHomeInfoDto = cfcNuringHomeInfoDto;
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

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private String scrutinyViewMode;
	private List<TbLocationMas> locationMasList;

	public List<TbLocationMas> getLocationMasList() {
		return locationMasList;
	}

	public void setLocationMasList(List<TbLocationMas> locationMasList) {
		this.locationMasList = locationMasList;
	}

	public String getScrutinyViewMode() {
		return scrutinyViewMode;
	}

	public void setScrutinyViewMode(String scrutinyViewMode) {
		this.scrutinyViewMode = scrutinyViewMode;
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

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	
	public CFCSonographyMastDto getCfcSonographyMastDto() {
		return cfcSonographyMastDto;
	}

	public void setCfcSonographyMastDto(CFCSonographyMastDto cfcSonographyMastDto) {
		this.cfcSonographyMastDto = cfcSonographyMastDto;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public boolean saveApprovalDetails(String valueOf, long orgid, String taskName, Long taskId, Long serviceId) {

		// Boolean checkFinalAproval =
		// cfcNursingHomeService.checkEmployeeRole(UserSession.getCurrent());
		/*
		 * if (checkFinalAproval == true) {
		 * setAttachments(ApplicationContextProvider.getApplicationContext().getBean(
		 * IFileUploadService.class) .setFileUploadMethod(new
		 * ArrayList<DocumentDetailsVO>()));
		 * 
		 * if (attachments.isEmpty() || attachments == null) { addValidationError(
		 * ApplicationSession.getInstance().getMessage(
		 * "NOCBuildingPermission.validation.document")); return false; }
		 * 
		 * else { FileUploadDTO uploadDTO = new FileUploadDTO();
		 * uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		 * uploadDTO.setStatus(MainetConstants.FlagA);
		 * uploadDTO.setDepartmentName(NOCForBuildPermissionConstant.COM);
		 * uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		 * uploadDTO.setIdfId(valueOf);
		 * cfcNuringHomeInfoDto.setOrgId(UserSession.getCurrent().getOrganisation().
		 * getOrgid());
		 * 
		 * fileUpload.doMasterFileUpload(getAttachments(), uploadDTO); }
		 * 
		 * }
		 */

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDepartmentName(NOCForBuildPermissionConstant.COM);
		requestDTO.setServiceId(getServiceId());
		getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));// change vi
		getWorkflowActionDto().setDecision(cfcNuringHomeInfoDto.getBirthRegstatus());
		getWorkflowActionDto().setComments(cfcNuringHomeInfoDto.getBirthRegremark());
		cfcNuringHomeInfoDto.setApmApplicationId(Long.parseLong(getWorkflowActionDto().getReferenceId()));
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		/*
		 * ServiceMaster service =
		 * ApplicationContextProvider.getApplicationContext().getBean(
		 * ServiceMasterService.class) .getServiceMasterByShortCode(,
		 * UserSession.getCurrent().getOrganisation().getOrgid());
		 */
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMaster(serviceId, UserSession.getCurrent().getOrganisation().getOrgid());

		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
		cfcNuringHomeInfoDto.setSmServiceId(service.getSmServiceId());
		getWorkflowActionDto().setServiceId(service.getSmServiceId());
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		cfcNursingHomeService.updateWorkFlowService(getWorkflowActionDto());

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
        // #129863
		String shortCode = tbServicesMstService.getServiceShortDescByServiceId(workflowActionDto.getServiceId());
		if(shortCode.equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)) {
			workflowActionDto.setComments(cfcSonographyMastDto.getBirthRegremark());
		}else {
		workflowActionDto.setComments(cfcNuringHomeInfoDto.getBirthRegremark());
		}
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		// workflowActionDto.setComments(workflowActionDto.getComments());
		return workflowActionDto;
	}

	@Override
	public void populateApplicationData(long applicationId) {

		setApmApplicationId(applicationId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TbCfcApplicationMst cfcApplicationMst = tbCFCApplicationMst.findById(applicationId);
        TbServicesMst master = tbServicesMstService.findById(cfcApplicationMst.getSmServiceId());	
		this.setCfcApplicationMst(cfcApplicationMst);
		this.setLocationMasList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
				.getlocationByDeptId(master.getTbDepartment().getDpDeptid(), orgId));

		this.setScrutinyViewMode(MainetConstants.FlagV);
         // #129863
		if(master.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)) {
			CFCSonographyMastDto mastDto = cfcNursingHomeService.findDetByApplicationId(applicationId);
			this.setCfcSonographyMastDto(mastDto);
			this.setServiceCode(master.getSmShortdesc());
		}else {
		CFCNursingHomeInfoDTO cfcNuringHomeInfoDto = cfcNursingHomeService.findByApplicationId(applicationId);
		this.setCfcNuringHomeInfoDto(cfcNuringHomeInfoDto);
		this.setServiceCode(master.getSmShortdesc());
		}

	
	}
       // #129863
	 public boolean saveSonoRegApprvalDet(String valueOf, long orgid, String taskName, Long taskId, Long serviceId) {
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDepartmentName(NOCForBuildPermissionConstant.COM);
		requestDTO.setServiceId(getServiceId());
		getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));// change vi
		getWorkflowActionDto().setDecision(cfcSonographyMastDto.getBirthRegstatus());
		getWorkflowActionDto().setComments(cfcSonographyMastDto.getBirthRegremark());
		cfcSonographyMastDto.setApmApplicationId(Long.parseLong(getWorkflowActionDto().getReferenceId()));
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMaster(serviceId, UserSession.getCurrent().getOrganisation().getOrgid());

		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
		cfcSonographyMastDto.setSmServiceId(service.getSmServiceId());
		getWorkflowActionDto().setServiceId(service.getSmServiceId());
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		cfcNursingHomeService.updateWorkFlowService(getWorkflowActionDto());
		return true;
	}
}
