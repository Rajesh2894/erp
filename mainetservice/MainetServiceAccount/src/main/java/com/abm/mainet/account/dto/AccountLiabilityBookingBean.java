package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountLiabilityBookingBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    private Long lbLiabilityId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Long lbLiabilityNo;

    private String lbEntryDate;

    private Long trTenderId;

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

    private String trEntryDate;

    private String trTenderNo;

    private String trType;

    private Long trEmdAmt;

    private String dpDept;

    private String vmVendorCode;

    private String vendorName;

    private String specialconditions;

    private Long trTenderAmount;

    private List<AccountLiabilityBookingDetBean> detList = new ArrayList<>();

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setLbLiabilityId(final Long lbLiabilityId) {
        this.lbLiabilityId = lbLiabilityId;
    }

    public Long getLbLiabilityId() {
        return lbLiabilityId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setLbLiabilityNo(final Long lbLiabilityNo) {
        this.lbLiabilityNo = lbLiabilityNo;
    }

    public Long getLbLiabilityNo() {
        return lbLiabilityNo;
    }

    public void setLbEntryDate(final String lbEntryDate) {
        this.lbEntryDate = lbEntryDate;
    }

    public String getLbEntryDate() {
        return lbEntryDate;
    }

    public void setTrTenderId(final Long trTenderId) {
        this.trTenderId = trTenderId;
    }

    public Long getTrTenderId() {
        return trTenderId;
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

    /**
     * @return the trEntryDate
     */
    public String getTrEntryDate() {
        return trEntryDate;
    }

    /**
     * @param trEntryDate the trEntryDate to set
     */
    public void setTrEntryDate(final String trEntryDate) {
        this.trEntryDate = trEntryDate;
    }

    /**
     * @return the trTenderNo
     */
    public String getTrTenderNo() {
        return trTenderNo;
    }

    /**
     * @param trTenderNo the trTenderNo to set
     */
    public void setTrTenderNo(final String trTenderNo) {
        this.trTenderNo = trTenderNo;
    }

    /**
     * @return the trType
     */
    public String getTrType() {
        return trType;
    }

    /**
     * @param trType the trType to set
     */
    public void setTrType(final String trType) {
        this.trType = trType;
    }

    /**
     * @return the trEmdAmt
     */
    public Long getTrEmdAmt() {
        return trEmdAmt;
    }

    /**
     * @param trEmdAmt the trEmdAmt to set
     */
    public void setTrEmdAmt(final Long trEmdAmt) {
        this.trEmdAmt = trEmdAmt;
    }

    /**
     * @return the dpDept
     */
    public String getDpDept() {
        return dpDept;
    }

    /**
     * @param dpDept the dpDept to set
     */
    public void setDpDept(final String dpDept) {
        this.dpDept = dpDept;
    }

    /**
     * @return the vmVendorCode
     */
    public String getVmVendorCode() {
        return vmVendorCode;
    }

    /**
     * @param vmVendorCode the vmVendorCode to set
     */
    public void setVmVendorCode(final String vmVendorCode) {
        this.vmVendorCode = vmVendorCode;
    }

    /**
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * @param vendorName the vendorName to set
     */
    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * @return the specialconditions
     */
    public String getSpecialconditions() {
        return specialconditions;
    }

    /**
     * @param specialconditions the specialconditions to set
     */
    public void setSpecialconditions(final String specialconditions) {
        this.specialconditions = specialconditions;
    }

    /**
     * @return the trTenderAmount
     */
    public Long getTrTenderAmount() {
        return trTenderAmount;
    }

    /**
     * @param trTenderAmount the trTenderAmount to set
     */
    public void setTrTenderAmount(final Long trTenderAmount) {
        this.trTenderAmount = trTenderAmount;
    }

    /**
     * @return the detList
     */
    public List<AccountLiabilityBookingDetBean> getDetList() {
        return detList;
    }

    /**
     * @param detList the detList to set
     */
    public void setDetList(final List<AccountLiabilityBookingDetBean> detList) {
        this.detList = detList;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountLiabilityBookingBean [lbLiabilityId=" + lbLiabilityId + ", lbLiabilityNo=" + lbLiabilityNo
                + ", lbEntryDate=" + lbEntryDate + ", trTenderId=" + trTenderId + ", orgid=" + orgid + ", createdBy="
                + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", langId=" + langId + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
                + ", trEntryDate=" + trEntryDate + ", trTenderNo=" + trTenderNo + ", trType=" + trType + ", trEmdAmt="
                + trEmdAmt + ", dpDept=" + dpDept + ", vmVendorCode=" + vmVendorCode + ", vendorName=" + vendorName
                + ", specialconditions=" + specialconditions + ", trTenderAmount=" + trTenderAmount + ", detList="
                + detList + "]";
    }

}
