package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author deepika.pimpale
 * @since 09 Sep 2016
 * @comment This table used for DEAS Voucher master entry.
 */
@Entity
@Table(name = "TB_AC_VOUCHER")
public class AccountVoucherEntryEntity implements Serializable {

    private static final long serialVersionUID = -8747813326195893892L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VOU_ID", precision = 12, scale = 0, nullable = false)
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

    @Column(name = "VOU_REFERENCE_NO", length = 20, nullable = false)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "master", cascade = CascadeType.ALL)
    private List<AccountVoucherEntryDetailsEntity> details = new ArrayList<>(0);

    public String[] getPkValues() {
        return new String[] { "AC", "TB_AC_VOUCHER", "VOU_ID" };
    }

    /**
     * @return the vouId
     */
    public long getVouId() {
        return vouId;
    }

    /**
     * @param vouId the vouId to set
     */
    public void setVouId(final long vouId) {
        this.vouId = vouId;
    }

    /**
     * @return the vouNo
     */
    public String getVouNo() {
        return vouNo;
    }

    /**
     * @param vouNo the vouNo to set
     */
    public void setVouNo(final String vouNo) {
        this.vouNo = vouNo;
    }

    /**
     * @return the vouDate
     */
    public Date getVouDate() {
        return vouDate;
    }

    /**
     * @param vouDate the vouDate to set
     */
    public void setVouDate(final Date vouDate) {
        this.vouDate = vouDate;
    }

    /**
     * @return the vouPostingDate
     */
    public Date getVouPostingDate() {
        return vouPostingDate;
    }

    /**
     * @param vouPostingDate the vouPostingDate to set
     */
    public void setVouPostingDate(final Date vouPostingDate) {
        this.vouPostingDate = vouPostingDate;
    }

    /**
     * @return the vouTypeCpdId
     */
    public Long getVouTypeCpdId() {
        return vouTypeCpdId;
    }

    /**
     * @param vouTypeCpdId the vouTypeCpdId to set
     */
    public void setVouTypeCpdId(final Long vouTypeCpdId) {
        this.vouTypeCpdId = vouTypeCpdId;
    }

    /**
     * @return the vouSubtypeCpdId
     */
    public Long getVouSubtypeCpdId() {
        return vouSubtypeCpdId;
    }

    /**
     * @param vouSubtypeCpdId the vouSubtypeCpdId to set
     */
    public void setVouSubtypeCpdId(final Long vouSubtypeCpdId) {
        this.vouSubtypeCpdId = vouSubtypeCpdId;
    }

    /**
     * @return the dpDeptid
     */
    public Long getDpDeptid() {
        return dpDeptid;
    }

    /**
     * @param dpDeptid the dpDeptid to set
     */
    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    /**
     * @return the vouReferenceNo
     */
    public String getVouReferenceNo() {
        return vouReferenceNo;
    }

    /**
     * @param vouReferenceNo the vouReferenceNo to set
     */
    public void setVouReferenceNo(final String vouReferenceNo) {
        this.vouReferenceNo = vouReferenceNo;
    }

    /**
     * @return the vouReferenceNoDate
     */
    public Date getVouReferenceNoDate() {
        return vouReferenceNoDate;
    }

    /**
     * @param vouReferenceNoDate the vouReferenceNoDate to set
     */
    public void setVouReferenceNoDate(final Date vouReferenceNoDate) {
        this.vouReferenceNoDate = vouReferenceNoDate;
    }

    /**
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(final String narration) {
        this.narration = narration;
    }

    /**
     * @return the payerPayee
     */
    public String getPayerPayee() {
        return payerPayee;
    }

    /**
     * @param payerPayee the payerPayee to set
     */
    public void setPayerPayee(final String payerPayee) {
        this.payerPayee = payerPayee;
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
     * @return the authoId
     */
    public Long getAuthoId() {
        return authoId;
    }

    /**
     * @param authoId the authoId to set
     */
    public void setAuthoId(final Long authoId) {
        this.authoId = authoId;
    }

    /**
     * @return the authoDate
     */
    public Date getAuthoDate() {
        return authoDate;
    }

    /**
     * @param authoDate the authoDate to set
     */
    public void setAuthoDate(final Date authoDate) {
        this.authoDate = authoDate;
    }

    /**
     * @return the authoFlg
     */
    public String getAuthoFlg() {
        return authoFlg;
    }

    /**
     * @param authoFlg the authoFlg to set
     */
    public void setAuthoFlg(final String authoFlg) {
        this.authoFlg = authoFlg;
    }

    /**
     * @return the entryType
     */
    public Long getEntryType() {
        return entryType;
    }

    /**
     * @param entryType the entryType to set
     */
    public void setEntryType(final Long entryType) {
        this.entryType = entryType;
    }

    /**
     * @return the fi04N1
     */
    public Long getFi04N1() {
        return fi04N1;
    }

    /**
     * @param fi04n1 the fi04N1 to set
     */
    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
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
     * @return the fi04Lo1
     */
    public String getFi04Lo1() {
        return fi04Lo1;
    }

    /**
     * @param fi04Lo1 the fi04Lo1 to set
     */
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    /**
     * @return the details
     */
    public List<AccountVoucherEntryDetailsEntity> getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(final List<AccountVoucherEntryDetailsEntity> details) {
        this.details = details;
    }

    /**
     * @return the org
     */
    public Long getOrg() {
        return org;
    }

    /**
     * @param org the org to set
     */
    public void setOrg(final Long org) {
        this.org = org;
    }

    /**
     * @return the updatedby
     */
    public Long getUpdatedby() {
        return updatedby;
    }

    /**
     * @param updatedby the updatedby to set
     */
    public void setUpdatedby(final Long updatedby) {
        this.updatedby = updatedby;
    }

    /**
     * @return the fieldId
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId the fieldId to set
     */
    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(final String authRemark) {
        this.authRemark = authRemark;
    }

}