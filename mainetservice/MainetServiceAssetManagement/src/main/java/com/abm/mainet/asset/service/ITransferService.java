package com.abm.mainet.asset.service;

import java.util.List;

import com.abm.mainet.asset.ui.dto.TransferDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * Provides methods to transfer asset.
 * @author Vardan.Savarde
 *
 */
public interface ITransferService {

    /**
     * Store Asset Transfer request in database
     * 
     * @param transferDTO
     * @param auditDTO
     * @param workfloFlag if YES then initiate workFlow else not
     */
    public String saveTransferReq(final TransferDTO dto, final AuditDetailsDTO audit, final String workFlowFlag,
            String moduleDeptCode, String serviceCodeDeptWise, List<DocumentDetailsVO> attachmentList,
            RequestDTO requestDTO);

    public boolean executeWfAction(final String wfReferenceId, final AuditDetailsDTO audit, final WorkflowTaskAction wfAction,
            String moduleDeptCode, String serviceCodeDeptWise);

    public TransferDTO getDetails(final Long transferId);

}
