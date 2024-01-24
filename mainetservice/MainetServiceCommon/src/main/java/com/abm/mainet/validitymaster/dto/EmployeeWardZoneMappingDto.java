/**
 * 
 */
package com.abm.mainet.validitymaster.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

/**
 * @author cherupelli.srikanth
 * @since 29 Nov 2021
 */
public class EmployeeWardZoneMappingDto implements Serializable{

	private static final long serialVersionUID = 1L;

    private Long empLocId;
	
	private Long empId;
	
	private Long orgId;
	
	private Date  createdDate;
	
	private Long createdBy;
	
	private String lgIpMac;
	
	private Date updatedDate;
	
	private Long updatedBy;
	
	private String lgIpMacUpd;
	
	private String empName;
	
    private List<EmployeeWardZoneMappingDetailDto> wardZoneDetalList = new ArrayList<>(0);

	public Long getEmpLocId() {
		return empLocId;
	}

	public void setEmpLocId(Long empLocId) {
		this.empLocId = empLocId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public List<EmployeeWardZoneMappingDetailDto> getWardZoneDetalList() {
		return wardZoneDetalList;
	}

	public void setWardZoneDetalList(List<EmployeeWardZoneMappingDetailDto> wardZoneDetalList) {
		this.wardZoneDetalList = wardZoneDetalList;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
}
