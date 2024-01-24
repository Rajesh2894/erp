package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Lalit.Prusti
 *
 */
public class DoorToDoorGarbageCollectionDTO implements Serializable {

    private static final long serialVersionUID = -8590732410692599771L;

    private Long dgId;

    private Long registrationId;

    private Date dgcDate;

    private Long createdBy;

    private Date createdDate;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    public Long getDgId() {
        return this.dgId;
    }

    public void setDgId(Long dgId) {
        this.dgId = dgId;
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

    public Date getDgcDate() {
        return this.dgcDate;
    }

    public void setDgcDate(Date dgcDate) {
        this.dgcDate = dgcDate;
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

    public Long getRegistrationId() {
        return this.registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
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