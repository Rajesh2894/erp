package com.abm.mainet.additionalservices.rest.ui.controller;

import javax.ws.rs.QueryParam;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.additionalservices.service.CFCSchedulingTrxService;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;

@RestController
@RequestMapping("/SchdlngCntrDet")
public class CFCSchedulingCounterDetController {

	@Autowired
	private CFCSchedulingTrxService cfcSchedulingTrxService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CFCSchedulingCounterDetController.class);

	@RequestMapping(value = "/getCntrDetail", method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getCounterDetByEmpId(@QueryParam("empId") Long empId,
			@QueryParam("orgId") Long orgId) {
		ResponseEntity<?> responseEntity = null;
		CFCSchedulingCounterDet cfcSchedulingCounterDet = null;
		try {
			cfcSchedulingCounterDet = cfcSchedulingTrxService.getScheduleDetByEmpiId(empId, orgId);
			if (cfcSchedulingCounterDet != null) {
				responseEntity = ResponseEntity.status(HttpStatus.OK).body(cfcSchedulingCounterDet);
				LOGGER.info("Scheduling Counter Detail for employee id"+ empId+" in json formate:"
						+ new ObjectMapper().writeValueAsString(cfcSchedulingCounterDet));

			} else {
				responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No Data Available Against Employee  id :" + empId);
				LOGGER.info("No Data Available Against Employee id : " + empId);
			}

		} catch (

		Exception ex) {
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
			LOGGER.error("Error While Fetching Noc buiding permission  detail: " + ex.getMessage(), ex);
		}
		return responseEntity;

	}
}
