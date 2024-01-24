package com.abm.mainet.tradeLicense.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.abm.mainet.common.integration.dms.domain.CFCAttachment;

public class OwnerFamilyDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5842462035878731077L;
	private Long famMemId;
	@JsonIgnore
	private TradeMasterDetailDTO masterTradeId;
	private String famMemName;
	private Long famMemUidNo;
	private Long famMemAge;
	private String famMemRelation;
	private Long orgid;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;

	private List<CFCAttachment> viewImg = new ArrayList<>();

	public Long getFamMemId() {
		return famMemId;
	}

	public void setFamMemId(Long famMemId) {
		this.famMemId = famMemId;
	}

	public TradeMasterDetailDTO getMasterTradeId() {
		return masterTradeId;
	}

	public void setMasterTradeId(TradeMasterDetailDTO masterTradeId) {
		this.masterTradeId = masterTradeId;
	}

	public String getFamMemName() {
		return famMemName;
	}

	public void setFamMemName(String famMemName) {
		this.famMemName = famMemName;
	}

	public Long getFamMemUidNo() {
		return famMemUidNo;
	}

	public void setFamMemUidNo(Long famMemUidNo) {
		this.famMemUidNo = famMemUidNo;
	}

	public Long getFamMemAge() {
		return famMemAge;
	}

	public void setFamMemAge(Long famMemAge) {
		this.famMemAge = famMemAge;
	}

	public String getFamMemRelation() {
		return famMemRelation;
	}

	public void setFamMemRelation(String famMemRelation) {
		this.famMemRelation = famMemRelation;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public List<CFCAttachment> getViewImg() {
		return viewImg;
	}

	public void setViewImg(List<CFCAttachment> viewImg) {
		this.viewImg = viewImg;
	}

	

}
