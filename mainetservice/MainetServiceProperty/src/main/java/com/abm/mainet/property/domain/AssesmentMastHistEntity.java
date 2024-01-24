package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author anwarul.hassan
 * @since 07-Dec-2020
 */
@Entity
@Table(name = "TB_AS_ASSESMENT_MAST_HIST")
public class AssesmentMastHistEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MN_ASS_HIS_id")
    private long mnAssHisId;

    @Column(name = "MN_ASS_id")
    private long mnAssId;

    @Column(name = "MN_ASS_no")
    private String assNo;

    @Column(name = "MN_approval_no")
    private String tppApprovalNo;

    @Column(name = "MN_ASS_oldpropno")
    private String assOldpropno;

    @Column(name = "MN_plot_no_cs")
    private String tppPlotNoCs;

    @Column(name = "FLAG")
    private String flag;

    @Column(name = "Barcode")
    @Lob
    private byte[] barCode;

    @Column(name = "MN_survey_number")
    private String tppSurveyNumber;

    @Column(name = "MN_khata_no")
    private String tppKhataNo;

    @Column(name = "MN_toji_no")
    private String tppTojiNo;

    @Column(name = "MN_plot_no")
    private String tppPlotNo;

    @Column(name = "MN_ASS_street_no")
    private String assStreetNo;

    @Column(name = "MN_village_mauja")
    private String tppVillageMauja;

    @Column(name = "MN_ASS_address")
    private String assAddress;

    @Column(name = "MN_ASS_EMAIL")
    private String assEmail;

    @Column(name = "MN_loc_id")
    private Long locId;

    @Column(name = "MN_ASS_pincode")
    private Long assPincode;

    @Column(name = "MN_ASS_corr_address")
    private String assCorrAddress;

    @Column(name = "MN_ASS_corr_email")
    private String assACorrEmail;

    @Column(name = "MN_ASS_corr_pincode")
    private Long assCorrPincode;

    @Column(name = "MN_ASS_lp_receipt_no")
    private String assLpReceiptNo;

    @Column(name = "MN_ASS_lp_receipt_amt")
    private BigDecimal assLpReceiptAmt;

    @Column(name = "MN_ASS_lp_receipt_date")
    private Date assLpReceiptDate;

    @Column(name = "MN_ASS_lp_year")
    private Long assLpYear;

    @Column(name = "MN_ASS_lp_bill_cycle")
    private Long assLpBillCycle;

    @Column(name = "MN_Bill_Amount")
    private BigDecimal billAmount;

    @Column(name = "MN_Outstanding_Amount")
    private BigDecimal outstandingAmount;

    @Column(name = "MN_ASS_acq_date")
    private Date assAcqDate;

    @Column(name = "MN_ASS_prop_type1")
    private Long assPropType1;

    @Column(name = "MN_ASS_prop_type2")
    private Long assPropType2;

    @Column(name = "MN_ASS_prop_type3")
    private Long assPropType3;

    @Column(name = "MN_ASS_prop_type4")
    private Long assPropType4;

    @Column(name = "MN_ASS_prop_type5")
    private Long assPropType5;

    @Column(name = "MN_ASS_plot_area")
    private Double assPlotArea;

    @Column(name = "MN_ASS_buit_area_gr")
    private Double assBuitAreaGr;

    @Column(name = "MN_ASS_owner_type")
    private Long assOwnerType;

    @Column(name = "MN_ASS_ward1")
    private Long assWard1;

    @Column(name = "MN_ASS_ward2")
    private Long assWard2;

    @Column(name = "MN_ASS_ward3")
    private Long assWard3;

    @Column(name = "MN_ASS_ward4")
    private Long assWard4;

    @Column(name = "MN_ASS_ward5")
    private Long assWard5;

    @Column(name = "MN_ASS_gis_id")
    private String assGisId;

    @Column(name = "MN_ASS_active")
    private String assActive;

    @Column(name = "MN_ASS_status")
    private String assStatus;

    @Column(name = "MN_ASS_aut_status")
    private String assAutStatus;

    @Column(name = "MN_ASS_aut_by")
    private Long assAutBy;

    @Column(name = "MN_ASS_aut_date")
    private Date assAutDate;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "lg_ip_mac", nullable = false)
    private String lgIpMac;

    @Column(name = "lg_ip_mac_upd")
    private String lgIpMacUpd;

    @Column(name = "APM_APPLICATION_ID")
    private Long apmApplicationId;

    @Column(name = "SM_SERVICE_ID")
    private Long smServiceId;

    @Column(name = "MN_INDFY_reason")
    private Long indfyReason;

    @Column(name = "MN_CHILD_PROP")
    private Long childProp;

    @Column(name = "MN_PARENT_PROP")
    private String parentProp;

    @Column(name = "FA_YEARID")
    private Long faYearId;

    @Column(name = "MN_PROP_LVL_ROAD_TYPE")
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

    @Column(name = "MN_ASS_TAX_COLL_EMP ")
    private Long taxCollEmp;

    @Column(name = "MOHALLA")
    private String mohalla;

    @Column(name = "MN_ASS_VSRNO")
    private String assVsrNo;

    @Column(name = "editable_date")
    private Date editableDate;
    
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

    @Column(name = "DES_UPD_FLAG")
    private String updateDataEntryFlag;
    
    @Column(name = "LOGICAL_PROP_NO")
    private String logicalPropNo;
    
    @Column(name = "MN_REFPROPNO")
    private String refPropNo;
           
    @Column(name = "MN_ASSNTDT")
    private Date firstAssessmentDate;

    @Column(name = "MN_LSTASSNTDT")
    private Date lastAssessmentDate;
      
    @Column(name = "MN_REVISE_ASSNTDT")
    private Date reviseAssessmentDate;
    
    @Column(name = "CPD_BILLMETH")
    private Long billMethod;
    
    @Column(name = "MN_BILL_CNG_REASON")
    private String billingMethodReason;
          
    @Column(name = "CPD_SURVEYTYP")
    private Long surveyType;
    
    @Column(name = "MN_ADDN_SURVEYNO")
    private String addiSurveyNo;
    
    @Column(name = "MN_ADDN_CITYSURVEYNO")
    private String addiCityServeyNo;
    
    @Column(name = "MN_AALINO")
    private String aaliNo;
        
    @Column(name = "MN_CHAALTANO")
    private String chaaltaNo;
      
    @Column(name = "MN_AREA_NAME")
    private String areaName;
    
    @Column(name = "MN_CLUSTER_NO")
    private Long clusterNo;
    
    @Column(name = "MN_CLU_BUILDING_NO")
    private Long buildingNo; 
         
    @Column(name = "MN_URB_LOC_GRP")
    private String urbLocationGrp;
    
    @Column(name = "MN_SPL_NOT_DUE_DATE")
    private Date splNotDueDate;
      
    @Column(name = "MN_FLAT_NO")
    private String flatNo;
    
	@Column(name = "UNIQUE_PROPERTY_ID")
	private String uniquePropertyId;

	@Column(name = "ADDRESS_REG")
	private String assAddressReg;

	@Column(name = "AREA_NAME_REG")
	private String areaNameReg;
	
	@Column(name = "BILL_MET_CNG_FLAG")
	private String billMethodChngFlag;
	
	@Column(name = "SERVICE_STATUS")
	private Long serviceStatus;
	
	@Column(name = "MN_ASS__PARSH_WARD1")
	private Long assParshadWard1;

	@Column(name = "MN_ASS__PARSH_WARD2")
	private Long assParshadWard2;

	@Column(name = "MN_ASS__PARSH_WARD3")
	private Long assParshadWard3;

	@Column(name = "MN_ASS__PARSH_WARD4")
	private Long assParshadWard4;

	@Column(name = "MN_ASS__PARSH_WARD5")
	private Long assParshadWard5;
	
	@Column(name = "new_house_no")
	private String newHouseNo;
	
	@Column(name = "H_STATUS")
    private String hStatus;
	
    public String[] getPkValues() {
        return new String[] { "AS", "TB_AS_ASSESMENT_MAST_HIST", "MN_ASS_HIS_ID" };
    }

    public long getMnAssHisId() {
        return mnAssHisId;
    }

    public void setMnAssHisId(long mnAssHisId) {
        this.mnAssHisId = mnAssHisId;
    }

    public long getMnAssId() {
        return mnAssId;
    }

    public void setMnAssId(long mnAssId) {
        this.mnAssId = mnAssId;
    }

    public String getAssNo() {
        return assNo;
    }

    public void setAssNo(String assNo) {
        this.assNo = assNo;
    }

    public String getTppApprovalNo() {
        return tppApprovalNo;
    }

    public void setTppApprovalNo(String tppApprovalNo) {
        this.tppApprovalNo = tppApprovalNo;
    }

    public String getAssOldpropno() {
        return assOldpropno;
    }

    public void setAssOldpropno(String assOldpropno) {
        this.assOldpropno = assOldpropno;
    }

    public String getTppPlotNoCs() {
        return tppPlotNoCs;
    }

    public void setTppPlotNoCs(String tppPlotNoCs) {
        this.tppPlotNoCs = tppPlotNoCs;
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

    public String getTppSurveyNumber() {
        return tppSurveyNumber;
    }

    public void setTppSurveyNumber(String tppSurveyNumber) {
        this.tppSurveyNumber = tppSurveyNumber;
    }

    public String getTppKhataNo() {
        return tppKhataNo;
    }

    public void setTppKhataNo(String tppKhataNo) {
        this.tppKhataNo = tppKhataNo;
    }

    public String getTppTojiNo() {
        return tppTojiNo;
    }

    public void setTppTojiNo(String tppTojiNo) {
        this.tppTojiNo = tppTojiNo;
    }

    public String getTppPlotNo() {
        return tppPlotNo;
    }

    public void setTppPlotNo(String tppPlotNo) {
        this.tppPlotNo = tppPlotNo;
    }

    public String getAssStreetNo() {
        return assStreetNo;
    }

    public void setAssStreetNo(String assStreetNo) {
        this.assStreetNo = assStreetNo;
    }

    public String getTppVillageMauja() {
        return tppVillageMauja;
    }

    public void setTppVillageMauja(String tppVillageMauja) {
        this.tppVillageMauja = tppVillageMauja;
    }

    public String getAssAddress() {
        return assAddress;
    }

    public void setAssAddress(String assAddress) {
        this.assAddress = assAddress;
    }

    public String getAssEmail() {
        return assEmail;
    }

    public void setAssEmail(String assEmail) {
        this.assEmail = assEmail;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getAssPincode() {
        return assPincode;
    }

    public void setAssPincode(Long assPincode) {
        this.assPincode = assPincode;
    }

    public String getAssCorrAddress() {
        return assCorrAddress;
    }

    public void setAssCorrAddress(String assCorrAddress) {
        this.assCorrAddress = assCorrAddress;
    }

    public String getAssACorrEmail() {
        return assACorrEmail;
    }

    public void setAssACorrEmail(String assACorrEmail) {
        this.assACorrEmail = assACorrEmail;
    }

    public Long getAssCorrPincode() {
        return assCorrPincode;
    }

    public void setAssCorrPincode(Long assCorrPincode) {
        this.assCorrPincode = assCorrPincode;
    }

    public String getAssLpReceiptNo() {
        return assLpReceiptNo;
    }

    public void setAssLpReceiptNo(String assLpReceiptNo) {
        this.assLpReceiptNo = assLpReceiptNo;
    }

    public BigDecimal getAssLpReceiptAmt() {
        return assLpReceiptAmt;
    }

    public void setAssLpReceiptAmt(BigDecimal assLpReceiptAmt) {
        this.assLpReceiptAmt = assLpReceiptAmt;
    }

    public Date getAssLpReceiptDate() {
        return assLpReceiptDate;
    }

    public void setAssLpReceiptDate(Date assLpReceiptDate) {
        this.assLpReceiptDate = assLpReceiptDate;
    }

    public Long getAssLpYear() {
        return assLpYear;
    }

    public void setAssLpYear(Long assLpYear) {
        this.assLpYear = assLpYear;
    }

    public Long getAssLpBillCycle() {
        return assLpBillCycle;
    }

    public void setAssLpBillCycle(Long assLpBillCycle) {
        this.assLpBillCycle = assLpBillCycle;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Date getAssAcqDate() {
        return assAcqDate;
    }

    public void setAssAcqDate(Date assAcqDate) {
        this.assAcqDate = assAcqDate;
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

    public Double getAssPlotArea() {
        return assPlotArea;
    }

    public void setAssPlotArea(Double assPlotArea) {
        this.assPlotArea = assPlotArea;
    }

    public Double getAssBuitAreaGr() {
        return assBuitAreaGr;
    }

    public void setAssBuitAreaGr(Double assBuitAreaGr) {
        this.assBuitAreaGr = assBuitAreaGr;
    }

    public Long getAssOwnerType() {
        return assOwnerType;
    }

    public void setAssOwnerType(Long assOwnerType) {
        this.assOwnerType = assOwnerType;
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

    public String getAssGisId() {
        return assGisId;
    }

    public void setAssGisId(String assGisId) {
        this.assGisId = assGisId;
    }

    public String getAssActive() {
        return assActive;
    }

    public void setAssActive(String assActive) {
        this.assActive = assActive;
    }

    public String getAssStatus() {
        return assStatus;
    }

    public void setAssStatus(String assStatus) {
        this.assStatus = assStatus;
    }

    public String getAssAutStatus() {
        return assAutStatus;
    }

    public void setAssAutStatus(String assAutStatus) {
        this.assAutStatus = assAutStatus;
    }

    public Long getAssAutBy() {
        return assAutBy;
    }

    public void setAssAutBy(Long assAutBy) {
        this.assAutBy = assAutBy;
    }

    public Date getAssAutDate() {
        return assAutDate;
    }

    public void setAssAutDate(Date assAutDate) {
        this.assAutDate = assAutDate;
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

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public Long getIndfyReason() {
        return indfyReason;
    }

    public void setIndfyReason(Long indfyReason) {
        this.indfyReason = indfyReason;
    }

    public Long getChildProp() {
        return childProp;
    }

    public void setChildProp(Long childProp) {
        this.childProp = childProp;
    }

    public String getParentProp() {
        return parentProp;
    }

    public void setParentProp(String parentProp) {
        this.parentProp = parentProp;
    }

    public Long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(Long faYearId) {
        this.faYearId = faYearId;
    }

    public Long getPropLvlRoadType() {
        return propLvlRoadType;
    }

    public void setPropLvlRoadType(Long propLvlRoadType) {
        this.propLvlRoadType = propLvlRoadType;
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

    public String getAssVsrNo() {
        return assVsrNo;
    }

    public void setAssVsrNo(String assVsrNo) {
        this.assVsrNo = assVsrNo;
    }

    public Date getEditableDate() {
        return editableDate;
    }

    public void setEditableDate(Date editableDate) {
        this.editableDate = editableDate;
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

	public String getUpdateDataEntryFlag() {
		return updateDataEntryFlag;
	}

	public void setUpdateDataEntryFlag(String updateDataEntryFlag) {
		this.updateDataEntryFlag = updateDataEntryFlag;
	}

	public String getLogicalPropNo() {
		return logicalPropNo;
	}

	public void setLogicalPropNo(String logicalPropNo) {
		this.logicalPropNo = logicalPropNo;
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

	public Date getReviseAssessmentDate() {
		return reviseAssessmentDate;
	}

	public void setReviseAssessmentDate(Date reviseAssessmentDate) {
		this.reviseAssessmentDate = reviseAssessmentDate;
	}

	public Long getBillMethod() {
		return billMethod;
	}

	public void setBillMethod(Long billMethod) {
		this.billMethod = billMethod;
	}

	public String getBillingMethodReason() {
		return billingMethodReason;
	}

	public void setBillingMethodReason(String billingMethodReason) {
		this.billingMethodReason = billingMethodReason;
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

	public String getUrbLocationGrp() {
		return urbLocationGrp;
	}

	public void setUrbLocationGrp(String urbLocationGrp) {
		this.urbLocationGrp = urbLocationGrp;
	}

	public Date getSplNotDueDate() {
		return splNotDueDate;
	}

	public void setSplNotDueDate(Date splNotDueDate) {
		this.splNotDueDate = splNotDueDate;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getUniquePropertyId() {
		return uniquePropertyId;
	}

	public void setUniquePropertyId(String uniquePropertyId) {
		this.uniquePropertyId = uniquePropertyId;
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

	public Long getAssParshadWard1() {
		return assParshadWard1;
	}

	public void setAssParshadWard1(Long assParshadWard1) {
		this.assParshadWard1 = assParshadWard1;
	}

	public Long getAssParshadWard2() {
		return assParshadWard2;
	}

	public void setAssParshadWard2(Long assParshadWard2) {
		this.assParshadWard2 = assParshadWard2;
	}

	public Long getAssParshadWard3() {
		return assParshadWard3;
	}

	public void setAssParshadWard3(Long assParshadWard3) {
		this.assParshadWard3 = assParshadWard3;
	}

	public Long getAssParshadWard4() {
		return assParshadWard4;
	}

	public void setAssParshadWard4(Long assParshadWard4) {
		this.assParshadWard4 = assParshadWard4;
	}

	public Long getAssParshadWard5() {
		return assParshadWard5;
	}

	public void setAssParshadWard5(Long assParshadWard5) {
		this.assParshadWard5 = assParshadWard5;
	}

	public String getNewHouseNo() {
		return newHouseNo;
	}

	public void setNewHouseNo(String newHouseNo) {
		this.newHouseNo = newHouseNo;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}
	
	
    
}
