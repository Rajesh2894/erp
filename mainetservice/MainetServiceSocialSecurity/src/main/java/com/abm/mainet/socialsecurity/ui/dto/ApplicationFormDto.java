/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author priti.singh
 *
 */
public class ApplicationFormDto implements Serializable {

	private static final long serialVersionUID = -3337806617014632572L;

	/* factor applicable */
	private Long applicationId;
	private Long serviceId;
	private String objOfschme;
	private Long orgId;
	private Long parentOrgId;
	private Long selectSchemeName;
	private String nameofApplicant;
	/* private Date applicationDob; */

	private Date applicantDob;
	private Long ageason;
	private Long genderId;
	private String applicantAdress;
	private Long pinCode;
	private Long mobNum;
	private Long educationId;
	private Long classs;
	private Long maritalStatusId;
	private Long categoryId;
	private BigDecimal annualIncome;

	private Long typeofDisId;
	private Long percenrofDis;
	private Long bplid;
	private Long bplinspectyr;
	private String bplfamily;

	private Long accountNumber;
	private Long bankNameId;
	private String bankAddress;
	private String ifscCode;

	private String nameofFather;
	private Long contactNumber;
	private String nameofMother;

	private String detailsoffamIncomeSource;
	private BigDecimal annualIncomeoffam;
	private Date createdDate;
	private Long createdBy;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long masterAppId;
	private String approvalFlag;
	private Long applicableServiceId;
	private Long deptId;
	private Long langId;
	private List<DocumentDetailsVO> documentList;
	private ApplicantDetailDTO applicant = new ApplicantDetailDTO();
	private String isBplApplicable;
	private Boolean statusFlag;
	private Date lastPaymentDate;
	private Long referenceNo;
	private String dataLegacyFlag;
	private String beneficiaryno;
	private String ulbName;
	private String serviceCode;
	private Date lastDateofLifeCerti;
	private List<ViewDtoList> viewList = new ArrayList<>();
	private Date validtoDate;
	private String pensionCancelReason;
	private Date pensionCancelDate;
	private Long apmApplicationId;
	private String aadharCard;
	private Long gridId;
	private String electionIdNo;
	private List<String> errorList;
	private String sourceFlag;
	private String disbilityCertNo;
	private List<SchemeAppFamilyDetailsDto> ownerFamilydetailDTO=new ArrayList<SchemeAppFamilyDetailsDto>();
	private String swdward1;
	private String swdward2;
	private String swdward3;
	private String swdward4;
	private String swdward5;
	private String servDesc;
	private String benfitedAmt;
	private Long subSchemeName;
	private String saveMode;
	private String status;
	private String statusDesc;
	private String email;
	private String year;


	/**
	 * @return the serviceId
	 */
	public Long getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the objOfschme
	 */
	public String getObjOfschme() {
		return objOfschme;
	}

