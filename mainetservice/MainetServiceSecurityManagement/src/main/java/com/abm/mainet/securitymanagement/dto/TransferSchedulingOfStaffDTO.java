package com.abm.mainet.securitymanagement.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TransferSchedulingOfStaffDTO implements Serializable{

	/**
	 * Arun Shinde
	 */
	private static final long serialVersionUID = 1L;
	private Long transferId;
	private Long locId;
	private Long cpdShiftId;
	private Long vendorId;
	private String contStaffName;
	private String contStaffIdNo;
	private Date contStaffSchFrom;
	private Date contStaffSchTo;
	private Long dayPrefixId;
	private Long  empTypeId;
	private String reasonTransfer;
	private String remarks;
	private Long createdBy;
	private Date createdDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long orgid;
	private Long updatedBy;
	private Date updatedDate;
	private String memberSelected;
	private Long count;
	private String messageDate;
	private String dayDesc;
	private EmployeeSchedulingDetDTO emplDetDto;
	private List<EmployeeSchedulingDetDTO> emplDetDtoList;
	
	public String getDayDesc() {
		return dayDesc;
	}
	public void setDayDesc(String dayDesc) {
		this.dayDesc = dayDesc;
	}
	public EmployeeSchedulingDetDTO getEmplDetDto() {
		return emplDetDto;
	}
	public void setEmplDetDto(EmployeeSchedulingDetDTO emplDetDto) {
		this.emplDetDto = emplDetDto;
	}
	public List<EmployeeSchedulingDetDTO> getEmplDetDtoList() {
		return emplDetDtoList;
	}
	public void setEmplDetDtoList(List<EmployeeSchedulingDetDTO> emplDetDtoList) {
		this.emplDetDtoList = emplDetDtoList;
	}
	public String getMemberSelected() {
		return memberSelected;
	}
	public void setMemberSelected(String memberSelected) {
		this.memberSelected = memberSelected;
	}
	
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Long getTransferId() {
		return transferId;
	}
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}
	public String getReasonTransfer() {
		return reasonTransfer;
	}
	public void setReasonTransfer(String reasonTransfer) {
		this.reasonTransfer = reasonTransfer;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(Long empTypeId) {
		this.empTypeId = empTypeId;
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
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public String getContStaffName() {
		return contStaffName;
	}
	public void setContStaffName(String contStaffName) {
		this.contStaffName = contStaffName;
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
	public Long getDayPrefixId() {
		return dayPrefixId;
	}
	public void setDayPrefixId(Long dayPrefixId) {
		this.dayPrefixId = dayPrefixId;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
