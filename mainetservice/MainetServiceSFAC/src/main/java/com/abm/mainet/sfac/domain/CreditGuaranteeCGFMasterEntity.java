package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TB_SFAC_CREDIT_CGF_MASTER")
public class CreditGuaranteeCGFMasterEntity implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5792024086212827492L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CGF_ID", nullable = false)
	private Long cgfID;
	
	@Column(name="NAME_OF_APPLICANT")
	private String nameOfApplication;
	
	@Column(name = "FPO_ID")
	private Long fpoId;
	
	@Column(name="FPO_NAME")
	private String fpoName;
	
	@Column(name = "CONSTITUTION")
	private Long constitution;
	
	@Column(name = "REG_NO")
	private String registrationNo;

	@Column(name = "REG_DATE")
	private Date registrationDate;
	
	@Column(name = "PLACE_OF_REG")
	private String placeOfRegistration;
	
	@Column(name = "REG_ACT")
	private Long regAct;
	
	@Column(name = "FPO_TAN_NO")
	private String fpoTanNo;

	@Column(name = "FPO_PAN_NO")
	private String fpoPanNo;
	
	@Column(name = "GSTIN")
	private String gstin;
	
	@Column(name = "BUSINESS_OF_FPO")
	private Long businessOfFPO;
	
	@Column(name="FPO_AGRI_BUSS")
	private Long fpoAgriBusiness;
	
	@Column(name = "FWD_LINK_DET")
	private String forwardLinkageDetails;
	
	@Column(name = "BWD_LINK_DET")
	private String backwardLinkageDetails;
	
	@Column(name="REGION_OF_FPO")
	private Long regionOFFpo;
	
	@Column(name="NO_OF_LANDLESS_FARMER")
	private Long noOfLandlessFarmer;
	
	@Column(name="NO_OF_SMALL_FARMER")
	private Long noOfSmallFarmer;
	
	@Column(name="NO_OF_MAR_FARMER")
	private Long noOfMarFarmer;
	
	@Column(name="NO_OF_BIG_FARMER")
	private Long noOfBigFarmer;
	
	@Column(name="FPO_PLAINS_MEM")
	private Long noOfFPOPlainMember;
	
	@Column(name = "NO_OF_SHARE_HOLDER_MEM")
	private Long noofShareholderMem;
	
	@Column(name="NORTH_EAST_FARMER")
	private Long noOfNorthEastFarmer;
	
	
	
	@Column(name = "NO_OF_WOMEN_FAR")
	private Long noOfWomenFarmer;
	
	@Column(name="NO_OF_SC_FAR")
	private Long noOfSTsFarmer;
	
	@Column(name="NO_OF_ST_FAR")
	private Long noOfSCFarmer;
	
	@Column(name = "FPO_ADD")
	private String fpoAddress;
	
	@Column(name = "STATE")
	private Long state;

	@Column(name = "DISTRICT")
	private Long district;
	
	@Column(name = "BLOCK")
	private Long block;
	
	@Column(name = "FPO_PIN_CODE")
	private Long fpoPinCode;
	
	@Column(name = "LATITUDE")
	private BigDecimal latitude;
	
	@Column(name = "LONGITUDE")
	private BigDecimal logitude;
	
	@Column(name = "IS_BUSS_ADD_SAME")
	private Long isBusinessAddressSame;
	
	
	@Column(name = "BUSS_FPO_ADD")
	private String businessFPOAddress;
	
	@Column(name = "BUSS_STATE")
	private Long sdb1;

	@Column(name = "BUSS_DISTRICT")
	private Long sdb2;
	
	@Column(name = "BUSS_BLOCK")
	private Long sdb3;
	
	@Column(name = "BUSS_FPO_PIN_CODE")
	private Long businessFpoPinCode;
	
	@Column(name = "BUSS_LATITUDE")
	private BigDecimal businessLatitude;
	
	@Column(name = "BUSS_LONGITUDE")
	private BigDecimal businessLogitude;
	
	@Column(name = "FPO_STATUS")
	private Long fpoStatus;
	
	@Column(name = "FPO_APPL_CGF")
	private Long fpoAppliedCGFOS;
	
	@Column(name ="EXT_CGPAN")
	private String etCGPAN;
	
	@Column(name ="TYPE_OF_CF")
	private Long typeOfCreditFacility;
	

	@Column(name ="WHICH_CG")
	private Long whichCreditGuarantee;
	
	@Column(name ="TOTAL_SAC_AMT")
	private BigDecimal totalSactionAmount;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDITY_CG")
	private Date validityOfCreditGuarantee;
	
	@Column(name = "PROMOTER_NAME")
	private String promoterName;
	
	@Column(name = "PROMOTER_LANDLINE")
	private String promoterLandline;
	
	@Column(name = "PROMOTER_MOBILE_NO")
	private String promoterMobileNo;

	@Column(name = "PROMOTER_EMAIL_ID")
	private String promoterEmailId;
	
	@Column(name="CF_CUST_ID")
	private String cfCustomerId;
	
	@Column(name ="CF_TYPE_OF_CF")
	private Long cfTypeOfCreditFacility;
	
	@Column(name="LANDING_ASS_TOOL")
	private Long LandingAssTool;
	
	@Column(name = "PURPOSE_OF_CF")
	private Long purposeOfCF;
	
	@Column(name = "WCTL_ACC_NO")
	private String wctlAccountNo;
	
	@Column(name="WCTL_SAC_AMT")
	private BigDecimal wctlSactionAmount;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "WCTL_SAC_DATE")
	private Date wctlSactionDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "WCTL_END_DT_INT_MORATORIUM")
	private Date wctlEndDateInterstMORATORIUM;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "WCTL_END_DT_PRI_MORATORIUM")
	private Date wctlEndDatePrincipleMORATORIUM;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "WCTL_DUE_DT_LT_INST")
	private Date wctlDueDateOfLastInstallment;
	
	@Column(name="WCTL_INTEREST_RATE")
	private BigDecimal wctlInterestRate;
	
	@Column(name="IS_WCTL_LOAN_DIS")
	private Long isWCTLLoanDis;
	
	@Column(name="WCTL_CF_EXT")
	private Long wctlCfExtend;
	
	@Column(name="WCTL_OUT_AMT")
	private BigDecimal wctlOutstandingAmount;
	
	@Column(name = "WCTL_PURPOSE_OF_CF")
	private Long wctlPurposeOfCF;
	

	@Column(name = "CC_PURPOSE_OF_CF")
	private Long ccPurposeOfCF;
	
	@Column(name = "CC_ACC_NO")
	private String ccAccountNo;
	
	@Column(name="CC_SAC_AMT")
	private BigDecimal ccSactionAmount;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CC_SAC_DATE")
	private Date ccSactionDate;
	
	@Column(name="CC_DRAW_AMT")
	private BigDecimal ccDrawAmount;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CC_END_OF_MORATORIUM")
	private Date ccEndDateOfMORATORIUM;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CC_END_DT_LOAN_VAL")
	private Date ccEndDateOfLoanValidity;
	
	@Column(name="CC_INTEREST_RATE")
	private BigDecimal ccInterestRate;
	
	@Column(name="IS_CC_LOAN_DIS")
	private Long isCCLoanDis;
	
	@Column(name="CC_OUT_AMT")
	private BigDecimal outstandingAmount;
	
	@Column(name="INPUT_AMT")
	private BigDecimal inputAmount;
	
	@Column(name="MARKETING_AMT")
	private BigDecimal marketingAmount;
	
	@Column(name="PROCESSING_AMT")
	private BigDecimal processingAmount;
	
	@Column(name="OTHER_AMT")
	private BigDecimal ohterAmount;
	
	@Column(name="TOTAL_AMT")
	private BigDecimal totalAmount;
	
	@Column(name="WCTL_TERM_LOAN")
	private BigDecimal wctlTermLoan;
	
	@Column(name="WCTL_PRO_MAR")
	private BigDecimal wctlPromoterMargin;
	
	@Column(name="WCTL_UCSEC_LOAN")
	private BigDecimal wctlUnsecuredLoan;
	
	@Column(name="WCTL_ANY_OTHER_SOURCE")
	private BigDecimal wctlAnyOtherSource;
	
	@Column(name="WCLR_INPUT_AMT")
	private BigDecimal wclrInputAmount;
	
	@Column(name="WCLR_MARKETING_AMT")
	private BigDecimal wclrMarketingAmount;
	
	@Column(name="WCLR_PROCESSING_AMT")
	private BigDecimal wclrProcessingAmount;
	
	@Column(name="WCLR_OTHER_AMT")
	private BigDecimal wclrOtherAmount;
	
	@Column(name="WCLR_TOTAL_AMT")
	private BigDecimal wclrTotalAmount;
	
	@Column(name="CC_LIMIT")
	private BigDecimal ccLimit;
	
	@Column(name="CC_PRO_MAR")
	private BigDecimal ccPromoterMargin;
	
	@Column(name="CC_UCSEC_LOAN")
	private BigDecimal ccUnsecuredLoan;
	
	@Column(name="CC_ANY_OTHER_SOURCE")
	private BigDecimal ccAnyOtherSource;
	
	@Column(name="TYPE_OF_SECURITY")
	private String typeOfSecurity;
	
	@Column(name="NATURE_OF_SECURITY")
	private Long natureOfSecurity;
	
	@Column(name="CG_SAC_WO_SEC")
	private Long cfSactionWithoutSecurity;
	
	@Column(name="VALUE_OF_SECURITY")
	private BigDecimal valueOfSecurity;
	
	@Column(name="UNDERTAKING")
	private String underTaking;
	
	@Column(name = "APP_NO")
	private Long applicationNumber;
	
	@Column(name = "APP_STAUTS")
	private String appStatus;
	
	@Column(name = "AUTH_REAMRK")
	private String authRemark;
	
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






	public Long getSdb1() {
		return sdb1;
	}



	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}



	public Long getSdb2() {
		return sdb2;
	}



	public void setSdb2(Long sdb2) {
		this.sdb2 = sdb2;
	}



	public Long getSdb3() {
		return sdb3;
	}



	public void setSdb3(Long sdb3) {
		this.sdb3 = sdb3;
	}



	public BigDecimal getWclrOtherAmount() {
		return wclrOtherAmount;
	}



	public void setWclrOtherAmount(BigDecimal wclrOtherAmount) {
		this.wclrOtherAmount = wclrOtherAmount;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
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



	public BigDecimal getWclrIhterAmount() {
		return wclrOtherAmount;
	}



	public void setWclrIhterAmount(BigDecimal wclrIhterAmount) {
		this.wclrOtherAmount = wclrIhterAmount;
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



	public String getAppStatus() {
		return appStatus;
	}



	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	
	


	public Long getNoOfMarFarmer() {
		return noOfMarFarmer;
	}



	public void setNoOfMarFarmer(Long noOfMarFarmer) {
		this.noOfMarFarmer = noOfMarFarmer;
	}



	public String getAuthRemark() {
		return authRemark;
	}



	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}



	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_CREDIT_CGF_MASTER", "CGF_ID" };
	}

	
	
}
