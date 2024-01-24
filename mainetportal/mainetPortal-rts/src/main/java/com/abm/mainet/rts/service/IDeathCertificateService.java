package com.abm.mainet.rts.service;

import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rts.dto.DeathCertificateDTO;

public interface IDeathCertificateService {

	DeathCertificateDTO savedeathCertificateDeatils(DeathCertificateDTO deathCertificateDTO);

	public WSResponseDTO getApplicableTaxes(WSRequestDTO taxRequestDto);
	
	public  WSResponseDTO getDeathCertificateCharges(WSRequestDTO wSRequestDTO);
	
	public DeathCertificateDTO getDeathCertificateDetail(Long appId, Long orgId);

}
