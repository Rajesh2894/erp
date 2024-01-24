package com.abm.mainet.common.dashboard.rest.ui.controller;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.dashboard.service.CitizenServicesDashboardGraphServiceImpl;

import io.swagger.annotations.Api;

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/citizenGrievance")
@Produces("application/json")
@WebService
@Api(value = "/citizenGrievance")
@Path("/citizenGrievance")
public class CitizenGrievanceController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CitizenGrievanceController.class);
	
	@Autowired
	CitizenServicesDashboardGraphServiceImpl CitizenServicesDashboardGraphServiceImpl;
	
	@RequestMapping(value = "/getPreviousDayGrivences", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getPreviousDayGrivences")
	public Map<String,Long> getPreviousDayGrivences() {
      Map<String,Long> map= new HashMap<String, Long>();
      Long priviousDayGrievances =0L;
      Long priviousDayReslovedGrievances =0L;
 		try {
			 priviousDayGrievances= CitizenServicesDashboardGraphServiceImpl.getPriviousDayGrievances();
			 priviousDayReslovedGrievances= CitizenServicesDashboardGraphServiceImpl.getPriviousDayReslovedGrievances();
			map.put("priviousDayRegisteredGrievances", priviousDayGrievances);
			map.put("priviousDayReslovedGrievances", priviousDayReslovedGrievances);
			map.put("statusCode", 00L);
			
		} catch (final Exception ex) {
			map.put("priviousDayRegisteredGrievances", 0L);
			map.put("priviousDayReslovedGrievances", 0L);
			map.put("statusCode", 11L);
			LOGGER.error("problem occurred while request for CitizenGrievanceController::getPreviousDayGrivences", ex);
		}

		return map;

	}

}
