		package com.abm.mainet.securitymanagement.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;

public class DeploymentOfStaffDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long deplId;
	private String deplSeq;
	private String wfStatus;
	private Long  empTypeId;
	private String contStaffName;
	private Long locId;
	private Long fromLocId;
	private String contStaffMob;
	private String contStaffIdNo;
	private Long cpdShiftId;
	private Long fromCpdShiftId;
	private Date contStaffSchFrom;
	private Date contStaffSchTo;
	private String remarks;
	private Long vendorId;
	private Long createdBy;
	private Date createdDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long orgid;
	private Long updatedBy;
	private Date updatedDate;
	private String vendorDesc;
	private String locDesc;
	private String shiftDesc;
	private Long count;
	private String statusApproval;
	private String remarkApproval;
	private String messageDate;
	private String dayDesc;
	private EmployeeSchedulingDTO eDto=new EmployeeSchedulingDTO();
	private EmployeeSchedulingDetDTO emplDetDto;
	private List<EmployeeSchedulingDetDTO> emplDetDtoList;
	private List<TbLocationMas> location;
	private List<ContractualStaffMasterDTO> empNameList;
	private List<TbAcVendormaster> VendorList;
	
	public List<ContractualStaffMasterDTO> getEmpNameList() {
		return empNameList;
	}
	public void setEmpNameList(List<ContractualStaffMasterDTO> empNameList) {
		this.empNameList = empNameList;
	}
	public List<TbAcVendormaster> getVendorList() {
		return VendorList;
	}
	public void setVendorList(List<TbAcVendormaster> vendorList) {
		VendorList = vendorList;
	}
	public List<TbLocationMas> getLocation() {
		return location;
	}
	public void setLocation(List<TbLocationMas> location) {
		this.location = location;
	}
	public EmployeeSchedulingDTO geteDto() {
		return eDto;
	}
	public void seteDto(EmployeeSchedulingDTO eDto) {
		this.eDto = eDto;
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
	public String getDayDesc() {
		return dayDesc;
	}
	public void setDayDesc(String dayDesc) {
		this.dayDesc = dayDesc;
	}
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	public String getWfStatus() {
		return wfStatus;
	}
	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}
	public String getStatusApproval() {
		return statusApproval;
	}
	public void setStatusApproval(String statusApproval) {
		this.statusApproval = statusApproval;
	}
	public String getRemarkApproval() {
		return remarkApproval;
	}
	public void setRemarkApproval(String remarkApproval) {
		this.remarkApproval = remarkApproval;
	}
	public String getDeplSeq() {
		return deplSeq;
	}
	public void setDeplSeq(String deplSeq) {
		this.deplSeq = deplSeq;
	}
	public Long getFromLocId() {
		return fromLocId;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public void setFromLocId(Long fromLocId) {
		this.fromLocId = fromLocId;
	}
	public Long getFromCpdShiftId() {
		return fromCpdShiftId;
	}
	public void setFromCpdShiftId(Long fromCpdShiftId) {
		this.fromCpdShiftId = fromCpdShiftId;
	}
	public Long getDeplId() {
		return deplId;
	}
	public void setDeplId(Long deplId) {
		this.deplId = deplId;
	}
	public String getShiftDesc() {
		return shiftDesc;
	}
	public void setShiftDesc(String shiftDesc) {
		this.shiftDesc = shiftDesc;
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
	public String getContStaffIdNo() {
		return contStaffIdNo;
	}
	public void setContStaffIdNo(String contStaffIdNo) {
		this.contStaffIdNo = contStaffIdNo;
	}
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public Long getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(Long empTypeId) {
		this.empTypeId = empTypeId;
	}
	public String getContStaffName() {
		return contStaffName;
	}
	public void setContStaffName(String contStaffName) {
		this.contStaffName = contStaffName;
	}
	public Long getLocId() {
		return locId;
	}
	public void setLocId(Long locId) {
		this.locId = locId;
	}
	public String getContStaffMob() {
		return contStaffMob;
	}
	public void setContStaffMob(String contStaffMob) {
		this.contStaffMob = contStaffMob;
	}
	public Long getCpdShiftId() {
		return cpdShiftId;
	}
	public void setCpdShiftId(Long cpdShiftId) {
		this.cpdShiftId = cpdShiftId;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
