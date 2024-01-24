package com.abm.mainet.workManagement.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class WmsRaBillTaxDetailsDto {

	private Long raTaxId;
	private Long taxId;
	private BigDecimal raTaxValue;
	private String raTaxType;
	private Long raTaxFact;
	private BigDecimal raTaxPercent;
	private WorksRABillDto worksRABill;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String taxDesc;
	private String raRemark;

	public Long getRaTaxId() {
		return raTaxId;
	}

	public void setRaTaxId(Long raTaxId) {
		this.raTaxId = raTaxId;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
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

	public WorksRABillDto getWorksRABill() {
		return worksRABill;
	}

	public void setWorksRABill(WorksRABillDto worksRABill) {
		this.worksRABill = worksRABill;
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

	public BigDecimal getRaTaxPercent() {
		return raTaxPercent;
	}

	public void setRaTaxPercent(BigDecimal raTaxPercent) {
		this.raTaxPercent = raTaxPercent;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}

	public String getRaRemark() {
		return raRemark;
	}

	public void setRaRemark(String raRemark) {
		this.raRemark = raRemark;
	}

}
