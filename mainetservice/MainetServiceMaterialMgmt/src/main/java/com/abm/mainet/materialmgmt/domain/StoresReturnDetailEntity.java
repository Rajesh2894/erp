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
@Table(name = "mm_storereturn_det")
public class StoresReturnDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "srdetid")
	private Long storeRetDetId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storereturnid")
	private StoresReturnEntity storesReturnEntity;

	@Column(name = "mdnid")
	private Long mdnId;

	@Column(name = "mdnitementryid")
	private Long mdnItemEntryId;

	@Column(name = "requeststoreid")
	private Long requestStoreId;

	@Column(name = "Issuestoreid")
	private Long issueStoreId;

	@Column(name = "itemid", nullable = false)
	private Long itemId;

	@Column(name = "Itemno")
	private String itemNo;

	@Column(name = "quantity")
	private BigDecimal quantity;

	@Column(name = "ReasonforReturn")
	private Long returnReason;

	@Column(name = "disposalflag", length = 1)
	private String disposalFlag;

	@Column(name = "binlocation")
	private Long binLocation;

	@Column(name = "Status", length = 1)
	private String status;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "LANGID")
	private Long langId;

	@Column(name = "USER_ID")
	private Long userId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LMODDATE")
	private Date createDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	public Long getStoreRetDetId() {
		return storeRetDetId;
	}

	public void setStoreRetDetId(Long storeRetDetId) {
		this.storeRetDetId = storeRetDetId;
	}

	public StoresReturnEntity getStoresReturnEntity() {
		return storesReturnEntity;
	}

	public void setStoresReturnEntity(StoresReturnEntity storesReturnEntity) {
		this.storesReturnEntity = storesReturnEntity;
	}

	public Long getMdnId() {
		return mdnId;
	}

	public void setMdnId(Long mdnId) {
		this.mdnId = mdnId;
	}

	public Long getMdnItemEntryId() {
		return mdnItemEntryId;
	}

	public void setMdnItemEntryId(Long mdnItemEntryId) {
		this.mdnItemEntryId = mdnItemEntryId;
	}

	public Long getRequestStoreId() {
		return requestStoreId;
	}

	public void setRequestStoreId(Long requestStoreId) {
		this.requestStoreId = requestStoreId;
	}

	public Long getIssueStoreId() {
		return issueStoreId;
	}

	public void setIssueStoreId(Long issueStoreId) {
		this.issueStoreId = issueStoreId;
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

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Long getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(Long returnReason) {
		this.returnReason = returnReason;
	}

	public String getDisposalFlag() {
		return disposalFlag;
	}

	public void setDisposalFlag(String disposalFlag) {
		this.disposalFlag = disposalFlag;
	}

	public Long getBinLocation() {
		return binLocation;
	}

	public void setBinLocation(Long binLocation) {
		this.binLocation = binLocation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
		return new String[] { "MMM", "mm_storereturn_det", "srdetid" };
	}

}
