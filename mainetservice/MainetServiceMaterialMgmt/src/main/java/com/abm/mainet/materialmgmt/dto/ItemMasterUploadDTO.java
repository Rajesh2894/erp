package com.abm.mainet.materialmgmt.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

public class ItemMasterUploadDTO {
	private String name;
	private String description;
	private String uom;
	private String itemGroup;
	private String itemSubGroup;
	private String type;
	private String isAsset;
	private String classification;
	private String valueMethod;
	private String management;
	private Long minLevel;
	private Long reorderLevel;
	private String category;
	private String isExpiry;
	private String expiryType;
	private Long hsnCode;
	private Double taxPercentage;
	private String Status;
	private String EntryFlag;
	private List<ItemMasterConversionUploadDTO> itemMasterConversionDtoList = new ArrayList<>();

	private Long orgId;

	private Long userId;

	private int langId;

	private Date lmodDate;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	private Long updatedBy;

	private Date updatedDate;

	@Size(max = 100)
	private String lgIpMac;

	@Size(max = 100)
	private String lgIpMacUpd;

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}

	public String getItemSubGroup() {
		return itemSubGroup;
	}

	public void setItemSubGroup(String itemSubGroup) {
		this.itemSubGroup = itemSubGroup;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsAsset() {
		return isAsset;
	}

	public void setIsAsset(String isAsset) {
		this.isAsset = isAsset;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getValueMethod() {
		return valueMethod;
	}

	public void setValueMethod(String valueMethod) {
		this.valueMethod = valueMethod;
	}

	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}

	public Long getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(Long minLevel) {
		this.minLevel = minLevel;
	}

	public Long getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(Long reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public String getIsExpiry() {
		return isExpiry;
	}

	public void setIsExpiry(String isExpiry) {
		this.isExpiry = isExpiry;
	}

	public String getExpiryType() {
		return expiryType;
	}

	public void setExpiryType(String expiryType) {
		this.expiryType = expiryType;
	}

	public Long getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(Long hsnCode) {
		this.hsnCode = hsnCode;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getEntryFlag() {
		return EntryFlag;
	}

	public void setEntryFlag(String entryFlag) {
		EntryFlag = entryFlag;
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

	public List<ItemMasterConversionUploadDTO> getItemMasterConversionDtoList() {
		return itemMasterConversionDtoList;
	}

	public void setItemMasterConversionDtoList(List<ItemMasterConversionUploadDTO> itemMasterConversionDtoList) {
		this.itemMasterConversionDtoList = itemMasterConversionDtoList;
	}

}
