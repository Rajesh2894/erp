package com.abm.mainet.adh.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationRequestDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author cherupelli.srikanth
 * @since 17 October 2019
 */
@Service
public class AgencyRegistrationServiceImpl implements IAgencyRegistrationService {

	@SuppressWarnings("unchecked")
	@Override
	public AgencyRegistrationResponseDto saveAgencyRegistrationData(AgencyRegistrationRequestDto requestDto)
			throws JsonParseException, JsonMappingException, IOException {

		
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(requestDto, ServiceEndpoints.ADVERTISER_HOARDING.AGENCY_REGISTRATION_DATA);
		final String response = new JSONObject(responseVo).toString();
		return new ObjectMapper().readValue(response, AgencyRegistrationResponseDto.class);

	}
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String, Object> getCheckListChargeFlagAndLicMaxDay(Long orgId, String serviceShortCode) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		requestParam.put(MainetConstants.AdvertisingAndHoarding.SERVICE_SGORTCODE, serviceShortCode);
		URI uri = dd.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_CHECKLIST_CHARGES_FLAG_AND_LICMAXDAYS,
				requestParam);
		final LinkedHashMap<String, Object> responseVo = (LinkedHashMap<String, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());

		map = responseVo;
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdvertiserMasterDto> getLicNoAndAgenNameAndStatusByorgId(Long orgId) {
		List<AdvertiserMasterDto> requestDto = new ArrayList<>();

		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = dd.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_AGENCY_LICNO_AND_NAME_BY_ORGID, requestParam);
		final List<LinkedHashMap<Long, Object>> responseVo = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());

		if (responseVo != null && !responseVo.isEmpty()) {
			responseVo.forEach(response -> {
				String jsonObject = new JSONObject(response).toString();
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					AdvertiserMasterDto lookUp = objectMapper.readValue(jsonObject, AdvertiserMasterDto.class);
					requestDto.add(lookUp);
				} catch (Exception exception) {
					throw new FrameworkException("Error Occured while fetching location ", exception);
				}
			});
		}

		return requestDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AgencyRegistrationResponseDto getAgencyDetailByLicnoAndOrgId(String agencyLicNo, Long orgId) {
		AgencyRegistrationResponseDto requestDto = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put(MainetConstants.AdvertisingAndHoarding.AGENCY_LIC_NO, String.valueOf(agencyLicNo));
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = dd.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_AGENCYDETAILS_BY_LICNO, requestParam);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		final String dto = new JSONObject(responseVo).toString();
		try {
			requestDto = new ObjectMapper().readValue(dto, AgencyRegistrationResponseDto.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to AdvertiserMasterDto", e);
		}
		return requestDto;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AgencyRegistrationResponseDto saveAndUpdateApplication(AgencyRegistrationRequestDto requestDto) {
		
		final LinkedHashMap<Long, Object> responseVo =(LinkedHashMap<Long, Object>)JersyCall.callRestTemplateClient(requestDto,
        		ServiceEndpoints.ADVERTISER_HOARDING.SAVE_CANCELLATION_SERVICE_APPLICATION);
		// New function added for User Story 112154 
        final String d = new JSONObject(responseVo).toString();
        try {   	
        	return new ObjectMapper().readValue(d,
        			AgencyRegistrationResponseDto.class);	
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
	}
	
	//Defect #129856
	
	@SuppressWarnings("unchecked")
	@Override
	public String getCalculateYearTypeBylicType(Long orgId, String serviceShortCode, Long licType) {

		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		requestParam.put(MainetConstants.AdvertisingAndHoarding.SERVICE_SGORTCODE, serviceShortCode);
		requestParam.put(MainetConstants.AdvertisingAndHoarding.LICTYPE, String.valueOf(licType));
		URI uri = dd.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_LICMAX_TENURE_BY_SERVICECODE_LICTYPE,
				requestParam);
		final Map<String, String> responseVo = (Map<String, String>) JersyCall.callRestTemplateClient(orgId,
				uri.toString());

		return responseVo.get("YearType");
	}

}
