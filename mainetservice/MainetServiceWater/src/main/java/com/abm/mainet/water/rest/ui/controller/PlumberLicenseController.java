package com.abm.mainet.water.rest.ui.controller;

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

import com.abm.mainet.cfc.checklist.dto.ChecklistServiceDTO;
import com.abm.mainet.cfc.checklist.dto.DocumentResubmissionRequestDTO;
import com.abm.mainet.cfc.checklist.dto.DocumentResubmissionResponseDTO;
import com.abm.mainet.cfc.checklist.modelmapper.ChecklistMapper;
import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;
import com.abm.mainet.water.service.PlumberLicenseService;
import com.abm.mainet.water.utility.WaterCommonUtility;

/**
 * @author Arun.Chavda
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/PlumberLicense")
public class PlumberLicenseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlumberLicenseController.class);

    @Resource
    private IChecklistSearchService checklistSearchService;
    
    @Resource
    private ChecklistMapper mapper;
    
    @Resource
    private IChecklistVerificationService documentUplodService;
    
    @Resource
    PlumberLicenseService plumberLicenseService;

    @RequestMapping(value = "/savePlumberLicenseData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object savePlumberLicenseData(@RequestBody final PlumberLicenseRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        PlumberLicenseResponseDTO responseDTO = new PlumberLicenseResponseDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            try {
                Organisation organisation = new Organisation();
                organisation.setOrgid(requestDTO.getOrgId());
                responseDTO = plumberLicenseService.savePlumberLicenseDetails(requestDTO);
                if (PrefixConstants.NewWaterServiceConstants.SUCCESS.equals(responseDTO.getStatus())) { // free
                    plumberLicenseService.initiateWorkFlowForFreeService(requestDTO);
                    WaterCommonUtility.sendSMSandEMail(requestDTO.getApplicant(), requestDTO.getApplicationId(),
                            requestDTO.getAmount(), MainetConstants.WaterServiceShortCode.PlUMBER_LICENSE, organisation);
                }

            } catch (final Exception ex) {
                responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
                LOGGER.error("Exception occured at Saving data for Plumber License Controller", ex);
            }
        }

        return responseDTO;

    }

    @RequestMapping(value = "/searchApplicantDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object searchApplicantDetails(
            @RequestBody final DocumentResubmissionRequestDTO requestVO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        final DocumentResubmissionResponseDTO response = new DocumentResubmissionResponseDTO();

        try {

            final ChecklistServiceDTO checklistDetail = mapper.mapChecklistStatusViewToChecklistServiceDTO(
                    checklistSearchService.getCheckListDataByApplication(requestVO.getOrgId(), requestVO.getApplicationId()));

            final List<CFCAttachmentsDTO> attachmentList = documentUplodService
                    .getUploadedDocumentByDocumentStatus(
                            requestVO.getApplicationId(),
                            requestVO.getApplicationStatus(),
                            requestVO.getOrgId());

            response.setChecklistDetail(checklistDetail);
            response.setAttachmentList(attachmentList);
            response.setStatus(MainetConstants.Req_Status.SUCCESS);
        } catch (final Exception ex) {

            response.setStatus(MainetConstants.Req_Status.FAIL);
            LOGGER.error(
                    "source dto object does not match to destination dto:",
                    bindingResult.getAllErrors());
        }

        return response;

    }
}
