package com.abm.mainet.tradeLicense.service;

import javax.jws.WebService;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface IChangeCategorySubCategoryService {

	/**
	 * used to get change in category subcategory charges( from BRMS Rule)
	 * 
	 * @param masDtoQ
	 * @return
	 */
	TradeMasterDetailDTO getChangeCategoryChargesFromBrmsRule(TradeMasterDetailDTO masDto);

	/**
	 * save Change in Category sub Category Application
	 * 
	 * @param masDto
	 * @return
	 */

	TradeMasterDetailDTO saveChangeCategoryService(TradeMasterDetailDTO masDto, TradeMasterDetailDTO tradeDto);
}
