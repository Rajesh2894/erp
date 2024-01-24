package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class LandCaseDetailsDto implements Serializable {

    private static final long serialVersionUID = 6485950869915729645L;

    private Long caseId;

    @JsonIgnore
    private LandInspectionDto landInspectionDto;

    private String crtName;

    private String crtAdd;

    private Date crtDate;

    private String litigant;

    private String respondent;

    private String advocateName;

    private String contactNo;//

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public LandInspectionDto getLandInspectionDto() {
        return landInspectionDto;
    }

    public void setLandInspectionDto(LandInspectionDto landInspectionDto) {
        this.landInspectionDto = landInspectionDto;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public String getCrtAdd() {
        return crtAdd;
    }

    public void setCrtAdd(String crtAdd) {
        this.crtAdd = crtAdd;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public String getLitigant() {
        return litigant;
    }

    public void setLitigant(String litigant) {
        this.litigant = litigant;
    }

    public String getRespondent() {
        return respondent;
    }

    public void setRespondent(String respondent) {
        this.respondent = respondent;
    }

    public String getAdvocateName() {
        return advocateName;
    }

    public void setAdvocateName(String advocateName) {
        this.advocateName = advocateName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
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
        return "LandCaseDetailsDto [caseId=" + caseId + ", landInspectionDto=" + landInspectionDto + ", crtName=" + crtName
                + ", crtAdd=" + crtAdd + ", crtDate=" + crtDate + ", litigant=" + litigant + ", respondent=" + respondent
                + ", advocateName=" + advocateName + ", contactNo=" + contactNo + ", orgId=" + orgId + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac="
                + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
    }

}
