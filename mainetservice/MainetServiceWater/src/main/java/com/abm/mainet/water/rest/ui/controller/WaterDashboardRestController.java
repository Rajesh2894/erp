package com.abm.mainet.water.rest.ui.controller;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.net.ssl.SSLContext;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jboss.dmr.JSONParser;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.security.JwtUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.abm.mainet.water.rest.dto.WaterDashboardMasterDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardRequestDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardRequestInfoDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardResponseDTO;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL),
		@HttpMethodConstraint(value = "GET", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/waterDashboardApi")
public class WaterDashboardRestController {
	@SuppressWarnings("unchecked")
	
	private static final Logger LOGGER = Logger.getLogger(WaterDashboardRestController.class);
	@RequestMapping(value = "/getDashboardDetails", method = RequestMethod.POST)
	public ResponseEntity<Object> getWaterDashboardDetails() {
		Organisation organisation = ApplicationSession.getInstance().getSuperUserOrganization();
		ResponseEntity<Object> response = null;
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("IA", "DDA", organisation);
		List<LocalDate> totalDates = new ArrayList<LocalDate>();
		if(lookUp.getOtherField().equals(MainetConstants.FlagY)) {
			LookUp frmDate = CommonMasterUtility.getValueFromPrefixLookUp("FRMD", "DDA", organisation);
			LookUp toDate = CommonMasterUtility.getValueFromPrefixLookUp("TD", "DDA", organisation);
			LocalDate startDate = LocalDate.parse(frmDate.getOtherField());
			LocalDate endDate = LocalDate.parse(toDate.getOtherField());
			long daysBetween = ChronoUnit.DAYS.between(startDate, endDate)+1;
			totalDates = LongStream.iterate(0,i -> i+1)
					.limit(daysBetween).mapToObj(i->startDate.plusDays(i))
					.collect(Collectors.toList());
			LOGGER.info("Water API Dates List:"+totalDates);
		}else {
			LocalDate curdate = LocalDate.now();
			totalDates.add(curdate);
		}
		if(!totalDates.isEmpty() || totalDates!=null) {
			for (LocalDate locD : totalDates) {
				LOGGER.info("Request data starts for Date:"+locD);
				String loc=locD.toString();
				SimpleDateFormat fdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat fdf2 = new SimpleDateFormat("dd/MM/yyyy");

				String localDate = null;
				try {
					localDate = fdf2.format(fdf1.parse(loc));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Date localDateNew=Utility.stringToDate(localDate);

				Date date1 = null;

				date1 = new java.sql.Date(localDateNew.getTime());

				WaterDashboardMasterDTO req = new WaterDashboardMasterDTO();
				WaterDashboardRequestInfoDTO reqInfo = new WaterDashboardRequestInfoDTO();
				List<Object> data = new ArrayList<>();
				RestTemplate restTemplate;
				HttpHeaders headers = new HttpHeaders();
				try {
					restTemplate = restTemplate();
					ResponseEntity<Object> response1 = getAcsessToken();
					ResponseEntity<?> response2 = getWaterDashboardResponseDTO(date1);
					List<WaterDashboardResponseDTO> data11 = (List<WaterDashboardResponseDTO>) response2.getBody();

					String access_token = null;
					Map<String,String> user_info = null;
					if (response1 != null) {
						Map<String, Object> mapResponse = null;
						mapResponse = (Map<String, Object>) response1.getBody();
						access_token = (String) mapResponse.get(MainetConstants.ACCESS_TOCKEN);
						user_info = (Map<String, String>) mapResponse.get("UserRequest");
						user_info.put("emailId", null);
						user_info.put("locale", null);
						user_info.put("permanentCity", null);
						LOGGER.info(" Token Value ---------------->" + access_token);
						LOGGER.info(" user info Value ---------------->" + user_info);

					}
					String url1 = ApplicationSession.getInstance().getMessage("wat.api.searchUrl");
					LOGGER.info(" url1 Value ---------------->" + url1);

					headers.setContentType(MediaType.APPLICATION_JSON);

					reqInfo.setAction(null);
					reqInfo.setApiId(ApplicationSession.getInstance().getMessage("wat.api.requestinfo.apiId"));
					reqInfo.setVer(null);
					reqInfo.setDid(null);
					reqInfo.setKey("");
					reqInfo.setMsgId("");
					reqInfo.setTs(null);
					reqInfo.setAuthToken(access_token);
					reqInfo.setUserInfo(user_info);
					req.setRequestInfo(reqInfo);
					if(null!=data11) 
						req.setDATA(data11);
					LOGGER.info("req-->"+req);

					String reqString = new JSONObject(req).toString();
					LOGGER.info("reqString-->"+reqString);

					HttpEntity<Object> request = new HttpEntity<Object>(req,headers);
					ObjectMapper obj = new ObjectMapper();
					response = restTemplate.exchange(url1, HttpMethod.POST, request, Object.class);
					LOGGER.info("Response from getWaterDashboardDetails ------>" + response);

				} catch (KeyManagementException e1) {
					// TODO Auto-generated catch block
					LOGGER.error("error at the time of API getWaterDashboardDetails call   " + e1);
					e1.printStackTrace();
				} catch (KeyStoreException e1) {
					// TODO Auto-generated catch block
					LOGGER.error("error at the time of API getWaterDashboardDetails call   " + e1);
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					LOGGER.error("error at the time of API getWaterDashboardDetails call   " + e1);
					e1.printStackTrace();
				}catch(Exception e) {
					LOGGER.error("error at the time of API getWaterDashboardDetails call   " + e);
					e.printStackTrace();
				}
				LOGGER.info("Water API RequestPayload received for date--->"+locD+"--->"+response);
			}
		}
		LOGGER.info("Water API Data Ingestion succesfully completed for the given dates.");
		return response;
	}

	@ResponseBody
	private ResponseEntity<?> getWaterDashboardResponseDTO(Date date1) {
		RestTemplate restTemplate;
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		WaterDashboardRequestDTO requestDto = new WaterDashboardRequestDTO();
		try {
			restTemplate = restTemplate();
			String url1 = ApplicationSession.getInstance().getMessage("wat.api.waterDashboard.Url");
			LOGGER.info(" url1 Value ---------------->" + url1);
			requestDto.setDate(date1);
			LOGGER.info(" Date Value ---------------->" + requestDto.getDate());

			responseEntity = RestClient.callRestTemplateClient(requestDto,
					url1);	 

			LOGGER.info("Response from getWaterDashboardResponseDTO ------>" + responseEntity);
			return  responseEntity;
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return responseEntity;
	}

	@RequestMapping(value = "/getAuthToken", method = RequestMethod.POST)
	public ResponseEntity<Object> getAcsessToken() {
		LOGGER.info(" Start getAcsessToken ------>");
		HttpHeaders swmHeaders = new HttpHeaders();
		RestTemplate restTemplate;
		ResponseEntity<Object> response = null;
		
		try {
			restTemplate = restTemplate();
			swmHeaders.add("Authorization", ApplicationSession.getInstance().getMessage("wat.authorization"));
			LOGGER.info(" Authorization Key ------>"+ApplicationSession.getInstance().getMessage("wat.authorization"));
			
			String url = ApplicationSession.getInstance().getMessage("wat.api.tokenUrl");

			swmHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("username", ApplicationSession.getInstance().getMessage("wat.username"));
			map.add("password", ApplicationSession.getInstance().getMessage("wat.password"));
			map.add("scope", ApplicationSession.getInstance().getMessage("wat.scope"));
			map.add("grant_type", ApplicationSession.getInstance().getMessage("wat.grantType"));
			map.add("tenantId", ApplicationSession.getInstance().getMessage("wat.tenantId"));
			map.add("userType", ApplicationSession.getInstance().getMessage("wat.userType"));
			LOGGER.info("  getAcsessToken url ------>" + url);
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
					swmHeaders);
			
			response = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);

			LOGGER.info("Response from getAuthToken ------>" + response);
			return response;
			
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			LOGGER.error("error at the time of API call   " + e1);
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			LOGGER.error("error at the time of API call   " + e1);
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			LOGGER.error("error at the time of API call   " + e1);
			e1.printStackTrace();
		}	
		
		return response;
	}

	public RestTemplate restTemplate() 
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(csf)
				.build();

		HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
	}
}
