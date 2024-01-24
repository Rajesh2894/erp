package com.abm.mainet.common.logging;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

public class MainetRequestLoggingFilter extends CommonsRequestLoggingFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainetRequestLoggingFilter.class);

    public MainetRequestLoggingFilter() {
        super.setIncludeClientInfo(true);
        super.setIncludePayload(true);
        super.setIncludeQueryString(true);
        super.setMaxPayloadLength(10000);
    }

    @Override
    protected void beforeRequest(final HttpServletRequest request, final String message) {
        super.beforeRequest(request, message);
        LOGGER.debug(MainetConstants.BEFORE_REQUEST_LOGGING);
        String userId = MainetConstants.BLANK;
        String orgId = MainetConstants.BLANK;
        String sessionId = MainetConstants.BLANK;
        final HttpSession session = request.getSession(false);
        Date date=new Date();
   
        if (session != null) {
            final UserSession userSession = (UserSession) session.getAttribute(MainetConstants.USER_SESSION);
           //119534  Checking whether the scheduling time is expired or not
            if(userSession != null && userSession.getValidEmpFlag()!= null && userSession.getValidEmpFlag().equals(MainetConstants.FlagY) && userSession.getScheduleDate() != null) {
              	if(userSession.getScheduleDate().before(date)) {
               		request.getSession(false).invalidate();
               	}
            }
            if ((userSession != null) && (userSession.getOrganisation() != null)) {
                orgId = String.valueOf(userSession.getOrganisation().getOrgid());
            }
            if ((userSession != null) && (userSession.getEmployee() != null)) {
                userId = String.valueOf(userSession.getEmployee().getEmpId());
            }
            sessionId = session.getId();
        }
        MDC.put(MainetConstants.START_TIME, Utility.dateToString(new Date()));
        MDC.put(MainetConstants.ORGID, orgId);
        MDC.put(MainetConstants.USER, userId);
        MDC.put(MainetConstants.REQ_UUID, sessionId);
        MDC.put(MainetConstants.LOGGING_URL, request.getRequestURL());

    }

    @Override
    protected void afterRequest(final HttpServletRequest request, final String message) {
        super.afterRequest(request, message);
        final Date startTime = Utility.stringToDate(MDC.get(MainetConstants.START_TIME).toString(),
                MainetConstants.COMMON_DATE_FORMAT);
        final long milliseconds = new Date().getTime() - startTime.getTime();
        LOGGER.debug("Total time taken in Miillisecond");
        MDC.put(MainetConstants.DURATION, milliseconds);
        MDC.remove(MainetConstants.START_TIME);
        MDC.remove(MainetConstants.REQ_UUID);
        MDC.remove(MainetConstants.ORGID);
        MDC.remove(MainetConstants.USER);
        MDC.remove(MainetConstants.DURATION);
        MDC.remove(MainetConstants.LOGGING_URL);

        LOGGER.info(MainetConstants.AFTER_REQUEST_LOGGING);
    }

}
