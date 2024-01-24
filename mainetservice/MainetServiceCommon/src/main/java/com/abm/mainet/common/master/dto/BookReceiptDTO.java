package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

public class BookReceiptDTO implements Serializable {

    private static final long serialVersionUID = 3030183249553555957L;
    private long rbId;
    private Long bookreceiptNoFrom;
    private Long bookreceiptNoTo;
    private Long empId;
    private Long faYearId;
    private Long totalbookReceiptNo;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Date updatedDate;
    private String ipMac;
    private String ipMacUpdated;
    private Long updatedBy;
    private Long bookreceiptNo;
    private String formMode;

    public String getFormMode() {
        return formMode;
    }

    public void setFormMode(String formMode) {
        this.formMode = formMode;
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

    public Long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(Long faYearId) {
        this.faYearId = faYearId;
    }

    public long getRbId() {
        return rbId;
    }

    public void setRbId(long rbId) {
        this.rbId = rbId;
    }

    public Long getBookreceiptNo() {
        return bookreceiptNo;
    }

    public void setBookreceiptNo(Long bookreceiptNo) {
        this.bookreceiptNo = bookreceiptNo;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getTotalbookReceiptNo() {
        return totalbookReceiptNo;
    }

    public void setTotalbookReceiptNo(Long totalbookReceiptNo) {
        this.totalbookReceiptNo = totalbookReceiptNo;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

}
