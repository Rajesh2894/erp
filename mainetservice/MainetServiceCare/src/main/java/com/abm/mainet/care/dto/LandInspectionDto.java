package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LandInspectionDto implements Serializable {

    private static final long serialVersionUID = 7691043912510668695L;

    private Long lnInspId;

    private String complaintNo;

    private String complaintDet;

    private String surveyReport;

    private Long lnTypeSurvey;// Type of Land for Surveys Prefix TOL

    private String latitude;

    private String longitude;

    private String photoCaption;

    private Long disttId; // prefix DIS

    private Long tehsilId; // prefix THL

    private Long villageId; // prefix VLG

    private String mahal;// hard coded in JSP

    private String lekhpalArea;

    private String khevatNo;

    private String accountNo;

    private String category;

    private Long khasraNoId; // prefix KSR

    private String rakba;

    private String aakhya;

    private String lnLordName;

    // Measurements 4 sides

    private Float nMeasure;

    private Float sMeasure;

    private Float eMeasure;

    private Float wMeasure;

    private Long landTypeId; // prefix TOL

    private String subLdType;// hard code in JSP

    private String propertyType;// hard code in JSP

    private String subPropType;// hard code in JSP

    private String permanentMark;// hard code in JSP

    private String hadbandi;// YES or NO

    private String measured;// YES or NO

    private String groundCondition;// hard code in JSP

    // If the land is less then who owns it START

    private String name;

    private String khasraNo;

    private Float areaOfLand;

    private String areaMeasurement;// hard code in JSP

    // If the land is less then who owns it END

    // HADBANDI Details (TB_EST_DEMARCAT_DET)

    // private Boolean demarcationSelect;

    private List<DemarcationDetailsDto> demarcationsDtos = new ArrayList<DemarcationDetailsDto>();

    // ENCROACHMENT Details (TB_EST_ENCROACH_DET)

    private String encrSingleSelect;

    private String encrMultipleSelect;

    private List<EncroachmentDetailsDto> encroachmentsDtos = new ArrayList<EncroachmentDetailsDto>();

    private List<EncroachmentDetailsDto> multiEncroachmentsDtos = new ArrayList<EncroachmentDetailsDto>();

    // CASE PENDANCY Details (TB_EST_LNCASE_DET)

    private String casePendancySelect;

    private List<LandCaseDetailsDto> landCasesDtos = new ArrayList<LandCaseDetailsDto>();

    private String conviction;// Conviction/Comment

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

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

    public Float getAreaOfLand() {
        return areaOfLand;
    }

    public void setAreaOfLand(Float areaOfLand) {
        this.areaOfLand = areaOfLand;
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

    public String getKhasraNo() {
        return khasraNo;
    }

    public void setKhasraNo(String khasraNo) {
        this.khasraNo = khasraNo;
    }

    public String getAreaMeasurement() {
        return areaMeasurement;
    }

    public void setAreaMeasurement(String areaMeasurement) {
        this.areaMeasurement = areaMeasurement;
    }

    public List<DemarcationDetailsDto> getDemarcationsDtos() {
        return demarcationsDtos;
    }

    public void setDemarcationsDtos(List<DemarcationDetailsDto> demarcationsDtos) {
        this.demarcationsDtos = demarcationsDtos;
    }

    public String getEncrSingleSelect() {
        return encrSingleSelect;
    }

    public void setEncrSingleSelect(String encrSingleSelect) {
        this.encrSingleSelect = encrSingleSelect;
    }

    public String getEncrMultipleSelect() {
        return encrMultipleSelect;
    }

    public void setEncrMultipleSelect(String encrMultipleSelect) {
        this.encrMultipleSelect = encrMultipleSelect;
    }

    public List<EncroachmentDetailsDto> getEncroachmentsDtos() {
        return encroachmentsDtos;
    }

    public void setEncroachmentsDtos(List<EncroachmentDetailsDto> encroachmentsDtos) {
        this.encroachmentsDtos = encroachmentsDtos;
    }

    public List<EncroachmentDetailsDto> getMultiEncroachmentsDtos() {
        return multiEncroachmentsDtos;
    }

    public void setMultiEncroachmentsDtos(List<EncroachmentDetailsDto> multiEncroachmentsDtos) {
        this.multiEncroachmentsDtos = multiEncroachmentsDtos;
    }

    public String getCasePendancySelect() {
        return casePendancySelect;
    }

    public void setCasePendancySelect(String casePendancySelect) {
        this.casePendancySelect = casePendancySelect;
    }

    public List<LandCaseDetailsDto> getLandCasesDtos() {
        return landCasesDtos;
    }

    public void setLandCasesDtos(List<LandCaseDetailsDto> landCasesDtos) {
        this.landCasesDtos = landCasesDtos;
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

}
