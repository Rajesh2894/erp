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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mm_deptreturn_det")
public class DeptReturnEntityDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "dreturnsdetid")
	private Long dreturnsdetid;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dreturnid", nullable = false)
	private DeptReturnEntity dreturnid;


	@Column(name = "storeid")
	private Long storeid;
	
	@Column(name = "indissuemid")
	private Long indissuemid;
	
	@Column(name = "itemid")
	private Long itemid;
	
	@Column(name = "itemno")
	private String itemno;
	
	@Column(name = "issuedqty")
	private Long issueqty;
	
	@Column(name = "returnqty")
	private Long returnqty;
	
	@Column(name = "itemcondition")
	private Long itemcondition;
	
	@Column(name = "ReasonforReturn")
	private Long reasonforreturn;
	
	@Column(name = "disposalflag")
	private Character disposalflag;
	
	@Column(name = "binlocation")
	private Long binlocation;
	
	@Column(name = "Status")
	private Character Status;
	
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
	
	
	

	public Long getDreturnsdetid() {
		return dreturnsdetid;
	}


	public void setDreturnsdetid(Long dreturnsdetid) {
		this.dreturnsdetid = dreturnsdetid;
	}


	


	

	public Long getStoreid() {
		return storeid;
	}


	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}


	public Long getIndissuemid() {
		return indissuemid;
	}


	public void setIndissuemid(Long indissuemid) {
		this.indissuemid = indissuemid;
	}


	public Long getItemid() {
		return itemid;
	}


	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}


	public String getItemno() {
		return itemno;
	}


	public void setItemno(String itemno) {
		this.itemno = itemno;
	}


	public Long getIssueqty() {
		return issueqty;
	}



	public void setIssueqty(Long issueqty) {
		this.issueqty = issueqty;
	}


	public Long getReturnqty() {
		return returnqty;
	}


	public void setReturnqty(Long returnqty) {
		this.returnqty = returnqty;
	}


	public Long getItemcondition() {
		return itemcondition;
	}

	public void setItemcondition(Long itemcondition) {
		this.itemcondition = itemcondition;
	}

	public Long getReasonforreturn() {
		return reasonforreturn;
	}

	public void setReasonforreturn(Long reasonforreturn) {
		this.reasonforreturn = reasonforreturn;
	}

	public Character getDisposalflag() {
		return disposalflag;
	}

	public void setDisposalflag(Character disposalflag) {
		this.disposalflag = disposalflag;
	}

	public Long getBinlocation() {
		return binlocation;
	}


	public void setBinlocation(Long binlocation) {
		this.binlocation = binlocation;
	}


	public Character getStatus() {
		return Status;
	}


	public void setStatus(Character status) {
		Status = status;
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
	
	


	public DeptReturnEntity getDreturnid() {
		return dreturnid;
	}


	public void setDreturnid(DeptReturnEntity dreturnid) {
		this.dreturnid = dreturnid;
	}


	public String[] getPkValues() {
		return new String[] { "MMM", "mm_deptreturn_det", "dreturnsdetid" };
	}


}
