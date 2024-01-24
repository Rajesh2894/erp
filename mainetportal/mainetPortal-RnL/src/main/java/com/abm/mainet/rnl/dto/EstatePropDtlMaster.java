
package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ritesh.patil
 *
 */
public class EstatePropDtlMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long propDetId;
    private Long propId;
    private Integer areaType;
    private Integer area;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUp;
    private Character isActive;

    public Long getPropId() {
        return propId;
    }

    public void setPropId(final Long propId) {
        this.propId = propId;
    }

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(final Integer areaType) {
        this.areaType = areaType;
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

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(final String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(final Integer area) {
        this.area = area;
    }

    public Long getPropDetId() {
        return propDetId;
    }

    public void setPropDetId(final Long propDetId) {
        this.propDetId = propDetId;
    }

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(final Character isActive) {
        this.isActive = isActive;
    }

}
