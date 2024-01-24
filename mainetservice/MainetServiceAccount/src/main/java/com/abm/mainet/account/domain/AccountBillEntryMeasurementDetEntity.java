package com.abm.mainet.account.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_BILL_MBDET")
public class AccountBillEntryMeasurementDetEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MB_ID", nullable = false)
	private Long mbId;

	@Column(name = "MB_ITEMDESC")
	private String mbItemDesc;

	@Column(name = "MB_ITEMWET")
	private BigDecimal mbItemWet;
	
	@Column(name = "MB_ITEMRATE")
	private BigDecimal mbItemRate;
	
	@Column(name = "MB_ITEMUNIT")
	private BigDecimal mbItemUnit;
	
	@Column(name = "MB_ITEMAMT")
	private BigDecimal mbItemAmt;

	@Column(name = "ORGID", nullable = false)
	private Long orgid;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@JsonIgnore
	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMacAddress;

	@JsonIgnore
	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacAddressUpdated;

	// ----------------------------------------------------------------------
	// ENTITY LINKS ( RELATIONSHIP )
	// ----------------------------------------------------------------------
	@ManyToOne
	@JoinColumn(name = "BM_ID", referencedColumnName = "BM_ID")
	private AccountBillEntryMasterEnitity billMasterId;

	public static String[] getPkValues() {
		return new String[] { "AC", "TB_AC_BILL_MBDET", "MB_ID" };
	}

	public Long getMbId() {
		return mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

	public String getMbItemDesc() {
		return mbItemDesc;
	}

	public void setMbItemDesc(String mbItemDesc) {
		this.mbItemDesc = mbItemDesc;
	}

	public BigDecimal getMbItemWet() {
		return mbItemWet;
	}

	public void setMbItemWet(BigDecimal mbItemWet) {
		this.mbItemWet = mbItemWet;
	}

	public BigDecimal getMbItemRate() {
		return mbItemRate;
	}

	public void setMbItemRate(BigDecimal mbItemRate) {
		this.mbItemRate = mbItemRate;
	}

	public BigDecimal getMbItemUnit() {
		return mbItemUnit;
	}

	public void setMbItemUnit(BigDecimal mbItemUnit) {
		this.mbItemUnit = mbItemUnit;
	}

	public BigDecimal getMbItemAmt() {
		return mbItemAmt;
	}

	public void setMbItemAmt(BigDecimal mbItemAmt) {
		this.mbItemAmt = mbItemAmt;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLgIpMacAddress() {
		return lgIpMacAddress;
	}

	public void setLgIpMacAddress(String lgIpMacAddress) {
		this.lgIpMacAddress = lgIpMacAddress;
	}

	public String getLgIpMacAddressUpdated() {
		return lgIpMacAddressUpdated;
	}

	public void setLgIpMacAddressUpdated(String lgIpMacAddressUpdated) {
		this.lgIpMacAddressUpdated = lgIpMacAddressUpdated;
	}

	public AccountBillEntryMasterEnitity getBillMasterId() {
		return billMasterId;
	}

	public void setBillMasterId(AccountBillEntryMasterEnitity billMasterId) {
		this.billMasterId = billMasterId;
	}

}
