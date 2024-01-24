package com.abm.mainet.common.workflow.service;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;

@Service
public class WorkflowRequestService implements IWorkflowRequestService {

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IWorkflowExecutionService workflowExecutionService;

	@Autowired
	private IWorkflowTyepResolverService iWorkflowTyepResolverService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private ICFCApplicationAddressService iCFCApplicationAddressService;

	@Autowired
	private IWorkflowActionService iWorkflowActionService;
	@Autowired
	private IOrganisationService organisationService;

	@Autowired
	private ICFCApplicationMasterService icfcApplicationService;
	
	@Autowired
	private WorkFlowTypeRepository workFlowTypeRepository;

	@Override
	public WorkflowRequest getWorkflowRequestByAppIdOrRefId(final Long applicationId, final String referenceId,
			final Long orgId) {

		String uuId = null;
		if (applicationId != null) {
			uuId = applicationId.toString();
		} else {
			uuId = referenceId;
		}
		Map<String, String> requestParam = new HashMap<>();
		requestParam.put(MainetConstants.Common_Constant.UUID, uuId);
		requestParam.put(MainetConstants.Common_Constant.ORGID, orgId.toString());
		WorkflowRequest workflowRequest = prepareBmpData(requestParam,
				ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_REQUEST_BY_UUID);

		return workflowRequest;
	}

	@Override
	public WorkflowRequest findByApplicationId(final Long applicationId) {

		Map<String, String> requestParam = new HashMap<>();
		requestParam.put(MainetConstants.Common_Constant.APPLICATION_ID, applicationId.toString());
		WorkflowRequest workflowRequest = prepareBmpData(requestParam,
				ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_REQUEST_BY_APP_ID);
		return workflowRequest;
	}

	private WorkflowRequest prepareBmpData(Map<String, String> requestParam, String url) {

		WorkflowRequest workflowTask = null;
		ResponseEntity<?> responseEntity = null;
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		URI uri = dd.expand(url, requestParam);
		try {
			responseEntity = RestClient.callRestTemplateClient(workflowTask, uri.toString());
			HttpStatus statusCode = responseEntity.getStatusCode();
			if (statusCode == HttpStatus.OK) {
				workflowTask = (WorkflowRequest) RestClient.castResponse(responseEntity, WorkflowRequest.class);
			}
		} catch (Exception ex) {
			throw new FrameworkException(
					"Exception occured while calling method workflow request actor Id :" + requestParam, ex);
		}
		return workflowTask;
	}

	@Override
	public void updateWorkflowAutoEscalationTask(Map<String, List<Long>> workflowDetails) {
		ResponseEntity<?> responseEntity = null;
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		URI uri = dd.expand("", workflowDetails);

		responseEntity = RestClient.callRestTemplateClient(Object.class, uri.toString());

	}

	public WorkflowRequest findByApplicationId(Long applicationId, Long workflowId) {
		Map<String, String> requestParam = new HashMap<>();
		requestParam.put(MainetConstants.Common_Constant.APPLICATION_ID, applicationId.toString());
		requestParam.put(MainetConstants.Common_Constant.WORK_FLOW_ID, workflowId.toString());
		WorkflowRequest workflowRequest = prepareBmpData(requestParam,
				ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_REQUEST_BY_APP_ID_AND_WORKFLOW_ID);
		return workflowRequest;
	}

	@Override
	public void updateWorkFlow(Long taskId, final CommonChallanDTO offlineDto, Long empType, Long empId,
			String empName) {
		String processName = serviceMasterService.getProcessName(offlineDto.getServiceId(), offlineDto.getOrgId());
		if (processName != null) {
			WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
			workflowdto.setProcessName(processName);
			WorkflowTaskAction workflowAction = new WorkflowTaskAction();
			try {
				if ((offlineDto.getLoiNo() != null) && !offlineDto.getLoiNo().isEmpty()) {
					workflowAction.setIsLoiGenerated(true);
				}
				workflowAction.setTaskId(taskId);
				workflowAction.setApplicationId(offlineDto.getApplNo());
				workflowAction.setReferenceId(offlineDto.getObjectionNumber());
				workflowAction.setDateOfAction(new Date());
				workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
				workflowAction.setOrgId(offlineDto.getOrgId());
				workflowAction.setEmpId(empId);
				workflowAction.setModifiedBy(empId);
				workflowAction.setEmpType(empType);
				workflowAction.setEmpName(empName);
				workflowAction.setCreatedBy(empId);
				workflowAction.setCreatedDate(new Date());
				workflowdto.setWorkflowTaskAction(workflowAction);
				workflowExecutionService.updateWorkflow(workflowdto);
			} catch (Exception e) {
				throw new FrameworkException("Exception while calling jbpm workflow :", e);
			}
		}
	}

