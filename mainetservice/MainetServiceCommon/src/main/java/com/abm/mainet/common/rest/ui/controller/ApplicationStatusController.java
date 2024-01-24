package com.abm.mainet.common.rest.ui.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.internal.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.abm.mainet.common.dto.ApplicationDetail;
import com.abm.mainet.common.dto.ApplicationStatusRequestVO;
import com.abm.mainet.common.dto.ApplicationStatusResponseVO;
import com.abm.mainet.common.integration.dto.WebServiceResponseDTO;
import com.abm.mainet.common.service.IApplicationStatusService;
import com.abm.mainet.common.utility.MainetTenantUtility;

/**
 * @author vishnu.jagdale
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/ApplicationStatus")
public class ApplicationStatusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStatusController.class);

    @Resource
    private IApplicationStatusService applicationStatusService;

    @RequestMapping(value = "/getApplicationStatusListOpenForUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object getApplicationStatusListOpenForUser(@RequestBody final ApplicationStatusRequestVO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        final ApplicationStatusResponseVO responseDTO = new ApplicationStatusResponseVO();
        List<WebServiceResponseDTO> wsInputErrorList = null;
        Assert.notNull(requestDTO,MainetConstants.CommonMasterUi.APP_REQ_VO_NOT_NULL);
        List<ApplicationDetail> appDetailList = null;

        if (!bindingResult.hasErrors()) {
            wsInputErrorList = applicationStatusService.validateInput(requestDTO, MainetConstants.DETAIL);
            if ((wsInputErrorList == null) || wsInputErrorList.isEmpty()) {
                MainetTenantUtility.setThreadLocal(requestDTO.getOrgId());
                try {
                    appDetailList = applicationStatusService.getApplicationStatusListOpenForUser(requestDTO);
                    responseDTO.setAppDetailDTO(appDetailList);
                } catch (final Exception ex) {
                    responseDTO.setErrorCode(MainetConstants.ERROR_CODE.PROCESS_REQUESTORE_ERROR);
                    responseDTO.setErrorCause(MainetConstants.ERROR_CODE.ERROR_CAUSE + ex.getMessage());
                    LOGGER.error("Error during Assessment Property Certificate Detail:", ex);
                }
            }
        } else {
            responseDTO.setErrorCode(MainetConstants.ERROR_CODE.PROCESS_REQUESTORE_ERROR);
            responseDTO.setErrorCause(MainetConstants.ERROR_CODE.ERROR_CAUSE);
            LOGGER.error("Error during fetching Marriage Certificate Detail:", bindingResult.getAllErrors());
        }

        responseDTO.setWsInputErrorList(wsInputErrorList);
        return responseDTO;
    }

    @RequestMapping(value = "/getApplicantStatusDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object getApplicantsName(@RequestBody final ApplicationStatusRequestVO requestDTO, final HttpServletRequest request,
            final BindingResult bindingResult) {

        final ApplicationStatusResponseVO responseDTO = new ApplicationStatusResponseVO();
        List<WebServiceResponseDTO> wsInputErrorList = null;
        Assert.notNull(requestDTO, "ApplicationStatusRequestVO can not be null ");
        List<ApplicationDetail> appDetailList = null;

        if (!bindingResult.hasErrors()) {
            wsInputErrorList = applicationStatusService.validateInputWithoutOrganisation(requestDTO, MainetConstants.DETAIL);
            if ((wsInputErrorList == null) || wsInputErrorList.isEmpty()) {
                MainetTenantUtility.setThreadLocal(requestDTO.getOrgId());
                try {
                    appDetailList = applicationStatusService.getApplicationStatusDetail(requestDTO);
                    responseDTO.setAppDetailDTO(appDetailList);
                } catch (final Exception ex) {
                    responseDTO.setErrorCode(MainetConstants.ERROR_CODE.PROCESS_REQUESTORE_ERROR);
                    responseDTO.setErrorCause(MainetConstants.ERROR_CODE.ERROR_CAUSE + ex.getMessage());
                    LOGGER.error("Error during Assessment Property Certificate Detail:", ex);
                }
            }
        } else {
            responseDTO.setErrorCode(MainetConstants.ERROR_CODE.PROCESS_REQUESTORE_ERROR);
            responseDTO.setErrorCause(MainetConstants.ERROR_CODE.ERROR_CAUSE);
            LOGGER.error("Error during fetching Marriage Certificate Detail:", bindingResult.getAllErrors());
        }

        responseDTO.setWsInputErrorList(wsInputErrorList);
        return responseDTO;
    }

}
