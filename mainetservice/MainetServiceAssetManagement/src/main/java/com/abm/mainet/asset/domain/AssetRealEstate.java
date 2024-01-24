package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

@Entity
@Table(name="TB_AST_REALSTD")
public class AssetRealEstate  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4519789363936382588L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ASSET_REALSTD_ID", nullable = false)
	private Long assetRealStdId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
	private AssetInformation assetId;

	@Column(name = "ASSESSMENT_NO", nullable = true)
	private String assessmentNo;
	
	@Column(name = "MUNICIPALITY_NAME", nullable = true)
	private String muncipalityName;
	
	@Column(name = "PROPERTY_REGISTRATION_NO", nullable = true)
	private String propertyRegistrationNo;
	
	@Column(name = "REAL_ESTATE_AMOUNT", nullable = true)
	private BigDecimal realEstateAmount;

	@Column(name = "TAX_CODE", nullable = true)
	private String taxCode;
	
	@Column(name = "TAX_ZONE_LOCATION", nullable = true)
	private String taxZoneLocation;
	
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

	
	
	
	public String getAssessmentNo() {
		return assessmentNo;
	}

	public void setAssessmentNo(String assessmentNo) {
		this.assessmentNo = assessmentNo;
	}

	public String getMuncipalityName() {
		return muncipalityName;
	}

	public void setMuncipalityName(String muncipalityName) {
		this.muncipalityName = muncipalityName;
	}

	public String getPropertyRegistrationNo() {
		return propertyRegistrationNo;
	}

	public void setPropertyRegistrationNo(String propertyRegistrationNo) {
		this.propertyRegistrationNo = propertyRegistrationNo;
	}

	public BigDecimal getRealEstateAmount() {
		return realEstateAmount;
	}

	public void setRealEstateAmount(BigDecimal realEstateAmount) {
		this.realEstateAmount = realEstateAmount;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getTaxZoneLocation() {
		return taxZoneLocation;
	}

	public void setTaxZoneLocation(String taxZoneLocation) {
		this.taxZoneLocation = taxZoneLocation;
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

	public Long getAssetRealStdId() {
		return assetRealStdId;
	}

	public void setAssetRealStdId(Long assetRealStdId) {
		this.assetRealStdId = assetRealStdId;
	}

	public AssetInformation getAssetId() {
		return assetId;
	}

	public void setAssetId(AssetInformation assetId) {
		this.assetId = assetId;
	}

	public String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_REALSTD", "ASSET_REALSTD_ID" };
    }

}
