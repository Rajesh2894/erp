package com.abm.mainet.legal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_LGL_CASE_COUNTER_AFF")
public class CounterAffidavitEntity {

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CAF_ID", unique = true, nullable = false)
	private Long cafId;

	@Column(name ="CSE_ID")
	private Long caseId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CAF_DATE")
	private Date afDate;
	
	@Column(name = "CAF_COUDATE")
	@Temporal(TemporalType.DATE)
	private Date cafDate;
	
	@Column(name = "CAF_TYPE")
	private String cafType;
	
	@Column(name = "CAF_DEFNAME")
	private String cafDefender;
	
	@Column(name = "CAF_RESNAME")
	private String cafPlaintiff;
	
	@Column(name = "ORGID")
	private long orgId;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "LG_IP_MAC")
	private String lgIpMac;
	
	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;
	
	@Column(name ="CREATED_BY")
	private Long createdBy;
	
	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public Date getAfDate() {
		return afDate;
	}

	public void setAfDate(Date afDate) {
		this.afDate = afDate;
	}

	public Date getCafDate() {
		return cafDate;
	}

	public void setCafDate(Date cafDate) {
		this.cafDate = cafDate;
	}

	public String getCafType() {
		return cafType;
	}

	public void setCafType(String cafType) {
		this.cafType = cafType;
	}

	public String getCafDefender() {
		return cafDefender;
	}

	public void setCafDefender(String cafDefender) {
		this.cafDefender = cafDefender;
	}

	public String getCafPlaintiff() {
		return cafPlaintiff;
	}

	public void setCafPlaintiff(String cafPlaintiff) {
		this.cafPlaintiff = cafPlaintiff;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
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

	public Long getCafId() {
		return cafId;
	}

	public void setCafId(Long cafId) {
		this.cafId = cafId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String[] getPkValues() {
		return new String[] { "LGL", "TB_LGL_CASE_COUTER_AFF", "CAF_ID" };
	}

}
