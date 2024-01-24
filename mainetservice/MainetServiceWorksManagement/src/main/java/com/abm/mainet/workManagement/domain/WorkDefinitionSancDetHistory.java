package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
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
 * @author vishwajeet.kumar
 * @since 30 April 2018
 */
@Entity
@Table(name = "TB_WMS_SANCTION_DET_HIST")
public class WorkDefinitionSancDetHistory implements Serializable {

	private static final long serialVersionUID = -8891780551508879613L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "WORK_SACTID_H")
	private Long workSancIdH;

	@Column(name = "WORK_SACTID", nullable = false)
	private Long workSancId;

	@Column(name = "WORK_ID")
	private Long workId;

	@Column(name = "DP_DEPTID")
	private Long deptId;

	@Column(name = "WORK_SACNO")
	private String workSancNo;

	@Column(name = "WORK_SACDATE")
	private Date workSancDate;

	@Column(name = "WORK_SACBY")
	private String workSancBy;

	@Column(name = "WORK_SACDSG")
	private String workDesignBy;

	@Column(name = "ORGID", nullable = false)
	private Long orgid;

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	private Long createdBy;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date CreatedDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "H_STATUS", length = 1)
	private String status;

	public Long getWorkSancId() {
		return workSancId;
	}

	public void setWorkSancId(Long workSancId) {
		this.workSancId = workSancId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getWorkSancNo() {
		return workSancNo;
	}

	public void setWorkSancNo(String workSancNo) {
		this.workSancNo = workSancNo;
	}

	public Date getWorkSancDate() {
		return workSancDate;
	}

	public void setWorkSancDate(Date workSancDate) {
		this.workSancDate = workSancDate;
	}

	public String getWorkSancBy() {
		return workSancBy;
	}

	public void setWorkSancBy(String workSancBy) {
		this.workSancBy = workSancBy;
	}

	public String getWorkDesignBy() {
		return workDesignBy;
	}

	public void setWorkDesignBy(String workDesignBy) {
		this.workDesignBy = workDesignBy;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
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

	public Long getWorkSancIdH() {
		return workSancIdH;
	}

	public void setWorkSancIdH(Long workSancIdH) {
		this.workSancIdH = workSancIdH;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_SANCTION_DET_HIST", "WORK_SACTID_H" };
	}
}
