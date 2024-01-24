package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Lalit.Prusti
 *
 */
public class CaseEntryDetailDTO implements Serializable {

    private static final long serialVersionUID = 1282061851756799301L;

    private Long csedId;

    private String csedAddress;

    private String csedContactno;

    @Size(max = 10)
    private String csedEmailid;

    private String csedFlag;

    private String csedName;

    private String csedFname;

    private String csedStatus;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long csedPartyType;

    private Long csedParty;

    @JsonIgnore
    private CaseEntryDTO tbLglCaseMa;

    public Long getCsedId() {
        return csedId;
    }

    public void setCsedId(Long csedId) {
        this.csedId = csedId;
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

    public String getCsedName() {
        return csedName;
    }

    public void setCsedName(String csedName) {
        this.csedName = csedName;
    }

    public String getCsedFname() {
        return csedFname;
    }

    public void setCsedFname(String csedFname) {
        this.csedFname = csedFname;
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

    public CaseEntryDTO getTbLglCaseMa() {
        return tbLglCaseMa;
    }

    public void setTbLglCaseMa(CaseEntryDTO tbLglCaseMa) {
        this.tbLglCaseMa = tbLglCaseMa;
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

    /**
     * @return the csedParty
     */
    public Long getCsedParty() {
        return csedParty;
    }

    /**
     * @param csedParty the csedParty to set
     */
    public void setCsedParty(Long csedParty) {
        this.csedParty = csedParty;
    }

}