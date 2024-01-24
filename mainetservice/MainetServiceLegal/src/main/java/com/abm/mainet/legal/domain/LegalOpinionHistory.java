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
 * The persistent class for the tb_lgl_legalopinion_hist database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_LEGALOPINION_HIST")
public class LegalOpinionHistory implements Serializable {

    private static final long serialVersionUID = -5504983055278915054L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LO_OPINIONID_H")
    private Long loOpinionidH;

    @Column(name = "LO_CPDOPINIONBY")
    private Long loCpdopinionby;

    @Temporal(TemporalType.DATE)
    @Column(name = "LO_DATE")
    private Date loDate;

    @Column(name = "LO_NAME")
    private String loName;

    @Column(name = "LO_OPINION")
    private String loOpinion;

    @Column(name = "LO_OPINIONID")
    private Long loOpinionid;

    @Column(name = "ADV_ID")
    private Long advId;

    @Column(name = "CSE_ID")
    private Long cseId;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public LegalOpinionHistory() {
    }

    public Long getLoOpinionidH() {
        return this.loOpinionidH;
    }

    public void setLoOpinionidH(Long loOpinionidH) {
        this.loOpinionidH = loOpinionidH;
    }

    public Long getAdvId() {
        return this.advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public Long getCreatedBy() {
        return createdBy;
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

    public Long getCseId() {
        return this.cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public Long getLoCpdopinionby() {
        return this.loCpdopinionby;
    }

    public void setLoCpdopinionby(Long loCpdopinionby) {
        this.loCpdopinionby = loCpdopinionby;
    }

    public Date getLoDate() {
        return this.loDate;
    }

    public void setLoDate(Date loDate) {
        this.loDate = loDate;
    }

    public String getLoName() {
        return this.loName;
    }

    public void setLoName(String loName) {
        this.loName = loName;
    }

    public String getLoOpinion() {
        return this.loOpinion;
    }

    public void setLoOpinion(String loOpinion) {
        this.loOpinion = loOpinion;
    }

    public Long getLoOpinionid() {
        return this.loOpinionid;
    }

    public void setLoOpinionid(Long loOpinionid) {
        this.loOpinionid = loOpinionid;
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

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_LEGALOPINION_HIST", "LO_OPINIONID_H" };
    }

}