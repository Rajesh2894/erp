package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class MeasurementBookDetailsDto implements Serializable {

	private static final long serialVersionUID = -3219353535769324614L;

	private Long mbdId;
	private Long workEstemateId;
	private WorkEstimateMasterDto workEstimateMaster;
	private String workFlag;
	private String workEstimateType;
	private BigDecimal workActualQty;
	private BigDecimal workUtlQty;
	private BigDecimal workActualAmt;
	private BigDecimal workUsedQty;
	private MeasurementBookMasterDto measurementBookMaster;
	private Long mbDetPId;
	private boolean mbDetails;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private boolean checked;
	private Long mbId;
	private Long rateId;
	private BigDecimal sorRate;
	private String sorSubDescription;
	

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public Long getMbdId() {
		return mbdId;
	}

	public void setMbdId(Long mbdId) {
		this.mbdId = mbdId;
	}

	public Long getWorkEstemateId() {
		return workEstemateId;
	}

	public void setWorkEstemateId(Long workEstemateId) {
		this.workEstemateId = workEstemateId;
	}

	public WorkEstimateMasterDto getWorkEstimateMaster() {
		return workEstimateMaster;
	}

	public void setWorkEstimateMaster(WorkEstimateMasterDto workEstimateMaster) {
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

	public BigDecimal getWorkActualAmt() {
		return workActualAmt;
	}

	public void setWorkActualAmt(BigDecimal workActualAmt) {
		this.workActualAmt = workActualAmt;
	}

	public MeasurementBookMasterDto getMeasurementBookMaster() {
		return measurementBookMaster;
	}

	public void setMeasurementBookMaster(MeasurementBookMasterDto measurementBookMaster) {
		this.measurementBookMaster = measurementBookMaster;
	}

	public Long getMbDetPId() {
		return mbDetPId;
	}

	public void setMbDetPId(Long mbDetPId) {
		this.mbDetPId = mbDetPId;
	}

	public boolean isMbDetails() {
		return mbDetails;
	}

	public void setMbDetails(boolean mbDetails) {
		this.mbDetails = mbDetails;
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public BigDecimal getWorkUsedQty() {
		return workUsedQty;
	}

	public void setWorkUsedQty(BigDecimal workUsedQty) {
		this.workUsedQty = workUsedQty;
	}

	public Long getMbId() {
		return mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

	public BigDecimal getSorRate() {
		return sorRate;
	}

	public void setSorRate(BigDecimal sorRate) {
		this.sorRate = sorRate;
	}

	public BigDecimal getWorkUtlQty() {
		return workUtlQty;
	}

	public void setWorkUtlQty(BigDecimal workUtlQty) {
		this.workUtlQty = workUtlQty;
	}

	public String getSorSubDescription() {
		return sorSubDescription;
	}

	public void setSorSubDescription(String sorSubDescription) {
		this.sorSubDescription = sorSubDescription;
	}

}
