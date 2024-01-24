package com.abm.mainet.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.common.exception.FrameworkException;

@Component
public class RestClient {
    private final Logger LOGGER = Logger.getLogger(RestClient.class);

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    @Autowired
    private ApplicationProperties applicationProperties;

    @PostConstruct
    void init() {

        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        headers = new HttpHeaders();
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<Charset> acceptableCharsetTypes = new ArrayList<Charset>();
        acceptableCharsetTypes.add(Charset.defaultCharset());
        acceptableCharsetTypes.add(Charset.forName("UTF-8"));
        acceptableCharsetTypes.add(Charset.forName("ISO-8859-1"));
        headers.setAcceptCharset(acceptableCharsetTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        headers.set("characterEncoding", "UTF-8");
        headers.setExpires(100000);
        headers.set("content-Type", "application/json");
        headers.set("accept-Charset", "UTF-8");
        headers.set("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");

    }

    public Object callRestTemplateClient(Object object, String Uri) throws FrameworkException {

        ResponseEntity<?> responseEntity = null;
        Object responseObj = null;
        try {
			String baseUrl = applicationProperties.getMainetServiceRestUrl();
			String completeUri = baseUrl + Uri;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
			if (object != null) {
			   mapper.writeValueAsString(object);
			}
			URI serverURI = new URI(completeUri.toString());
			HttpEntity<String> requestEntity = null;
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
			if (object != null) {
			    requestEntity = new HttpEntity<String>(mapper.writeValueAsString(object), headers);
			} else {
			    requestEntity = new HttpEntity<String>(headers);
			}
			LOGGER.info("object json structure " + requestEntity);
			responseEntity = restTemplate.exchange(serverURI, HttpMethod.POST, requestEntity, Object.class);
			responseObj = responseEntity.getBody();
		} catch (Exception e) {
			LOGGER.error("BPM -> RestClient-> callRestTemplateClient", e);
			throw new FrameworkException(e);
		}
        return responseObj;
    }

}
