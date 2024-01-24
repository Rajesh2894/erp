package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkDefinitionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long workId;
	private String workName;
	private Long projId;
	private String projName;
	private Date workStartDate;
	private Date workEndDate;
	private Long workType;
	private Long orgId;
	private Long deptId;
	private Long workProjPhase;
	private Long locIdSt;
	private Long locIdEn;
	private String workcode;
	private String workStatus;
	private Long createdBy;
	private Long updatedBy;
	private Date createdDate;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String projCode;
	private String workProjPhaseDesc;
	private String workTypeDesc;
	private BigDecimal workEstAmt;

	private String workAdminSacNo;
	private String workAdminSacBy;
	private Date workAdminSacDate;
	private String workTechSacNo;
	private String workTechSacBy;
	private String workTechSacdate;

	private String workSubStatus;

	private String startDateDesc;
	private String endDateDesc;
	private String createdDateDesc;
	private Boolean isFinalSanction;
	private boolean tenderInitiated;
	private String locationDesc;
	private Long projDeptId;
	private String workCompletionNo;
	private Date workCompletionDate;
	private String sanctionNumber;
	private String worksancDateDesc;
	private String projNameReg;
	private List<WorkDefinationYearDetDto> yearDtos = new ArrayList<>();
	private List<WorkDefinationAssetInfoDto> assetInfoDtos = new ArrayList<>();
	private List<WorkDefinitionSancDetDto> sanctionDetails = new ArrayList<>();
	private List<WorkCompletionRegisterDto> regDtos = new ArrayList<>();
	private List<WorkDefinationWardZoneDetailsDto> wardZoneDto = new ArrayList<>();
	private BigDecimal deviationPercent;
	private String raCode;
	private String agreementFromDate;
	private String agreementToDate;
	private String workOrderStartDate;
	private String contractorname;
	private String tenderpercent;
	private Long workCategory;
	private BigDecimal workOverHeadAmt;
	private BigDecimal estimateWithoutOverheadAmt;
	private Long workFlowId;
	private Long souceOffund;
	private Long SchemeName;
	private String proposalNo;
	private String latitude;
	private String longitude;

	String dePartmentName;

	private Long workSubType;
	
	

	public WorkDefinitionDto(Long workId, String workcode) {
		super();
		this.workId = workId;
		this.workcode = workcode;
	}

	public WorkDefinitionDto(Long workId, String workcode, BigDecimal workEstAmt ) {
		super();
		this.workId = workId;
		this.workcode = workcode;
		this.workEstAmt = workEstAmt;		
	}

	public WorkDefinitionDto() {	
	}
	
	
	public Long getWorkSubType() {
		return workSubType;
	}

	public void setWorkSubType(Long workSubType) {
		this.workSubType = workSubType;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public Date getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}

	public Date getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(Date workEndDate) {
		this.workEndDate = workEndDate;
	}

	public Long getWorkType() {
		return workType;
	}

	public void setWorkType(Long workType) {
		this.workType = workType;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getWorkProjPhase() {
		return workProjPhase;
	}

	public void setWorkProjPhase(Long workProjPhase) {
		this.workProjPhase = workProjPhase;
	}

	public Long getLocIdSt() {
		return locIdSt;
	}

	public void setLocIdSt(Long locIdSt) {
		this.locIdSt = locIdSt;
	}

	public String getWorkcode() {
		return workcode;
	}

	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public List<WorkDefinationYearDetDto> getYearDtos() {
		return yearDtos;
	}

	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public BigDecimal getWorkEstAmt() {
		return workEstAmt;
	}

	public void setWorkEstAmt(BigDecimal workEstAmt) {
		this.workEstAmt = workEstAmt;
	}

	public void setYearDtos(List<WorkDefinationYearDetDto> yearDtos) {
		this.yearDtos = yearDtos;
	}

	public List<WorkDefinationAssetInfoDto> getAssetInfoDtos() {
		return assetInfoDtos;
	}

	public void setAssetInfoDtos(List<WorkDefinationAssetInfoDto> assetInfoDtos) {
		this.assetInfoDtos = assetInfoDtos;
	}

	public Long getLocIdEn() {
		return locIdEn;
	}

	public void setLocIdEn(Long locIdEn) {
		this.locIdEn = locIdEn;
	}

	public String getWorkProjPhaseDesc() {
		return workProjPhaseDesc;
	}

	public void setWorkProjPhaseDesc(String workProjPhaseDesc) {
		this.workProjPhaseDesc = workProjPhaseDesc;
	}

	public String getWorkTypeDesc() {
		return workTypeDesc;
	}

	public void setWorkTypeDesc(String workTypeDesc) {
		this.workTypeDesc = workTypeDesc;
	}

	public String getWorkSubStatus() {
		return workSubStatus;
	}

	public void setWorkSubStatus(String workSubStatus) {
		this.workSubStatus = workSubStatus;
	}

	public String getWorkAdminSacNo() {
		return workAdminSacNo;
	}

	public void setWorkAdminSacNo(String workAdminSacNo) {
		this.workAdminSacNo = workAdminSacNo;
	}

	public String getWorkAdminSacBy() {
		return workAdminSacBy;
	}

	public void setWorkAdminSacBy(String workAdminSacBy) {
		this.workAdminSacBy = workAdminSacBy;
	}

	public Date getWorkAdminSacDate() {
		return workAdminSacDate;
	}

	public void setWorkAdminSacDate(Date workAdminSacDate) {
		this.workAdminSacDate = workAdminSacDate;
	}

	public String getWorkTechSacNo() {
		return workTechSacNo;
	}

	public void setWorkTechSacNo(String workTechSacNo) {
		this.workTechSacNo = workTechSacNo;
	}

	public String getWorkTechSacBy() {
		return workTechSacBy;
	}

	public void setWorkTechSacBy(String workTechSacBy) {
		this.workTechSacBy = workTechSacBy;
	}

	public String getWorkTechSacdate() {
		return workTechSacdate;
	}

	public void setWorkTechSacdate(String workTechSacdate) {
		this.workTechSacdate = workTechSacdate;
	}

	public String getStartDateDesc() {
		return startDateDesc;
	}

	public void setStartDateDesc(String startDateDesc) {
		this.startDateDesc = startDateDesc;
	}

	public String getEndDateDesc() {
		return endDateDesc;
	}

	public void setEndDateDesc(String endDateDesc) {
		this.endDateDesc = endDateDesc;
	}

	public Boolean getIsFinalSanction() {
		return isFinalSanction;
	}

	public void setIsFinalSanction(Boolean isFinalSanction) {
		this.isFinalSanction = isFinalSanction;
	}

	public String getCreatedDateDesc() {
		return createdDateDesc;
	}

	public void setCreatedDateDesc(String createdDateDesc) {
		this.createdDateDesc = createdDateDesc;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public boolean isTenderInitiated() {
		return tenderInitiated;
	}

	public void setTenderInitiated(boolean tenderInitiated) {
		this.tenderInitiated = tenderInitiated;
	}

	public Long getProjDeptId() {
		return projDeptId;
	}

	public void setProjDeptId(Long projDeptId) {
		this.projDeptId = projDeptId;
	}

	public List<WorkDefinitionSancDetDto> getSanctionDetails() {
		return sanctionDetails;
	}

	public void setSanctionDetails(List<WorkDefinitionSancDetDto> sanctionDetails) {
		this.sanctionDetails = sanctionDetails;
	}

	public String getSanctionNumber() {
		return sanctionNumber;
	}

	public void setSanctionNumber(String sanctionNumber) {
		this.sanctionNumber = sanctionNumber;
	}

	public String getWorkCompletionNo() {
		return workCompletionNo;
	}

	public void setWorkCompletionNo(String workCompletionNo) {
		this.workCompletionNo = workCompletionNo;
	}

	public Date getWorkCompletionDate() {
		return workCompletionDate;
	}

	public void setWorkCompletionDate(Date workCompletionDate) {
		this.workCompletionDate = workCompletionDate;
	}

	public String getWorksancDateDesc() {
		return worksancDateDesc;
	}

	public void setWorksancDateDesc(String worksancDateDesc) {
		this.worksancDateDesc = worksancDateDesc;
	}

	public BigDecimal getDeviationPercent() {
		return deviationPercent;
	}

	public void setDeviationPercent(BigDecimal deviationPercent) {
		this.deviationPercent = deviationPercent;
	}

	public String getRaCode() {
		return raCode;
	}

	public void setRaCode(String raCode) {
		this.raCode = raCode;
	}

	public List<WorkCompletionRegisterDto> getRegDtos() {
		return regDtos;
	}

	public void setRegDtos(List<WorkCompletionRegisterDto> regDtos) {
		this.regDtos = regDtos;
	}

	public String getAgreementFromDate() {
		return agreementFromDate;
	}

	public void setAgreementFromDate(String agreementFromDate) {
		this.agreementFromDate = agreementFromDate;
	}

	public String getAgreementToDate() {
		return agreementToDate;
	}

	public void setAgreementToDate(String agreementToDate) {
		this.agreementToDate = agreementToDate;
	}

	public String getWorkOrderStartDate() {
		return workOrderStartDate;
	}

	public void setWorkOrderStartDate(String workOrderStartDate) {
		this.workOrderStartDate = workOrderStartDate;
	}

	public String getContractorname() {
		return contractorname;
	}

	public void setContractorname(String contractorname) {
		this.contractorname = contractorname;
	}

	public String getTenderpercent() {
		return tenderpercent;
	}

	public void setTenderpercent(String tenderpercent) {
		this.tenderpercent = tenderpercent;
	}

	public Long getWorkCategory() {
		return workCategory;
	}

	public void setWorkCategory(Long workCategory) {
		this.workCategory = workCategory;
	}

	public BigDecimal getWorkOverHeadAmt() {
		return workOverHeadAmt;
	}

	public void setWorkOverHeadAmt(BigDecimal workOverHeadAmt) {
		this.workOverHeadAmt = workOverHeadAmt;
	}

	public BigDecimal getEstimateWithoutOverheadAmt() {
		if (this.workOverHeadAmt != null && workEstAmt != null) {
			this.estimateWithoutOverheadAmt = workEstAmt.subtract(workOverHeadAmt);
		} else {
			estimateWithoutOverheadAmt = workEstAmt;
		}
		return estimateWithoutOverheadAmt;
	}

	public void setEstimateWithoutOverheadAmt(BigDecimal estimateWithoutOverheadAmt) {
		this.estimateWithoutOverheadAmt = estimateWithoutOverheadAmt;
	}

	public Long getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(Long workFlowId) {
		this.workFlowId = workFlowId;
	}

	public Long getSouceOffund() {
		return souceOffund;
	}

	public void setSouceOffund(Long souceOffund) {
		this.souceOffund = souceOffund;
	}

	public List<WorkDefinationWardZoneDetailsDto> getWardZoneDto() {
		return wardZoneDto;
	}

	public void setWardZoneDto(List<WorkDefinationWardZoneDetailsDto> wardZoneDto) {
		this.wardZoneDto = wardZoneDto;
	}

	public Long getSchemeName() {
		return SchemeName;
	}

	public void setSchemeName(Long schemeName) {
		SchemeName = schemeName;
	}

	public String getDePartmentName() {
		return dePartmentName;
	}

	public void setDePartmentName(String dePartmentName) {
		this.dePartmentName = dePartmentName;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getProjNameReg() {
		return projNameReg;
	}

	public void setProjNameReg(String projNameReg) {
		this.projNameReg = projNameReg;
	}

}
