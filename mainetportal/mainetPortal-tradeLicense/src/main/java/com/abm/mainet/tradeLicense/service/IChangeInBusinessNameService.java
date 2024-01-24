package com.abm.mainet.tradeLicense.service;

import javax.jws.WebService;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface IChangeInBusinessNameService {

	/**
	 * used to get Business Name charges( from BRMS Rule)
	 * 
	 * @param masDtoQ
	 * @return
	 */
	TradeMasterDetailDTO getBusinessNameChargesFromBrmsRule(TradeMasterDetailDTO masDto);

	/**
	 * save Change in Business Name  Application
	 * 
	 * @param masDto
	 * @return
	 */

	TradeMasterDetailDTO saveChangeBusinessNameService(TradeMasterDetailDTO masDto);
}
