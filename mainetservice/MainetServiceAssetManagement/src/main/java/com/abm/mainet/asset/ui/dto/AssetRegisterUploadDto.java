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
public class AssetRegisterUploadDto implements Serializable {

    private static final long serialVersionUID = -8705360145708638774L;
    private String assetName;
    private String serialNo;
    private String discription;
    private Long noOfUnit;
    private BigDecimal gisId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String surveyNo;
    private String department;
    private String purpose;
    private String assetClass1;
    private String assetClass2;
    private BigDecimal totalArea;
    private BigDecimal carpetArea;
    private BigDecimal length;
    private BigDecimal breadth;
    private BigDecimal height;
    private BigDecimal depth;
    private BigDecimal volume;
    private Long noOfFloor;
    private String landServeNo;
    private Long modelId;
    private Long YearOfManu;
    private String manufacturer;
    private String acquisitionMethod;
    private Long yearofAcquisition;
    private Date dateofAcquisition;
    private String fromWhomAcquired;
    private String invoiceNo;
    private BigDecimal costOfAcquisition;
    private BigDecimal prevBookValue;
    private BigDecimal bookValue;
    private Date bookValueDate;
    private BigDecimal lifeInYear;
    private BigDecimal accuDepreciationPrevious;
    private BigDecimal accuDepreciationCurrent;
    private Long deprAdjustCurrentYear;
    private Date lastDepreciationDate;
    private BigDecimal salvageValue;
    private String depreciationMethod;
    private String deprFrequency;
    private BigDecimal writtenDownValueTillDate;
    private BigDecimal writtenDownValueDate;
    private Date disposalDate;
    private Long receiptNo;
    private BigDecimal disposeofamount;
    private String documentlist;
    private Long depId;
    private Long assetClassId;
    private Long assetClass2Id;
    private Long venderId;
    private Long acquiModeId;
    private String modelIdent;
    private Long depreMethodId;
    private Long assetStatus;
    private Long lengthUnit;
    private Long breadthUnit;
    private Long heightUnit;
    private Long weightUnit;
    private Long areaUnit;
    private Long volumeUnit;
    private Long carpetUnit;
    private String astClassCode;
    private String generatedAstCode;
    private String assetAppStatus;
    private String location;
    private Long locationId;
    private Long assetTypeId;
    private Long assetGroupId;
    private String assetType;
    private String assetGroup;
    private Long acquisiMethodId;
    private String deprApplicable;
    private Date writtenDownDate;
    private String licenseNo;
    private Date licenseDate;
    private String address;
    private String east;
    private String west;
    private String south;
    private String north;

    public Long getAcquisiMethodId() {
        return acquisiMethodId;
    }

