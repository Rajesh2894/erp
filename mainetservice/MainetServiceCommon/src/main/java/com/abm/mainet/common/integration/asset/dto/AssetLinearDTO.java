/**
 * 
 */
package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO class for Linear Asset
 * 
 * @author sarojkumar.yadav
 *
 */
@XmlRootElement(name = "AssetLinearDetails")
public class AssetLinearDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2004024346823645903L;

	private Long assetLinearId;
	private Long assetId;
	@NotNull(message = "{asset.vldnn.startPoint}")
	private BigDecimal startPoint;
	@NotNull(message = "{asset.vldnn.endPoint}")
	private BigDecimal endPoint;
	@NotNull(message = "{asset.vldnn.length}")
	private BigDecimal length;
	private Long lengthUnit;
	private String typeOffset1;
	private BigDecimal offset1;
	private Long offset1Value;
	private String typeOffset2;
	private BigDecimal offset2;
	private Long offset2Value;
	private String markCode;
	private String markDesc;
	private Long markType;
	private BigDecimal gridStartPoint;
	private BigDecimal gridEndPoint;
	private Long uom;
	private Date creationDate;
	private Long createdBy;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;

	/**
	 * @return the assetLinearId
	 */
	public Long getAssetLinearId() {
		return assetLinearId;
	}

	/**
	 * @param assetLinearId
	 *            the assetLinearId to set
	 */
	public void setAssetLinearId(Long assetLinearId) {
		this.assetLinearId = assetLinearId;
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
	 * @return the startPoint
	 */
	public BigDecimal getStartPoint() {
		return startPoint;
	}

	/**
	 * @param startPoint
	 *            the startPoint to set
	 */
	public void setStartPoint(BigDecimal startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * @return the endPoint
	 */
	public BigDecimal getEndPoint() {
		return endPoint;
	}

	/**
	 * @param endPoint
	 *            the endPoint to set
	 */
	public void setEndPoint(BigDecimal endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * @return the length
	 */
	public BigDecimal getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(BigDecimal length) {
		this.length = length;
	}

	/**
	 * @return the lengthUnit
	 */
	public Long getLengthUnit() {
		return lengthUnit;
	}

	/**
	 * @param lengthUnit
	 *            the lengthUnit to set
	 */
	public void setLengthUnit(Long lengthUnit) {
		this.lengthUnit = lengthUnit;
	}

	/**
	 * @return the typeOffset1
	 */
	public String getTypeOffset1() {
		return typeOffset1;
	}

	/**
	 * @param typeOffset1
	 *            the typeOffset1 to set
	 */
	public void setTypeOffset1(String typeOffset1) {
		this.typeOffset1 = typeOffset1;
	}

	/**
	 * @return the offset1
	 */
	public BigDecimal getOffset1() {
		return offset1;
	}

	/**
	 * @param offset1
	 *            the offset1 to set
	 */
	public void setOffset1(BigDecimal offset1) {
		this.offset1 = offset1;
	}

	/**
	 * @return the offset1Value
	 */
	public Long getOffset1Value() {
		return offset1Value;
	}

	/**
	 * @param offset1Value
	 *            the offset1Value to set
	 */
	public void setOffset1Value(Long offset1Value) {
		this.offset1Value = offset1Value;
	}

	/**
	 * @return the typeOffset2
	 */
	public String getTypeOffset2() {
		return typeOffset2;
	}

	/**
	 * @param typeOffset2
	 *            the typeOffset2 to set
	 */
	public void setTypeOffset2(String typeOffset2) {
		this.typeOffset2 = typeOffset2;
	}

	/**
	 * @return the offset2
	 */
	public BigDecimal getOffset2() {
		return offset2;
	}

	/**
	 * @param offset2
	 *            the offset2 to set
	 */
	public void setOffset2(BigDecimal offset2) {
		this.offset2 = offset2;
	}

	/**
	 * @return the offset2Value
	 */
	public Long getOffset2Value() {
		return offset2Value;
	}

	/**
	 * @param offset2Value
	 *            the offset2Value to set
	 */
	public void setOffset2Value(Long offset2Value) {
		this.offset2Value = offset2Value;
	}

	/**
	 * @return the markCode
	 */
	public String getMarkCode() {
		return markCode;
	}

	/**
	 * @param markCode
	 *            the markCode to set
	 */
	public void setMarkCode(String markCode) {
		this.markCode = markCode;
	}

	/**
	 * @return the markDesc
	 */
	public String getMarkDesc() {
		return markDesc;
	}

	/**
	 * @param markDesc
	 *            the markDesc to set
	 */
	public void setMarkDesc(String markDesc) {
		this.markDesc = markDesc;
	}

	/**
	 * @return the markType
	 */
	public Long getMarkType() {
		return markType;
	}

	/**
	 * @param markType
	 *            the markType to set
	 */
	public void setMarkType(Long markType) {
		this.markType = markType;
	}

	/**
	 * @return the gridStartPoint
	 */
	public BigDecimal getGridStartPoint() {
		return gridStartPoint;
	}

	/**
	 * @param gridStartPoint
	 *            the gridStartPoint to set
	 */
	public void setGridStartPoint(BigDecimal gridStartPoint) {
		this.gridStartPoint = gridStartPoint;
	}

	/**
	 * @return the gridEndPoint
	 */
	public BigDecimal getGridEndPoint() {
		return gridEndPoint;
	}

	/**
	 * @param gridEndPoint
	 *            the gridEndPoint to set
	 */
	public void setGridEndPoint(BigDecimal gridEndPoint) {
		this.gridEndPoint = gridEndPoint;
	}

	/**
	 * @return the uom
	 */
	public Long getUom() {
		return uom;
	}

	/**
	 * @param uom
	 *            the uom to set
	 */
	public void setUom(Long uom) {
		this.uom = uom;
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
		return "AssetLinearDTO [assetLinearId=" + assetLinearId + ", assetId=" + assetId + ", startPoint=" + startPoint
				+ ", endPoint=" + endPoint + ", length=" + length + ", lengthUnit=" + lengthUnit + ", typeOffset1="
				+ typeOffset1 + ", offset1=" + offset1 + ", offset1Value=" + offset1Value + ", typeOffset2="
				+ typeOffset2 + ", offset2=" + offset2 + ", offset2Value=" + offset2Value + ", markCode=" + markCode
				+ ", markDesc=" + markDesc + ", markType=" + markType + ", gridStartPoint=" + gridStartPoint
				+ ", gridEndPoint=" + gridEndPoint + ", uom=" + uom + ", creationDate=" + creationDate + ", createdBy="
				+ createdBy + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
				+ ", lgIpMacUpd=" + lgIpMacUpd + "]";
	}

}
