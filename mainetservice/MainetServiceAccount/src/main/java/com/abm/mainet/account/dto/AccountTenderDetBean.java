package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountTenderDetBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    private Long trTenderidDet;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Long trTenderId;

    private Long fieldId;

    private Long fundId;

    private Long functionId;

    private Long pacHeadId;

    private Long sacHeadId;

    private BigDecimal budgetaryProv;

    private BigDecimal balanceProv;

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

    private Long fi04N1;

    private String fi04V1;

    private Date fi04D1;

    private String fi04Lo1;

    private String trTenderAmount;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setTrTenderidDet(final Long trTenderidDet) {
        this.trTenderidDet = trTenderidDet;
    }

    public Long getTrTenderidDet() {
        return trTenderidDet;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setTrTenderId(final Long trTenderId) {
        this.trTenderId = trTenderId;
    }

    public Long getTrTenderId() {
        return trTenderId;
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

    public void setBudgetaryProv(final BigDecimal budgetaryProv) {
        this.budgetaryProv = budgetaryProv;
    }

    public BigDecimal getBudgetaryProv() {
        return budgetaryProv;
    }

    public void setBalanceProv(final BigDecimal balanceProv) {
        this.balanceProv = balanceProv;
    }

    public BigDecimal getBalanceProv() {
        return balanceProv;
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

    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public String getTrTenderAmount() {
        return trTenderAmount;
    }

    public void setTrTenderAmount(final String trTenderAmount) {
        this.trTenderAmount = trTenderAmount;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(trTenderidDet);
        sb.append("|");
        sb.append(trTenderId);
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
        sb.append(budgetaryProv);
        sb.append("|");
        sb.append(balanceProv);
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
        sb.append("|");
        sb.append(fi04N1);
        sb.append("|");
        sb.append(fi04V1);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append(fi04Lo1);
        sb.append("|");
        sb.append(trTenderAmount);
        return sb.toString();
    }

}
