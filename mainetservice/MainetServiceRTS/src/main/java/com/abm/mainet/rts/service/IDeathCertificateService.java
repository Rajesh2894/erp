package com.abm.mainet.rts.service;


import javax.jws.WebService;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.rts.dto.DeathCertificateDTO;
import com.abm.mainet.rts.ui.model.DeathCertificateModel;

@WebService
public interface IDeathCertificateService {

	public DeathCertificateDTO saveDeathCertificate(DeathCertificateDTO deathCertificateDTO,DeathCertificateModel tbDeathregModel);

	void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, DeathCertificateModel tbDeathregModel);

    public  WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO);
	
    public WSResponseDTO getApplicableTaxes( WSRequestDTO requestDTO);
    
    public DeathCertificateDTO saveDeathCertificateDeails(DeathCertificateDTO deathCertificateDTO);
    
    public DeathCertificateDTO getDeathCertificate(DeathCertificateDTO deathCertificateDTO);
}
