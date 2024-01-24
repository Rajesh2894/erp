package com.abm.mainet.property.utility;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.common.constant.MainetConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PropertyRestClient {

    private static final Logger LOGGER = Logger.getLogger(PropertyRestClient.class);

    private static final RestTemplate restTemplate;
    private static final HttpHeaders headers;

    static {
        final List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        acceptableMediaTypes.add(MediaType.APPLICATION_XML);
        acceptableMediaTypes.add(MediaType.TEXT_XML);
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(1200000);
        httpRequestFactory.setConnectTimeout(1200000);
        httpRequestFactory.setReadTimeout(1200000);
        restTemplate = new RestTemplate(httpRequestFactory);
        headers = new HttpHeaders();
        headers.set(MainetConstants.CHARACTER_ENCODING, MainetConstants.UTF_8);
        headers.setExpires(100000);
        headers.setAccept(acceptableMediaTypes);
        headers.set(MainetConstants.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");
    }

    public static Object getLandTypeDetails(final Class<?> clazz, final String url) {

        Object output = null;
        ResponseEntity<?> responseEntity = null;
        try {
            URI serverURI = new URI(url);
            responseEntity = restTemplate.getForEntity(serverURI, String.class);
            output = transformResult(clazz,
                    responseEntity.getHeaders().getContentType().toString(),
                    responseEntity.getBody().toString());
        } catch (Throwable t) {
            LOGGER.error("Exception while REST call to " + url + " with cause = " + t.getCause() + " and exception = "
                    + t.getMessage());
        }

        return output;
    }

    private static Object transformResult(Class<?> clazz, String contentType, String content) throws Exception {

        if (contentType.toLowerCase().contains("application/json")) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, clazz);
        } else if (contentType.toLowerCase().contains("application/xml") || contentType.toLowerCase().contains("text/xml")) {
            StringReader result = new StringReader(content);
            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { clazz });
            return jaxbContext.createUnmarshaller().unmarshal(result);
        }
        LOGGER.warn("Unable to find transformer for content type '" + contentType + "' to handle for content '" + content + "'");
        return content;
    }

}
