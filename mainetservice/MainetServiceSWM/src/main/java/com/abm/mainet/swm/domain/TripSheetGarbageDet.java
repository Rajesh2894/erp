package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_sw_tripsheet_gdet database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Entity
@Table(name = "TB_SW_TRIPSHEET_GDET")
public class TripSheetGarbageDet implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TRIPD_ID", unique = true, nullable = false)
    private Long tripdId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(nullable = false)
    private Long orgid;

    @Column(name = "TRIP_VOLUME", nullable = false)
    private BigDecimal tripVolume;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "WAST_TYPE", nullable = false)
    private Long wastType;

    // bi-directional many-to-one association to TbSwTripsheet
    @ManyToOne
    @JoinColumn(name = "TRIP_ID", nullable = false)
    private TripSheet tbSwTripsheet;

    public TripSheetGarbageDet() {
    }

    public Long getTripdId() {
        return this.tripdId;
    }

    public void setTripdId(Long tripdId) {
        this.tripdId = tripdId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public BigDecimal getTripVolume() {
        return tripVolume;
    }

    public void setTripVolume(BigDecimal tripVolume) {
        this.tripVolume = tripVolume;
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

    public Long getWastType() {
        return this.wastType;
    }

    public void setWastType(Long wastType) {
        this.wastType = wastType;
    }

    public TripSheet getTbSwTripsheet() {
        return this.tbSwTripsheet;
    }

    public void setTbSwTripsheet(TripSheet tbSwTripsheet) {
        this.tbSwTripsheet = tbSwTripsheet;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_TRIPSHEET_GDET", "TRIPD_ID" };
    }
}