    public void setAcquisiMethodId(Long acquisiMethodId) {
        this.acquisiMethodId = acquisiMethodId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Long getNoOfUnit() {
        return noOfUnit;
    }

    public void setNoOfUnit(Long noOfUnit) {
        this.noOfUnit = noOfUnit;
    }

    public BigDecimal getGisId() {
        return gisId;
    }

    public void setGisId(BigDecimal gisId) {
        this.gisId = gisId;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAssetClass1() {
        return assetClass1;
    }

    public void setAssetClass1(String assetClass1) {
        this.assetClass1 = assetClass1;
    }

    public String getAssetClass2() {
        return assetClass2;
    }

    public void setAssetClass2(String assetClass2) {
        this.assetClass2 = assetClass2;
    }

    public BigDecimal getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(BigDecimal totalArea) {
        this.totalArea = totalArea;
    }

    public BigDecimal getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(BigDecimal carpetArea) {
        this.carpetArea = carpetArea;
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

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getDepth() {
        return depth;
    }

    public void setDepth(BigDecimal depth) {
        this.depth = depth;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Long getNoOfFloor() {
        return noOfFloor;
    }

    public void setNoOfFloor(Long noOfFloor) {
        this.noOfFloor = noOfFloor;
    }

    public String getLandServeNo() {
        return landServeNo;
    }

    public void setLandServeNo(String landServeNo) {
        this.landServeNo = landServeNo;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getYearOfManu() {
        return YearOfManu;
    }

    public void setYearOfManu(Long yearOfManu) {
        YearOfManu = yearOfManu;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getYearofAcquisition() {
        return yearofAcquisition;
    }

    public void setYearofAcquisition(Long yearofAcquisition) {
        this.yearofAcquisition = yearofAcquisition;
    }

    public Date getDateofAcquisition() {
        return dateofAcquisition;
    }

    public void setDateofAcquisition(Date dateofAcquisition) {
        this.dateofAcquisition = dateofAcquisition;
    }

    public String getFromWhomAcquired() {
        return fromWhomAcquired;
    }

    public void setFromWhomAcquired(String fromWhomAcquired) {
        this.fromWhomAcquired = fromWhomAcquired;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public BigDecimal getCostOfAcquisition() {
        return costOfAcquisition;
    }

    public void setCostOfAcquisition(BigDecimal costOfAcquisition) {
        this.costOfAcquisition = costOfAcquisition;
    }

    public BigDecimal getPrevBookValue() {
        return prevBookValue;
    }

    public void setPrevBookValue(BigDecimal prevBookValue) {
        this.prevBookValue = prevBookValue;
    }

    public BigDecimal getBookValue() {
        return bookValue;
    }

    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public Date getBookValueDate() {
        return bookValueDate;
    }

    public void setBookValueDate(Date bookValueDate) {
        this.bookValueDate = bookValueDate;
    }

    public BigDecimal getLifeInYear() {
        return lifeInYear;
    }

    public void setLifeInYear(BigDecimal lifeInYear) {
        this.lifeInYear = lifeInYear;
    }

    public BigDecimal getAccuDepreciationPrevious() {
        return accuDepreciationPrevious;
    }

    public void setAccuDepreciationPrevious(BigDecimal accuDepreciationPrevious) {
        this.accuDepreciationPrevious = accuDepreciationPrevious;
    }

    public BigDecimal getAccuDepreciationCurrent() {
        return accuDepreciationCurrent;
    }

    public void setAccuDepreciationCurrent(BigDecimal accuDepreciationCurrent) {
        this.accuDepreciationCurrent = accuDepreciationCurrent;
    }

    public Long getDeprAdjustCurrentYear() {
        return deprAdjustCurrentYear;
    }

    public void setDeprAdjustCurrentYear(Long deprAdjustCurrentYear) {
        this.deprAdjustCurrentYear = deprAdjustCurrentYear;
    }

    public Date getLastDepreciationDate() {
        return lastDepreciationDate;
    }

    public void setLastDepreciationDate(Date lastDepreciationDate) {
        this.lastDepreciationDate = lastDepreciationDate;
    }

    public BigDecimal getSalvageValue() {
        return salvageValue;
    }

    public void setSalvageValue(BigDecimal salvageValue) {
        this.salvageValue = salvageValue;
    }

    public String getDepreciationMethod() {
        return depreciationMethod;
    }

    public void setDepreciationMethod(String depreciationMethod) {
        this.depreciationMethod = depreciationMethod;
    }

    public String getDeprFrequency() {
        return deprFrequency;
    }

    public void setDeprFrequency(String deprFrequency) {
        this.deprFrequency = deprFrequency;
    }

    public BigDecimal getWrittenDownValueTillDate() {
        return writtenDownValueTillDate;
    }

    public void setWrittenDownValueTillDate(BigDecimal writtenDownValueTillDate) {
        this.writtenDownValueTillDate = writtenDownValueTillDate;
    }

    public BigDecimal getWrittenDownValueDate() {
        return writtenDownValueDate;
    }

    public void setWrittenDownValueDate(BigDecimal writtenDownValueDate) {
        this.writtenDownValueDate = writtenDownValueDate;
    }

    public Date getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = disposalDate;
    }

    public Long getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(Long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public BigDecimal getDisposeofamount() {
        return disposeofamount;
    }

    public void setDisposeofamount(BigDecimal disposeofamount) {
        this.disposeofamount = disposeofamount;
    }

    public String getDocumentlist() {
        return documentlist;
    }

    public void setDocumentlist(String documentlist) {
        this.documentlist = documentlist;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public Long getAssetClassId() {
        return assetClassId;
    }

    public void setAssetClassId(Long assetClassId) {
        this.assetClassId = assetClassId;
    }

    public Long getAssetClass2Id() {
        return assetClass2Id;
    }

    public void setAssetClass2Id(Long assetClass2Id) {
        this.assetClass2Id = assetClass2Id;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Long getAcquiModeId() {
        return acquiModeId;
    }

    public void setAcquiModeId(Long acquiModeId) {
        this.acquiModeId = acquiModeId;
    }

    public String getModelIdent() {
        return modelIdent;
    }

    public void setModelIdent(String modelIdent) {
        this.modelIdent = modelIdent;
    }

    public Long getDepreMethodId() {
        return depreMethodId;
    }

    public void setDepreMethodId(Long depreMethodId) {
        this.depreMethodId = depreMethodId;
    }

    public Long getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Long assetStatus) {
        this.assetStatus = assetStatus;
    }

    public Long getLengthUnit() {
        return lengthUnit;
    }

    public void setLengthUnit(Long lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    public Long getBreadthUnit() {
        return breadthUnit;
    }

    public void setBreadthUnit(Long breadthUnit) {
        this.breadthUnit = breadthUnit;
    }

    public Long getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(Long heightUnit) {
        this.heightUnit = heightUnit;
    }

    public Long getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Long weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Long getAreaUnit() {
        return areaUnit;
    }

    public void setAreaUnit(Long areaUnit) {
        this.areaUnit = areaUnit;
    }

    public Long getVolumeUnit() {
        return volumeUnit;
    }

    public void setVolumeUnit(Long volumeUnit) {
        this.volumeUnit = volumeUnit;
    }

    public Long getCarpetUnit() {
        return carpetUnit;
    }

    public void setCarpetUnit(Long carpetUnit) {
        this.carpetUnit = carpetUnit;
    }

    public String getAstClassCode() {
        return astClassCode;
    }

    public void setAstClassCode(String astClassCode) {
        this.astClassCode = astClassCode;
    }

    public String getGeneratedAstCode() {
        return generatedAstCode;
    }

    public void setGeneratedAstCode(String generatedAstCode) {
        this.generatedAstCode = generatedAstCode;
    }

    public String getAssetAppStatus() {
        return assetAppStatus;
    }

    public void setAssetAppStatus(String assetAppStatus) {
        this.assetAppStatus = assetAppStatus;
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

    public Long getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(Long assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public Long getAssetGroupId() {
        return assetGroupId;
    }

    public void setAssetGroupId(Long assetGroupId) {
        this.assetGroupId = assetGroupId;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AssetRegisterUploadDto other = (AssetRegisterUploadDto) obj;
        if (serialNo == null) {
            if (other.serialNo != null)
                return false;
        } else if (!serialNo.equals(other.serialNo))
            return false;
        return true;
    }

    public String getAcquisitionMethod() {
        return acquisitionMethod;
    }

    public void setAcquisitionMethod(String acquisitionMethod) {
        this.acquisitionMethod = acquisitionMethod;
    }

    public String getDeprApplicable() {
        return deprApplicable;
    }

    public void setDeprApplicable(String deprApplicable) {
        this.deprApplicable = deprApplicable;
    }

    public Date getWrittenDownDate() {
        return writtenDownDate;
    }

    public void setWrittenDownDate(Date writtenDownDate) {
        this.writtenDownDate = writtenDownDate;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEast() {
        return east;
    }

    public void setEast(String east) {
        this.east = east;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    public String getNorth() {
        return north;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    @Override
    public String toString() {
        return "AssetRegisterUploadDto [assetName=" + assetName + ", serialNo=" + serialNo + ", discription=" + discription
                + ", noOfUnit=" + noOfUnit + ", gisId=" + gisId + ", latitude=" + latitude + ", longitude=" + longitude
                + ", surveyNo=" + surveyNo + ", department=" + department + ", purpose=" + purpose + ", assetClass1="
                + assetClass1 + ", assetClass2=" + assetClass2 + ", totalArea=" + totalArea + ", carpetArea=" + carpetArea
                + ", length=" + length + ", breadth=" + breadth + ", height=" + height + ", depth=" + depth + ", volume=" + volume
                + ", noOfFloor=" + noOfFloor + ", landServeNo=" + landServeNo + ", modelId=" + modelId + ", YearOfManu="
                + YearOfManu + ", manufacturer=" + manufacturer + ", acquisitionMethod=" + acquisitionMethod
                + ", yearofAcquisition=" + yearofAcquisition + ", dateofAcquisition=" + dateofAcquisition + ", fromWhomAcquired="
                + fromWhomAcquired + ", invoiceNo=" + invoiceNo + ", costOfAcquisition=" + costOfAcquisition + ", prevBookValue="
                + prevBookValue + ", bookValue=" + bookValue + ", bookValueDate=" + bookValueDate + ", lifeInYear=" + lifeInYear
                + ", accuDepreciationPrevious=" + accuDepreciationPrevious + ", accuDepreciationCurrent="
                + accuDepreciationCurrent + ", deprAdjustCurrentYear=" + deprAdjustCurrentYear + ", lastDepreciationDate="
                + lastDepreciationDate + ", salvageValue=" + salvageValue + ", depreciationMethod=" + depreciationMethod
                + ", deprFrequency=" + deprFrequency + ", writtenDownValueTillDate=" + writtenDownValueTillDate
                + ", writtenDownValueDate=" + writtenDownValueDate + ", disposalDate=" + disposalDate + ", receiptNo=" + receiptNo
                + ", disposeofamount=" + disposeofamount + ", documentlist=" + documentlist + ", depId=" + depId
                + ", assetClassId=" + assetClassId + ", assetClass2Id=" + assetClass2Id + ", venderId=" + venderId
                + ", acquiModeId=" + acquiModeId + ", modelIdent=" + modelIdent + ", depreMethodId=" + depreMethodId
                + ", assetStatus=" + assetStatus + ", lengthUnit=" + lengthUnit + ", breadthUnit=" + breadthUnit + ", heightUnit="
                + heightUnit + ", weightUnit=" + weightUnit + ", areaUnit=" + areaUnit + ", volumeUnit=" + volumeUnit
                + ", carpetUnit=" + carpetUnit + ", astClassCode=" + astClassCode + ", generatedAstCode=" + generatedAstCode
                + ", assetAppStatus=" + assetAppStatus + ", location=" + location + ", locationId=" + locationId
                + ", assetTypeId=" + assetTypeId + ", assetGroupId=" + assetGroupId + ", assetType=" + assetType + ", assetGroup="
                + assetGroup + ", acquisiMethodId=" + acquisiMethodId + ", deprApplicable=" + deprApplicable
                + ", writtenDownDate=" + writtenDownDate + ", licenseNo=" + licenseNo + ", licenseDate=" + licenseDate
                + ", address=" + address + ", east=" + east + ", west=" + west + ", south=" + south + ", north=" + north + "]";
    }

}
