package com.abm.mainet.swm.dto;

import java.util.Date;

public class vehicleLogBookDTO {

    private String vehicleType;
    private Long beatCount;
    private Long residentialCount;
    private Long commercialCount;
    private Long commercialShopsCount;
    private Long veNo;
    private String wardNo;
    private Date date;
    private String flagStatus;

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Long getBeatCount() {
        return beatCount;
    }

    public void setBeatCount(Long beatCount) {
        this.beatCount = beatCount;
    }

    public Long getResidentialCount() {
        return residentialCount;
    }

    public void setResidentialCount(Long residentialCount) {
        this.residentialCount = residentialCount;
    }

    public Long getCommercialCount() {
        return commercialCount;
    }

    public void setCommercialCount(Long commercialCount) {
        this.commercialCount = commercialCount;
    }

    public Long getCommercialShopsCount() {
        return commercialShopsCount;
    }

    public void setCommercialShopsCount(Long commercialShopsCount) {
        this.commercialShopsCount = commercialShopsCount;
    }

    public Long getVeNo() {
        return veNo;
    }

    public void setVeNo(Long veNo) {
        this.veNo = veNo;
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFlagStatus() {
        return flagStatus;
    }

    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
    }

    
}
