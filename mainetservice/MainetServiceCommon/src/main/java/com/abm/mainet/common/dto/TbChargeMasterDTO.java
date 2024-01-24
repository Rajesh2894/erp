package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Jeetendra.Pal
 *
 */
public class TbChargeMasterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long cmId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Long cmOrgid;

    private Long cmServiceId;

    private Long cmChargeapplicableat;

    private Long cmChargeType;

    private Long cmSlabDepend;

    private Long cmFlatDepend;

    @Size(max = 100)
    private String cmChargedescriptionEng;

    @Size(max = 100)
    private String cmChargedescriptionReg;

    private Long cmChargeSequence;

    private Date cmChargeStartDate;

    private Date cmChargeEndDate;

    private Date createdDate;

    private Long createdBy;

    private Date updatedDate;

    private Long updatedBy;

    @Size(min = 1, max = 1)
    private String isdeleted;

    @JsonIgnore
    @Size(min = 1, max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private String cmChargeStartDateStr;

    private String cmChargeEndDateStr;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setCmId(final Long cmId) {
        this.cmId = cmId;
    }

    public Long getCmId() {
        return cmId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setCmOrgid(final Long cmOrgid) {
        this.cmOrgid = cmOrgid;
    }

    public Long getCmOrgid() {
        return cmOrgid;
    }

    public void setCmServiceId(final Long cmServiceId) {
        this.cmServiceId = cmServiceId;
    }

    public Long getCmServiceId() {
        return cmServiceId;
    }

    public void setCmChargeapplicableat(final Long cmChargeapplicableat) {
        this.cmChargeapplicableat = cmChargeapplicableat;
    }

    public Long getCmChargeapplicableat() {
        return cmChargeapplicableat;
    }

    public void setCmChargeType(final Long cmChargeType) {
        this.cmChargeType = cmChargeType;
    }

    public Long getCmChargeType() {
        return cmChargeType;
    }

    public void setCmSlabDepend(final Long cmSlabDepend) {
        this.cmSlabDepend = cmSlabDepend;
    }

    public Long getCmSlabDepend() {
        return cmSlabDepend;
    }

    public void setCmFlatDepend(final Long cmFlatDepend) {
        this.cmFlatDepend = cmFlatDepend;
    }

    public Long getCmFlatDepend() {
        return cmFlatDepend;
    }

    public void setCmChargedescriptionEng(final String cmChargedescriptionEng) {
        this.cmChargedescriptionEng = cmChargedescriptionEng;
    }

    public String getCmChargedescriptionEng() {
        return cmChargedescriptionEng;
    }

    public void setCmChargedescriptionReg(final String cmChargedescriptionReg) {
        this.cmChargedescriptionReg = cmChargedescriptionReg;
    }

    public String getCmChargedescriptionReg() {
        return cmChargedescriptionReg;
    }

    public void setCmChargeSequence(final Long cmChargeSequence) {
        this.cmChargeSequence = cmChargeSequence;
    }

    public Long getCmChargeSequence() {
        return cmChargeSequence;
    }

    public void setCmChargeStartDate(final Date cmChargeStartDate) {
        this.cmChargeStartDate = cmChargeStartDate;
    }

    public Date getCmChargeStartDate() {
        return cmChargeStartDate;
    }

    public void setCmChargeEndDate(final Date cmChargeEndDate) {
        this.cmChargeEndDate = cmChargeEndDate;
    }

    public Date getCmChargeEndDate() {
        return cmChargeEndDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setIsdeleted(final String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(cmId);
        sb.append("|");
        sb.append(cmOrgid);
        sb.append("|");
        sb.append(cmServiceId);
        sb.append("|");
        sb.append(cmChargeapplicableat);
        sb.append("|");
        sb.append(cmChargeType);
        sb.append("|");
        sb.append(cmSlabDepend);
        sb.append("|");
        sb.append(cmFlatDepend);
        sb.append("|");
        sb.append(cmChargedescriptionEng);
        sb.append("|");
        sb.append(cmChargedescriptionReg);
        sb.append("|");
        sb.append(cmChargeSequence);
        sb.append("|");
        sb.append(cmChargeStartDate);
        sb.append("|");
        sb.append(cmChargeEndDate);
        sb.append("|");
        sb.append(createdDate);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(isdeleted);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        return sb.toString();
    }

    public String getCmChargeStartDateStr() {
        return cmChargeStartDateStr;
    }

    public void setCmChargeStartDateStr(final String cmChargeStartDateStr) {
        this.cmChargeStartDateStr = cmChargeStartDateStr;
    }

    public String getCmChargeEndDateStr() {
        return cmChargeEndDateStr;
    }

    public void setCmChargeEndDateStr(final String cmChargeEndDateStr) {
        this.cmChargeEndDateStr = cmChargeEndDateStr;
    }

}
