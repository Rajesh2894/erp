/**
 * 
 */
package com.abm.mainet.common.rest.ui.controller;

import org.springframework.stereotype.Controller;

import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.AbstractFormModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.authentication.admin.ui.model.AdminHomeModel;
import com.abm.mainet.authentication.service.AuthenticationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.EmployeeSession;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.entitlement.service.IEntitlementService;
import com.abm.mainet.common.entitlement.service.MenuRoleEntitlement;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.HttpHelper;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author cherupelli.srikanth
 *
 */

@Controller
@RequestMapping("/SSOLoginVerification")
public class SSOLoginVerificationController extends AbstractFormController<AdminHomeModel>{


	private static final Logger LOGGER = LoggerFactory.getLogger(SSOLoginVerificationController.class);

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private GroupMasterService groupMasterSer;

	@Autowired
	private IEntitlementService iEntitlementService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private IOrganisationService organisationService;
	
	@Autowired
	private ILocationMasService locationMasService;
	

	@RequestMapping(value = "/ssoEmployee", method = RequestMethod.POST)
	public void ssoEmployeeLoginByPostMeth(HttpServletRequest request, final HttpServletResponse response,
			@RequestBody SsoEmployeeDto ssoEmployee) throws IOException {

		final AdminHomeModel model = getModel();
		
		Map<String, Object> ssoCitizenMap = new HashMap<String, Object>();
		ssoCitizenMap.put("UserId", ssoEmployee.getUid());
		ssoCitizenMap.put("TokenId", ssoEmployee.getTokenId());

		ResponseEntity<Map> existSSOToken = isExistSSOTokenForEmployee(ssoCitizenMap);
		String tokenExist = (String) existSSOToken.getBody().get("Value");
		if (StringUtils.equalsIgnoreCase("Yes", tokenExist)) {
			LOGGER.info("Token validated successfully");
			Organisation officeOrganisation = organisationService.getActiveOrgByUlbBName(ssoEmployee.getOfficeName());
			UserSession.getCurrent().setOrganisation(officeOrganisation);
			String defaultEmpName = getApplicationSession().getMessage("default.employee.name");
			String defaultDeptCode = getApplicationSession().getMessage("default.employee.department.name");

			List<Employee> adminEmployee = employeeService.getEmployeeByEmpName(defaultEmpName);

			List<Employee> activeEmployeeByEmpMobileNo = employeeService.getActiveEmployeeByMobileNo(ssoEmployee.getMobileNumber());
			if (CollectionUtils.isNotEmpty(activeEmployeeByEmpMobileNo)) {
				LOGGER.info("Employee found");
				createEmployeeLoginPage(request, response, model, activeEmployeeByEmpMobileNo,ssoEmployee.getOfficeName());
				UserSession.getCurrent().setLoggedFromOtherAppl(MainetConstants.FlagD);
				response.sendRedirect(request.getContextPath() + "/AdminHome.html");
			} else {
				LOGGER.info("Employee not found, call started to create employee");
				EmployeeBean employee = new EmployeeBean();
				employee.setEmpname(ssoEmployee.getApplicantName());
				employee.setEmploginname(ssoEmployee.getApplicantName());

				Designation desgMast = designationService.findByName(ssoEmployee.getDesignation());
				if (desgMast != null) {
					LOGGER.info("Designation found");
					employee.setDsgid(desgMast.getDsgid());
				} else {
					createDesignationMaster(ssoEmployee.getDesignation(), adminEmployee, employee);
				}
				employee.setAlreadResisteredUser("N");
				employee.setIsEmpPhotoDeleted(MainetConstants.Common_Constant.YES);
				employee.setEmpmobno(ssoEmployee.getMobileNumber());
				
				Long deptId = departmentService.getDepartmentIdByDeptCode(defaultDeptCode);
				TbLocationMas locMas = locationMasService.findByLocationName(ssoEmployee.getOfficeName(), UserSession.getCurrent().getOrganisation().getOrgid());
				employee.setDepid(locMas.getLocId());
				employee.setDpDeptid(deptId);
				
				List<GroupMaster> groupMasList = groupMasterSer.getGmIdyGrCode(ssoEmployee.getDesignation().trim(), UserSession.getCurrent().getOrganisation().getOrgid());
				if (CollectionUtils.isNotEmpty(groupMasList)) {
					LOGGER.info("Group master found");
					GroupMaster groupMas = groupMasList.get(0);
					employee.setGmid(groupMas.getGmId());
				} else {
					try {
						createGroupMaster(ssoEmployee.getDesignation(), defaultDeptCode, adminEmployee, employee,deptId);
					}catch (Exception e) {
						LOGGER.error("Exception occured while inserting Group master" + e);
						throw new FrameworkException("Exception occured while inserting Group master" + e);
					}
				}

				try {
					LOGGER.info("Employee master not found , call started to insert Employee master");
					employeeService.create(employee, null, FileNetApplicationClient.getInstance(), UserSession.getCurrent().getOrganisation().getOrgid(),
							adminEmployee.get(0).getEmpId());
					LOGGER.info("call ended to insert Employee master");
				}catch (Exception e) {
				    LOGGER.error("Exception occured while inserting Employee master" + e);
					throw new FrameworkException("Exception occured while inserting Employee master" + e);
				}
				List<Employee> activeEmployeeByEmpMobileNo1 = employeeService.getActiveEmployeeByMobileNo(ssoEmployee.getMobileNumber());
				if (CollectionUtils.isNotEmpty(activeEmployeeByEmpMobileNo1)) {
					createEmployeeLoginPage(request, response, model, activeEmployeeByEmpMobileNo1,ssoEmployee.getOfficeName());
					UserSession.getCurrent().setLoggedFromOtherAppl(MainetConstants.FlagD);
					response.sendRedirect(request.getContextPath() + "/AdminHome.html");
				}

			}
		}else {
			LOGGER.error("Token validation failed");
			response.sendRedirect(request.getContextPath() + "/Home.html");
		}

	}

