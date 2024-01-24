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
@Table(name = "Tb_Sfac_Subsidies_Info_Detail")
public class SubsidiesInfoDetailEntity implements Serializable {
	




	/**
	 * 
	 */
	private static final long serialVersionUID = -3714220353034682952L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "SUBSIDIES_ID", nullable = false)
	private Long subsidiesId;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Column(name="Scheme_Name")
	private String schemeName;
	
	@Column(name="Subsidies_Amount")
	private BigDecimal subsidiesAmount;
	
	 
	@Column(name="SCHEME_AGENCY")
	private String schemeAgency;
	
	@Column(name="TOTAL_AMT")
	private BigDecimal totalAmount;
	
	@Column(name="AMT_PAID_BY_FARMER")
	private BigDecimal amountPaidByFarmer;
	
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
	


	
	
	public Long getSubsidiesId() {
		return subsidiesId;
	}




	public void setSubsidiesId(Long subsidiesId) {
		this.subsidiesId = subsidiesId;
	}





	public FPOProfileManagementMaster getFpoProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}


	public void setFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileMgmtMaster) {
		this.fpoProfileMgmtMaster = fpoProfileMgmtMaster;
	}


	public String getSchemeName() {
		return schemeName;
	}




	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}




	public BigDecimal getSubsidiesAmount() {
		return subsidiesAmount;
	}




	public void setSubsidiesAmount(BigDecimal subsidiesAmount) {
		this.subsidiesAmount = subsidiesAmount;
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


	


	public String getSchemeAgency() {
		return schemeAgency;
	}




	public void setSchemeAgency(String schemeAgency) {
		this.schemeAgency = schemeAgency;
	}




	public BigDecimal getTotalAmount() {
		return totalAmount;
	}




	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}




	public BigDecimal getAmountPaidByFarmer() {
		return amountPaidByFarmer;
	}




	public void setAmountPaidByFarmer(BigDecimal amountPaidByFarmer) {
		this.amountPaidByFarmer = amountPaidByFarmer;
	}




	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_Subsidies_Info_Detail", "SUBSIDIES_ID" };
	}

}
