/**
 * 
 */
package com.abm.mainet.asset.rest.dto;

import java.io.Serializable;
import java.util.Date;

//import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * DTO class for Asset Management Information
 * 
 * @author sarojkumar.yadav
 *
 */
@XmlRootElement(name = "AssetInformation")
public class AssetInformationExternalDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7096032925696160105L;
    private Long assetId;
    @NotNull(message = "{asset.vldnn.assetname}")
    @NotEmpty(message = "{asset.vldnn.assetname}")
    private String assetName;
    // @NotNull(message = "{asset.vldnn.assetSerailNo}")
    // @NotEmpty(message = "{asset.vldnn.assetSerailNo}")
    private String serialNo;
    private Long barcodeNo;
    private String rfiId;
    // @NotNull(message = "{asset.vldnn.brandname}")
    // @NotEmpty(message = "{asset.vldnn.brandname}")
    private String brandName;
    // @NotNull(message = "{asset.vldnn.assetGroup}")
    private Long assetGroup;
    // @NotNull(message = "{asset.vldnn.statusType}")
    private String assetStatus;
    // @NotNull(message = "{asset.vldnn.assetModel}")
    // @NotEmpty(message = "{asset.vldnn.assetModel}")
    private String assetModelIdentifier;
    private String assetParentIdentifier;
    @NotNull(message = "{asset.vldnn.assetDetails}")
    @NotEmpty(message = "{asset.vldnn.assetDetails}")
    private String details;
    private String remark;
    // @NotNull(message = "{asset.vldnn.assetSimilar}")
    private Long noOfSimilarAsset;
    @NotNull(message = "{asset.vldnn.assetAcquisitionMethod}")
    private String acquisitionMethod;
    private Long investmentReason;
    // @NotNull(message = "{asset.vldnn.assetType}")
    private Long assetType;
   /* private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Long orgId;*/
    @NotNull(message = "{asset.vldnn.assetClassification}")
    private String assetClass1;
    @NotNull(message = "{asset.vldnn.assetClass}")
    private String assetClass2;
    // @NotNull(message = "{asset.vldnn.assetClassification}")
    private String assetClass3;
    // @NotNull(message = "{asset.vldnn.assetClass}")
    private String assetClass4;
    // @NotNull(message = "{asset.vldnn.assetClass}")
    private String assetClass5;
    private String assetFlag;
    private String appovalStatus;
   /* private AssetInventoryInformationExternalDTO assetInventoryInfoDTO;*/
    private String registerDetail;
    @NotNull(message = "{asset.vldnn.purpose}")
    @NotEmpty(message = "{asset.vldnn.purpose}")
    private String purpose;
    private String urlParam;
    private String astCode;
    private Long astInfoId;// only use when asset entry info page
    private String astAppNo;
    private String roadName;
    private Long pincode;
    private AssetPostingInformationExternalDTO assetPostingInfoDTO;
    private AssetSpecificationExternalDTO assetSpecificationDTO;
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

    public String getAssetStatus() {
		return assetStatus;
	}

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
     * @param assetModelIdentifier the assetModelIdentifier to set
     */
    public void setAssetModelIdentifier(String assetModelIdentifier) {
        this.assetModelIdentifier = assetModelIdentifier;
    }

    /**
     * 
     * /**
     * @return the details
     */
    public String getDetails() {
        return details;
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

   
    public String getAcquisitionMethod() {
		return acquisitionMethod;
	}

	public void setAcquisitionMethod(String acquisitionMethod) {
		this.acquisitionMethod = acquisitionMethod;
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
     * @return the creationDate
     */
   /* public Date getCreationDate() {
        return creationDate;
    }

    *//**
     * @param creationDate the creationDate to set
     *//*
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    *//**
     * @return the createdBy
     *//*
    public Long getCreatedBy() {
        return createdBy;
    }

    *//**
     * @param createdBy the createdBy to set
     *//*
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    *//**
     * @return the updatedBy
     *//*
    public Long getUpdatedBy() {
        return updatedBy;
    }

    *//**
     * @param updatedBy the updatedBy to set
     *//*
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    *//**
     * @return the updatedDate
     *//*
    public Date getUpdatedDate() {
        return updatedDate;
    }

    *//**
     * @param updatedDate the updatedDate to set
     *//*
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    *//**
     * @return the lgIpMac
     *//*
    public String getLgIpMac() {
        return lgIpMac;
    }

    *//**
     * @param lgIpMac the lgIpMac to set
     *//*
    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    *//**
     * @return the lgIpMacUpd
     *//*
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    *//**
     * @param lgIpMacUpd the lgIpMacUpd to set
     *//*
    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    *//**
     * @return the orgId
     *//*
    public Long getOrgId() {
        return orgId;
    }

    *//**
     * @param orgId the orgId to set
     *//*
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }*/

    /**
     * @return the assetClass1
     */
    public String getAssetClass1() {
        return assetClass1;
    }

    /**
     * @param assetClass1 the assetClass1 to set
     */
    public void setAssetClass1(String assetClass1) {
        this.assetClass1 = assetClass1;
    }

    
  
    /**
     * @return the assetInventoryInfoDTO
     */
    public AssetPostingInformationExternalDTO getAssetPostingInfoDTO() {
		return assetPostingInfoDTO;
	}

	public void setAssetPostingInfoDTO(AssetPostingInformationExternalDTO assetPostingInfoDTO) {
		this.assetPostingInfoDTO = assetPostingInfoDTO;
	}

    /**
     * @param assetInventoryInfoDTO the assetInventoryInfoDTO to set
     */

	/*public AssetInventoryInformationExternalDTO getAssetInventoryInfoDTO() {
		return assetInventoryInfoDTO;
	}

	public void setAssetInventoryInfoDTO(AssetInventoryInformationExternalDTO assetInventoryInfoDTO) {
		this.assetInventoryInfoDTO = assetInventoryInfoDTO;
	}*/

    /**
     * @return the assetPostingInfoDTO
     */
   
    /**
     * @param assetPostingInfoDTO the assetPostingInfoDTO to set
     */
   

    /**
     * @return the assetSpecificationDTO
     */
  

    /**
     * @param assetSpecificationDTO the assetSpecificationDTO to set
     */
	public AssetSpecificationExternalDTO getAssetSpecificationDTO() {
		return assetSpecificationDTO;
	}

	public void setAssetSpecificationDTO(AssetSpecificationExternalDTO assetSpecificationDTO) {
		this.assetSpecificationDTO = assetSpecificationDTO;
	}

    /**
     * @return the assetFlag
     */
    public String getAssetFlag() {
        return assetFlag;
    }


	/**
     * @param assetFlag the assetFlag to set
     */
    public void setAssetFlag(String assetFlag) {
        this.assetFlag = assetFlag;
    }

    public String getAppovalStatus() {
        return appovalStatus;
    }

    public void setAppovalStatus(String appovalStatus) {
        this.appovalStatus = appovalStatus;
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

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */

   

    public String getAstCode() {
        return astCode;
    }

    public String getAssetClass3() {
		return assetClass3;
	}

	public void setAssetClass3(String assetClass3) {
		this.assetClass3 = assetClass3;
	}

	public String getAssetClass4() {
		return assetClass4;
	}

	public void setAssetClass4(String assetClass4) {
		this.assetClass4 = assetClass4;
	}

	public String getAssetClass5() {
		return assetClass5;
	}

	public void setAssetClass5(String assetClass5) {
		this.assetClass5 = assetClass5;
	}


	public String getAssetClass2() {
		return assetClass2;
	}

	public void setAssetClass2(String assetClass2) {
		this.assetClass2 = assetClass2;
	}

	public void setAstCode(String astCode) {
        this.astCode = astCode;
    }

    public Long getAstInfoId() {
        return astInfoId;
    }

    public void setAstInfoId(Long astInfoId) {
        this.astInfoId = astInfoId;
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

	@Override
	public String toString() {
		return "AssetInformationExternalDTO [assetId=" + assetId + ", assetName=" + assetName + ", serialNo=" + serialNo
				+ ", barcodeNo=" + barcodeNo + ", rfiId=" + rfiId + ", brandName=" + brandName + ", assetGroup="
				+ assetGroup + ", assetStatus=" + assetStatus + ", assetModelIdentifier=" + assetModelIdentifier
				+ ", assetParentIdentifier=" + assetParentIdentifier + ", details=" + details + ", remark=" + remark
				+ ", noOfSimilarAsset=" + noOfSimilarAsset + ", acquisitionMethod=" + acquisitionMethod
				+ ", investmentReason=" + investmentReason + ", assetType=" + assetType + ", assetClass1=" + assetClass1
				+ ", assetClass2=" + assetClass2 + ", assetClass3=" + assetClass3 + ", assetClass4=" + assetClass4
				+ ", assetClass5=" + assetClass5 + ", assetFlag=" + assetFlag + ", appovalStatus=" + appovalStatus
				+ ", assetPostingInfoDTO=" + assetPostingInfoDTO + ", assetSpecificationDTO=" + assetSpecificationDTO
				+ ", registerDetail=" + registerDetail + ", purpose=" + purpose + ", urlParam=" + urlParam
				+ ", astCode=" + astCode + ", astInfoId=" + astInfoId + ", astAppNo=" + astAppNo + ", roadName="
				+ roadName + ", pincode=" + pincode + "]";
	}

}
