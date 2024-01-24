package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;

public class BndAcknowledgementDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8997414865184820634L;
	
	private String applicantTitle;
	private String applicantName;
	private String serviceShortCode;
	private String departmentName;
	private Date appDate;
	private String appTime;
    private Date dueDate;
    private String dueTime;
    private String helpLine;
    private Long applicationId;
    
    
    
	public String getApplicantTitle() {
		return applicantTitle;
	}
	public void setApplicantTitle(String applicantTitle) {
		this.applicantTitle = applicantTitle;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getServiceShortCode() {
		return serviceShortCode;
	}
	public void setServiceShortCode(String serviceShortCode) {
		this.serviceShortCode = serviceShortCode;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
  
	
}
