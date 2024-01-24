package com.abm.mainet.firemanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class CallDetailsDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fireStation;
	private Date fromDate;
	private Date toDate;
	public String getFireStation() {
		return fireStation;
	}
	public void setFireStation(String fireStation) {
		this.fireStation = fireStation;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CallDetailsDTO [fireStation=" + fireStation + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
	}
	

}
