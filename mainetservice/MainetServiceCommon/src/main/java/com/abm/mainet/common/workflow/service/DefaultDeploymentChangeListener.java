package com.abm.mainet.common.workflow.service;

import org.apache.log4j.Logger;

/**
 * This is default implementation of BpmBrmDeploymentChnageListener, if there is no any module specific requirement related to
 * department then this implementation can be mapped under 'service-classes-configuration.properties'
 * 
 * 
 * @author sanket.joshi
 * @see BpmBrmDeploymentChnageListener
 *
 */
public class DefaultDeploymentChangeListener implements BpmBrmDeploymentChnageListener {

    private static final Logger LOGGER = Logger.getLogger(DefaultDeploymentChangeListener.class);

    /**
     * This method will send notification to all users related to the given department. This method will be invoked after any
     * change in BpmBrmDeploymentMaster.
     * 
     * @param deptCode Department short code
     * @see BpmBrmDeploymentServiceImpl
     */
    @Override
    public void notifyUsers(String deptCode) {
        LOGGER.info("Default deployment change listener called for department " + deptCode);

    }

}
