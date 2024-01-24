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

import com.abm.mainet.common.dashboard.domain.skdcl.AccountAmountTotalEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountClassifDashboardCntEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountCollectionEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountFundStatusEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountRatioDashboardCntEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountReceiptsAndPaymentsEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountTransCntDayWiseEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountTransactionCntEntity;
import com.abm.mainet.common.dashboard.service.AccountDashboardGraphServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/accountDashboardGraph")
@Produces("application/json")
@WebService
@Api(value = "/accountDashboardGraph")
@Path("/accountDashboardGraph")
public class AccountDashboardGraphController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountDashboardGraphController.class);

	@Autowired
	AccountDashboardGraphServiceImpl accountDashboardGraphServiceImpl;

	@RequestMapping(value = "/getYearWiseOnlineTransactionCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getYearWiseOnlineTransactionCount")
	@ApiOperation(value = "Get Year Wise Online Transaction Count", notes = "Get Year Wise Online Transaction Count", response = AccountTransactionCntEntity.class, responseContainer = "List")
	public List<AccountTransactionCntEntity> getYearWiseOnlineTransactionCount() {

		try {
			return accountDashboardGraphServiceImpl.getYearWiseOnlineTransactionCount();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getYearWiseCashTransactionCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getYearWiseCashTransactionCount")
	@ApiOperation(value = "Get Year Wise Cash Transaction Count", notes = "Get Year Wise Cash Transaction Count", response = AccountTransactionCntEntity.class, responseContainer = "List")
	public List<AccountTransactionCntEntity> getYearWiseCashTransactionCount() {

		try {
			return accountDashboardGraphServiceImpl.getYearWiseCashTransactionCount();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getYearWiseEstimatedBudget", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getYearWiseEstimatedBudget")
	@ApiOperation(value = "Get Year Wise Estimated Budget", notes = "Get Year Wise Estimated Budget", response = AccountAmountTotalEntity.class, responseContainer = "List")
	public List<AccountAmountTotalEntity> getYearWiseEstimatedBudget() {

		try {
			return accountDashboardGraphServiceImpl.getYearWiseEstimatedBudget();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getYearWiseExpenditureAmt", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getYearWiseExpenditureAmt")
	@ApiOperation(value = "Get Year Wise Expenditure Amount", notes = "Get Year Wise Expenditure Amount", response = AccountAmountTotalEntity.class, responseContainer = "List")
	public List<AccountAmountTotalEntity> getYearWiseExpenditureAmt() {

		try {
			return accountDashboardGraphServiceImpl.getYearWiseExpenditureAmt();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getDeptWiseCollectionForModes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getDeptWiseCollectionForModes")
	@ApiOperation(value = "Get Department Wise Collection For Modes", notes = "Get Department Wise Collection For Modes", response = AccountCollectionEntity.class, responseContainer = "List")
	public List<AccountCollectionEntity> getDeptWiseCollectionForModes() {

		try {
			return accountDashboardGraphServiceImpl.getDeptWiseCollectionForModes();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getfunctionWiseReceiptsAndPaymentCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getfunctionWiseReceiptsAndPaymentCount")
	@ApiOperation(value = "Get Function Wise Receipts And Payment Count", notes = "Get Function Wise Receipts And Payment Count", response = AccountReceiptsAndPaymentsEntity.class, responseContainer = "List")
	public List<AccountReceiptsAndPaymentsEntity> getfunctionWiseReceiptsAndPaymentCount() {

		try {
			return accountDashboardGraphServiceImpl.getfunctionWiseReceiptsAndPaymentCount();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getZoneWiseReceiptsAndPaymentCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getZoneWiseReceiptsAndPaymentCount")
	@ApiOperation(value = "Get Zone Wise Receipts And Payments Count", notes = "Get Zone Wise Receipts And Payments Count", response = AccountReceiptsAndPaymentsEntity.class, responseContainer = "List")
	public List<AccountReceiptsAndPaymentsEntity> getZoneWiseReceiptsAndPaymentCount() {

		try {
			return accountDashboardGraphServiceImpl.getZoneWiseReceiptsAndPaymentCount();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getDashboardCountByClassificationType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getDashboardCountByClassificationType")
	@ApiOperation(value = "Get Dashboard Count By Classification Type", notes = "Get Dashboard Count By Classification Type", response = AccountClassifDashboardCntEntity.class, responseContainer = "List")
	public List<AccountClassifDashboardCntEntity> getDashboardCountByClassificationType(
			@RequestParam(name = "type") @QueryParam(value = "type") String type) {

		try {
			return accountDashboardGraphServiceImpl.getDashboardCountByClassificationType(type);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getDashboardCountByRatioType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getDashboardCountByRatioType")
	@ApiOperation(value = "Get Dashboard Count By Ratio Type", notes = "Get Dashboard Count By Ratio Type", response = AccountRatioDashboardCntEntity.class, responseContainer = "List")
	public List<AccountRatioDashboardCntEntity> getDashboardCountByRatioType(
			@RequestParam(name = "type") @QueryParam(value = "type") String type) {

		try {
			return accountDashboardGraphServiceImpl.getDashboardCountByRatioType(type);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getDashboardCountByFundStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getDashboardCountByFundStatus")
	@ApiOperation(value = "Get Dashboard Count By Fund Status", notes = "Get Dashboard Count By Fund Status", response = AccountFundStatusEntity.class, responseContainer = "List")
	public List<AccountFundStatusEntity> getDashboardCountByFundStatus() {

		try {
			return accountDashboardGraphServiceImpl.getDashboardCountByFundStatus();
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}

	@RequestMapping(value = "/getTransactionCountByDays/{noOfDays}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/getTransactionCountByDays/{noOfDays}")
	@ApiOperation(value = "Get Transaction Count By Duration", notes = "Get Transaction Count By Duration", response = AccountTransCntDayWiseEntity.class, responseContainer = "List")
	public List<AccountTransCntDayWiseEntity> getTransactionCountByDays(
			@PathVariable("noOfDays") @PathParam("noOfDays") int noOfDays) {

		try {
			return accountDashboardGraphServiceImpl.getTransactionCountByDays(noOfDays);
		} catch (final Exception ex) {
			LOGGER.error("problem occurred while request for getting citizen app data:", ex);
		}

		return null;

	}
}
