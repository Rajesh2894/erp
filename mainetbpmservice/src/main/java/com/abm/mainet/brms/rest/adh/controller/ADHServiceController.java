package com.abm.mainet.brms.rest.adh.controller;

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
import com.abm.mainet.brms.rest.adh.service.IADHService;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author vishwajeet.kumar
 * @since 22 October 2019
 */

@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL)
})
@RestController
@RequestMapping("/adh")
public class ADHServiceController {

    @Autowired
    private IADHService adhService;

    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for New ADH Application charge!";

    /**
     * 
     * getNewADHApplicationCharges
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "/getNewADHApplicationCharges", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> getAdhApplicationCharge(@RequestBody WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = adhService.getNewADHApplicationCharges(requestDTO);
        } catch (FrameworkException exception) {
            throw new FrameworkException("Unable to process request for New ADH Application charge!", exception);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @RequestMapping(value = "/getChargesForMutlipleAahDetApplication", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> calculateRateForMultipleProperty(@RequestBody WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = adhService.calculateRateForMultipleADHApplicationCharges(requestDTO);
        } catch (FrameworkException ex) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
