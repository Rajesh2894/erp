package com.abm.mainet.config;

import java.util.Arrays;
import java.util.List;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.abm.mainet.dms.controller.IDocumentService;
import com.abm.mainet.dms.controller.IFolderService;
import com.abm.mainet.dms.controller.IOtherService;
import com.abm.mainet.dms.controller.IShareService;
import com.abm.mainet.util.ApiOriginFilter;

@Configuration
public class ServiceRsConfig implements ApplicationContextAware {

    private ApplicationContext context;

    @Autowired
    private Bus bus;

    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setAddress("/rest");
        endpoint.setProviders(getJAXRSProviders());
        endpoint.setServiceBeans(getJAXRSServices());
        endpoint.setFeatures(getJAXRSFeatures());
        return endpoint.create();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    private List<Object> getJAXRSProviders() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
        jsonProvider.setMapper(objectMapper);
        JacksonJaxbJsonProvider jaxbJsonProvider = new JacksonJaxbJsonProvider();
        jaxbJsonProvider.setMapper(objectMapper);
        ApiOriginFilter apiOriginFilter = new ApiOriginFilter();
        return Arrays.<Object> asList(jsonProvider, jaxbJsonProvider,  apiOriginFilter);
    }

    private List<Feature> getJAXRSFeatures() {
        Swagger2Feature swaggerApiDocs = new Swagger2Feature();
        swaggerApiDocs.setPrettyPrint(true);
        swaggerApiDocs.setTitle("DMS API");
        swaggerApiDocs.setDescription("Documentation DMS API");
        swaggerApiDocs.setVersion("1.0");
        
        return Arrays.<Feature> asList(swaggerApiDocs);
    }

    private List<Object> getJAXRSServices() {
        return Arrays.<Object> asList(context.getBean(IDocumentService.class),
        		context.getBean(IFolderService.class),
        		context.getBean(IShareService.class),
        		context.getBean(IOtherService.class));
    }

}
