/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sarojkumar.yadav
 *
 */
public class DepreciationReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6779908795028027474L;

	private Long depreciationReportId;
	private Long assetId;
	private String assetName;
	private String serialNo;
	private String details;
	private Long assetClass1;
	private String assetClass1Desc;
	private Long assetClass2;
	private String assetClass2Desc;
	private Date dateOfAcquisition;
	private BigDecimal costOfAcquisition;
	private BigDecimal purchaseBookValue;
	private Long orgId;
	private Long bookFinYear;
	private String bookFinYearDesc;
	private BigDecimal bookValue;
	private Date bookDate;
	private BigDecimal endBookValue;
	private Date endBookDate;
	private BigDecimal accumDeprValue;
	private BigDecimal additionalCost;
	private BigDecimal expenditureCost;
	private BigDecimal deprValue;
	private String changeType;
	private Date creationDate;
	private Long createdBy;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;

	/**
	 * @return the depreciationReportId
	 */
	public Long getDepreciationReportId() {
		return depreciationReportId;
	}

	/**
	 * @param depreciationReportId
	 *            the depreciationReportId to set
	 */
	public void setDepreciationReportId(Long depreciationReportId) {
		this.depreciationReportId = depreciationReportId;
	}

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
	 * @return the assetClass1
	 */
	public Long getAssetClass1() {
		return assetClass1;
	}

	/**
	 * @param assetClass1
	 *            the assetClass1 to set
	 */
	public void setAssetClass1(Long assetClass1) {
		this.assetClass1 = assetClass1;
	}

	/**
	 * @return the assetClass1Desc
	 */
	public String getAssetClass1Desc() {
		return assetClass1Desc;
	}

	/**
	 * @param assetClass1Desc
	 *            the assetClass1Desc to set
	 */
	public void setAssetClass1Desc(String assetClass1Desc) {
		this.assetClass1Desc = assetClass1Desc;
	}

	/**
	 * @return the assetClass2
	 */
	public Long getAssetClass2() {
		return assetClass2;
	}

	/**
	 * @param assetClass2
	 *            the assetClass2 to set
	 */
	public void setAssetClass2(Long assetClass2) {
		this.assetClass2 = assetClass2;
	}

	/**
	 * @return the assetClass2Desc
	 */
	public String getAssetClass2Desc() {
		return assetClass2Desc;
	}

	/**
	 * @param assetClass2Desc
	 *            the assetClass2Desc to set
	 */
	public void setAssetClass2Desc(String assetClass2Desc) {
		this.assetClass2Desc = assetClass2Desc;
	}

	/**
	 * @return the dateOfAcquisition
	 */
	public Date getDateOfAcquisition() {
		return dateOfAcquisition;
	}

	/**
	 * @param dateOfAcquisition
	 *            the dateOfAcquisition to set
	 */
	public void setDateOfAcquisition(Date dateOfAcquisition) {
		this.dateOfAcquisition = dateOfAcquisition;
	}

	/**
	 * @return the costOfAcquisition
	 */
	public BigDecimal getCostOfAcquisition() {
		return costOfAcquisition;
	}

	/**
	 * @param costOfAcquisition
	 *            the costOfAcquisition to set
	 */
	public void setCostOfAcquisition(BigDecimal costOfAcquisition) {
		this.costOfAcquisition = costOfAcquisition;
	}

	/**
	 * @return the purchaseBookValue
	 */
	public BigDecimal getPurchaseBookValue() {
		return purchaseBookValue;
	}

	/**
	 * @param purchaseBookValue
	 *            the purchaseBookValue to set
	 */
	public void setPurchaseBookValue(BigDecimal purchaseBookValue) {
		this.purchaseBookValue = purchaseBookValue;
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

	/**
	 * @return the bookFinYear
	 */
	public Long getBookFinYear() {
		return bookFinYear;
	}

	/**
	 * @param bookFinYear
	 *            the bookFinYear to set
	 */
	public void setBookFinYear(Long bookFinYear) {
		this.bookFinYear = bookFinYear;
	}

	/**
	 * @return the bookFinYearDesc
	 */
	public String getBookFinYearDesc() {
		return bookFinYearDesc;
	}

	/**
	 * @param bookFinYearDesc
	 *            the bookFinYearDesc to set
	 */
	public void setBookFinYearDesc(String bookFinYearDesc) {
		this.bookFinYearDesc = bookFinYearDesc;
	}

	/**
	 * @return the bookValue
	 */
	public BigDecimal getBookValue() {
		return bookValue;
	}

	/**
	 * @param bookValue
	 *            the bookValue to set
	 */
	public void setBookValue(BigDecimal bookValue) {
		this.bookValue = bookValue;
	}

	/**
	 * @return the bookDate
	 */
	public Date getBookDate() {
		return bookDate;
	}

	/**
	 * @param bookDate
	 *            the bookDate to set
	 */
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	/**
	 * @return the endBookValue
	 */
	public BigDecimal getEndBookValue() {
		return endBookValue;
	}

	/**
	 * @param endBookValue
	 *            the endBookValue to set
	 */
	public void setEndBookValue(BigDecimal endBookValue) {
		this.endBookValue = endBookValue;
	}

	/**
	 * @return the endBookDate
	 */
	public Date getEndBookDate() {
		return endBookDate;
	}

	/**
	 * @param endBookDate
	 *            the endBookDate to set
	 */
	public void setEndBookDate(Date endBookDate) {
		this.endBookDate = endBookDate;
	}

	/**
	 * @return the accumDeprValue
	 */
	public BigDecimal getAccumDeprValue() {
		return accumDeprValue;
	}

	/**
	 * @param accumDeprValue
	 *            the accumDeprValue to set
	 */
	public void setAccumDeprValue(BigDecimal accumDeprValue) {
		this.accumDeprValue = accumDeprValue;
	}

	/**
	 * @return the additionalCost
	 */
	public BigDecimal getAdditionalCost() {
		return additionalCost;
	}

	/**
	 * @param additionalCost
	 *            the additionalCost to set
	 */
	public void setAdditionalCost(BigDecimal additionalCost) {
		this.additionalCost = additionalCost;
	}

	/**
	 * @return the expenditureCost
	 */
	public BigDecimal getExpenditureCost() {
		return expenditureCost;
	}

	/**
	 * @param expenditureCost
	 *            the expenditureCost to set
	 */
	public void setExpenditureCost(BigDecimal expenditureCost) {
		this.expenditureCost = expenditureCost;
	}

	/**
	 * @return the deprValue
	 */
	public BigDecimal getDeprValue() {
		return deprValue;
	}

	/**
	 * @param deprValue
	 *            the deprValue to set
	 */
	public void setDeprValue(BigDecimal deprValue) {
		this.deprValue = deprValue;
	}

	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}

	/**
	 * @param changeType
	 *            the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DepreciationReportDTO [depreciationReportId=" + depreciationReportId + ", assetId=" + assetId
				+ ", assetName=" + assetName + ", serialNo=" + serialNo + ", details=" + details + ", assetClass1="
				+ assetClass1 + ", assetClass1Desc=" + assetClass1Desc + ", assetClass2=" + assetClass2
				+ ", assetClass2Desc=" + assetClass2Desc + ", dateOfAcquisition=" + dateOfAcquisition
				+ ", costOfAcquisition=" + costOfAcquisition + ", purchaseBookValue=" + purchaseBookValue + ", orgId="
				+ orgId + ", bookFinYear=" + bookFinYear + ", bookFinYearDesc=" + bookFinYearDesc + ", bookValue="
				+ bookValue + ", bookDate=" + bookDate + ", endBookValue=" + endBookValue + ", endBookDate="
				+ endBookDate + ", accumDeprValue=" + accumDeprValue + ", additionalCost=" + additionalCost
				+ ", expenditureCost=" + expenditureCost + ", deprValue=" + deprValue + ", changeType=" + changeType
				+ ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
	}

}
