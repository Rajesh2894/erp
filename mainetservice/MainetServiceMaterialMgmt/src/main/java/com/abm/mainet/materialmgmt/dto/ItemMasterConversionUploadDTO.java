package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ItemMasterConversionUploadDTO {

	private Long convid;
	private Long itemid;
	private Long convuom;
	private Long units;
	private String Status;
	private Long orgid;
	private Long userId;
	private int langId;
	private Date lmoddate;
	private Long updatedBy;
	private Date updatedDate;
	@Size(max = 100)
	private String lgIpMac;
	@Size(max = 100)
	private String lgIpMacUpd;

	public Long getConvid() {
		return convid;
	}

	public void setConvid(Long convid) {
		this.convid = convid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Long getConvuom() {
		return convuom;
	}

	public void setConvuom(Long convuom) {
		this.convuom = convuom;
	}

	public Long getUnits() {
		return units;
	}

	public void setUnits(Long units) {
		this.units = units;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
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
