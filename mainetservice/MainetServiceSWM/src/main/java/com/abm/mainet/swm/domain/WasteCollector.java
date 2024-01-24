/**
 * 
 */
package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author sarojkumar.yadav
 *
 */
@Entity
@Table(name = "TB_SW_CONSTDEMO_GARBAGECOLL")
public class WasteCollector implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3071399041722689370L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "COG_ID", nullable = false)
    private Long collectorId;
    @Column(name = "APM_APPLICATION_ID", nullable = false)
    private Long applicationId;
    @Column(name = "LOC_ID", nullable = false)
    private Long locationId;
    @Column(name = "VE_VETYPE", nullable = false)
    private Long vehicleType;
    @Column(name = "COG_COLL_DATE", nullable = false)
    private Date collectionDate;
    @Column(name = "COG_COLL_AMT", nullable = false)
    private Double collectionAmount;
    @Column(name = "COG_TERMS_ACCEPTED", nullable = false)
    private Boolean termAccepted;
    @Column(name = "COG_COLL_STATUS", nullable = false)
    private String collectionStatus;
    @Column(name = "COG_BUILDING_PERMISSIONNO", nullable = false)
    private String bldgPermission;
    @Column(name = "COG_NAMEOF_DRV")
    private String empName;
    @Column(name = "COG_PICKUP_DATE")
    private Date pickUpDate;
    @Column(name = "COG_CONSTRATION_ADD")
    private String locAddress;
    @Column(name = "COG_NIDDAN_COMPLAIN_NO", nullable = false)
    private String complainNo;
    @Column(name = "VE_CAPACITY", nullable = true)
    private Double capacity;
    @Column(name = "VE_NO")
    private Long vehicleNo;
    @Column(name = "MRF_ID")
    private Long mrfId;
    @Column(name = "COG_PAY_FLAG", nullable = false)
    private Boolean payFlag;
    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;
    @Column(name = "LG_IP_MAC", nullable = false)
    private String lgIpMac;
    @Column(name = "COG_NOOFTRIP", nullable = false)
    private Long noTrip;
    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;
    @Column(name = "ORGID", nullable = false)
    private Long orgId;
    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;
    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

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

    public Double getCollectionAmount() {
        return collectionAmount;
    }

    public void setCollectionAmount(Double collectionAmount) {
        this.collectionAmount = collectionAmount;
    }

    public Boolean getTermAccepted() {
        return termAccepted;
    }

    public void setTermAccepted(Boolean termAccepted) {
        this.termAccepted = termAccepted;
    }

    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus;
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

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
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

    public Long getNoTrip() {
        return noTrip;
    }

    public void setNoTrip(Long noTrip) {
        this.noTrip = noTrip;
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

    public String[] getPkValues() {
        return new String[] { MainetConstants.SOLID_WASTE_MGMT, "TB_SW_CONSTDEMO_GARBAGECOLL", "COG_ID" };
    }
}
