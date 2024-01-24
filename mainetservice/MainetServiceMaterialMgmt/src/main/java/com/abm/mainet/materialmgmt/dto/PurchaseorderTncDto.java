package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.Date;

public class PurchaseorderTncDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long tncId; 
	private Long poId;
	private String description;
	private char status; 
	private Long orgId;
	private Long userId; 
	private Long langId; 
	private Date lmodDate;
	private Long updateBy;
	private Date updatedDate; 
	private String lgIpMac;
	private String lgIpMacUpd;
	
	public Long getTncId() {
		return tncId;
	}
	public void setTncId(Long tncId) {
		this.tncId = tncId;
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
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
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
