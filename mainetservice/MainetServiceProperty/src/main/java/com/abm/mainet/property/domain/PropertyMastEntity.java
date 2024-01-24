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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tb_as_prop_mas")
public class PropertyMastEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5080996460449361749L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PM_PROPID")
    private long pmPropid;

    @Column(name = "APM_APPLICATION_ID")
    private Long apmApplicationId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "PM_APPROVAL_NO")
    private String tppApprovalNo;

    @Column(name = "PM_ACQ_DATE")
    private Date assAcqDate;

    @Column(name = "PM_ACTIVE")
    private String assActive;

    @Column(name = "PM_ADDRESS")
    private String assAddress;

    @Column(name = "PM_BUIT_AREA_GR")
    private Double assBuitAreaGr;

    @Column(name = "FLAG")
    private String flag;

    @Column(name = "BARCODE")
    @Lob
    private byte[] barCode;

    @Column(name = "PM_CORR_ADDRESS")
    private String assCorrAddress;

    @Column(name = "PM_CORR_EMAIL")
    private String assCorrEmail;

    @Column(name = "PM_CORR_PINCODE")
    private Long assCorrPincode;

    @Column(name = "PM_EMAIL")
    private String assEmail;

    @Column(name = "PM_GIS_ID")
    private String assGisId;

    @Column(name = "PM_PROP_NO")
    private String assNo;

    @Column(name = "PM_PROP_OLDPROPNO")
    private String assOldpropno;

    @Column(name = "PM_OWNER_TYPE")
    private Long assOwnerType;

    @Column(name = "PM_PINCODE")
    private Long assPincode;

    @Column(name = "PM_PLOT_AREA")
    private Double assPlotArea;

    @Column(name = "PM_PROP_TYPE1")
    private Long assPropType1;

    @Column(name = "PM_PROP_TYPE2")
    private Long assPropType2;

    @Column(name = "PM_PROP_TYPE3")
    private Long assPropType3;

    @Column(name = "PM_PROP_TYPE4")
    private Long assPropType4;

    @Column(name = "PM_PROP_TYPE5")
    private Long assPropType5;

    @Column(name = "PM_STATUS")
    private String assStatus;

    @Column(name = "PM_STREET_NO")
    private String assStreetNo;

    @Column(name = "PM_WARD1")
    private Long assWard1;

    @Column(name = "PM_WARD2")
    private Long assWard2;

    @Column(name = "PM_WARD3")
    private Long assWard3;

    @Column(name = "PM_WARD4")
    private Long assWard4;

    @Column(name = "PM_WARD5")
    private Long assWard5;

    @Column(name = "PM_BUILDING_NAME")
    private String buildingName;

    @Column(name = "PM_BUILDING_PERMISSION_DATE")
    private Date buildingPermissionDate;

    @Column(name = "PM_BUILDING_PERMISSION_NUMBER")
    private String buildingPermissionNumber;

    @Column(name = "PM_CABINS")
    private String cabins;

    @Column(name = "PM_CASE_ID")
    private String caseId;

    @Column(name = "PM_CELLULAR_TRANSMISSION_ANTENNA")
    private String cellularTransmissionAntenna;

    @Column(name = "PM_CHILD_PROP")
    private Long childProp;

    @Column(name = "PM_GATES_ENTRANCE")
    private String gatesEntrance;

    @Column(name = "PM_HEIGHT_OF_BUILDING")
    private Long heightOfBuilding;

    @Column(name = "PM_INDFY_REASON")
    private Long indfyReason;

    @Column(name = "PM_KHATA_NO")
    private String tppKhataNo;

    @Column(name = "PM_LAND_DEVELOPMENT_PERMISSION_DATE")
    private Date landDevelopmentPermissionDate;

    @Column(name = "PM_LAND_DEVELOPMENT_PERMISSION_NO")
    private String landDevelopmentPermissionNo;

    @Column(name = "PM_LOC_ID")
    private Long locId;

    @Column(name = "PM_NATURE_OF_LAND")
    private String natureOfLand;

    @Column(name = "PM_NO_OF_FOUR_WHEELER_PARKS")
    private Long noOfFourWheelerParks;

    @Column(name = "PM_NO_OF_TREES")
    private Long noOfTrees;

    @Column(name = "PM_NO_OF_TWO_WHEELER_PARKS")
    private Long noOfTwoWheelerParks;

    @Column(name = "PM_NUMBER_OF_FLOOR_UNITS")
    private Long numberOfFloorUnits;

    @Column(name = "PM_PARENT_PROP")
    private Long parentProp;

    @Column(name = "PM_PLOT_NO")
    private String tppPlotNo;

    @Column(name = "PM_PLOT_NO_CS")
    private String tppPlotNoCs;

    @Column(name = "PM_RAIN_WATER_HARVESTING")
    private String rainWaterHarvesting;

    @Column(name = "PM_SEWERAGE_FACILITY")
    private String sewerageFacility;

    @Column(name = "PM_SOLAR_EQUIPMENT")
    private String solarEquipment;

    @Column(name = "PM_SURVEY_NUMBER")
    private String tppSurveyNumber;

    @Column(name = "PM_TOJI_NO")
    private String tppTojiNo;

    @Column(name = "PM_VILLAGE_MAUJA")
    private String tppVillageMauja;

    @Column(name = "SM_SERVICE_ID")
    private String smServiceId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "PM_PROP_LVL_ROAD_TYPE")
    private Long propLvlRoadType;

    @Column(name = "DISTRICT")
    private String assDistrict;

    @Column(name = "TAHASIL")
    private String assTahasil;

    @Column(name = "HALKANO")
    private String assHalkaNo;

    @Column(name = "LAND_TYPE")
    private Long assLandType;

    @Column(name = "DEED_NO")
    private String assDeedNo;

    @Column(name = "PM_ASS_TAX_COLL_EMP")
    private Long taxCollEmp;

    @Column(name = "MOHALLA")
    private String mohalla;
    
    @Column(name = "PRO_ASS_VSRNO")
    private String assVsrNo;
    
    @Column(name = "group_prop_no")
    private String groupPropNo;

    @Column(name = "group_prop_name")
    private String groupPropName;

    @Column(name = "parent_prop_no")
    private String parentPropNo;

    @Column(name = "parent_prop_name")
    private String parentPropName;

    @Column(name = "is_group")
    private String isGroup;
    
    @Column(name = "CPD_BILLMETH")
    private Long billMethod;
    
    @Column(name = "pd_flatno")
    private String flatNo;
    
    @Column(name = "PM_REFPROPNO")
    private String refPropNo;
        
    @Column(name = "PM_ASSNTDT")
    private Date firstAssessmentDate;

    @Column(name = "PM_LSTASSNTDT")
    private Date lastAssessmentDate;
          
    @Column(name = "CPD_SURVEYTYP")
    private Long surveyType;
    
    @Column(name = "LM_ADDN_SURVEYNO")
    private String addiSurveyNo;
    
    @Column(name = "LM_ADDN_CITYSURVEYNO")
    private String addiCityServeyNo;
    
    @Column(name = "PM_AALINO")
    private String aaliNo;
        
    @Column(name = "PM_CHAALTANO")
    private String chaaltaNo;
      
    @Column(name = "PM_AREA_NAME")
    private String areaName;
    
    @Column(name = "PM_CLUSTER_NO")
    private Long clusterNo;
    
    @Column(name = "PM_CLU_BUILDING_NO")
    private Long buildingNo; 
    
    @Column(name = "PM_REVISE_ASSNTDT")
    private Date reviseAssessmentDate;
       
    @Column(name = "PM_BILL_CNG_REASON")
    private String billingMethodReason;
    
    @Column(name = "PM_URB_LOC_GRP")
    private String urbLocationGrp;

	@Column(name = "UNIQUE_PROPERTY_ID")
	private String uniquePropertyId;
	
	@Column(name = "LOGICAL_PROP_NO")
    private String logicalPropNo;
	
	@Column(name = "ADDRESS_REG")
	private String assAddressReg;

	@Column(name = "AREA_NAME_REG")
	private String areaNameReg;
	
	@Column(name = "BILL_MET_CNG_FLAG")
	private String billMethodChngFlag;
	
	@Column(name = "PM_SERVICE_STATUS")
	private Long serviceStatus;
	
	@Column(name = "new_house_no")
	private String newHouseNo;
	
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tbAsPrimaryMast", cascade = CascadeType.ALL)
    @Where(clause = "PD_ACTIVE = 'A'")
    private List<PropertyDetEntity> proDetList;

    public PropertyMastEntity() {
        super();
    }

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_primary_prop_mast", "PM_PROPID" };

    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
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

    public String getTppApprovalNo() {
        return tppApprovalNo;
    }

    public void setTppApprovalNo(String tppApprovalNo) {
        this.tppApprovalNo = tppApprovalNo;
    }

    public Date getAssAcqDate() {
        return assAcqDate;
    }

    public void setAssAcqDate(Date assAcqDate) {
        this.assAcqDate = assAcqDate;
    }

    public String getAssActive() {
        return assActive;
    }

    public void setAssActive(String assActive) {
        this.assActive = assActive;
    }

    public String getAssAddress() {
        return assAddress;
    }

    public void setAssAddress(String assAddress) {
        this.assAddress = assAddress;
    }

    public Double getAssBuitAreaGr() {
        return assBuitAreaGr;
    }

    public void setAssBuitAreaGr(Double assBuitAreaGr) {
        this.assBuitAreaGr = assBuitAreaGr;
    }

    public String getAssCorrAddress() {
        return assCorrAddress;
    }

    public void setAssCorrAddress(String assCorrAddress) {
        this.assCorrAddress = assCorrAddress;
    }

    public String getAssCorrEmail() {
        return assCorrEmail;
    }

    public void setAssCorrEmail(String assCorrEmail) {
        this.assCorrEmail = assCorrEmail;
    }

    public String getAssEmail() {
        return assEmail;
    }

    public void setAssEmail(String assEmail) {
        this.assEmail = assEmail;
    }

    public String getAssGisId() {
        return assGisId;
    }

    public void setAssGisId(String assGisId) {
        this.assGisId = assGisId;
    }

    public String getAssNo() {
        return assNo;
    }

    public void setAssNo(String assNo) {
        this.assNo = assNo;
    }

    public String getAssOldpropno() {
        return assOldpropno;
    }

    public void setAssOldpropno(String assOldpropno) {
        this.assOldpropno = assOldpropno;
    }

    public Long getAssOwnerType() {
        return assOwnerType;
    }

    public void setAssOwnerType(Long assOwnerType) {
        this.assOwnerType = assOwnerType;
    }

    public Double getAssPlotArea() {
        return assPlotArea;
    }

    public void setAssPlotArea(Double assPlotArea) {
        this.assPlotArea = assPlotArea;
    }

    public Long getAssPropType1() {
        return assPropType1;
    }

    public void setAssPropType1(Long assPropType1) {
        this.assPropType1 = assPropType1;
    }

    public Long getAssPropType2() {
        return assPropType2;
    }

    public void setAssPropType2(Long assPropType2) {
        this.assPropType2 = assPropType2;
    }

    public Long getAssPropType3() {
        return assPropType3;
    }

    public void setAssPropType3(Long assPropType3) {
        this.assPropType3 = assPropType3;
    }

    public Long getAssPropType4() {
        return assPropType4;
    }

    public void setAssPropType4(Long assPropType4) {
        this.assPropType4 = assPropType4;
    }

    public Long getAssPropType5() {
        return assPropType5;
    }

    public void setAssPropType5(Long assPropType5) {
        this.assPropType5 = assPropType5;
    }

    public String getAssStatus() {
        return assStatus;
    }

    public void setAssStatus(String assStatus) {
        this.assStatus = assStatus;
    }

    public String getAssStreetNo() {
        return assStreetNo;
    }

    public void setAssStreetNo(String assStreetNo) {
        this.assStreetNo = assStreetNo;
    }

    public Long getAssWard1() {
        return assWard1;
    }

    public void setAssWard1(Long assWard1) {
        this.assWard1 = assWard1;
    }

    public Long getAssWard2() {
        return assWard2;
    }

    public void setAssWard2(Long assWard2) {
        this.assWard2 = assWard2;
    }

    public Long getAssWard3() {
        return assWard3;
    }

    public void setAssWard3(Long assWard3) {
        this.assWard3 = assWard3;
    }

    public Long getAssWard4() {
        return assWard4;
    }

    public void setAssWard4(Long assWard4) {
        this.assWard4 = assWard4;
    }

    public Long getAssWard5() {
        return assWard5;
    }

    public void setAssWard5(Long assWard5) {
        this.assWard5 = assWard5;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Date getBuildingPermissionDate() {
        return buildingPermissionDate;
    }

    public void setBuildingPermissionDate(Date buildingPermissionDate) {
        this.buildingPermissionDate = buildingPermissionDate;
    }

    public String getBuildingPermissionNumber() {
        return buildingPermissionNumber;
    }

    public void setBuildingPermissionNumber(String buildingPermissionNumber) {
        this.buildingPermissionNumber = buildingPermissionNumber;
    }

    public String getCabins() {
        return cabins;
    }

    public void setCabins(String cabins) {
        this.cabins = cabins;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCellularTransmissionAntenna() {
        return cellularTransmissionAntenna;
    }

    public void setCellularTransmissionAntenna(String cellularTransmissionAntenna) {
        this.cellularTransmissionAntenna = cellularTransmissionAntenna;
    }

    public Long getChildProp() {
        return childProp;
    }

    public void setChildProp(Long childProp) {
        this.childProp = childProp;
    }

    public String getGatesEntrance() {
        return gatesEntrance;
    }

    public void setGatesEntrance(String gatesEntrance) {
        this.gatesEntrance = gatesEntrance;
    }

    public Long getHeightOfBuilding() {
        return heightOfBuilding;
    }

    public void setHeightOfBuilding(Long heightOfBuilding) {
        this.heightOfBuilding = heightOfBuilding;
    }

    public Long getIndfyReason() {
        return indfyReason;
    }

    public void setIndfyReason(Long indfyReason) {
        this.indfyReason = indfyReason;
    }

    public String getTppKhataNo() {
        return tppKhataNo;
    }

    public void setTppKhataNo(String tppKhataNo) {
        this.tppKhataNo = tppKhataNo;
    }

    public Date getLandDevelopmentPermissionDate() {
        return landDevelopmentPermissionDate;
    }

    public void setLandDevelopmentPermissionDate(Date landDevelopmentPermissionDate) {
        this.landDevelopmentPermissionDate = landDevelopmentPermissionDate;
    }

    public String getLandDevelopmentPermissionNo() {
        return landDevelopmentPermissionNo;
    }

    public void setLandDevelopmentPermissionNo(String landDevelopmentPermissionNo) {
        this.landDevelopmentPermissionNo = landDevelopmentPermissionNo;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public String getNatureOfLand() {
        return natureOfLand;
    }

    public void setNatureOfLand(String natureOfLand) {
        this.natureOfLand = natureOfLand;
    }

    public Long getNoOfFourWheelerParks() {
        return noOfFourWheelerParks;
    }

    public void setNoOfFourWheelerParks(Long noOfFourWheelerParks) {
        this.noOfFourWheelerParks = noOfFourWheelerParks;
    }

    public Long getNoOfTrees() {
        return noOfTrees;
    }

    public void setNoOfTrees(Long noOfTrees) {
        this.noOfTrees = noOfTrees;
    }

    public Long getNoOfTwoWheelerParks() {
        return noOfTwoWheelerParks;
    }

    public void setNoOfTwoWheelerParks(Long noOfTwoWheelerParks) {
        this.noOfTwoWheelerParks = noOfTwoWheelerParks;
    }

    public Long getNumberOfFloorUnits() {
        return numberOfFloorUnits;
    }

    public void setNumberOfFloorUnits(Long numberOfFloorUnits) {
        this.numberOfFloorUnits = numberOfFloorUnits;
    }

    public Long getParentProp() {
        return parentProp;
    }

    public void setParentProp(Long parentProp) {
        this.parentProp = parentProp;
    }

    public String getTppPlotNo() {
        return tppPlotNo;
    }

    public void setTppPlotNo(String tppPlotNo) {
        this.tppPlotNo = tppPlotNo;
    }

    public String getTppPlotNoCs() {
        return tppPlotNoCs;
    }

    public void setTppPlotNoCs(String tppPlotNoCs) {
        this.tppPlotNoCs = tppPlotNoCs;
    }

    public String getRainWaterHarvesting() {
        return rainWaterHarvesting;
    }

    public void setRainWaterHarvesting(String rainWaterHarvesting) {
        this.rainWaterHarvesting = rainWaterHarvesting;
    }

    public String getSewerageFacility() {
        return sewerageFacility;
    }

    public void setSewerageFacility(String sewerageFacility) {
        this.sewerageFacility = sewerageFacility;
    }

    public String getSolarEquipment() {
        return solarEquipment;
    }

    public void setSolarEquipment(String solarEquipment) {
        this.solarEquipment = solarEquipment;
    }

    public String getTppSurveyNumber() {
        return tppSurveyNumber;
    }

    public void setTppSurveyNumber(String tppSurveyNumber) {
        this.tppSurveyNumber = tppSurveyNumber;
    }

    public String getTppTojiNo() {
        return tppTojiNo;
    }

    public void setTppTojiNo(String tppTojiNo) {
        this.tppTojiNo = tppTojiNo;
    }

    public String getTppVillageMauja() {
        return tppVillageMauja;
    }

    public void setTppVillageMauja(String tppVillageMauja) {
        this.tppVillageMauja = tppVillageMauja;
    }

    public String getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(String smServiceId) {
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

    public List<PropertyDetEntity> getProDetList() {
        return proDetList;
    }

    public void setProDetList(List<PropertyDetEntity> proDetList) {
        this.proDetList = proDetList;
    }

    public long getPmPropid() {
        return pmPropid;
    }

    public void setPmPropid(long pmPropid) {
        this.pmPropid = pmPropid;
    }

    public Long getPropLvlRoadType() {
        return propLvlRoadType;
    }

    public void setPropLvlRoadType(Long propLvlRoadType) {
        this.propLvlRoadType = propLvlRoadType;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public byte[] getBarCode() {
        return barCode;
    }

    public void setBarCode(byte[] barCode) {
        this.barCode = barCode;
    }

    public String getAssDistrict() {
        return assDistrict;
    }

    public void setAssDistrict(String assDistrict) {
        this.assDistrict = assDistrict;
    }

    public String getAssTahasil() {
        return assTahasil;
    }

    public void setAssTahasil(String assTahasil) {
        this.assTahasil = assTahasil;
    }

    public String getAssHalkaNo() {
        return assHalkaNo;
    }

    public void setAssHalkaNo(String assHalkaNo) {
        this.assHalkaNo = assHalkaNo;
    }

    public Long getAssLandType() {
        return assLandType;
    }

    public void setAssLandType(Long assLandType) {
        this.assLandType = assLandType;
    }

    public String getAssDeedNo() {
        return assDeedNo;
    }

    public void setAssDeedNo(String assDeedNo) {
        this.assDeedNo = assDeedNo;
    }

    public Long getTaxCollEmp() {
        return taxCollEmp;
    }

    public void setTaxCollEmp(Long taxCollEmp) {
        this.taxCollEmp = taxCollEmp;
    }

    public String getMohalla() {
        return mohalla;
    }

    public void setMohalla(String mohalla) {
        this.mohalla = mohalla;
    }

    public Long getAssCorrPincode() {
        return assCorrPincode;
    }

    public void setAssCorrPincode(Long assCorrPincode) {
        this.assCorrPincode = assCorrPincode;
    }

    public Long getAssPincode() {
        return assPincode;
    }

    public void setAssPincode(Long assPincode) {
        this.assPincode = assPincode;
    }

	public String getAssVsrNo() {
		return assVsrNo;
	}

	public void setAssVsrNo(String assVsrNo) {
		this.assVsrNo = assVsrNo;
	}

	public String getGroupPropNo() {
		return groupPropNo;
	}

	public void setGroupPropNo(String groupPropNo) {
		this.groupPropNo = groupPropNo;
	}

	public String getGroupPropName() {
		return groupPropName;
	}

	public void setGroupPropName(String groupPropName) {
		this.groupPropName = groupPropName;
	}

	public String getParentPropNo() {
		return parentPropNo;
	}

	public void setParentPropNo(String parentPropNo) {
		this.parentPropNo = parentPropNo;
	}

	public String getParentPropName() {
		return parentPropName;
	}

	public void setParentPropName(String parentPropName) {
		this.parentPropName = parentPropName;
	}

	public String getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}

	public Long getBillMethod() {
		return billMethod;
	}

	public void setBillMethod(Long billMethod) {
		this.billMethod = billMethod;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getRefPropNo() {
		return refPropNo;
	}

	public void setRefPropNo(String refPropNo) {
		this.refPropNo = refPropNo;
	}

	public Date getFirstAssessmentDate() {
		return firstAssessmentDate;
	}

	public void setFirstAssessmentDate(Date firstAssessmentDate) {
		this.firstAssessmentDate = firstAssessmentDate;
	}

	public Date getLastAssessmentDate() {
		return lastAssessmentDate;
	}

	public void setLastAssessmentDate(Date lastAssessmentDate) {
		this.lastAssessmentDate = lastAssessmentDate;
	}
	
	public Long getSurveyType() {
		return surveyType;
	}

	public void setSurveyType(Long surveyType) {
		this.surveyType = surveyType;
	}

	public String getAddiSurveyNo() {
		return addiSurveyNo;
	}

	public void setAddiSurveyNo(String addiSurveyNo) {
		this.addiSurveyNo = addiSurveyNo;
	}

	public String getAddiCityServeyNo() {
		return addiCityServeyNo;
	}

	public void setAddiCityServeyNo(String addiCityServeyNo) {
		this.addiCityServeyNo = addiCityServeyNo;
	}

	public String getAaliNo() {
		return aaliNo;
	}

	public void setAaliNo(String aaliNo) {
		this.aaliNo = aaliNo;
	}

	public String getChaaltaNo() {
		return chaaltaNo;
	}

	public void setChaaltaNo(String chaaltaNo) {
		this.chaaltaNo = chaaltaNo;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getClusterNo() {
		return clusterNo;
	}

	public void setClusterNo(Long clusterNo) {
		this.clusterNo = clusterNo;
	}

	public Long getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(Long buildingNo) {
		this.buildingNo = buildingNo;
	}

	public Date getReviseAssessmentDate() {
		return reviseAssessmentDate;
	}

	public void setReviseAssessmentDate(Date reviseAssessmentDate) {
		this.reviseAssessmentDate = reviseAssessmentDate;
	}

	public String getBillingMethodReason() {
		return billingMethodReason;
	}

	public void setBillingMethodReason(String billingMethodReason) {
		this.billingMethodReason = billingMethodReason;
	}

	public String getUrbLocationGrp() {
		return urbLocationGrp;
	}

	public void setUrbLocationGrp(String urbLocationGrp) {
		this.urbLocationGrp = urbLocationGrp;
	}

	public String getUniquePropertyId() {
		return uniquePropertyId;
	}

	public void setUniquePropertyId(String uniquePropertyId) {
		this.uniquePropertyId = uniquePropertyId;
	}

	public String getLogicalPropNo() {
		return logicalPropNo;
	}

	public void setLogicalPropNo(String logicalPropNo) {
		this.logicalPropNo = logicalPropNo;
	}

	public String getAssAddressReg() {
		return assAddressReg;
	}

	public void setAssAddressReg(String assAddressReg) {
		this.assAddressReg = assAddressReg;
	}

	public String getAreaNameReg() {
		return areaNameReg;
	}

	public void setAreaNameReg(String areaNameReg) {
		this.areaNameReg = areaNameReg;
	}

	public String getBillMethodChngFlag() {
		return billMethodChngFlag;
	}

	public void setBillMethodChngFlag(String billMethodChngFlag) {
		this.billMethodChngFlag = billMethodChngFlag;
	}

	public Long getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(Long serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getNewHouseNo() {
		return newHouseNo;
	}

	public void setNewHouseNo(String newHouseNo) {
		this.newHouseNo = newHouseNo;
	}	
	
}
