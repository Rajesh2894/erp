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
@Table(name = "TB_SFAC_ABS_SUB_PARAM")
public class AuditBalanceSheetSubParameterEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8724824677633799871L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ABSS_ID", nullable = false)
	private Long abssId;

	@ManyToOne
	@JoinColumn(name = "ABSK_ID", referencedColumnName = "ABSK_ID")
	private AuditBalanceSheetKeyParameterEntity keyMasterEntity;

	@Column(name = "SUB_PARAMETER")
	private Long subParameter;

	@Column(name = "SUB_PARAMETER_DESC", length = 500)
	private String subParameterDesc;

	


	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "ABS_STATUS")
	private Long absStatus;

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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "absSubParamEntity",  cascade = CascadeType.ALL)
	List<AuditBalanceSheetSubParameterDetail> auditBalanceSheetSubParameterDetails = new ArrayList<>();
	
	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_ABS_SUB_PARAM", "ABSS_ID" };
	}

	public Long getAbssId() {
		return abssId;
	}

	public void setAbssId(Long abssId) {
		this.abssId = abssId;
	}

	public AuditBalanceSheetKeyParameterEntity getKeyMasterEntity() {
		return keyMasterEntity;
	}

	public void setKeyMasterEntity(AuditBalanceSheetKeyParameterEntity keyMasterEntity) {
		this.keyMasterEntity = keyMasterEntity;
	}

	public Long getSubParameter() {
		return subParameter;
	}

	public void setSubParameter(Long subParameter) {
		this.subParameter = subParameter;
	}

	public String getSubParameterDesc() {
		return subParameterDesc;
	}

	public void setSubParameterDesc(String subParameterDesc) {
		this.subParameterDesc = subParameterDesc;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getAbsStatus() {
		return absStatus;
	}

	public void setAbsStatus(Long absStatus) {
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

	public List<AuditBalanceSheetSubParameterDetail> getAuditBalanceSheetSubParameterDetails() {
		return auditBalanceSheetSubParameterDetails;
	}

	public void setAuditBalanceSheetSubParameterDetails(
			List<AuditBalanceSheetSubParameterDetail> auditBalanceSheetSubParameterDetails) {
		this.auditBalanceSheetSubParameterDetails = auditBalanceSheetSubParameterDetails;
	}
	
	

}
