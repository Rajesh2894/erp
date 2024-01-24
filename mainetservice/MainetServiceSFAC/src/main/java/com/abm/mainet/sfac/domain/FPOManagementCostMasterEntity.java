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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "tb_sfac_fpo_mgmt_cost_mast")
public class FPOManagementCostMasterEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 472086498928431150L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FMC_ID", nullable = false)
	private Long fmcId;
	
	@Column(name = "APP_NO")
	private Long applicationNumber;
	
	@ManyToOne
	@JoinColumn(name = "FPO_ID", referencedColumnName = "FPO_ID")
	private FPOMasterEntity fpoMasterEntity;
	
	@ManyToOne
	@JoinColumn(name = "CBBO_ID", referencedColumnName = "CBBO_ID")
	private CBBOMasterEntity cbboMasterEntity;
	
	@ManyToOne
	@JoinColumn(name = "IA_ID", referencedColumnName = "IA_ID")
	private IAMasterEntity iaMasterEntity;
	
	@Column(name = "FINANCIAL_YEAR")
	private Long financialYear;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "TOTAL_COST_APPROVED")
	private BigDecimal totalCostApproved;
	
	@Column(name = "AUTH_REMARK")
	private String authRemark;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoManagementCostMasterEntity", cascade = CascadeType.ALL)
	private List<FPOManagementCostDetailEntity> fpoManagementCostDetailEntities = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoManagementCostMasterEntity", cascade = CascadeType.ALL)
	private List<FPOManagementCostDocDetailEntity> fpoManagementCostDocDetailEntities = new ArrayList<>();
	
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

	public Long getFmcId() {
		return fmcId;
	}

	public void setFmcId(Long fmcId) {
		this.fmcId = fmcId;
	}

	public FPOMasterEntity getFpoMasterEntity() {
		return fpoMasterEntity;
	}

	public void setFpoMasterEntity(FPOMasterEntity fpoMasterEntity) {
		this.fpoMasterEntity = fpoMasterEntity;
	}

	public CBBOMasterEntity getCbboMasterEntity() {
		return cbboMasterEntity;
	}

	public void setCbboMasterEntity(CBBOMasterEntity cbboMasterEntity) {
		this.cbboMasterEntity = cbboMasterEntity;
	}

	public IAMasterEntity getIaMasterEntity() {
		return iaMasterEntity;
	}

	public void setIaMasterEntity(IAMasterEntity iaMasterEntity) {
		this.iaMasterEntity = iaMasterEntity;
	}

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
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
	
	



	

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public List<FPOManagementCostDetailEntity> getFpoManagementCostDetailEntities() {
		return fpoManagementCostDetailEntities;
	}

	public void setFpoManagementCostDetailEntities(List<FPOManagementCostDetailEntity> fpoManagementCostDetailEntities) {
		this.fpoManagementCostDetailEntities = fpoManagementCostDetailEntities;
	}

	public List<FPOManagementCostDocDetailEntity> getFpoManagementCostDocDetailEntities() {
		return fpoManagementCostDocDetailEntities;
	}

	public void setFpoManagementCostDocDetailEntities(
			List<FPOManagementCostDocDetailEntity> fpoManagementCostDocDetailEntities) {
		this.fpoManagementCostDocDetailEntities = fpoManagementCostDocDetailEntities;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalCostApproved() {
		return totalCostApproved;
	}

	public void setTotalCostApproved(BigDecimal totalCostApproved) {
		this.totalCostApproved = totalCostApproved;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "tb_sfac_fpo_mgmt_cost_mast", "FMC_ID" };
	}
}
