package com.abm.mainet.swm.service;

import java.util.List;

import com.abm.mainet.swm.dto.BeatMasterDTO;

public interface IRouteInfoService {
	
	List<BeatMasterDTO> getAllRoute(Long orgId);
	List<Object[]> searchVehicle(Long orgId);

}
