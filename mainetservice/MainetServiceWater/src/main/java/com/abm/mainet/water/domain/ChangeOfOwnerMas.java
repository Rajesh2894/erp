package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Rahul.Yadav
 * @since 09 Mar 2016
 */
@Entity
@Table(name = "TB_WT_CHANGE_OF_OWNERSHIP")
public class ChangeOfOwnerMas implements Serializable {
    private static final long serialVersionUID = -820886287711309235L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CH_IDN", precision = 12, scale = 0, nullable = false)
    private long chIdn;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = true)
    private Long csIdn;

    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
    private Long apmApplicationId;

    @Column(name = "CH_APLDATE", nullable = true)
    private Date chAPLDate;

    @Column(name = "CH_NEW_TITLE", precision = 12, scale = 0, nullable = true)
    private Long chNewTitle;

    @Column(name = "CH_NEW_NAME", length = 100, nullable = true)
    private String chNewName;

    @Column(name = "CH_NEW_MNAME", length = 100, nullable = true)
    private String chNewMName;

    @Column(name = "CH_NEW_LNAME", length = 100, nullable = true)
    private String chNewLname;

    @Column(name = "CH_NEW_GENDER", length = 10, nullable = true)
    private Long chNewGender;

    @Column(name = "CH_NEW_UID_NO", length = 12, nullable = true)
    private Long chNewUIDNo;

    @Column(name = "CH_NEW_ORGNAME", length = 100, nullable = true)
    private String chNewOrgName;

    @Column(name = "CH_OLD_TITLE", precision = 12, scale = 0, nullable = true)
    private Long chOldTitle;

    @Column(name = "CH_OLD_NAME", length = 150, nullable = true)
    private String chOldName;

    @Column(name = "CH_OLD_MNAME", length = 100, nullable = true)
    private String chOldMnNme;

    @Column(name = "CH_OLD_LNAME", length = 100, nullable = true)
    private String chOldLName;

    @Column(name = "CH_OLD_UID_NO", precision = 12, scale = 0, nullable = true)
    private Long chOldUIDNo;

    @Column(name = "CH_OLD_GENDER", length = 10, nullable = true)
    private Long chOldGender;

    @Column(name = "CH_OLD_ORGNAME", length = 100, nullable = true)
    private String chOldOrgName;

    @Column(name = "CH_REMARK", length = 200, nullable = true)
    private String chRemark;

    @Column(name = "CH_GRANTED", length = 1, nullable = true)
    private String chGranted;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private Long langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "WT_V1", length = 100, nullable = true)
    private String wtV1;

    @Column(name = "WT_V2", length = 100, nullable = true)
    private String wtV2;

    @Column(name = "WT_V3", length = 100, nullable = true)
    private String wtV3;

    @Column(name = "WT_V4", length = 100, nullable = true)
    private String wtV4;

    @Column(name = "WT_V5", length = 100, nullable = true)
    private String wtV5;

    @Column(name = "WT_N1", precision = 15, scale = 0, nullable = true)
    private Long wtN1;

    @Column(name = "WT_N2", precision = 15, scale = 0, nullable = true)
    private Long wtN2;

    @Column(name = "WT_N3", precision = 15, scale = 0, nullable = true)
    private Long wtN3;

    @Column(name = "WT_N4", precision = 15, scale = 0, nullable = true)
    private Long wtN4;

    @Column(name = "WT_N5", precision = 15, scale = 0, nullable = true)
    private Long wtN5;

    @Column(name = "WT_D1", nullable = true)
    private Date wtD1;

    @Column(name = "WT_D2", nullable = true)
    private Date wtD2;

    @Column(name = "WT_D3", nullable = true)
    private Date wtD3;

    @Column(name = "WT_LO1", length = 1, nullable = true)
    private String wtLo1;

    @Column(name = "WT_LO2", length = 1, nullable = true)
    private String wtLo2;

    @Column(name = "WT_LO3", length = 1, nullable = true)
    private String wtLo3;

    @Column(name = "CH_TRANSFER_MODE", precision = 12, scale = 0, nullable = true)
    private Long ownerTransferMode;

    public long getChIdn() {
        return chIdn;
    }

    public void setChIdn(final long chIdn) {
        this.chIdn = chIdn;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Date getChAPLDate() {
        return chAPLDate;
    }

    public void setChAPLDate(final Date chAPLDate) {
        this.chAPLDate = chAPLDate;
    }

    public Long getChNewTitle() {
        return chNewTitle;
    }

    public void setChNewTitle(final Long chNewTitle) {
        this.chNewTitle = chNewTitle;
    }

    public String getChNewName() {
        return chNewName;
    }

    public void setChNewName(final String chNewName) {
        this.chNewName = chNewName;
    }

    public String getChNewMName() {
        return chNewMName;
    }

    public void setChNewMName(final String chNewMName) {
        this.chNewMName = chNewMName;
    }

    public String getChNewLname() {
        return chNewLname;
    }

    public void setChNewLname(final String chNewLname) {
        this.chNewLname = chNewLname;
    }

    public Long getChNewGender() {
        return chNewGender;
    }

    public void setChNewGender(final Long chNewGender) {
        this.chNewGender = chNewGender;
    }

    public Long getChNewUIDNo() {
        return chNewUIDNo;
    }

    public void setChNewUIDNo(final Long chNewUIDNo) {
        this.chNewUIDNo = chNewUIDNo;
    }

    public String getChNewOrgName() {
        return chNewOrgName;
    }

    public void setChNewOrgName(final String chNewOrgName) {
        this.chNewOrgName = chNewOrgName;
    }

    public Long getChOldTitle() {
        return chOldTitle;
    }

    public void setChOldTitle(final Long chOldTitle) {
        this.chOldTitle = chOldTitle;
    }

    public String getChOldName() {
        return chOldName;
    }

    public void setChOldName(final String chOldName) {
        this.chOldName = chOldName;
    }

    public String getChOldMnNme() {
        return chOldMnNme;
    }

    public void setChOldMnNme(final String chOldMnNme) {
        this.chOldMnNme = chOldMnNme;
    }

    public String getChOldLName() {
        return chOldLName;
    }

    public void setChOldLName(final String chOldLName) {
        this.chOldLName = chOldLName;
    }

    public Long getChOldUIDNo() {
        return chOldUIDNo;
    }

    public void setChOldUIDNo(final Long chOldUIDNo) {
        this.chOldUIDNo = chOldUIDNo;
    }

    public Long getChOldGender() {
        return chOldGender;
    }

    public void setChOldGender(final Long chOldGender) {
        this.chOldGender = chOldGender;
    }

    public String getChOldOrgName() {
        return chOldOrgName;
    }

    public void setChOldOrgName(final String chOldOrgName) {
        this.chOldOrgName = chOldOrgName;
    }

    public String getChRemark() {
        return chRemark;
    }

    public void setChRemark(final String chRemark) {
        this.chRemark = chRemark;
    }

    public String getChGranted() {
        return chGranted;
    }

    public void setChGranted(final String chGranted) {
        this.chGranted = chGranted;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
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

    public String getWtV1() {
        return wtV1;
    }

    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
    }

    public String getWtV2() {
        return wtV2;
    }

    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    public String getWtV3() {
        return wtV3;
    }

    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    public String getWtV4() {
        return wtV4;
    }

    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    public String getWtV5() {
        return wtV5;
    }

    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    public Long getWtN1() {
        return wtN1;
    }

    public void setWtN1(final Long wtN1) {
        this.wtN1 = wtN1;
    }

    public Long getWtN2() {
        return wtN2;
    }

    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    public Long getWtN3() {
        return wtN3;
    }

    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    public Long getWtN4() {
        return wtN4;
    }

    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    public Long getWtN5() {
        return wtN5;
    }

    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    public Date getWtD1() {
        return wtD1;
    }

    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    public Date getWtD2() {
        return wtD2;
    }

    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    public Date getWtD3() {
        return wtD3;
    }

    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    public String getWtLo1() {
        return wtLo1;
    }

    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    public String getWtLo2() {
        return wtLo2;
    }

    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    public String getWtLo3() {
        return wtLo3;
    }

    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
    }

    public Long getOwnerTransferMode() {
        return ownerTransferMode;
    }

    public void setOwnerTransferMode(final Long ownerTransferMode) {
        this.ownerTransferMode = ownerTransferMode;
    }

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_CHANGE_OF_OWNERSHIP", "CH_IDN" };
    }

}