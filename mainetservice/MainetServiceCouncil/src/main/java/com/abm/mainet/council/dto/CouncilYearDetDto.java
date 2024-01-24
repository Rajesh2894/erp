package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CouncilYearDetDto implements Serializable {

	private Long yearId;

	private Long cbId;

	private Long sacHeadId;

	private Long faYearId;

	private BigDecimal yearPercntWork;

	private String yeDocRefNo;

	private BigDecimal yeBugAmount;

	private String yeActive;

	private Long orgId;

	private Long createdBy;

	private Long updatedBy;

	private Date createdDate;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String financeCodeDesc;

	private String finActiveFlag;

	private String faYearFromTo;
	
	private String budgetCodeDesc;
	
	private Long filedId;
	
	private Long proposalDepId;
	
	private BigDecimal orginalAmount =new BigDecimal("0.0");
	private BigDecimal curntYrAmount=new BigDecimal("0.0");
	private BigDecimal nxtYrAmount=new BigDecimal("0.0");
	private BigDecimal crntNxtYrAmount=new BigDecimal("0.0");
	private BigDecimal amountForNewProposal=new BigDecimal("0.0");
	private BigDecimal currentYearAmount=new BigDecimal("0.0");
	private BigDecimal currentNextYearAmount=new BigDecimal("0.0");
	private BigDecimal remainingAmount=new BigDecimal("0.0");
	private BigDecimal crntAsOnAmount=new BigDecimal("0.0");
	

	public Long getYearId() {
		return yearId;
	}

	public void setYearId(Long yearId) {
		this.yearId = yearId;
	}

	public Long getCbId() {
		return cbId;
	}

	public void setCbId(Long cbId) {
		this.cbId = cbId;
	}

	public Long getSacHeadId() {
		return sacHeadId;
	}

	public void setSacHeadId(Long sacHeadId) {
		this.sacHeadId = sacHeadId;
	}

	public Long getFaYearId() {
		return faYearId;
	}

	public void setFaYearId(Long faYearId) {
		this.faYearId = faYearId;
	}

	public BigDecimal getYearPercntWork() {
		return yearPercntWork;
	}

	public void setYearPercntWork(BigDecimal yearPercntWork) {
		this.yearPercntWork = yearPercntWork;
	}

	public String getYeDocRefNo() {
		return yeDocRefNo;
	}

	public void setYeDocRefNo(String yeDocRefNo) {
		this.yeDocRefNo = yeDocRefNo;
	}

	public BigDecimal getYeBugAmount() {
		return yeBugAmount;
	}

	public void setYeBugAmount(BigDecimal yeBugAmount) {
		this.yeBugAmount = yeBugAmount;
	}

	public String getYeActive() {
		return yeActive;
	}

	public void setYeActive(String yeActive) {
		this.yeActive = yeActive;
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

	public String getFinanceCodeDesc() {
		return financeCodeDesc;
	}

	public void setFinanceCodeDesc(String financeCodeDesc) {
		this.financeCodeDesc = financeCodeDesc;
	}

	public String getFinActiveFlag() {
		return finActiveFlag;
	}

	public void setFinActiveFlag(String finActiveFlag) {
		this.finActiveFlag = finActiveFlag;
	}

	public String getFaYearFromTo() {
		return faYearFromTo;
	}

	public void setFaYearFromTo(String faYearFromTo) {
		this.faYearFromTo = faYearFromTo;
	}

	public BigDecimal getOrginalAmount() {
		return orginalAmount;
	}

	public void setOrginalAmount(BigDecimal orginalAmount) {
		this.orginalAmount = orginalAmount;
	}

	public BigDecimal getCurntYrAmount() {
		return curntYrAmount;
	}

	public void setCurntYrAmount(BigDecimal curntYrAmount) {
		this.curntYrAmount = curntYrAmount;
	}

	public BigDecimal getNxtYrAmount() {
		return nxtYrAmount;
	}

	public void setNxtYrAmount(BigDecimal nxtYrAmount) {
		this.nxtYrAmount = nxtYrAmount;
	}

	public BigDecimal getAmountForNewProposal() {
		return amountForNewProposal;
	}

	public void setAmountForNewProposal(BigDecimal amountForNewProposal) {
		this.amountForNewProposal = amountForNewProposal;
	}

	public BigDecimal getCurrentYearAmount() {
		return currentYearAmount;
	}

	public void setCurrentYearAmount(BigDecimal currentYearAmount) {
		this.currentYearAmount = currentYearAmount;
	}

	public BigDecimal getCurrentNextYearAmount() {
		return currentNextYearAmount;
	}

	public void setCurrentNextYearAmount(BigDecimal currentNextYearAmount) {
		this.currentNextYearAmount = currentNextYearAmount;
	}

	public BigDecimal getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(BigDecimal remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public BigDecimal getCrntNxtYrAmount() {
		return crntNxtYrAmount;
	}

	public void setCrntNxtYrAmount(BigDecimal crntNxtYrAmount) {
		this.crntNxtYrAmount = crntNxtYrAmount;
	}

	public BigDecimal getCrntAsOnAmount() {
		return crntAsOnAmount;
	}

	public void setCrntAsOnAmount(BigDecimal crntAsOnAmount) {
		this.crntAsOnAmount = crntAsOnAmount;
	}

	public String getBudgetCodeDesc() {
		return budgetCodeDesc;
	}

	public void setBudgetCodeDesc(String budgetCodeDesc) {
		this.budgetCodeDesc = budgetCodeDesc;
	}

	public Long getFiledId() {
		return filedId;
	}

	public void setFiledId(Long filedId) {
		this.filedId = filedId;
	}

	public Long getProposalDepId() {
		return proposalDepId;
	}

	public void setProposalDepId(Long proposalDepId) {
		this.proposalDepId = proposalDepId;
	}

}
