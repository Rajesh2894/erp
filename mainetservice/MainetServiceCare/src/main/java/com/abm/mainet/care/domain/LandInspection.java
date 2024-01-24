package com.abm.mainet.care.domain;

import java.io.Serializable;
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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_LND_INSPECT")
public class LandInspection implements Serializable {

    private static final long serialVersionUID = -7002334106935328266L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LN_INSP_ID")
    private Long lnInspId;

    @Column(name = "COMPLAINT_NO", length = 30, nullable = true)
    private String complaintNo;

    @Column(name = "COMPLAINT_DET", length = 200, nullable = true)
    private String complaintDet;

    @Column(name = "SURVEY_REPORT", length = 300, nullable = true)
    private String surveyReport;

    @Column(name = "LN_TYPE_SUR", length = 15, nullable = false)
    private Long lnTypeSurvey;// Type of Land for Surveys Prefix TOL

    @Column(name = "LATITUDE", nullable = true)
    private String latitude;

    @Column(name = "LONGITUDE", nullable = true)
    private String longitude;

    @Column(name = "PHOTO_CAPTION", nullable = true)
    private String photoCaption;

    @Column(name = "DISTT", nullable = false)
    private Long disttId; // prefix DIS

    @Column(name = "TEHSIL", nullable = false)
    private Long tehsilId; // prefix THL

    @Column(name = "VILLAGE", nullable = false)
    private Long villageId; // prefix VLG

    @Column(name = "MAHAL", nullable = false)
    private String mahal;// hard coded in JSP

    @Column(name = "LEKHPAL_AREA", nullable = false)
    private String lekhpalArea;

    @Column(name = "KHEVAT_NO", nullable = false)
    private String khevatNo;

    @Column(name = "ACCOUNT_NO", nullable = false)
    private String accountNo;

    @Column(name = "CATEGORY", nullable = true)
    private String category;

    @Column(name = "KHASRA_NO_ID", nullable = true)
    private Long khasraNoId; // prefix KSR

    @Column(name = "RAKBA", nullable = true)
    private String rakba;

    @Column(name = "AAKHYA", nullable = true)
    private String aakhya;

    @Column(name = "LN_LORD_NAME", length = 100, nullable = false)
    private String lnLordName;

    // Measurements 4 sides
    @Column(name = "N_MEASURE", precision = 12, scale = 2)
    private Float nMeasure;

    @Column(name = "S_MEASURE", precision = 12, scale = 2)
    private Float sMeasure;

    @Column(name = "E_MEASURE", precision = 12, scale = 2)
    private Float eMeasure;

    @Column(name = "W_MEASURE", precision = 12, scale = 2)
    private Float wMeasure;

    @Column(name = "LAND_TYPE", nullable = false)
    private Long landTypeId; // prefix TOL

    @Column(name = "SUB_LN_TYPE", nullable = false)
    private String subLdType;// hard code in JSP

    @Column(name = "PROP_TYPE", nullable = false)
    private String propertyType;// hard code in JSP

    @Column(name = "SUB_PROP_TYPE", nullable = false)
    private String subPropType;// hard code in JSP

    @Column(name = "PERMANENT_MARK", length = 100, nullable = false)
    private String permanentMark;

    @Column(name = "HADBANDI", nullable = true)
    private String hadbandi;// YES or NO

    @Column(name = "MEASURED", nullable = false)
    private String measured;// YES or NO

    @Column(name = "GRD_CONDI", nullable = true)
    private String groundCondition;// hard code in JSP

    // If the land is less then who owns it START
    @Column(name = "NAME", length = 100, nullable = true)
    private String name;

    @Column(name = "KHASRA_NO", length = 50, nullable = true)
    private String khasraNo;

    @Column(name = "AREA_OF_LN", precision = 12, scale = 2, nullable = true)
    private Float areaOfLand;

    @Column(name = "AREA_MEASUREMENT", nullable = true)
    private String areaMeasurement;// hard code in JSP

