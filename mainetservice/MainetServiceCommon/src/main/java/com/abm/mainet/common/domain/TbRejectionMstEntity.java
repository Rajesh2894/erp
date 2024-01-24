/*
 * Created on 5 Apr 2016 ( Time 11:55:07 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a composite Primary Key

package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Persistent class for entity stored in table "TB_REJECTION_MST"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_REJECTION_MST")
// Define named queries here
@NamedQueries({
        @NamedQuery(name = "TbRejectionMstEntity.countAll", query = "SELECT COUNT(x) FROM TbRejectionMstEntity x")
})
public class TbRejectionMstEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( EMBEDDED IN AN EXTERNAL CLASS )
    // ----------------------------------------------------------------------
    @EmbeddedId
    private final TbRejectionMstEntityKey compositePrimaryKey;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "REJ_REF_ID")
    private Long rejRefId;

    @Column(name = "REJ_APPLICATION_ID")
    private Long rejApplicationId;

    @Column(name = "REJ_SERVICE_ID")
    private Long rejServiceId;

    @Column(name = "REJ_SCHEDULE_REASON", length = 200)
    private String rejScheduleReason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REJ_LETTER_DATE")
    private Date rejLetterDate;

    @Column(name = "REJ_TYPE")
    private Long rejType;

    @Column(name = "REJ_REMARKS")
    private String rejRemarks;

    @Column(name = "REJ_SCHEDULE", length = 1)
    private String rejSchedule;

    @Column(name = "REJ_LETTER_NO")
    private String rejLetterNo;

    @Column(name = "REJ_STATUS", length = 1)
    private String rejStatus;

    @Column(name = "USERID")
    private Long userid;

    @Column(name = "LANGID")
    private Long langid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LMODDATE")
    private Date lmoddate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbRejectionMstEntity() {
        super();
        compositePrimaryKey = new TbRejectionMstEntityKey();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE COMPOSITE KEY
    // ----------------------------------------------------------------------
    public void setRejId(final Long rejId) {
        compositePrimaryKey.setRejId(rejId);
    }

    public Long getRejId() {
        return compositePrimaryKey.getRejId();
    }

    public void setOrgid(final Long orgid) {
        compositePrimaryKey.setOrgid(orgid);
    }

    public Long getOrgid() {
        return compositePrimaryKey.getOrgid();
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : REJ_REF_ID ( NUMBER )
    public void setRejRefId(final Long rejRefId) {
        this.rejRefId = rejRefId;
    }

    public Long getRejRefId() {
        return rejRefId;
    }

    // --- DATABASE MAPPING : REJ_APPLICATION_ID ( NUMBER )
    public void setRejApplicationId(final Long rejApplicationId) {
        this.rejApplicationId = rejApplicationId;
    }

    public Long getRejApplicationId() {
        return rejApplicationId;
    }

    // --- DATABASE MAPPING : REJ_SERVICE_ID ( NUMBER )
    public void setRejServiceId(final Long rejServiceId) {
        this.rejServiceId = rejServiceId;
    }

    public Long getRejServiceId() {
        return rejServiceId;
    }

    // --- DATABASE MAPPING : REJ_SCHEDULE_REASON ( VARCHAR2 )
    public void setRejScheduleReason(final String rejScheduleReason) {
        this.rejScheduleReason = rejScheduleReason;
    }

    public String getRejScheduleReason() {
        return rejScheduleReason;
    }

    // --- DATABASE MAPPING : REJ_LETTER_DATE ( DATE )
    public void setRejLetterDate(final Date rejLetterDate) {
        this.rejLetterDate = rejLetterDate;
    }

    public Date getRejLetterDate() {
        return rejLetterDate;
    }

    // --- DATABASE MAPPING : REJ_TYPE ( NUMBER )
    public void setRejType(final Long rejType) {
        this.rejType = rejType;
    }

    public Long getRejType() {
        return rejType;
    }

    // --- DATABASE MAPPING : REJ_REMARKS ( NVARCHAR2 )
    public void setRejRemarks(final String rejRemarks) {
        this.rejRemarks = rejRemarks;
    }

    public String getRejRemarks() {
        return rejRemarks;
    }

    // --- DATABASE MAPPING : REJ_SCHEDULE ( CHAR )
    public void setRejSchedule(final String rejSchedule) {
        this.rejSchedule = rejSchedule;
    }

    public String getRejSchedule() {
        return rejSchedule;
    }

    // --- DATABASE MAPPING : REJ_LETTER_NO ( NVARCHAR2 )
    public void setRejLetterNo(final String rejLetterNo) {
        this.rejLetterNo = rejLetterNo;
    }

    public String getRejLetterNo() {
        return rejLetterNo;
    }

    // --- DATABASE MAPPING : REJ_STATUS ( CHAR )
    public void setRejStatus(final String rejStatus) {
        this.rejStatus = rejStatus;
    }

    public String getRejStatus() {
        return rejStatus;
    }

    // --- DATABASE MAPPING : USERID ( NUMBER )
    public void setUserid(final Long userid) {
        this.userid = userid;
    }

    public Long getUserid() {
        return userid;
    }

    // --- DATABASE MAPPING : LANGID ( NUMBER )
    public void setLangid(final Long langid) {
        this.langid = langid;
    }

    public Long getLangid() {
        return langid;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
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

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        if (compositePrimaryKey != null) {
            sb.append(compositePrimaryKey.toString());
        } else {
            sb.append("(null-key)");
        }
        sb.append("]:");
        sb.append(rejRefId);
        sb.append("|");
        sb.append(rejApplicationId);
        sb.append("|");
        sb.append(rejServiceId);
        sb.append("|");
        sb.append(rejScheduleReason);
        sb.append("|");
        sb.append(rejLetterDate);
        sb.append("|");
        sb.append(rejType);
        sb.append("|");
        sb.append(rejRemarks);
        sb.append("|");
        sb.append(rejSchedule);
        sb.append("|");
        sb.append(rejLetterNo);
        sb.append("|");
        sb.append(rejStatus);
        sb.append("|");
        sb.append(userid);
        sb.append("|");
        sb.append(langid);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        return sb.toString();
    }

}
