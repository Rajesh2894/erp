package com.abm.mainet.bnd.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.bnd.dto.DrainageConnectionDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("bndRtsServiceImpl")
public class RtsServiceImpl implements IRtsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RtsServiceImpl.class);

	@Override
	public Map<Long, String> fetchWardZone(Long orgId) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgId));
		URI uri = dd.expand(ServiceEndpoints.BIRTH_DEATH.BND_FETCH_WARDZONE, requestParam);
		@SuppressWarnings("unchecked")
		Map<Long, String> wardZoneMap = (Map<Long, String>) JersyCall.callRestTemplateClient(orgId, uri.toString());

		return wardZoneMap;
	}

	@Override
	public Map<String, String> fetchRtsService(Long orgId) {

		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgId));
		URI uri = dd.expand(ServiceEndpoints.BIRTH_DEATH.BND_FETCH_RTS_SERVICES, requestParam);
		@SuppressWarnings("unchecked")
		Map<String, String> serviceList = (Map<String, String>) JersyCall.callRestTemplateClient(orgId, uri.toString());
		return serviceList;
	}

	@Override
	public Map<String, String> serviceInfo(Long orgId) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgId));
		URI uri = dd.expand(ServiceEndpoints.BIRTH_DEATH.BND_SERVICE_INFO, requestParam);
		@SuppressWarnings("unchecked")
		Map<String, String> serviceInfo = (Map<String, String>) JersyCall.callRestTemplateClient(orgId, uri.toString());
		return serviceInfo;
	}

	@Override
	public RequestDTO fetchRtsApplicationInformationById(Long appId, Long orgId) {
		// TODO Auto-generated method stub

		RequestDTO rtsDto = new RequestDTO();
		rtsDto.setApplicationId(appId);
		rtsDto.setOrgId(orgId);

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				rtsDto, ServiceEndpoints.RTS.FETCH_DRN_FIRST_APPEAL + MainetConstants.WINDOWS_SLASH
						+ rtsDto.getApplicationId() + MainetConstants.WINDOWS_SLASH + rtsDto.getOrgId());
		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, RequestDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
	
	@Override
	public LinkedHashMap<String, Object> serviceInformation(Long orgId,String serviceShortCode) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgId));
		requestParam.put("serviceShortCode", serviceShortCode);
		URI uri = dd.expand(ServiceEndpoints.BIRTH_DEATH.SERVICE_INFORMAION_BND, requestParam);
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> serviceInfo = (LinkedHashMap<String, Object>) JersyCall.callRestTemplateClient(orgId, uri.toString());
		return serviceInfo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentDetailsVO> fetchDocumentDetailsByAppNo(Long appId, Long orgId) {
		// TODO Auto-generated method stub

		DrainageConnectionDto requestParam = new DrainageConnectionDto();
		requestParam.setApmApplicationId(appId);
		requestParam.setOrgId(orgId);
		try {
			new ObjectMapper().writeValueAsString(requestParam);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<LinkedHashMap<Long, Object>> responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(requestParam, ServiceEndpoints.BIRTH_DEATH.BND_GET_DCS_UPLOAD_DOCS);
		List<DocumentDetailsVO> responseList = new ArrayList<>();
		if (responseObj != null && !responseObj.isEmpty()) {
			responseObj.forEach(obj -> {
				String d = new JSONObject(obj).toString();
				try {
					DocumentDetailsVO app = new ObjectMapper().readValue(d, DocumentDetailsVO.class);
					responseList.add(app);
				} catch (Exception ex) {
					LOGGER.error("Exception while casting   rest response to  DocumentDetailsVO:" + ex);
				}

			});
		}

		return responseList;
	}
	@Override
	public List<DocumentDetailsVO> fetchDrainageConnectionDocsByAppNo(Long appId, Long orgId) {
		// TODO Auto-generated method stub

		DrainageConnectionDto requestParam = new DrainageConnectionDto();
		requestParam.setApmApplicationId(appId);
		requestParam.setOrgId(orgId);
		try {
			new ObjectMapper().writeValueAsString(requestParam);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<LinkedHashMap<Long, Object>> responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(requestParam, ServiceEndpoints.BIRTH_DEATH.BND_GET_DCS_UPLOAD_DOCS);
		List<DocumentDetailsVO> responseList = new ArrayList<>();
		if (responseObj != null && !responseObj.isEmpty()) {
			responseObj.forEach(obj -> {
				String d = new JSONObject(obj).toString();
				try {
					DocumentDetailsVO app = new ObjectMapper().readValue(d, DocumentDetailsVO.class);
					responseList.add(app);
				} catch (Exception ex) {
					LOGGER.error("Exception while casting   rest response to  DocumentDetailsVO:" + ex);
				}

			});
		}

		return responseList;
	}
}
