package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;

public class DeathRegisterDTO implements Serializable{
	
private static final long serialVersionUID = 2110704719241314572L;
	
	private String periodOfReportBy;
			
	private String registrationUnit;
			
	private String reportType;
			
	private String sortOrder;
	
	private Date fromDate;
	
	private Date toDate;
	
	private Date periodFrom;
	
	private Date periodTo;
	
	private String user;
	

	public String getPeriodOfReportBy() {
		return periodOfReportBy;
	}

	public void setPeriodOfReportBy(String periodOfReportBy) {
		this.periodOfReportBy = periodOfReportBy;
	}

	public String getRegistrationUnit() {
		return registrationUnit;
	}

	public void setRegistrationUnit(String registrationUnit) {
		this.registrationUnit = registrationUnit;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
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

	public Date getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Date getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Date periodTo) {
		this.periodTo = periodTo;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}


}
