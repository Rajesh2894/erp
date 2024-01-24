package com.abm.mainet.bpm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TB_WORKFLOW_ACTION")
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.bpm.domain.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WORKFLOW_ACT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WFTASK_ID", nullable = false)
    private WorkflowUserTask workFlowTask;

    @Column(name = "APM_APPLICATION_ID")
    private Long applicationId;

    @Column(name = "REFERENCE_ID")
    private String referenceId;

    @Column(name = "DECISION")
    private String decision;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "EMPID")
    private Long empId;

    @Column(name = "EMPL_TYPE")
    private Long empType;

    @Column(name = "TASK_ID")
    private Long taskId;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "TASK_SLA_DURATION")
    private Long taskSlaDurationInMS;

    @Column(name = "DATE_OF_ACTION")
    private Date dateOfAction = new Date();

    @Column(name = "CREATED_DATE")
    private Date createdDate = new Date();

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_DATE")
    private Date modifiedDate;

    @Column(name = "UPDATED_BY")
    private Long modifiedBy;

    @Column(name = "WORKFLOW_REQ_ID")
    private Long workFlowRequestId;

    @Column(name = "WORKFLOW_ATT")
    private String attachmentIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkflowUserTask getWorkFlowTask() {
        return workFlowTask;
    }

    public void setWorkFlowTask(WorkflowUserTask workFlowTask) {
        this.workFlowTask = workFlowTask;
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

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getEmpType() {
        return empType;
    }

    public void setEmpType(Long empType) {
        this.empType = empType;
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

    public Long getTaskSlaDurationInMS() {
        return taskSlaDurationInMS;
    }

    public void setTaskSlaDurationInMS(Long taskSlaDurationInMS) {
        this.taskSlaDurationInMS = taskSlaDurationInMS;
    }

    public Date getDateOfAction() {
        return dateOfAction;
    }

    public void setDateOfAction(Date dateOfAction) {
        this.dateOfAction = dateOfAction;
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

    public Long getWorkFlowRequestId() {
        return workFlowRequestId;
    }

    public void setWorkFlowRequestId(Long workFlowRequestId) {
        this.workFlowRequestId = workFlowRequestId;
    }

    public String getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    @Override
    public String toString() {
        return "WorkflowAction [id=" + id + ", applicationId=" + applicationId + ", decision=" + decision + ", comments="
                + comments + ", orgId=" + orgId + ", empId=" + empId + ", empType=" + empType + ", taskId=" + taskId
                + ", taskName=" + taskName + ", dateOfAction=" + dateOfAction + ", createdDate=" + createdDate + ", createdBy="
                + createdBy + ", modifiedDate=" + modifiedDate + ", modifiedBy=" + modifiedBy + ", attachmentIds=" + attachmentIds
                + "]";
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_WORKFLOW_ACTION", "WORKFLOW_ACT_ID" };
    }

}