package com.abm.mainet.common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;

@Component
public class CXFFrameworkExceptionHandler implements ExceptionMapper<FrameworkException> {
    private static final Logger LOGGER = Logger.getLogger(CXFFrameworkExceptionHandler.class);

    @Override
    public Response toResponse(FrameworkException e) {
	LOGGER.error(e.getMessage());
	Response.Status status = Response.Status.OK;
	return Response.status(status).header("status", MainetConstants.COMMON_STATUS.FAIL)
		.header("errCode", (e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage()))
		.header("errMsg", e.getMessage()).entity("{}").build();
    }

}
