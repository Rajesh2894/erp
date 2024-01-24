package com.abm.mainet.securitymanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class ShiftMasterDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long shiftMasId;
	private Long shiftId;
	private String shiftDesc;
	private String fromTime;
	private String toTime;
	private String isCrossDayShift;
	private String isGeneralShift;
	private Long createdBy;
	private Date createdDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long orgid;
	private Long updatedBy;
	private Date updatedDate;
	private String shiftIdDesc;
	private String status;
	private Long count;
	private String crossDayFlag;
	private String generalDayFlag;

	public String getCrossDayFlag() {
		return crossDayFlag;
	}

	public void setCrossDayFlag(String crossDayFlag) {
		this.crossDayFlag = crossDayFlag;
	}

	public String getGeneralDayFlag() {
		return generalDayFlag;
	}

	public void setGeneralDayFlag(String generalDayFlag) {
		this.generalDayFlag = generalDayFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getShiftIdDesc() {
		return shiftIdDesc;
	}

	public void setShiftIdDesc(String shiftIdDesc) {
		this.shiftIdDesc = shiftIdDesc;
	}

	public Long getShiftMasId() {
		return shiftMasId;
	}

	public void setShiftMasId(Long shiftMasId) {
		this.shiftMasId = shiftMasId;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public String getShiftDesc() {
		return shiftDesc;
	}

	public void setShiftDesc(String shiftDesc) {
		this.shiftDesc = shiftDesc;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getIsCrossDayShift() {
		return isCrossDayShift;
	}

	public void setIsCrossDayShift(String isCrossDayShift) {
		this.isCrossDayShift = isCrossDayShift;
	}

	public String getIsGeneralShift() {
		return isGeneralShift;
	}

	public void setIsGeneralShift(String isGeneralShift) {
		this.isGeneralShift = isGeneralShift;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
