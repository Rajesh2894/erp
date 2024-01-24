package com.abm.mainet.rts.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;

	@WebService
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public interface RtsBRMSService {

	    WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO requestDTO);

	    WSResponseDTO getApplicableCharges(@RequestBody WSRequestDTO requestDTO);
	}

