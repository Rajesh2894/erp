package com.abm.mainet.common.dto;

import java.io.Serializable;

import java.util.Date;

public class TbResourceDetDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3185128411027401074L;

	private Long resDetId;

	private String fieldId;

	private TbResourceMasDto tbResource;

	private String isMandatory;

	private String isVisible;

	private Long userId;

	private Date lmodDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long orgId;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getResDetId() {
		return resDetId;
	}

	public void setResDetId(Long resDetId) {
		this.resDetId = resDetId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public TbResourceMasDto getTbResource() {
		return tbResource;
	}

	public void setTbResource(TbResourceMasDto tbResource) {
		this.tbResource = tbResource;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
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
