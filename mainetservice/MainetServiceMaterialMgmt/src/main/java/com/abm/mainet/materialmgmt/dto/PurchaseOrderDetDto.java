package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PurchaseOrderDetDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long podet;
	private Long poId;
	private Long itemId;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private BigDecimal tax;
	private BigDecimal totalAmt;
	private Long prId;
	private String prNo;
	private String prDetId;
	private Character status;
	private Long orgId;
	private Long userId;
	private Long langId;
	private Date lmoDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String uonName;
	private Date prDate;
	private PurchaseOrderDto purchaseOrderDto;
	private String itemName;
	
	public Long getPodet() {
		return podet;
	}
	public void setPodet(Long podet) {
		this.podet = podet;
	}
	public Long getPoId() {
		return poId;
	}
	public void setPoId(Long poId) {
		this.poId = poId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public Long getPrId() {
		return prId;
	}
	public void setPrId(Long prId) {
		this.prId = prId;
	}
	public String getPrNo() {
		return prNo;
	}
	public void setPrNo(String prNo) {
		this.prNo = prNo;
	}
	public String getPrDetId() {
		return prDetId;
	}
	public void setPrDetId(String prDetId) {
		this.prDetId = prDetId;
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
	public String getUonName() {
		return uonName;
	}
	public void setUonName(String uonName) {
		this.uonName = uonName;
	}
	public PurchaseOrderDto getPurchaseOrderDto() {
		return purchaseOrderDto;
	}
	public void setPurchaseOrderDto(PurchaseOrderDto purchaseOrderDto) {
		this.purchaseOrderDto = purchaseOrderDto;
	}
	
	public Date getPrDate() {
		return prDate;
	}
	public void setPrDate(Date prDate) {
		this.prDate = prDate;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}
