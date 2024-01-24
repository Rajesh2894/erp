/**
 * 
 */
package com.abm.mainet.asset.service;

import com.abm.mainet.asset.ui.dto.RevaluationDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * @author sarojkumar.yadav
 *
 */
public interface IRevaluationService {

    /**
     * Store Asset Revaluation request in database
     * 
     * @param dto
     * @param audit
     * @param workfloFlag if YES then initiate workFlow else not
     */
    public String revaluate(final RevaluationDTO dto, final AuditDetailsDTO audit, final String workFlowFlag);

    /**
     * Returns the details as per the revaluation ID passed as parameter
     * @param revalId
     * @return
     */
    RevaluationDTO getDetails(Long revalId);

    /**
     * Executes the workflow action based on reference ID to uniquely identify the workflow request
     * @param wfReferenceId reference ID to uniquely identify the workflow request
     * @param auditDto information about user taking the action and from which IP
     * @param wfAction workflow action taken by the user
     * @return true if action was successful
     */
    boolean executeWfAction(String wfReferenceId, AuditDetailsDTO auditDto, WorkflowTaskAction wfAction);

    public void docUpload(String string, AuditDetailsDTO auditDto, WorkflowTaskAction wfAction);

}
