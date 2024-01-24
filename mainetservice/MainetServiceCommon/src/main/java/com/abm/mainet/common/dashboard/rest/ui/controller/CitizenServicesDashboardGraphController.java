package com.abm.mainet.common.dashboard.rest.ui.controller;

import java.util.List;

import javax.jws.WebService;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.dashboard.domain.skdcl.DeptOrSLAWiseDataEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.DeptOrSLAWiseServiceStatusEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.FinancialDurationWiseServiceStatusEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.ServiceStatusWiseCountEntity;
import com.abm.mainet.common.dashboard.service.CitizenServicesDashboardGraphServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/citizenServicesDashboardGraph")
@Produces("application/json")
@WebService
@Api(value = "/citizenServicesDashboardGraph")
@Path("/citizenServicesDashboardGraph")
public class CitizenServicesDashboardGraphController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CitizenServicesDashboardGraphController.class);

	@Autowired
	CitizenServicesDashboardGraphServiceImpl citizenServicesDashboardGraphServiceImpl;

	@RequestMapping(value = "/getServiceStatusWiseCounts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getServiceStatusWiseCounts")
	@ApiOperation(value = "Get Service Status Wise Counts", notes = "Get Service Status Wise Counts", response = ServiceStatusWiseCountEntity.class, responseContainer = "List")
	public List<ServiceStatusWiseCountEntity> getServiceStatusWiseCounts() {

		try {
			return citizenServicesDashboardGraphServiceImpl.getServiceStatusWiseCounts();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getSLAWiseServiceStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getSLAWiseServiceStatus")
	@ApiOperation(value = "Get SLA Wise Service Status", notes = "Get SLA Wise Service Status", response = DeptOrSLAWiseServiceStatusEntity.class, responseContainer = "List")
	public List<DeptOrSLAWiseServiceStatusEntity> getSLAWiseServiceStatus() {

		try {
			return citizenServicesDashboardGraphServiceImpl.getSLAWiseServiceStatus(0);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getDeptWiseServiceStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getDeptWiseServiceStatus")
	@ApiOperation(value = "Get Department Wise Service Status", notes = "Get Department Wise Service Status", response = DeptOrSLAWiseServiceStatusEntity.class, responseContainer = "List")
	public List<DeptOrSLAWiseServiceStatusEntity> getDeptWiseServiceStatus() {

		try {
			return citizenServicesDashboardGraphServiceImpl.getDeptWiseServiceStatus(0);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getSLAWiseServiceStatusByDays/{noOfDays}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getSLAWiseServiceStatusByDays/{noOfDays}")
	@ApiOperation(value = "Get SLA Wise Service Status By Duration", notes = "Get SLA Wise Service Status By Duration", response = DeptOrSLAWiseServiceStatusEntity.class, responseContainer = "List")
	public List<DeptOrSLAWiseServiceStatusEntity> getSLAWiseServiceStatusByDays(
			@PathVariable("noOfDays") @PathParam("noOfDays") int noOfDays) {

		try {
			return citizenServicesDashboardGraphServiceImpl.getSLAWiseServiceStatus(noOfDays);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getDeptWiseServiceStatusByDays/{noOfDays}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getDeptWiseServiceStatusByDays/{noOfDays}")
	@ApiOperation(value = "Get Department Wise Service Status By Duration", notes = "Get Department Wise Service Status By Duration", response = DeptOrSLAWiseServiceStatusEntity.class, responseContainer = "List")
	public List<DeptOrSLAWiseServiceStatusEntity> getDeptWiseServiceStatusByDays(
			@PathVariable("noOfDays") @PathParam("noOfDays") int noOfDays) {

		try {
			return citizenServicesDashboardGraphServiceImpl.getDeptWiseServiceStatus(noOfDays);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getGridDataByDaysAndSLA/{noOfDays}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getGridDataByDaysAndSLA/{noOfDays}")
	@ApiOperation(value = "Get Grid Data By Duration And SLA", notes = "Get Grid Data By Duration And SLA", response = DeptOrSLAWiseDataEntity.class, responseContainer = "List")
	public List<DeptOrSLAWiseDataEntity> getGridDataByDaysAndSLA(
			@PathVariable("noOfDays") @PathParam("noOfDays") int noOfDays,
			@RequestParam(name = "SLA") @QueryParam(value = "SLA") String sla) {

		try {
			return citizenServicesDashboardGraphServiceImpl.getGridDataByDaysAndSLA(noOfDays, sla);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getGridDataByDaysAndDept/{noOfDays}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getGridDataByDaysAndDept/{noOfDays}")
	@ApiOperation(value = "Get Grid Data By Duration And Department", notes = "Get Grid Data By Duration And Department", response = DeptOrSLAWiseDataEntity.class, responseContainer = "List")
	public List<DeptOrSLAWiseDataEntity> getGridDataByDaysAndDept(
			@PathVariable("noOfDays") @PathParam("noOfDays") int noOfDays,
			@RequestParam(name = "dept") @QueryParam(value = "dept") String dept) {

		try {
			return citizenServicesDashboardGraphServiceImpl.getGridDataByDaysAndDept(noOfDays, dept);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getYearWiseServiceStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getYearWiseServiceStatus")
	@ApiOperation(value = "Get Year Wise Service Status", notes = "Get Year Wise Service Status", response = FinancialDurationWiseServiceStatusEntity.class, responseContainer = "List")
	public List<FinancialDurationWiseServiceStatusEntity> getYearWiseServiceStatus() {

		try {
			return citizenServicesDashboardGraphServiceImpl.getYearWiseServiceStatus();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

}
