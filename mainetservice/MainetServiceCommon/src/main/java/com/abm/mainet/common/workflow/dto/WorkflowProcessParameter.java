package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;

public class WorkflowProcessParameter implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected String processName;
    protected String signalName;
    protected ApplicationMetadata applicationMetadata;
    protected WorkflowTaskAction workflowTaskAction;
    protected TaskAssignment taskAssignment;
    protected TaskAssignment requesterTaskAssignment;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }

    public ApplicationMetadata getApplicationMetadata() {
        return applicationMetadata;
    }

    public void setApplicationMetadata(ApplicationMetadata applicationMetadata) {
        this.applicationMetadata = applicationMetadata;
    }

    public WorkflowTaskAction getWorkflowTaskAction() {
        return workflowTaskAction;
    }

    public void setWorkflowTaskAction(WorkflowTaskAction workflowTaskAction) {
        this.workflowTaskAction = workflowTaskAction;
    }

    public TaskAssignment getTaskAssignment() {
        return taskAssignment;
    }

    public void setTaskAssignment(TaskAssignment taskAssignment) {
        this.taskAssignment = taskAssignment;
    }

    public TaskAssignment getRequesterTaskAssignment() {
        return requesterTaskAssignment;
    }

    public void setRequesterTaskAssignment(TaskAssignment requesterTaskAssignment) {
        this.requesterTaskAssignment = requesterTaskAssignment;
    }

}
