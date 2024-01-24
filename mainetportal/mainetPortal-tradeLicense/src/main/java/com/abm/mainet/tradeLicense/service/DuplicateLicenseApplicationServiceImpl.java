package com.abm.mainet.tradeLicense.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DuplicateLicenseApplicationServiceImpl implements IDuplicateLicenseApplicationService {

	private static Logger log = Logger.getLogger(IDuplicateLicenseApplicationService.class);

	@Override
	public TradeMasterDetailDTO getDuplicateChargesFromBrmsRule(TradeMasterDetailDTO masDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				masDto, ServiceEndpoints.TRADE_LICENSE_URL.GET_DUPLICATE_SERVICE_CHARGES_FROM_BRMS_RULE);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		}  catch (Exception e) {
            throw new FrameworkException(e);
        }
	}

	@Override
	public TradeMasterDetailDTO saveDuplicateLicense(TradeMasterDetailDTO tardeDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				tardeDto, ServiceEndpoints.TRADE_LICENSE_URL.SAVE_DUPLICATE_SERVICE_APPLICATION);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		}  catch (Exception e) {
            throw new FrameworkException(e);
        }
	}

}