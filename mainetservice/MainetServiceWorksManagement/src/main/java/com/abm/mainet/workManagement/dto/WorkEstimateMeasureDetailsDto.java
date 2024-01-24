package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author vishwajeet.kumar
 *
 */
public class WorkEstimateMeasureDetailsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long meMentId;

	private Long workEstemateId;

	private String meMentParticulare;

	private Long meMentNumber;

	private String meMentValType;

	private BigDecimal meMentLength;

	private BigDecimal meMentBreadth;

	private BigDecimal meMentHeight;

	private String meMentFormula;
	
	private String meMentLengthFormula;

	private String meMentBreadthFormula;
	
	private String meMentHeightFormula;

	private BigDecimal meMentToltal;

	private String meMentType;

	private String meMentActive;
	
	private Long meNoUtl;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private BigDecimal meValue;
	
	private Long unUtilizeNo;
	
	private BigDecimal mbArea;
	private BigDecimal mbVolume;
	private BigDecimal mbWeight;

	public Long getMeMentId() {
		return meMentId;
	}

	public void setMeMentId(Long meMentId) {
		this.meMentId = meMentId;
	}

	public Long getWorkEstemateId() {
		return workEstemateId;
	}

	public void setWorkEstemateId(Long workEstemateId) {
		this.workEstemateId = workEstemateId;
	}

	public String getMeMentParticulare() {
		return meMentParticulare;
	}

	public void setMeMentParticulare(String meMentParticulare) {
		this.meMentParticulare = meMentParticulare;
	}

	public Long getMeMentNumber() {
		return meMentNumber;
	}

	public void setMeMentNumber(Long meMentNumber) {
		this.meMentNumber = meMentNumber;
	}

	public String getMeMentValType() {
		return meMentValType;
	}

	public void setMeMentValType(String meMentValType) {
		this.meMentValType = meMentValType;
	}

	public BigDecimal getMeMentLength() {
		return meMentLength;
	}

	public void setMeMentLength(BigDecimal meMentLength) {
		this.meMentLength = meMentLength;
	}

	public BigDecimal getMeMentBreadth() {
		return meMentBreadth;
	}

	public void setMeMentBreadth(BigDecimal meMentBreadth) {
		this.meMentBreadth = meMentBreadth;
	}

	public BigDecimal getMeMentHeight() {
		return meMentHeight;
	}

	public void setMeMentHeight(BigDecimal meMentHeight) {
		this.meMentHeight = meMentHeight;
	}

	public String getMeMentFormula() {
		return meMentFormula;
	}

	public void setMeMentFormula(String meMentFormula) {
		this.meMentFormula = meMentFormula;
	}

	public BigDecimal getMeMentToltal() {
		return meMentToltal;
	}

	public void setMeMentToltal(BigDecimal meMentToltal) {
		this.meMentToltal = meMentToltal;
	}

	public String getMeMentType() {
		return meMentType;
	}

	public void setMeMentType(String meMentType) {
		this.meMentType = meMentType;
	}

	public String getMeMentActive() {
		return meMentActive;
	}

	public void setMeMentActive(String meMentActive) {
		this.meMentActive = meMentActive;
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

	public BigDecimal getMeValue() {
		return meValue;
	}

	public void setMeValue(BigDecimal meValue) {
		this.meValue = meValue;
	}

	public Long getUnUtilizeNo() {
		return unUtilizeNo;
	}

	public void setUnUtilizeNo(Long unUtilizeNo) {
		this.unUtilizeNo = unUtilizeNo;
	}

	public Long getMeNoUtl() {
		return meNoUtl;
	}

	public void setMeNoUtl(Long meNoUtl) {
		this.meNoUtl = meNoUtl;
	}

	public String getMeMentLengthFormula() {
		return meMentLengthFormula;
	}

	public void setMeMentLengthFormula(String meMentLengthFormula) {
		this.meMentLengthFormula = meMentLengthFormula;
	}

	public String getMeMentBreadthFormula() {
		return meMentBreadthFormula;
	}

	public void setMeMentBreadthFormula(String meMentBreadthFormula) {
		this.meMentBreadthFormula = meMentBreadthFormula;
	}

	public String getMeMentHeightFormula() {
		return meMentHeightFormula;
	}

	public void setMeMentHeightFormula(String meMentHeightFormula) {
		this.meMentHeightFormula = meMentHeightFormula;
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

	

	
}
