package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class VehicleLogBookMainPageDTO implements Serializable {

    private static final long serialVersionUID = -8127994900020623686L;

    private BigDecimal toatalPopInbeatCount;

    private BigDecimal toatalHouseInbeatCount;
    
    private BigDecimal totalEstInbeatCount;

    private BigDecimal totalAnimalCount;

    private BigDecimal totalHouseForCompost;

    private BigDecimal dryCapacity;

    private BigDecimal wetCapacity;

    private BigDecimal hazardiusCapacity;

    private String vehicleNo;

    private String timeIn;

    private String timeOut;

    private String dry;

    private BigDecimal dryOutPutwait;

    private String wet;

    private BigDecimal wetOutPutwait;

    private String hazardous;

    private BigDecimal hazardousOutPutwait;

    private String approved;

    private BigDecimal totalWasteCollHome;

    private String sign;

    private String tripDate;

    private String monthName;
    
    private String statusFlag;

    private List<VehicleLogBookMainPageDTO> vehicleLogBookMainPageList;

    public BigDecimal getToatalPopInbeatCount() {
        return toatalPopInbeatCount;
    }

    public void setToatalPopInbeatCount(BigDecimal toatalPopInbeatCount) {
        this.toatalPopInbeatCount = toatalPopInbeatCount;
    }

    public BigDecimal getToatalHouseInbeatCount() {
        return toatalHouseInbeatCount;
    }

    public void setToatalHouseInbeatCount(BigDecimal toatalHouseInbeatCount) {
        this.toatalHouseInbeatCount = toatalHouseInbeatCount;
    }

    public BigDecimal getTotalAnimalCount() {
        return totalAnimalCount;
    }

    public void setTotalAnimalCount(BigDecimal totalAnimalCount) {
        this.totalAnimalCount = totalAnimalCount;
    }

    public BigDecimal getTotalHouseForCompost() {
        return totalHouseForCompost;
    }

    public void setTotalHouseForCompost(BigDecimal totalHouseForCompost) {
        this.totalHouseForCompost = totalHouseForCompost;
    }

    public BigDecimal getDryCapacity() {
        return dryCapacity;
    }

    public void setDryCapacity(BigDecimal dryCapacity) {
        this.dryCapacity = dryCapacity;
    }

    public BigDecimal getWetCapacity() {
        return wetCapacity;
    }

    public void setWetCapacity(BigDecimal wetCapacity) {
        this.wetCapacity = wetCapacity;
    }

    public BigDecimal getHazardiusCapacity() {
        return hazardiusCapacity;
    }

    public void setHazardiusCapacity(BigDecimal hazardiusCapacity) {
        this.hazardiusCapacity = hazardiusCapacity;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getDry() {
        return dry;
    }

    public void setDry(String dry) {
        this.dry = dry;
    }

    public BigDecimal getDryOutPutwait() {
        return dryOutPutwait;
    }

    public void setDryOutPutwait(BigDecimal dryOutPutwait) {
        this.dryOutPutwait = dryOutPutwait;
    }

    public String getWet() {
        return wet;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    public BigDecimal getWetOutPutwait() {
        return wetOutPutwait;
    }

    public void setWetOutPutwait(BigDecimal wetOutPutwait) {
        this.wetOutPutwait = wetOutPutwait;
    }

    public String getHazardous() {
        return hazardous;
    }

    public void setHazardous(String hazardous) {
        this.hazardous = hazardous;
    }

    public BigDecimal getHazardousOutPutwait() {
        return hazardousOutPutwait;
    }

    public void setHazardousOutPutwait(BigDecimal hazardousOutPutwait) {
        this.hazardousOutPutwait = hazardousOutPutwait;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public BigDecimal getTotalWasteCollHome() {
        return totalWasteCollHome;
    }

    public void setTotalWasteCollHome(BigDecimal totalWasteCollHome) {
        this.totalWasteCollHome = totalWasteCollHome;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTripDate() {
        return tripDate;
    }

    public BigDecimal getTotalEstInbeatCount() {
        return totalEstInbeatCount;
    }

    public void setTotalEstInbeatCount(BigDecimal totalEstInbeatCount) {
        this.totalEstInbeatCount = totalEstInbeatCount;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public List<VehicleLogBookMainPageDTO> getVehicleLogBookMainPageList() {
        return vehicleLogBookMainPageList;
    }

    public void setVehicleLogBookMainPageList(List<VehicleLogBookMainPageDTO> vehicleLogBookMainPageList) {
        this.vehicleLogBookMainPageList = vehicleLogBookMainPageList;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

}
