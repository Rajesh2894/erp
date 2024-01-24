package com.abm.mainet.tradeLicense.service;

import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransferLicenseServiceImpl implements iTransferLicenseService {

	@Override
	public TradeMasterDetailDTO saveTransferLicenseService(TradeMasterDetailDTO tradeMasterDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				tradeMasterDto, ServiceEndpoints.TRADE_LICENSE_URL.TRADE_TRANSFER_SERVICE_SAVE_URL);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

	}

	@Override
	public TradeMasterDetailDTO getTransferChargesFromBrmsRule(TradeMasterDetailDTO masDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				masDto, ServiceEndpoints.TRADE_LICENSE_URL.GET_TRANSFER_SERVICE_CHARGES_FROM_BRMS_RULE);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
}
