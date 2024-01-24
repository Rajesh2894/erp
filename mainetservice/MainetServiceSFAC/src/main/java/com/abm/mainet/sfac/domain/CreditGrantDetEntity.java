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
@Table(name = "Tb_Sfac_Credit_Grant_Info_Detail")
public class CreditGrantDetEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8345643064860034580L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CG_ID", nullable = false)
	private Long fsID;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_CGF")
	private Date dateOfCGF;
	
	@Column(name = "CGF_AVAILED")
	private Long cgfAvailed;
	
	@Column(name = "AMT_OF_CGF")
	private BigDecimal amountOfCGF;
	
	@Column(name = "ACT_CGF")
	private String actCGF;
	
	@Column(name = "TOT_COVRAGE_CGF")
	private BigDecimal totalCovrageCGF;
	
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

	public Long getFsID() {
		return fsID;
	}

	public void setFsID(Long fsID) {
		this.fsID = fsID;
	}

	public FPOProfileManagementMaster getFPOProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}

	public void setFPOProfileMgmtMaster(FPOProfileManagementMaster fPOProfileMgmtMaster) {
		fpoProfileMgmtMaster = fPOProfileMgmtMaster;
	}

	public Date getDateOfCGF() {
		return dateOfCGF;
	}

	public void setDateOfCGF(Date dateOfCGF) {
		this.dateOfCGF = dateOfCGF;
	}

	public Long getCgfAvailed() {
		return cgfAvailed;
	}

	public void setCgfAvailed(Long cgfAvailed) {
		this.cgfAvailed = cgfAvailed;
	}

	public BigDecimal getAmountOfCGF() {
		return amountOfCGF;
	}

	public void setAmountOfCGF(BigDecimal amountOfCGF) {
		this.amountOfCGF = amountOfCGF;
	}

	public String getActCGF() {
		return actCGF;
	}

	public void setActCGF(String actCGF) {
		this.actCGF = actCGF;
	}

	public BigDecimal getTotalCovrageCGF() {
		return totalCovrageCGF;
	}

	public void setTotalCovrageCGF(BigDecimal totalCovrageCGF) {
		this.totalCovrageCGF = totalCovrageCGF;
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
		return new String[] { "SFAC", "Tb_Sfac_Credit_Grant_Info_Detail", "CG_ID" };
	}
}
