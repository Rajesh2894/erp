package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

public class LegalReferenceDTO implements Serializable {

    private static final long serialVersionUID = -4528565742719314306L;

    private Long loReferenceid;

    private String loDescription;

    private Date loReferencedate;

    private String loReferenceno;

    private Long loReferencetype;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long updatedBy;

    private Date updatedDate;

    private Long cseId;

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

    public String getLoDescription() {
        return this.loDescription;
    }

    public void setLoDescription(String loDescription) {
        this.loDescription = loDescription;
    }

    public Date getLoReferencedate() {
        return this.loReferencedate;
    }

    public void setLoReferencedate(Date loReferencedate) {
        this.loReferencedate = loReferencedate;
    }

    public Long getLoReferenceid() {
        return this.loReferenceid;
    }

    public void setLoReferenceid(Long loReferenceid) {
        this.loReferenceid = loReferenceid;
    }

    public String getLoReferenceno() {
        return this.loReferenceno;
    }

    public void setLoReferenceno(String loReferenceno) {
        this.loReferenceno = loReferenceno;
    }

    public Long getLoReferencetype() {
        return this.loReferencetype;
    }

    public void setLoReferencetype(Long loReferencetype) {
        this.loReferencetype = loReferencetype;
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

    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

}