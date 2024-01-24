package com.abm.mainet.security;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.HttpHelper;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

public class MultiLoginPreventInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(MultiLoginPreventInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
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
                    LOG.info("Current Session");
                } else {
                    UserSession userSession = UserSession.getCurrent();
                    Organisation org = userSession.getOrganisation();
                    /*No Employee before login*/
                    //String loginName = ApplicationSession.getInstance().getMessage("citizen.noUser.loginName");
                  //  Employee emp = service.getEmployeeByLoginName(loginName, org, "0");
                    int languageId = HttpHelper.getSessionLanguageId(request);
                    List<LookUp> paymentMode = userSession.getPaymentMode();
                    request.getSession(false).invalidate();
                    userSession = UserSession.getCurrent();
                    if (org != null ) {
                        userSession.setOrganisation(org);
                        //userSession.setEmployee(emp);
                        userSession.setLanguageId(languageId);
                        userSession.setPaymentMode(paymentMode);
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
