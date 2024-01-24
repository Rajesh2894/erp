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
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_WMS_MBOVERHEAD_DET")
public class MbOverHeadDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MB_OVHID", nullable = false)
	private Long mbOvhId;

	@ManyToOne
	@JoinColumn(name = "OV_ID")
	private WorkEstimOverHeadDetails overHeadId;

	@ManyToOne
	@JoinColumn(name = "MB_ID")
	private MeasurementBookMaster mbMaster;

	
	@Column(name = "WORK_ID", nullable = false)
	private Long workId;
	
	
	@Column(name = "OV_VALUE", nullable = true)
	private BigDecimal overHeadValue;
	
	@Column(name = "ACT_AMT", nullable = true)
	private BigDecimal actualAmount;

	
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

	public MeasurementBookMaster getMbMaster() {
		return mbMaster;
	}

	public void setMbMaster(MeasurementBookMaster mbMaster) {
		this.mbMaster = mbMaster;
	}

	public Long getMbOvhId() {
		return mbOvhId;
	}

	public void setMbOvhId(Long mbOvhId) {
		this.mbOvhId = mbOvhId;
	}



	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public BigDecimal getOverHeadValue() {
		return overHeadValue;
	}

	public void setOverHeadValue(BigDecimal overHeadValue) {
		this.overHeadValue = overHeadValue;
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

	public WorkEstimOverHeadDetails getOverHeadId() {
		return overHeadId;
	}

	public void setOverHeadId(WorkEstimOverHeadDetails overHeadId) {
		this.overHeadId = overHeadId;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_MBOVERHEAD_DET", "MB_OVHID" };
	}



}
