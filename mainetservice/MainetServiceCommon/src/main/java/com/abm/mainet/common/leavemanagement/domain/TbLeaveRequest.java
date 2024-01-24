package com.abm.mainet.common.leavemanagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "TB_LEAVE_REQUEST")
public class TbLeaveRequest implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 3959422064422551712L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "LEAVE_REQ_ID", nullable = false)
	private Long leaveReqId;

	@Column(name = "LEAVE_GRANT_ID", nullable = false)
	private Long leaveGrantId;

	@Column(name = "EMPID", nullable = false)
	private Long empId;

	@Column(name = "ORGID",  nullable = false)
	private Long orgId;

	@Column(name = "LEAVE_TYPE")
	private String leaveType;

	@Temporal(TemporalType.DATE)
	@Column(name = "LEAVE_FROMDT")
	private Date leaveFromdt;

	@Column(name = "LEAVE_FROMDT_SESSION")
	private String leaveFromdtSession;

	@Temporal(TemporalType.DATE)
	@Column(name = "LEAVE_TODT")
	private Date leaveTodt;

	@Column(name = "LEAVE_TODT_SESSION")
	private String leaveTodtSession;

	@Temporal(TemporalType.DATE)
	@Column(name = "LEAVE_APPLY_DATE")
	private Date leaveApplyDate;

	@Column(name = "APPROVER_ID")
	private Long approverId;

	@Column(name = "APPROVE_FLG")
	private String approveFlag;

	@Temporal(TemporalType.DATE)
	@Column(name = "APPROVE_DATE")
	private Date approveDate;

	@Column(name = "APPROVER_REMARKS")
	private String approverRemarks;

	@Column(name = "REASON")
	private String reason;

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = false, updatable = false)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "NO_OF_DAYS")
	private BigDecimal noOfDays;

	public BigDecimal getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(BigDecimal noOfDays) {
		this.noOfDays = noOfDays;
	}

	public Long getLeaveReqId() {
		return leaveReqId;
	}

	public void setLeaveReqId(Long leaveReqId) {
		this.leaveReqId = leaveReqId;
	}

	public Long getLeaveGrantId() {
		return leaveGrantId;
	}

	public void setLeaveGrantId(Long leaveGrantId) {
		this.leaveGrantId = leaveGrantId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public Date getLeaveFromdt() {
		return leaveFromdt;
	}

	public void setLeaveFromdt(Date leaveFromdt) {
		this.leaveFromdt = leaveFromdt;
	}

	public String getLeaveFromdtSession() {
		return leaveFromdtSession;
	}

	public void setLeaveFromdtSession(String leaveFromdtSession) {
		this.leaveFromdtSession = leaveFromdtSession;
	}

	public Date getLeaveTodt() {
		return leaveTodt;
	}

	public void setLeaveTodt(Date leaveTodt) {
		this.leaveTodt = leaveTodt;
	}

	public String getLeaveTodtSession() {
		return leaveTodtSession;
	}

	public void setLeaveTodtSession(String leaveTodtSession) {
		this.leaveTodtSession = leaveTodtSession;
	}

	public Date getLeaveApplyDate() {
		return leaveApplyDate;
	}

	public void setLeaveApplyDate(Date leaveApplyDate) {
		this.leaveApplyDate = leaveApplyDate;
	}

	public Long getApproverId() {
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public String getApproveFlag() {
		return approveFlag;
	}

	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getApproverRemarks() {
		return approverRemarks;
	}

	public void setApproverRemarks(String approverRemarks) {
		this.approverRemarks = approverRemarks;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
	public String[] getPkValues() {
        return new String[] { "LEAVE", "TB_LEAVE_REQUEST", "LEAVE_REQ_ID" };
    }
}
