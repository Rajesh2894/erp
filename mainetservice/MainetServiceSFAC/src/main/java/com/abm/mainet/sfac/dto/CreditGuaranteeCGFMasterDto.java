package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreditGuaranteeCGFMasterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7699024378798908619L;


	private Long cgfID;

	private String nameOfApplication;

	private Long fpoId;

	private List<DocumentDetailsVO> documentList;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	@JsonIgnore
	private FPOMasterDto fpoMasterDto;

	private String fpoName;

	private Long constitution;

	private String registrationNo;

	private Date registrationDate;

	private String placeOfRegistration;

	private Long regAct;

	private String fpoTanNo;

	private String fpoPanNo;

	private String gstin;

	private Long businessOfFPO;

	private Long fpoAgriBusiness;

	private String forwardLinkageDetails;

	private String backwardLinkageDetails;

	private Long regionOFFpo;

	private Long noOfLandlessFarmer;

	private Long noOfSmallFarmer;

	private Long noOfBigFarmer;

	private Long noOfFPOPlainMember;

	private Long noofShareholderMem;

	private Long noOfNorthEastFarmer;

	private Long noOfMarFarmer;



	private Long noOfWomenFarmer;

	private Long noOfSTsFarmer;

	private Long noOfSCFarmer;

	private String fpoAddress;

	private Long state;

	private Long district;

	private Long block;

	private Long fpoPinCode;

	private BigDecimal latitude;

	private BigDecimal logitude;

	private Long isBusinessAddressSame;

	private String businessFPOAddress;

	private Long bsdb1;

	private Long bsdb2;

	private Long bsdb3;

	private Long businessFpoPinCode;

	private BigDecimal businessLatitude;

	private BigDecimal businessLogitude;

	private Long fpoStatus;

	private Long fpoAppliedCGFOS;

	private String etCGPAN;

	private Long typeOfCreditFacility;

	private Long whichCreditGuarantee;

	private BigDecimal totalSactionAmount;

	private Date validityOfCreditGuarantee;

	private String promoterName;

	private String promoterLandline;

	private String promoterMobileNo;

	private String promoterEmailId;

	private String cfCustomerId;

	private Long cfTypeOfCreditFacility;

	private Long LandingAssTool;

	private Long purposeOfCF;

	private String wctlAccountNo;

	private BigDecimal wctlSactionAmount;

	private Date wctlSactionDate;

	private Date wctlEndDateInterstMORATORIUM;

	private Date wctlEndDatePrincipleMORATORIUM;

	private Date wctlDueDateOfLastInstallment;

	private BigDecimal wctlInterestRate;

	private Long isWCTLLoanDis;

	private Long wctlCfExtend;

	private BigDecimal wctlOutstandingAmount;

	private Long wctlPurposeOfCF;

	private Long ccPurposeOfCF;

	private String ccAccountNo;

	private BigDecimal ccSactionAmount;

	private Date ccSactionDate;

	private BigDecimal ccDrawAmount;

	private Date ccEndDateOfMORATORIUM;

	private Date ccEndDateOfLoanValidity;

	private BigDecimal ccInterestRate;

	private Long isCCLoanDis;

	private BigDecimal outstandingAmount;

	private BigDecimal inputAmount;

	private BigDecimal marketingAmount;

	private BigDecimal processingAmount;

	private BigDecimal ohterAmount;

	private BigDecimal totalAmount;

	private BigDecimal wctlTermLoan;

	private BigDecimal wctlPromoterMargin;

	private BigDecimal wctlUnsecuredLoan;

	private BigDecimal wctlAnyOtherSource;

	private BigDecimal wclrInputAmount;

	private BigDecimal wclrMarketingAmount;

	private BigDecimal wclrProcessingAmount;

	private BigDecimal wclrOtherAmount;

	private BigDecimal wclrTotalAmount;

	private BigDecimal ccLimit;

	private BigDecimal ccPromoterMargin;

	private BigDecimal ccUnsecuredLoan;

	private BigDecimal ccAnyOtherSource;

	private String typeOfSecurity;

	private Long natureOfSecurity;

	private Long cfSactionWithoutSecurity;

	private BigDecimal valueOfSecurity;

	private String underTaking;

	private Long applicationNumber;

	private String appStatus;

	private String authRemark;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getCgfID() {
		return cgfID;
	}

	public void setCgfID(Long cgfID) {
		this.cgfID = cgfID;
	}

	public String getNameOfApplication() {
		return nameOfApplication;
	}

	public void setNameOfApplication(String nameOfApplication) {
		this.nameOfApplication = nameOfApplication;
	}

	public Long getFpoId() {
		return fpoId;
	}

	public void setFpoId(Long fpoId) {
		this.fpoId = fpoId;
	}

	public String getFpoName() {
		return fpoName;
	}

	public void setFpoName(String fpoName) {
		this.fpoName = fpoName;
	}

	public Long getConstitution() {
		return constitution;
	}

	public void setConstitution(Long constitution) {
		this.constitution = constitution;
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

	public String getPlaceOfRegistration() {
		return placeOfRegistration;
	}

	public void setPlaceOfRegistration(String placeOfRegistration) {
		this.placeOfRegistration = placeOfRegistration;
	}

	public Long getRegAct() {
		return regAct;
	}

	public void setRegAct(Long regAct) {
		this.regAct = regAct;
	}

	public String getFpoTanNo() {
		return fpoTanNo;
	}

	public void setFpoTanNo(String fpoTanNo) {
		this.fpoTanNo = fpoTanNo;
	}

	public String getFpoPanNo() {
		return fpoPanNo;
	}

	public void setFpoPanNo(String fpoPanNo) {
		this.fpoPanNo = fpoPanNo;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public Long getBusinessOfFPO() {
		return businessOfFPO;
	}

	public void setBusinessOfFPO(Long businessOfFPO) {
		this.businessOfFPO = businessOfFPO;
	}

	public Long getFpoAgriBusiness() {
		return fpoAgriBusiness;
	}

	public void setFpoAgriBusiness(Long fpoAgriBusiness) {
		this.fpoAgriBusiness = fpoAgriBusiness;
	}

	public String getForwardLinkageDetails() {
		return forwardLinkageDetails;
	}

	public void setForwardLinkageDetails(String forwardLinkageDetails) {
		this.forwardLinkageDetails = forwardLinkageDetails;
	}

	public String getBackwardLinkageDetails() {
		return backwardLinkageDetails;
	}

	public void setBackwardLinkageDetails(String backwardLinkageDetails) {
		this.backwardLinkageDetails = backwardLinkageDetails;
	}

	public Long getRegionOFFpo() {
		return regionOFFpo;
	}

	public void setRegionOFFpo(Long regionOFFpo) {
		this.regionOFFpo = regionOFFpo;
	}

	public Long getNoOfLandlessFarmer() {
		return noOfLandlessFarmer;
	}

	public void setNoOfLandlessFarmer(Long noOfLandlessFarmer) {
		this.noOfLandlessFarmer = noOfLandlessFarmer;
	}

	public Long getNoOfSmallFarmer() {
		return noOfSmallFarmer;
	}

	public void setNoOfSmallFarmer(Long noOfSmallFarmer) {
		this.noOfSmallFarmer = noOfSmallFarmer;
	}

	public Long getNoOfBigFarmer() {
		return noOfBigFarmer;
	}

	public void setNoOfBigFarmer(Long noOfBigFarmer) {
		this.noOfBigFarmer = noOfBigFarmer;
	}

	public Long getNoOfFPOPlainMember() {
		return noOfFPOPlainMember;
	}

	public void setNoOfFPOPlainMember(Long noOfFPOPlainMember) {
		this.noOfFPOPlainMember = noOfFPOPlainMember;
	}

	public Long getNoofShareholderMem() {
		return noofShareholderMem;
	}

	public void setNoofShareholderMem(Long noofShareholderMem) {
		this.noofShareholderMem = noofShareholderMem;
	}

	public Long getNoOfNorthEastFarmer() {
		return noOfNorthEastFarmer;
	}

	public void setNoOfNorthEastFarmer(Long noOfNorthEastFarmer) {
		this.noOfNorthEastFarmer = noOfNorthEastFarmer;
	}


	public Long getNoOfWomenFarmer() {
		return noOfWomenFarmer;
	}

	public void setNoOfWomenFarmer(Long noOfWomenFarmer) {
		this.noOfWomenFarmer = noOfWomenFarmer;
	}

	public Long getNoOfSTsFarmer() {
		return noOfSTsFarmer;
	}

	public void setNoOfSTsFarmer(Long noOfSTsFarmer) {
		this.noOfSTsFarmer = noOfSTsFarmer;
	}

	public Long getNoOfSCFarmer() {
		return noOfSCFarmer;
	}

	public void setNoOfSCFarmer(Long noOfSCFarmer) {
		this.noOfSCFarmer = noOfSCFarmer;
	}

	public String getFpoAddress() {
		return fpoAddress;
	}

	public void setFpoAddress(String fpoAddress) {
		this.fpoAddress = fpoAddress;
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

	public Long getBlock() {
		return block;
	}

	public void setBlock(Long block) {
		this.block = block;
	}

	public Long getFpoPinCode() {
		return fpoPinCode;
	}

	public void setFpoPinCode(Long fpoPinCode) {
		this.fpoPinCode = fpoPinCode;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLogitude() {
		return logitude;
	}

	public void setLogitude(BigDecimal logitude) {
		this.logitude = logitude;
	}

	public Long getIsBusinessAddressSame() {
		return isBusinessAddressSame;
	}

	public void setIsBusinessAddressSame(Long isBusinessAddressSame) {
		this.isBusinessAddressSame = isBusinessAddressSame;
	}

	public String getBusinessFPOAddress() {
		return businessFPOAddress;
	}

	public void setBusinessFPOAddress(String businessFPOAddress) {
		this.businessFPOAddress = businessFPOAddress;
	}

	

	

	public Long getBsdb1() {
		return bsdb1;
	}

	public void setBsdb1(Long bsdb1) {
		this.bsdb1 = bsdb1;
	}

	public Long getBsdb2() {
		return bsdb2;
	}

	public void setBsdb2(Long bsdb2) {
		this.bsdb2 = bsdb2;
	}

	public Long getBsdb3() {
		return bsdb3;
	}

	public void setBsdb3(Long bsdb3) {
		this.bsdb3 = bsdb3;
	}

	public Long getBusinessFpoPinCode() {
		return businessFpoPinCode;
	}

	public void setBusinessFpoPinCode(Long businessFpoPinCode) {
		this.businessFpoPinCode = businessFpoPinCode;
	}

	public BigDecimal getBusinessLatitude() {
		return businessLatitude;
	}

	public void setBusinessLatitude(BigDecimal businessLatitude) {
		this.businessLatitude = businessLatitude;
	}

	public BigDecimal getBusinessLogitude() {
		return businessLogitude;
	}

	public void setBusinessLogitude(BigDecimal businessLogitude) {
		this.businessLogitude = businessLogitude;
	}

	public Long getFpoStatus() {
		return fpoStatus;
	}

	public void setFpoStatus(Long fpoStatus) {
		this.fpoStatus = fpoStatus;
	}

	public Long getFpoAppliedCGFOS() {
		return fpoAppliedCGFOS;
	}

	public void setFpoAppliedCGFOS(Long fpoAppliedCGFOS) {
		this.fpoAppliedCGFOS = fpoAppliedCGFOS;
	}

	public String getEtCGPAN() {
		return etCGPAN;
	}

	public void setEtCGPAN(String etCGPAN) {
		this.etCGPAN = etCGPAN;
	}

	public Long getTypeOfCreditFacility() {
		return typeOfCreditFacility;
	}

	public void setTypeOfCreditFacility(Long typeOfCreditFacility) {
		this.typeOfCreditFacility = typeOfCreditFacility;
	}

	public Long getWhichCreditGuarantee() {
		return whichCreditGuarantee;
	}

	public void setWhichCreditGuarantee(Long whichCreditGuarantee) {
		this.whichCreditGuarantee = whichCreditGuarantee;
	}

	public BigDecimal getTotalSactionAmount() {
		return totalSactionAmount;
	}

	public void setTotalSactionAmount(BigDecimal totalSactionAmount) {
		this.totalSactionAmount = totalSactionAmount;
	}

	public Date getValidityOfCreditGuarantee() {
		return validityOfCreditGuarantee;
	}

	public void setValidityOfCreditGuarantee(Date validityOfCreditGuarantee) {
		this.validityOfCreditGuarantee = validityOfCreditGuarantee;
	}

	public String getPromoterName() {
		return promoterName;
	}

	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}

	public String getPromoterLandline() {
		return promoterLandline;
	}

	public void setPromoterLandline(String promoterLandline) {
		this.promoterLandline = promoterLandline;
	}

	public String getPromoterMobileNo() {
		return promoterMobileNo;
	}

	public void setPromoterMobileNo(String promoterMobileNo) {
		this.promoterMobileNo = promoterMobileNo;
	}

	public String getPromoterEmailId() {
		return promoterEmailId;
	}

	public void setPromoterEmailId(String promoterEmailId) {
		this.promoterEmailId = promoterEmailId;
	}

	public String getCfCustomerId() {
		return cfCustomerId;
	}

	public void setCfCustomerId(String cfCustomerId) {
		this.cfCustomerId = cfCustomerId;
	}

	public Long getCfTypeOfCreditFacility() {
		return cfTypeOfCreditFacility;
	}

	public void setCfTypeOfCreditFacility(Long cfTypeOfCreditFacility) {
		this.cfTypeOfCreditFacility = cfTypeOfCreditFacility;
	}

	public Long getLandingAssTool() {
		return LandingAssTool;
	}

	public void setLandingAssTool(Long landingAssTool) {
		LandingAssTool = landingAssTool;
	}

	public Long getPurposeOfCF() {
		return purposeOfCF;
	}

	public void setPurposeOfCF(Long purposeOfCF) {
		this.purposeOfCF = purposeOfCF;
	}

	public String getWctlAccountNo() {
		return wctlAccountNo;
	}

	public void setWctlAccountNo(String wctlAccountNo) {
		this.wctlAccountNo = wctlAccountNo;
	}

	public BigDecimal getWctlSactionAmount() {
		return wctlSactionAmount;
	}

	public void setWctlSactionAmount(BigDecimal wctlSactionAmount) {
		this.wctlSactionAmount = wctlSactionAmount;
	}

	public Date getWctlSactionDate() {
		return wctlSactionDate;
	}

	public void setWctlSactionDate(Date wctlSactionDate) {
		this.wctlSactionDate = wctlSactionDate;
	}

	public Date getWctlEndDateInterstMORATORIUM() {
		return wctlEndDateInterstMORATORIUM;
	}

	public void setWctlEndDateInterstMORATORIUM(Date wctlEndDateInterstMORATORIUM) {
		this.wctlEndDateInterstMORATORIUM = wctlEndDateInterstMORATORIUM;
	}

	public Date getWctlEndDatePrincipleMORATORIUM() {
		return wctlEndDatePrincipleMORATORIUM;
	}

	public void setWctlEndDatePrincipleMORATORIUM(Date wctlEndDatePrincipleMORATORIUM) {
		this.wctlEndDatePrincipleMORATORIUM = wctlEndDatePrincipleMORATORIUM;
	}

	public Date getWctlDueDateOfLastInstallment() {
		return wctlDueDateOfLastInstallment;
	}

	public void setWctlDueDateOfLastInstallment(Date wctlDueDateOfLastInstallment) {
		this.wctlDueDateOfLastInstallment = wctlDueDateOfLastInstallment;
	}

	public BigDecimal getWctlInterestRate() {
		return wctlInterestRate;
	}

	public void setWctlInterestRate(BigDecimal wctlInterestRate) {
		this.wctlInterestRate = wctlInterestRate;
	}

	public Long getIsWCTLLoanDis() {
		return isWCTLLoanDis;
	}

	public void setIsWCTLLoanDis(Long isWCTLLoanDis) {
		this.isWCTLLoanDis = isWCTLLoanDis;
	}

	public Long getWctlCfExtend() {
		return wctlCfExtend;
	}

	public void setWctlCfExtend(Long wctlCfExtend) {
		this.wctlCfExtend = wctlCfExtend;
	}

	public BigDecimal getWctlOutstandingAmount() {
		return wctlOutstandingAmount;
	}

	public void setWctlOutstandingAmount(BigDecimal wctlOutstandingAmount) {
		this.wctlOutstandingAmount = wctlOutstandingAmount;
	}

	public Long getWctlPurposeOfCF() {
		return wctlPurposeOfCF;
	}

	public void setWctlPurposeOfCF(Long wctlPurposeOfCF) {
		this.wctlPurposeOfCF = wctlPurposeOfCF;
	}

	public Long getCcPurposeOfCF() {
		return ccPurposeOfCF;
	}

	public void setCcPurposeOfCF(Long ccPurposeOfCF) {
		this.ccPurposeOfCF = ccPurposeOfCF;
	}

	public String getCcAccountNo() {
		return ccAccountNo;
	}

	public void setCcAccountNo(String ccAccountNo) {
		this.ccAccountNo = ccAccountNo;
	}

	public BigDecimal getCcSactionAmount() {
		return ccSactionAmount;
	}

	public void setCcSactionAmount(BigDecimal ccSactionAmount) {
		this.ccSactionAmount = ccSactionAmount;
	}

	public Date getCcSactionDate() {
		return ccSactionDate;
	}

	public void setCcSactionDate(Date ccSactionDate) {
		this.ccSactionDate = ccSactionDate;
	}

	public BigDecimal getCcDrawAmount() {
		return ccDrawAmount;
	}

	public void setCcDrawAmount(BigDecimal ccDrawAmount) {
		this.ccDrawAmount = ccDrawAmount;
	}

	public Date getCcEndDateOfMORATORIUM() {
		return ccEndDateOfMORATORIUM;
	}

	public void setCcEndDateOfMORATORIUM(Date ccEndDateOfMORATORIUM) {
		this.ccEndDateOfMORATORIUM = ccEndDateOfMORATORIUM;
	}

	public Date getCcEndDateOfLoanValidity() {
		return ccEndDateOfLoanValidity;
	}

	public void setCcEndDateOfLoanValidity(Date ccEndDateOfLoanValidity) {
		this.ccEndDateOfLoanValidity = ccEndDateOfLoanValidity;
	}

	public BigDecimal getCcInterestRate() {
		return ccInterestRate;
	}

	public void setCcInterestRate(BigDecimal ccInterestRate) {
		this.ccInterestRate = ccInterestRate;
	}

	public Long getIsCCLoanDis() {
		return isCCLoanDis;
	}

	public void setIsCCLoanDis(Long isCCLoanDis) {
		this.isCCLoanDis = isCCLoanDis;
	}

	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public BigDecimal getInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(BigDecimal inputAmount) {
		this.inputAmount = inputAmount;
	}

	public BigDecimal getMarketingAmount() {
		return marketingAmount;
	}

	public void setMarketingAmount(BigDecimal marketingAmount) {
		this.marketingAmount = marketingAmount;
	}

	public BigDecimal getProcessingAmount() {
		return processingAmount;
	}

	public void setProcessingAmount(BigDecimal processingAmount) {
		this.processingAmount = processingAmount;
	}

	public BigDecimal getOhterAmount() {
		return ohterAmount;
	}

	public void setOhterAmount(BigDecimal ohterAmount) {
		this.ohterAmount = ohterAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getWctlTermLoan() {
		return wctlTermLoan;
	}

	public void setWctlTermLoan(BigDecimal wctlTermLoan) {
		this.wctlTermLoan = wctlTermLoan;
	}

	public BigDecimal getWctlPromoterMargin() {
		return wctlPromoterMargin;
	}

	public void setWctlPromoterMargin(BigDecimal wctlPromoterMargin) {
		this.wctlPromoterMargin = wctlPromoterMargin;
	}

	public BigDecimal getWctlUnsecuredLoan() {
		return wctlUnsecuredLoan;
	}

	public void setWctlUnsecuredLoan(BigDecimal wctlUnsecuredLoan) {
		this.wctlUnsecuredLoan = wctlUnsecuredLoan;
	}

	public BigDecimal getWctlAnyOtherSource() {
		return wctlAnyOtherSource;
	}

	public void setWctlAnyOtherSource(BigDecimal wctlAnyOtherSource) {
		this.wctlAnyOtherSource = wctlAnyOtherSource;
	}

	public BigDecimal getWclrInputAmount() {
		return wclrInputAmount;
	}

	public void setWclrInputAmount(BigDecimal wclrInputAmount) {
		this.wclrInputAmount = wclrInputAmount;
	}

	public BigDecimal getWclrMarketingAmount() {
		return wclrMarketingAmount;
	}

	public void setWclrMarketingAmount(BigDecimal wclrMarketingAmount) {
		this.wclrMarketingAmount = wclrMarketingAmount;
	}

	public BigDecimal getWclrProcessingAmount() {
		return wclrProcessingAmount;
	}

	public void setWclrProcessingAmount(BigDecimal wclrProcessingAmount) {
		this.wclrProcessingAmount = wclrProcessingAmount;
	}



	public BigDecimal getWclrTotalAmount() {
		return wclrTotalAmount;
	}

	public void setWclrTotalAmount(BigDecimal wclrTotalAmount) {
		this.wclrTotalAmount = wclrTotalAmount;
	}

	public BigDecimal getCcLimit() {
		return ccLimit;
	}

	public void setCcLimit(BigDecimal ccLimit) {
		this.ccLimit = ccLimit;
	}

	public BigDecimal getCcPromoterMargin() {
		return ccPromoterMargin;
	}

	public void setCcPromoterMargin(BigDecimal ccPromoterMargin) {
		this.ccPromoterMargin = ccPromoterMargin;
	}

	public BigDecimal getCcUnsecuredLoan() {
		return ccUnsecuredLoan;
	}

	public void setCcUnsecuredLoan(BigDecimal ccUnsecuredLoan) {
		this.ccUnsecuredLoan = ccUnsecuredLoan;
	}

	public BigDecimal getCcAnyOtherSource() {
		return ccAnyOtherSource;
	}

	public void setCcAnyOtherSource(BigDecimal ccAnyOtherSource) {
		this.ccAnyOtherSource = ccAnyOtherSource;
	}

	public String getTypeOfSecurity() {
		return typeOfSecurity;
	}

	public void setTypeOfSecurity(String typeOfSecurity) {
		this.typeOfSecurity = typeOfSecurity;
	}

	public Long getNatureOfSecurity() {
		return natureOfSecurity;
	}

	public void setNatureOfSecurity(Long natureOfSecurity) {
		this.natureOfSecurity = natureOfSecurity;
	}

	public Long getCfSactionWithoutSecurity() {
		return cfSactionWithoutSecurity;
	}

	public void setCfSactionWithoutSecurity(Long cfSactionWithoutSecurity) {
		this.cfSactionWithoutSecurity = cfSactionWithoutSecurity;
	}

	public BigDecimal getValueOfSecurity() {
		return valueOfSecurity;
	}

	public void setValueOfSecurity(BigDecimal valueOfSecurity) {
		this.valueOfSecurity = valueOfSecurity;
	}

	public String getUnderTaking() {
		return underTaking;
	}

	public void setUnderTaking(String underTaking) {
		this.underTaking = underTaking;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
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

	public FPOMasterDto getFpoMasterDto() {
		return fpoMasterDto;
	}

	public void setFpoMasterDto(FPOMasterDto fpoMasterDto) {
		this.fpoMasterDto = fpoMasterDto;
	}

	public Long getNoOfMarFarmer() {
		return noOfMarFarmer;
	}

	public void setNoOfMarFarmer(Long noOfMarFarmer) {
		this.noOfMarFarmer = noOfMarFarmer;
	}

	public BigDecimal getWclrOtherAmount() {
		return wclrOtherAmount;
	}

	public void setWclrOtherAmount(BigDecimal wclrOtherAmount) {
		this.wclrOtherAmount = wclrOtherAmount;
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

	


}
