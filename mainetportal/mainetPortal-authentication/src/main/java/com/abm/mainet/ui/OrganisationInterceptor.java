package com.abm.mainet.ui;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.HttpHelper;
import com.abm.mainet.common.util.UserSession;

/**
 * Interceptor to redirect the request to set the ULB based on server name.
 * Request gets redirected to the CitizenHome.html if server name is configured
 * in uLBDomain.properties. If not configured, request will get redirected to
 * the ULB selection page.
 *
 * Expected property format:
 *
 * <domain name>.orgId=<org id as set in tb_organisation table>
 *
 * where <domain name> is server/domain name for an ULB <org id> is org Id from
 * tb_organisation table for this ULB
 *
 */
public class OrganisationInterceptor implements HandlerInterceptor {
	/**
	 * Request attribute name against which org Id retrieved from properties file
	 */
	private final static String ORGANIZATION_REQ_ATTR_NAME = "orgId";

	/**
	 * Request attribute name against which requested URL path i.e. path after the
	 * servlet context name will be stored
	 */
	private final static String REQUESTED_URL_PATH_REQ_ATTR_NAME = "REQUESTED_URL_PATH";
	/**
	 * Request attribute name against which requested server name will be stored
	 */
	private final static String ULB_HOST_ATTR_NAME = "CURRENT_ULB_HOST";
	private final static Logger LOGGER = Logger.getLogger(OrganisationInterceptor.class);

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, final Exception excep) throws Exception {
		// do nothing
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelNView) throws Exception {
		// do nothing

	}

	/**
	 * Checks whether the server name in the request is configured in the
	 * uLBDomain.properties. If present, forwards the request to the ULBHome.html to
	 * set corresponding information else let default handler handle the request.
	 * 
	 * @param request  request
	 * @param response response
	 * @param handler  Instance of the handler
	 */
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		final String serverName = request.getServerName();
		LOGGER.debug("Request for server: " + serverName);
		LOGGER.debug("Request path: " + request.getServletPath());
		String orgId = HttpHelper.getOrgIdBasedOnDomain(request);
		LOGGER.info(
				"Application Session OrgId: " + ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
		final HttpSession currSession = request.getSession(false);
		HttpSession session = request.getSession();
		/*
		 * As per Palam Sir Code for User session variable data null after payment
		 * gateway page open and payment done
		 */ 
		
		if (request.getParameter("JSESSIONID") != null) {
			LOGGER.info("when JSESSIONID not null  "+request.getParameter("JSESSIONID"));
			Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
			response.addCookie(userCookie);
		} else {
			LOGGER.info("when JSESSIONID  null  ");
			String sessionId = session.getId();
			Cookie userCookie = new Cookie("JSESSIONID", sessionId);
			response.addCookie(userCookie);
		}
		 
		 
		if (null != currSession) {
			LOGGER.info("Current Session Id: " + currSession.getId());
			LOGGER.info("Current Session created on: " + currSession.getCreationTime());
			LOGGER.info("Current Session LastAccessedTime: " + currSession.getLastAccessedTime());
			LOGGER.info("Current Session MaxInactiveInterval: " + currSession.getMaxInactiveInterval());
		} else {
			LOGGER.info("Current Session is null");
		}

		if (null != UserSession.getCurrent()) {

			Organisation userSessionOrg = UserSession.getCurrent().getOrganisation();
			if (null != userSessionOrg) {
				LOGGER.info("UserSession Organisation: " + UserSession.getCurrent().getOrganisation());
				LOGGER.info("UserSession Organisation Id: " + UserSession.getCurrent().getOrganisation().getOrgid());
			} else {
				LOGGER.debug("UserSession Organisation is: " + userSessionOrg);
			}

			LOGGER.info("UserSession ContextName: " + UserSession.getCurrent().getContextName());
			LOGGER.info("UserSession CurrentAppid: " + UserSession.getCurrent().getCurrentAppid());
			LOGGER.info("UserSession Description: " + UserSession.getCurrent().getDescription());
			LOGGER.info("UserSession ErrorDescription: " + UserSession.getCurrent().getErrorDescription());
			LOGGER.info("UserSession LanguageId: " + UserSession.getCurrent().getLanguageId());
			LOGGER.info("UserSession MobileNoToValidate: " + UserSession.getCurrent().getMobileNoToValidate());
			LOGGER.info("UserSession Redirecturl: " + UserSession.getCurrent().getRedirecturl());
			LOGGER.info("UserSession Redirecturl: " + UserSession.getCurrent().getRedirecturl());
			LOGGER.info("UserSession EmplType: " + UserSession.getCurrent().getEmplType());

			Employee currEmployee = UserSession.getCurrent().getEmployee();

			if (null != currEmployee) {
				LOGGER.info("UserSession Employee Emploginname: "
						+ UserSession.getCurrent().getEmployee().getEmploginname());
				LOGGER.info("UserSession Employee FullName: " + UserSession.getCurrent().getEmployee().getFullName());
				LOGGER.info("UserSession Employee Id: " + UserSession.getCurrent().getEmployee().getEmpId());
				LOGGER.info("UserSession Employee Gmid: " + UserSession.getCurrent().getEmployee().getGmid());
				LOGGER.info("UserSession Employee OrgId: " + UserSession.getCurrent().getEmployee().getOrgId());
			} else {
				LOGGER.debug("UserSession Employee is : " + currEmployee);
			}

		} else {
			LOGGER.debug("User Session is null");
		}

		if (request.getServletPath().contains("/ULBHome.html")) {
			return true;
		}

		if (orgId == null && UserSession.getCurrent().getOrganisation() != null) {
			return true;
		}
		if ((orgId == null) || orgId.isEmpty() || orgId.trim().equalsIgnoreCase("default")) {
			if (ApplicationSession.getInstance().getSuperUserOrganization() != null) {
				orgId = String.valueOf(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
			}
		}
		if (isULBSet(request, Long.valueOf(orgId.trim()))) {
			LOGGER.info("Request for server: " + serverName + " is valid, forwarding as is");
			return true;
		} else {
			LOGGER.info("Request for server: " + serverName + " is not valid, setting values in the request");
			if (request.getSession(false) != null) {
				request.getSession(false).invalidate();
			}
			request.setAttribute(ULB_HOST_ATTR_NAME, serverName);
			request.setAttribute(ORGANIZATION_REQ_ATTR_NAME, orgId);
			request.setAttribute(REQUESTED_URL_PATH_REQ_ATTR_NAME, request.getServletPath());
			String bypassUrl = request.getServletPath()+ ((request.getQueryString() != null) ? "?" + request.getQueryString() : "");
			if (bypassUrl.contains(MainetConstants.operator.AMPERSAND)) {
				bypassUrl = bypassUrl.replaceAll(MainetConstants.operator.AMPERSAND,MainetConstants.operator.AT_THE_RATE);
			}
			request.getRequestDispatcher("ULBHome.html?bypassUrl=" + bypassUrl).forward(request, response);
		}
		return false;
	}

	/**
	 * Verifies whether request has already been through this interceptor or required information is already present in session
	 * 
	 * @param request request
	 * @param orgId   Organisation for which request was received
	 * @return true if Organisation information is present else false
	 */
	private boolean isULBSet(final HttpServletRequest request, final Long orgId) {
		boolean retVal = false;
		LOGGER.info(" Inside isULBSet orgid>>"+request.getAttribute(ORGANIZATION_REQ_ATTR_NAME));
		if (request.getAttribute(ORGANIZATION_REQ_ATTR_NAME) != null) {
			retVal = true;
		} else {
			final Organisation organisation = UserSession.getCurrent().getOrganisation();
			LOGGER.info(" Inside isULBSet organisation >>"+organisation);
			if ((organisation != null) && (organisation.getOrgid().longValue() == orgId.longValue())) {
				retVal = true;
			}
		}
		if (retVal) {
			final HttpSession session = request.getSession(false);
			if (null != session) {
				session.setAttribute(ULB_HOST_ATTR_NAME, request.getServerName());
			}
		}
		LOGGER.info("  isULBSet return Value >>"+retVal);
		return retVal;
	}

}
