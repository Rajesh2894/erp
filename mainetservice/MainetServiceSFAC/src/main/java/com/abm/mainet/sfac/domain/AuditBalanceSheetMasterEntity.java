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
@Table(name = "TB_SFAC_AUDIT_BALANCE_SHEET_MASTER")
public class AuditBalanceSheetMasterEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7417335892479994721L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ABS_ID", nullable = false)
	private Long absId;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	
	@Column(name = "ABS_STATUS")
	private String absStatus;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "CBBO_ID")
	private Long cbboId;

	@Column(name = "CBBO_NAME")
	private String cbboName;

	@Column(name = "FPO_ID")
	private Long fpoId;

	@Column(name = "FPO_NAME")
	private String fpoName;
	
	@Column(name = "FPO_ADDRESS")
	private String fpoAddress;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CRP_DT_FROM")
	private Date crpDateFrom;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CRP_DT_TO")
	private Date crpDateTo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "PRP_DT_FROM")
	private Date prpDateFrom;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "PRP_DT_TO")
	private Date prpDateTo;

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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterEntity", cascade = CascadeType.ALL)
	private List<AuditBalanceSheetKeyParameterEntity> auditBalanceSheetKeyParameterEntities = new ArrayList<>();

	public Long getAbsId() {
		return absId;
	}

	public void setAbsId(Long absId) {
		this.absId = absId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getAbsStatus() {
		return absStatus;
	}

	public void setAbsStatus(String absStatus) {
		this.absStatus = absStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getFpoAddress() {
		return fpoAddress;
	}

	public void setFpoAddress(String fpoAddress) {
		this.fpoAddress = fpoAddress;
	}

	public Date getCrpDateFrom() {
		return crpDateFrom;
	}

	public void setCrpDateFrom(Date crpDateFrom) {
		this.crpDateFrom = crpDateFrom;
	}

	public Date getCrpDateTo() {
		return crpDateTo;
	}

	public void setCrpDateTo(Date crpDateTo) {
		this.crpDateTo = crpDateTo;
	}

	public Date getPrpDateFrom() {
		return prpDateFrom;
	}

	public void setPrpDateFrom(Date prpDateFrom) {
		this.prpDateFrom = prpDateFrom;
	}

	public Date getPrpDateTo() {
		return prpDateTo;
	}

	public void setPrpDateTo(Date prpDateTo) {
		this.prpDateTo = prpDateTo;
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
	
	

	public List<AuditBalanceSheetKeyParameterEntity> getAuditBalanceSheetKeyParameterEntities() {
		return auditBalanceSheetKeyParameterEntities;
	}

	public void setAuditBalanceSheetKeyParameterEntities(
			List<AuditBalanceSheetKeyParameterEntity> auditBalanceSheetKeyParameterEntities) {
		this.auditBalanceSheetKeyParameterEntities = auditBalanceSheetKeyParameterEntities;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_AUDIT_BALANCE_SHEET_MASTER", "ABS_ID" };
	}

}
