package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
public class TripSheetGarbageDetDTO implements Serializable {

    private static final long serialVersionUID = 7017516156762482436L;

    private Long tripdId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private BigDecimal tripVolume;

    private Long updatedBy;

    private Date updatedDate;

    private Long wastType;

    private Long collCount;

    private Long collType;

    @JsonIgnore
    private TripSheetDTO tbSwTripsheet;

    public TripSheetGarbageDetDTO() {
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

    public TripSheetDTO getTbSwTripsheet() {
        return this.tbSwTripsheet;
    }

    public Long getCollCount() {
        return collCount;
    }

    public void setCollCount(Long collCount) {
        this.collCount = collCount;
    }

    public Long getCollType() {
        return collType;
    }

    public void setCollType(Long collType) {
        this.collType = collType;
    }

    public void setTbSwTripsheet(TripSheetDTO tbSwTripsheet) {
        this.tbSwTripsheet = tbSwTripsheet;
    }

}