package com.abm.mainet.bpm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Entity
@Table(name = "VW_TASK_LIST")
//@Table(name = "VW_TASK_LIST_UPD")
public class TaskView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6878425820924280089L;

    @Id
    @Column(name = "TASK_ID")
    private Long taskId;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "TASK_STATUS")
    private String taskStatus;

    @Column(name = "TASK_URL")
    private String taskUrl;

    @Column(name = "APM_APPLICATION_ID")
    private Long applicationId;

    @Column(name = "REFERENCE_ID")
    private String referenceId;

    @Column(name = "ORGID")
    private String orgId;

    @Column(name = "TASK_ACTOR_ID")
    private String taskActorId;

    @Column(name = "TASK_DATA")
    private String taskData;

    @Column(name = "workflowId")
    private Long workflowId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTaskActorId() {
        return taskActorId;
    }

    public void setTaskActorId(String taskActorId) {
        this.taskActorId = taskActorId;
    }

    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

}
