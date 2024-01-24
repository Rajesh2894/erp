package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

public class JudgementMasterDto implements Serializable {

    private static final long serialVersionUID = 4190927896337613452L;

    private Long judId;

    private Long cseId;

    private Long crtId;

    private String cseDeptName;

    private String cseCourtDesc;

    private String cseDateDesc;

    private Date cseDate;

    private Date judDate;

    private String judSummaryDetail;

    private String cseBenchName;

    private String judgementStatus;

    private Long orgId;

    private Long createdBy;

    private Long updatedBy;

    private Date createdDate;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    public Long getJudId() {
        return judId;
    }

    public void setJudId(Long judId) {
        this.judId = judId;
    }

    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public Long getCrtId() {
        return crtId;
    }

    public void setCrtId(Long crtId) {
        this.crtId = crtId;
    }

    public Date getCseDate() {
        return cseDate;
    }

    public void setCseDate(Date cseDate) {
        this.cseDate = cseDate;
    }

    public Date getJudDate() {
        return judDate;
    }

    public void setJudDate(Date judDate) {
        this.judDate = judDate;
    }

    public String getJudSummaryDetail() {
        return judSummaryDetail;
    }

    public void setJudSummaryDetail(String judSummaryDetail) {
        this.judSummaryDetail = judSummaryDetail;
    }

    public String getJudgementStatus() {
        return judgementStatus;
    }

    public void setJudgementStatus(String judgementStatus) {
        this.judgementStatus = judgementStatus;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getCseDeptName() {
        return cseDeptName;
    }

    public void setCseDeptName(String cseDeptName) {
        this.cseDeptName = cseDeptName;
    }

    public String getCseCourtDesc() {
        return cseCourtDesc;
    }

    public void setCseCourtDesc(String cseCourtDesc) {
        this.cseCourtDesc = cseCourtDesc;
    }

    public String getCseBenchName() {
        return cseBenchName;
    }

    public void setCseBenchName(String cseBenchName) {
        this.cseBenchName = cseBenchName;
    }

    public String getCseDateDesc() {
        return cseDateDesc;
    }

    public void setCseDateDesc(String cseDateDesc) {
        this.cseDateDesc = cseDateDesc;
    }

}
