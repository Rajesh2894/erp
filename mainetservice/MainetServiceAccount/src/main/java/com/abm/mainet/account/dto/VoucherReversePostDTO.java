package com.abm.mainet.account.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class VoucherReversePostDTO {

    private long vouId;

    private String vouNo;

    private Date vouDate;

    private Date vouPostingDate;

    private Long vouTypeCpdId;

    private Long vouSubtypeCpdId;

    private Long dpDeptid;

    private String vouReferenceNo;

    private Date vouReferenceNoDate;

    private String narration;

    private String payerPayee;

    private Long fieldId;

    private Long org;

    private Long createdBy;

    private Date lmodDate;

    private Long updatedby;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private Long authoId;

    private Date authoDate;

    private String authoFlg;

    private Long entryType;

    private Long fi04N1;

    // private Date fi04D1;

    private String fi04Lo1;

    private String authRemark;

    private List<VoucherReversePostDetailDTO> details;

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

    public List<VoucherReversePostDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<VoucherReversePostDetailDTO> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "VoucherReversePostDTO [vouId=" + vouId + ", vouNo=" + vouNo + ", vouDate=" + vouDate + ", vouPostingDate="
                + vouPostingDate + ", vouTypeCpdId=" + vouTypeCpdId + ", vouSubtypeCpdId=" + vouSubtypeCpdId + ", dpDeptid="
                + dpDeptid + ", vouReferenceNo=" + vouReferenceNo + ", vouReferenceNoDate=" + vouReferenceNoDate + ", narration="
                + narration + ", payerPayee=" + payerPayee + ", fieldId=" + fieldId + ", org=" + org + ", createdBy=" + createdBy
                + ", lmodDate=" + lmodDate + ", updatedby=" + updatedby + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + ", authoId=" + authoId + ", authoDate=" + authoDate + ", authoFlg=" + authoFlg
                + ", entryType=" + entryType + ", fi04N1=" + fi04N1 + ", fi04Lo1=" + fi04Lo1
                + ", authRemark=" + authRemark + ", details=" + details + "]";
    }

}
