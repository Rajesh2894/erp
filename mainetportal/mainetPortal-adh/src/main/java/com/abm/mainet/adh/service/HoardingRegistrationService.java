package com.abm.mainet.adh.service;

import java.util.List;

import com.abm.mainet.adh.dto.HoardingBookingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.common.util.LookUp;

/**
 * 
 * @author pooja.maske
 *
 */
public interface HoardingRegistrationService {

	
	List<String[]> getHoardingNumberAndIdListByOrgId(Long orgId);

	NewAdvertisementReqDto saveNewHoardingApplication(NewAdvertisementReqDto advertisementReqDto);

	HoardingMasterDto getByOrgIdAndHoardingId(Long orgId, Long hoardingId);

	List<HoardingBookingDto> getHoardingDetailsByOrgId(Long orgId);

}
