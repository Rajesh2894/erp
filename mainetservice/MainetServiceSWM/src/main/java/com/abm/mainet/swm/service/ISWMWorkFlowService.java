package com.abm.mainet.swm.service;

import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;

public interface ISWMWorkFlowService {
    
    WorkflowTaskActionResponse initiateWorkFlowSWMService(WorkflowTaskAction prepareWorkFlowTaskAction, Long workFlowId, String url, String workFlowFlag, String shortCode);

}
