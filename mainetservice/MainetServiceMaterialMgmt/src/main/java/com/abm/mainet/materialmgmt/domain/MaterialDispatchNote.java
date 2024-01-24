package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mm_mdn")
public class MaterialDispatchNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "mdnid")
	private Long mdnId;

	@Column(name = "mdnno")
	private String mdnNumber;

	@Column(name = "mdndate")
	private Date mdnDate;

	@Column(name = "issuestoreid")
	private Long issueStoreId;

	@Column(name = "siid")
	private Long siId;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "requeststoreid")
	private Long requestStoreId;

	@Column(name = "inspectiondate")
	private Date inspectionDate;

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

	@Column(name = "WF_Flag")
	private String WfFlag;

	@OneToMany(mappedBy = "materialDispatchNote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MaterialDispatchNoteItems> matDispatchItemEntities = new ArrayList<>();

	@OneToMany(mappedBy = "materialDispatchNote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MaterialDispatchNoteItemsEntry> matDispatchItemsEntryList = new ArrayList<>();

	public Long getMdnId() {
		return mdnId;
	}

	public void setMdnId(Long mdnId) {
		this.mdnId = mdnId;
	}

	public String getMdnNumber() {
		return mdnNumber;
	}

	public void setMdnNumber(String mdnNumber) {
		this.mdnNumber = mdnNumber;
	}

	public Date getMdnDate() {
		return mdnDate;
	}

	public void setMdnDate(Date mdnDate) {
		this.mdnDate = mdnDate;
	}

	public Long getIssueStoreId() {
		return issueStoreId;
	}

	public void setIssueStoreId(Long issueStoreId) {
		this.issueStoreId = issueStoreId;
	}

	public Long getSiId() {
		return siId;
	}

	public void setSiId(Long siId) {
		this.siId = siId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getRequestStoreId() {
		return requestStoreId;
	}

	public void setRequestStoreId(Long requestStoreId) {
		this.requestStoreId = requestStoreId;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
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

	public String getWfFlag() {
		return WfFlag;
	}

	public void setWfFlag(String wfFlag) {
		WfFlag = wfFlag;
	}

	public List<MaterialDispatchNoteItems> getMatDispatchItemEntities() {
		return matDispatchItemEntities;
	}

	public void setMatDispatchItemEntities(List<MaterialDispatchNoteItems> matDispatchItemEntities) {
		this.matDispatchItemEntities = matDispatchItemEntities;
	}

	public List<MaterialDispatchNoteItemsEntry> getMatDispatchItemsEntryList() {
		return matDispatchItemsEntryList;
	}

	public void setMatDispatchItemsEntryList(List<MaterialDispatchNoteItemsEntry> matDispatchItemsEntryList) {
		this.matDispatchItemsEntryList = matDispatchItemsEntryList;
	}

	public String[] getPkValues() {
		return new String[] { "MMM", "mm_mdn", "mdnid" };
	}

}
