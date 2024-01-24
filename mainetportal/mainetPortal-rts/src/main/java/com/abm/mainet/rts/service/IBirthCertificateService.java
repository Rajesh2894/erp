package com.abm.mainet.rts.service;

import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rts.dto.BirthCertificateDTO;

public interface IBirthCertificateService {
	
	BirthCertificateDTO saveBirthCertificateP(BirthCertificateDTO birthCertificateDto );

public  WSResponseDTO getApplicableTaxes( WSRequestDTO requestDTO);
	
	public  WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO);
	
	public BirthCertificateDTO getBirthCertificateInfo(Long appId, Long orgId);
	

}
