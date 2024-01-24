package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WmsProjectStatusReportDetDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String projNameEng;
	
	private String projNameReg;

	private Long schId;

	private String wmSchNameEng;
	
	private String wmSchNameReg;

	private Long workType;
	
	private String workeEstimateNo;
	
	private BigDecimal workEstimAmount;
	
	private BigDecimal tenderAmount;
	
	private String status;

	private Long orgId;

	private Long createdBy;

	private Long updatedBy;

	private Date createdDate;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String workName;

	private Long workId;
	
	private Date billReceivedTillDate;
	
	private String billReceivedDateDesc;
	
	private Date paymentisDoneTillDate;
	
	private String paymentisDoneTillDateDesc;
	
	private String workTypeDesc;

	private Long projId;

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String getProjNameEng() {
		return projNameEng;
	}

	public void setProjNameEng(String projNameEng) {
		this.projNameEng = projNameEng;
	}

	public Long getSchId() {
		return schId;
	}

	public void setSchId(Long schId) {
		this.schId = schId;
	}

	public Long getWorkType() {
		return workType;
	}

	public void setWorkType(Long workType) {
		this.workType = workType;
	}

	public String getWorkeEstimateNo() {
		return workeEstimateNo;
	}

	public void setWorkeEstimateNo(String workeEstimateNo) {
		this.workeEstimateNo = workeEstimateNo;
	}

	public BigDecimal getWorkEstimAmount() {
		return workEstimAmount;
	}

	public void setWorkEstimAmount(BigDecimal workEstimAmount) {
		this.workEstimAmount = workEstimAmount;
	}

	public BigDecimal getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(BigDecimal tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Date getBillReceivedTillDate() {
		return billReceivedTillDate;
	}

	public void setBillReceivedTillDate(Date billReceivedTillDate) {
		this.billReceivedTillDate = billReceivedTillDate;
	}

	public Date getPaymentisDoneTillDate() {
		return paymentisDoneTillDate;
	}

	public void setPaymentisDoneTillDate(Date paymentisDoneTillDate) {
		this.paymentisDoneTillDate = paymentisDoneTillDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getWorkTypeDesc() {
		return workTypeDesc;
	}

	public void setWorkTypeDesc(String workTypeDesc) {
		this.workTypeDesc = workTypeDesc;
	}

	public String getWmSchNameEng() {
		return wmSchNameEng;
	}

	public void setWmSchNameEng(String wmSchNameEng) {
		this.wmSchNameEng = wmSchNameEng;
	}

	public String getWmSchNameReg() {
		return wmSchNameReg;
	}

	public void setWmSchNameReg(String wmSchNameReg) {
		this.wmSchNameReg = wmSchNameReg;
	}

	public String getProjNameReg() {
		return projNameReg;
	}

	public void setProjNameReg(String projNameReg) {
		this.projNameReg = projNameReg;
	}

	public String getBillReceivedDateDesc() {
		return billReceivedDateDesc;
	}

	public void setBillReceivedDateDesc(String billReceivedDateDesc) {
		this.billReceivedDateDesc = billReceivedDateDesc;
	}

	public String getPaymentisDoneTillDateDesc() {
		return paymentisDoneTillDateDesc;
	}

	public void setPaymentisDoneTillDateDesc(String paymentisDoneTillDateDesc) {
		this.paymentisDoneTillDateDesc = paymentisDoneTillDateDesc;
	}

	
}
