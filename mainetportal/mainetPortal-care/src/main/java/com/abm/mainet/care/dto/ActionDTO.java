package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long applicationId;
    private String decision;
    private String comments;
    private Long orgId;
    private Long empId;
    private Long empType;
    private Long taskId;
    private String taskName;
    private Date dateOfAction;
    private Date createdDate;
    private Long createdBy;
    private Date modifiedDate;
    private Long modifiedBy;
    private String empName;
    private String empEmail;
    private String empGroupDescEng;
    private String empGroupDescReg;
    private List<Long> attachementId;

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

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpGroupDescEng() {
        return empGroupDescEng;
    }

    public void setEmpGroupDescEng(String empGroupDescEng) {
        this.empGroupDescEng = empGroupDescEng;
    }

    public String getEmpGroupDescReg() {
        return empGroupDescReg;
    }

    public void setEmpGroupDescReg(String empGroupDescReg) {
        this.empGroupDescReg = empGroupDescReg;
    }

    public List<Long> getAttachementId() {
        return attachementId;
    }

    public void setAttachementId(List<Long> attachementId) {
        this.attachementId = attachementId;
    }

}