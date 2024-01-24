package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class PetrolRequisitionDTO implements Serializable {

	private static final long serialVersionUID = -2432625085435473574L;

	private Long requestId;
	
	private String fuelReqNo;

	private Date date;

	private String time;

	private Long orgid;

	private Long department;

	private String deptDesc;

	private Long vehicleType;

	private String vehicleTypeDesc;

	private String veNo;
	
	private Long veId;
	
	private Long fuelType;

	private String fuelTypeDesc;

	private Double fuelQuantity;

	private String driverDesc;

	private Long driverName;

	private String requestStatus;
	
	private String petrolRegstatus;

	//private Long vehicleMeterRead;
	private Double vehicleMeterRead;
	
	private String petrolRegRemark;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private String PetrolWfStatus;
	
	private Long fuelApplicationId;
	
	private Long fuelQuantUnit;
	
	private Long puId;
	
	private String pumpName;
	
	private String pumpTyp;
	
	private String  pumpa;
	
	private String petrolDesc;
	
	private String fuelQuantUnitDesc;
	
	private Long langId;
	private Long userId;
	private String eName;
	private String couponNo;
	private String veChasisSrno;
	
    private String removeFileById;
	
	public Long getFuelQuantUnit() {
		return fuelQuantUnit;
	}

	public void setFuelQuantUnit(Long fuelQuantUnit) {
		this.fuelQuantUnit = fuelQuantUnit;
	}
	
/*	public Long getVehicleMeterRead() {
		return vehicleMeterRead;
	}

	public void setVehicleMeterRead(Long vehicleMeterRead) {
		this.vehicleMeterRead = vehicleMeterRead;
	}*/
	
	public Double getVehicleMeterRead() {
		return vehicleMeterRead;
	}

	public void setVehicleMeterRead(Double vehicleMeterRead) {
		this.vehicleMeterRead = vehicleMeterRead;
	}

	public String getPetrolWfStatus() {
		return PetrolWfStatus;
	}

	public void setPetrolWfStatus(String petrolWfStatus) {
		PetrolWfStatus = petrolWfStatus;
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

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public Long getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Long vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleTypeDesc() {
		return vehicleTypeDesc;
	}

	public void setVehicleTypeDesc(String vehicleTypeDesc) {
		this.vehicleTypeDesc = vehicleTypeDesc;
	}

	public Long getFuelType() {
		return fuelType;
	}

	public void setFuelType(Long fuelType) {
		this.fuelType = fuelType;
	}

	public String getFuelTypeDesc() {
		return fuelTypeDesc;
	}

	public void setFuelTypeDesc(String fuelTypeDesc) {
		this.fuelTypeDesc = fuelTypeDesc;
	}

	public Long getDriverName() {
		return driverName;
	}

	public void setDriverName(Long driverName) {
		this.driverName = driverName;
	}

	public String getVeNo() {
		return veNo;
	}

	public void setVeNo(String veNo) {
		this.veNo = veNo;
	}

	public Double getFuelQuantity() {
		return fuelQuantity;
	}

	public void setFuelQuantity(Double fuelQuantity) {
		this.fuelQuantity = fuelQuantity;
	}


	public String getDriverDesc() {
		return driverDesc;
	}

	public void setDriverDesc(String driverDesc) {
		this.driverDesc = driverDesc;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
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

	public String getPetrolRegstatus() {
		return petrolRegstatus;
	}

	public void setPetrolRegstatus(String petrolRegstatus) {
		this.petrolRegstatus = petrolRegstatus;
	}

	public String getPetrolRegRemark() {
		return petrolRegRemark;
	}

	public void setPetrolRegRemark(String petrolRegRemark) {
		this.petrolRegRemark = petrolRegRemark;
	}

	public Long getFuelApplicationId() {
		return fuelApplicationId;
	}

	public void setFuelApplicationId(Long fuelApplicationId) {
		this.fuelApplicationId = fuelApplicationId;
	}

	public String getFuelReqNo() {
		return fuelReqNo;
	}

	public void setFuelReqNo(String fuelReqNo) {
		this.fuelReqNo = fuelReqNo;
	}

	public Long getVeId() {
		return veId;
	}

	public void setVeId(Long veId) {
		this.veId = veId;
	}

		public Long getPuId() {
			return puId;
		}
	
		public void setPuId(Long puId) {
			this.puId = puId;
		}

		public String getPumpName() {
			return pumpName;
		}

		public void setPumpName(String pumpName) {
			this.pumpName = pumpName;
		}

		public String getPumpTyp() {
			return pumpTyp;
		}

		public void setPumpTyp(String pumpTyp) {
			this.pumpTyp = pumpTyp;
		}

		public String getPumpa() {
			return pumpa;
		}

		public void setPumpa(String pumpa) {
			this.pumpa = pumpa;
		}

		public String getPetrolDesc() {
			return petrolDesc;
		}

		public void setPetrolDesc(String petrolDesc) {
			this.petrolDesc = petrolDesc;
		}

		public String getFuelQuantUnitDesc() {
			return fuelQuantUnitDesc;
		}

		public void setFuelQuantUnitDesc(String fuelQuantUnitDesc) {
			this.fuelQuantUnitDesc = fuelQuantUnitDesc;
		}

		
		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getLangId() {
			return langId;
		}

		public void setLangId(Long langId) {
			this.langId = langId;
		}

		public String geteName() {
			return eName;
		}

		public void seteName(String eName) {
			this.eName = eName;
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

		public String getRemoveFileById() {
			return removeFileById;
		}

		public void setRemoveFileById(String removeFileById) {
			this.removeFileById = removeFileById;
		}

}





