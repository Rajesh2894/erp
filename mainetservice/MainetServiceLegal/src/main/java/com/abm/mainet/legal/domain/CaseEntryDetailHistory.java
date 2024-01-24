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
 * The persistent class for the tb_lgl_case_pddetails_hist database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_CASE_PDDETAILS_HIST")
public class CaseEntryDetailHistory implements Serializable {

    private static final long serialVersionUID = 5502665666756196550L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CSED_ID_H", unique = true, nullable = false)
    private Long csedIdH;

    @Column(name = "CSE_ID")
    private Long cseId;

    @Column(name = "CSED_ADDRESS")
    private String csedAddress;

    @Column(name = "CSED_CONTACTNO", length = 10)
    private String csedContactno;

    @Column(name = "CSED_EMAILID", length = 10)
    private String csedEmailid;

    @Column(name = "CSED_FLAG", length = 1)
    private String csedFlag;

    @Column(name = "CSED_ID")
    private Long csedId;

    @Column(name = "CSED_NAME", length = 500)
    private String csedName;

    @Column(name = "CSED_STATUS", length = 1)
    private String csedStatus;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CSED_PARTY_TYPE")
    private Long csedPartyType;

    public Long getCsedIdH() {
        return csedIdH;
    }

    public void setCsedIdH(Long csedIdH) {
        this.csedIdH = csedIdH;
    }

    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public String getCsedAddress() {
        return csedAddress;
    }

    public void setCsedAddress(String csedAddress) {
        this.csedAddress = csedAddress;
    }

    public String getCsedContactno() {
        return csedContactno;
    }

    public void setCsedContactno(String csedContactno) {
        this.csedContactno = csedContactno;
    }

    public String getCsedEmailid() {
        return csedEmailid;
    }

    public void setCsedEmailid(String csedEmailid) {
        this.csedEmailid = csedEmailid;
    }

    public String getCsedFlag() {
        return csedFlag;
    }

    public void setCsedFlag(String csedFlag) {
        this.csedFlag = csedFlag;
    }

    public Long getCsedId() {
        return csedId;
    }

    public void setCsedId(Long csedId) {
        this.csedId = csedId;
    }

    public String getCsedName() {
        return csedName;
    }

    public void setCsedName(String csedName) {
        this.csedName = csedName;
    }

    public String getCsedStatus() {
        return csedStatus;
    }

    public void setCsedStatus(String csedStatus) {
        this.csedStatus = csedStatus;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the csedPartyType
     */
    public Long getCsedPartyType() {
        return csedPartyType;
    }

    /**
     * @param csedPartyType the csedPartyType to set
     */
    public void setCsedPartyType(Long csedPartyType) {
        this.csedPartyType = csedPartyType;
    }

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_CASE_PDDETAILS_HIST", "CSED_ID_H" };
    }
}