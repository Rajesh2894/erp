package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import org.hibernate.annotations.GenericGenerator;

/**
 * Persistent class for entity stored in table "TB_WT_INST_CSMP"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_WT_INST_CSMP")
public class InstWiseConsmpEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "INST_ID", nullable = false)
    private Long instId;

    @Temporal(TemporalType.DATE)
    @Column(name = "INST_FRMDT")
    private Date instFrmdt;

    @Temporal(TemporalType.DATE)
    @Column(name = "INST_TODT")
    private Date instTodt;

    @Column(name = "INST_TYPE", length = 300)
    private String instType;

    @Column(name = "INST_LIT_PER_DAY")
    private Long instLitPerDay;

    @Column(name = "INST_FLAG", length = 1)
    private String instFlag;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "LANG_ID", nullable = false)
    private Long langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "WT_V1")
    private String wtV1;

    @Column(name = "WT_V2")
    private String wtV2;

    @Column(name = "WT_V3")
    private String wtV3;

    @Column(name = "WT_V4")
    private String wtV4;

    @Column(name = "WT_V5")
    private String wtV5;

    @Column(name = "WT_N1")
    private Long wtN1;

    @Column(name = "WT_N2")
    private Long wtN2;

    @Column(name = "WT_N3")
    private Long wtN3;

    @Column(name = "WT_N4")
    private Long wtN4;

    @Column(name = "WT_N5")
    private Long wtN5;

    @Temporal(TemporalType.DATE)
    @Column(name = "WT_D1")
    private Date wtD1;

    @Temporal(TemporalType.DATE)
    @Column(name = "WT_D2")
    private Date wtD2;

    @Temporal(TemporalType.DATE)
    @Column(name = "WT_D3")
    private Date wtD3;

    @Column(name = "WT_LO1", length = 1)
    private String wtLo1;

    @Column(name = "WT_LO2", length = 1)
    private String wtLo2;

    @Column(name = "WT_LO3", length = 1)
    private String wtLo3;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public InstWiseConsmpEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setInstId(final Long instId) {
        this.instId = instId;
    }

    public Long getInstId() {
        return instId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : INST_FRMDT ( DATE )
    public void setInstFrmdt(final Date instFrmdt) {
        this.instFrmdt = instFrmdt;
    }

    public Date getInstFrmdt() {
        return instFrmdt;
    }

    // --- DATABASE MAPPING : INST_TODT ( DATE )
    public void setInstTodt(final Date instTodt) {
        this.instTodt = instTodt;
    }

    public Date getInstTodt() {
        return instTodt;
    }

    // --- DATABASE MAPPING : INST_TYPE ( VARCHAR2 )
    public void setInstType(final String instType) {
        this.instType = instType;
    }

    public String getInstType() {
        return instType;
    }

    // --- DATABASE MAPPING : INST_LIT_PER_DAY ( NUMBER )
    public void setInstLitPerDay(final Long instLitPerDay) {
        this.instLitPerDay = instLitPerDay;
    }

    public Long getInstLitPerDay() {
        return instLitPerDay;
    }

    // --- DATABASE MAPPING : INST_FLAG ( CHAR )
    public void setInstFlag(final String instFlag) {
        this.instFlag = instFlag;
    }

    public String getInstFlag() {
        return instFlag;
    }

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : LG_IP_MAC ( VARCHAR2 )
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    // --- DATABASE MAPPING : LG_IP_MAC_UPD ( VARCHAR2 )
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    // --- DATABASE MAPPING : WT_V1 ( NVARCHAR2 )
    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
    }

    public String getWtV1() {
        return wtV1;
    }

    // --- DATABASE MAPPING : WT_V2 ( NVARCHAR2 )
    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    public String getWtV2() {
        return wtV2;
    }

    // --- DATABASE MAPPING : WT_V3 ( NVARCHAR2 )
    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    public String getWtV3() {
        return wtV3;
    }

    // --- DATABASE MAPPING : WT_V4 ( NVARCHAR2 )
    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    public String getWtV4() {
        return wtV4;
    }

    // --- DATABASE MAPPING : WT_V5 ( NVARCHAR2 )
    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    public String getWtV5() {
        return wtV5;
    }

    // --- DATABASE MAPPING : WT_N1 ( NUMBER )
    public void setWtN1(final Long wtN1) {
        this.wtN1 = wtN1;
    }

    public Long getWtN1() {
        return wtN1;
    }

    // --- DATABASE MAPPING : WT_N2 ( NUMBER )
    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    public Long getWtN2() {
        return wtN2;
    }

    // --- DATABASE MAPPING : WT_N3 ( NUMBER )
    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    public Long getWtN3() {
        return wtN3;
    }

    // --- DATABASE MAPPING : WT_N4 ( NUMBER )
    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    public Long getWtN4() {
        return wtN4;
    }

    // --- DATABASE MAPPING : WT_N5 ( NUMBER )
    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    public Long getWtN5() {
        return wtN5;
    }

    // --- DATABASE MAPPING : WT_D1 ( DATE )
    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    public Date getWtD1() {
        return wtD1;
    }

    // --- DATABASE MAPPING : WT_D2 ( DATE )
    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    public Date getWtD2() {
        return wtD2;
    }

    // --- DATABASE MAPPING : WT_D3 ( DATE )
    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    public Date getWtD3() {
        return wtD3;
    }

    // --- DATABASE MAPPING : WT_LO1 ( CHAR )
    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    public String getWtLo1() {
        return wtLo1;
    }

    // --- DATABASE MAPPING : WT_LO2 ( CHAR )
    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    public String getWtLo2() {
        return wtLo2;
    }

    // --- DATABASE MAPPING : WT_LO3 ( CHAR )
    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
    }

    public String getWtLo3() {
        return wtLo3;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(instId);
        sb.append("]:");
        sb.append(instFrmdt);
        sb.append("|");
        sb.append(instTodt);
        sb.append("|");
        sb.append(instType);
        sb.append("|");
        sb.append(instLitPerDay);
        sb.append("|");
        sb.append(instFlag);
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
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(wtV1);
        sb.append("|");
        sb.append(wtV2);
        sb.append("|");
        sb.append(wtV3);
        sb.append("|");
        sb.append(wtV4);
        sb.append("|");
        sb.append(wtV5);
        sb.append("|");
        sb.append(wtN1);
        sb.append("|");
        sb.append(wtN2);
        sb.append("|");
        sb.append(wtN3);
        sb.append("|");
        sb.append(wtN4);
        sb.append("|");
        sb.append(wtN5);
        sb.append("|");
        sb.append(wtD1);
        sb.append("|");
        sb.append(wtD2);
        sb.append("|");
        sb.append(wtD3);
        sb.append("|");
        sb.append(wtLo1);
        sb.append("|");
        sb.append(wtLo2);
        sb.append("|");
        sb.append(wtLo3);
        return sb.toString();
    }

}
