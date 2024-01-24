package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class MeasurementBookCheckListDetailDto implements Serializable {

	private static final long serialVersionUID = -8093067277379493610L;

	private Long mbcdId;

	private String mbcdValue;

	private Long slLabelId;

	private Long levels;

	private Long mbcId;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getMbcdId() {
		return mbcdId;
	}

	public void setMbcdId(Long mbcdId) {
		this.mbcdId = mbcdId;
	}

	public String getMbcdValue() {
		return mbcdValue;
	}

	public void setMbcdValue(String mbcdValue) {
		this.mbcdValue = mbcdValue;
	}

	public Long getSlLabelId() {
		return slLabelId;
	}

	public void setSlLabelId(Long slLabelId) {
		this.slLabelId = slLabelId;
	}

	public Long getLevels() {
		return levels;
	}

	public void setLevels(Long levels) {
		this.levels = levels;
	}

	public Long getMbcId() {
		return mbcId;
	}

	public void setMbcId(Long mbcId) {
		this.mbcId = mbcId;
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
