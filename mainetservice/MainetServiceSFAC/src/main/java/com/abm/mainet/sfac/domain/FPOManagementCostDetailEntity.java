package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name= "tb_sfac_fpo_mgmt_cost_mast_det")
public class FPOManagementCostDetailEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2499858687336683940L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FMCC_ID", nullable = false)
	private Long fmccId;
	
	@ManyToOne
	@JoinColumn(name = "FMC_ID", referencedColumnName = "FMC_ID")
	private FPOManagementCostMasterEntity fpoManagementCostMasterEntity;
	
	@Column(name = "PARTICULARS")
	private Long particulars;
	
	@Column(name = "MGMT_COST_EXPECTED", nullable = true)
	private BigDecimal managementCostExpected;
	
	@Column(name = "MGMT_COST_INCURRED", nullable = true)
	private BigDecimal managementCostIncurred;
	
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

	public Long getFmccId() {
		return fmccId;
	}

	public void setFmccId(Long fmccId) {
		this.fmccId = fmccId;
	}

	public FPOManagementCostMasterEntity getFpoManagementCostMasterEntity() {
		return fpoManagementCostMasterEntity;
	}

	public void setFpoManagementCostMasterEntity(FPOManagementCostMasterEntity fpoManagementCostMasterEntity) {
		this.fpoManagementCostMasterEntity = fpoManagementCostMasterEntity;
	}

	

	public BigDecimal getManagementCostExpected() {
		return managementCostExpected;
	}

	public void setManagementCostExpected(BigDecimal managementCostExpected) {
		this.managementCostExpected = managementCostExpected;
	}

	

	public Long getParticulars() {
		return particulars;
	}

	public void setParticulars(Long particulars) {
		this.particulars = particulars;
	}

	public BigDecimal getManagementCostIncurred() {
		return managementCostIncurred;
	}

	public void setManagementCostIncurred(BigDecimal managementCostIncurred) {
		this.managementCostIncurred = managementCostIncurred;
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
		return new String[] { "SFAC", "tb_sfac_fpo_mgmt_cost_mast_det", "FMCC_ID" };
	}
}
