package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MilestoneEntryDto implements Serializable {

	private Long mileId;

	private Long projId;

	private Long workId;

	private String milestoneName;

	private BigDecimal milestonePer;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private String WorkOrdCommDate;
	
	public Long getMileId() {
		return mileId;
	}

	public void setMileId(Long mileId) {
		this.mileId = mileId;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getMilestoneName() {
		return milestoneName;
	}

	public void setMilestoneName(String milestoneName) {
		this.milestoneName = milestoneName;
	}

	public BigDecimal getMilestonePer() {
		return milestonePer;
	}

	public void setMilestonePer(BigDecimal milestonePer) {
		this.milestonePer = milestonePer;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public String getWorkOrdCommDate() {
		return WorkOrdCommDate;
	}

	public void setWorkOrdCommDate(String workOrdCommDate) {
		WorkOrdCommDate = workOrdCommDate;
	}

}
