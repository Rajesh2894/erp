package com.abm.mainet.common.workflow.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;

public interface IWorkflowRequestService {

    /**
     * used to get Work flow Request By Application Id / Reference Id
     * @param applicationId
     * @param referenceId
     * @param orgId
     * @return
     */
    WorkflowRequest getWorkflowRequestByAppIdOrRefId(Long applicationId, String referenceId, Long orgId);

    /**
     * used to get Workflow Request data by Application Id
     * @param applicationId
     * @return
     */
    WorkflowRequest findByApplicationId(Long applicationId);

    /**
     * used to update Work flow Auto Escalation Task
     * @param workflowDetails
     */
    void updateWorkflowAutoEscalationTask(Map<String, List<Long>> workflowDetails);

    /**
     * used to get Workflow Request data by Application Id( overloaded method to get workflow by workflow request id)
     * @param applicationId
     * @param workflowId
     * @return
     */
    WorkflowRequest findByApplicationId(Long applicationId, Long workflowId);
    
    void initiateAndUpdateWorkFlowProcess(CommonChallanDTO offline, WardZoneBlockDTO dwzDto);

    void updateWorkFlow(Long taskId, CommonChallanDTO offlineDto, Long empType, Long empId, String empName);
    
    void signalWorkFlow(final CommonChallanDTO offline) ;


}
