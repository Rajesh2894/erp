package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
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
@Table(name = "tb_vm_petrol_requisition")
public class PetrolRequisitionDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8921245679601445203L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "request_Id", unique = true, nullable = false)
	private Long requestId;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private String time;

	private Long orgid;
	
	@Column(name = "Fuel_Request_No", nullable = false, length = 25)
	private String fuelReqNo;

	@Column(name = "dept_id", nullable = false)
	private Long department;

	@Column(name = "vehicle_Type")
	private Long vehicleType;

	@Column(name = "VE_ID")
	private Long veId;

	@Column(name = "fuel_Type")
	private Long fuelType;

	@Column(name = "request_Status", length = 15)
	private String requestStatus;

	@Column(name = "fuel_Quantity")
	private Double fuelQuantity;

	@Column(name = "vehicle_driver")
	private Long driverName;
	
//	@Column(name = "meter_reading")
//	private Long vehicleMeterRead;
	@Column(name = "meter_reading")
	private Double vehicleMeterRead;
	
	@Column(name = "created_by", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "lg_ip_mac", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "lg_ip_mac_upd", length = 100)
	private String lgIpMacUpd;
	
	@Column(name = "WF_STATUS", nullable = false)
	private String PetrolWFStatus;
	
	@Column(name = "fuel_Quant_Unit")
	private Long fuelQuantUnit;
	
	@Column(name = "PU_ID")
	private Long puId;
	
	@Column(name = "FUEL_REQ_REASON",nullable = false, length = 200)
	private String petrolDesc;
	
	@Column(name = "COUPON_No", length = 25)
	private String couponNo;
	
	@Column(name = "VE_CHASIS_SRNO", nullable = false, length = 20)
    private String veChasisSrno;
	
	public Long getFuelQuantUnit() {
		return fuelQuantUnit;
	}

	public void setFuelQuantUnit(Long fuelQuantUnit) {
		this.fuelQuantUnit = fuelQuantUnit;
	}

	//	public Long getVehicleMeterRead() {
//		return vehicleMeterRead;
//	}
//
//	public void setVehicleMeterRead(Long vehicleMeterRead) {
//		this.vehicleMeterRead = vehicleMeterRead;
//	}
	public Double getVehicleMeterRead() {
		return vehicleMeterRead;
	}

	public void setVehicleMeterRead(Double vehicleMeterRead) {
		this.vehicleMeterRead = vehicleMeterRead;
	}
	
	public String getPetrolWFStatus() {
		return PetrolWFStatus;
	}

	public void setPetrolWFStatus(String petrolWFStatus) {
		PetrolWFStatus = petrolWFStatus;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public Long getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Long vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Long getFuelType() {
		return fuelType;
	}

	public void setFuelType(Long fuelType) {
		this.fuelType = fuelType;
	}

	public Long getVeId() {
		return veId;
	}

	public void setVeId(Long veId) {
		this.veId = veId;
	}

	public Double getFuelQuantity() {
		return fuelQuantity;
	}

	public void setFuelQuantity(Double fuelQuantity) {
		this.fuelQuantity = fuelQuantity;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Long getDriverName() {
		return driverName;
	}

	public void setDriverName(Long driverName) {
		this.driverName = driverName;
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

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getFuelReqNo() {
		return fuelReqNo;
	}

	public void setFuelReqNo(String fuelReqNo) {
		this.fuelReqNo = fuelReqNo;
	}

	public String[] getPkValues() {
		return new String[] { "VM", "tb_fm_petrol_requisition", "request_Id" };
	}

	public Long getPuId() {
		return puId;
	}
	
	public void setPuId(Long puId) {
		this.puId = puId;
	}

	public String getPetrolDesc() {
		return petrolDesc;
	}

	public void setPetrolDesc(String petrolDesc) {
		this.petrolDesc = petrolDesc;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public String getVeChasisSrno() {
		return veChasisSrno;
	}

	public void setVeChasisSrno(String veChasisSrno) {
		this.veChasisSrno = veChasisSrno;
	}
	
	
}















