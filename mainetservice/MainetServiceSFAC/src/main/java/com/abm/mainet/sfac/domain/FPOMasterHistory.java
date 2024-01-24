/**
 * 
 */
package com.abm.mainet.sfac.domain;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_SFAC_FPO_MASTER_HIST")
public class FPOMasterHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 304649106783931985L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FPO_ID_H", nullable = false)
	private Long fpoIdH;

	@Column(name = "FPO_ID", nullable = false)
	private Long fpoId;

	@Column(name = "CBBO_NAME")
	private String cbboName;

	@Column(name = "IA_NAME")
	private String iaName;

	@Column(name = "SDB1")
	private Long sdb1;

	@Column(name = "SDB2")
	private Long sdb2;

	@Column(name = "SDB3")
	private Long sdb3;

	@Column(name = "SPECIAL_ALLOCATION")
	private Long specialAllocation;

	@Column(name = "DMC_APPROVAL")
	private Long dmcApproval;

	@Column(name = "FPO_NAME")
	private String fpoName;

	@Column(name = "FPO_REG_NO")
	private String fpoRegNo;

	@Column(name = "REG_ACT")
	private Long regAct;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_INCORPORATION")
	private Date dateIncorporation;

	@Column(name = "FPO_AGE")
	private Long fpoAge;

	@Column(name = "FPO_OFF_ADDR")
	private String fpoOffAddr;

	@Column(name = "FPO_PIN_CODE")
	private Long fpoPinCode;

	@Column(name = "FPO_ADHAR_NO")
	private String fpoAdharNo;

	@Column(name = "FPO_TAN_NO")
	private String fpoTanNo;

	@Column(name = "FPO_PAN_NO")
	private String fpoPanNo;

	@Column(name = "NO_FARM_MOB")
	private Long noFarmMob;

	@Column(name = "NO_SHARE_MEM")
	private Long noShareMem;

	@Column(name = "PAIDUP_CAPITAL")
	private Long paidupCapital;

	@Column(name = "SHARE_CAPITAL")
	private Long shareCapital;

	@Column(name = "TOTAL_EQUITY_AMT")
	private BigDecimal totalEquityAmt;

	@Column(name = "BASE_LINE_SURVEY")
	private Long baseLineSurvey;

	@Column(name = "IS_WOMEN_CENTRIC", length = 1, nullable = true)
	private String isWomenCentric;

	@Column(name = "COMPANY_REG_NO")
	private String companyRegNo;

	@Column(name = "TYPE_OF_PROM_AGE")
	private Long typeofPromAgen;

	@Column(name = "IA_ID")
	private Long iaId;

	@Column(name = "CBBO_ID")
	private Long cbboId;

	@Column(name = "ALLOCATION_CATEGORY")
	private Long allocationCategory;

	@Column(name = "ALLOCATION_SUB_CATEGORY", nullable = true)
	private Long allocationSubCategory;

	@Column(name = "STATE_CATEGORY")
	private String statecategory;

	@Column(name = "REGION")
	private String region;

	@Column(name = "IS_ASPIRATIONAL_DIST")
	private String isAspirationalDist;

	@Column(name = "IS_TRIBAL_DIST")
	private String isTribalDist;

	@Column(name = "ODOP_CULTIVATION", nullable = true)
	private Long odopCultivation;

	@Column(name = "UDYOG_AADHAR_APPLICABLE", length = 1, nullable = true)
	private String udyogAadharApplicable;

	@Column(name = "UDYOG_AADHAR_NO", nullable = true)
	private String udyogAadharNo;

	@Column(name = " UDYOG_AADHAR_DATE", nullable = true)
	private Date udyogAadharDate;

	@Column(name = "OFFICE_ADDRESS", nullable = true)
	private String officeAddress;

	@Column(name = "OFFICE_VILLAGE_NAME", nullable = true)
	private String officeVillageName;

	@Column(name = "FPO_POST_OFFICE", nullable = true)
	private String fpoPostOffice;

	@Column(name = "OFFICE_PIN_CODE", nullable = true)
	private String officePinCode;

	@Column(name = "ODOP")
	private String odop;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "IA_ALLOCATION_YEAR")
	private Long iaAlcYear;

	@Column(name = "CBBO_ALLOCATION_YEAR")
	private Long cbboAlcYear;

	@Column(name = "AUTHORIZE_CAPITAL")
	private Long authorizeCapital;

	@Column(name = "GSTIN")
	private String gstin;

	@Column(name = "TOTAL_AREA_COV_KHARIF", nullable = true)
	private String totalAreaCovKharif;

	@Column(name = "TOTAL_AREA_COV_RABI", nullable = true)
	private String totalAreaCovRabi;

	@Column(name = "DEPTID", nullable = true)
	private Long deptId;

	@Column(name = "APPL_PENDING_DATE")
	private Date appPendingDate;

	@Column(name = "REGISTERED_ON_ENAM")
	private Long registeredOnEnam;

	@Column(name = "USER_ID_ENAM")
	private String userIdEnam;

	@Column(name = "APPROVED", length = 1, nullable = true)
	private String approved;

	@Column(name = "AC_IN_STATUS", nullable = true)
	private String activeInactiveStatus;

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

	@Column(name = "SHARED_CAPITAL", nullable = true)
	private Long sharedCapital;

	@Column(name = "NORTHEAST_REGION", nullable = true)
	private Long northEastRegion;

	@Column(name = "H_STATUS", length = 1)
	private String historyStatus;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "APP_STATUS")
	private String appStatus;

	@Column(name = "REMARK")
	private String remark;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterHistEntity", cascade = CascadeType.ALL)
	List<FPOMasterDetailHistory> fpoDetHist = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterHistEntity", cascade = CascadeType.ALL)
	List<FPOAdministrativeDetHistory> fpoAdminDetHist = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterHistEntity", cascade = CascadeType.ALL)
	List<FPOBankDetailHistory> fpoBankDetHist = new ArrayList<>();

	/**
	 * @return the fpoIdH
	 */
	public Long getFpoIdH() {
		return fpoIdH;
	}

	/**
	 * @param fpoIdH the fpoIdH to set
	 */
	public void setFpoIdH(Long fpoIdH) {
		this.fpoIdH = fpoIdH;
	}

	/**
	 * @return the fpoId
	 */
	public Long getFpoId() {
		return fpoId;
	}

	/**
	 * @param fpoId the fpoId to set
	 */
	public void setFpoId(Long fpoId) {
		this.fpoId = fpoId;
	}

	/**
	 * @return the cbboName
	 */
	public String getCbboName() {
		return cbboName;
	}

	/**
	 * @param cbboName the cbboName to set
	 */
	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}

	/**
	 * @return the iaName
	 */
	public String getIaName() {
		return iaName;
	}

	/**
	 * @param iaName the iaName to set
	 */
	public void setIaName(String iaName) {
		this.iaName = iaName;
	}

	/**
	 * @return the sdb1
	 */
	public Long getSdb1() {
		return sdb1;
	}

	/**
	 * @param sdb1 the sdb1 to set
	 */
	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}

	/**
	 * @return the sdb2
	 */
	public Long getSdb2() {
		return sdb2;
	}

	/**
	 * @param sdb2 the sdb2 to set
	 */
	public void setSdb2(Long sdb2) {
		this.sdb2 = sdb2;
	}

	/**
	 * @return the sdb3
	 */
	public Long getSdb3() {
		return sdb3;
	}

	/**
	 * @param sdb3 the sdb3 to set
	 */
	public void setSdb3(Long sdb3) {
		this.sdb3 = sdb3;
	}

	/**
	 * @return the specialAllocation
	 */
	public Long getSpecialAllocation() {
		return specialAllocation;
	}

	/**
	 * @param specialAllocation the specialAllocation to set
	 */
	public void setSpecialAllocation(Long specialAllocation) {
		this.specialAllocation = specialAllocation;
	}

	/**
	 * @return the dmcApproval
	 */
	public Long getDmcApproval() {
		return dmcApproval;
	}

	/**
	 * @param dmcApproval the dmcApproval to set
	 */
	public void setDmcApproval(Long dmcApproval) {
		this.dmcApproval = dmcApproval;
	}

	/**
	 * @return the fpoName
	 */
	public String getFpoName() {
		return fpoName;
	}

	/**
	 * @param fpoName the fpoName to set
	 */
	public void setFpoName(String fpoName) {
		this.fpoName = fpoName;
	}

	/**
	 * @return the fpoRegNo
	 */
	public String getFpoRegNo() {
		return fpoRegNo;
	}

	/**
	 * @param fpoRegNo the fpoRegNo to set
	 */
	public void setFpoRegNo(String fpoRegNo) {
		this.fpoRegNo = fpoRegNo;
	}

	/**
	 * @return the regAct
	 */
	public Long getRegAct() {
		return regAct;
	}

	/**
	 * @param regAct the regAct to set
	 */
	public void setRegAct(Long regAct) {
		this.regAct = regAct;
	}

	/**
	 * @return the dateIncorporation
	 */
	public Date getDateIncorporation() {
		return dateIncorporation;
	}

	/**
	 * @param dateIncorporation the dateIncorporation to set
	 */
	public void setDateIncorporation(Date dateIncorporation) {
		this.dateIncorporation = dateIncorporation;
	}

	/**
	 * @return the fpoAge
	 */
	public Long getFpoAge() {
		return fpoAge;
	}

	/**
	 * @param fpoAge the fpoAge to set
	 */
	public void setFpoAge(Long fpoAge) {
		this.fpoAge = fpoAge;
	}

	/**
	 * @return the fpoOffAddr
	 */
	public String getFpoOffAddr() {
		return fpoOffAddr;
	}

	/**
	 * @param fpoOffAddr the fpoOffAddr to set
	 */
	public void setFpoOffAddr(String fpoOffAddr) {
		this.fpoOffAddr = fpoOffAddr;
	}

	/**
	 * @return the fpoPinCode
	 */
	public Long getFpoPinCode() {
		return fpoPinCode;
	}

	/**
	 * @param fpoPinCode the fpoPinCode to set
	 */
	public void setFpoPinCode(Long fpoPinCode) {
		this.fpoPinCode = fpoPinCode;
	}

	/**
	 * @return the fpoAdharNo
	 */
	public String getFpoAdharNo() {
		return fpoAdharNo;
	}

	/**
	 * @param fpoAdharNo the fpoAdharNo to set
	 */
	public void setFpoAdharNo(String fpoAdharNo) {
		this.fpoAdharNo = fpoAdharNo;
	}

	/**
	 * @return the fpoTanNo
	 */
	public String getFpoTanNo() {
		return fpoTanNo;
	}

	/**
	 * @param fpoTanNo the fpoTanNo to set
	 */
	public void setFpoTanNo(String fpoTanNo) {
		this.fpoTanNo = fpoTanNo;
	}

	/**
	 * @return the fpoPanNo
	 */
	public String getFpoPanNo() {
		return fpoPanNo;
	}

	/**
	 * @param fpoPanNo the fpoPanNo to set
	 */
	public void setFpoPanNo(String fpoPanNo) {
		this.fpoPanNo = fpoPanNo;
	}

	/**
	 * @return the noFarmMob
	 */
	public Long getNoFarmMob() {
		return noFarmMob;
	}

	/**
	 * @param noFarmMob the noFarmMob to set
	 */
	public void setNoFarmMob(Long noFarmMob) {
		this.noFarmMob = noFarmMob;
	}

	/**
	 * @return the noShareMem
	 */
	public Long getNoShareMem() {
		return noShareMem;
	}

	/**
	 * @param noShareMem the noShareMem to set
	 */
	public void setNoShareMem(Long noShareMem) {
		this.noShareMem = noShareMem;
	}

	/**
	 * @return the paidupCapital
	 */
	public Long getPaidupCapital() {
		return paidupCapital;
	}

	/**
	 * @param paidupCapital the paidupCapital to set
	 */
	public void setPaidupCapital(Long paidupCapital) {
		this.paidupCapital = paidupCapital;
	}

	/**
	 * @return the shareCapital
	 */
	public Long getShareCapital() {
		return shareCapital;
	}

	/**
	 * @param shareCapital the shareCapital to set
	 */
	public void setShareCapital(Long shareCapital) {
		this.shareCapital = shareCapital;
	}

	/**
	 * @return the totalEquityAmt
	 */
	public BigDecimal getTotalEquityAmt() {
		return totalEquityAmt;
	}

	/**
	 * @param totalEquityAmt the totalEquityAmt to set
	 */
	public void setTotalEquityAmt(BigDecimal totalEquityAmt) {
		this.totalEquityAmt = totalEquityAmt;
	}

	/**
	 * @return the baseLineSurvey
	 */
	public Long getBaseLineSurvey() {
		return baseLineSurvey;
	}

	/**
	 * @param baseLineSurvey the baseLineSurvey to set
	 */
	public void setBaseLineSurvey(Long baseLineSurvey) {
		this.baseLineSurvey = baseLineSurvey;
	}

	/**
	 * @return the isWomenCentric
	 */
	public String getIsWomenCentric() {
		return isWomenCentric;
	}

	/**
	 * @param isWomenCentric the isWomenCentric to set
	 */
	public void setIsWomenCentric(String isWomenCentric) {
		this.isWomenCentric = isWomenCentric;
	}

	/**
	 * @return the companyRegNo
	 */
	public String getCompanyRegNo() {
		return companyRegNo;
	}

	/**
	 * @param companyRegNo the companyRegNo to set
	 */
	public void setCompanyRegNo(String companyRegNo) {
		this.companyRegNo = companyRegNo;
	}

	/**
	 * @return the typeofPromAgen
	 */
	public Long getTypeofPromAgen() {
		return typeofPromAgen;
	}

	/**
	 * @param typeofPromAgen the typeofPromAgen to set
	 */
	public void setTypeofPromAgen(Long typeofPromAgen) {
		this.typeofPromAgen = typeofPromAgen;
	}

	/**
	 * @return the iaId
	 */
	public Long getIaId() {
		return iaId;
	}

	/**
	 * @param iaId the iaId to set
	 */
	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	/**
	 * @return the cbboId
	 */
	public Long getCbboId() {
		return cbboId;
	}

	/**
	 * @param cbboId the cbboId to set
	 */
	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}

	/**
	 * @return the allocationCategory
	 */
	public Long getAllocationCategory() {
		return allocationCategory;
	}

	/**
	 * @param allocationCategory the allocationCategory to set
	 */
	public void setAllocationCategory(Long allocationCategory) {
		this.allocationCategory = allocationCategory;
	}

	/**
	 * @return the allocationSubCategory
	 */
	public Long getAllocationSubCategory() {
		return allocationSubCategory;
	}

	/**
	 * @param allocationSubCategory the allocationSubCategory to set
	 */
	public void setAllocationSubCategory(Long allocationSubCategory) {
		this.allocationSubCategory = allocationSubCategory;
	}

	/**
	 * @return the statecategory
	 */
	public String getStatecategory() {
		return statecategory;
	}

	/**
	 * @param statecategory the statecategory to set
	 */
	public void setStatecategory(String statecategory) {
		this.statecategory = statecategory;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the isAspirationalDist
	 */
	public String getIsAspirationalDist() {
		return isAspirationalDist;
	}

	/**
	 * @param isAspirationalDist the isAspirationalDist to set
	 */
	public void setIsAspirationalDist(String isAspirationalDist) {
		this.isAspirationalDist = isAspirationalDist;
	}

	/**
	 * @return the isTribalDist
	 */
	public String getIsTribalDist() {
		return isTribalDist;
	}

	/**
	 * @param isTribalDist the isTribalDist to set
	 */
	public void setIsTribalDist(String isTribalDist) {
		this.isTribalDist = isTribalDist;
	}

	/**
	 * @return the odopCultivation
	 */
	public Long getOdopCultivation() {
		return odopCultivation;
	}

	/**
	 * @param odopCultivation the odopCultivation to set
	 */
	public void setOdopCultivation(Long odopCultivation) {
		this.odopCultivation = odopCultivation;
	}

	/**
	 * @return the udyogAadharApplicable
	 */
	public String getUdyogAadharApplicable() {
		return udyogAadharApplicable;
	}

	/**
	 * @param udyogAadharApplicable the udyogAadharApplicable to set
	 */
	public void setUdyogAadharApplicable(String udyogAadharApplicable) {
		this.udyogAadharApplicable = udyogAadharApplicable;
	}

	/**
	 * @return the udyogAadharNo
	 */
	public String getUdyogAadharNo() {
		return udyogAadharNo;
	}

	/**
	 * @param udyogAadharNo the udyogAadharNo to set
	 */
	public void setUdyogAadharNo(String udyogAadharNo) {
		this.udyogAadharNo = udyogAadharNo;
	}

	/**
	 * @return the udyogAadharDate
	 */
	public Date getUdyogAadharDate() {
		return udyogAadharDate;
	}

	/**
	 * @param udyogAadharDate the udyogAadharDate to set
	 */
	public void setUdyogAadharDate(Date udyogAadharDate) {
		this.udyogAadharDate = udyogAadharDate;
	}

	/**
	 * @return the officeAddress
	 */
	public String getOfficeAddress() {
		return officeAddress;
	}

	/**
	 * @param officeAddress the officeAddress to set
	 */
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	/**
	 * @return the officeVillageName
	 */
	public String getOfficeVillageName() {
		return officeVillageName;
	}

	/**
	 * @param officeVillageName the officeVillageName to set
	 */
	public void setOfficeVillageName(String officeVillageName) {
		this.officeVillageName = officeVillageName;
	}

	/**
	 * @return the fpoPostOffice
	 */
	public String getFpoPostOffice() {
		return fpoPostOffice;
	}

	/**
	 * @param fpoPostOffice the fpoPostOffice to set
	 */
	public void setFpoPostOffice(String fpoPostOffice) {
		this.fpoPostOffice = fpoPostOffice;
	}

	/**
	 * @return the officePinCode
	 */
	public String getOfficePinCode() {
		return officePinCode;
	}

	/**
	 * @param officePinCode the officePinCode to set
	 */
	public void setOfficePinCode(String officePinCode) {
		this.officePinCode = officePinCode;
	}

	/**
	 * @return the odop
	 */
	public String getOdop() {
		return odop;
	}

	/**
	 * @param odop the odop to set
	 */
	public void setOdop(String odop) {
		this.odop = odop;
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
	 * @return the iaAlcYear
	 */
	public Long getIaAlcYear() {
		return iaAlcYear;
	}

	/**
	 * @param iaAlcYear the iaAlcYear to set
	 */
	public void setIaAlcYear(Long iaAlcYear) {
		this.iaAlcYear = iaAlcYear;
	}

	/**
	 * @return the cbboAlcYear
	 */
	public Long getCbboAlcYear() {
		return cbboAlcYear;
	}

	/**
	 * @param cbboAlcYear the cbboAlcYear to set
	 */
	public void setCbboAlcYear(Long cbboAlcYear) {
		this.cbboAlcYear = cbboAlcYear;
	}

	/**
	 * @return the authorizeCapital
	 */
	public Long getAuthorizeCapital() {
		return authorizeCapital;
	}

	/**
	 * @param authorizeCapital the authorizeCapital to set
	 */
	public void setAuthorizeCapital(Long authorizeCapital) {
		this.authorizeCapital = authorizeCapital;
	}

	/**
	 * @return the gstin
	 */
	public String getGstin() {
		return gstin;
	}

	/**
	 * @param gstin the gstin to set
	 */
	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	/**
	 * @return the totalAreaCovKharif
	 */
	public String getTotalAreaCovKharif() {
		return totalAreaCovKharif;
	}

	/**
	 * @param totalAreaCovKharif the totalAreaCovKharif to set
	 */
	public void setTotalAreaCovKharif(String totalAreaCovKharif) {
		this.totalAreaCovKharif = totalAreaCovKharif;
	}

	/**
	 * @return the totalAreaCovRabi
	 */
	public String getTotalAreaCovRabi() {
		return totalAreaCovRabi;
	}

	/**
	 * @param totalAreaCovRabi the totalAreaCovRabi to set
	 */
	public void setTotalAreaCovRabi(String totalAreaCovRabi) {
		this.totalAreaCovRabi = totalAreaCovRabi;
	}

	/**
	 * @return the deptId
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the appPendingDate
	 */
	public Date getAppPendingDate() {
		return appPendingDate;
	}

	/**
	 * @param appPendingDate the appPendingDate to set
	 */
	public void setAppPendingDate(Date appPendingDate) {
		this.appPendingDate = appPendingDate;
	}

	/**
	 * @return the registeredOnEnam
	 */
	public Long getRegisteredOnEnam() {
		return registeredOnEnam;
	}

	/**
	 * @param registeredOnEnam the registeredOnEnam to set
	 */
	public void setRegisteredOnEnam(Long registeredOnEnam) {
		this.registeredOnEnam = registeredOnEnam;
	}

	/**
	 * @return the userIdEnam
	 */
	public String getUserIdEnam() {
		return userIdEnam;
	}

	/**
	 * @param userIdEnam the userIdEnam to set
	 */
	public void setUserIdEnam(String userIdEnam) {
		this.userIdEnam = userIdEnam;
	}

	/**
	 * @return the approved
	 */
	public String getApproved() {
		return approved;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(String approved) {
		this.approved = approved;
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
	 * @return the sharedCapital
	 */
	public Long getSharedCapital() {
		return sharedCapital;
	}

	/**
	 * @param sharedCapital the sharedCapital to set
	 */
	public void setSharedCapital(Long sharedCapital) {
		this.sharedCapital = sharedCapital;
	}

	/**
	 * @return the northEastRegion
	 */
	public Long getNorthEastRegion() {
		return northEastRegion;
	}

	/**
	 * @param northEastRegion the northEastRegion to set
	 */
	public void setNorthEastRegion(Long northEastRegion) {
		this.northEastRegion = northEastRegion;
	}

	/**
	 * @return the historyStatus
	 */
	public String getHistoryStatus() {
		return historyStatus;
	}

	/**
	 * @param historyStatus the historyStatus to set
	 */
	public void setHistoryStatus(String historyStatus) {
		this.historyStatus = historyStatus;
	}

	/**
	 * @return the fpoDetHist
	 */
	public List<FPOMasterDetailHistory> getFpoDetHist() {
		return fpoDetHist;
	}

	/**
	 * @param fpoDetHist the fpoDetHist to set
	 */
	public void setFpoDetHist(List<FPOMasterDetailHistory> fpoDetHist) {
		this.fpoDetHist = fpoDetHist;
	}

	/**
	 * @return the fpoAdminDetHist
	 */
	public List<FPOAdministrativeDetHistory> getFpoAdminDetHist() {
		return fpoAdminDetHist;
	}

	/**
	 * @param fpoAdminDetHist the fpoAdminDetHist to set
	 */
	public void setFpoAdminDetHist(List<FPOAdministrativeDetHistory> fpoAdminDetHist) {
		this.fpoAdminDetHist = fpoAdminDetHist;
	}

	/**
	 * @return the fpoBankDetHist
	 */
	public List<FPOBankDetailHistory> getFpoBankDetHist() {
		return fpoBankDetHist;
	}

	/**
	 * @param fpoBankDetHist the fpoBankDetHist to set
	 */
	public void setFpoBankDetHist(List<FPOBankDetailHistory> fpoBankDetHist) {
		this.fpoBankDetHist = fpoBankDetHist;
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
	 * @return the appStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}

	/**
	 * @param appStatus the appStatus to set
	 */
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the activeInactiveStatus
	 */
	public String getActiveInactiveStatus() {
		return activeInactiveStatus;
	}

	/**
	 * @param activeInactiveStatus the activeInactiveStatus to set
	 */
	public void setActiveInactiveStatus(String activeInactiveStatus) {
		this.activeInactiveStatus = activeInactiveStatus;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_FPO_MASTER_HIST", "FPO_ID_H" };
	}

}
