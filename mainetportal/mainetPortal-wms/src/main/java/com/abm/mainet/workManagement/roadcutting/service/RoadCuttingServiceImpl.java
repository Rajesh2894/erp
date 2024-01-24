/**
 * 
 */
package com.abm.mainet.workManagement.roadcutting.service;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadCuttingDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author satish.rathore
 *
 */
@Service
public class RoadCuttingServiceImpl implements IRoadCuttingService {

	@Override
	public WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO) {
		//this call goes to service side and fetch data from that and return it
		  return JersyCall.callServiceBRMSRestClient(requestDTO, ServiceEndpoints.RoadCuttingURLs.ROAD_CUTTING_APPLICATION_CHARGES);
	}

	@Override
	public WSResponseDTO getApplicableCharges(WSRequestDTO requestDTO) {
	        final WSResponseDTO response = JersyCall.callServiceBRMSRestClient(requestDTO,
	                ServiceEndpoints.RoadCuttingURLs.ROAD_CUTTING_SERVICE_CHARGE_URL);
	        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
	            return response;
	        } else {
	            throw new FrameworkException("Exception while fatching service charges" + response.getErrorMessage());
	        }
	}

	@Override
	public WSResponseDTO save(RoadCuttingDto roadCuttingDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoadCuttingDto saveRoadCutting(RoadCuttingDto requestDTO) {
		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> responseVo =(LinkedHashMap<Long, Object>)JersyCall.callRestTemplateClient(requestDTO,
        		ServiceEndpoints.RoadCuttingURLs.ROAD_CUTTING_SERVICE_SAVE);
        final String d = new JSONObject(responseVo).toString();
        try {
        	return new ObjectMapper().readValue(d,
        			RoadCuttingDto.class);  	
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return null;
	}

	@Override
	public RoadCuttingDto updateRoadCutting(RoadCuttingDto requestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
