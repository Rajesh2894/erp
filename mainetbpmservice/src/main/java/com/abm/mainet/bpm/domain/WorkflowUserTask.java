package com.abm.mainet.bpm.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_WORKFLOW_TASK")
public class WorkflowUserTask implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.bpm.domain.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WFTASK_ID")
    private Long workflowTaskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKFLOW_REQ_ID", nullable = false)
    private WorkflowRequest workFlowRequest;

    @Column(name = "TASK_ID")
    private Long taskId;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "TASK_STATUS")
    private String taskStatus;

    @Column(name = "APM_APPLICATION_ID")
    private Long applicationId;

    @Column(name = "REFERENCE_ID")
    private String referenceId;

    @Column(name = "WF_ID")
    private Long workflowId;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "DP_DEPTID")
    private Long deptId;

    @Column(name = "DP_DEPTDESC")
    private String deptName;

    @Column(name = "DP_NAME_MAR")
    private String deptNameReg;

    @Column(name = "SM_SERVICE_ID")
    private Long serviceId;

    @Column(name = "SM_SERVICE_NAME")
    private String serviceName;

    @Column(name = "SM_SERVICE_NAME_MAR")
    private String serviceNameReg;

    @Column(name = "EVENT_ID")
    private Long serviceEventId;

    @Column(name = "SMFNAME")
    private String serviceEventName;

    @Column(name = "SMFNAME_MAR")
    private String serviceEventNameReg;

    @Column(name = "SMFACTION")
    private String serviceEventUrl;

    @Column(name = "WFTASK_ACTORID")
    private String actorId;

    @Column(name = "WFTASK_ROLEID")
    private String actorRole;

    @Column(name = "WFTASK_ESCALLEVEL")
    private Long currentEscalationLevel;

    @Column(name = "TASK_SLA_DURATION")
    private Long taskSlaDurationInMS;

    @Column(name = "WFTASK_CCHEKLEVEL")
    private Long curentCheckerLevel;

    @Column(name = "WFTASK_CCHEKGROUP")
    private Long currentCheckerGroup;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_DATE")
    private Date modifiedDate;

    @Column(name = "UPDATED_BY")
    private Long modifiedBy;

    @Column(name = "WFTASK_ASSIGDATE")
    private Date dateOfAssignment;

    @Column(name = "WFTASK_COMPDATE")
    private Date dateOfCompletion;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workFlowTask", cascade = CascadeType.ALL)
    private List<WorkflowAction> workflowActionList = new ArrayList<>();

    public Long getWorkflowTaskId() {
        return workflowTaskId;
    }

    public void setWorkflowTaskId(Long workflowTaskId) {
        this.workflowTaskId = workflowTaskId;
    }

    public WorkflowRequest getWorkFlowRequest() {
        return workFlowRequest;
    }

    public void setWorkFlowRequest(WorkflowRequest workFlowRequest) {
        this.workFlowRequest = workFlowRequest;
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

    public List<WorkflowAction> getWorkflowActionList() {
        return workflowActionList;
    }

    public void setWorkflowActionList(List<WorkflowAction> workflowActionList) {
        this.workflowActionList = workflowActionList;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_WORKFLOW_TASK", "WFTASK_ID" };
    }

}
