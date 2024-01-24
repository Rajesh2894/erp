package com.abm.mainet.brms.rest.adh.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

/**
 * @author vishwajeet.kumar
 * @since 22 October 2019
 */

public interface IADHService {

    /**
     * @param requestDTO
     * @return
     */
    WSResponseDTO getNewADHApplicationCharges(WSRequestDTO requestDTO);

    /**
     * 
     * @param requestDTO
     * @return
     */
    WSResponseDTO calculateRateForMultipleADHApplicationCharges(WSRequestDTO requestDTO);
}
