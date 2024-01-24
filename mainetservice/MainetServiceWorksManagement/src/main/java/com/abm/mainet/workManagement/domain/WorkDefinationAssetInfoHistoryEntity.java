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

@Entity
@Table(name = "TB_WMS_WORKDEF_ASSET_INFO_HIST")
public class WorkDefinationAssetInfoHistoryEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "WORK_ASSETID_H")
	private Long workAssetIdH;

	@Column(name = "WORK_ASSETID")
	private Long workAssetId;

	@Column(name = "WORK_ID")
	private Long workId;

	@Column(name = "ASSET_ID")
	private Long assetId;

	@Column(name = "ASSET_CODE")
	private String assetCode;

	@Column(name = "ASSET_NAME")
	private String assetName;

	@Column(name = "ASSET_CATEGORY")
	private String assetCategory;

	@Column(name = "ASSET_DEPARTMENT")
	private String assetDepartment;

	@Column(name = "ASSET_LOCATION")
	private String assetLocation;

	@Column(name = "ASSET_STATUS")
	private String assetStatus;

	@Column(name = "H_STATUS")
	private String hStatus;

	@Column(name = "ASSET_PURPOSE", length = 200)
	private String assetPurpose;

	@Temporal(TemporalType.DATE)
	@Column(name = "ASSET_AQUI_DATE", nullable = true)
	private Date assetAquiDate;

	@Column(name = "ASSET_LENGTH")
	private BigDecimal assetLength;

	@Column(name = "ASSET_BREADTH")
	private BigDecimal assetBreadth;

	@Column(name = "ASSET_WIDTH")
	private BigDecimal assetWidth;

	@Column(name = "ASSET_HEIGHT")
	private BigDecimal assetHeight;

	@Column(name = "ASSET_PLOT_AREA")
	private BigDecimal assetPlotArea;

	@Column(name = "ASSET_CAAREA")
	private BigDecimal assetCaArea;

	@Column(name = "ASSET_AQUI_COST")
	private BigDecimal assetAquiCost;

	@Column(name = "ASSET_NOFLOOR", nullable = true)
	private Long assetNoOfFloors;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@Column(name = "ASSET_REC_STATUS")
	private String assetRecStatus;

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

	public Long getWorkAssetId() {
		return workAssetId;
	}

	public void setWorkAssetId(Long workAssetId) {
		this.workAssetId = workAssetId;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetCategory() {
		return assetCategory;
	}

	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}

	public String getAssetDepartment() {
		return assetDepartment;
	}

	public void setAssetDepartment(String assetDepartment) {
		this.assetDepartment = assetDepartment;
	}

	public String getAssetLocation() {
		return assetLocation;
	}

	public void setAssetLocation(String assetLocation) {
		this.assetLocation = assetLocation;
	}

	public String getAssetStatus() {
		return assetStatus;
	}

	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}

	public String getAssetRecStatus() {
		return assetRecStatus;
	}

	public void setAssetRecStatus(String assetRecStatus) {
		this.assetRecStatus = assetRecStatus;
	}

	public Long getWorkAssetIdH() {
		return workAssetIdH;
	}

	public void setWorkAssetIdH(Long workAssetIdH) {
		this.workAssetIdH = workAssetIdH;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_WORKDEF_ASSET_INFO_HIST", "WORK_ASSETID_H" };
	}

	public String getAssetPurpose() {
		return assetPurpose;
	}

	public void setAssetPurpose(String assetPurpose) {
		this.assetPurpose = assetPurpose;
	}

	public Date getAssetAquiDate() {
		return assetAquiDate;
	}

	public void setAssetAquiDate(Date assetAquiDate) {
		this.assetAquiDate = assetAquiDate;
	}

	public BigDecimal getAssetLength() {
		return assetLength;
	}

	public void setAssetLength(BigDecimal assetLength) {
		this.assetLength = assetLength;
	}

	public BigDecimal getAssetBreadth() {
		return assetBreadth;
	}

	public void setAssetBreadth(BigDecimal assetBreadth) {
		this.assetBreadth = assetBreadth;
	}

	public BigDecimal getAssetWidth() {
		return assetWidth;
	}

	public void setAssetWidth(BigDecimal assetWidth) {
		this.assetWidth = assetWidth;
	}

	public BigDecimal getAssetHeight() {
		return assetHeight;
	}

	public void setAssetHeight(BigDecimal assetHeight) {
		this.assetHeight = assetHeight;
	}

	public BigDecimal getAssetPlotArea() {
		return assetPlotArea;
	}

	public void setAssetPlotArea(BigDecimal assetPlotArea) {
		this.assetPlotArea = assetPlotArea;
	}

	public BigDecimal getAssetCaArea() {
		return assetCaArea;
	}

	public void setAssetCaArea(BigDecimal assetCaArea) {
		this.assetCaArea = assetCaArea;
	}

	public BigDecimal getAssetAquiCost() {
		return assetAquiCost;
	}

	public void setAssetAquiCost(BigDecimal assetAquiCost) {
		this.assetAquiCost = assetAquiCost;
	}

	public Long getAssetNoOfFloors() {
		return assetNoOfFloors;
	}

	public void setAssetNoOfFloors(Long assetNoOfFloors) {
		this.assetNoOfFloors = assetNoOfFloors;
	}

}
