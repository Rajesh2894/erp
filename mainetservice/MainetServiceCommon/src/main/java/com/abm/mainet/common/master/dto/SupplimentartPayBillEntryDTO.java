package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplimentartPayBillEntryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String empId;

	private String empName;

	private BigDecimal basicPay;

	private BigDecimal gradePay;

	private String designation;

	private String payMonth;

	private String payYear;

	private BigDecimal refELeave;

	private BigDecimal eLeave;

	private BigDecimal refMLeave;

	private BigDecimal mLeave;

	private BigDecimal hpLeave;

	private BigDecimal refHpLeave;

	private BigDecimal workDays;

	private BigDecimal refWorkDays;

	private String remark;

	private BigDecimal grossAmount;

	private BigDecimal deductionAmount;

	private BigDecimal netPay;

	private BigDecimal professionalTax;

	private BigDecimal stampAmount;

	private BigDecimal pFAmount;

	private BigDecimal otherDeductions;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public BigDecimal getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(BigDecimal basicPay) {
		this.basicPay = basicPay;
	}

	public BigDecimal getGradePay() {
		return gradePay;
	}

	public void setGradePay(BigDecimal gradePay) {
		this.gradePay = gradePay;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}

	public String getPayYear() {
		return payYear;
	}

	public void setPayYear(String payYear) {
		this.payYear = payYear;
	}

	public BigDecimal getRefELeave() {
		return refELeave;
	}

	public void setRefELeave(BigDecimal refELeave) {
		this.refELeave = refELeave;
	}

	public BigDecimal geteLeave() {
		return eLeave;
	}

	public void seteLeave(BigDecimal eLeave) {
		this.eLeave = eLeave;
	}

	public BigDecimal getRefMLeave() {
		return refMLeave;
	}

	public void setRefMLeave(BigDecimal refMLeave) {
		this.refMLeave = refMLeave;
	}

	public BigDecimal getmLeave() {
		return mLeave;
	}

	public void setmLeave(BigDecimal mLeave) {
		this.mLeave = mLeave;
	}

	public BigDecimal getHpLeave() {
		return hpLeave;
	}

	public void setHpLeave(BigDecimal hpLeave) {
		this.hpLeave = hpLeave;
	}

	public BigDecimal getRefHpLeave() {
		return refHpLeave;
	}

	public void setRefHpLeave(BigDecimal refHpLeave) {
		this.refHpLeave = refHpLeave;
	}

	public BigDecimal getWorkDays() {
		return workDays;
	}

	public void setWorkDays(BigDecimal workDays) {
		this.workDays = workDays;
	}

	public BigDecimal getRefWorkDays() {
		return refWorkDays;
	}

	public void setRefWorkDays(BigDecimal refWorkDays) {
		this.refWorkDays = refWorkDays;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(BigDecimal grossAmount) {
		this.grossAmount = grossAmount;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public BigDecimal getNetPay() {
		return netPay;
	}

	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}

	public BigDecimal getProfessionalTax() {
		return professionalTax;
	}

	public void setProfessionalTax(BigDecimal professionalTax) {
		this.professionalTax = professionalTax;
	}

	public BigDecimal getStampAmount() {
		return stampAmount;
	}

	public void setStampAmount(BigDecimal stampAmount) {
		this.stampAmount = stampAmount;
	}

	public BigDecimal getpFAmount() {
		return pFAmount;
	}

	public void setpFAmount(BigDecimal pFAmount) {
		this.pFAmount = pFAmount;
	}

	public BigDecimal getOtherDeductions() {
		return otherDeductions;
	}

	public void setOtherDeductions(BigDecimal otherDeductions) {
		this.otherDeductions = otherDeductions;
	}

}