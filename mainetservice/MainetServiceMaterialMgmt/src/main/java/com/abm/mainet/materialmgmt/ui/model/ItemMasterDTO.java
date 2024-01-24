package com.abm.mainet.materialmgmt.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ItemMasterDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------------------------
	// ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
	// ----------------------------------------------------------------------
	private Long itemId;
	private String itemCode;
	private String name;
	private String description;
	private Long uom;
	private String uomDesc;
	private Long itemGroup;
	private String itemGroupDesc;
	private Long itemSubGroup;
	private String itemSubGroupDesc;
	private Long type;
	private String typeDesc;
	private String isAsset;
	private Long classification;
	private String classificationDesc;
	private Long valueMethod;
	private String valueMethodDesc;
	private Long management;
	private String managementDesc;
	private Long minLevel;
	private Long reorderLevel;
	private Long category;
	private String categoryDesc;
	private String isExpiry;
	private Long expiryType;
	private String expiryTypeDesc;
	private Long hsnCode;
	private Double taxPercentage;
	private String status;
	private String statusDesc;
	private String entryFlag;
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
	private String hasError;
	@Transient
    private String uploadFileName;
	
	private List<ItemMasterConversionDTO> itemMasterConversionDtoList = new ArrayList<>();

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public Long getUom() {
		return uom;
	}

	public void setUom(Long uom) {
		this.uom = uom;
	}

	public String getUomDesc() {
		return uomDesc;
	}

	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	public Long getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(Long itemGroup) {
		this.itemGroup = itemGroup;
	}

	public String getItemGroupDesc() {
		return itemGroupDesc;
	}

	public void setItemGroupDesc(String itemGroupDesc) {
		this.itemGroupDesc = itemGroupDesc;
	}

	public Long getItemSubGroup() {
		return itemSubGroup;
	}

	public void setItemSubGroup(Long itemSubGroup) {
		this.itemSubGroup = itemSubGroup;
	}

	public String getItemSubGroupDesc() {
		return itemSubGroupDesc;
	}

	public void setItemSubGroupDesc(String itemSubGroupDesc) {
		this.itemSubGroupDesc = itemSubGroupDesc;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getIsAsset() {
		return isAsset;
	}

	public void setIsAsset(String isAsset) {
		this.isAsset = isAsset;
	}

	public Long getClassification() {
		return classification;
	}

	public void setClassification(Long classification) {
		this.classification = classification;
	}

	public String getClassificationDesc() {
		return classificationDesc;
	}

	public void setClassificationDesc(String classificationDesc) {
		this.classificationDesc = classificationDesc;
	}

	public Long getValueMethod() {
		return valueMethod;
	}

	public void setValueMethod(Long valueMethod) {
		this.valueMethod = valueMethod;
	}

	public String getValueMethodDesc() {
		return valueMethodDesc;
	}

	public void setValueMethodDesc(String valueMethodDesc) {
		this.valueMethodDesc = valueMethodDesc;
	}

	public Long getManagement() {
		return management;
	}

	public void setManagement(Long management) {
		this.management = management;
	}

	public String getManagementDesc() {
		return managementDesc;
	}

	public void setManagementDesc(String managementDesc) {
		this.managementDesc = managementDesc;
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

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getIsExpiry() {
		return isExpiry;
	}

	public void setIsExpiry(String isExpiry) {
		this.isExpiry = isExpiry;
	}

	public Long getExpiryType() {
		return expiryType;
	}

	public void setExpiryType(Long expiryType) {
		this.expiryType = expiryType;
	}

	public String getExpiryTypeDesc() {
		return expiryTypeDesc;
	}

	public void setExpiryTypeDesc(String expiryTypeDesc) {
		this.expiryTypeDesc = expiryTypeDesc;
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
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getEntryFlag() {
		return entryFlag;
	}

	public void setEntryFlag(String entryFlag) {
		this.entryFlag = entryFlag;
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

	public String getHasError() {
		return hasError;
	}

	public void setHasError(String hasError) {
		this.hasError = hasError;
	}

	public List<ItemMasterConversionDTO> getItemMasterConversionDtoList() {
		return itemMasterConversionDtoList;
	}

	public void setItemMasterConversionDtoList(List<ItemMasterConversionDTO> itemMasterConversionDtoList) {
		this.itemMasterConversionDtoList = itemMasterConversionDtoList;
	}
}