	@RequestMapping(value = "/ssoEmployeeLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public void ssoEmployeeLoginByGetMethod(HttpServletRequest request, final HttpServletResponse response,
			@RequestParam("applicantName") String applicantName, @RequestParam("MobileNo") String mobileNumber,
			@RequestParam("uid") String uid, @RequestParam("username") String userName,
			@RequestParam("email") String email, @RequestParam("rtnUrl") String rtnUrl,
			@RequestParam("ssoDashboardURL") String ssoDashboardURL, @RequestParam("TokenId") String tokenId,
			@RequestParam("DesignationID") String designationID, @RequestParam("Designation") String designation,
			@RequestParam("OfficeID") String officeID, @RequestParam("OfficeName") String officeName)
			throws IOException {

      final AdminHomeModel model = getModel();

		
		LOGGER.info("Call started to get employee details");
		Map<String, Object> ssoCitizenMap = new HashMap<String, Object>();
		ssoCitizenMap.put("UserId", uid);
		ssoCitizenMap.put("TokenId", tokenId);

		ResponseEntity<Map> existSSOToken = isExistSSOTokenForEmployee(ssoCitizenMap);
		String tokenExist = (String) existSSOToken.getBody().get("Value");
		if (StringUtils.equalsIgnoreCase("Yes", tokenExist)) {
			LOGGER.info("Token validated successfully");
			Organisation officeOrganisation = organisationService.getActiveOrgByUlbBName(officeName);
			UserSession.getCurrent().setOrganisation(officeOrganisation);
			String defaultEmpName = getApplicationSession().getMessage("default.employee.name");
			String defaultDeptCode = getApplicationSession().getMessage("default.employee.department.name");

			List<Employee> adminEmployee = employeeService.getEmployeeByEmpName(defaultEmpName);

			List<Employee> activeEmployeeByEmpMobileNo = employeeService.getActiveEmployeeByEmpMobileNoAndLoginName(mobileNumber,userName);
			if (CollectionUtils.isNotEmpty(activeEmployeeByEmpMobileNo)) {
				LOGGER.info("Employee found");
				createEmployeeLoginPage(request, response, model, activeEmployeeByEmpMobileNo,officeName);
				UserSession.getCurrent().setLoggedFromOtherAppl(MainetConstants.FlagD);
				response.sendRedirect(request.getContextPath() + "/AdminHome.html");
			} else {
				LOGGER.info("Employee not found, call started to create employee");
				EmployeeBean employee = new EmployeeBean();
				employee.setEmpname(applicantName);
				employee.setEmploginname(applicantName);

				Designation desgMast = designationService.findByName(designation);
				if (desgMast != null) {
					LOGGER.info("Designation found");
					employee.setDsgid(desgMast.getDsgid());
				} else {
					createDesignationMaster(designation, adminEmployee, employee);
				}
				employee.setAlreadResisteredUser("N");
				employee.setIsEmpPhotoDeleted(MainetConstants.Common_Constant.YES);
				employee.setEmpmobno(mobileNumber);
				
				Long deptId = departmentService.getDepartmentIdByDeptCode(defaultDeptCode);
				TbLocationMas locMas = locationMasService.findByLocationName(officeName, UserSession.getCurrent().getOrganisation().getOrgid());
				employee.setDepid(locMas.getLocId());
				employee.setDpDeptid(deptId);
				
				List<GroupMaster> groupMasList = groupMasterSer.getGmIdyGrCode(designation.trim(), UserSession.getCurrent().getOrganisation().getOrgid());
				if (CollectionUtils.isNotEmpty(groupMasList)) {
					LOGGER.info("Group master found");
					GroupMaster groupMas = groupMasList.get(0);
					employee.setGmid(groupMas.getGmId());
				} else {
					try {
						createGroupMaster(designation, defaultDeptCode, adminEmployee, employee,deptId);
					}catch (Exception e) {
						LOGGER.error("Exception occured while inserting Group master" + e);
						throw new FrameworkException("Exception occured while inserting Group master" + e);
					}
				}

				try {
					LOGGER.info("Employee master not found , call started to insert Employee master");
					employee.setEmploginname(userName);
					employeeService.create(employee, null, FileNetApplicationClient.getInstance(), UserSession.getCurrent().getOrganisation().getOrgid(),
							adminEmployee.get(0).getEmpId());
					LOGGER.info("call ended to insert Employee master");
				}catch (Exception e) {
				    LOGGER.error("Exception occured while inserting Employee master" + e);
					throw new FrameworkException("Exception occured while inserting Employee master" + e);
				}
				List<Employee> activeEmployeeByEmpMobileNo1 = employeeService.getActiveEmployeeByMobileNo(mobileNumber);
				if (CollectionUtils.isNotEmpty(activeEmployeeByEmpMobileNo1)) {
					createEmployeeLoginPage(request, response, model, activeEmployeeByEmpMobileNo1,officeName);
					UserSession.getCurrent().setLoggedFromOtherAppl(MainetConstants.FlagD);
					response.sendRedirect(request.getContextPath() + "/AdminHome.html");
				}

			}
		}else {
			LOGGER.error("Token validation failed");
			response.sendRedirect(request.getContextPath() + "/Home.html");
		}

	}

