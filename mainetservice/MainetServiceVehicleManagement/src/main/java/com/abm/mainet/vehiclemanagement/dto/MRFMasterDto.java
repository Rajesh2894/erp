package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MRFMasterDto implements Serializable {
    private static final long serialVersionUID = -3268410016724295360L;

    private Long mrfId;

    private String mrfPlantId;

    private String mrfPlantName;

    private Long mrfCategory;

    private Date mrfDateOpen;

    private String mrfDecentralised;

    private String mrfOwnerShip;

    private Long locId;

    private BigDecimal mrfPlantCap;

    private String mrfIsIntegratedPlant;

    private String mrfIntegratedPlantId;

    private String mrfIsrdf;

    private BigDecimal mrfRdfqrt;

    private String mrfIsctc;

    private Long mrfIsagreIntegrated;

    private String projCode;

    private BigDecimal pojCost;

    private String projProgress;

    private String assetCode;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String siteImage;

    private String locAddress;

    private String fromDate;

    private String toDate;

    private String tripDate;

    private BigDecimal dry;

    private BigDecimal wate;

    private BigDecimal hazardous;

    private BigDecimal sumOfDryWate;

    private BigDecimal totalVolume;

    private BigDecimal totaldry;

    private BigDecimal totalwate;

    private BigDecimal totalhazardous;

    private String flagMsg;

    private List<MRFMasterDto> mRFMasterList;

    private List<MRFVehicleDetailDto> tbSwMrfVechicleDet;

    private List<MRFEmployeeDetailDto> tbSwMrfEmployeeDet;

    public Long getMrfId() {
        return mrfId;
    }

    public void setMrfId(Long mrfId) {
        this.mrfId = mrfId;
    }

    public String getMrfPlantId() {
        return mrfPlantId;
    }

    public void setMrfPlantId(String mrfPlantId) {
        this.mrfPlantId = mrfPlantId;
    }

    public String getMrfPlantName() {
        return mrfPlantName;
    }

    public void setMrfPlantName(String mrfPlantName) {
        this.mrfPlantName = mrfPlantName;
    }

    public Long getMrfCategory() {
        return mrfCategory;
    }

    public void setMrfCategory(Long mrfCategory) {
        this.mrfCategory = mrfCategory;
    }

    public Date getMrfDateOpen() {
        return mrfDateOpen;
    }

    public void setMrfDateOpen(Date mrfDateOpen) {
        this.mrfDateOpen = mrfDateOpen;
    }

    public String getMrfDecentralised() {
        return mrfDecentralised;
    }

    public void setMrfDecentralised(String mrfDecentralised) {
        this.mrfDecentralised = mrfDecentralised;
    }

    public String getMrfOwnerShip() {
        return mrfOwnerShip;
    }

    public void setMrfOwnerShip(String mrfOwnerShip) {
        this.mrfOwnerShip = mrfOwnerShip;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public BigDecimal getMrfPlantCap() {
        return mrfPlantCap;
    }

    public void setMrfPlantCap(BigDecimal mrfPlantCap) {
        this.mrfPlantCap = mrfPlantCap;
    }

    public String getMrfIsIntegratedPlant() {
        return mrfIsIntegratedPlant;
    }

    public void setMrfIsIntegratedPlant(String mrfIsIntegratedPlant) {
        this.mrfIsIntegratedPlant = mrfIsIntegratedPlant;
    }

    public String getMrfIntegratedPlantId() {
        return mrfIntegratedPlantId;
    }

    public void setMrfIntegratedPlantId(String mrfIntegratedPlantId) {
        this.mrfIntegratedPlantId = mrfIntegratedPlantId;
    }

    public String getMrfIsrdf() {
        return mrfIsrdf;
    }

    public void setMrfIsrdf(String mrfIsrdf) {
        this.mrfIsrdf = mrfIsrdf;
    }

    public BigDecimal getMrfRdfqrt() {
        return mrfRdfqrt;
    }

    public void setMrfRdfqrt(BigDecimal mrfRdfqrt) {
        this.mrfRdfqrt = mrfRdfqrt;
    }

    public String getMrfIsctc() {
        return mrfIsctc;
    }

    public void setMrfIsctc(String mrfIsctc) {
        this.mrfIsctc = mrfIsctc;
    }

    public Long getMrfIsagreIntegrated() {
        return mrfIsagreIntegrated;
    }

    public void setMrfIsagreIntegrated(Long mrfIsagreIntegrated) {
        this.mrfIsagreIntegrated = mrfIsagreIntegrated;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public BigDecimal getPojCost() {
        return pojCost;
    }

    public void setPojCost(BigDecimal pojCost) {
        this.pojCost = pojCost;
    }

    public String getProjProgress() {
        return projProgress;
    }

    public void setProjProgress(String projProgress) {
        this.projProgress = projProgress;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
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

    public String getSiteImage() {
        return siteImage;
    }

    public String getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(String locAddress) {
        this.locAddress = locAddress;
    }

    public void setSiteImage(String siteImage) {
        this.siteImage = siteImage;
    }

    public List<MRFVehicleDetailDto> getTbSwMrfVechicleDet() {
        return tbSwMrfVechicleDet;
    }

    public void setTbSwMrfVechicleDet(List<MRFVehicleDetailDto> tbSwMrfVechicleDet) {
        this.tbSwMrfVechicleDet = tbSwMrfVechicleDet;
    }

    public List<MRFEmployeeDetailDto> getTbSwMrfEmployeeDet() {
        return tbSwMrfEmployeeDet;
    }

    public void setTbSwMrfEmployeeDet(List<MRFEmployeeDetailDto> tbSwMrfEmployeeDet) {
        this.tbSwMrfEmployeeDet = tbSwMrfEmployeeDet;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public BigDecimal getDry() {
        return dry;
    }

    public void setDry(BigDecimal dry) {
        this.dry = dry;
    }

    public BigDecimal getWate() {
        return wate;
    }

    public void setWate(BigDecimal wate) {
        this.wate = wate;
    }

    public BigDecimal getHazardous() {
        return hazardous;
    }

    public void setHazardous(BigDecimal hazardous) {
        this.hazardous = hazardous;
    }

    public BigDecimal getSumOfDryWate() {
        return sumOfDryWate;
    }

    public void setSumOfDryWate(BigDecimal sumOfDryWate) {
        this.sumOfDryWate = sumOfDryWate;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public List<MRFMasterDto> getmRFMasterList() {
        return mRFMasterList;
    }

    public void setmRFMasterList(List<MRFMasterDto> mRFMasterList) {
        this.mRFMasterList = mRFMasterList;
    }

    public BigDecimal getTotaldry() {
        return totaldry;
    }

    public void setTotaldry(BigDecimal totaldry) {
        this.totaldry = totaldry;
    }

    public BigDecimal getTotalwate() {
        return totalwate;
    }

    public void setTotalwate(BigDecimal totalwate) {
        this.totalwate = totalwate;
    }

    public BigDecimal getTotalhazardous() {
        return totalhazardous;
    }

    public void setTotalhazardous(BigDecimal totalhazardous) {
        this.totalhazardous = totalhazardous;
    }

    public String getFlagMsg() {
        return flagMsg;
    }

    public void setFlagMsg(String flagMsg) {
        this.flagMsg = flagMsg;
    }

}
