package com.abm.mainet.common.integration.rest.ui.controller;

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

import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dto.CommonServiceRequestDTO;
import com.abm.mainet.common.service.CommonService;

/**
 * @author Rahul.Yadav
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/CommonServiceDataRestController")
public class CommonServiceDataRestController {

    @Resource
    private CommonService commonService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonServiceDataRestController.class);

    @RequestMapping(value = "/fetchWardZoneId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object fetchWardZoneId(@RequestBody final CommonServiceRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        WardZoneBlockDTO result = null;
        try {
            if (!bindingResult.hasErrors()) {
                result = commonService.getWordZoneBlockByApplicationId(requestDTO.getApplicationId(),
                        requestDTO.getServiceId(), requestDTO.getOrgId());
            }
        } catch (final Exception e) {
            LOGGER.error("error while fetching wardZone" + e);
        }
        return result;
    }

}
