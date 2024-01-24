package com.abm.mainet.common.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ApplicationStatusDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApplicationService implements IApplicationService {

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationStatusDTO getApplicationStatus(long applicationId, int langId) {

		LinkedHashMap<Long, Object> appStatus = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
				ServiceEndpoints.APPLICATION_STATUS + MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO
						+ MainetConstants.WINDOWS_SLASH + applicationId + "/lang/" + langId);
		/*
		 * List<ActionDTOWithDoc> actionLog = new ArrayList<>(); requestList.forEach(obj
		 * -> { String d = new JSONObject(obj).toString(); try { ActionDTOWithDoc action
		 * = new ObjectMapper().readValue(d, ActionDTOWithDoc.class);
		 * actionLog.add(action); } catch (Exception ex) { throw new
		 * FrameworkException("Exception while getting applicaiton history : " + ex); }
		 * });
		 */

		ApplicationStatusDTO appStatusDto;
		try {
			String d = new JSONObject(appStatus).toString();

			appStatusDto = new ObjectMapper().readValue(d, ApplicationStatusDTO.class);
		} catch (Exception ex) {
			throw new FrameworkException("Exception while getting applicaiton history : " + ex);
		}

		return appStatusDto;
	}
//Defect #132264 for orgid dependancy in DSCL
	@Override
	public Long getOrgId(long applicationId) {

		final int id = (int) JersyCall.callRestTemplateClient(applicationId,
				ServiceEndpoints.JercyCallURL.APPLICATION_ORGID + MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO
						+ MainetConstants.WINDOWS_SLASH + applicationId);

		try {

			return Long.valueOf(id);

		} catch (Exception ex) {
			throw new FrameworkException("Exception while getting applicaiton history : " + ex);
		}

	}
	@Override
	public Long getConnId(String conNo, Long orgId) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler defaultTemplateHandler = new DefaultUriTemplateHandler();
		defaultTemplateHandler.setParsePath(true);
		requestParam.put("connectionNo", String.valueOf(conNo));
		requestParam.put("orgId", String.valueOf(orgId));
		Long csIdn = null;
		try {
			URI uri = defaultTemplateHandler.expand(ServiceEndpoints.WebServiceUrl.GET_WATER_CONNECTION_NO_BY_CONNNO,
					requestParam);
			@SuppressWarnings("unchecked")
			final Integer responseVo = (Integer) JersyCall
					.callRestTemplateClient(orgId, uri.toString());
			
			if (responseVo != null)
				csIdn = Long.valueOf(responseVo.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return csIdn;
	}
}
