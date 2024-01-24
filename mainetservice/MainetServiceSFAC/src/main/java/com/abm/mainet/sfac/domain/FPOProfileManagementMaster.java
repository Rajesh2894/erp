/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */

@Entity
@Table(name = "Tb_Sfac_Fpo_Profile_Mgmt_Mast")
public class FPOProfileManagementMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6925900306527732284L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FPM_ID", nullable = false)
	private Long fpmId;

	@OneToOne
	@JoinColumn(name = "FPO_ID", referencedColumnName = "FPO_ID")
	private FPOMasterEntity fpoMasterEntity;

	@Column(name = "OVERALL_TURNOVER")
	private BigDecimal overallTurnOver;

	@Column(name = "ADD_SHARES_ISSUED")
	private Long additionalSharesIssued;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_ISSUED")
	private Date dateIssued;

	@Column(name = "COMMODITY_NAME")
	private String commodityName;

	@Column(name = "COMMODITY_QUANTITY")
	private Long commodityQuanity;

	@Column(name = "LIVE_STOCK_NAME")
	private String liveStockName;

	@Column(name = "LIVE_STOCK_QUANTITY")
	private Long liveStockQuanity;

	@Column(name = "BLOCK_DE_ALLOCATED_CBBO")
	private String blockDeAllocatedCbbo;

	@Column(name = "OLD_BLOCK")
	private String oldBlock;

	@Column(name = "DPR_REC_DT")
	private Date dprRecDt;

	@Column(name = "DPR_REVIEWER")
	private String dprReviewer;

	@Column(name = "DPR_SCORE")
	private Long dprScore;

	@Column(name = "DPR_REV_SUBM_DT")
	private Date dprRevSubmDt;

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

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<LicenseInformationDetEntity> licenseInformationDetEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<CreditInformationDetEntity> creditInformationDetEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<FinancialInformationDetEntity> financialInformationDetEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<EquityInformationDetEntity> equityInformationDetEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<FPOProfileFarmerSummaryDetEntity> farmerSummaryDetEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<ManagementCostDetEntity> managementCostDetEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<CreditGrantDetEntity> creditGrantDetEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<FPOProfileTrainingDetEntity> fpoProfileTrainingDetEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<StrorageInfomartionDetailEntity> strorageInfomartionDetailEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<CustomHiringCenterInfoDetailEntity> customHiringCenterInfoDetailEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<CustomHiringServiceInfoDetailEntity> customHiringServiceInfoDetailEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<ProductionInfoDetailEntity> productionInfoDetailEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<SalesInfoDetailEntity> salesInfoDetailEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<SubsidiesInfoDetailEntity> subsidiesInfoDetailEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<PreHarveshInfraInfoDetailEntity> preHarveshInfraInfoDetailEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<PostHarvestInfraInfoDetailEntity> postHarvestInfraInfoDetailEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<TransportVehicleInfoDetailEntity> vehicleInfoDetailEntities = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<DPRInfoDetEntity> dprInfoDetEntities  = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<EquipmentInfoDetailEntity>  equipmentInfoDetailEntities  = new ArrayList<>();

	
	
	

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<MarketLinkageInfoDetailEntity> marketLinkageInfoDetailEntities = new ArrayList<>();



	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoProfileMgmtMaster", cascade = CascadeType.ALL)
	private List<BusinessPlanInfoDetailEntity> businessPlanInfoDetailEntities = new ArrayList<>();

	/**
	 * @return the fpmId
	 */
	public Long getFpmId() {
		return fpmId;
	}

	/**
	 * @param fpmId the fpmId to set
	 */
	public void setFpmId(Long fpmId) {
		this.fpmId = fpmId;
	}

	/**
	 * @return the overallTurnOver
	 */
	public BigDecimal getOverallTurnOver() {
		return overallTurnOver;
	}

	/**
	 * @param overallTurnOver the overallTurnOver to set
	 */
	public void setOverallTurnOver(BigDecimal overallTurnOver) {
		this.overallTurnOver = overallTurnOver;
	}

	/**
	 * @return the additionalSharesIssued
	 */
	public Long getAdditionalSharesIssued() {
		return additionalSharesIssued;
	}

	/**
	 * @param additionalSharesIssued the additionalSharesIssued to set
	 */
	public void setAdditionalSharesIssued(Long additionalSharesIssued) {
		this.additionalSharesIssued = additionalSharesIssued;
	}

	/**
	 * @return the dateIssued
	 */
	public Date getDateIssued() {
		return dateIssued;
	}

	/**
	 * @param dateIssued the dateIssued to set
	 */
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public List<LicenseInformationDetEntity> getLicenseInformationDetEntities() {
		return licenseInformationDetEntities;
	}

	public void setLicenseInformationDetEntities(List<LicenseInformationDetEntity> licenseInformationDetEntities) {
		this.licenseInformationDetEntities = licenseInformationDetEntities;
	}

	public List<StrorageInfomartionDetailEntity> getStrorageInfomartionDetailEntities() {
		return strorageInfomartionDetailEntities;
	}

	public void setStrorageInfomartionDetailEntities(
			List<StrorageInfomartionDetailEntity> strorageInfomartionDetailEntities) {
		this.strorageInfomartionDetailEntities = strorageInfomartionDetailEntities;
	}

	public List<CustomHiringCenterInfoDetailEntity> getCustomHiringCenterInfoDetailEntities() {
		return customHiringCenterInfoDetailEntities;
	}

	public void setCustomHiringCenterInfoDetailEntities(
			List<CustomHiringCenterInfoDetailEntity> customHiringCenterInfoDetailEntities) {
		this.customHiringCenterInfoDetailEntities = customHiringCenterInfoDetailEntities;
	}

	public List<CustomHiringServiceInfoDetailEntity> getCustomHiringServiceInfoDetailEntities() {
		return customHiringServiceInfoDetailEntities;
	}

	public void setCustomHiringServiceInfoDetailEntities(
			List<CustomHiringServiceInfoDetailEntity> customHiringServiceInfoDetailEntities) {
		this.customHiringServiceInfoDetailEntities = customHiringServiceInfoDetailEntities;
	}

	public List<ProductionInfoDetailEntity> getProductionInfoDetailEntities() {
		return productionInfoDetailEntities;
	}

	public void setProductionInfoDetailEntities(List<ProductionInfoDetailEntity> productionInfoDetailEntities) {
		this.productionInfoDetailEntities = productionInfoDetailEntities;
	}

	public List<SalesInfoDetailEntity> getSalesInfoDetailEntities() {
		return salesInfoDetailEntities;
	}

	public void setSalesInfoDetailEntities(List<SalesInfoDetailEntity> salesInfoDetailEntities) {
		this.salesInfoDetailEntities = salesInfoDetailEntities;
	}

	public List<SubsidiesInfoDetailEntity> getSubsidiesInfoDetailEntities() {
		return subsidiesInfoDetailEntities;
	}

	public void setSubsidiesInfoDetailEntities(List<SubsidiesInfoDetailEntity> subsidiesInfoDetailEntities) {
		this.subsidiesInfoDetailEntities = subsidiesInfoDetailEntities;
	}

	public List<PreHarveshInfraInfoDetailEntity> getPreHarveshInfraInfoDetailEntities() {
		return preHarveshInfraInfoDetailEntities;
	}

	public void setPreHarveshInfraInfoDetailEntities(
			List<PreHarveshInfraInfoDetailEntity> preHarveshInfraInfoDetailEntities) {
		this.preHarveshInfraInfoDetailEntities = preHarveshInfraInfoDetailEntities;
	}

	public List<PostHarvestInfraInfoDetailEntity> getPostHarvestInfraInfoDetailEntities() {
		return postHarvestInfraInfoDetailEntities;
	}

	public void setPostHarvestInfraInfoDetailEntities(
			List<PostHarvestInfraInfoDetailEntity> postHarvestInfraInfoDetailEntities) {
		this.postHarvestInfraInfoDetailEntities = postHarvestInfraInfoDetailEntities;
	}

	public List<TransportVehicleInfoDetailEntity> getVehicleInfoDetailEntities() {
		return vehicleInfoDetailEntities;
	}

	public void setVehicleInfoDetailEntities(List<TransportVehicleInfoDetailEntity> vehicleInfoDetailEntities) {
		this.vehicleInfoDetailEntities = vehicleInfoDetailEntities;
	}

	

	public List<MarketLinkageInfoDetailEntity> getMarketLinkageInfoDetailEntities() {
		return marketLinkageInfoDetailEntities;
	}

	public void setMarketLinkageInfoDetailEntities(
			List<MarketLinkageInfoDetailEntity> marketLinkageInfoDetailEntities) {
		this.marketLinkageInfoDetailEntities = marketLinkageInfoDetailEntities;
	}

	public List<BusinessPlanInfoDetailEntity> getBusinessPlanInfoDetailEntities() {
		return businessPlanInfoDetailEntities;
	}

	public void setBusinessPlanInfoDetailEntities(List<BusinessPlanInfoDetailEntity> businessPlanInfoDetailEntities) {
		this.businessPlanInfoDetailEntities = businessPlanInfoDetailEntities;
	}

	public List<CreditInformationDetEntity> getCreditInformationDetEntities() {
		return creditInformationDetEntities;
	}

	public List<FinancialInformationDetEntity> getFinancialInformationDetEntities() {
		return financialInformationDetEntities;
	}

	public void setFinancialInformationDetEntities(
			List<FinancialInformationDetEntity> financialInformationDetEntities) {
		this.financialInformationDetEntities = financialInformationDetEntities;
	}

	public List<EquityInformationDetEntity> getEquityInformationDetEntities() {
		return equityInformationDetEntities;
	}

	public void setEquityInformationDetEntities(List<EquityInformationDetEntity> equityInformationDetEntities) {
		this.equityInformationDetEntities = equityInformationDetEntities;
	}

	public List<FPOProfileFarmerSummaryDetEntity> getFarmerSummaryDetEntities() {
		return farmerSummaryDetEntities;
	}

	public void setFarmerSummaryDetEntities(List<FPOProfileFarmerSummaryDetEntity> farmerSummaryDetEntities) {
		this.farmerSummaryDetEntities = farmerSummaryDetEntities;
	}

	public List<ManagementCostDetEntity> getManagementCostDetEntities() {
		return managementCostDetEntities;
	}

	public void setManagementCostDetEntities(List<ManagementCostDetEntity> managementCostDetEntities) {
		this.managementCostDetEntities = managementCostDetEntities;
	}

	public List<CreditGrantDetEntity> getCreditGrantDetEntities() {
		return creditGrantDetEntities;
	}

	public void setCreditGrantDetEntities(List<CreditGrantDetEntity> creditGrantDetEntities) {
		this.creditGrantDetEntities = creditGrantDetEntities;
	}

	public List<FPOProfileTrainingDetEntity> getFpoProfileTrainingDetEntities() {
		return fpoProfileTrainingDetEntities;
	}

	public void setFpoProfileTrainingDetEntities(List<FPOProfileTrainingDetEntity> fpoProfileTrainingDetEntities) {
		this.fpoProfileTrainingDetEntities = fpoProfileTrainingDetEntities;
	}

	public void setCreditInformationDetEntities(List<CreditInformationDetEntity> creditInformationDetEntities) {
		this.creditInformationDetEntities = creditInformationDetEntities;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_Fpo_Profile_Mgmt_Mast", "FPM_ID" };
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Long getCommodityQuanity() {
		return commodityQuanity;
	}

	public void setCommodityQuanity(Long commodityQuanity) {
		this.commodityQuanity = commodityQuanity;
	}

	public String getLiveStockName() {
		return liveStockName;
	}

	public void setLiveStockName(String liveStockName) {
		this.liveStockName = liveStockName;
	}

	public Long getLiveStockQuanity() {
		return liveStockQuanity;
	}

	public void setLiveStockQuanity(Long liveStockQuanity) {
		this.liveStockQuanity = liveStockQuanity;
	}

	public FPOMasterEntity getFpoMasterEntity() {
		return fpoMasterEntity;
	}

	public void setFpoMasterEntity(FPOMasterEntity fpoMasterEntity) {
		this.fpoMasterEntity = fpoMasterEntity;
	}

	/**
	 * @return the blockDeAllocatedCbbo
	 */
	public String getBlockDeAllocatedCbbo() {
		return blockDeAllocatedCbbo;
	}

	/**
	 * @param blockDeAllocatedCbbo the blockDeAllocatedCbbo to set
	 */
	public void setBlockDeAllocatedCbbo(String blockDeAllocatedCbbo) {
		this.blockDeAllocatedCbbo = blockDeAllocatedCbbo;
	}

	/**
	 * @return the oldBlock
	 */
	public String getOldBlock() {
		return oldBlock;
	}

	/**
	 * @param oldBlock the oldBlock to set
	 */
	public void setOldBlock(String oldBlock) {
		this.oldBlock = oldBlock;
	}

	/**
	 * @return the dprRecDt
	 */
	public Date getDprRecDt() {
		return dprRecDt;
	}

	/**
	 * @param dprRecDt the dprRecDt to set
	 */
	public void setDprRecDt(Date dprRecDt) {
		this.dprRecDt = dprRecDt;
	}

	/**
	 * @return the dprReviewer
	 */
	public String getDprReviewer() {
		return dprReviewer;
	}

	/**
	 * @param dprReviewer the dprReviewer to set
	 */
	public void setDprReviewer(String dprReviewer) {
		this.dprReviewer = dprReviewer;
	}

	/**
	 * @return the dprScore
	 */
	public Long getDprScore() {
		return dprScore;
	}

	/**
	 * @param dprScore the dprScore to set
	 */
	public void setDprScore(Long dprScore) {
		this.dprScore = dprScore;
	}

	/**
	 * @return the dprRevSubmDt
	 */
	public Date getDprRevSubmDt() {
		return dprRevSubmDt;
	}

	/**
	 * @param dprRevSubmDt the dprRevSubmDt to set
	 */
	public void setDprRevSubmDt(Date dprRevSubmDt) {
		this.dprRevSubmDt = dprRevSubmDt;
	}

	public List<DPRInfoDetEntity> getDprInfoDetEntities() {
		return dprInfoDetEntities;
	}

	public void setDprInfoDetEntities(List<DPRInfoDetEntity> dprInfoDetEntities) {
		this.dprInfoDetEntities = dprInfoDetEntities;
	}

	public List<EquipmentInfoDetailEntity> getEquipmentInfoDetailEntities() {
		return equipmentInfoDetailEntities;
	}

	public void setEquipmentInfoDetailEntities(List<EquipmentInfoDetailEntity> equipmentInfoDetailEntities) {
		this.equipmentInfoDetailEntities = equipmentInfoDetailEntities;
	}
	
	

}
