package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

public class CFCSchedulingCounterDet implements Serializable {

	private static final long serialVersionUID = -8900729209374532782L;
	private String collcntrno;
	private String counterno;
	private Long empId;
    //119534  Fields added to get/set from api call
	private Date fromTime;
	private Date toTime;
	private String frequencySts;
	private String scheduleSts;
	
	private Long dwzId1;
	private Long orgId;
	
	private String deviceId;
	
	public String getScheduleSts() {
		return scheduleSts;
	}

	public void setScheduleSts(String scheduleSts) {
		this.scheduleSts = scheduleSts;
	}

	public String getFrequencySts() {
		return frequencySts;
	}

	public void setFrequencySts(String frequencySts) {
		this.frequencySts = frequencySts;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	public String getCollcntrno() {
		return collcntrno;
	}

	public void setCollcntrno(String collcntrno) {
		this.collcntrno = collcntrno;
	}

	public String getCounterno() {
		return counterno;
	}

	public void setCounterno(String counterno) {
		this.counterno = counterno;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getDwzId1() {
		return dwzId1;
	}

	public void setDwzId1(Long dwzId1) {
		this.dwzId1 = dwzId1;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
