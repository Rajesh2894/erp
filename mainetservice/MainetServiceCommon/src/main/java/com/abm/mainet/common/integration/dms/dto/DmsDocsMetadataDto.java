package com.abm.mainet.common.integration.dms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class DmsDocsMetadataDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long dmsId;

	private List<DmsDocsMetadataDetDto> dmsDocsMetadataDetList;

	private String dmsDocId;

	private String dmsDocName;

	private String deptId;

	private Date docDate;

	private String docType;

	private String isActive;

	private String userRoleId;

	private Organisation orgId;

	private Employee userId;

	private Date lmodDate;

	private Employee updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String storageType;

	private String assignedTo;

	private Long zone;

	private Long ward;

	private Long mohalla;

	private String docRefNo;

	private String assignedDept;

	private String seqNo;

	private String wfStatus;

	private String statusApproval;

	private String remark;

	private String kms;

	private String employeeIds;

	private String roleIds;

	private String assignDeptIds;

	private String knowledgeSharing;

	private Long ward1;

	private Long ward2;

	private Long ward3;

	private String deptCode;
	
	private String status;

	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	private Map<String, String> dmsServiceMap = new HashMap<String, String>(); 
	
	public Long getDmsId() {
		return dmsId;
	}

	public void setDmsId(Long dmsId) {
		this.dmsId = dmsId;
	}

	public List<DmsDocsMetadataDetDto> getDmsDocsMetadataDetList() {
		return dmsDocsMetadataDetList;
	}

	public void setDmsDocsMetadataDetList(List<DmsDocsMetadataDetDto> dmsDocsMetadataDetList) {
		this.dmsDocsMetadataDetList = dmsDocsMetadataDetList;
	}

	public String getDmsDocId() {
		return dmsDocId;
	}

	public void setDmsDocId(String dmsDocId) {
		this.dmsDocId = dmsDocId;
	}

	public String getDmsDocName() {
		return dmsDocName;
	}

	public void setDmsDocName(String dmsDocName) {
		this.dmsDocName = dmsDocName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
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

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public Long getZone() {
		return zone;
	}

	public void setZone(Long zone) {
		this.zone = zone;
	}

	public Long getWard() {
		return ward;
	}

	public void setWard(Long ward) {
		this.ward = ward;
	}

	public Long getMohalla() {
		return mohalla;
	}

	public void setMohalla(Long mohalla) {
		this.mohalla = mohalla;
	}

	public String getDocRefNo() {
		return docRefNo;
	}

	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}

	public String getAssignedDept() {
		return assignedDept;
	}

	public void setAssignedDept(String assignedDept) {
		this.assignedDept = assignedDept;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public String getKms() {
		return kms;
	}

	public void setKms(String kms) {
		this.kms = kms;
	}

	public String getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(String employeeIds) {
		this.employeeIds = employeeIds;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getAssignDeptIds() {
		return assignDeptIds;
	}

	public void setAssignDeptIds(String assignDeptIds) {
		this.assignDeptIds = assignDeptIds;
	}

	public String getKnowledgeSharing() {
		return knowledgeSharing;
	}

	public void setKnowledgeSharing(String knowledgeSharing) {
		this.knowledgeSharing = knowledgeSharing;
	}

	public Long getWard1() {
		return ward1;
	}

	public void setWard1(Long ward1) {
		this.ward1 = ward1;
	}

	public Long getWard2() {
		return ward2;
	}

	public void setWard2(Long ward2) {
		this.ward2 = ward2;
	}

	public Long getWard3() {
		return ward3;
	}

	public void setWard3(Long ward3) {
		this.ward3 = ward3;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Organisation getOrgId() {
		return orgId;
	}

	public void setOrgId(Organisation orgId) {
		this.orgId = orgId;
	}

	public Employee getUserId() {
		return userId;
	}

	public void setUserId(Employee userId) {
		this.userId = userId;
	}

	public Employee getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Employee updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Map<String, String> getDmsServiceMap() {
		return dmsServiceMap;
	}

	public void setDmsServiceMap(Map<String, String> dmsServiceMap) {
		this.dmsServiceMap = dmsServiceMap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
		
}
