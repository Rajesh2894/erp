package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_BILL_DET")
public class ReceivableDemandEntryDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TBD_ID", nullable = false)
    private Long billDetId;

    @Column(name = "TBD_AMOUNT")
    private BigDecimal billDetailsAmount;

    @Column(name = "TAX_ID")
    private Long taxId;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;
    
    @Column(name = "SAC_HEAD_ID")
    private Long sacHeadId;

    @Column(name = "ACC_NO")
    private String accNo;

    @Column(name = "ISDELETED")
    private String isDeleted;

    @Column(name = "SBD_REMARKS")
    private String remarks;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BM_ID")
    private ReceivableDemandEntry rcvblDemandDets;

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

    public Long getOrgid() {
        return orgid;
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

    public ReceivableDemandEntry getRcvblDemandDets() {
        return rcvblDemandDets;
    }

    public void setRcvblDemandDets(ReceivableDemandEntry rcvblDemandDets) {
        this.rcvblDemandDets = rcvblDemandDets;
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

        return new String[] { "AC", "TB_BILL_DET", "BD_ID" };
    }

}
