package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.CFCAttachment;

public class WorkflowAction implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long applicationId;

    private String referenceId;

    private String decision;

    private String comments;

    private Long orgId;

    private Long empId;

    private Long empType;

    private Long taskId;

    private String taskName;

    private Long taskSlaDurationInMS;

    private Date dateOfAction = new Date();

    private Date createdDate = new Date();

    private Long createdBy;

    private Date modifiedDate;

    private Long modifiedBy;

    private String attachmentIds;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
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

    public void setDecision(final String decision) {
        this.decision = decision;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(final Long empId) {
        this.empId = empId;
    }

    public Long getEmpType() {
        return empType;
    }

    public void setEmpType(final Long empType) {
        this.empType = empType;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(final Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(final String taskName) {
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

    public void setDateOfAction(final Date dateOfAction) {
        this.dateOfAction = dateOfAction;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

 

    public String getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    @Override
    public String toString() {
        return "WorkflowAction [id=" + id + ", applicationId=" + applicationId + ", decision=" + decision
                + ", comments=" + comments + ", orgId=" + orgId + ", empId=" + empId + ", empType=" + empType
                + ", taskId=" + taskId + ", taskName=" + taskName + ", dateOfAction=" + dateOfAction + ", createdDate="
                + createdDate + ", createdBy=" + createdBy + ", modifiedDate=" + modifiedDate + ", modifiedBy="
                + modifiedBy + ", attachmentIds=" + attachmentIds + "]";
    }

}