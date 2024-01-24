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
@Table(name = "Tb_Sfac_Management_Cost_Info_Detail")
public class ManagementCostDetEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1531961812787626379L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MC_ID", nullable = false)
	private Long mcID;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Column(name = "MC_DISBURSED")
	private BigDecimal managementCostDisbursed;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_RELEASE")
	private Date dateOfRelease;
	
	@Column(name = "AMT_RELEASE")
	private BigDecimal amountRelease;
	
	@Column(name = "CBBO_COST_DISBURSED")
	private BigDecimal cbboCostDisbursed;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CBBO_DATE_OF_RELEASE")
	private Date cbboDateOfRelease;
	
	@Column(name = "CBBO_AMT_RELEASE")
	private BigDecimal cbbodAmountRelease;
	
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


	public Long getMcID() {
		return mcID;
	}

	public void setMcID(Long mcID) {
		this.mcID = mcID;
	}

	public FPOProfileManagementMaster getFPOProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}

	public void setFPOProfileMgmtMaster(FPOProfileManagementMaster fPOProfileMgmtMaster) {
		fpoProfileMgmtMaster = fPOProfileMgmtMaster;
	}

	public BigDecimal getManagementCostDisbursed() {
		return managementCostDisbursed;
	}

	public void setManagementCostDisbursed(BigDecimal managementCostDisbursed) {
		this.managementCostDisbursed = managementCostDisbursed;
	}

	public Date getDateOfRelease() {
		return dateOfRelease;
	}

	public void setDateOfRelease(Date dateOfRelease) {
		this.dateOfRelease = dateOfRelease;
	}

	public BigDecimal getCbboCostDisbursed() {
		return cbboCostDisbursed;
	}

	public void setCbboCostDisbursed(BigDecimal cbboCostDisbursed) {
		this.cbboCostDisbursed = cbboCostDisbursed;
	}

	public Date getCbboDateOfRelease() {
		return cbboDateOfRelease;
	}

	public void setCbboDateOfRelease(Date cbboDateOfRelease) {
		this.cbboDateOfRelease = cbboDateOfRelease;
	}

	public BigDecimal getAmountRelease() {
		return amountRelease;
	}

	public void setAmountRelease(BigDecimal amountRelease) {
		this.amountRelease = amountRelease;
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
		return new String[] { "SFAC", "Tb_Sfac_Management_Cost_Info_Detail", "MC_ID" };
	}

	public BigDecimal getCbbodAmountRelease() {
		return cbbodAmountRelease;
	}

	public void setCbbodAmountRelease(BigDecimal cbbodAmountRelease) {
		this.cbbodAmountRelease = cbbodAmountRelease;
	}
	
	
}
