package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author prasad.kancharla
 * @since 05 feb 2018
 * @comment Table is used to store voucher Debit and Credit entry
 */
@Entity
@Table(name = "TB_AC_VOUCHER_DET_HIST")
public class AccountVoucherEntryDetailsHistEntity implements Serializable {

    private static final long serialVersionUID = -6044701315811655980L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VOUDET_HIST_ID", precision = 12, scale = 0, nullable = false)
    private long voudetHistId;

    @Column(name = "VOUDET_ID")
    private Long voudetId;

    /*
     * @ManyToOne(fetch = FetchType.LAZY)
     * @JoinColumn(name = "VOU_ID", nullable = true) private AccountVoucherEntryEntity master;
     */

    @Column(name = "VOU_ID")
    private Long master;

    @Column(name = "SAC_HEAD_ID", precision = 12, scale = 0, nullable = true)
    private Long sacHeadId;

    @Column(name = "VOUDET_AMT", precision = 15, scale = 2, nullable = true)
    private BigDecimal voudetAmt;

    @Column(name = "DRCR_CPD_ID", precision = 15, scale = 0, nullable = true)
    private Long drcrCpdId;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "PAC_HEAD_CODE")
    private String primaryHeadCode;

    @Column(name = "FI04_D1", nullable = true)
    private Date fi04D1;

    @Column(name = "SAC_HEAD_CODE")
    private String secondaryHeadCode;

    @Column(name = "BUDGETCODE_ID")
    private Long budgetCode;

    @Column(name = "FUNCTION_CODE")
    private String functionCode;

    @Column(name = "FUND_CODE")
    private String fundCode;

    @Column(name = "FIELD_CODE")
    private String fieldCode;

    @Column(name = "H_STATUS", length = 1)
    private Character hStatus;

    public String[] getPkValues() {
        return new String[] { "AC", "TB_AC_VOUCHER_DET_HIST", "VOUDET_HIST_ID" };
    }

    public AccountVoucherEntryDetailsHistEntity() {
        super();
    }

    public long getVoudetHistId() {
        return voudetHistId;
    }

    public void setVoudetHistId(long voudetHistId) {
        this.voudetHistId = voudetHistId;
    }

    public long getVoudetId() {
        return voudetId;
    }

    public void setVoudetId(long voudetId) {
        this.voudetId = voudetId;
    }

    public long getMaster() {
        return master;
    }

    public void setMaster(long master) {
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

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public String getSecondaryHeadCode() {
        return secondaryHeadCode;
    }

    public void setSecondaryHeadCode(String secondaryHeadCode) {
        this.secondaryHeadCode = secondaryHeadCode;
    }

    public Long getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(Long budgetCode) {
        this.budgetCode = budgetCode;
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

    public Character gethStatus() {
        return hStatus;
    }

    public void sethStatus(Character hStatus) {
        this.hStatus = hStatus;
    }

    @Override
    public String toString() {
        return "AccountVoucherEntryDetailsHistEntity [voudetHistId=" + voudetHistId + ", voudetId=" + voudetId
                + ", master=" + master + ", sacHeadId=" + sacHeadId + ", voudetAmt=" + voudetAmt + ", drcrCpdId="
                + drcrCpdId + ", orgId=" + orgId + ", createdBy=" + createdBy + ", lmodDate=" + lmodDate
                + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + ", primaryHeadCode=" + primaryHeadCode + ", fi04D1=" + fi04D1 + ", secondaryHeadCode="
                + secondaryHeadCode + ", budgetCode=" + budgetCode + ", functionCode=" + functionCode + ", fundCode="
                + fundCode + ", fieldCode=" + fieldCode + ", hStatus=" + hStatus + "]";
    }

}