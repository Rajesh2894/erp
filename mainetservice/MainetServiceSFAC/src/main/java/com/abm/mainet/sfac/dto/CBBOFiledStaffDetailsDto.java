package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

public class CBBOFiledStaffDetailsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6476299648027273948L;
	
	
	private Long fsdId;

	private Long cbboId;
	
	
	private String cbboExpertName;
	
	private String emailId;
	
	private Long contactNo;
	
	private Long sdb1;

	private Long sdb2;

	private Long sdb3;
	
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getFsdId() {
		return fsdId;
	}

	public void setFsdId(Long fsdId) {
		this.fsdId = fsdId;
	}

	public Long getCbboId() {
		return cbboId;
	}

	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}

	public String getCbboExpertName() {
		return cbboExpertName;
	}

	public void setCbboExpertName(String cbboExpertName) {
		this.cbboExpertName = cbboExpertName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getContactNo() {
		return contactNo;
	}

	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}

	public Long getSdb1() {
		return sdb1;
	}

	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}

	public Long getSdb2() {
		return sdb2;
	}

	public void setSdb2(Long sdb2) {
		this.sdb2 = sdb2;
	}

	public Long getSdb3() {
		return sdb3;
	}

	public void setSdb3(Long sdb3) {
		this.sdb3 = sdb3;
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
