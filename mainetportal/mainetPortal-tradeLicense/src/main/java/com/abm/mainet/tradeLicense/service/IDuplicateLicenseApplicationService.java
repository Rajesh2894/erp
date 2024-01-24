package com.abm.mainet.tradeLicense.service;

import javax.jws.WebService;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface IDuplicateLicenseApplicationService {

	/**
	 * used to get trade duplicate charges( from BRMS Rule)
	 * 
	 * @param masDtoQ
	 * @return
	 */
	TradeMasterDetailDTO getDuplicateChargesFromBrmsRule(TradeMasterDetailDTO masDto);

	/**
	 * Save Duplicate License Application
	 * 
	 * @param dto
	 * @return
	 */

	TradeMasterDetailDTO saveDuplicateLicense(TradeMasterDetailDTO dto);
}
