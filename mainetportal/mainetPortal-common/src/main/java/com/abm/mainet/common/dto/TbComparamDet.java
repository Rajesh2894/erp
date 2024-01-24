package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

public class TbComparamDet implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    // @NotNull
    private Long cpdId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    // @NotNull
    private Long orgid;

    // @NotNull
    @Size(min = 1, max = 200)
    private String cpdDesc;

    @Size(max = 200)
    private String cpdValue;

    // @NotNull
    @Size(min = 1, max = 1)
    private String cpdStatus = "A";

    // @NotNull
    private Long cpmId;

    // @NotNull
    private Long userId;

    // @NotNull
    private Long langId;

    // @NotNull
    private Date lmoddate;

    @Size(max = 1)
    private String cpdDefault;

    private Long updatedBy;

    private Date updatedDate;

    @Size(max = 270)
    private String cpdDescMar;

    @Size(max = 60)
    private String cpdOthers;

    @Size(max = 100)
    private String lgIpMac;

    @Size(max = 100)
    private String lgIpMacUpd;

    // private String editField;

    private TbComparamMas comparamMas;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setCpdId(Long cpdId) {
        this.cpdId = cpdId;
    }

    public Long getCpdId() {
        return this.cpdId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setCpdDesc(String cpdDesc) {
        this.cpdDesc = cpdDesc;
    }

    public String getCpdDesc() {
        return this.cpdDesc;
    }

    public void setCpdValue(String cpdValue) {
        this.cpdValue = cpdValue;
    }

    public String getCpdValue() {
        return this.cpdValue;
    }

    public void setCpdStatus(String cpdStatus) {
        this.cpdStatus = cpdStatus;
    }

    public String getCpdStatus() {
        return this.cpdStatus;
    }

    public void setCpmId(Long cpmId) {
        this.cpmId = cpmId;
    }

    public Long getCpmId() {
        return this.cpmId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return this.langId;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return this.lmoddate;
    }

    public void setCpdDefault(String cpdDefault) {
        this.cpdDefault = cpdDefault;
    }

    public String getCpdDefault() {
        return this.cpdDefault;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setCpdDescMar(String cpdDescMar) {
        this.cpdDescMar = cpdDescMar;
    }

    public String getCpdDescMar() {
        return this.cpdDescMar;
    }

    public void setCpdOthers(String cpdOthers) {
        this.cpdOthers = cpdOthers;
    }

    public String getCpdOthers() {
        return this.cpdOthers;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    /**
     * @return the comparamMas
     */
    public TbComparamMas getComparamMas() {
        return comparamMas;
    }

    /**
     * @param comparamMas the comparamMas to set
     */
    public void setComparamMas(TbComparamMas comparamMas) {
        this.comparamMas = comparamMas;
    }

    // public String getEditField() {
    //
    // StringBuilder datastring = new StringBuilder();
    //
    // datastring.append("<a href='javascript:void(0);' onclick=\"editInward('" + this.cpdId
    // + "')\">");
    // datastring.append("<img src='css/images/edit.png' width='20px' alt='Edit Inward' title='Edit Inward' />");
    // datastring.append("</a>");
    //
    // return datastring.toString();
    // }
    //
    // public void setEditField(String editField) {
    // this.editField = editField;
    // }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(cpdId);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(cpdDesc);
        sb.append("|");
        sb.append(cpdValue);
        sb.append("|");
        sb.append(cpdStatus);
        sb.append("|");
        sb.append(cpmId);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(cpdDefault);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(cpdDescMar);
        sb.append("|");
        sb.append(cpdOthers);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        return sb.toString();
    }

}
