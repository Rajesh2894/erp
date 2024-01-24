package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.Date;

public class GrnInspectionItemDetDTO implements Serializable{   
		
	private static final long serialVersionUID = 1326705884215245016L;
	
	private Long grnitementryid;
	
	private Long grnid;
	
	private Long grnitemid;

	private Long storeId;
	
	private Long itemId;
	
	private String itemNo;
	
	private Double quantity;
	
	private Character decision;
	
	private Date mfgDate;
	
	private Date expiryDate;
	
	private Long rejectionReason;
	
	private Long binLocation;
	
	private Long returnId;
	
	private Character status;
	
	private String itemDesc;
	
	private double receivedqty; 
	
	private String serialNumFrom;
	
	private String serialNumTo;
	
	private String management;
	
	private Long orgId;
	
	private Long userId;
	
	private Long langId;
	
	private Date lmoDate;
	
	private Long updatedBy;
	
	private Date updatedDate;
	
	private String lgIpMac;
	
	private String lgIpMacUpd;

	private Double acceptqty;
	private Double rejectqty;
		
	private Double inBatchAcceptqty;
	private Double inBatchRejectqty;	
	
	private Double notInBatchAcceptqty;
	private Double notIBatchRejectqty;	
	
	private Double serialAcceptqty;
	private Double serialRejectqty;	
		
	public Long getGrnitementryid() {
		return grnitementryid;
	}
	public void setGrnitementryid(Long grnitementryid) {
		this.grnitementryid = grnitementryid;
	}
	public Long getGrnid() {
		return grnid;
	}
	public void setGrnid(Long grnid) {
		this.grnid = grnid;
	}
	public Long getGrnitemid() {
		return grnitemid;
	}
	public void setGrnitemid(Long grnitemid) {
		this.grnitemid = grnitemid;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Character getDecision() {
		return decision;
	}
	public void setDecision(Character decision) {
		this.decision = decision;
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
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public double getReceivedqty() {
		return receivedqty;
	}
	public void setReceivedqty(double receivedqty) {
		this.receivedqty = receivedqty;
	}
	public String getSerialNumFrom() {
		return serialNumFrom;
	}
	public void setSerialNumFrom(String serialNumFrom) {
		this.serialNumFrom = serialNumFrom;
	}
	public String getSerialNumTo() {
		return serialNumTo;
	}
	public void setSerialNumTo(String serialNumTo) {
		this.serialNumTo = serialNumTo;
	}
	public String getManagement() {
		return management;
	}
	public void setManagement(String management) {
		this.management = management;
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
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public Date getMfgDate() {
		return mfgDate;
	}
	public void setMfgDate(Date mfgDate) {
		this.mfgDate = mfgDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Long getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(Long rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public Long getBinLocation() {
		return binLocation;
	}
	public void setBinLocation(Long binLocation) {
		this.binLocation = binLocation;
	}
	public Long getReturnId() {
		return returnId;
	}
	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public Date getLmoDate() {
		return lmoDate;
	}
	public void setLmoDate(Date lmoDate) {
		this.lmoDate = lmoDate;
	}
	public Double getInBatchAcceptqty() {
		return inBatchAcceptqty;
	}
	public void setInBatchAcceptqty(Double inBatchAcceptqty) {
		this.inBatchAcceptqty = inBatchAcceptqty;
	}
	public Double getInBatchRejectqty() {
		return inBatchRejectqty;
	}
	public void setInBatchRejectqty(Double inBatchRejectqty) {
		this.inBatchRejectqty = inBatchRejectqty;
	}
	public Double getNotInBatchAcceptqty() {
		return notInBatchAcceptqty;
	}
	public void setNotInBatchAcceptqty(Double notInBatchAcceptqty) {
		this.notInBatchAcceptqty = notInBatchAcceptqty;
	}
	public Double getNotIBatchRejectqty() {
		return notIBatchRejectqty;
	}
	public void setNotIBatchRejectqty(Double notIBatchRejectqty) {
		this.notIBatchRejectqty = notIBatchRejectqty;
	}
	public Double getSerialAcceptqty() {
		return serialAcceptqty;
	}
	public void setSerialAcceptqty(Double serialAcceptqty) {
		this.serialAcceptqty = serialAcceptqty;
	}
	public Double getSerialRejectqty() {
		return serialRejectqty;
	}
	public void setSerialRejectqty(Double serialRejectqty) {
		this.serialRejectqty = serialRejectqty;
	}
	public Double getRejectqty() {
		return rejectqty;
	}
	public void setRejectqty(Double rejectqty) {
		this.rejectqty = rejectqty;
	}
	public Double getAcceptqty() {
		return acceptqty;
	}
	public void setAcceptqty(Double acceptqty) {
		this.acceptqty = acceptqty;
	}



	
	
}
