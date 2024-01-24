package com.abm.mainet.brms.rest.wms.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public interface IWMSService {
	
	WSResponseDTO calculateEmdAmount(WSRequestDTO requestDTO);
	
	WSResponseDTO calculateRoadCuttingCharges(WSRequestDTO requestDTO);

}
