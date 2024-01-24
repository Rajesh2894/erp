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
@Table(name = "mm_storeindent")
public class StoreIndentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "siid")
	private Long storeIndentId;

	@Column(name = "sino")
	private String storeIndentNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sidate")
	private Date storeIndentdate;

	@Column(name = "requeststore")
	private Long requestStore;

	@Column(name = "requestedby")
	private Long requestedBy;

	@Column(name = "issuestore")
	private Long issueStore;

	@Column(name = "issueincharge")
	private Long issueIncharge;

	@Column(name = "Deliveryat")
	private String deliveryAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expecteddate")
	private Date expectedDate;

	@Column(name = "Status",length = 1)
	private String status;

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

	@Column(name = "WF_Flag")
	private String wfFlag;

	@OneToMany(mappedBy = "storeIndentEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<StoreIndentItemEntity> storeIndentItemEntityList = new ArrayList<>();

	public StoreIndentEntity() {
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

	public Long getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(Long requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Long getIssueStore() {
		return issueStore;
	}

	public void setIssueStore(Long issueStore) {
		this.issueStore = issueStore;
	}

	public Long getIssueIncharge() {
		return issueIncharge;
	}

	public void setIssueIncharge(Long issueIncharge) {
		this.issueIncharge = issueIncharge;
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

	public List<StoreIndentItemEntity> getStoreIndentItemEntityList() {
		return storeIndentItemEntityList;
	}

	public void setStoreIndentItemEntityList(List<StoreIndentItemEntity> storeIndentItemEntityList) {
		this.storeIndentItemEntityList = storeIndentItemEntityList;
	}

	public String[] getPkValues() {
		return new String[] { "MMM", "mm_storeindent", "siid" };
	}

}
