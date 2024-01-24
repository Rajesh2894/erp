package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PurchaseOrderOverheadsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long overHeadId;
	private Long poId;
	private String description;
	private Character overHeadType;
	private BigDecimal amount;
	private char status;
	private Long orgId;
	private Long userId;
	private Long langId;
	private Date lmodDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String LgIpMacUpd;


	
	public Long getOverHeadId() {
		return overHeadId;
	}
	public void setOverHeadId(Long overHeadId) {
		this.overHeadId = overHeadId;
	}
	public Long getPoId() {
		return poId;
	}
	public void setPoId(Long poId) {
		this.poId = poId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Character getOverHeadType() {
		return overHeadType;
	}
	public void setOverHeadType(Character overHeadType) {
		this.overHeadType = overHeadType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
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
	public Long getLangId() {
		return langId;
	}
	public void setLangId(Long langId) {
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
		return LgIpMacUpd;
	}
	public void setLgIpMacUpd(String lgIpMacUpd) {
		LgIpMacUpd = lgIpMacUpd;
	}


}
