package com.abm.mainet.tradeLicense.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface IChangeBusinessNameService {

	/**
	 * used to get Business Name charges( from BRMS Rule)
	 * 
	 * @param masDtoQ
	 * @return
	 */
	TradeMasterDetailDTO getBusinessNameChargesFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException;

	/**
	 * save Change in Business Name  Application
	 * 
	 * @param masDto
	 * @return
	 */

	TradeMasterDetailDTO saveChangeBusinessNameService(TradeMasterDetailDTO masDto);
	

    /**
     * used to get Word Zone Block By Application Id
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return
     */
    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);
    
    TradeMasterDetailDTO getScrutinyChargesFromBrmsRule(TradeMasterDetailDTO masDto);
    
    /**
     * get Loi Charges
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return
     * @throws CloneNotSupportedException
     */
    Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

    List<String> getTradLoicNumber(Long applicationId);
    
    void sendSmsEmail(TradeMasterDetailDTO masDto);
}
