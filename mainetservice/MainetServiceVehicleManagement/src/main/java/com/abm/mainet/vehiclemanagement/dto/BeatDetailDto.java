package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cherupelli.srikanth
 *
 */
public class BeatDetailDto implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Long beatDetId;

    private Long beatId;

    private String beatAreaType;

    private String beatAreaName;

    private Long beatHouseHold;

    private Long beatShop;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lpIpMac;

    private String lpIpMAcUpd;
    
    public Long getBeatId() {
        return beatId;
    }

    public void setBeatId(Long beatId) {
        this.beatId = beatId;
    }

    public Long getBeatDetId() {
        return beatDetId;
    }

    public void setBeatDetId(Long beatDetId) {
        this.beatDetId = beatDetId;
    }

   
    public String getBeatAreaType() {
        return beatAreaType;
    }

    public void setBeatAreaType(String beatAreaType) {
        this.beatAreaType = beatAreaType;
    }

    public String getBeatAreaName() {
        return beatAreaName;
    }

    public void setBeatAreaName(String beatAreaName) {
        this.beatAreaName = beatAreaName;
    }

    public Long getBeatHouseHold() {
        return beatHouseHold;
    }

    public void setBeatHouseHold(Long beatHouseHold) {
        this.beatHouseHold = beatHouseHold;
    }

    public Long getBeatShop() {
        return beatShop;
    }

    public void setBeatShop(Long beatShop) {
        this.beatShop = beatShop;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getLpIpMac() {
        return lpIpMac;
    }

    public void setLpIpMac(String lpIpMac) {
        this.lpIpMac = lpIpMac;
    }

    public String getLpIpMAcUpd() {
        return lpIpMAcUpd;
    }

    public void setLpIpMAcUpd(String lpIpMAcUpd) {
        this.lpIpMAcUpd = lpIpMAcUpd;
    }
    
    

}
