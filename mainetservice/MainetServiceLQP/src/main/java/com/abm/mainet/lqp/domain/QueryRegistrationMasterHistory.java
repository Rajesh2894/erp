package com.abm.mainet.lqp.domain;

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

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_LQP_QUERY_REGISTRATION_HIST")
public class QueryRegistrationMasterHistory implements Serializable {

    private static final long serialVersionUID = -439195488382715105L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "QUESTION_REG_ID_H")
    private Long questionRegHId;

    @Column(name = "QUESTION_REG_ID", nullable = false)
    private Long questionRegId;

    @Column(name = "QUESTION_ID", length = 20, nullable = false)
    private String questionId;

    @Column(name = "QUESTION_ID_WF", length = 50, nullable = false)
    private String questionIdWF;

    @Column(name = "QUESTION_DATE", nullable = false)
    private Date questionDate;

    @Column(name = "INWARD_NO", length = 20, nullable = false)
    private String inwardNo;

    @Column(name = "QUESTION_TYPE", nullable = false)
    private Long questionTypeId;

    @Column(name = "RAISED_BY_ASSEMBLY", nullable = false)
    private Long raisedByAssemblyId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_ID", nullable = false)
    private Department department;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMP_ID", nullable = false)
    private Employee employee;

    @Column(name = "QUESTION_SUBJECT", length = 100, nullable = false)
    private String questionSubject;

    @Column(name = "MLA_NAME", length = 100, nullable = false)
    private String mlaName;

    @Column(name = "QUESTION", length = 500, nullable = false)
    private String question;

    @Column(name = "MEETING_DATE", nullable = false)
    private Date meetingDate;

    @Column(name = "DEADLINE_DATE", nullable = false)
    private Date deadlineDate;

    @Column(name = "QUESTION_RAISED_DATE", nullable = false)
    private Date questionRaisedDate;

    @Column(name = "STATUS", length = 15, nullable = false)
    private String status;

    @Column(name = "REOPEN_REASON", length = 100)
    private String reopenReason;

    @Column(name = "REOPEN_DATE")
    private Date reopenDate;

    @Column(name = "REMARK", length = 100)
    private String remark;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "IS_ATTACH_DOC", length = 1, nullable = true)
    private String isAttachDoc;

    public Long getQuestionRegHId() {
        return questionRegHId;
    }

    public void setQuestionRegHId(Long questionRegHId) {
        this.questionRegHId = questionRegHId;
    }

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

    public Long getRaisedByAssemblyId() {
        return raisedByAssemblyId;
    }

    public void setRaisedByAssemblyId(Long raisedByAssemblyId) {
        this.raisedByAssemblyId = raisedByAssemblyId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public static String[] getPkValues() {
        return new String[] { "LQP", "TB_LQP_QUERY_REGISTRATION_HIST", "QUESTION_REG_ID_H" };
    }

    public String getIsAttachDoc() {
        return isAttachDoc;
    }

    public void setIsAttachDoc(String isAttachDoc) {
        this.isAttachDoc = isAttachDoc;
    }

}
