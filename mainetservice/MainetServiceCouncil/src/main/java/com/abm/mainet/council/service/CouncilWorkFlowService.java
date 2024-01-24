package com.abm.mainet.council.service;

import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * @@author aarti.paan
 * @since 22 May 2019
 */

public interface CouncilWorkFlowService {
    /**
     * Method Is used for Initiate Work flow Initiate
     * @param workflowActionDto
     * @param workFlowMas
     * @param url
     * @param workFlowFlag
     * @return String
     */
    public String initiateWorkFlowCouncilService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas,
            String url, String workFlowFlag);

    /**
     * Method is used for update workflow
     * 
     * @param workflowTaskAction
     */
    public String updateWorkFlowProposalService(WorkflowTaskAction workflowTaskAction);
}
