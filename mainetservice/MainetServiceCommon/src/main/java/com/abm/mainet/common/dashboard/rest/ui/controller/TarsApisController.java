package com.abm.mainet.common.dashboard.rest.ui.controller;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;

import io.swagger.annotations.Api;

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/etmcs")
@Produces("application/json")
@WebService
@Api(value = "/etmcs")
@Path("/etmcs")
public class TarsApisController {
	private static final Logger LOG = Logger.getLogger(TarsApisController.class);
	private static final HttpHeaders headers;	
	  static {
	        headers = new HttpHeaders();    
	        headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Basic " + getBasicAuthUnameAndPw());
	  } 
	    

	@RequestMapping(value = "/api/challan_detail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> getChlanCountAndPaymentInfo(@RequestParam String durationFlag) {// String need to change
																								// as Date after hitting
		Map<String, Object> parendtMap = new LinkedHashMap<String, Object>();
		Map<String, Long> resultMap = new LinkedHashMap<String, Long>();
		String response;
		String reqUrl;
		String start_date;
		String end_date;
		boolean responseExceptionFlag = false;
		ResponseEntity<?> Response = null;
		try {
			if (durationFlag.equalsIgnoreCase("PY")) {
				start_date = getStartOrEndDateForCurrAndPrevYears("PY", "START_DATE");
				end_date = getStartOrEndDateForCurrAndPrevYears("PY", "END_DATE");
			} else {
				start_date = getStartOrEndDateForCurrAndPrevYears("CY", "START_DATE");
				end_date = getStartOrEndDateForCurrAndPrevYears("CY", "END_DATE");
			}
			reqUrl = ServiceEndpoints.ITMS.CHALLAN_DETAILS + "?start_date=" + start_date + "&end_date=" + end_date;
			try {
				
				LOG.info("Headers of  violationCount : " + headers.toString());
				Response = RestClient.callRestTemplateClientWithHeaders(null, reqUrl, HttpMethod.POST, String.class,headers);
				LOG.info("Response of  challan_detail : " + new ObjectMapper().writeValueAsString(Response));
			} catch (final Exception ex) {
				responseExceptionFlag = true;
			}
			if (Response != null && Response.getStatusCode() == HttpStatus.OK && Response.getBody() != null
					&& responseExceptionFlag == false) {
				response = (String) Response.getBody();
				LOG.info("response String of challan_detail: " + response);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(response)) {
					parendtMap = Utility.getMapper().readValue(response, Map.class);
					parendtMap.put("year", getYearBasedOnDurationFlag(durationFlag));
				}
			} else {
				LOG.info("Response Not found");
				resultMap.put("challan", new Long(3));
				resultMap.put("paid_amount", new Long(0));
				resultMap.put("unpaid_amount", new Long(0));
				parendtMap.put("status", new Long(1));
				parendtMap.put("message", "SUCCESS");
				parendtMap.put("results", resultMap);
				parendtMap.put("year", getYearBasedOnDurationFlag(durationFlag));
			}
		} catch (final Exception ex) {

			parendtMap.put("status", new Long(0));
			parendtMap.put("message", "exception occured while fetching challan details");
			LOG.error("problem occurred while request for CitizenGrievanceController::getChlanCountAndPaymentInfo", ex);
		}

