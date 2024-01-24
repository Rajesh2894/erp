package com.abm.mainet.care.service;

import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;

public interface ICareWorkflowService {

	WorkflowProcessParameter prepareInitCareWorkflowProcessParameter(RequestDTO applicantDetailDto,
			CareRequest careRequest, WorkflowMas workflowType, WorkflowTaskAction workflowAction);

	WorkflowProcessParameter prepareUpdateCareWorkflowProcessParameter(WorkflowTaskAction workflowTaskAction);

	WorkflowProcessParameter prepareReopenCareWorkflowProcessParameter(WorkflowTaskAction workflowTaskAction);
	
	WorkflowTaskActionResponse initiateAndUpdateWorkFlowMC(WorkflowTaskAction prepareWorkFlowTaskAction, Long workFlowId,
	            String url, String workFlowFlag, String shortCode);

}
