package com.abm.mainet.authentication.security;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.HttpHelper;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.ui.CustomHttpServletResponseWrapper;

public class MultiLoginPreventInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = Logger.getLogger(MultiLoginPreventInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        LOG.info("Current Session  Inside pre handle");
        CustomHttpServletResponseWrapper resWrap = new CustomHttpServletResponseWrapper(response);
        if (request.getSession(false) != null && UserSession.getCurrent().getEmployee() != null
                && UserSession.getCurrent().getEmployee().getEmploginname() != null
                && !UserSession.getCurrent().getEmployee().getEmploginname().equals("NOUSER")
                && (ApplicationSession.getInstance() != null
                        && (ApplicationSession.getInstance().getMessage("allowMultiLogin") == null
                                || !ApplicationSession.getInstance().getMessage("allowMultiLogin").equals("Y")))) {

            IEmployeeService service = (IEmployeeService) ApplicationContextProvider.getApplicationContext()
                    .getBean("employeeService");

            String sessionId = service.findEmployeeSessionId(UserSession.getCurrent().getEmployee().getEmpId(), "0");

            if (null != sessionId) {
                if (sessionId.equalsIgnoreCase(request.getRequestedSessionId())) {
                    LOG.info("Current Session Inside If");
                } else {
                	 LOG.info("Current Session Inside else ");
                    UserSession userSession = UserSession.getCurrent();
                    Organisation org = userSession.getOrganisation();
                    LOG.info("Current Session Inside else " +org);
                    String loginName = ApplicationSession.getInstance().getMessage("citizen.noUser.loginName");
                    Employee emp = service.getEmployeeByLoginName(loginName, org, "0");
                    int languageId = HttpHelper.getSessionLanguageId(request);
                    List<LookUp> paymentMode = userSession.getPaymentMode();
                    Map<Long, String> onlineBankList = userSession.getOnlineBankList();
                    request.getSession(false).invalidate();
                    userSession = UserSession.getCurrent();
                    HttpHelper.removeLidCookie(request, response);
                    if (org != null && emp != null) {
                        userSession.setOrganisation(org);
                        userSession.setEmployee(emp);
                        userSession.setLanguageId(languageId);
                        userSession.setPaymentMode(paymentMode);
                        userSession.setOnlineBankList(onlineBankList);
                        HttpHelper.setLanguage(request, resWrap, languageId, false);
                    }
                    resWrap.sendRedirect(request.getContextPath() + "/AccessDenied.html");
                    return false;

                }
            }

        }

        return true;
    }
}
