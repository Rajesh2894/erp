package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MaterialDispatchNoteItemsEntryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long mdnItemEntryId;

	private Long mdnItemId;

	private Long mdnId;

	private Long issueStoreId;

	private String issueStore;

	private Long itemId;

	private String itemName;

	private Long issueBinLocation;

	private String issueBinLocationName;

	private String itemNo;

	private BigDecimal availableQuantity;
	
	private BigDecimal quantity;

	private BigDecimal acceptQty;

	private BigDecimal rejectQty;

	private Date mfgDate;

	private Date expiryDate;

	private Long rejectionReason;

	private Long receivedBinLocation;

	private Long returnId;

	private String status;

	private Long orgId;

	private Long userId;

	private Long langId;

	private Date lmodDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private Long siItemId;

	private List<String> itemNumberList = new ArrayList<>();

	public Long getMdnItemEntryId() {
		return mdnItemEntryId;
	}

	public void setMdnItemEntryId(Long mdnItemEntryId) {
		this.mdnItemEntryId = mdnItemEntryId;
	}

	public Long getMdnItemId() {
		return mdnItemId;
	}

	public void setMdnItemId(Long mdnItemId) {
		this.mdnItemId = mdnItemId;
	}

	public Long getMdnId() {
		return mdnId;
	}

	public void setMdnId(Long mdnId) {
		this.mdnId = mdnId;
	}

	public Long getIssueStoreId() {
		return issueStoreId;
	}

	public void setIssueStoreId(Long issueStoreId) {
		this.issueStoreId = issueStoreId;
	}

	public String getIssueStore() {
		return issueStore;
	}

	public void setIssueStore(String issueStore) {
		this.issueStore = issueStore;
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

	public Long getIssueBinLocation() {
		return issueBinLocation;
	}

	public void setIssueBinLocation(Long issueBinLocation) {
		this.issueBinLocation = issueBinLocation;
	}

	public String getIssueBinLocationName() {
		return issueBinLocationName;
	}

	public void setIssueBinLocationName(String issueBinLocationName) {
		this.issueBinLocationName = issueBinLocationName;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public BigDecimal getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(BigDecimal availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAcceptQty() {
		return acceptQty;
	}

	public void setAcceptQty(BigDecimal acceptQty) {
		this.acceptQty = acceptQty;
	}

	public BigDecimal getRejectQty() {
		return rejectQty;
	}

	public void setRejectQty(BigDecimal rejectQty) {
		this.rejectQty = rejectQty;
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

	public Long getReceivedBinLocation() {
		return receivedBinLocation;
	}

	public void setReceivedBinLocation(Long receivedBinLocation) {
		this.receivedBinLocation = receivedBinLocation;
	}

	public Long getReturnId() {
		return returnId;
	}

	public void setReturnId(Long returnId) {
		this.returnId = returnId;
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
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Long getSiItemId() {
		return siItemId;
	}

	public void setSiItemId(Long siItemId) {
		this.siItemId = siItemId;
	}
	
	public List<String> getItemNumberList() {
		return itemNumberList;
	}

	public void setItemNumberList(List<String> itemNumberList) {
		this.itemNumberList = itemNumberList;
	}
	
	
}