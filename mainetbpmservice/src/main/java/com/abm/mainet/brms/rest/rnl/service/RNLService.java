package com.abm.mainet.brms.rest.rnl.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

public interface RNLService {

    WSResponseDTO calculateServiceCharges(WSRequestDTO requestDTO);

    WSResponseDTO calculateTaxPercentage(WSRequestDTO requestDTO);

    WSResponseDTO calculateRateForMultipleProperty(WSRequestDTO request);
}
