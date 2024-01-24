package com.abm.mainet.water.rest.ui.controller;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.abm.mainet.common.constant.MainetConstants.WebServiceStatus;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterDisconnectionService;

/**
 * @author Jeetendra.Pal
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/WaterDisconnection")
public class WaterDisconnectionRestController {

    @Resource
    private WaterDisconnectionService waterDisconnectionService;

    @Resource
    private ServiceMasterService serviceMasterService;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(WaterDisconnectionRestController.class);

    @RequestMapping(value = "/searchConnectionDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getConnectionDetails(
            @RequestBody final WaterDeconnectionRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        WaterDisconnectionResponseDTO responseDTO = new WaterDisconnectionResponseDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            try {
                responseDTO = waterDisconnectionService.getConnectionsAvailableForDisConnection(requestDTO);
                String paymentStatusFlagByApplNo = null;
                if (CollectionUtils.isEmpty(responseDTO.getConnectionList())) {
                    TBWaterDisconnectionDTO disconnectionAppDetailsByCsIdn = null;
                    TbCsmrInfoDTO consumerInfo = ApplicationContextProvider.getApplicationContext()
                            .getBean(NewWaterConnectionService.class).fetchConnectionDetailsByConnNo(
                                    requestDTO.getConnectionNo(), requestDTO.getOrgId());
                    if (consumerInfo != null) {
                        disconnectionAppDetailsByCsIdn = waterDisconnectionService.getDisconnectionAppDetailsByCsIdn(
                                requestDTO.getOrgId(), consumerInfo.getCsIdn());
                        if (disconnectionAppDetailsByCsIdn != null) {
                            paymentStatusFlagByApplNo = ApplicationContextProvider.getApplicationContext()
                                    .getBean(ApplicationService.class)
                                    .getPaymentStatusFlagByApplNo(disconnectionAppDetailsByCsIdn.getApmApplicationId(),
                                            requestDTO.getOrgId());
                            if (StringUtils.isBlank(paymentStatusFlagByApplNo)) {
                                responseDTO = waterDisconnectionService
                                        .getConnectionsForDisConnection(requestDTO);
                            }

                            // D#117836 point no 4

                            if (disconnectionAppDetailsByCsIdn.getDiscGrantFlag() == null) {
                                responseDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("water.discon.search.error"));
                            }
                        }
                    }
                }
                if ((responseDTO.getConnectionList() != null) && !responseDTO.getConnectionList().isEmpty()) {
                    responseDTO.setStatus(WebServiceStatus.SUCCESS);
                    TbWtBillMasEntity waterBillDues = waterDisconnectionService.getWaterBillDues(
                            responseDTO.getConnectionList().get(0).getCsIdn(),
                            requestDTO.getOrgId());
                    // D#117836 point no 4
                    String status = checkDisconnection(
                            requestDTO.getOrgId(), responseDTO.getConnectionList().get(0).getCsIdn());
                    if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
                        responseDTO.setStatus(MainetConstants.TASK_STATUS_PENDING);
                        responseDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("water.discon.search.error"));
                    }
                    //
                    if (waterBillDues != null) {
                        responseDTO.setBilloutstandingAmoount(waterBillDues.getBmTotalOutstanding());
                    } else {
                        responseDTO.setBilloutstandingAmoount(0.0);
                    }
                } else {
                    responseDTO.setStatus(WebServiceStatus.FAIL);
                }
            } catch (final Exception ex) {
                responseDTO.setStatus(WebServiceStatus.FAIL);
                LOGGER.error("error while fetching connection details", ex);
            }
        } else {
            responseDTO.setStatus(WebServiceStatus.FAIL);
            LOGGER.error("error while fetching connection details :",
                    bindingResult.getAllErrors());
        }
        return responseDTO;

    }

    private String checkDisconnection(Long orgId, long csIdn) {
        TBWaterDisconnectionDTO disconnectionAppDetailsByCsIdn = null;
        try {
            disconnectionAppDetailsByCsIdn = waterDisconnectionService.getDisconnectionAppDetailsByCsIdn(
                    orgId, csIdn);
        } catch (final Exception ex) {
            LOGGER.error("error while fetching connection details", ex);
        }
        if (disconnectionAppDetailsByCsIdn != null) {
            String status = waterDisconnectionService.getWorkflowRequestByAppId(
                    disconnectionAppDetailsByCsIdn.getApmApplicationId(), orgId);
            return status;
        }
        return null;

    }

    @RequestMapping(value = "/saveDisconnectionDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveDisconnectionDetails(
            @RequestBody final WaterDeconnectionRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        WaterDisconnectionResponseDTO responseDTO = new WaterDisconnectionResponseDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {

            try {
                /*
                 * final ServiceMaster service = serviceMasterService
                 * .getServiceByShortName(WaterServiceShortCode.WATER_DISCONNECTION, requestDTO.getOrgId());
                 * requestDTO.setServiceId(service.getSmServiceId());
                 */
                responseDTO = waterDisconnectionService
                        .saveDisconnectionDetails(requestDTO);
                waterDisconnectionService.initiateWorkflowForFreeService(requestDTO);
                responseDTO.setStatus(WebServiceStatus.SUCCESS);
            } catch (final Exception ex) {
                responseDTO.setStatus(WebServiceStatus.FAIL);
                LOGGER.error("error while saving Disconnection Details", ex);
            }
        } else {

            LOGGER.error("error while saving Disconnection Details :",
                    bindingResult.getAllErrors());
        }
        return responseDTO;

    }

    @RequestMapping(value = "/validatePlumberLinceOutsideULB", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object validatePlumberLinceOutsideULB(@RequestBody final String licence, final HttpServletRequest request) {
        Long plumberId = null;
        Assert.notNull(licence, "Licence No Can not be null");
        try {

            plumberId = waterDisconnectionService.validateOtherUlbLicence(licence);

        } catch (final Exception ex) {

            LOGGER.error("error while saving Disconnection Details", ex);
        }

        return plumberId;

    }

}
