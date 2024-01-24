package com.abm.mainet.swm.dto;

import java.util.Date;

public class SwmErrorDetails {

    private Long errId;

    private String errLabel1;

    private String errLabel2;

    private String errLabel3;

    private String errData;

    private String errDescription;

    private String fileName;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String errFlag;

    public String getErrLabel1() {
        return errLabel1;
    }

    public void setErrLabel1(String errLabel1) {
        this.errLabel1 = errLabel1;
    }

    public String getErrLabel2() {
        return errLabel2;
    }

    public void setErrLabel2(String errLabel2) {
        this.errLabel2 = errLabel2;
    }

    public String getErrLabel3() {
        return errLabel3;
    }

    public void setErrLabel3(String errLabel3) {
        this.errLabel3 = errLabel3;
    }

    public String getErrData() {
        return errData;
    }

    public void setErrData(String errData) {
        this.errData = errData;
    }

    public String getErrDescription() {
        return errDescription;
    }

    public void setErrDescription(String errDescription) {
        this.errDescription = errDescription;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public Long getErrId() {
        return errId;
    }

    public void setErrId(Long errId) {
        this.errId = errId;
    }

    public String getErrFlag() {
        return errFlag;
    }

    public void setErrFlag(String errFlag) {
        this.errFlag = errFlag;
    }

}
