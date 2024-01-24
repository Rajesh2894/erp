package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class TaxDefinationDto implements Serializable {

	private static final long serialVersionUID = 5445407587095936863L;
	private Long taxDefId;
	private Long taxId;
	private Long cpdVendorSubType;
	private String taxPanApp;
	private BigDecimal taxThreshold;
	private Long taxUnit;
	private BigDecimal raTaxValue;
	private String raTaxType;
	private Long raTaxFact;
	private BigDecimal raTaxPercent;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String taxDesc;
	private String lookUpDesc;
	private String vendorSubTypeDesc;
	private String taxUnitDesc;
	private String lookUpOtherField;
	private String taxFixed;

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

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}

	public String getLookUpDesc() {
		return lookUpDesc;
	}

	public void setLookUpDesc(String lookUpDesc) {
		this.lookUpDesc = lookUpDesc;
	}

	public String getVendorSubTypeDesc() {
		return vendorSubTypeDesc;
	}

	public void setVendorSubTypeDesc(String vendorSubTypeDesc) {
		this.vendorSubTypeDesc = vendorSubTypeDesc;
	}

	public String getTaxUnitDesc() {
		return taxUnitDesc;
	}

	public void setTaxUnitDesc(String taxUnitDesc) {
		this.taxUnitDesc = taxUnitDesc;
	}

	public String getLookUpOtherField() {
		return lookUpOtherField;
	}

	public void setLookUpOtherField(String lookUpOtherField) {
		this.lookUpOtherField = lookUpOtherField;
	}

	public String getTaxFixed() {
		return taxFixed;
	}

	public void setTaxFixed(String taxFixed) {
		this.taxFixed = taxFixed;
	}

}
