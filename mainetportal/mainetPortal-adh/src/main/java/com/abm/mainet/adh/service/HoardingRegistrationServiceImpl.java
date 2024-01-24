package com.abm.mainet.adh.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.adh.dto.HoardingBookingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HoardingRegistrationServiceImpl implements HoardingRegistrationService{
	
	
	

    @Override
    @Transactional
    public List<String[]> getHoardingNumberAndIdListByOrgId(Long orgId) {
		
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = dd.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_HOARDINGNO_LIST_BY_ORGID, requestParam);
		List<String[]> responseStringList = (List<String[]>) JersyCall.callRestTemplateClient(orgId, uri.toString());
		return responseStringList;
    }
    
   	
	   @SuppressWarnings("unchecked")
	    @Override
	    public NewAdvertisementReqDto saveNewHoardingApplication(NewAdvertisementReqDto advertisementReqDto) {

	        LinkedHashMap<Long, Object> responseRequest = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
	                advertisementReqDto,
	                ServiceEndpoints.ADVERTISER_HOARDING.SAVE_HOARDING_APPLICATION);
	        NewAdvertisementReqDto reqDto = new NewAdvertisementReqDto();
	        if (responseRequest != null && !responseRequest.isEmpty()) {
	            String json = new JSONObject(responseRequest).toString();
	            try {
	                ObjectMapper objectMapper = new ObjectMapper();
	                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	                return objectMapper.readValue(json, NewAdvertisementReqDto.class);
	            } catch (Exception exception) {
	                throw new FrameworkException("Error Occurred while Saving the new  advertisement Request " + exception);
	            }
	        }
	        return reqDto;
	    }
	@Override
	public HoardingMasterDto getByOrgIdAndHoardingId(Long orgId, Long hoardingId) {
		 Map<String, String> requestParam = new HashMap<>();
	        DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
	        uriHandler.setParsePath(true);
	        requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
	        requestParam.put(MainetConstants.AdvertisingAndHoarding.HOARDING_ID, String.valueOf(hoardingId));      
	        URI uri = uriHandler.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_HOARDING_DETAILS_BY_ID_AND_ORGID, requestParam);
	        LinkedHashMap<Long, Object> requestData = (LinkedHashMap<Long, Object>) JersyCall
	                .callRestTemplateClient(orgId, uri.toString());
	        HoardingMasterDto hoardingMasterDto = null;
	        if (requestData != null && !requestData.isEmpty()) {
	            String jsonObject = new JSONObject(requestData).toString();
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	            try {
	            	hoardingMasterDto = mapper.readValue(jsonObject,
	                        HoardingMasterDto.class);
	            } catch (Exception exception) {
	                throw new FrameworkException("Exception occured while fetching details of hoarding Details ");
	            }
	        }
	        return hoardingMasterDto;
		
	}
	
	@Override
	public List<HoardingBookingDto> getHoardingDetailsByOrgId(Long orgId) {
		 Map<String, String> requestParam = new HashMap<>();
	        DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
	        uriHandler.setParsePath(true);
	        requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
	        /*requestParam.put(MainetConstants.AdvertisingAndHoarding.HOARDING_ID, String.valueOf(hoardingId));*/      
	        URI uri = uriHandler.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_HOARDINGBOOKING_DETAILS_BY_ORGID, requestParam);
	        List<LinkedHashMap<Long, Object>> requestData = (List<LinkedHashMap<Long, Object>>) JersyCall
	                .callRestTemplateClient(orgId, uri.toString());
	        List<HoardingBookingDto> hoardingBookingDtos = new ArrayList<>();
			if (requestData != null && !requestData.isEmpty()) {
				requestData.forEach(hoardingBooking -> {
					String jsonObject = new JSONObject(hoardingBooking).toString();
					ObjectMapper mapper = new ObjectMapper();
					mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					try {
						HoardingBookingDto applicationDto = mapper.readValue(jsonObject,
								HoardingBookingDto.class);
							hoardingBookingDtos.add(applicationDto);
						

					} catch (Exception exception) {
						throw new FrameworkException("Exception occured while fetching details of Advertisement Details ");
					}
				});
			}
	        return hoardingBookingDtos;
		
	}
}
