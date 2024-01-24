package com.abm.mainet.common.integration.brms.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;

/**
 * BRMS common services for Initialize model and fetching documents for particular service.
 * @author hiren.poriya
 * @Since 02-Jun-2018
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface BRMSCommonService {

    /**
     * this service is used for initialize model.
     * @param requestDTO
     * @return WSResponseDTO with model default initialized details
     */
    WSResponseDTO initializeModel(@RequestBody WSRequestDTO requestDTO);

    /**
     * this service is used for get Checklist details from BRMS.
     * @param requestDTO
     * @return WSResponseDTO with List<DocumentDetailsVO> which contain required documents details for particular service
     */
    WSResponseDTO getChecklist(@RequestBody WSRequestDTO requestDTO);

    WSResponseDTO getChecklistMRG(@RequestBody WSRequestDTO requestDTO);

}
