package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ritesh.patil
 *
 */
public class EstateMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long esId;
    private String code;
    private Long orgId;
    private String nameEng;
    private String nameReg;
    private Long locId;
    private String address;
    private String assetNo;
    private Character category;
    private Integer type1;
    private Integer type2;
    private String regNo;
    private Date regDate;
    private Date constDate;
    private Date compDate;
    private Integer floors;
    private Integer basements;
    private Long createdBy;
    private Long updatedBy;
    private String imagesPath;
    private String docsPath;
    private String hiddeValue;
    private Date updatedDate;
    private Date createdDate;
    private Character isActive;
    private String lgIpMac;
    private String lgIpMacUp;
    private Long natureOfLand;
    private String surveyNo;
    private String latitude;
    private String longitude;
    private String east;
    private String west;
    private String south;
    private String north;

    private int langId;
    private Long purpose;// EPR
    private Long acqType;// AQM
    private Long holdingType;// EHT
    private String rnlGstNo;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getEsId() {
        return esId;
    }

    public void setEsId(final Long esId) {
        this.esId = esId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(final String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameReg() {
        return nameReg;
    }

    public void setNameReg(final String nameReg) {
        this.nameReg = nameReg;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(final Long locId) {
        this.locId = locId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(final String assetNo) {
        this.assetNo = assetNo;
    }

    public Character getCategory() {
        return category;
    }

    public void setCategory(final Character category) {
        this.category = category;
    }

    public Integer getType1() {
        return type1;
    }

    public void setType1(final Integer type1) {
        this.type1 = type1;
    }

    public Integer getType2() {
        return type2;
    }

    public void setType2(final Integer type2) {
        this.type2 = type2;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(final String regNo) {
        this.regNo = regNo;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(final Date regDate) {
        this.regDate = regDate;
    }

    public Date getConstDate() {
        return constDate;
    }

    public void setConstDate(final Date constDate) {
        this.constDate = constDate;
    }

    public Date getCompDate() {
        return compDate;
    }

    public void setCompDate(final Date compDate) {
        this.compDate = compDate;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(final Integer floors) {
        this.floors = floors;
    }

    public Integer getBasements() {
        return basements;
    }

    public void setBasements(final Integer basements) {
        this.basements = basements;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(final String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getDocsPath() {
        return docsPath;
    }

    public void setDocsPath(final String docsPath) {
        this.docsPath = docsPath;
    }

    public String getHiddeValue() {
        return hiddeValue;
    }

    public void setHiddeValue(final String hiddeValue) {
        this.hiddeValue = hiddeValue;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(final Character isActive) {
        this.isActive = isActive;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public Long getNatureOfLand() {
        return natureOfLand;
    }

    public void setNatureOfLand(final Long natureOfLand) {
        this.natureOfLand = natureOfLand;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(final String surveyNo) {
        this.surveyNo = surveyNo;
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

    public Long getPurpose() {
        return purpose;
    }

    public void setPurpose(Long purpose) {
        this.purpose = purpose;
    }

    public Long getAcqType() {
        return acqType;
    }

    public void setAcqType(Long acqType) {
        this.acqType = acqType;
    }

    public Long getHoldingType() {
        return holdingType;
    }

    public void setHoldingType(Long holdingType) {
        this.holdingType = holdingType;
    }

	public String getRnlGstNo() {
		return rnlGstNo;
	}

	public void setRnlGstNo(String rnlGstNo) {
		this.rnlGstNo = rnlGstNo;
	}
    
    
}
