package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class ITAssetRegisterUploadDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 118118923565250349L;
	
	private String fromWhomAcquired;
	private String purchaseOrderNo;
	private BigDecimal costOfAcquisition;
	private Date dateOfAcquisition;
    private String licenseNo;
	private Date licenseDate;
	private String modeOfPayment;
	private Long modeOfPaymentId;
	private String countryOfOrigin1;
	private Date warrantyTillDate;
	private Long venderId;
	private Long acquisiMethodId;
	private Long countryOfOrigin1Id;
	
	private String serialNo;
	private String assetClass1;// AssetClassification is required for getting
    private String assetClass2;
	private Long assetClassId;
    private Long assetClass2Id;
	private String assetModelIdentifier;
	private String processor;
	private String ramSize;
	private String screenSize;
	private String osName;
	private String hardDiskSize;
	private Long processorId;
	private Long ramSizeId;
	private Long screenSizeId;
	private Long osNameId;
	private Long hardDiskSizeId;
	private Date manufacturingYear;
	private String brandName;
	private Long assetStatus;
	private String acquisitionMethod;
	private String remark;
	private String astClassCode;
	private String generatedAstCode;
    private String assetAppStatus;
	
	private String isServiceAplicable;
	private String serviceProvider;
	private Date serviceStartDate;
	private Long warrenty;
	private Date serviceExpiryDate;
	private String serviceDescription;
	
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

	
	public Long getAcquisiMethodId() {
		return acquisiMethodId;
	}

	public void setAcquisiMethodId(Long acquisiMethodId) {
		this.acquisiMethodId = acquisiMethodId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getAssetStatus() {
		return assetStatus;
	}

	public void setAssetStatus(Long assetStatus) {
		this.assetStatus = assetStatus;
	}

	public String getAcquisitionMethod() {
		return acquisitionMethod;
	}

	public void setAcquisitionMethod(String acquisitionMethod) {
		this.acquisitionMethod = acquisitionMethod;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFromWhomAcquired() {
		return fromWhomAcquired;
	}

	public void setFromWhomAcquired(String fromWhomAcquired) {
		this.fromWhomAcquired = fromWhomAcquired;
	}

	public Long getVenderId() {
		return venderId;
	}

	public void setVenderId(Long venderId) {
		this.venderId = venderId;
	}

	public String getAstClassCode() {
		return astClassCode;
	}

	public void setAstClassCode(String astClassCode) {
		this.astClassCode = astClassCode;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public BigDecimal getCostOfAcquisition() {
		return costOfAcquisition;
	}

	public void setCostOfAcquisition(BigDecimal costOfAcquisition) {
		this.costOfAcquisition = costOfAcquisition;
	}

	public Date getDateOfAcquisition() {
		return dateOfAcquisition;
	}

	public void setDateOfAcquisition(Date dateOfAcquisition) {
		this.dateOfAcquisition = dateOfAcquisition;
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

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public Long getModeOfPaymentId() {
		return modeOfPaymentId;
	}

	public void setModeOfPaymentId(Long modeOfPaymentId) {
		this.modeOfPaymentId = modeOfPaymentId;
	}

	public String getCountryOfOrigin1() {
		return countryOfOrigin1;
	}

	public void setCountryOfOrigin1(String countryOfOrigin1) {
		this.countryOfOrigin1 = countryOfOrigin1;
	}

	public Long getCountryOfOrigin1Id() {
		return countryOfOrigin1Id;
	}

	public void setCountryOfOrigin1Id(Long countryOfOrigin1Id) {
		this.countryOfOrigin1Id = countryOfOrigin1Id;
	}

	public Date getWarrantyTillDate() {
		return warrantyTillDate;
	}

	public void setWarrantyTillDate(Date warrantyTillDate) {
		this.warrantyTillDate = warrantyTillDate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public String getAssetModelIdentifier() {
		return assetModelIdentifier;
	}

	public void setAssetModelIdentifier(String assetModelIdentifier) {
		this.assetModelIdentifier = assetModelIdentifier;
	}


	public Date getManufacturingYear() {
		return manufacturingYear;
	}

	public void setManufacturingYear(Date manufacturingYear) {
		this.manufacturingYear = manufacturingYear;
	}

	public String getIsServiceAplicable() {
		return isServiceAplicable;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getRamSize() {
		return ramSize;
	}

	public void setRamSize(String ramSize) {
		this.ramSize = ramSize;
	}

	public String getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getHardDiskSize() {
		return hardDiskSize;
	}

	public void setHardDiskSize(String hardDiskSize) {
		this.hardDiskSize = hardDiskSize;
	}

	public Long getProcessorId() {
		return processorId;
	}

	public void setProcessorId(Long processorId) {
		this.processorId = processorId;
	}

	public Long getRamSizeId() {
		return ramSizeId;
	}

	public void setRamSizeId(Long ramSizeId) {
		this.ramSizeId = ramSizeId;
	}

	public Long getScreenSizeId() {
		return screenSizeId;
	}

	public void setScreenSizeId(Long screenSizeId) {
		this.screenSizeId = screenSizeId;
	}

	public Long getOsNameId() {
		return osNameId;
	}

	public void setOsNameId(Long osNameId) {
		this.osNameId = osNameId;
	}

	public Long getHardDiskSizeId() {
		return hardDiskSizeId;
	}

	public void setHardDiskSizeId(Long hardDiskSizeId) {
		this.hardDiskSizeId = hardDiskSizeId;
	}

	public void setIsServiceAplicable(String isServiceAplicable) {
		this.isServiceAplicable = isServiceAplicable;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public Long getWarrenty() {
		return warrenty;
	}

	public void setWarrenty(Long warrenty) {
		this.warrenty = warrenty;
	}

	public Date getServiceExpiryDate() {
		return serviceExpiryDate;
	}

	public void setServiceExpiryDate(Date serviceExpiryDate) {
		this.serviceExpiryDate = serviceExpiryDate;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	
	    
	    
	   
	    
	
	
}
