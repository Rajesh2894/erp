package com.abm.mainet.bpm.service;

import java.util.List;

import com.abm.mainet.bpm.domain.TaskView;

public interface ITaskViewService {

    /**
     * fetch all completed and not completed task from task view
     * @param applicationnId
     * @param referenceNo
     * @param workFlowId 
     * @return
     */
    List<TaskView> getAllTaskById(Long applicationnId, String referenceNo, Long workFlowId);

}
