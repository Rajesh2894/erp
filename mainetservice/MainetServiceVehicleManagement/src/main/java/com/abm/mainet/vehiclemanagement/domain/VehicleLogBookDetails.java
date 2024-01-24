package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_vm_vehicle_log_book")
public class VehicleLogBookDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8861905403041769112L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")

	@Column(name = "ve_ID")
	private Long veID;

	@Column(name = "VNO_ID")
	private Long veNo;


	@Column(name = "driver_Id")
	private Long driverId;

	@Column(name = "OUT_DATE")
	@Temporal(TemporalType.DATE)
	private Date outDate;

	@Column(name = "IN_DATE")
	@Temporal(TemporalType.DATE)
	private Date inDate;

	@Column(name = "ve_Out_Time")
	private String vehicleOutTime;

	@Column(name = "ve_In_Time")
	private String vehicleInTime;

	@Column(name = "ve_Journey_From")
	private String vehicleJourneyFrom;

	@Column(name = "ve_Journey_To")
	private String vehicleJourneyTo;

	@Column(name = "day_Start_Meter_Read", precision = 10)
	private BigDecimal dayStartMeterReading;

	@Column(name = "day_End_Meter_Read", precision = 10)
	private BigDecimal dayEndMeterReading;

	@Column(name = "fuel_In_Litre")
	private Double fuelInLitre;

	@Column(name = "date_Of_Fueling")
	private Date dateOfFueling;

	@Column(name = "ve_type", precision = 15)
	private Long typeOfVehicle;

	@Column(name = "day_Visit_Desc", precision = 10)
	private String dayVisitDescription;

	@Column(name = "reason")
	private String reason;

	@Column(name = "ORGID")
	private Long orgid;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;
	
	@Column(name = "End_FuelInLitre")
	private Double endFuelInLitre;
	
	public Long getVeID() {
		return veID;
	}

	public void setVeID(Long veID) {
		this.veID = veID;
	}

	public Long getVeNo() {
		return veNo;
	}

	public void setVeNo(Long veNo) {
		this.veNo = veNo;
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

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	

	public Long getTypeOfVehicle() {
		return typeOfVehicle;
	}

	public void setTypeOfVehicle(Long typeOfVehicle) {
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
		return orgid;
	}

	public void setOrgId(Long orgId) {
		this.orgid = orgId;
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
	
	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
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

	public static String[] getPkValues() {
		return new String[] { "VM", "tb_vm_vehicle_log_book", "ve_ID" };
	}

	public Double getEndFuelInLitre() {
		return endFuelInLitre;
	}

	public void setEndFuelInLitre(Double endFuelInLitre) {
		this.endFuelInLitre = endFuelInLitre;
	}

}

