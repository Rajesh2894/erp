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
    private Double area;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUp;
    private Character isActive;
    private Double areaMeter;
    

	private Double length;
    private Double breadth;
    private Double leftArea;
    private Double totalArea;
    
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

  

    public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
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

	public Double getAreaMeter() {
		return areaMeter;
	}

	public void setAreaMeter(Double areaMeter) {
		this.areaMeter = areaMeter;
	}
	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getBreadth() {
		return breadth;
	}

	public void setBreadth(Double breadth) {
		this.breadth = breadth;
	}

	public Double getLeftArea() {
		return leftArea;
	}

	public void setLeftArea(Double leftArea) {
		this.leftArea = leftArea;
	}

	public Double getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(Double totalArea) {
		this.totalArea = totalArea;
	}
    
	
}
