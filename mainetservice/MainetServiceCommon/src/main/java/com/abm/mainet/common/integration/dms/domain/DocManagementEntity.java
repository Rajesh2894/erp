package com.abm.mainet.common.integration.dms.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_RECORD_MANAGEMENT")
public class DocManagementEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DMS_ID", nullable = false)
	private Long dmsId;

	@OneToMany(mappedBy = "docManagementEntity", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<DocManagementDetEntity> docManagementDetEntity;

	@Column(name = "DMS_DOC_ID")
	private String dmsDocId;

	@Column(name = "DMS_DOC_NAME")
	private String dmsDocName;

	@Column(name = "DEPT_ID")
	private String deptId;

	@Column(name = "DOC_DATE")
	private Date docDate;

	@Column(name = "DOC_TYPE")
	private String docType;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "USER_ROLE_ID")
	private String userRoleId;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORGID")
	private Organisation orgId;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CREATED_BY")
	private Employee userId;

	@Column(name = "CREATED_DATE")
	private Date lmodDate;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UPDATED_BY")
	private Employee updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@Column(name = "STORAGE_TYPE")
	private String storageType;

	@Column(name = "ASSIGNED_TO")
	private String assignedTo;

	@Column(name = "ZONE")
	private Long zone;

	@Column(name = "WARD")
	private Long ward;

	@Column(name = "MOHALLA")
	private Long mohalla;

	@Column(name = "DOC_REF_NO")
	private String docRefNo;

	@Column(name = "ASSIGNED_DEPT")
	private String assignedDept;
	
	@Column(name = "SEQ_NO")
	private String seqNo;
	
	@Column(name = "WF_STATUS")
	private String wfStatus;
	
	@Column(name = "REMARKS")
	private String remark;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "RETEN_DAYS")
	private Long retDays;
	
	@Column(name = "DEL_DAYS")
	private Long delDays;
	
	@Column(name = "DOC_RTRVL_DAYS")
	private Long docRtrvlDays;
	
	@Column(name = "RET_DOC_ACTION_DT")
	private Date retDocActionDate;
	
	@Column(name = "DEL_DOC_ACTION_DT")
	private Date delDocActionDate;
	
	public Long getDmsId() {
		return dmsId;
	}

	public void setDmsId(Long dmsId) {
		this.dmsId = dmsId;
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

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Employee getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Employee updatedBy) {
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

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLangId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLangId(int langId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getIsDeleted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIsDeleted(String isDeleted) {
		// TODO Auto-generated method stub

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getPkValues() {

		return new String[] { "CFC", "TB_RECORD_MANAGEMENT", "DMS_ID" };
	}

	public Long getRetDays() {
		return retDays;
	}

	public void setRetDays(Long retDays) {
		this.retDays = retDays;
	}

	public Long getDelDays() {
		return delDays;
	}

	public void setDelDays(Long delDays) {
		this.delDays = delDays;
	}

	public List<DocManagementDetEntity> getDocManagementDetEntity() {
		return docManagementDetEntity;
	}

	public void setDocManagementDetEntity(List<DocManagementDetEntity> docManagementDetEntity) {
		this.docManagementDetEntity = docManagementDetEntity;
	}

	public Long getDocRtrvlDays() {
		return docRtrvlDays;
	}

	public void setDocRtrvlDays(Long docRtrvlDays) {
		this.docRtrvlDays = docRtrvlDays;
	}

	public Date getRetDocActionDate() {
		return retDocActionDate;
	}

	public void setRetDocActionDate(Date retDocActionDate) {
		this.retDocActionDate = retDocActionDate;
	}

	public Date getDelDocActionDate() {
		return delDocActionDate;
	}

	public void setDelDocActionDate(Date delDocActionDate) {
		this.delDocActionDate = delDocActionDate;
	}

}
