package com.abm.mainet.tradeLicense.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.tradeLicense.dto.TradeMasterCategoryDto;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChangeCategorySubCategoryServiceImpl implements IChangeCategorySubCategoryService {

	private static Logger log = Logger.getLogger(IChangeCategorySubCategoryService.class);

	@SuppressWarnings("unchecked")
	@Override
	public TradeMasterDetailDTO getChangeCategoryChargesFromBrmsRule(TradeMasterDetailDTO masDto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				masDto, ServiceEndpoints.TRADE_LICENSE_URL.GET_CATEGORY_SUBCATEGORY_CHARGES_FROM_BRMS_RULE);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		} catch (Exception e) {
            throw new FrameworkException(e);
        }
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public TradeMasterDetailDTO saveChangeCategoryService(TradeMasterDetailDTO masDto, TradeMasterDetailDTO tradeDto) {
		
		TradeMasterCategoryDto dto = new TradeMasterCategoryDto();
		dto.setTradeMasterDetailDTO(masDto);
		dto.setTradeDTO(tradeDto);
		
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				dto, ServiceEndpoints.TRADE_LICENSE_URL.SAVE_CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_APPLICATION);

		final String d = new JSONObject(responseVo).toString();
		try {
			return new ObjectMapper().readValue(d, TradeMasterDetailDTO.class);
		} catch (Exception e) {
            throw new FrameworkException(e);
        }

   }
	
}
