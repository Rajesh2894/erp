package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_as_prop_det")
public class PropertyDetEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2101537469478245851L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PD_ID")
    private long pmAssdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PM_PROPID", nullable = false)
    private PropertyMastEntity tbAsPrimaryMast;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "PD_ACTIVE")
    private String assdActive;

    @Column(name = "PD_ALV")
    private Double assdAlv;

    @Column(name = "PD_ANNUAL_RENT")
    private Double assdAnnualRent;

    @Column(name = "PD_ASSESMENT_DATE")
    private Date assdAssesmentDate;

    @Column(name = "PD_BUILDUP_AREA")
    private Double assdBuildupArea;

    @Column(name = "PD_CONSTRU_TYPE")
    private Long assdConstruType;

    @Column(name = "PD_CV")
    private Double assdCv;

    @Column(name = "PD_FLOOR_NO")
    private Long assdFloorNo;

    @Column(name = "PD_MONTHLY_RENT")
    private Double monthlyRent;

    @Column(name = "PD_OCCUPANCY_TYPE")
    private Long assdOccupancyType;

    @Column(name = "PD_OCCUPIER_NAME")
    private String occupierName;

    @Column(name = "PD_ROAD_FACTOR")
    private Long assdRoadFactor;

    @Column(name = "PD_RV")
    private Double assdRv;

    @Column(name = "PD_STD_RATE")
    private Double assdStdRate;

    @Column(name = "PD_UNIT_NO")
    private Long assdUnitNo;

    @Column(name = "PD_UNIT_TYPE_ID")
    private Long assdUnitTypeId;

    @Column(name = "PD_USAGETYPE1")
    private Long assdUsagetype1;

    @Column(name = "PD_USAGETYPE2")
    private Long assdUsagetype2;

    @Column(name = "PD_USAGETYPE3")
    private Long assdUsagetype3;

    @Column(name = "PD_USAGETYPE4")
    private Long assdUsagetype4;

    @Column(name = "PD_USAGETYPE5")
    private Long assdUsagetype5;

    @Column(name = "PD_YEAR_CONSTRUCTION")
    private Date assdYearConstruction;

    @Column(name = "PM_FA_YEARID")
    private Long faYearId;

    @Column(name = "PD_OC_DATE")
    private Date ocDate;

    @Column(name = "PD_OC_NUMBER")
    private Long ocNumber;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

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
    
    @Column(name = "PD_FASSESMENT_DATE")
    private Date firstAssesmentDate;
    
    @Column(name = "PD_CARPET_AREA")
    private Double carpetArea;
    
    @Column(name = "PM_AGE")
    private Double age;
    
    @Column(name = "PM_CONSTRUCT_PERMISSION_NUMBER")
    private String constructPermissionNo;

    @Column(name = "PD_PERMISSION_USE_NO")
    private String permissionUseNo;

    @Column(name = "PD_ASSESSMENT_REMARK")
    private String assessmentRemark;

    @Column(name = "PM_LEGAL")
    private String legal;
    
    @Column(name = "PD_OCCUPIER_NAME_REG")
    private String occupierNameReg;
    
    @Column(name = "PM_ASSE_MOBILENO")
    private String occupierMobNo;
    
    @Column(name = "PM_OCCUPIER_EMAIL")
    private String occupierEmail;
    
    @Column(name = "PD_ACTUAL_RENT")
    private Double actualRent;
    
    @Column(name = "PD_FLAT_NO")
    private String flatNo;
   
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tbAsPrimaryDet", cascade = CascadeType.ALL)
    @Where(clause = "PF_ACTIVE = 'A'")
    private List<PropertyFactorEntity> propfactorDtlList;
    	
    @JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "propDetEntity", cascade =CascadeType.ALL) 
    private List<PropertyRoomDetailEntity> roomDetailEntityList;
	 
    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_prop_det", "PD_ID" };

    }

    public PropertyMastEntity getTbAsPrimaryMast() {
        return tbAsPrimaryMast;
    }

    public void setTbAsPrimaryMast(PropertyMastEntity tbAsPrimaryMast) {
        this.tbAsPrimaryMast = tbAsPrimaryMast;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public Date getOcDate() {
        return ocDate;
    }

    public void setOcDate(Date ocDate) {
        this.ocDate = ocDate;
    }

    public Long getOcNumber() {
        return ocNumber;
    }

    public void setOcNumber(Long ocNumber) {
        this.ocNumber = ocNumber;
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

    public long getPmAssdId() {
        return pmAssdId;
    }

    public void setPmAssdId(long pmAssdId) {
        this.pmAssdId = pmAssdId;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public String getOccupierName() {
        return occupierName;
    }

    public void setOccupierName(String occupierName) {
        this.occupierName = occupierName;
    }

    public List<PropertyFactorEntity> getPropfactorDtlList() {
        return propfactorDtlList;
    }

    public void setPropfactorDtlList(List<PropertyFactorEntity> propfactorDtlList) {
        this.propfactorDtlList = propfactorDtlList;
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
	
	public void setAssdNatureOfproperty5(Long assdNatureOfproperty5) {
        this.assdNatureOfproperty5 = assdNatureOfproperty5;
    }

    public Long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(Long faYearId) {
        this.faYearId = faYearId;
    }

	public List<PropertyRoomDetailEntity> getRoomDetailEntityList() {
		return roomDetailEntityList;
	}

	public void setRoomDetailEntityList(List<PropertyRoomDetailEntity> roomDetailEntityList) {
		this.roomDetailEntityList = roomDetailEntityList;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}
    
    
}
