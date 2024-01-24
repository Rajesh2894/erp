package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StoreMasterDTO implements Serializable {

	private static final long serialVersionUID = 1L;	
   
	private Long storeId;
	private String storeCode;
	private String storeName;
	private Long location;
	private String address;
	private Long storeIncharge;
	private Character status;
    private List<StoreGroupMappingDto>	storeGrMappingDtoList= new ArrayList<>();
	private Long orgId;
    private Long userId;
    private Long langId;
    private Date lmoDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String storeInchargeName;
    private String locationName;
    private Long itemGroupId;
	public StoreMasterDTO() {
		
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public Long getLocation() {
		return location;
	}
	public void setLocation(Long location) {
		this.location = location;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getStoreIncharge() {
		return storeIncharge;
	}
	public void setStoreIncharge(Long storeIncharge) {
		this.storeIncharge = storeIncharge;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public List<StoreGroupMappingDto> getStoreGrMappingDtoList() {
		return storeGrMappingDtoList;
	}
	public void setStoreGrMappingDtoList(List<StoreGroupMappingDto> storeGrMappingDtoList) {
		this.storeGrMappingDtoList = storeGrMappingDtoList;
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
	public Date getLmoDate() {
		return lmoDate;
	}
	public void setLmoDate(Date lmoDate) {
		this.lmoDate = lmoDate;
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
	
	public String getStoreInchargeName() {
		return storeInchargeName;
	}
	public void setStoreInchargeName(String storeInchargeName) {
		this.storeInchargeName = storeInchargeName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public Long getItemGroupId() {
		return itemGroupId;
	}
	public void setItemGroupId(Long itemGroupId) {
		this.itemGroupId = itemGroupId;
	}
	@Override
	public String toString() {
		return "StoreMasterDTO [storeId=" + storeId + ", storeCode=" + storeCode + ", storeName=" + storeName
				+ ", location=" + location + ", address=" + address + ", storeIncharge=" + storeIncharge + ", status="
				+ status + ", storeGrMappingDtoList=" + storeGrMappingDtoList + ", orgId=" + orgId + ", userId=" + userId + ", langId="
				+ langId + ", lmoDate=" + lmoDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
				+ ", storeInchargeName=" + storeInchargeName + ", locationName=" + locationName + "]";
	}
}
