/*
 * Created on 23 Jul 2015 ( Time 11:51:35 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TbCustbanks implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    // @NotNull
    private Long cbBankid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    // @NotNull
    private Long cbBankcode;

    // @NotNull
    @Size(min = 1, max = 500)
    private String cbBankname;

    // @NotNull
    private Long userId;

    // @NotNull
    private Long langId;

    // @NotNull
    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    @Size(max = 500)
    private String cbBranchname;

    @Size(max = 100)
    private String cbCity;

    @Size(max = 250)
    private String cbAddress;

    private Long cmBankid;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    @Size(max = 1)
    private String pgFlag = "N";

    private String formMode;

    private TbCustbanksMas tbCustbanksMas;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setCbBankid(final Long cbBankid) {
        this.cbBankid = cbBankid;
    }

    public Long getCbBankid() {
        return cbBankid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setCbBankcode(final Long cbBankcode) {
        this.cbBankcode = cbBankcode;
    }

    public Long getCbBankcode() {
        return cbBankcode;
    }

    public void setCbBankname(final String cbBankname) {
        this.cbBankname = cbBankname;
    }

    public String getCbBankname() {
        return cbBankname;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

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

    public void setCbBranchname(final String cbBranchname) {
        this.cbBranchname = cbBranchname;
    }

    public String getCbBranchname() {
        return cbBranchname;
    }

    public void setCbCity(final String cbCity) {
        this.cbCity = cbCity;
    }

    public String getCbCity() {
        return cbCity;
    }

    public void setCbAddress(final String cbAddress) {
        this.cbAddress = cbAddress;
    }

    public String getCbAddress() {
        return cbAddress;
    }

    public void setCmBankid(final Long cmBankid) {
        this.cmBankid = cmBankid;
    }

    public Long getCmBankid() {
        return cmBankid;
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

    public void setPgFlag(final String pgFlag) {
        this.pgFlag = pgFlag;
    }

    public String getPgFlag() {
        return pgFlag;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    /**
     * @return the formMode
     */
    public String getFormMode() {
        return formMode;
    }

    /**
     * @param formMode the formMode to set
     */
    public void setFormMode(final String formMode) {
        this.formMode = formMode;
    }

    public TbCustbanksMas getTbCustbanksMas() {
        return tbCustbanksMas;
    }

    public void setTbCustbanksMas(final TbCustbanksMas tbCustbanksMas) {
        this.tbCustbanksMas = tbCustbanksMas;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(cbBankid);
        sb.append("|");
        sb.append(cbBankcode);
        sb.append("|");
        sb.append(cbBankname);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(cbBranchname);
        sb.append("|");
        sb.append(cbCity);
        sb.append("|");
        sb.append(cbAddress);
        sb.append("|");
        sb.append(cmBankid);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(pgFlag);
        return sb.toString();
    }

}
