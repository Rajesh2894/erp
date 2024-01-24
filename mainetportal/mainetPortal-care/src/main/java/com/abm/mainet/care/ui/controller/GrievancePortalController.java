package com.abm.mainet.care.ui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.care.dto.ActionResponseDTO;
import com.abm.mainet.care.dto.CareFeedbackDTO;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintRegistrationAcknowledgementDTO;
import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.care.dto.GrievanceReqDTO;
import com.abm.mainet.care.dto.ResponseType;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.service.IDepartmentComplaintService;
import com.abm.mainet.care.ui.model.ComplaintRegistrationModel;
import com.abm.mainet.care.ui.validator.GrievanceBeanValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ActionDTO;
import com.abm.mainet.common.dto.ActionDTOWithDoc;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.LocationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(MainetConstants.WINDOWS_SLASH + MainetConstants.GrievanceConstants.GRIEVANCE_CONTROLER)
public class GrievancePortalController extends AbstractFormController<ComplaintRegistrationModel> {

	private static final Logger LOGGER = Logger.getLogger(GrievancePortalController.class);

	@Autowired
	private IDepartmentComplaintService departmentComplaintService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private ICareRequestService careRequestService;

	@Autowired
	private IOrganisationService organisatonService;

	private CareRequestDTO careRequestDTO;

