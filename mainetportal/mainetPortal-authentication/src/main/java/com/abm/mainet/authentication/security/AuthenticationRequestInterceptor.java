package com.abm.mainet.authentication.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.abm.mainet.autherization.service.IAuthorizationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;

public class AuthenticationRequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = Logger.getLogger(AuthenticationRequestInterceptor.class);
    private static String requestedURl = "";
    private static String requestQueryString = "";
    private static List<String> byPassedUrls  = new ArrayList<String>();
    ApplicationSession session = ApplicationSession.getInstance();

    @Override
    public boolean preHandle(final HttpServletRequest request,
            final HttpServletResponse response, final Object handler) throws Exception {
        requestedURl = request.getServletPath();
        requestQueryString = request.getQueryString();
        LOGGER.info("inside AuthenticationRequestInterceptor preHandle requestedURl >> "+requestedURl+">>requestQueryString >>"+requestQueryString);
        if (isHomeUrl(requestedURl,requestQueryString)) {
            return true;

        } else {
            Employee employee = UserSession.getCurrent().getEmployee();
            if ((employee != null
                    && employee.getEmploginname() != null)) {
            	//#122732 
                  //  && !employee.getEmploginname().equalsIgnoreCase("NOUSER")) {
            	LOGGER.info("inside AuthenticationRequestInterceptor preHandle employee  >>"+employee);
                String key = requestedURl.substring(requestedURl.indexOf(MainetConstants.WINDOWS_SLASH) + 1).trim();
				
                Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
                LOGGER.info("inside AuthenticationRequestInterceptor preHandle organisation data  >>"+UserSession.getCurrent().getOrganisation());
                Long gmid = null;
                                                     //#122732 
                if (employee.getEmplType() == null && employee.getGmid() != null) {
                    gmid = employee.getGmid();

                } else {
                    IEntitlementService iEntitlementService = (IEntitlementService) ApplicationContextProvider
                            .getApplicationContext()
                            .getBean("entitlementService");
                    gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.DEFAULT_CITIZEN,
                            orgId);
                }

                if (gmid != null ) {
                    String headerName = request.getHeader("x-requested-with");
                    if (null == headerName) {
                        IAuthorizationService service = (IAuthorizationService) ApplicationContextProvider.getApplicationContext()
                                .getBean("authorizationService");

                        if (service.isAuthUrl(key, gmid, orgId) && !employee.getEmploginname().equalsIgnoreCase(MainetConstants.NOUSER)) {
                            return true;
                        } else {
                            response.sendRedirect(request.getContextPath() + "/AutherizationFail.html");
                            return true;
                        }
                    } else {
                        return true;
                    }
                }else {
                	 response.sendRedirect(request.getContextPath() + "/AutherizationFail.html");
                     return true;
                }
            }
        }

        return true;
    }

    private boolean isHomeUrl(final String requestedURl2,String requestQueryString) {
    	
		if (byPassedUrls.isEmpty()) {
			String urlString = session.getMessage("nouser.bypass.urls");
			if (urlString != null && urlString != "") {
				byPassedUrls = Arrays.asList(urlString.split(MainetConstants.operator.COMA));
			}
		}
		if (byPassedUrls != null && !byPassedUrls.isEmpty()) {
			if(requestQueryString !=null && requestQueryString.equalsIgnoreCase(MainetConstants.EDIT_USER) && UserSession.getCurrent().getEmployee() != null &&  UserSession.getCurrent().getEmployee().getEmploginname().equalsIgnoreCase(MainetConstants.NOUSER)) {
				return false;
			}
			return byPassedUrls.contains(requestedURl2);
		}
		return false;
   
  }

   
    @Override
    public void postHandle(final HttpServletRequest request,
            final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(final HttpServletRequest request,
            final HttpServletResponse response, final Object handler, final Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

}
