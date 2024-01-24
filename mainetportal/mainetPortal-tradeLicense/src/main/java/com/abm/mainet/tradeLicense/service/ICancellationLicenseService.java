package com.abm.mainet.tradeLicense.service;

import javax.jws.WebService;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface ICancellationLicenseService {

	/**
	 * Save Cancellation License Application
	 * 
	 * @param dto
	 * @return
	 */

	TradeMasterDetailDTO saveCancellationLicense(TradeMasterDetailDTO dto);

	TradeMasterDetailDTO getDuplicateChargesFromBrmsRule(TradeMasterDetailDTO masDto);
}
