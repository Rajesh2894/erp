package com.abm.mainet.water.rest.ui.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.water.dto.ChangeOfUsageDTO;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.dto.ChangeOfUsageResponseDTO;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.service.ChangeOfUsageService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterDisconnectionService;

/**
 * @author Lalit.Mohan
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/ChangeOfUsage")
public class ChangeOfUsageRestController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ChangeOfUsageRestController.class);

    @Resource
    private ChangeOfUsageService changeOfUsageService;

    @Resource
    private WaterCommonService waterCommonService;

    @Autowired
    private WaterDisconnectionService waterDisconnectionService;

    @RequestMapping(value = "/getConnectionData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getConnectionData(
            @RequestBody final ChangeOfUsageRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        final ChangeOfUsageResponseDTO responseDTO = new ChangeOfUsageResponseDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            try {
                final CustomerInfoDTO connectionDetail = waterCommonService.getConnectionDetailDto(
                        requestDTO.getOrgId(), requestDTO.getConnectionNo());
                if (null == connectionDetail) {
                    responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                    responseDTO.setErrorMsg(ApplicationSession.getInstance().getMessage("water.invalidConnection"));
                } else {
                    responseDTO.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
                    List<Long> applicationIdList = changeOfUsageService.getChangeOfUsageApplicationId(requestDTO.getOrgId(),
                            connectionDetail.getCsIdn());
                    if (CollectionUtils.isNotEmpty(applicationIdList)) {
                        Long applicationId = applicationIdList.get(applicationIdList.size() - 1);
                        String status = waterDisconnectionService.getWorkflowRequestByAppId(applicationId, requestDTO.getOrgId());
                        if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
                            responseDTO.setStatus(MainetConstants.TASK_STATUS_PENDING);
                            responseDTO.setErrorMsg(
                                    ApplicationSession.getInstance().getMessage("water.change.of.usage.search.error"));
                        }
                    }
                }
                responseDTO.setCustomerInfoDTO(connectionDetail);
            } catch (final NoResultException ex) {
                responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                LOGGER.error("No Record found for connection no = "
                        + requestDTO.getConnectionNo(), ex);
            } catch (final Exception ex) {
                responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                LOGGER.error(
                        "Exception occured at change of Usage Search Rest Controller",
                        ex);
            }
        }
        return responseDTO;

    }

    @RequestMapping(value = "/saveChangeData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveChangeData(
            @RequestBody final ChangeOfUsageRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        final ChangeOfUsageResponseDTO responseDTO = new ChangeOfUsageResponseDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            try {

                final ChangeOfUsageDTO dto = changeOfUsageService.saveOrUpdateChangeOfUses(requestDTO);
                if ((null != dto) && (null != dto.getApmApplicationId())) {
                    responseDTO.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
                    responseDTO.setApplicationNo(dto.getApmApplicationId());
                    changeOfUsageService.initiateWorkflowForFreeService(requestDTO);
                } else {
                    responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                }
            } catch (final Exception ex) {
                responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                LOGGER.error(
                        "Exception occured at change of Usage saveChangeData Rest Controller",
                        ex);
            }
        }

        return responseDTO;

    }

}
