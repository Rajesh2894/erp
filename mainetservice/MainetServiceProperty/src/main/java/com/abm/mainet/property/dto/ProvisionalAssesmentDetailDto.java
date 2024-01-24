
package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProvisionalAssesmentDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long proAssdId;

    private ProvisionalAssesmentMstDto tbAsAssesmentMast;

    private Long assdUnitTypeId;

    private Long assdFloorNo;

    private Double assdBuildupArea;

    private Long assdUsagetype1;

    private Long assdUsagetype2;

    private Long assdUsagetype3;

    private Long assdUsagetype4;

    private Long assdUsagetype5;

    private Long assdConstruType;

    private Date assdYearConstruction;

    private Long assdOccupancyType;

    private Long assdRoadFactor;

    private Long assdUnitNo;

    private String occupierName;

    private double monthlyRent;

    private Date assdAssesmentDate;

    private Double assdAnnualRent;

    private Double assdStdRate;

    private Double assdAlv;

    private Double assdRv;

    private Double assdCv;

    private String assdActive;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long faYearId;

    private String proAssdUnitType;

    private String proFloorNo;

    private String baseRate;

    private String ruleId;

    private Double maintainceCharge;

    private String proAssdUsagetypeDesc;

    private String proAssdConstruTypeDesc;

    // private String proAssdRoadfactorDesc;

    private String proAssdOccupancyTypeDesc;

    private String proAssdConstructionDate;
    
    private String proAssdAssesmentDate;

    private String proFaYearIdDesc;

    private Date assEffectiveDate;

    private String proAssEffectiveDateDesc;

    private String proAssdUsagetypeDesc2;

    private String proAssdUsagetypeDesc3;

    private String proAssdUsagetypeDesc4;

    private String proAssdUsagetypeDesc5;

    private String propNo;

    private Long subGroup;

    private Long assdNatureOfproperty1;

    private Long assdNatureOfproperty2;

    private Long assdNatureOfproperty3;

    private Long assdNatureOfproperty4;

    private Long assdNatureOfproperty5;

    private String assdNatureOfpropertyDesc1;

    private String assdNatureOfpropertyDesc2;

    private String assdNatureOfpropertyDesc3;

    private String assdNatureOfpropertyDesc4;

    private String assdNatureOfpropertyDesc5;

    private Date firstAssesmentDate;

    private String firstAssesmentStringDate;

    private Date lastAssesmentDate;

    private String lastAssesmentStringDate;

    private String assdBifurcateNo;
    
    private Double carpetArea;

    private Double age;
    
    private String constructPermissionNo;

    private String permissionUseNo;

    private String assessmentRemark;

    private String legal;
    
    private String occupierNameReg;
    
    private String occupierMobNo;
    
    private String occupierEmail;
    
    private Double actualRent;
    
    private String authoStatus;
    
    private String flatNo;

    private List<ProvisionalAssesmentFactorDtlDto> provisionalAssesmentFactorDtlDtoList = new ArrayList<>(0);

    private String errorMsg;// for Rule not found error MSG
    
    private Long cpdUsageCls;
    
    private Long apmApplicationId;
    
    private double revisedPaidAmount;
    
    private double paidAmount;
    
    private List<PropertyRoomDetailsDto> roomDetailsDtoList=new ArrayList<>();
    
    private Date constructPermissionDate;
    
    private Double roomTotalArea;

	private String areaOrRvBasedChange;
	
	private String newOccupierName;
	
	private Date manualRecDate;
	
	private String manualReceiptNo;
	
	private Double extendedRv;
	
	private String newFlatNo;
	
    private Long tempUsePrimKey;
	
	private Long pageNo;
	
	private String factVal;
	
	private Double oldAssRv;
	
	private String electricBillUnitNo;

    private String electricConsumerNo;
    
    private String electricMeterNo;
	
    public String getElectricBillUnitNo() {
		return electricBillUnitNo;
	}

	public void setElectricBillUnitNo(String electricBillUnitNo) {
		this.electricBillUnitNo = electricBillUnitNo;
	}

	public String getElectricConsumerNo() {
		return electricConsumerNo;
	}

	public void setElectricConsumerNo(String electricConsumerNo) {
		this.electricConsumerNo = electricConsumerNo;
	}

	public String getElectricMeterNo() {
		return electricMeterNo;
	}

	public void setElectricMeterNo(String electricMeterNo) {
		this.electricMeterNo = electricMeterNo;
	}

	public long getProAssdId() {
        return proAssdId;
    }

    public void setProAssdId(long proAssdId) {
        this.proAssdId = proAssdId;
    }

    public ProvisionalAssesmentMstDto getTbAsAssesmentMast() {
        return tbAsAssesmentMast;
    }

    public void setTbAsAssesmentMast(ProvisionalAssesmentMstDto tbAsAssesmentMast) {
        this.tbAsAssesmentMast = tbAsAssesmentMast;
    }

    public Long getAssdUnitTypeId() {
        return assdUnitTypeId;
    }

    public void setAssdUnitTypeId(Long assdUnitTypeId) {
        this.assdUnitTypeId = assdUnitTypeId;
    }

    public Long getAssdFloorNo() {
        return assdFloorNo;
    }

    public void setAssdFloorNo(Long assdFloorNo) {
        this.assdFloorNo = assdFloorNo;
    }

    public Double getAssdBuildupArea() {
        return assdBuildupArea;
    }

    public void setAssdBuildupArea(Double assdBuildupArea) {
        this.assdBuildupArea = assdBuildupArea;
    }

    public Long getAssdUsagetype1() {
        return assdUsagetype1;
    }

    public void setAssdUsagetype1(Long assdUsagetype1) {
        this.assdUsagetype1 = assdUsagetype1;
    }

    public Long getAssdUsagetype2() {
        return assdUsagetype2;
    }

    public void setAssdUsagetype2(Long assdUsagetype2) {
        this.assdUsagetype2 = assdUsagetype2;
    }

    public Long getAssdUsagetype3() {
        return assdUsagetype3;
    }

    public void setAssdUsagetype3(Long assdUsagetype3) {
        this.assdUsagetype3 = assdUsagetype3;
    }

    public Long getAssdUsagetype4() {
        return assdUsagetype4;
    }

    public void setAssdUsagetype4(Long assdUsagetype4) {
        this.assdUsagetype4 = assdUsagetype4;
    }

    public Long getAssdUsagetype5() {
        return assdUsagetype5;
    }

    public void setAssdUsagetype5(Long assdUsagetype5) {
        this.assdUsagetype5 = assdUsagetype5;
    }

    public Long getAssdConstruType() {
        return assdConstruType;
    }

    public void setAssdConstruType(Long assdConstruType) {
        this.assdConstruType = assdConstruType;
    }

    public Date getAssdYearConstruction() {
        return assdYearConstruction;
    }

    public void setAssdYearConstruction(Date assdYearConstruction) {
        this.assdYearConstruction = assdYearConstruction;
    }

    public Long getAssdOccupancyType() {
        return assdOccupancyType;
    }

    public void setAssdOccupancyType(Long assdOccupancyType) {
        this.assdOccupancyType = assdOccupancyType;
    }

    public Long getAssdRoadFactor() {
        return assdRoadFactor;
    }

    public void setAssdRoadFactor(Long assdRoadFactor) {
        this.assdRoadFactor = assdRoadFactor;
    }

    public Long getAssdUnitNo() {
        return assdUnitNo;
    }

    public void setAssdUnitNo(Long assdUnitNo) {
        this.assdUnitNo = assdUnitNo;
    }

    public Date getAssdAssesmentDate() {
        return assdAssesmentDate;
    }

    public void setAssdAssesmentDate(Date assdAssesmentDate) {
        this.assdAssesmentDate = assdAssesmentDate;
    }

    public Double getAssdAnnualRent() {
        return assdAnnualRent;
    }

    public void setAssdAnnualRent(Double assdAnnualRent) {
        this.assdAnnualRent = assdAnnualRent;
    }

    public Double getAssdStdRate() {
        return assdStdRate;
    }

    public void setAssdStdRate(Double assdStdRate) {
        this.assdStdRate = assdStdRate;
    }

    public Double getAssdAlv() {
        return assdAlv;
    }

    public void setAssdAlv(Double assdAlv) {
        this.assdAlv = assdAlv;
    }

    public Double getAssdRv() {
        return assdRv;
    }

    public void setAssdRv(Double assdRv) {
        this.assdRv = assdRv;
    }

    public Double getAssdCv() {
        return assdCv;
    }

    public void setAssdCv(Double assdCv) {
        this.assdCv = assdCv;
    }

    public String getAssdActive() {
        return assdActive;
    }

    public void setAssdActive(String assdActive) {
        this.assdActive = assdActive;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public Long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(Long faYearId) {
        this.faYearId = faYearId;
    }

    public String getProAssdUnitType() {
        return proAssdUnitType;
    }

    public void setProAssdUnitType(String proAssdUnitType) {
        this.proAssdUnitType = proAssdUnitType;
    }

    public String getProFloorNo() {
        return proFloorNo;
    }

    public void setProFloorNo(String proFloorNo) {
        this.proFloorNo = proFloorNo;
    }

    public String getProAssdUsagetypeDesc() {
        return proAssdUsagetypeDesc;
    }

    public void setProAssdUsagetypeDesc(String proAssdUsagetypeDesc) {
        this.proAssdUsagetypeDesc = proAssdUsagetypeDesc;
    }

    public String getProAssdConstruTypeDesc() {
        return proAssdConstruTypeDesc;
    }

    public void setProAssdConstruTypeDesc(String proAssdConstruTypeDesc) {
        this.proAssdConstruTypeDesc = proAssdConstruTypeDesc;
    }

    /*
     * public String getProAssdRoadfactorDesc() { return proAssdRoadfactorDesc; } public void setProAssdRoadfactorDesc(String
     * proAssdRoadfactorDesc) { this.proAssdRoadfactorDesc = proAssdRoadfactorDesc; }
     */

    public String getProAssdOccupancyTypeDesc() {
        return proAssdOccupancyTypeDesc;
    }

    public void setProAssdOccupancyTypeDesc(String proAssdOccupancyTypeDesc) {
        this.proAssdOccupancyTypeDesc = proAssdOccupancyTypeDesc;
    }

    public String getProAssdConstructionDate() {
        return proAssdConstructionDate;
    }

    public void setProAssdConstructionDate(String proAssdConstructionDate) {
        this.proAssdConstructionDate = proAssdConstructionDate;
    }

    public String getProFaYearIdDesc() {
        return proFaYearIdDesc;
    }

    public void setProFaYearIdDesc(String proFaYearIdDesc) {
        this.proFaYearIdDesc = proFaYearIdDesc;
    }

    public String getProAssEffectiveDateDesc() {
        return proAssEffectiveDateDesc;
    }

    public void setProAssEffectiveDateDesc(String proAssEffectiveDateDesc) {
        this.proAssEffectiveDateDesc = proAssEffectiveDateDesc;
    }

    public List<ProvisionalAssesmentFactorDtlDto> getProvisionalAssesmentFactorDtlDtoList() {
        return provisionalAssesmentFactorDtlDtoList;
    }

    public void setProvisionalAssesmentFactorDtlDtoList(
            List<ProvisionalAssesmentFactorDtlDto> provisionalAssesmentFactorDtlDtoList) {
        this.provisionalAssesmentFactorDtlDtoList = provisionalAssesmentFactorDtlDtoList;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getAssEffectiveDate() {
        return assEffectiveDate;
    }

    public void setAssEffectiveDate(Date assEffectiveDate) {
        this.assEffectiveDate = assEffectiveDate;
    }

    public String getOccupierName() {
        return occupierName;
    }

    public void setOccupierName(String occupierName) {
        this.occupierName = occupierName;
    }

    public double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public String getProAssdUsagetypeDesc2() {
        return proAssdUsagetypeDesc2;
    }

    public void setProAssdUsagetypeDesc2(String proAssdUsagetypeDesc2) {
        this.proAssdUsagetypeDesc2 = proAssdUsagetypeDesc2;
    }

    public String getProAssdUsagetypeDesc3() {
        return proAssdUsagetypeDesc3;
    }

    public void setProAssdUsagetypeDesc3(String proAssdUsagetypeDesc3) {
        this.proAssdUsagetypeDesc3 = proAssdUsagetypeDesc3;
    }

    public String getProAssdUsagetypeDesc4() {
        return proAssdUsagetypeDesc4;
    }

    public void setProAssdUsagetypeDesc4(String proAssdUsagetypeDesc4) {
        this.proAssdUsagetypeDesc4 = proAssdUsagetypeDesc4;
    }

    public String getProAssdUsagetypeDesc5() {
        return proAssdUsagetypeDesc5;
    }

    public void setProAssdUsagetypeDesc5(String proAssdUsagetypeDesc5) {
        this.proAssdUsagetypeDesc5 = proAssdUsagetypeDesc5;
    }

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
    }

    public Long getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(Long subGroup) {
        this.subGroup = subGroup;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(String baseRate) {
        this.baseRate = baseRate;
    }

    public Double getMaintainceCharge() {
        return maintainceCharge;
    }

    public void setMaintainceCharge(Double maintainceCharge) {
        this.maintainceCharge = maintainceCharge;
    }

    public Long getAssdNatureOfproperty1() {
        return assdNatureOfproperty1;
    }

    public void setAssdNatureOfproperty1(Long assdNatureOfproperty1) {
        this.assdNatureOfproperty1 = assdNatureOfproperty1;
    }

    public Long getAssdNatureOfproperty2() {
        return assdNatureOfproperty2;
    }

    public void setAssdNatureOfproperty2(Long assdNatureOfproperty2) {
        this.assdNatureOfproperty2 = assdNatureOfproperty2;
    }

    public Long getAssdNatureOfproperty3() {
        return assdNatureOfproperty3;
    }

    public void setAssdNatureOfproperty3(Long assdNatureOfproperty3) {
        this.assdNatureOfproperty3 = assdNatureOfproperty3;
    }

    public Long getAssdNatureOfproperty4() {
        return assdNatureOfproperty4;
    }

    public void setAssdNatureOfproperty4(Long assdNatureOfproperty4) {
        this.assdNatureOfproperty4 = assdNatureOfproperty4;
    }

    public Long getAssdNatureOfproperty5() {
        return assdNatureOfproperty5;
    }

    public void setAssdNatureOfproperty5(Long assdNatureOfproperty5) {
        this.assdNatureOfproperty5 = assdNatureOfproperty5;
    }

    public String getAssdNatureOfpropertyDesc1() {
        return assdNatureOfpropertyDesc1;
    }

    public void setAssdNatureOfpropertyDesc1(String assdNatureOfpropertyDesc1) {
        this.assdNatureOfpropertyDesc1 = assdNatureOfpropertyDesc1;
    }

    public String getAssdNatureOfpropertyDesc2() {
        return assdNatureOfpropertyDesc2;
    }

    public void setAssdNatureOfpropertyDesc2(String assdNatureOfpropertyDesc2) {
        this.assdNatureOfpropertyDesc2 = assdNatureOfpropertyDesc2;
    }

    public String getAssdNatureOfpropertyDesc3() {
        return assdNatureOfpropertyDesc3;
    }

    public void setAssdNatureOfpropertyDesc3(String assdNatureOfpropertyDesc3) {
        this.assdNatureOfpropertyDesc3 = assdNatureOfpropertyDesc3;
    }

    public String getAssdNatureOfpropertyDesc4() {
        return assdNatureOfpropertyDesc4;
    }

    public void setAssdNatureOfpropertyDesc4(String assdNatureOfpropertyDesc4) {
        this.assdNatureOfpropertyDesc4 = assdNatureOfpropertyDesc4;
    }

    public String getAssdNatureOfpropertyDesc5() {
        return assdNatureOfpropertyDesc5;
    }

    public void setAssdNatureOfpropertyDesc5(String assdNatureOfpropertyDesc5) {
        this.assdNatureOfpropertyDesc5 = assdNatureOfpropertyDesc5;
    }

    public Date getFirstAssesmentDate() {
        return firstAssesmentDate;
    }

    public void setFirstAssesmentDate(Date firstAssesmentDate) {
        this.firstAssesmentDate = firstAssesmentDate;
    }

    public String getFirstAssesmentStringDate() {
        return firstAssesmentStringDate;
    }

    public void setFirstAssesmentStringDate(String firstAssesmentStringDate) {
        this.firstAssesmentStringDate = firstAssesmentStringDate;
    }

    public Date getLastAssesmentDate() {
        return lastAssesmentDate;
    }

    public void setLastAssesmentDate(Date lastAssesmentDate) {
        this.lastAssesmentDate = lastAssesmentDate;
    }

    public String getLastAssesmentStringDate() {
        return lastAssesmentStringDate;
    }

    public void setLastAssesmentStringDate(String lastAssesmentStringDate) {
        this.lastAssesmentStringDate = lastAssesmentStringDate;
    }

    public String getAssdBifurcateNo() {
        return assdBifurcateNo;
    }

    public void setAssdBifurcateNo(String assdBifurcateNo) {
        this.assdBifurcateNo = assdBifurcateNo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

	public Double getCarpetArea() {
		return carpetArea;
	}

	public void setCarpetArea(Double carpetArea) {
		this.carpetArea = carpetArea;
	}

	public Double getAge() {
		return age;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	public String getConstructPermissionNo() {
		return constructPermissionNo;
	}

	public void setConstructPermissionNo(String constructPermissionNo) {
		this.constructPermissionNo = constructPermissionNo;
	}

	public String getPermissionUseNo() {
		return permissionUseNo;
	}

	public void setPermissionUseNo(String permissionUseNo) {
		this.permissionUseNo = permissionUseNo;
	}

	public String getAssessmentRemark() {
		return assessmentRemark;
	}

	public void setAssessmentRemark(String assessmentRemark) {
		this.assessmentRemark = assessmentRemark;
	}

	public String getLegal() {
		return legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}

	public String getOccupierNameReg() {
		return occupierNameReg;
	}

	public void setOccupierNameReg(String occupierNameReg) {
		this.occupierNameReg = occupierNameReg;
	}

	public String getOccupierMobNo() {
		return occupierMobNo;
	}

	public void setOccupierMobNo(String occupierMobNo) {
		this.occupierMobNo = occupierMobNo;
	}

	public String getOccupierEmail() {
		return occupierEmail;
	}

	public void setOccupierEmail(String occupierEmail) {
		this.occupierEmail = occupierEmail;
	}

	public Double getActualRent() {
		return actualRent;
	}

	public void setActualRent(Double actualRent) {
		this.actualRent = actualRent;
	}

	public String getAuthoStatus() {
		return authoStatus;
	}

	public void setAuthoStatus(String authoStatus) {
		this.authoStatus = authoStatus;
	}

	public List<PropertyRoomDetailsDto> getRoomDetailsDtoList() {
		return roomDetailsDtoList;
	}

	public void setRoomDetailsDtoList(List<PropertyRoomDetailsDto> roomDetailsDtoList) {
		this.roomDetailsDtoList = roomDetailsDtoList;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public Long getCpdUsageCls() {
		return cpdUsageCls;
	}

	public void setCpdUsageCls(Long cpdUsageCls) {
		this.cpdUsageCls = cpdUsageCls;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public double getRevisedPaidAmount() {
		return revisedPaidAmount;
	}

	public void setRevisedPaidAmount(double revisedPaidAmount) {
		this.revisedPaidAmount = revisedPaidAmount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Date getConstructPermissionDate() {
		return constructPermissionDate;
	}

	public void setConstructPermissionDate(Date constructPermissionDate) {
		this.constructPermissionDate = constructPermissionDate;
	}

	public Double getRoomTotalArea() {
		return roomTotalArea;
	}

	public void setRoomTotalArea(Double roomTotalArea) {
		this.roomTotalArea = roomTotalArea;
	}

	public String getAreaOrRvBasedChange() {
		return areaOrRvBasedChange;
	}

	public void setAreaOrRvBasedChange(String areaOrRvBasedChange) {
		this.areaOrRvBasedChange = areaOrRvBasedChange;
	}

	public String getNewOccupierName() {
		return newOccupierName;
	}

	public void setNewOccupierName(String newOccupierName) {
		this.newOccupierName = newOccupierName;
	}

	public Date getManualRecDate() {
		return manualRecDate;
	}

	public void setManualRecDate(Date manualRecDate) {
		this.manualRecDate = manualRecDate;
	}

	public String getManualReceiptNo() {
		return manualReceiptNo;
	}

	public void setManualReceiptNo(String manualReceiptNo) {
		this.manualReceiptNo = manualReceiptNo;
	}

	public Double getExtendedRv() {
		return extendedRv;
	}

	public void setExtendedRv(Double extendedRv) {
		this.extendedRv = extendedRv;
	}

	public String getNewFlatNo() {
		return newFlatNo;
	}

	public void setNewFlatNo(String newFlatNo) {
		this.newFlatNo = newFlatNo;
	}

	public Long getTempUsePrimKey() {
		return tempUsePrimKey;
	}

	public void setTempUsePrimKey(Long tempUsePrimKey) {
		this.tempUsePrimKey = tempUsePrimKey;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public String getFactVal() {
		return factVal;
	}

	public void setFactVal(String factVal) {
		this.factVal = factVal;
	}

	public String getProAssdAssesmentDate() {
		return proAssdAssesmentDate;
	}

	public void setProAssdAssesmentDate(String proAssdAssesmentDate) {
		this.proAssdAssesmentDate = proAssdAssesmentDate;
	}

	public Double getOldAssRv() {
		return oldAssRv;
	}

	public void setOldAssRv(Double oldAssRv) {
		this.oldAssRv = oldAssRv;
	}
	
	
	   
    /*
     * public String getAssdNatureOfpropertyDesc1() { return assdNatureOfpropertyDesc1; } public void
     * setAssdNatureOfpropertyDesc1(String assdNatureOfpropertyDesc1) { this.assdNatureOfpropertyDesc1 =
     * assdNatureOfpropertyDesc1; } public String getAssdNatureOfpropertyDesc2() { return assdNatureOfpropertyDesc2; } public void
     * setAssdNatureOfpropertyDesc2(String assdNatureOfpropertyDesc2) { this.assdNatureOfpropertyDesc2 =
     * assdNatureOfpropertyDesc2; } public String getAssdNatureOfpropertyDesc3() { return assdNatureOfpropertyDesc3; } public void
     * setAssdNatureOfpropertyDesc3(String assdNatureOfpropertyDesc3) { this.assdNatureOfpropertyDesc3 =
     * assdNatureOfpropertyDesc3; } public String getAssdNatureOfpropertyDesc4() { return assdNatureOfpropertyDesc4; } public void
     * setAssdNatureOfpropertyDesc4(String assdNatureOfpropertyDesc4) { this.assdNatureOfpropertyDesc4 =
     * assdNatureOfpropertyDesc4; } public String getAssdNatureOfpropertyDesc5() { return assdNatureOfpropertyDesc5; } public void
     * setAssdNatureOfpropertyDesc5(String assdNatureOfpropertyDesc5) { this.assdNatureOfpropertyDesc5 =
     * assdNatureOfpropertyDesc5; }
     */
}