	@Override
	@Transactional
	public void initiateAndUpdateWorkFlowProcess(final CommonChallanDTO offline, WardZoneBlockDTO dwzDto) {
		String processName = serviceMasterService.getProcessName(offline.getServiceId(), offline.getOrgId());
		if (processName != null) {
			ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(offline.getServiceId(),
					offline.getOrgId());
			WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
			workflowdto.setProcessName(processName);
			WorkflowTaskAction workflowAction = setWorkFlowActionData(offline);
			TaskAssignment requesterTaskAssignment = setRequesterTask(offline);

			try {
				if ((offline.getLoiNo() != null && !offline.getLoiNo().isEmpty())) {
					workflowdto.setWorkflowTaskAction(workflowAction);
					workflowExecutionService.updateWorkflow(workflowdto);

				} else {
					ApplicationMetadata applicationData = setWorkFlowApplicationData(offline, dwzDto, serviceMaster);
					workflowdto.setApplicationMetadata(applicationData);
					workflowdto.setWorkflowTaskAction(workflowAction);
					workflowdto.setRequesterTaskAssignment(requesterTaskAssignment);
					workflowExecutionService.initiateWorkflow(workflowdto);
					Organisation org = new Organisation();
					org.setOrgid(offline.getOrgId());
					if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TCP) 
							&& StringUtils.equals(MainetConstants.TCP_NEW_LICENSE, serviceMasterService.fetchServiceShortCode(offline.getServiceId(), offline.getOrgId()))) {
						
						workFlowTypeRepository.updateNLDraftFlagAfterInitiateWorkflow(offline.getApplNo(), offline.getOrgId());
					}
				}
			} catch (Exception e) {
				throw new FrameworkException("Exception while calling work flow", e);
			}
		}
	}

	private WorkflowTaskAction setWorkFlowActionData(final CommonChallanDTO offline) {
		Organisation org = organisationService.getOrganisationById(offline.getOrgId());
		WorkflowTaskAction workflowAction = new WorkflowTaskAction();
		workflowAction.setApplicationId(offline.getApplNo());
		workflowAction.setReferenceId(offline.getObjectionNumber());
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.ASCL) 
				&& StringUtils.equals(MainetConstants.Property.MUT, serviceMasterService.fetchServiceShortCode(offline.getServiceId(), offline.getOrgId()))
				&& StringUtils.isNotEmpty(offline.getReferenceNo())) {
			workflowAction.setReferenceId(offline.getReferenceNo());
		} else if (Utility.isEnvPrefixAvailable(org, MainetConstants.Property.MUT)) {
			workflowAction.setReferenceId(offline.getReferenceNo());
		} 
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TCP) 
				&& StringUtils.equals(MainetConstants.TCP_NEW_LICENSE, serviceMasterService.fetchServiceShortCode(offline.getServiceId(), offline.getOrgId()))) {
			
			List<Object[]> ddzList = workFlowTypeRepository.findKhrsColumnsByApplicationNoAndOrgId(offline.getApplNo(), offline.getOrgId());
			for (Object[] ddz : ddzList) {
				workflowAction.setCodIdOperLevel1(ddz.length > 0 ? Long.valueOf(ddz[0].toString())  : null);
				workflowAction.setCodIdOperLevel2(ddz.length > 1 ? Long.valueOf(ddz[1].toString()) : null);
				workflowAction.setCodIdOperLevel3(ddz.length > 2 ? Long.valueOf(ddz[2].toString()) : null);
				workflowAction.setCodIdOperLevel4(ddz.length > 3 ? Long.valueOf(ddz[3].toString()) : null);
			}
		}
		
		workflowAction.setDateOfAction(new Date());
		workflowAction.setOrgId(offline.getOrgId());
		workflowAction.setEmpId(offline.getUserId());
		workflowAction.setEmpType(offline.getEmpType());
		workflowAction.setEmpName(offline.getApplicantName());
		workflowAction.setCreatedBy(offline.getUserId());
		workflowAction.setCreatedDate(new Date());
		if (StringUtils.isEmpty(offline.getChallanNo())) {
			workflowAction.setPaymentMode(MainetConstants.PAYMENT.ONLINE);
		} else {
			workflowAction.setPaymentMode(MainetConstants.PAYMENT.OFFLINE);
		}
		if (offline.getLoiNo() != null && !offline.getLoiNo().isEmpty()) {
			workflowAction.setIsLoiGenerated(true);
			workflowAction.setTaskId(offline.getTaskId());
			workflowAction.setModifiedBy(offline.getUserId());
			workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		} else {
			if (offline.isDocumentUploaded()) {
				List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(offline.getApplNo(),
						offline.getOrgId());
				workflowAction.setAttachementId(attachmentId);
			}
			workflowAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
		}
		return workflowAction;
	}

	private ApplicationMetadata setWorkFlowApplicationData(final CommonChallanDTO offline, WardZoneBlockDTO dwzDto,
			ServiceMaster serviceMaster) {
		ApplicationMetadata applicationData = new ApplicationMetadata();
		boolean autoescalate = false;
		Organisation org = new Organisation();
		org.setOrgid(offline.getOrgId());
		if (dwzDto == null) {
			dwzDto = offline.getDwzDTO();
		}
		if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL) && (StringUtils.equalsIgnoreCase(MainetConstants.ServiceShortCode.APPLICATION_FOR_BIRTH_CERTIFICATE, serviceMaster.getSmShortdesc()))
				|| (StringUtils.equalsIgnoreCase(MainetConstants.ServiceShortCode.APPLICATION_FOR_DEATH_CERTIFICATE, serviceMaster.getSmShortdesc())) ) {
			dwzDto = offline.getDwzDTO();
		}
		WorkflowMas mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(offline.getOrgId(),
				offline.getDeptId(), offline.getServiceId(), dwzDto.getAreaDivision1(), dwzDto.getAreaDivision2(),
				dwzDto.getAreaDivision3(), dwzDto.getAreaDivision4(), dwzDto.getAreaDivision5());
		if (mas != null) {
			String code = CommonMasterUtility
					.getNonHierarchicalLookUpObject(mas.getWorkflowMode(), mas.getOrganisation()).getLookUpCode();
			if (code.equals("AE")) {
				autoescalate = true;
			}
			applicationData.setWorkflowId(mas.getWfId());
		}
		applicationData.setIsCheckListApplicable(false);
		if (offline.isDocumentUploaded()) {

			String checklistFlag = null;
			final List<LookUp> lookUps = CommonMasterUtility.getLookUps("APL", org);
			for (final LookUp lookUp : lookUps) {
				if (serviceMaster.getSmChklstVerify() != null
						&& lookUp.getLookUpId() == serviceMaster.getSmChklstVerify().longValue()) {
					checklistFlag = lookUp.getLookUpCode();
					break;
				}
			}
			serviceMaster = serviceMasterService.getServiceMaster(offline.getServiceId(), offline.getOrgId());
			if (serviceMaster != null && serviceMaster.getSmCheckListReq().equals(MainetConstants.FlagN)
					&& MainetConstants.Common_Constant.ACTIVE_FLAG.equalsIgnoreCase(checklistFlag)) {
				applicationData.setIsCheckListApplicable(false);
			}
			if (serviceMaster != null && serviceMaster.getSmCheckListReq().equals(MainetConstants.FlagY)
					&& MainetConstants.Common_Constant.ACTIVE_FLAG.equalsIgnoreCase(checklistFlag)) {
				applicationData.setIsCheckListApplicable(true);
			}
		}
		applicationData.setApplicationId(offline.getApplNo());
		applicationData.setIsAutoEscalate(autoescalate);
		applicationData.setIsFreeService(false);
		if (MainetConstants.Y_FLAG.equalsIgnoreCase(serviceMaster.getSmScrutinyApplicableFlag())) {
			applicationData.setIsScrutinyApplicable(true);
		} else {
			applicationData.setIsScrutinyApplicable(false);
		}
		if (MainetConstants.Y_FLAG.equalsIgnoreCase(serviceMaster.getSmScrutinyChargeFlag())) {
			applicationData.setIsLoiApplicable(true);
		} else {
			applicationData.setIsLoiApplicable(false);
		}
		if (serviceMaster.getSmPrintRespons() != null) {
			String printRespon = CommonMasterUtility
					.getNonHierarchicalLookUpObject(serviceMaster.getSmPrintRespons(), org).getLookUpCode();
			if (!printRespon.equals(MainetConstants.CommonConstants.NA)) {
				applicationData.setIsApprovalLetterGeneration(true);
			}
		}
		applicationData.setOrgId(offline.getOrgId());
		if (StringUtils.isEmpty(offline.getChallanNo())) {
			applicationData.setPaymentMode(MainetConstants.PAYMENT.ONLINE);
		} else {
			applicationData.setPaymentMode(MainetConstants.PAYMENT.OFFLINE);
		}
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.ASCL) && StringUtils.equals(MainetConstants.Property.MUT, serviceMaster.getSmShortdesc())
				&& StringUtils.isNotEmpty(offline.getReferenceNo())) {
			applicationData.setReferenceId(offline.getReferenceNo());
		}else if (Utility.isEnvPrefixAvailable(org, MainetConstants.Property.MUT)
				&& StringUtils.isNotEmpty(offline.getReferenceNo())) {
			applicationData.setReferenceId(offline.getReferenceNo());
		}
		return applicationData;
	}

	private TaskAssignment setRequesterTask(final CommonChallanDTO offline) {
		TaskAssignment assignment = new TaskAssignment();
		Set<String> actorIds = new HashSet<>();

		//ServiceMaster serviceMaster = new ServiceMaster();
		Organisation org = new Organisation();
		org.setOrgid(offline.getOrgId());
		//code added for restrict the orgid dependancy for RTI module
		if (offline.getApplNo() != null) {
			CFCApplicationAddressEntity addressEntity = null;
			if (offline.getDeptCode() != null && offline.getDeptCode().equals(MainetConstants.DEPT_SHORT_NAME.RTI)) {

				addressEntity = icfcApplicationService.getApplicantsDetails(offline.getApplNo());
			} else {
				addressEntity = iCFCApplicationAddressService.getApplicationAddressByAppId(offline.getApplNo(),
						offline.getOrgId());
			}
			assignment
					.setActorId(addressEntity.getUserId() + MainetConstants.operator.COMMA + offline.getMobileNumber());
			actorIds.add(addressEntity.getUserId().toString());
			actorIds.add(offline.getMobileNumber());
			assignment.setActorIds(actorIds);
			assignment.setOrgId(addressEntity.getOrgId().getOrgid());
		}
		if (Utility.isEnvPrefixAvailable(org,  MainetConstants.ENV_TCP)
				&& StringUtils.equals(MainetConstants.TCP_NEW_LICENSE, serviceMasterService.fetchServiceShortCode(offline.getServiceId(), offline.getOrgId()))) {

			assignment.setDeptId(offline.getDeptId());

			assignment.setServiceId(offline.getServiceId());

			assignment.setUrl("ApplicationAuthorization.html");

		}
		return assignment;
	}

	public void signalWorkFlow(final CommonChallanDTO offline) {
		WorkflowTaskAction workflowAction = new WorkflowTaskAction();
		workflowAction.setApplicationId(offline.getApplNo());
		workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		workflowAction.setReferenceId(offline.getObjectionNumber());
		Employee emp = new Employee();
		emp.setEmpId(offline.getUserId());
		// emp.setEmpname(offline.getEname());
		emp.setEmplType(offline.getEmpType());

		iWorkflowActionService.signalWorkFlow(workflowAction, emp, offline.getOrgId(), offline.getServiceId(),
				MainetConstants.WorkFlow.Signal.HEARING);
	}
}
