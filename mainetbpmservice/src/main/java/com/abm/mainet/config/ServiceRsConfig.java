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

import com.abm.mainet.bpm.service.ITaskAssignmentService;
import com.abm.mainet.bpm.service.IWorkflowActionService;
import com.abm.mainet.bpm.service.IWorkflowRequestService;
import com.abm.mainet.bpm.service.IWorkflowTaskService;
import com.abm.mainet.common.exception.CXFFrameworkExceptionHandler;
import com.abm.mainet.util.ApiOriginFilter;

@Configuration
public class ServiceRsConfig implements ApplicationContextAware {

    private ApplicationContext context;

    @Autowired
    private Bus bus;

    @Autowired
    private ApplicationProperties applicationProperties;

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
        CXFFrameworkExceptionHandler exceptionMappingProvider = new CXFFrameworkExceptionHandler();
        ApiOriginFilter apiOriginFilter = new ApiOriginFilter();
        return Arrays.<Object> asList(jsonProvider, jaxbJsonProvider, exceptionMappingProvider, apiOriginFilter);
    }

    private List<Feature> getJAXRSFeatures() {
        Swagger2Feature swaggerApiDocs = new Swagger2Feature();
        swaggerApiDocs.setPrettyPrint(true);
        swaggerApiDocs.setTitle("Workflow API");
        swaggerApiDocs.setDescription("Documentation Workflow API");
        swaggerApiDocs.setVersion("1.0");
        
        return Arrays.<Feature> asList(swaggerApiDocs);
    }

    private List<Object> getJAXRSServices() {
        return Arrays.<Object> asList(
                context.getBean(ITaskAssignmentService.class),
                context.getBean(IWorkflowRequestService.class),
                context.getBean(IWorkflowTaskService.class),
                context.getBean(IWorkflowActionService.class),
                context.getBean(applicationProperties.getBpmRuntime()));
    }

}
