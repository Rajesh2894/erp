package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author prasad.kancharla
 * @since 05 feb 2018
 * @comment This table used for DEAS Voucher master entry.
 */
@Entity
@Table(name = "TB_AC_VOUCHER_HIST")
public class AccountVoucherEntryHistEntity implements Serializable {

    private static final long serialVersionUID = -8747813326195893892L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VOU_HIST_ID", precision = 12, scale = 0, nullable = false)
    private long vouHistId;

    @Column(name = "VOU_ID", nullable = false)
    private long vouId;

    @Column(name = "VOU_NO", length = 12, nullable = true)
    private String vouNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "VOU_DATE", nullable = true)
    private Date vouDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "VOU_POSTING_DATE", nullable = true)
    private Date vouPostingDate;

    @Column(name = "VOU_TYPE_CPD_ID", precision = 12, scale = 0, nullable = true)
    private Long vouTypeCpdId;

    @Column(name = "VOU_SUBTYPE_CPD_ID", precision = 12, scale = 0, nullable = true)
    private Long vouSubtypeCpdId;

    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = true)
    private Long dpDeptid;

    @Column(name = "VOU_REFERENCE_NO", length = 20, nullable = true)
    private String vouReferenceNo;

    @Column(name = "VOU_REFERENCE_NO_DATE", nullable = true)
    private Date vouReferenceNoDate;

    @Column(name = "NARRATION", length = 1000, nullable = true)
    private String narration;

    @Column(name = "PAYER_PAYEE", length = 1000, nullable = true)
    private String payerPayee;

    @Column(name = "FIELD_ID", precision = 12, scale = 0, nullable = true)
    private Long fieldId;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long org;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = true)
    private Long updatedby;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "AUTHO_ID", precision = 7, scale = 0, updatable = true, nullable = true)
    private Long authoId;

    @Column(name = "AUTHO_DATE", nullable = true)
    private Date authoDate;

    @Column(name = "AUTHO_FLG", length = 1, nullable = true)
    private String authoFlg;

    @Column(name = "ENTRY_TYPE", precision = 2, scale = 0, nullable = false)
    private Long entryType;

    @Column(name = "FI04_N1", precision = 15, scale = 0, nullable = true)
    private Long fi04N1;

    @Column(name = "FI04_D1", nullable = true)
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1, nullable = true)
    private String fi04Lo1;

    @Column(name = "AUTHO_REMARK", nullable = false)
    private String authRemark;

    @Column(name = "H_STATUS", length = 1)
    private Character hStatus;

    public String[] getPkValues() {
        return new String[] { "AC", "TB_AC_VOUCHER_HIST", "VOU_HIST_ID" };
    }

    public AccountVoucherEntryHistEntity() {
        super();
    }

    public long getVouHistId() {
        return vouHistId;
    }

    public void setVouHistId(long vouHistId) {
        this.vouHistId = vouHistId;
    }

    public long getVouId() {
        return vouId;
    }

    public void setVouId(long vouId) {
        this.vouId = vouId;
    }

    public String getVouNo() {
        return vouNo;
    }

    public void setVouNo(String vouNo) {
        this.vouNo = vouNo;
    }

    public Date getVouDate() {
        return vouDate;
    }

    public void setVouDate(Date vouDate) {
        this.vouDate = vouDate;
    }

    public Date getVouPostingDate() {
        return vouPostingDate;
    }

    public void setVouPostingDate(Date vouPostingDate) {
        this.vouPostingDate = vouPostingDate;
    }

    public Long getVouTypeCpdId() {
        return vouTypeCpdId;
    }

    public void setVouTypeCpdId(Long vouTypeCpdId) {
        this.vouTypeCpdId = vouTypeCpdId;
    }

    public Long getVouSubtypeCpdId() {
        return vouSubtypeCpdId;
    }

    public void setVouSubtypeCpdId(Long vouSubtypeCpdId) {
        this.vouSubtypeCpdId = vouSubtypeCpdId;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public String getVouReferenceNo() {
        return vouReferenceNo;
    }

    public void setVouReferenceNo(String vouReferenceNo) {
        this.vouReferenceNo = vouReferenceNo;
    }

    public Date getVouReferenceNoDate() {
        return vouReferenceNoDate;
    }

    public void setVouReferenceNoDate(Date vouReferenceNoDate) {
        this.vouReferenceNoDate = vouReferenceNoDate;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getPayerPayee() {
        return payerPayee;
    }

    public void setPayerPayee(String payerPayee) {
        this.payerPayee = payerPayee;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getOrg() {
        return org;
    }

    public void setOrg(Long org) {
        this.org = org;
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

    public Long getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Long updatedby) {
        this.updatedby = updatedby;
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

    public Long getAuthoId() {
        return authoId;
    }

    public void setAuthoId(Long authoId) {
        this.authoId = authoId;
    }

    public Date getAuthoDate() {
        return authoDate;
    }

    public void setAuthoDate(Date authoDate) {
        this.authoDate = authoDate;
    }

    public String getAuthoFlg() {
        return authoFlg;
    }

    public void setAuthoFlg(String authoFlg) {
        this.authoFlg = authoFlg;
    }

    public Long getEntryType() {
        return entryType;
    }

    public void setEntryType(Long entryType) {
        this.entryType = entryType;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04N1(Long fi04n1) {
        fi04N1 = fi04n1;
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

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }

    public Character gethStatus() {
        return hStatus;
    }

    public void sethStatus(Character hStatus) {
        this.hStatus = hStatus;
    }

    @Override
    public String toString() {
        return "AccountVoucherEntryHistEntity [vouHistId=" + vouHistId + ", vouId=" + vouId + ", vouNo=" + vouNo
                + ", vouDate=" + vouDate + ", vouPostingDate=" + vouPostingDate + ", vouTypeCpdId=" + vouTypeCpdId
                + ", vouSubtypeCpdId=" + vouSubtypeCpdId + ", dpDeptid=" + dpDeptid + ", vouReferenceNo="
                + vouReferenceNo + ", vouReferenceNoDate=" + vouReferenceNoDate + ", narration=" + narration
                + ", payerPayee=" + payerPayee + ", fieldId=" + fieldId + ", org=" + org + ", createdBy=" + createdBy
                + ", lmodDate=" + lmodDate + ", updatedby=" + updatedby + ", updatedDate=" + updatedDate + ", lgIpMac="
                + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", authoId=" + authoId + ", authoDate=" + authoDate
                + ", authoFlg=" + authoFlg + ", entryType=" + entryType + ", fi04N1=" + fi04N1 + ", fi04D1=" + fi04D1
                + ", fi04Lo1=" + fi04Lo1 + ", authRemark=" + authRemark + ", hStatus=" + hStatus + "]";
    }

}