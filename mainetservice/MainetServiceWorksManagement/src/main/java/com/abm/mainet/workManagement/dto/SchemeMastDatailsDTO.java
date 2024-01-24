package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author vishwajeet.kumar
 *
 */
public class SchemeMastDatailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long schDetId;

    private Long wmSchId;

    private String schDSpon;

    private BigDecimal schSharPer;

    private Long orgid;

    private Long createdBy;

    private Date CreatedDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String schDActive;

    private String schActiveFlag;

    public String getSchDActive() {
        return schDActive;
    }

    public void setSchDActive(String schDActive) {
        this.schDActive = schDActive;
    }

    public Long getSchDetId() {
        return schDetId;
    }

    public void setSchDetId(Long schDetId) {
        this.schDetId = schDetId;
    }

    public Long getWmSchId() {
        return wmSchId;
    }

    public void setWmSchId(Long wmSchId) {
        this.wmSchId = wmSchId;
    }

    public String getSchDSpon() {
        return schDSpon;
    }

    public void setSchDSpon(String schDSpon) {
        this.schDSpon = schDSpon;
    }

    public BigDecimal getSchSharPer() {
        return schSharPer;
    }

    public void setSchSharPer(BigDecimal schSharPer) {
        this.schSharPer = schSharPer;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public String toString() {
        return "WmsSchemeMastDatailsDTO [schDetId=" + schDetId + ", schDSpon=" + schDSpon + ", schSharPer="
                + schSharPer + ", orgid=" + orgid + ", CreatedDate=" + CreatedDate + ", updatedBy="
                + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
    }

    public String getSchActiveFlag() {
        return schActiveFlag;
    }

    public void setSchActiveFlag(String schActiveFlag) {
        this.schActiveFlag = schActiveFlag;
    }

}
