package com.abm.mainet.integration.ws;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

/**
 *
 * This is a REST Client Utility to make REST call in order to consume exposed web service
 *
 */
@Component
public final class JersyCall {

    private static final String DMS_URL = "DMS.url";
    private static final String OBJECT_JSON_STRUCTURE = "object json structure {}";
    private static final String PROBLEM_WHILE_GETTING_COMPLETE_URI = "problem while getting complete URI:";
    private static final String JERSEY_URL = "jersey.url";
    private static final String FAIL = "FAIL";
    private static final String PROBLEM_WHILE_CONVERTING_SOURCE_DTO_TO_JSON_STRING = "problem while converting source dto to jsonString:";
    //private static final RestTemplate restTemplate;
    private static final HttpHeaders headers;
    private static final Logger LOGGER = Logger.getLogger(JersyCall.class);

    static {
        //restTemplate = new RestTemplate();
        //restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
        headers = new HttpHeaders();
        final List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setContentType(MediaType.APPLICATION_JSON);
        final List<Charset> acceptableCharsetTypes = new ArrayList<>();
        acceptableCharsetTypes.add(Charset.defaultCharset());
        acceptableCharsetTypes.add(Charset.forName(MainetConstants.UTF8));
        acceptableCharsetTypes.add(Charset.forName("ISO-8859-1"));
        headers.setAcceptCharset(acceptableCharsetTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        headers.set("characterEncoding", MainetConstants.UTF8);
        headers.setExpires(300000);
        // headers.set("Content-Type", "application/json; UTF-8");
        headers.set("accept-Charset", MainetConstants.UTF8);
        headers.set("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");

    }

    /**
     * to avoid object initialization
     */
    private JersyCall() {

    }

	/*
	 * public static Object callRestTemplateClient(final Object object, final String
	 * uri) { return callRestTemplateClient(object, uri, null); }
	 */

    /**
     * 
     * @param object
     * @param uri
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     * @throws URISyntaxException
     */
    public static Object callRestTemplateClient(final Object object, final String uri) {

        ResponseEntity<?> responseEntity = null;
        Object responseObj = null;
        //Boolean isReachable = false;
        boolean cxfCall = uri.startsWith("http");
        RestTemplate restTemplate = new RestTemplate();
        final String jerseyUrl = getJerseyUrl(JERSEY_URL);
        /*if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            isReachable = pingURL(jerseyUrl, 2000);
        }
        if (isReachable || cxfCall) {*/
            String completeUri = null;
            if (cxfCall) {
                completeUri = uri;
            } else {
                completeUri = jerseyUrl + uri;
            }
            completeUri=replaceHttpsToHttp(completeUri);
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            if (object != null) {
                try {
                    mapper.writeValueAsString(object);
                } catch (final IOException e) {
                    LOGGER.error(MainetConstants.ERROR_OCCURED, e);
                }
            }

            URI serverURI = null;
            try {
                serverURI = new URI(completeUri);
            } catch (final URISyntaxException e) {
                LOGGER.error(MainetConstants.ERROR_OCCURED, e);
            }
            try {

                HttpEntity<String> requestEntity = null;
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                if (object != null) {
                    requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);

                } else {
                    requestEntity = new HttpEntity<>(headers);
                }
                String result = mapper.writeValueAsString(object);
                LOGGER.info("Service call :: URI = {}  | INPUT = {}" + completeUri + result);
                responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);
                responseObj = responseEntity.getBody();
                result = mapper.writeValueAsString(responseObj);
                LOGGER.info("Service call OUTPUT :: {}"+ result);
            } catch (Exception e) {
                throw new FrameworkException(e);
            }
      //  }

