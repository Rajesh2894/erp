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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name ="MM_REQUISITION_DET")
public class PurchaseRequistionDetEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "prdetid")
	private Long prdetId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prid")
    private PurchaseRequistionEntity purchaseRequistionEntity;
	
	@Column(name="itemid")
	private Long itemId; 
	
	@Column(name="quantity")
	private BigDecimal quantity;
	
	@Column(name="Status")
	private char status;
	
	@Column(name="podetref")
	private Long podetRef;
	
	@Column(name="ORGID")
	private Long orgId;
	
	@Column(name="USER_ID") 
	private Long userId;
	
	@Column(name="LANGID")
	private Long langId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LMODDATE")
	private Date lmoDate;
	
	@Column(name="UPDATED_BY")
	private Long updatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;  
	
	@Column(name="LG_IP_MAC")
	private String lgIpMac; 
	
	@Column(name="LG_IP_MAC_UPD")
	private String lgIpMacUpd;
	

	public Long getPrdetId() {
		return prdetId;
	}


	public void setPrdetId(Long prdetId) {
		this.prdetId = prdetId;
	}


	public PurchaseRequistionEntity getPurchaseRequistionEntity() {
		return purchaseRequistionEntity;
	}


	public void setPurchaseRequistionEntity(PurchaseRequistionEntity purchaseRequistionEntity) {
		this.purchaseRequistionEntity = purchaseRequistionEntity;
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


	public String[] getPkValues() {

        return new String[] { "MMM", "MM_REQUISITION_DET", "prdetid" };
    }
}
