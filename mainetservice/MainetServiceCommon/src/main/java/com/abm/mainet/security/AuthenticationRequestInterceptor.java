package com.abm.mainet.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IAuthorizationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

public class AuthenticationRequestInterceptor extends HandlerInterceptorAdapter {

    private static String homePagePath = "/Home.html";
    private static String homePagePath1 = "/entitlement.html";
    private static String requestedURl = "";

    ApplicationSession session = ApplicationSession.getInstance();

    @Override
    public boolean preHandle(final HttpServletRequest request,
            final HttpServletResponse response, final Object handler) throws Exception {
        requestedURl = request.getServletPath();

        if (isHomeUrl(requestedURl)) {
            return true;

        } else if ((UserSession.getCurrent().getEmployee() != null
                && UserSession.getCurrent().getEmployee().getEmploginname() != null)
                && !UserSession.getCurrent().getEmployee().getEmploginname().equalsIgnoreCase("NOUSER")) {
            String key = requestedURl.substring(requestedURl.indexOf(MainetConstants.WINDOWS_SLASH) + 1).trim();
            final Long gmid = UserSession.getCurrent().getEmployee().getGmid();

            if (gmid != null) {
                String headerName = request.getHeader("x-requested-with");
                if (null == headerName) {
                    IAuthorizationService service = (IAuthorizationService) ApplicationContextProvider.getApplicationContext()
                            .getBean("authorizationService");
                    if (service.isAuthUrl(key, gmid, UserSession.getCurrent().getOrganisation().getOrgid())) {
                        return true;
                    } else {
                        response.sendRedirect(request.getContextPath() + "/AutherizationFail.html");
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }

        return true;
    }

    private boolean isHomeUrl(final String requestedURl2) {

        return requestedURl2.equalsIgnoreCase(homePagePath)
                || requestedURl2.contains(homePagePath1)
                || requestedURl2.contains("/AdminHome.html")
                || requestedURl2.contains("/LogOut.html")
                || requestedURl2.contains("/AutherizationFail.html")
                || requestedURl2.contains("/Autherization.html")
                || requestedURl2.contains("/Accessibility.html")
                || requestedURl2.contains("/PaymentController.html")
                || requestedURl2.contains("/ChallanAtULBCounter.html")
                || requestedURl2.contains("/ChecklistVerification.html")
                || requestedURl2.contains("/sitemap.html")
                || requestedURl2.contains("/LoiPayment.html")
                || requestedURl2.contains("/CommonRejectionLetter.html")
                || requestedURl2.contains("/AdminDashboard.html")
                || requestedURl2.contains("/OperatorDashboardView.html");
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
