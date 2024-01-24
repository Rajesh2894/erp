package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author deepika.pimpale
 * @since 09 Sep 2016
 * @comment Table is used to store voucher Debit and Credit entry
 */
@Entity
@Table(name = "TB_AC_VOUCHER_DET")
public class AccountVoucherEntryDetailsEntity implements Serializable {

    private static final long serialVersionUID = -6044701315811655980L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VOUDET_ID", precision = 12, scale = 0, nullable = false)
    private long voudetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOU_ID", referencedColumnName = "VOU_ID", nullable = true)
    private AccountVoucherEntryEntity master;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID", nullable = true)
    private AccountBudgetCodeEntity budgetCode;

    @Column(name = "FUNCTION_CODE")
    private String functionCode;

    @Column(name = "FUND_CODE")
    private String fundCode;

    @Column(name = "FIELD_CODE")
    private String fieldCode;

    public String[] getPkValues() {
        return new String[] { "AC", "TB_AC_VOUCHER_DET", "VOUDET_ID" };
    }

    /**
     * @return the voudetId
     */
    public long getVoudetId() {
        return voudetId;
    }

    /**
     * @param voudetId the voudetId to set
     */
    public void setVoudetId(final long voudetId) {
        this.voudetId = voudetId;
    }

    /**
     * @return the voudetAmt
     */
    public BigDecimal getVoudetAmt() {
        return voudetAmt;
    }

    /**
     * @param voudetAmt the voudetAmt to set
     */
    public void setVoudetAmt(final BigDecimal voudetAmt) {
        this.voudetAmt = voudetAmt;
    }

    /**
     * @return the drcrCpdId
     */
    public Long getDrcrCpdId() {
        return drcrCpdId;
    }

    /**
     * @param drcrCpdId the drcrCpdId to set
     */
    public void setDrcrCpdId(final Long drcrCpdId) {
        this.drcrCpdId = drcrCpdId;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the fi04D1
     */
    public Date getFi04D1() {
        return fi04D1;
    }

    /**
     * @param fi04d1 the fi04D1 to set
     */
    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    /**
     * @return the master
     */
    public AccountVoucherEntryEntity getMaster() {
        return master;
    }

    /**
     * @param master the master to set
     */
    public void setMaster(final AccountVoucherEntryEntity master) {
        this.master = master;
    }

    /**
     * @return the budgetCode
     */
    public AccountBudgetCodeEntity getBudgetCode() {
        return budgetCode;
    }

    /**
     * @param budgetCode the budgetCode to set
     */
    public void setBudgetCode(final AccountBudgetCodeEntity budgetCode) {
        this.budgetCode = budgetCode;
    }

    public String getPrimaryHeadCode() {
        return primaryHeadCode;
    }

    public void setPrimaryHeadCode(final String primaryHeadCode) {
        this.primaryHeadCode = primaryHeadCode;
    }

    public String getSecondaryHeadCode() {
        return secondaryHeadCode;
    }

    public void setSecondaryHeadCode(final String secondaryHeadCode) {
        this.secondaryHeadCode = secondaryHeadCode;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(final String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(final String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(final String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

}