/*
 * Created on 6 Aug 2015 ( Time 16:35:10 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TbComparentDet implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    // @NotNull
    private Long codId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    // @NotNull
    private Long comId;

    // @NotNull
    @Size(min = 1, max = 400)
    private String codDesc;

    // @NotNull
    @Size(min = 1, max = 400)
    private String codValue;

    private Long parentId;

    // @NotNull
    private Long orgid;

    // @NotNull
    private Long userId;

    // @NotNull
    private Long langId;

    // @NotNull
    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    @Size(max = 1)
    private String cpdDefault;

    @Size(max = 1)
    private String codStatus = "Y";

    @Size(max = 400)
    private String codDescMar;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;
    
    @Size(max = 60)
    private String codOthers;
    
    private boolean chkBox;

    private long level;

    private long tempId;

    private long tempParentId;

    private TbComparentDet tbComparentDet;

    private TbComparentMas tbComparentMas;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setCodId(final Long codId) {
        this.codId = codId;
    }

    public Long getCodId() {
        return codId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setComId(final Long comId) {
        this.comId = comId;
    }

    public Long getComId() {
        return comId;
    }

    public void setCodDesc(final String codDesc) {
        this.codDesc = codDesc;
    }

    public String getCodDesc() {
        return codDesc;
    }

    public void setCodValue(final String codValue) {
        this.codValue = codValue;
    }

    public String getCodValue() {
        return codValue;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
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

    public void setCpdDefault(final String cpdDefault) {
        this.cpdDefault = cpdDefault;
    }

    public String getCpdDefault() {
        return cpdDefault;
    }

    public void setCodStatus(final String codStatus) {
        this.codStatus = codStatus;
    }

    public String getCodStatus() {
        return codStatus;
    }

    public void setCodDescMar(final String codDescMar) {
        this.codDescMar = codDescMar;
    }

    public String getCodDescMar() {
        return codDescMar;
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

    public long getLevel() {
        return level;
    }

    public void setLevel(final long level) {
        this.level = level;
    }

    public long getTempId() {
        return tempId;
    }

    public void setTempId(final long tempId) {
        this.tempId = tempId;
    }

    public long getTempParentId() {
        return tempParentId;
    }

    public void setTempParentId(final long tempParentId) {
        this.tempParentId = tempParentId;
    }

    public TbComparentMas getTbComparentMas() {
        return tbComparentMas;
    }

    public void setTbComparentMas(final TbComparentMas tbComparentMas) {
        this.tbComparentMas = tbComparentMas;
    }

    public TbComparentDet getTbComparentDet() {
        return tbComparentDet;
    }

    public void setTbComparentDet(final TbComparentDet tbComparentDet) {
        this.tbComparentDet = tbComparentDet;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    public String getCodOthers() {
		return codOthers;
	}

	public void setCodOthers(String codOthers) {
		this.codOthers = codOthers;
	}


	public boolean isChkBox() {
		return chkBox;
	}

	public void setChkBox(boolean chkBox) {
		this.chkBox = chkBox;
	}

	@Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(codId);
        sb.append("|");
        sb.append(comId);
        sb.append("|");
        sb.append(codDesc);
        sb.append("|");
        sb.append(codValue);
        sb.append("|");
        sb.append(parentId);
        sb.append("|");
        sb.append(orgid);
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
        sb.append(cpdDefault);
        sb.append("|");
        sb.append(codStatus);
        sb.append("|");
        sb.append(codDescMar);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        return sb.toString();
    }
}
