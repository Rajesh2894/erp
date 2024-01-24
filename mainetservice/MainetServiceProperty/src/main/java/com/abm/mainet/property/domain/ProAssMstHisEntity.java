package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_AS_PRO_MAST_HIST")
public class ProAssMstHisEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9218969737327010254L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PRO_ASS_HIST_ID")
    private long proAssHistId;

    @Column(name = "APM_APPLICATION_ID")
    private Long apmApplicationId;

    @Column(name = "BILL_AMOUNT")
    private Double billAmount;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "FA_YEARID")
    private Long faYearId;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "LG_IP_MAC", nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "LOC_ID")
    private Long locId;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "OUTSTANDING_AMOUNT")
    private Double outstandingAmount;

    @Column(name = "PRO_ASS_ACQ_DATE")
    private Date assAcqDate;

    @Column(name = "PRO_ASS_ACTIVE")
    private String assActive;

    @Column(name = "PRO_ASS_ADDRESS")
    private String assAddress;

    @Column(name = "PRO_ASS_AUT_BY")
    private Long assAutBy;

    @Column(name = "PRO_ASS_AUT_DATE")
    private Date assAutDate;

    @Column(name = "PRO_ASS_AUT_STATUS")
    private String assAutStatus;

    @Column(name = "PRO_ASS_BUIT_AREA_GR")
    private Double assBuitAreaGr;

    @Column(name = "PRO_ASS_CORR_ADDRESS")
    private String assCorrAddress;

    @Column(name = "PRO_ASS_CORR_EMAIL")
    private String assCorrEmail;

    @Column(name = "PRO_ASS_CORR_PINCODE")
    private Long assCorrPincode;

    @Column(name = "PRO_ASS_EMAIL")
    private String assEmail;

    @Column(name = "PRO_ASS_GIS_ID")
    private String assGisId;

    @Column(name = "PRO_ASS_ID")
    private Long proAssId;

    @Column(name = "PRO_ASS_LP_BILL_CYCLE")
    private Long assLpBillCycle;

    @Column(name = "PRO_ASS_LP_RECEIPT_AMT")
    private Double assLpReceiptAmt;

    @Column(name = "PRO_ASS_LP_RECEIPT_DATE")
    private Date assLpReceiptDate;

    @Column(name = "PRO_ASS_LP_RECEIPT_NO")
    private String assLpReceiptNo;

    @Column(name = "PRO_ASS_LP_YEAR")
    private Long assLpYear;

    @Column(name = "PRO_ASS_NO")
    private String assNo;

    @Column(name = "PRO_ASS_OLDPROPNO")
    private String assOldpropno;

    @Column(name = "PRO_ASS_OWNER_TYPE")
    private Long assOwnerType;

    @Column(name = "PRO_ASS_PINCODE")
    private Long assPincode;

    @Column(name = "PRO_ASS_PLOT_AREA")
    private Double assPlotArea;

    @Column(name = "PRO_ASS_PROP_TYPE1")
    private Long assPropType1;

    @Column(name = "PRO_ASS_PROP_TYPE2")
    private Long assPropType2;

    @Column(name = "PRO_ASS_PROP_TYPE3")
    private Long assPropType3;

    @Column(name = "PRO_ASS_PROP_TYPE4")
    private Long assPropType4;

    @Column(name = "PRO_ASS_PROP_TYPE5")
    private Long assPropType5;

    @Column(name = "PRO_ASS_STATUS")
    private String assStatus;

    @Column(name = "PRO_ASS_STREET_NO")
    private String assStreetNo;

    @Column(name = "PRO_ASS_WARD1")
    private Long assWard1;

    @Column(name = "PRO_ASS_WARD2")
    private Long assWard2;

    @Column(name = "PRO_ASS_WARD3")
    private Long assWard3;

    @Column(name = "PRO_ASS_WARD4")
    private Long assWard4;

    @Column(name = "PRO_ASS_WARD5")
    private Long assWard5;

    @Column(name = "PRO_CHILD_PROP")
    private Long childProp;

    @Column(name = "PRO_INDFY_REASON")
    private String indfyReason;

    @Column(name = "PRO_PARENT_PROP")
    private String parentProp;

    @Column(name = "SM_SERVICE_ID")
    private Long smServiceId;

    @Column(name = "TPP_APPROVAL_NO")
    private String tppApprovalNo;

    @Column(name = "TPP_KHATA_NO")
    private String tppKhataNo;

    @Column(name = "TPP_PLOT_NO")
    private String tppPlotNo;

    @Column(name = "TPP_PLOT_NO_CS")
    private String tppPlotNoCs;

    @Column(name = "TPP_SURVEY_NUMBER")
    private String tppSurveyNumber;

    @Column(name = "TPP_TOJI_NO")
    private String tppTojiNo;

    @Column(name = "TPP_VILLAGE_MAUJA")
    private String tppVillageMauja;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

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

    @Column(name = "FLAG")
    private String flag;

    @Column(name = "BARCODE")
    @Lob
    private byte[] barCode;

    @Column(name = "PRO_ASS_LP_TYPE")
    private String billPayment;

    @Column(name = "PRO_PROP_LVL_ROAD_TYPE")
    private Long propLvlRoadType;

    @Column(name = "PRO_ASS_TAX_COLL_EMP ")
    private Long taxCollEmp;

    @Column(name = "MOHALLA")
    private String mohalla;

    @Column(name = "PRO_ASS_VSRNO")
    private String assVsrNo;

    @Column(name = "editable_date")
    private Date editableDate;
    
    @Column(name = "PRO_REFPROPNO")
    private String refPropNo;
           
    @Column(name = "PRO_ASSNTDT")
    private Date firstAssessmentDate;

    @Column(name = "PRO_LSTASSNTDT")
    private Date lastAssessmentDate;
      
    @Column(name = "PRO_REVISE_ASSNTDT")
    private Date reviseAssessmentDate;
    
    @Column(name = "CPD_BILLMETH")
    private Long billMethod;
    
    @Column(name = "PRO_BILL_CNG_REASON")
    private String billingMethodReason;
          
    @Column(name = "PRO_SURVEYTYP")
    private Long surveyType;
    
    @Column(name = "PRO_ADDN_SURVEYNO")
    private String addiSurveyNo;
    
    @Column(name = "PRO_ADDN_CITYSURVEYNO")
    private String addiCityServeyNo;
    
    @Column(name = "LOGICAL_PROP_NO")
    private String logicalPropNo;
    
    @Column(name = "PRO_AALINO")
    private String aaliNo;
        
    @Column(name = "PRO_CHAALTANO")
    private String chaaltaNo;
      
    @Column(name = "PRO_AREA_NAME")
    private String areaName;
    
    @Column(name = "PRO_CLUSTER_NO")
    private Long clusterNo;
    
    @Column(name = "PRO_CLU_BUILDING_NO")
    private Long buildingNo; 
         
    @Column(name = "PRO_URB_LOC_GRP")
    private String urbLocationGrp;
    
    @Column(name = "PRO_SPL_NOT_DUE_DATE")
    private Date splNotDueDate;
    
    @Column(name = "PRO_FLAT_NO")
    private String flatNo;
    
    public long getProAssHistId() {
        return proAssHistId;
    }

    public String[] getPkValues() {
        return new String[] { "AS", "TB_AS_PRO_MAST_HIST", "PRO_ASS_HIST_ID" };

    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
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

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(Double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getAssActive() {
        return assActive;
    }

    public void setAssActive(String assActive) {
        this.assActive = assActive;
    }

    public Long getAssAutBy() {
        return assAutBy;
    }

    public void setAssAutBy(Long assAutBy) {
        this.assAutBy = assAutBy;
    }

    public String getAssAutStatus() {
        return assAutStatus;
    }

    public void setAssAutStatus(String assAutStatus) {
        this.assAutStatus = assAutStatus;
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

    public Long getAssCorrPincode() {
        return assCorrPincode;
    }

    public void setAssCorrPincode(Long assCorrPincode) {
        this.assCorrPincode = assCorrPincode;
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

    public Long getAssLpBillCycle() {
        return assLpBillCycle;
    }

    public void setAssLpBillCycle(Long assLpBillCycle) {
        this.assLpBillCycle = assLpBillCycle;
    }

    public Double getAssLpReceiptAmt() {
        return assLpReceiptAmt;
    }

    public void setAssLpReceiptAmt(Double assLpReceiptAmt) {
        this.assLpReceiptAmt = assLpReceiptAmt;
    }

    public Date getAssLpReceiptDate() {
        return assLpReceiptDate;
    }

    public void setAssLpReceiptDate(Date assLpReceiptDate) {
        this.assLpReceiptDate = assLpReceiptDate;
    }

    public String getAssLpReceiptNo() {
        return assLpReceiptNo;
    }

    public void setAssLpReceiptNo(String assLpReceiptNo) {
        this.assLpReceiptNo = assLpReceiptNo;
    }

    public Long getAssLpYear() {
        return assLpYear;
    }

    public void setAssLpYear(Long assLpYear) {
        this.assLpYear = assLpYear;
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

    public Long getAssPincode() {
        return assPincode;
    }

    public void setAssPincode(Long assPincode) {
        this.assPincode = assPincode;
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

    public Long getChildProp() {
        return childProp;
    }

    public void setChildProp(Long childProp) {
        this.childProp = childProp;
    }

    public String getIndfyReason() {
        return indfyReason;
    }

    public void setIndfyReason(String indfyReason) {
        this.indfyReason = indfyReason;
    }

    public String getParentProp() {
        return parentProp;
    }

    public void setParentProp(String parentProp) {
        this.parentProp = parentProp;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public String getTppApprovalNo() {
        return tppApprovalNo;
    }

    public void setTppApprovalNo(String tppApprovalNo) {
        this.tppApprovalNo = tppApprovalNo;
    }

    public String getTppKhataNo() {
        return tppKhataNo;
    }

    public void setTppKhataNo(String tppKhataNo) {
        this.tppKhataNo = tppKhataNo;
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

    public void setProAssHistId(long proAssHistId) {
        this.proAssHistId = proAssHistId;
    }

    public Long getProAssId() {
        return proAssId;
    }

    public void setProAssId(Long proAssId) {
        this.proAssId = proAssId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getAssAcqDate() {
        return assAcqDate;
    }

    public void setAssAcqDate(Date assAcqDate) {
        this.assAcqDate = assAcqDate;
    }

    public String getAssAddress() {
        return assAddress;
    }

    public void setAssAddress(String assAddress) {
        this.assAddress = assAddress;
    }

    public Date getAssAutDate() {
        return assAutDate;
    }

    public void setAssAutDate(Date assAutDate) {
        this.assAutDate = assAutDate;
    }

    public Long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(Long faYearId) {
        this.faYearId = faYearId;
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

    public String getBillPayment() {
        return billPayment;
    }

    public void setBillPayment(String billPayment) {
        this.billPayment = billPayment;
    }

    public Long getPropLvlRoadType() {
        return propLvlRoadType;
    }

    public void setPropLvlRoadType(Long propLvlRoadType) {
        this.propLvlRoadType = propLvlRoadType;
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

	public String getLogicalPropNo() {
		return logicalPropNo;
	}

	public void setLogicalPropNo(String logicalPropNo) {
		this.logicalPropNo = logicalPropNo;
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
    
	
	
}
