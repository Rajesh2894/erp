package com.abm.mainet.buildingplan.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebMethod;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.buildingplan.domain.ProfessionalRegistrationEntity;
import com.abm.mainet.buildingplan.dto.ProfessionalRegistrationDTO;
import com.abm.mainet.buildingplan.repository.ProfessionalRegistrationRepo;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class ProfessionalRegistrationServiceImpl implements ProfessionalRegistrationService {

	private final static Logger LOGGER = Logger.getLogger(ProfessionalRegistrationService.class);

	@Autowired
	private ProfessionalRegistrationRepo professionalRegRepo;

	@Autowired
	private IWorkflowRequestService workflowRequestService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IFileUploadService fileUpload;

	@Override
	public void saveRegForm(ProfessionalRegistrationDTO professionalRegDTO) {
		ProfessionalRegistrationEntity entity = new ProfessionalRegistrationEntity();
		RequestDTO reqDto = new RequestDTO();
		setRequestApplicantDetails(reqDto, professionalRegDTO);	
		if(professionalRegDTO.getApplicationId()!=null && professionalRegDTO.getApplicationId()>0) {
			professionalRegRepo.deleteDocument(professionalRegDTO.getApplicationId(),professionalRegDTO.getServiceId(),professionalRegDTO.getOrgId());
		}
		if (professionalRegDTO.getId() == null || professionalRegDTO.getId() == 0) {
			professionalRegDTO.setApplicationId(applicationService.createApplication(reqDto));
			callWorkFlowForFreeService(professionalRegDTO);
		}
		if ((professionalRegDTO.getDocs() != null) && !professionalRegDTO.getDocs().isEmpty()) {
			reqDto.setApplicationId(professionalRegDTO.getApplicationId());
			fileUpload.doFileUpload(professionalRegDTO.getDocs(), reqDto);
		}
		BeanUtils.copyProperties(professionalRegDTO, entity);
		professionalRegRepo.save(entity);
		
	}

	private void setRequestApplicantDetails(final RequestDTO reqDto, ProfessionalRegistrationDTO professionalRegDTO) {
		reqDto.setfName(professionalRegDTO.getFirstName());
		reqDto.setMobileNo(professionalRegDTO.getMobileNo());
		reqDto.setEmail(professionalRegDTO.getEmailId());
		reqDto.setPincodeNo(Long.valueOf(professionalRegDTO.getPincode()));
		reqDto.setOrgId(professionalRegDTO.getOrgId());
		reqDto.setLangId(professionalRegDTO.getLangId());
		reqDto.setUserId(professionalRegDTO.getCreatedBy());
		reqDto.setServiceId(professionalRegDTO.getServiceId());
		reqDto.setPayStatus(MainetConstants.PAYMENT.FREE);
	}

	@Override
	public ProfessionalRegistrationDTO getDetailByAppIdAndOrgId(Long applicationId, Long orgid) {
		ProfessionalRegistrationDTO professionalRegDTO = new ProfessionalRegistrationDTO();
		ProfessionalRegistrationEntity entity = professionalRegRepo.getDetailByAppIdAndOrgId(applicationId, orgid);
		if (entity != null) {
			BeanUtils.copyProperties(entity, professionalRegDTO);
		}
		return professionalRegDTO;
	}

	public ProfessionalRegistrationDTO callWorkFlowForFreeService(ProfessionalRegistrationDTO professionalRegDTO) {
		ApplicationMetadata applicationData = new ApplicationMetadata();
		Organisation org = new Organisation();
		org.setOrgid(professionalRegDTO.getOrgId());
		final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
		applicationData.setApplicationId(professionalRegDTO.getApplicationId());
		applicationData.setIsCheckListApplicable(false);
		applicationData.setOrgId(professionalRegDTO.getOrgId());
		applicantDetailDto.setServiceId(professionalRegDTO.getServiceId());
		applicantDetailDto.setDepartmentId(professionalRegDTO.getDeptId());
		applicantDetailDto.setUserId(professionalRegDTO.getCreatedBy());
		applicantDetailDto.setOrgId(professionalRegDTO.getOrgId());
		applicantDetailDto.setMobileNo(professionalRegDTO.getMobileNo());
		applicantDetailDto.setDwzid1(professionalRegDTO.getDistrict());
		commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
		return professionalRegDTO;
	}

	@Override
	public boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, String eventName, Long serviceId,
			String serviceShortCode) {
		boolean updateFlag = false;
		try {
			if (StringUtils.equalsIgnoreCase(eventName, serviceShortCode)) {

				if (StringUtils.equalsIgnoreCase(taskAction.getDecision(),
						MainetConstants.WorkFlow.Decision.APPROVED)) {
					professionalRegRepo.updateAgencyApprovalWorkflow(MainetConstants.FlagA, taskAction.getEmpId(),
							taskAction.getApplicationId());
				} else if (StringUtils.equalsIgnoreCase(taskAction.getDecision(),
						MainetConstants.WorkFlow.Decision.REJECTED)) {
					professionalRegRepo.updateAgencyApprovalWorkflow(MainetConstants.FlagR, taskAction.getEmpId(),
							taskAction.getApplicationId());
				}
				updateWorkflowTaskAction(taskAction, serviceId);
				updateFlag = true;

			}
		} catch (Exception exception) {
			LOGGER.error("Exception Occured while Updating workflow action task", exception);
			throw new FrameworkException("Error while Updating workflow action task", exception);
		}
		return updateFlag;
	}

	@Transactional
	@WebMethod(exclude = true)
	private WorkflowTaskActionResponse updateWorkflowTaskAction(WorkflowTaskAction taskAction, Long serviceId) {

		WorkflowTaskActionResponse workflowResponse = null;
		try {
			String processName = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getProcessName(serviceId, taskAction.getOrgId());
			if (StringUtils.isNotBlank(processName)) {

				WorkflowProcessParameter workflowDto = new WorkflowProcessParameter();
				workflowDto.setProcessName(processName);
				workflowDto.setWorkflowTaskAction(taskAction);
				workflowResponse = ApplicationContextProvider.getApplicationContext()
						.getBean(IWorkflowExecutionService.class).updateWorkflow(workflowDto);

			}
		} catch (Exception exception) {
			throw new FrameworkException("Error while Updating workflow action task", exception);
		}
		return workflowResponse;
	}

	@Override
	public void saveOBPASData(ProfessionalRegistrationDTO professionalRegDTO) {
		Map<String, Object> responseObject = null;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			Organisation org = new Organisation();
			org.setOrgid(professionalRegDTO.getOrgId());
			Map<String, String> requestMap = new LinkedHashMap<>();
			SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.BuildingPlanning.DATE_FORMAT);
			requestMap.put("user_id", String.valueOf(professionalRegDTO.getId()));
			String userType = CommonMasterUtility.getNonHierarchicalLookUpObject(professionalRegDTO.getUserType(), org)
					.getDescLangFirst();
			if (StringUtils.equalsAnyIgnoreCase(MainetConstants.BuildingPlanning.ARCHITECT, userType)) {
				requestMap.put("user_type", MainetConstants.Common_Constant.NUMBER.TWO);
			} else if (StringUtils.equalsAnyIgnoreCase(MainetConstants.BuildingPlanning.SUPERVISOR, userType)) {
				requestMap.put("user_type", MainetConstants.Common_Constant.NUMBER.TWELVE);
			} else if (StringUtils.equalsAnyIgnoreCase(MainetConstants.BuildingPlanning.STRUCTURE_ENGINEER, userType)) {
				requestMap.put("user_type", MainetConstants.Common_Constant.NUMBER.THIRTEEN);
			} else if (StringUtils.equalsAnyIgnoreCase(MainetConstants.BuildingPlanning.PROOF_CONSULTANT, userType)) {
				requestMap.put("user_type", MainetConstants.Common_Constant.NUMBER.FOURTEEN);
			} else if (StringUtils.equalsAnyIgnoreCase(MainetConstants.BuildingPlanning.ENGINEER, userType)) {
				requestMap.put("user_type", MainetConstants.Common_Constant.NUMBER.FIFTEN);
			} else if (StringUtils.equalsAnyIgnoreCase(MainetConstants.BuildingPlanning.CIVIL_ENGINEER, userType)) {
				requestMap.put("user_type", MainetConstants.Common_Constant.NUMBER.SIXTEEN);
			}
			requestMap.put("name", professionalRegDTO.getFirstName());
			requestMap.put("mobile", professionalRegDTO.getMobileNo());
			requestMap.put("email", professionalRegDTO.getEmailId());
			requestMap.put("address", professionalRegDTO.getAddress());
			requestMap.put("pin", professionalRegDTO.getPincode());
			requestMap.put("state", CommonMasterUtility
					.getNonHierarchicalLookUpObject(professionalRegDTO.getState(), org).getDescLangFirst());
			requestMap.put("district", CommonMasterUtility
					.getNonHierarchicalLookUpObject(professionalRegDTO.getDistrict(), org).getDescLangFirst());
			requestMap.put("coa_number",
					StringUtils.isNoneBlank(professionalRegDTO.getCoaNo()) ? professionalRegDTO.getCoaNo() : "");
			requestMap.put("coa_date",
					professionalRegDTO.getCoaValDate() != null ? dateFormat.format(professionalRegDTO.getCoaValDate())
							: "");
			String request = mapper.writeValueAsString(requestMap);
			LOGGER.info("request:- " + request);
			StringEntity entity = new StringEntity(request);
			CloseableHttpClient httpclient = getCloseableHttpClient();
			HttpPost httpPost = new HttpPost(ApplicationSession.getInstance().getMessage("obpas.api.url"));
			httpPost.setEntity(entity);
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader("ABM_TOKEN", ApplicationSession.getInstance().getMessage("obpas.api.key"));
			LOGGER.info("httpPost:- " + httpPost);
			HttpResponse httpresponse = httpclient.execute(httpPost);
			int statusCode = httpresponse.getStatusLine().getStatusCode();
			String content = EntityUtils.toString(httpresponse.getEntity());
			LOGGER.info("content:- " + request);
			ObjectMapper obj = new ObjectMapper();
			responseObject = obj.readValue(content, Map.class);
			int resStatus = (int) responseObject.get("status");
			String resMsg = (String) responseObject.get("message");
			if (resStatus == 1) {
				LOGGER.info("Response Success Status:- " + resStatus);
				LOGGER.info("Response Success Message:- " + resMsg);
			} else {
				LOGGER.info("Response Failure Status:- " + resStatus);
				LOGGER.info("Response Failure Message:- " + resMsg);
			}
		} catch (Exception e) {
			LOGGER.error("Exception: " + e);
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("deprecation")
	public static CloseableHttpClient getCloseableHttpClient() {
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
					.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
						public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
							return true;
						}
					}).build()).build();

		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			LOGGER.error("Exception: " + e);
			e.printStackTrace();
		}
		return httpClient;
	}
}
