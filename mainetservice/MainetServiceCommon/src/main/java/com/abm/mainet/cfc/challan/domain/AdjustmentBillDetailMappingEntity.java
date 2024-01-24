package com.abm.mainet.cfc.challan.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_ADJ_BILL_DETAILS")
public class AdjustmentBillDetailMappingEntity implements Serializable {

    private static final long serialVersionUID = 8231988725767976067L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ADJBD_ID", precision = 12, scale = 0, nullable = false)
    private long adjbdId;

    @Column(name = "ADJD_ID", nullable = false, updatable = false)
    private Long adjId;

    @Column(name = "BD_BILLDETID", nullable = false, updatable = false)
    private Long adjbmId;

    @Column(name = "dp_deptid", nullable = false, updatable = false)
    private Long dpDeptId;

    @Column(name = "adj_amount", precision = 15, scale = 2, nullable = true)
    private double adjAmount;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Transient
    private Long taxId;

    public String[] getPkValues() {
        return new String[] { "COM", "TB_ADJ_BILL_DETAILS", "ADJBD_ID" };
    }

    public long getAdjbdId() {
        return adjbdId;
    }

    public void setAdjbdId(final long adjbdId) {
        this.adjbdId = adjbdId;
    }

    public Long getAdjId() {
        return adjId;
    }

    public void setAdjId(final Long adjId) {
        this.adjId = adjId;
    }

    public Long getAdjbmId() {
        return adjbmId;
    }

    public void setAdjbmId(final Long adjbmId) {
        this.adjbmId = adjbmId;
    }

    public Long getDpDeptId() {
        return dpDeptId;
    }

    public void setDpDeptId(final Long dpDeptId) {
        this.dpDeptId = dpDeptId;
    }

    public double getAdjAmount() {
        return adjAmount;
    }

    public void setAdjAmount(final double adjAmount) {
        this.adjAmount = adjAmount;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
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

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }
}
