package com.abm.mainet.firemanagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VehicleLogBookDTO implements Serializable {

	private static final long serialVersionUID = -2498307488575123462L;

	private Long veID;

	private Date outDate;
	private Date inDate;
	private Date fromDate;
	private Date toDate;
	private String vehicleOutTime;
	private String vehicleInTime;
	private String vehicleJourneyFrom;
	private String vehicleJourneyTo;
	private BigDecimal dayStartMeterReading;
	private BigDecimal dayEndMeterReading;
	private Double fuelInLitre;
	private Date dateOfFueling;
	private String veNo;
	private String driverName;
	private String driverDesc;
	private Long driverId;
	private String typeOfVehicle;
	private String dayVisitDescription;
	private String reason;
	private Long orgId;
	private String fireStation;
	private String department;
	
	private Long createdBy;
	private Date createdDate;
	
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;

	
	
	public String getFireStation() {
		return fireStation;
	}

	public void setFireStation(String fireStation) {
		this.fireStation = fireStation;
	}

	public Long getVeID() {
		return veID;
	}

	public void setVeID(Long veID) {
		this.veID = veID;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public String getVehicleOutTime() {
		return vehicleOutTime;
	}

	public void setVehicleOutTime(String vehicleOutTime) {
		this.vehicleOutTime = vehicleOutTime;
	}

	public String getVehicleInTime() {
		return vehicleInTime;
	}

	public void setVehicleInTime(String vehicleInTime) {
		this.vehicleInTime = vehicleInTime;
	}

	public String getVehicleJourneyFrom() {
		return vehicleJourneyFrom;
	}

	public void setVehicleJourneyFrom(String vehicleJourneyFrom) {
		this.vehicleJourneyFrom = vehicleJourneyFrom;
	}

	public String getVehicleJourneyTo() {
		return vehicleJourneyTo;
	}

	public void setVehicleJourneyTo(String vehicleJourneyTo) {
		this.vehicleJourneyTo = vehicleJourneyTo;
	}


	public BigDecimal getDayStartMeterReading() {
		return dayStartMeterReading;
	}

	public void setDayStartMeterReading(BigDecimal dayStartMeterReading) {
		this.dayStartMeterReading = dayStartMeterReading;
	}

	public BigDecimal getDayEndMeterReading() {
		return dayEndMeterReading;
	}

	public void setDayEndMeterReading(BigDecimal dayEndMeterReading) {
		this.dayEndMeterReading = dayEndMeterReading;
	}

	public Double getFuelInLitre() {
		return fuelInLitre;
	}

	public void setFuelInLitre(Double fuelInLitre) {
		this.fuelInLitre = fuelInLitre;
	}

	public Date getDateOfFueling() {
		return dateOfFueling;
	}

	public void setDateOfFueling(Date dateOfFueling) {
		this.dateOfFueling = dateOfFueling;
	}


	public String getVeNo() {
		return veNo;
	}

	public void setVeNo(String veNo) {
		this.veNo = veNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getTypeOfVehicle() {
		return typeOfVehicle;
	}

	public void setTypeOfVehicle(String typeOfVehicle) {
		this.typeOfVehicle = typeOfVehicle;
	}

	public String getDayVisitDescription() {
		return dayVisitDescription;
	}

	public void setDayVisitDescription(String dayVisitDescription) {
		this.dayVisitDescription = dayVisitDescription;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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


	public String getDriverDesc() {
		return driverDesc;
	}

	public void setDriverDesc(String driverDesc) {
		this.driverDesc = driverDesc;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	
	
	
     
}