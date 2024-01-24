package com.abm.mainet.sfac.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name ="TB_SFAC_DPR_ENTRY_MASTER")
public class DPREntryMasterEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2906947716300608520L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DPR_ID", nullable = false)
	private Long dprId;
	
	@Column(name = "CBBO_ID")
	private Long cbboId;
	
	@Column(name = "CBBO_NAME")
	private String cbboName;
	
	@Column(name = "FPO_ID")
	private Long fpoId;
	
	@Column(name = "FPO_NAME")
	private String fpoName;
	
	@Column(name = "IA_ID")
	private Long iaId;
	
	@Column(name = "IA_NAME")
	private String iaName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_SUBMISSION")
	private Date dateOfSubmission;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_RESUBMISSION")
	private Date dateOfResubmission;
	
	@Column(name = "APP_NO")
	private Long applicationNumber;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "AUTH_REMARK")
	private String authRemark;
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dprEntryMasterEntity", cascade = CascadeType.ALL)
	private List<DPREntryDetailsEntity> dprEntryDetailsEntities = new ArrayList<>();
	
	

	public List<DPREntryDetailsEntity> getDprEntryDetailsEntities() {
		return dprEntryDetailsEntities;
	}

	public void setDprEntryDetailsEntities(List<DPREntryDetailsEntity> dprEntryDetailsEntities) {
		this.dprEntryDetailsEntities = dprEntryDetailsEntities;
	}

	public Long getDprId() {
		return dprId;
	}

	public void setDprId(Long dprId) {
		this.dprId = dprId;
	}

	public Long getCbboId() {
		return cbboId;
	}

	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}

	public String getCbboName() {
		return cbboName;
	}

	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}

	public Long getFpoId() {
		return fpoId;
	}

	public void setFpoId(Long fpoId) {
		this.fpoId = fpoId;
	}

	public String getFpoName() {
		return fpoName;
	}

	public void setFpoName(String fpoName) {
		this.fpoName = fpoName;
	}

	public Long getIaId() {
		return iaId;
	}

	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	public String getIaName() {
		return iaName;
	}

	public void setIaName(String iaName) {
		this.iaName = iaName;
	}

	public Date getDateOfSubmission() {
		return dateOfSubmission;
	}

	public void setDateOfSubmission(Date dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
	}

	public Date getDateOfResubmission() {
		return dateOfResubmission;
	}

	public void setDateOfResubmission(Date dateOfResubmission) {
		this.dateOfResubmission = dateOfResubmission;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
		return new String[] { "SFAC", "TB_SFAC_DPR_ENTRY_MASTER", "DPR_ID" };
	}

}
