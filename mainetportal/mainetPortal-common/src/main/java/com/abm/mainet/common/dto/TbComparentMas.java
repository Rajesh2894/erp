package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

public class TbComparentMas implements Serializable, Comparable<TbComparentMas> {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    // @NotNull
    private Long comId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    // @NotNull
    private Long cpmId;

    // @NotNull
    @Size(min = 1, max = 200)
    private String comDesc;

    // @NotNull
    @Size(min = 1, max = 200)
    private String comValue;

    // @NotNull
    private Long comLevel;

    @Size(max = 1)
    private String comStatus;

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

    @Size(max = 400)
    private String comDescMar;

    @Size(max = 1)
    private String comReplicateFlag;

    @Size(max = 100)
    private String lgIpMac;

    @Size(max = 100)
    private String lgIpMacUpd;

    private TbComparamMas comparamMas;

    private List<TbComparentDet> comparentDetList = new ArrayList<TbComparentDet>();

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setComId(Long comId) {
        this.comId = comId;
    }

    public Long getComId() {
        return this.comId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setCpmId(Long cpmId) {
        this.cpmId = cpmId;
    }

    public Long getCpmId() {
        return this.cpmId;
    }

    public void setComDesc(String comDesc) {
        this.comDesc = comDesc;
    }

    public String getComDesc() {
        return this.comDesc;
    }

    public void setComValue(String comValue) {
        this.comValue = comValue;
    }

    public String getComValue() {
        return this.comValue;
    }

    public void setComLevel(Long comLevel) {
        this.comLevel = comLevel;
    }

    public Long getComLevel() {
        return this.comLevel;
    }

    public void setComStatus(String comStatus) {
        this.comStatus = comStatus;
    }

    public String getComStatus() {
        return this.comStatus;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return this.orgid;
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

    public void setComDescMar(String comDescMar) {
        this.comDescMar = comDescMar;
    }

    public String getComDescMar() {
        return this.comDescMar;
    }

    public void setComReplicateFlag(String comReplicateFlag) {
        this.comReplicateFlag = comReplicateFlag;
    }

    public String getComReplicateFlag() {
        return this.comReplicateFlag;
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

    public List<TbComparentDet> getComparentDetList() {
        return comparentDetList;
    }

    public void setComparentDetList(List<TbComparentDet> comparentDetList) {
        this.comparentDetList = comparentDetList;
    }

    public TbComparamMas getComparamMas() {
        return comparamMas;
    }

    public void setComparamMas(TbComparamMas comparamMas) {
        this.comparamMas = comparamMas;
    }
    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(comId);
        sb.append("|");
        sb.append(cpmId);
        sb.append("|");
        sb.append(comDesc);
        sb.append("|");
        sb.append(comValue);
        sb.append("|");
        sb.append(comLevel);
        sb.append("|");
        sb.append(comStatus);
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
        sb.append(comDescMar);
        sb.append("|");
        sb.append(comReplicateFlag);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        return sb.toString();
    }

    @Override
    public int compareTo(TbComparentMas tbComparentMas) {

        return this.comLevel.compareTo(tbComparentMas.comLevel);
    }

}
