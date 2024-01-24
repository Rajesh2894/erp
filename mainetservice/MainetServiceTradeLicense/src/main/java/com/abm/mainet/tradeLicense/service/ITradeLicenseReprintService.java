package com.abm.mainet.tradeLicense.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.tradeLicense.dto.LicenseReprintDetailsDto;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface ITradeLicenseReprintService {
	/**
	 * used to Fetch License Details for reprint
	 * 
	 * @param applicationId and Organisation Id
	 * @return
	 */
	public LicenseReprintDetailsDto fetchLicenseViewDetails(Long applicationId, Long orgId);
	
	/**
	 * used to Fetch LicenseNo and Application No 
	 * 
	 * @param  Organisation Id
	 * @return
	 */

	List<TradeMasterDetailDTO> getTradeLicenseNoAndAppNo(Long orgId);

}
