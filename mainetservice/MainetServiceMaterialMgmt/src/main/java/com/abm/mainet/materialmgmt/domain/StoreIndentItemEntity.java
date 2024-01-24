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
@Table(name = "mm_storeindent_item")
public class StoreIndentItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "siitemid")
	private Long siItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "siid")
	private StoreIndentEntity storeIndentEntity;

	@Column(name = "itemid")
	private Long itemId;

	@Column(name = "quantity")
	private BigDecimal requestedQuantity;

	@Column(name = "issuedqty")
	private BigDecimal issuedQuantity;

	@Column(name = "Remarks")
	private String remarks;

	@Column(name = "Status")
	private Character status;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private Long langId;

	@Column(name = "LMODDATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;
	
	public StoreIndentItemEntity() {
	}

	public Long getSiItemId() {
		return siItemId;
	}

	public void setSiItemId(Long siItemId) {
		this.siItemId = siItemId;
	}

	public StoreIndentEntity getStoreIndentEntity() {
		return storeIndentEntity;
	}

	public void setStoreIndentEntity(StoreIndentEntity storeIndentEntity) {
		this.storeIndentEntity = storeIndentEntity;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}


	public BigDecimal getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(BigDecimal requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	public BigDecimal getIssuedQuantity() {
		return issuedQuantity;
	}

	public void setIssuedQuantity(BigDecimal issuedQuantity) {
		this.issuedQuantity = issuedQuantity;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
		return new String[] { "MMM", "mm_storeindent_item", "siitemid" };
	}

}
