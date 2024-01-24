package com.abm.mainet.additionalservices.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.additionalservices.constant.NOCForBuildPermissionConstant;
import com.abm.mainet.additionalservices.dto.NOCForBuildingPermissionDTO;
import com.abm.mainet.additionalservices.service.NOCForBuildingPermissionService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.ui.model.LoiGenerationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;

@Component

@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class NOCForBuildingPermissionApprovalModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8536261915240072284L;

	private String saveMode;

	private List<TbDepartment> deptList = null;

	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	List<TbServicesMst> serviceMstList = null;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Resource
	IFileUploadService fileUpload;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	/* private List<DocumentDetailsVO> uploadFileList = new ArrayList<>(); */

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();

	NOCForBuildingPermissionDTO nocBuildingPermissionDto = new NOCForBuildingPermissionDTO();
	
	

	@Autowired
	private NOCForBuildingPermissionService nocBuildPerService;

	private boolean lastChecker;

	private long levelcheck;
	
	

	private String loiChargeApplFlag = MainetConstants.FlagN;

	private List<TbLoiDet> loiDetail = new ArrayList<>();

	private String showFlag = MainetConstants.FlagN;

	private Map<Long, Double> loiCharges = null;

	private String loiNo;

	private Double totalLoiAmount;
	private String taxDesc;

	public String getLoiChargeApplFlag() {
		return loiChargeApplFlag;
	}

	public void setLoiChargeApplFlag(String loiChargeApplFlag) {
		this.loiChargeApplFlag = loiChargeApplFlag;
	}

	public List<TbLoiDet> getLoiDetail() {
		return loiDetail;
	}

	public void setLoiDetail(List<TbLoiDet> loiDetail) {
		this.loiDetail = loiDetail;
	}

	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

	public long getLevelcheck() {
		return levelcheck;
	}

	public void setLevelcheck(long levelcheck) {
		this.levelcheck = levelcheck;
	}

	public boolean isLastChecker() {
		return lastChecker;
	}

	public void setLastChecker(boolean lastChecker) {
		this.lastChecker = lastChecker;
	}
	

	public List<TbServicesMst> getServiceMstList() {
		return serviceMstList;
	}

	public void setServiceMstList(List<TbServicesMst> serviceMstList) {
		this.serviceMstList = serviceMstList;
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

	public List<TbDepartment> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<TbDepartment> deptList) {
		this.deptList = deptList;
	}

	public NOCForBuildingPermissionDTO getNocBuildingPermissionDto() {
		return nocBuildingPermissionDto;
	}

	public void setNocBuildingPermissionDto(NOCForBuildingPermissionDTO nocBuildingPermissionDto) {
		this.nocBuildingPermissionDto = nocBuildingPermissionDto;
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

	public Map<Long, Double> getLoiCharges() {
		return loiCharges;
	}

	public void setLoiCharges(Map<Long, Double> loiCharges) {
		this.loiCharges = loiCharges;
	}

	public String getLoiNo() {
		return loiNo;
	}

	public void setLoiNo(String loiNo) {
		this.loiNo = loiNo;
	}

	public Double getTotalLoiAmount() {
		return totalLoiAmount;
	}

	public void setTotalLoiAmount(Double totalLoiAmount) {
		this.totalLoiAmount = totalLoiAmount;
	}

	@Override
	public boolean saveForm() {

		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		nocBuildingPermissionDto.setUpdatedBy(employee.getEmpId());
		nocBuildingPermissionDto.setUpdatedDate(new Date());
		nocBuildingPermissionDto.setLmoddate(new Date());

		nocBuildingPermissionDto.setLgIpMac(employee.getEmppiservername());
		nocBuildingPermissionDto.setLgIpMacUpd(employee.getEmppiservername());
		nocBuildingPermissionDto.setOrgId(employee.getOrganisation().getOrgid());
		nocBuildingPermissionDto.setLangId(langId);

		nocBuildPerService.saveRegDet(nocBuildingPermissionDto);
		this.setSuccessMessage(getAppSession().getMessage("NOCBuildingPermission.succes.msg")
				+ nocBuildingPermissionDto.getApmApplicationId());
		return true;

	}

	public boolean saveApprovalDetails(String applicationId, Long orgId, String task) {
		Boolean checkFinalAproval = nocBuildPerService.checkEmployeeRole(UserSession.getCurrent());
		/* if (checkFinalAproval == true) { */
		setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDepartmentName(NOCForBuildPermissionConstant.COM);
		requestDTO.setServiceId(getServiceId());
		getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));// change vi
		
		if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
			getWorkflowActionDto().setDecision(nocBuildingPermissionDto.getBirthRegstatus());
			getWorkflowActionDto().setComments(nocBuildingPermissionDto.getBirthRegremark());
		}

		nocBuildingPermissionDto.setApplicationId(getWorkflowActionDto().getReferenceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(NOCForBuildPermissionConstant.NBP,
						UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
		nocBuildingPermissionDto.setSmServiceId(service.getSmServiceId());
		if (null != attachments && !attachments.isEmpty()) {
			final RequestDTO uploadDTO = new RequestDTO();
			uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			uploadDTO.setStatus(MainetConstants.FlagA);
			uploadDTO.setDepartmentName(NOCForBuildPermissionConstant.COM);
			uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			uploadDTO.setIdfId(applicationId);
			nocBuildingPermissionDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			uploadDTO.setServiceId(service.getSmServiceId());
			uploadDTO.setDeptId(requestDTO.getDeptId());
			uploadDTO.setApmMode(NOCForBuildPermissionConstant.FLAGF);
			uploadDTO.setfName(requestDTO.getfName());
			uploadDTO.setGender(requestDTO.getGender());
			// uploadDTO.setLangId(Long.valueOf(requestDTO.getLangId()));
			uploadDTO.setlName(requestDTO.getlName());
			uploadDTO.setmName(requestDTO.getmName());
			uploadDTO.setEmail(requestDTO.getEmail());
			uploadDTO.setUserId(requestDTO.getUserId());
			uploadDTO.setReferenceId(requestDTO.getReferenceId());
			uploadDTO.setApplicationId(Long.valueOf(applicationId));
			fileUpload.doFileUpload(getAttachments(), uploadDTO);
		}
		
		List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(nocBuildingPermissionDto.getApmApplicationId(),UserSession.getCurrent().getOrganisation().getOrgid());
		getWorkflowActionDto().setAttachementId(attachmentId);
		
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && this.lastChecker) {
			saveLoiData();
		}
		nocBuildPerService.updateWorkFlowService(getWorkflowActionDto(), nocBuildingPermissionDto);

		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(Long.valueOf(nocBuildingPermissionDto.getApplicationId()), null,
						UserSession.getCurrent().getOrganisation().getOrgid());
		int size = workflowRequest.getWorkFlowTaskList().size();
		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			nocBuildPerService.updateApproveStatusBR(nocBuildingPermissionDto, null, workflowRequest.getLastDecision());
			nocBuildPerService.updateWorkFlowStatusBR(nocBuildingPermissionDto.getBpId(),
					workflowRequest.getLastDecision(), orgId, nocBuildingPermissionDto.getStatus());
		}

		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
				&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
			nocBuildPerService.updateApproveStatusBR(nocBuildingPermissionDto, workflowRequest.getLastDecision(),
					workflowRequest.getStatus());
			nocBuildPerService.updateWorkFlowStatusBR(nocBuildingPermissionDto.getBpId(), workflowRequest.getStatus(),
					orgId, nocBuildingPermissionDto.getStatus());
			// Current Task Name
			String taskName = workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName();
			// Previous Task Name
			String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size - 2).getTaskName();
			if (!taskName.equals(taskNamePrevious)) {
				nocBuildPerService.updateWorkFlowStatusBR(nocBuildingPermissionDto.getBpId(),
						workflowRequest.getStatus(), orgId, nocBuildingPermissionDto.getStatus());
				nocBuildingPermissionDto.setWfStatus(taskNamePrevious);

			}
		}
		if (workflowRequest != null
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)
				&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
			nocBuildPerService.updateApproveStatusBR(nocBuildingPermissionDto, workflowRequest.getLastDecision(),
					workflowRequest.getStatus());
			nocBuildPerService.updateWorkFlowStatusBR(nocBuildingPermissionDto.getBpId(), workflowRequest.getStatus(),
					orgId, nocBuildingPermissionDto.getStatus());
			// Current Task Name
			String taskName = workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName();
			// Previous Task Name
			String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size - 2).getTaskName();
			if (!taskName.equals(taskNamePrevious)) {
				nocBuildPerService.updateWorkFlowStatusBR(nocBuildingPermissionDto.getBpId(),
						workflowRequest.getStatus(), orgId, nocBuildingPermissionDto.getStatus());
				nocBuildingPermissionDto.setWfStatus(taskNamePrevious);

			}
		}
		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			nocBuildPerService.updateApproveStatusBR(nocBuildingPermissionDto, workflowRequest.getLastDecision(),
					workflowRequest.getStatus());
			nocBuildingPermissionDto.setWfStatus(workflowRequest.getWorkFlowTaskList().get(size - 1).getTaskName());
			nocBuildingPermissionDto.setStatus(NOCForBuildPermissionConstant.STATUSAPPROVED);
			nocBuildPerService.updateWorkFlowStatusBR(nocBuildingPermissionDto.getBpId(),
					workflowRequest.getLastDecision(), orgId, nocBuildingPermissionDto.getStatus());
		}

		
		return true;
	}

	public boolean saveLoiData() {
		boolean status = false;
        Map<Long, Double> loiCharges = new HashMap<Long, Double>();
		LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(NOCForBuildPermissionConstant.NOC_BUILDING_PERMISSION_TAX_CODE, MainetConstants.PG_REQUEST_PROPERTY.TXN,
				UserSession.getCurrent().getOrganisation());
		TbTaxMas taxMas = tbTaxMasService.findByTaxDescIdAndOrgid(lookup.getLookUpId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		loiCharges.put(taxMas.getTaxId(),
				Math.ceil(nocBuildingPermissionDto.getMalabaCharge() * nocBuildingPermissionDto.getBuiltUpArea().doubleValue()));
        getWorkflowActionDto().setApplicationId(nocBuildingPermissionDto.getApmApplicationId());
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(NOCForBuildPermissionConstant.NBP,
				UserSession.getCurrent().getOrganisation().getOrgid());
		setServiceId(serviceMas.getSmServiceId());
		if (MapUtils.isNotEmpty(loiCharges)) {
			TbLoiMas tbLoiMas = ApplicationContextProvider.getApplicationContext().getBean(LoiGenerationModel.class)
					.saveLOIAppData(loiCharges, getServiceId(), loiDetail,
							false/* approvalLetterGenerationApplicable */, getWorkflowActionDto());
			setLoiDetail(loiDetail);
			if (StringUtils.isNotBlank(tbLoiMas.getLoiNo())) {
				setLoiNo(tbLoiMas.getLoiNo());
			}
			status = true;
		}else {
			/*model.addValidationError(
					getApplicationSession().getMessage("Problrm occured while fetching LOI Charges from BRMS Sheet"));*/
		}
		return status;

	}

	private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
		getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		workflowActionDto.setDateOfAction(new Date());
		workflowActionDto.setCreatedDate(new Date());
		workflowActionDto.setComments(nocBuildingPermissionDto.getBirthRegremark());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		workflowActionDto.setIsObjectionAppealApplicable(false);

		// workflowActionDto.setComments(workflowActionDto.getComments());
		return workflowActionDto;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}
}
