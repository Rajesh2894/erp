package com.abm.mainet.adh.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ADHBillMasServiceImpl implements IADHBillMasService{

	@SuppressWarnings("unchecked")
	@Override
	public ContractAgreementSummaryDTO findByContractNo(Long orgId, String contractNo) {
		Map<String, String> requestParam = new HashMap<>();

		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put("contractNo", contractNo);
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ServiceEndpoints.ADVERTISER_HOARDING.GET_SEARCH_BILL_PAYMENT_DATA, requestParam);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public ContractAgreementSummaryDTO updateBillPayment(ContractAgreementSummaryDTO dto) {

		LinkedHashMap<Long, Object> requestList = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(dto, ServiceEndpoints.ADVERTISER_HOARDING.UPDATE_BILL_PAYMENT_DATA);

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
