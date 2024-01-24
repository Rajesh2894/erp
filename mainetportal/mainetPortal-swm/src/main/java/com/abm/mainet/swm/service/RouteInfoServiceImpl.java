package com.abm.mainet.swm.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.swm.dto.BeatMasterDTO;

@Service
public class RouteInfoServiceImpl implements IRouteInfoService {

	@SuppressWarnings("unchecked")
	@Override
	public List<BeatMasterDTO> getAllRoute(Long orgId) {

		List<BeatMasterDTO> dto = new ArrayList<BeatMasterDTO>();
		StringBuilder url = new StringBuilder(ApplicationSession.getInstance().getMessage("SWM_GET_ALLROUTE"));
		url.append(orgId);
		dto = (List<BeatMasterDTO>) JersyCall.callRestTemplateClient(null, url.toString(),
				new ParameterizedTypeReference<List<BeatMasterDTO>>() {
				});
		return dto;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> searchVehicle(Long orgId) {
	
		StringBuilder url = new StringBuilder(ApplicationSession.getInstance().getMessage("SWM_GET_ALL_VEHICLE"));
		url.append(orgId);			
		return (List<Object[]>) JersyCall.callRestTemplateClient(null, url.toString());
	}

}
