package com.abm.mainet.common.leavemanagement.dto;

import java.io.Serializable;



public class LeaveManagementApplyRequestDTO  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6754546762610596425L;
	private Long empId;
	private Long orgId;
	private String leaveType;
	private String leaveFromdt;
	private String leaveFromdtSession;
	private String leaveTodt;
	private String leaveTodtSession;
	private Long approverId;
	private String lgIpMac;
	private String reason;
	private String base64DocData;
	

	public String getBase64DocData() {
		return base64DocData;
	}

	public void setBase64DocData(String base64DocData) {
		this.base64DocData = base64DocData;
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
	
	public String getLeaveFromdtSession() {
		return leaveFromdtSession;
	}
	public void setLeaveFromdtSession(String leaveFromdtSession) {
		this.leaveFromdtSession = leaveFromdtSession;
	}
	
	public String getLeaveTodtSession() {
		return leaveTodtSession;
	}
	public void setLeaveTodtSession(String leaveTodtSession) {
		this.leaveTodtSession = leaveTodtSession;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLgIpMac() {
		return lgIpMac;
	}
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}
	
	public Long getApproverId() {
		return approverId;
	}
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}
	public String getLeaveFromdt() {
		return leaveFromdt;
	}

	public void setLeaveFromdt(String leaveFromdt) {
		this.leaveFromdt = leaveFromdt;
	}

	public String getLeaveTodt() {
		return leaveTodt;
	}

	public void setLeaveTodt(String leaveTodt) {
		this.leaveTodt = leaveTodt;
	}	
}
