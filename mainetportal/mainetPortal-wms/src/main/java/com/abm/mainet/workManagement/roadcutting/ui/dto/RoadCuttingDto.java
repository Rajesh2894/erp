package com.abm.mainet.workManagement.roadcutting.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;

/**
 * @author satish.rathore
 *
 */
public class RoadCuttingDto extends RequestDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3578916292682745367L;
	
	/*applicant Information*/
	private Long rcId;
	private String applicantCompName1;
	private String companyAddress1;
	private String personName1;
	private String personAddress1;
	private BigInteger personMobileNo1;
	private String faxNumber1;
	private BigInteger telephoneNo1;
	private String personEmailId1;
	/*local office detail*/
	private String companyName2;
	private  String companyAddress2;
	private String personAddress2;
	private String personName2;
	private String faxNumber2;
	private BigInteger telephoneNo2;
	private BigInteger personMobileNo2;
	private String personEmailId2;
	/*contractor datails*/
	private String contractorName;
	private String contractorAddress;
	private String contractorContactPerName;
	private BigInteger contracterContactPerMobileNo;
	private String contractorEmailId;
	/*road/route details*/
	List<RoadRouteDetailsDto> roadList =new ArrayList<>();
	/*total budget and estimated amount*/ 
	private BigDecimal totalCostOfproject;
	private BigDecimal estimteForRoadDamgCharge;
	/*ward Zone fields*/
    private Long codWard1;
    private Long codWard2;
    private Long codWard3;
    private Long codWard4;
    private Long codWard5;
    
    
    
    private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Date lModDate;
	private Date apmApplicationDate;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private Map<Long, Double> chargesMap = new HashMap<>();
	private Long wmsDeptId;
	private String rcAssignEng;
	
	
	private List<DocumentDetailsVO> projectLocation;

	/**
	 * @return the apmApplicationDate
	 */
	public Date getApmApplicationDate() {
		return apmApplicationDate;
	}

	/**
	 * @param apmApplicationDate the apmApplicationDate to set
	 */
	public void setApmApplicationDate(Date apmApplicationDate) {
		this.apmApplicationDate = apmApplicationDate;
	}
	/**
	 * @return the wmsDeptId
	 */
	public Long getWmsDeptId() {
		return wmsDeptId;
	}
	/**
	 * @param wmsDeptId the wmsDeptId to set
	 */
	public void setWmsDeptId(Long wmsDeptId) {
		this.wmsDeptId = wmsDeptId;
	}
	/**
	 * @return the applicantCompName1
	 */
	public String getApplicantCompName1() {
		return applicantCompName1;
	}
	/**
	 * @return the chargesMap
	 */
	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}
	/**
	 * @param chargesMap the chargesMap to set
	 */
	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
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
	 * @param telephoneNo1 the telephoneNo1 to set
	 */
	public void setTelephoneNo1(BigInteger telephoneNo1) {
		this.telephoneNo1 = telephoneNo1;
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
	 * @return the roadList
	 */
	public List<RoadRouteDetailsDto> getRoadList() {
		return roadList;
	}
	/**
	 * @param roadList the roadList to set
	 */
	public void setRoadList(List<RoadRouteDetailsDto> roadList) {
		this.roadList = roadList;
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
	 * @return the telephoneNo1
	 */
	public BigInteger getTelephoneNo1() {
		return telephoneNo1;
	}
	/**
	 * @return the codWard1
	 */
	public Long getCodWard1() {
		return codWard1;
	}
	/**
	 * @param codWard1 the codWard1 to set
	 */
	public void setCodWard1(Long codWard1) {
		this.codWard1 = codWard1;
	}
	/**
	 * @return the codWard2
	 */
	public Long getCodWard2() {
		return codWard2;
	}
	/**
	 * @param codWard2 the codWard2 to set
	 */
	public void setCodWard2(Long codWard2) {
		this.codWard2 = codWard2;
	}
	/**
	 * @return the codWard3
	 */
	public Long getCodWard3() {
		return codWard3;
	}
	/**
	 * @param codWard3 the codWard3 to set
	 */
	public void setCodWard3(Long codWard3) {
		this.codWard3 = codWard3;
	}
	/**
	 * @return the codWard4
	 */
	public Long getCodWard4() {
		return codWard4;
	}
	/**
	 * @param codWard4 the codWard4 to set
	 */
	public void setCodWard4(Long codWard4) {
		this.codWard4 = codWard4;
	}
	/**
	 * @return the codWard5
	 */
	public Long getCodWard5() {
		return codWard5;
	}
	/**
	 * @param codWard5 the codWard5 to set
	 */
	public void setCodWard5(Long codWard5) {
		this.codWard5 = codWard5;
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
	 * @return the applicantDetailDto
	 */
	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}
	/**
	 * @param applicantDetailDto the applicantDetailDto to set
	 */
	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}
	/**
	 * @return the lModDate
	 */
	public Date getlModDate() {
		return lModDate;
	}
	/**
	 * @param lModDate the lModDate to set
	 */
	public void setlModDate(Date lModDate) {
		this.lModDate = lModDate;
	}
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
	

	public String getRcAssignEng() {
		return rcAssignEng;
	}

	public void setRcAssignEng(String rcAssignEng) {
		this.rcAssignEng = rcAssignEng;
	}

	/**
	 * @return the projectLocation
	 */
	public List<DocumentDetailsVO> getProjectLocation() {
		return projectLocation;
	}

	/**
	 * @param projectLocation the projectLocation to set
	 */
	public void setProjectLocation(List<DocumentDetailsVO> projectLocation) {
		this.projectLocation = projectLocation;
	}
	

}
