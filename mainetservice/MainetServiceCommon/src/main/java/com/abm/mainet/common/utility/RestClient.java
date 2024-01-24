package com.abm.mainet.common.utility;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.exception.service.SaveExceptionDetails;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;

/**
 * This is a REST Client Utility to make REST call in order to consume exposed web service
 *
 * @author Vivek.Kumar
 * @since 03 June 2016
 */
public final class RestClient {

    // private static final String DMS_URL = "DMS.url";
    private static final String OBJECT_JSON_STRUCTURE = "object json structure {}";
    //private static final RestTemplate restTemplate;
    private static final HttpHeaders headers;
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    private static RestClient instance = new RestClient();
    
    // private static Properties PROPERTIES = null;

    static {
        //restTemplate = new RestTemplate();
        //restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
        headers = new HttpHeaders();
        final List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        
        headers.setContentType(MediaType.APPLICATION_JSON);
      
        final List<Charset> acceptableCharsetTypes = new ArrayList<>();
        acceptableCharsetTypes.add(Charset.defaultCharset());
        acceptableCharsetTypes.add(Charset.forName(MainetConstants.UTF_8));
        acceptableCharsetTypes.add(Charset.forName(MainetConstants.ISO_8859_1));
        headers.setAcceptCharset(acceptableCharsetTypes);
        // headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        headers.set(MainetConstants.CHARACTER_ENCODING, MainetConstants.UTF_8);
        headers.setExpires(300000);
        /* headers.set(MainetConstants.CONTENT_TYPE, MainetConstants.APPLICATION_JSON_UTF_8); */
       // headers.set(MainetConstants.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)"); As per mayank said
    }

    /**
     * prevent object initialization
     */
    private RestClient() {

    }

    /**
     *
     * @param object
     * @param Uri
     * @return
     */
    public static Object callRestTemplateClientForBRMS(final Object object, final String Uri) {

        ResponseEntity<?> responseEntity = null;
        Object responseObj = null;
        HttpEntity<String> requestEntity = null;
        URI serverURI = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
        	
            final ObjectMapper mapper = new ObjectMapper();
            serverURI = new URI(Uri);
            if (object != null) {
                mapper.writeValueAsString(object);
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
            } else {
                requestEntity = new HttpEntity<>(headers);
            }

            String result = mapper.writeValueAsString(object);

            LOGGER.info("BRMS call :: URI = {}  | INPUT = {}", Uri, result);
            responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);

