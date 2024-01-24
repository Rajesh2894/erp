package com.abm.mainet.vehiclemanagement.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.abm.mainet.vehiclemanagement.dto.BeatMasterDTO;

/**
 * The Class RouteMasterServiceImpl.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 07-May-2018
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.IBeatMasterService")
@Path(value = "/routemasterservice")
public class BeatMastService implements IBeatMasterService {

	@Override
	public BeatMasterDTO getRouteByRouteId(Long routeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BeatMasterDTO saveRoute(BeatMasterDTO routeDetails) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BeatMasterDTO updateRoute(BeatMasterDTO routeDetails, List<Long> areaIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRoute(Long routeId, Long empId, String ipMacAdd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BeatMasterDTO> serchRouteByRouteTypeAndRouteNo(String routeName, String routeNo, Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRouteDetails(String status, Long empId, String ipMacAdd, Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BeatMasterDTO> serchRoute(String routeName, String routeNo, Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateRoute(BeatMasterDTO routeMasterDTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BeatMasterDTO> getAllRouteNo(long orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BeatMasterDTO> serchRouteByOrgid(Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}


}
