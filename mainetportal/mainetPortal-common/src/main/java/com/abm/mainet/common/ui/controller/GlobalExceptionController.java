package com.abm.mainet.common.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.ErrorResource;
import com.abm.mainet.common.exception.FieldErrorResource;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.exception.InvalidRequestException;

@ControllerAdvice
public class GlobalExceptionController {
    private final Logger LOG = Logger.getLogger(GlobalExceptionController.class);

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
        final ErrorResource error = new ErrorResource(MainetConstants.INVALID_REQUEST, "Please Check Error List");
        error.setFieldErrors(fieldErrorResources);
        error.setStatus(MainetConstants.FAIL);
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
        error.setStatus(MainetConstants.FAIL);
        error.setFieldErrors(fieldErrorResources);
        return error;
    }

    @ExceptionHandler(FrameworkException.class)
    public ModelAndView handleCustomException(final FrameworkException ex) {
        LOG.error(MainetConstants.EXCEPTION_OCCURED, ex);
        final ModelAndView model = new ModelAndView();
        model.setViewName(MainetConstants.DEFAULT_EXCEPTION_VIEW);
        if (ex.getErrCode() != null) {
            model.addObject("errCode", ex.getErrCode());
        }
        model.addObject("errMsg", ex.getErrMsg());
        return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(final Exception ex) {
        LOG.error(MainetConstants.EXCEPTION_OCCURED, ex);
        final ModelAndView model = new ModelAndView();
        model.setViewName(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        return model;
    }

}
