/**
 * 
 */
package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 *
 */
public class WorkflowDetDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long wfdId;
	private Long eventId;
	private Long mapOrgId;
	private Long mapDeptId;
	private String roleType;
	private String sla;
	private Long unit;
	private String status;
	private Long currentOrgId;
	private Long createdBy;
	private Date  createDate;
	private Long updatedBy;
	private Date updatedDate;
	@JsonIgnore
    @Size(max=100)
	private String	lgIpMac;
	@JsonIgnore
    @Size(max=100)
	private String	lgIpMacUpd;
	private int apprCount;
	
	private String mapOrgName;
	private String mapDeptName;
	
	private String roleOrEmpIds;
	
	List<Object[]> mapDeptList;
	List<Object[]> mapEmpList;
	List<Object[]> mapRoleList;
	
	private String hiddenRoleOrEmpId;
	private String roleOrEmployeeDesc;

	
	
	public Long getWfdId() {
		return wfdId;
	}
	public void setWfdId(Long wfdId) {
		this.wfdId = wfdId;
	}


	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getSla() {
		return sla;
	}
	public void setSla(String sla) {
		this.sla = sla;
	}
	public Long getUnit() {
		return unit;
	}
	public void setUnit(Long unit) {
		this.unit = unit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public int getApprCount() {
		return apprCount;
	}
	public void setApprCount(int apprCount) {
		this.apprCount = apprCount;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getCurrentOrgId() {
		return currentOrgId;
	}
	public void setCurrentOrgId(Long currentOrgId) {
		this.currentOrgId = currentOrgId;
	}
	public Long getMapOrgId() {
		return mapOrgId;
	}
	public void setMapOrgId(Long mapOrgId) {
		this.mapOrgId = mapOrgId;
	}
	public Long getMapDeptId() {
		return mapDeptId;
	}
	public void setMapDeptId(Long mapDeptId) {
		this.mapDeptId = mapDeptId;
	}
	public String getRoleOrEmpIds() {
		return roleOrEmpIds;
	}
	public void setRoleOrEmpIds(String roleOrEmpIds) {
		this.roleOrEmpIds = roleOrEmpIds;
	}
	public String getMapOrgName() {
		return mapOrgName;
	}
	public void setMapOrgName(String mapOrgName) {
		this.mapOrgName = mapOrgName;
	}
	public String getMapDeptName() {
		return mapDeptName;
	}
	public void setMapDeptName(String mapDeptName) {
		this.mapDeptName = mapDeptName;
	}
	public List<Object[]> getMapDeptList() {
		return mapDeptList;
	}
	public void setMapDeptList(List<Object[]> mapDeptList) {
		this.mapDeptList = mapDeptList;
	}
	public List<Object[]> getMapEmpList() {
		return mapEmpList;
	}
	public void setMapEmpList(List<Object[]> mapEmpList) {
		this.mapEmpList = mapEmpList;
	}
	public List<Object[]> getMapRoleList() {
		return mapRoleList;
	}
	public void setMapRoleList(List<Object[]> mapRoleList) {
		this.mapRoleList = mapRoleList;
	}
	public String getHiddenRoleOrEmpId() {
		return hiddenRoleOrEmpId;
	}
	public void setHiddenRoleOrEmpId(String hiddenRoleOrEmpId) {
		this.hiddenRoleOrEmpId = hiddenRoleOrEmpId;
	}
	public String getRoleOrEmployeeDesc() {
		return roleOrEmployeeDesc;
	}
	public void setRoleOrEmployeeDesc(String roleOrEmployeeDesc) {
		this.roleOrEmployeeDesc = roleOrEmployeeDesc;
	}

	
	
}
