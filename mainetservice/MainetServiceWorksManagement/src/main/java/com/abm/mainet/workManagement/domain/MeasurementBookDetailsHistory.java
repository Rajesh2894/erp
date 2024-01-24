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
@Table(name = "TB_WMS_MEASUREMENTBOOK_DET_HIST")
public class MeasurementBookDetailsHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6467776018276981843L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MBD_ID_H", nullable = false)
	private Long mbdIdH;

	@Column(name = "MBD_ID", nullable = false)
	private Long mbdId;

	@Column(name = "WORKE_ID")
	private Long workEId;

	@Column(name = "WORKE_FLAG", nullable = true)
	private String workFlag;

	@Column(name = "WORKE_ESTIMATE_TYPE", nullable = false)
	private String workEstimateType;

	@Column(name = "WORKE_ACTUAL_QTY", nullable = true)
	private BigDecimal workActualQty;

	@Column(name = "WORKE_ACTUAL_AMOUNT", nullable = true)
	private BigDecimal workActualAmt;

	@Column(name = "MBD_PID", nullable = true)
	private Long mbDetPId;

	@Column(name = "MB_ID")
	private Long mbId;

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

	public Long getMbdIdH() {
		return mbdIdH;
	}

	public void setMbdIdH(Long mbdIdH) {
		this.mbdIdH = mbdIdH;
	}

	public Long getMbdId() {
		return mbdId;
	}

	public void setMbdId(Long mbdId) {
		this.mbdId = mbdId;
	}

	public Long getWorkEId() {
		return workEId;
	}

	public void setWorkEId(Long workEId) {
		this.workEId = workEId;
	}

	public String getWorkFlag() {
		return workFlag;
	}

	public void setWorkFlag(String workFlag) {
		this.workFlag = workFlag;
	}

	public String getWorkEstimateType() {
		return workEstimateType;
	}

	public void setWorkEstimateType(String workEstimateType) {
		this.workEstimateType = workEstimateType;
	}

	public BigDecimal getWorkActualQty() {
		return workActualQty;
	}

	public void setWorkActualQty(BigDecimal workActualQty) {
		this.workActualQty = workActualQty;
	}

	public BigDecimal getWorkActualAmt() {
		return workActualAmt;
	}

	public void setWorkActualAmt(BigDecimal workActualAmt) {
		this.workActualAmt = workActualAmt;
	}

	public Long getMbDetPId() {
		return mbDetPId;
	}

	public void setMbDetPId(Long mbDetPId) {
		this.mbDetPId = mbDetPId;
	}

	public Long getMbId() {
		return mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
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
		return new String[] { "WMS", "TB_WMS_MEASUREMENTBOOK_DET_HIST", "MBD_ID_H" };
	}

}
