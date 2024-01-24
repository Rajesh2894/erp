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
@Table(name = "Tb_Sfac_Market_Linkage_Info_Detail")
public class MarketLinkageInfoDetailEntity implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3402051810606265936L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ML_ID", nullable = false)
	private Long marketLinkageId;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Column(name="Commodity_Name")
	private String commodityName;
	
	@Column(name="Market_Place")
	private String marketPlace;
	
	@Column(name="Commodity_Rate")
	private BigDecimal commodityRate;
	
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
	
	

	
	public Long getMarketLinkageId() {
		return marketLinkageId;
	}




	public void setMarketLinkageId(Long marketLinkageId) {
		this.marketLinkageId = marketLinkageId;
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




	



	public String getMarketPlace() {
		return marketPlace;
	}




	public void setMarketPlace(String marketPlace) {
		this.marketPlace = marketPlace;
	}




	public BigDecimal getCommodityRate() {
		return commodityRate;
	}




	public void setCommodityRate(BigDecimal commodityRate) {
		this.commodityRate = commodityRate;
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
		return new String[] { "SFAC", "Tb_Sfac_Market_Linkage_Info_Detail", "ML_ID" };
	}

}
