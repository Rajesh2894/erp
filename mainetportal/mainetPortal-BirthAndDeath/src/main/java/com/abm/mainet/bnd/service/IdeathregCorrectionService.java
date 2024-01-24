package com.abm.mainet.bnd.service;

import java.util.List;

import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

public interface IdeathregCorrectionService {

	List<TbDeathregDTO> getDeathRegDataByStatus(TbDeathregDTO tbDeathregDTO);

	TbDeathregDTO getDeathById(Long drID);

	TbDeathregDTO savedeathCorrectionDeatils(TbDeathregDTO tbDeathregDTO);

	long CalculateNoOfDays(TbDeathregDTO tbDeathregDTO);
	
	public WSResponseDTO getApplicableTaxes(WSRequestDTO taxRequestDto);
	
	public  WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO);
	
	TbDeathregDTO saveIssuanceDeathCertificateDetail(TbDeathregDTO tbDeathregDTO);
	
	public TbDeathregDTO getDeathIssuanceApplId(Long applicationId,Long orgId);
	
	public TbDeathregDTO getDeathByApplId(Long applicationId,Long orgId);
	
}
