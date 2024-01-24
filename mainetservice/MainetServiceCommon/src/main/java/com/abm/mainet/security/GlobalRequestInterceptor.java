package com.abm.mainet.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.HttpHelper;
import com.abm.mainet.common.utility.JsonHelper;
import com.abm.mainet.common.utility.UserSession;

public class GlobalRequestInterceptor extends HandlerInterceptorAdapter {
    private static final String PRAGMA = "Pragma";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String NO_CACHE = "no-cache";
    private static final String P3P = "P3P";
    private static final String EXPIRES = "Expires";
    private final static String homePagePathSlash = "/";
    private final static String homePagePath = "/Home.html";
    private final static String VIEW_PROP_DETAIL = "/ViewPropertyDetail.html";

    private boolean isHomePageUrl(final HttpServletRequest request) {
        //Defect #31605: Handled case wherein OTP verification page was not loading. added "/AdminOTPVerification.html", "/AdminResetPassword.html", "/AdminSetPassword.html" to the list of home Urls

        return (request.getServletPath().equalsIgnoreCase(homePagePath)
                || request.getServletPath().equalsIgnoreCase(homePagePathSlash)
               || request.getServletPath().equalsIgnoreCase(VIEW_PROP_DETAIL))
        		|| request.getServletPath().equalsIgnoreCase("/AdminLogin.html")
        		 || request.getServletPath().equalsIgnoreCase("/AdminResetPassword.html") 
                 || request.getServletPath().equalsIgnoreCase("/AdminOTPVerification.html")
                 || request.getServletPath().equalsIgnoreCase("/AdminUpdatePersonalDtls.html")
                 || request.getServletPath().equalsIgnoreCase("/AdminSetPassword.html")
                 ||request.getServletPath().equalsIgnoreCase("/PaymentController.html");
        
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        if (isHomePageUrl(request)) {
            return true;
        }

        final HttpSession session = request.getSession(false);

        if (!isSessionValid(session)) {
            redirectToHomePage(request, response);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
            final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);

        response.setHeader(CACHE_CONTROL, "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader(PRAGMA, NO_CACHE); // HTTP 1.0
        response.setDateHeader(EXPIRES, 0); // Proxies.
        // response.addHeader(P3P, "CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
        /*
         * response.setHeader("SET-COOKIE", "JSESSIONID=" + request.getSession().getId() +"; HttpOnly");
         * response.setHeader("Access-Control-Allow-Origin", "*"); response.setHeader("Access-Control-Allow-Methods",
         * "POST, GET, PUT, DELETE"); response.setHeader("Access-Control-Allow-Headers",
         * "x-requested-with, origin, content-type, accept"); response.setHeader("Access-Control-Max-Age", "18000");
         */

    }

    private void redirectToHomePage(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String redirectLoginUrl = StringUtils.replace(request.getRequestURL().toString(), request.getServletPath(),
                homePagePath);

        final boolean asyncRequest = HttpHelper.isAjaxRequest(request);

        if (asyncRequest) {
            response.setContentType("application/json");

            final Map<String, String> jsonMap = new HashMap<>();
            final String msg = "<h5 class='text-center padding-10 text-blue-2'>"
                    + ApplicationSession.getInstance().getMessage("eip.admin.session")
                    + "<h5><div class='text-center'><a href='LogOut.html' class='btn btn-info'>click here</a></div>";
            jsonMap.put(msg, null);
            String output = JsonHelper.toJsonString(jsonMap);
            output = output.replaceAll("[{}:,]", MainetConstants.BLANK);
            output = output.replace(MainetConstants.StandardAccountHeadMapping.NULL, MainetConstants.BLANK);
            final int outputLength = output.length();
            if ((outputLength >= 2) && (output.charAt(0) == '"') && (output.charAt(outputLength - 1) == '"')) {
                output = output.substring(1, outputLength - 1);
            }
            response.getOutputStream().write(output.getBytes());
        } else {
            response.sendRedirect(redirectLoginUrl);
        }
    }

    public Boolean isSessionValid(final HttpSession session) {
        boolean validSession = false;
        if ((session != null) && (UserSession.getCurrent().getOrganisation() != null) && (UserSession.getCurrent().getEmployee() != null) && (UserSession.getCurrent().getEmployee().getEmpId() != null)) {
            validSession = true;
        }
        return validSession;

    }
}
