package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dpDeptid;
    private String dpDeptcode;
    private String dpDeptdesc;
    private Integer userId;
    private Short langId;
    private Date lmoddate;
    private String status;
    private String dpNameMar;
    private String subDeptFlg;
    private Integer updatedBy;
    private Date updatedDate;
    private Long dpSmfid;
    private String dpCheck;
    private String dpPrefix;
    private String lgIpMac;
    private String lgIpMacUpd;

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public String getDpDeptcode() {
        return dpDeptcode;
    }

    public void setDpDeptcode(final String dpDeptcode) {
        this.dpDeptcode = dpDeptcode;
    }

    public String getDpDeptdesc() {
        return dpDeptdesc;
    }

    public void setDpDeptdesc(final String dpDeptdesc) {
        this.dpDeptdesc = dpDeptdesc;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public Short getLangId() {
        return langId;
    }

    public void setLangId(final Short langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getDpNameMar() {
        return dpNameMar;
    }

    public void setDpNameMar(final String dpNameMar) {
        this.dpNameMar = dpNameMar;
    }

    public String getSubDeptFlg() {
        return subDeptFlg;
    }

    public void setSubDeptFlg(final String subDeptFlg) {
        this.subDeptFlg = subDeptFlg;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getDpSmfid() {
        return dpSmfid;
    }

    public void setDpSmfid(final Long dpSmfid) {
        this.dpSmfid = dpSmfid;
    }

    public String getDpCheck() {
        return dpCheck;
    }

    public void setDpCheck(final String dpCheck) {
        this.dpCheck = dpCheck;
    }

    public String getDpPrefix() {
        return dpPrefix;
    }

    public void setDpPrefix(final String dpPrefix) {
        this.dpPrefix = dpPrefix;
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

}
