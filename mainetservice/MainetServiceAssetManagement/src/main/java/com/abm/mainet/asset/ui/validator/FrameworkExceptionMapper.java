package com.abm.mainet.asset.ui.validator;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.abm.mainet.common.exception.FrameworkException;

public class FrameworkExceptionMapper implements ExceptionMapper<FrameworkException> {

    @Override
    public Response toResponse(FrameworkException exception) {

        return Response.status(Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON).build();
    }

}
