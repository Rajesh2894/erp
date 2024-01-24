package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author vishwajeet.kumar
 * @since 22 March 2018
 */
@Entity
@Table(name = "TB_WMS_MILESTONE_MAST")
public class MileStone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MILE_ID", nullable = false)
	private Long mileId;

	/*
	 * @Column(name = "WORK_ID", nullable = true) private Long workId;
	 */

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WORK_ID", referencedColumnName = "WORK_ID")
	private WorkDefinationEntity mastDetailsEntity;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROJ_ID", referencedColumnName = "PROJ_ID")
	private TbWmsProjectMaster projectMaster;

	/*
	 * @Column(name = "PROJ_ID", nullable = false) private Long projId;
	 */

	@Column(name = "milestone_id", nullable = true)
	private Long mileStoneId;

	@Column(name = "MILE_DESCRIPTION", nullable = true)
	private String mileStoneDesc;

	@Column(name = "MILE_WEIGHTAGE", nullable = false)
	private BigDecimal mileStoneWeight;

	@Column(name = "MILE_STARTDATE", nullable = false)
	private Date msStartDate;

	@Column(name = "MILE_ENDDATE", nullable = false)
	private Date msEndDate;

	@Column(name = "MILE_PERCENTAGE", nullable = false)
	private BigDecimal msPercent;

	@Column(name = "MILE_TYPE", nullable = false)
	private String mileStoneType;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	public Long getMileId() {
		return mileId;
	}

	public void setMileId(Long mileId) {
		this.mileId = mileId;
	}

	/*
	 * public Long getWorkId() { return workId; } public void setWorkId(Long workId)
	 * { this.workId = workId; }
	 */

	/*
	 * public Long getProjId() { return projId; } public void setProjId(Long projId)
	 * { this.projId = projId; }
	 */
	public String getMileStoneDesc() {
		return mileStoneDesc;
	}

	public void setMileStoneDesc(String mileStoneDesc) {
		this.mileStoneDesc = mileStoneDesc;
	}

	public BigDecimal getMileStoneWeight() {
		return mileStoneWeight;
	}

	public void setMileStoneWeight(BigDecimal mileStoneWeight) {
		this.mileStoneWeight = mileStoneWeight;
	}

	public Date getMsStartDate() {
		return msStartDate;
	}

	public void setMsStartDate(Date msStartDate) {
		this.msStartDate = msStartDate;
	}

	public Date getMsEndDate() {
		return msEndDate;
	}

	public void setMsEndDate(Date msEndDate) {
		this.msEndDate = msEndDate;
	}

	public BigDecimal getMsPercent() {
		return msPercent;
	}

	public void setMsPercent(BigDecimal msPercent) {
		this.msPercent = msPercent;
	}

	public String getMileStoneType() {
		return mileStoneType;
	}

	public void setMileStoneType(String mileStoneType) {
		this.mileStoneType = mileStoneType;
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

	public WorkDefinationEntity getMastDetailsEntity() {
		return mastDetailsEntity;
	}

	public void setMastDetailsEntity(WorkDefinationEntity mastDetailsEntity) {
		this.mastDetailsEntity = mastDetailsEntity;
	}

	public TbWmsProjectMaster getProjectMaster() {
		return projectMaster;
	}

	public void setProjectMaster(TbWmsProjectMaster projectMaster) {
		this.projectMaster = projectMaster;
	}

	public Long getMileStoneId() {
		return mileStoneId;
	}

	public void setMileStoneId(Long mileStoneId) {
		this.mileStoneId = mileStoneId;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_MILESTONE_MAST", "MILE_ID" };
	}
}
