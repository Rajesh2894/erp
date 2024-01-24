package com.abm.mainet.common.workflow.service;

import java.util.List;

import com.abm.mainet.common.workflow.dto.UserTaskDTO;

public interface IWorkflowTaskService {

    /**
     * This method will return task list by UUID(applicationId/referenceId)
     * 
     * @param uuid
     * @return
     */
    List<UserTaskDTO> findByUUId(Long uuid);

    /**
     * this method will return Task by task id
     * 
     * @param taskId
     * @param applicationId
     * @return
     */
    UserTaskDTO findByTaskId(Long taskId);

    /**
     * this method will return unique Task by task id and reference id
     * @param taskId
     * @param referenceId
     * @return
     */
    UserTaskDTO findByTaskIdAndReferenceId(Long taskId, String referenceId);
    /**
     * this method will return unique Task by task id and reference id
     * @param appId
     * @param orgId
     * @return
     */
	Long getTaskIdByAppIdAndOrgId(Long appId, Long orgId);

	List<String> getTaskByAppId(Long appId, Long orgId);

}