	private void createDesignationMaster(String designation, List<Employee> adminEmployee, EmployeeBean employee) {
		try {
			LOGGER.info("Designation not found , call started to insert designation");
			DesignationBean designationBean = new DesignationBean();
			designationBean.setDsgname(designation);
			designationBean.setDsgdescription(designation);
			designationBean.setDsgnameReg(designation);
			designationBean.setDsgshortname(designation);
			designationBean.setUserId(adminEmployee.get(0).getEmpId());
			designationBean.setIsdeleted(MainetConstants.Common_Constant.ZERO_SEC);
			designationBean.setLmoddate(new Date());
			designationBean.setLgIpMac(Utility.getMacAddress());
			UserSession.getCurrent().setEmployee(adminEmployee.get(0));
			DesignationBean desgnationEntity = designationService.create(designationBean);
			employee.setDsgid(desgnationEntity.getDsgid());
			LOGGER.info("call ended to insert designation");
		}catch (Exception e) {
			LOGGER.error("Exception occured while inserting designation master" + e);
			throw new FrameworkException("Exception occured while inserting designation master" + e);
		}
	}

	private void createGroupMaster(String designation, String defaultDeptCode, List<Employee> adminEmployee,
			EmployeeBean employee,Long deptId) {
		LOGGER.info("Group master not found , call started to insert Group master");
		GroupMaster groupMaster = new GroupMaster();
		groupMaster.setGrCode(designation);
		groupMaster.setGrDescEng(designation);

		groupMaster.setGrDescReg(designation);
		groupMaster.setGrStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
		groupMaster.setOrgId(adminEmployee.get(0).getOrganisation());
		groupMaster.setDpDeptId(deptId);
		groupMaster.setEntryDate(new Date());
		groupMaster.setUserId(adminEmployee.get(0));
		groupMaster.setLgIpMac(Utility.getMacAddress());
		GroupMaster createGroupMaster = groupMasterSer.createGroupMaster(groupMaster,
				adminEmployee.get(0).getOrganisation());
		employee.setGmid(createGroupMaster.getGmId());
		LOGGER.info("call ended to insert Group master");
	}