        return responseObj;
    }

    private static String replaceHttpsToHttp(String completeUri) {
  		return completeUri.replaceFirst("^https", "http");
	}

	public static <T> Object callRestTemplateClient(final Object object, final String uri,
            final ParameterizedTypeReference<?> type) {

        ResponseEntity<?> responseEntity = null;
        Object responseObj = null;
       // Boolean isReachable = false;
        boolean cxfCall = uri.startsWith("http");
        RestTemplate restTemplate = new RestTemplate();
        final String jerseyUrl = getJerseyUrl(JERSEY_URL);
        /*if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            isReachable = true;
        }
        if (isReachable || cxfCall) {*/
            String completeUri = null;
            if (cxfCall) {
                completeUri = uri;
            } else {
                completeUri = jerseyUrl + uri;
            }
            completeUri=replaceHttpsToHttp(completeUri);
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            if (object != null) {
                try {
                    mapper.writeValueAsString(object);
                } catch (final IOException e) {
                    LOGGER.error(MainetConstants.ERROR_OCCURED, e);
                }
            }

            URI serverURI = null;
            try {
                serverURI = new URI(completeUri);
            } catch (final URISyntaxException e) {
                LOGGER.error(MainetConstants.ERROR_OCCURED, e);
            }
            try {

                HttpEntity<String> requestEntity = null;
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                if (object != null) {
                    requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);

                } else {
                    requestEntity = new HttpEntity<>(headers);
                }
                String result = mapper.writeValueAsString(object);
                LOGGER.info("Service call :: URI = {}  | INPUT = {}" + completeUri + result);
                if (type == null) {
                    responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);
                } else {
                    responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, type);
                }
                responseObj = responseEntity.getBody();
                result = mapper.writeValueAsString(responseObj);
                LOGGER.info("Service call OUTPUT :: {}" + result);
            } catch (Exception e) {
                throw new FrameworkException(e);
            }
       // }

        return responseObj;
    }

    /**
     * 
     * @param object
     * @param uri
     * @return
     */
    public static Object callRestTemplateClientForBRMS(final Object object, final String uri) {

        ResponseEntity<?> responseEntity = null;
        Object responseObj = null;
        try {
            final String brmsURL = getJerseyUrl("brms.absolutepath.url");

            final String completeUri = brmsURL + uri;
            final ObjectMapper mapper = new ObjectMapper();
            if (object != null) {
                mapper.writeValueAsString(object);
            }
            RestTemplate restTemplate = new RestTemplate();
            final URI serverURI = new URI(completeUri);
            HttpEntity<String> requestEntity = null;
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            
            if (object != null) {
                requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
            } else {
                requestEntity = new HttpEntity<>(headers);
            }
            String result = mapper.writeValueAsString(object);
            LOGGER.info("BRMS call :: URI = {} | INPUT {}" + completeUri + result);
            responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);
            responseObj = responseEntity.getBody();
            result = mapper.writeValueAsString(responseObj);
            LOGGER.info("BRMS call OUTPUT :: {}" + result);

        } catch (final JsonGenerationException jGE) {
            LOGGER.error(PROBLEM_WHILE_CONVERTING_SOURCE_DTO_TO_JSON_STRING, jGE);

        } catch (final JsonMappingException jME) {
            LOGGER.error(PROBLEM_WHILE_CONVERTING_SOURCE_DTO_TO_JSON_STRING, jME);
        } catch (final IOException ioException) {
            LOGGER.error(PROBLEM_WHILE_CONVERTING_SOURCE_DTO_TO_JSON_STRING, ioException);
        } catch (final URISyntaxException uriSyntaxException) {
            LOGGER.error(PROBLEM_WHILE_GETTING_COMPLETE_URI, uriSyntaxException);
        } catch (final HttpClientErrorException | HttpServerErrorException e) {
            final WSResponseDTO responseDTO = new WSResponseDTO();
            responseDTO.setWsStatus(FAIL);
            responseDTO.setErrorMessage(e.getResponseBodyAsString());
            responseObj = responseDTO;
            LOGGER.error("problem while web service call:" + e.getResponseBodyAsString(), e);
        } catch (final Exception ex) {
            LOGGER.error("problem ", ex);

        }

        return responseObj;
    }

    /**
     * 
     * @param requestDTO
     * @param uri
     * @return
     */
    @SuppressWarnings("unchecked")
    public static WSResponseDTO callBRMS(final WSRequestDTO requestDTO, final String uri) {
        WSResponseDTO responseDTO = null;

        try {
            final Object response = JersyCall.callRestTemplateClientForBRMS(requestDTO, uri);

            if (response instanceof WSResponseDTO) {
                responseDTO = (WSResponseDTO) response;
            } else {
                final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) response;
                final String jsonString = new JSONObject(responseVo).toString();
                responseDTO = new ObjectMapper().readValue(jsonString,
                        WSResponseDTO.class);
            }

        } catch (final Exception e) {
            responseDTO = new WSResponseDTO();
            responseDTO.setWsStatus(FAIL);
            LOGGER.error("Error Occurred while BRMS call", e);
        }

        return responseDTO;
    }

    /**
     * use this method to cast respective response model which is about to receive from BRMS call
     * @param response : pass WSResponse object received from BRMS call
     * @param clazz : pass expected response Class to cast
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz) {

        Object dataModel = null;
        LinkedHashMap<Long, Object> responseMap = null;
        final List<Object> dataModelList = new ArrayList<>();
        try {
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> list = (List<?>) response.getResponseObj();
                for (final Object object : list) {
                    responseMap = (LinkedHashMap<Long, Object>) object;
                    final String jsonString = new JSONObject(responseMap).toString();
                    dataModel = new ObjectMapper().readValue(jsonString, clazz);
                    dataModelList.add(dataModel);
                }
            }

        } catch (final IOException e) {
            LOGGER.error("Error Occurred during cast response object while BRMS call is success!", e);
        }

        return dataModelList;

    }

    /**
     * use this method to cast dataModel if WSResponseDTO's responseObj field contain different type of dataModel object list
     * @param response
     * @param clazz : pass desired class to cast
     * @param position : pass index position
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

        Object dataModel = null;
        LinkedHashMap<Long, Object> responseMap = null;
        final List<Object> dataModelList = new ArrayList<>();
        try {
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> list = (List<?>) response.getResponseObj();
                final Object object = list.get(position);
                responseMap = (LinkedHashMap<Long, Object>) object;
                final String jsonString = new JSONObject(responseMap).toString();
                dataModel = new ObjectMapper().readValue(jsonString, clazz);
                dataModelList.add(dataModel);

            }

        } catch (final IOException e) {
            LOGGER.error("Error Occurred during cast response object while BRMS call is success!", e);
        }

        return dataModelList;

    }

    public static String getJerseyUrl(final String urlKey) {

        return ApplicationSession.getInstance().getMessage(urlKey);

    }

    /**
     *
     * @param responseEntity
     * @param targetClass
     * @return Use this method to cast the response into respective DTO
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static <T> T castResponse(final ResponseEntity<?> responseEntity, final Class<T> targetClass) throws IOException {

        T dataModel = null;
        try {
            final LinkedHashMap<Long, Object> responseMap = (LinkedHashMap<Long, Object>) responseEntity.getBody();
            final String jsonString = new JSONObject(responseMap).toString();
            dataModel = new ObjectMapper().readValue(jsonString, targetClass);

        } catch (final IOException e) {
            LOGGER.error("Error Occurred during cast responseEntity received while BRMS call is success!", e);
            throw new IOException("Error Occurred during cast responseEntity received while BRMS call is success!", e);
        }

        return dataModel;

    }

   
   /* public static boolean pingURL(String url, final int timeout) {
        String replaceUrl = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL
                                                                // certificates.
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(replaceUrl).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            final int responseCode = connection.getResponseCode();
            return ((200 <= responseCode) && (responseCode <= 399));
        } catch (final IOException exception) {
            LOGGER.error(MainetConstants.ERROR_OCCURED, exception);
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }*/

    public static Object callRestTemplateClientForJBPM(final Object object, final String uri) {

        ResponseEntity<?> responseEntity = null;
        Object responseObj = null;
        Boolean isReachable = false;
        RestTemplate restTemplate = new RestTemplate();
        final String jerseyUrl = getJerseyUrl(JERSEY_URL);
        boolean cxfCall = uri.startsWith("http");
        /*if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            isReachable = pingURL(jerseyUrl, 2000);
        }
        if (isReachable) {*/
            String completeUri = null;
            if (cxfCall) {
                completeUri = uri;
            } else {
                completeUri = jerseyUrl + uri;
            }
            completeUri=replaceHttpsToHttp(completeUri);
            final ObjectMapper mapper = new ObjectMapper();
            try {

                mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
                if (object != null) {
                    mapper.writeValueAsString(object);
                }

                final URI serverURI = new URI(completeUri);
                HttpEntity<String> requestEntity = null;
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                
                if (object != null) {
                    requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
                } else {
                    requestEntity = new HttpEntity<>(headers);
                }

                String result = mapper.writeValueAsString(object);
                LOGGER.info("JBPM call :: URI = {} | INPUT = {}" + serverURI + result);
                responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);
                responseObj = responseEntity.getBody();
                result = mapper.writeValueAsString(responseObj);
                LOGGER.info("JBPM call OUTPUT :: {}" + result);
            } catch (Exception e) {
                throw new FrameworkException(e);
            }
       // }

        return responseObj;
    }

    public static ResponseEntity<?> callAnyRestTemplateClient(final Object object, final String uri) {

        ResponseEntity<?> responseEntity = null;
        Boolean isReachable = false;
        HttpEntity<String> requestEntity = null;
        URI serverURI = null;
        Object responseObj = null;
        try {
            final ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            final String jerseyUrl = getJerseyUrl(JERSEY_URL);

            /*if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
                isReachable = pingURL(jerseyUrl, 2000);
            }
            if (isReachable) {*/
                final String completeUri = jerseyUrl + uri;
                serverURI = new URI(completeUri);
                if (object != null) {

                    mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
                    mapper.writeValueAsString(object);
                    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
                    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                    requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
                } else {
                    requestEntity = new HttpEntity<>(headers);
                }
                String result = mapper.writeValueAsString(object);
                LOGGER.info("REST call :: URI = {} | INPUT = {}" + completeUri + result);

                responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);

                responseObj = responseEntity.getBody();
                result = mapper.writeValueAsString(responseObj);
                LOGGER.info("REST call OUTPUT :: {}"+ result);
           // }

        } catch (final IOException e) {
            LOGGER.error(PROBLEM_WHILE_CONVERTING_SOURCE_DTO_TO_JSON_STRING, e);

        } catch (final URISyntaxException uriSyntaxException) {
            LOGGER.error(PROBLEM_WHILE_GETTING_COMPLETE_URI, uriSyntaxException);
        } catch (final HttpClientErrorException | HttpServerErrorException e) {
            final WSResponseDTO responseDTO = new WSResponseDTO();
            responseDTO.setWsStatus(FAIL);
            responseDTO.setErrorMessage(e.getResponseBodyAsString());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getResponseBodyAsString());
            LOGGER.error("problem while web service call:" + e.getResponseBodyAsString(), e);
        } catch (final Exception ex) {
            LOGGER.error(PROBLEM_WHILE_GETTING_COMPLETE_URI, ex);
        }
        return responseEntity;

    }

    public static String callDmsCreateFolder(final String folderPath) {
    	RestTemplate restTemplate = new RestTemplate();
        final String jerseyUrl = getJerseyUrl(DMS_URL);
        final String getUrl = jerseyUrl + ServiceEndpoints.DMS_CREATE_FOLDER_URL + "?protocol="
                + MainetConstants.DmsClientType.DMS_CLIENT_SOAP + "&folderpath=" + folderPath;
        LOGGER.info(getUrl);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        final ResponseEntity<String> postResponse = restTemplate.exchange(getUrl, HttpMethod.POST, null, String.class);
        LOGGER.info("Response for Post Request: " + postResponse.getBody());
        return postResponse.getBody();
    }

    /**
     * 
     * @param object
     * @param Uri
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     * @throws URISyntaxException
     */
    public static Object dmsCreateDoc(final Object object) {
        final String jerseyUrl = getJerseyUrl(DMS_URL);
        Object result = null;

        try {
            final String completeUri = jerseyUrl + ServiceEndpoints.DMS_CREATE_DOC_URL;
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            if (object != null) {
                mapper.writeValueAsString(object);
            }
            URI serverURI = null;
            serverURI = new URI(completeUri);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> requestEntity = null;
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            
            if (object != null) {
                requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
            } else {
                requestEntity = new HttpEntity<>(headers);
            }
            LOGGER.info(OBJECT_JSON_STRUCTURE + requestEntity);

            final ResponseEntity<String> postResponse = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity,
                    String.class);
            result = postResponse.getBody();
        } catch (final Exception e) {
            throw new FrameworkException(e);
        }
        return result;

    }

    public static Object dmsGetDoc(final Object object) {
        final String jerseyUrl = getJerseyUrl(DMS_URL);
        Object result = null;

        try {
            final String completeUri = jerseyUrl + ServiceEndpoints.DMS_GET_DOC_BY_ID_URL;
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            if (object != null) {
                mapper.writeValueAsString(object);
            }
            URI serverURI = null;
            serverURI = new URI(completeUri);
            HttpEntity<String> requestEntity = null;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            if (object != null) {
                requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
            } else {
                requestEntity = new HttpEntity<>(headers);
            }

            String jsonMsg = mapper.writeValueAsString(object);
            LOGGER.info("DMS call :: URI = {}  | INPUT = {}" + completeUri + jsonMsg);
            final ResponseEntity<Object> postResponse = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity,
                    Object.class);

            result = postResponse.getBody();

            jsonMsg = mapper.writeValueAsString(result);
            LOGGER.info("JBPM call OUTPUT :: {}" + jsonMsg);
        } catch (final Exception e) {
            throw new FrameworkException(e);
        }
        return result;

    }

    /**
     *
     * @param requestDTO
     * @param uri
     * @return WSResponseDTO
     */
    @SuppressWarnings("unchecked")
    public static WSResponseDTO callServiceBRMSRestClient(final WSRequestDTO requestDTO, final String uri) {
        WSResponseDTO responseDTO = null;
        try {
            String result = requestDTO.toString();
            LOGGER.info("Service BRMS call :: URI = {}  | INPUT = {}" + uri + result);
            final Object response = callRestTemplateClient(requestDTO, uri);
            if (response instanceof WSResponseDTO) {
                responseDTO = (WSResponseDTO) response;
            } else {
                final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) response;
                final String jsonString = new JSONObject(responseVo).toString();
                responseDTO = new ObjectMapper().readValue(jsonString,
                        WSResponseDTO.class);
            }
            result = responseDTO.toString();
            LOGGER.info("Service BRMS call OUTPUT :: {}" + result);
        } catch (final Exception e) {
            responseDTO = new WSResponseDTO();
            responseDTO.setWsStatus(FAIL);
            LOGGER.error("Error Occurred while Service BRMS call", e);
        }
        return responseDTO;
    }
    public static Object callRestTeplateWithHeaders(final Object object, final String completeUri,HttpHeaders customHeaders,HttpMethod methodType) {

        ResponseEntity<?> responseEntity = null;
        Object responseObj = null;
        //Boolean isReachable = false;
        List<Map<String,Object>> mapResponse=null;
        HttpHeaders requiredHeaders;
        if(customHeaders !=null) {
        	requiredHeaders = customHeaders;
        	LOGGER.info("Custom Headers----------------------------------->"+customHeaders);
        }else {
        	requiredHeaders = headers;
        }
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            if (object != null) {
                try {
                    mapper.writeValueAsString(object);
                } catch (final IOException e) {
                    LOGGER.error(MainetConstants.ERROR_OCCURED, e);
                }
            }

            URI serverURI = null;
            try {
                serverURI = new URI(completeUri);
            } catch (final URISyntaxException e) {
                LOGGER.error(MainetConstants.ERROR_OCCURED, e);
            }
            try {
            	RestTemplate restTemplate = new RestTemplate();
            	LOGGER.info("Method Type----------------------------------->"+methodType);
                HttpEntity<String> requestEntity = null;
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                if (object != null) {
                    requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), requiredHeaders);

                } else {
                    requestEntity = new HttpEntity<>(requiredHeaders);
                }
                String result = mapper.writeValueAsString(object);
                LOGGER.info("Service call :: URI = {}  | INPUT = {}" + completeUri + result);
                if(methodType.equals(HttpMethod.GET)) {
                	LOGGER.info("URL----------------------------------->"+serverURI);
                	responseEntity = restTemplate.exchange(serverURI, HttpMethod.GET, requestEntity,
							String.class);
                	if(responseEntity !=null ) {
                		responseObj = (Object)responseEntity.getBody();	
                		LOGGER.info("Response Object ----------------------------------->"+responseEntity.getStatusCode());
                	}
                	
                }else if(methodType.equals(HttpMethod.POST)) {
                	 responseObj = restTemplate.postForObject(serverURI, requestEntity, Object.class);
                }else {
                	//need to add based on requirement
                }
                result = mapper.writeValueAsString(responseObj);
                LOGGER.info("Service call OUTPUT :: {}"+ result);
            } catch (Exception e) {
                throw new FrameworkException(e);
            }
       

        return responseObj;
    }
    
    public static HttpClient getHttpsClient() {
    	HttpClient clientstack = null;
    	try {
    	SSLContext context = SSLContext.getInstance("TLSv1.2");
    	TrustManager[] trustManager = new TrustManager[] {
    	    new X509TrustManager() {
    	       public X509Certificate[] getAcceptedIssuers() {
    	           return new X509Certificate[0];
    	       }
    	       public void checkClientTrusted(X509Certificate[] certificate, String str) {}
    	       public void checkServerTrusted(X509Certificate[] certificate, String str) {}
    	    }
    	};
    	context.init(null, trustManager, new SecureRandom());

    	SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(context,
    	        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

    	 clientstack = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();
    	}catch(Exception e) {
    		LOGGER.error("Https client error");
    	}
    	return clientstack;

    }
}
