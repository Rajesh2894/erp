package com.abm.mainet.common.rest.ui.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XML;
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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.EmployeeBean;
import com.abm.mainet.common.dto.JWTRequestDto;
import com.abm.mainet.common.dto.PortalServiceDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.ConsumerBillViewDTO;
import com.abm.mainet.common.integration.dto.PropertyTaxMasterDTO;
import com.abm.mainet.common.integration.dto.RequestInfo;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.security.JwtUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Common Rest Controller for Service Master related Services
 * 
 * @author vishwajeet.kumar
 * @since 19 January 2018
 *
 */
@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL),
		@HttpMethodConstraint(value = "GET", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/servicePortal")
public class PortalServiceRestController {

	private static final Logger LOGGER = Logger.getLogger(PortalServiceRestController.class);
	@Autowired
	private IServiceMasterService masterService;

	@Autowired
	private IEmployeeService iEmployeeService;

	@Autowired
	JwtUtil jwtUtil;

	@RequestMapping(value = "/saveService", method = RequestMethod.POST, produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> createService(@RequestBody PortalServiceDTO serviceDto) {
		ResponseEntity<?> responseEntity = null;
		try {
			PortalService portalService = new PortalService();
			final int langId = serviceDto.getLangId().intValue();
			Employee employee = new Employee();
			employee.setEmpId(serviceDto.getUserId());

			BeanUtils.copyProperties(serviceDto, portalService);
			portalService.setLangId(langId);
			portalService.setUserId(employee);
			PortalService service = masterService.createRestPortalService(portalService);
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(service);
		} catch (Exception exception) {
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
		}
		return responseEntity;

	}

	@RequestMapping(value = "/updateService", method = RequestMethod.POST, produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> updatePortalService(@RequestBody PortalServiceDTO serviceDTO) {
		ResponseEntity<?> responseEntity = null;
		PortalService service = masterService.findShortCodeByOrgId(serviceDTO.getShortName().toUpperCase(),
				serviceDTO.getServiceOrgId());
		PortalService portalService = new PortalService();

		try {
			if (service != null) {
				service.setUpdatedDate(new Date());
				Employee employee = new Employee();
				employee.setEmpId(serviceDTO.getUpdatedBy());
				service.setUpdatedBy(employee);
				service.setIsDeleted(serviceDTO.getIsDeleted());
				masterService.createRestPortalService(service);
			} else {
				BeanUtils.copyProperties(serviceDTO, portalService);
				masterService.createRestPortalService(portalService);
			}
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(portalService);
		} catch (Exception exception) {
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
		}

		return responseEntity;

	}

	// For property Tax Integration
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "searchPropertyTax", method = RequestMethod.POST)
	public ResponseEntity<Object> getPropertyTaxDetails(@QueryParam("consumerCode") String consumerCode,
			@QueryParam("tenantId") String tenantId, @QueryParam("businessService") String businessService) {

		PropertyTaxMasterDTO req = new PropertyTaxMasterDTO();
		RequestInfo reqInfo = new RequestInfo();
		RestTemplate restTemplate;
		ResponseEntity<Object> response = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			restTemplate = restTemplate();
			ResponseEntity<Object> response1 = getAcsessToken();
			System.out.println(response1);
			
			String access_token = null;
			if (response1 != null) {
				Map<String, Object> mapResponse = null;
				mapResponse = (Map<String, Object>) response1.getBody();
				access_token = (String) mapResponse.get(MainetConstants.ACCESS_TOCKEN);
				LOGGER.info(" Token Value ---------------->" + access_token);

			}
			String url1 = ApplicationSession.getInstance().getMessage("prop.api.searchUrl") + "consumerCode=" + consumerCode
					+ "&tenantId=" + tenantId + "&businessService=" + businessService;
			LOGGER.info(" url1 Value ---------------->" + url1);
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			reqInfo.setAction("");
			reqInfo.setApiId(ApplicationSession.getInstance().getMessage("prop.api.requestinfo.apiId"));
			reqInfo.setVer(ApplicationSession.getInstance().getMessage("prop.api.requestinfo.ver"));
			reqInfo.setDid(ApplicationSession.getInstance().getMessage("prop.api.requestinfo.did"));
			reqInfo.setKey("");
			reqInfo.setMsgId(ApplicationSession.getInstance().getMessage("prop.api.requestinfo.msgId"));
			reqInfo.setRequesterId("");
			reqInfo.setAuthToken(access_token);
			
			req.setRequestInfo(reqInfo);
			
			HttpEntity<Object> request = new HttpEntity<Object>(req,headers);
			
			response = restTemplate.exchange(url1, HttpMethod.POST, request, Object.class);
			
			LOGGER.info("Response from getPropertyTaxDetails ------>" + response);
			return response;
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			LOGGER.error("error at the time of API getPropertyTaxDetails call   " + e1);
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			LOGGER.error("error at the time of API getPropertyTaxDetails call   " + e1);
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			LOGGER.error("error at the time of API getPropertyTaxDetails call   " + e1);
			e1.printStackTrace();
		}
		
		return response;
	}

	@RequestMapping(value = "getAuthToken", method = RequestMethod.POST)
	public ResponseEntity<Object> getAcsessToken() {
		LOGGER.info(" Start getAcsessToken ------>");
		HttpHeaders swmHeaders = new HttpHeaders();
		RestTemplate restTemplate;
		ResponseEntity<Object> response = null;
		
		try {
			restTemplate = restTemplate();
			swmHeaders.add("Authorization", ApplicationSession.getInstance().getMessage("prop.authorization"));
			LOGGER.info(" Authorization Key ------>"+ApplicationSession.getInstance().getMessage("prop.authorization"));
			
			String url = ApplicationSession.getInstance().getMessage("prop.api.tokenUrl");

			swmHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		

			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("username", ApplicationSession.getInstance().getMessage("prop.username"));
			map.add("password", ApplicationSession.getInstance().getMessage("prop.password"));
			map.add("scope", ApplicationSession.getInstance().getMessage("prop.scope"));
			map.add("grant_type", ApplicationSession.getInstance().getMessage("prop.grantType"));
			map.add("tenantId", ApplicationSession.getInstance().getMessage("prop.tenantId"));
			map.add("userType", ApplicationSession.getInstance().getMessage("prop.userType"));
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

	@RequestMapping(value = "getUserDetail", method = RequestMethod.POST)
	public List<EmployeeBean> getUserDetail() {
		List<EmployeeBean> emp = new ArrayList<EmployeeBean>();
		try {

			emp = iEmployeeService.getAllEmployeeDetails();

		} catch (Exception e) {
			LOGGER.error(" Exception occured at the time of fetching all employee details ---------------->");
		}
		return emp;
	}

	@RequestMapping(value = "/getJwtToken/{orgId}/{userId}", method = RequestMethod.POST, produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public String getJwtToken(@PathVariable("orgId") @PathParam("orgId") Long orgId,
			@PathVariable("userId") @PathParam("userId") Long userId) {
		return jwtUtil.createJWToken(orgId, userId);
	}

	@RequestMapping(value = "parseJWTToken", method = RequestMethod.POST, produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public EmployeeBean parseToken(@RequestBody JWTRequestDto jWTRequestDto) {
		return iEmployeeService.findById(JwtUtil.getInstance().parseToken(jWTRequestDto.getToken()).getEmpId());
	}
	
	@RequestMapping(value = "/getDoonWaterDetails", method = RequestMethod.POST)
	public ConsumerBillViewDTO getDoonWaterDetail(@QueryParam("connectionNo") String connectionNo){

		LOGGER.info("getDoonWaterDetail started..");
		ConsumerBillViewDTO consumerBillViewDTO = new ConsumerBillViewDTO();
		String waterData[] = getDoonWaterDetails(connectionNo).split("\\|");
	
		if(waterData!=null && waterData.length>1){
			if(waterData[7].equalsIgnoreCase("Bill")){
				consumerBillViewDTO.setStatus(MainetConstants.SUCCESS);
				consumerBillViewDTO.setConsumerName(waterData[1]);
				consumerBillViewDTO.setConAddress(waterData[2]);
				consumerBillViewDTO.setConsumerCode(waterData[3]);		 
				consumerBillViewDTO.setWaterBillNo(waterData[4]);
				consumerBillViewDTO.setWaterBillAmount(waterData[5]);
				consumerBillViewDTO.setWaterPaymentDueDate(waterData[6]);
			}
			else{
				consumerBillViewDTO.setStatus(MainetConstants.FAIL);
			}
		}
		else{
			consumerBillViewDTO.setStatus(MainetConstants.FAIL);
		}
		 
		LOGGER.info("getDoonWaterDetail end response -->"+consumerBillViewDTO.toString());
		return consumerBillViewDTO;
	}
	
	public String getDoonWaterDetails(String connectionNo) {

		String reString =MainetConstants.BLANK;
		String urltocall = ApplicationSession.getInstance().getMessage("water.data.url");//"https://ujsbill.uk.gov.in/updn.asmx/RequestDSCLBill";

		String keyval = ApplicationSession.getInstance().getMessage("water.data.key");//"@ujs#DSCLBill";
		String Consval = "DSCL";
		URL vUrl = null;
		URLConnection urlConnection = null;
		InputStream in = null;
		BufferedReader lo_in = null;
		try {
			String conscode = connectionNo; // 0699970 input ConsCode
			String encryptval = encval(Consval, keyval, conscode);
			String auth_request = "auth=" + Consval + "|" + conscode + "|" + encryptval;
			/* urlConnection = url.openConnection(); */
			// Create a trust manager that does not validate certificate chains
		       TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
		              public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		                    return null;
		                }
		                public void checkClientTrusted(X509Certificate[] certs, String authType) {
		                }
		                public void checkServerTrusted(X509Certificate[] certs, String authType) {
		                }
		            }
		        };
		 
		        // Install the all-trusting trust manager
		        SSLContext sc = SSLContext.getInstance("SSL");
		        sc.init(null, trustAllCerts, new java.security.SecureRandom());
		        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		 
		        // Create all-trusting host name verifier
		        HostnameVerifier allHostsValid = new HostnameVerifier() {
		            public boolean verify(String hostname, SSLSession session) {
		                return true;
		            }
		        };
		        vUrl = new URL(urltocall);
		        // Install the all-trusting host verifier
		        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

				if(vUrl.openConnection() instanceof HttpsURLConnection)
				{
					urlConnection = (HttpsURLConnection)vUrl.openConnection();
				} 
				else if(vUrl.openConnection() instanceof HttpsURLConnection)
				{
					urlConnection = (HttpsURLConnection)vUrl.openConnection();
				} else
				{
					urlConnection = (URLConnection)vUrl.openConnection();
				}
			HttpURLConnection httpurlconnection = (HttpURLConnection) urlConnection;
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setDoInput(true);
			httpurlconnection.setUseCaches(false);
			httpurlconnection.setRequestMethod("POST");
			httpurlconnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			
			httpurlconnection.setConnectTimeout(20000);
			httpurlconnection.setReadTimeout(20000);
			LOGGER.info("  request message=" + auth_request);
			OutputStream out = httpurlconnection.getOutputStream();
			Writer wout = new OutputStreamWriter(out);
			wout.write(auth_request);
			String inputLine = null;
			StringBuffer respbuf = new StringBuffer();
			wout.flush();
			wout.close();
			InputStreamReader in_1 = new InputStreamReader(httpurlconnection.getInputStream(),"UTF-8");
			lo_in = new BufferedReader(in_1);
			while ((inputLine = lo_in.readLine()) != null) {
				respbuf.append(inputLine);
			}

		    LOGGER.info(" response message =" + respbuf.toString());
			JSONObject xmlJSONObj = XML.toJSONObject(respbuf.toString());
			String jsonPrettyPrintString = xmlJSONObj.toString();
			System.out.println(jsonPrettyPrintString);
			JSONObject json = new JSONObject(new JSONTokener(jsonPrettyPrintString));
			LinkedHashMap<String, Object> mm = (LinkedHashMap<String, Object>) new ObjectMapper().readValue(jsonPrettyPrintString, LinkedHashMap.class);
			LinkedHashMap<String, String> v1 = (LinkedHashMap<String, String>) mm.get("string");	
			 reString = (String)v1.get("content");
		     LOGGER.info(reString);
			System.out.println(reString);
		} catch (Exception error) {
			error.printStackTrace();

		}
	return reString;
	}

	private static String encval(String consval, String keyval, String conscode) {

		String encryptvalue = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");

			String input = conscode;

			byte[] inputBytes = input.getBytes("ASCII");

			byte[] hash = md5.digest(inputBytes);
			StringBuilder sb = new StringBuilder();
			for (byte b : hash) {
				sb.append(String.format("%02X", b));
			}

			String message = sb.toString().toLowerCase();

			byte[] keyByte = keyval.getBytes("UTF-8");

			SecretKeySpec signingKey = new SecretKeySpec(keyByte, "HmacSHA1");

			Mac hmacsha1 = Mac.getInstance("HmacSHA1");
			hmacsha1.init(signingKey);

			// Compute the hmac on input data bytes
			byte[] rawHmac = hmacsha1.doFinal(message.getBytes("UTF-8"));

			StringBuilder sb1 = new StringBuilder();

			for (byte b : rawHmac) {
				sb1.append(String.format("%02X", b));
			}

			encryptvalue = sb1.toString().toLowerCase();

		}

		catch (Exception error) {
			error.printStackTrace();

		}
		return encryptvalue;
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
