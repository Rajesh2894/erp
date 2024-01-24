package com.abm.mainet.securitymanagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tb_sm_shift_master")
public class ShiftMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name="SHIFT_MAS_ID")
	private Long shiftMasId;

	@Column(name="SHIFT_DESC")
	private String shiftDesc;

	@Column(name="SHIFT_ID")
	private Long shiftId;
	
	@Column(name="FROM_TIME")
	private String fromTime;

	@Column(name="TO_TIME")
	private String toTime;
	
	@Column(name="IS_CROSS_DAY_SHIFT")
	private String isCrossDayShift;

	@Column(name="IS_GENERAL_SHIFT")
	private String isGeneralShift;

	@Column(name="STATUS")
	private String status;
	
	@Column(name="LG_IP_MAC")
	private String lgIpMac;

	@Column(name="LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	private Long orgid;

	@Column(name="CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	
	public Long getShiftMasId() {
		return shiftMasId;
	}

	public void setShiftMasId(Long shiftMasId) {
		this.shiftMasId = shiftMasId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getShiftDesc() {
		return shiftDesc;
	}

	public void setShiftDesc(String shiftDesc) {
		this.shiftDesc = shiftDesc;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
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
	
	public String[] getPkValues() {
        return new String[] { "SM", "tb_sm_shift_master", "SHIFT_MAS_ID" };
    }
	
}
