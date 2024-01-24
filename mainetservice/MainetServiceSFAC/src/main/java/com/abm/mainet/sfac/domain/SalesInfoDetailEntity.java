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
@Table(name = "Tb_Sfac_Sale_Info_Detail")
public class SalesInfoDetailEntity implements Serializable {
	
	private static final long serialVersionUID = 5293370418238076071L;



	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "SALE_ID", nullable = false)
	private Long saleId;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Column(name="Commodity_Name")
	private String commodityName;
	
	@Column(name="Commodity_Quantity")
	private Long commodityQuantity;
	
	@Column(name="Unit")
	private Long unit;
	
	@Column(name="REVN_GEN")
	private BigDecimal revenueGenerated;
	
	@Column(name="MANDI_NAME")
	private String mandiName;
	
	@Column(name="MANDI_ADD")
	private String mandiAddress;
	
	@Column(name="NAME_IF_TRADER ")
	private String nameOfTrader;
	
	@Column(name="Commodity_Rate")
	private BigDecimal commodityRate;
	
	@Column(name="Commodity_Sold_Price")
	private BigDecimal commoditySoldPrice;
	
	
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

	

	public Long getSaleId() {
		return saleId;
	}



	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}




	public FPOProfileManagementMaster getFpoProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}



	public void setFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileMgmtMaster) {
		this.fpoProfileMgmtMaster = fpoProfileMgmtMaster;
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



	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_Sale_Info_Detail", "SALE_ID" };
	}

}
