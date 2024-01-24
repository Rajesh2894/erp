package com.abm.mainet.landEstate.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LandAcquisitionDto implements Serializable {

    private static final long serialVersionUID = -1684926535153804971L;

    private Long lnaqId;

    private Long apmApplicationId; // referenceId for identify in workflow like applicationId

    private String lnDesc;

    private Long locId;

    private Long acqModeId;

    private String lnServno;

    private String lnArea;

    private String payTo;

    private String lnOth;

    private String acqPurpose;

    private String lnTitle;

    private Date acqDate;

    private String acqDateDesc;

    private String currentUsage;

    private BigDecimal currentMarktValue;

    private BigDecimal acqCost;

    private String lnRemark;

    private String acqStatus;

    private String transferStatus;

    private String lnBillNo;

    private Long lnMobNo;

    private String lnAddress;

    private Long assetId;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long vendorId;
    
    private Date fromDate;
    
    private Date toDate;
    
    private Long couEleWZId1;

    private Long couEleWZId2;

    private Long couEleWZId3;

    private Long couEleWZId4;

    private Long couEleWZId5;


    public Long getCouEleWZId1() {
		return couEleWZId1;
	}

	public void setCouEleWZId1(Long couEleWZId1) {
		this.couEleWZId1 = couEleWZId1;
	}

	public Long getCouEleWZId2() {
		return couEleWZId2;
	}

	public void setCouEleWZId2(Long couEleWZId2) {
		this.couEleWZId2 = couEleWZId2;
	}

	public Long getCouEleWZId3() {
		return couEleWZId3;
	}

	public void setCouEleWZId3(Long couEleWZId3) {
		this.couEleWZId3 = couEleWZId3;
	}

	public Long getCouEleWZId4() {
		return couEleWZId4;
	}

	public void setCouEleWZId4(Long couEleWZId4) {
		this.couEleWZId4 = couEleWZId4;
	}

	public Long getCouEleWZId5() {
		return couEleWZId5;
	}

	public void setCouEleWZId5(Long couEleWZId5) {
		this.couEleWZId5 = couEleWZId5;
	}


    public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getLnaqId() {
        return lnaqId;
    }

    public void setLnaqId(Long lnaqId) {
        this.lnaqId = lnaqId;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getLnDesc() {
        return lnDesc;
    }

    public void setLnDesc(String lnDesc) {
        this.lnDesc = lnDesc;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getAcqModeId() {
        return acqModeId;
    }

    public void setAcqModeId(Long acqModeId) {
        this.acqModeId = acqModeId;
    }

    public String getLnServno() {
        return lnServno;
    }

    public void setLnServno(String lnServno) {
        this.lnServno = lnServno;
    }

    public String getLnArea() {
        return lnArea;
    }

    public void setLnArea(String lnArea) {
        this.lnArea = lnArea;
    }

    public String getPayTo() {
        return payTo;
    }

    public void setPayTo(String payTo) {
        this.payTo = payTo;
    }

    public String getLnOth() {
        return lnOth;
    }

    public void setLnOth(String lnOth) {
        this.lnOth = lnOth;
    }

    public String getAcqPurpose() {
        return acqPurpose;
    }

    public void setAcqPurpose(String acqPurpose) {
        this.acqPurpose = acqPurpose;
    }

    public String getLnTitle() {
        return lnTitle;
    }

    public void setLnTitle(String lnTitle) {
        this.lnTitle = lnTitle;
    }

    public Date getAcqDate() {
        return acqDate;
    }

    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
    }

    public String getAcqDateDesc() {
        return acqDateDesc;
    }

    public void setAcqDateDesc(String acqDateDesc) {
        this.acqDateDesc = acqDateDesc;
    }

    public String getCurrentUsage() {
        return currentUsage;
    }

    public void setCurrentUsage(String currentUsage) {
        this.currentUsage = currentUsage;
    }

    public BigDecimal getCurrentMarktValue() {
        return currentMarktValue;
    }

    public void setCurrentMarktValue(BigDecimal currentMarktValue) {
        this.currentMarktValue = currentMarktValue;
    }

    public BigDecimal getAcqCost() {
        return acqCost;
    }

    public void setAcqCost(BigDecimal acqCost) {
        this.acqCost = acqCost;
    }

    public String getLnRemark() {
        return lnRemark;
    }

    public void setLnRemark(String lnRemark) {
        this.lnRemark = lnRemark;
    }

    public String getAcqStatus() {
        return acqStatus;
    }

    public void setAcqStatus(String acqStatus) {
        this.acqStatus = acqStatus;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getLnBillNo() {
        return lnBillNo;
    }

    public void setLnBillNo(String lnBillNo) {
        this.lnBillNo = lnBillNo;
    }

    public Long getLnMobNo() {
        return lnMobNo;
    }

    public void setLnMobNo(Long lnMobNo) {
        this.lnMobNo = lnMobNo;
    }

    public String getLnAddress() {
        return lnAddress;
    }

    public void setLnAddress(String lnAddress) {
        this.lnAddress = lnAddress;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

}
