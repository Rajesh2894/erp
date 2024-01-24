package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_WMS_WORKDEFINATION")
public class WorkDefinationEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "WORK_ID", nullable = false)
	private Long workId;

	@Column(name = "WORK_NAME", length = 500, nullable = false)
	private String workName;

	@ManyToOne
	@JoinColumn(name = "PROJ_ID", referencedColumnName = "PROJ_ID", nullable = false)
	private TbWmsProjectMaster projMasEntity;

	@Temporal(TemporalType.DATE)
	@Column(name = "WORK_START_DATE", nullable = false)
	private Date workStartDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "WORK_END_DATE", nullable = false)
	private Date workEndDate;

	@Column(name = "WORK_TYPE", nullable = false)
	private Long workType;

	@Column(name = "WRK_SUBTPEID", nullable = true)
	private Long workSubType;

	@Temporal(TemporalType.DATE)
	@Column(name = "WORK_COMPLETION_DT", nullable = true)
	private Date workCompletionDate;

	@Column(name = "WORK_COMPLETION_NO", nullable = true)
	private String workCompletionNo;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "DP_DEPTID", nullable = false)
	private Long deptId;

	@Column(name = "WORK_PROJECT_PHASE", nullable = true)
	private Long workProjPhase;

	@Column(name = "LOC_ID_ST", nullable = false)
	private Long locIdSt;

	@Column(name = "LOC_ID_EN", nullable = false)
	private Long locIdEn;

	@Column(name = "WORK_CODE", length = 50, nullable = false)
	private String workcode;

	@Column(name = "WORK_STATUS", length = 3, nullable = false)
	private String workStatus;

	@Column(name = "WORK_CATEGORY", nullable = false)
	private Long workCategory;

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "WORK_ESTAMT")
	private BigDecimal workEstAmt;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workDefEntity", cascade = CascadeType.ALL)
	@Where(clause = "YE_ACTIVE='Y'")
	private List<WorkDefinationYearDetEntity> wdYearDetEntity = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workDefEntity", cascade = CascadeType.ALL)
	@Where(clause = "ASSET_REC_STATUS='Y'")
	private List<WorkDefinationAssetInfoEntity> wdAssetInfoEntity = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workDefEntity", cascade = CascadeType.ALL)
	private List<WorkDefinitionSancDet> workSancDetails = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workDefEntity", cascade = CascadeType.ALL)
	private List<WorkDefinationWardZoneDetails> wardZoneDetails = new ArrayList<>();

	@Column(name = "WORK_DEVPER")
	private BigDecimal deviationPercent;

	@Column(name = "WORK_OVERHEAD")
	private BigDecimal workOverHeadAmt;

	@Column(name = "PROPOSAL_NO")
	private String proposalNo;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "LONGITUDE")
	private String longitude;

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

	public TbWmsProjectMaster getProjMasEntity() {
		return projMasEntity;
	}

	public void setProjMasEntity(TbWmsProjectMaster projMasEntity) {
		this.projMasEntity = projMasEntity;
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

	public List<WorkDefinationYearDetEntity> getWdYearDetEntity() {
		return wdYearDetEntity;
	}

	public void setWdYearDetEntity(List<WorkDefinationYearDetEntity> wdYearDetEntity) {
		this.wdYearDetEntity = wdYearDetEntity;
	}

	public List<WorkDefinationAssetInfoEntity> getWdAssetInfoEntity() {
		return wdAssetInfoEntity;
	}

	public void setWdAssetInfoEntity(List<WorkDefinationAssetInfoEntity> wdAssetInfoEntity) {
		this.wdAssetInfoEntity = wdAssetInfoEntity;
	}

	public List<WorkDefinitionSancDet> getWorkSancDetails() {
		return workSancDetails;
	}

	public void setWorkSancDetails(List<WorkDefinitionSancDet> workSancDetails) {
		this.workSancDetails = workSancDetails;
	}

	public List<WorkDefinationWardZoneDetails> getWardZoneDetails() {
		return wardZoneDetails;
	}

	public void setWardZoneDetails(List<WorkDefinationWardZoneDetails> wardZoneDetails) {
		this.wardZoneDetails = wardZoneDetails;
	}

	public BigDecimal getDeviationPercent() {
		return deviationPercent;
	}

	public void setDeviationPercent(BigDecimal deviationPercent) {
		this.deviationPercent = deviationPercent;
	}

	public Long getWorkCategory() {
		return workCategory;
	}

	public void setWorkCategory(Long workCategory) {
		this.workCategory = workCategory;
	}

	public BigDecimal getWorkOverHeadAmt() {
		return workOverHeadAmt;
	}

	public void setWorkOverHeadAmt(BigDecimal workOverHeadAmt) {
		this.workOverHeadAmt = workOverHeadAmt;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_WORKDEFINATION", "WORK_ID" };
	}

}
