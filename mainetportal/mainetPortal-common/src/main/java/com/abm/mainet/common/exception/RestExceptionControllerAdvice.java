package com.abm.mainet.common.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
@ControllerAdvice
public class RestExceptionControllerAdvice extends ResponseEntityExceptionHandler{

    private final Logger LOG = Logger.getLogger(RestExceptionControllerAdvice.class);


    @ExceptionHandler(FrameworkException.class)
    @ResponseBody
    public final ResponseEntity<ErrorResponse> handleAllExceptions(FrameworkException ex, WebRequest request) {
	LOG.error(ex);
        ErrorResponse error = new ErrorResponse();
        error.setCode(ex.getErrCode());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
    
    
}

