package com.abm.mainet.mobile.rest.ui.controller;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.InvalidRequestException;
import com.abm.mainet.common.utility.MainetTenantUtility;
import com.abm.mainet.mobile.dto.CommonAppResponseDTO;
import com.abm.mainet.mobile.dto.DeviceRegistrationReqDTO;
import com.abm.mainet.mobile.service.DeviceRegistration;

/**
 * @author umashanker.kanaujiya
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/deviceRegistrationController")
public class DeviceRegistrationController {

    private static Logger LOG = Logger.getLogger(DeviceRegistrationController.class);

    @Value("${error.code.could_not_process_request}")
    private String processRequestError;

    @Resource
    private DeviceRegistration registrationService;

    @RequestMapping(value = "/doRegisterDeviceID", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public CommonAppResponseDTO doRegistration(@RequestBody @Valid final DeviceRegistrationReqDTO reqDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        LOG.info("Inside the doRegisterDeviceID in DeviceRegistrationController ");
        if (!bindingResult.hasErrors()) {
            CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
            MainetTenantUtility.setThreadLocal(reqDTO.getOrgId());
            try {
                responseDTO = registrationService.doDevRegistrationService(reqDTO);

            } catch (final Exception ex) {
                responseDTO.setStatus(MainetConstants.COMMON_STATUS.FAIL);
                responseDTO.setErrorMsg(processRequestError + ex.getMessage());
                LOG.error("Error during fetching doRegistration in in CitizenRegistrationController :", ex);
            }
            return responseDTO;
        } else {
            LOG.error("Error during binding  doRegisterDeviceID in :" + bindingResult.getAllErrors());
            throw new InvalidRequestException("Invalid Request", bindingResult);

        }

    }

}
