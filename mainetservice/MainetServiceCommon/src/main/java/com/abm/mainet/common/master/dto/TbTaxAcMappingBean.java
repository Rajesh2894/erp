/**
 *
 */
package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.abm.mainet.common.ui.model.AbstractModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Harsha.Ramachandran
 *
 */
public class TbTaxAcMappingBean extends AbstractModel implements Serializable {

    private static final long serialVersionUID = -7446198935861283079L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    private Long taxbId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String taxbActive;

    @JsonIgnore
    @Size(max=100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max=100)
    private String lgIpMacUpd;

    private Long taxId;

    private Long sacHeadId;

    private String acHeadCode;

    private Long dmdClass;

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

    public String getAcHeadCode() {
        return acHeadCode;
    }

    public void setAcHeadCode(final String acHeadCode) {
        this.acHeadCode = acHeadCode;
    }

    public Long getDmdClass() {
        return dmdClass;
    }

    public void setDmdClass(Long dmdClass) {
        this.dmdClass = dmdClass;
    }

}
