package com.abm.mainet.common.integration.acccount.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Entity
@Table(name = "TB_TAXPER_DEFINATION")
public class TaxDefinationEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1178068625421335240L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "TAX_DEFID", nullable = false)
	private Long taxDefId;

	@Column(name = "TAX_ID", nullable = true)
	private Long taxId;

	@Column(name = "CPD_VENDORSUBTYPE")
	private Long cpdVendorSubType;

	@Column(name = "TAX_PANAPP", nullable = true)
	private String taxPanApp;

	@Column(name = "TAX_THRESHOLD", nullable = true)
	private BigDecimal taxThreshold;

	@Column(name = "TAX_UNIT", nullable = true)
	private Long taxUnit;

	@Column(name = "TAX_TAXVALUE", nullable = true)
	private BigDecimal raTaxValue;

	@Column(name = "TAX_TAXTYPE", nullable = true)
	private String raTaxType;

	@Column(name = "TAX_TAXFACT", nullable = true)
	private Long raTaxFact;

	@Column(name = "TAX_TAXPER", nullable = true)
	private BigDecimal raTaxPercent;

	@Column(name = "TAX_FIX", nullable = false)
	private String taxFixed;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	public Long getTaxDefId() {
		return taxDefId;
	}

	public void setTaxDefId(Long taxDefId) {
		this.taxDefId = taxDefId;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

	public Long getCpdVendorSubType() {
		return cpdVendorSubType;
	}

	public void setCpdVendorSubType(Long cpdVendorSubType) {
		this.cpdVendorSubType = cpdVendorSubType;
	}

	public String getTaxPanApp() {
		return taxPanApp;
	}

	public void setTaxPanApp(String taxPanApp) {
		this.taxPanApp = taxPanApp;
	}

	public BigDecimal getTaxThreshold() {
		return taxThreshold;
	}

	public void setTaxThreshold(BigDecimal taxThreshold) {
		this.taxThreshold = taxThreshold;
	}

	public Long getTaxUnit() {
		return taxUnit;
	}

	public void setTaxUnit(Long taxUnit) {
		this.taxUnit = taxUnit;
	}

	public BigDecimal getRaTaxValue() {
		return raTaxValue;
	}

	public void setRaTaxValue(BigDecimal raTaxValue) {
		this.raTaxValue = raTaxValue;
	}

	public String getRaTaxType() {
		return raTaxType;
	}

	public void setRaTaxType(String raTaxType) {
		this.raTaxType = raTaxType;
	}

	public Long getRaTaxFact() {
		return raTaxFact;
	}

	public void setRaTaxFact(Long raTaxFact) {
		this.raTaxFact = raTaxFact;
	}

	public BigDecimal getRaTaxPercent() {
		return raTaxPercent;
	}

	public void setRaTaxPercent(BigDecimal raTaxPercent) {
		this.raTaxPercent = raTaxPercent;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public String getTaxFixed() {
		return taxFixed;
	}

	public void setTaxFixed(String taxFixed) {
		this.taxFixed = taxFixed;
	}

	public static String[] getPkValues() {
		return new String[] { "COM", "TB_TAXPER_DEFINATION", "TAX_DEFID" };
	}

}