	/**
	 * 
	 * @param careRequest
	 * @param result
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView raiseComplaint(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "isReset", required = false) String isReset) throws Exception {

		LOGGER.info("Creating Grievance Request from createRequest of GrievancePortalController");
		Organisation org = organisatonService.getOrganisationById(Utility.getOrgId());
		sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		StringBuffer applicantType = new StringBuffer();
		StringBuffer complaintLabel = new StringBuffer();

		ComplaintRegistrationModel model = getModel();
		model.setCommonHelpDocs("grievance.html");
		Employee emp = UserSession.getCurrent().getEmployee();
		if (emp != null && !emp.getEmploginname().equalsIgnoreCase("NOUSER"))
			this.setApplicantDetail(emp, model);

		modelMap.put(MainetConstants.GrievanceConstants.TASKNAME, MainetConstants.GrievanceConstants.REQUESTOR_ACTION);

		List<LookUp> lookupList = CommonMasterUtility.getLookUpList("AC");

		lookupList.forEach(lookup -> {
			if (applicantType != null && !applicantType.toString().isEmpty()) {
				applicantType.append(",");
			}
			if (lookup.getOtherField()!=null && lookup.getOtherField().equalsIgnoreCase(MainetConstants.FlagY)) {
				applicantType.append(lookup.getLookUpCode());
				complaintLabel.append(lookup.getDescLangFirst());
				complaintLabel.append(",");
			}
		});
		model.setApplicationType(applicantType.toString());
		model.setLabelType(complaintLabel.toString());
		if (org != null) {
			if (org.getDefaultStatus() == null || org.getDefaultStatus().isEmpty()) {
				model.setDept(departmentComplaintService
						.getCareWorkflowMasterDefinedDepartmentsListByOrgId(Utility.getOrgId()));
			}
		}
		// U#96713 check here code running on KDMC or not
		boolean isKDMCENV = isKDMCEnvPresent();
		model.setKdmcEnv(MainetConstants.FlagN);
		if (model.getApplicantDetailDTO().getTitleId() == null) {
			model.getApplicantDetailDTO().setTitleId(1L);
		}
		// doing here because in KDMC & DSCL both JSP different and they want to hide
		// below values
		// below code look like if hide from JSP and doesn't set than it give error and
		// in other case like they want input at JSP than input value capture from JSP
		LookUp rfmLookup = CommonMasterUtility.getValueFromPrefixLookUp("WB", "RFM", org);
		LookUp rfcLookup = CommonMasterUtility.getValueFromPrefixLookUp("GP", "RFC", org);
		// hidden value set only in case of KDMC - Mode and category and reference date
		// applicationType
		model.getCareRequestDto().setReferenceMode(String.valueOf(rfmLookup.getLookUpId()));
		model.getCareRequestDto().setReferenceCategory(String.valueOf(rfcLookup.getLookUpId()));
		model.getCareRequestDto().setApplnType("C");
		model.getCareRequestDto().setReferenceDate(new Date());
		if (isKDMCENV) {
			model.setKdmcEnv(MainetConstants.FlagY);
			/*
			 * LookUp rfmLookup= CommonMasterUtility.getValueFromPrefixLookUp("WB", "RFM",
			 * org); LookUp rfcLookup=CommonMasterUtility.getValueFromPrefixLookUp("GP",
			 * "RFC", org);
			 */
			// hidden value set only in case of KDMC - Mode and category and reference date
			// applicationType
			/*
			 * model.getCareRequestDto().setReferenceMode(String.valueOf(rfmLookup.
			 * getLookUpId()));
			 * model.getCareRequestDto().setReferenceCategory(String.valueOf(rfcLookup.
			 * getLookUpId())); model.getCareRequestDto().setReferenceDate(new Date());
			 */
			// D#111261
			model.getCareRequestDto().setLocation(1272L);
			model.setDept(
					departmentComplaintService.getCareWorkflowMasterDefinedDepartmentsListByOrgId(Utility.getOrgId()));
			
		}

		if (emp.getEmploginname().equalsIgnoreCase("NOUSER")) {
			getModel().setLoginFlag("N");
			// this block execute in case of without login
			if (isKDMCENV) {
				// design specific to SKDCL
				// D#111349
				if (StringUtils.isNotEmpty(isReset) && isReset.equals("Y")) {
					return new ModelAndView("NewComplaintRegistrationFormKDMCPreLoginValid", MainetConstants.FORM_NAME,
							model);
				} else {
					return new ModelAndView("NewComplaintRegistrationFormKDMCPreLogin", MainetConstants.FORM_NAME,
							model);
				}
			} else {
				// D#111349
				if (StringUtils.isNotEmpty(isReset) && isReset.equals("Y")) {
					return new ModelAndView(MainetConstants.GrievanceConstants.NEWCOMPLAINTREGISTRATIONFORMVALID,
							MainetConstants.FORM_NAME, model);
				} else {
					return new ModelAndView(MainetConstants.GrievanceConstants.NEWCOMPLAINTREGISTRATIONFORMPRELOGIN,
							MainetConstants.FORM_NAME, model);
				}
			}

		} else {
			// other than SKDCL
			if (StringUtils.isNotEmpty(isReset) && isReset.equals("Y")) {
				return new ModelAndView(MainetConstants.GrievanceConstants.NEWCOMPLAINTREGISTRATIONFORMVALID,
						MainetConstants.FORM_NAME, model);
			} else {
				return new ModelAndView(MainetConstants.GrievanceConstants.NEWCOMPLAINTREGISTRATIONFORM,
						MainetConstants.FORM_NAME, model);
			}

		}

	}

	/**
	 * 
	 * @param careRequest
	 * @param resultet
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @throws URISyntaxException
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(params = MainetConstants.GrievanceConstants.SAVE_DETAILS, method = RequestMethod.POST)
	public ModelAndView submitComplaint(
			@ModelAttribute(MainetConstants.GrievanceConstants.CARE_REQUEST_DTO) CareRequestDTO careRequests,
			HttpServletRequest request, BindingResult result, ModelMap modelMap, HttpServletResponse response,
			final RedirectAttributes redirectAttributes) throws URISyntaxException, Exception {
		LOGGER.info("Submitting Grievance Request from submit Complaint of GrievancePortalController");
		// Bind HTTP request with model
		getModel().bind(request);
		ComplaintRegistrationModel model = getModel();
		ActionResponseDTO actResponse = new ActionResponseDTO();
		ModelAndView modelAndView = null;
		Organisation org = organisatonService.getOrganisationById(Utility.getOrgId());
		// Retrive DTO from model
		RequestDTO applicantDetailDto = model.getApplicantDetailDTO();
		CareRequestDTO careRequest = model.getCareRequestDto();
		// D#127037 CONCAT property no and flat no if present in extRefereNo
		if (StringUtils.isNotBlank(careRequest.getPropFlatNo())) {
			careRequest.setExtReferNumber(careRequest.getExtReferNumber() + "-" + careRequest.getPropFlatNo());
		}
		
		List<DepartmentComplaintDTO> deptList = new ArrayList<>();
		try {
			if(null != careRequest.getOrgId()){
			deptList = ApplicationContextProvider.getApplicationContext().getBean(IDepartmentComplaintService.class).getCareWorkflowMasterDefinedDepartmentsListByOrgId(careRequest.getOrgId());
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		DepartmentComplaintDTO dept= deptList.stream().filter(dep->dep.getDepartment().getDpDeptid()==careRequest.getDepartmentComplaint())
				.findFirst().orElse(null);
		String deptCode="";
		if(dept!= null) {
			deptCode=dept.getDepartment().getDpDeptcode();
		}
		
		if(deptCode.equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.PROP_TAX) && StringUtils.isNotBlank(careRequest.getExtReferNumber())){
			boolean flatListEmpty = careRequestService.isFlatListEmpty(careRequest.getExtReferNumber());
			careRequest.setFlatListEmpty(flatListEmpty);	
		}
		
		ActionDTO startAction = model.getAction();
		Long organisation = null;
		String orgLogoPath = null;
		if (careRequest.getOrgId() != null) {
			organisation = careRequest.getOrgId();
		} else {
			organisation = Utility.getOrgId();
			careRequest.setOrgId(organisation);
		}
		
		// Validate data
		DepartmentComplaintTypeDTO complaintType = departmentComplaintService
				.getDepartmentComplaintTypeById(careRequest.getComplaintType());
		actResponse = GrievanceBeanValidator.doSubmitValidation(applicantDetailDto, careRequest, result, actResponse,
				model.getKdmcEnv(), org, complaintType);

		if (getModel().getLoginFlag() != null && getModel().getLoginFlag().equalsIgnoreCase("N")) {
			if (complaintType != null && StringUtils.isNotEmpty(complaintType.getOtpValidReq())
					&& complaintType.getOtpValidReq().equalsIgnoreCase(MainetConstants.FlagY)) {
				if (getModel().getEnteredMobileOTP() == null || getModel().getEnteredMobileOTP().isEmpty()) {
					result.reject(ApplicationSession.getInstance()
							.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.OTP_EMPTY));
				}
			}

			if (!getModel().getEnteredMobileOTP().isEmpty()
					&& !getModel().getEnteredMobileOTP().equals(getModel().getGeneratedMobileOTP())) {
				result.reject(ApplicationSession.getInstance()
						.getMessage(MainetConstants.GrievanceConstants.ValidationMessage.OTP_INVALID));
			}

			if (result.hasErrors()) {
				actResponse.setResponse("");
				actResponse.setResponse(ResponseType.Fail.toString());
				GrievanceBeanValidator.onError(actResponse, result);
			} /*
				 * else { actResponse = new ActionResponseDTO(ResponseType.Fail.toString());
				 * GrievanceBeanValidator.onError(actResponse, result); }
				 */
		}

		if (actResponse != null && actResponse.getResponse() != null
				&& actResponse.getResponse().toUpperCase().equals(ResponseType.Fail.toString())) {
			GrievanceBeanValidator.onError(actResponse, result);
			modelMap.addAttribute(MainetConstants.GrievanceConstants.ACTIONRESPONSE, actResponse);
			modelMap.put(MainetConstants.GrievanceConstants.DEPARTMENTS,
					departmentComplaintService.getCareWorkflowMasterDefinedDepartmentsListByOrgId(Utility.getOrgId()));

			if (isKDMCEnvPresent()
					&& UserSession.getCurrent().getEmployee().getEmploginname().equalsIgnoreCase("NOUSER")) {
				modelAndView = new ModelAndView("NewComplaintRegistrationFormKDMCPreLoginValid",
						MainetConstants.FORM_NAME, model);
			} else {

				modelAndView = new ModelAndView(MainetConstants.GrievanceConstants.NEWCOMPLAINTREGISTRATIONFORMVALID,
						MainetConstants.FORM_NAME, model);
			}
		} else {

			applicantDetailDto.setOrgId(organisation);
			applicantDetailDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			applicantDetailDto.setLangId(new Long(UserSession.getCurrent().getLanguageId()));

			careRequest.setOrgId(organisation);
			careRequest.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			careRequest.setCreatedDate(new Date());
			careRequest.setRequestType(MainetConstants.GrievanceConstants.REQUESTOR_ACTION);
			// careRequest.setReferenceMode(model.getReferenceMode());

			startAction.setComments(careRequest.getDescription());

			// told by RJ to set in tb_workflow_request EMPID column
			startAction.setEmpId(Long.parseLong(applicantDetailDto.getMobileNo()));
			startAction.setCreatedBy(Long.parseLong(applicantDetailDto.getMobileNo()));

			startAction.setEmpType(model.getEmplType());
			startAction.setOrgId(organisation);

			GrievanceReqDTO reqDTO = new GrievanceReqDTO();
			reqDTO.setReopen(false);
			reqDTO.setApplicantDetailDto(applicantDetailDto);
			reqDTO.setCareRequest(careRequest);
			reqDTO.setAction(startAction);
			reqDTO.setAttachments(setFileUploadMethod(model.getAttachments()));

			LOGGER.info("Making Jersey Call to Grievance Service Rest Controller");
			LinkedHashMap<Long, Object> responseVo = null;
			try {
				responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClientForJBPM(reqDTO,
						ServiceEndpoints.CARE_SERVICE_SAVE_GRIEVANCE_URL);
			} catch (Exception e) {
				modelAndView = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW, MainetConstants.FORM_NAME,
						model);
				return modelAndView;
			}
			String d = new JSONObject(responseVo).toString();
			actResponse = new ObjectMapper().readValue(d, ActionResponseDTO.class);
			if (actResponse.getResponse().toUpperCase().equals(ResponseType.Success.toString())) {
				List<LocationDTO> locations = locationService.getLocationByOrgId(organisation,
						UserSession.getCurrent().getLanguageId());
				modelMap.put(MainetConstants.GrievanceConstants.DEPARTMENTS,
						departmentComplaintService.getCareWorkflowMasterDefinedDepartmentsListByOrgId(organisation));
				modelMap.put(MainetConstants.GrievanceConstants.LOCATIONS, locations);
				long requestNo = Long.parseLong(actResponse.getResponseData("requestNo"));
				modelMap.addAttribute(MainetConstants.GrievanceConstants.COMPLAINTACKNOWLEDGEMENTMODEL,
						careRequestService.constructRequestStatusAcknowledgement(requestNo,
								UserSession.getCurrent().getLanguageId()));
				modelMap.put("applicationId", requestNo);
				
				if (isDSCLEnvPresent()) {
					String sbmComplaintNo = actResponse.getResponseData("SBMResponse");
					modelMap.put("sbmComplaintNo", sbmComplaintNo);
				}
				
				
				LOGGER.info("Grievance request submitted successfully");
				modelMap.put("kdmcEnv", model.getKdmcEnv());
				/*
				 * D#120577 -> to get logo based on org selection in complaint form only for
				 * dehradoon Environment
				 */
				final Organisation orgDetails = organisatonService.getOrganisationById(organisation);
				if (orgDetails != null && orgDetails.getOLogo() != null) {
					final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
							+ MainetConstants.FILE_PATH_SEPARATOR + "SHOW_DOCS" + MainetConstants.FILE_PATH_SEPARATOR
							+ organisation;
					orgLogoPath = Utility.downloadedFileUrl(orgDetails.getOLogo(), outputPath,
							FileNetApplicationClient.getInstance());
				} else {
					orgLogoPath = null;
				}
				modelMap.put("orgLogoPath", orgLogoPath);
				modelAndView = new ModelAndView(MainetConstants.GrievanceConstants.REGISTRATIONACKNOWLEDGEMENTRECEIPT,
						MainetConstants.GrievanceConstants.CARE_REQUEST, careRequest);
			} else {
				// D#123953
				result.reject(ApplicationSession.getInstance().getMessage("workflow.NoWorkflow.define"));
				GrievanceBeanValidator.onError(actResponse, result);
				modelMap.addAttribute(MainetConstants.GrievanceConstants.ACTIONRESPONSE, actResponse);
				if (isKDMCEnvPresent()
						&& UserSession.getCurrent().getEmployee().getEmploginname().equalsIgnoreCase("NOUSER")) {
					modelAndView = new ModelAndView("NewComplaintRegistrationFormKDMCPreLoginValid",
							MainetConstants.FORM_NAME, model);
				} else {

					modelAndView = new ModelAndView(
							MainetConstants.GrievanceConstants.NEWCOMPLAINTREGISTRATIONFORMVALID,
							MainetConstants.FORM_NAME, model);
				}
			}
		}
		return modelAndView;
	}

	/**
	 * 
	 * @param careRequest
	 * @param result
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @throws URISyntaxException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = MainetConstants.GrievanceConstants.SAVE_REOPEN_DETAILS, method = RequestMethod.POST)
	public ModelAndView reopenComplaint(
			@ModelAttribute(MainetConstants.GrievanceConstants.CARE_REQUEST) CareRequestDTO CareRequestDTO,
			BindingResult result, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes redirectAttributes) throws URISyntaxException, Exception {
		LOGGER.info("Reopening Grievance Request from reopenComplaint of GrievancePortalController");

		getModel().bind(request);
		ModelAndView mv = null;
		ComplaintRegistrationModel model = getModel();
		ActionDTO reopenAction = model.getAction();

		CareRequestDTO careRequest = careRequestService
				.getCareRequestByapplicationId(reopenAction.getApplicationId().toString());
		reopenAction.setApplicationId(careRequest.getApplicationId());
		reopenAction.setCreatedDate(new Date());
		// told by RJ to set in tb_workflow_request EMPID column
		reopenAction.setEmpId(Long.parseLong(UserSession.getCurrent().getEmployee().getEmpmobno()));
		reopenAction.setCreatedBy(Long.parseLong(UserSession.getCurrent().getEmployee().getEmpmobno()));

		reopenAction.setEmpType(model.getEmplType());
		reopenAction.setOrgId(Utility.getOrgId());

		GrievanceReqDTO reqDTO = new GrievanceReqDTO();
		reqDTO.setCareRequest(careRequest);
		reqDTO.setAction(reopenAction);
		reqDTO.setReopen(true);
		reqDTO.setAttachments(setFileUploadMethod(model.getAttachments()));
		
		//#144732 check for mobile no while reopening by other user
		ComplaintRegistrationAcknowledgementDTO ackm= new ComplaintRegistrationAcknowledgementDTO();
		if (careRequest.getApplicationId() != null) 
			ackm = careRequestService
					.constructRequestStatusAcknowledgement(careRequest.getApplicationId(), UserSession.getCurrent().getLanguageId());
		
		if(ackm.getComplainantMobileNo() != null) {
	    String complainantMobileNo = ackm.getComplainantMobileNo();
			if (!(UserSession.getCurrent().getEmployee().getEmpmobno().equalsIgnoreCase(complainantMobileNo))) {
				getModel().addValidationError(getApplicationSession().getMessage("care.cant.reopen.otheruser"));
				mv = new ModelAndView(MainetConstants.GrievanceConstants.REOPENGRIEVANCEFORMVALID,
					MainetConstants.FORM_NAME, getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		    return mv;
		}
		}

		ActionResponseDTO actResponse = new ActionResponseDTO();
		if (result != null) {
			if (result.hasErrors()) {
				final ActionResponseDTO actFailedResponse = new ActionResponseDTO(ResponseType.Fail.toString());
				GrievanceBeanValidator.onError(actFailedResponse, result);
				actResponse = actFailedResponse;
			} else {
				LOGGER.info("Making Jersey Call to Grievance Service Rest Controller");

				LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
						.callRestTemplateClientForJBPM(reqDTO, ServiceEndpoints.CARE_SERVICE_REOPEN_GRIEVANCE_URL);
				String actResponse_ = new JSONObject(responseVo).toString();
				actResponse = new ObjectMapper().readValue(actResponse_, ActionResponseDTO.class);
			}
		}

		if (actResponse.getResponse().toUpperCase().equals(ResponseType.Success.toString())) {
			careRequest = careRequestService.getCareRequestById(careRequest.getId());
			List<LocationDTO> locations = locationService.getLocationByOrgId(Utility.getOrgId(),
					UserSession.getCurrent().getLanguageId());
			modelMap.put(MainetConstants.GrievanceConstants.DEPARTMENTS,
					departmentComplaintService.getCareWorkflowMasterDefinedDepartmentsListByOrgId(Utility.getOrgId()));
			modelMap.put(MainetConstants.GrievanceConstants.LOCATIONS, locations);
			long requestNo = Long.parseLong(actResponse.getResponseData("requestNo"));
			modelMap.addAttribute(MainetConstants.GrievanceConstants.COMPLAINTACKNOWLEDGEMENTMODEL, careRequestService
					.constructRequestStatusAcknowledgement(requestNo, UserSession.getCurrent().getLanguageId()));
			LOGGER.info("Grievance request reopned successfully");
			mv = new ModelAndView(MainetConstants.GrievanceConstants.REGISTRATIONACKNOWLEDGEMENTRECEIPT,
					MainetConstants.GrievanceConstants.CARE_REQUEST, careRequest);
		} else {
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
				getModel().addValidationError(actResponse.getResponseData().get("errorMessage"));
			else
				getModel().addValidationError(actResponse.getResponseData().get("errorMessageReg"));
			mv = new ModelAndView(MainetConstants.GrievanceConstants.REOPENGRIEVANCEFORMVALID,
					MainetConstants.FORM_NAME, getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		}
		return mv;
	}

	/**
	 * 
	 * @param careRequest
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(params = MainetConstants.GrievanceConstants.GET_ALL_GRIEVANCE_RAISED_BY_REGISTERED_CITIZEN_VIEW, method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getAllComplaintRaisedByRegisteredCitizenView(
			@ModelAttribute(MainetConstants.GrievanceConstants.CARE_REQUEST) CareRequestDTO careRequest,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		LOGGER.info("Request to get all Complaint raised by citizen | "
				+ UserSession.getCurrent().getEmployee().getEmpId());
		// doing because when application punch from portal side than in place of EMPID
		// set Mobile no told by RJ
		// and data fetch from COMPLAINTREGISTER VIEW(here in place of EMPID<->MOB_NO
		// only in case of portal)
		Long searchValue = UserSession.getCurrent().getEmployee().getEmpId();
		if (StringUtils.isNotEmpty(UserSession.getCurrent().getEmployee().getEmpmobno())) {
			searchValue = Long.parseLong(UserSession.getCurrent().getEmployee().getEmpmobno());
		}
		List<CareRequestDTO> requestLists = careRequestService.getAllComplaintRaisedByCitizenById(searchValue,
				UserSession.getCurrent().getEmployee().getEmplType(), null);
		model.put(MainetConstants.GrievanceConstants.REQUESTLISTS, requestLists);
		// D#112758
		requestLists.removeIf(care -> care.getStatus()
				.equals(MainetConstants.ApplicationMasterConstant.APPLICATION_STATUS_PENDING)
				|| care.getStatus().equals(MainetConstants.ApplicationMasterConstant.APPLICATION_STATUS_REJECTED));
		model.put("requestLists", requestLists);

		return new ModelAndView(MainetConstants.GrievanceConstants.REOPENGRIEVANCEFORM, MainetConstants.FORM_NAME,
				getModel());

	}

	/**
	 * 
	 * @param searchString
	 * @param careRequest
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = MainetConstants.GrievanceConstants.SEARCH_GRIEVANCE_RAISED_BY_REGISTERED_CITIZEN, method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView searchGrievanceRaisedByRegisteredCitizen(
			@RequestParam(MainetConstants.GrievanceConstants.SEARCHSTRING) String searchString,
			@ModelAttribute(MainetConstants.GrievanceConstants.CARE_REQUEST) CareRequestDTO careRequest,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView mv = null;
		List<CareRequestDTO> careObj = null;
		List<CareRequestDTO> requestLists = new ArrayList<>();
		Long searchValue = null;
		bindModel(request);
		LOGGER.info("Search request to get all Complaint raised by citizen | "
				+ UserSession.getCurrent().getEmployee().getEmpId());

		/* To reopen complaint which was saved from service side for same mob no */
		if (StringUtils.isNotEmpty(searchString)) {
			CareRequestDTO reqDto = new CareRequestDTO();
			reqDto.setComplaintId(searchString.trim());
			reqDto.setApaMobNo(UserSession.getCurrent().getEmployee().getEmpmobno());
			/* it will fetch only one record by referenceId */
			careObj = careRequestService.getComplaintByRefIdAndMobNo(reqDto);
			if (CollectionUtils.isNotEmpty(careObj)) {
				requestLists.addAll(careObj);
			}
		} else {
			// doing because when application punch from portal side than in place of EMPID
			// set Mobile no told by RJ
			// and data fetch from COMPLAINTREGISTER VIEW(here in place of EMPID<->MOB_NO
			// only in case of portal)
			searchValue = UserSession.getCurrent().getEmployee().getEmpId();
			if (StringUtils.isNotEmpty(UserSession.getCurrent().getEmployee().getEmpmobno())) {
				searchValue = Long.parseLong(UserSession.getCurrent().getEmployee().getEmpmobno());
			}
			requestLists = careRequestService.getAllComplaintRaisedByCitizenById(searchValue,
					UserSession.getCurrent().getEmployee().getEmplType(), searchString.toString().trim());
		}
		LOGGER.info("before final list requestLists is " + requestLists.isEmpty());

		// <!-- D#112758 --> validation for rejected and pending complaint if user input
		if (requestLists.size() == 1 && requestLists.get(0).getStatus()
				.equals(MainetConstants.ApplicationMasterConstant.APPLICATION_STATUS_PENDING)) {
			getModel().addValidationError(getApplicationSession().getMessage("care.cant.reopen.pending.invalid"));
		} else if (requestLists.size() == 1 && requestLists.get(0).getLastDecision()
				.equals(MainetConstants.ApplicationMasterConstant.APPLICATION_FORCE_CLOSURE)) {
			getModel().addValidationError(getApplicationSession().getMessage("care.cant.reopen.forceclosure.invalid"));
		} else if (requestLists.size() == 1 && requestLists.get(0).getStatus()
				.equals(MainetConstants.ApplicationMasterConstant.APPLICATION_STATUS_REJECTED)) {
			getModel().addValidationError(getApplicationSession().getMessage("care.cant.reopen.rejected.invalid"));
		} else {

			requestLists.removeIf(care -> care.getStatus()
					.equals(MainetConstants.ApplicationMasterConstant.APPLICATION_STATUS_PENDING)
					|| care.getStatus().equals(MainetConstants.ApplicationMasterConstant.APPLICATION_STATUS_REJECTED));
			model.put("requestLists", requestLists);
		}

		if (requestLists.isEmpty()) {
			getModel().addValidationError(getApplicationSession().getMessage("care.token.invalid"));
		} else {
			LOGGER.info("requestLists came " + requestLists.get(0).getComplaintId());
		}
		mv = new ModelAndView(MainetConstants.GrievanceConstants.REOPENGRIEVANCEFORMVALID, MainetConstants.FORM_NAME,
				getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		LOGGER.info("final list requestLists done");
		return mv;
	}

	/**
	 * 
	 * @param careRequest
	 * @param result
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(params = MainetConstants.GrievanceConstants.GRIEVANCE_STATUS, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView verifyComplaintRegistrationStatus(
			@ModelAttribute(MainetConstants.GrievanceConstants.CARE_REQUEST) CareRequestDTO careRequest,
			BindingResult result, HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("Opening Complaint status request form");
		sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		final Employee employee = UserSession.getCurrent().getEmployee();

		if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
				.equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
			getModel().setLoginFlag("N");
			return new ModelAndView(MainetConstants.GrievanceConstants.REGISTRATIONSTATUSPRELOGIN,
					MainetConstants.FORM_NAME, getModel());
		} else if (null != employee && null != employee.getLoggedIn()
				&& employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {
			return new ModelAndView(MainetConstants.GrievanceConstants.REGISTRATIONSTATUSPRELOGIN,
					MainetConstants.FORM_NAME, getModel());
		} else {
			return new ModelAndView(MainetConstants.GrievanceConstants.REGISTRATIONSTATUS,
					MainetConstants.GrievanceConstants.CARE_REQUEST, careRequest);
		}
	}

	/**
	 * 
	 * @param searchString
	 * @param careRequest
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = MainetConstants.GrievanceConstants.DISPLAY_STATUS, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView displayComplaintAcknowledgementStatus(
			@RequestParam(MainetConstants.GrievanceConstants.SEARCHSTRING) String searchString,
			@ModelAttribute(MainetConstants.GrievanceConstants.CARE_REQUEST) CareRequestDTO careRequest,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		LOGGER.info("Opening Complaint request with action history of applicationId | " + searchString);

		sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();

		if (searchString != null) {
			ComplaintRegistrationAcknowledgementDTO ackm = careRequestService
					.constructRequestStatusAcknowledgement(searchString, UserSession.getCurrent().getLanguageId());
			if (ackm != null) {
				this.getModel().setComplaintAcknowledgementModel(ackm);

				@SuppressWarnings("unchecked")
				LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
						null, ServiceEndpoints.CARE_SERVICE_GET_FEEDBACK_URL + ackm.getTokenNumber());
				String d = new JSONObject(responseVo).toString();
				CareFeedbackDTO feedbackDetails = new ObjectMapper().readValue(d, CareFeedbackDTO.class);
				if (feedbackDetails != null) {
					this.getModel().setFeedbackDetails(feedbackDetails);
				}

				ModelAndView modelAndView = new ModelAndView(
						MainetConstants.GrievanceConstants.REGISTRATIONACKNOWLEDGEMENTRECEIPTSTATUS,
						MainetConstants.GrievanceConstants.CARE_REQUEST, this.getModel());

				return modelAndView;
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	@RequestMapping(params = "displayStatusPreLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView displayComplaintAckStatus(
			@RequestParam(MainetConstants.GrievanceConstants.SEARCHSTRING) Long searchString,
			@ModelAttribute(MainetConstants.GrievanceConstants.CARE_REQUEST) CareRequestDTO careRequest,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		LOGGER.info("Opening Complaint request with action history of applicationId | " + searchString);
		sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		ComplaintRegistrationModel model = this.getModel();

		if (searchString != null) {
			List<CareRequestDTO> ackm = careRequestService.complaintStatusAcknowledgement(searchString);
			if (ackm.size() != 0) {
				if (ackm.size() == 1) {
					ComplaintRegistrationAcknowledgementDTO ackDTO = careRequestService
							.constructRequestStatusAcknowledgement(Long.valueOf(ackm.get(0).getApplicationId()),
									UserSession.getCurrent().getLanguageId());
					if (ackDTO != null) {
						model.setComplaintAcknowledgementModel(ackDTO);

						@SuppressWarnings("unchecked")
						LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
								.callRestTemplateClient(null,
										ServiceEndpoints.CARE_SERVICE_GET_FEEDBACK_URL + ackDTO.getTokenNumber());
						String d = new JSONObject(responseVo).toString();
						CareFeedbackDTO feedbackDetails = new ObjectMapper().readValue(d, CareFeedbackDTO.class);
						if (feedbackDetails != null) {
							this.getModel().setFeedbackDetails(feedbackDetails);
						}

						return new ModelAndView(
								MainetConstants.GrievanceConstants.REGISTRATIONACKNOWLEDGEMENTRECEIPTSTATUS,
								MainetConstants.GrievanceConstants.CARE_REQUEST, model);
					} else {
						return null;
					}
				} else {
					getModel().setCareDTO(ackm);
					return new ModelAndView(MainetConstants.GrievanceConstants.GRIEVANCECOMPLAINTACKLIST,
							MainetConstants.FORM_NAME, getModel());
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param documentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = MainetConstants.GrievanceConstants.GET_ACTION_HISTORY_BY_APPLICATIONID, method = RequestMethod.POST)
	@ResponseBody
	public List<ActionDTOWithDoc> getActionHistoryByDocumentId(
			@RequestParam(value = MainetConstants.GrievanceConstants.APPLICATION_ID, required = true) Long applicationId)
			throws Exception {
		LOGGER.info("Retriving all action taken on Complaint request of applicationId | " + applicationId);
		List<ActionDTOWithDoc> actionList = careRequestService.getWorkflowActionLogByApplicationId(applicationId,
				Utility.getOrgId(), UserSession.getCurrent().getLanguageId());
		return actionList;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = MainetConstants.GrievanceConstants.FIND_DEPARTMENT_COMPLAINT_BY_DEPARTMENTID, method = RequestMethod.POST)
	@ResponseBody
	public List<DepartmentComplaintTypeDTO> findDepartmentComplaintByDepartmentId(
			@RequestParam(value = "id", required = true) Long id) throws Exception {
		LOGGER.info("Retriving Department Complaint Type of Department id | " + id);
		DepartmentComplaintDTO departmentComplaint = departmentComplaintService.getDepartmentComplaintByDepartmentId(id,
				Utility.getOrgId());
		return departmentComplaint.getComplaintTypes();
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = MainetConstants.GrievanceConstants.GET_CARE_WORKFLOWMASTER_DEFINED_DEPARTMENT_COMPLAINTTYPE_BY_DEPARTMENTID, method = RequestMethod.POST)
	@ResponseBody
	public List<DepartmentComplaintTypeDTO> getCareWorkflowMasterDefinedDepartmentComplaintTypeByDepartmentId(
			@RequestParam(value = "id", required = true) Long id) throws Exception {
		LOGGER.info("Retriving Workflow defined Department Complaint Type of Department id | " + id);
		List<DepartmentComplaintTypeDTO> departmentComplaintType = departmentComplaintService
				.getCareWorkflowMasterDefinedDepartmentComplaintTypeByDepartmentId(id, Utility.getOrgId());
		return departmentComplaintType;
	}

	/**
	 * 
	 * @param careRequest
	 * @param result
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = MainetConstants.GrievanceConstants.SHOW_FEEDBACK_DETAILS, method = { RequestMethod.POST })
	public ModelAndView provideFeedback(
			@ModelAttribute(MainetConstants.GrievanceConstants.CARE_REQUEST) CareRequestDTO careRequest,
			BindingResult result, HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		LOGGER.info("Opening Complaint Request feedback form");
		sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		getModel().bind(request);
		String tokenNumber = request.getParameter(MainetConstants.GrievanceConstants.REQUEST_NO);
		CareFeedbackDTO reqDTO = new CareFeedbackDTO();
		reqDTO.setTokenNumber(tokenNumber);
		LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
				ServiceEndpoints.CARE_SERVICE_GET_FEEDBACK_URL + tokenNumber);
		String d = new JSONObject(responseVo).toString();
		CareFeedbackDTO feedbackDetails = new ObjectMapper().readValue(d, CareFeedbackDTO.class);
		if (feedbackDetails != null) {
			this.getModel().setFeedbackDetails(feedbackDetails);
		}
		model.addAttribute(MainetConstants.GrievanceConstants.TOKENNUMBER, tokenNumber);
		return new ModelAndView(MainetConstants.GrievanceConstants.COMPLAINTFEEDBACK, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(params = MainetConstants.GrievanceConstants.SAVE_FEEDBACK_DETAILS, method = RequestMethod.POST)
	public @ResponseBody String submitFeedback(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = MainetConstants.GrievanceConstants.REQUEST_NO, required = true) String requestNo,
			@RequestParam(value = MainetConstants.GrievanceConstants.RATING_INPUT, required = true) String ratinginput,
			@RequestParam(value = MainetConstants.GrievanceConstants.RATING_CONTENT, required = true) String ratingcontent,
			@RequestParam(value = MainetConstants.GrievanceConstants.FEEDBACK_ID) Long feedbackId,
			final RedirectAttributes redirectAttributes) throws URISyntaxException, Exception {

		LOGGER.info("Submitting Grievance Request feedback for applicationId |" + requestNo);
		String decisionResponse = null;
		CareFeedbackDTO careFeedback = new CareFeedbackDTO();
		careFeedback.setRatings(MainetConstants.GrievanceConstants.RATINGS.get(ratinginput));
		careFeedback.setRatingsContent(ratingcontent);
		careFeedback.setTokenNumber(requestNo);
		careFeedback.setRatingsStarCount(Integer.parseInt(ratinginput));
		if (feedbackId != null) {
			careFeedback.setId(feedbackId);
		}

		GrievanceReqDTO reqDTO = new GrievanceReqDTO();
		reqDTO.setCareFeedback(careFeedback);

		@SuppressWarnings("unchecked")
		LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClientForJBPM(reqDTO, ServiceEndpoints.CARE_SERVICE_SAVE_FEEDBACK_URL);
		String actResponse_ = new JSONObject(responseVo).toString();
		ActionResponseDTO actResponse = new ObjectMapper().readValue(actResponse_, ActionResponseDTO.class);

		if (actResponse.getResponseData(MainetConstants.GrievanceConstants.RESPONSE_)
				.equals(MainetConstants.GrievanceConstants.ALERT_SAVE_SUCCESS)) {
			// decisionResponse =
			// MainetConstants.GrievanceConstants.FEEDBACK_SAVED_SUCCESSFULLY;
			decisionResponse = ApplicationSession.getInstance().getMessage("care.feedback.save");
		}
		LOGGER.info("Grievance Request feedback submitted successfully for applicationId |" + requestNo);
		return decisionResponse;
	}

	@RequestMapping(params = MainetConstants.GrievanceConstants.GET_PINCODE_BY_LOCATION_ID, method = RequestMethod.POST)
	@ResponseBody
	public Long getPincodeByLocationId(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "orgId", required = true) Long orgId) throws Exception {
		LOGGER.info("Retriving location(pincode) by id | " + id);
		Long organistion = null;
		if (orgId != null) {
			organistion = orgId;
		} else {
			organistion = Utility.getOrgId();
		}
		List<LocationDTO> locationList = locationService.getLocationByOrgId(organistion,
				UserSession.getCurrent().getLanguageId());
		Long pinCode = 0L;
		for (LocationDTO loc : locationList) {
			if (id != null) {
				if (loc.getLocId().equals(id)) {
					pinCode = loc.getPincode();
					break;
				}
			}
		}
		return pinCode;
	}

	@RequestMapping(params = MainetConstants.GrievanceConstants.GET_ALL_LOCATIONS_BY_PINCODE, method = RequestMethod.POST)
	@ResponseBody
	public List<LocationDTO> getAllLocationsByPinCodeAjax(
			@RequestParam(value = MainetConstants.GrievanceConstants.PINCODE, required = true) Integer pinCode)
			throws Exception {
		LOGGER.info("Retriving location by pincode | " + pinCode);

		List<LocationDTO> locationList = new ArrayList<>();
		if (pinCode != null) {
			locationList = locationService.getLocationByPinCode(pinCode, UserSession.getCurrent().getLanguageId());
		}
		return locationList;
	}

	@RequestMapping(params = MainetConstants.GrievanceConstants.GET_UPLOADDED_FILES, method = { RequestMethod.POST })
	@ResponseBody
	public Map<Long, Set<File>> getUploaddedCount(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return FileUploadUtility.getCurrent().getFileMap();
	}

	@RequestMapping(params = MainetConstants.GrievanceConstants.GRIEVANCE_COMPLAINT_TYPES, method = RequestMethod.POST)
	public @ResponseBody Set<LookUp> getComplaintTypes(
			@RequestParam(MainetConstants.GrievanceConstants.DEPT_ID) Long deptId, @RequestParam("orgId") Long orgId)
			throws Exception {
		// ModelAndView modelAndView = null;
		Long organistion = null;
		if (orgId != null) {
			organistion = orgId;
		} else {
			organistion = Utility.getOrgId();
		}
		List<DepartmentComplaintTypeDTO> complaintTypes = departmentComplaintService
				.getCareWorkflowMasterDefinedDepartmentComplaintTypeByDepartmentId(deptId, organistion);
		if (complaintTypes != null && !complaintTypes.isEmpty()) {
			Set<LookUp> complaintTypes1 = new HashSet<>();
			for (DepartmentComplaintTypeDTO c : complaintTypes) {
				LookUp detData = new LookUp();
				detData.setLookUpId(c.getCompId());
				detData.setDescLangFirst(c.getComplaintDesc());
				detData.setDescLangSecond(c.getComplaintDescReg());
				detData.setLookUpDesc(detData.getLookUpDesc());
				complaintTypes1.add(detData);
			}
			this.getModel().setComplaintTypes(complaintTypes1);
			/*
			 * modelAndView = new
			 * ModelAndView(MainetConstants.GrievanceConstants.GRIEVANCE_COMPLAINT_TYPES,
			 * MainetConstants.FORM_NAME, getModel());
			 */
		}
		return this.getModel().getComplaintTypes();
	}

	@ResponseBody
	@RequestMapping(params = "checkComplaintTypeData", method = RequestMethod.POST)
	public DepartmentComplaintTypeDTO checkComplaintTypeData(
			@RequestParam("complaintTypeId") final Long complaintTypeId, final HttpServletRequest request)
			throws Exception {
		getModel().bind(request);
		DepartmentComplaintTypeDTO complaintType = departmentComplaintService
				.getDepartmentComplaintTypeById(complaintTypeId);
		return complaintType;
	}

	@RequestMapping(params = MainetConstants.GrievanceConstants.GRIEVANCE_LOCATIONS, method = RequestMethod.POST)
	public @ResponseBody Set<LookUp> getLocations(@RequestParam("deptId") Long deptId,
			@RequestParam("orgId") Long orgId) throws Exception {
		// ModelAndView modelAndView = null;
		Long organistion = null;
		if (orgId != null) {
			organistion = orgId;
		} else {
			organistion = Utility.getOrgId();
		}
		List<LocationDTO> locs = careRequestService.getLocationByOrgIdAndDeptId(organistion, deptId,
				UserSession.getCurrent().getLanguageId());

		Set<LookUp> locations = new HashSet<>();
		for (LocationDTO l : locs) {
			LookUp detData = new LookUp();
			detData.setLookUpId(l.getLocId());
			detData.setDescLangFirst(l.getLocNameEng());
			detData.setDescLangSecond(l.getLocNameReg());
			detData.setLookUpDesc(detData.getLookUpDesc());
			locations.add(detData);
		}
		this.getModel().setLocations(locations);
		/*
		 * modelAndView = new
		 * ModelAndView(MainetConstants.GrievanceConstants.GRIEVANCE_LOCATIONS,
		 * MainetConstants.FORM_NAME, getModel());
		 */

		return this.getModel().getLocations();
	}

	@RequestMapping(params = "findWardZone", method = RequestMethod.POST)
	public ModelAndView getGrievanceOpWardZone(HttpServletRequest request) throws Exception {
		getModel().bind(request);
		ModelAndView mv = null;
		getModel().getCareRequestDto().setOrgId(Utility.getOrgId());
		// return
		// careRequestService.getGrievanceOpWardZone(getModel().getCareRequestDto());
		CareRequestDTO careRequestDTO = careRequestService.getGrievanceOpWardZone(getModel().getCareRequestDto());
		getModel().setCareRequestDto(careRequestDTO);
		// return new
		// ModelAndView("grievanceLocationMappingWardZone",MainetConstants.FORM_NAME,
		// getModel());
		Employee emp = UserSession.getCurrent().getEmployee();
		if (emp.getEmploginname().equalsIgnoreCase("NOUSER")) {
			getModel().setLoginFlag("N");

		}

		if (StringUtils.isEmpty(careRequestDTO.getPrefixName())) {
			getModel().addValidationError(
					getApplicationSession().getMessage("care.validate.department.prefix.notdefined"));
		} else {
			getModel().getCareRequestDto().setWard1(null);
			getModel().getCareRequestDto().setWard2(null);
			getModel().getCareRequestDto().setWard3(null);
			getModel().getCareRequestDto().setWard4(null);
			getModel().getCareRequestDto().setWard5(null);
		}

		// D#127037 reset flat no list
		getModel().getCareRequestDto().setPropFlatNo(null);
		getModel().setFlatNoList(new ArrayList<>());

		if (isKDMCEnvPresent() && UserSession.getCurrent().getEmployee().getEmploginname().equalsIgnoreCase("NOUSER")) {
			mv = new ModelAndView("NewComplaintRegistrationFormKDMCPreLoginValid", MainetConstants.FORM_NAME,
					getModel());
		} else {
			mv = new ModelAndView(MainetConstants.GrievanceConstants.NEWCOMPLAINTREGISTRATIONFORMVALID,
					MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	@RequestMapping(params = MainetConstants.GrievanceConstants.VALIDATE_MOBILE_OTP, method = RequestMethod.POST)
	public @ResponseBody boolean validateMobileOtp(@RequestParam("mobileOTP") String mobileOTP,
			HttpServletRequest request) throws Exception {
		getModel().bind(request);
		ComplaintRegistrationModel model = getModel();
		if (model.getGeneratedMobileOTP() == null || mobileOTP == null)
			return false;
		return model.getGeneratedMobileOTP().equals(mobileOTP);
	}

	private void setApplicantDetail(Employee emp, ComplaintRegistrationModel model) {
		Organisation org = organisatonService.getOrganisationById(Utility.getOrgId());
		model.getApplicantDetailDTO().setEmpId(emp.getId());
		model.getApplicantDetailDTO().setfName(emp.getEmpname());
		model.getApplicantDetailDTO().setmName(emp.getEmpMName());
		model.getApplicantDetailDTO().setlName(emp.getEmpLName());
		model.getApplicantDetailDTO().setMobileNo(emp.getEmpmobno());
		model.getApplicantDetailDTO().setEmail(emp.getEmpemail());
		model.getApplicantDetailDTO().setAreaName(
				emp.getEmpAddress() + MainetConstants.operator.COMA + MainetConstants.NEWLINE + emp.getEmpAddress1());
		if (emp.getPincode() != null && StringUtils.isNotBlank(emp.getPincode()))
			model.getApplicantDetailDTO().setPincodeNo(Long.parseLong(emp.getPincode()));
		List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.GENDER, org);
		List<LookUp> lookUpsTitle = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.TITLE, org);
		for (LookUp lookUp : lookUps) {
			if (emp.getEmpGender() != null && !emp.getEmpGender().isEmpty()) {
				if (lookUp.getLookUpCode().equals(emp.getEmpGender())) {
					model.getApplicantDetailDTO().setGender("" + lookUp.getLookUpId());
					/* model.setGender(lookUp.getLookUpDesc()); */
					break;
				}
			}
		}
		LOGGER.info("Gender setted is : " + model.getApplicantDetailDTO().getGender());
		for (LookUp lookUp : lookUpsTitle) {
			if (emp.getTitle() != null) {
				if (lookUp.getLookUpId() == emp.getTitle()) {
					model.getApplicantDetailDTO().setTitleId(lookUp.getLookUpId());
					/* model.setTitle(lookUp.getLookUpDesc()); */
					break;
				}
			}
		}

	}

	private List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs) {
		Base64 base64 = null;
		List<File> list = null;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			list = new ArrayList<>(entry.getValue());
			for (final File file : list) {
				try {
					base64 = new Base64();
					final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
					DocumentDetailsVO d = new DocumentDetailsVO();
					d.setDocumentName(file.getName());
					d.setDocumentByteCode(bytestring);
					docs.add(d);

				} catch (final IOException e) {
					logger.error("Exception has been occurred in file byte to string conversions", e);
				}
			}
		}
		return docs;
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.GrievanceConstants.SEND_OTP)
	public @ResponseBody String sendOTP(@RequestParam String mobileNo, HttpServletRequest httpServletRequest) {
		String oldOtp = null;
		if (mobileNo == null || mobileNo.isEmpty()) {
			return "N";
		} else {
			oldOtp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
			this.getModel().setGeneratedMobileOTP(oldOtp);
			careRequestService.sendOTPDetails(mobileNo, this.getModel().getGeneratedMobileOTP(),
					this.getModel().getApplicantDetailDTO());
			return "Y";
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = "grievanceOrganisations")
	public @ResponseBody List<LookUp> grievanceOrganisations(@RequestParam Long districtId,
			HttpServletRequest httpServletRequest) {
		this.getModel().getOrgLookups().clear();
		this.getModel().setOrg(careRequestService.getOrgnizationsByDist(districtId));
		for (OrganisationDTO org : getModel().getOrg()) {
				LookUp lookup = new LookUp();
				lookup.setLookUpId(org.getOrgid().longValue());
				lookup.setLookUpCode(org.getOrgShortNm());
				lookup.setDescLangFirst(org.getONlsOrgname());
				lookup.setDescLangSecond(org.getONlsOrgnameMar());
				lookup.setLookUpDesc(lookup.getLookUpDesc());
				this.getModel().getOrgLookups().add(lookup);
					
		}
		/*
		 * return new ModelAndView(MainetConstants.GrievanceConstants.ORGANISATION,
		 * MainetConstants.FORM_NAME, getModel());
		 */
		return this.getModel().getOrgLookups();
	}

	@RequestMapping(method = RequestMethod.POST, params = "grievanceDepartments")
	public @ResponseBody List<LookUp> grievanceDepartments(@RequestParam Long orgId,
			HttpServletRequest httpServletRequest) {
		this.getModel().getDeptLookups().clear();
		try {
			this.getModel()
					.setDept(departmentComplaintService.getCareWorkflowMasterDefinedDepartmentsListByOrgId(orgId));
		} catch (Exception e) {
			logger.error("Exception has been occurred in Fetching Department", e);
		}
		for (DepartmentComplaintDTO dept : getModel().getDept()) {
			LookUp lookup = new LookUp();
			lookup.setLookUpId(dept.getDepartment().getDpDeptid());
			lookup.setLookUpCode(dept.getDepartment().getDpDeptcode());
			lookup.setDescLangFirst(dept.getDepartment().getDpDeptdesc());
			lookup.setDescLangSecond(dept.getDepartment().getDpNameMar());
			lookup.setLookUpDesc(lookup.getLookUpDesc());
			this.getModel().getDeptLookups().add(lookup);
		}
		return this.getModel().getDeptLookups();
	}

	/**
	 * 
	 * @param careRequest
	 * @param result
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(params = "complaintRegistrationStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView complaintRegistrationStatus(
			@ModelAttribute(MainetConstants.GrievanceConstants.CARE_REQUEST) CareRequestDTO careRequest,
			BindingResult result, HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("Opening Complaint status request form");
		sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		final Employee employee = UserSession.getCurrent().getEmployee();
		if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
				.equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
			getModel().setLoginFlag("N");
			return new ModelAndView("complaintRegistrationStatus", MainetConstants.FORM_NAME, getModel());
		} else if (null != employee && null != employee.getLoggedIn()
				&& employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {
			return new ModelAndView(MainetConstants.GrievanceConstants.REGISTRATIONSTATUSPRELOGIN,
					MainetConstants.FORM_NAME, getModel());
		} else {
			return new ModelAndView(MainetConstants.GrievanceConstants.REGISTRATIONSTATUS,
					MainetConstants.GrievanceConstants.CARE_REQUEST, getModel());
		}
	}

	/**
	 * 
	 * @param searchString
	 * @param careRequest
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "displayComplaintStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView displayComplaintStatus(
			@RequestParam(MainetConstants.GrievanceConstants.SEARCHSTRING) String searchString,
			@ModelAttribute(MainetConstants.GrievanceConstants.CARE_REQUEST) CareRequestDTO careRequest,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		LOGGER.info("Opening Complaint request with action history of Complaint | " + searchString);

		sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();

		if (searchString != null) {
			ComplaintRegistrationAcknowledgementDTO ackm = careRequestService
					.constructRequestStatusAcknowledgement(searchString, UserSession.getCurrent().getLanguageId());
			if (ackm != null) {
				this.getModel().setComplaintAcknowledgementModel(ackm);
				return new ModelAndView("complaintStatus", MainetConstants.GrievanceConstants.CARE_REQUEST,
						this.getModel());
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView dashboardView(@RequestParam("appId") final String appId,
			@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) throws Exception {
		this.sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		ComplaintRegistrationModel model = this.getModel();
		this.getModel().setSaveMode(MainetConstants.D2KFUNCTION.CPD_VALUE);
		this.getModel().setAppStatus(appStatus);
		// Long orgId = Utility.getOrgId();
		/*
		 * To get application No of care using complain no as in method parameter we are
		 * getting complaint no
		 */
		CareRequestDTO careRequestDTO= new CareRequestDTO();
		boolean isASCLENV = isASCLEnvPresent();
		if(isASCLENV){
			careRequestDTO = careRequestService.getCareRequestByapplicationId(appId);
			LOGGER.info("Inside dashbord view controller, inside ASCL Env if | " + appId);
		}
		else{
			LOGGER.info("Inside dashbord view controller, other than ASCL Env, inside else | " + appId);
			careRequestDTO = careRequestService.getCareRequestBycomplaintId(appId);
		}
		/*CareRequestDTO careRequestDTO = careRequestService.getCareRequestBycomplaintId(appId);*/
		
		boolean isDSCLENV = isDSCLEnvPresent();
			 if (isDSCLENV && careRequestDTO.getExternalWorkFlowFlag()!=null &&
					 careRequestDTO.getExternalWorkFlowFlag().equalsIgnoreCase(MainetConstants.FlagY)) {
				 this.getModel().setExternalStatus(MainetConstants.FlagY);
			 }
		/*
		 * D#120937 - passing orgId from careRequestDTO because in dehradoon Env.
		 * organisation is selected by user
		 */

		model.setDept(departmentComplaintService
				.getCareWorkflowMasterDefinedDepartmentsListByOrgId(careRequestDTO.getOrgId()));
		/*
		 * Defect #117485 - passing orgId from careRequestDTO because in dehradoon Env.
		 * organisation is selected by user
		 */
		GrievanceReqDTO grtoDto = careRequestService.getCareRequestDetails(careRequestDTO.getApplicationId(),
				careRequestDTO.getOrgId());
		this.getModel().setCareRequestDto(grtoDto.getCareRequest());
		this.getModel().setApplicantDetailDTO(grtoDto.getApplicantDetailDto());
		CareRequestDTO careDto = this.getModel().getCareRequestDto();
		/*
		 * D#120937 - passing orgId from careRequestDTO because in dehradoon Env.
		 * organisation is selected by user
		 */
		List<LocationDTO> locs = careRequestService.getLocationByOrgIdAndDeptId(careRequestDTO.getOrgId(),
				careDto.getDepartmentComplaint(), UserSession.getCurrent().getLanguageId());
		Set<LookUp> locations = new HashSet<>();
		for (LocationDTO l : locs) {
			LookUp detData = new LookUp();
			detData.setLookUpId(l.getLocId());
			detData.setDescLangFirst(l.getLocNameEng());
			detData.setDescLangSecond(l.getLocNameReg());
			detData.setLookUpDesc(detData.getLookUpDesc());
			locations.add(detData);
		}
		this.getModel().setLocations(locations);
		// added for defect#100980
		model.setAttachments(grtoDto.getAttachments());
		/*
		 * D#120937 - passing orgId from careRequestDTO because in dehradoon Env.
		 * organisation is selected by user
		 */
		List<DepartmentComplaintTypeDTO> complaintTypes = departmentComplaintService
				.getCareWorkflowMasterDefinedDepartmentComplaintTypeByDepartmentId(careDto.getDepartmentComplaint(),
						careRequestDTO.getOrgId());
		if (complaintTypes != null && !complaintTypes.isEmpty()) {
			Set<LookUp> complaintTypes1 = new HashSet<>();
			for (DepartmentComplaintTypeDTO c : complaintTypes) {
				LookUp detData = new LookUp();
				detData.setLookUpId(c.getCompId());
				detData.setDescLangFirst(c.getComplaintDesc());
				detData.setDescLangSecond(c.getComplaintDescReg());
				detData.setLookUpDesc(detData.getLookUpDesc());
				complaintTypes1.add(detData);
			}
			this.getModel().setComplaintTypes(complaintTypes1);
		}
		// Defect #110539
		StringBuffer applicantType = new StringBuffer();
		StringBuffer complaintLabel = new StringBuffer();
		List<LookUp> lookupList = CommonMasterUtility.getLookUpList("AC");
		if (CollectionUtils.isNotEmpty(lookupList))
			lookupList.forEach(lookup -> {
				if (applicantType != null && !applicantType.toString().isEmpty()) {
					applicantType.append(",");
				}
				if (lookup.getOtherField().equalsIgnoreCase(MainetConstants.FlagY)) {
					applicantType.append(lookup.getLookUpCode());
					complaintLabel.append(lookup.getDescLangFirst());
					complaintLabel.append(",");
				}
			});
		model.setApplicationType(applicantType.toString());
		model.setLabelType(complaintLabel.toString());
		boolean isKDMCENV = isKDMCEnvPresent();
		getModel().setKdmcEnv(MainetConstants.FlagN);
		if (isKDMCENV) {
			getModel().setKdmcEnv(MainetConstants.FlagY);
		}
		this.getModel().setOrg(careRequestService.getOrgnizationsByDist(grtoDto.getCareRequest().getDistrict()));
		//D#137609 code for set lookUp list based on orgId DSCL
		Map<Integer, List<LookUp>> lookupMap = new HashMap<>();
		lookupMap.put(1, this.getModel().getAlphaNumericSortedLevelDataByOrgId("CWZ",1,careRequestDTO.getOrgId()));
		if(careRequestDTO.getWard2()!= null) {
			lookupMap.put(2, getApplicationSession().getChildLookUpsFromParentId(careRequestDTO.getWard1()));
		}
		getModel().setLookupMap(lookupMap);
		
		ComplaintRegistrationAcknowledgementDTO ackm= new ComplaintRegistrationAcknowledgementDTO();
		if (careRequestDTO.getApplicationId() != null)
		ackm = careRequestService
		.constructRequestStatusAcknowledgement(careRequestDTO.getApplicationId(), UserSession.getCurrent().getLanguageId());
		
		if (ackm.getComplainantMobileNo() != null && !ackm.getComplainantMobileNo().isEmpty()){
		careRequestDTO.setApaMobNo(ackm.getComplainantMobileNo());
		model.setCareRequestDto(careRequestDTO);
		}
		
		if(ackm.getStatus() != null && !ackm.getStatus().isEmpty()){
		careRequestDTO.setStatus(ackm.getStatus());
		model.setCareRequestDto(careRequestDTO);
		}
		
		mv = new ModelAndView(MainetConstants.GrievanceConstants.NEWCOMPLAINTREGISTRATIONFORMVALID,
				MainetConstants.FORM_NAME, getModel());
		return mv;
	}

	@ResponseBody
	@RequestMapping(params = "checkDues", method = RequestMethod.POST)
	public String checkDues(@RequestParam("refNo") final String refNo, @RequestParam("deptId") final Long deptId,
			@RequestParam(value = "flatNo", required = false) String flatNo, final HttpServletRequest request) {
		getModel().bind(request);
		String message = "";
		Organisation org = organisatonService.getOrganisationById(Utility.getOrgId());
		Long orgId = org.getOrgid();
		CareRequestDTO careRequestDTO = getModel().getCareRequestDto();
		careRequestDTO.setExtReferNumber(refNo);
		careRequestDTO.setOrgId(orgId);
		careRequestDTO.setPropFlatNo(flatNo);
		Object responseVo = (Object) JersyCall.callRestTemplateClient(careRequestDTO,
				ServiceEndpoints.CARE_SERVICE_OUTSTANDING_DUES);

		/*
		 * Object responseObj = (Object) JersyCall.callRestTemplateClient(null,
		 * ServiceEndpoints.CARE_SERVICE_OUTSTANDING_DUES +
		 * MainetConstants.WINDOWS_SLASH + refNo + MainetConstants.WINDOWS_SLASH +
		 * deptId + MainetConstants.WINDOWS_SLASH + orgId);
		 */
		if (responseVo == null || responseVo == "")
			return "";

		message = (String) getApplicationSession().getMessage("care.dueAmt.exist");
		/*
		 * ComplaintRegistrationAcknowledgementDTO complaintAcknowledgementModel = new
		 * ObjectMapper().readValue(d, ComplaintRegistrationAcknowledgementDTO.class);
		 */
		return message;
	}

	@RequestMapping(params = "findOpWardZoneByReferenceNo", method = RequestMethod.POST)
	public ModelAndView findOpWardZoneByReferenceNo(final HttpServletRequest request) throws Exception {
		getModel().bind(request);
		ModelAndView mv = null;
		getModel().getCareRequestDto().setOrgId(Utility.getOrgId());
		// CareRequestDTO careRequestDTO =
		// careRequestService.getGrievanceOpWardZone(getModel().getCareRequestDto());
		CareRequestDTO careRequestDTO = getModel().getCareRequestDto();
		/*
		 * careRequestDTO.setDepartmentComplaint(deptId);
		 * careRequestDTO.setExtReferNumber(refNo);
		 * careRequestDTO.setPropFlatNo(flatNo);
		 */
		@SuppressWarnings("unchecked")
		LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(careRequestDTO, ServiceEndpoints.CARE_SERVICE_OP_WARD_ZONE_BY_REFNO);
		if (responseVo != null) {
			String d = new JSONObject(responseVo).toString();
			careRequestDTO = new ObjectMapper().readValue(d, CareRequestDTO.class);
		}
		getModel().setCareRequestDto(careRequestDTO);
		if (isKDMCEnvPresent() && UserSession.getCurrent().getEmployee().getEmploginname().equalsIgnoreCase("NOUSER")) {
			mv = new ModelAndView("NewComplaintRegistrationFormKDMCPreLoginValid", MainetConstants.FORM_NAME,
					getModel());
		} else {
			mv = new ModelAndView(MainetConstants.GrievanceConstants.NEWCOMPLAINTREGISTRATIONFORMVALID,
					MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(params = "fetchPropertyFlatNo", method = RequestMethod.POST)
	public List<String> fetchPropertyFlatNo(@RequestParam("refNo") final String refNo,
			final HttpServletRequest request) {
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// call for flat no list
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		if (StringUtils.isNotBlank(refNo)) {
			requestParam.put("propNo", refNo.replaceAll("\\s", ""));
		} else {
			LOGGER.info("reference no can't be empty");
		}

		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ApplicationSession.getInstance().getMessage("GET_FLAT_LIST_BY_PROP_NO"),
				requestParam);
		List<String> flatNoList = (List<String>) JersyCall.callRestTemplateClient(requestParam, uri.toString());
		if (flatNoList != null && !flatNoList.isEmpty()) {
			getModel().setFlatNoList(flatNoList);
			return flatNoList;
		} else {
			getModel().setFlatNoList(flatNoList);
			return flatNoList;
		}
		// return null;

	}

	public boolean isKDMCEnvPresent() {
		Organisation org = organisatonService.getOrganisationById(Utility.getOrgId());
		List<LookUp> envLookUpList = CommonMasterUtility.getLookUps("ENV", org);
		return envLookUpList.stream().anyMatch(env -> Arrays.asList(MainetConstants.ENVIRNMENT_VARIABLE.ENV_PRODUCT).contains(env.getLookUpCode().toUpperCase())
				&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
		// return true;
	}

	// Defect #127174 changes for showing feedback button in care closed application
	@RequestMapping(params = "viewCareFeedBack", method = RequestMethod.POST)
	public ModelAndView viewCareFeedBack(@RequestParam("appId") final String appId,
			@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest, ModelMap model)
			throws Exception {
		LOGGER.info("Opening Complaint Request feedback form");
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		String tokenNumber = appId;
		CareFeedbackDTO reqDTO = new CareFeedbackDTO();
		reqDTO.setTokenNumber(tokenNumber);
		LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
				ServiceEndpoints.CARE_SERVICE_GET_FEEDBACK_URL + tokenNumber);
		String d = new JSONObject(responseVo).toString();
		CareFeedbackDTO feedbackDetails = new ObjectMapper().readValue(d, CareFeedbackDTO.class);
		if (feedbackDetails != null) {
			this.getModel().setFeedbackDetails(feedbackDetails);
		}
		model.addAttribute(MainetConstants.GrievanceConstants.TOKENNUMBER, appId);
		model.addAttribute(MainetConstants.DASHBOARD.DASH_BOARD_FEEDBACK, "Y");
		return new ModelAndView("ComplaintFeedbackDash", MainetConstants.FORM_NAME, this.getModel());

	}

	// Defect #127174 changes for showing feedback button in care closed application
	@RequestMapping(params = "isFeedBackOnce", method = RequestMethod.POST)
	public @ResponseBody boolean isFeedBackOnce(@RequestParam("appId") final String appId, String appStatus,
			final HttpServletRequest httpServletRequest, ModelMap model) throws Exception {
		LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
				ServiceEndpoints.CARE_SERVICE_GET_FEEDBACK_URL + appId);
		String d = new JSONObject(responseVo).toString();
		CareFeedbackDTO feedbackDetails = new ObjectMapper().readValue(d, CareFeedbackDTO.class);
		if (feedbackDetails != null && feedbackDetails.getTokenNumber() != null) {
			return true;
		}
		return false;

	}
	
	@RequestMapping(params = "saveswm", method = RequestMethod.POST)
	public @ResponseBody boolean saveswm(final HttpServletRequest request, ModelMap model) {
		LOGGER.info("Opening Complaint status request form");
		getModel().bind(request);
		GrievanceReqDTO reqDTO = new GrievanceReqDTO();
		reqDTO.setCareRequest(this.getModel().getCareRequestDto());
		reqDTO.setAttachments(setFileUploadMethod(this.getModel().getAttachments()));
		boolean responseVo = false;
		try {
			responseVo = (boolean) JersyCall.callRestTemplateClientForJBPM(reqDTO,
					ServiceEndpoints.CARE_SAVE_SWM_COMPLAINT_URL);
			if (responseVo) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.info("Exception occur while saving swm complaint " + e);
		}
		return false;
	}
	
	public boolean isDSCLEnvPresent() {
		Organisation org = organisatonService.getOrganisationById(Utility.getOrgId());
		List<LookUp> envLookUpList = CommonMasterUtility.getLookUps("ENV", org);
		return envLookUpList.stream().anyMatch(env -> env.getLookUpCode().equals("DSCL")
				&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
		// return true;
	}
	
	public boolean isASCLEnvPresent(){
		Organisation org= organisatonService.getOrganisationById(Utility.getOrgId());
		List<LookUp> envLookUpList = CommonMasterUtility.getLookUps("ENV", org);
		LOGGER.info("LookUList from ACSCL boolean method | " + envLookUpList);
		return envLookUpList.stream().anyMatch(env -> env.getLookUpCode().equals("ASCL")
				&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
	}
	
	//D#132874
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "viewCareReopen", method = RequestMethod.POST)
	public ModelAndView viewCareReopen(@RequestParam("appId") final String appId,@RequestParam("compNo") final String compNo, 
			final HttpServletRequest httpServletRequest, ModelMap modelMap)throws Exception {
		LOGGER.info("Opening Complaint Reopen form");
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		modelMap.put("appId", appId);
		modelMap.put("compNo", compNo);
		ModelAndView mv = null;
		
		
		ActionResponseDTO actResponse = new ActionResponseDTO();
		CareRequestDTO careRequest = new CareRequestDTO();
		careRequest.setOrgId(Utility.getOrgId());
		try {
			careRequest.setApplicationId(Long.parseLong(appId));	
		}catch(NumberFormatException e) {
			LOGGER.info("Number format exception when parse " +e.getMessage());	
		}
		careRequest.setComplaintId(compNo);
		
		LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClientForJBPM(careRequest, ServiceEndpoints.VALID_COMPL_FOR_REOPEN);
		String actResponse_ = new JSONObject(responseVo).toString();
		actResponse = new ObjectMapper().readValue(actResponse_, ActionResponseDTO.class);

		if (actResponse.getResponse().toUpperCase().equals(ResponseType.Success.toString())) {
			mv = new  ModelAndView("ComplaintReopenFormDash", MainetConstants.FORM_NAME, this.getModel());
		} else {
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
				getModel().addValidationError(actResponse.getResponseData().get("errorMessage"));
			else
				getModel().addValidationError(actResponse.getResponseData().get("errorMessageReg"));
			mv = new ModelAndView(MainetConstants.GrievanceConstants.REOPENGRIEVANCEFORMVALID,
					MainetConstants.FORM_NAME, getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		}
		return mv;
		

	}
	

	
	@RequestMapping(method = RequestMethod.POST, params = "getZone")
	public @ResponseBody List<LookUp> getZone(@RequestParam Long orgId,
			HttpServletRequest httpServletRequest) {
		
		return this.getModel().getAlphaNumericSortedLevelDataByOrgId("CWZ",1,orgId);
		
	}
	
	

}