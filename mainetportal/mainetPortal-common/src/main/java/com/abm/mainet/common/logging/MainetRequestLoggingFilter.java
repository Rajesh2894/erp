package com.abm.mainet.common.logging;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;
import org.apache.log4j.Logger;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

public class MainetRequestLoggingFilter extends CommonsRequestLoggingFilter {
    private static final Logger LOGGER = Logger.getLogger(MainetRequestLoggingFilter.class);

    public MainetRequestLoggingFilter() {
        super.setIncludeClientInfo(true);
        super.setIncludePayload(true);
        super.setIncludeQueryString(true);
        super.setMaxPayloadLength(10000);
    }

    @Override
    protected void beforeRequest(final HttpServletRequest request, final String message) {
        super.beforeRequest(request, message);
        String userId = MainetConstants.BLANK;
        String orgId = MainetConstants.BLANK;
        final HttpSession session = request.getSession(false);
        if (session != null) {
            final UserSession userSession = (UserSession) session.getAttribute(MainetConstants.USER_SESSION);
            if ((userSession != null) && (userSession.getOrganisation() != null)) {
                orgId = String.valueOf(userSession.getOrganisation().getOrgid());
            }
            if ((userSession != null) && (userSession.getEmployee() != null)) {
                userId = String.valueOf(userSession.getEmployee().getEmpId());
            }
        }
        MDC.put(MainetConstants.START_TIME, Utility.dateToString(new Date()));
        MDC.put(MainetConstants.ORGID, orgId);
        MDC.put(MainetConstants.USER, userId);
        MDC.put(MainetConstants.REQ_UUID,
                request.getRequestedSessionId() == null ? UUID.randomUUID() : request.getRequestedSessionId());

        MDC.put(MainetConstants.LOGGING_URL, request.getRequestURL());
        LOGGER.info(MainetConstants.BEFORE_REQUEST_LOGGING);
    }

    @Override
    protected void afterRequest(final HttpServletRequest request, final String message) {
        if(request.getContentType() != null && request.getContentType().contains("multipart/form-data")) {
    		final String shortmessage = message.substring(0, message.indexOf("Content-Type:"));
    		super.afterRequest(request, shortmessage);
    	}else {
        super.afterRequest(request, message);
    	}   
        final Date startTime = Utility.stringToDate(MDC.get(MainetConstants.START_TIME).toString(),
                MainetConstants.COMMON_DATE_FORMAT);
        final long milliseconds = new Date().getTime() - startTime.getTime();
        MDC.put(MainetConstants.DURATION, milliseconds);
        LOGGER.info(MainetConstants.AFTER_REQUEST_LOGGING);
        MDC.remove(MainetConstants.START_TIME);
        MDC.remove(MainetConstants.REQ_UUID);
        MDC.remove(MainetConstants.ORGID);
        MDC.remove(MainetConstants.USER);
        MDC.remove(MainetConstants.DURATION);
        MDC.remove(MainetConstants.LOGGING_URL);
    }

}
