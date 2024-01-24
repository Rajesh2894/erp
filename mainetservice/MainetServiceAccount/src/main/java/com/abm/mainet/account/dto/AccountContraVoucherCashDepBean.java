package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** @author tejas.kotekar */
public class AccountContraVoucherCashDepBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long cashdepDetId;

    private Long tbAcContradet;

    private Long tbComparamDet;

    private Long denomination;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private Long langId;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private String denomDesc;

    /** @return the cashdepDetId */
    public Long getCashdepDetId() {
        return cashdepDetId;
    }

    /**
     * @param cashdepDetId the cashdepDetId to set
     */
    public void setCashdepDetId(final Long cashdepDetId) {
        this.cashdepDetId = cashdepDetId;
    }

    /** @return the tbAcContradet */
    public Long getTbAcContradet() {
        return tbAcContradet;
    }

    /**
     * @param tbAcContradet the tbAcContradet to set
     */
    public void setTbAcContradet(final Long tbAcContradet) {
        this.tbAcContradet = tbAcContradet;
    }

    /** @return the tbComparamDet */
    public Long getTbComparamDet() {
        return tbComparamDet;
    }

    /**
     * @param tbComparamDet the tbComparamDet to set
     */
    public void setTbComparamDet(final Long tbComparamDet) {
        this.tbComparamDet = tbComparamDet;
    }

    /** @return the denomination */
    public Long getDenomination() {
        return denomination;
    }

    /**
     * @param denomination the denomination to set
     */
    public void setDenomination(final Long denomination) {
        this.denomination = denomination;
    }

    /** @return the orgid */
    public Long getOrgid() {
        return orgid;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    /** @return the createdBy */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /** @return the createdDate */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /** @return the langId */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    /** @return the updatedBy */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /** @return the updatedDate */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /** @return the lgIpMac */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /** @return the lgIpMacUpd */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /** @return the denomDesc */
    public String getDenomDesc() {
        return denomDesc;
    }

    /**
     * @param denomDesc the denomDesc to set
     */
    public void setDenomDesc(final String denomDesc) {
        this.denomDesc = denomDesc;
    }

}
