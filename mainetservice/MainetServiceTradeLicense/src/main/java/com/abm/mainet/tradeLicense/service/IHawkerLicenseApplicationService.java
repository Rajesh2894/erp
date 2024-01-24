package com.abm.mainet.tradeLicense.service;

import java.util.Date;

import javax.jws.WebService;

import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface IHawkerLicenseApplicationService
{
	
	
	
	    TradeMasterDetailDTO saveAndUpdateApplication(TradeMasterDetailDTO tradeMasterDto);

	   
	    public TradeMasterDetailDTO getTradeLicenceAppChargesFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException;

	 
		
	   Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId, TradeMasterDetailDTO tradeMasterDto);
	
	   Long validateAdharNumber( Long adharNumber, Long orgId);
	   
	   /**
	     * used to get Word Zone Block By Application Id
	     * @param applicationId
	     * @param serviceId
	     * @param orgId
	     * @return
	     */
	    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

	
	

}
