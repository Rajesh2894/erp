package com.abm.mainet.common.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.abm.mainet.constant.MainetConstants;

@Component
public class CXFFrameworkExceptionHandler implements ExceptionMapper<FrameworkException> {
	private static final Logger LOGGER = Logger.getLogger(CXFFrameworkExceptionHandler.class);

	@Override
	public Response toResponse(FrameworkException e) {
		LOGGER.error(e.getMessage());
		Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

		if (e instanceof WorkflowFrameworkException) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setCode(MainetConstants.COMMON_STATUS.FAIL);
			errorResponse.setMessage((e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage()));
			return Response.status(status).header("status", MainetConstants.COMMON_STATUS.FAIL)
					.header("errCode", "BPM_PROCESS_ERROR")
					.header("errMsg", (e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage()))
					.entity(errorResponse).build();
		} else if (e instanceof MapFrameworkException) {
			Map<String, Object> statusMap = new LinkedHashMap<>();
			statusMap.put("status", MainetConstants.COMMON_STATUS.FAIL);
			statusMap.put("errCode", (e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage()));
			statusMap.put("errMsg", e.getMessage());
			return Response.status(status).header("status", MainetConstants.COMMON_STATUS.FAIL)
					.header("errCode", "BPM_PROCESS_ERROR")
					.header("errMsg", (e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage()))
					.entity(statusMap).build();
		} else {
			List<Object> emptyList = new ArrayList<>();
			return Response.status(status).header("status", MainetConstants.COMMON_STATUS.FAIL)
					.header("errCode", "BPM_PROCESS_ERROR")
					.header("errMsg", (e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage()))
					.entity(emptyList).build();
		}

	}

}
