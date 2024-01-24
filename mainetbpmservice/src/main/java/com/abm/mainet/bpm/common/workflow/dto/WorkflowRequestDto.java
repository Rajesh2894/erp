package com.abm.mainet.bpm.common.workflow.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkflowRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long applicationId;

    private String referenceId;

    private Long processSessionId;

    private String processName;

    private Long workflowTypeId;

    private Long orgId;

    private Long empId;

    private Long empType;

    private String status;

    private String lastDecision;

    private Long applicationSlaDurationInMS;

    private Date dateOfRequest = new Date();

    private Date lastDateOfAction;

    private Date createdDate = new Date();

    private Long createdBy;

    private Date modifiedDate;

    private Long modifiedBy;

    private List<WorkflowTaskDto> workFlowTaskList = new ArrayList<>();

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

    public Long getProcessSessionId() {
        return processSessionId;
    }

    public void setProcessSessionId(final Long processSessionId) {
        this.processSessionId = processSessionId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Long getWorkflowTypeId() {
        return workflowTypeId;
    }

    public void setWorkflowTypeId(final Long workflowTypeId) {
        this.workflowTypeId = workflowTypeId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(String lastDecision) {
        this.lastDecision = lastDecision;
    }

    public Long getApplicationSlaDurationInMS() {
        return applicationSlaDurationInMS;
    }

    public void setApplicationSlaDurationInMS(Long applicationSlaDurationInMS) {
        this.applicationSlaDurationInMS = applicationSlaDurationInMS;
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(final Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public Date getLastDateOfAction() {
        return lastDateOfAction;
    }

    public void setLastDateOfAction(Date lastDateOfAction) {
        this.lastDateOfAction = lastDateOfAction;
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

    public List<WorkflowTaskDto> getWorkFlowTaskList() {
        return workFlowTaskList;
    }

    public void setWorkFlowTaskList(List<WorkflowTaskDto> workFlowTaskList) {
        this.workFlowTaskList = workFlowTaskList;
    }

}
