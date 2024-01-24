package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountLiabilityBookingDetBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    private Long lbLiabilityDetId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Long lbLiabilityId;

    private Long fieldId;

    private Long fundId;

    private Long functionId;

    private Long pacHeadId;

    private Long sacHeadId;

    private Long liabilityAmount;

    private Long faYearid;

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

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setLbLiabilityDetId(final Long lbLiabilityDetId) {
        this.lbLiabilityDetId = lbLiabilityDetId;
    }

    public Long getLbLiabilityDetId() {
        return lbLiabilityDetId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setLbLiabilityId(final Long lbLiabilityId) {
        this.lbLiabilityId = lbLiabilityId;
    }

    public Long getLbLiabilityId() {
        return lbLiabilityId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    public Long getPacHeadId() {
        return pacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setLiabilityAmount(final Long liabilityAmount) {
        this.liabilityAmount = liabilityAmount;
    }

    public Long getLiabilityAmount() {
        return liabilityAmount;
    }

    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    public Long getFaYearid() {
        return faYearid;
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

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(lbLiabilityDetId);
        sb.append("|");
        sb.append(lbLiabilityId);
        sb.append("|");
        sb.append(fieldId);
        sb.append("|");
        sb.append(fundId);
        sb.append("|");
        sb.append(functionId);
        sb.append("|");
        sb.append(pacHeadId);
        sb.append("|");
        sb.append(sacHeadId);
        sb.append("|");
        sb.append(liabilityAmount);
        sb.append("|");
        sb.append(faYearid);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(createdDate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);

        return sb.toString();
    }

}
