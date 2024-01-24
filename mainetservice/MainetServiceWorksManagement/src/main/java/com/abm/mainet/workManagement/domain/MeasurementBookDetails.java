package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Entity
@Table(name = "TB_WMS_MEASUREMENTBOOK_DET")

public class MeasurementBookDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8942610549740256207L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MBD_ID", nullable = false)
	private Long mbdId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WORKE_ID", referencedColumnName = "WORKE_ID")
	private WorkEstimateMaster workEstimateMaster;

	@Column(name = "WORKE_FLAG", nullable = true)
	private String workFlag;

	@Column(name = "WORKE_ESTIMATE_TYPE", nullable = false)
	private String workEstimateType;

	@Column(name = "WORKE_ACTUAL_QTY", nullable = true)
	private BigDecimal workActualQty;
	
	@Column(name = "WORKE_UTL_QTY", nullable = true)
	private BigDecimal workUtlQty;
	
	@Column(name = "WORKE_ACTUAL_AMOUNT", nullable = true)
	private BigDecimal workActualAmt;
	
	@Column(name = "MBD_PID", nullable = true)
	private Long mbDetPId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MB_ID")
	private MeasurementBookMaster mbMaster;

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

	@Column(name = "SORD_SUB_DESCRIPTION", nullable = false)
	private String sorSubDescription;

	public Long getMbdId() {
		return mbdId;
	}

	public void setMbdId(Long mbdId) {
		this.mbdId = mbdId;
	}

	public WorkEstimateMaster getWorkEstimateMaster() {
		return workEstimateMaster;
	}

	public void setWorkEstimateMaster(WorkEstimateMaster workEstimateMaster) {
		this.workEstimateMaster = workEstimateMaster;
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

	public BigDecimal getWorkUtlQty() {
		return workUtlQty;
	}

	public void setWorkUtlQty(BigDecimal workUtlQty) {
		this.workUtlQty = workUtlQty;
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

	public MeasurementBookMaster getMbMaster() {
		return mbMaster;
	}

	public void setMbMaster(MeasurementBookMaster mbMaster) {
		this.mbMaster = mbMaster;
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

	public String getSorSubDescription() {
		return sorSubDescription;
	}

	public void setSorSubDescription(String sorSubDescription) {
		this.sorSubDescription = sorSubDescription;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_MEASUREMENTBOOK_DET", "MBD_ID" };
	}

}
