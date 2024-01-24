package com.abm.mainet.asset.ui.dto;

import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AssetSummary")
public class SummaryDTO {

    private Long astModelId;
    private String astClass;
    private String details;
    private String costCenter;
    private String costCenterDesc;
    private String location;
    private Long locationId;
    private String deptName;
    private Long assetClass1;
    private Long assetClass2;
    private String assetClass1Desc;
    private String assetClass1Code;
    private String assetClass2Desc;
    private String assetStatusDesc;
    private Long parentId;
    private Long astId;
    private String serialNo;
    private String appovalStatus;
    private Long barcode;
    private String assetStatus;
    private BufferedImage bufferImage;
    private String astCode;
    private String assetName;
    private String depriChecked;
    private String astAppNo;
    private String assetModelIdentifier;

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

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

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
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

    public String getCostCenterDesc() {
        return costCenterDesc;
    }

    public void setCostCenterDesc(String costCenterDesc) {
        this.costCenterDesc = costCenterDesc;
    }

    public String getAssetClass1Desc() {
        return assetClass1Desc;
    }

    public void setAssetClass1Desc(String assetClass1Desc) {
        this.assetClass1Desc = assetClass1Desc;
    }

    public String getAssetClass1Code() {
        return assetClass1Code;
    }

    public void setAssetClass1Code(String assetClass1Code) {
        this.assetClass1Code = assetClass1Code;
    }

    public String getAssetClass2Desc() {
        return assetClass2Desc;
    }

    public void setAssetClass2Desc(String assetClass2Desc) {
        this.assetClass2Desc = assetClass2Desc;
    }

    public String getAssetStatusDesc() {
        return assetStatusDesc;
    }

    public void setAssetStatusDesc(String assetStatusDesc) {
        this.assetStatusDesc = assetStatusDesc;
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

    /**
     * @return the barcode
     */
    public Long getBarcode() {
        return barcode;
    }

    /**
     * @param barcode the barcode to set
     */
    public void setBarcode(Long barcode) {
        this.barcode = barcode;
    }

    /**
     * @return the bufferImage
     */
    public BufferedImage getBufferImage() {
        return bufferImage;
    }

    /**
     * @param bufferImage the bufferImage to set
     */
    public void setBufferImage(BufferedImage bufferImage) {
        this.bufferImage = bufferImage;
    }

    public String getAstCode() {
        return astCode;
    }

    public void setAstCode(String astCode) {
        this.astCode = astCode;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getDepriChecked() {
        return depriChecked;
    }

    public void setDepriChecked(String depriChecked) {
        this.depriChecked = depriChecked;
    }

    public String getAstAppNo() {
        return astAppNo;
    }

    public void setAstAppNo(String astAppNo) {
        this.astAppNo = astAppNo;
    }
    
    public String getAssetModelIdentifier() {
		return assetModelIdentifier;
	}

	public void setAssetModelIdentifier(String assetModelIdentifier) {
		this.assetModelIdentifier = assetModelIdentifier;
	}

	@Override
	public String toString() {
		return "SummaryDTO [astModelId=" + astModelId + ", astClass=" + astClass + ", details=" + details
				+ ", costCenter=" + costCenter + ", costCenterDesc=" + costCenterDesc + ", location=" + location
				+ ", locationId=" + locationId + ", deptName=" + deptName + ", assetClass1=" + assetClass1
				+ ", assetClass2=" + assetClass2 + ", assetClass1Desc=" + assetClass1Desc + ", assetClass1Code="
				+ assetClass1Code + ", assetClass2Desc=" + assetClass2Desc + ", assetStatusDesc=" + assetStatusDesc
				+ ", parentId=" + parentId + ", astId=" + astId + ", serialNo=" + serialNo + ", appovalStatus="
				+ appovalStatus + ", barcode=" + barcode + ", assetStatus=" + assetStatus + ", bufferImage="
				+ bufferImage + ", astCode=" + astCode + ", assetName=" + assetName + ", depriChecked=" + depriChecked
				+ ", astAppNo=" + astAppNo + ", assetModelIdentifier=" + assetModelIdentifier + "]";
	}

}
