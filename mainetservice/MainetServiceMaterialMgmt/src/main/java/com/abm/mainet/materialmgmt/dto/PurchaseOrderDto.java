package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseOrderDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long poId; 
	private String poNo;  
	private Date poDate;  
	private Long storeId; 
	private String storeName;
	private Long workOrderId; 
	private Long vendorId;  
	private String vendorName;  
	private Date expectedDeliveryDate;
	private char status; 
	private Long orgId; 
	private Long userId; 
	private Long langId; 
	private Date lmoDate; 
	private Long updatedBy; 
	private Date updatedDate;
	private String lgIpMac; 
	private String lgIpMacUpd;  
	private String wfFlag;
	private Long prIdGetData; 
	private Date fromDate;
	private Date toDate;
	
	List<PurchaseOrderDetDto> purchaseOrderDetDto=new ArrayList<PurchaseOrderDetDto>();
	List<PurchaseOrderOverheadsDto> purchaseOrderOverheadsDto=new ArrayList<PurchaseOrderOverheadsDto>();
	List<PurchaseorderTncDto> purchaseorderTncDto=new ArrayList<PurchaseorderTncDto>();
	List<PurchaseorderAttachmentDto> purchaseorderAttachmentDto=new ArrayList<PurchaseorderAttachmentDto>();
	
	public Long getPoId() {
		return poId;
	}
	public void setPoId(Long poId) {
		this.poId = poId;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public Date getPoDate() {
		return poDate;
	}
	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
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
	public String getWfFlag() {
		return wfFlag;
	}
	public void setWfFlag(String wfFlag) {
		this.wfFlag = wfFlag;
	}
	public List<PurchaseOrderDetDto> getPurchaseOrderDetDto() {
		return purchaseOrderDetDto;
	}
	public void setPurchaseOrderDetDto(List<PurchaseOrderDetDto> purchaseOrderDetDto) {
		this.purchaseOrderDetDto = purchaseOrderDetDto;
	}
	public List<PurchaseOrderOverheadsDto> getPurchaseOrderOverheadsDto() {
		return purchaseOrderOverheadsDto;
	}
	public void setPurchaseOrderOverheadsDto(List<PurchaseOrderOverheadsDto> purchaseOrderOverheadsDto) {
		this.purchaseOrderOverheadsDto = purchaseOrderOverheadsDto;
	}
	public List<PurchaseorderTncDto> getPurchaseorderTncDto() {
		return purchaseorderTncDto;
	}
	public void setPurchaseorderTncDto(List<PurchaseorderTncDto> purchaseorderTncDto) {
		this.purchaseorderTncDto = purchaseorderTncDto;
	}
	public List<PurchaseorderAttachmentDto> getPurchaseorderAttachmentDto() {
		return purchaseorderAttachmentDto;
	}
	public void setPurchaseorderAttachmentDto(List<PurchaseorderAttachmentDto> purchaseorderAttachmentDto) {
		this.purchaseorderAttachmentDto = purchaseorderAttachmentDto;
	}
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Long getPrIdGetData() {
		return prIdGetData;
	}
	public void setPrIdGetData(Long prIdGetData) {
		this.prIdGetData = prIdGetData;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	@Override
	public String toString() {
		return "PurchaseOrderDto [poId=" + poId + ", poNo=" + poNo + ", poDate=" + poDate + ", storeId=" + storeId
				+ ", workOrderId=" + workOrderId + ", vendorId=" + vendorId + ", expectedDeliveryDate="
				+ expectedDeliveryDate + ", status=" + status + ", orgId=" + orgId + ", userId=" + userId + ", langId="
				+ langId + ", lmoDate=" + lmoDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", wfFlag=" + wfFlag
				+ ", purchaseOrderDetDto=" + purchaseOrderDetDto + ", purchaseOrderOverheadsDto="
				+ purchaseOrderOverheadsDto + ", purchaseorderTncDto=" + purchaseorderTncDto
				+ ", purchaseorderAttachmentDto=" + purchaseorderAttachmentDto + ", getPoId()=" + getPoId()
				+ ", getPoNo()=" + getPoNo() + ", getPoDate()=" + getPoDate() + ", getStoreId()=" + getStoreId()
				+ ", getWorkOrderId()=" + getWorkOrderId() + ", getVendorId()=" + getVendorId()
				+ ", getExpectedDeliveryDate()=" + getExpectedDeliveryDate() + ", getStatus()=" + getStatus()
				+ ", getOrgId()=" + getOrgId() + ", getUserId()=" + getUserId() + ", getLangId()=" + getLangId()
				+ ", getLmoDate()=" + getLmoDate() + ", getUpdatedBy()=" + getUpdatedBy() + ", getUpdatedDate()="
				+ getUpdatedDate() + ", getLgIpMac()=" + getLgIpMac() + ", getLgIpMacUpd()=" + getLgIpMacUpd()
				+ ", getWfFlag()=" + getWfFlag() + ", getPurchaseOrderDetDto()=" + getPurchaseOrderDetDto()
				+ ", getPurchaseOrderOverheadsDto()=" + getPurchaseOrderOverheadsDto() + ", getPurchaseorderTncDto()="
				+ getPurchaseorderTncDto() + ", getPurchaseorderAttachmentDto()=" + getPurchaseorderAttachmentDto()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	} 
	
}
