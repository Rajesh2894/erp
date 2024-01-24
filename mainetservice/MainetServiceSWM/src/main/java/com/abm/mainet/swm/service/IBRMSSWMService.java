/**
 * 
 */
package com.abm.mainet.swm.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;

/**
 * @author sarojkumar.yadav
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface IBRMSSWMService {

	/**
	 * this service is used for get applicable tax from BRMS.
	 * 
	 * @param WSRequestDTO
	 *            which contain OrgId, service code and charge applicable at field
	 *            id
	 * @return WSResponseDTO which contain RNLRateMaster DTO with applicable tax
	 *         details
	 */
	WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO wsRequestDTO);

	/**
	 * this service is used for get Service charges from BRMS.
	 * 
	 * @param requestDTO
	 * @return WSResponseDTO with ChargeDetailDTO for water service
	 */
	WSResponseDTO getApplicableCharges(@RequestBody WSRequestDTO requestDTO);
}
