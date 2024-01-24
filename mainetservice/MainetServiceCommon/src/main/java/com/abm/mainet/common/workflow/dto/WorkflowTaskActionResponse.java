package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.exception.ErrorResponse;

public class WorkflowTaskActionResponse extends ErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long processInstanceId;
    private Long taskId;
    private Long workflowRequestId;
    private Boolean isProcessAlive;
    private String signalName;
    private List<Long> activeTasks;

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getWorkflowRequestId() {
        return workflowRequestId;
    }

    public void setWorkflowRequestId(Long workflowRequestId) {
        this.workflowRequestId = workflowRequestId;
    }

    public Boolean getIsProcessAlive() {
        return isProcessAlive;
    }

    public void setIsProcessAlive(Boolean isProcessAlive) {
        this.isProcessAlive = isProcessAlive;
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }

    public List<Long> getActiveTasks() {
        return activeTasks;
    }

    public void setActiveTasks(List<Long> activeTasks) {
        this.activeTasks = activeTasks;
    }

}