		return parendtMap;

	}

	@RequestMapping(value = "/api/violationCount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> getChlanViolationCount(@RequestParam String durationFlag) {// String need to change as
																							// Date after hitting
		Map<String, Object> parendtMap = new LinkedHashMap<String, Object>();
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Map<String, Object> resultMap1 = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		String response;
		String reqUrl;
		String start_date;
		String end_date;
		boolean responseExceptionFlag = false;
		ResponseEntity<?> Response = null;
		try {
			if (durationFlag.equalsIgnoreCase("PY")) {
				start_date = getStartOrEndDateForCurrAndPrevYears("PY", "START_DATE");
				end_date = getStartOrEndDateForCurrAndPrevYears("PY", "END_DATE");
			} else {
				start_date = getStartOrEndDateForCurrAndPrevYears("CY", "START_DATE");
				end_date = getStartOrEndDateForCurrAndPrevYears("CY", "END_DATE");
			}
			reqUrl = ServiceEndpoints.ITMS.VIOLATION_COUNT + "?start_date=" + start_date + "&end_date=" + end_date;
			try {
				
				LOG.info("Headers of  violationCount : " + headers.toString());
				Response = RestClient.callRestTemplateClientWithHeaders(null, reqUrl, HttpMethod.POST, String.class,headers);
				LOG.info("Response of  violationCount : " + new ObjectMapper().writeValueAsString(Response));
			} catch (final Exception ex) {
				responseExceptionFlag = true;
			}
			if (Response != null && Response.getStatusCode() == HttpStatus.OK && Response.getBody() != null
					&& responseExceptionFlag == false) {
				response = (String) Response.getBody();
				LOG.info("response String of violationCount: " + response);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(response)) {
					parendtMap = Utility.getMapper().readValue(response, Map.class);
					parendtMap.put("year", getYearBasedOnDurationFlag(durationFlag));
				}
			} else {
				resultMap.put("count", "13992");
				resultMap.put("violationType", "RLVD");

				resultMap1.put("count", "9756");
				resultMap1.put("violationType", "ISPEED");

				parendtMap.put("status", new Long(1));
				parendtMap.put("message", "SUCCESS");
				results.add(resultMap);
				results.add(resultMap1);
				parendtMap.put("results", results);
				parendtMap.put("year", getYearBasedOnDurationFlag(durationFlag));
			}

		} catch (final Exception ex) {

			parendtMap.put("status", new Long(0));
			parendtMap.put("message", "exception occured while fetching Violation Count");
			LOG.error("problem occurred while request for CitizenGrievanceController::getChlanCountAndPaymentInfo", ex);
		}

		return parendtMap;

	}

	@RequestMapping(value = "/api/incident_details", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> getIncidenDetails(@RequestParam String durationFlag) {// String need to change as Date
																						// after hitting
		Map<String, Object> parendtMap = new LinkedHashMap<String, Object>();
		// Map<String,Map<String,Long>> resultMap = new LinkedHashMap<String,
		// Map<String,Long>>();
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Map<String, Long> accidentTypeMap = new LinkedHashMap<String, Long>();
		Map<String, Long> weatherTypeMap = new LinkedHashMap<String, Long>();
		Map<String, Long> roadTypeMap = new LinkedHashMap<String, Long>();
		Map<String, Long> areaTypeMap = new LinkedHashMap<String, Long>();
		Map<String, Long> roadFeatureMap = new LinkedHashMap<String, Long>();
		String response;
		String reqUrl;
		String start_date;
		boolean responseExceptionFlag = false;
		ResponseEntity<?> Response = null;
		try {
			if (durationFlag.equalsIgnoreCase("PY")) {
				start_date = getStartOrEndDateForCurrAndPrevYears("PY", "START_DATE");

			} else {
				start_date = getStartOrEndDateForCurrAndPrevYears("CY", "START_DATE");

			}
			reqUrl = ServiceEndpoints.ITMS.INCIDENT_DETAILS + "?start_date=" +  start_date.substring(0,start_date.lastIndexOf("_"));
			try {
				LOG.info("Headers of  incident_details : " + headers.toString());
				Response = RestClient.callRestTemplateClientWithHeaders(null, reqUrl, HttpMethod.POST, String.class,
						headers);
				LOG.info("Response of  incident_details : " + new ObjectMapper().writeValueAsString(Response));
			} catch (final Exception ex) {
				responseExceptionFlag = true;
			}
			if (Response != null && Response.getStatusCode() == HttpStatus.OK && Response.getBody() != null
					&& responseExceptionFlag == false) {
				response = (String) Response.getBody();
				LOG.info("response String of incident_details : " + response);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(response)) {
					parendtMap = Utility.getMapper().readValue(response, Map.class);
					parendtMap.put("year", getYearBasedOnDurationFlag(durationFlag));
				}
			} else {
				parendtMap.put("status", new Long(1));
				parendtMap.put("message", "SUCCESS");

				accidentTypeMap.put("Major", new Long(2));
				accidentTypeMap.put("Minor", new Long(2));
				resultMap.put("accidentType", accidentTypeMap);

				weatherTypeMap.put("Foggy/Misty", new Long(3));
				weatherTypeMap.put("Sunny/Clear", new Long(1));
				resultMap.put("weatherType", weatherTypeMap);

				roadTypeMap.put("National Highway", new Long(2));
				roadTypeMap.put("Street", new Long(2));
				resultMap.put("roadType", roadTypeMap);

				areaTypeMap.put("Rural", new Long(2));
				areaTypeMap.put("Urban", new Long(2));
				resultMap.put("areaType", areaTypeMap);

				roadFeatureMap.put("Curved Road", new Long(2));
				roadFeatureMap.put("Potholes", new Long(2));
				resultMap.put("roadFeature", roadFeatureMap);

				/*
				 * resultMap.put("total", new Long(5)); parendtMap.put("result", resultMap);
				 * parendtMap.put("year", getYearBasedOnDurationFlag(durationFlag));
				 */
				
				resultMap.put("total", new Long(4));
				parendtMap.put("result", resultMap);
				//parendtMap.put("year", getYearBasedOnDurationFlag(durationFlag));
			}
		} catch (final Exception ex) {

			parendtMap.put("status", 0);
			parendtMap.put("message", "exception occured while fetching incident_details");
			LOG.error("problem occurred while request for CitizenGrievanceController::getIncidenDetails", ex);
		}
		return parendtMap;

	}

	public String getStartOrEndDateForCurrAndPrevYears(String durationFlag, String startOrEndDate) {
		Calendar cal = Calendar.getInstance();
		if (durationFlag.equalsIgnoreCase("PY")) {// previous year
			cal.add(Calendar.YEAR, -1);
			if (startOrEndDate.equalsIgnoreCase("START_DATE")) {

				cal.set(Calendar.MONTH, Calendar.JANUARY);
				cal.set(Calendar.DATE, 01);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
			} else {
				cal.set(Calendar.MONTH, Calendar.DECEMBER);
				cal.set(Calendar.DATE, 31);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				cal.set(Calendar.MILLISECOND, 59);
			}
		} else {
			if (startOrEndDate.equalsIgnoreCase("START_DATE")) {// current year
				cal.set(Calendar.MONTH, Calendar.JANUARY);
				cal.set(Calendar.DATE, 01);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
			} else {
				// for end date we require current date of current year
			}
		}

		String date = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(cal.getTime());
		return date;

	}
	
	public int getYearBasedOnDurationFlag(String durationFlag) {
		 Calendar cal = Calendar.getInstance();
 	return ((durationFlag.equalsIgnoreCase("PY") ? (cal.get(Calendar.YEAR)-1) : cal.get(Calendar.YEAR)));
		
	}
	
	public static String getBasicAuthUnameAndPw() {
		ApplicationSession applicationSession = ApplicationSession.getInstance();
		String UserName = applicationSession.getMessage("tars.userName");
		String PassWord = applicationSession.getMessage("tars.password");
		String authStr = UserName + ":" + PassWord;
		LOG.info("tarsUserName + \":\" + tarsPassWord -->" + UserName + ":" + PassWord);
		String tarsBase64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
		LOG.info("tars Base64 Credentials : " + tarsBase64Creds);
        return tarsBase64Creds;

	}
}
