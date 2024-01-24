package com.abm.mainet.common.workflow.service;

import java.util.List;

import com.abm.mainet.common.workflow.dto.BpmBrmDeploymentMasterDTO;

/**
 * This provides services to manage BPM/BRM deployments and their runtime. This master services allow you to manage integration of
 * MainetServices and BPM/BRM tools.
 * 
 * @author sanket.joshi
 *
 */
public interface BpmBrmDeploymentService {

    /**
     * This method will allow us to persist {@code BpmBrmDeploymentMaster} into the DB.
     * @param BpmBrmDeploymentMasterDto
     */
    void saveBpmBrmDeploymentMaster(BpmBrmDeploymentMasterDTO bpmBrmDeploymentMasterDto);

    /**
     * This method will allow us to update {@code BpmBrmDeploymentMaster}. This method will also notify respective users regarding
     * change in deployments at runtime. This method will invoke target implementation of {@link BpmBrmDeploymentChnageListener}
     * using java reflection mechanism.
     * 
     * @param BpmBrmDeploymentMasterDto
     * @see BpmBrmDeploymentChnageListener
     */
    void updateBpmBrmDeploymentMaster(BpmBrmDeploymentMasterDTO bpmBrmDeploymentMasterDto);

    /**
     * This will return active {@code BpmBrmDeploymentMaster} list
     * @return
     */

    List<BpmBrmDeploymentMasterDTO> getAllBpmBrmDeploymentMaster();

    /**
     * This method will return {@code BpmBrmDeploymentMaster} by id;
     * 
     * @param id
     * @return
     */
    BpmBrmDeploymentMasterDTO getBpmBrmDeploymentMasterrById(Long id);

    /**
     * This method will return true is deployment already exist with same group id and artifact name else false.
     * 
     * @param deployment
     * @return
     */
    boolean isDeploymentExist(BpmBrmDeploymentMasterDTO deployment);

}
