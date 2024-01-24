package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MM_PURCHASEORDER_DET")
public class PurchaseOrderDetEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "podet")
	private Long podet;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poid")
	private PurchaseOrderEntity purchaseOrderEntity;
	
	@Column(name = "itemid")
	private Long itemId;

	@Column(name = "quantity")
	private BigDecimal quantity;

	@Column(name = "unitprice")
	private BigDecimal unitPrice;

	@Column(name = "tax")
	private BigDecimal tax;

	@Column(name = "totalamt")
	private BigDecimal totalAmt;

	@Column(name = "prid")
	private Long prId;

	@Column(name = "prdetid")
	private String prDetId;

	@Column(name = "Status")
	private char status;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private Long langId;

	@Column(name = "LMODDATE")
	private Date lmoDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;
	
	
	
	public PurchaseOrderDetEntity() {
		
	}

	public Long getPodet() {
		return podet;
	}

	public void setPodet(Long podet) {
		this.podet = podet;
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

	public String getPrDetId() {
		return prDetId;
	}

	public void setPrDetId(String prDetId) {
		this.prDetId = prDetId;
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

	public PurchaseOrderEntity getPurchaseOrderEntity() {
		return purchaseOrderEntity;
	}

	public void setPurchaseOrderEntity(PurchaseOrderEntity purchaseOrderEntity) {
		this.purchaseOrderEntity = purchaseOrderEntity;
	}

	public String[] getPkValues() {

        return new String[] { "MMM", "MM_PURCHASEORDER_DET", "podet" };
    }

}
