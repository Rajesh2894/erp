/*
 * Created on 19 Aug 2015 ( Time 17:09:55 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a composite Primary Key

package com.abm.mainet.cfc.checklist.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;

/**
 * Persistent class for entity stored in table "TB_CFC_REJECTION"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_CFC_REJECTION")
// Define named queries here
@NamedQueries({
        @NamedQuery(name = "TbCfcRejectionEntity.countAll", query = "SELECT COUNT(x) FROM TbCfcRejectionEntity x")
})
public class TbCfcRejectionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( EMBEDDED IN AN EXTERNAL CLASS )
    // ----------------------------------------------------------------------
    @EmbeddedId
    private final TbCfcRejectionEntityKey compositePrimaryKey;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "LANG_ID", nullable = false)
    private Long langId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LMODDATE", nullable = false)
    private Date lmoddate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CFC_REJ_TYPE")
    private String cfcRejType;

    @Column(name = "CLM_GROUP_ID")
    private Long clmGroupId;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "CLM_ID", referencedColumnName = "CLM_ID", insertable = false, updatable = false)
    private TbCfcChecklistMstEntity tbCfcChecklistMst;

    @ManyToOne
    @JoinColumn(name = "ORGID", referencedColumnName = "ORGID")
    private Organisation tbOrganisation;

    @ManyToOne
    @JoinColumn(name = "APM_APPLICATION_ID", referencedColumnName = "APM_APPLICATION_ID")
    private TbCfcApplicationMstEntity tbCfcApplicationMst;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "EMPID")
    private Employee employee2;

    @ManyToOne
    @JoinColumn(name = "UPDATED_BY", referencedColumnName = "EMPID")
    private Employee employee;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbCfcRejectionEntity() {
        super();
        compositePrimaryKey = new TbCfcRejectionEntityKey();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE COMPOSITE KEY
    // ----------------------------------------------------------------------
    public void setCfcRejectionId(final Long cfcRejectionId) {
        compositePrimaryKey.setCfcRejectionId(cfcRejectionId);
    }

    public Long getCfcRejectionId() {
        return compositePrimaryKey.getCfcRejectionId();
    }

    public void setClmId(final Long clmId) {
        compositePrimaryKey.setClmId(clmId);
    }

    public Long getClmId() {
        return compositePrimaryKey.getClmId();
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
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

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : CFC_REJ_TYPE ( NVARCHAR2 )
    public void setCfcRejType(final String cfcRejType) {
        this.cfcRejType = cfcRejType;
    }

    public String getCfcRejType() {
        return cfcRejType;
    }

    // --- DATABASE MAPPING : CLM_GROUP_ID ( NUMBER )
    public void setClmGroupId(final Long clmGroupId) {
        this.clmGroupId = clmGroupId;
    }

    public Long getClmGroupId() {
        return clmGroupId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------
    public void setTbCfcChecklistMst(final TbCfcChecklistMstEntity tbCfcChecklistMst) {
        this.tbCfcChecklistMst = tbCfcChecklistMst;
    }

    public TbCfcChecklistMstEntity getTbCfcChecklistMst() {
        return tbCfcChecklistMst;
    }

    public void setTbOrganisation(final Organisation tbOrganisation) {
        this.tbOrganisation = tbOrganisation;
    }

    public Organisation getTbOrganisation() {
        return tbOrganisation;
    }

    public void setTbCfcApplicationMst(final TbCfcApplicationMstEntity tbCfcApplicationMst) {
        this.tbCfcApplicationMst = tbCfcApplicationMst;
    }

    public TbCfcApplicationMstEntity getTbCfcApplicationMst() {
        return tbCfcApplicationMst;
    }

    public void setEmployee2(final Employee employee2) {
        this.employee2 = employee2;
    }

    public Employee getEmployee2() {
        return employee2;
    }

    public void setEmployee(final Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
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
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(cfcRejType);
        sb.append("|");
        sb.append(clmGroupId);
        return sb.toString();
    }

}