            responseObj = responseEntity.getBody();
            result = mapper.writeValueAsString(responseObj);
            LOGGER.info("BRMS call OUTPUT :: {}", result);

        } catch (final IOException e) {
            LOGGER.error("problem while converting source dto to jsonString:", e);
            getInstance().saveExceptionDetails(e, "callRestTemplateClientForBRMS", Uri, "RestClient");

        } catch (final URISyntaxException uriSyntaxException) {
            LOGGER.error("problem while getting complete URI:", uriSyntaxException);
            getInstance().saveExceptionDetails(uriSyntaxException, "callRestTemplateClientForBRMS", Uri, "RestClient");
         
        } catch (final HttpClientErrorException | HttpServerErrorException e) {
            final WSResponseDTO responseDTO = new WSResponseDTO();
            responseDTO.setWsStatus("FAIL");
            responseDTO.setErrorMessage(e.getResponseBodyAsString());
            responseObj = responseDTO;
            LOGGER.error("problem while web service call:" + e.getResponseBodyAsString(), e);
            getInstance().saveExceptionDetails(e, "callRestTemplateClientForBRMS", Uri, "RestClient");
            throw e;
        } catch (final Exception ex) {
            LOGGER.error("problem while getting complete URI:", ex);
            getInstance().saveExceptionDetails(ex, "callRestTemplateClientForBRMS", Uri, "RestClient");
            throw ex;
        } catch (final Throwable ex) {
            LOGGER.error("problem while getting complete URI:", ex.getCause());
            getInstance().saveExceptionDetails(ex, "callRestTemplateClientForBRMS", Uri, "RestClient");
            throw ex;
        }


        return responseObj;
    }

    public static ResponseEntity<?> callRestTemplateClient(final Object object, final String Uri) {

        ResponseEntity<?> responseEntity = null;
        HttpEntity<String> requestEntity = null;
        URI serverURI = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            final ObjectMapper mapper = new ObjectMapper();
            
            serverURI = new URI(Uri);
            if (object != null) {
                mapper.writeValueAsString(object);
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
            } else {
                requestEntity = new HttpEntity<>(headers);
            }

            String result = mapper.writeValueAsString(object);
            LOGGER.info("Service call :: URI = {}  | INPUT = {}", Uri, result);

            responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);
            result = mapper.writeValueAsString(responseEntity.getBody());
            LOGGER.info("Service call OUTPUT :: {}", result);

        } catch (final IOException e) {
            LOGGER.error("problem while converting source dto to jsonString:", e);
            getInstance().saveExceptionDetails(e, "callRestTemplateClient", Uri, "RestClient");

        } catch (final URISyntaxException uriSyntaxException) {
            LOGGER.error("problem while getting complete URI:", uriSyntaxException);
            getInstance().saveExceptionDetails(uriSyntaxException, "callRestTemplateClient", Uri, "RestClient");
           
        } catch (final HttpClientErrorException | HttpServerErrorException e) {
            final WSResponseDTO responseDTO = new WSResponseDTO();
            responseDTO.setWsStatus("FAIL");
            responseDTO.setErrorMessage(e.getResponseBodyAsString());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
            LOGGER.error("problem while web service call:" + e.getResponseBodyAsString(), e);
            getInstance().saveExceptionDetails(e, "callRestTemplateClient", Uri, "RestClient");
            throw e;
        } catch (final Exception ex) {
            LOGGER.error("problem while getting complete URI:", ex);
            getInstance().saveExceptionDetails(ex, "callRestTemplateClient", Uri, "RestClient");
            throw ex;
        } catch (final Throwable ex) {
            LOGGER.error("problem while getting complete URI:", ex.getCause());
            getInstance().saveExceptionDetails(ex, "callRestTemplateClient", Uri, "RestClient");
            throw ex;
        }

        return responseEntity;
    }
    @SuppressWarnings("unchecked")
	public static ResponseEntity<?> callRestTemplateClient(final Object object, final String Uri,final HttpMethod method,final Class cl) {
    	
        ResponseEntity<?> responseEntity = null;
        HttpEntity<String> requestEntity = null;
        URI serverURI = null;
        
        RestTemplate restTemplate = new RestTemplate();
        try {
            final ObjectMapper mapper = new ObjectMapper();
            serverURI = new URI(Uri);
            if (object != null && !method.equals(HttpMethod.GET)) {
            	LOGGER.info("Executing Post method");
                mapper.writeValueAsString(object);
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
                
            } else {
            	LOGGER.info("Executing Get method");
                requestEntity = new HttpEntity<>(headers);
            }

            String result = mapper.writeValueAsString(headers);
            LOGGER.info("Service call :: URI = {}  | INPUT = {}", Uri);
            LOGGER.info("Service call :: Result = {}  | INPUT = {}", result);
    
            responseEntity = restTemplate.exchange(serverURI, method, requestEntity, cl);
            result = mapper.writeValueAsString(responseEntity.getBody());
            LOGGER.info("Service call OUTPUT :: {}", result);

        } catch (final IOException e) {
            LOGGER.error("IOException  problem while converting source dto to jsonString:", e);
            getInstance().saveExceptionDetails(e, "callRestTemplateClient", Uri, "RestClient");

        } catch (final URISyntaxException uriSyntaxException) {
            LOGGER.error("uriSyntaxException problem while getting complete URI:", uriSyntaxException);
            getInstance().saveExceptionDetails(uriSyntaxException, "callRestTemplateClient", Uri, "RestClient");
           
        } catch (final HttpClientErrorException | HttpServerErrorException e) {
            final WSResponseDTO responseDTO = new WSResponseDTO();
            responseDTO.setWsStatus("FAIL");
            responseDTO.setErrorMessage(e.getResponseBodyAsString());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
            LOGGER.error("HttpServerErrorException  problem while web service call:" + e.getResponseBodyAsString(), e);
            getInstance().saveExceptionDetails(e, "callRestTemplateClient", Uri, "RestClient");
            throw e;
        } catch (final Exception ex) {
            LOGGER.error("Generic problem while getting complete URI:", ex);
            getInstance().saveExceptionDetails(ex, "callRestTemplateClient", Uri, "RestClient");
            throw ex;
        } catch (final Throwable ex) {
            LOGGER.error("problem while getting complete URI:", ex.getCause());
            getInstance().saveExceptionDetails(ex, "callRestTemplateClient", Uri, "RestClient");
            throw ex;
        }

        return responseEntity;
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

            String result = requestDTO.toString();

            LOGGER.info("BRMS call :: URI = {}  | INPUT = {}", uri, result);

            final Object response = RestClient.callRestTemplateClientForBRMS(requestDTO, uri);

            if (response instanceof WSResponseDTO) {
                responseDTO = (WSResponseDTO) response;
            } else {
                final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) response;
                final String jsonString = new JSONObject(responseVo).toString();
                responseDTO = new ObjectMapper().readValue(jsonString,
                        WSResponseDTO.class);
            }

            result = responseDTO.toString();
            LOGGER.info("BRMS call OUTPUT :: {}", result);

        } catch (final Exception e) {
            responseDTO = new WSResponseDTO();
            responseDTO.setWsStatus(MainetConstants.Req_Status.FAIL);
            LOGGER.error("Error Occurred while BRMS call", e);
            getInstance().saveExceptionDetails(e, "callBRMS", uri, "RestClient");
        }catch (final Throwable e) {
            responseDTO = new WSResponseDTO();
            responseDTO.setWsStatus(MainetConstants.Req_Status.FAIL);
            LOGGER.error("Error Occurred while BRMS call", e.getCause());
            getInstance().saveExceptionDetails(e, "callBRMS", uri, "RestClient");
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
            if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> list = (List<?>) response.getResponseObj();
                for (final Object object : list) {
                	if (object instanceof Map) {
                		responseMap = (LinkedHashMap<Long, Object>) object;
                        final String jsonString = new JSONObject(responseMap).toString();
                        dataModel = new ObjectMapper().readValue(jsonString, clazz);
                        dataModelList.add(dataModel);
                    } else {
                    	 dataModelList.add(object);
                    }
                	
                    
                }
            }

        } catch (final IOException e) {
            LOGGER.error("Error Occurred during cast response object while BRMS call is success!", e);
            getInstance().saveExceptionDetails(e, "castResponse", null, "RestClient");
        }

        return dataModelList;

    }

    /**
     *
     * @param responseEntity
     * @param targetClass
     * @return Use this method to cast the response into respective DTO
     */
    @SuppressWarnings("unchecked")
    public static Object castResponse(final ResponseEntity<?> responseEntity, final Class<?> targetClass) {

        Object dataModel = null;
        try {
            final LinkedHashMap<Long, Object> responseMap = (LinkedHashMap<Long, Object>) responseEntity.getBody();
            final String jsonString = new JSONObject(responseMap).toString();
            dataModel = new ObjectMapper().readValue(jsonString, targetClass);

        } catch (final IOException e) {
            LOGGER.error("Error Occurred during cast responseEntity received while BRMS call is success!", e);
            getInstance().saveExceptionDetails(e, "castResponse", null, "RestClient");
        }

        return dataModel;

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
            if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> list = (List<?>) response.getResponseObj();
                final Object object = list.get(position);
                responseMap = (LinkedHashMap<Long, Object>) object;
                final String jsonString = new JSONObject(responseMap).toString();
                dataModel = new ObjectMapper().readValue(jsonString, clazz);
                dataModelList.add(dataModel);
            }

        } catch (final IOException e) {
            LOGGER.error("Error Occurred during cast response object while BRMS call is success!", e);
            getInstance().saveExceptionDetails(e, "castResponse", null, "RestClient");
        }

        return dataModelList;

    }

    public static Object callJbossBPM(final Object object, final String Uri) throws Exception {

        ResponseEntity<?> responseEntity = null;
        Object responseObj = null;
        HttpEntity<String> requestEntity = null;
        URI serverURI = null;
        
        final ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        String result = null;
        serverURI = new URI(Uri);
        if (object != null) {
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            result = mapper.writeValueAsString(object);
            LOGGER.info("JbossBPM call :: URI = {}  | INPUT = {}", Uri, result);
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
        } else {
//            LOGGER.warn("JbossBPM call :: URI = {}  | INPUT = {}", Uri, result);
            requestEntity = new HttpEntity<>(headers);
        }
        responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);
        if(responseEntity != null){
            HttpHeaders headers = responseEntity.getHeaders();
            List<String> wfstatusList = headers.get("status");
            if(CollectionUtils.isNotEmpty(wfstatusList)){
        	String wfstatus = wfstatusList.get(0);
        	if(StringUtils.equalsIgnoreCase(MainetConstants.COMMON_STATUS.FAIL, wfstatus)){
        	    List<String> wferrCodeList = headers.get("errCode");
        	    List<String> wferrMsgList = headers.get("errMsg");
        	    throw new FrameworkException(wferrMsgList.get(0), new FrameworkException(wferrCodeList.get(0)));    
        	}
            }
        }
        responseObj = responseEntity.getBody();
        result = mapper.writeValueAsString(responseObj);
        LOGGER.info("JbossBPM call OUTPUT :: {}", result);
        return responseObj;
    }

    /*
     * Generic method created to post single object data with dynamic URI
     */
    public static ResponseEntity<?> postData(final Object requestObject, final String uri)
            throws JsonGenerationException, JsonMappingException, IOException, URISyntaxException {

        URI serverURI = null;
        ResponseEntity<?> responseEntity = null;
        HttpEntity<String> requestEntity = null;
        final ObjectMapper mapper = new ObjectMapper();
        Object object = requestObject;
        String result = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            serverURI = new URI(uri.toString());
            if (object != null) {
                mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
                result = mapper.writeValueAsString(object);
                LOGGER.info("Portal call :: URI = {}  | INPUT = {}", uri, result);
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
            } else {
                LOGGER.error("Portal call :: URI = {}  | INPUT = {}", uri, result);
                requestEntity = new HttpEntity<>(headers);
            }
            responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);

        } catch (final IOException e) {
            LOGGER.error("problem while converting source dto to jsonString:", e);
            getInstance().saveExceptionDetails(e, "postData", uri, "RestClient");
        } catch (final URISyntaxException uriSyntaxException) {
            LOGGER.error("problem while getting complete URI:", uriSyntaxException);
            getInstance().saveExceptionDetails(uriSyntaxException, "postData", uri, "RestClient");
        } catch (final Exception ex) {
            LOGGER.error("problem while getting complete URI:", ex);
            getInstance().saveExceptionDetails(ex, "postData", uri, "RestClient");
        } catch (final Throwable ex) {
            LOGGER.error("problem while getting complete URI:", ex.getCause());
            getInstance().saveExceptionDetails(ex, "postData", uri, "RestClient");
        }
        result = mapper.writeValueAsString(responseEntity.getBody());
        LOGGER.info("Portal call OUTPUT :: {}", result);
        return responseEntity;
    }

    public static String callDmsCreateFolder(final String folderPath) {
        // final String jerseyUrl = getJerseyUrl(DMS_URL);/ jerseyUrl +
        final String getUrl = ServiceEndpoints.DMS_CREATE_FOLDER_URL + "?folderPath=" + folderPath;
        LOGGER.info(getUrl);
        HttpEntity<String> requestEntity = null;
        requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
        final ResponseEntity<String> postResponse = restTemplate.exchange(getUrl, HttpMethod.POST, requestEntity, String.class);
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
        // final String jerseyUrl = getJerseyUrl(DMS_URL);
        Object result = null;
        URI serverURI = null;
        try {
            // final String completeUri = jerseyUrl + ServiceEndpoints.DMS_CREATE_DOC_URL;
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            if (object != null) {
                mapper.writeValueAsString(object);
            }
            serverURI = new URI(ServiceEndpoints.DMS_CREATE_DOC_URL);
            HttpEntity<String> requestEntity = null;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
            if (object != null) {
                requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);
            } else {
                requestEntity = new HttpEntity<>(headers);
            }
            LOGGER.info(OBJECT_JSON_STRUCTURE, requestEntity);

            final ResponseEntity<String> postResponse = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity,
                    String.class);
            result = postResponse.getBody();
        } catch (final Exception e) {
        	getInstance().saveExceptionDetails(e, "dmsCreateDoc", serverURI.toString() , "RestClient");
            throw new FrameworkException(e);
        }
        return result;

    }

    public static String dmsGetDoc( String docId) {

    	String jsonMsg  = null;
    	URI serverURI = null;
        try {      	
            String completeUri = ServiceEndpoints.DMS_GET_DOC_BY_ID_URL+MainetConstants.WINDOWS_SLASH+docId;          
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            
            serverURI = new URI(completeUri);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
            LOGGER.info("DMS call :: URI = {}  | INPUT = {}", completeUri);
            final ResponseEntity<String> postResponse = restTemplate.getForEntity(serverURI, String.class);
            jsonMsg  =(String)postResponse.getBody();

            LOGGER.info("JBPM call OUTPUT :: {}", jsonMsg);
        } catch (final Exception e) {
        	getInstance().saveExceptionDetails(e, "dmsGetDoc", serverURI.toString() , "RestClient");
            throw new FrameworkException(e);
        }
        return jsonMsg;

    }

    /*
     * private static String getJerseyUrl(final String urlKey) { return ApplicationSession.getInstance().getMessage(urlKey); }
     */

    @SuppressWarnings("unchecked")
    public static Object castResponseToRate(final WSResponseDTO result, final Class<?> clazz) {
        Object dataModel = null;
        LinkedHashMap<Long, Object> responseMap = null;
        final List<Object> dataModelList = new ArrayList<>();
        try {
            responseMap = (LinkedHashMap<Long, Object>) result.getResponseObj();
            final String jsonString = new JSONObject(responseMap).toString();
            dataModel = new ObjectMapper().readValue(jsonString, clazz);
            dataModelList.add(dataModel);
        } catch (final IOException e) {
            LOGGER.error("Error Occurred during cast response object while BRMS call is success!", e);
            getInstance().saveExceptionDetails(e, "castResponseToRate", null , "RestClient");
        }
        return dataModelList.get(0);
    }
    
    
    public static RestClient getInstance() {
		return instance;
	}
    
    private void saveExceptionDetails(Throwable e, String methodName, String url, String fileName) {
    	ApplicationContextProvider.getApplicationContext().getBean(SaveExceptionDetails.class).saveExcepionDetails(e, methodName, url, fileName);
    }
    
	public static ResponseEntity<?> callRestTemplateClientForMultipart(MultiValueMap<String, Object> body, final String uri,final HttpMethod method,HttpHeaders headers) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
		ResponseEntity<String> response = null;
		try {
		 HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		 response = restTemplate.postForEntity(uri, requestEntity, String.class);
		LOGGER.info("response callRestTemplateClientForMultipart"+response);
		}catch (final Exception e) {
            LOGGER.error("Error Occurred during callRestTemplateClientForMultipart", e);
            getInstance().saveExceptionDetails(e, "callRestTemplateClientForMultipart", null , "RestClient");
        }
        return response;
    }
	 @SuppressWarnings("unchecked")
		public static ResponseEntity<?> callRestTemplateClientWithHeaders(final Object object, final String Uri,final HttpMethod method,final Class cl, HttpHeaders customHeaders) {
		 
	        ResponseEntity<?> responseEntity = null;
	        HttpEntity<String> requestEntity = null;
	        URI serverURI = null;
	        
	        RestTemplate restTemplate = new RestTemplate();
	        try {
	            final ObjectMapper mapper = new ObjectMapper();
	            serverURI = new URI(Uri);
	            if (!method.equals(HttpMethod.GET)) {
	            	LOGGER.info("Executing Post method");
	                mapper.writeValueAsString(object);
	                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
	                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
	                requestEntity = new HttpEntity<>(((object==null)?"{}":mapper.writeValueAsString(object)), customHeaders) ;	                
	                LOGGER.info("requestEntity before request for post"+mapper.writeValueAsString(requestEntity));
	            } else {
	            	LOGGER.info("Executing Get method");
	                requestEntity = new HttpEntity<>(customHeaders);
	                LOGGER.info("requestEntity before request for get"+ mapper.writeValueAsString(requestEntity));
	            }

	            String result = mapper.writeValueAsString(customHeaders);
	            LOGGER.info("Service call :: URI = {}  | INPUT = {}", Uri);
	            LOGGER.info("Service call :: Result = {}  | INPUT = {}", result);
	    
	            responseEntity = restTemplate.exchange(serverURI, method, requestEntity, cl);
	            result = mapper.writeValueAsString(responseEntity.getBody());
	            LOGGER.info("Service call OUTPUT :: {}", result);
	            LOGGER.info("Service call Response :: "+ result);

	        } catch (final IOException e) {
	            LOGGER.error("IOException  problem while converting source dto to jsonString:", e);
	            getInstance().saveExceptionDetails(e, "callRestTemplateClient", Uri, "RestClient");

	        } catch (final URISyntaxException uriSyntaxException) {
	            LOGGER.error("uriSyntaxException problem while getting complete URI:", uriSyntaxException);
	            getInstance().saveExceptionDetails(uriSyntaxException, "callRestTemplateClient", Uri, "RestClient");
	           
	        } catch (final HttpClientErrorException | HttpServerErrorException e) {
	            final WSResponseDTO responseDTO = new WSResponseDTO();
	            responseDTO.setWsStatus("FAIL");
	            responseDTO.setErrorMessage(e.getResponseBodyAsString());
	            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
	            LOGGER.error("HttpServerErrorException  problem while web service call:" + e.getResponseBodyAsString(), e);
	            getInstance().saveExceptionDetails(e, "callRestTemplateClient", Uri, "RestClient");
	            throw e;
	        } catch (final Exception ex) {
	            LOGGER.error("Generic problem while getting complete URI:", ex);
	            getInstance().saveExceptionDetails(ex, "callRestTemplateClient", Uri, "RestClient");
	            throw ex;
	        } catch (final Throwable ex) {
	            LOGGER.error("problem while getting complete URI:", ex.getCause());
	            getInstance().saveExceptionDetails(ex, "callRestTemplateClient", Uri, "RestClient");
	            throw ex;
	        }

	        return responseEntity;
	    }
}
