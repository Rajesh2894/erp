package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Ajay Kumar
 *
 */
public class PurchaseRequistionDetDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long prdetId;
	private Long itemId; 
	private BigDecimal quantity;
	private char status;
	private Long podetRef;
	private Long orgId;
	private Long userId;
	private Long langId;
	private Date lmoDate;
	private Long updatedBy;
	private Date updatedDate;  
	private String lgIpMac; 
	private String lgIpMacUpd;
	private String uonName;
	private String itemName;
	@JsonIgnore
	private PurchaseRequistionDto purchaseRequistionDto;
	
	public PurchaseRequistionDetDto() {
		
	}

	public Long getPrdetId() {
		return prdetId;
	}

	public void setPrdetId(Long prdetId) {
		this.prdetId = prdetId;
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Long getPodetRef() {
		return podetRef;
	}

	public void setPodetRef(Long podetRef) {
		this.podetRef = podetRef;
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

	public PurchaseRequistionDto getPurchaseRequistionDto() {
		return purchaseRequistionDto;
	}

	public void setPurchaseRequistionDto(PurchaseRequistionDto purchaseRequistionDto) {
		this.purchaseRequistionDto = purchaseRequistionDto;
	}

	public String getUonName() {
		return uonName;
	}

	public void setUonName(String uonName) {
		this.uonName = uonName;
	}

	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "PurchaseRequistionDetDto [prdetId=" + prdetId + ", itemId=" + itemId + ", quantity=" + quantity
				+ ", status=" + status + ", podetRef=" + podetRef + ", orgId=" + orgId + ", userId=" + userId
				+ ", langId=" + langId + ", lmoDate=" + lmoDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", purchaseRequistionDto="
				+ purchaseRequistionDto + "]";
	}
}
