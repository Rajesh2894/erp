package com.abm.mainet.account.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class VoucherReversePostDetailDTO {

    private long voudetId;

    private Long sacHeadId;

    private BigDecimal voudetAmt;

    private Long drcrCpdId;

    private Long orgId;

    private Long createdBy;

    private Date lmodDate;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private String primaryHeadCode;

    // private Date fi04D1;

    private String secondaryHeadCode;

    private Long budgetCode;

    private String functionCode;

    private String fundCode;

    private String fieldCode;

    private VoucherReversePostDTO master;

    public long getVoudetId() {
        return voudetId;
    }

    public void setVoudetId(long voudetId) {
        this.voudetId = voudetId;
    }

    public VoucherReversePostDTO getMaster() {
        return master;
    }

    public void setMaster(VoucherReversePostDTO master) {
        this.master = master;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public BigDecimal getVoudetAmt() {
        return voudetAmt;
    }

    public void setVoudetAmt(BigDecimal voudetAmt) {
        this.voudetAmt = voudetAmt;
    }

    public Long getDrcrCpdId() {
        return drcrCpdId;
    }

    public void setDrcrCpdId(Long drcrCpdId) {
        this.drcrCpdId = drcrCpdId;
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

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(Date lmodDate) {
        this.lmodDate = lmodDate;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getPrimaryHeadCode() {
        return primaryHeadCode;
    }

    public void setPrimaryHeadCode(String primaryHeadCode) {
        this.primaryHeadCode = primaryHeadCode;
    }

    public String getSecondaryHeadCode() {
        return secondaryHeadCode;
    }

    public void setSecondaryHeadCode(String secondaryHeadCode) {
        this.secondaryHeadCode = secondaryHeadCode;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public Long getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(Long budgetCode) {
        this.budgetCode = budgetCode;
    }

    @Override
    public String toString() {
        return "VoucherReversePostDetailDTO [voudetId=" + voudetId + ", master=" + master + ", sacHeadId=" + sacHeadId
                + ", voudetAmt=" + voudetAmt + ", drcrCpdId=" + drcrCpdId + ", orgId=" + orgId + ", createdBy=" + createdBy
                + ", lmodDate=" + lmodDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + ", primaryHeadCode=" + primaryHeadCode + ", secondaryHeadCode="
                + secondaryHeadCode + ", budgetCode=" + budgetCode + ", functionCode=" + functionCode
                + ", fundCode=" + fundCode + ", fieldCode=" + fieldCode + "]";
    }

}
