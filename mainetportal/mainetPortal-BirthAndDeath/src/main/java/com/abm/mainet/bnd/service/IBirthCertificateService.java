package com.abm.mainet.bnd.service;

import java.util.List;

import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

public interface IBirthCertificateService {
	
	BirthCertificateDTO saveBirthCertificateP(BirthCertificateDTO birthCertificateDto );

public  WSResponseDTO getApplicableTaxes( WSRequestDTO requestDTO);
	
	public  WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO);
	
	public BirthCertificateDTO getBirthCertificateInfo(Long appId, Long orgId);

	public List<BirthCertificateDTO> searchBirthCertiDetails(BirthCertificateDTO searchDto); 
	

}
