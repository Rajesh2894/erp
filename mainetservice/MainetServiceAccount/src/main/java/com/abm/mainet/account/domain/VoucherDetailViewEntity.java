package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * View created for various Account report purpose
 * @author Vivek.Kumar
 * @since 16-Aug-2017
 */
@Entity
@Table(name = "VW_VOUCHER_DETAIL")
public class VoucherDetailViewEntity implements Serializable {

    private static final long serialVersionUID = 3355904412775614033L;

    @Column(name = "VOU_ID", precision = 12, scale = 0, nullable = false)
    private long vouId;

    @Id
    @Column(name = "VOUDET_ID", precision = 12, scale = 0, nullable = false)
    private long vouDetId;

    @Column(name = "ORGID", length = 12, nullable = true)
    private Long orgId;

    @Temporal(TemporalType.DATE)
    @Column(name = "VOU_DATE", nullable = true)
    private Date vouDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "VOU_POSTING_DATE", nullable = true)
    private Date voPostingDate;

    @Column(name = "VOU_NO", nullable = true)
    private String vouNo;

    @Column(name = "PAYER_PAYEE", nullable = true)
    private String payerPayee;

    @Column(name = "Particulars", nullable = true)
    private String particulars;

    @Column(name = "VOU_TYPE_CPD_ID", precision = 12, scale = 0, nullable = true)
    private Long vouTypeCpdId;

    @Column(name = "VOU_SUBTYPE_CPD_ID", precision = 12, scale = 0, nullable = true)
    private Long vouSubtypeCpdId;

    @Column(name = "AC_HEAD_CODE", nullable = true)
    private String acHeadCode;

    @Column(name = "SAC_HEAD_ID", nullable = true)
    private Long sacHeadId;

    @Column(name = "Voucher_Amount", nullable = true)
    private Double voucherAmount;

    @Column(name = "VAMT_DR", nullable = true)
    private BigDecimal drAmount;

    @Column(name = "VAMT_CR", nullable = true)
    private BigDecimal crAmount;

    @Column(name = "DRCR", nullable = true)
    private String drCr;

    @Column(name = "REFERENCE_NO", nullable = true)
    private String referenceNo;
    
    @Column(name = "FIELD_ID", nullable = true)
    private Long fieldId;

    public long getVouId() {
        return vouId;
    }

    public void setVouId(final long vouId) {
        this.vouId = vouId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Date getVouDate() {
        return vouDate;
    }

    public void setVouDate(final Date vouDate) {
        this.vouDate = vouDate;
    }

    public Date getVoPostingDate() {
        return voPostingDate;
    }

    public void setVoPostingDate(final Date voPostingDate) {
        this.voPostingDate = voPostingDate;
    }

    public String getVouNo() {
        return vouNo;
    }

    public void setVouNo(final String vouNo) {
        this.vouNo = vouNo;
    }

    public String getPayerPayee() {
        return payerPayee;
    }

    public void setPayerPayee(final String payerPayee) {
        this.payerPayee = payerPayee;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(final String particulars) {
        this.particulars = particulars;
    }

    public Long getVouTypeCpdId() {
        return vouTypeCpdId;
    }

    public void setVouTypeCpdId(final Long vouTypeCpdId) {
        this.vouTypeCpdId = vouTypeCpdId;
    }

    public Long getVouSubtypeCpdId() {
        return vouSubtypeCpdId;
    }

    public void setVouSubtypeCpdId(final Long vouSubtypeCpdId) {
        this.vouSubtypeCpdId = vouSubtypeCpdId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Double getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(final Double voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public BigDecimal getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(BigDecimal drAmount) {
        this.drAmount = drAmount;
    }

    public BigDecimal getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(BigDecimal crAmount) {
        this.crAmount = crAmount;
    }

    public String getDrCr() {
        return drCr;
    }

    public void setDrCr(final String drCr) {
        this.drCr = drCr;
    }

    public long getVouDetId() {
        return vouDetId;
    }

    public void setVouDetId(final long vouDetId) {
        this.vouDetId = vouDetId;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("VoucherDetailViewEntity [vouId=");
        builder.append(vouId);
        builder.append(", vouDetId=");
        builder.append(vouDetId);
        builder.append(", orgId=");
        builder.append(orgId);
        builder.append(", vouDate=");
        builder.append(vouDate);
        builder.append(", voPostingDate=");
        builder.append(voPostingDate);
        builder.append(", vouNo=");
        builder.append(vouNo);
        builder.append(", payerPayee=");
        builder.append(payerPayee);
        builder.append(", particulars=");
        builder.append(particulars);
        builder.append(", vouTypeCpdId=");
        builder.append(vouTypeCpdId);
        builder.append(", vouSubtypeCpdId=");
        builder.append(vouSubtypeCpdId);
        builder.append(", sacHeadCode=");
        builder.append(acHeadCode);
        builder.append(", acHeadId=");
        builder.append(sacHeadId);
        builder.append(", voucherAmount=");
        builder.append(voucherAmount);
        builder.append(", drAmount=");
        builder.append(drAmount);
        builder.append(", crAmount=");
        builder.append(crAmount);
        builder.append(", drCr=");
        builder.append(drCr);
        builder.append("]");
        return builder.toString();
    }

    public String getAcHeadCode() {
        return acHeadCode;
    }

    public void setAcHeadCode(String acHeadCode) {
        this.acHeadCode = acHeadCode;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

}
