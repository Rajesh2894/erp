package com.abm.mainet.cms.ui.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XML;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.UriEncoder;

import com.abm.mainet.cms.dto.EventSensorAccTockenReqDTO;
import com.abm.mainet.cms.dto.ItmsUpdateDTO;
import com.abm.mainet.cms.dto.MDDAAPIReqDto;
import com.abm.mainet.cms.dto.MDDAApiResponseDto;
import com.abm.mainet.cms.dto.NearByStopsDTO;
import com.abm.mainet.cms.dto.NewUPDCLEleConDTO;
import com.abm.mainet.cms.dto.TraficUpdateDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.MappingJackson2HttpMessageConverter;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.aspose.p6a2feef8.pce5140df.pbdb106a0.h;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope(value = "session")
public class DoonIntegrationModel extends AbstractFormModel implements Serializable {

	private static final long serialVersionUID = -8089318502962092944L;
	private static final Logger LOG = Logger.getLogger(CitizenHomeModel.class);

	private List<TraficUpdateDTO> traficUpdateAPIList;

	private List<ItmsUpdateDTO> itmsUpdateAPIList;

	private Map<String,Integer> map=new LinkedHashMap<>();

	private Map<String,Integer>  mddastatus=new LinkedHashMap<>();

	private Map<String,Integer> totalMDDA=new LinkedHashMap<>();

	private List<MDDAApiResponseDto> mDDAApiResponseDto=new ArrayList<>();  

	private NewUPDCLEleConDTO newUPDCLEleConDTO;

	public void setTraficUpdateAPIList(List<TraficUpdateDTO> traficUpdateAPIList) {
		this.traficUpdateAPIList = traficUpdateAPIList;
	}

	public void setItmsUpdateAPIList(List<ItmsUpdateDTO> itmsUpdateAPIList) {
		this.itmsUpdateAPIList = itmsUpdateAPIList;
	}

	//E-Bus service started

	//E-Bus service access token 
	@SuppressWarnings("unchecked")
	public String getEbusServiceDet() {

		Log.info("getEbusServiceDet started");
		String responseToken = null;
		EventSensorAccTockenReqDTO req = new EventSensorAccTockenReqDTO();
		try {

			req.setAuthorizationKey(ApplicationSession.getInstance().getMessage("event.sensor.auth.key"));
			req.setUsername(ApplicationSession.getInstance().getMessage("event.sensor.username"));
			req.setPassword(ApplicationSession.getInstance().getMessage("event.sensor.password"));
			req.setGrantType(ApplicationSession.getInstance().getMessage("event.sensor.grantType"));
			String url = ApplicationSession.getInstance().getMessage("event.sensor.url");

			Object access_tockenObject = JersyCall.callRestTeplateWithHeaders(req, url, null, HttpMethod.POST);

			Log.info("Response >>>"+access_tockenObject);

			String access_token = null;
			if (access_tockenObject != null) {
				Map<String, Object> mapResponse = null;
				mapResponse = (Map<String, Object>) access_tockenObject;
				access_token = (String) mapResponse.get(MainetConstants.ACCESS_TOCKEN);
				Log.info("access token-->"+access_token);
				if(null!=access_token && !access_token.isEmpty()){
					responseToken = access_token;
					return responseToken;
				}


			}
			return responseToken;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the getEbusServiceDet api service"+e);
		}
		Log.info("getEbusServiceDet finished");
		return responseToken;
	}

	//E-Bus service distinct stop
	public List<Object> getDistinctStopsDetails(String accessToken) {
		Log.info("getDistinctStopsDetails started");
		Map<String, Object> getDistinctStopsResponse = null;
		List<Object> distinctStopsList = new ArrayList<>();

		try {
			String tenantCode = "dscl";
			String getDistinctStopUrl = ApplicationSession.getInstance().getMessage("ebus.service.distinctStop");
			String tenantData= UriEncoder.encode("tenantCode=" + tenantCode);
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

			Object distinctStopResponse = JersyCall.callRestTeplateWithHeaders(null, getDistinctStopUrl + tenantData, sensorHeaders,
					HttpMethod.GET);	
			Log.info("distinctStopResponse >>> "+distinctStopResponse);

			if (distinctStopResponse != null) {		
				getDistinctStopsResponse = Utility.getMapper().readValue((String) distinctStopResponse, Map.class);
				if(getDistinctStopsResponse.get("status").equals(true)) {
					distinctStopsList = (List<Object>) getDistinctStopsResponse.get("distinctStopList");

					Log.info("Distinct Stop List >>>"+distinctStopsList);
					return distinctStopsList;
				}
			}

			return distinctStopsList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the distinct stop api service");
		}
		Log.info("getDistinctStopsDetails finished");
		return distinctStopsList;
	}

	//E-Bus service routes
	public List<Object> getRoutesDetails(String accessToken) {

		Log.info("getRoutesDetails started");
		Map<String, Object> getRoutesResponse = null;
		List<Object> routesList = new ArrayList<>();

		try {
			String tenantCode = "DSCL";
			String getRoutesUrl = ApplicationSession.getInstance().getMessage("ebus.service.routes");
			String tenantData= UriEncoder.encode("tenantCode=" + tenantCode);
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

			Object routesResponse = JersyCall.callRestTeplateWithHeaders(null, getRoutesUrl + tenantData, sensorHeaders,
					HttpMethod.GET);
			Log.info("routesResponse >>> "+routesResponse);

			if (routesResponse != null) {

				getRoutesResponse = Utility.getMapper().readValue((String) routesResponse, Map.class);
				if(getRoutesResponse.get("status").equals(true)) {
					routesList = (List<Object>) getRoutesResponse.get("routeList");					
					Log.info("Routes List >>>"+routesList);
					return routesList;
				}
			}

			return routesList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the routes api service");
		}
		Log.info("getRoutesDetails finished");
		return routesList;
	}

