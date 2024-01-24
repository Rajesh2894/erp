package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author vishwajeet.kumar
 * @since 22 March 2018
 */
public class MileStoneDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long mileId;

	private Long workId;

	private Long projId;

	private String mileStoneDesc;

	private BigDecimal mileStoneWeight;

	private String msStartDate;

	private String msEndDate;

	private BigDecimal msPercent;

	private String mileStoneType;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String projName;
	private String projNameReg;

	private String lgIpMacUpd;
	private String workName;

	private Long mileStoneId;
	
	private BigDecimal mileStoneAmount;

	public BigDecimal getMileStoneAmount() {
		return mileStoneAmount;
	}

	public void setMileStoneAmount(BigDecimal bigDecimal) {
		this.mileStoneAmount = bigDecimal;
	}

	public Long getMileStoneId() {
		return mileStoneId;
	}

	public void setMileStoneId(Long mileStoneId) {
		this.mileStoneId = mileStoneId;
	}

	public Long getMileId() {
		return mileId;
	}

	public void setMileId(Long mileId) {
		this.mileId = mileId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String getMileStoneDesc() {
		return mileStoneDesc;
	}

	public void setMileStoneDesc(String mileStoneDesc) {
		this.mileStoneDesc = mileStoneDesc;
	}

	public BigDecimal getMileStoneWeight() {
		return mileStoneWeight;
	}

	public void setMileStoneWeight(BigDecimal mileStoneWeight) {
		this.mileStoneWeight = mileStoneWeight;
	}

	public String getMsStartDate() {
		return msStartDate;
	}

	public void setMsStartDate(String msStartDate) {
		this.msStartDate = msStartDate;
	}

	public String getMsEndDate() {
		return msEndDate;
	}

	public void setMsEndDate(String msEndDate) {
		this.msEndDate = msEndDate;
	}

	public BigDecimal getMsPercent() {
		return msPercent;
	}

	public void setMsPercent(BigDecimal msPercent) {
		this.msPercent = msPercent;
	}

	public String getMileStoneType() {
		return mileStoneType;
	}

	public void setMileStoneType(String mileStoneType) {
		this.mileStoneType = mileStoneType;
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

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getProjNameReg() {
		return projNameReg;
	}

	public void setProjNameReg(String projNameReg) {
		this.projNameReg = projNameReg;
	}

}
