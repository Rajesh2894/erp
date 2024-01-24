/**
 * 
 */
package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.RequestDTO;

/**
 * @author sarojkumar.yadav
 *
 */
public class WasteCollectorDTO extends RequestDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4841223509881931739L;

    private Long collectorId;
    private Long applicationId;
    private Long locationId;
    private Long vehicleType;
    private Long vehicleNo;
    private Date collectionDate;
    private BigDecimal collectionAmount;
    private String bldgPermission;
    private String complainNo;
    private Boolean termAccepted;
    private Double capacity;
    private Long noTrip;
    private String collectionStatus;
    private Boolean payFlag;
    private Long createdBy;
    private Date createdDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Long orgId;
    private Long updatedBy;
    private Date updatedDate;
    private String collDate;
    private BigDecimal veCapacity;
    private String districtName;
    private String monthName;
    private String centerName;
    private String wardName;
    private String vType;
    private Long empType;
    private Long mrfId;
    private String empName;
    private Date pickUpDate;
    private String locAddress;
    private String workName;
    private String vehicleNoStr;
    private List<WasteCollectorDTO> wasteCollectorDTOList;

    public Long getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Long collectorId) {
        this.collectorId = collectorId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Long vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public BigDecimal getCollectionAmount() {
        return collectionAmount;
    }

    public void setCollectionAmount(BigDecimal collectionAmount) {
        this.collectionAmount = collectionAmount;
    }

    public String getBldgPermission() {
        return bldgPermission;
    }

    public void setBldgPermission(String bldgPermission) {
        this.bldgPermission = bldgPermission;
    }

    public String getComplainNo() {
        return complainNo;
    }

    public void setComplainNo(String complainNo) {
        this.complainNo = complainNo;
    }

    public Boolean getTermAccepted() {
        return termAccepted;
    }

    public void setTermAccepted(Boolean termAccepted) {
        this.termAccepted = termAccepted;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Long getNoTrip() {
        return noTrip;
    }

    public void setNoTrip(Long noTrip) {
        this.noTrip = noTrip;
    }

    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public Boolean getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(Boolean payFlag) {
        this.payFlag = payFlag;
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

    public Long getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(Long vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public List<WasteCollectorDTO> getWasteCollectorDTOList() {
        return wasteCollectorDTOList;
    }

    public void setWasteCollectorDTOList(List<WasteCollectorDTO> wasteCollectorDTOList) {
        this.wasteCollectorDTOList = wasteCollectorDTOList;
    }

    public String getCollDate() {
        return collDate;
    }

    public void setCollDate(String collDate) {
        this.collDate = collDate;
    }

    public BigDecimal getVeCapacity() {
        return veCapacity;
    }

    public void setVeCapacity(BigDecimal veCapacity) {
        this.veCapacity = veCapacity;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public Long getEmpType() {
        return empType;
    }

    public void setEmpType(Long empType) {
        this.empType = empType;
    }

    public Long getMrfId() {
        return mrfId;
    }

    public void setMrfId(Long mrfId) {
        this.mrfId = mrfId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(String locAddress) {
        this.locAddress = locAddress;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getVehicleNoStr() {
        return vehicleNoStr;
    }

    public void setVehicleNoStr(String vehicleNoStr) {
        this.vehicleNoStr = vehicleNoStr;
    }

    @Override
    public String toString() {
        return "WasteCollectorDTO [collectorId=" + collectorId + ", applicationId=" + applicationId + ", locationId=" + locationId
                + ", vehicleType=" + vehicleType + ", vehicleNo=" + vehicleNo + ", collectionDate=" + collectionDate
                + ", collectionAmount=" + collectionAmount + ", bldgPermission=" + bldgPermission + ", complainNo=" + complainNo
                + ", termAccepted=" + termAccepted + ", capacity=" + capacity + ", noTrip=" + noTrip + ", collectionStatus="
                + collectionStatus + ", payFlag=" + payFlag + ", createdBy=" + createdBy + ", createdDate=" + createdDate
                + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", orgId=" + orgId + ", updatedBy=" + updatedBy
                + ", updatedDate=" + updatedDate + ", collDate=" + collDate + ", veCapacity=" + veCapacity + ", districtName="
                + districtName + ", monthName=" + monthName + ", centerName=" + centerName + ", wardName=" + wardName + ", vType="
                + vType + ", empType=" + empType + ", mrfId=" + mrfId + ", empName=" + empName + ", pickUpDate=" + pickUpDate
                + ", wasteCollectorDTOList=" + wasteCollectorDTOList + "]";
    }

}
