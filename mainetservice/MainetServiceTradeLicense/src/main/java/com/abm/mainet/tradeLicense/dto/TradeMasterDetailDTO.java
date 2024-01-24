package com.abm.mainet.tradeLicense.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

public class TradeMasterDetailDTO extends RequestDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6816027960395238997L;
	/**
	 * 
	 */

	private Long trdId;
	private Long apmApplicationId;
	private Long trdType;
	private Long trdLictype;
	private String trdBusnm;
	private String trdBusadd;
	private String trdOldlicno;
	private Date trdLicfromDate;
	private Date trdLictoDate;
	private Date trdLicisdate;
	private Long trdWard1;
	private Long trdWard2;
	private Long trdWard3;
	private Long trdWard4;
	private Long trdWard5;
	private Long trdEWard1;
	private Long trdEWard2;
	private Long trdEWard3;
	private Long trdEWard4;
	private Long trdEWard5;
	private Long trdFtype;
	private String trdFarea;
	private String trdEty;
	private String pmPropNo;
	private BigDecimal trdMonthlyIncome;
	private String trdReg;
	private BigDecimal trdInvestmentAmount;
	private String trdExperience;
	private String trdRegNo;
	private Long trdBankId;
	private String trdBankIfscCode;
	private Long trdAcctNo;
	private Long trdMode;
	private String trdComodity;
	private String trdRoundArea;
	private Long trdLocId;
	private String trdRationNo;
	private String trdRationAuth;
	private Long orgid;
	private Date createdDate;
	private Long createdBy;
	private Date updatedDate;
	private Long updatedBy;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long langId;
	private BigDecimal totalApplicationFee;
	private String primaryOwnerName;
	private double totalOutsatandingAmt;
	private String usage;
	private Long trdStatus;
	private String trdLicno;
	private String trdOwnerNm;
	private Long trdOthCatType;
	private BigDecimal dueOnProperty;
	private Long dpZone;
	private String buldingPermissionNo;
	private String fireNonApplicable;
	private String fireNOCNo;
	private BigDecimal dueOnWater;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private List<TradeLicenseOwnerDetailDTO> tradeLicenseOwnerdetailDTO = new ArrayList<TradeLicenseOwnerDetailDTO>();
	private List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = new ArrayList<TradeLicenseItemDetailDTO>();
	private List<OwnerFamilyDetailDTO> ownerFamilydetailDTO = new ArrayList<OwnerFamilyDetailDTO>();

	private RenewalMasterDetailDTO renewalMasterDetailDTO = new RenewalMasterDetailDTO();
	private String licenseType;
	private String licenseFromDate;
	private String licenseToDate;
	private Date agreementFromDate;
	private Date agreementToDate;
	private List<Long> ownIds = new ArrayList<>();
	private List<Long> removeItemIds = new ArrayList<>();
	private Long serviceId;
	private Map<Long, Double> feeIds = new HashMap<>(0);
	private String licenseDateDesc;
	private String createdDateDesc;
	private String applicationCharge;
	private Long rmRcptno;
	private Date rmDate;
	private BigDecimal rmAmount;
	private String cpdDesc;
	private String cpdDescMar;
	private String rmLoiNo;
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private String trdNewBusnm;
	private String remarks;
	private String trdFlatNo;
	private String applicationchargeApplFlag;
	private String scrutinyAppFlag;
	private Long dpDeptid;
	private String propertyAddress;
	private String canRemark;
	private String checklistAppFlag;
	private String gstNo;
	private String emailId;
	private String MobileNo;
	private String rejectRemark;
	private String cancelReason;
	private Date cancelDate;
	private String cancelBy;
	private String transferMode;
	private String liceCategory;
	private String liceSubCategory;
	private Long renewalPendingDays;
	private String propertyType;
	private String villageName;
	private String surveyNumber;
	private String partNo;
	private String plotNo;
	private String roadType;
	private Long pTaxCollEmpId;
	private String pTaxCollEmpName;
	private String pTaxCollEmpEmailId;
	private String pTaxCollEmpMobNo;
	private String licenceTypeReg;
	private String licCatgoryReg;
	private String licSubCatReg;
	private Long propLvlRoadType;
	private Long landType;
	private Double assPlotArea;
	private String assessmentCheckFlag;
	private Map<String, Double> chargesInfo = new HashMap<>(0);
	private List<String> flatNoList = new ArrayList<>();
	private Long checkStatus;
	private String cpdValue;
	private boolean editValue;
	private Long renewCycle;
	private String propertyOwnerAddress;
	private String ward1Level;
	private String ward2Level;
	private Long pincode;
	private String landMark;
	private String directorsNameAndAdd;
	private BigDecimal loiFee;
	private String checkApptimeCharge;
	private double totalWaterOutsatandingAmt;
	private String source;
	private Long serviceDuration;
	private String serviceName;
	private BigDecimal securityDepositeAmt;
	private BigDecimal licenseFee;
	private String bussinessNature;

	@JsonIgnore
	private String gISUri;

	public Long getTrdId() {
		return trdId;
	}

	public void setTrdId(Long trdId) {
		this.trdId = trdId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public String getRejectRemark() {
		return rejectRemark;
	}

	public void setRejectRemark(String rejectRemark) {
		this.rejectRemark = rejectRemark;
	}

	public Long getTrdType() {
		return trdType;
	}

	public void setTrdType(Long trdType) {
		this.trdType = trdType;
	}

	public Long getTrdLictype() {
		return trdLictype;
	}

	public void setTrdLictype(Long trdLictype) {
		this.trdLictype = trdLictype;
	}

	public String getTrdBusnm() {
		return trdBusnm;
	}

	public void setTrdBusnm(String trdBusnm) {
		this.trdBusnm = trdBusnm;
	}

	public String getTrdBusadd() {
		return trdBusadd;
	}

	public void setTrdBusadd(String trdBusadd) {
		this.trdBusadd = trdBusadd;
	}

	public String getTrdOldlicno() {
		return trdOldlicno;
	}

	public void setTrdOldlicno(String trdOldlicno) {
		this.trdOldlicno = trdOldlicno;
	}

	public Date getTrdLicfromDate() {
		return trdLicfromDate;
	}

	public void setTrdLicfromDate(Date trdLicfromDate) {
		this.trdLicfromDate = trdLicfromDate;
	}

	public Date getTrdLictoDate() {
		return trdLictoDate;
	}

	public void setTrdLictoDate(Date trdLictoDate) {
		this.trdLictoDate = trdLictoDate;
	}

	public Date getTrdLicisdate() {
		return trdLicisdate;
	}

	public void setTrdLicisdate(Date trdLicisdate) {
		this.trdLicisdate = trdLicisdate;
	}

	public Long getTrdWard1() {
		return trdWard1;
	}

	public void setTrdWard1(Long trdWard1) {
		this.trdWard1 = trdWard1;
	}

	public Long getTrdWard2() {
		return trdWard2;
	}

	public void setTrdWard2(Long trdWard2) {
		this.trdWard2 = trdWard2;
	}

	public Long getTrdWard3() {
		return trdWard3;
	}

	public void setTrdWard3(Long trdWard3) {
		this.trdWard3 = trdWard3;
	}

	public Long getTrdWard4() {
		return trdWard4;
	}

	public void setTrdWard4(Long trdWard4) {
		this.trdWard4 = trdWard4;
	}

	public Long getTrdWard5() {
		return trdWard5;
	}

	public void setTrdWard5(Long trdWard5) {
		this.trdWard5 = trdWard5;
	}

	public Long getTrdFtype() {
		return trdFtype;
	}

	public void setTrdFtype(Long trdFtype) {
		this.trdFtype = trdFtype;
	}

	public String getTrdFarea() {
		return trdFarea;
	}

	public void setTrdFarea(String trdFarea) {
		this.trdFarea = trdFarea;
	}

	public String getTrdEty() {
		return trdEty;
	}

	public void setTrdEty(String trdEty) {
		this.trdEty = trdEty;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public List<TradeLicenseOwnerDetailDTO> getTradeLicenseOwnerdetailDTO() {
		return tradeLicenseOwnerdetailDTO;
	}

	public void setTradeLicenseOwnerdetailDTO(List<TradeLicenseOwnerDetailDTO> tradeLicenseOwnerdetailDTO) {
		this.tradeLicenseOwnerdetailDTO = tradeLicenseOwnerdetailDTO;
	}

	public List<TradeLicenseItemDetailDTO> getTradeLicenseItemDetailDTO() {
		return tradeLicenseItemDetailDTO;
	}

	public void setTradeLicenseItemDetailDTO(List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO) {
		this.tradeLicenseItemDetailDTO = tradeLicenseItemDetailDTO;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public BigDecimal getTotalApplicationFee() {
		return totalApplicationFee;
	}

	public void setTotalApplicationFee(BigDecimal totalApplicationFee) {
		this.totalApplicationFee = totalApplicationFee;
	}

	public String getPrimaryOwnerName() {
		return primaryOwnerName;
	}

	public void setPrimaryOwnerName(String primaryOwnerName) {
		this.primaryOwnerName = primaryOwnerName;
	}

	public double getTotalOutsatandingAmt() {
		return totalOutsatandingAmt;
	}

	public void setTotalOutsatandingAmt(double totalOutsatandingAmt) {
		this.totalOutsatandingAmt = totalOutsatandingAmt;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getTrdLicno() {
		return trdLicno;
	}

	public Long getTrdStatus() {
		return trdStatus;
	}

	public void setTrdStatus(Long trdStatus) {
		this.trdStatus = trdStatus;
	}

	public void setTrdLicno(String trdLicno) {
		this.trdLicno = trdLicno;
	}

	public String getPmPropNo() {
		return pmPropNo;
	}

	public void setPmPropNo(String pmPropNo) {
		this.pmPropNo = pmPropNo;
	}

	public String getTrdOwnerNm() {
		return trdOwnerNm;
	}

	public void setTrdOwnerNm(String trdOwnerNm) {
		this.trdOwnerNm = trdOwnerNm;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getLicenseFromDate() {
		return licenseFromDate;
	}

	public void setLicenseFromDate(String licenseFromDate) {
		this.licenseFromDate = licenseFromDate;
	}

	public String getLicenseToDate() {
		return licenseToDate;
	}

	public void setLicenseToDate(String licenseToDate) {
		this.licenseToDate = licenseToDate;
	}

	public List<Long> getOwnIds() {
		return ownIds;
	}

	public void setOwnIds(List<Long> ownIds) {
		this.ownIds = ownIds;
	}

	public List<Long> getRemoveItemIds() {
		return removeItemIds;
	}

	public void setRemoveItemIds(List<Long> removeItemIds) {
		this.removeItemIds = removeItemIds;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Map<Long, Double> getFeeIds() {
		return feeIds;
	}

	public void setFeeIds(Map<Long, Double> feeIds) {
		this.feeIds = feeIds;
	}

	public String getLicenseDateDesc() {
		return licenseDateDesc;
	}

	public void setLicenseDateDesc(String licenseDateDesc) {
		this.licenseDateDesc = licenseDateDesc;
	}

	public String getCreatedDateDesc() {
		return createdDateDesc;
	}

	public void setCreatedDateDesc(String createdDateDesc) {
		this.createdDateDesc = createdDateDesc;
	}

	public String getApplicationCharge() {
		return applicationCharge;
	}

	public void setApplicationCharge(String applicationCharge) {
		this.applicationCharge = applicationCharge;
	}

	public Long getRmRcptno() {
		return rmRcptno;
	}

	public void setRmRcptno(Long rmRcptno) {
		this.rmRcptno = rmRcptno;
	}

	public Date getRmDate() {
		return rmDate;
	}

	public void setRmDate(Date rmDate) {
		this.rmDate = rmDate;
	}

	public BigDecimal getRmAmount() {
		return rmAmount;
	}

	public void setRmAmount(BigDecimal rmAmount) {
		this.rmAmount = rmAmount;
	}

	public String getCpdDesc() {
		return cpdDesc;
	}

	public void setCpdDesc(String cpdDesc) {
		this.cpdDesc = cpdDesc;
	}

	public String getCpdDescMar() {
		return cpdDescMar;
	}

	public void setCpdDescMar(String cpdDescMar) {
		this.cpdDescMar = cpdDescMar;
	}

	public String getRmLoiNo() {
		return rmLoiNo;
	}

	public void setRmLoiNo(String rmLoiNo) {
		this.rmLoiNo = rmLoiNo;
	}

	public RenewalMasterDetailDTO getRenewalMasterDetailDTO() {
		return renewalMasterDetailDTO;
	}

	public void setRenewalMasterDetailDTO(RenewalMasterDetailDTO renewalMasterDetailDTO) {
		this.renewalMasterDetailDTO = renewalMasterDetailDTO;
	}

	public List<OwnerFamilyDetailDTO> getOwnerFamilydetailDTO() {
		return ownerFamilydetailDTO;
	}

	public void setOwnerFamilydetailDTO(List<OwnerFamilyDetailDTO> ownerFamilydetailDTO) {
		this.ownerFamilydetailDTO = ownerFamilydetailDTO;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public String getTrdNewBusnm() {
		return trdNewBusnm;
	}

	public void setTrdNewBusnm(String trdNewBusnm) {
		this.trdNewBusnm = trdNewBusnm;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getApplicationchargeApplFlag() {
		return applicationchargeApplFlag;
	}

	public void setApplicationchargeApplFlag(String applicationchargeApplFlag) {
		this.applicationchargeApplFlag = applicationchargeApplFlag;
	}

	public Long getDpDeptid() {
		return dpDeptid;
	}

	public void setDpDeptid(Long dpDeptid) {
		this.dpDeptid = dpDeptid;
	}

	public String getScrutinyAppFlag() {
		return scrutinyAppFlag;
	}

	public void setScrutinyAppFlag(String scrutinyAppFlag) {
		this.scrutinyAppFlag = scrutinyAppFlag;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public String getTrdFlatNo() {
		return trdFlatNo;
	}

	public void setTrdFlatNo(String trdFlatNo) {
		this.trdFlatNo = trdFlatNo;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public Date getAgreementFromDate() {
		return agreementFromDate;
	}

	public void setAgreementFromDate(Date agreementFromDate) {
		this.agreementFromDate = agreementFromDate;
	}

	public Date getAgreementToDate() {
		return agreementToDate;
	}

	public void setAgreementToDate(Date agreementToDate) {
		this.agreementToDate = agreementToDate;
	}

	public String getChecklistAppFlag() {
		return checklistAppFlag;
	}

	public void setChecklistAppFlag(String checklistAppFlag) {
		this.checklistAppFlag = checklistAppFlag;
	}

	public String getCanRemark() {
		return canRemark;
	}

	public void setCanRemark(String canRemark) {
		this.canRemark = canRemark;
	}

	public BigDecimal getTrdMonthlyIncome() {
		return trdMonthlyIncome;
	}

	public void setTrdMonthlyIncome(BigDecimal trdMonthlyIncome) {
		this.trdMonthlyIncome = trdMonthlyIncome;
	}

	public String getTrdReg() {
		return trdReg;
	}

	public void setTrdReg(String trdReg) {
		this.trdReg = trdReg;
	}

	public BigDecimal getTrdInvestmentAmount() {
		return trdInvestmentAmount;
	}

	public void setTrdInvestmentAmount(BigDecimal trdInvestmentAmount) {
		this.trdInvestmentAmount = trdInvestmentAmount;
	}

	public String getTrdExperience() {
		return trdExperience;
	}

	public void setTrdExperience(String trdExperience) {
		this.trdExperience = trdExperience;
	}

	public String getTrdRegNo() {
		return trdRegNo;
	}

	public void setTrdRegNo(String trdRegNo) {
		this.trdRegNo = trdRegNo;
	}

	public Long getTrdBankId() {
		return trdBankId;
	}

	public void setTrdBankId(Long trdBankId) {
		this.trdBankId = trdBankId;
	}

	public Long getTrdAcctNo() {
		return trdAcctNo;
	}

	public void setTrdAcctNo(Long trdAcctNo) {
		this.trdAcctNo = trdAcctNo;
	}

	public Long getTrdMode() {
		return trdMode;
	}

	public void setTrdMode(Long trdMode) {
		this.trdMode = trdMode;
	}

	public String getTrdComodity() {
		return trdComodity;
	}

	public void setTrdComodity(String trdComodity) {
		this.trdComodity = trdComodity;
	}

	public String getTrdRoundArea() {
		return trdRoundArea;
	}

	public void setTrdRoundArea(String trdRoundArea) {
		this.trdRoundArea = trdRoundArea;
	}

	public Long getTrdLocId() {
		return trdLocId;
	}

	public void setTrdLocId(Long trdLocId) {
		this.trdLocId = trdLocId;
	}

	public String getTrdRationNo() {
		return trdRationNo;
	}

	public void setTrdRationNo(String trdRationNo) {
		this.trdRationNo = trdRationNo;
	}

	public String getTrdRationAuth() {
		return trdRationAuth;
	}

	public void setTrdRationAuth(String trdRationAuth) {
		this.trdRationAuth = trdRationAuth;
	}

	public Long getTrdEWard1() {
		return trdEWard1;
	}

	public void setTrdEWard1(Long trdEWard1) {
		this.trdEWard1 = trdEWard1;
	}

	public Long getTrdEWard2() {
		return trdEWard2;
	}

	public void setTrdEWard2(Long trdEWard2) {
		this.trdEWard2 = trdEWard2;
	}

	public Long getTrdEWard3() {
		return trdEWard3;
	}

	public void setTrdEWard3(Long trdEWard3) {
		this.trdEWard3 = trdEWard3;
	}

	public Long getTrdEWard4() {
		return trdEWard4;
	}

	public void setTrdEWard4(Long trdEWard4) {
		this.trdEWard4 = trdEWard4;
	}

	public Long getTrdEWard5() {
		return trdEWard5;
	}

	public void setTrdEWard5(Long trdEWard5) {
		this.trdEWard5 = trdEWard5;
	}

	public String getTrdBankIfscCode() {
		return trdBankIfscCode;
	}

	public void setTrdBankIfscCode(String trdBankIfscCode) {
		this.trdBankIfscCode = trdBankIfscCode;
	}

	public Long getTrdOthCatType() {
		return trdOthCatType;
	}

	public void setTrdOthCatType(Long trdOthCatType) {
		this.trdOthCatType = trdOthCatType;
	}

	public String getgISUri() {
		return gISUri;
	}

	public void setgISUri(String gISUri) {
		this.gISUri = gISUri;
	}

	public BigDecimal getDueOnProperty() {
		return dueOnProperty;
	}

	public void setDueOnProperty(BigDecimal dueOnProperty) {
		this.dueOnProperty = dueOnProperty;
	}

	public Long getDpZone() {
		return dpZone;
	}

	public void setDpZone(Long dpZone) {
		this.dpZone = dpZone;
	}

	public String getFireNonApplicable() {
		return fireNonApplicable;
	}

	public void setFireNonApplicable(String fireNonApplicable) {
		this.fireNonApplicable = fireNonApplicable;
	}

	public String getBuldingPermissionNo() {
		return buldingPermissionNo;
	}

	public void setBuldingPermissionNo(String buldingPermissionNo) {
		this.buldingPermissionNo = buldingPermissionNo;
	}

	public String getFireNOCNo() {
		return fireNOCNo;
	}

	public void setFireNOCNo(String fireNOCNo) {
		this.fireNOCNo = fireNOCNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelBy() {
		return cancelBy;
	}

	public void setCancelBy(String cancelBy) {
		this.cancelBy = cancelBy;
	}

	public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}

	public String getLiceCategory() {
		return liceCategory;
	}

	public void setLiceCategory(String liceCategory) {
		this.liceCategory = liceCategory;
	}

	public String getLiceSubCategory() {
		return liceSubCategory;
	}

	public void setLiceSubCategory(String liceSubCategory) {
		this.liceSubCategory = liceSubCategory;
	}

	public Long getRenewalPendingDays() {
		return renewalPendingDays;
	}

	public void setRenewalPendingDays(Long renewalPendingDays) {
		this.renewalPendingDays = renewalPendingDays;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getSurveyNumber() {
		return surveyNumber;
	}

	public void setSurveyNumber(String surveyNumber) {
		this.surveyNumber = surveyNumber;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPlotNo() {
		return plotNo;
	}

	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}

	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	public Long getpTaxCollEmpId() {
		return pTaxCollEmpId;
	}

	public void setpTaxCollEmpId(Long pTaxCollEmpId) {
		this.pTaxCollEmpId = pTaxCollEmpId;
	}

	public String getpTaxCollEmpName() {
		return pTaxCollEmpName;
	}

	public void setpTaxCollEmpName(String pTaxCollEmpName) {
		this.pTaxCollEmpName = pTaxCollEmpName;
	}

	public String getpTaxCollEmpEmailId() {
		return pTaxCollEmpEmailId;
	}

	public void setpTaxCollEmpEmailId(String pTaxCollEmpEmailId) {
		this.pTaxCollEmpEmailId = pTaxCollEmpEmailId;
	}

	public String getpTaxCollEmpMobNo() {
		return pTaxCollEmpMobNo;
	}

	public void setpTaxCollEmpMobNo(String pTaxCollEmpMobNo) {
		this.pTaxCollEmpMobNo = pTaxCollEmpMobNo;
	}

	public String getLicenceTypeReg() {
		return licenceTypeReg;
	}

	public void setLicenceTypeReg(String licenceTypeReg) {
		this.licenceTypeReg = licenceTypeReg;
	}

	public String getLicCatgoryReg() {
		return licCatgoryReg;
	}

	public void setLicCatgoryReg(String licCatgoryReg) {
		this.licCatgoryReg = licCatgoryReg;
	}

	public String getLicSubCatReg() {
		return licSubCatReg;
	}

	public void setLicSubCatReg(String licSubCatReg) {
		this.licSubCatReg = licSubCatReg;
	}

	public Long getPropLvlRoadType() {
		return propLvlRoadType;
	}

	public void setPropLvlRoadType(Long propLvlRoadType) {
		this.propLvlRoadType = propLvlRoadType;
	}

	public Long getLandType() {
		return landType;
	}

	public void setLandType(Long landType) {
		this.landType = landType;
	}

	public Double getAssPlotArea() {
		return assPlotArea;
	}

	public void setAssPlotArea(Double assPlotArea) {
		this.assPlotArea = assPlotArea;
	}

	public String getAssessmentCheckFlag() {
		return assessmentCheckFlag;
	}

	public void setAssessmentCheckFlag(String assessmentCheckFlag) {
		this.assessmentCheckFlag = assessmentCheckFlag;
	}

	public Map<String, Double> getChargesInfo() {
		return chargesInfo;
	}

	public void setChargesInfo(Map<String, Double> chargesInfo) {
		this.chargesInfo = chargesInfo;
	}

	public List<String> getFlatNoList() {
		return flatNoList;
	}

	public void setFlatNoList(List<String> flatNoList) {
		this.flatNoList = flatNoList;
	}

	public Long getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Long checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCpdValue() {
		return cpdValue;
	}

	public void setCpdValue(String cpdValue) {
		this.cpdValue = cpdValue;
	}

	public boolean isEditValue() {
		return editValue;
	}

	public void setEditValue(boolean editValue) {
		this.editValue = editValue;
	}

	public Long getRenewCycle() {
		return renewCycle;
	}

	public void setRenewCycle(Long renewCycle) {
		this.renewCycle = renewCycle;
	}

	public BigDecimal getDueOnWater() {
		return dueOnWater;
	}

	public void setDueOnWater(BigDecimal dueOnWater) {
		this.dueOnWater = dueOnWater;
	}

	public String getPropertyOwnerAddress() {
		return propertyOwnerAddress;
	}

	public void setPropertyOwnerAddress(String propertyOwnerAddress) {
		this.propertyOwnerAddress = propertyOwnerAddress;
	}

	public String getWard1Level() {
		return ward1Level;
	}

	public void setWard1Level(String ward1Level) {
		this.ward1Level = ward1Level;
	}

	public String getWard2Level() {
		return ward2Level;
	}

	public void setWard2Level(String ward2Level) {
		this.ward2Level = ward2Level;
	}

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getDirectorsNameAndAdd() {
		return directorsNameAndAdd;
	}

	public void setDirectorsNameAndAdd(String directorsNameAndAdd) {
		this.directorsNameAndAdd = directorsNameAndAdd;
	}


	public BigDecimal getLoiFee() {
		return loiFee;
	}

	public void setLoiFee(BigDecimal loiFee) {
		this.loiFee = loiFee;
	}

	public String getCheckApptimeCharge() {
		return checkApptimeCharge;
	}

	public void setCheckApptimeCharge(String checkApptimeCharge) {
		this.checkApptimeCharge = checkApptimeCharge;
	}

	public double getTotalWaterOutsatandingAmt() {
		return totalWaterOutsatandingAmt;
	}

	public void setTotalWaterOutsatandingAmt(double totalWaterOutsatandingAmt) {
		this.totalWaterOutsatandingAmt = totalWaterOutsatandingAmt;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getServiceDuration() {
		return serviceDuration;
	}

	public void setServiceDuration(Long serviceDuration) {
		this.serviceDuration = serviceDuration;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}



	public BigDecimal getLicenseFee() {
		return licenseFee;
	}

	public void setLicenseFee(BigDecimal licenseFee) {
		this.licenseFee = licenseFee;
	}

	public BigDecimal getSecurityDepositeAmt() {
		return securityDepositeAmt;
	}

	public void setSecurityDepositeAmt(BigDecimal securityDepositeAmt) {
		this.securityDepositeAmt = securityDepositeAmt;
	}

	public String getBussinessNature() {
		return bussinessNature;
	}

	public void setBussinessNature(String bussinessNature) {
		this.bussinessNature = bussinessNature;
	}

	
     
	
}
