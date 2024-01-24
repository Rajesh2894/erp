/**
 * 
 */
package com.abm.mainet.brms.rest.swm.controller;

import java.util.Optional;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.brms.rest.swm.service.ISWMService;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author sarojkumar.yadav
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/swm")
public class SWMServiceController {

	@Autowired
    private ISWMService swmService;

    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for service charge!";
	
	  /**
     * 
     * @param requestDTO
     * @param request
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/getServiceCharge", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> doGetServiceCharges(@RequestBody WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = swmService.calculateServiceCharges(requestDTO);
        } catch (FrameworkException ex) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

  /*  *//**
     * 
     * @param requestDTO
     * @param request
     * @param bindingResult
     * @return
     *//*
    @RequestMapping(value = "/getTaxPercentage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<WSResponseDTO> doGetTaxPercentage(@RequestBody WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = null;
        try {
           // responseDTO = rnlService.calculateTaxPercentage(requestDTO);
        } catch (FrameworkException ex) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
*/
}