	@RequestMapping(value = "/ssoCitizenLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public void ssoCitizenLoginByGetMethod1(HttpServletRequest request, final HttpServletResponse response,
			@RequestParam("UserId") String userId, @RequestParam("EmailId") String emailId,
			@RequestParam("MobNo") String mobileNumber, @RequestParam("ReturnUrl") String returnUrl,
			@RequestParam("RedirectUrl") String redirectUrl, @RequestParam("TokenId") String tokenId)
			throws IOException {
		LOGGER.info("Call started to get citizen employee details");
		final AdminHomeModel model = getModel();

		Map<String, Object> ssoCitizenMap = new HashMap<String, Object>();
		ssoCitizenMap.put("UserId", userId);
		ssoCitizenMap.put("TokenId", tokenId);

		String defaultEmpName = getApplicationSession().getMessage("default.employee.name");
		String defaultGmIdCode = getApplicationSession().getMessage("default.citizen.gmid.name");
		String defaultDesgnCode = getApplicationSession().getMessage("default.citizen.designation.name");
		String defaultDeptCode = getApplicationSession().getMessage("default.citizen.department.name");
		String defaultLocationCode = getApplicationSession().getMessage("default.citizen.location.name");
		
		SsoCitizenDto dto = new SsoCitizenDto();
		dto.setUserId(userId);
		dto.setTokenId(tokenId);
		String existTokenRes = isExistSSOTokenForCitizenGetMethod(dto);

		JSONObject json = new JSONObject(existTokenRes);
		Object object = json.get("Value");
		
		
		if (StringUtils.equalsIgnoreCase("Yes", object.toString())) {
			LOGGER.info("Token validated successfully");
			Organisation superOrg = getApplicationSession().getSuperUserOrganization();
			UserSession.getCurrent().setOrganisation(superOrg);
			List<Employee> adminEmployee = employeeService.getEmployeeByEmpName(defaultEmpName);
			if(CollectionUtils.isNotEmpty(adminEmployee)) {
				LOGGER.info("Default employee found");
			}else {
				LOGGER.info("Default employee not found");
			}
			List<Employee> activeEmployeeByEmpMobileNo = employeeService.getActiveEmployeeByMobileNo(mobileNumber);
			if (CollectionUtils.isNotEmpty(activeEmployeeByEmpMobileNo)) {
				LOGGER.info("Citizen employee found");
				createEmployeeLoginPage(request, response, model, activeEmployeeByEmpMobileNo,null);
				UserSession.getCurrent().setLoggedFromOtherAppl(MainetConstants.FlagC);
				response.sendRedirect(request.getContextPath() + "/AdminHome.html");
			} else {
				LOGGER.info("Citize employee not found, started call to insert citizen employee");
				EmployeeBean employee = new EmployeeBean();
				employee.setEmpname(mobileNumber);
				employee.setEmploginname(mobileNumber);

				Designation desgMast = designationService.findByName(defaultDesgnCode);
				if (desgMast != null) {
					LOGGER.info("Citizen designation found");
					employee.setDsgid(desgMast.getDsgid());
				}
				employee.setAlreadResisteredUser("N");
				employee.setIsEmpPhotoDeleted(MainetConstants.Common_Constant.YES);
				employee.setEmpmobno(mobileNumber);
				
				Long deptId = departmentService.getDepartmentIdByDeptCode(defaultDeptCode);
				if(deptId != null) {
					LOGGER.info("Citizen deparment found");
				}
				TbLocationMas locMas = locationMasService.findByLocationName(defaultLocationCode, UserSession.getCurrent().getOrganisation().getOrgid());
				if(locMas != null) {
					LOGGER.info("Location master found");
				}
				employee.setDepid(locMas.getLocId());
				employee.setDpDeptid(deptId);
				
				List<GroupMaster> groupMasList = groupMasterSer.getGmIdyGrCode(defaultGmIdCode,superOrg.getOrgid());
				if (CollectionUtils.isNotEmpty(groupMasList)) {
					LOGGER.info("Citizen group master found");
					GroupMaster groupMas = groupMasList.get(0);
					employee.setGmid(groupMas.getGmId());
				}

				try {
					LOGGER.info("call started to insert citizen employee master");
					employeeService.create(employee, null, FileNetApplicationClient.getInstance(), superOrg.getOrgid(),
							adminEmployee.get(0).getEmpId());
					LOGGER.info("call ended to insert citizen employee master");
				}catch (Exception e) {
					LOGGER.error("Exception occured while saving citizen employee master" +e);
					throw new FrameworkException("Exception occured while saving citizen employee master" +e);
				}
				List<Employee> activeEmployeeByEmpMobileNo1 = employeeService.getActiveEmployeeByMobileNo(mobileNumber);
				if (CollectionUtils.isNotEmpty(activeEmployeeByEmpMobileNo1)) {
					createEmployeeLoginPage(request, response, model, activeEmployeeByEmpMobileNo1,null);
					UserSession.getCurrent().setLoggedFromOtherAppl(MainetConstants.FlagC);
					response.sendRedirect(request.getContextPath() + "/AdminHome.html");
				}

			}
		}else {
			LOGGER.error("Token validation failed");
			response.sendRedirect(request.getContextPath() + "/Home.html");
		}

	}

