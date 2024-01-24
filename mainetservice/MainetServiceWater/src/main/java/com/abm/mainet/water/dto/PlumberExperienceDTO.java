package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

public class PlumberExperienceDTO implements Serializable {

    private static final long serialVersionUID = 4702936708139892748L;

    private long plumExpId;

    private Long plumId;

    private String plumCompanyName;

    private String plumCompanyAddress;

    private Long plumExpMonth;

    private Long plumExpYear;

    private Long orgId;

    private Long userId;

    private int langId;

    private Date lmodDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String expFromDate;

    private String expToDate;

    private Double experience;

    private Long firmType;

    private Double totalExprience;

    public long getPlumExpId() {
        return plumExpId;
    }

    public void setPlumExpId(final long plumExpId) {
        this.plumExpId = plumExpId;
    }

    public Long getPlumId() {
        return plumId;
    }

    public void setPlumId(final Long plumId) {
        this.plumId = plumId;
    }

    public String getPlumCompanyName() {
        return plumCompanyName;
    }

    public void setPlumCompanyName(final String plumCompanyName) {
        this.plumCompanyName = plumCompanyName;
    }

    public String getPlumCompanyAddress() {
        return plumCompanyAddress;
    }

    public void setPlumCompanyAddress(final String plumCompanyAddress) {
        this.plumCompanyAddress = plumCompanyAddress;
    }

    public Long getPlumExpMonth() {
        return plumExpMonth;
    }

    public void setPlumExpMonth(final Long plumExpMonth) {
        this.plumExpMonth = plumExpMonth;
    }

    public Long getPlumExpYear() {
        return plumExpYear;
    }

    public void setPlumExpYear(final Long plumExpYear) {
        this.plumExpYear = plumExpYear;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
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

    public String getExpFromDate() {
        return expFromDate;
    }

    public void setExpFromDate(final String expFromDate) {
        this.expFromDate = expFromDate;
    }

    public String getExpToDate() {
        return expToDate;
    }

    public void setExpToDate(final String expToDate) {
        this.expToDate = expToDate;
    }

    public Double getExperience() {
        return experience;
    }

    public void setExperience(final Double experience) {
        this.experience = experience;
    }

    public Long getFirmType() {
        return firmType;
    }

    public void setFirmType(final Long firmType) {
        this.firmType = firmType;
    }

    public Double getTotalExprience() {
        return totalExprience;
    }

    public void setTotalExprience(final Double totalExprience) {
        this.totalExprience = totalExprience;
    }

}