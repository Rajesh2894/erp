package com.abm.mainet.swm.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Service
public class SWMWorkFlowServiceImpl implements ISWMWorkFlowService {

    private static final Logger LOGGER = Logger.getLogger(SWMWorkFlowServiceImpl.class);

    @Resource
    private IWorkflowExecutionService iWorkflowExecutionService;

    @Resource
    private ServiceMasterService serviceMasterService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.ISWMWorkFlowService#initiateWorkFlowSWMService(com.abm.mainet.common.workflow.dto.
     * WorkflowTaskAction, java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public WorkflowTaskActionResponse initiateWorkFlowSWMService(WorkflowTaskAction prepareWorkFlowTaskAction, Long workFlowId,
            String url, String workFlowFlag, String shortCode) {
        WorkflowTaskActionResponse response = null;
        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
        try {

            ServiceMaster serviceMast = serviceMasterService.getServiceMasterByShortCode(shortCode, prepareWorkFlowTaskAction.getOrgId());
            String processName = serviceMasterService.getProcessName(serviceMast.getSmServiceId(), prepareWorkFlowTaskAction.getOrgId());
            processParameter.setProcessName(processName);

            ApplicationMetadata applicationMetadata = null;
            applicationMetadata = new ApplicationMetadata();
            applicationMetadata.setApplicationId(prepareWorkFlowTaskAction.getApplicationId());
            applicationMetadata.setOrgId(prepareWorkFlowTaskAction.getOrgId());
            applicationMetadata.setWorkflowId(workFlowId);
            applicationMetadata.setPaymentMode(prepareWorkFlowTaskAction.getPaymentMode());
            applicationMetadata.setIsCheckListApplicable(false);
            applicationMetadata.setIsAutoEscalate(false);
            applicationMetadata.setIsFreeService(false);
            applicationMetadata.setIsScrutinyApplicable(false);

            // Task manager assignment is depends no LDAP integration his check added in
            // BRm/BPM layer
            TaskAssignment requesterTaskAssignment = new TaskAssignment();
            requesterTaskAssignment.setActorId(prepareWorkFlowTaskAction.getEmpId().toString());
            requesterTaskAssignment.addActorId(prepareWorkFlowTaskAction.getEmpId().toString());
            requesterTaskAssignment.setOrgId(prepareWorkFlowTaskAction.getOrgId());
            requesterTaskAssignment.setUrl(url);

            // Reviewer TaskAssignment has been removed from here,because it will be fetch
            // on the fly by BPM to Service callback.

            processParameter.setRequesterTaskAssignment(requesterTaskAssignment);

            if (workFlowFlag.equals(MainetConstants.FlagU)) {
                applicationMetadata.setStatus(MainetConstants.WorkFlow.Status.COMPLETED);
                if (prepareWorkFlowTaskAction.getIsFinalApproval() == null)
                    prepareWorkFlowTaskAction.setIsFinalApproval(false);
                if (prepareWorkFlowTaskAction.getIsObjectionAppealApplicable() == null)
                    prepareWorkFlowTaskAction.setIsObjectionAppealApplicable(false);
                processParameter.setApplicationMetadata(applicationMetadata);
                processParameter.setWorkflowTaskAction(prepareWorkFlowTaskAction);
                response = iWorkflowExecutionService.updateWorkflow(processParameter);
            } else {
                applicationMetadata.setStatus(MainetConstants.WorkFlow.Status.PENDING);
                processParameter.setApplicationMetadata(applicationMetadata);
                processParameter.setWorkflowTaskAction(prepareWorkFlowTaskAction);
                response = iWorkflowExecutionService.initiateWorkflow(processParameter);
            }
            return response;
        } catch (Exception e) {
            LOGGER.error("Exception occured while call workflow action execution: ", e);
        }
        return response;

    }

}
