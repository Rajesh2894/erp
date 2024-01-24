package com.abm.mainet.mrm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.dto.MarriageRequest;
import com.abm.mainet.mrm.dto.MarriageResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MarriageServiceImpl implements IMarriageService {

	//private static final Logger LOGGER = Logger.getLogger(MarriageServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public MarriageResponse getMarriageData(MarriageRequest marriageRequest)
			throws JsonParseException, JsonMappingException, IOException {
		MarriageResponse marResponse = null;
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(marriageRequest, ServiceEndpoints.MRM_URL.GET_MARRIAGE_DATA);
		final String jsonString = new JSONObject(responseVo).toString();
		try {
			marResponse = new ObjectMapper().readValue(jsonString, MarriageResponse.class);
		} catch (final Exception e) {
			throw new FrameworkException("Error while casting response to MarriageResponse", e);
		}
		return marResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MarriageDTO saveMarriageRegInDraftMode(MarriageDTO marriageDTO) throws JsonParseException, JsonMappingException, IOException {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(marriageDTO, ServiceEndpoints.MRM_URL.SAVE_MARRIAGE_TAB_DATA);
		final String response = new JSONObject(responseVo).toString();
		try {
			marriageDTO =new ObjectMapper().readValue(response, MarriageDTO.class);
		} catch (final Exception e) {
			throw new FrameworkException("Error while saving marriage tabs", e);
		}
		
		return marriageDTO;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<MarriageDTO> fetchMarriageData(MarriageRequest marriageRequest) {
		List<MarriageDTO> marriageDTOs = new ArrayList<>();
		final List<LinkedHashMap<Long, Object>> responseVo = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(marriageRequest, ServiceEndpoints.MRM_URL.FETCH_MARRIAGE_DATA);
		if (responseVo != null && !responseVo.isEmpty()) {
			responseVo.forEach(response -> {
				String jsonObject = new JSONObject(response).toString();
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					MarriageDTO mrgDTO = objectMapper.readValue(jsonObject, MarriageDTO.class);
					marriageDTOs.add(mrgDTO);
				} catch (Exception exception) {
					throw new FrameworkException("Error Occured while fetching marriage data ", exception);
				}
			});
		}
		return marriageDTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MarriageDTO saveWitnessDetails(MarriageDTO marriageDTO)throws JsonParseException, JsonMappingException, IOException {
		LinkedHashMap<Long, Object> responseRequest = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(marriageDTO, 
				ServiceEndpoints.MRM_URL.SAVE_WITNESS_TAB_DATA);
		MarriageDTO mrmDTO = new MarriageDTO();
		if (responseRequest != null && !responseRequest.isEmpty()) {
			String json = new JSONObject(responseRequest).toString();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				return objectMapper.readValue(json, MarriageDTO.class);
			} catch (Exception exception) {
				throw new FrameworkException("Error Occurred while Saving the marriage details Request " + exception);
			}
		}
		return mrmDTO; 
		
	}

}
