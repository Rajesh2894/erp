package com.abm.mainet.water.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.water.dto.WaterBillRequestDTO;
import com.abm.mainet.water.dto.WaterBillResponseDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WaterBillPaymentServiceImpl implements IWaterBillPaymentService {

    @SuppressWarnings("unchecked")
    @Override
    public WaterBillResponseDTO fetchBillingData(final WaterBillRequestDTO requestDto)
            throws JsonParseException, JsonMappingException, IOException {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
                .callRestTemplateClient(requestDto,ServiceEndpoints.ServiceCallURI.FETCH_WATER_BILL_DATA);
        final String d = new JSONObject(responseVo).toString();

        return new ObjectMapper().readValue(d,
                WaterBillResponseDTO.class);

    }

    @SuppressWarnings("unchecked")
    @Override
    public WaterBillResponseDTO saveOrUpdateBillPaid(final WaterBillRequestDTO inputData)
            throws JsonParseException, JsonMappingException, IOException {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
                .callRestTemplateClient(inputData,
                        ServiceEndpoints.ServiceCallURI.SAVE_BILL_PAID);
        final String d = new JSONObject(responseVo).toString();
        return new ObjectMapper().readValue(d,
                WaterBillResponseDTO.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public WaterBillResponseDTO saveOrUpdateAdvancePayment(final WaterBillRequestDTO inputData)
            throws JsonParseException, JsonMappingException, IOException {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
                .callRestTemplateClient(inputData,
                        ServiceEndpoints.ServiceCallURI.TAX_ID_ADVANCE_PAY);
        final String d = new JSONObject(responseVo).toString();
        return new ObjectMapper().readValue(d,
                WaterBillResponseDTO.class);
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<WaterDataEntrySearchDTO> getBillPaymentDetailData(WaterDataEntrySearchDTO searchDto) {
		List<WaterDataEntrySearchDTO> dto = new ArrayList<>();
		
		 final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(searchDto,
				 ServiceEndpoints.ServiceCallURI.BILL_PAYMENT_DETAIL_DATA);
  
	        try {
	        	for(LinkedHashMap<Long, Object> obj:responseVo) {
	        		final String d = new JSONObject(obj).toString();
	        		WaterDataEntrySearchDTO propSearchDto= new ObjectMapper().readValue(d,
	        				WaterDataEntrySearchDTO.class);
	        		dto.add(propSearchDto);
	        	}
         } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        }
		
		return dto;
	}
    

}
