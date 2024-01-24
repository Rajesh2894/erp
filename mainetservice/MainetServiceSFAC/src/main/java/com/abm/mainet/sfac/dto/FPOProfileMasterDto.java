package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

/**
 * @author priyesh.chourasia
 *
 */
/**
 * @author priyesh.chourasia
 *
 */
public class FPOProfileMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5629255263861411744L;
	
	private Long fpmId;
	
	private String fpoName;
	
	private Long fpoId;
	
	private String fpoRegNo;
	
	private Long iaId;

	private BigDecimal overallTurnOver;

	private Long additionalSharesIssued;

	private Date dateIssued;
	
	private String commodityName;
	
	private Long commodityQuanity;
	
	private String liveStockName;
	
	private Long liveStockQuanity;
	
	private String blockDeAllocatedCbbo;

	private String oldBlock;

	private Date dprRecDt;

	private String dprReviewer;

	private Long dprScore;

	private Date dprRevSubmDt;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private List<LicenseInformationDTO> licenseInformationDetEntities = new ArrayList<>();
	
	private List<CreditLinkageInformationDTO> creditInformationDetEntities = new ArrayList<>();
	
	private List<FinancialInformationDto> financialInformationDto = new ArrayList<>();
	
	private List<EquityInformationDetDto> equityInformationDetDto = new ArrayList<>();
	
	private List<FPOProfileFarmerSummaryDto> farmerSummaryDto = new ArrayList<>();
	
	private List<ManagementCostDetailDto> managementCostDetailDto  = new ArrayList<>();
	
	private List<CreditGrantDetailDto> creditGrantDetailDto = new ArrayList<>();
	
	private List<FPOProfileTrainingDetailDto> fpoProfileTrainingDetailDto = new ArrayList<>();
	
	private List<StrorageInfomartionDTO> strorageInfomartionDTOs = new ArrayList<>();
	
	private List<CustomHiringCenterInfoDTO>  customHiringCenterInfoDTOs = new ArrayList<>();
	
	private List<CustomHiringServiceInfoDTO> customHiringServiceInfoDTOs = new ArrayList<>();
	
	private List<ProductionInfoDTO> productionInfoDTOs = new ArrayList<>();
	
	private List<SalesInfoDTO> salesInfoDTOs = new ArrayList<>();
	
	private List<SubsidiesInfoDTO> subsidiesInfoDTOs = new ArrayList<>();
	
	private List<PreHarveshInfraInfoDTO> preHarveshInfraInfoDTOs = new ArrayList<>();
	
	private List<PostHarvestInfraInfoDTO> postHarvestInfraInfoDTOs = new ArrayList<>();
	
	private List<TransportVehicleInfoDTO> transpostVehicleInfoDTOs  = new ArrayList<>();
	
	private List<AuditedBalanceSheetInfoDTO> auditedBalanceSheetInfoDetailEntities = new ArrayList<>();
	
	private List<MarketLinkageInfoDTO>  marketLinkageInfoDTOs = new ArrayList<>();
	
	private List<DPRInfoDTO> dprInfoDTOs = new ArrayList<>();
	
	private List<EquipmentInfoDto> equipmentInfoDtos = new ArrayList<>();
	
	
	
	public Long getIaId() {
		return iaId;
	}

	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	private List<BusinessPlanInfoDTO> businessPlanInfoDTOs = new ArrayList<>();

	public Long getFpmId() {
		return fpmId;
	}

	public void setFpmId(Long fpmId) {
		this.fpmId = fpmId;
	}

	public BigDecimal getOverallTurnOver() {
		return overallTurnOver;
	}

	public void setOverallTurnOver(BigDecimal overallTurnOver) {
		this.overallTurnOver = overallTurnOver;
	}

	public Long getAdditionalSharesIssued() {
		return additionalSharesIssued;
	}

	public void setAdditionalSharesIssued(Long additionalSharesIssued) {
		this.additionalSharesIssued = additionalSharesIssued;
	}

	public Date getDateIssued() {
		return dateIssued;
	}

	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
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

	public List<LicenseInformationDTO> getLicenseInformationDetEntities() {
		return licenseInformationDetEntities;
	}

	public void setLicenseInformationDetEntities(List<LicenseInformationDTO> licenseInformationDetEntities) {
		this.licenseInformationDetEntities = licenseInformationDetEntities;
	}

	

	/*public List<AuditedBalanceSheetInfoDTO> getAuditedBalanceSheetInfoDetailEntities() {
		return auditedBalanceSheetInfoDetailEntities;
	}

	public void setAuditedBalanceSheetInfoDetailEntities(
			List<AuditedBalanceSheetInfoDTO> auditedBalanceSheetInfoDetailEntities) {
		this.auditedBalanceSheetInfoDetailEntities = auditedBalanceSheetInfoDetailEntities;
	}*/

	

	public List<CreditLinkageInformationDTO> getCreditInformationDetEntities() {
		return creditInformationDetEntities;
	}

	public void setCreditInformationDetEntities(List<CreditLinkageInformationDTO> creditInformationDetEntities) {
		this.creditInformationDetEntities = creditInformationDetEntities;
	}

	

	public List<StrorageInfomartionDTO> getStrorageInfomartionDTOs() {
		return strorageInfomartionDTOs;
	}

	public void setStrorageInfomartionDTOs(List<StrorageInfomartionDTO> strorageInfomartionDTOs) {
		this.strorageInfomartionDTOs = strorageInfomartionDTOs;
	}

	public List<CustomHiringCenterInfoDTO> getCustomHiringCenterInfoDTOs() {
		return customHiringCenterInfoDTOs;
	}

	public void setCustomHiringCenterInfoDTOs(List<CustomHiringCenterInfoDTO> customHiringCenterInfoDTOs) {
		this.customHiringCenterInfoDTOs = customHiringCenterInfoDTOs;
	}

	public List<CustomHiringServiceInfoDTO> getCustomHiringServiceInfoDTOs() {
		return customHiringServiceInfoDTOs;
	}

	public void setCustomHiringServiceInfoDTOs(List<CustomHiringServiceInfoDTO> customHiringServiceInfoDTOs) {
		this.customHiringServiceInfoDTOs = customHiringServiceInfoDTOs;
	}

	public List<ProductionInfoDTO> getProductionInfoDTOs() {
		return productionInfoDTOs;
	}

	public void setProductionInfoDTOs(List<ProductionInfoDTO> productionInfoDTOs) {
		this.productionInfoDTOs = productionInfoDTOs;
	}

	public List<SalesInfoDTO> getSalesInfoDTOs() {
		return salesInfoDTOs;
	}

	public void setSalesInfoDTOs(List<SalesInfoDTO> salesInfoDTOs) {
		this.salesInfoDTOs = salesInfoDTOs;
	}

	public List<SubsidiesInfoDTO> getSubsidiesInfoDTOs() {
		return subsidiesInfoDTOs;
	}

	public void setSubsidiesInfoDTOs(List<SubsidiesInfoDTO> subsidiesInfoDTOs) {
		this.subsidiesInfoDTOs = subsidiesInfoDTOs;
	}

	public List<PreHarveshInfraInfoDTO> getPreHarveshInfraInfoDTOs() {
		return preHarveshInfraInfoDTOs;
	}

	public void setPreHarveshInfraInfoDTOs(List<PreHarveshInfraInfoDTO> preHarveshInfraInfoDTOs) {
		this.preHarveshInfraInfoDTOs = preHarveshInfraInfoDTOs;
	}

	public List<PostHarvestInfraInfoDTO> getPostHarvestInfraInfoDTOs() {
		return postHarvestInfraInfoDTOs;
	}

	public void setPostHarvestInfraInfoDTOs(List<PostHarvestInfraInfoDTO> postHarvestInfraInfoDTOs) {
		this.postHarvestInfraInfoDTOs = postHarvestInfraInfoDTOs;
	}

	public List<TransportVehicleInfoDTO> getTranspostVehicleInfoDTOs() {
		return transpostVehicleInfoDTOs;
	}

	public void setTranspostVehicleInfoDTOs(List<TransportVehicleInfoDTO> transpostVehicleInfoDTOs) {
		this.transpostVehicleInfoDTOs = transpostVehicleInfoDTOs;
	}

	public List<MarketLinkageInfoDTO> getMarketLinkageInfoDTOs() {
		return marketLinkageInfoDTOs;
	}

	public void setMarketLinkageInfoDTOs(List<MarketLinkageInfoDTO> marketLinkageInfoDTOs) {
		this.marketLinkageInfoDTOs = marketLinkageInfoDTOs;
	}

	public List<BusinessPlanInfoDTO> getBusinessPlanInfoDTOs() {
		return businessPlanInfoDTOs;
	}

	public void setBusinessPlanInfoDTOs(List<BusinessPlanInfoDTO> businessPlanInfoDTOs) {
		this.businessPlanInfoDTOs = businessPlanInfoDTOs;
	}

	public List<FinancialInformationDto> getFinancialInformationDto() {
		return financialInformationDto;
	}

	public void setFinancialInformationDto(List<FinancialInformationDto> financialInformationDto) {
		this.financialInformationDto = financialInformationDto;
	}


	
	public List<EquityInformationDetDto> getEquityInformationDetDto() {
		return equityInformationDetDto;
	}

	public void setEquityInformationDetDto(List<EquityInformationDetDto> equityInformationDetDto) {
		this.equityInformationDetDto = equityInformationDetDto;
	}

	public List<FPOProfileFarmerSummaryDto> getFarmerSummaryDto() {
		return farmerSummaryDto;
	}

	public void setFarmerSummaryDto(List<FPOProfileFarmerSummaryDto> farmerSummaryDto) {
		this.farmerSummaryDto = farmerSummaryDto;
	}

	public List<ManagementCostDetailDto> getManagementCostDetailDto() {
		return managementCostDetailDto;
	}

	public void setManagementCostDetailDto(List<ManagementCostDetailDto> managementCostDetailDto) {
		this.managementCostDetailDto = managementCostDetailDto;
	}

	public List<CreditGrantDetailDto> getCreditGrantDetailDto() {
		return creditGrantDetailDto;
	}

	public void setCreditGrantDetailDto(List<CreditGrantDetailDto> creditGrantDetailDto) {
		this.creditGrantDetailDto = creditGrantDetailDto;
	}

	public List<FPOProfileTrainingDetailDto> getFpoProfileTrainingDetailDto() {
		return fpoProfileTrainingDetailDto;
	}

	public void setFpoProfileTrainingDetailDto(List<FPOProfileTrainingDetailDto> fpoProfileTrainingDetailDto) {
		this.fpoProfileTrainingDetailDto = fpoProfileTrainingDetailDto;
	}

	public Long getFpoId() {
		return fpoId;
	}

	public void setFpoId(Long fpoId) {
		this.fpoId = fpoId;
	}

	public String getFpoRegNo() {
		return fpoRegNo;
	}

	public void setFpoRegNo(String fpoRegNo) {
		this.fpoRegNo = fpoRegNo;
	}

	public String getFpoName() {
		return fpoName;
	}

	public void setFpoName(String fpoName) {
		this.fpoName = fpoName;
	}

	public List<AuditedBalanceSheetInfoDTO> getAuditedBalanceSheetInfoDetailEntities() {
		return auditedBalanceSheetInfoDetailEntities;
	}

	public void setAuditedBalanceSheetInfoDetailEntities(
			List<AuditedBalanceSheetInfoDTO> auditedBalanceSheetInfoDetailEntities) {
		this.auditedBalanceSheetInfoDetailEntities = auditedBalanceSheetInfoDetailEntities;
	}

	public String getBlockDeAllocatedCbbo() {
		return blockDeAllocatedCbbo;
	}

	public void setBlockDeAllocatedCbbo(String blockDeAllocatedCbbo) {
		this.blockDeAllocatedCbbo = blockDeAllocatedCbbo;
	}

	public String getOldBlock() {
		return oldBlock;
	}

	public void setOldBlock(String oldBlock) {
		this.oldBlock = oldBlock;
	}

	public Date getDprRecDt() {
		return dprRecDt;
	}

	public void setDprRecDt(Date dprRecDt) {
		this.dprRecDt = dprRecDt;
	}

	public String getDprReviewer() {
		return dprReviewer;
	}

	public void setDprReviewer(String dprReviewer) {
		this.dprReviewer = dprReviewer;
	}

	public Long getDprScore() {
		return dprScore;
	}

	public void setDprScore(Long dprScore) {
		this.dprScore = dprScore;
	}

	public Date getDprRevSubmDt() {
		return dprRevSubmDt;
	}

	public void setDprRevSubmDt(Date dprRevSubmDt) {
		this.dprRevSubmDt = dprRevSubmDt;
	}

	public List<DPRInfoDTO> getDprInfoDTOs() {
		return dprInfoDTOs;
	}

	public void setDprInfoDTOs(List<DPRInfoDTO> dprInfoDTOs) {
		this.dprInfoDTOs = dprInfoDTOs;
	}

	public List<EquipmentInfoDto> getEquipmentInfoDtos() {
		return equipmentInfoDtos;
	}

	public void setEquipmentInfoDtos(List<EquipmentInfoDto> equipmentInfoDtos) {
		this.equipmentInfoDtos = equipmentInfoDtos;
	}
	
	

}
