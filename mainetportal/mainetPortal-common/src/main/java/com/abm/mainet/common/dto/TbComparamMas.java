package com.abm.mainet.common.dto;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

public class TbComparamMas {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    // @NotNull
    private Long cpmId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    // @NotNull
    @Size(min = 1, max = 3)
    private String cpmPrefix;

    // @NotNull
    @Size(min = 1, max = 200)
    private String cpmDesc;

    // @NotNull
    @Size(min = 1, max = 1)
    private String cpmStatus = "I";

    // @NotNull
    private Long userId;

    // @NotNull
    private Long langId;

    // @NotNull
    private Date lmoddate;

    @Size(max = 1)
    private String cpmLimitedYn;

    @Size(max = 10)
    private String cpmModuleName;

    private Long updatedBy;

    private Date updatedDate;

    @Size(max = 1)
    private String cpmConfig;

    @Size(max = 50)
    private String cpmEdit;

    @Size(max = 100)
    private String lgIpMac;

    @Size(max = 100)
    private String lgIpMacUpd;

    @Size(max = 1)
    private String cpmReplicateFlag;

    @Size(max = 1)
    private String cpmType;

    @Size(max = 1)
    private String loadAtStartup;

    private String lmodDateStr;

    @Size(max = 1)
    private String cpmEditDesc;

    @Size(max = 1)
    private String cpmEditValue;

    @Size(max = 1)
    private String cpmEditOth;

    @Size(max = 1)
    private String cpmEditDefault;

    @Size(max = 1)
    private String cpmEditStatus;

    private String cpmTypeValue;

    private List<TbComparamDet> comparamDetList = new ArrayList<TbComparamDet>();

    private List<TbComparentMas> comparentMas = new ArrayList<TbComparentMas>();

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setCpmId(Long cpmId) {
        this.cpmId = cpmId;
    }

    public Long getCpmId() {
        return this.cpmId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setCpmPrefix(String cpmPrefix) {
        this.cpmPrefix = cpmPrefix;
    }

    public String getCpmPrefix() {
        return this.cpmPrefix;
    }

    public void setCpmDesc(String cpmDesc) {
        this.cpmDesc = cpmDesc;
    }

    public String getCpmDesc() {
        return this.cpmDesc;
    }

    public void setCpmStatus(String cpmStatus) {
        this.cpmStatus = cpmStatus;
    }

    public String getCpmStatus() {
        return this.cpmStatus;
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

    public void setCpmLimitedYn(String cpmLimitedYn) {
        this.cpmLimitedYn = cpmLimitedYn;
    }

    public String getCpmLimitedYn() {
        return this.cpmLimitedYn;
    }

    public void setCpmModuleName(String cpmModuleName) {
        this.cpmModuleName = cpmModuleName;
    }

    public String getCpmModuleName() {
        return this.cpmModuleName;
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

    public void setCpmConfig(String cpmConfig) {
        this.cpmConfig = cpmConfig;
    }

    public String getCpmConfig() {
        return this.cpmConfig;
    }

    public void setCpmEdit(String cpmEdit) {
        this.cpmEdit = cpmEdit;
    }

    public String getCpmEdit() {
        return this.cpmEdit;
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

    public void setCpmReplicateFlag(String cpmReplicateFlag) {
        this.cpmReplicateFlag = cpmReplicateFlag;
    }

    public String getCpmReplicateFlag() {
        return this.cpmReplicateFlag;
    }

    public void setCpmType(String cpmType) {
        this.cpmType = cpmType;
    }

    public String getCpmType() {
        return this.cpmType;
    }

    public void setLoadAtStartup(String loadAtStartup) {
        this.loadAtStartup = loadAtStartup;
    }

    public String getLoadAtStartup() {
        return this.loadAtStartup;
    }

    public String getCpmEditDesc() {
        return cpmEditDesc;
    }

    public void setCpmEditDesc(String cpmEditDesc) {
        this.cpmEditDesc = cpmEditDesc;
    }

    public String getCpmEditValue() {
        return cpmEditValue;
    }

    public void setCpmEditValue(String cpmEditValue) {
        this.cpmEditValue = cpmEditValue;
    }

    public String getCpmEditOth() {
        return cpmEditOth;
    }

    public void setCpmEditOth(String cpmEditOth) {
        this.cpmEditOth = cpmEditOth;
    }

    public String getCpmEditDefault() {
        return cpmEditDefault;
    }

    public void setCpmEditDefault(String cpmEditDefault) {
        this.cpmEditDefault = cpmEditDefault;
    }

    public String getCpmEditStatus() {
        return cpmEditStatus;
    }

    public void setCpmEditStatus(String cpmEditStatus) {
        this.cpmEditStatus = cpmEditStatus;
    }

    public String getLmodDateStr() {

        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        lmodDateStr = formatter.format(lmoddate);

        return lmodDateStr;
    }

    public void setLmodDateStr(String lmodDateStr) {
        this.lmodDateStr = lmodDateStr;
    }

    /**
     * @return the cpmTypeValue
     */
    public String getCpmTypeValue() {
        return cpmTypeValue;
    }

    /**
     * @param cpmTypeValue the cpmTypeValue to set
     */
    public void setCpmTypeValue(String cpmTypeValue) {
        this.cpmTypeValue = cpmTypeValue;
    }

    /**
     * @return the comparamDetList
     */
    public List<TbComparamDet> getComparamDetList() {
        return comparamDetList;
    }

    /**
     * @param comparamDetList the comparamDetList to set
     */
    public void setComparamDetList(List<TbComparamDet> comparamDetList) {
        this.comparamDetList = comparamDetList;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    /**
     * @return the comparentMas
     */
    public List<TbComparentMas> getComparentMas() {
        return comparentMas;
    }

    /**
     * @param comparentMas the comparentMas to set
     */
    public void setComparentMas(List<TbComparentMas> comparentMas) {
        this.comparentMas = comparentMas;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(cpmId);
        sb.append("|");
        sb.append(cpmPrefix);
        sb.append("|");
        sb.append(cpmDesc);
        sb.append("|");
        sb.append(cpmStatus);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(cpmLimitedYn);
        sb.append("|");
        sb.append(cpmModuleName);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(cpmConfig);
        sb.append("|");
        sb.append(cpmEdit);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(cpmReplicateFlag);
        sb.append("|");
        sb.append(cpmType);
        sb.append("|");
        sb.append(loadAtStartup);
        sb.append("|");
        sb.append(cpmEditDesc);
        sb.append("|");
        sb.append(cpmEditValue);
        sb.append("|");
        sb.append(cpmEditOth);
        sb.append("|");
        sb.append(cpmEditDefault);
        sb.append("|");
        sb.append(cpmEditStatus);
        return sb.toString();
    }

}
