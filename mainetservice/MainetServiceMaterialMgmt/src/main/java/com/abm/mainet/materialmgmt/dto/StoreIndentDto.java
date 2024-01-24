package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StoreIndentDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long storeIndentId;

	private String storeIndentNo;

	private Date storeIndentdate;

	private Long requestStore;

	private String requestStoreName;

	private Long requestStoreLocId;

	private String requestStoreLocName;

	private Long requestedBy;

	private String requestedByName;

	private Long issueStore;

	private String issueStoreName;

	private Long issueStoreLocId;

	private String issueStoreLocName;

	private Long issueIncharge;

	private String issueInchargeName;

	private String deliveryAt;

	private Date expectedDate;

	private String status;

	private Long orgId;

	private Long userId;

	private String userName;

	private Long langId;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String wfFlag;

	private List<StoreIndentItemDto> storeIndentItemDtoList = new ArrayList<>();

	public StoreIndentDto() {
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

	public Date getStoreIndentdate() {
		return storeIndentdate;
	}

	public void setStoreIndentdate(Date storeIndentdate) {
		this.storeIndentdate = storeIndentdate;
	}

	public Long getRequestStore() {
		return requestStore;
	}

	public void setRequestStore(Long requestStore) {
		this.requestStore = requestStore;
	}

	public String getRequestStoreName() {
		return requestStoreName;
	}

	public void setRequestStoreName(String requestStoreName) {
		this.requestStoreName = requestStoreName;
	}

	public Long getRequestStoreLocId() {
		return requestStoreLocId;
	}

	public void setRequestStoreLocId(Long requestStoreLocId) {
		this.requestStoreLocId = requestStoreLocId;
	}

	public String getRequestStoreLocName() {
		return requestStoreLocName;
	}

	public void setRequestStoreLocName(String requestStoreLocName) {
		this.requestStoreLocName = requestStoreLocName;
	}

	public Long getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(Long requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getRequestedByName() {
		return requestedByName;
	}

	public void setRequestedByName(String requestedByName) {
		this.requestedByName = requestedByName;
	}

	public Long getIssueStore() {
		return issueStore;
	}

	public void setIssueStore(Long issueStore) {
		this.issueStore = issueStore;
	}

	public String getIssueStoreName() {
		return issueStoreName;
	}

	public void setIssueStoreName(String issueStoreName) {
		this.issueStoreName = issueStoreName;
	}

	public Long getIssueStoreLocId() {
		return issueStoreLocId;
	}

	public void setIssueStoreLocId(Long issueStoreLocId) {
		this.issueStoreLocId = issueStoreLocId;
	}

	public String getIssueStoreLocName() {
		return issueStoreLocName;
	}

	public void setIssueStoreLocName(String issueStoreLocName) {
		this.issueStoreLocName = issueStoreLocName;
	}

	public Long getIssueIncharge() {
		return issueIncharge;
	}

	public void setIssueIncharge(Long issueIncharge) {
		this.issueIncharge = issueIncharge;
	}

	public String getIssueInchargeName() {
		return issueInchargeName;
	}

	public void setIssueInchargeName(String issueInchargeName) {
		this.issueInchargeName = issueInchargeName;
	}

	public String getDeliveryAt() {
		return deliveryAt;
	}

	public void setDeliveryAt(String deliveryAt) {
		this.deliveryAt = deliveryAt;
	}

	public Date getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(Date expectedDate) {
		this.expectedDate = expectedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String indentData) {
		this.status = indentData;
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

	public String getWfFlag() {
		return wfFlag;
	}

	public void setWfFlag(String wfFlag) {
		this.wfFlag = wfFlag;
	}

	public List<StoreIndentItemDto> getStoreIndentItemDtoList() {
		return storeIndentItemDtoList;
	}

	public void setStoreIndentItemDtoList(List<StoreIndentItemDto> storeIndentItemDtoList) {
		this.storeIndentItemDtoList = storeIndentItemDtoList;
	}

}
