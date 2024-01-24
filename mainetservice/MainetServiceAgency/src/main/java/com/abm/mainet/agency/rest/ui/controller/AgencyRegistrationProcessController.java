package com.abm.mainet.agency.rest.ui.controller;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

import com.abm.mainet.agency.authentication.dto.AgencyEmployeeReqDTO;
import com.abm.mainet.agency.authentication.dto.AgencyEmployeeResDTO;
import com.abm.mainet.agency.dto.TPAgencyReqDTO;
import com.abm.mainet.agency.dto.TPAgencyResDTO;
import com.abm.mainet.agency.service.AgencyRegistrationProcessService;
import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author Arun.Chavda
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/agencyRegistration")
public class AgencyRegistrationProcessController {

    @Resource
    private AgencyRegistrationProcessService agencyRegProcessService;

    @Value("${error.code.could_not_be_converted}")
    private transient String typeCastError;

    @Value("${error.cause.could_not_be_converted}")
    private transient String typeCastErrorCause;

    @Value("${error.code.could_not_process_request}")
    private transient String processRequestError;

    @Value("${error.cause.could_not_process_request}")
    private transient String processRequestErrorCause;

    private static final String REQUESTDTO_CANT_NULL = "AgencyRegistrationDTO can not be null";
    private static final Logger LOGGER = LoggerFactory.getLogger(AgencyRegistrationProcessController.class);

    /**
     * This handler method will return License Detail Info based on Registration No. and year
     */
    @RequestMapping(value = "/saveAgnEmployee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveAgencyEmployeeDetails(@RequestBody final AgencyEmployeeReqDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        LOGGER.info("inside the saveAgencyEmployeeDetails() in AgencyRegistrationProcessController ");
        AgencyEmployeeResDTO responseDTO = new AgencyEmployeeResDTO();
        Assert.notNull(requestDTO, REQUESTDTO_CANT_NULL);
        if (!bindingResult.hasErrors()) {
            if (requestDTO != null) {
                try {
                    responseDTO = agencyRegProcessService.saveAgnEmployeeDetails(requestDTO);
                } catch (final Exception ex) {
                    ex.printStackTrace();
                    responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                    responseDTO.setErrorCode(processRequestError);
                    responseDTO.setCause(processRequestErrorCause + ex.getMessage());
                    LOGGER.error("Error during fetching License Detail:", ex);
                }
            } else {
                responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
            }
        } else {
            responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
            responseDTO.setErrorCode(typeCastError);
            responseDTO.setCause(typeCastErrorCause);
            LOGGER.error("Error during fetching License Detail:", bindingResult.getAllErrors());
        }

        return responseDTO;
    }

    /**
     * This handler method will return License Detail Info based on Registration No. and year
     */
    /**
     * This handler method will return Authorization Info based on EmpId and orgId
     */
    @RequestMapping(value = "/getAuthStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getAuthStatus(@RequestBody final TPAgencyReqDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        LOGGER.info("inside the getAuthStatus() in AgencyRegistrationProcessController ");
        TPAgencyResDTO responseDTO = new TPAgencyResDTO();
        Assert.notNull(requestDTO, REQUESTDTO_CANT_NULL);
        if (!bindingResult.hasErrors()) {
            if (requestDTO != null) {
                try {
                    responseDTO = agencyRegProcessService.getAuthStatus(requestDTO);
                } catch (final Exception ex) {
                    ex.printStackTrace();
                    responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                    responseDTO.setErrorCode(processRequestError);
                    responseDTO.setCause(processRequestErrorCause + ex.getMessage());
                    LOGGER.error("Error during fetching License Detail:", ex);
                }
            } else {
                responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
            }
        } else {
            responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
            responseDTO.setErrorCode(typeCastError);
            responseDTO.setCause(typeCastErrorCause);
            LOGGER.error("Error during fetching License Detail:", bindingResult.getAllErrors());
        }

        return responseDTO;
    }

    /**
     * This handler method will return TP Technical Person Info based on EmpId and orgId
     */

    /**
     * This handler method will return License Detail Info based on Registration No. and year
     */
    @RequestMapping(value = "/saveReUploadDoc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveReUploadDoc(@RequestBody final TPAgencyReqDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        LOGGER.info("inside the saveTPEmployeeDetails() in TownPlanningController ");
        TPAgencyResDTO responseDTO = new TPAgencyResDTO();
        Assert.notNull(requestDTO, REQUESTDTO_CANT_NULL);
        if (!bindingResult.hasErrors()) {
            if (requestDTO != null) {

                try {
                    responseDTO = agencyRegProcessService.saveReUploadDocuments(requestDTO);
                } catch (final Exception ex) {
                    ex.printStackTrace();
                    responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                    responseDTO.setErrorCode(processRequestError);
                    responseDTO.setCause(processRequestErrorCause + ex.getMessage());
                    LOGGER.error("Error during fetching License Detail:", ex);
                }
            } else {
                responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
            }
        } else {
            responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
            responseDTO.setErrorCode(typeCastError);
            responseDTO.setCause(typeCastErrorCause);
            LOGGER.error("Error during fetching License Detail:", bindingResult.getAllErrors());
        }

        return responseDTO;
    }

}
