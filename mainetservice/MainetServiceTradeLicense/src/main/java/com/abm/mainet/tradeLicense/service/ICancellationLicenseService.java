package com.abm.mainet.tradeLicense.service;

import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface ICancellationLicenseService {

    /**
     * save Cancellation License Application
     * 
     * @param masDto
     * @return
     */

    TradeMasterDetailDTO saveCancellationService(TradeMasterDetailDTO masDto);

    /**
     * Get Application charges from BRMS rule
     * @param tradeDto
     * @return
     */

    TradeMasterDetailDTO getApplicationChargesBrmsRule(TradeMasterDetailDTO tradeDto);

    /**
     * Get ward Zone by application id
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return
     */

    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

    /**
     * Get Scrutiny charges from BRMS rule
     * @param masDto
     * @return
     */

    TradeMasterDetailDTO getscrutinyChargesBrmsRule(TradeMasterDetailDTO masDto);

    /**
     * get Loi Charges
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return
     * @throws CloneNotSupportedException
     */
    Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

	TradeMasterDetailDTO getTradeLicenceAppChargesFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException;

	Boolean saveCancellationLicenseByForceForm(TradeMasterDetailDTO dto);

	void sendSmsEmail(TradeMasterDetailDTO masDto);

}
