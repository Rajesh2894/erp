/**
 * 
 */
package com.abm.mainet.brms.rest.swm.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

/**
 * @author sarojkumar.yadav
 *
 */
public interface ISWMService {

	WSResponseDTO calculateServiceCharges(WSRequestDTO requestDTO);
}
