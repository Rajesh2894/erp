package com.abm.mainet.water.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.water.dto.WaterRateMaster;

public interface IWaterBRMSService extends Serializable {

    /**
     * @param rate
     * @param orgid
     * @param serviceId
     * @return
     */
    WSResponseDTO getApplicableTaxes(WaterRateMaster rate, long orgid, String serviceShortCode);

    /**
     * @param requiredCHarges
     * @return
     */
    List<ChargeDetailDTO> getApplicableCharges(List<WaterRateMaster> requiredCHarges);

    /**
     * @param charges
     * @return
     */
    double chargesToPay(List<ChargeDetailDTO> charges);

}
