package com.abm.mainet.tradeLicense.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
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
	
	TradeMasterDetailDTO getAppDuplicateChargesFromBrmsRule(TradeMasterDetailDTO masDto);

	/**
	 * save Duplicate Application
	 * 
	 * @param masDto
	 * @return
	 */

	TradeMasterDetailDTO saveAndGenerateApplnNo(TradeMasterDetailDTO masDto);
	

    /**
     * used to get Word Zone Block By Application Id
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return
     */
    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

	void saveHistoryData(TradeMasterDetailDTO masDto);


	List<TbLoiMas> getTotalAmount(String licNo);

	String getLicenseNoByAppId(Long applicationId, Long orgId);

	void sendSmsEmail(TradeMasterDetailDTO masDto);

	Boolean updateStatusAfterDishonurEntry(Long appId, Long orgId);

}
