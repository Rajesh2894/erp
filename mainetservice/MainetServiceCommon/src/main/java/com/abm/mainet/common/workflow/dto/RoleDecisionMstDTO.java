package com.abm.mainet.common.workflow.dto;

import java.util.List;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;

public class RoleDecisionMstDTO {
	
    private static final long serialVersionUID = 2909676960079814154L;
    private long roleDecisionId;
    private String decisionName;
    private String decisionNameReg;
    private String serviceName;
    private String serviceNameReg;
    private Long serviceId;
    private String isActive;
    private Long smServiceId;
    private Department deptId;
    private Long roleId;
    private Organisation organisation;
    private List<String> decisionMapId;
    private List<String> decisionMapSelectedId;
    
    
    
	public long getRoleDecisionId() {
		return roleDecisionId;
	}
	public void setRoleDecisionId(long roleDecisionId) {
		this.roleDecisionId = roleDecisionId;
	}
	public String getDecisionName() {
		return decisionName;
	}
	public void setDecisionName(String decisionName) {
		this.decisionName = decisionName;
	}
	public String getDecisionNameReg() {
		return decisionNameReg;
	}
	public void setDecisionNameReg(String decisionNameReg) {
		this.decisionNameReg = decisionNameReg;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceNameReg() {
		return serviceNameReg;
	}
	public void setServiceNameReg(String serviceNameReg) {
		this.serviceNameReg = serviceNameReg;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Long getSmServiceId() {
		return smServiceId;
	}
	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}
	public Department getDeptId() {
		return deptId;
	}
	public void setDeptId(Department deptId) {
		this.deptId = deptId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Organisation getOrganisation() {
		return organisation;
	}
	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	public List<String> getDecisionMapId() {
		return decisionMapId;
	}
	public void setDecisionMapId(List<String> decisionMapId) {
		this.decisionMapId = decisionMapId;
	}
	public List<String> getDecisionMapSelectedId() {
		return decisionMapSelectedId;
	}
	public void setDecisionMapSelectedId(List<String> decisionMapSelectedId) {
		this.decisionMapSelectedId = decisionMapSelectedId;
	}
	
	
}
