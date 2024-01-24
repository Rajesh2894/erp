/**
 * 
 */
package com.abm.mainet.workManagement.roadcutting.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadCuttingDto;

/**
 * @author satish.rathore
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface IRoadCuttingService {
	
	
	  /**
     * this service is used for get applicable tax for Road Cutting  service
     * @param requestDTO
     * @return WSResponseDTO with model applicable tax details
     */
    WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO requestDTO);

    /**
     * this service is used for get Service charges from BRMS.
     * @param requestDTO
     * @return WSResponseDTO with ChargeDetailDTO for Road Cutting  service
     */
    WSResponseDTO getApplicableCharges(@RequestBody WSRequestDTO requestDTO);
    
    WSResponseDTO save(@RequestBody RoadCuttingDto roadCuttingDto);
    
    RoadCuttingDto saveRoadCutting(@RequestBody RoadCuttingDto requestDTO);

	//RoadCuttingDto getRoadCuttingApplicationData(long applicationId, long orgid);

	//WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

	//Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

	RoadCuttingDto updateRoadCutting(RoadCuttingDto requestDTO);

}
