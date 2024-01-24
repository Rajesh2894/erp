package com.abm.mainet.disastermanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class DisasterCallDetailsDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5464393974349125359L;
	private String location;
	private Long callType1;
	private Long callType2;
	private Long callType3;
	private Long callType4;
	private Long callType5;
	private Date fromDate;
	private Date toDate;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	/*
	 * public Long getCallType() { return callType; } public void setCallType(Long
	 * callType) { this.callType = callType; }
	 */
	
	public Date getFromDate() {
		return fromDate;
	}
	
	public Long getCallType1() {
		return callType1;
	}
	public void setCallType1(Long callType1) {
		this.callType1 = callType1;
	}
	public Long getCallType2() {
		return callType2;
	}
	public void setCallType2(Long callType2) {
		this.callType2 = callType2;
	}
	public Long getCallType3() {
		return callType3;
	}
	public void setCallType3(Long callType3) {
		this.callType3 = callType3;
	}
	public Long getCallType4() {
		return callType4;
	}
	public void setCallType4(Long callType4) {
		this.callType4 = callType4;
	}
	public Long getCallType5() {
		return callType5;
	}
	public void setCallType5(Long callType5) {
		this.callType5 = callType5;
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
	
	

}
