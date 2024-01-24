package com.abm.mainet.cfc.checklist.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jugnu.pandey
 * @since 13 May 2015
 */
@Entity
@Table(name = "VW_REJ_CHECKLIST")
public class ViewCheckList implements Serializable {
   
    private static final long serialVersionUID = -4014906993132426569L;

    @Id
    @Column(name = "CLM_ID", precision = 12, scale = 0, nullable = false)
    private Long clmId;

    @Column(name = "CLM_DESC", length = 2000, nullable = false)
    private String clmDesc;

    @Column(name = "CLM_DESC_ENGL", length = 2000, nullable = true)
    private String clmDescEngl;

    @Column(name = "APPLICATION_ID", precision = 16, scale = 0, nullable = false)
    private Long apmApplicationId;

    @Column(name = "CLM_REMARK", length = 30, nullable = true)
    private String clmRemark;

    public String getClmRemark() {
        return clmRemark;
    }

    public void setClmRemark(final String clmRemark) {
        this.clmRemark = clmRemark;
    }

    public Long getClmId() {
        return clmId;
    }

    public void setClmId(final Long clmId) {
        this.clmId = clmId;
    }

    public String getClmDesc() {
        return clmDesc;
    }

    public void setClmDesc(final String clmDesc) {
        this.clmDesc = clmDesc;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getClmDescEngl() {
        return clmDescEngl;
    }

    public void setClmDescEngl(final String clmDescEngl) {
        this.clmDescEngl = clmDescEngl;
    }
}