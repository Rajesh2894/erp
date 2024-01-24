package com.abm.mainet.tradeLicense.service;


import javax.jws.WebService;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;



@WebService
public interface IRenewalLicenseApplicationService {
	
	/**
	 * used to Save And Update Renewal License Application Form
	 * @param tradeMasterDetailDTO
	 * @return
	 */
	TradeMasterDetailDTO saveAndUpdateApplication(TradeMasterDetailDTO dto);

	boolean isKDMCEnvPresent();

	TradeMasterDetailDTO getTradeLicenceApplicationChargesFromBrmsRule(TradeMasterDetailDTO masDto);

	Boolean checkLicenseNoExist(String trdLicno, Long orgId);
}
