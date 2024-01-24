package com.abm.mainet.quartz.domain;

import java.math.BigDecimal;
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

@Entity
@Table(name = "BILL_MAS")
public class ViewSalaryBillMasterEntity {
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BM_ID", precision = 19, scale = 0, nullable = false)
    private Long bmId;
    @Column(name = "BM_BILLNO", length = 40, scale = 0, nullable = false)
    private String bmBillno;
    @Column(name = "BM_BILLTYPE_CPD_ID", precision = 19, scale = 0, nullable = false)
    private Long bmBillTypeCpdId;
    @Temporal(TemporalType.DATE)
    @Column(name = "BM_ENTRYDATE", nullable = false)
    private Date bmEntryDate;
    @Column(name = "DP_DEPTID", precision = 19, scale = 0, nullable = false)
    private Long dpDepId;
    @Column(name = "VM_VENDORID", precision = 19, scale = 0, nullable = false)
    private Long vmVenderId;
    @Column(name = "BM_INVOICENUMBER", precision = 40, scale = 0, nullable = false)
    private String bmInvoiceNumber;
    @Column(name = "BM_INVOICEVALUE", precision = 15, scale = 2, nullable = false)
    private BigDecimal bmInvoiceValue;
    @Column(name = "BM_NARRATION", length = 1400, nullable = false)
    private String bmNarration;
    @Column(name = "ORGID", precision = 19, scale = 0, nullable = false)
    private Long orgId;
    @Column(name = "CREATED_BY", precision = 19, scale = 0, nullable = false)
    private Long createdBy;
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;
    @Column(name = "LG_IP_MAC", length = 100, scale = 0, nullable = false)
    private String lgIpMac;
    @Column(name = "FIELD_ID", precision = 19, scale = 0, nullable = false)
    private Long fieldId;
    @OneToMany(mappedBy = "viewBillMasterId", targetEntity = ViewBillExpenditureEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ViewBillExpenditureEntity> viewExpenditureDetailList;
    @OneToMany(mappedBy = "viewBillMasterId", targetEntity = ViewBillDeductionEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ViewBillDeductionEntity> viewDeductionDetailList;

    public Long getBmId() {
        return bmId;
    }

    public void setBmId(Long bmId) {
        this.bmId = bmId;
    }

    public String getBmBillno() {
        return bmBillno;
    }

    public void setBmBillno(String bmBillno) {
        this.bmBillno = bmBillno;
    }

    public Long getBmBillTypeCpdId() {
        return bmBillTypeCpdId;
    }

    public void setBmBillTypeCpdId(Long bmBillTypeCpdId) {
        this.bmBillTypeCpdId = bmBillTypeCpdId;
    }

    public Date getBmEntryDate() {
        return bmEntryDate;
    }

    public void setBmEntryDate(Date bmEntryDate) {
        this.bmEntryDate = bmEntryDate;
    }

    public Long getDpDepId() {
        return dpDepId;
    }

    public void setDpDepId(Long dpDepId) {
        this.dpDepId = dpDepId;
    }

    public Long getVmVenderId() {
        return vmVenderId;
    }

    public void setVmVenderId(Long vmVenderId) {
        this.vmVenderId = vmVenderId;
    }

    public String getBmInvoiceNumber() {
        return bmInvoiceNumber;
    }

    public void setBmInvoiceNumber(String bmInvoiceNumber) {
        this.bmInvoiceNumber = bmInvoiceNumber;
    }

    public BigDecimal getBmInvoiceValue() {
        return bmInvoiceValue;
    }

    public void setBmInvoiceValue(BigDecimal bmInvoiceValue) {
        this.bmInvoiceValue = bmInvoiceValue;
    }

    public String getBmNarration() {
        return bmNarration;
    }

    public void setBmNarration(String bmNarration) {
        this.bmNarration = bmNarration;
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

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public List<ViewBillExpenditureEntity> getViewExpenditureDetailList() {
        return viewExpenditureDetailList;
    }

    public void setViewExpenditureDetailList(List<ViewBillExpenditureEntity> viewExpenditureDetailList) {
        this.viewExpenditureDetailList = viewExpenditureDetailList;
    }

    public List<ViewBillDeductionEntity> getViewDeductionDetailList() {
        return viewDeductionDetailList;
    }

    public void setViewDeductionDetailList(List<ViewBillDeductionEntity> viewDeductionDetailList) {
        this.viewDeductionDetailList = viewDeductionDetailList;
    }
}
