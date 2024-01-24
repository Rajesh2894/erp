
package com.abm.mainet.brms.rest.controller;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.brms.rest.service.ICommonService;

/**
 * This is common Rest Controller,all request made for BRMS will be process and handle by this Controller
 * 
 * @author Vivek.Kumar
 * @since 30 May 2016
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/brms")
public class BRMSCommonController {

    /**
     * LOGGER to log the error if something goes wrong.
     */
    private static final Logger LOGGER = Logger.getLogger(BRMSCommonController.class);

    @Autowired
    private ICommonService iCommonService;

    /**
     * this will return initialized model with their default state
     * @param requestDTO
     * @param request
     * @param bindingResult
     * @return RuleResponseDTO
     */
    @RequestMapping(value = "/getInitializedModel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> doGetInitializedModel(
            @RequestBody WSRequestDTO requestDTO) {
        LOGGER.info("Request received for [" + requestDTO.getModelName()
                + "] Model Initialization ");
        WSResponseDTO responseDTO = null;
        responseDTO = iCommonService.returnInitializedModels(requestDTO);
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * request handler method to process request and return applicable checklist for Service
     * 
     * @param request
     * @param bindingResult
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "/getCheckList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<WSResponseDTO> doGetCheckList(@RequestBody WSRequestDTO requestDTO) {
        LOGGER.info("Request received to find applicable CheckList");
        WSResponseDTO responseDTO = null;
        responseDTO = iCommonService.findApplicableCheckList(requestDTO);
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
