package com.abm.mainet.rts.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.regexp.recompile;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.ui.model.DrainageConnectionModel;
import com.abm.mainet.rts.ui.model.RtsServiceFormModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DrainageConnectionServiceImpl implements DrainageConnectionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DrainageConnectionServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public DrainageConnectionDto saveDrainageConnection(DrainageConnectionModel model) {

		DrainageConnectionDto dto = model.getDrainageConnectionDto();

		dto.setReqDTO(model.getReqDTO());
		dto.setCheckListApplFlag(model.getCheckListApplFlag());
		dto.setApplicationchargeApplFlag(model.getApplicationchargeApplFlag());

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(dto, ServiceEndpoints.RTS.SAVE_DRAINAGE_DATA);
		final String d1 = new JSONObject(responseVo).toString();

		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, DrainageConnectionDto.class);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

	@Override
	public Map<Long, String> getDept(Long orgId) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);

		requestParam.put("orgId", String.valueOf(orgId));

		URI uri = dd.expand(ServiceEndpoints.RTS.FETCH_DEPT, requestParam);

		@SuppressWarnings("unchecked")
		Map<Long, String> deptList = (Map<Long, String>) JersyCall.callRestTemplateClient(orgId, uri.toString());

		return deptList;

	}

	@Override
	public Map<Long, String> getService(Long orgId, Long deptId, String activeStatus) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();

		requestParam.put("orgId", String.valueOf(orgId));
		requestParam.put("deptId", String.valueOf(deptId));
		requestParam.put("activeStatus", String.valueOf(activeStatus));

		URI uri = dd.expand(ServiceEndpoints.RTS.FETCH_RTS_SERVICES, requestParam);

		@SuppressWarnings("unchecked")
		Map<Long, String> serviceList = (Map<Long, String>) JersyCall.callRestTemplateClient(orgId, uri.toString());

		return serviceList;

	}

	/**
	 * Method for fetching Drainage connection Information Defect #81731
	 */
	@Override
	public DrainageConnectionDto fetchDrainageConnectionInfo(Long appId, Long orgId) {
		DrainageConnectionDto drnDto = new DrainageConnectionDto();
		drnDto.setApmApplicationId(appId);
		drnDto.setOrgId(orgId);

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(drnDto, ServiceEndpoints.RTS.FETCH_DRN_CONN_INFO + MainetConstants.WINDOWS_SLASH
						+ drnDto.getApmApplicationId() + MainetConstants.WINDOWS_SLASH + drnDto.getOrgId());
		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, DrainageConnectionDto.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	/**
	 * Method for fetching Drainage connection document Information Defect #81731
	 */
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
				.callRestTemplateClient(requestParam, ServiceEndpoints.RTS.GET_DCS_UPLOAD_DOCS);
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

	/*
	 * @Override public Map<Long, String> getWardZones(Long orgId) { // TODO
	 * Auto-generated method stub return null; }
	 */
}
