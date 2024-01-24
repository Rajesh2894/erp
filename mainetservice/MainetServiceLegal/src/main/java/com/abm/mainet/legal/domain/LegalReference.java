package com.abm.mainet.legal.domain;

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
 * The persistent class for the tb_lgl_legalreference database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_LEGALREFERENCE")
public class LegalReference implements Serializable {

    private static final long serialVersionUID = 2987298960324545845L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LO_REFERENCEID")
    private Long loReferenceid;

    @Column(name = "LO_DESCRIPTION")
    private String loDescription;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LO_REFERENCEDATE")
    private Date loReferencedate;

    @Column(name = "LO_REFERENCENO")
    private String loReferenceno;

    @Column(name = "LO_REFERENCETYPE")
    private Long loReferencetype;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CSE_ID")
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

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_LEGALREFERENCE", "LO_REFERENCEID" };
    }
}