/*
 * Created on 9 Dec 2015 ( Time 12:11:59 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LocRevenueWZMappingDto implements Serializable {

    private static final long serialVersionUID = -8915969200923276281L;
    private Long locrwzmpId;
    private Long orgId;
    private Long locId;
    private Long codIdRevLevel1;
    private Long codIdRevLevel2;
    private Long codIdRevLevel3;
    private Long codIdRevLevel4;
    private Long codIdRevLevel5;
    private Long userId;
    private int langId;
    private Date lmodDate;
    private Long updatedBy;
    private Date updatedDate;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMac;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMacUpd;
    private boolean revenueChkBox;

    public Long getLocrwzmpId() {
        return locrwzmpId;
    }

    public void setLocrwzmpId(final Long locrwzmpId) {
        this.locrwzmpId = locrwzmpId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(final Long locId) {
        this.locId = locId;
    }

    public Long getCodIdRevLevel1() {
        return codIdRevLevel1;
    }

    public void setCodIdRevLevel1(final Long codIdRevLevel1) {
        this.codIdRevLevel1 = codIdRevLevel1;
    }

    public Long getCodIdRevLevel2() {
        return codIdRevLevel2;
    }

    public void setCodIdRevLevel2(final Long codIdRevLevel2) {
        this.codIdRevLevel2 = codIdRevLevel2;
    }

    public Long getCodIdRevLevel3() {
        return codIdRevLevel3;
    }

    public void setCodIdRevLevel3(final Long codIdRevLevel3) {
        this.codIdRevLevel3 = codIdRevLevel3;
    }

    public Long getCodIdRevLevel4() {
        return codIdRevLevel4;
    }

    public void setCodIdRevLevel4(final Long codIdRevLevel4) {
        this.codIdRevLevel4 = codIdRevLevel4;
    }

    public Long getCodIdRevLevel5() {
        return codIdRevLevel5;
    }

    public void setCodIdRevLevel5(final Long codIdRevLevel5) {
        this.codIdRevLevel5 = codIdRevLevel5;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public boolean isRevenueChkBox() {
        return revenueChkBox;
    }

    public void setRevenueChkBox(final boolean revenueChkBox) {
        this.revenueChkBox = revenueChkBox;
    }

}
