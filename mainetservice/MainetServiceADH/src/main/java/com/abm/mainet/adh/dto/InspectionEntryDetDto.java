package com.abm.mainet.adh.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Anwarul.Hassan
 * @since 25-Oct-2019
 */
public class InspectionEntryDetDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long inesDetId;

    private Long inesId;

    private Long remarkId;

    private Long remarkStatusId;

    private String observation;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    public Long getInesDetId() {
        return inesDetId;
    }

    public void setInesDetId(Long inesDetId) {
        this.inesDetId = inesDetId;
    }

    public Long getInesId() {
        return inesId;
    }

    public void setInesId(Long inesId) {
        this.inesId = inesId;
    }

    public Long getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(Long remarkId) {
        this.remarkId = remarkId;
    }

    public Long getRemarkStatusId() {
        return remarkStatusId;
    }

    public void setRemarkStatusId(Long remarkStatusId) {
        this.remarkStatusId = remarkStatusId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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

}
