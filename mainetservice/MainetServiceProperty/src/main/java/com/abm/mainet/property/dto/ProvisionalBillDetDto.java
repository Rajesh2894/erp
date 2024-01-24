package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProvisionalBillDetDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9113945413335694459L;

    private long proBdBilldetid;

    private ProvisionalBillMasDto bmIdNo;

    private Long taxId;

    private Long rebateId;

    private Long adjustmentId;

    private BigDecimal proBdCurBalTaxamt;

    private BigDecimal proBdCurTaxamt;

    private BigDecimal proBdCurTaxamtPrint;

    private BigDecimal proBdPrvArramt;

    private BigDecimal proBdPrvBalArramt;

    private String proBdBillflag;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long updatedBy;

    private Date updatedDate;

    private Long collSeq;

    private Long taxCategory;

    public long getProBdBilldetid() {
        return proBdBilldetid;
    }

    public void setProBdBilldetid(long proBdBilldetid) {
        this.proBdBilldetid = proBdBilldetid;
    }

    public ProvisionalBillMasDto getBmIdNo() {
        return bmIdNo;
    }

    public void setBmIdNo(ProvisionalBillMasDto bmIdNo) {
        this.bmIdNo = bmIdNo;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Long getRebateId() {
        return rebateId;
    }

    public void setRebateId(Long rebateId) {
        this.rebateId = rebateId;
    }

    public Long getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(Long adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public BigDecimal getProBdCurBalTaxamt() {
        return proBdCurBalTaxamt;
    }

    public void setProBdCurBalTaxamt(BigDecimal proBdCurBalTaxamt) {
        this.proBdCurBalTaxamt = proBdCurBalTaxamt;
    }

    public BigDecimal getProBdCurTaxamt() {
        return proBdCurTaxamt;
    }

    public void setProBdCurTaxamt(BigDecimal proBdCurTaxamt) {
        this.proBdCurTaxamt = proBdCurTaxamt;
    }

    public BigDecimal getProBdCurTaxamtPrint() {
        return proBdCurTaxamtPrint;
    }

    public void setProBdCurTaxamtPrint(BigDecimal proBdCurTaxamtPrint) {
        this.proBdCurTaxamtPrint = proBdCurTaxamtPrint;
    }

    public BigDecimal getProBdPrvArramt() {
        return proBdPrvArramt;
    }

    public void setProBdPrvArramt(BigDecimal proBdPrvArramt) {
        this.proBdPrvArramt = proBdPrvArramt;
    }

    public BigDecimal getProBdPrvBalArramt() {
        return proBdPrvBalArramt;
    }

    public void setProBdPrvBalArramt(BigDecimal proBdPrvBalArramt) {
        this.proBdPrvBalArramt = proBdPrvBalArramt;
    }

    public String getProBdBillflag() {
        return proBdBillflag;
    }

    public void setProBdBillflag(String proBdBillflag) {
        this.proBdBillflag = proBdBillflag;
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

    public Long getCollSeq() {
        return collSeq;
    }

    public void setCollSeq(Long collSeq) {
        this.collSeq = collSeq;
    }

    public Long getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(Long taxCategory) {
        this.taxCategory = taxCategory;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

}
