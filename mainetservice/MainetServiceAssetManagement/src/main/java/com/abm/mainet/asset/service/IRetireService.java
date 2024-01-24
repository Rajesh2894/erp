/**
 * 
 */
package com.abm.mainet.asset.service;

import com.abm.mainet.asset.ui.dto.RetirementDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * @author sarojkumar.yadav
 *
 */
public interface IRetireService {

    /**
     * Store Asset Retire request in database
     * 
     * @param transferDTO
     * @param auditDTO
     * @param workfloFlag if YES then initiate workFlow else not
     */
    public String retire(final RetirementDTO dto, final AuditDetailsDTO audit, final String workFlowFlag, String moduleDeptCode,
            String serviceCodeDeptWise);

    /**
     * workFlow request for asset transfer
     * 
     * @param transferDTO
     * @param auditDTO
     * @param workfloFlag if YES then initiate workFlow else not
     */
    public void initiateWorkFlow(final RetirementDTO dto, final AuditDetailsDTO audit, final String workFlowFlag);

    public RetirementDTO getDetails(final Long retireId);

    boolean executeWfAction(String wfReferenceId, AuditDetailsDTO auditDto, WorkflowTaskAction wfAction, String moduleDeptCode,
            String serviceCodeDeptWise);
}
