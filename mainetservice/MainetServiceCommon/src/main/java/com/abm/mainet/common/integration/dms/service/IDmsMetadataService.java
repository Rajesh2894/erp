package com.abm.mainet.common.integration.dms.service;

import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface IDmsMetadataService {

	public Long getMaxCount(Long orgid);

	public String initiateWorkFlowWorksService(WorkflowTaskAction prepareWorkFlowTaskAction, WorkflowMas workFlowMas,
			String url, String flag);

	String updateWorkFlowMetadataService(WorkflowTaskAction workflowTaskAction);

	void updateWfStatus(String requestId, String remark, String decision, Long orgId);

	public boolean saveForm(FileUploadDTO fileUploadDTO);
}
