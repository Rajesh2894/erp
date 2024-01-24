package com.abm.mainet.common.leavemanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class LeaveManagementApproverDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 985816939731204379L;

	private Long empId;
	private Long orgId;
	private String leaveType;
	private Date leaveFromdt;
	private String leaveFromdtSession;
	private Date leaveTodt;
	private String leaveTodtSession;
	private Date leaveApplyDate;
	private String approveFlag;
	private String reason;
	private String empName;
	private String empDesignation;
	private String reportingTo;
	private Long leaveReqId;
	
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
	public String getApproveFlag() {
		return approveFlag;
	}
	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpDesignation() {
		return empDesignation;
	}
	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}
	public String getReportingTo() {
		return reportingTo;
	}
	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}
	public Long getLeaveReqId() {
		return leaveReqId;
	}
	public void setLeaveReqId(Long leaveReqId) {
		this.leaveReqId = leaveReqId;
	}
	
	

}
