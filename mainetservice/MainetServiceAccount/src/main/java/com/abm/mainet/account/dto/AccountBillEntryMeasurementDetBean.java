package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBillEntryMeasurementDetBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long mbId;

    private Long billMasterId;
    
    private String mbItemDesc;

    private BigDecimal mbItemWet;
    
    private BigDecimal mbItemRate;
    
    private BigDecimal mbItemUnit;
    
    private BigDecimal mbItemAmt;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private Long languageId;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddress;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddressUpdated;
    
	public Long getMbId() {
		return mbId;
	}
	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}
	public Long getBillMasterId() {
		return billMasterId;
	}
	public void setBillMasterId(Long billMasterId) {
		this.billMasterId = billMasterId;
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
	public Long getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
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

}
