package com.abm.mainet.brms.rest.propertytax.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.brms.core.utility.CommonMasterUtility;
import com.abm.mainet.brms.rest.service.RuleEngineService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.rule.propertytax.datamodel.ALVMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.FactorMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyRateMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyTaxDataModel;

/**
 * this REST Controller handle all calculation related to Property Tax like, ALV,ARV, RATE etc.
 * @author Vivek.Kumar
 * @since 27/04/2017
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/PropertyTax")
public class PropertyTaxRestController {

    private static final Logger LOGGER = Logger.getLogger(PropertyTaxRestController.class);

    @Autowired
    private RuleEngineService ruleEngineService;

    /**
     * use this in order to get ALV
     * @param dataModel : pass {@code ALVMasterModel} model
     * @return
     */
    @RequestMapping(value = "/alv", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateALV(@RequestBody WSRequestDTO request) {

        ResponseEntity<?> responseEntity = null;
        try {
            Map<String, ALVMasterModel> alvMap = CommonMasterUtility.castRequestToALVapModel(request);
            WSResponseDTO responseDTO = new WSResponseDTO();
            alvMap.forEach((key, value) -> {
                ruleEngineService.calculateALV(value);
                alvMap.put(key, value);
            });
            responseDTO.setResponseObj(alvMap);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (FrameworkException ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR!");
            LOGGER.error("INTERNAL SERVER ERROR!" + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    /**
     * use this in order to get Factor
     * @param dataModel
     * @return
     */
    @RequestMapping(value = "/factor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateFactor(@RequestBody WSRequestDTO request) {

        ResponseEntity<?> responseEntity = null;
        WSResponseDTO responseDTO = new WSResponseDTO();
        try {
            Map<String, List<FactorMasterModel>> factorMap = CommonMasterUtility.castRequestToDataModelMapRate(
                    request,
                    FactorMasterModel.class);
            factorMap.forEach((key, value) -> {
                value.forEach(factor -> {
                    ruleEngineService.calculateFactor(factor);
                });
                factorMap.put(key, value);
            });
            responseDTO.setResponseObj(factorMap);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (FrameworkException ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR!");
            LOGGER.error("INTERNAL SERVER ERROR!" + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    /**
     * use this in order to get Property Rate
     * @param dataModel
     * @return
     */
    @RequestMapping(value = "/rate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateRate(@RequestBody WSRequestDTO request) {

        ResponseEntity<?> responseEntity = null;
        WSResponseDTO responseDTO = new WSResponseDTO();
        try {
            Map<String, Map<Date, List<PropertyRateMasterModel>>> dataModel = CommonMasterUtility
                    .castRequestToTaxModel(request);

            dataModel.forEach((key, schMap) -> {
                schMap.forEach((schDate, taxModelList) -> {
                    taxModelList.forEach(taxModel -> {
                        ruleEngineService.calculatePropertyTax(taxModel);
                    });
                });
            });

            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
            responseDTO.setResponseObj(dataModel);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (FrameworkException ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR!");
            LOGGER.error("INTERNAL SERVER ERROR!" + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    /**
     * use this in order to get Property Rate
     * @param dataModel
     * @return
     */
    @RequestMapping(value = "/PropertyLevelrate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculatePropertyLevelrate(@RequestBody WSRequestDTO request) {

        ResponseEntity<?> responseEntity = null;
        WSResponseDTO responseDTO = new WSResponseDTO();
        try {
            Map<Date, List<PropertyRateMasterModel>> dataModel = CommonMasterUtility
                    .castRequestToTaxModelPropertyLevel(request);
            dataModel.forEach((schDate, taxModelList) -> {
                taxModelList.forEach(taxModel -> {
                    ruleEngineService.calculatePropertyTax(taxModel);
                });
            });
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
            responseDTO.setResponseObj(dataModel);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (FrameworkException ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR!");
            LOGGER.error("INTERNAL SERVER ERROR!" + ex.getMessage(), ex);
        }
        return responseEntity;
    }

    /**
     * use this in order to get sddr Rate
     * @param dataModel
     * @return
     */
    @RequestMapping(value = "/sddrRate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateSDDRRate(@RequestBody WSRequestDTO request) {
        ResponseEntity<?> responseEntity = null;
        try {
            Map<String, PropertyTaxDataModel> sddrMap = CommonMasterUtility.castRequestToSddrRate(request);
            WSResponseDTO responseDTO = new WSResponseDTO();
            sddrMap.forEach((key, value) -> {
                ruleEngineService.calculateSDDRRate(value);
                sddrMap.put(key, value);
            });
            responseDTO.setResponseObj(sddrMap);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (FrameworkException ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR!");
            LOGGER.error("INTERNAL SERVER ERROR!" + ex.getMessage(), ex);
        }
        return responseEntity;
    }

}
