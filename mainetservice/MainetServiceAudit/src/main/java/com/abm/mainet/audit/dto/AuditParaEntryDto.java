package com.abm.mainet.audit.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AuditParaEntryDto {
	
	private String auditParaCode;
	
	private Long auditParaId;
	
	private Date auditEntryDate;
	
	private Long auditType;
	
	private Long auditSeverity;
	
	private String auditParaSub;
	
	private String auditWorkName;
	
	private String auditContractorName;

	private String auditParaDesc;
	
	private Long auditDeptId;
	
	private Long auditParaStatus;
	
	private Long auditWard;
	
	private String auditWardDesc;
	
	private Long auditWard1;
	
	private Long auditWard2;
	
	private Long auditWard3;
	
	private Long auditWard4;
	
	private Long auditWard5;
	
	private Long auditParaSendTo;
	
	private Long auditParaChk;
	
	private Long auditParaYear;
	
	private String auditDeptFeedback;
	
	private String closerRemarks;
	
	private Date auditDate;
	
	private Long orgId;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Long updatedBy;
	
	private Date updatedDate;
	
	private String lgIpMac;
	
	private String lgIpMacUpd;
	
	// Non Table fields
	
	private String auditDeptStr;
	
	private String auditZoneStr;
	
	private String mode;
	
	private String auditStatusStr;
	
	private String auditWfStatus;
	
	private String forwardToDept;
	
	private String removeFileById;
	
	private Long auditParaTOYear;
	
	private Integer categoryId;
	
	private BigDecimal recAmt;
	
	private String auditAppendix;
	
	private Integer subUnit;
	
	private Integer subUnitClosed;
	
	private String auditDateDesc;
	
	private String subUnitCompPending;
	
	private String subUnitCompDone;
	
	private List<Long> attacheMentIds;
	
	

	public String getForwardToDept() {
		return forwardToDept;
	}

	public void setForwardToDept(String forwardToDept) {
		this.forwardToDept = forwardToDept;
	}

	public String getAuditStatusStr() {
		return auditStatusStr;
	}

	public void setAuditStatusStr(String auditStatusStr) {
		this.auditStatusStr = auditStatusStr;
	}

	public String getAuditParaCode() {
		return auditParaCode;
	}

	public void setAuditParaCode(String auditParaCode) {
		this.auditParaCode = auditParaCode;
	}

	public Long getAuditParaId() {
		return auditParaId;
	}

	public void setAuditParaId(Long auditParaId) {
		this.auditParaId = auditParaId;
	}

	public Date getAuditEntryDate() {
		return auditEntryDate;
	}

	public void setAuditEntryDate(Date auditEntryDate) {
		this.auditEntryDate = auditEntryDate;
	}

	public Long getAuditType() {
		return auditType;
	}

	public void setAuditType(Long auditType) {
		this.auditType = auditType;
	}

	public Long getAuditSeverity() {
		return auditSeverity;
	}

	public void setAuditSeverity(Long auditSeverity) {
		this.auditSeverity = auditSeverity;
	}

	public String getAuditParaSub() {
		return auditParaSub;
	}

	public void setAuditParaSub(String auditParaSub) {
		this.auditParaSub = auditParaSub;
	}

	public String getAuditWorkName() {
		return auditWorkName;
	}

	public void setAuditWorkName(String auditWorkName) {
		this.auditWorkName = auditWorkName;
	}

	public String getAuditContractorName() {
		return auditContractorName;
	}

	public void setAuditContractorName(String auditContractorName) {
		this.auditContractorName = auditContractorName;
	}

	public String getAuditParaDesc() {
		return auditParaDesc;
	}

	public void setAuditParaDesc(String auditParaDesc) {
		this.auditParaDesc = auditParaDesc;
	}

	public Long getAuditDeptId() {
		return auditDeptId;
	}

	public void setAuditDeptId(Long auditDeptId) {
		this.auditDeptId = auditDeptId;
	}

	public Long getAuditParaStatus() {
		return auditParaStatus;
	}

	public void setAuditParaStatus(Long auditParaStatus) {
		this.auditParaStatus = auditParaStatus;
	}

	public Long getAuditWard1() {
		return auditWard1;
	}

	public void setAuditWard1(Long auditWard1) {
		this.auditWard1 = auditWard1;
	}

	public Long getAuditWard2() {
		return auditWard2;
	}

	public void setAuditWard2(Long auditWard2) {
		this.auditWard2 = auditWard2;
	}

	public Long getAuditWard3() {
		return auditWard3;
	}

	public void setAuditWard3(Long auditWard3) {
		this.auditWard3 = auditWard3;
	}

	public Long getAuditWard4() {
		return auditWard4;
	}

	public void setAuditWard4(Long auditWard4) {
		this.auditWard4 = auditWard4;
	}

	public Long getAuditWard5() {
		return auditWard5;
	}

	public void setAuditWard5(Long auditWard5) {
		this.auditWard5 = auditWard5;
	}

	public Long getAuditParaSendTo() {
		return auditParaSendTo;
	}

	public void setAuditParaSendTo(Long auditParaSendTo) {
		this.auditParaSendTo = auditParaSendTo;
	}

	public Long getAuditParaChk() {
		return auditParaChk;
	}

	public void setAuditParaChk(Long auditParaChk) {
		this.auditParaChk = auditParaChk;
	}

	public Long getAuditParaYear() {
		return auditParaYear;
	}

	public void setAuditParaYear(Long auditParaYear) {
		this.auditParaYear = auditParaYear;
	}

	public String getAuditDeptFeedback() {
		return auditDeptFeedback;
	}

	public void setAuditDeptFeedback(String auditDeptFeedback) {
		this.auditDeptFeedback = auditDeptFeedback;
	}

	public String getCloserRemarks() {
		return closerRemarks;
	}

	public void setCloserRemarks(String closerRemarks) {
		this.closerRemarks = closerRemarks;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public String getAuditDeptStr() {
		return auditDeptStr;
	}

	public void setAuditDeptStr(String auditDeptStr) {
		this.auditDeptStr = auditDeptStr;
	}

	public String getAuditZoneStr() {
		return auditZoneStr;
	}

	public void setAuditZoneStr(String auditZoneStr) {
		this.auditZoneStr = auditZoneStr;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getAuditWfStatus() {
		return auditWfStatus;
	}

	public void setAuditWfStatus(String auditWfStatus) {
		this.auditWfStatus = auditWfStatus;
	}

	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Long getAuditParaTOYear() {
		return auditParaTOYear;
	}

	public void setAuditParaTOYear(Long auditParaTOYear) {
		this.auditParaTOYear = auditParaTOYear;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getRecAmt() {
		return recAmt;
	}

	public void setRecAmt(BigDecimal recAmt) {
		this.recAmt = recAmt;
	}

	public Long getAuditWard() {
		return auditWard;
	}

	public void setAuditWard(Long auditWard) {
		this.auditWard = auditWard;
	}

	public String getAuditWardDesc() {
		return auditWardDesc;
	}

	public void setAuditWardDesc(String auditWardDesc) {
		this.auditWardDesc = auditWardDesc;
	}

	public String getAuditAppendix() {
		return auditAppendix;
	}

	public void setAuditAppendix(String auditAppendix) {
		this.auditAppendix = auditAppendix;
	}

	public Integer getSubUnit() {
		return subUnit;
	}

	public void setSubUnit(Integer subUnit) {
		this.subUnit = subUnit;
	}

	public Integer getSubUnitClosed() {
		return subUnitClosed;
	}

	public void setSubUnitClosed(Integer subUnitClosed) {
		this.subUnitClosed = subUnitClosed;
	}

	public String getAuditDateDesc() {
		return auditDateDesc;
	}

	public void setAuditDateDesc(String auditDateDesc) {
		this.auditDateDesc = auditDateDesc;
	}

	public String getSubUnitCompPending() {
		return subUnitCompPending;
	}

	public void setSubUnitCompPending(String subUnitCompPending) {
		this.subUnitCompPending = subUnitCompPending;
	}

	public String getSubUnitCompDone() {
		return subUnitCompDone;
	}

	public void setSubUnitCompDone(String subUnitCompDone) {
		this.subUnitCompDone = subUnitCompDone;
	}

	public List<Long> getAttacheMentIds() {
		return attacheMentIds;
	}

	public void setAttacheMentIds(List<Long> attacheMentIds) {
		this.attacheMentIds = attacheMentIds;
	}
	
	
}
