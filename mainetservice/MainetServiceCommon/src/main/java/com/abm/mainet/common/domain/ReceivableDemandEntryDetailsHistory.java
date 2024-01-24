package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Entity
@Table(name = "TB_BILL_DET_HIST")
public class ReceivableDemandEntryDetailsHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_TBD_ID", nullable = false)
    private Long billDetIdH;

    @Column(name = "TBD_ID")
    private Long billDetId;

    @Column(name = "TBD_AMOUNT")
    private BigDecimal billDetailsAmount;

    @Column(name = "TAX_ID")
    private Long taxId;

    @Column(name = "SAC_HEAD_ID")
    private Long sacHeadId;

    @Column(name = "ACC_NO")
    private String accNo;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "ISDELETED")
    private String isDeleted;

    @Column(name = "SBD_REMARKS")
    private String remarks;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "CREATEDBY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDDATETIME", nullable = false)
    private Date createdDate;

    @Column(name = "LASTUPDATEDBY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LASTUPDATEDDATETIME")
    private Date updatedDate;

    @Column(name = "BM_ID")
    private Long billId;

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getBillDetIdH() {
        return billDetIdH;
    }

    public void setBillDetIdH(Long billDetIdH) {
        this.billDetIdH = billDetIdH;
    }

    public Long getBillDetId() {
        return billDetId;
    }

    public void setBillDetId(Long billDetId) {
        this.billDetId = billDetId;
    }

    public BigDecimal getBillDetailsAmount() {
        return billDetailsAmount;
    }

    public void setBillDetailsAmount(BigDecimal billDetailsAmount) {
        this.billDetailsAmount = billDetailsAmount;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public Long getOrgid() {
        return orgid;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String[] getPkValues() {

        return new String[] { "AC", "TB_BILL_DET_HIST", "BD_ID_H" };
    }

}
