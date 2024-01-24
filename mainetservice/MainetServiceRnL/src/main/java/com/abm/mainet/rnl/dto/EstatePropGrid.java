package com.abm.mainet.rnl.dto;

import java.io.Serializable;

/**
 * @author ritesh.patil
 *
 */
public class EstatePropGrid implements Serializable {

    private static final long serialVersionUID = -863531766366430188L;
    private Long propId;
    private String name;
    private String code;
    private Integer unitNo;
    private Integer occupancy;
    private Integer usage;
    private Integer floor;
    private String occValue;
    private String usageValue;
    private String floorValue;
    private Double totalArea;

    public String getName() {
        return name;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(final Long propId) {
        this.propId = propId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(final Integer unitNo) {
        this.unitNo = unitNo;
    }

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(final Integer occupancy) {
        this.occupancy = occupancy;
    }

    public Integer getUsage() {
        return usage;
    }

    public void setUsage(final Integer usage) {
        this.usage = usage;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(final Integer floor) {
        this.floor = floor;
    }

 public String getOccValue() {
        return occValue;
    }

    public Double getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(Double totalArea) {
		this.totalArea = totalArea;
	}

	public void setOccValue(final String occValue) {
        this.occValue = occValue;
    }

    public String getUsageValue() {
        return usageValue;
    }

    public void setUsageValue(final String usageValue) {
        this.usageValue = usageValue;
    }

    public String getFloorValue() {
        return floorValue;
    }

    public void setFloorValue(final String floorValue) {
        this.floorValue = floorValue;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    
}
