package com.abm.mainet.asset.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Service
public class AssetWorkflowServiceImpl implements IAssetWorkflowService {

	private static final Logger LOGGER = Logger.getLogger(AssetWorkflowServiceImpl.class);

	@Resource
	private IWorkflowExecutionService iWorkflowExecutionService;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Override
	@Transactional(readOnly = true)
	public WorkflowTaskActionResponse initiateWorkFlowAssetService(WorkflowTaskAction prepareWorkFlowTaskAction,
			Long workFlowId, String url, String workFlowFlag, String shortCode) {
		WorkflowTaskActionResponse response = null;
		WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
		try {

			ServiceMaster serviceMast = serviceMasterService.getServiceMasterByShortCode(shortCode,
					prepareWorkFlowTaskAction.getOrgId());
			String processName = serviceMasterService.getProcessName(serviceMast.getSmServiceId(),
					prepareWorkFlowTaskAction.getOrgId());
			processParameter.setProcessName(processName);

			ApplicationMetadata applicationMetadata = null;
			applicationMetadata = new ApplicationMetadata();
			applicationMetadata.setReferenceId(prepareWorkFlowTaskAction.getReferenceId());
			applicationMetadata.setOrgId(prepareWorkFlowTaskAction.getOrgId());
			applicationMetadata.setWorkflowId(workFlowId);
			applicationMetadata.setPaymentMode(prepareWorkFlowTaskAction.getPaymentMode());
			applicationMetadata.setIsCheckListApplicable(false);

			// Task manager assignment is depends no LDAP integration his check added in
			// BRm/BPM layer
			TaskAssignment requesterTaskAssignment = new TaskAssignment();
			if (prepareWorkFlowTaskAction.getEmpId() != null) {
				requesterTaskAssignment.setActorId(prepareWorkFlowTaskAction.getEmpId().toString());
			}
			if (prepareWorkFlowTaskAction.getEmpId() != null) {
				requesterTaskAssignment.addActorId(prepareWorkFlowTaskAction.getEmpId().toString());
			}
			requesterTaskAssignment.setOrgId(prepareWorkFlowTaskAction.getOrgId());
			requesterTaskAssignment.setUrl(url);

			// Reviewer TaskAssignment has been removed from here,because it will be fetch
			// on the fly by BPM to Service callback.

			processParameter.setRequesterTaskAssignment(requesterTaskAssignment);
			processParameter.setApplicationMetadata(applicationMetadata);

			processParameter.setWorkflowTaskAction(prepareWorkFlowTaskAction);

			if (workFlowFlag.equals(MainetConstants.FlagU)) {
				response = iWorkflowExecutionService.updateWorkflow(processParameter);
			} else {
				response = iWorkflowExecutionService.initiateWorkflow(processParameter);
			}

		} catch (Exception e) {
			LOGGER.error("Exception occured while call workflow action execution: ", e);
			throw new FrameworkException("Exception occured while call workflow action execution", e);
		}

		return response;
	}

}
