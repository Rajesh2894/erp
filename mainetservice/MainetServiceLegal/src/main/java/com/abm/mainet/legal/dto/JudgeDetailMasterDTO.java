package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

public class JudgeDetailMasterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Date fromPeriod;
    private Date toPeriod;
    private String judgeStatus;
    private Long orgId;
    private Long createdBy;
    private Date createDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Long crtId;
    private Long crtType;
    private String crtName;
    private String crtAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFromPeriod() {
        return fromPeriod;
    }

    public void setFromPeriod(Date fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public Date getToPeriod() {
        return toPeriod;
    }

    public void setToPeriod(Date toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(String judgeStatus) {
        this.judgeStatus = judgeStatus;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public Long getCrtId() {
        return crtId;
    }

    public void setCrtId(Long crtId) {
        this.crtId = crtId;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getCrtType() {
        return crtType;
    }

    public void setCrtType(Long crtType) {
        this.crtType = crtType;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public String getCrtAddress() {
        return crtAddress;
    }

    public void setCrtAddress(String crtAddress) {
        this.crtAddress = crtAddress;
    }

}
