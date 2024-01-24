package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mm_mdn_items")
public class MaterialDispatchNoteItems implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "mdnitemid")
	private Long mdnItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mdnid")
	private MaterialDispatchNote materialDispatchNote;

	@Column(name = "issuestoreid")
	private Long issueStoreId;

	@Column(name = "itemid")
	private Long itemId;

	@Column(name = "uom")
	private Long uom;

	@Column(name = "management", length = 1)
	private String management;

	@Column(name = "requestedqty")
	private BigDecimal requestedQty;

	@Column(name = "prevrecqty")
	private BigDecimal prevRecQty;

	@Column(name = "issuedqty")
	private BigDecimal issuedQty;

	@Column(name = "acceptqty")
	private BigDecimal acceptQty;

	@Column(name = "rejectqty")
	private BigDecimal rejectQty;

	@Column(name = "inspectorremarks")
	private String inspectorRemarks;

	@Column(name = "storeremarks")
	private String storeRemarks;

	@Column(name = "Status", length = 1)
	private String status;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private Long langId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LMODDATE")
	private Date lmodDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@OneToMany(mappedBy = "materialDispatchNoteItems", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MaterialDispatchNoteItemsEntry> matDispatchItemsEntryEntities = new ArrayList<>();

	public Long getMdnItemId() {
		return mdnItemId;
	}

	public void setMdnItemId(Long mdnItemId) {
		this.mdnItemId = mdnItemId;
	}

	public MaterialDispatchNote getMaterialDispatchNote() {
		return materialDispatchNote;
	}

	public void setMaterialDispatchNote(MaterialDispatchNote materialDispatchNote) {
		this.materialDispatchNote = materialDispatchNote;
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

	public Long getUom() {
		return uom;
	}

	public void setUom(Long uom) {
		this.uom = uom;
	}

	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
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


	public List<MaterialDispatchNoteItemsEntry> getMatDispatchItemsEntryEntities() {
		return matDispatchItemsEntryEntities;
	}

	public void setMatDispatchItemsEntryEntities(List<MaterialDispatchNoteItemsEntry> matDispatchItemsEntryEntities) {
		this.matDispatchItemsEntryEntities = matDispatchItemsEntryEntities;
	}

	public String[] getPkValues() {
		return new String[] { "MMM", "mm_mdn_items", "mdnitemid" };
	}

}
