package com.abm.mainet.adh.service;

import java.util.List;

import javax.jws.WebService;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;

/**
 * Object: this web services is used for BRMS RUlE SHEET .
 * @author vishwajeet.kumar
 * @since 24 october 2019
 * 
 */

@WebService
public interface IBRMSADHService {

    /**
     * this service is used for get applicable tax for BRMS service
     * @param WSRequestDTO which contain OrgId, service code and charge applicable at field id
     * @return WSResponseDTO which contain ADHRateMaster DTO with applicable tax details
     */
    WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO wsRequestDTO);

    /**
     * this service is used for get Service for ADH Service charges from BRMS.
     * @param requestDTO with contain ADHRateMaster details
     * @return WSResponseDTO with List<ChargeDetailDTO> for ADH service
     */
    WSResponseDTO getApplicableCharges(@RequestBody WSRequestDTO wsRequestDTO);

    /**
     * Method is used for get Loi Taxes
     * @param requestDTO
     * @param orgId
     * @param serviceShortCode
     * @return
     */
    List<ADHRateMaster> getLoiChargesForADH(WSRequestDTO requestDTO, Long orgId, String serviceShortCode);

    WSResponseDTO getPenaltyTaxes(WSRequestDTO wsRequestDTO);

	List<ADHRateMaster> getLoiChargesForADHData(WSRequestDTO requestDTO, AdvertiserMasterDto masterDto,
			String serviceShortCode);
    
}
