package com.abm.mainet.brms.rest.mrm.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

public interface IMRMService {

	WSResponseDTO getServiceCharges(WSRequestDTO requestDTO);
	
	
}
