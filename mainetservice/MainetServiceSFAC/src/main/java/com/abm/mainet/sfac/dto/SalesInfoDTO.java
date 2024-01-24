package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SalesInfoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2143589827528063393L;
	
	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private Long saleId;

	private String commodityName;
	
	private Long commodityQuantity;
	
	private Long unit;
	
	private BigDecimal commodityRate;
	
	private BigDecimal commoditySoldPrice;
	
	private BigDecimal revenueGenerated;
	
	private String mandiName;
	
	private String mandiAddress;
	
	private String nameOfTrader;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Long getCommodityQuantity() {
		return commodityQuantity;
	}

	public void setCommodityQuantity(Long commodityQuantity) {
		this.commodityQuantity = commodityQuantity;
	}

	

	public Long getUnit() {
		return unit;
	}

	public void setUnit(Long unit) {
		this.unit = unit;
	}

	public BigDecimal getCommodityRate() {
		return commodityRate;
	}

	public void setCommodityRate(BigDecimal commodityRate) {
		this.commodityRate = commodityRate;
	}

	public BigDecimal getCommoditySoldPrice() {
		return commoditySoldPrice;
	}

	public void setCommoditySoldPrice(BigDecimal commoditySoldPrice) {
		this.commoditySoldPrice = commoditySoldPrice;
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

	public BigDecimal getRevenueGenerated() {
		return revenueGenerated;
	}

	public void setRevenueGenerated(BigDecimal revenueGenerated) {
		this.revenueGenerated = revenueGenerated;
	}

	public String getMandiName() {
		return mandiName;
	}

	public void setMandiName(String mandiName) {
		this.mandiName = mandiName;
	}

	public String getMandiAddress() {
		return mandiAddress;
	}

	public void setMandiAddress(String mandiAddress) {
		this.mandiAddress = mandiAddress;
	}

	public String getNameOfTrader() {
		return nameOfTrader;
	}

	public void setNameOfTrader(String nameOfTrader) {
		this.nameOfTrader = nameOfTrader;
	}
	
	

	
}
