/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author pooja.maske
 *
 */
public class FPOMasterDto implements Serializable {

	private static final long serialVersionUID = -1509517546757359410L;

	private Long fpoId;

	private String cbboName;

	private String iaName;

	private Long sdb1;

	private Long sdb2;

	private Long sdb3;

	private Long specialAllocation;

	private Long dmcApproval;

	private String fpoName;

	private String fpoRegNo;

	private Long regAct;

	private Date dateIncorporation;

	private Long fpoAge;

	private String fpoOffAddr;

	private Long fpoPinCode;

	private String fpoAdharNo;

	private String fpoTanNo;

	private String fpoPanNo;

	private Long noFarmMob;

	private Long noShareMem;

	private Long paidupCapital;

	private Long shareCapital;

	private BigDecimal totalEquityAmt;

	private Long baseLineSurvey;

	private String isWomenCentric;

	private String statecategory;

	private Long northEastRegion;

	private String region;

	private String isAspirationalDist;

	private String isTribalDist;

	private String odop;

	private Long allocationCategory;

	private Long allocationSubCategory;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long langId;

	private String companyRegNo;

	private Long typeofPromAgen;

	private Long iaId;

	private Long cbboId;

	private Long odopCultivation;

	private String udyogAadharApplicable;

	private String udyogAadharNo;

	private Date udyogAadharDate;

	private String officeAddress;

	private String officeVillageName;

	private String fpoPostOffice;

	private String officePinCode;

	private Long iaAlcYear;

	private Long cbboAlcYear;

	private Long authorizeCapital;

	private Long sharedCapital;

	private String gstin;

	private String totalAreaCovKharif;

	private String totalAreaCovRabi;

	private Long deptId;

	private String fpoRegDate;

	private Date appPendingDate;

	private Long registeredOnEnam;

	private String userIdEnam;

	private Long approvedByIa;

	private Long approvedByCbbo;

	private Long approvedByFpo;

	private String appByIaDesc;

	private String appByCbboDesc;

	private String appByFpoDesc;

	private Long applicationId;

	private String appStatus;

	private String mobileNo;

	private String email;

	private String remark;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private List<FPOMasterDetailDto> fpoMasterDetailDto = new ArrayList<FPOMasterDetailDto>();

	private List<FPOBankDetailDto> fpoBankDetailDto = new ArrayList<FPOBankDetailDto>();

	private List<FPOAdministrativeDto> fpoAdministrativeDto = new ArrayList<FPOAdministrativeDto>();

	List<LookUp> stateList = new ArrayList<>();

	List<LookUp> districtList = new ArrayList<>();

	List<LookUp> blockList = new ArrayList<>();

	List<BlockAllocationDetailDto> blockDto = new ArrayList<>();

	private String activeInactiveStatus;

	private String summaryStatus;

	private Long acknowledgementNumber;

	private String approved;

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
	 * @return the fpoMasterDetailDto
	 */
	public List<FPOMasterDetailDto> getFpoMasterDetailDto() {
		return fpoMasterDetailDto;
	}

