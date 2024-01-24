package com.abm.mainet.bpm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_WORKFLOWTASK_DETAIL")
public class TaskDetailView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4158518721068108424L;

    @Id
    @Column(name = "WORKFLOW_ACT_ID")
    private Long workFlowActionId;

    @Column(name = "APM_APPLICATION_ID")
    private Long applicationId;

    @Column(name = "DECISION")
    private String lastDecision;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "EMPID")
    private Long empId;

    @Column(name = "EMPL_TYPE")
    private Long empType;

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

    @Column(name = "serviceEventName")
    private String serviceEventName;

    @Column(name = "serviceEventNameReg")
    private String serviceEventNameReg;

    @Column(name = "SMFACTION")
    private String serviceEventUrl;

    @Column(name = "WFTASK_ESCALLEVEL")
    private Long currentEscalationLevel;

    @Column(name = "TASK_SLA_DURATION")
    private Long taskSlaDurationInMS;

    @Column(name = "WFTASK_CCHEKLEVEL")
    private Long curentCheckerLevel;

    @Column(name = "WFTASK_CCHEKGROUP")
    private Long currentCheckerGroup;

    @Column(name = "TASK_ID")
    private Long taskId;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "DATE_OF_ACTION")
    private Date dateOfAction;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_DATE")
    private Date modifiedDate;

    @Column(name = "UPDATED_BY")
    private Long modifiedBy;

    @Column(name = "EMPNAME")
    private String empName;

    @Column(name = "EMPMNAME")
    private String empMName;

    @Column(name = "EMPLNAME")
    private String empLName;

    @Column(name = "EMPEMAIL")
    private String empEmail;

    @Column(name = "GR_DESC_ENG")
    private String grDescEng;

    @Column(name = "GR_DESC_REG")
    private String grDescReg;

    @Column(name = "REFERENCE_ID")
    private String referenceId;

    @Column(name = "att_path")
    private String attPath;

    @Column(name = "TASK_STATUS")
    private String taskStatus;

    @Column(name = "EMPMOBNO")
    private String mobileNumber;

    @Column(name = "WORKFLOW_REQ_ID")
    private Long workflowReqId;

    @Column(name = "WORFLOW_TYPE_ID")
    private Long workflowId;

    @Column(name = "WFTASK_ASSIGDATE")
    private Date dateOfAssignment;

    @Column(name = "WFTASK_COMPDATE")
    private Date dateOfCompletion;

    @Column(name = "DATE_OF_REQUEST")
    private Date requestDate;

    public Long getWorkFlowActionId() {
        return workFlowActionId;
    }

    public void setWorkFlowActionId(Long workFlowActionId) {
        this.workFlowActionId = workFlowActionId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(String lastDecision) {
        this.lastDecision = lastDecision;
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

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpMName() {
        return empMName;
    }

    public void setEmpMName(String empMName) {
        this.empMName = empMName;
    }

    public String getEmpLName() {
        return empLName;
    }

    public void setEmpLName(String empLName) {
        this.empLName = empLName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getGrDescEng() {
        return grDescEng;
    }

    public void setGrDescEng(String grDescEng) {
        this.grDescEng = grDescEng;
    }

    public String getGrDescReg() {
        return grDescReg;
    }

    public void setGrDescReg(String grDescReg) {
        this.grDescReg = grDescReg;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getAttPath() {
        return attPath;
    }

    public void setAttPath(String attPath) {
        this.attPath = attPath;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Long getWorkflowReqId() {
        return workflowReqId;
    }

    public void setWorkflowReqId(Long workflowReqId) {
        this.workflowReqId = workflowReqId;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
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

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

}
