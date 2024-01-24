package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;

public class PropertyPenltyDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -541997390478174334L;

    private long penaltyId;

    private double actualAmount;

    private Long createdBy;

    private Date createdDate;

    private Long finYearId;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgId;

    private double pendingAmount;

    private String propNo;

    private Long updatedBy;

    private Date updatedDate;
    
    private String activeFlag;
    
    private String groupPropNo;
    
    private String parentPropNo;

    public long getPenaltyId() {
        return penaltyId;
    }

    public void setPenaltyId(long penaltyId) {
        this.penaltyId = penaltyId;
    }

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
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

    public Long getFinYearId() {
        return finYearId;
    }

    public void setFinYearId(Long finYearId) {
        this.finYearId = finYearId;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public double getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(double pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
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

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getGroupPropNo() {
		return groupPropNo;
	}

	public void setGroupPropNo(String groupPropNo) {
		this.groupPropNo = groupPropNo;
	}

	public String getParentPropNo() {
		return parentPropNo;
	}

	public void setParentPropNo(String parentPropNo) {
		this.parentPropNo = parentPropNo;
	}

    
}
