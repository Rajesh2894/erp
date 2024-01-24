package com.abm.mainet.securitymanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class ContractualStaffMasterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8672162447296302365L;

	private Long contStsffId;
	private String contStaffAddress;
	private Date contStaffAppointDate;
	private String contStaffIdNo;
	private String contStaffMob;
	private String contStaffName;
	private Date contStaffSchFrom;
	private Date contStaffSchTo;
	private Long cpdShiftId;
	private Long createdBy;
	private Long dayPrefixId;
	private Date createdDate;
	private Long dsgId;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long locId;
	private Long orgid;
	private Long updatedBy;
	private Date updatedDate;
	private Long vendorId;
	private String desiDesc;
	private String vendorDesc;
	private String locDesc;
	private String shiftDesc;
	private String status;
	private String sex;
	private Date dob;
	private String empType;
	private Long empId;
	
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDesiDesc() {
		return desiDesc;
	}
	public void setDesiDesc(String desiDesc) {
		this.desiDesc = desiDesc;
	}
	public String getVendorDesc() {
		return vendorDesc;
	}
	public void setVendorDesc(String vendorDesc) {
		this.vendorDesc = vendorDesc;
	}
	public String getLocDesc() {
		return locDesc;
	}
	public void setLocDesc(String locDesc) {
		this.locDesc = locDesc;
	}
	public String getShiftDesc() {
		return shiftDesc;
	}
	public void setShiftDesc(String shiftDesc) {
		this.shiftDesc = shiftDesc;
	}
	public Long getContStsffId() {
		return contStsffId;
	}
	public void setContStsffId(Long contStsffId) {
		this.contStsffId = contStsffId;
	}
	public String getContStaffAddress() {
		return contStaffAddress;
	}
	public void setContStaffAddress(String contStaffAddress) {
		this.contStaffAddress = contStaffAddress;
	}
	public Date getContStaffAppointDate() {
		return contStaffAppointDate;
	}
	public void setContStaffAppointDate(Date contStaffAppointDate) {
		this.contStaffAppointDate = contStaffAppointDate;
	}
	public String getContStaffIdNo() {
		return contStaffIdNo;
	}
	public void setContStaffIdNo(String contStaffIdNo) {
		this.contStaffIdNo = contStaffIdNo;
	}
	public String getContStaffMob() {
		return contStaffMob;
	}
	public void setContStaffMob(String contStaffMob) {
		this.contStaffMob = contStaffMob;
	}
	public String getContStaffName() {
		return contStaffName;
	}
	public void setContStaffName(String contStaffName) {
		this.contStaffName = contStaffName;
	}
	public Date getContStaffSchFrom() {
		return contStaffSchFrom;
	}
	public void setContStaffSchFrom(Date contStaffSchFrom) {
		this.contStaffSchFrom = contStaffSchFrom;
	}
	public Date getContStaffSchTo() {
		return contStaffSchTo;
	}
	public void setContStaffSchTo(Date contStaffSchTo) {
		this.contStaffSchTo = contStaffSchTo;
	}
	public Long getCpdShiftId() {
		return cpdShiftId;
	}
	public void setCpdShiftId(Long cpdShiftId) {
		this.cpdShiftId = cpdShiftId;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getDayPrefixId() {
		return dayPrefixId;
	}
	public void setDayPrefixId(Long dayPrefixId) {
		this.dayPrefixId = dayPrefixId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Long getDsgId() {
		return dsgId;
	}
	public void setDsgId(Long dsgId) {
		this.dsgId = dsgId;
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
	public Long getLocId() {
		return locId;
	}
	public void setLocId(Long locId) {
		this.locId = locId;
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
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	
	@Override
	public String toString() {
		return "ContractualStaffMasterDTO [contStsffId=" + contStsffId + ", contStaffAddress=" + contStaffAddress
				+ ", contStaffAppointDate=" + contStaffAppointDate + ", contStaffIdNo=" + contStaffIdNo
				+ ", contStaffMob=" + contStaffMob + ", contStaffName=" + contStaffName + ", contStaffSchFrom="
				+ contStaffSchFrom + ", contStaffSchTo=" + contStaffSchTo + ", cpdShiftId=" + cpdShiftId
				+ ", createdBy=" + createdBy + ", dayPrefixId=" + dayPrefixId + ", createdDate=" + createdDate
				+ ", dsgId=" + dsgId + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", locId=" + locId
				+ ", orgid=" + orgid + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", vendorId="
				+ vendorId + ", desiDesc=" + desiDesc + ", vendorDesc=" + vendorDesc + ", locDesc=" + locDesc
				+ ", shiftDesc=" + shiftDesc + ", status=" + status + ", sex=" + sex + ", dob=" + dob + "]";
	}
	
	
	

	
	
}
