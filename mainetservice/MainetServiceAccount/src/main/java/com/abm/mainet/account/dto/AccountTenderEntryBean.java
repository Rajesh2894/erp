package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountTenderEntryBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    private Long trTenderId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    private String trEntryDate;

    private String trTenderNo;

    private String trTenderDate;

    private Long trTypeCpdId;

    private String trNameofwork;

    private String trEmdAmt;

    private Long dpDeptid;

    private Long vmVendorid;

    private String specialconditions;

    private String trTenderAmount;

    private String trProposalNo;

    private String trProposalDate;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private Long langId;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private Long authorisedBy;

    private String authRemark;

    private Date authDate;

    private String authStatus;

    private String hasError;

    private Long vouId;

    private String statusCodeValue;

    private List<AccountTenderDetBean> tenderDetList = new ArrayList<>();

    private String prExpBudgetCode;

    private String appNo;

    private String orgShortName;

    private String actualTaskId;

    private String workFlowIdentityFlag;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setTrTenderId(final Long trTenderId) {
        this.trTenderId = trTenderId;
    }

    public Long getTrTenderId() {
        return trTenderId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setTrEntryDate(final String trEntryDate) {
        this.trEntryDate = trEntryDate;
    }

    public String getTrEntryDate() {
        return trEntryDate;
    }

    public void setTrTenderNo(final String trTenderNo) {
        this.trTenderNo = trTenderNo;
    }

    public String getTrTenderNo() {
        return trTenderNo;
    }

    public void setTrTenderDate(final String trTenderDate) {
        this.trTenderDate = trTenderDate;
    }

    public String getTrTenderDate() {
        return trTenderDate;
    }

    public void setTrTypeCpdId(final Long trTypeCpdId) {
        this.trTypeCpdId = trTypeCpdId;
    }

    public Long getTrTypeCpdId() {
        return trTypeCpdId;
    }

    public void setTrNameofwork(final String trNameofwork) {
        this.trNameofwork = trNameofwork;
    }

    public String getTrNameofwork() {
        return trNameofwork;
    }

    public void setTrEmdAmt(final String trEmdAmt) {
        this.trEmdAmt = trEmdAmt;
    }

    public String getTrEmdAmt() {
        return trEmdAmt;
    }

    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setVmVendorid(final Long vmVendorid) {
        this.vmVendorid = vmVendorid;
    }

    public Long getVmVendorid() {
        return vmVendorid;
    }

    public void setSpecialconditions(final String specialconditions) {
        this.specialconditions = specialconditions;
    }

    public String getSpecialconditions() {
        return specialconditions;
    }

    public void setTrTenderAmount(final String trTenderAmount) {
        this.trTenderAmount = trTenderAmount;
    }

    public String getTrTenderAmount() {
        return trTenderAmount;
    }

    public void setTrProposalNo(final String trProposalNo) {
        this.trProposalNo = trProposalNo;
    }

    public String getTrProposalNo() {
        return trProposalNo;
    }

    public void setTrProposalDate(final String trProposalDate) {
        this.trProposalDate = trProposalDate;
    }

    public String getTrProposalDate() {
        return trProposalDate;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public Long getAuthorisedBy() {
        return authorisedBy;
    }

    public void setAuthorisedBy(final Long authorisedBy) {
        this.authorisedBy = authorisedBy;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(final String authRemark) {
        this.authRemark = authRemark;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(final Date authDate) {
        this.authDate = authDate;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(final String authStatus) {
        this.authStatus = authStatus;
    }

    public Long getVouId() {
        return vouId;
    }

    public void setVouId(final Long vouId) {
        this.vouId = vouId;
    }

    /**
     * @return the tenderDetList
     */
    public List<AccountTenderDetBean> getTenderDetList() {
        return tenderDetList;
    }

    /**
     * @param tenderDetList the tenderDetList to set
     */
    public void setTenderDetList(final List<AccountTenderDetBean> tenderDetList) {
        this.tenderDetList = tenderDetList;
    }

    /**
     * @return the hasError
     */
    public String getHasError() {
        return hasError;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public String getPrExpBudgetCode() {
        return prExpBudgetCode;
    }

    public void setPrExpBudgetCode(final String prExpBudgetCode) {
        this.prExpBudgetCode = prExpBudgetCode;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    public String getStatusCodeValue() {
        return statusCodeValue;
    }

    public void setStatusCodeValue(String statusCodeValue) {
        this.statusCodeValue = statusCodeValue;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getActualTaskId() {
        return actualTaskId;
    }

    public void setActualTaskId(String actualTaskId) {
        this.actualTaskId = actualTaskId;
    }

    public String getOrgShortName() {
        return orgShortName;
    }

    public void setOrgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }

    public String getWorkFlowIdentityFlag() {
        return workFlowIdentityFlag;
    }

    public void setWorkFlowIdentityFlag(String workFlowIdentityFlag) {
        this.workFlowIdentityFlag = workFlowIdentityFlag;
    }

    @Override
    public String toString() {
        return "AccountTenderEntryBean [trTenderId=" + trTenderId + ", trEntryDate=" + trEntryDate + ", trTenderNo="
                + trTenderNo + ", trTenderDate=" + trTenderDate + ", trTypeCpdId=" + trTypeCpdId + ", trNameofwork="
                + trNameofwork + ", trEmdAmt=" + trEmdAmt + ", dpDeptid=" + dpDeptid + ", vmVendorid=" + vmVendorid
                + ", specialconditions=" + specialconditions + ", trTenderAmount=" + trTenderAmount + ", trProposalNo="
                + trProposalNo + ", trProposalDate=" + trProposalDate + ", orgid=" + orgid + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
                + ", langId=" + langId + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", authorisedBy="
                + authorisedBy + ", authRemark=" + authRemark + ", authDate=" + authDate + ", authStatus=" + authStatus
                + ", hasError=" + hasError + ", vouId=" + vouId + ", statusCodeValue=" + statusCodeValue
                + ", tenderDetList=" + tenderDetList + ", prExpBudgetCode=" + prExpBudgetCode + ", appNo=" + appNo
                + ", orgShortName=" + orgShortName + ", actualTaskId=" + actualTaskId + ", workFlowIdentityFlag="
                + workFlowIdentityFlag + "]";
    }

}
