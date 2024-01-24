package com.abm.mainet.disastermanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class CallScrutinyDTO implements Serializable  {

	/**
	 * 
	 */
private static final long serialVersionUID = 1L;

	
	private String tbDepartment;
	private String empName;
	private String complainStatus;
	private String complaintRemark;
	

	private Date callAttendDate;

	private String callAttendTime;
	
	private String callAttendEmployee;

	private String reasonForDelay;
	
	public String getTbDepartment() {
		return tbDepartment;
	}
	public void setTbDepartment(String tbDepartment) {
		this.tbDepartment = tbDepartment;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getComplainStatus() {
		return complainStatus;
	}
	public void setComplainStatus(String complainStatus) {
		this.complainStatus = complainStatus;
	}
	public String getComplaintRemark() {
		return complaintRemark;
	}
	public void setComplaintRemark(String complaintRemark) {
		this.complaintRemark = complaintRemark;
	}	
	public Date getCallAttendDate() {
		return callAttendDate;
	}

	public void setCallAttendDate(Date callAttendDate) {
		this.callAttendDate = callAttendDate;
	}

	public String getCallAttendTime() {
		return callAttendTime;
	}

	public void setCallAttendTime(String callAttendTime) {
		this.callAttendTime = callAttendTime;
	}

	public String getCallAttendEmployee() {
		return callAttendEmployee;
	}

	public void setCallAttendEmployee(String callAttendEmployee) {
		this.callAttendEmployee = callAttendEmployee;
	}

	public String getReasonForDelay() {
		return reasonForDelay;
	}

	public void setReasonForDelay(String reasonForDelay) {
		this.reasonForDelay = reasonForDelay;
	}
	
}	