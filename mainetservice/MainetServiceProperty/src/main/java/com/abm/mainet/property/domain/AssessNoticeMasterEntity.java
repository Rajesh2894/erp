package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_as_not_mas database table.
 * 
 */
@Entity
@Table(name = "tb_as_not_mas")
public class AssessNoticeMasterEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MN_NOTID")
    private long mnNotid;

    @Column(name = "CPD_NOTTYP")
    private Long cpdNottyp;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "DEL_REASON")
    private String delReason;

    @Column(name = "DELETED_BY")
    private long deletedBy;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "MN_ASS_NO")
    private String mnAssNo;

    @Column(name = "MN_DUEDT")
    private Date mnDuedt;

    @Column(name = "MN_NOTACCEPTDT")
    private Date mnNotacceptdt;

    @Column(name = "MN_NOTDT")
    private Date mnNotdt;

    @Column(name = "MN_NOTNO")
    private Long mnNotno;

    @Column(name = "MN_REMARKS")
    private String mnRemarks;

    @Column(name = "MN_SIGNEDBY")
    private Long mnSignedby;

    @Column(name = "MN_SIGNEDDT")
    private Date mnSigneddt;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_not_mas", "MN_NOTID" };
    }

    public long getMnNotid() {
        return this.mnNotid;
    }

    public void setMnNotid(long mnNotid) {
        this.mnNotid = mnNotid;
    }

    public Long getCpdNottyp() {
        return this.cpdNottyp;
    }

    public void setCpdNottyp(Long cpdNottyp) {
        this.cpdNottyp = cpdNottyp;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDelReason() {
        return this.delReason;
    }

    public void setDelReason(String delReason) {
        this.delReason = delReason;
    }

    public long getDeletedBy() {
        return this.deletedBy;
    }

    public void setDeletedBy(long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getMnAssNo() {
        return this.mnAssNo;
    }

    public void setMnAssNo(String mnAssNo) {
        this.mnAssNo = mnAssNo;
    }

    public Date getMnDuedt() {
        return this.mnDuedt;
    }

    public void setMnDuedt(Date mnDuedt) {
        this.mnDuedt = mnDuedt;
    }

    public Date getMnNotacceptdt() {
        return this.mnNotacceptdt;
    }

    public void setMnNotacceptdt(Date mnNotacceptdt) {
        this.mnNotacceptdt = mnNotacceptdt;
    }

    public Date getMnNotdt() {
        return this.mnNotdt;
    }

    public void setMnNotdt(Date mnNotdt) {
        this.mnNotdt = mnNotdt;
    }

    public Long getMnNotno() {
        return this.mnNotno;
    }

    public void setMnNotno(Long mnNotno) {
        this.mnNotno = mnNotno;
    }

    public String getMnRemarks() {
        return this.mnRemarks;
    }

    public void setMnRemarks(String mnRemarks) {
        this.mnRemarks = mnRemarks;
    }

    public Long getMnSignedby() {
        return this.mnSignedby;
    }

    public void setMnSignedby(Long mnSignedby) {
        this.mnSignedby = mnSignedby;
    }

    public Date getMnSigneddt() {
        return this.mnSigneddt;
    }

    public void setMnSigneddt(Date mnSigneddt) {
        this.mnSigneddt = mnSigneddt;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

}