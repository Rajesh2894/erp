package com.abm.mainet.common.integration.dto;


public class Permission{
	private String access;
	private DateRange dateRange;
	private Frequency frequency;
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public DateRange getDateRange() {
		return dateRange;
	}
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	
	
}
