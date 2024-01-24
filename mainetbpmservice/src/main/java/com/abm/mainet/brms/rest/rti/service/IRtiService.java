package com.abm.mainet.brms.rest.rti.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

public interface IRtiService {

    WSResponseDTO calculateServiceCharges(WSRequestDTO requestDTO);

}
