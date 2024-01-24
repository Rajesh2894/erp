package com.abm.mainet.water.rest.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;
import com.abm.mainet.water.service.WaterReconnectionService;

/**
 * @author Arun.Chavda
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/WaterReconnection")
public class WaterReconnectionController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(WaterReconnectionController.class);

    @Resource
    private WaterReconnectionService waterReconnectionService;

    @RequestMapping(value = "/searchReconnectionDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getConnectionDetails(
            @RequestBody final WaterReconnectionRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        List<WaterReconnectionResponseDTO> responseDTO = new ArrayList<>();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {

            try {
                responseDTO = waterReconnectionService.getDisconnectionDetailsForReConnection(requestDTO);

                if (!responseDTO.isEmpty()) {
                    responseDTO.get(0)
                            .setAlreadyApplied(waterReconnectionService.isAlreadyAppliedForReConn(responseDTO.get(0).getCsIdn()));
                }
                requestDTO.setResponseDTOs(responseDTO);

            } catch (final Exception ex) {
                LOGGER.error("error while fetching connection details", ex);
            }

        } else {

            LOGGER.error("error while fetching connection details :", bindingResult.getAllErrors());
        }

        return requestDTO;
    }

    @RequestMapping(value = "/registeredPlumberLicNo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object isRegisteredPlumberLicNo(
            @RequestBody final WaterReconnectionRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        WaterReconnectionResponseDTO responseDTO = new WaterReconnectionResponseDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {

            try {
                responseDTO = waterReconnectionService.checkIsRegisteredPlumberLicNo(requestDTO);
            } catch (final Exception ex) {
                LOGGER.error("error while fetching connection details", ex);
            }

        } else {

            LOGGER.error("error while fetching connection details :", bindingResult.getAllErrors());
        }

        return responseDTO;
    }
    // saveReconnectionData

    @RequestMapping(value = "/saveReconnectionData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveChangeData(@RequestBody final WaterReconnectionRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        WaterReconnectionResponseDTO responseDTO = new WaterReconnectionResponseDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            try {
                responseDTO = waterReconnectionService.saveWaterReconnectionDetails(requestDTO);
                waterReconnectionService.initiateWorkflowForFreeService(requestDTO);
            } catch (final Exception ex) {
                responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                LOGGER.error("Exception occured at Saving data for Water Reconnection  Controller", ex);
            }
        }

        return responseDTO;

    }

}
