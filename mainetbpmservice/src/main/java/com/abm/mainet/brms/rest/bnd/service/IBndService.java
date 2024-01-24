package com.abm.mainet.brms.rest.bnd.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public interface IBndService {
	
	WSResponseDTO calculateBndCharges(WSRequestDTO requestDTO);

}
