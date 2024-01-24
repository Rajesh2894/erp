package com.abm.mainet.additionalservices.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_NOC_FOR_BUILDING_PERMISSION")
public class NOCForBuildingPermissionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "BP_ID", nullable = false)
	private Long bpId;

	@Column(nullable = false)
	private Long orgId;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE")
	private Date date;

	@Column(name = "APPLICANT_FNAME", length = 100)
	private String fName;

	@Column(name = "applicantLname ", length = 100)
	private String lName;

	@Column(name = "APPLICANT_MNAME", length = 100)
	private String mName;

	@Column(name = "APPLICANT_TITLE")
	private Long titleId;

	@Column(name = "APPLICATION_TYPE")
	private Long applicationType;

	@Column(name = "SEX", length = 2)
	private String sex;

	@Column(name = "DEPT_ID")
	private Long deptId;

	@Column(name = "BUILDING_PERMISSION_APP_NO", length = 100)
	private String buildingPermissionAppNo;

	@Column(name = "SURVEY_NO", length = 100)
	private String surveyNo;

	@Column(name = "PLOT_NO", length = 100)
	private String plotNo;

	@Column(name = "CITIY_SURVEY_NO", length = 100)
	private String citiySurveyNo;

	@Column(name = "ADDRESS", length = 100)
	private String address;

	@Column(name = "LOCATION")
	private Long location;

	@Column(name = "PLOT_AREA")
	private Double plotArea;

	@Column(name = "BUILD_UP_AREA")
	private Double builtUpArea;

	@Column(name = "BR_REMARKS", length = 2000)
	private String brRemarks;

	@Column(name = "LANG_ID", nullable = false)
	private int langId;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lmoddate;

	@Column(name = "SM_SERVICE_ID")
	private Long smServiceId;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "WF_STATUS", nullable = false)
	private String wfStatus;

	@Column(name = "STATUS", length = 1)
	private String status;

	@Column(name = "SERVICE_ID ")
	private Long serviceId;

	@Transient
	private Long applnId;
	
	@Column(name = "APPLICANT_ADR", length = 100)
	private String applicantAddress;
	
	@Column(name = "NOC_TYPEID")
	private Long nocType;
	
	@Column(name = "USAGE_TYPE1")
	private Long usageType1;
	
	@Column(name = "USAGE_TYPE2")
	private Long usageType2;
	
	@Column(name = "USAGE_TYPE3")
	private Long usageType3;
	
	@Column(name = "USAGE_TYPE4")
	private Long usageType4;
	
	@Column(name = "USAGE_TYPE5")
	private Long usageType5;
	
	@Column(name = "REF_NO ", length = 100)
	private String refNo;
	
	@Column(name = "FILE_NO", length = 100)
	private String fNo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "REF_DATE")
	private Date refDate;
	
	@Column(name = "P_NAME", length = 100)
	private String purchaserName;
	
	@Column(name = " P_ADDR", length = 100)
	private String purchaserAddress;
	
	@Column(name = "  S_NAME", length = 100)
	private String sellerName;
	
	@Column(name = " S_ADDR", length = 100)
	private String sellerAddress;
	
	@Column(name = " EAST", length = 100)
	private String east;
	
	@Column(name = "WEST", length = 100)
	private String west;
	
	@Column(name = " SOUTH", length = 100)
	private String south;
	
	@Column(name = "NORTH", length = 100)
	private String north;
	 
	@Column(name = "A_EMAIL ", length = 100)
	private String applicantEmail;
	
	@Column(name = "C_NUMBER")
	private Long contactNumber;
	
	@Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = false)
	private Long apmApplicationId;
	
	@Column(name = " PRO_DESC", length = 1000)
	private String proDesc;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "SALE_DEED_DATE ")
	private Date saleDate;
	
	@Column(name = "MALABA_CHARGE")
	private Double malabaCharge;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CRUTINY_DATE")
	private Date scrutinyDate;

	
	@Column(name = "LAND_OWNER", length = 1,nullable = true)
	private String landOwner;

	@Column(name = "REG_NO")
	private String regNo;
	
	@Column(name = "REF_NOC ", length = 100)
	private String refNoc;
	

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getBpId() {
		return bpId;
	}

	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	public Long getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(Long applicationType) {
		this.applicationType = applicationType;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getBuildingPermissionAppNo() {
		return buildingPermissionAppNo;
	}

	public void setBuildingPermissionAppNo(String buildingPermissionAppNo) {
		this.buildingPermissionAppNo = buildingPermissionAppNo;
	}

	public String getSurveyNo() {
		return surveyNo;
	}

	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}

	public String getPlotNo() {
		return plotNo;
	}

	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}

	public String getCitiySurveyNo() {
		return citiySurveyNo;
	}

	public void setCitiySurveyNo(String citiySurveyNo) {
		this.citiySurveyNo = citiySurveyNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
		this.location = location;
	}

	public Double getPlotArea() {
		return plotArea;
	}

	public void setPlotArea(Double plotArea) {
		this.plotArea = plotArea;
	}

	public Double getBuiltUpArea() {
		return builtUpArea;
	}

	public void setBuiltUpArea(Double builtUpArea) {
		this.builtUpArea = builtUpArea;
	}

	public String getBrRemarks() {
		return brRemarks;
	}

	public void setBrRemarks(String brRemarks) {
		this.brRemarks = brRemarks;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
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

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getApplnId() {
		return applnId;
	}

	public void setApplnId(Long applnId) {
		this.applnId = applnId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String[] getPkValues() {
		return new String[] { "HD", "TB_NOC_FOR_BUILDING_PERMISSION", "BP_ID" };
	}

	public String getApplicantAddress() {
		return applicantAddress;
	}

	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}

	public Long getNocType() {
		return nocType;
	}

	public void setNocType(Long nocType) {
		this.nocType = nocType;
	}

	public Long getUsageType1() {
		return usageType1;
	}

	public void setUsageType1(Long usageType1) {
		this.usageType1 = usageType1;
	}

	public Long getUsageType2() {
		return usageType2;
	}

	public void setUsageType2(Long usageType2) {
		this.usageType2 = usageType2;
	}

	public Long getUsageType3() {
		return usageType3;
	}

	public void setUsageType3(Long usageType3) {
		this.usageType3 = usageType3;
	}

	public Long getUsageType4() {
		return usageType4;
	}

	public void setUsageType4(Long usageType4) {
		this.usageType4 = usageType4;
	}

	public Long getUsageType5() {
		return usageType5;
	}

	public void setUsageType5(Long usageType5) {
		this.usageType5 = usageType5;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getfNo() {
		return fNo;
	}

	public void setfNo(String fNo) {
		this.fNo = fNo;
	}
	
	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserAddress() {
		return purchaserAddress;
	}

	public void setPurchaserAddress(String purchaserAddress) {
		this.purchaserAddress = purchaserAddress;
	}

	public String getEast() {
		return east;
	}

	public void setEast(String east) {
		this.east = east;
	}

	public String getWest() {
		return west;
	}

	public void setWest(String west) {
		this.west = west;
	}

	public String getSouth() {
		return south;
	}

	public void setSouth(String south) {
		this.south = south;
	}

	public String getNorth() {
		return north;
	}

	public void setNorth(String north) {
		this.north = north;
	}

	public String getApplicantEmail() {
		return applicantEmail;
	}

	public void setApplicantEmail(String applicantEmail) {
		this.applicantEmail = applicantEmail;
	}

	
	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Double getMalabaCharge() {
		return malabaCharge;
	}

	public void setMalabaCharge(Double malabaCharge) {
		this.malabaCharge = malabaCharge;
	}

	public Date getScrutinyDate() {
		return scrutinyDate;
	}

	public void setScrutinyDate(Date scrutinyDate) {
		this.scrutinyDate = scrutinyDate;
	}

	public String getLandOwner() {
		return landOwner;
	}

	public void setLandOwner(String landOwner) {
		this.landOwner = landOwner;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRefNoc() {
		return refNoc;
	}

	public void setRefNoc(String refNoc) {
		this.refNoc = refNoc;
	}
	
	


}
