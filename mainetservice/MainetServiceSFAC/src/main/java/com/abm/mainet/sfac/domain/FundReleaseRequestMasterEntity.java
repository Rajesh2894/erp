package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "Tb_SFAC_FUND_RELEASE_REQ_MASTER")
public class FundReleaseRequestMasterEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 702405027618887700L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FRR_ID", nullable = false)
	private Long frrId;
	
	@Column(name = "IA_ID")
	private Long iaId;
	
	@Column(name = "FILE_REF_NO")
	private String fileReferenceNumber;
	
	@Column(name = "FIN_YEAR")
	private Long financialYear;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "APP_NO")
	private Long applicationNumber;
	
	@Column(name = "NEW_DEMAND_TOTAL")
	private BigDecimal newDemandTotal;
	
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fundReleaseRequestMasterEntity", cascade = CascadeType.ALL)
	private List<FundReleaseRequestDetailEntity> fundReleaseRequestDetailEntities = new ArrayList<>();
	
	

	public List<FundReleaseRequestDetailEntity> getFundReleaseRequestDetailEntities() {
		return fundReleaseRequestDetailEntities;
	}

	public void setFundReleaseRequestDetailEntities(List<FundReleaseRequestDetailEntity> fundReleaseRequestDetailEntities) {
		this.fundReleaseRequestDetailEntities = fundReleaseRequestDetailEntities;
	}

	public Long getFrrId() {
		return frrId;
	}

	public void setFrrId(Long frrId) {
		this.frrId = frrId;
	}

	public Long getIaId() {
		return iaId;
	}

	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	public String getFileReferenceNumber() {
		return fileReferenceNumber;
	}

	public void setFileReferenceNumber(String fileReferenceNumber) {
		this.fileReferenceNumber = fileReferenceNumber;
	}

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public BigDecimal getNewDemandTotal() {
		return newDemandTotal;
	}

	public void setNewDemandTotal(BigDecimal newDemandTotal) {
		this.newDemandTotal = newDemandTotal;
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
		return new String[] { "SFAC", "Tb_SFAC_FUND_RELEASE_REQ_MASTER", "FRR_ID" };
	}

}
