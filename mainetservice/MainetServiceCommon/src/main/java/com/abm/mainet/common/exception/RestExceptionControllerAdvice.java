package com.abm.mainet.common.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ErrorResource;
import com.abm.mainet.common.dto.FieldErrorResource;

@Component
@ControllerAdvice
public class RestExceptionControllerAdvice extends ResponseEntityExceptionHandler{

    private final Logger LOG = Logger.getLogger(RestExceptionControllerAdvice.class);
    private static final String PLEASE_CHECK_ERROR_LIST = "Please Check Error List";

    @ExceptionHandler(FrameworkException.class)
    @ResponseBody
    public final ResponseEntity<?> handleAllExceptions(FrameworkException ex, WebRequest request) {
	LOG.error(ex);
	ResponseEntity<?> respEntity =  new ResponseEntity<>("{}", HttpStatus.OK);
	respEntity.getHeaders().set("errCode", (ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage()));
	respEntity.getHeaders().set("errMsg", ex.getMessage());
	respEntity.getHeaders().set("status", MainetConstants.COMMON_STATUS.FAIL);
        return respEntity;
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResource handleException(final MethodArgumentNotValidException exception) {
        LOG.error("error", exception);
        final BindingResult bindingResult = exception.getBindingResult();
        final List<FieldErrorResource> fieldErrorResources = new ArrayList<>();

        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (final FieldError fieldError : fieldErrors) {
            final FieldErrorResource fieldErrorResource = new FieldErrorResource();
            fieldErrorResource.setResource(fieldError.getObjectName());
            fieldErrorResource.setField(fieldError.getField());
            fieldErrorResource.setCode(fieldError.getCode());
            fieldErrorResource.setMessage(fieldError.getDefaultMessage());
            fieldErrorResources.add(fieldErrorResource);
        }
        final ErrorResource error = new ErrorResource(MainetConstants.INVALID_REQUEST, PLEASE_CHECK_ERROR_LIST);
        error.setFieldErrors(fieldErrorResources);
        error.setStatus(MainetConstants.COMMON_STATUS.FAIL);
        return error;
    }

    @ExceptionHandler({ InvalidRequestException.class })
    public ErrorResource handleInvalidRequest(final RuntimeException e) {
        final InvalidRequestException ire = (InvalidRequestException) e;
        LOG.error("InvalidRequestException", ire);
        final List<FieldErrorResource> fieldErrorResources = new ArrayList<>();

        final List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
        for (final FieldError fieldError : fieldErrors) {
            final FieldErrorResource fieldErrorResource = new FieldErrorResource();
            fieldErrorResource.setResource(fieldError.getObjectName());
            fieldErrorResource.setField(fieldError.getField());
            fieldErrorResource.setCode(fieldError.getCode());
            fieldErrorResource.setMessage(fieldError.getDefaultMessage());
            fieldErrorResources.add(fieldErrorResource);
        }
        final ErrorResource error = new ErrorResource(MainetConstants.INVALID_REQUEST, ire.getMessage());
        error.setStatus(MainetConstants.COMMON_STATUS.FAIL);
        error.setFieldErrors(fieldErrorResources);
        return error;
    }
    
    
}

