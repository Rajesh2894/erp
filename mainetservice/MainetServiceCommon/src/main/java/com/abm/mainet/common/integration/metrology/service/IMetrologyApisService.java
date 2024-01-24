package com.abm.mainet.common.integration.metrology.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Metrology api integration author rajesh.das
 * 
 * @Since 02-12-2021
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface IMetrologyApisService {

	@GET
	@ApiOperation(value = "District wise Nowcast Warnings", notes = "District wise Nowcast Warnings")
	@Path("/getDistrictWiseNowCastWarning/id/{id}")
	public Object getNowCastDistrictDataById(
			@ApiParam(value = "District  Id", required = true) @PathParam("id") Long id) throws Exception;
	
	@GET
	@ApiOperation(value = "All District wise Nowcast Warnings For ", notes = "All District wise Nowcast Warnings")
	@Path("/getAllDistrictNowCastWarning")
	public Object getAllDistrictNowCastWarning() throws Exception;
	
	@GET
	@ApiOperation(value = "Weather forecast for 7 days by id", notes = "Weather forecast for 7 days by id")
	@Path("/getWeatherForecastForSeveDaysById/id/{id}")
	public Object getWeatherForecastForSevenDaysById(
			@ApiParam(value = "Id", required = true) @PathParam("id") Long id) throws Exception;
	
	@GET
	@ApiOperation(value = "All Weather forecast for 7 days", notes = "All Weather forecast for 7 days")
	@Path("/getAllWeatherForecastForSevenDays")
	public Object getAllWeatherForecastForSevenDays() throws Exception;
	
	@GET
	@ApiOperation(value = "Districtwise Warnings By Id", notes = "Districtwise Warnings By Id")
	@Path("/getDistrictWiseWarningById/id/{id}")
	public Object getDistrictWiseWarningById(@ApiParam(value = "District  Id", required = true) @PathParam("id") String id) throws Exception;
	
	
	@GET
	@ApiOperation(value = "Station wise now cast API", notes = "Station wise now cast API")
	@Path("/getStationWiseNowCastDataById/id/{id}")
	public Object getStationWiseNowCastDataById(@ApiParam(value = "Station  name", required = true) @PathParam("id") String id) throws Exception;
	
	@GET
	@ApiOperation(value = "Current Weather Data By station Id", notes = "Current Weather Data By station Id")
	@Path("/getCurrentWeatherDataByStationId/id/{id}")
	public Object getCurrentWeatherDataByStationId(@ApiParam(value = "Station  id", required = true) @PathParam("id") String id) throws Exception;
	
	@GET
	@ApiOperation(value = "Current Weather Data ", notes = "Current Weather Data ")
	@Path("/getCurrentWeatherData")
	public Object getCurrentWeatherData() throws Exception;
	
	
	
}
