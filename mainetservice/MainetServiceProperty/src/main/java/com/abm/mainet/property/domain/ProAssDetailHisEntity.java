package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_as_pro_detail_hist")
public class ProAssDetailHisEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5784155483359506031L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "pro_assd_HIST_ID", nullable = false)
    private long proAssdHisId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "lg_ip_mac")
    private String lgIpMac;

    @Column(name = "lg_ip_mac_upd")
    private String lgIpMacUpd;

    @Column(name = "orgId")
    private Long orgId;

    @Column(name = "pro_ass_id")
    private Long assId;

    @Column(name = "pro_assd_active")
    private String assdActive;

    @Column(name = "pro_assd_alv")
    private Double assdAlv;

    @Column(name = "pro_assd_annual_rent")
    private Double assdAnnualRent;

    @Column(name = "pro_assd_assesment_date")
    private Date assdAssesmentDate;

    @Column(name = "pro_assd_buildup_area")
    private Double assdBuildupArea;

    @Column(name = "pro_assd_constru_type")
    private Long assdConstruType;

    @Column(name = "pro_assd_cv")
    private Double assdCv;

    @Column(name = "pro_assd_floor_no")
    private Long assdFloorNo;

    @Column(name = "pro_assd_id")
    private Long proAssdId;

    @Column(name = "pro_assd_monthly_rent")
    private Double assdMonthlyRent;

    @Column(name = "pro_assd_occupancy_type")
    private Long assdOccupancyType;

    @Column(name = "pro_assd_occupier_name")
    private String assdOccupierName;

    @Column(name = "pro_assd_road_factor")
    private Long assdRoadFactor;

    @Column(name = "pro_assd_rv")
    private Double assdRv;

    @Column(name = "pro_assd_std_rate")
    private Double assdStdRate;

    @Column(name = "pro_assd_unit_no")
    private Long assdUnitNo;

    @Column(name = "pro_assd_unit_type_id")
    private Long assdUnitTypeId;

    @Column(name = "pro_assd_usagetype1")
    private Long assdUsagetype1;

    @Column(name = "pro_assd_usagetype2")
    private Long assdUsagetype2;

    @Column(name = "pro_assd_usagetype3")
    private Long assdUsagetype3;

    @Column(name = "pro_assd_usagetype4")
    private Long assdUsagetype4;

    @Column(name = "pro_assd_usagetype5")
    private Long assdUsagetype5;

    @Column(name = "pro_assd_year_construction")
    private Date assdYearConstruction;

    @Column(name = "pro_FA_YEARID")
    private Long faYearId;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "PRO_PARENT_PROP")
    private String propNo;

    @Column(name = "pro_maintaince_charge")
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
    
    @Column(name = "PRO_FASSESMENT_DATE")
    private Date firstAssesmentDate;
    
    @Column(name = "PRO_CARPET_AREA")
    private Double carpetArea;
    
    @Column(name = "PRO_AGE")
    private Double age;
    
    @Column(name = "PRO_CONSTRUCT_PERMISSION_NUMBER")
    private String constructPermissionNo;

    @Column(name = "PRO_PERMISSION_USE_NO")
    private String permissionUseNo;

    @Column(name = "PRO_ASSESSMENT_REMARK")
    private String assessmentRemark;

    @Column(name = "PRO_LEGAL")
    private String legal;
    
    @Column(name = "PRO_OCCUPIER_NAME_REG")
    private String occupierNameReg;
    
    @Column(name = "PRO_ASSE_MOBILENO")
    private String occupierMobNo;
    
    @Column(name = "PRO_OCCUPIER_EMAIL")
    private String occupierEmail;
    
    @Column(name = "PRO_ACTUAL_RENT")
    private Double actualRent;
    
    @Column(name = "PRO_FLAT_NO")
    private String flatNo;
    
    @Column(name = "PRO_FACTOR_VAL")
    private String factVal;

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_pro_detail_hist", "pro_assd_HIST_ID" };

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

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public Long getAssId() {
        return assId;
    }

    public void setAssId(Long assId) {
        this.assId = assId;
    }

    public String getAssdActive() {
        return assdActive;
    }

    public void setAssdActive(String assdActive) {
        this.assdActive = assdActive;
    }

    public Double getAssdAlv() {
        return assdAlv;
    }

    public void setAssdAlv(Double assdAlv) {
        this.assdAlv = assdAlv;
    }

    public Double getAssdAnnualRent() {
        return assdAnnualRent;
    }

    public void setAssdAnnualRent(Double assdAnnualRent) {
        this.assdAnnualRent = assdAnnualRent;
    }

    public Date getAssdAssesmentDate() {
        return assdAssesmentDate;
    }

    public void setAssdAssesmentDate(Date assdAssesmentDate) {
        this.assdAssesmentDate = assdAssesmentDate;
    }

    public Double getAssdBuildupArea() {
        return assdBuildupArea;
    }

    public void setAssdBuildupArea(Double assdBuildupArea) {
        this.assdBuildupArea = assdBuildupArea;
    }

    public Long getAssdConstruType() {
        return assdConstruType;
    }

    public void setAssdConstruType(Long assdConstruType) {
        this.assdConstruType = assdConstruType;
    }

    public Double getAssdCv() {
        return assdCv;
    }

    public void setAssdCv(Double assdCv) {
        this.assdCv = assdCv;
    }

    public Long getAssdFloorNo() {
        return assdFloorNo;
    }

    public void setAssdFloorNo(Long assdFloorNo) {
        this.assdFloorNo = assdFloorNo;
    }

    public Long getProAssdId() {
        return proAssdId;
    }

    public void setProAssdId(Long proAssdId) {
        this.proAssdId = proAssdId;
    }

    public Double getAssdMonthlyRent() {
        return assdMonthlyRent;
    }

    public void setAssdMonthlyRent(Double assdMonthlyRent) {
        this.assdMonthlyRent = assdMonthlyRent;
    }

    public Long getAssdOccupancyType() {
        return assdOccupancyType;
    }

    public void setAssdOccupancyType(Long assdOccupancyType) {
        this.assdOccupancyType = assdOccupancyType;
    }

    public String getAssdOccupierName() {
        return assdOccupierName;
    }

    public void setAssdOccupierName(String assdOccupierName) {
        this.assdOccupierName = assdOccupierName;
    }

    public Long getAssdRoadFactor() {
        return assdRoadFactor;
    }

    public void setAssdRoadFactor(Long assdRoadFactor) {
        this.assdRoadFactor = assdRoadFactor;
    }

    public Double getAssdRv() {
        return assdRv;
    }

    public void setAssdRv(Double assdRv) {
        this.assdRv = assdRv;
    }

    public Double getAssdStdRate() {
        return assdStdRate;
    }

    public void setAssdStdRate(Double assdStdRate) {
        this.assdStdRate = assdStdRate;
    }

    public Long getAssdUnitNo() {
        return assdUnitNo;
    }

    public void setAssdUnitNo(Long assdUnitNo) {
        this.assdUnitNo = assdUnitNo;
    }

    public Long getAssdUnitTypeId() {
        return assdUnitTypeId;
    }

    public void setAssdUnitTypeId(Long assdUnitTypeId) {
        this.assdUnitTypeId = assdUnitTypeId;
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

    public Date getAssdYearConstruction() {
        return assdYearConstruction;
    }

    public void setAssdYearConstruction(Date assdYearConstruction) {
        this.assdYearConstruction = assdYearConstruction;
    }

    public Long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(Long faYearId) {
        this.faYearId = faYearId;
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

    public long getProAssdHisId() {
        return proAssdHisId;
    }

    public void setProAssdHisId(long proAssdHisId) {
        this.proAssdHisId = proAssdHisId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
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

	public String getFactVal() {
		return factVal;
	}

	public void setFactVal(String factVal) {
		this.factVal = factVal;
	}

  

}
