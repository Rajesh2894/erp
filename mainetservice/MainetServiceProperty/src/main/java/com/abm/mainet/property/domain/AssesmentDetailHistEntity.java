/**
 * 
 */
package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author anwarul.hassan
 * @since 08-Jan-2021
 */

@Entity
@Table(name = "TB_AS_ASSESMENT_DETAIL_HIST")
public class AssesmentDetailHistEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MN_assd_his_id")
    private long proAssdHisId;

    @Column(name = "MN_assd_id")
    private long proAssdId;

    @Column(name = "MN_ass_id", nullable = false)
    private Long mnAssId;

    @Column(name = "MN_assd_unit_type_id")
    private Long assdUnitTypeId;

    @Column(name = "MN_assd_floor_no")
    private Long assdFloorNo;

    @Column(name = "MN_assd_buildup_area")
    private Double assdBuildupArea;

    @Column(name = "MN_assd_usagetype1")
    private Long assdUsagetype1;

    @Column(name = "MN_assd_usagetype2")
    private Long assdUsagetype2;

    @Column(name = "MN_assd_usagetype3")
    private Long assdUsagetype3;

    @Column(name = "MN_assd_usagetype4")
    private Long assdUsagetype4;

    @Column(name = "MN_assd_usagetype5")
    private Long assdUsagetype5;

    @Column(name = "MN_assd_constru_type")
    private Long assdConstruType;

    @Column(name = "MN_assd_year_construction")
    private Date assdYearConstruction;

    @Column(name = "MN_assd_occupancy_type")
    private Long assdOccupancyType;

    @Column(name = "MN_assd_assesment_date")
    private Date assdAssesmentDate;

    @Column(name = "MN_assd_annual_rent")
    private Double assdAnnualRent;

    @Column(name = "MN_assd_std_rate")
    private Double assdStdRate;

    @Column(name = "MN_assd_alv")
    private Double assdAlv;

    @Column(name = "MN_assd_rv")
    private Double assdRv;

    @Column(name = "MN_assd_cv")
    private Double assdCv;

    @Column(name = "MN_assd_active")
    private String assdActive;

    @Column(name = "orgId")
    private Long orgId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "lg_ip_mac")
    private String lgIpMac;

    @Column(name = "lg_ip_mac_upd")
    private String lgIpMacUpd;

    @Column(name = "MN_FA_YEARID")
    private Long faYearId;

    @Column(name = "MN_assd_road_factor")
    private Long assdRoadFactor;

    @Column(name = "MN_assd_unit_no")
    private Long assdUnitNo;

    @Column(name = "MN_assd_occupier_name")
    private String occupierName;

    @Column(name = "MN_assd_monthly_rent")
    private double monthlyRent;

    @Column(name = "MN_PARENT_PROP")
    private String propNo;

    @Column(name = "BASERATE")
    private String baseRate;

    @Column(name = "RULEID")
    private String ruleId;

    @Column(name = "MN_MAINTAINCE_CHARGE")
    private Double maintainceCharge;

    @Column(name = "natureOfProperty1")
    private Long assdNatureOfproperty1;

    @Column(name = "natureOfProperty2")
    private Long assdNatureOfproperty2;

    @Column(name = "natureOfProperty3")
    private Long assdNatureOfproperty3;

    @Column(name = "natureOfProperty4")
    private Long assdNatureOfproperty4;

    @Column(name = "natureOfProperty5")
    private Long assdNatureOfproperty5;

    @Column(name = "mn_assd_fassesment_date")
    private Date firstAssesmentDate;

    @Column(name = "H_STATUS")
    private String hStatus;
    
    @Column(name = "MN_CARPET_AREA")
    private Double carpetArea;
    
    @Column(name = "MN_AGE")
    private Double age;
    
    @Column(name = "MN_CONSTRUCT_PERMISSION_NUMBER")
    private String constructPermissionNo;

    @Column(name = "MN_PERMISSION_USE_NO")
    private String permissionUseNo;

    @Column(name = "MN_ASSESSMENT_REMARK")
    private String assessmentRemark;

    @Column(name = "MN_LEGAL")
    private String legal;
    
    @Column(name = "MN_OCCUPIER_NAME_REG")
    private String occupierNameReg;
    
    @Column(name = "MN_ASSE_MOBILENO")
    private String occupierMobNo;
    
    @Column(name = "MN_OCCUPIER_EMAIL")
    private String occupierEmail;
    
    @Column(name = "MN_ACTUAL_RENT")
    private Double actualRent;
    
	@Column(name = "MN_FLAT_NO")
	private String flatNo;
	
    @Column(name = "APM_APPLICATION_ID")
    private Long apmApplicationId;
    
    @Column(name = "MN_ASS_HIS_id")
    private long proAssMastHisId;
    
    @Column(name = "MN_FACTOR_VAL")
    private String factVal;

    public String[] getPkValues() {
        return new String[] { "AS", "TB_AS_ASSESMENT_DETAIL_HIST", "MN_assd_his_id" };
    }

    public long getProAssdHisId() {
        return proAssdHisId;
    }

    public void setProAssdHisId(long proAssdHisId) {
        this.proAssdHisId = proAssdHisId;
    }

    public long getProAssdId() {
        return proAssdId;
    }

    public void setProAssdId(long proAssdId) {
        this.proAssdId = proAssdId;
    }

    public Long getMnAssId() {
        return mnAssId;
    }

    public void setMnAssId(Long mnAssId) {
        this.mnAssId = mnAssId;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
    }

    public String getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(String baseRate) {
        this.baseRate = baseRate;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
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

    public Date getFirstAssesmentDate() {
        return firstAssesmentDate;
    }

    public void setFirstAssesmentDate(Date firstAssesmentDate) {
        this.firstAssesmentDate = firstAssesmentDate;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public long getProAssMastHisId() {
		return proAssMastHisId;
	}

	public void setProAssMastHisId(long proAssMastHisId) {
		this.proAssMastHisId = proAssMastHisId;
	}

	public String getFactVal() {
		return factVal;
	}

	public void setFactVal(String factVal) {
		this.factVal = factVal;
	}
	

}
