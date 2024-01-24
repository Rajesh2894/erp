package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class WorkCompletionRegisterDto implements Serializable {

	private static final long serialVersionUID = -5011060687842739071L;

	private Long workId;
	private String workName;
	private Long locIdSt;
	private String workOrderStartDate;
	private String agreeFromDate;
	private String agreeToDate;
	private String raCode;
	private String contractorName;
	private Long mbId;
	private BigDecimal workActualQty;
	private BigDecimal workUtlQty;
	private BigDecimal workActualAmt;
	private BigDecimal workEstAmt;
	private String sorDIteamNo;
	private String sorDDescription;
	private String sorIteamUnit;
	private BigDecimal sorBasicRate;
	private BigDecimal workEstimQuantity;

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Long getLocIdSt() {
		return locIdSt;
	}

	public void setLocIdSt(Long locIdSt) {
		this.locIdSt = locIdSt;
	}

	public String getWorkOrderStartDate() {
		return workOrderStartDate;
	}

	public void setWorkOrderStartDate(String workOrderStartDate) {
		this.workOrderStartDate = workOrderStartDate;
	}

	public String getAgreeFromDate() {
		return agreeFromDate;
	}

	public void setAgreeFromDate(String agreeFromDate) {
		this.agreeFromDate = agreeFromDate;
	}

	public String getAgreeToDate() {
		return agreeToDate;
	}

	public void setAgreeToDate(String agreeToDate) {
		this.agreeToDate = agreeToDate;
	}

	public String getRaCode() {
		return raCode;
	}

	public void setRaCode(String raCode) {
		this.raCode = raCode;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public Long getMbId() {
		return mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

	public BigDecimal getWorkActualQty() {
		return workActualQty;
	}

	public void setWorkActualQty(BigDecimal workActualQty) {
		this.workActualQty = workActualQty;
	}

	public BigDecimal getWorkUtlQty() {
		return workUtlQty;
	}

	public void setWorkUtlQty(BigDecimal workUtlQty) {
		this.workUtlQty = workUtlQty;
	}

	public BigDecimal getWorkActualAmt() {
		return workActualAmt;
	}

	public void setWorkActualAmt(BigDecimal workActualAmt) {
		this.workActualAmt = workActualAmt;
	}

	public BigDecimal getWorkEstAmt() {
		return workEstAmt;
	}

	public void setWorkEstAmt(BigDecimal workEstAmt) {
		this.workEstAmt = workEstAmt;
	}

	public String getSorDIteamNo() {
		return sorDIteamNo;
	}

	public void setSorDIteamNo(String sorDIteamNo) {
		this.sorDIteamNo = sorDIteamNo;
	}

	public String getSorDDescription() {
		return sorDDescription;
	}

	public void setSorDDescription(String sorDDescription) {
		this.sorDDescription = sorDDescription;
	}


	public String getSorIteamUnit() {
		return sorIteamUnit;
	}

	public void setSorIteamUnit(String sorIteamUnit) {
		this.sorIteamUnit = sorIteamUnit;
	}

	public BigDecimal getSorBasicRate() {
		return sorBasicRate;
	}

	public void setSorBasicRate(BigDecimal sorBasicRate) {
		this.sorBasicRate = sorBasicRate;
	}

	public BigDecimal getWorkEstimQuantity() {
		return workEstimQuantity;
	}

	public void setWorkEstimQuantity(BigDecimal workEstimQuantity) {
		this.workEstimQuantity = workEstimQuantity;
	}
}
