package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_WMS_PROJECT_MAST_HIST")
public class TbWmsProjectMasterHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7125580338527287274L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "PROJ_ID_H", precision = 12, scale = 0, nullable = false)
	private Long projIdHist;

	@Column(name = "PROJ_ID", nullable = true)
	private Long projId;

	@Column(name = "PROJ_TYPE", nullable = false)
	private Long projType;

	@Column(name = "PROJ_CODE", length = 10, nullable = false)
	private String projCode;

	@Column(name = "DP_DEPTID", nullable = false)
	private Long dpDeptId;

	@Column(name = "PROJ_RISK", nullable = true)
	private Long projRisk;

	@Column(name = "PROJ_COMPLEXITY", nullable = true)
	private Long projComplexity;

	@Column(name = "PROJ_NAME_ENG", length = 250, nullable = false)
	private String projNameEng;

	@Column(name = "PROJ_NAME_REG", length = 250, nullable = true)
	private String projNameReg;

	@Column(name = "PROJ_DESCRIPTION", length = 1000, nullable = false)
	private String projDescription;

	@Column(name = "PROJ_START_DATE", nullable = true)
	private Date projStartDate;

	@Column(name = "PROJ_END_DATE", nullable = true)
	private Date projEndDate;

	@Column(name = "SCH_ID", nullable = true)
	private Long schId;

	@Column(name = "PROJ_ESTIMATE_COST", nullable = true)
	private BigDecimal projEstimateCost;

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = false, updatable = false)
	private Long updatedBy;

	@Column(name = "CREATED_DATE", nullable = true)
	private Date createdDate;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "H_STATUS", length = 1, nullable = true)
	private String hStatus;

	@Column(name = "RSO_NO", length = 40, nullable = true)
	private String rsoNumber;

	@Column(name = "RSO_DATE", nullable = true)
	private Date rsoDate;

	@Column(name = "PROJ_PRD", nullable = true)
	private Long projPrd;

	@Column(name = "PROJ_PRD_UNT", nullable = true)
	private Long projPrdUnit;

	public Long getProjPrd() {
		return projPrd;
	}

	public void setProjPrd(Long projPrd) {
		this.projPrd = projPrd;
	}

	public Long getProjPrdUnit() {
		return projPrdUnit;
	}

	public void setProjPrdUnit(Long projPrdUnit) {
		this.projPrdUnit = projPrdUnit;
	}

	public Long getProjIdHist() {
		return projIdHist;
	}

	public void setProjIdHist(Long projIdHist) {
		this.projIdHist = projIdHist;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public Long getProjType() {
		return projType;
	}

	public void setProjType(Long projType) {
		this.projType = projType;
	}

	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public Long getDpDeptId() {
		return dpDeptId;
	}

	public void setDpDeptId(Long dpDeptId) {
		this.dpDeptId = dpDeptId;
	}

	public Long getProjRisk() {
		return projRisk;
	}

	public void setProjRisk(Long projRisk) {
		this.projRisk = projRisk;
	}

	public Long getProjComplexity() {
		return projComplexity;
	}

	public void setProjComplexity(Long projComplexity) {
		this.projComplexity = projComplexity;
	}

	public String getProjNameEng() {
		return projNameEng;
	}

	public void setProjNameEng(String projNameEng) {
		this.projNameEng = projNameEng;
	}

	public String getProjNameReg() {
		return projNameReg;
	}

	public void setProjNameReg(String projNameReg) {
		this.projNameReg = projNameReg;
	}

	public String getProjDescription() {
		return projDescription;
	}

	public void setProjDescription(String projDescription) {
		this.projDescription = projDescription;
	}

	public Date getProjStartDate() {
		return projStartDate;
	}

	public void setProjStartDate(Date projStartDate) {
		this.projStartDate = projStartDate;
	}

	public Date getProjEndDate() {
		return projEndDate;
	}

	public void setProjEndDate(Date projEndDate) {
		this.projEndDate = projEndDate;
	}

	public Long getSchId() {
		return schId;
	}

	public void setSchId(Long schId) {
		this.schId = schId;
	}

	public BigDecimal getProjEstimateCost() {
		return projEstimateCost;
	}

	public void setProjEstimateCost(BigDecimal projEstimateCost) {
		this.projEstimateCost = projEstimateCost;
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

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}

	public String getRsoNumber() {
		return rsoNumber;
	}

	public void setRsoNumber(String rsoNumber) {
		this.rsoNumber = rsoNumber;
	}

	public Date getRsoDate() {
		return rsoDate;
	}

	public void setRsoDate(Date rsoDate) {
		this.rsoDate = rsoDate;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_PROJECT_MAST_HIST", "PROJ_ID_H" };
	}

}
