package com.abm.mainet.adh.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author vishwajeet.kumar
 * @since 11 October 2019
 */
public class NewAdvertisementApplicationDetDto implements Serializable {

    private static final long serialVersionUID = -5078275627581573301L;

    private Long adhHrdDetId;

    private Long adhId;

    private Long adhTypeId1;

    private Long adhTypeId2;

    private Long adhTypeId3;

    private Long adhTypeId4;

    private Long adhTypeId5;

    private String advDetailsDesc;

    private BigDecimal advDetailsLength;

    private BigDecimal advDetailsHeight;

    private BigDecimal advDetailsArea;

    private Long unit;

    private Long dispTypeId;

    private BigDecimal adhFee;

    private String gisId;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;
    
    private Long hoardingId;
    private String hoardingNumber;

    private String displayIdDesc;

    public Long getAdhHrdDetId() {
        return adhHrdDetId;
    }

    public void setAdhHrdDetId(Long adhHrdDetId) {
        this.adhHrdDetId = adhHrdDetId;
    }

    public Long getAdhId() {
        return adhId;
    }

    public void setAdhId(Long adhId) {
        this.adhId = adhId;
    }

    public Long getAdhTypeId1() {
        return adhTypeId1;
    }

    public void setAdhTypeId1(Long adhTypeId1) {
        this.adhTypeId1 = adhTypeId1;
    }

    public Long getAdhTypeId2() {
        return adhTypeId2;
    }

    public void setAdhTypeId2(Long adhTypeId2) {
        this.adhTypeId2 = adhTypeId2;
    }

    public Long getAdhTypeId3() {
        return adhTypeId3;
    }

    public void setAdhTypeId3(Long adhTypeId3) {
        this.adhTypeId3 = adhTypeId3;
    }

    public Long getAdhTypeId4() {
        return adhTypeId4;
    }

    public void setAdhTypeId4(Long adhTypeId4) {
        this.adhTypeId4 = adhTypeId4;
    }

    public Long getAdhTypeId5() {
        return adhTypeId5;
    }

    public void setAdhTypeId5(Long adhTypeId5) {
        this.adhTypeId5 = adhTypeId5;
    }

    public String getAdvDetailsDesc() {
        return advDetailsDesc;
    }

    public void setAdvDetailsDesc(String advDetailsDesc) {
        this.advDetailsDesc = advDetailsDesc;
    }

    public BigDecimal getAdvDetailsLength() {
        return advDetailsLength;
    }

    public void setAdvDetailsLength(BigDecimal advDetailsLength) {
        this.advDetailsLength = advDetailsLength;
    }

    public BigDecimal getAdvDetailsHeight() {
        return advDetailsHeight;
    }

    public void setAdvDetailsHeight(BigDecimal advDetailsHeight) {
        this.advDetailsHeight = advDetailsHeight;
    }

    public BigDecimal getAdvDetailsArea() {
        return advDetailsArea;
    }

    public void setAdvDetailsArea(BigDecimal advDetailsArea) {
        this.advDetailsArea = advDetailsArea;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public Long getDispTypeId() {
        return dispTypeId;
    }

    public void setDispTypeId(Long dispTypeId) {
        this.dispTypeId = dispTypeId;
    }

    public BigDecimal getAdhFee() {
        return adhFee;
    }

    public void setAdhFee(BigDecimal adhFee) {
        this.adhFee = adhFee;
    }

    public String getGisId() {
        return gisId;
    }

    public void setGisId(String gisId) {
        this.gisId = gisId;
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

	public Long getHoardingId() {
		return hoardingId;
	}

	public void setHoardingId(Long hoardingId) {
		this.hoardingId = hoardingId;
	}

	public String getHoardingNumber() {
		return hoardingNumber;
	}

	public void setHoardingNumber(String hoardingNumber) {
		this.hoardingNumber = hoardingNumber;
	}

	public String getDisplayIdDesc() {
		return displayIdDesc;
	}

	public void setDisplayIdDesc(String displayIdDesc) {
		this.displayIdDesc = displayIdDesc;
	}

}
