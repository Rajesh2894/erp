package com.abm.mainet.mrm.dto;

import java.io.Serializable;
import java.util.Date;

public class MarriageRequest implements Serializable {

    private static final long serialVersionUID = 7750447623720964225L;

    private Long orgId;
    private Long serviceId;
    private Long deptId;
    private String smShortCode;

    // get record
    Long marId;
    Long applicationId;

    // search criteria
    Date marriageDate;
    Date appDate;
    String status;
    String serialNo;
    Long husbandId;
    Long wifeId;
    String hitFrom;
    Boolean skdclENVPresent = false;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getSmShortCode() {
        return smShortCode;
    }

    public Long getMarId() {
        return marId;
    }

    public void setMarId(Long marId) {
        this.marId = marId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public void setSmShortCode(String smShortCode) {
        this.smShortCode = smShortCode;
    }

    public Date getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(Date marriageDate) {
        this.marriageDate = marriageDate;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Long getHusbandId() {
        return husbandId;
    }

    public void setHusbandId(Long husbandId) {
        this.husbandId = husbandId;
    }

    public Long getWifeId() {
        return wifeId;
    }

    public void setWifeId(Long wifeId) {
        this.wifeId = wifeId;
    }

    public String getHitFrom() {
        return hitFrom;
    }

    public void setHitFrom(String hitFrom) {
        this.hitFrom = hitFrom;
    }

    public Boolean getSkdclENVPresent() {
        return skdclENVPresent;
    }

    public void setSkdclENVPresent(Boolean skdclENVPresent) {
        this.skdclENVPresent = skdclENVPresent;
    }

    @Override
    public String toString() {
        return "MarriageRequest [orgId=" + orgId + ", serviceId=" + serviceId + ", deptId=" + deptId + ", smShortCode="
                + smShortCode + ", marId=" + marId + ", applicationId=" + applicationId + ", marriageDate=" + marriageDate
                + ", appDate=" + appDate + ", status=" + status + ", serialNo=" + serialNo + ", husbandId=" + husbandId
                + ", wifeId=" + wifeId + ", hitFrom=" + hitFrom + ", skdclENVPresent=" + skdclENVPresent + "]";
    }

}
