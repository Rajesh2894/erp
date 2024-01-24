package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EquityGrantShareHoldingDetailDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2094523780552571201L;
	
	private Long noOfShareHolder;
	
	private BigDecimal faceValueShareAllotted;
	
	private BigDecimal totalAmtPaid;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getNoOfShareHolder() {
		return noOfShareHolder;
	}

	public void setNoOfShareHolder(Long noOfShareHolder) {
		this.noOfShareHolder = noOfShareHolder;
	}

	public BigDecimal getFaceValueShareAllotted() {
		return faceValueShareAllotted;
	}

	public void setFaceValueShareAllotted(BigDecimal faceValueShareAllotted) {
		this.faceValueShareAllotted = faceValueShareAllotted;
	}

	public BigDecimal getTotalAmtPaid() {
		return totalAmtPaid;
	}

	public void setTotalAmtPaid(BigDecimal totalAmtPaid) {
		this.totalAmtPaid = totalAmtPaid;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
	
	

}
