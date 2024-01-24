package com.abm.mainet.adh.service;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationRequestDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author cherupelli.srikanth
 * @since 17 October 2019
 */
public interface IAgencyRegistrationService {

	AgencyRegistrationResponseDto saveAgencyRegistrationData(AgencyRegistrationRequestDto requestDto)
			throws JsonParseException, JsonMappingException, IOException;

	LinkedHashMap<String, Object> getCheckListChargeFlagAndLicMaxDay(Long orgId, String serviceShortCode);

	List<AdvertiserMasterDto> getLicNoAndAgenNameAndStatusByorgId(Long orgId);

	AgencyRegistrationResponseDto getAgencyDetailByLicnoAndOrgId(String agencyLicNo, Long orgId);
	
	// New function added for User Story 112154
	AgencyRegistrationResponseDto saveAndUpdateApplication(AgencyRegistrationRequestDto requestDto);
	//Defect #129856
	String  getCalculateYearTypeBylicType(Long orgId, String serviceShortCode,Long licType);
	

}
