package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.MbOverHeadDetails;

/**
 * @author vishwajeet.kumar
 *
 */
public class WorkEstimOverHeadDetDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long overHeadId;

	private Long workId;

	private String overHeadCode;

	private String overheadDesc;

	private Long overHeadvalType;

	private BigDecimal overHeadRate;

	private BigDecimal workEstimAmount;

	private BigDecimal overHeadValue;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private String active;
	//from
	private String meRemark;
	
	public String getMeRemark() {
		return meRemark;
	}

	public void setMeRemark(String meRemark) {
		this.meRemark = meRemark;
	}//to

	private List<WorkEstimOverHeadDetDto> overHeadDetailsList = new ArrayList<>();
	 
	 private List<MbOverHeadDetDto> mbOverHeadDetDto = new ArrayList<>();
	 
	 private MbOverHeadDetDto mbOverheadDetails = new MbOverHeadDetDto();

	public Long getOverHeadId() {
		return overHeadId;
	}

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

    public List<WorkEstimOverHeadDetDto> getOverHeadDetailsList() {
        return overHeadDetailsList;
    }

    public void setOverHeadDetailsList(List<WorkEstimOverHeadDetDto> overHeadDetailsList) {
        this.overHeadDetailsList = overHeadDetailsList;
    }

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<MbOverHeadDetDto> getMbOverHeadDetDto() {
		return mbOverHeadDetDto;
	}

	public void setMbOverHeadDetDto(List<MbOverHeadDetDto> mbOverHeadDetDto) {
		this.mbOverHeadDetDto = mbOverHeadDetDto;
	}

	public MbOverHeadDetDto getMbOverheadDetails() {
		return mbOverheadDetails;
	}

	public void setMbOverheadDetails(MbOverHeadDetDto mbOverheadDetails) {
		this.mbOverheadDetails = mbOverheadDetails;
	}
	

}
