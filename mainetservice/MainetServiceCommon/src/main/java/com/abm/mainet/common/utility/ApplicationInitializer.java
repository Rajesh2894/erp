package com.abm.mainet.common.utility;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.security.XSSContentCheckerInterceptor;

/**
 * Initializes the context specific required attributes
 */
public class ApplicationInitializer implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(ApplicationInitializer.class);

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet. ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent servletContextEvent) {
        LOGGER.info("Initializing the application ....");
        servletContextEvent.getServletContext();
        Filepaths.setfilepath(ApplicationSession.getInstance().getMessage("tempfilepath"));
        XSSContentCheckerInterceptor.setPatternList();
        ReportUtility.compileAndLoadAllReport();
        LOGGER.info("Initializing of the application completed");
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet. ServletContextEvent)
     */
    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
    }

}
