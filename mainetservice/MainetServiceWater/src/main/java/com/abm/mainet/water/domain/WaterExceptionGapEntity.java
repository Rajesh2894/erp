package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Rahul.Yadav
 * @since 31 Mar 2017
 */
@Entity
@Table(name = "TB_WT_EXCEPTION_MGAP")
public class WaterExceptionGapEntity implements Serializable {
    private static final long serialVersionUID = -2478787047591718685L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MGAP_EXGID", precision = 12, scale = 0, nullable = false)
    private Long mgapExgid;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = false)
    private Long csIdn;

    @Column(name = "CPD_MTRSTATUS", precision = 12, scale = 0, nullable = true)
    private Long cpdMtrstatus;

    @Column(name = "CPD_GAP", precision = 12, scale = 0, nullable = true)
    private Long cpdGap;

    @Temporal(TemporalType.DATE)
    @Column(name = "MGAP_FROM", nullable = false)
    private Date mgapFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "MGAP_TO", nullable = false)
    private Date mgapTo;

    @Column(name = "MGAP_BILL_GEN", length = 1, nullable = true)
    private String mgapBillGen;

    @Column(name = "BM_IDNO", precision = 12, scale = 0, nullable = true)
    private Long bmIdno;

    @Column(name = "MGAP_ACTIVE", length = 1, nullable = true)
    private String mgapActive;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = true)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "MGAP_REMARK", length = 500, nullable = false)
    private String mgapRemark;

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_EXCEPTION_MGAP", "MGAP_EXGID" };
    }

    public Long getMgapExgid() {
        return mgapExgid;
    }

    public void setMgapExgid(final Long mgapExgid) {
        this.mgapExgid = mgapExgid;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Long getCpdMtrstatus() {
        return cpdMtrstatus;
    }

    public void setCpdMtrstatus(final Long cpdMtrstatus) {
        this.cpdMtrstatus = cpdMtrstatus;
    }

    public Long getCpdGap() {
        return cpdGap;
    }

    public void setCpdGap(final Long cpdGap) {
        this.cpdGap = cpdGap;
    }

    public Date getMgapFrom() {
        return mgapFrom;
    }

    public void setMgapFrom(final Date mgapFrom) {
        this.mgapFrom = mgapFrom;
    }

    public Date getMgapTo() {
        return mgapTo;
    }

    public void setMgapTo(final Date mgapTo) {
        this.mgapTo = mgapTo;
    }

    public String getMgapBillGen() {
        return mgapBillGen;
    }

    public void setMgapBillGen(final String mgapBillGen) {
        this.mgapBillGen = mgapBillGen;
    }

    public Long getBmIdno() {
        return bmIdno;
    }

    public void setBmIdno(final Long bmIdno) {
        this.bmIdno = bmIdno;
    }

    public String getMgapActive() {
        return mgapActive;
    }

    public void setMgapActive(final String mgapActive) {
        this.mgapActive = mgapActive;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getMgapRemark() {
        return mgapRemark;
    }

    public void setMgapRemark(final String mgapRemark) {
        this.mgapRemark = mgapRemark;
    }

}