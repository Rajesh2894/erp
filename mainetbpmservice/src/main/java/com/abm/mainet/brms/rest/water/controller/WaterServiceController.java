/**
 * 
 */
package com.abm.mainet.brms.rest.water.controller;

import java.util.Optional;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.abm.mainet.brms.rest.water.service.WaterService;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author Vivek.Kumar
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/water")
public class WaterServiceController {

    @Autowired
    private WaterService waterService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WaterServiceController.class);
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_WATER_CONSUMPTION = "Unable to process request for water consumption!";
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_NOOFDAYS = "Unable to process request for NoOfDays!";
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_WATER_RATE = "Unable to process request for Water Rate!";
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_WATER_TAX = "Unable to process request for Water Tax!";
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_CONSUMPTION_AND_DAYS = "Unable to process request for Water consumption and Days!";

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
            responseDTO = waterService.calculateServiceCharges(requestDTO);
        } catch (FrameworkException ex) {
            LOGGER.error(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE + ex.getMessage(), ex);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * 
     * @param requestDTO
     * @param request
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/getWaterConsumption", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> doGetWaterConsumption(@RequestBody WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = waterService.findWaterConsumption(requestDTO);
        } catch (FrameworkException ex) {
            LOGGER.error(UNABLE_TO_PROCESS_REQUEST_FOR_WATER_CONSUMPTION + ex.getMessage(), ex);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_WATER_CONSUMPTION, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * find no of days
     * @param requestDTO
     * @param request
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/getNoOfDays", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> doGetNoOfDays(@RequestBody WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = waterService.findNoOfDays(requestDTO);
        } catch (FrameworkException ex) {
            LOGGER.error(UNABLE_TO_PROCESS_REQUEST_FOR_NOOFDAYS + ex.getMessage(), ex);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_NOOFDAYS, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * 
     * @param requestDTO
     * @param request
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/getWaterRate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> doGetWaterRate(@RequestBody WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = waterService.findWaterRate(requestDTO);
        } catch (FrameworkException ex) {
            LOGGER.error(UNABLE_TO_PROCESS_REQUEST_FOR_WATER_RATE + ex.getMessage(), ex);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_WATER_RATE, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @RequestMapping(value = "/getWaterRateBill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> doGetWaterRateBill(@RequestBody WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = waterService.findWaterRateBill(requestDTO);
        } catch (FrameworkException ex) {
            LOGGER.error(UNABLE_TO_PROCESS_REQUEST_FOR_WATER_RATE + ex.getMessage(), ex);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_WATER_RATE, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * 
     * @param requestDTO
     * @param request
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/getWaterTax", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> doGetWaterTax(@RequestBody WSRequestDTO requestDTO) {

        WSResponseDTO responseDTO = null;
        try {
            responseDTO = waterService.findWaterTax(requestDTO);
        } catch (FrameworkException ex) {
            LOGGER.error(UNABLE_TO_PROCESS_REQUEST_FOR_WATER_TAX + ex.getMessage(), ex);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_WATER_TAX, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @RequestMapping(value = "/getWaterTaxBill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> doGetWaterTaxBill(@RequestBody WSRequestDTO requestDTO) {

        WSResponseDTO responseDTO = null;
        try {
            responseDTO = waterService.findWaterTaxBill(requestDTO);
        } catch (FrameworkException ex) {
            LOGGER.error(UNABLE_TO_PROCESS_REQUEST_FOR_WATER_TAX + ex.getMessage(), ex);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_WATER_TAX, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * 
     * @param requestDTO
     * @param request
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/getWaterConsumptionAndDays", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WSResponseDTO> doGetWaterConsumptionAndDays(@RequestBody WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = waterService.findWaterConsumptionAndDays(requestDTO);
        } catch (FrameworkException ex) {
            LOGGER.error(UNABLE_TO_PROCESS_REQUEST_FOR_CONSUMPTION_AND_DAYS + ex.getMessage(), ex);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_CONSUMPTION_AND_DAYS, ex);
        }
        return Optional.ofNullable(responseDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
