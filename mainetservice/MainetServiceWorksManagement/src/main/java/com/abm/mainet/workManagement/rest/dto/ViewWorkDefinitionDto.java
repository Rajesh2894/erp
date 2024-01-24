package com.abm.mainet.workManagement.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ViewWorkDefinitionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String workName;
	@JsonIgnore
	private Long projId;
	private String projName;
	private Date workStartDate;
	private Date workEndDate;
	
	private Long workType;
	@JsonIgnore
	private Long workProjPhase;	
	private String locStart;
	private String locEnd;
	private String workcode;
	private String workStatus;
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
	private Boolean isFinalSanction;
	private boolean tenderInitiated;
	private String locationDesc;
	private String projDept;
	private String workCompletionNo;
	private Date workCompletionDate;
	private String sanctionNumber;
	private String worksancDateDesc;
	private List<ViewWorkDefinationYearDetDto> yearDtos = new ArrayList<>();
	private List<ViewWorkDefinationAssetInfoDto> assetInfoDtos = new ArrayList<>();
	private List<ViewWorkDefinitionSancDetDto> sanctionDetails = new ArrayList<>();
	private List<ViewWorkCompletionRegisterDto> regDtos = new ArrayList<>();
	private List<ViewWorkDefinationWardZoneDetailsDto> wardZoneDto = new ArrayList<>();
	private BigDecimal deviationPercent;
	private String raCode;
	private String agreementFromDate;
	private String agreementToDate;
	private String workOrderStartDate;
	private String contractorname;
	private String tenderpercent;
	private String workCategory;
	private BigDecimal workOverHeadAmt;
	private BigDecimal estimateWithoutOverheadAmt;
	@JsonIgnore
	private Long workFlowId;
	private String souceOffund;
	private Long SchemeName;
	private String proposalNo;

	String dePartmentName;

	private Long workSubType;

	public Long getWorkSubType() {
		return workSubType;
	}

	public void setWorkSubType(Long workSubType) {
		this.workSubType = workSubType;
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

	public Long getWorkProjPhase() {
		return workProjPhase;
	}

	public void setWorkProjPhase(Long workProjPhase) {
		this.workProjPhase = workProjPhase;
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


	public List<ViewWorkDefinationYearDetDto> getYearDtos() {
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

	public void setYearDtos(List<ViewWorkDefinationYearDetDto> yearDtos) {
		this.yearDtos = yearDtos;
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

	
	public String getProjDept() {
		return projDept;
	}

	public void setProjDept(String projDept) {
		this.projDept = projDept;
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

	public String getWorkCategory() {
		return workCategory;
	}

	public void setWorkCategory(String workCategory) {
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

	public String getSouceOffund() {
		return souceOffund;
	}

	public void setSouceOffund(String souceOffund) {
		this.souceOffund = souceOffund;
	}

	public List<ViewWorkDefinationAssetInfoDto> getAssetInfoDtos() {
		return assetInfoDtos;
	}

	public void setAssetInfoDtos(List<ViewWorkDefinationAssetInfoDto> assetInfoDtos) {
		this.assetInfoDtos = assetInfoDtos;
	}

	public List<ViewWorkDefinitionSancDetDto> getSanctionDetails() {
		return sanctionDetails;
	}

	public void setSanctionDetails(List<ViewWorkDefinitionSancDetDto> sanctionDetails) {
		this.sanctionDetails = sanctionDetails;
	}

	public List<ViewWorkCompletionRegisterDto> getRegDtos() {
		return regDtos;
	}

	public void setRegDtos(List<ViewWorkCompletionRegisterDto> regDtos) {
		this.regDtos = regDtos;
	}

	public List<ViewWorkDefinationWardZoneDetailsDto> getWardZoneDto() {
		return wardZoneDto;
	}

	public void setWardZoneDto(List<ViewWorkDefinationWardZoneDetailsDto> wardZoneDto) {
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

	public String getLocStart() {
		return locStart;
	}

	public void setLocStart(String locStart) {
		this.locStart = locStart;
	}

	public String getLocEnd() {
		return locEnd;
	}

	public void setLocEnd(String locEnd) {
		this.locEnd = locEnd;
	}

}
