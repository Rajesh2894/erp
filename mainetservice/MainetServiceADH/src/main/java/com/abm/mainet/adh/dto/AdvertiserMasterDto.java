package com.abm.mainet.adh.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.RequestDTO;

/**
 * @author cherupelli.srikanth
 * @since 02 august 2019
 */
public class AdvertiserMasterDto extends RequestDTO implements Serializable {

    private static final long serialVersionUID = 5844892755832603709L;

    private Long agencyId;

    private String agencyLicNo;

    private String agencyName;

    private Long agencyCategory;

    private String agencyOwner;

    private String agencyEmail;

    private String panNumber;

    private Long uidNo;

    private String gstNo;

    private String agencyContactNo;

    private String agencyAdd;

    private Date agencyLicIssueDate;

    private Date agencyLicFromDate;

    private String agencyLicenseFromDate;

    private Date agencyLicToDate;

    private String agencyLicenseToDate;

    private String agencyRegisDate;

    private String agencyRemark;

    private String agencyOldLicNo;

    private Long apmApplicationId;

    private String agencyStatus;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private Long langId;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMacUpd;

    private Date cancellationDate;

    private String cancellationReason;

    private String regDate;

    private String validUpto;

    private String applicantMobileNo;
    private Long trdFtype;

    private List<AdvertiserMasterDto> advertiserMasterDtoList;

    /**
     * This field is used to differentiate application is through Master form(M) or Agency Registration Service(S)
     */
    private String applicationTypeFlag;

    private String agencyCategoryDesc;

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyLicNo() {
        return agencyLicNo;
    }

    public void setAgencyLicNo(String agencyLicNo) {
        this.agencyLicNo = agencyLicNo;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Long getAgencyCategory() {
        return agencyCategory;
    }

    public void setAgencyCategory(Long agencyCategory) {
        this.agencyCategory = agencyCategory;
    }

    public String getAgencyOwner() {
        return agencyOwner;
    }

    public void setAgencyOwner(String agencyOwner) {
        this.agencyOwner = agencyOwner;
    }

    public String getAgencyEmail() {
        return agencyEmail;
    }

    public void setAgencyEmail(String agencyEmail) {
        this.agencyEmail = agencyEmail;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public Long getUidNo() {
        return uidNo;
    }

    public void setUidNo(Long uidNo) {
        this.uidNo = uidNo;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getAgencyContactNo() {
        return agencyContactNo;
    }

    public void setAgencyContactNo(String agencyContactNo) {
        this.agencyContactNo = agencyContactNo;
    }

    public String getAgencyAdd() {
        return agencyAdd;
    }

    public void setAgencyAdd(String agencyAdd) {
        this.agencyAdd = agencyAdd;
    }

    public Date getAgencyLicIssueDate() {
        return agencyLicIssueDate;
    }

    public void setAgencyLicIssueDate(Date agencyLicIssueDate) {
        this.agencyLicIssueDate = agencyLicIssueDate;
    }

    public Date getAgencyLicFromDate() {
        return agencyLicFromDate;
    }

    public void setAgencyLicFromDate(Date agencyLicFromDate) {
        this.agencyLicFromDate = agencyLicFromDate;
    }

    public String getAgencyLicenseFromDate() {
        return agencyLicenseFromDate;
    }

    public void setAgencyLicenseFromDate(String agencyLicenseFromDate) {
        this.agencyLicenseFromDate = agencyLicenseFromDate;
    }

    public Date getAgencyLicToDate() {
        return agencyLicToDate;
    }

    public void setAgencyLicToDate(Date agencyLicToDate) {
        this.agencyLicToDate = agencyLicToDate;
    }

    public String getAgencyLicenseToDate() {
        return agencyLicenseToDate;
    }

    public void setAgencyLicenseToDate(String agencyLicenseToDate) {
        this.agencyLicenseToDate = agencyLicenseToDate;
    }

    public String getAgencyRegisDate() {
        return agencyRegisDate;
    }

    public void setAgencyRegisDate(String agencyRegisDate) {
        this.agencyRegisDate = agencyRegisDate;
    }

    public String getAgencyRemark() {
        return agencyRemark;
    }

    public void setAgencyRemark(String agencyRemark) {
        this.agencyRemark = agencyRemark;
    }

    public String getAgencyOldLicNo() {
        return agencyOldLicNo;
    }

    public void setAgencyOldLicNo(String agencyOldLicNo) {
        this.agencyOldLicNo = agencyOldLicNo;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getAgencyStatus() {
        return agencyStatus;
    }

    public void setAgencyStatus(String agencyStatus) {
        this.agencyStatus = agencyStatus;
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

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getApplicationTypeFlag() {
        return applicationTypeFlag;
    }

    public void setApplicationTypeFlag(String applicationTypeFlag) {
        this.applicationTypeFlag = applicationTypeFlag;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public List<AdvertiserMasterDto> getAdvertiserMasterDtoList() {
        return advertiserMasterDtoList;
    }

    public void setAdvertiserMasterDtoList(List<AdvertiserMasterDto> advertiserMasterDtoList) {
        this.advertiserMasterDtoList = advertiserMasterDtoList;

    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(String validUpto) {
        this.validUpto = validUpto;
    }

    public String getApplicantMobileNo() {
        return applicantMobileNo;
    }

    public void setApplicantMobileNo(String applicantMobileNo) {
        this.applicantMobileNo = applicantMobileNo;
    }

    public Long getTrdFtype() {
        return trdFtype;
    }

    public void setTrdFtype(Long trdFtype) {
        this.trdFtype = trdFtype;
    }

    public String getAgencyCategoryDesc() {
        return agencyCategoryDesc;
    }

    public void setAgencyCategoryDesc(String agencyCategoryDesc) {
        this.agencyCategoryDesc = agencyCategoryDesc;
    }

    @Override
    public String toString() {
        return "AdvertiserMasterDto [agencyId=" + agencyId + ", agencyLicNo=" + agencyLicNo + ", agencyName=" + agencyName
                + ", agencyCategory=" + agencyCategory + ", agencyOwner=" + agencyOwner + ", agencyEmail=" + agencyEmail
                + ", panNumber=" + panNumber + ", uidNo=" + uidNo + ", gstNo=" + gstNo + ", agencyContactNo=" + agencyContactNo
                + ", agencyAdd=" + agencyAdd + ", agencyLicIssueDate=" + agencyLicIssueDate + ", agencyLicFromDate="
                + agencyLicFromDate + ", agencyLicenseFromDate=" + agencyLicenseFromDate + ", agencyLicToDate=" + agencyLicToDate
                + ", agencyLicenseToDate=" + agencyLicenseToDate + ", agencyRegisDate=" + agencyRegisDate + ", agencyRemark="
                + agencyRemark + ", agencyOldLicNo=" + agencyOldLicNo + ", apmApplicationId=" + apmApplicationId
                + ", agencyStatus=" + agencyStatus + ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate="
                + createdDate + ", lgIpMac=" + lgIpMac + ", langId=" + langId + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", lgIpMacUpd=" + lgIpMacUpd + ", cancellationDate=" + cancellationDate + ", cancellationReason="
                + cancellationReason + ", regDate=" + regDate + ", validUpto=" + validUpto + ", applicantMobileNo="
                + applicantMobileNo + ", trdFtype=" + trdFtype + ", advertiserMasterDtoList=" + advertiserMasterDtoList
                + ", applicationTypeFlag=" + applicationTypeFlag + ", agencyCategoryDesc=" + agencyCategoryDesc + "]";
    }

}
