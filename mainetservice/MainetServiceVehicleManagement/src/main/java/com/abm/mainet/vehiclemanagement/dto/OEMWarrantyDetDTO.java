package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class OEMWarrantyDetDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6978147572564332037L;
	
	private Long oemDetId;;

	private Long partType;
	private String partTypeDesc;

	private Long partPosition;

	private String partName;

	private Long warrantyPeriod;

	private Date purchaseDate;

	private Date lastDateOfWarranty;
	
	private Date maintanceDate;

	private Long department;

	private String deptDesc;

	private Long vehicleType;
    
	private String veNo;
	
	private Long veId;

	private String veDesc;

	private String remarks;

	
	private Long createdBy;

	private Date createdDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long orgid;

	private Long updatedBy;

	private Date updatedDate;
	
	private Long unit;
	
	private String partIdNo;
	
	private String partPositionDesc;
	
	private Long partQty;
	
	private String partRemark;

	//@JsonIgnore
	private OEMWarrantyDTO tboemwarranty;

	public OEMWarrantyDetDTO() {
	}

	public OEMWarrantyDTO getTboemwarranty() {
		return tboemwarranty;
	}

	public void setTboemwarranty(OEMWarrantyDTO tboemwarranty) {
		this.tboemwarranty = tboemwarranty;
	}

	public Long getOemDetId() {
		return oemDetId;
	}

	public void setOemDetId(Long oemDetId) {
		this.oemDetId = oemDetId;
	}

	public Long getPartType() {
		return partType;
	}

	public void setPartType(Long partType) {
		this.partType = partType;
	}

	public Long getPartPosition() {
		return partPosition;
	}

	public void setPartPosition(Long partPosition) {
		this.partPosition = partPosition;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Long getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(Long warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getLastDateOfWarranty() {
		return lastDateOfWarranty;
	}

	public void setLastDateOfWarranty(Date lastDateOfWarranty) {
		this.lastDateOfWarranty = lastDateOfWarranty;
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

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public String getPartTypeDesc() {
		return partTypeDesc;
	}

	public void setPartTypeDesc(String partTypeDesc) {
		this.partTypeDesc = partTypeDesc;
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

	public String getVeNo() {
		return veNo;
	}

	public void setVeNo(String veNo) {
		this.veNo = veNo;
	}

	public Long getVeId() {
		return veId;
	}

	public void setVeId(Long veId) {
		this.veId = veId;
	}

	public String getVeDesc() {
		return veDesc;
	}

	public void setVeDesc(String veDesc) {
		this.veDesc = veDesc;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public Long getUnit() {
		return unit;
	}

	public void setUnit(Long unit) {
		this.unit = unit;
	}

	public Date getMaintanceDate() {
		return maintanceDate;
	}

	public void setMaintanceDate(Date maintanceDate) {
		this.maintanceDate = maintanceDate;
	}

	public String getPartIdNo() {
		return partIdNo;
	}

	public void setPartIdNo(String partIdNo) {
		this.partIdNo = partIdNo;
	}

	public String getPartPositionDesc() {
		return partPositionDesc;
	}

	public void setPartPositionDesc(String partPositionDesc) {
		this.partPositionDesc = partPositionDesc;
	}

	public Long getPartQty() {
		return partQty;
	}

	public void setPartQty(Long partQty) {
		this.partQty = partQty;
	}

	public String getPartRemark() {
		return partRemark;
	}

	public void setPartRemark(String partRemark) {
		this.partRemark = partRemark;
	}

}





