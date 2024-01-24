/*
 * Created on 2 Jan 2016 ( Time 11:56:57 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TbFincialyearorgMap implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    private Long mapId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    private Long faYearid;

    private Long orgid;

    private Long userId;

    private Date lmoddate;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private Long yaTypeCpdId;

    @JsonIgnore
    @Size(max = 100)
    private String ipMac;

    @JsonIgnore
    @Size(max = 100)
    private String ipMacUpd;

    private String authStatus;

    private Long autBy;

    private Date autDate;

    private String centraleno;

    private String orgNameEng;
    private String orgNameReg;

    private Long faFromMonth;

    private Long faToMonth;

    private Long faFromYear;

    private Long faToYear;

    private Long faMonthStatus;

    // new added field to get financial from date and financial to date for web service
    private Date faFromDate;
    private Date faToDate;
    private String faYearstatusDesc;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setMapId(final Long mapId) {
        this.mapId = mapId;
    }

    public Long getMapId() {
        return mapId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    public Long getFaYearid() {
        return faYearid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    /*
     * public void setLangId( Long langId ) { this.langId = langId; } public Long getLangId() { return this.langId; }
     */

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setYaTypeCpdId(final Long yaTypeCpdId) {
        this.yaTypeCpdId = yaTypeCpdId;
    }

    public Long getYaTypeCpdId() {
        return yaTypeCpdId;
    }

    public void setIpMac(final String ipMac) {
        this.ipMac = ipMac;
    }

    public String getIpMac() {
        return ipMac;
    }

    public void setIpMacUpd(final String ipMacUpd) {
        this.ipMacUpd = ipMacUpd;
    }

    public String getIpMacUpd() {
        return ipMacUpd;
    }

    public void setAuthStatus(final String authStatus) {
        this.authStatus = authStatus;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAutBy(final Long autBy) {
        this.autBy = autBy;
    }

    public Long getAutBy() {
        return autBy;
    }

    public void setAutDate(final Date autDate) {
        this.autDate = autDate;
    }

    public Date getAutDate() {
        return autDate;
    }

    public void setCentraleno(final String centraleno) {
        this.centraleno = centraleno;
    }

    public String getCentraleno() {
        return centraleno;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(mapId);
        sb.append("|");
        sb.append(faYearid);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        // sb.append("|");
        // sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(yaTypeCpdId);
        sb.append("|");
        sb.append(ipMac);
        sb.append("|");
        sb.append(ipMacUpd);
        sb.append("|");
        sb.append(authStatus);
        sb.append("|");
        sb.append(autBy);
        sb.append("|");
        sb.append(autDate);
        sb.append("|");
        sb.append(centraleno);
        return sb.toString();
    }

    public String getOrgNameEng() {
        return orgNameEng;
    }

    public void setOrgNameEng(final String orgNameEng) {
        this.orgNameEng = orgNameEng;
    }

    public String getOrgNameReg() {
        return orgNameReg;
    }

    public void setOrgNameReg(final String orgNameReg) {
        this.orgNameReg = orgNameReg;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the faFromMonth
     */
    public Long getFaFromMonth() {
        return faFromMonth;
    }

    /**
     * @param faFromMonth the faFromMonth to set
     */
    public void setFaFromMonth(final Long faFromMonth) {
        this.faFromMonth = faFromMonth;
    }

    /**
     * @return the faToMonth
     */
    public Long getFaToMonth() {
        return faToMonth;
    }

    /**
     * @param faToMonth the faToMonth to set
     */
    public void setFaToMonth(final Long faToMonth) {
        this.faToMonth = faToMonth;
    }

    /**
     * @return the faFromYear
     */
    public Long getFaFromYear() {
        return faFromYear;
    }

    /**
     * @param faFromYear the faFromYear to set
     */
    public void setFaFromYear(final Long faFromYear) {
        this.faFromYear = faFromYear;
    }

    /**
     * @return the faToYear
     */
    public Long getFaToYear() {
        return faToYear;
    }

    /**
     * @param faToYear the faToYear to set
     */
    public void setFaToYear(final Long faToYear) {
        this.faToYear = faToYear;
    }

    /**
     * @return the faMonthStatus
     */
    public Long getFaMonthStatus() {
        return faMonthStatus;
    }

    /**
     * @param faMonthStatus the faMonthStatus to set
     */
    public void setFaMonthStatus(final Long faMonthStatus) {
        this.faMonthStatus = faMonthStatus;
    }

    public Date getFaFromDate() {
        return faFromDate;
    }

    public void setFaFromDate(Date faFromDate) {
        this.faFromDate = faFromDate;
    }

    public Date getFaToDate() {
        return faToDate;
    }

    public void setFaToDate(Date faToDate) {
        this.faToDate = faToDate;
    }

    public String getFaYearstatusDesc() {
        return faYearstatusDesc;
    }

    public void setFaYearstatusDesc(String faYearstatusDesc) {
        this.faYearstatusDesc = faYearstatusDesc;
    }
}
