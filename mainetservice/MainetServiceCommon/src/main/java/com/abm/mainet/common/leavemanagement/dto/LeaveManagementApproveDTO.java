package com.abm.mainet.common.leavemanagement.dto;

import java.io.Serializable;

public class LeaveManagementApproveDTO implements Serializable {

	/**
	 * @author bhagyashri.dongardive
	 *
	 */
	private static final long serialVersionUID = 4885256105028414217L;

	private Long leaveReqId;
	private Long approverId;
	private Long empId;
	private Long orgId;
	private String approverRemarks;
	private String approveFlag;
	private String lgIpMacUpd;
	
	
	public Long getLeaveReqId() {
		return leaveReqId;
	}
	public void setLeaveReqId(Long leaveReqId) {
		this.leaveReqId = leaveReqId;
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
	public String getApproverRemarks() {
		return approverRemarks;
	}
	public void setApproverRemarks(String approverRemarks) {
		this.approverRemarks = approverRemarks;
	}
	public String getApproveFlag() {
		return approveFlag;
	}
	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}
	public Long getApproverId() {
		return approverId;
	}
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}
	
	
	

}
