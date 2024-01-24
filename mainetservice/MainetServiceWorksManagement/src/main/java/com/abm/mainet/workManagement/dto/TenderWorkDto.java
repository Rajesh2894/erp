package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hiren.poriya
 * @Since 10-Apr-2018
 */
public class TenderWorkDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long tndWId;
	private Long tndId;
	private Long workId;
	private BigDecimal workEstimateAmt;
	private Long orgId;
	private String status;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private boolean initiated;
	private String workName;
	private String workCode;
	private Long workType;
	private Long workAssignee;
	private String workAssigneeName;
	private Date workAssignedDate;

	private Long venderId;
	private Long venderClassId;
	private BigDecimal tenderSecAmt;
	private BigDecimal tenderFeeAmt;
	private Long tenderType;
	private BigDecimal tenderValue;
	private String vendorWorkPeriod;
	private Long vendorWorkPeriodUnit;
	private String tndLOANo;
	private Date tndLOADate;
	private BigDecimal tenderAmount;
	private Long tenderNoOfDayAggremnt;
	private BigDecimal tenderStampFee;
	private BigDecimal totalTenderAmount;
	private boolean tenderInitiated;
	private String vendorName;
	private String vendorAddress;
	private String projectName;
	private String tenderNo;
	private Date tenderDate;

	private String tndDate;

	private Long venderTypeId;
	private Long tenderDeptId;
	private String tndResolutionNo;
	private Date tndResolutionDate;
	private String action;
	private String tndLoaDateFormat;
	private String tndDateFormat;

	private TenderMasterDto masterDto = new TenderMasterDto();
	private Long contractId;
	private String workPlannedDate;
	private String workEndDate;
	private String unitDesc;
	private String tenderDateDesc;
	private String tenderTypeDesc;
	private String technicalOpenDateDesc;
	private String tenderIssueFromDateDesc;
	private String tenderIssueToDateDesc;
	private String venderClassDesc;

	private String amountInStringFormat;
	private Long tndValidityDay;

	private String tenderTypeCode;
	private Long projDeptId;
	private Long workCategory;
	private Long locId;
	private String tndEarAmntString;
	private String vendorPanNo;
	private Long vendorSubType;
	
	private Date tndSubmitDate;
	private String tndGSTApl;
	private Long tndCopntAuth;
	private String tndAwdResNo;
	private Date tndAwdResDate;
	private Long tndPGTypeId;
	private Long tndAuthDesgid;
	private BigDecimal tndPGRate;
	private BigDecimal tndPGAmount;
	private Long tndPGModeId;
	
	private Long tendTypePercent;

	private String workTypeCode;
	private Long prId;
	private Long expiryId;

	public String getTenderTypeCode() {
		return tenderTypeCode;
	}

	public void setTenderTypeCode(String tenderTypeCode) {
		this.tenderTypeCode = tenderTypeCode;
	}

	public String getTndDate() {
		return tndDate;
	}

	public void setTndDate(String tndDate) {
		this.tndDate = tndDate;
	}

	public Long getTndWId() {
		return tndWId;
	}

	public void setTndWId(Long tndWId) {
		this.tndWId = tndWId;
	}

	public Long getTndId() {
		return tndId;
	}

	public void setTndId(Long tndId) {
		this.tndId = tndId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public BigDecimal getWorkEstimateAmt() {
		return workEstimateAmt;
	}

	public void setWorkEstimateAmt(BigDecimal workEstimateAmt) {
		this.workEstimateAmt = workEstimateAmt;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public boolean isInitiated() {
		return initiated;
	}

	public void setInitiated(boolean initiated) {
		this.initiated = initiated;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public Long getWorkAssignee() {
		return workAssignee;
	}

	public void setWorkAssignee(Long workAssignee) {
		this.workAssignee = workAssignee;
	}

	public Date getWorkAssignedDate() {
		return workAssignedDate;
	}

	public void setWorkAssignedDate(Date workAssignedDate) {
		this.workAssignedDate = workAssignedDate;
	}

	public String getWorkAssigneeName() {
		return workAssigneeName;
	}

	public void setWorkAssigneeName(String workAssigneeName) {
		this.workAssigneeName = workAssigneeName;
	}

	public Long getVenderClassId() {
		return venderClassId;
	}

	public void setVenderClassId(Long venderClassId) {
		this.venderClassId = venderClassId;
	}

	public BigDecimal getTenderSecAmt() {
		return tenderSecAmt;
	}

	public void setTenderSecAmt(BigDecimal tenderSecAmt) {
		this.tenderSecAmt = tenderSecAmt;
	}

	public BigDecimal getTenderFeeAmt() {
		return tenderFeeAmt;
	}

	public void setTenderFeeAmt(BigDecimal tenderFeeAmt) {
		this.tenderFeeAmt = tenderFeeAmt;
	}

	public Long getTenderType() {
		return tenderType;
	}

	public void setTenderType(Long tenderType) {
		this.tenderType = tenderType;
	}

	public BigDecimal getTenderValue() {
		return tenderValue;
	}

	public void setTenderValue(BigDecimal tenderValue) {
		this.tenderValue = tenderValue;
	}

	public String getVendorWorkPeriod() {
		return vendorWorkPeriod;
	}

	public void setVendorWorkPeriod(String vendorWorkPeriod) {
		this.vendorWorkPeriod = vendorWorkPeriod;
	}

	public Long getVendorWorkPeriodUnit() {
		return vendorWorkPeriodUnit;
	}

	public void setVendorWorkPeriodUnit(Long vendorWorkPeriodUnit) {
		this.vendorWorkPeriodUnit = vendorWorkPeriodUnit;
	}

	public Long getVenderId() {
		return venderId;
	}

	public void setVenderId(Long venderId) {
		this.venderId = venderId;
	}

	public String getTndLOANo() {
		return tndLOANo;
	}

	public void setTndLOANo(String tndLOANo) {
		this.tndLOANo = tndLOANo;
	}

	public Date getTndLOADate() {
		return tndLOADate;
	}

	public void setTndLOADate(Date tndLOADate) {
		this.tndLOADate = tndLOADate;
	}

	public BigDecimal getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(BigDecimal tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public Long getTenderNoOfDayAggremnt() {
		return tenderNoOfDayAggremnt;
	}

	public void setTenderNoOfDayAggremnt(Long tenderNoOfDayAggremnt) {
		this.tenderNoOfDayAggremnt = tenderNoOfDayAggremnt;
	}

	public BigDecimal getTenderStampFee() {
		return tenderStampFee;
	}

	public void setTenderStampFee(BigDecimal tenderStampFee) {
		this.tenderStampFee = tenderStampFee;
	}

	public boolean isTenderInitiated() {
		return tenderInitiated;
	}

	public void setTenderInitiated(boolean tenderInitiated) {
		this.tenderInitiated = tenderInitiated;
	}

	public BigDecimal getTotalTenderAmount() {
		return totalTenderAmount;
	}

	public void setTotalTenderAmount(BigDecimal totalTenderAmount) {
		this.totalTenderAmount = totalTenderAmount;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTenderNo() {
		return tenderNo;
	}

	public void setTenderNo(String tenderNo) {
		this.tenderNo = tenderNo;
	}

	public Date getTenderDate() {
		return tenderDate;
	}

	public void setTenderDate(Date tenderDate) {
		this.tenderDate = tenderDate;
	}

    public TenderMasterDto getMasterDto() {
		return masterDto;
	}

	public void setMasterDto(TenderMasterDto masterDto) {
		this.masterDto = masterDto;
	}

	public Long getVenderTypeId() {
		return venderTypeId;
	}

	public void setVenderTypeId(Long venderTypeId) {
		this.venderTypeId = venderTypeId;
	}

	public Long getTenderDeptId() {
		return tenderDeptId;
	}

	public void setTenderDeptId(Long tenderDeptId) {
		this.tenderDeptId = tenderDeptId;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getTndResolutionNo() {
		return tndResolutionNo;
	}

	public void setTndResolutionNo(String tndResolutionNo) {
		this.tndResolutionNo = tndResolutionNo;
	}

	public Date getTndResolutionDate() {
		return tndResolutionDate;
	}

	public void setTndResolutionDate(Date tndResolutionDate) {
		this.tndResolutionDate = tndResolutionDate;
	}

	public String getWorkPlannedDate() {
		return workPlannedDate;
	}

	public void setWorkPlannedDate(String workPlannedDate) {
		this.workPlannedDate = workPlannedDate;
	}

	public String getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(String workEndDate) {
		this.workEndDate = workEndDate;
	}

	public Long getWorkType() {
		return workType;
	}

	public void setWorkType(Long workType) {
		this.workType = workType;
	}

	public String getUnitDesc() {
		return unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTndLoaDateFormat() {
		return tndLoaDateFormat;
	}

	public void setTndLoaDateFormat(String tndLoaDateFormat) {
		this.tndLoaDateFormat = tndLoaDateFormat;
	}

	public String getTndDateFormat() {
		return tndDateFormat;
	}

	public void setTndDateFormat(String tndDateFormat) {
		this.tndDateFormat = tndDateFormat;
	}

	public String getTenderDateDesc() {
		return tenderDateDesc;
	}

	public void setTenderDateDesc(String tenderDateDesc) {
		this.tenderDateDesc = tenderDateDesc;
	}

	public String getTenderTypeDesc() {
		return tenderTypeDesc;
	}

	public void setTenderTypeDesc(String tenderTypeDesc) {
		this.tenderTypeDesc = tenderTypeDesc;
	}

	public String getTechnicalOpenDateDesc() {
		return technicalOpenDateDesc;
	}

	public void setTechnicalOpenDateDesc(String technicalOpenDateDesc) {
		this.technicalOpenDateDesc = technicalOpenDateDesc;
	}

	public String getTenderIssueFromDateDesc() {
		return tenderIssueFromDateDesc;
	}

	public void setTenderIssueFromDateDesc(String tenderIssueFromDateDesc) {
		this.tenderIssueFromDateDesc = tenderIssueFromDateDesc;
	}

	public String getTenderIssueToDateDesc() {
		return tenderIssueToDateDesc;
	}

	public void setTenderIssueToDateDesc(String tenderIssueToDateDesc) {
		this.tenderIssueToDateDesc = tenderIssueToDateDesc;
	}

	public String getVenderClassDesc() {
		return venderClassDesc;
	}

	public void setVenderClassDesc(String venderClassDesc) {
		this.venderClassDesc = venderClassDesc;
	}

	public Long getProjDeptId() {
		return projDeptId;
	}

	public void setProjDeptId(Long projDeptId) {
		this.projDeptId = projDeptId;
	}

	public Long getWorkCategory() {
		return workCategory;
	}

	public void setWorkCategory(Long workCategory) {
		this.workCategory = workCategory;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getAmountInStringFormat() {
		return amountInStringFormat;
	}

	public void setAmountInStringFormat(String amountInStringFormat) {
		this.amountInStringFormat = amountInStringFormat;
	}

	public Long getTndValidityDay() {
		return tndValidityDay;
	}

	public void setTndValidityDay(Long tndValidityDay) {
		this.tndValidityDay = tndValidityDay;
	}

	public String getTndEarAmntString() {
		return tndEarAmntString;
	}

	public void setTndEarAmntString(String tndEarAmntString) {
		this.tndEarAmntString = tndEarAmntString;
	}

	public String getVendorPanNo() {
		return vendorPanNo;
	}

	public void setVendorPanNo(String vendorPanNo) {
		this.vendorPanNo = vendorPanNo;
	}

	public Long getVendorSubType() {
		return vendorSubType;
	}

	public void setVendorSubType(Long vendorSubType) {
		this.vendorSubType = vendorSubType;
	}

	public String getWorkTypeCode() {
		return workTypeCode;
	}

	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}

	public Date getTndSubmitDate() {
		return tndSubmitDate;
	}


	public void setTndSubmitDate(Date tndSubmitDate) {
		this.tndSubmitDate = tndSubmitDate;
	}

	public String getTndGSTApl() {
		return tndGSTApl;
	}

	public void setTndGSTApl(String tndGSTApl) {
		this.tndGSTApl = tndGSTApl;
	}

	public Long getTndCopntAuth() {
		return tndCopntAuth;
	}

	
	public void setTndCopntAuth(Long tndCopntAuth) {
		this.tndCopntAuth = tndCopntAuth;
	}

	
	public String getTndAwdResNo() {
		return tndAwdResNo;
	}

	public void setTndAwdResNo(String tndAwdResNo) {
		this.tndAwdResNo = tndAwdResNo;
	}

	
	public Date getTndAwdResDate() {
		return tndAwdResDate;
	}

	public void setTndAwdResDate(Date tndAwdResDate) {
		this.tndAwdResDate = tndAwdResDate;
	}

	public Long getTndPGTypeId() {
		return tndPGTypeId;
	}

	public void setTndPGTypeId(Long tndPGTypeId) {
		this.tndPGTypeId = tndPGTypeId;
	}

	public Long getTndAuthDesgid() {
		return tndAuthDesgid;
	}

	public void setTndAuthDesgid(Long tndAuthDesgid) {
		this.tndAuthDesgid = tndAuthDesgid;
	}

	public BigDecimal getTndPGRate() {
		return tndPGRate;
	}


	public void setTndPGRate(BigDecimal tndPGRate) {
		this.tndPGRate = tndPGRate;
	}

	public BigDecimal getTndPGAmount() {
		return tndPGAmount;
	}

	public void setTndPGAmount(BigDecimal tndPGAmount) {
		this.tndPGAmount = tndPGAmount;
	}

	public Long getTndPGModeId() {
		return tndPGModeId;
	}

	public void setTndPGModeId(Long tndPGModeId) {
		this.tndPGModeId = tndPGModeId;
	}

	public Long getTendTypePercent() {
		return tendTypePercent;
	}

	public void setTendTypePercent(Long tendTypePercent) {
		this.tendTypePercent = tendTypePercent;
	}

	public Long getPrId() {
		return prId;
	}

	public void setPrId(Long prId) {
		this.prId = prId;
	}

	public Long getExpiryId() {
		return expiryId;
	}

	public void setExpiryId(Long expiryId) {
		this.expiryId = expiryId;
	}
    
}
