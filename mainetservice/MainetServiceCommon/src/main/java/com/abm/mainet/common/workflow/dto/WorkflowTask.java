package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class WorkflowTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long workflowTaskId;

    private Long taskId;

    private String taskName;

    private String taskStatus;

    private Long applicationId;

    private String referenceId;

    private Long workflowId;

    private Long workflowReqId;

    private Long orgId;

    private Long deptId;

    private String deptName;

    private String deptNameReg;

    private Long serviceId;

    private String serviceName;

    private String serviceNameReg;

    private Long serviceEventId;

    private String serviceEventName;

    private String serviceEventNameReg;

    private String serviceEventUrl;

    private String actorId;

    private String actorRole;

    private Long currentEscalationLevel;

    private Long taskSlaDurationInMS;

    private Long curentCheckerLevel;

    private Long currentCheckerGroup;

    private Date createdDate;

    private Long createdBy;

    private Date modifiedDate;

    private Long modifiedBy;

    private Date dateOfAssignment;

    private Date dateOfCompletion;

    /* private WorkflowRequestDto workFlowRequest = new WorkflowRequestDto(); */

    private List<WorkflowAction> workFlowActionList = new ArrayList<>();

    public Long getWorkflowTaskId() {
        return workflowTaskId;
    }

    public void setWorkflowTaskId(Long workflowTaskId) {
        this.workflowTaskId = workflowTaskId;
    }

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

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public Long getWorkflowReqId() {
        return workflowReqId;
    }

    public void setWorkflowReqId(Long workflowReqId) {
        this.workflowReqId = workflowReqId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNameReg() {
        return deptNameReg;
    }

    public void setDeptNameReg(String deptNameReg) {
        this.deptNameReg = deptNameReg;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNameReg() {
        return serviceNameReg;
    }

    public void setServiceNameReg(String serviceNameReg) {
        this.serviceNameReg = serviceNameReg;
    }

    public Long getServiceEventId() {
        return serviceEventId;
    }

    public void setServiceEventId(Long serviceEventId) {
        this.serviceEventId = serviceEventId;
    }

    public String getServiceEventName() {
        return serviceEventName;
    }

    public void setServiceEventName(String serviceEventName) {
        this.serviceEventName = serviceEventName;
    }

    public String getServiceEventNameReg() {
        return serviceEventNameReg;
    }

    public void setServiceEventNameReg(String serviceEventNameReg) {
        this.serviceEventNameReg = serviceEventNameReg;
    }

    public String getServiceEventUrl() {
        return serviceEventUrl;
    }

    public void setServiceEventUrl(String serviceEventUrl) {
        this.serviceEventUrl = serviceEventUrl;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorRole() {
        return actorRole;
    }

    public void setActorRole(String actorRole) {
        this.actorRole = actorRole;
    }

    public Long getCurrentEscalationLevel() {
        return currentEscalationLevel;
    }

    public void setCurrentEscalationLevel(Long currentEscalationLevel) {
        this.currentEscalationLevel = currentEscalationLevel;
    }

    public Long getTaskSlaDurationInMS() {
        return taskSlaDurationInMS;
    }

    public void setTaskSlaDurationInMS(Long taskSlaDurationInMS) {
        this.taskSlaDurationInMS = taskSlaDurationInMS;
    }

    public Long getCurentCheckerLevel() {
        return curentCheckerLevel;
    }

    public void setCurentCheckerLevel(Long curentCheckerLevel) {
        this.curentCheckerLevel = curentCheckerLevel;
    }

    public Long getCurrentCheckerGroup() {
        return currentCheckerGroup;
    }

    public void setCurrentCheckerGroup(Long currentCheckerGroup) {
        this.currentCheckerGroup = currentCheckerGroup;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getDateOfAssignment() {
        return dateOfAssignment;
    }

    public void setDateOfAssignment(Date dateOfAssignment) {
        this.dateOfAssignment = dateOfAssignment;
    }

    public Date getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(Date dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    /*
     * public WorkflowRequestDto getWorkFlowRequest() { return workFlowRequest; } public void
     * setWorkFlowRequest(WorkflowRequestDto workFlowRequest) { this.workFlowRequest = workFlowRequest; }
     */

    public List<WorkflowAction> getWorkFlowActionList() {
        return workFlowActionList;
    }

    public void setWorkFlowActionList(List<WorkflowAction> workFlowActionList) {
        this.workFlowActionList = workFlowActionList;
    }
}
