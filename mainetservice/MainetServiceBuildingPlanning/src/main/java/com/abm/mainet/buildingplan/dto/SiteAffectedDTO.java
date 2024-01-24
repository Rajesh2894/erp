package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;
import java.util.Date;

public class SiteAffectedDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long sId;
	private Long cfcApplicationId;
	private Long slLabelId;
	private Long gmId;
	private Long level;
	private Long dsgId;
	private Double buffer;
	private String capacity;
	private Long orgId;
	private Date createdDate;
	private Long createdBy;
	private Date updatedDate;
	private Long updatedBy;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String mainLine;
	private String status;

	public Long getsId() {
		return sId;
	}

	public void setsId(Long sId) {
		this.sId = sId;
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

	public Double getBuffer() {
		return buffer;
	}

	public void setBuffer(Double buffer) {
		this.buffer = buffer;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
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

	public String getMainLine() {
		return mainLine;
	}

	public void setMainLine(String mainLine) {
		this.mainLine = mainLine;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
