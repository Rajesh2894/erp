package com.abm.mainet.mobile.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cms.ui.listener.SessionListener;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.EmployeeBean;
import com.abm.mainet.common.exception.InvalidRequestException;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.mobile.dto.CommonAppResponseDTO;
import com.abm.mainet.mobile.dto.EmployeeOTPReqDTO;
import com.abm.mainet.mobile.dto.EmployeeRequestDTO;
import com.abm.mainet.mobile.dto.LoginRequestVO;
import com.abm.mainet.mobile.dto.LoginResponseVO;
import com.abm.mainet.mobile.service.RegistrationService;
import com.abm.mainet.security.JwtUtil;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author umashanker.kanaujiya
 *
 */
@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/registrationController")
public class CitizenRegistrationMobileController {

	private static final String REQUESTDTO_CANT_NULL = "RequestDTO can not be null";
	private static final Logger LOG = Logger.getLogger(CitizenRegistrationMobileController.class);

	@Resource
	private RegistrationService registrationService;

	@Autowired
	private IEmployeeService employeeService; // Injected by Spring
	@Autowired
	ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private IOrganisationService iOrganisationService;

	@RequestMapping(value = "/doRegistration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object doRegistration(@RequestBody @Valid final EmployeeRequestDTO employeeDTO,
			final HttpServletRequest request, final BindingResult bindingResult) {

		LOG.info("EmployeeDTO " + employeeDTO.toString());
		CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
		Assert.notNull(employeeDTO, REQUESTDTO_CANT_NULL);
		if (!bindingResult.hasErrors()) {
			responseDTO = registrationService.doRegistrationService(employeeDTO);
		} else {
			LOG.error("Error during binding  doRegistration in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}
		return responseDTO;
	}

	@RequestMapping(value = "/editUserProfileForMobile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> editUserProfileForMobile(@RequestBody @Valid final LoginResponseVO loginResponseVO,
			final HttpServletRequest request, final BindingResult bindingResult) {
		LOG.info("LoginResponseVO " + loginResponseVO.toString());
		Employee entity = null;
		final ApplicationSession session = ApplicationSession.getInstance();
		String validateInput = registrationService.validateInput(loginResponseVO);
		if (validateInput.isEmpty()) {
			entity = registrationService.saveEditedUserProfileForMobile(loginResponseVO);
		}
		ResponseEntity<?> status = Optional.ofNullable(entity)
				.map(result -> new ResponseEntity<>(session.getMessage("User profile saved for provided details"),
						HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		if (!status.getStatusCode().is2xxSuccessful()) {
			throw new IllegalArgumentException("User profile save failed {" + status.getBody());
		}
		return status;
	}

	@RequestMapping(value = "/doOTPVerification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object doOTPVerification(@RequestBody @Valid final EmployeeOTPReqDTO reqDTO,
			final HttpServletRequest request, final BindingResult bindingResult) {

		LOG.info("EmployeeOTPReqDTO " + reqDTO.toString());
		CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
		Assert.notNull(reqDTO, REQUESTDTO_CANT_NULL);
		if (!bindingResult.hasErrors()) {
			responseDTO = registrationService.doOTPVerificationService(reqDTO);
		} else {
			LOG.error("Error during binding  doOTPVerification in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}

		return responseDTO;
	}

	@RequestMapping(value = "/setPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object setPassword(@RequestBody @Valid final EmployeeOTPReqDTO otpReqDTO, final HttpServletRequest request,
			final BindingResult bindingResult) {

		LOG.info("EmployeeOTPReqDTO " + otpReqDTO.toString());
		CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
		Assert.notNull(otpReqDTO, REQUESTDTO_CANT_NULL);
		if (!bindingResult.hasErrors()) {
			responseDTO = registrationService.setPasswordService(otpReqDTO);

		} else {
			LOG.error("Error during binding  setPassword in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}

		return responseDTO;
	}

	@RequestMapping(value = "/ResendOTP", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object ResendOTP(@RequestBody @Valid final EmployeeOTPReqDTO otpReqDTO, final HttpServletRequest request,
			final BindingResult bindingResult) {

		LOG.info("EmployeeOTPReqDTO " + otpReqDTO.toString());
		CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
		Assert.notNull(otpReqDTO, REQUESTDTO_CANT_NULL);
		if (!bindingResult.hasErrors()) {
			responseDTO = registrationService.reSendOTPService(otpReqDTO);

		} else {
			LOG.error("Error during binding  setPassword in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}
		return responseDTO;
	}

	@RequestMapping(value = "/AuthenticationProcess", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public LoginResponseVO authenticationProcess(@RequestBody @Valid final LoginRequestVO loginRequestVO,
			final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {

		LOG.info("inside the authenticationProcess in controller ");
		Assert.notNull(loginRequestVO, REQUESTDTO_CANT_NULL);
		LoginResponseVO responseDTO = new LoginResponseVO();
		//Commented As per Rajesh Sir
		
		Organisation org = iOrganisationService.getOrganisationById(loginRequestVO.getOrgId());
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL)) {
			if (!isCorrectMobileVersion(loginRequestVO, org)) {
				responseDTO.setStatus(MainetConstants.FAIL);
				responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("mob.software.update"));
				return responseDTO;
			}
		}

		if (!bindingResult.hasErrors()) {
			responseDTO = registrationService.getAuthenticationProcess(loginRequestVO);

			setJWTTokenOnAuthenticationSuccess(response, responseDTO, loginRequestVO.getOrgId());

		} else {
			LOG.error("Error during binding  authenticationProcess in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}

		return responseDTO;
	}

	private void setJWTTokenOnAuthenticationSuccess(final HttpServletResponse response, LoginResponseVO responseDTO,
			final Long orgId) {
		try {
			EmployeeBean edto = new EmployeeBean();
			edto.setOrgid(orgId);
			edto.setEmpId(responseDTO.getUserId());
			JwtUtil jwt = new JwtUtil();
			String jwtToken = jwt.createJWT(edto);
			LOG.info("JWT_TOKEN{UserId-" + responseDTO.getUserId() + "]>>" + jwtToken);
			response.addHeader(JwtUtil.getJwtToken(), jwtToken);
		} catch (Exception e) {
			LOG.warn("Failed To Create Token CitizenRegistrationController->setJWTTokenOnAuthenticationSuccess", e);
		}
	}

	@RequestMapping(value = "/resendOTPForMobile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object resendOTPForMobile(@RequestBody @Valid final EmployeeOTPReqDTO otpReqDTO,
			final HttpServletRequest request, final BindingResult bindingResult) {

		LOG.info("EmployeeOTPReqDTO " + otpReqDTO.toString());
		CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
		Assert.notNull(otpReqDTO, REQUESTDTO_CANT_NULL);
		if (!bindingResult.hasErrors()) {
			responseDTO = registrationService.reSendOTPServiceForMobile(otpReqDTO);

		} else {
			LOG.error("Error during binding  setPassword in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}
		return responseDTO;
	}

	@RequestMapping(value = "/getRegisteredCitizenCount/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Map<String, Long> findAllCitizenCount(@PathVariable long orgId) {

		LOG.info("getRegisteredEmpCount ");
		Map map = new HashMap<String, Long>();
		try {
			Long regEmployeeCount = employeeService.findAllCitizenCount(orgId);
			map.put("Total_Registered_Users", regEmployeeCount);
		} catch (Exception e) {
			LOG.error("Error Occured getRegisteredEmpCount");
		}
		return map;
	}

	// Mobile send OTP service for registered property
	@RequestMapping(value = "/sendOTPServiceForAssessment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object resendOTPForNoChange(@RequestBody @Valid final EmployeeOTPReqDTO otpReqDTO,
			final HttpServletRequest request, final BindingResult bindingResult) {

		LOG.info("EmployeeOTPReqDTO " + otpReqDTO.toString());
		CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
		Assert.notNull(otpReqDTO, REQUESTDTO_CANT_NULL);
		if (!bindingResult.hasErrors()) {
			responseDTO = registrationService.sendOTPServiceForAssessment(otpReqDTO);

		} else {
			LOG.error("Error during binding setPassword in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}
		return responseDTO;
	}

	// Mobile OTP verification for registered property
	@RequestMapping(value = "/doOTPVerificationForAssessment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object doOTPVerificationForNoChange(@RequestBody @Valid final EmployeeOTPReqDTO reqDTO,
			final HttpServletRequest request, final BindingResult bindingResult) {

		LOG.info("EmployeeOTPReqDTO " + reqDTO.toString());
		CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
		Assert.notNull(reqDTO, REQUESTDTO_CANT_NULL);
		if (!bindingResult.hasErrors()) {
			responseDTO = registrationService.doOTPVerificationForAssessment(reqDTO);
		} else {
			LOG.error("Error during binding doOTPVerificationForNoChange in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}
		return responseDTO;
	}

	// D#152479
	@RequestMapping(value = "/logOutForMobile/{empId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public boolean logOutForMobile(@PathVariable long empId) {

		LOG.info("employee Id " + empId);
		Employee emp = new Employee();
		emp.setEmpId(empId);
		employeeService.resetEmployeeLoggedInFlag(emp);
		LOG.info("employee Logged out successfully");

		return true;

	}

	@RequestMapping(value = "/getCitizenData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Map<String, Long> getCitizenData() {

		Map<String, Long> resultMap = new ConcurrentHashMap<String, Long>();

		resultMap.put("totalRegisUser", employeeService.findCountOfRegisteredEmployee());
		resultMap.put("loggedInUser", employeeService.findCountOfLoggedInUser());
		resultMap.put("activeuser", Long.valueOf(SessionListener.getActiveSessions()));
		return resultMap;

	}

	@RequestMapping(value = "/getTotalUser/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public long getTotalVisitor(@PathVariable long orgId) {

		LOG.info("Organisation Id " + orgId);
		long count = employeeService.getHitCounterUser(orgId);
		LOG.info("Total visitor    " + count);

		return count;

	}
	

	@RequestMapping(value = "sendOtp", method = RequestMethod.POST, produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public String sendOTPDetails(@RequestBody EmployeeRequestDTO applicantDetailDTO) {
		String mobileOTP=null;
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		if(applicantDetailDTO.getMobileNo()!=null) {
			mobileOTP = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
		Organisation org = iOrganisationService.getOrganisationById(applicantDetailDTO.getOrgId());
		dto.setEmail(applicantDetailDTO.getEmailId());
		dto.setMobnumber(applicantDetailDTO.getMobileNo().toString());
		dto.setOneTimePassword(mobileOTP);
		dto.setAppName(applicantDetailDTO.getFirstName()+ " " + applicantDetailDTO.getLastName());
		if (applicantDetailDTO.getLangId() == MainetConstants.ENGLISH) {
			dto.setV_muncipality_name(org.getONlsOrgname());
		} else {
			dto.setV_muncipality_name(org.getONlsOrgnameMar());
		}
		if (applicantDetailDTO.getLangId() != null)
			dto.setLangId(applicantDetailDTO.getLangId().intValue());
		else
			dto.setLangId(MainetConstants.ENGLISH);
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPARTMENT.CFC_CODE,
				"CitizenRegistration.html", MainetConstants.SMS_EMAIL.OTP_MSG, dto,
				org,dto.getLangId());
		}
		else {
			return "Please send mobile No";
		}
		return mobileOTP;
	}
	
	private boolean isCorrectMobileVersion(LoginRequestVO loginRequestVO, Organisation org) {
		try {
			List<String> versionList = CommonMasterUtility.getListLookup(PrefixConstants.Common.Apk_Version, org)
					.stream().map(a -> a.getOtherField()).collect(Collectors.toList());
			if (StringUtils.isBlank(loginRequestVO.getApkVersion())) {
				return false;
			} else if (!(CollectionUtils.isNotEmpty(versionList)
					&& versionList.contains(loginRequestVO.getApkVersion()))) {
				return false;
			}
		} catch (Exception e) {
			LOG.error("Error occured at the time of checking mobile version" + e);
		}
		return true;
	}
	 
}
