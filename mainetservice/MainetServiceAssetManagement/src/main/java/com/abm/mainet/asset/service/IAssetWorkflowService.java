package com.abm.mainet.asset.service;

import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;

public interface IAssetWorkflowService {

    WorkflowTaskActionResponse initiateWorkFlowAssetService(WorkflowTaskAction prepareWorkFlowTaskAction, Long workFlowId,
            String url, String workFlowFlag, String shortCode);

}
