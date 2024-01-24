package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Jeetendra.Pal
 *
 */

@Entity
@Table(name = "TB_WMS_MEASUREMENT_DETAIL_HIST")
public class WorkEstimateMeasureDetailsHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6320383322334268493L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ME_ID_H", nullable = false)
	private Long meMentIdH;

	@Column(name = "ME_ID", nullable = false)
	private Long meMentId;

	@Column(name = "WORKE_ID", nullable = false)
	private Long workEstemateId;

	@Column(name = "ME_PARTICULARE", nullable = false)
	private String meMentParticulare;

	@Column(name = "ME_NOS", nullable = false)
	private Long meMentNumber;

	@Column(name = "ME_VALUE_TYPE", nullable = false)
	private String meMentValType;

	@Column(name = "ME_VALUE", nullable = false)
	private BigDecimal meValue;

	@Column(name = "ME_LENGTH", nullable = false)
	private BigDecimal meMentLength;

	@Column(name = "ME_BREADTH", nullable = false)
	private BigDecimal meMentBreadth;

	@Column(name = "ME_HEIGHT", nullable = false)
	private BigDecimal meMentHeight;

	@Column(name = "ME_FORMULA", nullable = false)
	private String meMentFormula;

	@Column(name = "ME_TOTAL", nullable = false)
	private BigDecimal meMentToltal;

	@Column(name = "ME_TYPE", nullable = false)
	private String meMentType;

	@Column(name = "ME_ACTIVE", nullable = false)
	private String meMentActive;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	public Long getMeMentIdH() {
		return meMentIdH;
	}

	public void setMeMentIdH(Long meMentIdH) {
		this.meMentIdH = meMentIdH;
	}

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

	public BigDecimal getMeValue() {
		return meValue;
	}

	public void setMeValue(BigDecimal meValue) {
		this.meValue = meValue;
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

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_MEASUREMENT_DETAIL_HIST", "ME_ID_H" };
	}

}
