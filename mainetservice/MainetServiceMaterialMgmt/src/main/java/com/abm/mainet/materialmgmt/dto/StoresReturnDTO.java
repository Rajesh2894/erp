package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StoresReturnDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long storeReturnId;

	private String storeReturnNo;

	private Date storeReturnDate;

	private Long mdnId;

	private String mdnNumber;

	private Date mdnDate;

	private Long storeIndentId;

	private String storeIndentNo;

	private Long requestStoreId;

	private String requestStoreName;

	private Long issueStoreId;

	private String issueStoreName;

	private String noting;

	private String status;

	private Long orgId;

	private Long langId;

	private Long userId;

	private String userName;

	private Date createDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private List<StoresReturnDetailDTO> storesReturnDetailList = new ArrayList<>();

	private Date fromDate;

	private Date toDate;

	public Long getStoreReturnId() {
		return storeReturnId;
	}

	public void setStoreReturnId(Long storeReturnId) {
		this.storeReturnId = storeReturnId;
	}

	public String getStoreReturnNo() {
		return storeReturnNo;
	}

	public void setStoreReturnNo(String storeReturnNo) {
		this.storeReturnNo = storeReturnNo;
	}

	public Date getStoreReturnDate() {
		return storeReturnDate;
	}

	public void setStoreReturnDate(Date storeReturnDate) {
		this.storeReturnDate = storeReturnDate;
	}

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

	public Long getStoreIndentId() {
		return storeIndentId;
	}

	public void setStoreIndentId(Long storeIndentId) {
		this.storeIndentId = storeIndentId;
	}

	public String getStoreIndentNo() {
		return storeIndentNo;
	}

	public void setStoreIndentNo(String storeIndentNo) {
		this.storeIndentNo = storeIndentNo;
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

	public String getNoting() {
		return noting;
	}

	public void setNoting(String noting) {
		this.noting = noting;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public List<StoresReturnDetailDTO> getStoresReturnDetailList() {
		return storesReturnDetailList;
	}

	public void setStoresReturnDetailList(List<StoresReturnDetailDTO> storesReturnDetailList) {
		this.storesReturnDetailList = storesReturnDetailList;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "StoresReturnDTO [storeReturnId=" + storeReturnId + ", storeReturnNo=" + storeReturnNo
				+ ", storeReturnDate=" + storeReturnDate + ", mdnId=" + mdnId + ", mdnNumber=" + mdnNumber
				+ ", storeIndentId=" + storeIndentId + ", storeIndentNo=" + storeIndentNo + ", requestStoreId="
				+ requestStoreId + ", requestStoreName=" + requestStoreName + ", issueStoreId=" + issueStoreId
				+ ", issueStoreName=" + issueStoreName + ", noting=" + noting + ", status=" + status + ", orgId="
				+ orgId + ", langId=" + langId + ", userId=" + userId + ", createDate=" + createDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
				+ ", storesReturnDetailList=" + storesReturnDetailList + "]";
	}

}
