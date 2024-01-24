package com.abm.mainet.firemanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class VehicleLogBookReportDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fromDate;
	private Date toDate;
	private String veNo;
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
	public String getVeNo() {
		return veNo;
	}
	public void setVeNo(String veNo) {
		this.veNo = veNo;
	}
	
}
