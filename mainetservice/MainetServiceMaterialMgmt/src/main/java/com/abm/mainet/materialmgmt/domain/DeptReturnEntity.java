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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mm_deptreturn")
public class DeptReturnEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "dreturnid")
	private Long dreturnid;

	@Column(name = "dreturnno")
	private String dreturnno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dreturndate")
	private Date dreturndate;

	@Column(name = "indenter")
	private Long indenter;

	@Column(name = "reportingmgr")
	private Long reportingmgr;

	@Column(name = "beneficiary")
	private String beneficiary;

	@Column(name = "previndent")
	private Character previndent;

	@Column(name = "indentid")
	private Long indentid;

	@Column(name = "noting")
	private String noting;

	@Column(name = "Status")
	private Character status;

	@Column(name = "ORGID")
	private Long orgid;

	@Column(name = "USER_ID")
	private Long userid;

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

	@Column(name = "storeid")
	private Long storeid;

	@OneToMany(mappedBy = "dreturnid", cascade = CascadeType.ALL)
	private List<DeptReturnEntityDetail> deptReturnEntityDetail = new ArrayList<>();

	public Long getDreturnid() {
		return dreturnid;
	}

	public void setDreturnid(Long dreturnid) {
		this.dreturnid = dreturnid;
	}

	public String getDreturnno() {
		return dreturnno;
	}

	public void setDreturnno(String dreturnno) {
		this.dreturnno = dreturnno;
	}

	public Date getDreturndate() {
		return dreturndate;
	}

	public void setDreturndate(Date dreturndate) {
		this.dreturndate = dreturndate;
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

	public Character getPrevindent() {
		return previndent;
	}

	public void setPrevindent(Character previndent) {
		this.previndent = previndent;
	}

	public Long getIndentid() {
		return indentid;
	}

	public void setIndentid(Long indentid) {
		this.indentid = indentid;
	}

	public String getNoting() {
		return noting;
	}

	public void setNoting(String noting) {
		this.noting = noting;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
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

	public String getWfFlag() {
		return wfFlag;
	}

	public void setWfFlag(String wfFlag) {
		this.wfFlag = wfFlag;
	}

	public Long getStoreid() {
		return storeid;
	}

	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}

	public List<DeptReturnEntityDetail> getDeptReturnEntityDetail() {
		return deptReturnEntityDetail;
	}

	public void setDeptReturnEntityDetail(List<DeptReturnEntityDetail> deptReturnEntityDetail) {
		this.deptReturnEntityDetail = deptReturnEntityDetail;
	}

	public String[] getPkValues() {
		return new String[] { "MMM", "mm_deptreturn", "dreturnid" };
	}

}
