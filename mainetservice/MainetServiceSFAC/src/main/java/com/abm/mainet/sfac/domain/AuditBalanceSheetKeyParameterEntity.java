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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_SFAC_ABS_KEY_PARAM")
public class AuditBalanceSheetKeyParameterEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 737476623405992569L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ABSK_ID", nullable = false)
	private Long absKId;

	@ManyToOne
	@JoinColumn(name = "ABS_ID", referencedColumnName = "ABS_ID")
	private AuditBalanceSheetMasterEntity masterEntity;

	@Column(name = "KEY_PARAMETER")
	private Long keyParameter;

	@Column(name = "KEY_PARAMETER_DESC", length = 100)
	private String keyParameterDesc;


	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "ABS_STATUS")
	private String absStatus;

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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "keyMasterEntity", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private List<AuditBalanceSheetSubParameterEntity> auditBalanceSheetSubParameterEntities = new ArrayList<>();

	public Long getAbsKId() {
		return absKId;
	}

	public void setAbsKId(Long absKId) {
		this.absKId = absKId;
	}

	public AuditBalanceSheetMasterEntity getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(AuditBalanceSheetMasterEntity masterEntity) {
		this.masterEntity = masterEntity;
	}

	public Long getKeyParameter() {
		return keyParameter;
	}

	public void setKeyParameter(Long keyParameter) {
		this.keyParameter = keyParameter;
	}

	public String getKeyParameterDesc() {
		return keyParameterDesc;
	}

	public void setKeyParameterDesc(String keyParameterDesc) {
		this.keyParameterDesc = keyParameterDesc;
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
	
	
	public List<AuditBalanceSheetSubParameterEntity> getAuditBalanceSheetSubParameterEntities() {
		return auditBalanceSheetSubParameterEntities;
	}

	public void setAuditBalanceSheetSubParameterEntities(
			List<AuditBalanceSheetSubParameterEntity> auditBalanceSheetSubParameterEntities) {
		this.auditBalanceSheetSubParameterEntities = auditBalanceSheetSubParameterEntities;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_ABS_KEY_PARAM", "ABSK_ID" };
	}
}
