package com.abm.mainet.water.rest.ui.controller;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
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
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.water.dto.ChangeOfOwnerRequestDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerResponseDTO;
import com.abm.mainet.water.service.ChangeOfOwnerShipService;

/**
 * @author Rahul.Yadav
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/ChangeOfOwnerWaterConnection")
public class ChangeOfOwnerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOfOwnerRestController.class);

    @Resource
    private ChangeOfOwnerShipService iChangeOfOwnerShipService;

    @RequestMapping(value = "/getOldConnectionData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getOldConnectionData(
            @RequestBody final ChangeOfOwnerRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        ChangeOfOwnerResponseDTO responseDTO = new ChangeOfOwnerResponseDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {

            try {
                responseDTO = iChangeOfOwnerShipService.fetchAndVelidatetConnectionData(requestDTO.getConnectionNo(),
                        requestDTO.getOrgnId());
            } catch (final NoResultException ex) {
                responseDTO.setStatus(MainetConstants.PAY_STATUS_FLAG.NO);
                LOGGER.error("No Record found for connection no=" + requestDTO.getConnectionNo(), ex);
            } catch (final Exception ex) {
                responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                LOGGER.error("Exception occured at change of Ownere Search Rest Controller", ex);
            }
        }
        return responseDTO;

    }

    @RequestMapping(value = "/saveChangeData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveChangeData(@RequestBody final ChangeOfOwnerRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        ChangeOfOwnerResponseDTO responseDTO = new ChangeOfOwnerResponseDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            try {
                responseDTO = (ChangeOfOwnerResponseDTO) iChangeOfOwnerShipService.saveChangeData(requestDTO);

                if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getStatus())) {
                	iChangeOfOwnerShipService.initiateWorkFlowForFreeService(requestDTO);
                }
                
            } catch (final Exception ex) {
                responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                LOGGER.error("Exception occured at change of Ownership saveChangeData Rest Controller", ex);
            }
        }

        return responseDTO;

    }

}
