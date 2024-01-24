package com.abm.mainet.tradeLicense.service;

import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface IContractorApplicantService {
	
	RequestDTO getContractorApplicant(Long applicationId, Long orgid);

	String updateWorkFlowSecurityService(WorkflowTaskAction workflowActionDto);
}
