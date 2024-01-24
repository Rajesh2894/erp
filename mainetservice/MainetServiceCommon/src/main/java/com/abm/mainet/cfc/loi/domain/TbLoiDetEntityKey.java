/*
 * Created on 5 Apr 2016 ( Time 11:43:31 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.cfc.loi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Composite primary key for entity "TbLoiDetEntity" ( stored in table "TB_LOI_DET" )
 *
 * @author Telosys Tools Generator
 *
 */
@Embeddable
public class TbLoiDetEntityKey implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY KEY ATTRIBUTES
    // ----------------------------------------------------------------------
    @Column(name = "LOI_DET_ID", nullable = false)
    private Long loiDetId;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    // ----------------------------------------------------------------------
    // CONSTRUCTORS
    // ----------------------------------------------------------------------
    public TbLoiDetEntityKey() {
        super();
    }

    public TbLoiDetEntityKey(final Long loiDetId, final Long orgid) {
        super();
        this.loiDetId = loiDetId;
        this.orgid = orgid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR KEY FIELDS
    // ----------------------------------------------------------------------

    // ----------------------------------------------------------------------
    // equals METHOD
    // ----------------------------------------------------------------------
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final TbLoiDetEntityKey other = (TbLoiDetEntityKey) obj;
        // --- Attribute loiDetId
        if (loiDetId == null) {
            if (other.loiDetId != null) {
                return false;
            }
        } else if (!loiDetId.equals(other.loiDetId)) {
            return false;
        }
        // --- Attribute orgid
        if (orgid == null) {
            if (other.orgid != null) {
                return false;
            }
        } else if (!orgid.equals(other.orgid)) {
            return false;
        }
        return true;
    }

    // ----------------------------------------------------------------------
    // hashCode METHOD
    // ----------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        // --- Attribute loiDetId
        result = (prime * result) + ((loiDetId == null) ? 0 : loiDetId.hashCode());
        // --- Attribute orgid
        result = (prime * result) + ((orgid == null) ? 0 : orgid.hashCode());

        return result;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(loiDetId);
        sb.append("|");
        sb.append(orgid);
        return sb.toString();
    }

    public Long getLoiDetId() {
        return loiDetId;
    }

    public void setLoiDetId(final Long loiDetId) {
        this.loiDetId = loiDetId;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }
}
