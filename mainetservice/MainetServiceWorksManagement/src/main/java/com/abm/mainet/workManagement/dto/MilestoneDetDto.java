package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.Date;


/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class MilestoneDetDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long mileId;

	private Long miledId;

	private String proUpdateDate;

	private BigDecimal phyPercent;

	private BigDecimal finPercent;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	

	public Long getMileId() {
		return mileId;
	}

	public void setMileId(Long mileId) {
		this.mileId = mileId;
	}

	public Long getMiledId() {
		return miledId;
	}

	public void setMiledId(Long miledId) {
		this.miledId = miledId;
	}

	public String getProUpdateDate() {
		return proUpdateDate;
	}

	public void setProUpdateDate(String proUpdateDate) {
		this.proUpdateDate = proUpdateDate;
	}

	public BigDecimal getPhyPercent() {
		return phyPercent;
	}

	public void setPhyPercent(BigDecimal phyPercent) {
		this.phyPercent = phyPercent;
	}

	public BigDecimal getFinPercent() {
		return finPercent;
	}

	public void setFinPercent(BigDecimal finPercent) {
		this.finPercent = finPercent;
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

}
