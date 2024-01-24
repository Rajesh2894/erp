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
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.adh.dto.ADHRequestDto;
import com.abm.mainet.adh.dto.ADHResponseDTO;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author vishwajeet.kumar
 * @since 14 October 2019
 */

@Service
public class NewAdvertisementApplicationServiceImpl implements INewAdvertisementApplicationService {

	@Override
	@SuppressWarnings("unchecked")
	public List<AdvertiserMasterDto> getAdvertiserDetails(Long advertiserCategoryId, Long orgId) {

		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put(MainetConstants.AdvertisingAndHoarding.ADVERTSIER_CAT, String.valueOf(advertiserCategoryId));
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_AGENCY_BY_AGENCY_CATEOGRYID, requestParam);

		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());

		List<AdvertiserMasterDto> masterDtosList = new ArrayList<>();
		requestList.forEach(requestObj -> {
			String jsonObject = new JSONObject(requestObj).toString();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				AdvertiserMasterDto advertiserMasterDto = objectMapper.readValue(jsonObject, AdvertiserMasterDto.class);
				masterDtosList.add(advertiserMasterDto);
			} catch (Exception exception) {
				throw new FrameworkException("Exception Occured when fetching Advertiser details ", exception);
			}
		});
		return masterDtosList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LookUp> geLocationByOrgId(Long orgId) {

		Map<String, String> requestParam = new HashMap<>();
		List<LookUp> lookUpsList = new ArrayList<>();
		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_LOCATION_NEWADV_APP_BY_ORGID,
				requestParam);
		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(requestParam, uri.toString());
		if (requestList != null && !requestList.isEmpty()) {
			requestList.forEach(response -> {
				String jsonObject = new JSONObject(response).toString();
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					LookUp lookUp = objectMapper.readValue(jsonObject, LookUp.class);
					lookUpsList.add(lookUp);
				} catch (Exception exception) {
					throw new FrameworkException("Error Occured while fetching location ", exception);
				}
			});
		}
		return lookUpsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NewAdvertisementReqDto saveNewAdvertisementApplication(NewAdvertisementReqDto advertisementReqDto) {

		LinkedHashMap<Long, Object> responseRequest = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				advertisementReqDto, ServiceEndpoints.ADVERTISER_HOARDING.SAVE_ADVERTISEMENT_APPLICATION);
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
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, Object> getCheckListChargeFlagAndLicMaxDay(Long orgId, String serviceShortCode) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
		uriTemplateHandler.setParsePath(true);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		requestParam.put(MainetConstants.AdvertisingAndHoarding.SERVICE_SGORTCODE, serviceShortCode);
		URI uri = uriTemplateHandler
				.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_CHECKLIST_CHARGES_FLAG_AND_LICMAXDAYS, requestParam);
		final LinkedHashMap<String, Object> responseVo = (LinkedHashMap<String, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		map = responseVo;
		return map;
	}
	
	//Defect #129856
	
	@Override
	@SuppressWarnings("unchecked")
	public String getLicMaxTenureDays(Long orgId, String serviceShortCode,Long licType) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
		uriTemplateHandler.setParsePath(true);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		requestParam.put(MainetConstants.AdvertisingAndHoarding.SERVICE_SGORTCODE, serviceShortCode);
		requestParam.put(MainetConstants.AdvertisingAndHoarding.LICTYPE, String.valueOf(licType));
		URI uri = uriTemplateHandler
				.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_LICMAX_TENURE_DAYS, requestParam);
		final Map<String, String> responseVo = (Map<String, String>)JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		return responseVo.get("licMaxTenureDays");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NewAdvertisementApplicationDto> getLicenseNoByOrgId(Long orgId) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_LICENSE_NO_BY_ORGID, requestParam);

		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		List<NewAdvertisementApplicationDto> advertisementApplicationDtos = new ArrayList<>();
		if (requestList != null && !requestList.isEmpty()) {
			requestList.forEach(listOfLicenseNo -> {
				String jsonObject = new JSONObject(listOfLicenseNo).toString();
				ObjectMapper mapper = new ObjectMapper();
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				try {
					NewAdvertisementApplicationDto applicationDto = mapper.readValue(jsonObject,
							NewAdvertisementApplicationDto.class);
					if (StringUtils.equals(applicationDto.getMobileNo(),
							UserSession.getCurrent().getEmployee().getEmpmobno())) {
						advertisementApplicationDtos.add(applicationDto);
					}

				} catch (Exception exception) {
					throw new FrameworkException("Exception occured while fetching details of Advertisement Details ");
				}
			});
		}
		return advertisementApplicationDtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NewAdvertisementReqDto getAdvertisementApplicationByLicenseNo(String licenseNo, Long orgId) {

		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put(MainetConstants.AdvertisingAndHoarding.LICENSE_NO, String.valueOf(licenseNo));
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_ADVERTISEMENT_DETAILS_BY_LICENSENO,
				requestParam);

		LinkedHashMap<Long, Object> requestData = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(orgId,
				uri.toString());
		NewAdvertisementReqDto advertisementReqDto = null;
		if (requestData != null && !requestData.isEmpty()) {
			String jsonObject = new JSONObject(requestData).toString();
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			try {
				advertisementReqDto = mapper.readValue(jsonObject, NewAdvertisementReqDto.class);
			} catch (Exception exception) {
				throw new FrameworkException("Exception occured while fetching details of Advertisement Details ");
			}
		}
		return advertisementReqDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NewAdvertisementReqDto getAdvertisementApplicationByApp(Long applicationId, Long orgId) {
		Map<String, Long> requestParam = new HashMap<>();
		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put(MainetConstants.AdvertisingAndHoarding.APPLICATION_ID, applicationId);
		requestParam.put(MainetConstants.Common.ORGID, orgId);
		URI uri = uriHandler.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_DATA_BY_APPLICATION_ID, requestParam);

		LinkedHashMap<Long, Object> requestData = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(orgId,
				uri.toString());
		NewAdvertisementReqDto advertisementReqDto = null;
		if (requestData != null && !requestData.isEmpty()) {
			String jsonObject = new JSONObject(requestData).toString();
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			try {
				advertisementReqDto = mapper.readValue(jsonObject, NewAdvertisementReqDto.class);
			} catch (Exception exception) {
				throw new FrameworkException("Exception occured while fetching details of Advertisement Details ");
			}
		}
		return advertisementReqDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ADHResponseDTO getADHDataByApplicationId(ADHRequestDto adhRequestDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				adhRequestDto, ServiceEndpoints.ADVERTISER_HOARDING.GET_ADH_DATA_BY_APPLICATION_ID);
		ADHResponseDTO adhResponseDto = null;
		if (responseVo != null && !responseVo.isEmpty()) {
			String jsonObject = new JSONObject(responseVo).toString();
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			try {
				adhResponseDto = mapper.readValue(jsonObject, ADHResponseDTO.class);
			} catch (Exception exception) {
				throw new FrameworkException("Exception occured while fetching details of Advertisement Details ");
			}
		}
		return adhResponseDto;
	}


	@SuppressWarnings("unchecked")
	@Override
	public NewAdvertisementApplicationDto getPropertyDetailsByPropertyNumber(NewAdvertisementApplicationDto reqDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				reqDto, ServiceEndpoints.ADVERTISER_HOARDING.GET_PROPERTY_NO_DATA_BY_PROPERTY_NO);
		NewAdvertisementApplicationDto NewadhResponseDto = null;
		if (responseVo != null && !responseVo.isEmpty()) {
			String jsonObject = new JSONObject(responseVo).toString();
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			try {
				NewadhResponseDto = mapper.readValue(jsonObject, NewAdvertisementApplicationDto.class);
			} catch (Exception exception) {
				throw new FrameworkException("Exception occured while fetching details of Advertisement Details ");
			}
		}
		return NewadhResponseDto;
	}
}

