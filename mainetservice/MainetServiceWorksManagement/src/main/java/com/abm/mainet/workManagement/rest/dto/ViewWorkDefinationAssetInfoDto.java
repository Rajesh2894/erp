package com.abm.mainet.workManagement.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ViewWorkDefinationAssetInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long workAssetId;
	private Long assetId;
	private String assetCode;
	private String assetName;
	private String assetCategory;
	private String assetDepartment;
	private String assetLocation;
	private String assetStatus;
	private String assetRecStatus;
	private String assetPurpose;
	private Date assetAquiDate;
	private BigDecimal assetLength;
	private BigDecimal assetBreadth;
	private BigDecimal assetWidth;
	private BigDecimal assetHeight;
	private BigDecimal assetPlotArea;
	private BigDecimal assetAquiCost;
	private Long assetNoOfFloors;
	private BigDecimal assetCaArea;

	private String assetActiveFlag;
	private Long workId;


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

	public String getAssetActiveFlag() {
		return assetActiveFlag;
	}

	public void setAssetActiveFlag(String assetActiveFlag) {
		this.assetActiveFlag = assetActiveFlag;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getAssetCaArea() {
		return assetCaArea;
	}

	public void setAssetCaArea(BigDecimal assetCaArea) {
		this.assetCaArea = assetCaArea;
	}

}
