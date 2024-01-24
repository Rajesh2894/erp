package com.abm.mainet.cfc.challan.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AdjustmentMasterDTO implements Serializable {

    private static final long serialVersionUID = -8215758990143432628L;

    private long adjId;

    private Long dpDeptId;

    private String adjRefNo;

    private Date adjDate;

    private String adjType;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;
    
    private List<AdjustmentDetailDTO> adjDetailDto = null;
    
    private String flatNo;

    public long getAdjId() {
        return adjId;
    }

    public void setAdjId(final long adjId) {
        this.adjId = adjId;
    }

    public Long getDpDeptId() {
        return dpDeptId;
    }

    public void setDpDeptId(final Long dpDeptId) {
        this.dpDeptId = dpDeptId;
    }

    public String getAdjRefNo() {
        return adjRefNo;
    }

    public void setAdjRefNo(final String adjRefNo) {
        this.adjRefNo = adjRefNo;
    }

    public Date getAdjDate() {
        return adjDate;
    }

    public void setAdjDate(final Date adjDate) {
        this.adjDate = adjDate;
    }

    public String getAdjType() {
        return adjType;
    }

    public void setAdjType(final String adjType) {
        this.adjType = adjType;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public List<AdjustmentDetailDTO> getAdjDetailDto() {
        return adjDetailDto;
    }

    public void setAdjDetailDto(final List<AdjustmentDetailDTO> adjDetailDto) {
        this.adjDetailDto = adjDetailDto;
    }
    public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

}
