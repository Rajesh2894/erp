package com.abm.mainet.config.logging;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.dms.utility.ConversionUtility;

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
        String userId = MainetConstants.Common.BLANK;
        String orgId = MainetConstants.Common.BLANK;
        MDC.put(MainetConstants.LOGGING.START_TIME,
                ConversionUtility.dateToString(new Date(), MainetConstants.Common.DATE_FORMAT));
        MDC.put(MainetConstants.LOGGING.ORGID, orgId);
        MDC.put(MainetConstants.LOGGING.USER, userId);
        MDC.put(MainetConstants.LOGGING.REQ_UUID, UUID.randomUUID());
        MDC.put(MainetConstants.LOGGING.LOGGING_URL, request.getRequestURL());
        LOGGER.info(MainetConstants.LOGGING.BEFORE_REQUEST_LOGGING);
    }

    @Override
    protected void afterRequest(final HttpServletRequest request, final String message) {
        super.afterRequest(request, message);
        final Date startTime = ConversionUtility.stringToDate(MDC.get(MainetConstants.LOGGING.START_TIME).toString(),
                MainetConstants.Common.DATE_FORMAT);
        final long milliseconds = new Date().getTime() - startTime.getTime();
        MDC.put(MainetConstants.LOGGING.DURATION, milliseconds);
        LOGGER.info(MainetConstants.LOGGING.AFTER_REQUEST_LOGGING);
        MDC.remove(MainetConstants.LOGGING.START_TIME);
        MDC.remove(MainetConstants.LOGGING.REQ_UUID);
        MDC.remove(MainetConstants.LOGGING.ORGID);
        MDC.remove(MainetConstants.LOGGING.USER);
        MDC.remove(MainetConstants.LOGGING.DURATION);
        MDC.remove(MainetConstants.LOGGING.LOGGING_URL);
    }

}
