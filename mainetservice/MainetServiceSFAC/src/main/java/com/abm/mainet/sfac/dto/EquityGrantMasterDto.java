package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class EquityGrantMasterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2309151987969083240L;
	
	
	private Long egId;

	@JsonIgnore
	FPOMasterDto fpoMasterDto;
	
	@JsonIgnore
	List<EquityGrantDetailDto> equityGrantDetailDto;
	
	@JsonIgnore
	List<EquityGrantDetailDto> equityGrantDetailDtoBOM;
	
	@JsonIgnore
	List<EquityGrantFunctionalCommitteeDetailDto> equityGrantFunctionalCommitteeDetailDtos;
	
	@JsonIgnore
	List<EquityGrantShareHoldingDetailDto> equityGrantShareHoldingDetailDtos;
	
	 private List<DocumentDetailsVO> documentList;
	 
	 private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	
	private Long appNumber;
	
	private String authRemark;
	

	
	
	private Long fpoName;
	
	private String fpoNameString;

	private Long state;

	private Long district;

	private String CorrespondenceAdd;

	private String mobileNo;

	private String emailId;
	
	private String contactNo;

	private String registrationNo;

	private Date registrationDate;

	private String businessofFPC;

	private Long noofShareholderMem;

	private Long noOfSMLShareholderMemb;

	private BigDecimal authorisedCapital;

	private BigDecimal paidUpCapital;

	private BigDecimal amountofEquityGrant;

	private BigDecimal maxIndShareholdMem;

	private BigDecimal MaxShareholdInsMem;

	private String riName;

	private String riContactNo;

	private String riAddress;

	private String riEmailId;

	private String appStatus;

	private Long noOfDirectors;

	private Long womenDirectors;

	private Date lastYrBoardMeetDt;

	private String NoofFuncCommittees;

	private String RolesRespofBoardDirec;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private String bankName;
	
	private Long bankId;
	
	private String branchEmail;
	
	private Long modeOfBoardFormation;
	
	

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBranchEmail() {
		return branchEmail;
	}

	public void setBranchEmail(String branchEmail) {
		this.branchEmail = branchEmail;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getEgId() {
		return egId;
	}

	public void setEgId(Long egId) {
		this.egId = egId;
	}

	public Long getFpoName() {
		return fpoName;
	}

	public void setFpoName(Long fpoName) {
		this.fpoName = fpoName;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Long getDistrict() {
		return district;
	}

	public void setDistrict(Long district) {
		this.district = district;
	}

	public String getCorrespondenceAdd() {
		return CorrespondenceAdd;
	}

	public void setCorrespondenceAdd(String correspondenceAdd) {
		CorrespondenceAdd = correspondenceAdd;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getBusinessofFPC() {
		return businessofFPC;
	}

	public void setBusinessofFPC(String businessofFPC) {
		this.businessofFPC = businessofFPC;
	}

	public Long getNoofShareholderMem() {
		return noofShareholderMem;
	}

	public void setNoofShareholderMem(Long noofShareholderMem) {
		this.noofShareholderMem = noofShareholderMem;
	}

	public Long getNoOfSMLShareholderMemb() {
		return noOfSMLShareholderMemb;
	}

	public void setNoOfSMLShareholderMemb(Long noOfSMLShareholderMemb) {
		this.noOfSMLShareholderMemb = noOfSMLShareholderMemb;
	}

	public BigDecimal getAuthorisedCapital() {
		return authorisedCapital;
	}

	public void setAuthorisedCapital(BigDecimal authorisedCapital) {
		this.authorisedCapital = authorisedCapital;
	}

	public BigDecimal getPaidUpCapital() {
		return paidUpCapital;
	}

	public void setPaidUpCapital(BigDecimal paidUpCapital) {
		this.paidUpCapital = paidUpCapital;
	}

	public BigDecimal getAmountofEquityGrant() {
		return amountofEquityGrant;
	}

	public void setAmountofEquityGrant(BigDecimal amountofEquityGrant) {
		this.amountofEquityGrant = amountofEquityGrant;
	}

	public BigDecimal getMaxIndShareholdMem() {
		return maxIndShareholdMem;
	}

	public void setMaxIndShareholdMem(BigDecimal maxIndShareholdMem) {
		this.maxIndShareholdMem = maxIndShareholdMem;
	}

	public BigDecimal getMaxShareholdInsMem() {
		return MaxShareholdInsMem;
	}

	public void setMaxShareholdInsMem(BigDecimal maxShareholdInsMem) {
		MaxShareholdInsMem = maxShareholdInsMem;
	}

	public String getRiName() {
		return riName;
	}

	public void setRiName(String riName) {
		this.riName = riName;
	}

	public String getRiContactNo() {
		return riContactNo;
	}

	public void setRiContactNo(String riContactNo) {
		this.riContactNo = riContactNo;
	}

	public String getRiAddress() {
		return riAddress;
	}

	public void setRiAddress(String riAddress) {
		this.riAddress = riAddress;
	}

	public String getRiEmailId() {
		return riEmailId;
	}

	public void setRiEmailId(String riEmailId) {
		this.riEmailId = riEmailId;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public Long getNoOfDirectors() {
		return noOfDirectors;
	}

	public void setNoOfDirectors(Long noOfDirectors) {
		this.noOfDirectors = noOfDirectors;
	}

	public Long getWomenDirectors() {
		return womenDirectors;
	}

	public void setWomenDirectors(Long womenDirectors) {
		this.womenDirectors = womenDirectors;
	}

	public Date getLastYrBoardMeetDt() {
		return lastYrBoardMeetDt;
	}

	public void setLastYrBoardMeetDt(Date lastYrBoardMeetDt) {
		this.lastYrBoardMeetDt = lastYrBoardMeetDt;
	}

	public String getNoofFuncCommittees() {
		return NoofFuncCommittees;
	}

	public void setNoofFuncCommittees(String noofFuncCommittees) {
		NoofFuncCommittees = noofFuncCommittees;
	}

	public String getRolesRespofBoardDirec() {
		return RolesRespofBoardDirec;
	}

	public void setRolesRespofBoardDirec(String rolesRespofBoardDirec) {
		RolesRespofBoardDirec = rolesRespofBoardDirec;
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

	public String getFpoNameString() {
		return fpoNameString;
	}

	public void setFpoNameString(String fpoNameString) {
		this.fpoNameString = fpoNameString;
	}

	public FPOMasterDto getFpoMasterDto() {
		return fpoMasterDto;
	}

	public void setFpoMasterDto(FPOMasterDto fpoMasterDto) {
		this.fpoMasterDto = fpoMasterDto;
	}

	public List<EquityGrantDetailDto> getEquityGrantDetailDto() {
		return equityGrantDetailDto;
	}

	public void setEquityGrantDetailDto(List<EquityGrantDetailDto> equityGrantDetailDto) {
		this.equityGrantDetailDto = equityGrantDetailDto;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	



	public Long getAppNumber() {
		return appNumber;
	}

	public void setAppNumber(Long appNumber) {
		this.appNumber = appNumber;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public List<EquityGrantFunctionalCommitteeDetailDto> getEquityGrantFunctionalCommitteeDetailDtos() {
		return equityGrantFunctionalCommitteeDetailDtos;
	}

	public void setEquityGrantFunctionalCommitteeDetailDtos(
			List<EquityGrantFunctionalCommitteeDetailDto> equityGrantFunctionalCommitteeDetailDtos) {
		this.equityGrantFunctionalCommitteeDetailDtos = equityGrantFunctionalCommitteeDetailDtos;
	}

	public List<EquityGrantShareHoldingDetailDto> getEquityGrantShareHoldingDetailDtos() {
		return equityGrantShareHoldingDetailDtos;
	}

	public void setEquityGrantShareHoldingDetailDtos(
			List<EquityGrantShareHoldingDetailDto> equityGrantShareHoldingDetailDtos) {
		this.equityGrantShareHoldingDetailDtos = equityGrantShareHoldingDetailDtos;
	}

	public List<EquityGrantDetailDto> getEquityGrantDetailDtoBOM() {
		return equityGrantDetailDtoBOM;
	}

	public void setEquityGrantDetailDtoBOM(List<EquityGrantDetailDto> equityGrantDetailDtoBOM) {
		this.equityGrantDetailDtoBOM = equityGrantDetailDtoBOM;
	}

	public Long getModeOfBoardFormation() {
		return modeOfBoardFormation;
	}

	public void setModeOfBoardFormation(Long modeOfBoardFormation) {
		this.modeOfBoardFormation = modeOfBoardFormation;
	}

	
}
