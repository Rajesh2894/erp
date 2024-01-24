package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MaterialDispatchNoteItemsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long mdnItemId;

	private Long mdnId;

	private Long issueStoreId;

	private String issueStore;

	private Long itemId;

	private String itemName;

	private Long uom;

	private String uomDesc;

	private String management;

	private String managementDesc;

	private BigDecimal requestedQty;

	private BigDecimal prevRecQty;

	private BigDecimal issuedQty;

	private BigDecimal acceptQty;

	private BigDecimal rejectQty;

	private String inspectorRemarks;

	private String storeRemarks;

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
	
	private BigDecimal remainingQty;

	private List<MaterialDispatchNoteItemsEntryDTO> matDispatchItemsEntryList = new ArrayList<>();

    private String isExpiry;
	
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

	public Long getUom() {
		return uom;
	}

	public void setUom(Long uom) {
		this.uom = uom;
	}

	public String getUomDesc() {
		return uomDesc;
	}

	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}

	public String getManagementDesc() {
		return managementDesc;
	}

	public void setManagementDesc(String managementDesc) {
		this.managementDesc = managementDesc;
	}

	public BigDecimal getRequestedQty() {
		return requestedQty;
	}

	public void setRequestedQty(BigDecimal requestedQty) {
		this.requestedQty = requestedQty;
	}

	public BigDecimal getPrevRecQty() {
		return prevRecQty;
	}

	public void setPrevRecQty(BigDecimal prevRecQty) {
		this.prevRecQty = prevRecQty;
	}

	public BigDecimal getIssuedQty() {
		return issuedQty;
	}

	public void setIssuedQty(BigDecimal issuedQty) {
		this.issuedQty = issuedQty;
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

	public String getInspectorRemarks() {
		return inspectorRemarks;
	}

	public void setInspectorRemarks(String inspectorRemarks) {
		this.inspectorRemarks = inspectorRemarks;
	}

	public String getStoreRemarks() {
		return storeRemarks;
	}

	public void setStoreRemarks(String storeRemarks) {
		this.storeRemarks = storeRemarks;
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

	public BigDecimal getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(BigDecimal remainingQty) {
		this.remainingQty = remainingQty;
	}

	public List<MaterialDispatchNoteItemsEntryDTO> getMatDispatchItemsEntryList() {
		return matDispatchItemsEntryList;
	}

	public void setMatDispatchItemsEntryList(List<MaterialDispatchNoteItemsEntryDTO> matDispatchItemsEntryList) {
		this.matDispatchItemsEntryList = matDispatchItemsEntryList;
	}

	public String getIsExpiry() {
		return isExpiry;
	}

	public void setIsExpiry(String isExpiry) {
		this.isExpiry = isExpiry;
	}

}
