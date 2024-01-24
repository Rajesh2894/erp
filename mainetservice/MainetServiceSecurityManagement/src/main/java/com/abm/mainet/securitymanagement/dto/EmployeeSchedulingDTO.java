package com.abm.mainet.securitymanagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.securitymanagement.domain.EmployeeScheduling;

public class EmployeeSchedulingDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long emplScdlId;
	private Long vendorId;
	private Long locId;
	private Long cpdShiftId;
	private String emplSchFrom;
	private String emplSchTo;
	private String emplIdNo;
	private String contStaffName;
	private Long dayPrefixId;
	private String dayDesc;
	private Long createdBy;
	private Date createdDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long orgid;
	private Long updatedBy;
	private Date updatedDate;
	private Long empTypeId;
	private String contStaffIdNo;
	private Date contStaffSchFrom;
	private Date contStaffSchTo;
	private Long deplId;
	private Long transferId;
	private String shiftDesc;
	private Long count;
	private String fromTime;
	private String toTime;
	private String messageDate;
	private List<Date> sheduleDate;
	private List<EmployeeSchedulingDetDTO> emplDetDto;
	private List <EmployeeScheduling> dto=new ArrayList<>();
	private List<EmployeeSchedulingDTO> edto=new ArrayList<>();
	
	public List<EmployeeSchedulingDTO> getEdto() {
		return edto;
	}
	public void setEdto(List<EmployeeSchedulingDTO> edto) {
		this.edto = edto;
	}
	public List<Date> getSheduleDate() {
		return sheduleDate;
	}
	public void setSheduleDate(List<Date> sheduleDate) {
		this.sheduleDate = sheduleDate;
	}
	public List<EmployeeSchedulingDetDTO> getEmplDetDto() {
		return emplDetDto;
	}
	public void setEmplDetDto(List<EmployeeSchedulingDetDTO> emplDetDto) {
		this.emplDetDto = emplDetDto;
	}
	public List<EmployeeScheduling> getDto() {
		return dto;
	}
	public void setDto(List<EmployeeScheduling> empSchedulingList) {
		this.dto = empSchedulingList;
	}
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getShiftDesc() {
		return shiftDesc;
	}
	public void setShiftDesc(String shiftDesc) {
		this.shiftDesc = shiftDesc;
	}
	public Long getDeplId() {
		return deplId;
	}
	public void setDeplId(Long deplId) {
		this.deplId = deplId;
	}
	public Long getTransferId() {
		return transferId;
	}
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}
	public String getContStaffIdNo() {
		return contStaffIdNo;
	}
	public void setContStaffIdNo(String contStaffIdNo) {
		this.contStaffIdNo = contStaffIdNo;
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
	public String getDayDesc() {
		return dayDesc;
	}
	public void setDayDesc(String dayDesc) {
		this.dayDesc = dayDesc;
	}
	public Long getDayPrefixId() {
		return dayPrefixId;
	}
	public void setDayPrefixId(Long dayPrefixId) {
		this.dayPrefixId = dayPrefixId;
	}
	public Long getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(Long empTypeId) {
		this.empTypeId = empTypeId;
	}
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public Long getLocId() {
		return locId;
	}
	public void setLocId(Long locId) {
		this.locId = locId;
	}
	public Long getCpdShiftId() {
		return cpdShiftId;
	}
	public void setCpdShiftId(Long cpdShiftId) {
		this.cpdShiftId = cpdShiftId;
	}
	public Long getEmplScdlId() {
		return emplScdlId;
	}
	public void setEmplScdlId(Long emplScdlId) {
		this.emplScdlId = emplScdlId;
	}
	public String getEmplSchFrom() {
		return emplSchFrom;
	}
	public void setEmplSchFrom(String emplSchFrom) {
		this.emplSchFrom = emplSchFrom;
	}
	public String getEmplSchTo() {
		return emplSchTo;
	}
	public void setEmplSchTo(String emplSchTo) {
		this.emplSchTo = emplSchTo;
	}
	public String getEmplIdNo() {
		return emplIdNo;
	}
	public void setEmplIdNo(String emplIdNo) {
		this.emplIdNo = emplIdNo;
	}
	public String getContStaffName() {
		return contStaffName;
	}
	public void setContStaffName(String contStaffName) {
		this.contStaffName = contStaffName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
