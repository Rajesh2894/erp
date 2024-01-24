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
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WaterReconnectionFormService implements IWaterReconnectionFormService {

	private static Logger log = Logger.getLogger(WaterReconnectionFormService.class);
    @SuppressWarnings("unchecked")
    @Override
    public WaterReconnectionRequestDTO searchConnectionDetails(final WaterReconnectionRequestDTO requestDTO)
            throws JsonParseException, JsonMappingException, IOException {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
                .callRestTemplateClient(requestDTO, ServiceEndpoints.ServiceCallURI.WATER_RECONNECTION_SEARCH_CON_DETAIL);
        final String d = new JSONObject(responseVo).toString();
        return new ObjectMapper().readValue(d, WaterReconnectionRequestDTO.class);

    }

    @SuppressWarnings("unchecked")
    @Override
    public WaterReconnectionResponseDTO serachPlumerLicense(final WaterReconnectionRequestDTO requestDTO)
            throws JsonParseException, JsonMappingException, IOException {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
                .callRestTemplateClient(requestDTO, ServiceEndpoints.ServiceCallURI.WATER_RECONNECTION_PLUMBER_SEARCH);
        final String d = new JSONObject(responseVo).toString();
        return new ObjectMapper().readValue(d, WaterReconnectionResponseDTO.class);

    }

    @SuppressWarnings("unchecked")
    @Override
    public WaterReconnectionResponseDTO saveOrUpdateReconnection(final WaterReconnectionRequestDTO reconnectionRequestDTO)
            throws JsonParseException, JsonMappingException, IOException {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
                .callRestTemplateClient(reconnectionRequestDTO,
                        ServiceEndpoints.ServiceCallURI.RECONNECTION_SAVE_FORM);
        final String response = new JSONObject(responseVo).toString();
        return new ObjectMapper()
                .readValue(response, WaterReconnectionResponseDTO.class);
    }

	@Override
	public WaterReconnectionRequestDTO getAppicationDetails(Long applicationId, Long orgId) {
		WaterReconnectionRequestDTO requestDto = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("applicationId", String.valueOf(applicationId));
		requestParam.put("orgId", String.valueOf(orgId));
		URI uri = dd.expand(ServiceEndpoints.WebServiceUrl.GET_WATER_RECONNECTION_DETAILS_BY_APPID, requestParam);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		final String dto = new JSONObject(responseVo).toString();
		try {
			requestDto = new ObjectMapper().readValue(dto, WaterReconnectionRequestDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to WaterReconnectionRequestDTO", e);
		}
		
		log.info("WaterReconnectionRequestDTO formed is " + requestDto.toString());
		return requestDto;
	}

}
