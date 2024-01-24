package com.abm.mainet.bpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.abm.mainet.bpm.common.dto.WorkflowProcessParameter;
import com.abm.mainet.bpm.common.dto.WorkflowTask;
import com.abm.mainet.bpm.common.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;

/**
 * Oracle task service provider.
 * 
 * @author sanket.joshi
 * 
 * @see IBpmService
 */
@Service(MainetConstants.WorkFlow.ImplementationService.ORACLE)
public class OracleTaskServiceImpl implements IBpmService {

    @Override
    public WorkflowTaskActionResponse initiateProcess(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse completeTasks(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse updateTasks(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse deleteTasks(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse claimTasks(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse delegateTasks(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse forwardToTasks(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse releaseTask(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse suspendTask(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse resumeTask(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse skipTask(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse stopTask(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse failTask(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getTaskContent(Long taskId) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<WorkflowTask> getTaskListByUser(String userId) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<WorkflowTask> getTaskListByUuid(String uuid) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkflowTaskActionResponse signalProcess(WorkflowProcessParameter parameter) throws FrameworkException {
        // TODO Auto-generated method stub
        return null;
    }

}
