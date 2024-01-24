/*package com.abm.mainet.common.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ErrorResource;
import com.abm.mainet.common.dto.FieldErrorResource;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.exception.InvalidRequestException;
import com.abm.mainet.common.exception.RestCommonExeception;

@ControllerAdvice
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GlobalExceptionController {
    private static final String PLEASE_CHECK_ERROR_LIST = "Please Check Error List";
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

    @ExceptionHandler(FrameworkException.class)
    public ModelAndView handleCustomException(final FrameworkException ex, HttpServletRequest request) {
        LOG.error("Exception found : ", ex);
        final ModelAndView model = new ModelAndView();
        if (request.getMethod().equalsIgnoreCase(RequestMethod.GET.name())) {
            model.setViewName(MainetConstants.DEFAULT_EXCEPTION_VIEW);
        }
        if (request.getMethod().equalsIgnoreCase(RequestMethod.POST.name())) {
            model.setViewName(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }
        if (ex.getErrCode() != null) {
            model.addObject(MainetConstants.ERR_CODE, ex.getErrCode());
        }
        model.addObject(MainetConstants.ERR_MSG, ex.getErrMsg());
        return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(final Exception ex, HttpServletRequest request) {
        LOG.error("Exception found : ", ex);
        final ModelAndView model = new ModelAndView();
        if (request.getMethod().equalsIgnoreCase(RequestMethod.GET.name())) {
            model.setViewName(MainetConstants.DEFAULT_EXCEPTION_VIEW);
        }
        if (request.getMethod().equalsIgnoreCase(RequestMethod.POST.name())) {
            model.setViewName(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }
        return model;
    }

    @ExceptionHandler(RestCommonExeception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map handleException(final RuntimeException exception) {
        LOG.error("error", exception);
        return getExceptionAsMap(exception, new ArrayList<Throwable>());
    }

    private Map getExceptionAsMap(final Throwable exception, final List<Throwable> exceptions) {
        final Map exceptionAsMap = new HashMap();
        exceptionAsMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.COMMON_STATUS.FAIL);
        exceptionAsMap.put(MainetConstants.MESSAGE, exception.getMessage());
        if ((exception.getCause() != null) && !exceptions.contains(exception)) {
            exceptions.add(exception);
            final Map causeAsMap = getExceptionAsMap(exception.getCause(), exceptions);
            exceptionAsMap.put(MainetConstants.CAUSE, causeAsMap);
        }

        return exceptionAsMap;
    }

}
*/