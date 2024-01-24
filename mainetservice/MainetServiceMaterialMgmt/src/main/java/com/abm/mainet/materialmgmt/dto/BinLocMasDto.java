package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class BinLocMasDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long binLocId;
	private Long storeId;
	private String storeName;
	private String storeLocation;
	private String storeAdd;
	private String binLocation;
	private String locationName;
	private Long orgId;
	private Long userId;
	private Long langId;
	private Date lmodDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long createdBy;
	private Date createdDate;
	private String definitions;
	private Map<Long,Long> locMapping;

	public Long getBinLocId() {
		return binLocId;
	}

	public void setBinLocId(Long binLocId) {
		this.binLocId = binLocId;
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

	public String getStoreLocation() {
		return storeLocation;
	}

	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}

	public String getStoreAdd() {
		return storeAdd;
	}

	public void setStoreAdd(String storeAdd) {
		this.storeAdd = storeAdd;
	}

	public String getBinLocation() {
		return binLocation;
	}

	public void setBinLocation(String binLocation) {
		this.binLocation = binLocation;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDefinitions() {
		return definitions;
	}

	public void setDefinitions(String definitions) {
		this.definitions = definitions;
	}

	public Map<Long, Long> getLocMapping() {
		return locMapping;
	}

	public void setLocMapping(Map<Long, Long> locMapping) {
		this.locMapping = locMapping;
	}

	
	
}
