package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.Date;

public class PurchaseReturnDetailDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long returnDetId;

	private Long returnId;

	private Long grnItemEntryId;

	private Long grnId;

	private Long grnNo;

	private Long storeId;

	private String storeName;

	private Long itemId;

	private String itemName;

	private Long uomId;

	private String uomName;

	private String managementMethod;

	private String itemNo;

	private Double quantity;

	private Character status;

	private String rejectionRemark;

	private Long orgId;

	private Long userId;

	private Long langId;

	private Date lmodDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getReturnDetId() {
		return returnDetId;
	}

	public void setReturnDetId(Long returnDetId) {
		this.returnDetId = returnDetId;
	}

	public Long getReturnId() {
		return returnId;
	}

	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}

	public Long getGrnItemEntryId() {
		return grnItemEntryId;
	}

	public void setGrnItemEntryId(Long grnItemEntryId) {
		this.grnItemEntryId = grnItemEntryId;
	}

	public Long getGrnId() {
		return grnId;
	}

	public void setGrnId(Long grnId) {
		this.grnId = grnId;
	}

	public Long getGrnNo() {
		return grnNo;
	}

	public void setGrnNo(Long grnNo) {
		this.grnNo = grnNo;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getRejectionRemark() {
		return rejectionRemark;
	}

	public void setRejectionRemark(String rejectionRemark) {
		this.rejectionRemark = rejectionRemark;
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
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

}
