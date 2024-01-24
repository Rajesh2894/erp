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
@Table(name = "MM_INDENT")
public class IndentProcessEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "indentid")
	private Long indentid;

	@Column(name = "Indentno")
	private String indentno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "indentdate")
	private Date indentdate;

	@Column(name = "indenter")
	private Long indenter;

	@Column(name = "reportingmgr")
	private Long reportingmgr;

	@Column(name = "beneficiary")
	private String beneficiary;

	@Column(name = "storeid")
	private Long storeid;

	@Column(name = "Deliveryat")
	private String deliveryat;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expecteddate")
	private Date expecteddate;

	@Column(name = "Issuedby")
	private Long issuedby;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IssuedDate")
	private Date issuedDate;

	@Column(name = "Status")
	private String status;

	@Column(name = "ORGID")
	private Long orgid;

	@Column(name = "USER_ID")
	private Long user_id;

	@Column(name = "LANGID")
	private Long langId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LMODDATE")
	private Date lmoddate;

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
	private String wfFlag;

	@Column(name = "DEPT_ID")
	private Long deptId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "indentid", cascade = CascadeType.ALL)
	private List<IndentItemEntity> item = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "indentid", cascade = CascadeType.ALL)
	private List<IndentIssueEntity> issueItemList = new ArrayList<>();

	public List<IndentIssueEntity> getIssueItemList() {
		return issueItemList;
	}

	public void setIssueItemList(List<IndentIssueEntity> issueItemList) {
		this.issueItemList = issueItemList;
	}

	public Long getIndentid() {
		return indentid;
	}

	public void setIndentid(Long indentid) {
		this.indentid = indentid;
	}

	public String getIndentno() {
		return indentno;
	}

	public void setIndentno(String indentno) {
		this.indentno = indentno;
	}

	public Date getIndentdate() {
		return indentdate;
	}

	public void setIndentdate(Date indentdate) {
		this.indentdate = indentdate;
	}

	public Long getIndenter() {
		return indenter;
	}

	public void setIndenter(Long indenter) {
		this.indenter = indenter;
	}

	public Long getReportingmgr() {
		return reportingmgr;
	}

	public void setReportingmgr(Long reportingmgr) {
		this.reportingmgr = reportingmgr;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public Long getStoreid() {
		return storeid;
	}

	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}

	public String getDeliveryat() {
		return deliveryat;
	}

	public void setDeliveryat(String deliveryat) {
		this.deliveryat = deliveryat;
	}

	public Date getExpecteddate() {
		return expecteddate;
	}

	public void setExpecteddate(Date expecteddate) {
		this.expecteddate = expecteddate;
	}

	public Long getIssuedby() {
		return issuedby;
	}

	public void setIssuedby(Long issuedby) {
		this.issuedby = issuedby;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
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

	public String getWf_flag() {
		return wfFlag;
	}

	public void setWf_flag(String wfFlag) {
		this.wfFlag = wfFlag;
	}

	public List<IndentItemEntity> getItem() {
		return item;
	}

	public void setItem(List<IndentItemEntity> entity) {
		this.item = entity;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String[] getPkValues() {
		return new String[] { "ITP", "MM_INDENT", "indentid" };
	}


}
