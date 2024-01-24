package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MaterialDispatchNoteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long mdnId;

	private String mdnNumber;

	private Date mdnDate;

	private Long issueStoreId;

	private String issueStore;

	private String issueStoreLocName;

	private String issueInchargeName;

	private Long siId;

	private Date storeIndentdate;

	private String siNumber;

	private String remarks;

	private Long requestStoreId;

	private String requestStore;

	private String requestStoreLocName;

	private String requestedByName;

	private Date inspectionDate;

	private String status;

	private Long orgId;

	private Long userId;
	
	private String userName;

	private Long langId;

	private Date lmodDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String WfFlag;

	private List<MaterialDispatchNoteItemsDTO> matDispatchItemList = new ArrayList<>();

	private List<MaterialDispatchNoteItemsEntryDTO> matDispatchItemsEntryList = new ArrayList<>();

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

	public String getIssueStore() {
		return issueStore;
	}

	public void setIssueStore(String issueStore) {
		this.issueStore = issueStore;
	}

	public String getIssueStoreLocName() {
		return issueStoreLocName;
	}

	public void setIssueStoreLocName(String issueStoreLocName) {
		this.issueStoreLocName = issueStoreLocName;
	}

	public String getIssueInchargeName() {
		return issueInchargeName;
	}

	public void setIssueInchargeName(String issueInchargeName) {
		this.issueInchargeName = issueInchargeName;
	}

	public Long getSiId() {
		return siId;
	}

	public void setSiId(Long siId) {
		this.siId = siId;
	}

	public Date getStoreIndentdate() {
		return storeIndentdate;
	}

	public void setStoreIndentdate(Date storeIndentdate) {
		this.storeIndentdate = storeIndentdate;
	}

	public String getSiNumber() {
		return siNumber;
	}

	public void setSiNumber(String siNumber) {
		this.siNumber = siNumber;
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

	public String getRequestStore() {
		return requestStore;
	}

	public void setRequestStore(String requestStore) {
		this.requestStore = requestStore;
	}

	public String getRequestStoreLocName() {
		return requestStoreLocName;
	}

	public void setRequestStoreLocName(String requestStoreLocName) {
		this.requestStoreLocName = requestStoreLocName;
	}

	public String getRequestedByName() {
		return requestedByName;
	}

	public void setRequestedByName(String requestedByName) {
		this.requestedByName = requestedByName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public List<MaterialDispatchNoteItemsDTO> getMatDispatchItemList() {
		return matDispatchItemList;
	}

	public void setMatDispatchItemList(List<MaterialDispatchNoteItemsDTO> matDispatchItemList) {
		this.matDispatchItemList = matDispatchItemList;
	}

	public List<MaterialDispatchNoteItemsEntryDTO> getMatDispatchItemsEntryList() {
		return matDispatchItemsEntryList;
	}

	public void setMatDispatchItemsEntryList(List<MaterialDispatchNoteItemsEntryDTO> matDispatchItemsEntryList) {
		this.matDispatchItemsEntryList = matDispatchItemsEntryList;
	}

}
