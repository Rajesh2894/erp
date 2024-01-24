package com.abm.mainet.care.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CareApplicantDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String applicantTitle;
    private String applicantFirstName;
    private String applicantMiddleName;
    private String applicantLastName;
    private String gender;
    private String mobileNo;
    private String emailId;
    private String flatBuildingNo;
    private String buildingName;
    private String roadName;
    private String blockName;
    private String areaName;
    private String villageTownSub;
    private String pinCode;
    private String aadharNo;
    private String panNumber;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getApplicantTitle() {
        return applicantTitle;
    }

    public void setApplicantTitle(final String applicantTitle) {
        this.applicantTitle = applicantTitle;
    }

    public String getApplicantFirstName() {
        return applicantFirstName;
    }

    public void setApplicantFirstName(final String applicantFirstName) {
        this.applicantFirstName = applicantFirstName;
    }

    public String getApplicantMiddleName() {
        return applicantMiddleName;
    }

    public void setApplicantMiddleName(final String applicantMiddleName) {
        this.applicantMiddleName = applicantMiddleName;
    }

    public String getApplicantLastName() {
        return applicantLastName;
    }

    public void setApplicantLastName(final String applicantLastName) {
        this.applicantLastName = applicantLastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getFlatBuildingNo() {
        return flatBuildingNo;
    }

    public void setFlatBuildingNo(final String flatBuildingNo) {
        this.flatBuildingNo = flatBuildingNo;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(final String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(final String roadName) {
        this.roadName = roadName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(final String blockName) {
        this.blockName = blockName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(final String areaName) {
        this.areaName = areaName;
    }

    public String getVillageTownSub() {
        return villageTownSub;
    }

    public void setVillageTownSub(final String villageTownSub) {
        this.villageTownSub = villageTownSub;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(final String pinCode) {
        this.pinCode = pinCode;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(final String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(final String panNumber) {
        this.panNumber = panNumber;
    }

}
