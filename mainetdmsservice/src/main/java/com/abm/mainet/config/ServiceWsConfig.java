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

import com.abm.mainet.dms.controller.IDocumentService;
import com.abm.mainet.dms.controller.IFolderService;
import com.abm.mainet.dms.controller.IOtherService;
import com.abm.mainet.dms.controller.IShareService;

@Configuration
public class ServiceWsConfig implements ApplicationContextAware {

    private ApplicationContext context;

    @Autowired
    private Bus bus;

   
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Bean
    public Endpoint documentService() {
        EndpointImpl endpoint = new EndpointImpl(bus, context.getBean(IDocumentService.class));
        endpoint.publish("/dms/document");
        return endpoint;
    }
    @Bean
    public Endpoint folderService() {
        EndpointImpl endpoint = new EndpointImpl(bus, context.getBean(IFolderService.class));
        endpoint.publish("/dms/folder");
        return endpoint;
    }
    @Bean
    public Endpoint shareService() {
        EndpointImpl endpoint = new EndpointImpl(bus, context.getBean(IShareService.class));
        endpoint.publish("/dms/share");
        return endpoint;
    }

    @Bean
    public Endpoint otherService() {
        EndpointImpl endpoint = new EndpointImpl(bus, context.getBean(IOtherService.class));
        endpoint.publish("/dms/other");
        return endpoint;
    }
 
}
