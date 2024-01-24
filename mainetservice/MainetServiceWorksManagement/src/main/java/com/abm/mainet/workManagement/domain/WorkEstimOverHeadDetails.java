package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author vishwajeet.kumar
 *
 */
@Entity
@Table(name = "TB_WMS_OVERHEAD_DETAIL")
public class WorkEstimOverHeadDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "OV_ID", nullable = false)
	private Long overHeadId;

	@Column(name = "WORK_ID", nullable = true)
	private Long workId;

	@Column(name = "OV_CODE", nullable = false)
	private String overHeadCode;

	@Column(name = "OV_DESCRIPTION", nullable = false)
	private String overheadDesc;

	@Column(name = "OV_VALUE_TYPE", nullable = false)
	private Long overHeadvalType;

	@Column(name = "OV_RATE", nullable = false)
	private BigDecimal overHeadRate;

	@Column(name = "WORK_EST_AMT", nullable = false)
	private BigDecimal workEstimAmount;

	@Column(name = "OV_VALUE", nullable = false)
	private BigDecimal overHeadValue;
	
	@Column(name = "OV_ACTIVE", nullable = false)
	private String active;

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
	
	//from
	@Column(name = "ME_REMARK", nullable = true) 
	private String meRemark;//to
	 
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "overHeadId", cascade = CascadeType.ALL)
	private List<MbOverHeadDetails> mbOverheadDetails = new ArrayList<>();

	public Long getOverHeadId() {
		return overHeadId;
	}
	//from
	public String getMeRemark() {
		return meRemark;
	}

	public void setMeRemark(String meRemark) {
		this.meRemark = meRemark;
	}//to

	public void setOverHeadId(Long overHeadId) {
		this.overHeadId = overHeadId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getOverHeadCode() {
		return overHeadCode;
	}

	public void setOverHeadCode(String overHeadCode) {
		this.overHeadCode = overHeadCode;
	}

	public String getOverheadDesc() {
		return overheadDesc;
	}

	public void setOverheadDesc(String overheadDesc) {
		this.overheadDesc = overheadDesc;
	}

	public Long getOverHeadvalType() {
		return overHeadvalType;
	}

	public void setOverHeadvalType(Long overHeadvalType) {
		this.overHeadvalType = overHeadvalType;
	}

	public BigDecimal getOverHeadRate() {
		return overHeadRate;
	}

	public void setOverHeadRate(BigDecimal overHeadRate) {
		this.overHeadRate = overHeadRate;
	}

	public BigDecimal getWorkEstimAmount() {
		return workEstimAmount;
	}

	public void setWorkEstimAmount(BigDecimal workEstimAmount) {
		this.workEstimAmount = workEstimAmount;
	}

	public BigDecimal getOverHeadValue() {
		return overHeadValue;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_OVERHEAD_DETAIL", "OV_ID" };
	}

	public List<MbOverHeadDetails> getMbOverheadDetails() {
		return mbOverheadDetails;
	}

	public void setMbOverheadDetails(List<MbOverHeadDetails> mbOverheadDetails) {
		this.mbOverheadDetails = mbOverheadDetails;
	}
	
}
