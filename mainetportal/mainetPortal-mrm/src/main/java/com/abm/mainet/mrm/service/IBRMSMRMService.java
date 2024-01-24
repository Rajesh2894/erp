package com.abm.mainet.mrm.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.mrm.dto.MRMRateMaster;

public interface IBRMSMRMService {

    WSResponseDTO getApplicableTaxes(@RequestBody final MRMRateMaster mrmRateMaster);

    List<ChargeDetailDTO> getApplicableCharges(List<MRMRateMaster> requiredCHarges);
    
    WSResponseDTO getApplicableTaxes(WSRequestDTO taxReqDTO);


}
