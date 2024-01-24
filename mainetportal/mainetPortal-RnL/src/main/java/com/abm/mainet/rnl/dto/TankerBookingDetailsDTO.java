package com.abm.mainet.rnl.dto;

import java.util.Date;



public class TankerBookingDetailsDTO {
	
	private Long id;
	
	private Long driverId;

	private String remark;

	private Date tankerReturnDate;

	private String returnRemark;

	private EstateBookingDTO estateBooking;
	 
	private Long orgId;

	private long langId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUp;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getTankerReturnDate() {
		return tankerReturnDate;
	}

	public void setTankerReturnDate(Date tankerReturnDate) {
		this.tankerReturnDate = tankerReturnDate;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}


	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public long getLangId() {
		return langId;
	}

	public void setLangId(long langId) {
		this.langId = langId;
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

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUp() {
		return lgIpMacUp;
	}

	public void setLgIpMacUp(String lgIpMacUp) {
		this.lgIpMacUp = lgIpMacUp;
	}

	public EstateBookingDTO getEstateBooking() {
		return estateBooking;
	}

	public void setEstateBooking(EstateBookingDTO estateBooking) {
		this.estateBooking = estateBooking;
	}

	
	
	
}
