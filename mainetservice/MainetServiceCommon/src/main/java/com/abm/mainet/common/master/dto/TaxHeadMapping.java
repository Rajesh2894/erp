package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Lalit.Prusti
 * @since 20 Aug 2016
 * @comment Table is used to store mapping between tax
 */
public class TaxHeadMapping implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6973547338137048593L;

    private long taxheadId;

    private Long taxId;

    private Long fundId;

    private Long functionId;

    private Long pacHeadId;

    private Long sacHeadId;

    private Long pacHeadIdLib;

    private Long sacHeadIdLib;

    private Long orgid;

    private Long userId;

    private Long langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    private String lgIpMac;

    @JsonIgnore
    private String lgIpMacUpd;

    public long getTaxheadId() {
        return taxheadId;
    }

    public void setTaxheadId(final long taxheadId) {
        this.taxheadId = taxheadId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    public Long getPacHeadId() {
        return pacHeadId;
    }

    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getPacHeadIdLib() {
        return pacHeadIdLib;
    }

    public void setPacHeadIdLib(final Long pacHeadIdLib) {
        this.pacHeadIdLib = pacHeadIdLib;
    }

    public Long getSacHeadIdLib() {
        return sacHeadIdLib;
    }

    public void setSacHeadIdLib(final Long sacHeadIdLib) {
        this.sacHeadIdLib = sacHeadIdLib;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

}