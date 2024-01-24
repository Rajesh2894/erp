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

/**
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_AST_LINEAR")
public class AssetLinear implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4519789363936382588L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ASSET_LINEAR_ID", nullable = false)
	private Long assetLinearId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
	private AssetInformation assetId;

	@Column(name = "START_POINT", nullable = true)
	private BigDecimal startPoint;

	@Column(name = "END_POINT", nullable = true)
	private BigDecimal endPoint;

	@Column(name = "AST_LENGTH", nullable = true)
	private BigDecimal length;

	@Column(name = "LENGTH_UNIT", nullable = true)
	private Long lengthUnit;

	@Column(name = "TYPE_OF_OFFSET1", nullable = true)
	private String typeOffset1;

	@Column(name = "OFFSET1", nullable = true)
	private BigDecimal offset1;

	@Column(name = "OFFSET1_UNIT", nullable = true)
	private Long offset1Value;

	@Column(name = "TYPE_OF_OFFSET2", nullable = true)
	private String typeOffset2;

	@Column(name = "OFFSET2", nullable = true)
	private BigDecimal offset2;

	@Column(name = "OFFSET2_UNIT", nullable = true)
	private Long offset2Value;

	@Column(name = "MARKER_CODE", nullable = true)
	private String markCode;

	@Column(name = "MARKER_DESCRIPTION", nullable = true)
	private String markDesc;

	@Column(name = "MARKER_TYPE", nullable = true)
	private Long markType;

	@Column(name = "GRID_START_POINT", nullable = true)
	private BigDecimal gridStartPoint;

	@Column(name = "GRID_END_POINT", nullable = true)
	private BigDecimal gridEndPoint;

	@Column(name = "UOM", nullable = true)
	private Long uom;

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

	public Long getAssetLinearId() {
		return assetLinearId;
	}

	public void setAssetLinearId(Long assetLinearId) {
		this.assetLinearId = assetLinearId;
	}

	public AssetInformation getAssetId() {
		return assetId;
	}

	public void setAssetId(AssetInformation assetId) {
		this.assetId = assetId;
	}

	public BigDecimal getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(BigDecimal startPoint) {
		this.startPoint = startPoint;
	}

	public BigDecimal getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(BigDecimal endPoint) {
		this.endPoint = endPoint;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public Long getLengthUnit() {
		return lengthUnit;
	}

	public void setLengthUnit(Long lengthUnit) {
		this.lengthUnit = lengthUnit;
	}

	public String getTypeOffset1() {
		return typeOffset1;
	}

	public void setTypeOffset1(String typeOffset1) {
		this.typeOffset1 = typeOffset1;
	}

	public BigDecimal getOffset1() {
		return offset1;
	}

	public void setOffset1(BigDecimal offset1) {
		this.offset1 = offset1;
	}

	public Long getOffset1Value() {
		return offset1Value;
	}

	public void setOffset1Value(Long offset1Value) {
		this.offset1Value = offset1Value;
	}

	public String getTypeOffset2() {
		return typeOffset2;
	}

	public void setTypeOffset2(String typeOffset2) {
		this.typeOffset2 = typeOffset2;
	}

	public BigDecimal getOffset2() {
		return offset2;
	}

	public void setOffset2(BigDecimal offset2) {
		this.offset2 = offset2;
	}

	public Long getOffset2Value() {
		return offset2Value;
	}

	public void setOffset2Value(Long offset2Value) {
		this.offset2Value = offset2Value;
	}

	public String getMarkCode() {
		return markCode;
	}

	public void setMarkCode(String markCode) {
		this.markCode = markCode;
	}

	public String getMarkDesc() {
		return markDesc;
	}

	public void setMarkDesc(String markDesc) {
		this.markDesc = markDesc;
	}

	public Long getMarkType() {
		return markType;
	}

	public void setMarkType(Long markType) {
		this.markType = markType;
	}

	public BigDecimal getGridStartPoint() {
		return gridStartPoint;
	}

	public void setGridStartPoint(BigDecimal gridStartPoint) {
		this.gridStartPoint = gridStartPoint;
	}

	public BigDecimal getGridEndPoint() {
		return gridEndPoint;
	}

	public void setGridEndPoint(BigDecimal gridEndPoint) {
		this.gridEndPoint = gridEndPoint;
	}

	public Long getUom() {
		return uom;
	}

	public void setUom(Long uom) {
		this.uom = uom;
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

	public static String[] getPkValues() {
		return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_LINEAR", "ASSET_LINEAR_ID" };

	}
}
