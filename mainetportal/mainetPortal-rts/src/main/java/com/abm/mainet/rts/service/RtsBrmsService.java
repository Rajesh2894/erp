package com.abm.mainet.rts.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rts.dto.MediaChargeAmountDTO;
import com.abm.mainet.rts.ui.model.WaterRateMaster;
 

public interface RtsBrmsService  extends Serializable{
	
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
    List<MediaChargeAmountDTO> getApplicableCharges(List<WaterRateMaster> requiredCHarges);

    /**
     * @param charges
     * @return
     */
    double chargesToPay(List<ChargeDetailDTO> charges);

}
