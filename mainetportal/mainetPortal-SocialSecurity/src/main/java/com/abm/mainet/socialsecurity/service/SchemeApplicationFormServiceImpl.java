/**
 * 
 */
package com.abm.mainet.socialsecurity.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.BankMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.CriteriaDto;
import com.fasterxml.jackson.databind.ObjectMapper;

/*  @author priti.singh
 *
 */
@Service
public class SchemeApplicationFormServiceImpl implements ISchemeApplicationFormService {

	private static final Logger logger = Logger.getLogger(SchemeApplicationFormServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationFormDto saveApplicationDetails(ApplicationFormDto applicationformdto) {
		boolean status = false;
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				applicationformdto, ServiceEndpoints.SOCIAL_SECURITY_URL.SOCIAL_SECURITY_SAVE_URL);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, ApplicationFormDto.class);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LookUp> FindSecondLevelPrefixByFirstLevelPxCode(Long orgId, String parentPx, Long parentpxId,
			Long level) {

		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);

		requestParam.put("orgId", String.valueOf(orgId));
		requestParam.put("parentPx", String.valueOf(parentPx));
		requestParam.put("parentpxId", String.valueOf(parentpxId));
		requestParam.put("level", String.valueOf(level));

		URI uri = dd.expand(ServiceEndpoints.SOCIAL_SECURITY_URL.FIND_SECONDLEVEL_PREFIX_BY_FIRSTLEVEL_PXCODE,
				requestParam);

		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		List<LookUp> dtos = new ArrayList<>();
		requestList.forEach(obj -> {
			String d = new JSONObject(obj).toString();
			try {
				LookUp dto = new ObjectMapper().readValue(d, LookUp.class);

				dtos.add(dto);
			} catch (Exception e) {
			}
		});
		return dtos;

	}

	@Override
	public List<BankMasterDTO> getBankList(Long orgId) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);

		requestParam.put("orgId", String.valueOf(orgId));

		URI uri = dd.expand(ServiceEndpoints.SOCIAL_SECURITY_URL.BANK_MASTER_URL, requestParam);

		@SuppressWarnings("unchecked")
		List<LinkedHashMap<Long, Object>> bankList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		List<BankMasterDTO> dtos = new ArrayList<>();
		try {
			for (LinkedHashMap<Long, Object> obj : bankList) {
				final String d = new JSONObject(obj).toString();
				BankMasterDTO dto = new ObjectMapper().readValue(d, BankMasterDTO.class);
				dtos.add(dto);
			}
		} catch (Exception e) {
		}
		return dtos;
	}

	@Override
	public List<Object[]> findAllActiveServicesWhichIsNotActual(Long orgId, Long depId, long activeStatusId,
			String notActualFlag) {
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);

		requestParam.put("orgId", String.valueOf(orgId));
		requestParam.put("depId", String.valueOf(depId));
		requestParam.put("activeStatusId", String.valueOf(activeStatusId));
		requestParam.put("notActualFlag", "N");

		// URI uri = dd.expand(ServiceEndpoints.SOCIAL_SECURITY_URL.SERVICE_MASTER_URL,
		// requestParam);
		URI uri = dd.expand(ServiceEndpoints.SOCIAL_SECURITY_URL.GET_ACTIVE_SERVICE, requestParam);

		@SuppressWarnings("unchecked")
		List<Object[]> requestList = (List<Object[]>) JersyCall.callRestTemplateClient(orgId, uri.toString());

		return requestList;

	}

	@Override
	public Long getCriteriaGridId(CriteriaDto dto) {
		final int id = (int) JersyCall.callRestTemplateClient(dto, ServiceEndpoints.SOCIAL_SECURITY_URL.GET_GRID_ID);

		Long gridId = Long.valueOf(id);

//		if (responseVo != null) {
//			final String d = new JSONObject(responseVo).toString();
//
//			 
//		}
		return gridId;
	}
}