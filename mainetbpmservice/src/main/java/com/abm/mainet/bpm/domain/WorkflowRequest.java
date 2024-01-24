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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_WORKFLOW_REQUEST")
public class WorkflowRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.bpm.domain.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WORKFLOW_REQ_ID")
    private Long id;

    @Column(name = "APM_APPLICATION_ID")
    private Long applicationId;

    @Column(name = "REFERENCE_ID")
    private String referenceId;

    @Column(name = "PROCESS_SESSIONID")
    private Long processSessionId;

    @Column(name = "PROCESS_NAME")
    private String processName;

    @Column(name = "WORFLOW_TYPE_ID")
    private Long workflowTypeId;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "EMPID")
    private Long empId;

    @Column(name = "EMPL_TYPE")
    private Long empType;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "LAST_DECISION")
    private String lastDecision;

    @Column(name = "APPLICATION_SLA_DURATION")
    private Long applicationSlaDurationInMS;

    @Column(name = "DATE_OF_REQUEST")
    private Date dateOfRequest = new Date();

    @Column(name = "LAST_DATE_OF_ACTION")
    private Date lastDateOfAction;

    @Column(name = "CREATED_DATE")
    private Date createdDate = new Date();

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_DATE")
    private Date modifiedDate;

    @Column(name = "UPDATED_BY")
    private Long modifiedBy;

    // added Deployment Id for Completing old version task
    @Column(name = "DEPLOYMENT_ID")
    private Long deploymentId;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workFlowRequest", cascade = CascadeType.ALL)
    private List<WorkflowUserTask> workFlowTaskList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getProcessSessionId() {
        return processSessionId;
    }

    public void setProcessSessionId(Long processSessionId) {
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

    public void setWorkflowTypeId(Long workflowTypeId) {
        this.workflowTypeId = workflowTypeId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public void setDateOfRequest(Date dateOfRequest) {
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

    public List<WorkflowUserTask> getWorkFlowTaskList() {
        return workFlowTaskList;
    }

    public void setWorkFlowTaskList(List<WorkflowUserTask> workFlowTaskList) {
        this.workFlowTaskList = workFlowTaskList;
    }

    public Long getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(Long deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_WORKFLOW_REQUEST", "WORKFLOW_REQ_ID" };
    }

}
