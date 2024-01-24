package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBillEntryDeductionDetHistDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long hBdhId;
    private Long id;
    private Long deductionRate;
    private BigDecimal deductionAmount;
    private Long orgid;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddress;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddressUpdated;
    private Long bchId;
    private String fi04V1;
    private Date fi04D1;
    private BigDecimal deductionBalAmt;
    private Character hStatus;
    private Long sacHeadId;

    public Long gethBdhId() {
        return hBdhId;
    }

    public void sethBdhId(Long hBdhId) {
        this.hBdhId = hBdhId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeductionRate() {
        return deductionRate;
    }

    public void setDeductionRate(Long deductionRate) {
        this.deductionRate = deductionRate;
    }

    public BigDecimal getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(BigDecimal deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public String getLgIpMacAddress() {
        return lgIpMacAddress;
    }

    public void setLgIpMacAddress(String lgIpMacAddress) {
        this.lgIpMacAddress = lgIpMacAddress;
    }

    public String getLgIpMacAddressUpdated() {
        return lgIpMacAddressUpdated;
    }

    public void setLgIpMacAddressUpdated(String lgIpMacAddressUpdated) {
        this.lgIpMacAddressUpdated = lgIpMacAddressUpdated;
    }

    public Long getBchId() {
        return bchId;
    }

    public void setBchId(Long bchId) {
        this.bchId = bchId;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V1(String fi04v1) {
        fi04V1 = fi04v1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public BigDecimal getDeductionBalAmt() {
        return deductionBalAmt;
    }

    public void setDeductionBalAmt(BigDecimal deductionBalAmt) {
        this.deductionBalAmt = deductionBalAmt;
    }

    public Character gethStatus() {
        return hStatus;
    }

    public void sethStatus(Character hStatus) {
        this.hStatus = hStatus;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    @Override
    public String toString() {
        return "AccountBillEntryDeductionDetHistDto [hBdhId=" + hBdhId + ", id=" + id + ", deductionRate="
                + deductionRate + ", deductionAmount=" + deductionAmount + ", orgid=" + orgid + ", createdBy="
                + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", lgIpMacAddress=" + lgIpMacAddress + ", lgIpMacAddressUpdated="
                + lgIpMacAddressUpdated + ", bchId=" + bchId + ", fi04V1=" + fi04V1 + ", fi04D1=" + fi04D1
                + ", deductionBalAmt=" + deductionBalAmt + ", hStatus=" + hStatus + ", sacHeadId=" + sacHeadId + "]";
    }
}
