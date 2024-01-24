package com.abm.mainet.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author ritesh.patil
 *
 */
@Entity
@Table(name = "DESIGNATION_HIST")
public class DesignationHistory {

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_DSGID")
    private Long hdesgId;

    /**
     * @return the hdesgId
     */
    public Long getHdesgId() {
        return hdesgId;
    }

    /**
     * @param hdesgId the hdesgId to set
     */
    public void setHdesgId(final Long hdesgId) {
        this.hdesgId = hdesgId;
    }

    @Column(name = "DSGID")
    private Long dsgid;

    @Column(name = "DSGNAME", nullable = false)
    private String dsgname;

    @Column(name = "DSGSHORTNAME")
    private String dsgshortname;

    @Column(name = "DSGDESCRIPTION")
    private String dsgdescription;

    @Column(name = "ISDELETED", length = 1)
    private String isdeleted;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LANG_ID")
    private Long langId;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "AUT_V1")
    private String autV1;

    @Column(name = "AUT_V2")
    private String autV2;

    @Column(name = "AUT_V3")
    private String autV3;

    @Column(name = "AUT_V4")
    private String autV4;

    @Column(name = "AUT_V5")
    private String autV5;

    @Column(name = "AUT_N1")
    private Long autN1;

    @Column(name = "AUT_N2")
    private Long autN2;

    @Column(name = "AUT_N3")
    private Long autN3;

    @Column(name = "AUT_N4")
    private Long autN4;

    @Column(name = "AUT_N5")
    private Long autN5;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_D1")
    private Date autD1;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_D2")
    private Date autD2;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_D3")
    private Date autD3;

    @Column(name = "AUT_LO1", length = 1)
    private String autLo1;

    @Column(name = "AUT_LO2", length = 1)
    private String autLo2;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_D4")
    private Date autD4;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_D5")
    private Date autD5;

    @Column(name = "DSGNAME_REG")
    private String dsgnameReg;

    @Column(name = "AUT_BY")
    private String autBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_DATE")
    private Date autDate;

    @Column(name = "CENTRALENO")
    private String centraleno;

    @Column(name = "ACTION", length = 1)
    private String action;

    @Column(name = "DSGONAME", length = 50)
    private String dsgoname;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE")
    private Date lmoddate;

    @Column(name = "AUT_STATUS", length = 1)
    private String autStatus;

    @Column(name = "STATUS", length = 1)
    private String status;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------

    @Column(name = "LOCID")
    private Long locId;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setDsgid(final Long dsgid) {
        this.dsgid = dsgid;
    }

    public Long getDsgid() {
        return dsgid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : DSGNAME ( NVARCHAR2 )
    public void setDsgname(final String dsgname) {
        this.dsgname = dsgname;
    }

    public String getDsgname() {
        return dsgname;
    }

    // --- DATABASE MAPPING : DSGSHORTNAME ( NVARCHAR2 )
    public void setDsgshortname(final String dsgshortname) {
        this.dsgshortname = dsgshortname;
    }

    public String getDsgshortname() {
        return dsgshortname;
    }

    // --- DATABASE MAPPING : DSGDESCRIPTION ( NVARCHAR2 )
    public void setDsgdescription(final String dsgdescription) {
        this.dsgdescription = dsgdescription;
    }

    public String getDsgdescription() {
        return dsgdescription;
    }

    // --- DATABASE MAPPING : ISDELETED ( VARCHAR2 )
    public void setIsdeleted(final String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
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

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
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

    // --- DATABASE MAPPING : AUT_V1 ( NVARCHAR2 )
    public void setAutV1(final String autV1) {
        this.autV1 = autV1;
    }

    public String getAutV1() {
        return autV1;
    }

    // --- DATABASE MAPPING : AUT_V2 ( NVARCHAR2 )
    public void setAutV2(final String autV2) {
        this.autV2 = autV2;
    }

    public String getAutV2() {
        return autV2;
    }

    // --- DATABASE MAPPING : AUT_V3 ( NVARCHAR2 )
    public void setAutV3(final String autV3) {
        this.autV3 = autV3;
    }

    public String getAutV3() {
        return autV3;
    }

    // --- DATABASE MAPPING : AUT_V4 ( NVARCHAR2 )
    public void setAutV4(final String autV4) {
        this.autV4 = autV4;
    }

    public String getAutV4() {
        return autV4;
    }

    // --- DATABASE MAPPING : AUT_V5 ( NVARCHAR2 )
    public void setAutV5(final String autV5) {
        this.autV5 = autV5;
    }

    public String getAutV5() {
        return autV5;
    }

    // --- DATABASE MAPPING : AUT_N1 ( NUMBER )
    public void setAutN1(final Long autN1) {
        this.autN1 = autN1;
    }

    public Long getAutN1() {
        return autN1;
    }

    // --- DATABASE MAPPING : AUT_N2 ( NUMBER )
    public void setAutN2(final Long autN2) {
        this.autN2 = autN2;
    }

    public Long getAutN2() {
        return autN2;
    }

    // --- DATABASE MAPPING : AUT_N3 ( NUMBER )
    public void setAutN3(final Long autN3) {
        this.autN3 = autN3;
    }

    public Long getAutN3() {
        return autN3;
    }

    // --- DATABASE MAPPING : AUT_N4 ( NUMBER )
    public void setAutN4(final Long autN4) {
        this.autN4 = autN4;
    }

    public Long getAutN4() {
        return autN4;
    }

    // --- DATABASE MAPPING : AUT_N5 ( NUMBER )
    public void setAutN5(final Long autN5) {
        this.autN5 = autN5;
    }

    public Long getAutN5() {
        return autN5;
    }

    // --- DATABASE MAPPING : AUT_D1 ( DATE )
    public void setAutD1(final Date autD1) {
        this.autD1 = autD1;
    }

    public Date getAutD1() {
        return autD1;
    }

    // --- DATABASE MAPPING : AUT_D2 ( DATE )
    public void setAutD2(final Date autD2) {
        this.autD2 = autD2;
    }

    public Date getAutD2() {
        return autD2;
    }

    // --- DATABASE MAPPING : AUT_D3 ( DATE )
    public void setAutD3(final Date autD3) {
        this.autD3 = autD3;
    }

    public Date getAutD3() {
        return autD3;
    }

    // --- DATABASE MAPPING : AUT_LO1 ( CHAR )
    public void setAutLo1(final String autLo1) {
        this.autLo1 = autLo1;
    }

    public String getAutLo1() {
        return autLo1;
    }

    // --- DATABASE MAPPING : AUT_LO2 ( CHAR )
    public void setAutLo2(final String autLo2) {
        this.autLo2 = autLo2;
    }

    public String getAutLo2() {
        return autLo2;
    }

    // --- DATABASE MAPPING : AUT_D4 ( DATE )
    public void setAutD4(final Date autD4) {
        this.autD4 = autD4;
    }

    public Date getAutD4() {
        return autD4;
    }

    // --- DATABASE MAPPING : AUT_D5 ( DATE )
    public void setAutD5(final Date autD5) {
        this.autD5 = autD5;
    }

    public Date getAutD5() {
        return autD5;
    }

    // --- DATABASE MAPPING : DSGNAME_REG ( NVARCHAR2 )
    public void setDsgnameReg(final String dsgnameReg) {
        this.dsgnameReg = dsgnameReg;
    }

    public String getDsgnameReg() {
        return dsgnameReg;
    }

    // --- DATABASE MAPPING : AUT_BY ( NVARCHAR2 )
    public void setAutBy(final String autBy) {
        this.autBy = autBy;
    }

    public String getAutBy() {
        return autBy;
    }

    // --- DATABASE MAPPING : AUT_DATE ( DATE )
    public void setAutDate(final Date autDate) {
        this.autDate = autDate;
    }

    public Date getAutDate() {
        return autDate;
    }

    // --- DATABASE MAPPING : CENTRALENO ( NVARCHAR2 )
    public void setCentraleno(final String centraleno) {
        this.centraleno = centraleno;
    }

    public String getCentraleno() {
        return centraleno;
    }

    // --- DATABASE MAPPING : ACTION ( VARCHAR2 )
    public void setAction(final String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    // --- DATABASE MAPPING : DSGONAME ( VARCHAR2 )
    public void setDsgoname(final String dsgoname) {
        this.dsgoname = dsgoname;
    }

    public String getDsgoname() {
        return dsgoname;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    // --- DATABASE MAPPING : AUT_STATUS ( CHAR )
    public void setAutStatus(final String autStatus) {
        this.autStatus = autStatus;
    }

    public String getAutStatus() {
        return autStatus;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(dsgid);
        sb.append("]:");
        sb.append(dsgname);
        sb.append("|");
        sb.append(dsgshortname);
        sb.append("|");
        sb.append(dsgdescription);
        sb.append("|");
        sb.append(isdeleted);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(autV1);
        sb.append("|");
        sb.append(autV2);
        sb.append("|");
        sb.append(autV3);
        sb.append("|");
        sb.append(autV4);
        sb.append("|");
        sb.append(autV5);
        sb.append("|");
        sb.append(autN1);
        sb.append("|");
        sb.append(autN2);
        sb.append("|");
        sb.append(autN3);
        sb.append("|");
        sb.append(autN4);
        sb.append("|");
        sb.append(autN5);
        sb.append("|");
        sb.append(autD1);
        sb.append("|");
        sb.append(autD2);
        sb.append("|");
        sb.append(autD3);
        sb.append("|");
        sb.append(autLo1);
        sb.append("|");
        sb.append(autLo2);
        sb.append("|");
        sb.append(autD4);
        sb.append("|");
        sb.append(autD5);
        sb.append("|");
        sb.append(dsgnameReg);
        sb.append("|");
        sb.append(autBy);
        sb.append("|");
        sb.append(autDate);
        sb.append("|");
        sb.append(centraleno);
        sb.append("|");
        sb.append(action);
        sb.append("|");
        sb.append(dsgoname);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(autStatus);
        return sb.toString();
    }

    /**
     * @return the locId
     */
    public Long getLocId() {
        return locId;
    }

    /**
     * @param locId the locId to set
     */
    public void setLocId(final Long locId) {
        this.locId = locId;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "DESIGNATION_HIST", "H_DSGID" };
    }

}
