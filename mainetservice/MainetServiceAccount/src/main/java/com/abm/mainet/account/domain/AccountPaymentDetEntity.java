package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_PAYMENT_DET")
public class AccountPaymentDetEntity implements Serializable {

    private static final long serialVersionUID = 4071703294723862640L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PAYMENTDET_ID", precision = 12, scale = 0, nullable = false)
    private long paymentDetId;

    @Column(name = "BCH_ID", precision = 12, scale = 0, nullable = true)
    private Long bchIdExpenditure;

    @Column(name = "BDH_ID", precision = 12, scale = 0, nullable = true)
    private Long bdhIdDeduction;

    @Column(name = "BUDGETCODE_ID", precision = 12, scale = 0, nullable = true)
    private Long budgetCodeId;

    @Column(name = "PAYMENT_AMT", precision = 15, scale = 2, nullable = true)
    private BigDecimal paymentAmt;

    @Column(name = "PAYMENT_DEDUCTION_AMT", precision = 15, scale = 2, nullable = true)
    private BigDecimal paymentDeductionAmt;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private Long langId;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "BM_ID", precision = 15, scale = 0, nullable = true)
    private Long billId;

    @Column(name = "FI04_V2", length = 100, nullable = true)
    private String fi04V2;

    @Column(name = "FI04_D1", nullable = true)
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1, nullable = true)
    private String fi04Lo1;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_ID", referencedColumnName = "PAYMENT_ID")
    private AccountPaymentMasterEntity paymentMasterId;

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_PAYMENT_DET", "PAYMENTDET_ID" };
    }

    public long getPaymentDetId() {
        return paymentDetId;
    }

    public void setPaymentDetId(final long paymentDetId) {
        this.paymentDetId = paymentDetId;
    }

    public Long getBchIdExpenditure() {
        return bchIdExpenditure;
    }

    public void setBchIdExpenditure(final Long bchIdExpenditure) {
        this.bchIdExpenditure = bchIdExpenditure;
    }

    public Long getBdhIdDeduction() {
        return bdhIdDeduction;
    }

    public void setBdhIdDeduction(final Long bdhIdDeduction) {
        this.bdhIdDeduction = bdhIdDeduction;
    }

    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public BigDecimal getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(BigDecimal paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public BigDecimal getPaymentDeductionAmt() {
        return paymentDeductionAmt;
    }

    public void setPaymentDeductionAmt(BigDecimal paymentDeductionAmt) {
        this.paymentDeductionAmt = paymentDeductionAmt;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(final Long billId) {
        this.billId = billId;
    }

    public String getFi04V2() {
        return fi04V2;
    }

    public void setFi04V2(final String fi04v2) {
        fi04V2 = fi04v2;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public AccountPaymentMasterEntity getPaymentMasterId() {
        return paymentMasterId;
    }

    public void setPaymentMasterId(final AccountPaymentMasterEntity paymentMasterId) {
        this.paymentMasterId = paymentMasterId;
    }

    @Override
    public String toString() {
        return "AccountPaymentDetEntity [paymentDetId=" + paymentDetId + ", bchIdExpenditure=" + bchIdExpenditure
                + ", bdhIdDeduction=" + bdhIdDeduction + ", budgetCodeId=" + budgetCodeId + ", paymentAmt=" + paymentAmt
                + ", paymentDeductionAmt=" + paymentDeductionAmt + ", orgId=" + orgId + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
                + ", langId=" + langId + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", billId=" + billId
                + ", fi04V2=" + fi04V2 + ", fi04D1=" + fi04D1 + ", fi04Lo1=" + fi04Lo1 + ", paymentMasterId="
                + paymentMasterId + "]";
    }

}