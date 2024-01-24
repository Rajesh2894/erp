/**
 * 
 */
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
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_AST_INFO_MST_HIST")
public class InformationHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7960425757151652655L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ASSET_HIST_ID", nullable = false)
    private Long astHistId;

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

    @Column(name = "NO_OF_SIMILAR_ASSET", nullable = false)
    private Long noOfSimilarAsset;

    @Column(name = "ACQUISITION_METHOD", nullable = false)
    private Long acquisitionMethod;

    @Column(name = "INVESTMENT_REASON", nullable = true)
    private Long investmentReason;

    @Column(name = "ASSET_TYPE", nullable = true)
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
    private String lengthValue;

    @Column(name = "BREADTH", nullable = true)
    private BigDecimal breadth;

    @Column(name = "BREADTHVALUE", nullable = true)
    private String breadthValue;

    @Column(name = "HEIGHT", nullable = true)
    private BigDecimal height;

    @Column(name = "HEIGHTVALUE", nullable = true)
    private String heightValue;

    @Column(name = "WIDTH", nullable = true)
    private BigDecimal width;

    @Column(name = "WIDTHVALUE", nullable = true)
    private String widthValue;

    @Column(name = "WEIGHT", nullable = true)
    private BigDecimal weight;

    @Column(name = "WEIGHTVALUE", nullable = true)
    private String weightValue;

    @Column(name = "AREA", nullable = true)
    private BigDecimal area;

    @Column(name = "AREAVALUE", nullable = true)
    private String areaValue;

    @Column(name = "VOLUME", nullable = true)
    private BigDecimal volume;

    @Column(name = "VOLUMEVALUE", nullable = true)
    private String volumeValue;

    @Column(name = "H_STATUS", nullable = true)
    private String historyFlag;

    @Column(name = "ASSET_LOCATION", nullable = true)
    private Long location;

    @Column(name = "PURPOSE_USAGE", nullable = false)
    private String purpose;

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
    
    @Column(name = "PRINTER_TYPE_ID", nullable = true)
    private Long printerTypeId;
    
    public String getGroupRefId() {
		return groupRefId;
	}

	public void setGroupRefId(String groupRefId) {
		this.groupRefId = groupRefId;
	}

	/**
     * @return the astHistId
     */
    public Long getAstHistId() {
        return astHistId;
    }

    /**
     * @param astHistId the astHistId to set
     */
    public void setAstHistId(Long astHistId) {
        this.astHistId = astHistId;
    }

    /**
     * @return the assetId
     */
    public Long getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the assetName
     */
    public String getAssetName() {
        return assetName;
    }

    /**
     * @param assetName the assetName to set
     */
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    /**
     * @return the serialNo
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * @param serialNo the serialNo to set
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * @return the barcodeNo
     */
    public Long getBarcodeNo() {
        return barcodeNo;
    }

    /**
     * @param barcodeNo the barcodeNo to set
     */
    public void setBarcodeNo(Long barcodeNo) {
        this.barcodeNo = barcodeNo;
    }

    /**
     * @return the rfiId
     */
    public String getRfiId() {
        return rfiId;
    }

    /**
     * @param rfiId the rfiId to set
     */
    public void setRfiId(String rfiId) {
        this.rfiId = rfiId;
    }

    /**
     * @return the brandName
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brandName the brandName to set
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * @return the assetGroup
     */
    public Long getAssetGroup() {
        return assetGroup;
    }

    /**
     * @param assetGroup the assetGroup to set
     */
    public void setAssetGroup(Long assetGroup) {
        this.assetGroup = assetGroup;
    }

    /**
     * @return the assetStatus
     */
    public Long getAssetStatus() {
        return assetStatus;
    }

    /**
     * @param assetStatus the assetStatus to set
     */
    public void setAssetStatus(Long assetStatus) {
        this.assetStatus = assetStatus;
    }

    /**
     * @return the assetModelIdentifier
     */
    public String getAssetModelIdentifier() {
        return assetModelIdentifier;
    }

    /**
     * @param assetModelIdentifier the assetModelIdentifier to set
     */
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

    /**
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the noOfSimilarAsset
     */
    public Long getNoOfSimilarAsset() {
        return noOfSimilarAsset;
    }

    /**
     * @param noOfSimilarAsset the noOfSimilarAsset to set
     */
    public void setNoOfSimilarAsset(Long noOfSimilarAsset) {
        this.noOfSimilarAsset = noOfSimilarAsset;
    }

    /**
     * @return the investmentReason
     */
    public Long getInvestmentReason() {
        return investmentReason;
    }

    /**
     * @param investmentReason the investmentReason to set
     */
    public void setInvestmentReason(Long investmentReason) {
        this.investmentReason = investmentReason;
    }

    /**
     * @return the assetType
     */
    public Long getAssetType() {
        return assetType;
    }

    /**
     * @param assetType the assetType to set
     */
    public void setAssetType(Long assetType) {
        this.assetType = assetType;
    }

    /**
     * @return the acquisitionMethod
     */
    public Long getAcquisitionMethod() {
        return acquisitionMethod;
    }

    /**
     * @param acquisitionMethod the acquisitionMethod to set
     */
    public void setAcquisitionMethod(Long acquisitionMethod) {
        this.acquisitionMethod = acquisitionMethod;
    }

    /**
     * @return the assetClass1
     */
    public Long getAssetClass1() {
        return assetClass1;
    }

    /**
     * @param assetClass1 the assetClass1 to set
     */
    public void setAssetClass1(Long assetClass1) {
        this.assetClass1 = assetClass1;
    }

    /**
     * @return the assetClass2
     */
    public Long getAssetClass2() {
        return assetClass2;
    }

    /**
     * @param assetClass2 the assetClass2 to set
     */
    public void setAssetClass2(Long assetClass2) {
        this.assetClass2 = assetClass2;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the inventoryNo
     */
    public Long getInventoryNo() {
        return inventoryNo;
    }

    /**
     * @param inventoryNo the inventoryNo to set
     */
    public void setInventoryNo(Long inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    /**
     * @return the lastInventoryOn
     */
    public Date getLastInventoryOn() {
        return lastInventoryOn;
    }

    /**
     * @param lastInventoryOn the lastInventoryOn to set
     */
    public void setLastInventoryOn(Date lastInventoryOn) {
        this.lastInventoryOn = lastInventoryOn;
    }

    /**
     * @return the inventoryNote
     */
    public String getInventoryNote() {
        return inventoryNote;
    }

    /**
     * @param inventoryNote the inventoryNote to set
     */
    public void setInventoryNote(String inventoryNote) {
        this.inventoryNote = inventoryNote;
    }

    /**
     * @return the includeAssetInventoryLst
     */
    public String getIncludeAssetInventoryLst() {
        return includeAssetInventoryLst;
    }

    /**
     * @param includeAssetInventoryLst the includeAssetInventoryLst to set
     */
    public void setIncludeAssetInventoryLst(String includeAssetInventoryLst) {
        this.includeAssetInventoryLst = includeAssetInventoryLst;
    }

    /**
     * @return the capitalizeOn
     */
    public Date getCapitalizeOn() {
        return capitalizeOn;
    }

    /**
     * @param capitalizeOn the capitalizeOn to set
     */
    public void setCapitalizeOn(Date capitalizeOn) {
        this.capitalizeOn = capitalizeOn;
    }

    /**
     * @return the firstAcquisitionOn
     */
    public Date getFirstAcquisitionOn() {
        return firstAcquisitionOn;
    }

    /**
     * @param firstAcquisitionOn the firstAcquisitionOn to set
     */
    public void setFirstAcquisitionOn(Date firstAcquisitionOn) {
        this.firstAcquisitionOn = firstAcquisitionOn;
    }

    /**
     * @return the acquisitionYear
     */
    public Long getAcquisitionYear() {
        return acquisitionYear;
    }

    /**
     * @param acquisitionYear the acquisitionYear to set
     */
    public void setAcquisitionYear(Long acquisitionYear) {
        this.acquisitionYear = acquisitionYear;
    }

    /**
     * @return the orderOn
     */
    public Date getOrderOn() {
        return orderOn;
    }

    /**
     * @param orderOn the orderOn to set
     */
    public void setOrderOn(Date orderOn) {
        this.orderOn = orderOn;
    }

    /**
     * @return the custodian
     */
    public String getCustodian() {
        return custodian;
    }

    /**
     * @param custodian the custodian to set
     */
    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    /**
     * @return the employeeId
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the length
     */
    public BigDecimal getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(BigDecimal length) {
        this.length = length;
    }

    /**
     * @return the lengthValue
     */
    public String getLengthValue() {
        return lengthValue;
    }

    /**
     * @param lengthValue the lengthValue to set
     */
    public void setLengthValue(String lengthValue) {
        this.lengthValue = lengthValue;
    }

    /**
     * @return the breadth
     */
    public BigDecimal getBreadth() {
        return breadth;
    }

    /**
     * @param breadth the breadth to set
     */
    public void setBreadth(BigDecimal breadth) {
        this.breadth = breadth;
    }

    /**
     * @return the breadthValue
     */
    public String getBreadthValue() {
        return breadthValue;
    }

    /**
     * @param breadthValue the breadthValue to set
     */
    public void setBreadthValue(String breadthValue) {
        this.breadthValue = breadthValue;
    }

    /**
     * @return the height
     */
    public BigDecimal getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    /**
     * @return the heightValue
     */
    public String getHeightValue() {
        return heightValue;
    }

    /**
     * @param heightValue the heightValue to set
     */
    public void setHeightValue(String heightValue) {
        this.heightValue = heightValue;
    }

    /**
     * @return the width
     */
    public BigDecimal getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    /**
     * @return the widthValue
     */
    public String getWidthValue() {
        return widthValue;
    }

    /**
     * @param widthValue the widthValue to set
     */
    public void setWidthValue(String widthValue) {
        this.widthValue = widthValue;
    }

    /**
     * @return the weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * @return the weightValue
     */
    public String getWeightValue() {
        return weightValue;
    }

    /**
     * @param weightValue the weightValue to set
     */
    public void setWeightValue(String weightValue) {
        this.weightValue = weightValue;
    }

    /**
     * @return the area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    /**
     * @return the areaValue
     */
    public String getAreaValue() {
        return areaValue;
    }

    /**
     * @param areaValue the areaValue to set
     */
    public void setAreaValue(String areaValue) {
        this.areaValue = areaValue;
    }

    /**
     * @return the volume
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * @return the volumeValue
     */
    public String getVolumeValue() {
        return volumeValue;
    }

    /**
     * @param volumeValue the volumeValue to set
     */
    public void setVolumeValue(String volumeValue) {
        this.volumeValue = volumeValue;
    }

    /**
     * @return the historyFlag
     */
    public String getHistoryFlag() {
        return historyFlag;
    }

    /**
     * @param historyFlag the historyFlag to set
     */
    public void setHistoryFlag(String historyFlag) {
        this.historyFlag = historyFlag;
    }

    /**
     * @return the location
     */
    public Long getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Long location) {
        this.location = location;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAssetCode() {
        return assetCode;
    }

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
    public Long getPrinterTypeId() {
		return printerTypeId;
	}

	public void setPrinterTypeId(Long printerTypeId) {
		this.printerTypeId = printerTypeId;
	}

	public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_INFO_MST_HIST",
                "ASSET_HIST_ID" };
    }

}
