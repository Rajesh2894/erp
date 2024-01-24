package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Harsha.Ramachandran
 *
 */

@Entity
@Table(name = "TB_TAX_AC_MAPPING")
public class TbTaxAcMappingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * ---------------------------------------------------------------------- ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
     * ----------------------------------------------------------------------
     */
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TAXB_ID", nullable = false)
    private Long taxbId;

    /*----------------------------------------------------------------------
    	ENTITY DATA FIELDS
    ----------------------------------------------------------------------*/
    @Column(name = "ORGID", nullable = true)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "TAXB_ACTIVE", nullable = true)
    private String taxbActive;

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    @Column(name = "TAX_ID", nullable = false)
    private Long taxId;

    @Column(name = "SAC_HEAD_ID", nullable = false)
    private Long sacHeadId;

    @Column(name = "DMD_CLASS_ID", nullable = true)
    private Long dmdClass;

    /*
     * CONSTRUCTOR
     */
    public TbTaxAcMappingEntity() {
        super();
    }

    /*----------------------------------------------------
      	GETTERS & SETTERS
     ----------------------------------------------------*/

    public Long getTaxbId() {
        return taxbId;
    }

    public void setTaxbId(final Long taxbId) {
        this.taxbId = taxbId;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
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

    public String getTaxbActive() {
        return taxbActive;
    }

    public void setTaxbActive(final String taxbActive) {
        this.taxbActive = taxbActive;
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

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    @Override
    public String toString() {
        return "TbTaxAcMappingEntity [orgId=" + orgId
                + ", createdBy=" + createdBy + ", createdDate=" + createdDate
                + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
                + ", taxbActive=" + taxbActive + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + ", tbTaxMasEntityList="
                + ", accountBudgetCodeEntity="
                + "]";
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_TAX_AC_MAPPING", "TAXB_ID" };
    }

    public Long getDmdClass() {
        return dmdClass;
    }

    public void setDmdClass(Long dmdClass) {
        this.dmdClass = dmdClass;
    }

}
