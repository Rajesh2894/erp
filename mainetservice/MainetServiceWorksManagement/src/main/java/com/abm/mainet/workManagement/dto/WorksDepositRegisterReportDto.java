package com.abm.mainet.workManagement.dto;

import java.util.Date;
import java.math.BigDecimal;

public class WorksDepositRegisterReportDto {
	
	private Long vmVendorid;
	
	private String vmVendorname;
	
	private Long projectId;
	
	private Long workId;
	
	private String projNameEng;

	private String projNameReg;

	private Date projStartDate;
	
	private String projStartDateDesc;
	
	private String projEndDateDesc;

	private Date projEndDate;
	
	private String workcode;

	private String workName;

	private Date workStartDate;

	private Date workEndDate;
	
	private String workStartDateDesc;
	
	private String workEndDateDesc;
	
	private String workStatus;
	
	private String cpdDesc;
	
	private String cpdDescMar;
	
	private Date depDate;
	
	private String depDateDesc;
	
	private BigDecimal depAmount;
	
	private Long rmRcptno;
	
	 private Date rmDate;
	 
	 private BigDecimal totalSum;
	 
	 
	public BigDecimal getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(BigDecimal totalSum) {
		this.totalSum = totalSum;
	}

	public Long getVmVendorid() {
		return vmVendorid;
	}

	public void setVmVendorid(Long vmVendorid) {
		this.vmVendorid = vmVendorid;
	}

	public String getVmVendorname() {
		return vmVendorname;
	}

	public void setVmVendorname(String vmVendorname) {
		this.vmVendorname = vmVendorname;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getProjNameEng() {
		return projNameEng;
	}

	public void setProjNameEng(String projNameEng) {
		this.projNameEng = projNameEng;
	}

	public String getProjNameReg() {
		return projNameReg;
	}

	public void setProjNameReg(String projNameReg) {
		this.projNameReg = projNameReg;
	}

	public Date getProjStartDate() {
		return projStartDate;
	}

	public void setProjStartDate(Date projStartDate) {
		this.projStartDate = projStartDate;
	}

	public Date getProjEndDate() {
		return projEndDate;
	}

	public void setProjEndDate(Date projEndDate) {
		this.projEndDate = projEndDate;
	}

	public String getWorkcode() {
		return workcode;
	}

	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Date getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}

	public Date getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(Date workEndDate) {
		this.workEndDate = workEndDate;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getCpdDesc() {
		return cpdDesc;
	}

	public void setCpdDesc(String cpdDesc) {
		this.cpdDesc = cpdDesc;
	}

	public String getCpdDescMar() {
		return cpdDescMar;
	}

	public void setCpdDescMar(String cpdDescMar) {
		this.cpdDescMar = cpdDescMar;
	}

	public String getProjStartDateDesc() {
		return projStartDateDesc;
	}

	public void setProjStartDateDesc(String projStartDateDesc) {
		this.projStartDateDesc = projStartDateDesc;
	}

	public String getProjEndDateDesc() {
		return projEndDateDesc;
	}

	public void setProjEndDateDesc(String projEndDateDesc) {
		this.projEndDateDesc = projEndDateDesc;
	}

	public String getWorkStartDateDesc() {
		return workStartDateDesc;
	}

	public void setWorkStartDateDesc(String workStartDateDesc) {
		this.workStartDateDesc = workStartDateDesc;
	}

	public String getWorkEndDateDesc() {
		return workEndDateDesc;
	}

	public void setWorkEndDateDesc(String workEndDateDesc) {
		this.workEndDateDesc = workEndDateDesc;
	}

	public Date getDepDate() {
		return depDate;
	}

	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}

	public BigDecimal getDepAmount() {
		return depAmount;
	}

	public void setDepAmount(BigDecimal depAmount) {
		this.depAmount = depAmount;
	}

	public String getDepDateDesc() {
		return depDateDesc;
	}

	public void setDepDateDesc(String depDateDesc) {
		this.depDateDesc = depDateDesc;
	}

	public Long getRmRcptno() {
		return rmRcptno;
	}

	public void setRmRcptno(Long rmRcptno) {
		this.rmRcptno = rmRcptno;
	}

	public Date getRmDate() {
		return rmDate;
	}

	public void setRmDate(Date rmDate) {
		this.rmDate = rmDate;
	}
	
	/*private String depositType;
	
	private Date depositDate;
	
	private BigDecimal depositAmount;*/
	
	


}
