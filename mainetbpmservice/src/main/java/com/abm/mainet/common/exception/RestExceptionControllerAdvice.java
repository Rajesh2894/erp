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

import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.brms.core.exception.RuleNotFoundException;
import com.abm.mainet.constant.MainetConstants;

@Component
@ControllerAdvice
public class RestExceptionControllerAdvice extends ResponseEntityExceptionHandler{

	private static final Logger LOGGER = Logger.getLogger(RestExceptionControllerAdvice.class);

    @ExceptionHandler(FrameworkException.class)
    @ResponseBody
    public final ResponseEntity<WSResponseDTO> handleAllExceptions(FrameworkException ex, WebRequest request) {
    	LOGGER.error(ex);
    	WSResponseDTO dto = new WSResponseDTO();
    	dto.setWsStatus(MainetConstants.COMMON_STATUS.FAIL);
    	dto.setErrorMessage(ex.getRootCause().getMessage());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    
    @ExceptionHandler(RuleNotFoundException.class)
    @ResponseBody
    public final ResponseEntity<WSResponseDTO> handleAllExceptions(RuleNotFoundException ex, WebRequest request) {
    	LOGGER.error(ex);
    	WSResponseDTO dto = new WSResponseDTO();
    	dto.setWsStatus(MainetConstants.COMMON_STATUS.FAIL);
    	dto.setErrorMessage(ex.getRootCause().getMessage());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    
}

