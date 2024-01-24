package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "Tb_Sfac_Equity_Grant_shareholding_Det")
public class EquityGrantShareHoldingDetailEntity implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 7341729100698248653L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "EGSH_ID", nullable = false)
	private Long egfcId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EG_ID", referencedColumnName = "EG_ID")
	private EquityGrantMasterEntity masterEntity;

	@Column(name = "NO_OF_SH")
	private Long noOfShareHolder;

	@Column(name = "VALUE_OF_SHARE_ALLOT")
	private BigDecimal faceValueShareAllotted;

	@Column(name = "TOTAL_AMT_PAID")
	private BigDecimal totalAmtPaid;

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




	public Long getEgfcId() {
		return egfcId;
	}




	public void setEgfcId(Long egfcId) {
		this.egfcId = egfcId;
	}




	public EquityGrantMasterEntity getMasterEntity() {
		return masterEntity;
	}




	public void setMasterEntity(EquityGrantMasterEntity masterEntity) {
		this.masterEntity = masterEntity;
	}



	public Long getNoOfShareHolder() {
		return noOfShareHolder;
	}




	public void setNoOfShareHolder(Long noOfShareHolder) {
		this.noOfShareHolder = noOfShareHolder;
	}




	public BigDecimal getFaceValueShareAllotted() {
		return faceValueShareAllotted;
	}




	public void setFaceValueShareAllotted(BigDecimal faceValueShareAllotted) {
		this.faceValueShareAllotted = faceValueShareAllotted;
	}




	public BigDecimal getTotalAmtPaid() {
		return totalAmtPaid;
	}




	public void setTotalAmtPaid(BigDecimal totalAmtPaid) {
		this.totalAmtPaid = totalAmtPaid;
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
		return new String[] { "SFAC", "Tb_Sfac_Equity_Grant_shareholding_Det", "EGSH_ID" };
	}

}
