package com.abm.mainet.water.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WaterCommonServiceImpl implements WaterCommonService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(WaterCommonServiceImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> validateAndFetchProperty(final String propertyNo, final Long orgId) {
        final Map<String, String> request = new HashMap<>(0);
        final Map<String, String> response = new HashMap<>(0);
        request.put(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.REQUEST_PROPNO, propertyNo);
        request.put(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.REQUEST_ORGID, orgId.toString());
        try {
            final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(request,
                    ApplicationSession.getInstance().getMessage("PROPERTY_BILL_PAYMENT"));
            final String jsonString = new JSONObject(responseVo).toString();
            final Map<String, Object> result = new ObjectMapper().readValue(jsonString,
                    Map.class);
            if (result != null && !result.isEmpty()) {
                response.put(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.RESPONSE_AMOUNT,
                        result.get(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.RESPONSE_AMOUNT).toString());
                response.put(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.RESPONSE_PROPNO,
                        result.get(MainetConstants.NewWaterServiceConstants.PROPERTY_INTEGRATION.RESPONSE_PROPNO).toString());
            }
        } catch (Exception e) {
            LOGGER.error("error while fetching property details in water connection for property No:" + propertyNo + " and orgID:"
                    + orgId, e);
        }
        return response;
    }

}
