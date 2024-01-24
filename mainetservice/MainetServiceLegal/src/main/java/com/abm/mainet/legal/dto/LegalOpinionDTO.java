package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

public class LegalOpinionDTO implements Serializable {

    private static final long serialVersionUID = -1925630797942307777L;

    private Long loOpinionid;

    private Long loCpdopinionby;

    private Date loDate;

    private String loName;

    private String loOpinion;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long updatedBy;

    private Date updatedDate;

    private Long advId;

    private Long cseId;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getLoCpdopinionby() {
        return this.loCpdopinionby;
    }

    public void setLoCpdopinionby(Long loCpdopinionby) {
        this.loCpdopinionby = loCpdopinionby;
    }

    public Date getLoDate() {
        return this.loDate;
    }

    public void setLoDate(Date loDate) {
        this.loDate = loDate;
    }

    public String getLoName() {
        return this.loName;
    }

    public void setLoName(String loName) {
        this.loName = loName;
    }

    public String getLoOpinion() {
        return this.loOpinion;
    }

    public void setLoOpinion(String loOpinion) {
        this.loOpinion = loOpinion;
    }

    public Long getLoOpinionid() {
        return this.loOpinionid;
    }

    public void setLoOpinionid(Long loOpinionid) {
        this.loOpinionid = loOpinionid;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getAdvId() {
        return advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

}