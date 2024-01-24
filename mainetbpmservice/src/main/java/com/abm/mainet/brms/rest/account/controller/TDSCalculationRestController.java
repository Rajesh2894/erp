package com.abm.mainet.brms.rest.account.controller;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.brms.rest.account.service.TDSCalculationService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.rule.account.datamodel.TDSCalculation;

/**
 * 
 * @author Vivek.Kumar
 * @since 14/04/2017
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/TDSCalculation")
public class TDSCalculationRestController {

    private static final Logger LOGGER = Logger.getLogger(TDSCalculationRestController.class);

    @Autowired
    private TDSCalculationService tdsCalculationService;

    @RequestMapping(value = "/rate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateTDSRate(@RequestBody TDSCalculation dataModel) {

        ResponseEntity<?> responseEntity = null;
        try {
            LOGGER.info("provided input model:" + dataModel);
            responseEntity = tdsCalculationService.validate(dataModel);
            if (responseEntity.getStatusCode() == HttpStatus.ACCEPTED) {
                responseEntity = tdsCalculationService.calculateTdsRate(dataModel);
            } else {
                LOGGER.error("Provided inputs are invalid:" + responseEntity.getBody());
            }
        } catch (FrameworkException ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR!");
            LOGGER.error("INTERNAL SERVER ERROR!" + ex.getMessage(), ex);
        }
        return responseEntity;
    }

}
