package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;

import com.abm.mainet.common.dto.ComplaintTypeBean;

/**
 * @author ritesh.patil
 *
 */
public class WorkFlowTypeGrid implements Serializable {

	private static final long serialVersionUID = -1131877224397064956L;
	private Long id;
	private String orgName;
	private String orgRegName;
	private String depName;
	private String deptRegName;
	private Long deptId;
	private Long orgId;
	private Long serviceId;
	private String serviceName;
	private String serviceNameReg;
	private Long compId;
	private String compDesc;
	private String workFlowMode;
	private String workflowType;
	private Long comSerId;
	private String comSerDesc;
	private String status;
	private ComplaintTypeBean complaint;
	private Long schemeId;
	private Long extIdentifier;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgRegName() {
		return orgRegName;
	}

	public void setOrgRegName(String orgRegName) {
		this.orgRegName = orgRegName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDeptRegName() {
		return deptRegName;
	}

	public void setDeptRegName(String deptRegName) {
		this.deptRegName = deptRegName;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
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

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public String getCompDesc() {
		return compDesc;
	}

	public void setCompDesc(String compDesc) {
		this.compDesc = compDesc;
	}

	public String getWorkFlowMode() {
		return workFlowMode;
	}

	public void setWorkFlowMode(String workFlowMode) {
		this.workFlowMode = workFlowMode;
	}

	public String getWorkflowType() {
		return workflowType;
	}

	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
	}

	public Long getComSerId() {
		return comSerId;
	}

	public void setComSerId(Long comSerId) {
		this.comSerId = comSerId;
	}

	public String getComSerDesc() {
		return comSerDesc;
	}

	public void setComSerDesc(String comSerDesc) {
		this.comSerDesc = comSerDesc;
	}

	public ComplaintTypeBean getComplaint() {
		return complaint;
	}

	public void setComplaint(ComplaintTypeBean complaint) {
		this.complaint = complaint;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return the extIdentifier
	 */
	public Long getExtIdentifier() {
		return extIdentifier;
	}

	/**
	 * @param extIdentifier the extIdentifier to set
	 */
	public void setExtIdentifier(Long extIdentifier) {
		this.extIdentifier = extIdentifier;
	}

}
