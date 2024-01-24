package com.abm.mainet.bnd.service;


import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.dto.ViewDeathCertiDetailRequestDto;
import com.abm.mainet.bnd.ui.model.DeathCertificateModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;

@WebService
public interface IDeathCertificateServices {

	public DeathCertificateDTO saveDeathCertificate(DeathCertificateDTO deathCertificateDTO,DeathCertificateModel tbDeathregModel);

	void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, DeathCertificateModel tbDeathregModel);

    public  WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO);
	
    public WSResponseDTO getApplicableTaxes( WSRequestDTO requestDTO);
    
    public DeathCertificateDTO saveDeathCertificateDeails(DeathCertificateDTO deathCertificateDTO);
    
    public DeathCertificateDTO getDeathCertificate(DeathCertificateDTO deathCertificateDTO);

	List <DeathCertificateDTO> viewDeathRegisteredAppliDetails(ViewDeathCertiDetailRequestDto deathViewRequestDto);
}
