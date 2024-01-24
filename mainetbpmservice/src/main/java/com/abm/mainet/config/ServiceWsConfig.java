package com.abm.mainet.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
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

@Configuration
public class ServiceWsConfig implements ApplicationContextAware {

    private ApplicationContext context;

    @Autowired
    private Bus bus;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Bean
    public Endpoint bpmService() {
        EndpointImpl endpoint = new EndpointImpl(bus, context.getBean(applicationProperties.getBpmRuntime()));
        endpoint.publish("/bpm");
        return endpoint;
    }

    @Bean
    public Endpoint taskAssignmentService() {
        EndpointImpl endpoint = new EndpointImpl(bus, context.getBean(ITaskAssignmentService.class));
        endpoint.publish("/task/assignment");
        return endpoint;
    }

    @Bean
    public Endpoint workflowRequestService() {
        EndpointImpl endpoint = new EndpointImpl(bus, context.getBean(IWorkflowRequestService.class));
        endpoint.publish("/workflow/request");
        return endpoint;
    }

    @Bean
    public Endpoint workflowTaskService() {
        EndpointImpl endpoint = new EndpointImpl(bus, context.getBean(IWorkflowTaskService.class));
        endpoint.publish("/workflow/task");
        return endpoint;
    }

    @Bean
    public Endpoint workflowActionService() {
        EndpointImpl endpoint = new EndpointImpl(bus, context.getBean(IWorkflowActionService.class));
        endpoint.publish("/workflow/action");
        return endpoint;
    }

}
