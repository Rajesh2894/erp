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
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.dto.ChangeOfUsageResponseDTO;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChangeOfUsageServiceImpl implements IChangeOfUsageService {

	private static Logger log = Logger.getLogger(ChangeOfUsageServiceImpl.class);
    @SuppressWarnings("unchecked")
    @Override
    public ChangeOfUsageResponseDTO fetchConnectionData(final ChangeOfUsageRequestDTO requestVo)
            throws JsonParseException, JsonMappingException, IOException {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
                .callRestTemplateClient(requestVo, ServiceEndpoints.ServiceCallURI.CHANGE_OF_USAGE_CONNECTION);
        final String result = new JSONObject(responseVo).toString();
        return new ObjectMapper()
                .readValue(result, ChangeOfUsageResponseDTO.class);
    }

    @Override
    public ChangeOfUsageResponseDTO saveOrUpdateChangeUsage(final ChangeOfUsageRequestDTO requestDTO)
            throws JsonParseException, JsonMappingException, IOException {

        @SuppressWarnings("unchecked")
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
                requestDTO, ServiceEndpoints.ServiceCallURI.CHANGE_USAGE_SAVE);
        final String response = new JSONObject(responseVo).toString();
        return new ObjectMapper().readValue(response,
                ChangeOfUsageResponseDTO.class);

    }

	@Override
	 @SuppressWarnings("unchecked")
	public ChangeOfUsageRequestDTO getAppicationDetails(Long applicationId, Long orgId) {
		
		ChangeOfUsageRequestDTO requestDto = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("applicationId", String.valueOf(applicationId));
		requestParam.put("orgId", String.valueOf(orgId));
		URI uri = dd.expand(ServiceEndpoints.WebServiceUrl.GET_CHANGEOFUSAGE_DETAILS_BY_APPID, requestParam);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		final String dto = new JSONObject(responseVo).toString();
		try {
			requestDto = new ObjectMapper().readValue(dto, ChangeOfUsageRequestDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to ChangeOfUsageRequestDTO", e);
		}
		log.info("ChangeOfUsageRequestDTO formed is " + requestDto.toString());
		return requestDto;
	
	}
}
