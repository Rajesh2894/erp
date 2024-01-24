package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;
import java.util.Date;

public class LicenseGrantedDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long dId;
	private Long cfcApplicationId;
	private Long slLabelId;
	private Long gmId;
	private Long level;
	private Long dsgId;
	private Long sequence;
	private String categoryOfAppliedLand;
	private Double area;
	private Double npa;
	private Double balanceNpa;
	private Long createdBy;
	private Date createdDate;
	private Date updatedDate;
	private Long updatedBy;
	private String status;
	private String lgIpMac;
	private String lgIpMacUpd;

	public Long getdId() {
		return dId;
	}

	public void setdId(Long dId) {
		this.dId = dId;
	}

	public Long getCfcApplicationId() {
		return cfcApplicationId;
	}

	public void setCfcApplicationId(Long cfcApplicationId) {
		this.cfcApplicationId = cfcApplicationId;
	}

	public Long getSlLabelId() {
		return slLabelId;
	}

	public void setSlLabelId(Long slLabelId) {
		this.slLabelId = slLabelId;
	}

	public Long getGmId() {
		return gmId;
	}

	public void setGmId(Long gmId) {
		this.gmId = gmId;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getDsgId() {
		return dsgId;
	}

	public void setDsgId(Long dsgId) {
		this.dsgId = dsgId;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getCategoryOfAppliedLand() {
		return categoryOfAppliedLand;
	}

	public void setCategoryOfAppliedLand(String categoryOfAppliedLand) {
		this.categoryOfAppliedLand = categoryOfAppliedLand;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getNpa() {
		return npa;
	}

	public void setNpa(Double npa) {
		this.npa = npa;
	}

	public Double getBalanceNpa() {
		return balanceNpa;
	}

	public void setBalanceNpa(Double balanceNpa) {
		this.balanceNpa = balanceNpa;
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

}
