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

import com.abm.mainet.common.dashboard.domain.skdcl.LicenseCntDayWiseEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseCntEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseDataEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseDaysWiseDetEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseIssuedCntAndRevenueEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseIssuedDetEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LocAndCategoryWiseLicenseCntEntity;
import com.abm.mainet.common.dashboard.service.LicenseDashboardGraphServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/licenseDashboardGraph")
@Produces("application/json")
@WebService
@Api(value = "/licenseDashboardGraph")
@Path("/licenseDashboardGraph")
public class LicenseDashboardGraphController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicenseDashboardGraphController.class);

	@Autowired
	LicenseDashboardGraphServiceImpl licenseDashboardGraphServiceImpl;

	@RequestMapping(value = "/getZoneAndCategoryWiseLicenseCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getZoneAndCategoryWiseLicenseCount")
	@ApiOperation(value = "Get Zone And Category Wise License Count", notes = "Get Zone And Category Wise License Count", response = LocAndCategoryWiseLicenseCntEntity.class, responseContainer = "List")
	public List<LocAndCategoryWiseLicenseCntEntity> getZoneAndCategoryWiseLicenseCount() {

		try {
			return licenseDashboardGraphServiceImpl.getZoneAndCategoryWiseLicenseCount();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getYearWiseLicenseCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getYearWiseLicenseCount")
	@ApiOperation(value = "Get Year Wise License Count", notes = "Get Year Wise License Count", response = LicenseCntEntity.class, responseContainer = "List")
	public List<LicenseCntEntity> getYearWiseLicenseCount() {

		try {
			return licenseDashboardGraphServiceImpl.getYearWiseLicenseCount();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getLicenseCountByDays/{noOfDays}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getLicenseCountByDays/{noOfDays}")
	@ApiOperation(value = "Get License Count By Duration", notes = "Get License Count By Duration", response = LicenseCntDayWiseEntity.class, responseContainer = "List")
	public List<LicenseCntDayWiseEntity> getLicenseCountByDays(
			@PathVariable("noOfDays") @PathParam("noOfDays") int noOfDays) {

		try {
			return licenseDashboardGraphServiceImpl.getLicenseCountByDays(noOfDays);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getLicenseDataByDaysAndType/{noOfDays}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getLicenseDataByDaysAndType/{noOfDays}")
	@ApiOperation(value = "Get License Data By Duration And License Type", notes = "Get License Data By Duration And License Type", response = LicenseDataEntity.class, responseContainer = "List")
	public List<LicenseDataEntity> getLicenseDataByDaysAndType(
			@PathVariable("noOfDays") @PathParam("noOfDays") int noOfDays,
			@RequestParam(name = "type") @QueryParam(value = "type") String type) {

		try {
			return licenseDashboardGraphServiceImpl.getLicenseDataByDaysAndType(noOfDays, type);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getIssuedLicenseDetByOrgId/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getIssuedLicenseDetByOrgId/{orgId}")
	@ApiOperation(value = "Get Issued License Data By OrgId", notes = "Get Issued License Data By OrgId", response = LicenseIssuedDetEntity.class, responseContainer = "List")
	public List<LicenseIssuedDetEntity> getIssuedLicenseDetByOrgId(
			@PathVariable("orgId") @PathParam("orgId") long orgId) {

		try {
			return licenseDashboardGraphServiceImpl.getIssuedLicenseDetByOrgId(orgId);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getYearWiseLicenseData/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getYearWiseLicenseData/{orgId}")
	@ApiOperation(value = "Get Year Wise License Data By OrgId", notes = "Get Year Wise License Data By OrgId", response = LicenseDaysWiseDetEntity.class, responseContainer = "List")
	public List<LicenseDaysWiseDetEntity> getYearWiseLicenseData(
			@PathVariable("orgId") @PathParam("orgId") long orgId) {

		try {
			return licenseDashboardGraphServiceImpl.getYearWiseLicenseData(orgId);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}
	
	@RequestMapping(value = "/getHalfYearWiseLicenseData/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getHalfYearWiseLicenseData/{orgId}")
	@ApiOperation(value = "Get Half Year Wise License Data By OrgId", notes = "Get Year Wise License Data By OrgId", response = LicenseDaysWiseDetEntity.class, responseContainer = "List")
	public List<LicenseDaysWiseDetEntity> getHalfYearWiseLicenseData(
			@PathVariable("orgId") @PathParam("orgId") long orgId) {

		try {
			return licenseDashboardGraphServiceImpl.getHalfYearWiseLicenseData(orgId);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}
	@RequestMapping(value = "/getQuarterWiseLicenseData/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getQuarterWiseLicenseData/{orgId}")
	@ApiOperation(value = "Get Quarter Wise License Data By OrgId", notes = "Get Quarter Wise License Data By OrgId", response = LicenseDaysWiseDetEntity.class, responseContainer = "List")
	public List<LicenseDaysWiseDetEntity> getQuarterWiseLicenseData(
			@PathVariable("orgId") @PathParam("orgId") long orgId) {

		try {
			return licenseDashboardGraphServiceImpl.getQuarterWiseLicenseData(orgId);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}
	
	@RequestMapping(value = "/getsevenDayrWiseLicenseData/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getsevenDayrWiseLicenseData/{orgId}")
	@ApiOperation(value = "Get Seven Day Wise License Data By OrgId", notes = "Get Seven day Wise License Data By OrgId", response = LicenseDaysWiseDetEntity.class, responseContainer = "List")
	public List<LicenseDaysWiseDetEntity> getsevenDayrWiseLicenseData(
			@PathVariable("orgId") @PathParam("orgId") long orgId) {

		try {
			return licenseDashboardGraphServiceImpl.getsevenDayrWiseLicenseData(orgId);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}
	@RequestMapping(value = "/getLicenceULBReport/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getLicenceULBReport/{orgId}")
	@ApiOperation(value = "Get License ULB Wise License Data By OrgId", notes = "Get License ULB Data By OrgId", response = LicenseDaysWiseDetEntity.class, responseContainer = "List")
	public List<LicenseIssuedDetEntity> getLicenceULBReport(
			@PathVariable("orgId") @PathParam("orgId") long orgId) {

		try {
			return licenseDashboardGraphServiceImpl.getLicenceULBReport(orgId);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}
	@RequestMapping(value = "/getTotalIssuedlicenseTotalrevenue/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getTotalIssuedlicenseTotalrevenue/{orgId}")
	@ApiOperation(value = "Get toatal issue and revenueData By OrgId", notes = "Get toatal issue and revenueData By OrgId", response = LicenseIssuedCntAndRevenueEntity.class, responseContainer = "List")
	public List<LicenseIssuedCntAndRevenueEntity> getTotalIssuedlicenseTotalrevenue(
			@PathVariable("orgId") @PathParam("orgId") long orgId) {

		try {
			return licenseDashboardGraphServiceImpl.getTotalIssuedlicenseTotalrevenue(orgId);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}
}