	@RequestMapping(value = "/ssoCitizen", method = RequestMethod.POST)
	public void ssoCitizenLoginByPostMethod(HttpServletRequest request, final HttpServletResponse response,
			@RequestBody SsoCitizenDto ssoCitizen) throws IOException {

		final AdminHomeModel model = getModel();

		Map<String, Object> ssoCitizenMap = new HashMap<String, Object>();
		ssoCitizenMap.put("UserId", ssoCitizen.getUserId());
		ssoCitizenMap.put("TokenId", ssoCitizen.getTokenId());
		
		ResponseEntity<Map> existSSOToken = isExistSSOTokenForCitizen(ssoCitizenMap);
		String tokenExist = (String) existSSOToken.getBody().get("Value");
		if (StringUtils.equalsIgnoreCase("Yes", tokenExist)) {
			LOGGER.info("Token validated successfully");
			Organisation superOrg = getApplicationSession().getSuperUserOrganization();
			UserSession.getCurrent().setOrganisation(superOrg);
			String defaultEmpName = getApplicationSession().getMessage("default.employee.name");
			String defaultGmIdCode = getApplicationSession().getMessage("default.citizen.gmid.name");
			String defaultDesgnCode = getApplicationSession().getMessage("default.citizen.designation.name");
			String defaultDeptCode = getApplicationSession().getMessage("default.citizen.department.name");
			String defaultLocationCode = getApplicationSession().getMessage("default.citizen.location.name");
			
			List<Employee> adminEmployee = employeeService.getEmployeeByEmpName(defaultEmpName);
			List<Employee> activeEmployeeByEmpMobileNo = employeeService.getActiveEmployeeByMobileNo(ssoCitizen.getMobileNumber());
			if (CollectionUtils.isNotEmpty(activeEmployeeByEmpMobileNo)) {
				LOGGER.info("Citizen employee found");
				createEmployeeLoginPage(request, response, model, activeEmployeeByEmpMobileNo,null);
				UserSession.getCurrent().setLoggedFromOtherAppl(MainetConstants.FlagC);
				response.sendRedirect(request.getContextPath() + "/AdminHome.html");
			} else {
				LOGGER.info("Citize employee not found, started call to insert citizen employee");
				EmployeeBean employee = new EmployeeBean();
				employee.setEmpname(ssoCitizen.getMobileNumber());
				employee.setEmploginname(ssoCitizen.getMobileNumber());

				Designation desgMast = designationService.findByName(defaultDesgnCode);
				if (desgMast != null) {
					LOGGER.info("Citizen designation found");
					employee.setDsgid(desgMast.getDsgid());
				}
				employee.setAlreadResisteredUser("N");
				employee.setIsEmpPhotoDeleted(MainetConstants.Common_Constant.YES);
				employee.setEmpmobno(ssoCitizen.getMobileNumber());
				
				Long deptId = departmentService.getDepartmentIdByDeptCode(defaultDeptCode);
				TbLocationMas locMas = locationMasService.findByLocationName(defaultLocationCode, UserSession.getCurrent().getOrganisation().getOrgid());
				employee.setDepid(locMas.getLocId());
				employee.setDpDeptid(deptId);
				
				List<GroupMaster> groupMasList = groupMasterSer.getGmIdyGrCode(defaultGmIdCode,superOrg.getOrgid());
				if (CollectionUtils.isNotEmpty(groupMasList)) {
					LOGGER.info("Citizen group master found");
					GroupMaster groupMas = groupMasList.get(0);
					employee.setGmid(groupMas.getGmId());
				}

				try {
					LOGGER.info("call started to insert citizen employee master");
					employeeService.create(employee, null, FileNetApplicationClient.getInstance(), superOrg.getOrgid(),
							adminEmployee.get(0).getEmpId());
					LOGGER.info("call ended to insert citizen employee master");
				}catch (Exception e) {
					LOGGER.error("Exception occured while saving citizen employee master" +e);
					throw new FrameworkException("Exception occured while saving citizen employee master" +e);
				}
				
				List<Employee> activeEmployeeByEmpMobileNo1 = employeeService.getActiveEmployeeByMobileNo(ssoCitizen.getMobileNumber());
				if (CollectionUtils.isNotEmpty(activeEmployeeByEmpMobileNo1)) {
					createEmployeeLoginPage(request, response, model, activeEmployeeByEmpMobileNo1,null);
					UserSession.getCurrent().setLoggedFromOtherAppl(MainetConstants.FlagC);
					response.sendRedirect(request.getContextPath() + "/AdminHome.html");
				}

			}
		}else {
			LOGGER.error("Token validation failed");
			response.sendRedirect(request.getContextPath() + "/Home.html");
		}

	}

