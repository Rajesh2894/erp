package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FinancialInformationDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1566520978505373257L;
	
	private Long finId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;

	private Long financialYear;

	private BigDecimal revenue;

	private String businessActivities;

	private Long noBeneficiaryFarmers;

	private BigDecimal netProfit;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getFinId() {
		return finId;
	}

	public void setFinId(Long finId) {
		this.finId = finId;
	}

	

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public String getBusinessActivities() {
		return businessActivities;
	}

	public void setBusinessActivities(String businessActivities) {
		this.businessActivities = businessActivities;
	}

	public Long getNoBeneficiaryFarmers() {
		return noBeneficiaryFarmers;
	}

	public void setNoBeneficiaryFarmers(Long noBeneficiaryFarmers) {
		this.noBeneficiaryFarmers = noBeneficiaryFarmers;
	}

	public BigDecimal getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(BigDecimal netProfit) {
		this.netProfit = netProfit;
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
	
	

}
