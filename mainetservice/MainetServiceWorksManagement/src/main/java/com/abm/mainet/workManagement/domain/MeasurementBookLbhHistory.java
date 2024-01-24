package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Entity
@Table(name = "TB_WMS_MEASUREMENTBOOK_LBH_HIS")
public class MeasurementBookLbhHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2495848731597390806L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MB_LBHID_H", nullable = false)
	private Long mbLbhIdH;

	@Column(name = "MB_LBHID", nullable = false)
	private Long mbLbhId;
	
	@Column(name = "MBD_ID", nullable = true)
	private Long mbdId;

	@OneToOne
	@JoinColumn(name = "ME_ID", referencedColumnName = "ME_ID", nullable = true)
	private WorkEstimateMeasureDetails details;

	@Column(name = "MB_VALUE_TYPE", nullable = true)
	private String mbValueType;

	@Column(name = "MB_TYPE", nullable = true)
	private String mbType;

	@Column(name = "MB_TOTAL", nullable = true)
	private BigDecimal mbTotal;

	@Column(name = "MB_PARTICULARE", nullable = true)
	private String mbParticulare;

	@Column(name = "MB_NOS_ACT", nullable = true)
	private Long mbNosAct;

	@Column(name = "MB_LENGTH", nullable = true)
	private BigDecimal mbLength;

	@Column(name = "MB_HEIGHT", nullable = true)
	private BigDecimal mbHeight;

	@Column(name = "MB_FORMULA", nullable = true)
	private String mbFormula;

	@Column(name = "MB_BREADTH", nullable = true)
	private BigDecimal mbBreadth;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;
	
	
	  @Column(name = "MB_AREA", nullable = true) 
	  private BigDecimal mbArea;
	  
	  @Column(name = "MB_VOLUME", nullable = true)
	  private BigDecimal mbVolume;
	  
	  @Column(name = "MB_WEIGHT", nullable = true)
	  private BigDecimal mbWeight;
	 

	public Long getMbLbhIdH() {
		return mbLbhIdH;
	}

	public void setMbLbhIdH(Long mbLbhIdH) {
		this.mbLbhIdH = mbLbhIdH;
	}

	public Long getMbLbhId() {
		return mbLbhId;
	}

	public void setMbLbhId(Long mbLbhId) {
		this.mbLbhId = mbLbhId;
	}

	public Long getMbdId() {
		return mbdId;
	}

	public void setMbdId(Long mbdId) {
		this.mbdId = mbdId;
	}

	public WorkEstimateMeasureDetails getDetails() {
		return details;
	}

	public void setDetails(WorkEstimateMeasureDetails details) {
		this.details = details;
	}

	public String getMbValueType() {
		return mbValueType;
	}

	public void setMbValueType(String mbValueType) {
		this.mbValueType = mbValueType;
	}

	public String getMbType() {
		return mbType;
	}

	public void setMbType(String mbType) {
		this.mbType = mbType;
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

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_MEASUREMENTBOOK_LBH_HIS", "MB_LBHID_H" };
	}

}
