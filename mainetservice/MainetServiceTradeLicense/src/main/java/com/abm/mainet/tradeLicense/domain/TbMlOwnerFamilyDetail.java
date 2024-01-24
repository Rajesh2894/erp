package com.abm.mainet.tradeLicense.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_ML_LICFAMDTL")
public class TbMlOwnerFamilyDetail implements Serializable {

	private static final long serialVersionUID = 7611278409498785136L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FAM_MEMID", unique = true, nullable = false)
	private Long famMemId; 

	@ManyToOne
	@JoinColumn(name = "TRD_ID", referencedColumnName = "TRD_ID")
	private TbMlTradeMast masterTradeId;

	@Column(name = "MEM_NAME")
	private String famMemName;

	@Column(name = "MEM_UIDNO")
	private Long famMemUidNo;

	@Column(name = "MEM_AGE")
	private Long famMemAge;

	@Column(name = "MEM_RELATION")
	private String famMemRelation;

	@Column(name = "ORGID", nullable = false)
	private Long orgid;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	

	public Long getFamMemId() {
		return famMemId;
	}



	public void setFamMemId(Long famMemId) {
		this.famMemId = famMemId;
	}



	public TbMlTradeMast getMasterTradeId() {
		return masterTradeId;
	}



	public void setMasterTradeId(TbMlTradeMast masterTradeId) {
		this.masterTradeId = masterTradeId;
	}



	public String getFamMemName() {
		return famMemName;
	}



	public void setFamMemName(String famMemName) {
		this.famMemName = famMemName;
	}



	public Long getFamMemUidNo() {
		return famMemUidNo;
	}



	public void setFamMemUidNo(Long famMemUidNo) {
		this.famMemUidNo = famMemUidNo;
	}



	public Long getFamMemAge() {
		return famMemAge;
	}



	public void setFamMemAge(Long famMemAge) {
		this.famMemAge = famMemAge;
	}



	public String getFamMemRelation() {
		return famMemRelation;
	}



	public void setFamMemRelation(String famMemRelation) {
		this.famMemRelation = famMemRelation;
	}



	public Long getOrgid() {
		return orgid;
	}



	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}



	public Long getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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



	public String[] getPkValues() {
		return new String[] { "ML", "TB_ML_LICFAMDTL", "FAM_MEMID" };
	}

}