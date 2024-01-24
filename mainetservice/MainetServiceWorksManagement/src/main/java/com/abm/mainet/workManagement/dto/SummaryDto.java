package com.abm.mainet.workManagement.dto;

import java.awt.image.BufferedImage;
import java.io.Serializable;
/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class SummaryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long astModelId;
    private String astClass;
    private String details;
    private Long costCenter;
    private String costCenterDesc;
    private String location;
    private Long locationId;
    private String deptName;
    private Long assetClass1;
    private Long assetClass2;
    private String assetClass1Desc;
    private String assetClass2Desc;
    private Long parentId;
    private Long astId;
    private String serialNo;
    private String appovalStatus;
    private Long barcode;
    private String assetStatus;
    private BufferedImage bufferImage;
    
    private String astCode;
    
	public Long getAstModelId() {
		return astModelId;
	}
	public void setAstModelId(Long astModelId) {
		this.astModelId = astModelId;
	}
	public String getAstClass() {
		return astClass;
	}
	public void setAstClass(String astClass) {
		this.astClass = astClass;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Long getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(Long costCenter) {
		this.costCenter = costCenter;
	}
	public String getCostCenterDesc() {
		return costCenterDesc;
	}
	public void setCostCenterDesc(String costCenterDesc) {
		this.costCenterDesc = costCenterDesc;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getAssetClass1() {
		return assetClass1;
	}
	public void setAssetClass1(Long assetClass1) {
		this.assetClass1 = assetClass1;
	}
	public Long getAssetClass2() {
		return assetClass2;
	}
	public void setAssetClass2(Long assetClass2) {
		this.assetClass2 = assetClass2;
	}
	public String getAssetClass1Desc() {
		return assetClass1Desc;
	}
	public void setAssetClass1Desc(String assetClass1Desc) {
		this.assetClass1Desc = assetClass1Desc;
	}
	public String getAssetClass2Desc() {
		return assetClass2Desc;
	}
	public void setAssetClass2Desc(String assetClass2Desc) {
		this.assetClass2Desc = assetClass2Desc;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getAstId() {
		return astId;
	}
	public void setAstId(Long astId) {
		this.astId = astId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getAppovalStatus() {
		return appovalStatus;
	}
	public void setAppovalStatus(String appovalStatus) {
		this.appovalStatus = appovalStatus;
	}
	public Long getBarcode() {
		return barcode;
	}
	public void setBarcode(Long barcode) {
		this.barcode = barcode;
	}
	public String getAssetStatus() {
		return assetStatus;
	}
	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}
	public BufferedImage getBufferImage() {
		return bufferImage;
	}
	public void setBufferImage(BufferedImage bufferImage) {
		this.bufferImage = bufferImage;
	}
	public String getAstCode() {
		return astCode;
	}
	public void setAstCode(String astCode) {
		this.astCode = astCode;
	}

	
}
