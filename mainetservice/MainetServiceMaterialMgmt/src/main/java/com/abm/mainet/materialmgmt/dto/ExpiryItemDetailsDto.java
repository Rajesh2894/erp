package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ExpiryItemDetailsDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
    
	private Long expiryItemId;
	private Long expiryId;
	private Long storeId;
	private Long itemId;
	private Long transactionId;
	private String itemNo;
	private Double quantity;
	private String flag;
	private String disposedFlag;
	private String remarks;
	private Long binLocation;
	private String binLocName;
	
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
	
	@JsonIgnore
	private ExpiryItemsDto expiryItemsDto;
	
	private String itemName;
	
	private String uomName;
	
	private Long uomId;
	
	

	public Long getExpiryItemId() {
		return expiryItemId;
	}

	public void setExpiryItemId(Long expiryItemId) {
		this.expiryItemId = expiryItemId;
	}

	public Long getExpiryId() {
		return expiryId;
	}

	public void setExpiryId(Long expiryId) {
		this.expiryId = expiryId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDisposedFlag() {
		return disposedFlag;
	}

	public void setDisposedFlag(String disposedFlag) {
		this.disposedFlag = disposedFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	
	public ExpiryItemsDto getExpiryItemsDto() {
		return expiryItemsDto;
	}

	public void setExpiryItemsDto(ExpiryItemsDto expiryItemsDto) {
		this.expiryItemsDto = expiryItemsDto;
	}
	public Long getBinLocation() {
		return binLocation;
	}

	public void setBinLocation(Long binLocation) {
		this.binLocation = binLocation;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public Long getUomId() {
		return uomId;
	}

	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}

	public String getBinLocName() {
		return binLocName;
	}

	public void setBinLocName(String binLocName) {
		this.binLocName = binLocName;
	}

	@Override
	public String toString() {
		return "ExpiryItemDetailsDto [expiryItemId=" + expiryItemId + ", expiryId=" + expiryId + ", storeId=" + storeId
				+ ", itemId=" + itemId + ", transactionId=" + transactionId + ", itemNo=" + itemNo + ", quantity="
				+ quantity + ", flag=" + flag + ", disposedFlag=" + disposedFlag + ", remarks=" + remarks + ", orgId="
				+ orgId + ", userId=" + userId + ", langId=" + langId + ", lmodDate=" + lmodDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
				+ "]";
	}
	
}
