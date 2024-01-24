
package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * Persistent class for Asset Information entity stored in table "TB_AST_INFO_MST"
 *
 * @author Anuj Dixit
 *
 */

@Entity
@Table(name = "TB_AST_INFO_MST_REV")
public class AssetInformationRev implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3446440097727689858L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ASSET_REV_ID", nullable = false)
    private Long assetRevId;

    @Column(name = "ASSET_ID", nullable = false)
    private Long assetId;

    @Column(name = "ASSET_NAME", nullable = false)
    private String assetName;

    @Column(name = "SERIAL_NO", nullable = true)
    private String serialNo;

    @Column(name = "BARCODE_NO", nullable = true)
    private Long barcodeNo;

    @Column(name = "RFI_ID", nullable = true)
    private String rfiId;

    @Column(name = "BRAND_NAME", nullable = true)
    private String brandName;

    @Column(name = "ASSET_GROUP", nullable = false)
    private Long assetGroup;

    @Column(name = "ASSET_STATUS", nullable = false)
    private Long assetStatus;

    @Column(name = "ASSET_MODEL_IDENTIFIER", nullable = false)
    private String assetModelIdentifier;

    @Column(name = "ASSET_PARENT_IDENTIFIER", nullable = true)
    private String assetParentIdentifier;

    @Column(name = "DETAILS", nullable = false)
    private String details;

    @Column(name = "REMARK", nullable = true)
    private String remark;

    @Column(name = "NO_OF_SIMILAR_ASSET", nullable = true)
    private Long noOfSimilarAsset;

    @Column(name = "ACQUISITION_METHOD", nullable = false)
    private Long acquisitionMethod;

    @Column(name = "INVESTMENT_REASON", nullable = true)
    private Long investmentReason;

    @Column(name = "ASSET_TYPE", nullable = false)
    private Long assetType;

    @Column(name = "ASSET_CLASSIFICATION", nullable = false)
    private Long assetClass1;

    @Column(name = "ASSET_CLASS", nullable = false)
    private Long assetClass2;

    @Column(name = "CREATION_DATE", nullable = true)
    private Date creationDate;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "INVENTORY_NO", nullable = true)
    private Long inventoryNo;

    @Column(name = "LAST_INVENTORY_ON", nullable = true)
    private Date lastInventoryOn;

    @Column(name = "INVENTORY_NOTE", nullable = true)
    private String inventoryNote;

    @Column(name = "INCLUDE_INVENTORY_LIST", nullable = true)
    private String includeAssetInventoryLst;

    @Column(name = "CAPITALIZE_ON", nullable = true)
    private Date capitalizeOn;

    @Column(name = "FIRST_ACQUISITION_ON", nullable = true)
    private Date firstAcquisitionOn;

    @Column(name = "ACQUISITION_YEAR", nullable = true)
    private Long acquisitionYear;

    @Column(name = "ORDER_ON", nullable = true)
    private Date orderOn;

    @Column(name = "CUSTODIAN", nullable = true)
    private String custodian;

    @Column(name = "EMPLOYEE_ID", nullable = true)
    private Long employeeId;

    @Column(name = "AST_LENGTH", nullable = true)
    private BigDecimal length;

    @Column(name = "LENGTHVALUE", nullable = true)
    private Long lengthValue;

    @Column(name = "BREADTH", nullable = true)
    private BigDecimal breadth;

    @Column(name = "BREADTHVALUE", nullable = true)
    private Long breadthValue;

    @Column(name = "HEIGHT", nullable = true)
    private BigDecimal height;

    @Column(name = "HEIGHTVALUE", nullable = true)
    private Long heightValue;

    @Column(name = "WIDTH", nullable = true)
    private BigDecimal width;

    @Column(name = "WIDTHVALUE", nullable = true)
    private Long widthValue;

    @Column(name = "WEIGHT", nullable = true)
    private BigDecimal weight;

    @Column(name = "WEIGHTVALUE", nullable = true)
    private Long weightValue;

    @Column(name = "AREA", nullable = true)
    private BigDecimal area;

    @Column(name = "AREAVALUE", nullable = true)
    private Long areaValue;

    @Column(name = "VOLUME", nullable = true)
    private BigDecimal volume;

    @Column(name = "VOLUMEVALUE", nullable = true)
    private Long volumeValue;

    @Column(name = "ASSET_APP_STATUS", nullable = true)
    private String appovalStatus;

    @Column(name = "ASSET_LOCATION", nullable = true)
    private Long location;

    @Column(name = "URL_PARAM", nullable = true)
    private String urlParam;

    @Column(name = "REGISTRATION_DETAILS", nullable = true)
    private String registerDetail;

    @Column(name = "PURPOSE_USAGE", nullable = false)
    private String purpose;

    @Column(name = "NO_OF_FLOOR", nullable = true)
    private Long noOfFloor;

    @Column(name = "CARPET", nullable = true)
    private BigDecimal carpet;

    @Column(name = "CARPET_VALUE", nullable = true)
    private Long carpetValue;

    @Column(name = "ASSET_CODE", nullable = true)
    private String assetCode;

    @Column(name = "ASSET_APP_NO", nullable = true)
    private String astAppNo;

    @Column(name = "ROAD_NAME", nullable = true)
    private String roadName;

    @Column(name = "PINCODE", nullable = true)
    private Long pincode;

    @Column(name = "DEPT_CODE", length = 5, nullable = false)
    private String deptCode;

    
    @Column(name = "PROCESSOR", nullable = true)
    private Long processor;
    
    @Column(name = "RAM_SIZE", nullable = true)
    private Long ramSize;
    
    @Column(name = "SCREEN_SIZE", nullable = true)
    private Long screenSize;
    
    @Column(name = "OS_NAME", nullable = true)
    private Long osName;
    
    @Column(name = "HARD_DISK_SIZE", nullable = true)
    private Long hardDiskSize;
    
    @Column(name = "MANUFACTURING_YEAR", nullable = true)
    private Date manufacturingYear;
    
    @Column(name = "GROUP_REF_ID", nullable = true)
    private String groupRefId ;
    
	public String getGroupRefId() {
		return groupRefId;
	}

	public void setGroupRefId(String groupRefId) {
		this.groupRefId = groupRefId;
	}

	public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
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

    public Long getBarcodeNo() {
        return barcodeNo;
    }

    public void setBarcodeNo(Long barcodeNo) {
        this.barcodeNo = barcodeNo;
    }

    public String getRfiId() {
        return rfiId;
    }

    public void setRfiId(String rfiId) {
        this.rfiId = rfiId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(Long assetGroup) {
        this.assetGroup = assetGroup;
    }

    public Long getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Long assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAssetModelIdentifier() {
        return assetModelIdentifier;
    }

    public void setAssetModelIdentifier(String assetModelIdentifier) {
        this.assetModelIdentifier = assetModelIdentifier;
    }

    /**
     * @return the assetParentIdentifier
     */
    public String getAssetParentIdentifier() {
        return assetParentIdentifier;
    }

    /**
     * @param assetParentIdentifier the assetParentIdentifier to set
     */
    public void setAssetParentIdentifier(String assetParentIdentifier) {
        this.assetParentIdentifier = assetParentIdentifier;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getNoOfSimilarAsset() {
        return noOfSimilarAsset;
    }

    public void setNoOfSimilarAsset(Long noOfSimilarAsset) {
        this.noOfSimilarAsset = noOfSimilarAsset;
    }

    public Long getAcquisitionMethod() {
        return acquisitionMethod;
    }

    public void setAcquisitionMethod(Long acquisitionMethod) {
        this.acquisitionMethod = acquisitionMethod;
    }

    public Long getInvestmentReason() {
        return investmentReason;
    }

    public void setInvestmentReason(Long investmentReason) {
        this.investmentReason = investmentReason;
    }

    public Long getAssetType() {
        return assetType;
    }

    public void setAssetType(Long assetType) {
        this.assetType = assetType;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(Long inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public Date getLastInventoryOn() {
        return lastInventoryOn;
    }

    public void setLastInventoryOn(Date lastInventoryOn) {
        this.lastInventoryOn = lastInventoryOn;
    }

    public String getInventoryNote() {
        return inventoryNote;
    }

    public void setInventoryNote(String inventoryNote) {
        this.inventoryNote = inventoryNote;
    }

    public String getIncludeAssetInventoryLst() {
        return includeAssetInventoryLst;
    }

    public void setIncludeAssetInventoryLst(String includeAssetInventoryLst) {
        this.includeAssetInventoryLst = includeAssetInventoryLst;
    }

    public Date getCapitalizeOn() {
        return capitalizeOn;
    }

    public void setCapitalizeOn(Date capitalizeOn) {
        this.capitalizeOn = capitalizeOn;
    }

    public Date getFirstAcquisitionOn() {
        return firstAcquisitionOn;
    }

    public void setFirstAcquisitionOn(Date firstAcquisitionOn) {
        this.firstAcquisitionOn = firstAcquisitionOn;
    }

    public Long getAcquisitionYear() {
        return acquisitionYear;
    }

    public void setAcquisitionYear(Long acquisitionYear) {
        this.acquisitionYear = acquisitionYear;
    }

    public Date getOrderOn() {
        return orderOn;
    }

    public void setOrderOn(Date orderOn) {
        this.orderOn = orderOn;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public Long getLengthValue() {
        return lengthValue;
    }

    public void setLengthValue(Long lengthValue) {
        this.lengthValue = lengthValue;
    }

    public BigDecimal getBreadth() {
        return breadth;
    }

    public void setBreadth(BigDecimal breadth) {
        this.breadth = breadth;
    }

    public Long getBreadthValue() {
        return breadthValue;
    }

    public void setBreadthValue(Long breadthValue) {
        this.breadthValue = breadthValue;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Long getHeightValue() {
        return heightValue;
    }

    public void setHeightValue(Long heightValue) {
        this.heightValue = heightValue;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public Long getWidthValue() {
        return widthValue;
    }

    public void setWidthValue(Long widthValue) {
        this.widthValue = widthValue;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Long getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Long weightValue) {
        this.weightValue = weightValue;
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

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Long getVolumeValue() {
        return volumeValue;
    }

    public void setVolumeValue(Long volumeValue) {
        this.volumeValue = volumeValue;
    }

    public Long getLocation() {
        return location;
    }

    public String getAppovalStatus() {
        return appovalStatus;
    }

    public void setAppovalStatus(String appovalStatus) {
        this.appovalStatus = appovalStatus;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    /**
     * @return the registerDetail
     */
    public String getRegisterDetail() {
        return registerDetail;
    }

    /**
     * @param registerDetail the registerDetail to set
     */
    public void setRegisterDetail(String registerDetail) {
        this.registerDetail = registerDetail;
    }

    /**
     * @return the purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * @return the noOfFloor
     */
    public Long getNoOfFloor() {
        return noOfFloor;
    }

    /**
     * @param noOfFloor the noOfFloor to set
     */
    public void setNoOfFloor(Long noOfFloor) {
        this.noOfFloor = noOfFloor;
    }

    /**
     * @return the carpet
     */
    public BigDecimal getCarpet() {
        return carpet;
    }

    /**
     * @param carpet the carpet to set
     */
    public void setCarpet(BigDecimal carpet) {
        this.carpet = carpet;
    }

    /**
     * @return the carpetValue
     */
    public Long getCarpetValue() {
        return carpetValue;
    }

    /**
     * @param carpetValue the carpetValue to set
     */
    public void setCarpetValue(Long carpetValue) {
        this.carpetValue = carpetValue;
    }

    public Long getAssetRevId() {
        return assetRevId;
    }

    public void setAssetRevId(Long assetRevId) {
        this.assetRevId = assetRevId;
    }

    /**
     * @return the assetCode
     */
    public String getAssetCode() {
        return assetCode;
    }

    /**
     * @param assetCode the assetCode to set
     */
    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAstAppNo() {
        return astAppNo;
    }

    public void setAstAppNo(String astAppNo) {
        this.astAppNo = astAppNo;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    

    public Long getProcessor() {
		return processor;
	}

	public void setProcessor(Long processor) {
		this.processor = processor;
	}

	public Long getRamSize() {
		return ramSize;
	}

	public void setRamSize(Long ramSize) {
		this.ramSize = ramSize;
	}

	public Long getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(Long screenSize) {
		this.screenSize = screenSize;
	}

	public Long getOsName() {
		return osName;
	}

	public void setOsName(Long osName) {
		this.osName = osName;
	}

	public Long getHardDiskSize() {
		return hardDiskSize;
	}

	public void setHardDiskSize(Long hardDiskSize) {
		this.hardDiskSize = hardDiskSize;
	}

	public Date getManufacturingYear() {
		return manufacturingYear;
	}

	public void setManufacturingYear(Date manufacturingYear) {
		this.manufacturingYear = manufacturingYear;
	}



    public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_INFO_MST_REV", "ASSET_REV_ID" };
    }

}
