package com.abm.mainet.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

@Entity
@Table(name = "TB_RECEIPTBOOKLEAF_MAS")
public class BookReceiptEntity {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RB_ID", precision = 12, scale = 0, nullable = false, columnDefinition = "Long default 100")
    private Long rbId;

    @Column(name = "FA_YEARID", length = 100, nullable = true)
    private long faYearId;

    @Column(name = "RB_RECEIPT_BNO", length = 100, nullable = true)
    private Long bookreceiptNo;

    @Column(name = "RB_RECEIPT_FNO", length = 100, nullable = true)
    private Long bookreceiptNoFrom;

    @Column(name = "RB_RECEIPT_TNO", length = 100, nullable = true)
    private Long bookreceiptNoTo;

    @Column(name = "EMPID ", length = 100, nullable = true)
    private Long empId;

    @Column(name = "RB_LEAVE", length = 100, nullable = true)
    private long totalbookReceiptNo;

    @Column(name = "ORGID", length = 100, nullable = true)
    private long orgId;

    @Column(name = "CREATED_BY", length = 100, nullable = true)
    private long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPDATED_BY", length = 7, nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String ipMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String ipMacUpdated;

    public Long getRbId() {
        return rbId;
    }

    public void setRbId(Long rbId) {
        this.rbId = rbId;
    }

    public long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(long faYearId) {
        this.faYearId = faYearId;
    }

    public Long getBookreceiptNo() {
        return bookreceiptNo;
    }

    public void setBookreceiptNo(Long bookreceiptNo) {
        this.bookreceiptNo = bookreceiptNo;
    }

    public Long getBookreceiptNoFrom() {
        return bookreceiptNoFrom;
    }

    public void setBookreceiptNoFrom(Long bookreceiptNoFrom) {
        this.bookreceiptNoFrom = bookreceiptNoFrom;
    }

    public Long getBookreceiptNoTo() {
        return bookreceiptNoTo;
    }

    public void setBookreceiptNoTo(Long bookreceiptNoTo) {
        this.bookreceiptNoTo = bookreceiptNoTo;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public long getTotalbookReceiptNo() {
        return totalbookReceiptNo;
    }

    public void setTotalbookReceiptNo(long totalbookReceiptNo) {
        this.totalbookReceiptNo = totalbookReceiptNo;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
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

    public String getIpMac() {
        return ipMac;
    }

    public void setIpMac(String ipMac) {
        this.ipMac = ipMac;
    }

    public String getIpMacUpdated() {
        return ipMacUpdated;
    }

    public void setIpMacUpdated(String ipMacUpdated) {
        this.ipMacUpdated = ipMacUpdated;
    }

    public String[] getPkValues() {
        return new String[] { MainetConstants.CommonConstants.COM, "TB_RECEIPTBOOKLEAF_MAS", "RB_ID" };

    }

}