	private void createEmployeeLoginPage(HttpServletRequest request, final HttpServletResponse response,
			final AdminHomeModel model, List<Employee> activeEmployeeByEmpMobileNo,String officeName) {
		landingPage(request, response);
		Employee loggedEmployee = activeEmployeeByEmpMobileNo.get(0);
		final UserSession userSession = UserSession.getCurrent();
		loggedEmployee.setEmppiservername(Utility.getClientIpAddress(request));
		loggedEmployee.setEmpisecuritykey(request.getRequestedSessionId());
		userSession.setEmployee(loggedEmployee);
		Organisation organisation = null;
		if(StringUtils.isNotBlank(officeName)) {
			 organisation = organisationService.getActiveOrgByUlbBName(officeName);
		}else {
			  organisation = organisationService
					.getOrganisationById(userSession.getOrganisation().getOrgid());
		}
		userSession.setOrganisation(organisation);
		employeeService.setEmployeeLoggedInFlag(loggedEmployee);

		if (loggedEmployee.getGmid() != null) {
			MenuRoleEntitlement.getCurrentMenuRoleManager().getMenuList(loggedEmployee.getGmid(),
					organisation.getOrgid());
		} else {
			final Long groupId = iEntitlementService.getGroupIdByName(MainetConstants.MENU.PORTAL_LOGIN,
					organisation.getOrgid());
			MenuRoleEntitlement.getCurrentMenuRoleManager().getSpecificMenuList(groupId, organisation.getOrgid());
		}

		final List<LookUp> finalLookUp = new ArrayList<>();
		try {
			final List<LookUp> lookUps = model.getLevelData(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER);
			for (final LookUp lookUp : lookUps) {
				if ("Y".equals(lookUp.getOtherField())) {
					finalLookUp.add(lookUp);
				}
			}

		} catch (final Exception e) {
			logger.info("Payment mode didnt get loaded on startup");
		}

		userSession.setPaymentMode(finalLookUp);

		// code for setting employee role code
		if (loggedEmployee != null) {
			GroupMaster gmas = groupMasterSer.findByGmId(loggedEmployee.getGmid(), organisation.getOrgid());
			if (gmas != null)
				userSession.setRoleCode(gmas.getGrCode());
		}

		try {
			EmployeeSession empSession = new EmployeeSession();
			empSession.setDateOfAction(new Date());
			empSession.setLoginDate(new Date());
			// empSession.setLogOutDate(new Date());
			if (UserSession.getCurrent().getOrganisation() != null)
				empSession.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			empSession.setTransMode(MainetConstants.FlagS);
			if (UserSession.getCurrent().getEmployee() != null)
				empSession.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());

			boolean flag = employeeService.saveEmployeeSession(empSession);
			if (flag) {
				// log.info(" Successfully save employee session data at the time of citizen
				// login");
			}
		} catch (Exception e) {
			// log.error("Exception occured at the time of saving employee session data ");
		}
	}

	private void landingPage(HttpServletRequest request, final HttpServletResponse response) {
		final int langId = HttpHelper.getSessionLanguageId(request);
		if (request.getSession(false) != null) {
			request.removeAttribute("_csrf");
			request.getSession(false).invalidate();
		}
		final UserSession userSession = UserSession.getCurrent();
		final Organisation superOrg = getApplicationSession().getSuperUserOrganization();
		if (null == userSession.getOrganisation()) {
			userSession.setOrganisation(superOrg);
		}
		if (langId == 0) {
			HttpHelper.setLanguage(request, response, superOrg, false);
		} else {
			HttpHelper.setLanguage(request, response, langId, false);
		}

		String key = UUID.randomUUID().toString();
		String substringKey = key.substring(key.length() - 17, key.length() - 1);
		UserSession.getCurrent().setUniqueKeyId(substringKey);
	}

	public ResponseEntity<Map> isExistSSOTokenForCitizen(Map<String, Object> request) {

		String URL = getApplicationSession().getMessage("sso.token.verify.url");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.postForEntity(URL, request, Map.class);
		if (response.getStatusCode() == HttpStatus.OK) {
		}
		return response;
	}
	
	public ResponseEntity<Map> isExistSSOTokenForEmployee(Map<String, Object> request) {

		String URL = getApplicationSession().getMessage("sso.token.verify.url.dept");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.postForEntity(URL, request, Map.class);
		if (response.getStatusCode() == HttpStatus.OK) {
		}
		return response;
	}

	public String isExistSSOTokenForEmployeeGetMethod(SsoEmployeeDto ssoEmployee) throws ClientProtocolException, IOException {
		String URL = getApplicationSession().getMessage("sso.token.verify.url.dept");
		Map<String, Object> ssoCitizenMap = new HashMap<String, Object>();
		ssoCitizenMap.put("UserId", ssoEmployee.getUid());
		ssoCitizenMap.put("TokenId", ssoEmployee.getTokenId());

		HttpPost httppost = new HttpPost(URL);
		DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
		ObjectMapper data = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String writeValueAsString = data.writeValueAsString(ssoEmployee);
		LOGGER.info("input json ->" + writeValueAsString);

		StringEntity params = new StringEntity(writeValueAsString);
		httppost.addHeader("content-type", "application/json");
		httppost.setEntity(params);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity responseEntity = response.getEntity();
		String responseString = EntityUtils.toString(response.getEntity());
		return responseString;
	}
	
	public String isExistSSOTokenForCitizenGetMethod(SsoCitizenDto ssoCitizen) throws ClientProtocolException, IOException {
		String URL = getApplicationSession().getMessage("sso.token.verify.url");
		Map<String, Object> ssoCitizenMap = new HashMap<String, Object>();
		ssoCitizenMap.put("UserId", ssoCitizen.getUserId());
		ssoCitizenMap.put("TokenId", ssoCitizen.getTokenId());

		HttpPost httppost = new HttpPost(URL);
		DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
		ObjectMapper data = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String writeValueAsString = data.writeValueAsString(ssoCitizen);
		LOGGER.info("input json ->" + writeValueAsString);

		StringEntity params = new StringEntity(writeValueAsString);
		httppost.addHeader("content-type", "application/json");
		httppost.setEntity(params);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity responseEntity = response.getEntity();
		String responseString = EntityUtils.toString(response.getEntity());
		return responseString;
	}


}
