package com.abm.mainet.common.workflow.service;

/**
 * BpmBrmDeploymentMaster is used to manage project units deployed under BPM tools. Whenever this deployment get updated with it
 * required to send notification to all department users related to such change.
 * 
 * This interface provide method to notify all user using department short code. Implementation of this interface can be modules
 * specific depending on the department.
 * 
 * Implementation of this interface should be mapped with department short code using system properties defined under
 * "service-classes-configuration.properties" with pattern "mainet.bpm.deploymentChangeListener.{DEPT_SHORT_CODE}=Class Name"
 * 
 * 
 * Example. "mainet.bpm.deploymentChangeListener.cfc=com.abm.mainet.common.workflow.service.DefaultDeploymentChangeListener"
 * 
 * 
 * @author sanket.joshi
 * @see DefaultDeploymentChangeListener
 *
 */
public interface BpmBrmDeploymentChnageListener {

    /**
     * This method will send notification to all users related to the given department. This method will be invoked after any
     * change in BpmBrmDeploymentMaster
     * 
     * @param deptCode Department short code
     * @see BpmBrmDeploymentServiceImpl
     */
    void notifyUsers(String deptCode);
}
