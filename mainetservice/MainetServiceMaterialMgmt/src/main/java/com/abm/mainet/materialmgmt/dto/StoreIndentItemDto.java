package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StoreIndentItemDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long siItemId;

	private Long storeIndentId;

	private Long itemId;

	private String itemName;

	private Long uomId;

	private String uomName;

	private String managementMethod;

	private BigDecimal requestedQuantity;

	private BigDecimal issuedQuantity;

	private String remarks;

	private Character status;

	private Long orgId;

	private Long userId;

	private Long langId;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	public StoreIndentItemDto() {
	}

	public Long getSiItemId() {
		return siItemId;
	}

	public void setSiItemId(Long siItemId) {
		this.siItemId = siItemId;
	}

	public Long getStoreIndentId() {
		return storeIndentId;
	}

	public void setStoreIndentId(Long storeIndentId) {
		this.storeIndentId = storeIndentId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getUomId() {
		return uomId;
	}

	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public String getManagementMethod() {
		return managementMethod;
	}

	public void setManagementMethod(String managementMethod) {
		this.managementMethod = managementMethod;
	}

	public BigDecimal getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(BigDecimal requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	public BigDecimal getIssuedQuantity() {
		return issuedQuantity;
	}

	public void setIssuedQuantity(BigDecimal issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
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
