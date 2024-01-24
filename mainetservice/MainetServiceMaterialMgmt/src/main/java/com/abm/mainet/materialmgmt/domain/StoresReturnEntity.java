package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mm_storereturn")
public class StoresReturnEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "storereturnid")
	private Long storeReturnId;

	@Column(name = "storereturnno")
	private String storeReturnNo;

	@Column(name = "storereturndate")
	private Date storeReturnDate;

	@Column(name = "mdnid")
	private Long mdnId;

	@Column(name = "siid")
	private Long storeIndentId;

	@Column(name = "requeststoreid")
	private Long requestStoreId;

	@Column(name = "issuestoreid")
	private Long issueStoreId;

	@Column(name = "noting")
	private String noting;

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

	@OneToMany(mappedBy = "storesReturnEntity", cascade = CascadeType.ALL)
	private List<StoresReturnDetailEntity> returnDetailEntityList = new ArrayList<>();

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

	public Long getStoreIndentId() {
		return storeIndentId;
	}

	public void setStoreIndentId(Long storeIndentId) {
		this.storeIndentId = storeIndentId;
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

	public List<StoresReturnDetailEntity> getReturnDetailEntityList() {
		return returnDetailEntityList;
	}

	public void setReturnDetailEntityList(List<StoresReturnDetailEntity> returnDetailEntityList) {
		this.returnDetailEntityList = returnDetailEntityList;
	}

	public String[] getPkValues() {
		return new String[] { "MMM", "mm_storereturn", "storereturnid" };
	}

}
