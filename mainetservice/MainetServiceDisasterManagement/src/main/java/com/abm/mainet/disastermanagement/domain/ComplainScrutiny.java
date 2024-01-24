package com.abm.mainet.disastermanagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Complain Scrutiny
 */

@Entity
@Table(name = "tb_dm_complain_scrutiny")
public class ComplainScrutiny implements Serializable {

	private static final long serialVersionUID = 4487850785958071747L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "scrutiny_id", nullable = false)
	private Long scrutinyId;
	
	@Column(name = "complain_id", nullable = false)
	private Long complainId;

	@Column(name = "complain_no")
	private String complainNo;

	@Column(name = "DP_DEPTID")
	private Long deptId;
	
//    @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
//    private Department tbDepartment;
	
	@Column(name = "complain_scrutiny_status")
	private String complainScrutinyStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="complain_Scrutiny_date")
	private Date complainScrutinyDate;

	@Column(name = "complaint_remark")
	private String complaintRemark;
	
	@Column(name = "orgid")
	private Long orgid;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "LANG_ID")
	private int langId;

	@Column(name = "created_by")
	private Long createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "lg_ip_mac")
	private String lgIpMac;

	@Column(name = "lg_ip_mac_upd")
	private String lgIpMacUpd;
	
	@Transient
	private String complainDesc;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "call_attend_date")
	private Date callAttendDate;

	@Column(name = "call_attend_time")
	private String callAttendTime;
	
	@Column(name = "call_attend_employee", length = 200)
	private String callAttendEmployee;

	@Column(name = "reason_for_delay", length = 1000)
	private String reasonForDelay;
	
	
	public String getComplainDesc() {
		return complainDesc;
	}

	public void setComplainDesc(String complainDesc) {
		this.complainDesc = complainDesc;
	}

	// Getter & Setter
	public Long getScrutinyId() {
		return scrutinyId;
	}

	public void setScrutinyId(Long scrutinyId) {
		this.scrutinyId = scrutinyId;
	}

	public Long getComplainId() {
		return complainId;
	}

	public void setComplainId(Long complainId) {
		this.complainId = complainId;
	}

	public String getComplainNo() {
		return complainNo;
	}

	public void setComplainNo(String complainNo) {
		this.complainNo = complainNo;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
//	public Department getTbDepartment() {
//		return tbDepartment;
//	}
//
//	public void setTbDepartment(Department tbDepartment) {
//		this.tbDepartment = tbDepartment;
//	}
	


	public Date getComplainScrutinyDate() {
		return complainScrutinyDate;
	}

	public String getComplainScrutinyStatus() {
		return complainScrutinyStatus;
	}

	public void setComplainScrutinyStatus(String complainScrutinyStatus) {
		this.complainScrutinyStatus = complainScrutinyStatus;
	}

	public void setComplainScrutinyDate(Date complainScrutinyDate) {
		this.complainScrutinyDate = complainScrutinyDate;
	}

	public String getComplaintRemark() {
		return complaintRemark;
	}

	public void setComplaintRemark(String complaintRemark) {
		this.complaintRemark = complaintRemark;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
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

	public Date getCallAttendDate() {
		return callAttendDate;
	}

	public void setCallAttendDate(Date callAttendDate) {
		this.callAttendDate = callAttendDate;
	}

	public String getCallAttendTime() {
		return callAttendTime;
	}

	public void setCallAttendTime(String callAttendTime) {
		this.callAttendTime = callAttendTime;
	}

	public String getCallAttendEmployee() {
		return callAttendEmployee;
	}

	public void setCallAttendEmployee(String callAttendEmployee) {
		this.callAttendEmployee = callAttendEmployee;
	}

	public String getReasonForDelay() {
		return reasonForDelay;
	}

	public void setReasonForDelay(String reasonForDelay) {
		this.reasonForDelay = reasonForDelay;
	}
	public String[] getPkValues() {
		return new String[] { "DM", "tb_dm_complain_scrutiny", "scrutiny_id" };
	}
	
	
}
