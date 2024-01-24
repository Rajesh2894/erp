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

import org.apache.log4j.Logger;
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

import com.abm.mainet.common.dashboard.domain.YearWiseGrievanceGraphEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.BndApplicationDtlEntity;
import com.abm.mainet.common.dashboard.service.IBNDDashboardGraphService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/bndCitizenDashboardGraph")
@Produces("application/json")
@WebService
@Api(value = "/bndCitizenDashboardGraph")
@Path("/bndCitizenDashboardGraph")
public class BNDDashboardGraphController {

	private static final Logger LOG = Logger.getLogger(BNDDashboardGraphController.class);

	@Autowired
	private IBNDDashboardGraphService bndDashboardGraphService;

	@RequestMapping(value = "/getYearwiseBNDStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getYearwiseBNDStatus")
	@ApiOperation(value = "Get Year Wise BND Status", notes = "Get Year Wise BND Status", response = YearWiseGrievanceGraphEntity.class, responseContainer = "List")
	public List<YearWiseGrievanceGraphEntity> getYearwiseBNDStatus() {

		try {
			return bndDashboardGraphService.getYearWiseGraphEntityList();
		} catch (final Exception ex) {
			LOG.error("problem occurred while request for getting citizen app data:", ex);
		}
		return null;

	}

	@RequestMapping(value = "/getBNDStatusByYearwiseAndType/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getBNDStatusByYearwiseAndType/{type}")
	@ApiOperation(value = "Get BND Status By Duration And Type", notes = "Get BND Status By Duration And Type", response = YearWiseGrievanceGraphEntity.class, responseContainer = "List")
	public List<YearWiseGrievanceGraphEntity> getBNDDataStatusByYearAndType(
			@PathVariable("type") @PathParam("type") String type,
			@RequestParam("noOfDays") @QueryParam(value = "noOfDays") Integer noOfDays) {

		try {
			return bndDashboardGraphService.getBNDDataStatusByYearAndType(type, noOfDays);
		} catch (final Exception ex) {
			LOG.error("problem occurred while request for getting citizen app data:", ex);
		}
		return null;

	}

	@RequestMapping(value = "/getBNDApplicationData/{noOfDays}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getBNDApplicationData/{noOfDays}")
	@ApiOperation(value = "Get BND Application Data By Duration, Type And Status", notes = "Get BND Application Data By Duration, Type And Status", response = BndApplicationDtlEntity.class, responseContainer = "List")
	public List<BndApplicationDtlEntity> getBNDDataListByStatusAndType(
			@PathVariable("noOfDays") @PathParam("noOfDays") Integer noOfDays,
			@RequestParam("type") @QueryParam(value = "type") String type,
			@RequestParam("status") @QueryParam(value = "status") String status) {

		try {
			return bndDashboardGraphService.getBNDDataListByStatusAndType(type, noOfDays, status);
		} catch (final Exception ex) {
			LOG.error("problem occurred while request for getting citizen app data:", ex);
		}
		return null;

	}
}
