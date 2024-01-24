package com.abm.mainet.lqp.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;

public class QueryRegistrationMasterDto implements Serializable {

    private static final long serialVersionUID = -417350226769888469L;

    private Long questionRegId;

    private String questionId;

    private String questionIdWF;

    private Date questionDate;

    private String inwardNo;

    private Long questionTypeId;

    private String questionType;

    private Long raisedByAssemblyId;

    private String raisedByAssembly;

    private Long deptId;

    private Department department;

    private Long empId;

    private Employee employee;

    private String questionSubject;

    private String mlaName;

    private String question;

    private Date meetingDate;

    private Date deadlineDate;

    private Date questionRaisedDate;

    private String status;

    private String reopenReason;

    private Date reopenDate;

    private String remark;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Date fromDate;

    private Date toDate;
    
    private String isAttachDoc;

    public Long getQuestionRegId() {
        return questionRegId;
    }

    public void setQuestionRegId(Long questionRegId) {
        this.questionRegId = questionRegId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionIdWF() {
        return questionIdWF;
    }

    public void setQuestionIdWF(String questionIdWF) {
        this.questionIdWF = questionIdWF;
    }

    public Date getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Date questionDate) {
        this.questionDate = questionDate;
    }

    public String getInwardNo() {
        return inwardNo;
    }

    public void setInwardNo(String inwardNo) {
        this.inwardNo = inwardNo;
    }

    public Long getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(Long questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Long getRaisedByAssemblyId() {
        return raisedByAssemblyId;
    }

    public void setRaisedByAssemblyId(Long raisedByAssemblyId) {
        this.raisedByAssemblyId = raisedByAssemblyId;
    }

    public String getRaisedByAssembly() {
        return raisedByAssembly;
    }

    public void setRaisedByAssembly(String raisedByAssembly) {
        this.raisedByAssembly = raisedByAssembly;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getQuestionSubject() {
        return questionSubject;
    }

    public void setQuestionSubject(String questionSubject) {
        this.questionSubject = questionSubject;
    }

    public String getMlaName() {
        return mlaName;
    }

    public void setMlaName(String mlaName) {
        this.mlaName = mlaName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Date getQuestionRaisedDate() {
        return questionRaisedDate;
    }

    public void setQuestionRaisedDate(Date questionRaisedDate) {
        this.questionRaisedDate = questionRaisedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReopenReason() {
        return reopenReason;
    }

    public void setReopenReason(String reopenReason) {
        this.reopenReason = reopenReason;
    }

    public Date getReopenDate() {
        return reopenDate;
    }

    public void setReopenDate(Date reopenDate) {
        this.reopenDate = reopenDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "QueryRegistrationMasterDto [questionRegId=" + questionRegId + ", questionId=" + questionId + ", questionDate="
                + questionDate + ", inwardNo=" + inwardNo + ", questionType=" + questionType + ", raisedByAssembly="
                + raisedByAssembly + ", department=" + department + ", questionSubject=" + questionSubject + ", mlaName="
                + mlaName + ", question=" + question + ", meetingDate=" + meetingDate + ", deadlineDate=" + deadlineDate
                + ", questionRaisedDate=" + questionRaisedDate + ", status=" + status + ", reopenReason=" + reopenReason
                + ", remark=" + remark + ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate=" + createdDate
                + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + "]";
    }

	public String getIsAttachDoc() {
		return isAttachDoc;
	}

	public void setIsAttachDoc(String isAttachDoc) {
		this.isAttachDoc = isAttachDoc;
	}

}
