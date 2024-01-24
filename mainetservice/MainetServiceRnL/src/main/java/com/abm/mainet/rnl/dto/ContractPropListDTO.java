package com.abm.mainet.rnl.dto;

import java.io.Serializable;

public class ContractPropListDTO implements Serializable {

    private static final long serialVersionUID = 3138207448145088550L;

    private Long propId;
    private String propertyNo;
    private String occupancy;
    private String usage;
    private String unit;
    private String floor;
    private String totalArea;
    private String propName;

    public Long getPropId() {
        return propId;
    }

    public void setPropId(final Long propId) {
        this.propId = propId;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(final String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(final String occupancy) {
        this.occupancy = occupancy;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(final String usage) {
        this.usage = usage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(final String floor) {
        this.floor = floor;
    }

    public String getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(final String totalArea) {
        this.totalArea = totalArea;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(final String propName) {
        this.propName = propName;
    }
}
