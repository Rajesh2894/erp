package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WorksBudgetReportDto implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private Long projId;
	
	private String projCode;
	
	private String projNameEng;
	
	private String projNameReg;
	
	private Date projStartDate;
	
	private Date projEndDate;
	
	private String projStartDateDesc;
	
	private String projEndDateDesc;
	
	private Long workId;
	
	private String workcode;
	
	private String workName;
	
	private Date workStartDate;
	
	private Date workEndDate;
	
	private String workStartDateDesc;
	
	private String workEndDateDesc;
	
	private Long sacHeadId;
	
	private String acHeadCode;
	
	private String financeCodeDesc;
	
	private BigDecimal contAmount;
	
	private BigDecimal mbTotalAmt;
	
	private BigDecimal raBillTaxAmt;
	
	private BigDecimal mbTotalAmtUnderApproval;
	
	private BigDecimal budgetBalanceAmt;
	
	private BigDecimal budgetBalancePerc;
	
	private String mbStatus;
	
	private Long taxId;

	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
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

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Long getSacHeadId() {
		return sacHeadId;
	}

	public void setSacHeadId(Long sacHeadId) {
		this.sacHeadId = sacHeadId;
	}

	public String getFinanceCodeDesc() {
		return financeCodeDesc;
	}

	public void setFinanceCodeDesc(String financeCodeDesc) {
		this.financeCodeDesc = financeCodeDesc;
	}

	public BigDecimal getContAmount() {
		return contAmount;
	}

	public void setContAmount(BigDecimal contAmount) {
		this.contAmount = contAmount;
	}

	public BigDecimal getMbTotalAmt() {
		return mbTotalAmt;
	}

	public void setMbTotalAmt(BigDecimal mbTotalAmt) {
		this.mbTotalAmt = mbTotalAmt;
	}

	public String getAcHeadCode() {
		return acHeadCode;
	}

	public void setAcHeadCode(String acHeadCode) {
		this.acHeadCode = acHeadCode;
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

	public String getMbStatus() {
		return mbStatus;
	}

	public void setMbStatus(String mbStatus) {
		this.mbStatus = mbStatus;
	}

	public BigDecimal getMbTotalAmtUnderApproval() {
		return mbTotalAmtUnderApproval;
	}

	public void setMbTotalAmtUnderApproval(BigDecimal mbTotalAmtUnderApproval) {
		this.mbTotalAmtUnderApproval = mbTotalAmtUnderApproval;
	}

	public BigDecimal getBudgetBalanceAmt() {
		return budgetBalanceAmt;
	}

	public void setBudgetBalanceAmt(BigDecimal budgetBalanceAmt) {
		this.budgetBalanceAmt = budgetBalanceAmt;
	}

	public BigDecimal getBudgetBalancePerc() {
		return budgetBalancePerc;
	}

	public void setBudgetBalancePerc(BigDecimal budgetBalancePerc) {
		this.budgetBalancePerc = budgetBalancePerc;
	}

	public BigDecimal getRaBillTaxAmt() {
		return raBillTaxAmt;
	}

	public void setRaBillTaxAmt(BigDecimal raBillTaxAmt) {
		this.raBillTaxAmt = raBillTaxAmt;
	}

	
	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}
	
}
