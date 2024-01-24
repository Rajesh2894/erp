package com.abm.mainet.adh.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

/**
 * @author cherupelli.srikanth
 * @since 31 October 2019
 */
public interface IBRMSADHService {

    /**
     * this service is used for get applicable tax for BRMS service
     * 
     * @param WSRequestDTO which contain OrgId, service code and charge applicable at field id
     * @return WSResponseDTO which contain ADHRateMaster DTO with applicable tax details
     */
    WSResponseDTO getApplicableTaxes(@RequestBody final ADHRateMaster rate);

    /**
     * this service is used for get Service for ADH Service charges from BRMS.
     * 
     * @param requestDTO with contain ADHRateMaster details
     * @return WSResponseDTO with List<ChargeDetailDTO> for ADH service
     */
    List<ChargeDetailDTO> getApplicableCharges(List<ADHRateMaster> requiredCHarges);

    WSResponseDTO getApplicableTaxes(WSRequestDTO taxReqDTO);

    List<ADHRateMaster> getLoiChargesForADH(WSRequestDTO requestDto, Long orgId, String serviceShortCode);

    public LinkedHashMap<String, Object> getCheckListChargeFlagAndLicMaxDay(Long orgId, String serviceShortCode);

    Map<String, String> getWardAndZone(Long orgId, Long Location);
}
