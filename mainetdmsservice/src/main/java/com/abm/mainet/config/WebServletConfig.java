package com.abm.mainet.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.abm.mainet.config.logging.MainetRequestLoggingFilter;

@Configuration
public class WebServletConfig implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("*.html");
        dispatcher.addMapping("*.jsp");

        ServletRegistration.Dynamic dispatcherRest = servletContext.addServlet("dispatcher-rest", new DispatcherServlet(context));
        dispatcherRest.setLoadOnStartup(1);
        dispatcherRest.addMapping("/rest/*");

        ServletRegistration.Dynamic dispatcherCxf = servletContext.addServlet("dispatcher-cxf", new CXFServlet());
        dispatcherCxf.setLoadOnStartup(1);
        dispatcherCxf.addMapping("/services/*");

        servletContext.addFilter("loggingFilter", MainetRequestLoggingFilter.class).addMappingForServletNames(null, false,
                "dispatcher", "dispatcher-rest");
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.abm.mainet.config");
        return context;
    }

}
