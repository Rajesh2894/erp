package com.abm.mainet.mrm.service;

import java.util.List;

import javax.jws.WebService;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.mrm.dto.MRMRateMaster;

@WebService
public interface IBRMSMRMService {

    WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO wsRequestDTO);

    WSResponseDTO getApplicableCharges(@RequestBody WSRequestDTO wsRequestDTO);

    List<MRMRateMaster> getLoiChargesForMRM(WSRequestDTO requestDTO, Long orgId, String serviceShortCode);

}
