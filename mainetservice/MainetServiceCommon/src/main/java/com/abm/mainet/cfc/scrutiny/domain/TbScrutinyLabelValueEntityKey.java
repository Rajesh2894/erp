/**
 *
 */
package com.abm.mainet.cfc.scrutiny.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Rajendra.Bhujbal
 *
 */

@Embeddable
public class TbScrutinyLabelValueEntityKey implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4565123901368178673L;

    @Column(name = "SL_LABEL_ID", nullable = false)
    private Long slLabelId;

    @Column(name = "CFC_APPLICATION_ID", nullable = false)
    private Long saApplicationId;

    // ----------------------------------------------------------------------
    // CONSTRUCTORS
    // ----------------------------------------------------------------------
    public TbScrutinyLabelValueEntityKey() {
        super();
    }

    public TbScrutinyLabelValueEntityKey(final Long slLabelId, final Long saApplicationId) {
        super();
        this.slLabelId = slLabelId;
        this.saApplicationId = saApplicationId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR KEY FIELDS
    // ----------------------------------------------------------------------
    public void setSlLabelId(final Long value) {
        slLabelId = value;
    }

    public Long getSlLabelId() {
        return slLabelId;
    }

    /**
     * @return the saApplicationId
     */
    public Long getSaApplicationId() {
        return saApplicationId;
    }

    /**
     * @param saApplicationId the saApplicationId to set
     */
    public void setSaApplicationId(final Long saApplicationId) {
        this.saApplicationId = saApplicationId;
    }

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
        final TbScrutinyLabelValueEntityKey other = (TbScrutinyLabelValueEntityKey) obj;
        // --- Attribute slLabelId
        if (slLabelId == null) {
            if (other.slLabelId != null) {
                return false;
            }
        } else if (!slLabelId.equals(other.slLabelId)) {
            return false;
        }
        // --- Attribute orgid
        if (saApplicationId == null) {
            if (other.saApplicationId != null) {
                return false;
            }
        } else if (!saApplicationId.equals(other.saApplicationId)) {
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

        // --- Attribute slLabelId
        result = (prime * result) + ((slLabelId == null) ? 0 : slLabelId.hashCode());
        // --- Attribute orgid
        result = (prime * result) + ((saApplicationId == null) ? 0 : saApplicationId.hashCode());

        return result;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(slLabelId);
        sb.append("|");
        sb.append(saApplicationId);
        return sb.toString();
    }
}
