package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;

public class AssessNoticeMasterDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -623099481020563843L;

    private long mnNotid;

    private Long cpdNottyp;

    private Long createdBy;

    private Date creationDate;

    private String delReason;

    private long deletedBy;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String mnAssNo;

    private Date mnDuedt;

    private Date mnNotacceptdt;

    private Date mnNotdt;

    private Long mnNotno;

    private String mnRemarks;

    private Long mnSignedby;

    private Date mnSigneddt;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    public long getMnNotid() {
        return mnNotid;
    }

    public void setMnNotid(long mnNotid) {
        this.mnNotid = mnNotid;
    }

    public Long getCpdNottyp() {
        return cpdNottyp;
    }

    public void setCpdNottyp(Long cpdNottyp) {
        this.cpdNottyp = cpdNottyp;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDelReason() {
        return delReason;
    }

    public void setDelReason(String delReason) {
        this.delReason = delReason;
    }

    public long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(long deletedBy) {
        this.deletedBy = deletedBy;
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

    public String getMnAssNo() {
        return mnAssNo;
    }

    public void setMnAssNo(String mnAssNo) {
        this.mnAssNo = mnAssNo;
    }

    public Date getMnDuedt() {
        return mnDuedt;
    }

    public void setMnDuedt(Date mnDuedt) {
        this.mnDuedt = mnDuedt;
    }

    public Date getMnNotacceptdt() {
        return mnNotacceptdt;
    }

    public void setMnNotacceptdt(Date mnNotacceptdt) {
        this.mnNotacceptdt = mnNotacceptdt;
    }

    public Date getMnNotdt() {
        return mnNotdt;
    }

    public void setMnNotdt(Date mnNotdt) {
        this.mnNotdt = mnNotdt;
    }

    public Long getMnNotno() {
        return mnNotno;
    }

    public void setMnNotno(Long mnNotno) {
        this.mnNotno = mnNotno;
    }

    public String getMnRemarks() {
        return mnRemarks;
    }

    public void setMnRemarks(String mnRemarks) {
        this.mnRemarks = mnRemarks;
    }

    public Long getMnSignedby() {
        return mnSignedby;
    }

    public void setMnSignedby(Long mnSignedby) {
        this.mnSignedby = mnSignedby;
    }

    public Date getMnSigneddt() {
        return mnSigneddt;
    }

    public void setMnSigneddt(Date mnSigneddt) {
        this.mnSigneddt = mnSigneddt;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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
}
