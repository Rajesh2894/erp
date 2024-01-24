package com.abm.mainet.common.workflow.service;

import java.util.List;

import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;

/**
 * All methods are wrapper on methods provided {@link WorkflowExecutionService} to fetch task list from MANIET_BPM_SERVICE.
 * 
 * @author sanket.joshi
 * @see IWorkflowExecutionService
 *
 */
public interface ITaskService {

    /**
     * This method will accept instance of {@link TaskSearchRequest} to fetch task by field listed in {@link TaskSearchRequest}
     * 
     * @param taskSearchRequest
     * @return
     * @throws Exception
     */
    List<UserTaskDTO> getTaskList(TaskSearchRequest taskSearchRequest) throws Exception;

    /**
     * This method will search tasks by UUID.
     * 
     * @param uuid unique request identifier
     * @return
     * @throws Exception
     */
    List<UserTaskDTO> getTaskList(String uuid) throws Exception;

    /**
     * This method will search tasks by UUID and returns first task from task list
     * 
     * @param uuid unique request identifier
     * @return
     * @throws Exception
     */
    UserTaskDTO getTask(String uuid) throws Exception;
    
    List<UserTaskDTO> fetchClosedTaskList(TaskSearchRequest taskSearchRequest);

	List<UserTaskDTO> fetchIssuedTaskList(TaskSearchRequest taskSearchRequest);
}
