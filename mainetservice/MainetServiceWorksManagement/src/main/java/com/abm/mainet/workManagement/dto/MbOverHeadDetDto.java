package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.workManagement.domain.MeasurementBookMaster;

/**
 * @author pooja.maske
 *
 */
public class MbOverHeadDetDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long mbOvhId;
	
	private BigDecimal overHeadValue;
	
	private BigDecimal actualAmount;
	
	private Long workId;
	
	private WorkEstimOverHeadDetDto workEstimOverHeadDetDto;
	
	private MeasurementBookMasterDto measurementBookMasterDto;
	
	private Long overHeadId;
	
	private String overHeadCode;

	private String overheadDesc;

	private Long overHeadvalType;

	private BigDecimal overHeadRate;
	
	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long mbMasterId;

	public Long getMbMasterId() {
		return mbMasterId;
	}

	public void setMbMasterId(Long mbMasterId) {
		this.mbMasterId = mbMasterId;
	}

	public MeasurementBookMasterDto getMeasurementBookMasterDto() {
		return measurementBookMasterDto;
	}

	public void setMeasurementBookMasterDto(MeasurementBookMasterDto measurementBookMasterDto) {
		this.measurementBookMasterDto = measurementBookMasterDto;
	}

	public Long getMbOvhId() {
		return mbOvhId;
	}

	public void setMbOvhId(Long mbOvhId) {
		this.mbOvhId = mbOvhId;
	}

	public BigDecimal getOverHeadValue() {
		return overHeadValue;
	}

	public void setOverHeadValue(BigDecimal overHeadValue) {
		this.overHeadValue = overHeadValue;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	
	public WorkEstimOverHeadDetDto getWorkEstimOverHeadDetDto() {
		return workEstimOverHeadDetDto;
	}

	public void setWorkEstimOverHeadDetDto(WorkEstimOverHeadDetDto workEstimOverHeadDetDto) {
		this.workEstimOverHeadDetDto = workEstimOverHeadDetDto;
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

	public Long getOverHeadId() {
		return overHeadId;
	}

	public void setOverHeadId(Long overHeadId) {
		this.overHeadId = overHeadId;
	}


}
