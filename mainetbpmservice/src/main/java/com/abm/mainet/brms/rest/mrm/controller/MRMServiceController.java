package com.abm.mainet.brms.rest.mrm.controller;

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
import com.abm.mainet.brms.rest.mrm.service.IMRMService;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author akshay.golam
 * @since 17 Nov 2020
 */

@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL)
})
@RestController
@RequestMapping("/mrm")
public class MRMServiceController {
	
	@Autowired
    private IMRMService mrmService;

    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for MRM Service charge!";

    /**
     * 
     * getNewADHApplicationCharges
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "/getMRMCharges", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> getServiceCharge(@RequestBody WSRequestDTO requestDTO) {
    	WSResponseDTO responseDTO = null;
        try {
            responseDTO = mrmService.getServiceCharges(requestDTO);
        } catch (FrameworkException exception) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, exception);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
