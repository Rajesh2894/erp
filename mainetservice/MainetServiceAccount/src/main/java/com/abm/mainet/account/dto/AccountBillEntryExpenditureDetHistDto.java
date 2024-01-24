package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBillEntryExpenditureDetHistDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long hBchId;
    private Long id;
    private BigDecimal billChargesAmount;
    private BigDecimal disallowedAmount;
    private String disallowedRemark;
    private BigDecimal actualAmount;
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
    private Long sacHeadId;
    private String fi04V1;
    private Date fi04D1;
    private String fi04Lo1;
    private Character hStatus;

    public Long gethBchId() {
        return hBchId;
    }

    public void sethBchId(Long hBchId) {
        this.hBchId = hBchId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBillChargesAmount() {
        return billChargesAmount;
    }

    public void setBillChargesAmount(BigDecimal billChargesAmount) {
        this.billChargesAmount = billChargesAmount;
    }

    public BigDecimal getDisallowedAmount() {
        return disallowedAmount;
    }

    public void setDisallowedAmount(BigDecimal disallowedAmount) {
        this.disallowedAmount = disallowedAmount;
    }

    public String getDisallowedRemark() {
        return disallowedRemark;
    }

    public void setDisallowedRemark(String disallowedRemark) {
        this.disallowedRemark = disallowedRemark;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
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

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
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

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public Character gethStatus() {
        return hStatus;
    }

    public void sethStatus(Character hStatus) {
        this.hStatus = hStatus;
    }

    @Override
    public String toString() {
        return "AccountBillEntryExpenditureDetHistDto [hBchId=" + hBchId + ", id=" + id + ", billChargesAmount="
                + billChargesAmount + ", disallowedAmount=" + disallowedAmount + ", disallowedRemark="
                + disallowedRemark + ", actualAmount=" + actualAmount + ", orgid=" + orgid + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
                + ", lgIpMacAddress=" + lgIpMacAddress + ", lgIpMacAddressUpdated=" + lgIpMacAddressUpdated
                + ", sacHeadId=" + sacHeadId + ", fi04V1=" + fi04V1 + ", fi04D1=" + fi04D1 + ", fi04Lo1=" + fi04Lo1
                + ", hStatus=" + hStatus + "]";
    }

}