	/**
	 * @param fpoMasterDetailDto the fpoMasterDetailDto to set
	 */
	public void setFpoMasterDetailDto(List<FPOMasterDetailDto> fpoMasterDetailDto) {
		this.fpoMasterDetailDto = fpoMasterDetailDto;
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
	 * @return the langId
	 */
	public Long getLangId() {
		return langId;
	}

	/**
	 * @param langId the langId to set
	 */
	public void setLangId(Long langId) {
		this.langId = langId;
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
	 * @return the fpoBankDetailDto
	 */
	public List<FPOBankDetailDto> getFpoBankDetailDto() {
		return fpoBankDetailDto;
	}

	/**
	 * @param fpoBankDetailDto the fpoBankDetailDto to set
	 */
	public void setFpoBankDetailDto(List<FPOBankDetailDto> fpoBankDetailDto) {
		this.fpoBankDetailDto = fpoBankDetailDto;
	}

	/**
	 * @return the fpoAdministrativeDto
	 */
	public List<FPOAdministrativeDto> getFpoAdministrativeDto() {
		return fpoAdministrativeDto;
	}

	/**
	 * @param fpoAdministrativeDto the fpoAdministrativeDto to set
	 */
	public void setFpoAdministrativeDto(List<FPOAdministrativeDto> fpoAdministrativeDto) {
		this.fpoAdministrativeDto = fpoAdministrativeDto;
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

	public Long getSharedCapital() {
		return sharedCapital;
	}

	public void setSharedCapital(Long sharedCapital) {
		this.sharedCapital = sharedCapital;
	}

	public Long getNorthEastRegion() {
		return northEastRegion;
	}

	public void setNorthEastRegion(Long northEastRegion) {
		this.northEastRegion = northEastRegion;
	}

	/**
	 * @return the fpoRegDate
	 */
	public String getFpoRegDate() {
		return fpoRegDate;
	}

	/**
	 * @param fpoRegDate the fpoRegDate to set
	 */
	public void setFpoRegDate(String fpoRegDate) {
		this.fpoRegDate = fpoRegDate;
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
	 * @return the approvedByIa
	 */
	public Long getApprovedByIa() {
		return approvedByIa;
	}

	/**
	 * @param approvedByIa the approvedByIa to set
	 */
	public void setApprovedByIa(Long approvedByIa) {
		this.approvedByIa = approvedByIa;
	}

	/**
	 * @return the approvedByCbbo
	 */
	public Long getApprovedByCbbo() {
		return approvedByCbbo;
	}

	/**
	 * @param approvedByCbbo the approvedByCbbo to set
	 */
	public void setApprovedByCbbo(Long approvedByCbbo) {
		this.approvedByCbbo = approvedByCbbo;
	}

	/**
	 * @return the approvedByFpo
	 */
	public Long getApprovedByFpo() {
		return approvedByFpo;
	}

	/**
	 * @param approvedByFpo the approvedByFpo to set
	 */
	public void setApprovedByFpo(Long approvedByFpo) {
		this.approvedByFpo = approvedByFpo;
	}

	/**
	 * @return the appByIaDesc
	 */
	public String getAppByIaDesc() {
		return appByIaDesc;
	}

	/**
	 * @param appByIaDesc the appByIaDesc to set
	 */
	public void setAppByIaDesc(String appByIaDesc) {
		this.appByIaDesc = appByIaDesc;
	}

	/**
	 * @return the appByCbboDesc
	 */
	public String getAppByCbboDesc() {
		return appByCbboDesc;
	}

	/**
	 * @param appByCbboDesc the appByCbboDesc to set
	 */
	public void setAppByCbboDesc(String appByCbboDesc) {
		this.appByCbboDesc = appByCbboDesc;
	}

	/**
	 * @return the appByFpoDesc
	 */
	public String getAppByFpoDesc() {
		return appByFpoDesc;
	}

	/**
	 * @param appByFpoDesc the appByFpoDesc to set
	 */
	public void setAppByFpoDesc(String appByFpoDesc) {
		this.appByFpoDesc = appByFpoDesc;
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
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the stateList
	 */
	public List<LookUp> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<LookUp> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the districtList
	 */
	public List<LookUp> getDistrictList() {
		return districtList;
	}

	/**
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}

	/**
	 * @return the blockList
	 */
	public List<LookUp> getBlockList() {
		return blockList;
	}

	/**
	 * @param blockList the blockList to set
	 */
	public void setBlockList(List<LookUp> blockList) {
		this.blockList = blockList;
	}

	/**
	 * @return the blockDto
	 */
	public List<BlockAllocationDetailDto> getBlockDto() {
		return blockDto;
	}

	/**
	 * @param blockDto the blockDto to set
	 */
	public void setBlockDto(List<BlockAllocationDetailDto> blockDto) {
		this.blockDto = blockDto;
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

	/**
	 * @return the summaryStatus
	 */
	public String getSummaryStatus() {
		return summaryStatus;
	}

	/**
	 * @param summaryStatus the summaryStatus to set
	 */
	public void setSummaryStatus(String summaryStatus) {
		this.summaryStatus = summaryStatus;
	}

	/**
	 * @return the acknowledgementNumber
	 */
	public Long getAcknowledgementNumber() {
		return acknowledgementNumber;
	}

	/**
	 * @param acknowledgementNumber the acknowledgementNumber to set
	 */
	public void setAcknowledgementNumber(Long acknowledgementNumber) {
		this.acknowledgementNumber = acknowledgementNumber;
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

}