	//E-Bus service near by stops
	@SuppressWarnings("null")
	public List<Object> getNearByStopsDetails(String accessToken, String latitude, String longitude, String radius) {

		Log.info("getNearByStopsDetails started");

		ResponseEntity<Object> getNearByStopsResponse = null;
		Map<String,Object> nearByStopResponse = null;
		NearByStopsDTO req = new NearByStopsDTO();
		List<Object> nearByStopList = new ArrayList<>();
		RestTemplate restTemplate;

		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			String tenantCode = "dscl";
			
			String getNearByStopsUrl = ApplicationSession.getInstance().getMessage("ebus.service.nearByStops");

			req.setTenantCode(tenantCode);
			req.setLat(Double.valueOf(latitude));
			req.setLon(Double.valueOf(longitude));
			req.setRadius(Integer.valueOf(radius));

			HttpEntity<Object> request = new HttpEntity<Object>(req,sensorHeaders);

			getNearByStopsResponse = restTemplate.exchange(getNearByStopsUrl, HttpMethod.POST, request, Object.class);

			Log.info("nearByStopsResponse >>> "+getNearByStopsResponse);

			if (getNearByStopsResponse != null) {
				nearByStopResponse = ( Map<String,Object>) getNearByStopsResponse.getBody();
				if(nearByStopResponse.get("status").equals(true)) {
					nearByStopList =( List<Object>) nearByStopResponse.get("nearByStops");
					Log.info("Near by stops List >>>"+nearByStopList);
					return nearByStopList;
				}
			}
			return nearByStopList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the near by stops api service"+e);
		}
		Log.info("getNearByStopsDetails finished");
		return nearByStopList;
	}
	//E-Bus service ended

	//E-Bus getRouteStarted
	@SuppressWarnings("null")
	public List<Object> getRouteTypesDetails(String accessToken,String routeName) {

		Log.info("getRouteTypesDetails started");

		ResponseEntity<Object> getRouteTypesDetailsResponse = null;
		Map<String,Object> routeTypeResponse = null;

		Map<String,String> req=new HashMap<>();

		List<Object> routeTypeList = new ArrayList<>();
		RestTemplate restTemplate;

		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);


			String getRouteTypeUrl = ApplicationSession.getInstance().getMessage("ebus.service.getRouteType");
			req.put("routeName",routeName);

			HttpEntity<Object> request = new HttpEntity<Object>(req,sensorHeaders);

			getRouteTypesDetailsResponse = restTemplate.exchange(getRouteTypeUrl, HttpMethod.POST, request, Object.class);

			Log.info("getRouteTypesDetailsResponse >>> "+getRouteTypesDetailsResponse);

			if (getRouteTypesDetailsResponse != null) {
				routeTypeResponse = ( Map<String,Object>) getRouteTypesDetailsResponse.getBody();
				if(routeTypeResponse.get("status").equals(true)) {
					routeTypeList =( List<Object>) routeTypeResponse.get("routeTypeList");
					Log.info("getRouteTypesDetails List >>>"+routeTypeList);
					return routeTypeList;
				}
			}
			return routeTypeList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the get RouteTypes Details api service"+e);
		}
		Log.info("getRouteTypesDetails finished");
		return routeTypeList;
	}//E-Bus getRoute Ended

	//E-Bus getEnrouteDetails Started

	@SuppressWarnings("null")
	public List<Object> getEnrouteDetails(String accessToken,String sourceId,String destinationId,String routeId) { 

		Log.info("getEnrouteDetails started");

		ResponseEntity<Object> getEnrouteDetailsResponse = null;
		Map<String,Object> enrouteDetailsResponse = null;

		Map<String,String> req= new HashMap<String, String>();

		List<Object> enrouteDetailsList = new ArrayList<>();
		RestTemplate restTemplate;

		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			String getEnrouteDetailsUrl = ApplicationSession.getInstance().getMessage("ebus.service.getEnrouteDetails");

			req.put("sourceId",sourceId);
			req.put("destinationId",destinationId);
			req.put("routeId",routeId);

			HttpEntity<Object> request = new HttpEntity<Object>(req,sensorHeaders);
			getEnrouteDetailsResponse = restTemplate.exchange(getEnrouteDetailsUrl, HttpMethod.POST, request, Object.class);
			Log.info("getEnrouteDetailsResponse >>> "+getEnrouteDetailsResponse);					

			if (getEnrouteDetailsResponse != null) {
				enrouteDetailsResponse = ( Map<String,Object>) getEnrouteDetailsResponse.getBody();
				if(enrouteDetailsResponse.get("status").equals(true)) {
					enrouteDetailsList =( List<Object>) enrouteDetailsResponse.get("enroutedDet");				
					Log.info("getEnrouteDetailsResponse List >>>"+enrouteDetailsList);
					return enrouteDetailsList;
				}
			}
			return enrouteDetailsList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the Enrouted Details api service"+e);
		}
		Log.info("getEnrouteDetails finished");
		return enrouteDetailsList;
	}//E-Bus getRoute Ended

	//ArrvingBusList Started	
	@SuppressWarnings("null")
	public List<Object> getArrivingBusesList(String accessToken,String stopId) { 

		Log.info("getArrivingBusesList started");

		ResponseEntity<Object> getArrivingBusesResponse = null;
		Map<String,Object> arrrivingBusesResponse = null;		
		Map<String,String> req= new HashMap<>();		
		List<Object> arrivingBusesList= new ArrayList<>();
		RestTemplate restTemplate;	
		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			String getArrivingBusesUrl = ApplicationSession.getInstance().getMessage("ebus.service.getArrivingBusesList");

			req.put("stopId",stopId);					
			HttpEntity<Object> request = new HttpEntity<Object>(req,sensorHeaders);			
			getArrivingBusesResponse = restTemplate.exchange(getArrivingBusesUrl, HttpMethod.POST, request, Object.class);			
			Log.info("getArrivingBusesResponse >>> "+getArrivingBusesResponse);	

			if (getArrivingBusesResponse != null) {
				arrrivingBusesResponse = ( Map<String,Object>) getArrivingBusesResponse.getBody();
				if(arrrivingBusesResponse.get("status").equals(true)) {
					arrivingBusesList =( List<Object>) arrrivingBusesResponse.get("arrivingBusesList");
					Log.info("arrivingBuses List >>>"+arrivingBusesList);
					return arrivingBusesList;
				}
			}
			return arrivingBusesList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the arriving Buses List api service"+e);
		}
		Log.info("getArrivingBusesList finished");
		return arrivingBusesList;
	}//E-Bus  ArrvingBusList ended

	//E-Bus getTripPlannerDetails Started	
	@SuppressWarnings("null")
	public List<Object> getTripPlannerDetails(String accessToken,String source,String destination, String time) { 

		Log.info("getTripPlannerDetails started");

		ResponseEntity<Object> getTripPlannerDetailsResponse = null;
		Map<String,Object> tripPlannerDetailsResponse = null;		
		Map<String,String> req= new HashMap<>();		
		List<Object> tripPlannerDetailsList= new ArrayList<>();
		RestTemplate restTemplate;	
		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			String  getTripPlannerDetaiilsUrl = ApplicationSession.getInstance().getMessage("ebus.service.getTripPlannerDetails");

			req.put("source",source);		
			req.put("destination",destination);
			req.put("time", time);
			HttpEntity<Object> request = new HttpEntity<Object>(req,sensorHeaders);			
			getTripPlannerDetailsResponse = restTemplate.exchange(getTripPlannerDetaiilsUrl, HttpMethod.POST, request, Object.class);			
			Log.info("getTripPlannerDetaiilsResponse >>> "+getTripPlannerDetailsResponse);	

			if (getTripPlannerDetailsResponse != null) {
				tripPlannerDetailsResponse = ( Map<String,Object>) getTripPlannerDetailsResponse.getBody();
				if(tripPlannerDetailsResponse.get("status").equals(true)) {
					tripPlannerDetailsList =( List<Object>) tripPlannerDetailsResponse.get("plannerDetails");
					Log.info("tripPlannerDetaiilsList >>>"+tripPlannerDetailsList);
					return tripPlannerDetailsList;
				}
			}
			return tripPlannerDetailsList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the getTripPlannerDetails api service"+e);
		}
		Log.info("getTripPlannerDetails finished");
		return tripPlannerDetailsList;
	}//E-Bus  getTripPlannerDetails ended

	//E-Bus getFareDetails Started	
	@SuppressWarnings("null")
	public List<Object> getFareDetails(String accessToken,String srcId,String destId, String routeId) { 

		Log.info("getFareDetails started");

		ResponseEntity<Object> getFareDetailsResponse = null;
		Map<String,Object> fareDetailsResponse = null;		
		Map<String,Long> req= new HashMap<>();		
		List<Object> getFareDetailsList= new ArrayList<>();
		RestTemplate restTemplate;	
		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			String  getFareDetailsUrl = ApplicationSession.getInstance().getMessage("ebus.service.getFareDetails");

			req.put("srcId",Long.valueOf(srcId));		
			req.put("destId",Long.valueOf(destId));
			req.put("routeId", Long.valueOf(routeId));
			HttpEntity<Object> request = new HttpEntity<Object>(req,sensorHeaders);			
			getFareDetailsResponse = restTemplate.exchange(getFareDetailsUrl, HttpMethod.POST, request, Object.class);			
			Log.info("getFareDetailsResponse >>> "+getFareDetailsResponse);	

			if (getFareDetailsResponse != null) {
				fareDetailsResponse = ( Map<String,Object>) getFareDetailsResponse.getBody();
				if(fareDetailsResponse.get("status").equals(true)) {
					getFareDetailsList =( List<Object>) fareDetailsResponse.get("fareList");
					Log.info("getFareDetailsList >>>"+getFareDetailsList);
					return getFareDetailsList;
				}
			}
			return getFareDetailsList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the getFareDetailsList api service"+e);
		}
		Log.info("getTripPlannerDetails finished");
		return getFareDetailsList;
	}//E-Bus  getFareDetails ended	

	//E-Bus timeTableDetails Started	
	@SuppressWarnings("null")
	public Map<String, List> getTimeTableDetails(String accessToken,String routeId,String time) { 

		Log.info("getTimeTableDetails started");

		ResponseEntity<Object> getTimeTableDetailsResponse = null;
		Map<String,Object> timeTableDetailsResponse = null;		
		Map<String,String> req= new HashMap<>();		
		Map<String,List> mapList = new HashMap<>();
		List<Object> getRouteTimingsList= new ArrayList<>();
		List<Object> getRouteDetailsList = new ArrayList<>();
		RestTemplate restTemplate;	
		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			String  getTimeTableDetailsUrl = ApplicationSession.getInstance().getMessage("ebus.service.getTimeTableDetails");

			req.put("routeId",routeId);		
			req.put("time",time);
			HttpEntity<Object> request = new HttpEntity<Object>(req,sensorHeaders);			
			getTimeTableDetailsResponse = restTemplate.exchange(getTimeTableDetailsUrl, HttpMethod.POST, request, Object.class);			
			Log.info("getTimeTableDetailsResponse >>> "+getTimeTableDetailsResponse);	

			if (getTimeTableDetailsResponse != null) {
				timeTableDetailsResponse = ( Map<String,Object>) getTimeTableDetailsResponse.getBody();
				if(timeTableDetailsResponse.get("status").equals(true)) {
					getRouteTimingsList =( List<Object>) timeTableDetailsResponse.get("routeTimings");
					getRouteDetailsList =( List<Object>) timeTableDetailsResponse.get("routeDetails");

					mapList.put("routeTimings", getRouteTimingsList);
					mapList.put("routeDetails", getRouteDetailsList);
					Log.info("getTimeTableDetails >>>"+mapList);
					return mapList;
				}
			}
			return mapList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the getTimeTable Details List api service"+e);
		}
		Log.info("getTimeTableDetails finished");
		return mapList;
	}//E-Bus  timeTableDetails ended	

	//E-Bus routeDetails Started	
	@SuppressWarnings("null")
	public List<Object> getRouteDetails(String accessToken,String routeId) { 

		Log.info("getRouteDetails started");

		ResponseEntity<Object> getRouteDetailsResponse = null;
		Map<String,Object> routeDetailsResponse = null;		
		Map<String,String> req= new HashMap<>();		
		List<Object> getRouteDetailsList= new ArrayList<>();
		RestTemplate restTemplate;	
		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

			String  getRouteDetailsUrl = ApplicationSession.getInstance().getMessage("ebus.service.getRouteDetails");

			req.put("routeId",routeId);		
			HttpEntity<Object> request = new HttpEntity<Object>(req,sensorHeaders);			
			getRouteDetailsResponse = restTemplate.exchange(getRouteDetailsUrl, HttpMethod.POST, request, Object.class);			
			Log.info("getRouteDetailsResponse >>> "+getRouteDetailsResponse);	

			if (getRouteDetailsResponse != null) {
				routeDetailsResponse = ( Map<String,Object>) getRouteDetailsResponse.getBody();
				if(routeDetailsResponse.get("status").equals(true)) {
					getRouteDetailsList =( List<Object>) routeDetailsResponse.get("routeDetails");
					Log.info("getRouteDetailsList >>>"+getRouteDetailsList);
					return getRouteDetailsList;
				}
			}
			return getRouteDetailsList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the getRouteDetailsList  api service"+e);
		}
		Log.info("getTripPlannerDetails finished");
		return getRouteDetailsList;
	}//E-Bus  routeDetails ended	

	//E-Bus scheduledTripDetails Started	
	@SuppressWarnings("null")
	public List<Object> getScheduledTripDetails(String accessToken,String routeId) { 

		Log.info("getScheduledTripDetails started");

		ResponseEntity<Object> getScheduledTripDetailsResponse = null;
		Map<String,Object> scheduledTripDetailsResponse = null;		
		Map<String,String> req= new HashMap<>();		
		List<Object> getScheduledTripDetailsList= new ArrayList<>();
		RestTemplate restTemplate;	
		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

			String  getScheduledTripDetailsUrl = ApplicationSession.getInstance().getMessage("ebus.service.getScheduledTripDetails");

			req.put("routeId",routeId);		
			HttpEntity<Object> request = new HttpEntity<Object>(req,sensorHeaders);			
			getScheduledTripDetailsResponse = restTemplate.exchange(getScheduledTripDetailsUrl, HttpMethod.POST, request, Object.class);			
			Log.info("getScheduledTripDetailsResponse >>> "+getScheduledTripDetailsResponse);	

			if (getScheduledTripDetailsResponse != null) {
				scheduledTripDetailsResponse = ( Map<String,Object>) getScheduledTripDetailsResponse.getBody();
				if(scheduledTripDetailsResponse.get("status").equals(true)) {
					getScheduledTripDetailsList =( List<Object>) scheduledTripDetailsResponse.get("scheduledTripDetails");
					Log.info("getScheduledTripDetails List >>>"+getScheduledTripDetailsList);
					return getScheduledTripDetailsList;
				}
			}
			return getScheduledTripDetailsList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the get Scheduled TripDetails  api service"+e);
		}
		Log.info("getScheduledTripDetails  finished");
		return getScheduledTripDetailsList;
	}//E-Bus  scheduledTripDetails ended	

	//E-Bus wayPointsDetails Started	
	@SuppressWarnings("null")
	public List<Object> getWayPointsDetails(String accessToken,String routeId,String fleetId) { 

		Log.info("getWayPointsDetails started");

		ResponseEntity<Object> getWayPointsDetailsResponse = null;
		Map<String,Object> wayPointsDetailsResponse = null;		
		Map<String,String> req= new HashMap<>();		
		List<Object> getWayPointsDetailsList= new ArrayList<>();
		RestTemplate restTemplate;	
		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			String  getWayPointsDetailsUrl = ApplicationSession.getInstance().getMessage("ebus.service.getWayPointsDetails");

			req.put("routeId",routeId);		
			req.put("fleetId", fleetId);
			HttpEntity<Object> request = new HttpEntity<Object>(req,sensorHeaders);			
			getWayPointsDetailsResponse = restTemplate.exchange(getWayPointsDetailsUrl, HttpMethod.POST, request, Object.class);			
			Log.info("getWayPointsDetailsResponse >>> "+getWayPointsDetailsResponse);	

			if (getWayPointsDetailsResponse != null) {
				wayPointsDetailsResponse = ( Map<String,Object>) getWayPointsDetailsResponse.getBody();
				if(wayPointsDetailsResponse.get("status").equals(true)) {
					getWayPointsDetailsList =( List<Object>) wayPointsDetailsResponse.get("wayPointDetails");
					Log.info("getWayPointsDetailsList >>>"+getWayPointsDetailsList);
					return getWayPointsDetailsList;
				}
			}
			return getWayPointsDetailsList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the get WayPointsDetails  api service"+e);
		}
		Log.info("getWayPointsDetails  finished");
		return getWayPointsDetailsList;
	}//E-Bus  wayPointsDetails ended

	//E-Bus getIncidentSubTypes Started	
	@SuppressWarnings("null")
	public List<Object> getIncidentSubTypes(String accessToken) { 

		Log.info("getIncidentSubTypes started");
		ResponseEntity<Object> getIncidentSubTypesResponse = null;
		Map<String,Object> incidentSubTypes = null;		
		Map<String,String> req= new HashMap<>();		
		List<Object> getIncidentSubTypesList= new ArrayList<>();
		RestTemplate restTemplate;	
		try {
			restTemplate = restTemplate();
			HttpHeaders sensorHeaders = new HttpHeaders();
			sensorHeaders.add("Content-Type", "application/json");
			sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			String tenantCode = "dscl";
			String tenantData= UriEncoder.encode("tenantCode=" + tenantCode);
			String  getIncidentSubTypesUrl = ApplicationSession.getInstance().getMessage("ebus.service.getIncidentSubTypes");

			HttpEntity<Object> request = new HttpEntity<Object>(sensorHeaders);			
			getIncidentSubTypesResponse = restTemplate.exchange(getIncidentSubTypesUrl+tenantData, HttpMethod.GET, request, Object.class);			
			Log.info("getIncidentSubTypesResponse >>> "+getIncidentSubTypesResponse);	

			if (getIncidentSubTypesResponse != null) {
				incidentSubTypes = ( Map<String,Object>) getIncidentSubTypesResponse.getBody();
				if(incidentSubTypes.get("status").equals(true)) {
					getIncidentSubTypesList =( List<Object>) incidentSubTypes.get("resultData");
					Log.info("getIncidentSubTypesList >>>"+getIncidentSubTypesList);
					return getIncidentSubTypesList;
				}
			}
			return getIncidentSubTypesList;
		}
		catch (Exception e) {
			Log.error("Error occured while calling the getIncidentSubTypesList  api service"+e);
		}
		Log.info("getIncidentSubTypesList  finished");
		return getIncidentSubTypesList;
	}//E-Bus  getIncidentSubTypes ended



	@SuppressWarnings("unchecked")
	public Map<String, Object> getEnvSensorUpdate(String sensorLocCode) {

		Object finalReponse = null;
		Map<String, Object> response = null;
		EventSensorAccTockenReqDTO req = new EventSensorAccTockenReqDTO();
		try {
			req.setAuthorizationKey(ApplicationSession.getInstance().getMessage("event.sensor.auth.key"));
			req.setUsername(ApplicationSession.getInstance().getMessage("event.sensor.username"));
			req.setPassword(ApplicationSession.getInstance().getMessage("event.sensor.password"));
			req.setGrantType(ApplicationSession.getInstance().getMessage("event.sensor.grantType"));
			String url = ApplicationSession.getInstance().getMessage("event.sensor.url");

			long limit = 5;
			Date currentDate = new Date();

			SimpleDateFormat df = new SimpleDateFormat(MainetConstants.FORMAT_YYYYMMDD_HHMMSS);		 
			Calendar cal = Calendar.getInstance();

			cal.setTime(currentDate);
			String toDateString = df.format(cal.getTime());

			cal.add(Calendar.SECOND, -86400);
			String fromDateString = df.format(cal.getTime());

			String getEventSensorUrl = ApplicationSession.getInstance().getMessage("event.sensor.get.envdata.url") ; 			
			String encodeData= UriEncoder.encode("fromdate=" + fromDateString
					+ "&todate=" + toDateString + "&columns=CO2" + "&macid=" + sensorLocCode + "&limit=" + limit);

			LOG.info(" getEventSensorUrl Value ---------------->" + getEventSensorUrl + encodeData);

			/*String accessToken = ApplicationSession.getInstance().getMessage("event.sensor.accessToken");
		String expires_in = ApplicationSession.getInstance().getMessage("event.sensor.expires");
		String refresh_token = ApplicationSession.getInstance().getMessage("event.sensor.refresh.token");
		String scope = ApplicationSession.getInstance().getMessage("event.sensor.scope");
		String token_type = ApplicationSession.getInstance().getMessage("event.sensor.token.type");*/

			Object access_tockenObject = JersyCall.callRestTeplateWithHeaders(req, url, null, HttpMethod.POST);
			String access_token = null;
			if (access_tockenObject != null) {
				Map<String, Object> mapResponse = null;
				mapResponse = (Map<String, Object>) access_tockenObject;
				access_token = (String) mapResponse.get(MainetConstants.ACCESS_TOCKEN);

				HttpHeaders sensorHeaders = new HttpHeaders();
				sensorHeaders.add("Content-Type", "application/json");
				sensorHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + access_token);
				/*sensorHeaders.add("access_token"," : "+accessToken);
		sensorHeaders.add("expires_in"," : "+expires_in);
		sensorHeaders.add("refresh_token"," : "+refresh_token);
		sensorHeaders.add("scope"," : "+scope);
		sensorHeaders.add("token_type"," : "+token_type);*/


				Object sensorData = JersyCall.callRestTeplateWithHeaders(null, getEventSensorUrl+encodeData, sensorHeaders,
						HttpMethod.GET);
				if (sensorData != null) {
					try {
						response = Utility.getMapper().readValue((String) sensorData, Map.class);
						response = (Map<String, Object>) response.get(MainetConstants.ENVIRONMENT);
						response = (Map<String, Object>) response.get(MainetConstants.DATA);
						Log.info("Response >>>"+response);

					} catch (IOException e1) {
						Log.error("Error occured while Casting");
					}
				}
			}
		}catch (Exception e) {
			Log.error("Error occured while Calling the server");
		}
		Log.info("Response >>>"+response);
		return response;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getEchallanById(String echallanId) {
		List<Map<String, String>> response = null;
		String finalOutput = "";
		String url = ApplicationSession.getInstance().getMessage("echallan.url");
		String basicAuthUserName = ApplicationSession.getInstance().getMessage("echallan.basic.auth.un");
		String basicAuthPass = ApplicationSession.getInstance().getMessage("echallan.basic.auth.pass");
		String authStr = basicAuthUserName + ":" + basicAuthPass;
		String tarsBase64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
		HttpGet get = new HttpGet(url + "getChallanById/" + echallanId);
		get.addHeader("Authorization", "Basic " + tarsBase64Creds);
		HttpClient client = JersyCall.getHttpsClient();
		if (client != null) {
			try {
				HttpResponse responsestack = client.execute(get);
				BufferedReader bf = new BufferedReader(new InputStreamReader(responsestack.getEntity().getContent()));

				String line = "";

				while ((line = bf.readLine()) != null) {
					finalOutput = finalOutput + line;

				}
				LOG.info(finalOutput);
			} catch (Exception e) {
				LOG.error("Exception on challan API");
			}
		}
		if (finalOutput != null && !finalOutput.isEmpty()) {
			// need to remove after the working of API

			try {
				Map<String, Object> res = (Map<String, Object>) Utility.getMapper().readValue(finalOutput, Map.class);
				if (res != null && !res.isEmpty()) {
					response = (List<Map<String, String>>) res.get(MainetConstants.MYOBJECTLIST);
				}

			} catch (IOException e1) {
				Log.error("Error occured while Casting");
			}

		}
		if (response == null) {
			response = new ArrayList<>();
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public List<TraficUpdateDTO> getTrafficUpdate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0,
				new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);

		HttpHeaders headers = new HttpHeaders();
		headers.add("auth-licence",ApplicationSession.getInstance().getMessage("trafic.update.auth-licence"));
		headers.add("auth-client", ApplicationSession.getInstance().getMessage("trafic.update.auth-client"));

		HttpEntity<String> requestEntity = null;
		List<TraficUpdateDTO> traficDtos = new ArrayList<TraficUpdateDTO>();
		Map<String,Object> mapResponse=null;
		Object resp = null;
		String traficUpateUrl = ApplicationSession.getInstance().getMessage("trafic.update.url");
		requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> responseEntity = null;

		if (traficUpateUrl != null) {
			responseEntity = restTemplate.exchange(traficUpateUrl, HttpMethod.GET, requestEntity, Object.class);
			LOG.info("The given trafic update URI is " + traficUpateUrl);
		} else {
			LOG.error("url is invalid trafic please check " + traficUpateUrl);
		}

		if (responseEntity != null) {
			mapResponse = ( Map<String,Object>) responseEntity.getBody();
		}
		Map<String,Object> objMap=( Map<String,Object>) mapResponse.get("TrafficSignal");
		List<LinkedHashMap<String, Object>> objList=(List<LinkedHashMap<String, Object>>) objMap.get("dynamics");
		for(LinkedHashMap<String, Object> traifcMap:objList) {
			TraficUpdateDTO traficDto = new TraficUpdateDTO();
			traficDto.setSignalSCN((String) traifcMap.get("systemCodeNumber"));
			traficDto.setShortDescription((String) traifcMap.get("shortDescription"));
			traficDto.setisActive((Integer) traifcMap.get("isActive"));
			traficDto.setmodeType((String) traifcMap.get("modeType"));
			traficDto.setUpdateDate((String) traifcMap.get("congestionLastUpdated"));
			try {
				Double congection = Double.parseDouble((String) traifcMap.get("colour")) ;
				traficDto.setCongestion(Math.round(congection * 1e3) / 1e3);
			}catch (Exception e) {
				LOG.error("Cannot cast to double value");
			}
			if( traficDto.getCongestion() ==0 ) {
				traficDto.setStatus(MainetConstants.TraficUpdate.LOW);
			}else if(traficDto.getCongestion() >=0 && traficDto.getCongestion() <= 0.99) {
				traficDto.setStatus(MainetConstants.TraficUpdate.MEDIUM);
			}else if(traficDto.getCongestion() >=1 ){
				traficDto.setStatus(MainetConstants.TraficUpdate.HIGH);
			}
			traficDtos.add(traficDto);	
		}
		if(traficDtos!=null && !traficDtos.isEmpty()) {
			traficDtos =traficDtos.stream().sorted(Comparator.comparingDouble(TraficUpdateDTO::getCongestion)).collect(Collectors.toList());
			this.setTraficUpdateAPIList(traficDtos);

		}
		return traficDtos;
	}

	@SuppressWarnings("unchecked")
	public List<MDDAApiResponseDto> getMDDAApiResponse() {

		MDDAAPIReqDto req=new MDDAAPIReqDto();

		req.setUsername(ApplicationSession.getInstance().getMessage("mdda.username"));
		req.setPassword(ApplicationSession.getInstance().getMessage("mdda.password"));
		RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers=new HttpHeaders();
		HttpEntity<String> requestEntity = null;
		List<Map<String,Object>> finalReponse=null;

		Object resp=null;
		String lid=null;


		String urlForAutheticate=ApplicationSession.getInstance().getMessage("mdda.authenticate.url");
		String urlForSummary=ApplicationSession.getInstance().getMessage("mdda.summary.url");

		LinkedHashMap< String , Object> responseMap=null;
		if(req!=null && urlForAutheticate!=null) {
			resp = JersyCall.callRestTemplateClient(req,urlForAutheticate);
			LOG.info("The given URI is "+urlForAutheticate +"with given request entity is "+req);
		}
		responseMap=(LinkedHashMap<String , Object>)resp;

		if(responseMap!=null) {
			lid=String.valueOf(responseMap.get("lid"));
			headers.set("lid", lid);
			requestEntity = new HttpEntity<>(null, headers);
		}

		ResponseEntity<Object> responseEntity=null;

		if(urlForSummary!=null && requestEntity!=null){
			responseEntity = restTemplate.exchange(urlForSummary, HttpMethod.POST, requestEntity, Object.class);
			LOG.info("The given URI is "+urlForSummary +"with request entity is "+requestEntity);
		}else {
			LOG.error("url is invalid please check "+urlForSummary);
		}

		if(responseEntity!=null) {
			finalReponse=(List<Map<String,Object>>)responseEntity.getBody();	
		}

		int Compounding=0; 
		int SELF_COMPUNDING=0; 
		int New_Submission=0; 
		int One_Time_Settlement=0; 
		int LAYOUT_APPROVAL=0; 
		int OTHERS=0; 
		int issued=0; 
		int rejected=0; 
		int approved=0; 
		int inprogess=0; 
		int first_total = 0;
		int second_total =0;
		int minYear=0;
		int maxYear=0;
		boolean yearFlag=true;
		if(finalReponse!=null){

			for(Map<String,Object> MDDADto:finalReponse) {
				if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("Compounding")) {
					map.put("COMPUNDING",++Compounding); 
					first_total++;
				}
				else if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("SELF Compounding")) {
					map.put("SELF COMPUNDING",++SELF_COMPUNDING); 
					first_total++;
				} 
				else if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("New Submission")){
					map.put("NEW SUBMISSION",++New_Submission); 
					first_total++;
				}
				else if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("One Time Settlement")){
					map.put("ONE TIME SETTLEMENT",++One_Time_Settlement); 
					first_total++;
				}
				else if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("Layout Approval")){
					map.put("LAYOUT APPROVAL",++LAYOUT_APPROVAL);
					first_total++;
				}
				else if(MDDADto.get("subDesc").toString().trim().equalsIgnoreCase("others")){
					map.put("OTHERS",++OTHERS); 
					second_total++;
				}

				if(MDDADto.get("approvalStatus").toString().trim().equalsIgnoreCase("rejected")) {
					mddastatus.put("REJECTED",++rejected);   
					second_total++;
				} 
				else if(MDDADto.get("approvalStatus").toString().trim().equalsIgnoreCase("approved")){
					mddastatus.put("APPROVED",++approved); 
					second_total++;

				}else if(MDDADto.get("approvalStatus").toString().trim().equalsIgnoreCase("issued")) {
					mddastatus.put("ISSUED",++issued); 
					second_total++;
				}
				else if(MDDADto.get("approvalStatus").toString().trim().equalsIgnoreCase("")){
					mddastatus.put("OTHERS",++inprogess); 
					second_total++;
				}
				if(MDDADto.get("year").toString().trim() !=null) {
					try {
						String year = MDDADto.get("year").toString().trim();
						int mYear = Integer.parseInt(year);
						if(yearFlag) {
							minYear = mYear;
							maxYear = mYear;
							yearFlag = false;
						}else{
							if(minYear >= mYear) {
								minYear = mYear;
							}
							if(maxYear <= mYear) {
								maxYear = mYear;
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			totalMDDA.put("first_total",first_total); 
			totalMDDA.put("second_total",second_total); 
		}
		totalMDDA.put("minYear",minYear);
		totalMDDA.put("maxYear",maxYear);
		map.put("sizeOfMDDAService", map.size());
		mddastatus.put("sizeOfMDDAStatus", mddastatus.size());
		//mDDAApiResponseDto.addAll(finalReponse);
		return mDDAApiResponseDto;
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>>  getDsclBillDetData(String AccountNo,String UserKey) {
		List<Map<String, Object>>  response = null;
		String finalOutput = "";
		String serviceKey=ApplicationSession.getInstance().getMessage("electricity.bill.serviceKey");
		String did=ApplicationSession.getInstance().getMessage("electricity.bill.did");
		String dsclElectricityBill = ApplicationSession.getInstance().getMessage("electricity.bill.url");
		//https://appservices.upcl.org:8443/wss/api/v1/externalBillDetails?accountNumber=40110248281;//
		if (dsclElectricityBill != null) {
			dsclElectricityBill =dsclElectricityBill+"accountNumber="+AccountNo;
		}
		HttpGet get = new HttpGet(dsclElectricityBill);
		get.addHeader("serviceKey",serviceKey);
		get.addHeader("did", did);
		HttpClient client = JersyCall.getHttpsClient();

		if (client != null) {
			try {
				HttpResponse responsestack = client.execute(get);
				BufferedReader bf = new BufferedReader(new InputStreamReader(responsestack.getEntity().getContent()));

				String line = "";

				while ((line = bf.readLine()) != null) {
					finalOutput = finalOutput + line;

				}
				LOG.info(finalOutput);
			} catch (Exception e) {
				LOG.error("Exception on challan API");
			}
		}
		if (finalOutput != null && !finalOutput.isEmpty()) {
			// need to remove after the working of API
			try {

				Map<String, Object> hardCodeAferCorrectionMap = (Map<String, Object> ) Utility.getMapper().readValue(finalOutput, Map.class);
				response =(List<Map<String, Object>>)hardCodeAferCorrectionMap.get("billDetails");
			} catch (IOException e1) {
				Log.error("Error occured while Casting");
			}
		}
		return response;
	}
	public Map<String, Integer> getMddastatus() {
		return mddastatus;
	}

	public void setMddastatus(Map<String, Integer> mddastatus) {
		this.mddastatus = mddastatus;
	}

	public Map<String, Integer> getTotalMDDA() {
		return totalMDDA;
	}

	public void setTotalMDDA(Map<String, Integer> totalMDDA) {
		this.totalMDDA = totalMDDA;
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
	public List<TraficUpdateDTO> getTraficUpdateAPIList() {
		return traficUpdateAPIList;
	}

	public void setNewUPDCLEleConDTO(NewUPDCLEleConDTO newUPDCLEleConDTO) {
		this.newUPDCLEleConDTO = newUPDCLEleConDTO;
	}

	public NewUPDCLEleConDTO getNewUPDCLEleConDTO() {
		return newUPDCLEleConDTO;
	}

	public List<String> saveUpdclNewEleCon(NewUPDCLEleConDTO newUPDCLEleConDTO) {

		List<Map<String, Object>>  response = null;
		String finalOutput = "";
		String dsclElectricityBill = ApplicationSession.getInstance().getMessage("electricity.new.reg.url");

		HttpPost post = new HttpPost(dsclElectricityBill);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("applicantName",newUPDCLEleConDTO.getApplicantName() ));
		params.add(new BasicNameValuePair("fatherHusbandName", newUPDCLEleConDTO.getFatherHusbandName()));
		params.add(new BasicNameValuePair("streetName", newUPDCLEleConDTO.getStreetName()));
		params.add(new BasicNameValuePair("housePlotNumber", newUPDCLEleConDTO.getHousePlotNumber()));
		params.add(new BasicNameValuePair("colonyArea", newUPDCLEleConDTO.getColonyArea()));
		params.add(new BasicNameValuePair("email", newUPDCLEleConDTO.getEmail()));
		params.add(new BasicNameValuePair("phone", newUPDCLEleConDTO.getPhone()));
		params.add(new BasicNameValuePair("residencePhone", newUPDCLEleConDTO.getResidencePhone()));
		params.add(new BasicNameValuePair("aadhar", newUPDCLEleConDTO.getAadhar()));
		params.add(new BasicNameValuePair("gstin", newUPDCLEleConDTO.getGstin()));
		params.add(new BasicNameValuePair("service", newUPDCLEleConDTO.getService()));
		params.add(new BasicNameValuePair("projectArea", newUPDCLEleConDTO.getProjectArea()));
		params.add(new BasicNameValuePair("consumerType", newUPDCLEleConDTO.getConsumerType()));
		params.add(new BasicNameValuePair("locationType", newUPDCLEleConDTO.getLocationType()));
		params.add(new BasicNameValuePair("govtScheme", newUPDCLEleConDTO.getGovtScheme()));
		params.add(new BasicNameValuePair("divisionOffice", newUPDCLEleConDTO.getDivisionOffic1()));
		params.add(new BasicNameValuePair("subdivOffice", newUPDCLEleConDTO.getDivisionOffic2()));
		params.add(new BasicNameValuePair("district", newUPDCLEleConDTO.getDivisionOffic1()));
		params.add(new BasicNameValuePair("subDistrict", newUPDCLEleConDTO.getDivisionOffic2()));
		params.add(new BasicNameValuePair("villageTown", newUPDCLEleConDTO.getVillageTown()));
		params.add(new BasicNameValuePair("category", newUPDCLEleConDTO.getCategor1()));
		params.add(new BasicNameValuePair("supplyType", newUPDCLEleConDTO.getCategor2()));
		params.add(new BasicNameValuePair("purpose", newUPDCLEleConDTO.getPurpose()));
		params.add(new BasicNameValuePair("appliedLoad", newUPDCLEleConDTO.getAppliedLoad()));
		params.add(new BasicNameValuePair("loadUnit", newUPDCLEleConDTO.getLoadUnit()));
		params.add(new BasicNameValuePair("builtupArea", newUPDCLEleConDTO.getBuiltupArea()));
		params.add(new BasicNameValuePair("plotSize", newUPDCLEleConDTO.getPlotSize()));
		params.add(new BasicNameValuePair("houseOwnername", newUPDCLEleConDTO.getHouseOwnername()));
		params.add(new BasicNameValuePair("houseOwnerplotno", newUPDCLEleConDTO.getHouseOwnerplotno()));
		params.add(new BasicNameValuePair("houseOwnerarea", newUPDCLEleConDTO.getHouseOwnerarea()));
		params.add(new BasicNameValuePair("houseOwnerstreet", newUPDCLEleConDTO.getHouseOwnerstreet()));
		params.add(new BasicNameValuePair("houseOwnercity", newUPDCLEleConDTO.getHouseOwnercity()));
		params.add(new BasicNameValuePair("empId", newUPDCLEleConDTO.getEmpId()));
		params.add(new BasicNameValuePair("empOfficename", newUPDCLEleConDTO.getEmpOfficename()));
		params.add(new BasicNameValuePair("empName", newUPDCLEleConDTO.getEmpName()));
		params.add(new BasicNameValuePair("empStatus", newUPDCLEleConDTO.getEmpStatus()));
		params.add(new BasicNameValuePair("serviceNumber", newUPDCLEleConDTO.getServiceNumber()));
		params.add(new BasicNameValuePair("connectedDate", newUPDCLEleConDTO.getConnectedDate()));
		params.add(new BasicNameValuePair("consumerName", newUPDCLEleConDTO.getConsumerName()));
		params.add(new BasicNameValuePair("consumerFatherHusband", newUPDCLEleConDTO.getConsumerFatherHusband()));
		params.add(new BasicNameValuePair("poleNumber", newUPDCLEleConDTO.getPoleNumber()));
		params.add(new BasicNameValuePair("dtrNumber", newUPDCLEleConDTO.getDtrNumber()));
		params.add(new BasicNameValuePair("neighbourConsumernumber", newUPDCLEleConDTO.getNeighbourConsumernumber()));
		params.add(new BasicNameValuePair("neighbourConsumername", newUPDCLEleConDTO.getNeighbourConsumername()));
		params.add(new BasicNameValuePair("registeredBy", newUPDCLEleConDTO.getRegisteredBy()));
		params.add(new BasicNameValuePair("referenceNumber", newUPDCLEleConDTO.getReferenceNumber()));
		params.add(new BasicNameValuePair("applicantImage", newUPDCLEleConDTO.getApplicantImage()));
		params.add(new BasicNameValuePair("saleDeed", newUPDCLEleConDTO.getSaleDeed()));
		params.add(new BasicNameValuePair("leaseDeed", newUPDCLEleConDTO.getLeaseDeed()));
		params.add(new BasicNameValuePair("powerOfAttorney", newUPDCLEleConDTO.getPowerOfAttorney()));
		params.add(new BasicNameValuePair("demandNotice", newUPDCLEleConDTO.getDemandNotice()));
		params.add(new BasicNameValuePair("letterOfAllotment", newUPDCLEleConDTO.getLetterOfAllotment()));
		params.add(new BasicNameValuePair("noc", newUPDCLEleConDTO.getNoc()));
		params.add(new BasicNameValuePair("khasra", newUPDCLEleConDTO.getKhasra()));
		params.add(new BasicNameValuePair("khatauli", newUPDCLEleConDTO.getKhatauli()));
		params.add(new BasicNameValuePair("electoralId", newUPDCLEleConDTO.getElectoralId()));
		params.add(new BasicNameValuePair("passport", newUPDCLEleConDTO.getPassport()));
		params.add(new BasicNameValuePair("rationCard", newUPDCLEleConDTO.getRationCard()));
		params.add(new BasicNameValuePair("authorityLetter", newUPDCLEleConDTO.getAuthorityLetter()));
		params.add(new BasicNameValuePair("govtPhotoID", newUPDCLEleConDTO.getGovtPhotoID()));
		params.add(new BasicNameValuePair("certificate", newUPDCLEleConDTO.getCertificate()));
		params.add(new BasicNameValuePair("other", newUPDCLEleConDTO.getOther()));   
		params.add(new BasicNameValuePair("decleration", newUPDCLEleConDTO.getDecleration()));
		params.add(new BasicNameValuePair("nscForm", newUPDCLEleConDTO.getNscForm()));

		HttpClient client = JersyCall.getHttpsClient();

		if (client != null) {
			try {
				post.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse responsestack = client.execute(post);
				BufferedReader bf = new BufferedReader(new InputStreamReader(responsestack.getEntity().getContent()));

				String line = "";

				while ((line = bf.readLine()) != null) {
					finalOutput = finalOutput + line;

				}
				LOG.info(finalOutput);
			} catch (Exception e) {
				LOG.error("Exception on challan API");
			}
		}
		if (finalOutput != null && !finalOutput.isEmpty()) {
			// need to remove after the working of API
			try {

				Map<String, Object> hardCodeAferCorrectionMap = (Map<String, Object> ) Utility.getMapper().readValue(finalOutput, Map.class);
				response =(List<Map<String, Object>>)hardCodeAferCorrectionMap.get(MainetConstants.DATA);
			} catch (IOException e1) {
				Log.error("Error occured while Casting");
			}
		}
		//return response;

		List<String> errList = new ArrayList<String>();
		return errList;
	}

	//ITMS code added
	@SuppressWarnings("unchecked")
	public List<ItmsUpdateDTO> getItmsUpdate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0,
				new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> requestEntity = null;
		List<ItmsUpdateDTO> itmsDtos = new ArrayList<ItmsUpdateDTO>();
		List<Map<String,Object>> mapResponse=null;
		Object resp = null;
		String itmsUpateUrl = ApplicationSession.getInstance().getMessage("itms.update.url");
		requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Object> responseEntity = null;
		//no record found further
		if (itmsUpateUrl != null) {
			responseEntity = restTemplate.exchange(itmsUpateUrl, HttpMethod.POST, requestEntity, Object.class);
			LOG.info("The given itms update URI is " + itmsUpateUrl);
		} else {
			LOG.error("url is invalid itms please check " + itmsUpateUrl);
		}

		if (responseEntity != null) {
			mapResponse = ( List<Map<String,Object>>) responseEntity.getBody();
		}
		for(Map<String,Object> itmsMap:mapResponse) {
			ItmsUpdateDTO itmsDto = new ItmsUpdateDTO();
			itmsDto.setSignalSCN((String) itmsMap.get(MainetConstants.ItmsUpdate.signalSCN));
			itmsDto.setAccidentType((String) itmsMap.get(MainetConstants.ItmsUpdate.accidentType));
			itmsDto.setWeatherType((String) itmsMap.get(MainetConstants.ItmsUpdate.weatherType));
			itmsDto.setAreaType((String) itmsMap.get(MainetConstants.ItmsUpdate.areaType));
			itmsDto.setRoadType((String) itmsMap.get(MainetConstants.ItmsUpdate.roadType));
			itmsDto.setRoadFeature((String) itmsMap.get(MainetConstants.ItmsUpdate.roadFeature));

			itmsDtos.add(itmsDto);
		}

		if(itmsDtos!=null && !itmsDtos.isEmpty()) 
			this.setItmsUpdateAPIList(itmsDtos);			

		return itmsDtos;
	}

	public String getDoonWaterDetails(String connectionNo) {

		String reString =null;
		String urltocall = ApplicationSession.getInstance().getMessage("water.data.url");//"https://ujsbill.uk.gov.in/updn.asmx/RequestDSCLBill";

		String keyval = ApplicationSession.getInstance().getMessage("water.data.key");//@ujs#DSCLBill";
		String Consval = "DSCL";
		URL url = null;
		URLConnection urlConnection = null;
		InputStream in = null;
		BufferedReader lo_in = null;
		try {
			String conscode = connectionNo; // 0699970 input ConsCode
			String encryptval = encval(Consval, keyval, conscode);
			String auth_request = "auth=" + Consval + "|" + conscode + "|" + encryptval;
			url = new URL(urltocall);
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
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());


			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			urlConnection = url.openConnection();
			HttpURLConnection httpurlconnection = (HttpURLConnection) urlConnection;
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setDoInput(true);
			httpurlconnection.setUseCaches(false);
			httpurlconnection.setRequestMethod("POST");
			httpurlconnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

			httpurlconnection.setConnectTimeout(20000);
			httpurlconnection.setReadTimeout(20000);
			LOG.info("  request message=" + auth_request);
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

			LOG.info(" response message =" + respbuf.toString());
			JSONObject xmlJSONObj = XML.toJSONObject(respbuf.toString());
			String jsonPrettyPrintString = xmlJSONObj.toString();
			System.out.println(jsonPrettyPrintString);
			JSONObject json = new JSONObject(new JSONTokener(jsonPrettyPrintString));
			LinkedHashMap<String, Object> mm = (LinkedHashMap<String, Object>) new ObjectMapper().readValue(jsonPrettyPrintString, LinkedHashMap.class);
			LinkedHashMap<String, String> v1 = (LinkedHashMap<String, String>) mm.get("string");	
			reString = (String)v1.get("content");
			LOG.info(reString);
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

			//Compute the hmac on input data bytes
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


