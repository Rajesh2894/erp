package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StoresReturnDetailDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long storeRetDetId;

	private Long storeReturnId;

	private Long mdnId;

	private Long mdnItemEntryId;

	private Long requestStoreId;

	private String requestStoreName;

	private Long issueStoreId;

	private String issueStoreName;

	private Long itemId;

	private String itemName;

	private Long uom;

	private String uomName;

	private String itemNo;

	private BigDecimal quantity;

	private Long returnReason;

	private String returnReasonDesc;
	
	private String disposalFlag;

	private Long binLocation;

	private Long binLocationName;

	private String status;

	private Long orgId;

	private Long langId;

	private Long userId;

	private Date createDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getStoreRetDetId() {
		return storeRetDetId;
	}

	public void setStoreRetDetId(Long storeRetDetId) {
		this.storeRetDetId = storeRetDetId;
	}

	public Long getStoreReturnId() {
		return storeReturnId;
	}

	public void setStoreReturnId(Long storeReturnId) {
		this.storeReturnId = storeReturnId;
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

	public String getRequestStoreName() {
		return requestStoreName;
	}

	public void setRequestStoreName(String requestStoreName) {
		this.requestStoreName = requestStoreName;
	}

	public Long getIssueStoreId() {
		return issueStoreId;
	}

	public void setIssueStoreId(Long issueStoreId) {
		this.issueStoreId = issueStoreId;
	}

	public String getIssueStoreName() {
		return issueStoreName;
	}

	public void setIssueStoreName(String issueStoreName) {
		this.issueStoreName = issueStoreName;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getUom() {
		return uom;
	}

	public void setUom(Long uom) {
		this.uom = uom;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
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

	public String getReturnReasonDesc() {
		return returnReasonDesc;
	}

	public void setReturnReasonDesc(String returnReasonDesc) {
		this.returnReasonDesc = returnReasonDesc;
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

	public Long getBinLocationName() {
		return binLocationName;
	}

	public void setBinLocationName(Long binLocationName) {
		this.binLocationName = binLocationName;
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

	@Override
	public String toString() {
		return "StoresReturnDetailDTO [storeRetDetId=" + storeRetDetId + ", storeReturnId=" + storeReturnId + ", mdnId="
				+ mdnId + ", mdnItemEntryId=" + mdnItemEntryId + ", requestStoreId=" + requestStoreId
				+ ", requestStoreName=" + requestStoreName + ", issueStoreId=" + issueStoreId + ", issueStoreName="
				+ issueStoreName + ", itemId=" + itemId + ", itemName=" + itemName + ", uom=" + uom + ", uomName="
				+ uomName + ", itemNo=" + itemNo + ", quantity=" + quantity + ", returnReason=" + returnReason
				+ ", disposalFlag=" + disposalFlag + ", binLocation=" + binLocation + ", binLocationName="
				+ binLocationName + ", status=" + status + ", orgId=" + orgId + ", langId=" + langId + ", userId="
				+ userId + ", createDate=" + createDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
	}

}
