package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GenVehicleMasterDetDTO implements Serializable {

    private static final long serialVersionUID = -8109565703621969254L;

    private Long vedId;

    private Long veId;

    private Long wasteType;

    private BigDecimal veCapacity;

    private String veActive;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;


    private GenVehicleMasterDTO tbSwVehicleMaster;

    public Long getVedId() {
        return vedId;
    }

    public void setVedId(Long vedId) {
        this.vedId = vedId;
    }

    public Long getVeId() {
        return veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public Long getWasteType() {
        return wasteType;
    }

    public void setWasteType(Long wasteType) {
        this.wasteType = wasteType;
    }

    public BigDecimal getVeCapacity() {
        return veCapacity;
    }

    public void setVeCapacity(BigDecimal veCapacity) {
        this.veCapacity = veCapacity;
    }

    public String getVeActive() {
        return veActive;
    }

    public void setVeActive(String veActive) {
        this.veActive = veActive;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

	public GenVehicleMasterDTO getTbSwVehicleMaster() {
		return tbSwVehicleMaster;
	}

	public void setTbSwVehicleMaster(GenVehicleMasterDTO tbSwVehicleMaster) {
		this.tbSwVehicleMaster = tbSwVehicleMaster;
	}



 

}
