/**
 * 
 */
package com.abm.mainet.additional.dto;

import java.io.Serializable;

/**
 * @author cherupelli.srikanth
 *
 */
import java.util.Date;
public class EChallanItemDetailsDto implements Serializable{

	private static final long serialVersionUID = 4159647573647196892L;
	private Long itemId;
	private String itemNo;
	private String itemDesc;
	private Long itemQuantity;
	private String storeId;
	private String status;
	private Long orgid;
	private Long createdBy;
	private Date createdDate;
	private String lgIpMac;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMacUpd;
	private Long langId;
	private Long serviceId;
	private Long deptId;
	private Double itemAmount;
	
	private EChallanMasterDto echallanMasterDto = new EChallanMasterDto();
	
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public Long getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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
	public String getLgIpMac() {
		return lgIpMac;
	}
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
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
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}
	public EChallanMasterDto getEchallanMasterDto() {
		return echallanMasterDto;
	}
	public void setEchallanMasterDto(EChallanMasterDto echallanMasterDto) {
		this.echallanMasterDto = echallanMasterDto;
	}
	public Long getLangId() {
		return langId;
	}
	public void setLangId(Long langId) {
		this.langId = langId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Double getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}


}
