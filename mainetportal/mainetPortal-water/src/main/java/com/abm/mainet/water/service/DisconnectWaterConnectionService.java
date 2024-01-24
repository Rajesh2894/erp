package com.abm.mainet.water.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DisconnectWaterConnectionService implements IDisconnectWaterConnectionService {

	private static Logger log = Logger.getLogger(DisconnectWaterConnectionService.class);
    @SuppressWarnings("unchecked")
    @Override
    public WaterDisconnectionResponseDTO fetchConnectionDetails(final WaterDeconnectionRequestDTO waterRequestDto)
            throws JsonParseException, JsonMappingException, IOException {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
                waterRequestDto, ServiceEndpoints.ServiceCallURI.DISCONNECTION_SEARCH_CON_DETAIL);

        final String d = new JSONObject(responseVo).toString();
        return new ObjectMapper().readValue(d,
                WaterDisconnectionResponseDTO.class);
    }

    @Override
    public Object validatePlumbetValidity(final String plumberLicence) {
        return JersyCall.callRestTemplateClient(plumberLicence,
                ServiceEndpoints.ServiceCallURI.DISCONNECTION_PLUMBER_LICENSE_VALID);

    }

    @SuppressWarnings("unchecked")
    @Override
    public WaterDisconnectionResponseDTO saveOrUpdateDisconnection(final WaterDeconnectionRequestDTO waterRequestDto)
            throws JsonParseException, JsonMappingException, IOException {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
                waterRequestDto, ServiceEndpoints.ServiceCallURI.DISCONNECTION_SAVE_FORM);
        final String d = new JSONObject(responseVo).toString();
        return new ObjectMapper().readValue(d,
                WaterDisconnectionResponseDTO.class);

    }

	@Override
	public WaterDeconnectionRequestDTO getWaterDisconnecDetails(Long applicationId, Long orgId) {
		WaterDeconnectionRequestDTO requestDto = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler defaultTemplateHandler = new DefaultUriTemplateHandler();
		defaultTemplateHandler.setParsePath(true);
		requestParam.put("applicationId", String.valueOf(applicationId));
		requestParam.put("orgId", String.valueOf(orgId));

		try {
		URI uri = defaultTemplateHandler.expand(ServiceEndpoints.WebServiceUrl.GET_WATER_DISCONNECTION_DETAILS_BY_APPID, requestParam);
		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		final String dto = new JSONObject(responseVo).toString();

			requestDto = new ObjectMapper().readValue(dto, WaterDeconnectionRequestDTO.class);
		} catch (final IOException e) {
			log.error("Error while fetching values from service: " + e.getMessage(), e);
			throw new FrameworkException("Error while casting response to WaterDeconnectionRequestDTO", e);
		}
		
		log.info("WaterDeconnectionRequestDTO formed is " + requestDto.toString());
		return requestDto;
	}

}
