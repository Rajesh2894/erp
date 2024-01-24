package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class MeasurementBookLbhDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3131040432605356827L;

	private Long mbLbhId;
	private Long estimateMeasureDetId;
	private Long mbdId;
	private WorkEstimateMeasureDetailsDto details;
	private String mbType;
	private String mbValueType;
	private BigDecimal mbTotal;
	private String mbParticulare;
	private Long mbNosAct;
	private BigDecimal mbValue;
	private BigDecimal mbLength;
	private BigDecimal mbHeight;
	private String mbFormula;
	private BigDecimal mbBreadth;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private BigDecimal sorRate;
	private BigDecimal mbArea;
	private BigDecimal mbVolume;
	private BigDecimal mbWeight;
	private Long MBFlag;
	private String meValue;
	private String mbEstTotal;
	private BigDecimal totalAmt;
	private Character deletedFlag;
	
	public Long getMbLbhId() {
		return mbLbhId;
	}
	public void setMbLbhId(Long mbLbhId) {
		this.mbLbhId = mbLbhId;
	}
	public Long getEstimateMeasureDetId() {
		return estimateMeasureDetId;
	}
	public void setEstimateMeasureDetId(Long estimateMeasureDetId) {
		this.estimateMeasureDetId = estimateMeasureDetId;
	}
	public Long getMbdId() {
		return mbdId;
	}
	public void setMbdId(Long mbdId) {
		this.mbdId = mbdId;
	}
	public WorkEstimateMeasureDetailsDto getDetails() {
		return details;
	}
	public void setDetails(WorkEstimateMeasureDetailsDto details) {
		this.details = details;
	}
	public String getMbType() {
		return mbType;
	}
	public void setMbType(String mbType) {
		this.mbType = mbType;
	}
	public String getMbValueType() {
		return mbValueType;
	}
	public void setMbValueType(String mbValueType) {
		this.mbValueType = mbValueType;
	}
	public BigDecimal getMbTotal() {
		return mbTotal;
	}
	public void setMbTotal(BigDecimal mbTotal) {
		this.mbTotal = mbTotal;
	}
	public String getMbParticulare() {
		return mbParticulare;
	}
	public void setMbParticulare(String mbParticulare) {
		this.mbParticulare = mbParticulare;
	}
	public Long getMbNosAct() {
		return mbNosAct;
	}
	public void setMbNosAct(Long mbNosAct) {
		this.mbNosAct = mbNosAct;
	}
	public BigDecimal getMbLength() {
		return mbLength;
	}
	public void setMbLength(BigDecimal mbLength) {
		this.mbLength = mbLength;
	}
	public BigDecimal getMbHeight() {
		return mbHeight;
	}
	public void setMbHeight(BigDecimal mbHeight) {
		this.mbHeight = mbHeight;
	}
	public String getMbFormula() {
		return mbFormula;
	}
	public void setMbFormula(String mbFormula) {
		this.mbFormula = mbFormula;
	}
	public BigDecimal getMbBreadth() {
		return mbBreadth;
	}
	public void setMbBreadth(BigDecimal mbBreadth) {
		this.mbBreadth = mbBreadth;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	public BigDecimal getMbValue() {
		return mbValue;
	}
	public void setMbValue(BigDecimal mbValue) {
		this.mbValue = mbValue;
	}
	public BigDecimal getSorRate() {
		return sorRate;
	}
	public void setSorRate(BigDecimal sorRate) {
		this.sorRate = sorRate;
	}
	public BigDecimal getMbArea() {
		return mbArea;
	}
	public void setMbArea(BigDecimal mbArea) {
		this.mbArea = mbArea;
	}
	public BigDecimal getMbVolume() {
		return mbVolume;
	}
	public void setMbVolume(BigDecimal mbVolume) {
		this.mbVolume = mbVolume;
	}
	public BigDecimal getMbWeight() {
		return mbWeight;
	}
	public void setMbWeight(BigDecimal mbWeight) {
		this.mbWeight = mbWeight;
	}
	public Long getMBFlag() {
		return MBFlag;
	}
	public void setMBFlag(Long mBFlag) {
		MBFlag = mBFlag;
	}
	public String getMeValue() {
		return meValue;
	}
	public void setMeValue(String meValue) {
		this.meValue = meValue;
	}
	public String getMbEstTotal() {
		return mbEstTotal;
	}
	public void setMbEstTotal(String mbEstTotal) {
		this.mbEstTotal = mbEstTotal;
	}
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public Character getDeletedFlag() {
		return deletedFlag;
	}
	public void setDeletedFlag(Character deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

}
