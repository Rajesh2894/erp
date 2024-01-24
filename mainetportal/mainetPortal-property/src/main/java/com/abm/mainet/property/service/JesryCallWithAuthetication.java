package com.abm.mainet.property.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;

public class JesryCallWithAuthetication {   
    
    private static final String JERSEY_URL = "jersey.url";
    private static final RestTemplate restTemplate;
    private static final HttpHeaders headers;
    private static final Logger LOGGER = LoggerFactory.getLogger(JesryCallWithAuthetication.class);

    static {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF8)));
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
        headers.setExpires(100000);
        // headers.set("Content-Type", "application/json; UTF-8");
        headers.set("accept-Charset", MainetConstants.UTF8);       
        headers.set("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");
        headers.set("Authorization",createHeaders("anilkumar.pawar@oracle.com", "@Nanya1234567"));

    }

    /**
     * to avoid object initialization
     */
    private JesryCallWithAuthetication() {

    }
    
    public static Object callRestTemplateClient(final Object object, final String uri) {
        return callRestTemplateClient(object, uri, null);
    }

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
    public static <T> Object callRestTemplateClient(final Object object, final String uri, final ParameterizedTypeReference<?> type) {

        ResponseEntity<?> responseEntity = null;
        Object responseObj = null;
        Boolean isReachable = false;
        boolean cxfCall = uri.startsWith("http");
        final String jerseyUrl = getJerseyUrl(JERSEY_URL);
        if ((jerseyUrl != null) && !jerseyUrl.isEmpty()) {
            isReachable = pingURL(jerseyUrl, 2000);
        }
        if (isReachable || cxfCall) {
            String completeUri = null;
            if (cxfCall) {
                completeUri = uri;
            } else {
                completeUri = jerseyUrl + uri;
            }
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            if (object != null) {
                try {
                    mapper.writeValueAsString(object);
                } catch (final IOException e) {
                	throw new FrameworkException(MainetConstants.ERROR_OCCURED, e);
                }
            }

            URI serverURI = null;
            try {
                serverURI = new URI(completeUri);
            } catch (final URISyntaxException e) {
            	throw new FrameworkException(MainetConstants.ERROR_OCCURED, e);
            }
            try {

                HttpEntity<String> requestEntity = null;
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                if (object != null) {
                    requestEntity = new HttpEntity<>(mapper.writeValueAsString(object), headers);

                } else {
                    requestEntity = new HttpEntity<>(headers);
                }
                String result = mapper.writeValueAsString(object);
                LOGGER.info("Service call :: URI = {}  | INPUT = {}", completeUri, result);
                if(type == null) {
                        responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);
                } else {
                        responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, type);
                }
                responseObj = responseEntity.getBody();
                result = mapper.writeValueAsString(responseObj);
                LOGGER.info("Service call OUTPUT :: {}", result);
            } catch (Exception e) {
                throw new FrameworkException(e);
            }
        }

        return responseObj;
    }
    
    private static String getJerseyUrl(final String urlKey) {

        return ApplicationSession.getInstance().getMessage(urlKey);

    }
    
    public static boolean pingURL(String url, final int timeout) {
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
    }
    
    static String createHeaders(String username, String password){
     
              String auth = username + ":" + password;
              byte[] encodedAuth = Base64.encodeBase64( 
                 auth.getBytes(Charset.forName("US-ASCII")) );
              String authHeader = "Basic " + new String( encodedAuth );
            return authHeader;
             
           
     }

}
