package com.abm.mainet.authentication.service;

import java.util.Base64;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.authentication.ldap.IAuthenticationManager;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.PasswordManager;
import com.abm.mainet.common.utility.Utility;

import io.swagger.annotations.Api;

@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.authentication.service.AuthenticationService")
@Api(value = "/authenticationService")
@Path("/authenticationService")
@Service(value = "authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	@Autowired
	private IEmployeeService iEmployeeService;
	@Autowired
	@Qualifier("authManager")
	private IAuthenticationManager authManager;
	
	
	/**
	 * Web service for authenticating employee.
	 * @param emploginname
	 * @param emppassword
	 * @param orgId
	 * @return String, accountLock - If account is locked, passwordExpired -  If password is expired, FirstLogin - In case of first login attempt, LoginSuccessful - On successful login, OTP not verified - if OTP is not verified, AccessDenied - failed login attempt
	 */
	@Override
	@POST
	@WebMethod
	@Consumes("application/json")
	@Path("/authenticateEmployee/{emploginname}/{emppassword}/{orgId}")
	@Transactional()
	public String authenticateEmployee(@PathParam(value = "emploginname") String emploginname,
			@PathParam(value = "emppassword") String emppassword, @PathParam(value = "orgId") long orgId) {
		String result = "Access Denied";
		if ((orgId != 1) || (orgId != 0)) {

			final Employee loggedEmployee = validateEmployee(emploginname, emppassword, (new Long(orgId)).toString());

			if (loggedEmployee != null) {
				// userSession.setContextName(request.getContextPath());
				if (null != loggedEmployee.getLockUnlock() && loggedEmployee.getLockUnlock().equals("L")) {
					return "accountLock";
				}
				if (PasswordManager.isPasswordExpired(loggedEmployee)) {
					return "passwordExpired";
				}
				if (null != loggedEmployee.getLoggedIn()
						&& loggedEmployee.getLoggedIn().equals(MainetConstants.Common_Constant.FIRST)) {
					return "FirstLogin";
				}

				final boolean isOTPVerifed = isOTPVerificationDone(loggedEmployee);

				if (isOTPVerifed) {

					iEmployeeService.setEmployeeLoggedInFlag(loggedEmployee);

					result = "LoginSuccessful";

				} else {
					result = "OTP not verified";
				}
			}

		} else {

			return ApplicationSession.getInstance().getMessage(MainetConstants.AdminHome.ADMIN_LOG_ORG_SELECTED);
		}

		return result;
	}

	/**
	 * This method validates employee against ldap and DB.
	 * @param empLoginString
	 * @param empPassword
	 * @param orgId
	 * @return, employee if validation is successful else return null
	 */
	@Transactional()
	private Employee validateEmployee(String empLoginString, String empPassword, String orgId) {

		Employee validateEmployee = null;
		empPassword = new String(Base64.getDecoder().decode(empPassword));
		empPassword = Utility.encryptPassword(empLoginString, empPassword);

		final boolean isLdapAuthenticated = authManager.authenticateUser(empLoginString + "_" + orgId, empPassword);
		if (isLdapAuthenticated) {
			return iEmployeeService.validateLoggedInEmployee(empLoginString, empPassword, orgId);
		} else {
			return validateEmployee;
		}

	}

	private boolean isOTPVerificationDone(final Employee validEmployee) {
		if ((validEmployee.getAutMob() != null) && !validEmployee.getAutMob().equalsIgnoreCase("N")) {
			return true;
		}
		return false;

	}
}
