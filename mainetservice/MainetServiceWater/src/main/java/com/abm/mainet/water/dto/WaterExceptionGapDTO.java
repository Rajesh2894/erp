package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

public class WaterExceptionGapDTO implements Serializable {

    private static final long serialVersionUID = -6341057068289079867L;

    private Long mgapExgid;

    private Long csIdn;

    private Long cpdMtrstatus;

    private Long cpdGap;

    private Date mgapFrom;

    private Date mgapTo;

    private String mgapBillGen;

    private Long bmIdno;

    private String mgapActive;

    private Long orgId;

    private Long createdBy;

    private Date lmodDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String mgapRemark;

    private String csName;

    private String ccnNumber;

    public Long getMgapExgid() {
        return mgapExgid;
    }

    public void setMgapExgid(final Long mgapExgid) {
        this.mgapExgid = mgapExgid;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Long getCpdMtrstatus() {
        return cpdMtrstatus;
    }

    public void setCpdMtrstatus(final Long cpdMtrstatus) {
        this.cpdMtrstatus = cpdMtrstatus;
    }

    public Long getCpdGap() {
        return cpdGap;
    }

    public void setCpdGap(final Long cpdGap) {
        this.cpdGap = cpdGap;
    }

    public Date getMgapFrom() {
        return mgapFrom;
    }

    public void setMgapFrom(final Date mgapFrom) {
        this.mgapFrom = mgapFrom;
    }

    public Date getMgapTo() {
        return mgapTo;
    }

    public void setMgapTo(final Date mgapTo) {
        this.mgapTo = mgapTo;
    }

    public String getMgapBillGen() {
        return mgapBillGen;
    }

    public void setMgapBillGen(final String mgapBillGen) {
        this.mgapBillGen = mgapBillGen;
    }

    public Long getBmIdno() {
        return bmIdno;
    }

    public void setBmIdno(final Long bmIdno) {
        this.bmIdno = bmIdno;
    }

    public String getMgapActive() {
        return mgapActive;
    }

    public void setMgapActive(final String mgapActive) {
        this.mgapActive = mgapActive;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
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

    public String getCsName() {
        return csName;
    }

    public void setCsName(final String csName) {
        this.csName = csName;
    }

    public String getCcnNumber() {
        return ccnNumber;
    }

    public void setCcnNumber(final String ccnNumber) {
        this.ccnNumber = ccnNumber;
    }

    public String getMgapRemark() {
        return mgapRemark;
    }

    public void setMgapRemark(final String mgapRemark) {
        this.mgapRemark = mgapRemark;
    }

}
