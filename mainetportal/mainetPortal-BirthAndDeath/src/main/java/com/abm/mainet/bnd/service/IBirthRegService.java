package com.abm.mainet.bnd.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

@WebService
public interface IBirthRegService {

	public List<BirthRegistrationDTO> getBirthRegisteredAppliDetail(String brCertNo, String brRegNo, String year,
			Date brDob, String brChildName, String applicationId, Long orgId);

	public BirthRegistrationDTO getBirthByID(Long brId);

	public long CalculateNoOfDays(BirthRegistrationDTO birthRegDto);

	public BirthRegistrationDTO saveBirthCorrectionDet(BirthRegistrationDTO birthRegDto);

	public WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO);

	public WSResponseDTO getBndCharge(WSRequestDTO wSRequestDTO);
	
	public LinkedHashMap<String, Object> serviceInformation(Long orgId,String serviceShortCode);

	public BirthRegistrationDTO saveIssuanceOfBirtCert(BirthRegistrationDTO birthRegDto);

	public List<BirthRegistrationDTO> getBirthRegiDetailForCorr(BirthRegistrationDTO birthRegDto);

	public HospitalMasterDTO getHospitalById(Long hiId);
	
	public BirthRegistrationDTO getBirthByApplId(Long applicationId,Long orgId);
	
	public BirthRegistrationDTO getBirthIssuanceApplId(Long applicationId,Long orgId);

	public LinkedHashMap<String, Object> getTaxDescByTaxCode(Long orgIds, String taxCode);

}