    // If the land is less then who owns it END

    // HADBANDI Details (TB_EST_DEMARCAT_DET)
    @OneToMany(mappedBy = "landInspection", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DemarcationDetails> demarcations = new ArrayList<DemarcationDetails>();

    // ENCROACHMENT Details (TB_EST_ENCROACH_DET)
    @OneToMany(mappedBy = "landInspection", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<EncroachmentDetails> encroachments = new ArrayList<EncroachmentDetails>();

    // CASE PENDANCY Details (TB_EST_LNCASE_DET)
    @OneToMany(mappedBy = "landInspection", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<LandCaseDetails> landCases = new ArrayList<LandCaseDetails>();

    @Column(name = "CONVICTION", length = 500, nullable = true)
    private String conviction;// Conviction/Comment

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getLnInspId() {
        return lnInspId;
    }

    public void setLnInspId(Long lnInspId) {
        this.lnInspId = lnInspId;
    }

    public String getComplaintNo() {
        return complaintNo;
    }

    public void setComplaintNo(String complaintNo) {
        this.complaintNo = complaintNo;
    }

    public String getComplaintDet() {
        return complaintDet;
    }

    public void setComplaintDet(String complaintDet) {
        this.complaintDet = complaintDet;
    }

    public String getSurveyReport() {
        return surveyReport;
    }

    public void setSurveyReport(String surveyReport) {
        this.surveyReport = surveyReport;
    }

    public Long getLnTypeSurvey() {
        return lnTypeSurvey;
    }

    public void setLnTypeSurvey(Long lnTypeSurvey) {
        this.lnTypeSurvey = lnTypeSurvey;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhotoCaption() {
        return photoCaption;
    }

    public void setPhotoCaption(String photoCaption) {
        this.photoCaption = photoCaption;
    }

    public Long getDisttId() {
        return disttId;
    }

    public void setDisttId(Long disttId) {
        this.disttId = disttId;
    }

    public Long getTehsilId() {
        return tehsilId;
    }

    public void setTehsilId(Long tehsilId) {
        this.tehsilId = tehsilId;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public String getMahal() {
        return mahal;
    }

    public void setMahal(String mahal) {
        this.mahal = mahal;
    }

    public String getLekhpalArea() {
        return lekhpalArea;
    }

    public void setLekhpalArea(String lekhpalArea) {
        this.lekhpalArea = lekhpalArea;
    }

    public String getKhevatNo() {
        return khevatNo;
    }

    public void setKhevatNo(String khevatNo) {
        this.khevatNo = khevatNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getKhasraNoId() {
        return khasraNoId;
    }

    public void setKhasraNoId(Long khasraNoId) {
        this.khasraNoId = khasraNoId;
    }

    public String getRakba() {
        return rakba;
    }

    public void setRakba(String rakba) {
        this.rakba = rakba;
    }

    public String getAakhya() {
        return aakhya;
    }

    public void setAakhya(String aakhya) {
        this.aakhya = aakhya;
    }

    public String getLnLordName() {
        return lnLordName;
    }

    public void setLnLordName(String lnLordName) {
        this.lnLordName = lnLordName;
    }

    public Float getnMeasure() {
        return nMeasure;
    }

    public void setnMeasure(Float nMeasure) {
        this.nMeasure = nMeasure;
    }

    public Float getsMeasure() {
        return sMeasure;
    }

    public void setsMeasure(Float sMeasure) {
        this.sMeasure = sMeasure;
    }

    public Float geteMeasure() {
        return eMeasure;
    }

    public void seteMeasure(Float eMeasure) {
        this.eMeasure = eMeasure;
    }

    public Float getwMeasure() {
        return wMeasure;
    }

    public void setwMeasure(Float wMeasure) {
        this.wMeasure = wMeasure;
    }

    public void setAreaOfLand(Float areaOfLand) {
        this.areaOfLand = areaOfLand;
    }

    public Float getAreaOfLand() {
        return areaOfLand;
    }

    public Long getLandTypeId() {
        return landTypeId;
    }

    public void setLandTypeId(Long landTypeId) {
        this.landTypeId = landTypeId;
    }

    public String getSubLdType() {
        return subLdType;
    }

    public void setSubLdType(String subLdType) {
        this.subLdType = subLdType;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getSubPropType() {
        return subPropType;
    }

    public void setSubPropType(String subPropType) {
        this.subPropType = subPropType;
    }

    public String getPermanentMark() {
        return permanentMark;
    }

    public void setPermanentMark(String permanentMark) {
        this.permanentMark = permanentMark;
    }

    public String getHadbandi() {
        return hadbandi;
    }

    public void setHadbandi(String hadbandi) {
        this.hadbandi = hadbandi;
    }

    public String getMeasured() {
        return measured;
    }

    public void setMeasured(String measured) {
        this.measured = measured;
    }

    public String getGroundCondition() {
        return groundCondition;
    }

    public void setGroundCondition(String groundCondition) {
        this.groundCondition = groundCondition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaMeasurement() {
        return areaMeasurement;
    }

    public void setAreaMeasurement(String areaMeasurement) {
        this.areaMeasurement = areaMeasurement;
    }

    public String getKhasraNo() {
        return khasraNo;
    }

    public void setKhasraNo(String khasraNo) {
        this.khasraNo = khasraNo;
    }

    public List<DemarcationDetails> getDemarcations() {
        return demarcations;
    }

    public void setDemarcations(List<DemarcationDetails> demarcations) {
        this.demarcations = demarcations;
    }

    public List<EncroachmentDetails> getEncroachments() {
        return encroachments;
    }

    public void setEncroachments(List<EncroachmentDetails> encroachments) {
        this.encroachments = encroachments;
    }

    public List<LandCaseDetails> getLandCases() {
        return landCases;
    }

    public void setLandCases(List<LandCaseDetails> landCases) {
        this.landCases = landCases;
    }

    public String getConviction() {
        return conviction;
    }

    public void setConviction(String conviction) {
        this.conviction = conviction;
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

    @Override
    public String toString() {
        return "LandInspection [lnInspId=" + lnInspId + ", complaintNo=" + complaintNo + ", complaintDet=" + complaintDet
                + ", surveyReport=" + surveyReport + ", lnTypeSurvey=" + lnTypeSurvey + ", latitude=" + latitude + ", Longitude="
                + longitude + ", photoCaption=" + photoCaption + ", disttId=" + disttId + ", tehsilId=" + tehsilId
                + ", villageId=" + villageId + ", mahal=" + mahal + ", lekhpalArea=" + lekhpalArea + ", khevatNo=" + khevatNo
                + ", accountNo=" + accountNo + ", category=" + category + ", khasraNoId=" + khasraNoId + ", rakba=" + rakba
                + ", aakhya=" + aakhya + ", lnLordName=" + lnLordName + ", nMeasure=" + nMeasure + ", sMeasure=" + sMeasure
                + ", eMeasure=" + eMeasure + ", wMeasure=" + wMeasure + ", landTypeId=" + landTypeId + ", subLdType=" + subLdType
                + ", propertyType=" + propertyType + ", subPropType=" + subPropType + ", permanentMark=" + permanentMark
                + ", hadbandi=" + hadbandi + ", measured=" + measured + ", groundCondition=" + groundCondition + ", name=" + name
                + ", khasraNo=" + khasraNo + ", areaOfLand=" + areaOfLand + ", areaMeasurement=" + areaMeasurement
                + ", demarcations=" + demarcations + ", encroachments=" + encroachments + ", landCases=" + landCases
                + ", conviction=" + conviction + ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate=" + createdDate
                + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + "]";
    }

    public static String[] getPkValues() {
        return new String[] { "COM", "TB_LND_INSPECT", "LN_INSP_ID" };
    }
}
