package com.abm.mainet.workManagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.dto.ContractCompletionDto;
import com.abm.mainet.workManagement.dto.SearchDTO;
import com.abm.mainet.workManagement.dto.SummaryDto;
import com.abm.mainet.workManagement.dto.WorkDefinationAssetInfoDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Service
public class CompletionCertificateServiceImpl implements CompletionCertificateService {
    private static final Logger lOGGER = LoggerFactory.getLogger(CompletionCertificateServiceImpl.class);

    @Autowired
    WorkDefinitionService workDefinitionService;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<SummaryDto> getAssetDetails(SearchDTO searchDTO) {
        ResponseEntity<?> respEntity = RestClient.callRestTemplateClient(searchDTO, ServiceEndpoints.WMS_ASSET_DETAILS);
        Object resObj = respEntity.getBody();
        List<LinkedHashMap<Long, Object>> responseObjList = (List<LinkedHashMap<Long, Object>>) resObj;
        List<SummaryDto> responseList = new ArrayList<>();
        if (responseObjList != null && !responseObjList.isEmpty()) {
            responseObjList.forEach(obj -> {
                String d = new JSONObject(obj).toString();
                try {
                    SummaryDto app = new ObjectMapper().readValue(d, SummaryDto.class);
                    responseList.add(app);
                } catch (Exception ex) {
                      throw new FrameworkException("Exception occured while fetching Asset details : ", ex);

                }

            });
        }
        return responseList;
    }

    public String generateCompletionNo(Long workId, Long orgId) {

        FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class)
                .getFinanciaYearByDate(new Date());
        // find organization code.
        String ulbCode = ApplicationContextProvider.getApplicationContext().getBean(TbOrganisationService.class).findById(orgId)
                .getOrgShortNm();
        WorkDefinitionDto workEntity = workDefinitionService.findAllWorkDefinitionById(workId);

        // find department code.
        String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                .getDeptCode(workEntity.getProjDeptId());

        // get financial year from date & end date and generate financial year as like:
        // 2018-19 format for initiation code
        String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

        // generate sequence number.
        final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
                .generateSequenceNo(
                        MainetConstants.WorksManagement.WORKS_MANAGEMENT,
                        "TB_WMS_WORKDEFINATION", MainetConstants.WorksManagement.TND_LOA_NO, orgId, MainetConstants.FlagC,
                        financiaYear.getFaYear());

        // generate initiation code.
        return (ulbCode + MainetConstants.WINDOWS_SLASH + MainetConstants.WorksManagement.IN
                + MainetConstants.WINDOWS_SLASH + deptCode + MainetConstants.WINDOWS_SLASH
                + String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence)
                + MainetConstants.WINDOWS_SLASH + finacialYear);
    }

    @Override
    @Transactional
    public void updateWorkCompltionNoAndDate(ContractCompletionDto contractCompletionDto, Long orgId) {
        String completionNo = generateCompletionNo(contractCompletionDto.getWorkId(), orgId);
        WorkDefinitionDto definitionDto = workDefinitionService
                .findAllWorkDefinitionById(contractCompletionDto.getWorkId());
        if (definitionDto != null) {
            if (!definitionDto.getAssetInfoDtos().isEmpty()) {
                definitionDto.getAssetInfoDtos().add(contractCompletionDto.getAssetInfoDto());
            } else {
                List<WorkDefinationAssetInfoDto> assetInfoDtoList = new ArrayList<>();
                assetInfoDtoList.add(contractCompletionDto.getAssetInfoDto());
                definitionDto.setAssetInfoDtos(assetInfoDtoList);
            }
            definitionDto.setWorkStatus(MainetConstants.FlagC);
            definitionDto.setWorkCompletionDate(Utility.stringToDate(contractCompletionDto.getCompletionDate()));
            definitionDto.setWorkCompletionNo(completionNo);
            workDefinitionService.updateWorkDefinition(definitionDto, null, null, null, null, null, null, null);
        }
    }

    @Override
    @Transactional
    public Long pushAssetDetails(AssetDetailsDTO astDet) {
        Long astId = null;
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = RestClient.callRestTemplateClient(astDet, ServiceEndpoints.WMS_ASSET_DETAILS);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                astId = Long.valueOf(responseEntity.getBody().toString());
            }
        } catch (Exception ex) {
            lOGGER.error("Exception occured while pushAssetDetails() : " + ex);
            return astId;
        }
        return astId;
    }

}
