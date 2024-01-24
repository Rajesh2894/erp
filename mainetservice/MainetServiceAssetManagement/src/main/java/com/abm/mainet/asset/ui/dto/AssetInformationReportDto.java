/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author satish.rathore
 *
 */
public class AssetInformationReportDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1103326847993142477L;

    private String assetIdentiNo;
    private String descriptionStru;
    private Long location;
    private String locationDes;
    private String surveyNo;
    private Long lengthUnit;
    private BigDecimal length;
    private BigDecimal breadth;
    private Long breadthUnit;
    private String lengthUnitDesc;
    private String breadtUnitDesc;
    private BigDecimal area;
    private Long areaValue;
    private String areaUnitDesc;
    private String titleDoc;
    private Long acquisitionMethod;
    private String acquMethodDesc;
    private Long warrenty;
    private BigDecimal securityDposit;
    private Date dpositDate;
    private Long noOfTreeBuilding;
    private BigDecimal paidValueTree;

    public String getAssetIdentiNo() {
		return assetIdentiNo;
	}

	public void setAssetIdentiNo(String assetIdentiNo) {
		this.assetIdentiNo = assetIdentiNo;
	}

    public String getDescriptionStru() {
        return descriptionStru;
    }

    public void setDescriptionStru(String descriptionStru) {
        this.descriptionStru = descriptionStru;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public String getLocationDes() {
        return locationDes;
    }

    public void setLocationDes(String locationDes) {
        this.locationDes = locationDes;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public Long getLengthUnit() {
        return lengthUnit;
    }

    public void setLengthUnit(Long lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getBreadth() {
        return breadth;
    }

    public void setBreadth(BigDecimal breadth) {
        this.breadth = breadth;
    }

    public Long getBreadthUnit() {
        return breadthUnit;
    }

    public void setBreadthUnit(Long breadthUnit) {
        this.breadthUnit = breadthUnit;
    }

    public String getLengthUnitDesc() {
        return lengthUnitDesc;
    }

    public void setLengthUnitDesc(String lengthUnitDesc) {
        this.lengthUnitDesc = lengthUnitDesc;
    }

    public String getBreadtUnitDesc() {
        return breadtUnitDesc;
    }

    public void setBreadtUnitDesc(String breadtUnitDesc) {
        this.breadtUnitDesc = breadtUnitDesc;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Long getAreaValue() {
        return areaValue;
    }

    public void setAreaValue(Long areaValue) {
        this.areaValue = areaValue;
    }

    public String getAreaUnitDesc() {
        return areaUnitDesc;
    }

    public void setAreaUnitDesc(String areaUnitDesc) {
        this.areaUnitDesc = areaUnitDesc;
    }

    public String getTitleDoc() {
        return titleDoc;
    }

    public void setTitleDoc(String titleDoc) {
        this.titleDoc = titleDoc;
    }

    public Long getAcquisitionMethod() {
        return acquisitionMethod;
    }

    public void setAcquisitionMethod(Long acquisitionMethod) {
        this.acquisitionMethod = acquisitionMethod;
    }

    public String getAcquMethodDesc() {
        return acquMethodDesc;
    }

    public void setAcquMethodDesc(String acquMethodDesc) {
        this.acquMethodDesc = acquMethodDesc;
    }

    public Long getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(Long warrenty) {
        this.warrenty = warrenty;
    }

    public BigDecimal getSecurityDposit() {
        return securityDposit;
    }

    public void setSecurityDposit(BigDecimal securityDposit) {
        this.securityDposit = securityDposit;
    }

    public Date getDpositDate() {
        return dpositDate;
    }

    public void setDpositDate(Date dpositDate) {
        this.dpositDate = dpositDate;
    }

    public Long getNoOfTreeBuilding() {
        return noOfTreeBuilding;
    }

    public void setNoOfTreeBuilding(Long noOfTreeBuilding) {
        this.noOfTreeBuilding = noOfTreeBuilding;
    }

    public BigDecimal getPaidValueTree() {
        return paidValueTree;
    }

    public void setPaidValueTree(BigDecimal paidValueTree) {
        this.paidValueTree = paidValueTree;
    }

    @Override
    public String toString() {
        return "AssetInformationReportDto [assetIdentiNo=" + assetIdentiNo + ", descriptionStru=" + descriptionStru
                + ", location=" + location + ", locationDes=" + locationDes + ", surveyNo=" + surveyNo + ", lengthUnit="
                + lengthUnit + ", length=" + length + ", breadth=" + breadth + ", breadthUnit=" + breadthUnit
                + ", lengthUnitDesc=" + lengthUnitDesc + ", breadtUnitDesc=" + breadtUnitDesc + ", area=" + area + ", areaValue="
                + areaValue + ", areaUnitDesc=" + areaUnitDesc + ", titleDoc=" + titleDoc + ", acquisitionMethod="
                + acquisitionMethod + ", acquMethodDesc=" + acquMethodDesc + ", warrenty=" + warrenty + ", securityDposit="
                + securityDposit + ", dpositDate=" + dpositDate + ", noOfTreeBuilding=" + noOfTreeBuilding + ", paidValueTree="
                + paidValueTree + "]";
    }

}
