/**
 * 
 */
package com.abm.mainet.workManagement.roadcutting.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_WMS_ROAD_CUTTING")
public class RoadCuttingEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6823024212950896723L;
	/* applicant Information */
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "RC_ID ", nullable = true)
	private Long rcId;
	@Column(name = "RC_COMNAME", nullable = true)
	private String applicantCompName1;
	@Column(name = "RC_COMMOFFADD", nullable = true)
	private String companyAddress1;
	@Column(name = "RC_COMREPNAME", nullable = true)
	private String personName1;
	@Column(name = "RC_COMREPADD", nullable = true)
	private String personAddress1;
	@Column(name = "RC_COMFAXNUM", nullable = true)
	private String faxNumber1;
	@Column(name = "RC_COMTELNO", nullable = true)
	private BigInteger telephoneNo1;
	@Column(name = "RC_COMREPMOB", nullable = true)
	private BigInteger personMobileNo1;
	@Column(name = "RC_COMREPEMAIL", nullable = true)
	private String personEmailId1;
	/* local office detail */
	@Column(name = "RC_COMLOCOFFNM", nullable = true)
	private String companyName2;
	@Column(name = "RC_COMLOCOFFADD", nullable = true)
	private String companyAddress2;
	@Column(name = "RC_COMLOCREPNAME", nullable = true)
	private String personName2;
	@Column(name = "RC_COMLOCREPADD", nullable = true)
	private String personAddress2;
	@Column(name = "RC_COMLOCREPMOB", nullable = true)
	private BigInteger personMobileNo2;
	@Column(name = "RC_COMLOCFAXNUM", nullable = true)
	private String faxNumber2;
	@Column(name = "RC_COMLOCTELNO", nullable = true)
	private BigInteger telephoneNo2;
	@Column(name = "RC_COMLOCREPEMAIL", nullable = true)
	private String personEmailId2;
	/* contractor datails */
	@Column(name = "RC_CONTNM", nullable = true)
	private String contractorName;
	@Column(name = "RC_CONTADD", nullable = true)
	private String contractorAddress;
	@Column(name = "RC_CONTACTPNM", nullable = true)
	private String contractorContactPerName;
	@Column(name = "RC_CONTACTPMOB", nullable = true)
	private BigInteger contracterContactPerMobileNo;
	@Column(name = "RC_CONTACTPEML", nullable = true)
	private String contractorEmailId;
	@Column(name = "RC_COSTPROJECT", nullable = true)
	private BigDecimal totalCostOfproject;
	@Column(name = "RC_ROADDAMAGE", nullable = true)
	private BigDecimal estimteForRoadDamgCharge;
	@Column(name = "RC_ASSIGN_ENG", nullable = true)
	private String rcAssignEng;
	
	/**Applicant Info  */
	
	@Column(name = "APPLICANT_NAME", nullable = true)
	private String applicantName;
	@Column(name = "APPLICANT_M_NAME", nullable = true)
	private String applicantMName;
	@Column(name = "APPLICANT_L_NAME", nullable = true)
	private String applicantLName;
	@Column(name = "APPLICANT_ADD", nullable = true)
	private String applicantAddress;
	@Column(name = "APPLICANT_HOUSENO", nullable = true)
	private String applicantHouseNo;
	@Column(name = "APPLICANT_AREA_NAME", nullable = true)
	private String applicantAreaName;
	@Column(name = "APPLICANT_MOBILE", nullable = true)
	private Long applicantMobileNo;
	
	
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;
	//@OneToMany(fetch = FetchType.LAZY, targetEntity = RoadRouteDetailEntity.class, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "roadCuttingEntity", cascade = CascadeType.ALL)
	private List<RoadRouteDetailEntity> roadRouteList;
	
	
	@Column(name = "COD_WARD1")
	private Long codWard1;
	@Column(name = "COD_WARD2")
	private Long codWard2;
	@Column(name = "COD_WARD3")
	private Long codWard3;
	@Column(name = "COD_WARD4")
	private Long codWard4;
	@Column(name = "COD_WARD5")
	private Long codWard5;
	
	@Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;
	
	@Column(name = "RC_COMREPALTMOB")
	private Long alterContact1;
	@Column(name = "RC_COMLOCREPALTMOB")
	private Long alterContact2;
	
	@Column(name = "PURPOSE_VALUE")
	private String purposeValue;
	@Column(name = "PURPOSE_ID")
	private Long purpose;
	
	@Column(name = "REF_ID")
	private String refId;
	
	

	/**
	 * @return the rcId
	 */
	public Long getRcId() {
		return rcId;
	}

	/**
	 * @param rcId the rcId to set
	 */
	public void setRcId(Long rcId) {
		this.rcId = rcId;
	}

	/**
	 * @return the applicantCompName1
	 */
	public String getApplicantCompName1() {
		return applicantCompName1;
	}

	/**
	 * @param applicantCompName1 the applicantCompName1 to set
	 */
	public void setApplicantCompName1(String applicantCompName1) {
		this.applicantCompName1 = applicantCompName1;
	}

	/**
	 * @return the companyAddress1
	 */
	public String getCompanyAddress1() {
		return companyAddress1;
	}

	/**
	 * @param companyAddress1 the companyAddress1 to set
	 */
	public void setCompanyAddress1(String companyAddress1) {
		this.companyAddress1 = companyAddress1;
	}

	/**
	 * @return the personName1
	 */
	public String getPersonName1() {
		return personName1;
	}

	/**
	 * @param personName1 the personName1 to set
	 */
	public void setPersonName1(String personName1) {
		this.personName1 = personName1;
	}

	/**
	 * @return the personAddress1
	 */
	public String getPersonAddress1() {
		return personAddress1;
	}

	/**
	 * @param personAddress1 the personAddress1 to set
	 */
	public void setPersonAddress1(String personAddress1) {
		this.personAddress1 = personAddress1;
	}

	/**
	 * @return the faxNumber1
	 */
	public String getFaxNumber1() {
		return faxNumber1;
	}

	/**
	 * @param faxNumber1 the faxNumber1 to set
	 */
	public void setFaxNumber1(String faxNumber1) {
		this.faxNumber1 = faxNumber1;
	}

	/**
	 * @return the telephoneNo1
	 */
	public BigInteger getTelephoneNo1() {
		return telephoneNo1;
	}

	/**
	 * @param telephoneNo1 the telephoneNo1 to set
	 */
	public void setTelephoneNo1(BigInteger telephoneNo1) {
		this.telephoneNo1 = telephoneNo1;
	}

	/**
	 * @return the personMobileNo1
	 */
	public BigInteger getPersonMobileNo1() {
		return personMobileNo1;
	}

	/**
	 * @param personMobileNo1 the personMobileNo1 to set
	 */
	public void setPersonMobileNo1(BigInteger personMobileNo1) {
		this.personMobileNo1 = personMobileNo1;
	}

	

	

	/**
	 * @return the personEmailId1
	 */
	public String getPersonEmailId1() {
		return personEmailId1;
	}

	/**
	 * @param personEmailId1 the personEmailId1 to set
	 */
	public void setPersonEmailId1(String personEmailId1) {
		this.personEmailId1 = personEmailId1;
	}

	/**
	 * @return the companyName2
	 */
	public String getCompanyName2() {
		return companyName2;
	}

	/**
	 * @param companyName2 the companyName2 to set
	 */
	public void setCompanyName2(String companyName2) {
		this.companyName2 = companyName2;
	}

	/**
	 * @return the companyAddress2
	 */
	public String getCompanyAddress2() {
		return companyAddress2;
	}

	/**
	 * @param companyAddress2 the companyAddress2 to set
	 */
	public void setCompanyAddress2(String companyAddress2) {
		this.companyAddress2 = companyAddress2;
	}

	/**
	 * @return the personName2
	 */
	public String getPersonName2() {
		return personName2;
	}

	/**
	 * @param personName2 the personName2 to set
	 */
	public void setPersonName2(String personName2) {
		this.personName2 = personName2;
	}

	/**
	 * @return the personAddress2
	 */
	public String getPersonAddress2() {
		return personAddress2;
	}

	/**
	 * @param personAddress2 the personAddress2 to set
	 */
	public void setPersonAddress2(String personAddress2) {
		this.personAddress2 = personAddress2;
	}

	/**
	 * @return the personMobileNo2
	 */
	public BigInteger getPersonMobileNo2() {
		return personMobileNo2;
	}

	/**
	 * @param personMobileNo2 the personMobileNo2 to set
	 */
	public void setPersonMobileNo2(BigInteger personMobileNo2) {
		this.personMobileNo2 = personMobileNo2;
	}

	/**
	 * @return the faxNumber2
	 */
	public String getFaxNumber2() {
		return faxNumber2;
	}

	/**
	 * @param faxNumber2 the faxNumber2 to set
	 */
	public void setFaxNumber2(String faxNumber2) {
		this.faxNumber2 = faxNumber2;
	}

	/**
	 * @return the telephoneNo2
	 */
	public BigInteger getTelephoneNo2() {
		return telephoneNo2;
	}

	/**
	 * @param telephoneNo2 the telephoneNo2 to set
	 */
	public void setTelephoneNo2(BigInteger telephoneNo2) {
		this.telephoneNo2 = telephoneNo2;
	}





	/**
	 * @return the personEmailId2
	 */
	public String getPersonEmailId2() {
		return personEmailId2;
	}

	/**
	 * @param personEmailId2 the personEmailId2 to set
	 */
	public void setPersonEmailId2(String personEmailId2) {
		this.personEmailId2 = personEmailId2;
	}

	/**
	 * @return the contractorName
	 */
	public String getContractorName() {
		return contractorName;
	}

	/**
	 * @param contractorName the contractorName to set
	 */
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	/**
	 * @return the contractorAddress
	 */
	public String getContractorAddress() {
		return contractorAddress;
	}

	/**
	 * @param contractorAddress the contractorAddress to set
	 */
	public void setContractorAddress(String contractorAddress) {
		this.contractorAddress = contractorAddress;
	}

	/**
	 * @return the contractorContactPerName
	 */
	public String getContractorContactPerName() {
		return contractorContactPerName;
	}

	/**
	 * @param contractorContactPerName the contractorContactPerName to set
	 */
	public void setContractorContactPerName(String contractorContactPerName) {
		this.contractorContactPerName = contractorContactPerName;
	}

	/**
	 * @return the contracterContactPerMobileNo
	 */
	public BigInteger getContracterContactPerMobileNo() {
		return contracterContactPerMobileNo;
	}

	/**
	 * @param contracterContactPerMobileNo the contracterContactPerMobileNo to set
	 */
	public void setContracterContactPerMobileNo(BigInteger contracterContactPerMobileNo) {
		this.contracterContactPerMobileNo = contracterContactPerMobileNo;
	}

	
	

	/**
	 * @return the contractorEmailId
	 */
	public String getContractorEmailId() {
		return contractorEmailId;
	}

	/**
	 * @param contractorEmailId the contractorEmailId to set
	 */
	public void setContractorEmailId(String contractorEmailId) {
		this.contractorEmailId = contractorEmailId;
	}

	/**
	 * @return the totalCostOfproject
	 */
	public BigDecimal getTotalCostOfproject() {
		return totalCostOfproject;
	}

	/**
	 * @param totalCostOfproject the totalCostOfproject to set
	 */
	public void setTotalCostOfproject(BigDecimal totalCostOfproject) {
		this.totalCostOfproject = totalCostOfproject;
	}

	

	/**
	 * @return the estimteForRoadDamgCharge
	 */
	public BigDecimal getEstimteForRoadDamgCharge() {
		return estimteForRoadDamgCharge;
	}

	/**
	 * @param estimteForRoadDamgCharge the estimteForRoadDamgCharge to set
	 */
	public void setEstimteForRoadDamgCharge(BigDecimal estimteForRoadDamgCharge) {
		this.estimteForRoadDamgCharge = estimteForRoadDamgCharge;
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

	/**
	 * @return the roadRouteList
	 */
	public List<RoadRouteDetailEntity> getRoadRouteList() {
		return roadRouteList;
	}

	/**
	 * @param roadRouteList the roadRouteList to set
	 */
	public void setRoadRouteList(List<RoadRouteDetailEntity> roadRouteList) {
		this.roadRouteList = roadRouteList;
	}
	
	

	public Long getCodWard1() {
		return codWard1;
	}

	public void setCodWard1(Long codWard1) {
		this.codWard1 = codWard1;
	}

	public Long getCodWard2() {
		return codWard2;
	}

	public void setCodWard2(Long codWard2) {
		this.codWard2 = codWard2;
	}

	public Long getCodWard3() {
		return codWard3;
	}

	public void setCodWard3(Long codWard3) {
		this.codWard3 = codWard3;
	}

	public Long getCodWard4() {
		return codWard4;
	}

	public void setCodWard4(Long codWard4) {
		this.codWard4 = codWard4;
	}

	public Long getCodWard5() {
		return codWard5;
	}

	public void setCodWard5(Long codWard5) {
		this.codWard5 = codWard5;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public static String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_ROAD_CUTTING", "RC_ID" };
	}

	/**
	 * @return the rcAssignEng
	 */
	public String getRcAssignEng() {
		return rcAssignEng;
	}

	/**
	 * @param rcAssignEng the rcAssignEng to set
	 */
	public void setRcAssignEng(String rcAssignEng) {
		this.rcAssignEng = rcAssignEng;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantAddress() {
		return applicantAddress;
	}

	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}

	public String getApplicantHouseNo() {
		return applicantHouseNo;
	}

	public void setApplicantHouseNo(String applicantHouseNo) {
		this.applicantHouseNo = applicantHouseNo;
	}

	public String getApplicantAreaName() {
		return applicantAreaName;
	}

	public void setApplicantAreaName(String applicantAreaName) {
		this.applicantAreaName = applicantAreaName;
	}

	public Long getApplicantMobileNo() {
		return applicantMobileNo;
	}

	public void setApplicantMobileNo(Long applicantMobileNo) {
		this.applicantMobileNo = applicantMobileNo;
	}

	public String getApplicantMName() {
		return applicantMName;
	}

	public void setApplicantMName(String applicantMName) {
		this.applicantMName = applicantMName;
	}

	public String getApplicantLName() {
		return applicantLName;
	}

	public void setApplicantLName(String applicantLName) {
		this.applicantLName = applicantLName;
	}

	public Long getAlterContact1() {
		return alterContact1;
	}

	public void setAlterContact1(Long alterContact1) {
		this.alterContact1 = alterContact1;
	}

	public Long getAlterContact2() {
		return alterContact2;
	}

	public void setAlterContact2(Long alterContact2) {
		this.alterContact2 = alterContact2;
	}

	public String getPurposeValue() {
		return purposeValue;
	}

	public void setPurposeValue(String purposeValue) {
		this.purposeValue = purposeValue;
	}

	public Long getPurpose() {
		return purpose;
	}

	public void setPurpose(Long purpose) {
		this.purpose = purpose;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	
	
	
	
}
