package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

public class CommonAcknowledgementDto implements Serializable{

	private static final long serialVersionUID = -9134431246600184769L;
	
	private String applicantName;
	private String serviceName;
	private String departmentName;
	private Date appDate;
	private String appTime;
    private Date dueDate;
    private String dueTime;
    private String helpLine;
    private Long applicationId;
    
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getAppTime() {
		return appTime;
	}
	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getDueTime() {
		return dueTime;
	}
	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}
	public String getHelpLine() {
		return helpLine;
	}
	public void setHelpLine(String helpLine) {
		this.helpLine = helpLine;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
    
    
    

}
