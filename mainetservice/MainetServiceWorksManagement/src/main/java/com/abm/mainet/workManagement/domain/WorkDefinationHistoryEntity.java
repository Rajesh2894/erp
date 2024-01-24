package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author hiren.poriya
 * @Since 08-Mar-2018
 */
@Entity
@Table(name = "TB_WMS_WORKDEFINATION_HIST")
public class WorkDefinationHistoryEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "WORK_ID_H")
	private Long workIdH;

	@Column(name = "WORK_ID")
	private Long workId;

	@Column(name = "WORK_NAME")
	private String workName;

	@Column(name = "PROJ_ID")
	private Long projId;

	@Temporal(TemporalType.DATE)
	@Column(name = "WORK_START_DATE")
	private Date workStartDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "WORK_END_DATE")
	private Date workEndDate;

	@Column(name = "WORK_TYPE")
	private Long workType;

	@Column(name = "WRK_SUBTPEID", nullable = true)
	private Long workSubType;

	@Temporal(TemporalType.DATE)
	@Column(name = "WORK_COMPLETION_DT")
	private Date workCompletionDate;

	@Column(name = "WORK_COMPLETION_NO", nullable = true)
	private String workCompletionNo;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "DP_DEPTID")
	private Long deptId;

	@Column(name = "WORK_PROJECT_PHASE")
	private Long workProjPhase;

	@Column(name = "LOC_ID_ST")
	private Long locIdSt;

	@Column(name = "LOC_ID_EN")
	private Long locIdEn;

	@Column(name = "WORK_CODE")
	private String workcode;

	@Column(name = "WORK_STATUS")
	private String workStatus;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@Column(name = "WORK_ESTAMT")
	private BigDecimal workEstAmt;

	@Column(name = "H_STATUS")
	private String hStatus;

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Date getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}

	public Date getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(Date workEndDate) {
		this.workEndDate = workEndDate;
	}

	public Long getWorkType() {
		return workType;
	}

	public void setWorkType(Long workType) {
		this.workType = workType;
	}

	public Long getWorkSubType() {
		return workSubType;
	}

	public void setWorkSubType(Long workSubType) {
		this.workSubType = workSubType;
	}

	public Date getWorkCompletionDate() {
		return workCompletionDate;
	}

	public void setWorkCompletionDate(Date workCompletionDate) {
		this.workCompletionDate = workCompletionDate;
	}

	public String getWorkCompletionNo() {
		return workCompletionNo;
	}

	public void setWorkCompletionNo(String workCompletionNo) {
		this.workCompletionNo = workCompletionNo;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getWorkProjPhase() {
		return workProjPhase;
	}

	public void setWorkProjPhase(Long workProjPhase) {
		this.workProjPhase = workProjPhase;
	}

	public Long getLocIdSt() {
		return locIdSt;
	}

	public void setLocIdSt(Long locIdSt) {
		this.locIdSt = locIdSt;
	}

	public Long getLocIdEn() {
		return locIdEn;
	}

	public void setLocIdEn(Long locIdEn) {
		this.locIdEn = locIdEn;
	}

	public String getWorkcode() {
		return workcode;
	}

	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public BigDecimal getWorkEstAmt() {
		return workEstAmt;
	}

	public void setWorkEstAmt(BigDecimal workEstAmt) {
		this.workEstAmt = workEstAmt;
	}

	public Long getWorkIdH() {
		return workIdH;
	}

	public void setWorkIdH(Long workIdH) {
		this.workIdH = workIdH;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_WORKDEFINATION_HIST", "WORK_ID_H" };
	}

}
