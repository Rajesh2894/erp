package com.abm.mainet.audit.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.audit.constant.IAuditConstants;
@Entity
@Table(name = IAuditConstants.AUDIT_HISTORY_TABLE)
public class AuditParaEntryHistoryEntity {
	
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "AUDIT_PARA_ID_HIST", unique = true, nullable = false)
	private Long auditParaIdHis;
	
	@Column(name = "AUDIT_PARA_ID")
	private Long auditParaId;
    
    // Need to be discussed
	@Column(name = "AUDIT_PARA_CODE")
	private String auditParaCode;
	
    @Column(name = "HIST_STATUS", length = 1)
	private String histStatus;	
	
	public Long getAuditParaIdHis() {
		return auditParaIdHis;
	}

	public String getHistStatus() {
		return histStatus;
	}

	public void setAuditParaIdHis(Long auditParaIdHis) {
		this.auditParaIdHis = auditParaIdHis;
	}

	public void setHistStatus(String histStatus) {
		this.histStatus = histStatus;
	}

	@Temporal(value = TemporalType.DATE)
    @Column(name="AUDIT_ENTRY_DATE")
	private Date auditEntryDate;
	
    @Column(name="AUDIT_TYPE")
	private Long auditType;
	
    @Column(name="AUDIT_SEVERITY")
	private Long auditSeverity;
	
    @Column(name="AUDIT_PARA_SUB")
	private String auditParaSub;
	
    @Column(name="AUDIT_WORK_NAME")
	private String auditWorkName;
	
    @Column(name="AUDIT_CNTRCTR_NAME")
	private String auditContractorName;

    @Column(name="AUDIT_PARA_DESC")
	private String auditParaDesc;
	
    @Column(name="AUDIT_DEPT_ID")
	private Long auditDeptId;
	
    @Column(name="AUDIT_PARA_STATUS")
	private Long auditParaStatus;
	
    @Column(name="AUDIT_WARD1")
	private Long auditWard1;
	
    @Column(name="AUDIT_WARD2")
	private Long auditWard2;
	
    @Column(name="AUDIT_WARD3")
	private Long auditWard3;
	
    @Column(name="AUDIT_WARD4")
	private Long auditWard4;
	
    @Column(name="AUDIT_WARD5")
	private Long auditWard5;
	
    @Column(name="AUDIT_PARA_SEND_TO")
	private Long auditParaSendTo;
	
    @Column(name="AUDIT_PARA_CHK")
	private Long auditParaChk;
	
    @Column(name="AUDIT_PARA_YEAR")
	private Long auditParaYear;
    
    @Column(name="WFSTATUS")
   	private String auditWfStatus;
	
	@Column(name="AUDIT_DEPT_FEEDBK")
	private String auditDeptFeedback;
	
    @Column(name="CLOSER_REMARKS")
	private String closerRemarks;
	
    @Column(name="AUDIT_DATE")
   	private Date auditDate;
    
    @Column(name="ORGID")
	private Long orgId;
    
    @Column(name="CREATED_BY")
    private Long createdBy;
	
    @Column(name="CREATED_DATE")
	private Date createdDate;
	
    @Column(name="UPDATED_BY")
	private Long updatedBy;
	
    @Column(name="UPDATED_DATE")
	private Date updatedDate;
	
    @Column(name="LG_IP_MAC")
	private String lgIpMac;
	
    @Column(name="LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	public Long getAuditParaId() {
		return auditParaId;
	}
	
	public void setAuditParaId(Long auditParaId) {
		this.auditParaId = auditParaId;
	}

	public String getAuditParaCode() {
		return auditParaCode;
	}

	public void setAuditParaCode(String auditParaCode) {
		this.auditParaCode = auditParaCode;
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
	
    public String getAuditWfStatus() {
		return auditWfStatus;
	}

	public void setAuditWfStatus(String auditWfStatus) {
		this.auditWfStatus = auditWfStatus;
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
		
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String[] getPkValues() {
        return new String[] { "AUD", IAuditConstants.AUDIT_HISTORY_TABLE, IAuditConstants.AUDIT_HISTORY_PRIMARY_KEY };
    }

}
