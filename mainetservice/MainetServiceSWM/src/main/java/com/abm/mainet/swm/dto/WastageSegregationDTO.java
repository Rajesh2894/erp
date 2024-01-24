package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
public class WastageSegregationDTO implements Serializable {

    private static final long serialVersionUID = 9072391751503153112L;

    private Long grId;

    private Long createdBy;

    private Date createdDate;

    private Date grDate;

    private Long grTotal;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private Long deId;

    private Long empId;

    private Long venId;

    private String dName;

    private String waste;

    private String subTypeWaste;

    private String valome;

    private List<WastageSegregationDTO> wastageSegregationList;

    private String fromDate;

    private String toDate;

    private BigDecimal totalVol;

    private String subTypeWaste2;

    private BigDecimal totalVolume;

    private BigDecimal volume;

    private String vendorName;

    private String flagMsg;

    private Long mrfId;

    private Long monthNo;

    private String mrfCenterName;

    private String dsName;

    private String monthName;

    private String year;

    private Long date;

    private String month;

    private String totalDry;

    private String totalWet;

    private String totalHazardius;

    
    public BigDecimal getTotalVol() {
		return totalVol;
	}

	public void setTotalVol(BigDecimal totalVol) {
		this.totalVol = totalVol;
	}

	private List<WastageSegregationDetailsDTO> tbSwWastesegDets;

    public WastageSegregationDTO() {
    }

    public Long getGrId() {
        return this.grId;
    }

    public void setGrId(Long grId) {
        this.grId = grId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getGrDate() {
        return this.grDate;
    }

    public void setGrDate(Date grDate) {
        this.grDate = grDate;
    }

    public Long getGrTotal() {
        return this.grTotal;
    }

    public void setGrTotal(Long grTotal) {
        this.grTotal = grTotal;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getVenId() {
        return venId;
    }

    public void setVenId(Long venId) {
        this.venId = venId;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getDeId() {
        return deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public List<WastageSegregationDetailsDTO> getTbSwWastesegDets() {
        return this.tbSwWastesegDets;
    }

    public void setTbSwWastesegDets(
            List<WastageSegregationDetailsDTO> tbSwWastesegDets) {
        this.tbSwWastesegDets = tbSwWastesegDets;
    }

    public WastageSegregationDetailsDTO addTbSwWastesegDet(
            WastageSegregationDetailsDTO tbSwWastesegDet) {
        getTbSwWastesegDets().add(tbSwWastesegDet);
        tbSwWastesegDet.setTbSwWasteseg(this);

        return tbSwWastesegDet;
    }

    public WastageSegregationDetailsDTO removeTbSwWastesegDet(
            WastageSegregationDetailsDTO tbSwWastesegDet) {
        getTbSwWastesegDets().remove(tbSwWastesegDet);
        tbSwWastesegDet.setTbSwWasteseg(null);

        return tbSwWastesegDet;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getWaste() {
        return waste;
    }

    public void setWaste(String waste) {
        this.waste = waste;
    }

    public String getSubTypeWaste() {
        return subTypeWaste;
    }

    public void setSubTypeWaste(String subTypeWaste) {
        this.subTypeWaste = subTypeWaste;
    }

    public String getValome() {
        return valome;
    }

    public void setValome(String valome) {
        this.valome = valome;
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

    public List<WastageSegregationDTO> getWastageSegregationList() {
        return wastageSegregationList;
    }

    public void setWastageSegregationList(
            List<WastageSegregationDTO> wastageSegregationList) {
        this.wastageSegregationList = wastageSegregationList;
    }

    public String getSubTypeWaste2() {
        return subTypeWaste2;
    }

    public void setSubTypeWaste2(String subTypeWaste2) {
        this.subTypeWaste2 = subTypeWaste2;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getFlagMsg() {
        return flagMsg;
    }

    public void setFlagMsg(String flagMsg) {
        this.flagMsg = flagMsg;
    }

    public Long getMrfId() {
        return mrfId;
    }

    public void setMrfId(Long mrfId) {
        this.mrfId = mrfId;
    }

    public Long getMonthNo() {
        return monthNo;
    }

    public void setMonthNo(Long monthNo) {
        this.monthNo = monthNo;
    }

    public String getMrfCenterName() {
        return mrfCenterName;
    }

    public void setMrfCenterName(String mrfCenterName) {
        this.mrfCenterName = mrfCenterName;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalDry() {
        return totalDry;
    }

    public void setTotalDry(String totalDry) {
        this.totalDry = totalDry;
    }

    public String getTotalWet() {
        return totalWet;
    }

    public void setTotalWet(String totalWet) {
        this.totalWet = totalWet;
    }

    public String getTotalHazardius() {
        return totalHazardius;
    }

    public void setTotalHazardius(String totalHazardius) {
        this.totalHazardius = totalHazardius;
    }

}