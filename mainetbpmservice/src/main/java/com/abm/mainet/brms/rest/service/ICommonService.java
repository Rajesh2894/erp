package com.abm.mainet.brms.rest.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

/**
 * 
 * @author Vivek.Kumar
 * @since 30 May 2016
 */
public interface ICommonService {

    /**
     * this will return initialized model with their default state
     * @param requestDTO
     * @param request
     * @param bindingResult
     * @return RuleResponseDTO
     */
    WSResponseDTO returnInitializedModels(WSRequestDTO requestDTO);

    /**
     * this will return document group as response.
     * @param requestDTO
     * @return RuleResponseDTO
     */
    WSResponseDTO findApplicableCheckList(WSRequestDTO requestDTO);

}