	/**
	 * @param objOfschme the objOfschme to set
	 */
	public void setObjOfschme(String objOfschme) {
		this.objOfschme = objOfschme;
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
	 * @return the selectSchemeName
	 */
	public Long getSelectSchemeName() {
		return selectSchemeName;
	}

	/**
	 * @param selectSchemeName the selectSchemeName to set
	 */
	public void setSelectSchemeName(Long selectSchemeName) {
		this.selectSchemeName = selectSchemeName;
	}

	/**
	 * @return the nameofApplicant
	 */
	public String getNameofApplicant() {
		return nameofApplicant;
	}

	/**
	 * @param nameofApplicant the nameofApplicant to set
	 */
	public void setNameofApplicant(String nameofApplicant) {
		this.nameofApplicant = nameofApplicant;
	}

	/**
	 * @return the applicationDob
	 */
	/*
	 * public Date getApplicationDob() { return applicationDob; }
	 *//**
		 * @param applicationDob the applicationDob to set
		 *//*
			 * public void setApplicationDob(Date applicationDob) { this.applicationDob =
			 * applicationDob; }
			 */

	/**
	 * @return the genderId
	 */
	public Long getGenderId() {
		return genderId;
	}

	/**
	 * @param genderId the genderId to set
	 */
	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	/**
	 * @return the applicantAdress
	 */
	public String getApplicantAdress() {
		return applicantAdress;
	}

	/**
	 * @param applicantAdress the applicantAdress to set
	 */
	public void setApplicantAdress(String applicantAdress) {
		this.applicantAdress = applicantAdress;
	}

	/**
	 * @return the pinCode
	 */
	public Long getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(Long pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the mobNum
	 */
	public Long getMobNum() {
		return mobNum;
	}

	/**
	 * @param mobNum the mobNum to set
	 */
	public void setMobNum(Long mobNum) {
		this.mobNum = mobNum;
	}

	/**
	 * @return the educationId
	 */
	public Long getEducationId() {
		return educationId;
	}

	/**
	 * @param educationId the educationId to set
	 */
	public void setEducationId(Long educationId) {
		this.educationId = educationId;
	}

	/**
	 * @return the maritalStatusId
	 */
	public Long getMaritalStatusId() {
		return maritalStatusId;
	}

	/**
	 * @param maritalStatusId the maritalStatusId to set
	 */
	public void setMaritalStatusId(Long maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	/**
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the bankNameId
	 */
	public Long getBankNameId() {
		return bankNameId;
	}

	/**
	 * @param bankNameId the bankNameId to set
	 */
	public void setBankNameId(Long bankNameId) {
		this.bankNameId = bankNameId;
	}
	
	//Bank address 

	public String getBankAddress() {
		return bankAddress;
	}

	
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	
	// bank ifsc code
	
	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	/**
	 * @return the contactNumber
	 */
	public Long getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the nameofMother
	 */
	public String getNameofMother() {
		return nameofMother;
	}

	/**
	 * @param nameofMother the nameofMother to set
	 */
	public void setNameofMother(String nameofMother) {
		this.nameofMother = nameofMother;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the typeofDisId
	 */
	public Long getTypeofDisId() {
		return typeofDisId;
	}

	/**
	 * @param typeofDisId the typeofDisId to set
	 */
	public void setTypeofDisId(Long typeofDisId) {
		this.typeofDisId = typeofDisId;
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

	public BigDecimal getAnnualIncomeoffam() {
		return annualIncomeoffam;
	}

	public void setAnnualIncomeoffam(BigDecimal annualIncomeoffam) {
		this.annualIncomeoffam = annualIncomeoffam;
	}

	public Long getAgeason() {
		return ageason;
	}

	public void setAgeason(Long ageason) {
		this.ageason = ageason;
	}

	public BigDecimal getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getDetailsoffamIncomeSource() {
		return detailsoffamIncomeSource;
	}

	public void setDetailsoffamIncomeSource(String detailsoffamIncomeSource) {
		this.detailsoffamIncomeSource = detailsoffamIncomeSource;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getPercenrofDis() {
		return percenrofDis;
	}

	public void setPercenrofDis(Long percenrofDis) {
		this.percenrofDis = percenrofDis;
	}

	public String getBplfamily() {
		return bplfamily;
	}

	public void setBplfamily(String bplfamily) {
		this.bplfamily = bplfamily;
	}

	public Long getBplinspectyr() {
		return bplinspectyr;
	}

	public void setBplinspectyr(Long bplinspectyr) {
		this.bplinspectyr = bplinspectyr;
	}

	public Long getMasterAppId() {
		return masterAppId;
	}

	public void setMasterAppId(Long masterAppId) {
		this.masterAppId = masterAppId;
	}

	public String getApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public Long getApplicableServiceId() {
		return applicableServiceId;
	}

	public void setApplicableServiceId(Long applicableServiceId) {
		this.applicableServiceId = applicableServiceId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public ApplicantDetailDTO getApplicant() {
		return applicant;
	}

	public void setApplicant(ApplicantDetailDTO applicant) {
		this.applicant = applicant;
	}

	public Long getClasss() {
		return classs;
	}

	public void setClasss(Long classs) {
		this.classs = classs;
	}

	public String getIsBplApplicable() {
		return isBplApplicable;
	}

	public void setIsBplApplicable(String isBplApplicable) {
		this.isBplApplicable = isBplApplicable;
	}

	public Long getBplid() {
		return bplid;
	}

	public void setBplid(Long bplid) {
		this.bplid = bplid;
	}

	/**
	 * @return the nameofFather
	 */
	public String getNameofFather() {
		return nameofFather;
	}

	/**
	 * @param nameofFather the nameofFather to set
	 */
	public void setNameofFather(String nameofFather) {
		this.nameofFather = nameofFather;
	}

	/**
	 * @return the statusFlag
	 */
	public Boolean getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(Boolean statusFlag) {
		this.statusFlag = statusFlag;
	}

	/**
	 * @return the lastPaymentDate
	 */
	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}

	/**
	 * @param lastPaymentDate the lastPaymentDate to set
	 */
	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	/**
	 * @return the referenceNo
	 */
	public Long getReferenceNo() {
		return referenceNo;
	}

	/**
	 * @param referenceNo the referenceNo to set
	 */
	public void setReferenceNo(Long referenceNo) {
		this.referenceNo = referenceNo;
	}

	/**
	 * @return the dataLegacyFlag
	 */
	public String getDataLegacyFlag() {
		return dataLegacyFlag;
	}

	/**
	 * @param dataLegacyFlag the dataLegacyFlag to set
	 */
	public void setDataLegacyFlag(String dataLegacyFlag) {
		this.dataLegacyFlag = dataLegacyFlag;
	}

	/**
	 * @return the beneficiaryno
	 */
	public String getBeneficiaryno() {
		return beneficiaryno;
	}

	/**
	 * @param beneficiaryno the beneficiaryno to set
	 */
	public void setBeneficiaryno(String beneficiaryno) {
		this.beneficiaryno = beneficiaryno;
	}

	/**
	 * @return the ulbName
	 */
	public String getUlbName() {
		return ulbName;
	}

	/**
	 * @param ulbName the ulbName to set
	 */
	public void setUlbName(String ulbName) {
		this.ulbName = ulbName;
	}

	/**
	 * @return the serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 * @param serviceCode the serviceCode to set
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 * @return the lastDateofLifeCerti
	 */
	public Date getLastDateofLifeCerti() {
		return lastDateofLifeCerti;
	}

	/**
	 * @param lastDateofLifeCerti the lastDateofLifeCerti to set
	 */
	public void setLastDateofLifeCerti(Date lastDateofLifeCerti) {
		this.lastDateofLifeCerti = lastDateofLifeCerti;
	}

	/**
	 * @return the viewList
	 */
	public List<ViewDtoList> getViewList() {
		return viewList;
	}

	/**
	 * @param viewList the viewList to set
	 */
	public void setViewList(List<ViewDtoList> viewList) {
		this.viewList = viewList;
	}

	/**
	 * @return the validtoDate
	 */
	public Date getValidtoDate() {
		return validtoDate;
	}

	/**
	 * @param validtoDate the validtoDate to set
	 */
	public void setValidtoDate(Date validtoDate) {
		this.validtoDate = validtoDate;
	}

	/**
	 * @return the pensionCancelReason
	 */
	public String getPensionCancelReason() {
		return pensionCancelReason;
	}

	/**
	 * @param pensionCancelReason the pensionCancelReason to set
	 */
	public void setPensionCancelReason(String pensionCancelReason) {
		this.pensionCancelReason = pensionCancelReason;
	}

	/**
	 * @return the pensionCancelDate
	 */
	public Date getPensionCancelDate() {
		return pensionCancelDate;
	}

	/**
	 * @param pensionCancelDate the pensionCancelDate to set
	 */
	public void setPensionCancelDate(Date pensionCancelDate) {
		this.pensionCancelDate = pensionCancelDate;
	}

	/**
	 * @return the apmApplicationId
	 */
	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	/**
	 * @param apmApplicationId the apmApplicationId to set
	 */
	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	/**
	 * @return the aadharCard
	 */
	public String getAadharCard() {
		return aadharCard;
	}

	/**
	 * @param aadharCard the aadharCard to set
	 */
	public void setAadharCard(String aadharCard) {
		this.aadharCard = aadharCard;
	}

	/**
	 * @return the parentOrgId
	 */
	public Long getParentOrgId() {
		return parentOrgId;
	}

	/**
	 * @param parentOrgId the parentOrgId to set
	 */
	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public Date getApplicantDob() {
		return applicantDob;
	}

	public void setApplicantDob(Date applicantDob) {
		this.applicantDob = applicantDob;
	}

	public Long getGridId() {
		return gridId;
	}

	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}

	public String getElectionIdNo() {
		return electionIdNo;
	}

	public void setElectionIdNo(String electionIdNo) {
		this.electionIdNo = electionIdNo;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public String getSourceFlag() {
		return sourceFlag;
	}

	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}

	public String getDisbilityCertNo() {
		return disbilityCertNo;
	}

	public void setDisbilityCertNo(String disbilityCertNo) {
		this.disbilityCertNo = disbilityCertNo;
	}

	public List<SchemeAppFamilyDetailsDto> getOwnerFamilydetailDTO() {
		return ownerFamilydetailDTO;
	}

	public void setOwnerFamilydetailDTO(List<SchemeAppFamilyDetailsDto> ownerFamilydetailDTO) {
		this.ownerFamilydetailDTO = ownerFamilydetailDTO;
	}

	public String getSwdward1() {
		return swdward1;
	}

	public String getSwdward2() {
		return swdward2;
	}

	public String getSwdward3() {
		return swdward3;
	}

	public String getSwdward4() {
		return swdward4;
	}

	public String getSwdward5() {
		return swdward5;
	}

	public void setSwdward1(String swdward1) {
		this.swdward1 = swdward1;
	}

	public void setSwdward2(String swdward2) {
		this.swdward2 = swdward2;
	}

	public void setSwdward3(String swdward3) {
		this.swdward3 = swdward3;
	}

	public void setSwdward4(String swdward4) {
		this.swdward4 = swdward4;
	}

	public String getServDesc() {
		return servDesc;
	}

	public void setServDesc(String servDesc) {
		this.servDesc = servDesc;
	}

	public void setSwdward5(String swdward5) {
		this.swdward5 = swdward5;
	}

	public String getBenfitedAmt() {
		return benfitedAmt;
	}

	public void setBenfitedAmt(String benfitedAmt) {
		this.benfitedAmt = benfitedAmt;
	}

	public Long getSubSchemeName() {
		return subSchemeName;
	}

	public void setSubSchemeName(Long subSchemeName) {
		this.subSchemeName = subSchemeName;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
