package com.abm.mainet.water.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;

@Service
public class PlumberLicenseFormServiceImpl implements IPlumberLicenseFormService {

	private static Logger log = Logger.getLogger(PlumberLicenseFormServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public PlumberLicenseResponseDTO saveOrUpdatePlumberLicense(final PlumberLicenseRequestDTO requestDTO)
			throws JsonParseException, JsonMappingException, IOException {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(requestDTO, ServiceEndpoints.ServiceCallURI.PLUMBER_LICENSE_SAVE);
		final String response = new JSONObject(responseVo).toString();
		return new ObjectMapper().readValue(response, PlumberLicenseResponseDTO.class);

	}

	@SuppressWarnings("unchecked")
	@Override
	public PlumberLicenseRequestDTO getApplicationDetails(Long applicationId, Long orgId) {

		PlumberLicenseRequestDTO requestDto = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("applicationId", String.valueOf(applicationId));
		requestParam.put("orgId", String.valueOf(orgId));
		URI uri = dd.expand(ServiceEndpoints.WebServiceUrl.GET_PLUM_DETAILS_BY_APPID, requestParam);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		final String dto = new JSONObject(responseVo).toString();
		try {
			requestDto = new ObjectMapper().readValue(dto, PlumberLicenseRequestDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to PlumberLicenseRequestDTO", e);
		}
		log.info("PlumberLicenseRequestDTO formed is " + requestDto.toString());
		return requestDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlumberLicenseRequestDTO getPlumberDetailsByLicenseNumber(Long orgId, String plumberLicenceNo) {
		PlumberLicenseRequestDTO detailDTO = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgId));
		requestParam.put("licenseNumber", String.valueOf(plumberLicenceNo));
		URI uri = dd.expand(ServiceEndpoints.WebServiceUrl.GET_PLUMBER_BY_PLUMBERNO, requestParam);

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		final String d = new JSONObject(responseVo).toString();
		try {
			detailDTO = new ObjectMapper().readValue(d, PlumberLicenseRequestDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to PlumberLicenseRequestDTO", e);
		}
		return detailDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlumberLicenseResponseDTO savePlumberRenewalData(PlumberLicenseRequestDTO requestDTO)
			throws JsonParseException, JsonMappingException, IOException {
		try {
			final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
					.callRestTemplateClient(requestDTO, ServiceEndpoints.ServiceCallURI.PLUMBER_LICENSE_RENEW_SAVE);
			final String response = new JSONObject(responseVo).toString();
			return new ObjectMapper().readValue(response, PlumberLicenseResponseDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to PlumberLicenseResponseDTO", e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public PlumberLicenseResponseDTO saveDuplicatePlumberData(PlumberLicenseRequestDTO requestDTO)
			throws JsonParseException, JsonMappingException, IOException {
		try {
			final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
					.callRestTemplateClient(requestDTO, ServiceEndpoints.ServiceCallURI.DUPLICATE_PLUMBER_LICENSE_SAVE);
			final String response = new JSONObject(responseVo).toString();
			return new ObjectMapper().readValue(response, PlumberLicenseResponseDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to PlumberLicenseResponseDTO", e);
		}
	}

}
