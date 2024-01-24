package com.abm.mainet.water.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;

/**
 * @author hiren.poriya
 * @Since 07-Jun-2018
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface BRMSWaterService {

    /**
     * this service is used for get applicable tax for water service
     * @param requestDTO
     * @return WSResponseDTO with model applicable tax details
     */
    WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO requestDTO);

    /**
     * this service is used for get Service charges from BRMS.
     * @param requestDTO
     * @return WSResponseDTO with ChargeDetailDTO for water service
     */
    WSResponseDTO getApplicableCharges(@RequestBody WSRequestDTO requestDTO);
    

	TbSrcptModesDetBean getDishonorCharges(Long deptId, Organisation org, TbSrcptModesDetBean recMode);
}
