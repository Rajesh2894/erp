package com.abm.mainet.materialmgmt.ui.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ItemMasterConversionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// ----------------------------------------------------------------------
	// ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
	// ----------------------------------------------------------------------
	private Long convId;
	private Long itemId;
	private Long convUom;
	private Long units;
	private String status;
	@NotNull
	private Long orgId;

	@NotNull
	private Long userId;

	@NotNull
	private int langId;

	@NotNull
	private Date lmodDate;

	private Long updatedBy;

	private Date updatedDate;

	@JsonIgnore
	@Size(max = 100)
	private String lgIpMac;

	@JsonIgnore
	@Size(max = 100)
	private String lgIpMacUpd;


	public Long getConvId() {
		return convId;
	}

	public void setConvId(Long convId) {
		this.convId = convId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getConvUom() {
		return convUom;
	}

	public void setConvUom(Long convUom) {
		this.convUom = convUom;
	}

	public Long getUnits() {
		return units;
	}

	public void setUnits(Long units) {
		this.units = units;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	@Override
	public String toString() {
		return "ItemMasterConversionDTO [convId=" + convId + ", itemId=" + itemId + ", convUom=" + convUom + ", units="
				+ units + ", status=" + status + ", orgId=" + orgId + ", userId=" + userId + ", langId=" + langId
				+ ", lmodDate=" + lmodDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac="
				+ lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
	}

}
