package com.abm.mainet.water.domain;

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
 * Persistent class for entity stored in table "tb_wt_slope_parameter"
 *
 * @author pushkar.dike
 *
 */

@Entity
@Table(name = "TB_WT_SLOPE_PARAMETER")
public class SlopeParameterEntity {
	


    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SP_ID", nullable = false)
    private Long spId;

    @Column(name = "SP_FROM")
    private Double spFrom;

    @Column(name = "SP_TO")
    private Double spTo;

    @Column(name = "SP_VALUE")
    private Double spValue;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "LANG_ID")
    private Long langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE")
    private Date lmoddate;

    
    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------

    public void setSpId(final Long spId) {
        this.spId = spId;
    }

    public Long getSpId() {
        return spId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : SP_FROM ( NUMBER )
    public void setSpFrom(final Double spFrom) {
        this.spFrom = spFrom;
    }

    public Double getSpFrom() {
        return spFrom;
    }

    // --- DATABASE MAPPING : SP_TO ( NUMBER )
    public void setSpTo(final Double spTo) {
        this.spTo = spTo;
    }

    public Double getSpTo() {
        return spTo;
    }

    // --- DATABASE MAPPING : SP_VALUE ( NUMBER )
    public void setSpValue(final Double spValue) {
        this.spValue = spValue;
    }

    public Double getSpValue() {
        return spValue;
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

    

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(spId);
        sb.append("]:");
        sb.append(spFrom);
        sb.append("|");
        sb.append(spTo);
        sb.append("|");
        sb.append(spValue);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        
        return sb.toString();
    }
    

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_SLOPE_PARAMETER", "SP_ID" };
    }



}
