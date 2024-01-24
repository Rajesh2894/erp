package com.abm.mainet.brms.rest.bpmsratemaster.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

public interface IBPMSRateMasterService {
	
	WSResponseDTO calculateBPMSCharges(WSRequestDTO requestDTO);
	
}
