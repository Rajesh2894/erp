package com.abm.mainet.disastermanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class AllAttendedDisasterDTO implements Serializable{
	private static final long serialVersionUID = 2110704719241314572L;
	
	private String location;
	private Date fromDate;
	private Date toDate;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
	
	
	

}
