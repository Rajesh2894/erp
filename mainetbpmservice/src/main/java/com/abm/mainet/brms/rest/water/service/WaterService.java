/**
 * 
 */
package com.abm.mainet.brms.rest.water.service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;

/**
 * @author Vivek.Kumar
 *
 */
public interface WaterService {

    WSResponseDTO calculateServiceCharges(WSRequestDTO requestDTO);

    /**
     * use this method to find applicable Charge for Service
     * @param requestDTO
     * @param responseDTO
     * @return
     */
    WSResponseDTO findWaterConsumption(WSRequestDTO requestDTO);

    WSResponseDTO findNoOfDays(WSRequestDTO requestDTO);

    WSResponseDTO findWaterRate(WSRequestDTO requestDTO);

    WSResponseDTO findWaterTax(WSRequestDTO requestDTO);

    WSResponseDTO findWaterConsumptionAndDays(WSRequestDTO requestDTO);

    WSResponseDTO findWaterRateBill(WSRequestDTO requestDTO);

    WSResponseDTO findWaterTaxBill(WSRequestDTO requestDTO);
}
