package com.abm.mainet.brms.rest.ml.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

public interface IMLService {

    WSResponseDTO getNewTradeLicenseFee(WSRequestDTO requestDTO);

}
