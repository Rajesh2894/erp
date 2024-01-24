package com.abm.mainet.brms.rest.additionalservices.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

public interface IAdditionalServicesService {
	
	WSResponseDTO calculateAdditionalServicesCharges(WSRequestDTO requestDTO);

	

}
