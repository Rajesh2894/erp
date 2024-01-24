/**
 * 
 */
package com.abm.mainet.socialsecurity.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author priti.singh
 *
 */

@Entity
@Table(name = "TB_SWD_SCHEME_APPLICATION")
public class SocialSecurityApplicationForm implements Serializable {

	private static final long serialVersionUID = -6664054119735051186L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "SAPI_ID", nullable = false)
	private Long applicationId;

	@Column(name = "SAPI_NAME", nullable = false)
	private String nameofApplicant;

	@Column(name = "SAPI_DATE")
	private Date applicationDob;

	@Column(name = "SAPI_AGE", nullable = true)
	private Long ageason;

	@Column(name = "SAPI_GEN", nullable = true)
	private Long genderId;

	@Column(name = "SAPI_ADDRESS", nullable = true)
	private String applicantAdress;

	@Column(name = "SAPI_PINCODE", nullable = true)
	private Long pinCode;

	@Column(name = "SAPI_MOBILE", nullable = true)
	private Long mobNum;

	@Column(name = "SAPI_EDUCATION", nullable = true)
	private Long educationId;

	@Column(name = "SAPI_CLASS", nullable = true)
	private Long classs;

	@Column(name = "SAPI_MARTIALSTATUS", nullable = true)
	private Long maritalStatusId;

	@Column(name = "SAPI_CATEGORY", nullable = true)
	private Long categoryId;

	@Column(name = "SAPI_ANNUAL", nullable = true)
	private BigDecimal annualIncome;

	@Column(name = "SAPI_DISABILITY", nullable = true)
	private Long typeofDisId;

	@Column(name = "SAPI_PERCENTAGE", nullable = true)
	private Long percenrofDis;

	@Column(name = "SAPI_BPL", nullable = true)
	private String bplid;

	@Column(name = "SAPI_INSPECTIONYE", nullable = true)
	private Long bplinspectyr;

	@Column(name = "SAPI_FAMILYID", nullable = true)
	private String bplfamily;

	@Column(name = "SAPI_BANKID", nullable = true)
	private Long bankNameId;

	@Column(name = "SAPI_ACCOUNTID", nullable = true)
	private Long accountNumber;

	@Column(name = "SAPI_BRANCH_ID", nullable = true)
	private Long branchname;

	@Column(name = "SAPI_IFSC_ID", nullable = true)
	private String ifsccode;

	@Column(name = "SAPI_APP_FNAME", nullable = true)
	private String nameofFather;

	@Column(name = "SAPI_APP_MNAME", nullable = true)
	private String nameofMother;

	@Column(name = "SAPI_APP_CONTNO", nullable = true)
	private Long contactNumber;

	@Column(name = "SAPI_APP_FINC", nullable = true)
	private String detailsoffamIncomeSource;

	@Column(name = "SAPI_APP_ANNUAL", nullable = true)
	private BigDecimal annualIncomeoffam;

	@Column(name = "APM_APPLICATION_ID", nullable = true)
	private String apmApplicationId;

	@Column(name = "SAPI_STATUS", nullable = true)
	private String sapiStatus;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	@Column(name = "SAPI_LAST_PAYMENTDT")
	private Date lastPaymentDate;

	@Column(name = "SAPI_LAST_PAYMENTNO", nullable = true)
	private Long referenceno;

	@Column(name = "SAIP_TR_TY", nullable = true)
	private String datalegacyflag;

	@Column(name = "BENEFICIARY_NUMBER", nullable = true)
	private String beneficiarynumber;

	@Column(name = "VALID_TO_DATE")
	private Date validtoDate;

	@Column(name = "SAPI_LAST_LIFECERTDATE")
	private Date lastDateofLifeCerti;

	@Column(name = "SDSCH_SER_ID", nullable = true)
	private Long selectSchemeName;

	@Column(name = "SAPI_CANCEL_RESON", nullable = true)
	private String pensionCancelReason;

	// @Temporal(TemporalType.DATE)
	@Column(name = "SAPI_CANCEL_DATE", nullable = true)
	private Date pensionCancelDate;

	@Column(name = "SAPI_UID", nullable = true)
	private String aadharCard;

	@Column(name = "ELECTION_NO", nullable = true)
	private String electionIdNo;

	@Column(name = "SWD_WARD1", nullable = true)
	private String swdward1;

	@Column(name = "SWD_WARD2", nullable = true)
	private String swdward2;

	@Column(name = "SWD_WARD3", nullable = true)
	private String swdward3;

	@Column(name = "SWD_WARD4", nullable = true)
	private String swdward4;

	@Column(name = "SWD_WARD5", nullable = true)
	private String swdward5;

	@Column(name = "DISABILITY_CERT_NO", nullable = true)
	private String disbilityCertNo;
	
	@Column(name = "SDSCH_SUB_SER_ID", nullable = true)
	private Long subSchemeName;
	
	@Column(name = "STATUS", nullable = true, length = 1)
	private String status;
	
	@Column(name = "EMAIL")
	private String email;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationId", cascade = CascadeType.ALL)
	private List<SchemeApplicantFamilyDetEntity> ownerFamilyDetails = new ArrayList<>();

	public static String[] getPkValues() {
		return new String[] { "SWD", "TB_SWD_SCHEME_APPLICATION", "SAPI_ID" };
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getNameofApplicant() {
		return nameofApplicant;
	}

	public void setNameofApplicant(String nameofApplicant) {
		this.nameofApplicant = nameofApplicant;
	}

	public Date getApplicationDob() {
		return applicationDob;
	}

	public void setApplicationDob(Date applicationDob) {
		this.applicationDob = applicationDob;
	}

	public Long getAgeason() {
		return ageason;
	}

	public void setAgeason(Long ageason) {
		this.ageason = ageason;
	}

	public Long getGenderId() {
		return genderId;
	}

	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	public String getApplicantAdress() {
		return applicantAdress;
	}

	public void setApplicantAdress(String applicantAdress) {
		this.applicantAdress = applicantAdress;
	}

	public Long getPinCode() {
		return pinCode;
	}

	public void setPinCode(Long pinCode) {
		this.pinCode = pinCode;
	}

	public Long getMobNum() {
		return mobNum;
	}

	public void setMobNum(Long mobNum) {
		this.mobNum = mobNum;
	}

	public Long getEducationId() {
		return educationId;
	}

	public void setEducationId(Long educationId) {
		this.educationId = educationId;
	}

	public Long getClasss() {
		return classs;
	}

	public void setClasss(Long classs) {
		this.classs = classs;
	}

	public Long getMaritalStatusId() {
		return maritalStatusId;
	}

	public void setMaritalStatusId(Long maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}

	public Long getTypeofDisId() {
		return typeofDisId;
	}

	public void setTypeofDisId(Long typeofDisId) {
		this.typeofDisId = typeofDisId;
	}

	public Long getPercenrofDis() {
		return percenrofDis;
	}

	public void setPercenrofDis(Long percenrofDis) {
		this.percenrofDis = percenrofDis;
	}

	public String getBplid() {
		return bplid;
	}

	public void setBplid(String bplid) {
		this.bplid = bplid;
	}

	public Long getBplinspectyr() {
		return bplinspectyr;
	}

	public void setBplinspectyr(Long bplinspectyr) {
		this.bplinspectyr = bplinspectyr;
	}

	public String getBplfamily() {
		return bplfamily;
	}

	public void setBplfamily(String bplfamily) {
		this.bplfamily = bplfamily;
	}

	public Long getBankNameId() {
		return bankNameId;
	}

	public void setBankNameId(Long bankNameId) {
		this.bankNameId = bankNameId;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getBranchname() {
		return branchname;
	}

	public void setBranchname(Long branchname) {
		this.branchname = branchname;
	}

	public String getIfsccode() {
		return ifsccode;
	}

	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}

	public String getNameofFather() {
		return nameofFather;
	}

	public void setNameofFather(String nameofFather) {
		this.nameofFather = nameofFather;
	}

	public String getNameofMother() {
		return nameofMother;
	}

	public void setNameofMother(String nameofMother) {
		this.nameofMother = nameofMother;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getDetailsoffamIncomeSource() {
		return detailsoffamIncomeSource;
	}

	public void setDetailsoffamIncomeSource(String detailsoffamIncomeSource) {
		this.detailsoffamIncomeSource = detailsoffamIncomeSource;
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

	public BigDecimal getAnnualIncomeoffam() {
		return annualIncomeoffam;
	}

	public void setAnnualIncomeoffam(BigDecimal annualIncomeoffam) {
		this.annualIncomeoffam = annualIncomeoffam;
	}

	/**
	 * @return the apmApplicationId
	 */
	public String getApmApplicationId() {
		return apmApplicationId;
	}

	/**
	 * @param apmApplicationId the apmApplicationId to set
	 */
	public void setApmApplicationId(String apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public String getSapiStatus() {
		return sapiStatus;
	}

	public void setSapiStatus(String sapiStatus) {
		this.sapiStatus = sapiStatus;
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
	 * @return the referenceno
	 */
	public Long getReferenceno() {
		return referenceno;
	}

	/**
	 * @param referenceno the referenceno to set
	 */
	public void setReferenceno(Long referenceno) {
		this.referenceno = referenceno;
	}

	/**
	 * @return the datalegacyflag
	 */
	public String getDatalegacyflag() {
		return datalegacyflag;
	}

	/**
	 * @param datalegacyflag the datalegacyflag to set
	 */
	public void setDatalegacyflag(String datalegacyflag) {
		this.datalegacyflag = datalegacyflag;
	}

	/**
	 * @return the beneficiarynumber
	 */
	public String getBeneficiarynumber() {
		return beneficiarynumber;
	}

	/**
	 * @param beneficiarynumber the beneficiarynumber to set
	 */
	public void setBeneficiarynumber(String beneficiarynumber) {
		this.beneficiarynumber = beneficiarynumber;
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

	public String getElectionIdNo() {
		return electionIdNo;
	}

	public void setElectionIdNo(String electionIdNo) {
		this.electionIdNo = electionIdNo;
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

	public String getDisbilityCertNo() {
		return disbilityCertNo;
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

	public void setSwdward5(String swdward5) {
		this.swdward5 = swdward5;
	}

	public void setDisbilityCertNo(String disbilityCertNo) {
		this.disbilityCertNo = disbilityCertNo;
	}

	public List<SchemeApplicantFamilyDetEntity> getOwnerFamilyDetails() {
		return ownerFamilyDetails;
	}

	public void setOwnerFamilyDetails(List<SchemeApplicantFamilyDetEntity> ownerFamilyDetails) {
		this.ownerFamilyDetails = ownerFamilyDetails;
	}

	public Long getSubSchemeName() {
		return subSchemeName;
	}

	public void setSubSchemeName(Long subSchemeName) {
		this.subSchemeName = subSchemeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
