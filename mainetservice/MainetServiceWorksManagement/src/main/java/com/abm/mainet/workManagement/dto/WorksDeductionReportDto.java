package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WorksDeductionReportDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long vmVendorid;
	
	private String vmVendorname;
	
	private Long projectId;
	
	private Long workId;
	
	private String projNameEng;

	private String projNameReg;

	private String projStartDate;

	private String projEndDate;
	
	private String workcode;

	private String workName;

	private Date contractFromDate;;

	private Date contractToDate;
	
	private String contractFromDateDesc;
	
	private String contractToDateDesc;

	private Long taxId;
	
	private String taxDesc;
	
	private Date bmDate;
	
	private String bmDateDesc;
	
	private BigDecimal mbTaxValue;
	
	private Long sacHeadId;
	
	private String raCode;

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

	public String getProjStartDate() {
		return projStartDate;
	}

	public void setProjStartDate(String projStartDate) {
		this.projStartDate = projStartDate;
	}

	public String getProjEndDate() {
		return projEndDate;
	}

	public void setProjEndDate(String projEndDate) {
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

	public Date getContractFromDate() {
		return contractFromDate;
	}

	public void setContractFromDate(Date contractFromDate) {
		this.contractFromDate = contractFromDate;
	}

	public Date getContractToDate() {
		return contractToDate;
	}

	public void setContractToDate(Date contractToDate) {
		this.contractToDate = contractToDate;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

	public Date getBmDate() {
		return bmDate;
	}

	public void setBmDate(Date bmDate) {
		this.bmDate = bmDate;
	}

	public BigDecimal getMbTaxValue() {
		return mbTaxValue;
	}

	public void setMbTaxValue(BigDecimal mbTaxValue) {
		this.mbTaxValue = mbTaxValue;
	}

	public Long getSacHeadId() {
		return sacHeadId;
	}

	public void setSacHeadId(Long sacHeadId) {
		this.sacHeadId = sacHeadId;
	}

	public String getContractFromDateDesc() {
		return contractFromDateDesc;
	}

	public void setContractFromDateDesc(String contractFromDateDesc) {
		this.contractFromDateDesc = contractFromDateDesc;
	}

	public String getContractToDateDesc() {
		return contractToDateDesc;
	}

	public void setContractToDateDesc(String contractToDateDesc) {
		this.contractToDateDesc = contractToDateDesc;
	}

	public String getBmDateDesc() {
		return bmDateDesc;
	}

	public void setBmDateDesc(String bmDateDesc) {
		this.bmDateDesc = bmDateDesc;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}

	public String getRaCode() {
		return raCode;
	}

	public void setRaCode(String raCode) {
		this.raCode = raCode;
	}
	
}
