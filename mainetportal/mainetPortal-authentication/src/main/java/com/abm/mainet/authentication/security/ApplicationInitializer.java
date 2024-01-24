package com.abm.mainet.authentication.security;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.JspConstants;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.ApplicationSession;

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
        servletContextEvent.getServletContext().setAttribute("MainetConstants", new JspConstants());
        servletContextEvent.getServletContext();
        Filepaths.setfilepath(ApplicationSession.getInstance().getMessage("tempfilepath"));
        LOGGER.info("Initializing of the application completed");
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet. ServletContextEvent)
     */
    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
        // do nothing
    }

}
