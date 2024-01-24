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
@Table(name = "MM_PURCHASEORDER_OVERHEADS")
public class PurchaseOrderOverheadsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "overheadid")
	private Long overHeadId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poid")
	private PurchaseOrderEntity purchaseOrderEntity;

	@Column(name = "description")
	private String description;

	@Column(name = "overheadtype")
	private char overHeadType;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "Status")
	private char status;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private Long langId;

	@Column(name = "LMODDATE")
	private Date lmodDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String LgIpMacUpd;
	
	public PurchaseOrderOverheadsEntity() {
		
	}

	public Long getOverHeadId() {
		return overHeadId;
	}

	public void setOverHeadId(Long overHeadId) {
		this.overHeadId = overHeadId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getOverHeadType() {
		return overHeadType;
	}

	public void setOverHeadType(char overHeadType) {
		this.overHeadType = overHeadType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
		return LgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		LgIpMacUpd = lgIpMacUpd;
	}

	public PurchaseOrderEntity getPurchaseOrderEntity() {
		return purchaseOrderEntity;
	}

	public void setPurchaseOrderEntity(PurchaseOrderEntity purchaseOrderEntity) {
		this.purchaseOrderEntity = purchaseOrderEntity;
	}

	public String[] getPkValues() {

        return new String[] { "MMM", "MM_PURCHASEORDER_OVERHEADS", "overheadid" };
    }
}
