/**
 * 
 */
package com.abm.mainet.additionalservices.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;

/**
 * @author cherupelli.srikanth
 *
 */
public interface BRMSAdditionalService {

	
	 /**
     * this service is used for get applicable tax for BRMS service
     * @param WSRequestDTO which contain OrgId, service code and charge applicable at field id
     * @return WSResponseDTO which contain AdditionalServicesModel DTO with applicable tax details
     */
    WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO wsRequestDTO);

    /**
     * this service is used for get Service for ADH Service charges from BRMS.
     * @param requestDTO with contain AdditionalServicesModel details
     * @return WSResponseDTO with List<ChargeDetailDTO> for ADH service
     */
    WSResponseDTO getApplicableCharges(@RequestBody WSRequestDTO wsRequestDTO);
}
