package com.abm.mainet.common.workflow.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;

/**
 * WorkflowExecutionService is the main entry point to interact with the MAINET_BPM_SERVICE. WorkflowExecutionService Entry Point
 * serves as facade of all the services like initiate, update, abort work-flow.
 * 
 * @author sanket.joshi
 *
 */
public interface IWorkflowExecutionService {

    /**
     * This method initiate new process instance in BPM with given WorkflowProcessParameter. This mandatory to set processName in
     * WorkflowProcessParameter to identify BPM process runtime.
     * 
     * @param workflowProcessParameter
     * @return
     * @throws Exception
     */
    WorkflowTaskActionResponse initiateWorkflow(WorkflowProcessParameter workflowProcessParameter) throws Exception;

    /**
     * This method updates process instance with in BPM with given WorkflowProcessParameter. This mandatory to set processName,
     * taskId in WorkflowProcessParameter to identify BPM process runtime. By default this method using task completion API to
     * update/complete task.
     * 
     * @param workflowProcessParameter
     * @return
     * @throws Exception
     */
    WorkflowTaskActionResponse updateWorkflow(WorkflowProcessParameter workflowProcessParameter) throws Exception;

    /**
     * This method is used to triggered Signal for process instance to continue the Hearing process. Case where Hearing process is
     * used as Sub-process than below method is used to trigger Objection and Hearing Process.
     * 
     * @param workflowProcessParameter
     * @throws Exception
     */
    void signalWorkFlow(WorkflowProcessParameter workflowProcessParameter) throws Exception;

    /**
     * This method will return list of WorkflowProcessParameter with additional task details by taskId
     * 
     * @param taskId
     * @return
     * @throws Exception
     */
    Map<?, ?> getTaskData(Long taskId) throws Exception;

    /**
     * This method will return list of pending tasks by userId and orgId
     * 
     * @param userId
     * @param orgId
     * @return
     * @throws Exception
     */
    List<UserTaskDTO> getTaskList(TaskSearchRequest requester) throws Exception;

    /**
     * 
     * This method will return list of pending tasks by UUID (applicationId/referenceId)
     * 
     * @param uuid
     * @return
     * @throws Exception
     */
    List<UserTaskDTO> getTaskList(String uuid) throws Exception;

    /**
     * This method will search task by various field of TaskSearchRequest, where all not null field will be act as an AND of
     * operation in SQl query
     * 
     * @param searchRequest
     * @return
     * @throws Exception
     */
    List<UserTaskDTO> searchTask(TaskSearchRequest searchRequest) throws Exception;

}
