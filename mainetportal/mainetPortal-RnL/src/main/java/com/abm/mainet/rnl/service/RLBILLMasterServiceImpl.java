package com.abm.mainet.rnl.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.rnl.dto.ContractAgreementSummaryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RLBILLMasterServiceImpl implements IRLBILLMasterService{
	
	@Override
	public List<ContractAgreementSummaryDTO> fetchSummaryData(Long orgId) {

		Map<String, String> requestParam = new HashMap<>();

		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));

		URI uri = uriHandler.expand(ServiceEndpoints.BRMS_RNL_URL.FETCH_SUMMARY_DATA, requestParam);
		List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
				.callRestTemplateClient(requestParam, uri.toString());

		List<ContractAgreementSummaryDTO> propInfoDTOList = new ArrayList<>();
		requestList.forEach(estateBookingCancelDto -> {
			String jsonObject = new JSONObject(estateBookingCancelDto).toString();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				ContractAgreementSummaryDTO propInfoDTO = objectMapper.readValue(jsonObject, ContractAgreementSummaryDTO.class);
				propInfoDTOList.add(propInfoDTO);
			} catch (Exception exception) {
				throw new FrameworkException("Exception Occured when fetching Booked Property details ", exception);
			}
		});

		return propInfoDTOList;
	}
	
	@Override
	public ContractAgreementSummaryDTO fetchSearchData(String contNo,String propertyContractNo, Long orgId) {

		Map<String, String> requestParam = new HashMap<>();

		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put("contNo", contNo);
		requestParam.put("propertyContractNo", propertyContractNo);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));

		URI uri = uriHandler.expand(ServiceEndpoints.BRMS_RNL_URL.FETCH_SEARCH_DATA, requestParam);
		LinkedHashMap<Long, Object> requestList = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(requestParam, uri.toString());

		if (requestList != null) {
			final String d = new JSONObject(requestList).toString();

			try {
				return new ObjectMapper().readValue(d, ContractAgreementSummaryDTO.class);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}
	
	@Override
	public ContractAgreementSummaryDTO updateBillPayment(ContractAgreementSummaryDTO dto) {

		LinkedHashMap<Long, Object> requestList = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(dto, ServiceEndpoints.BRMS_RNL_URL.UPDATE_BILL_DATA);

		if (requestList != null) {
			final String d = new JSONObject(requestList).toString();

			try {
				return new ObjectMapper().readValue(d, ContractAgreementSummaryDTO.class);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}

		}
		return null;
	}

}
