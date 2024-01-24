package com.abm.mainet.bnd.service;

import java.util.List;

import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

public interface IDeathCertificateService {

	DeathCertificateDTO savedeathCertificateDeatils(DeathCertificateDTO deathCertificateDTO);

	public WSResponseDTO getApplicableTaxes(WSRequestDTO taxRequestDto);
	
	public  WSResponseDTO getDeathCertificateCharges(WSRequestDTO wSRequestDTO);
	
	public DeathCertificateDTO getDeathCertificateDetail(Long appId, Long orgId);

	public List<DeathCertificateDTO> searchDeathCertiDetails(DeathCertificateDTO searchDto);

}
