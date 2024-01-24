package com.abm.mainet.common.utility;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.junit.Test;

public class ReflectionUtilsTest {
	
	

    @Test
    public void nullProperties() throws Exception {
    	AssetInformationDTO bean = new AssetInformationDTO();

        List<String> result = ReflectionUtils.getNullPropertiesList(bean, AssetInformationDTO.class);

        assertTrue(result.size() == 26);

    }
    
    @Test
    public void isBeanPopulated_false() throws Exception {
    	AssetInformationDTO bean = new AssetInformationDTO();

        boolean result = ReflectionUtils.isBeanPopulated(bean, AssetInformationDTO.class);
        
        assertFalse(result);
    }
    
    @Test
    public void isBeanPopulated_true() throws Exception {
    	AssetInformationDTO bean = new AssetInformationDTO();
    	bean.setAcquisitionMethod("asdf");

        boolean result = ReflectionUtils.isBeanPopulated(bean, AssetInformationDTO.class);
        
        assertTrue(result);
    }
    
    @Test
    public void isBeanPopulated_falsecomplex() throws Exception {
    	AssetDetailsDTO bean = new AssetDetailsDTO();
        boolean result = ReflectionUtils.isBeanPopulated(bean, AssetDetailsDTO.class);
        
        assertFalse(result);
    }
    
    @Test
    public void isBeanPopulated_truecomplex() throws Exception {
    	AssetDetailsDTO bean = new AssetDetailsDTO();
    	bean.setAssetInfo(new AssetInformationDTO());
        boolean result = ReflectionUtils.isBeanPopulated(bean, AssetDetailsDTO.class);
        
        assertTrue(result);
    }

}

class AssetDetailsDTO {
	private String assetType;
	//private int id;
	private AssetInformationDTO assetInfo;
	/**
	 * @return the assetType
	 */
	public String getAssetType() {
		return assetType;
	}
	/**
	 * @param assetType the assetType to set
	 */
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	/**
	 * @return the assetInfo
	 */
	public AssetInformationDTO getAssetInfo() {
		return assetInfo;
	}
	/**
	 * @param assetInfo the assetInfo to set
	 */
	public void setAssetInfo(AssetInformationDTO assetInfo) {
		this.assetInfo = assetInfo;
	}
	/**
	 * @return the id
	 */
	/*public int getId() {
		return id;
	}
	*//**
	 * @param id the id to set
	 *//*
	public void setId(int id) {
		this.id = id;
	}*/
	
}

class AssetInformationDTO implements Serializable {

	private static final long serialVersionUID = -7096032925696160105L;

	private Long assetId;
	@NotNull
	private String assetName;
	private String serialNo;
	private Long barcodeNo;
	private String rfiId;
	@NotNull
	private String brandName;
	private String assetGroup;
	@NotNull
	private String assetStatus;
	@NotNull
	private String assetModelIdentifier;
	private String assetChildIdentifier;
	@NotNull
	private String details;
	private String remark;
	@NotNull
	private Long noOfSimilarAsset;
	private String acquisitionMethod;
	private String investmentReason;
	private String assetType;
	@NotNull
	private String assetClassification;
	private Date startDate;
	private Date endDate;
	private Date creationDate;
	private Long createdBy;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long orgId;


	/**
	 * @return the assetId
	 */
	public Long getAssetId() {
		return assetId;
	}

	/**
	 * @param assetId
	 *            the assetId to set
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
	 * @param assetName
	 *            the assetName to set
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
	 * @param serialNo
	 *            the serialNo to set
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
	 * @param barcodeNo
	 *            the barcodeNo to set
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
	 * @param rfiId
	 *            the rfiId to set
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
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the assetGroup
	 */
	public String getAssetGroup() {
		return assetGroup;
	}

	/**
	 * @param assetGroup
	 *            the assetGroup to set
	 */
	public void setAssetGroup(String assetGroup) {
		this.assetGroup = assetGroup;
	}

	/**
	 * @return the assetStatus
	 */
	public String getAssetStatus() {
		return assetStatus;
	}

	/**
	 * @param assetStatus
	 *            the assetStatus to set
	 */
	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}

	/**
	 * @return the assetModelIdentifier
	 */
	public String getAssetModelIdentifier() {
		return assetModelIdentifier;
	}

	/**
	 * @param assetModelIdentifier
	 *            the assetModelIdentifier to set
	 */
	public void setAssetModelIdentifier(String assetModelIdentifier) {
		this.assetModelIdentifier = assetModelIdentifier;
	}

	/**
	 * @return the assetChildIdentifier
	 */
	public String getAssetChildIdentifier() {
		return assetChildIdentifier;
	}

	/**
	 * @param assetChildIdentifier
	 *            the assetChildIdentifier to set
	 */
	public void setAssetChildIdentifier(String assetChildIdentifier) {
		this.assetChildIdentifier = assetChildIdentifier;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
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
	 * @param remark
	 *            the remark to set
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
	 * @param noOfSimilarAsset
	 *            the noOfSimilarAsset to set
	 */
	public void setNoOfSimilarAsset(Long noOfSimilarAsset) {
		this.noOfSimilarAsset = noOfSimilarAsset;
	}

	/**
	 * @return the acquisitionMethod
	 */
	public String getAcquisitionMethod() {
		return acquisitionMethod;
	}

	/**
	 * @param acquisitionMethod
	 *            the acquisitionMethod to set
	 */
	public void setAcquisitionMethod(String acquisitionMethod) {
		this.acquisitionMethod = acquisitionMethod;
	}

	/**
	 * @return the investmentReason
	 */
	public String getInvestmentReason() {
		return investmentReason;
	}

	/**
	 * @param investmentReason
	 *            the investmentReason to set
	 */
	public void setInvestmentReason(String investmentReason) {
		this.investmentReason = investmentReason;
	}

	/**
	 * @return the assetType
	 */
	public String getAssetType() {
		return assetType;
	}

	/**
	 * @param assetType
	 *            the assetType to set
	 */
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	/**
	 * @return the assetClassification
	 */
	public String getAssetClassification() {
		return assetClassification;
	}

	/**
	 * @param assetClassification
	 *            the assetClassification to set
	 */
	public void setAssetClassification(String assetClassification) {
		this.assetClassification = assetClassification;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
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
	 * @param createdBy
	 *            the createdBy to set
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
	 * @param updatedBy
	 *            the updatedBy to set
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
	 * @param updatedDate
	 *            the updatedDate to set
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
	 * @param lgIpMac
	 *            the lgIpMac to set
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
	 * @param lgIpMacUpd
	 *            the lgIpMacUpd to set
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
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Override
	public String toString() {
		return "AssetInformationDTO [assetId=" + assetId + ", assetName=" + assetName + ", serialNo=" + serialNo
				+ ", barcodeNo=" + barcodeNo + ", rfiId=" + rfiId + ", brandName=" + brandName + ", assetGroup="
				+ assetGroup + ", assetStatus=" + assetStatus + ", assetModelIdentifier=" + assetModelIdentifier
				+ ", assetChildIdentifier=" + assetChildIdentifier + ", details=" + details + ", remark=" + remark
				+ ", noOfSimilarAsset=" + noOfSimilarAsset + ", acquisitionMethod=" + acquisitionMethod
				+ ", investmentReason=" + investmentReason + ", assetType=" + assetType + ", assetClassification="
				+ assetClassification + ", startDate=" + startDate + ", endDate=" + endDate + ", creationDate="
				+ creationDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", orgId=" + orgId;
	}

}
