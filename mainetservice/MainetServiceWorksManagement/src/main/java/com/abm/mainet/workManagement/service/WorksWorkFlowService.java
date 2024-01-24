package com.abm.mainet.workManagement.service;

import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * @author vishwajeet.kumar
 * @since 8 May 2018
 */
public interface WorksWorkFlowService {

    /**
     * Method Is used for Initiate Work flow Initiate
     * @param workflowActionDto
     * @param workFlowMas
     * @param url
     * @param workFlowFlag
     * @return String
     */
    public String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas,
            String url, String workFlowFlag);

    /**
     * Method Is used for Update Work flow
     * @param workflowTaskAction
     * @return string
     */
    public String updateWorkFlowWorksService(WorkflowTaskAction workflowTaskAction);

    /**
     * This method is used for Update Work Flow
     * @param workflowActionDto
     * @param smServiceId
     * @param parentOrgId
     * @return status
     */
    public String updateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, Long smServiceId, Long parentOrgId);

}
