/**
 * 
 */
package com.abm.mainet.water.rest.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akshata.bhat
 *
 */
public class KDMCWaterDetailsResponseDTO {

	private String status;
	
	private Long orgId;
	
	private String connNo;
	
	private Long connId;
	
	private String ownerName;
	
	private List<String> ownerAdd = null;
	
	private Double totalPayAmt;
	
	private Double totalActPayAmt;
	
	private Double totalInterestAmt;

	private String billNo;
	
	private List<WaterTaxDetailsDto> waterTaxDetailList = new ArrayList<>();
	
	private Double adjustmentAmount;
	
	private Double advancePaymentAmount;

	private Integer langId;
	
	private String errorMsg;
	
	private String errorCode;
	
	private String cause;

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

	public String getConnNo() {
		return connNo;
	}

	public void setConnNo(String connNo) {
		this.connNo = connNo;
	}

	public Long getConnId() {
		return connId;
	}

	public void setConnId(Long connId) {
		this.connId = connId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public List<String> getOwnerAdd() {
		return ownerAdd;
	}

	public void setOwnerAdd(List<String> ownerAdd) {
		this.ownerAdd = ownerAdd;
	}

	public Double getTotalPayAmt() {
		return totalPayAmt;
	}

	public void setTotalPayAmt(Double totalPayAmt) {
		this.totalPayAmt = totalPayAmt;
	}

	public Double getTotalInterestAmt() {
		return totalInterestAmt;
	}

	public void setTotalInterestAmt(Double totalInterestAmt) {
		this.totalInterestAmt = totalInterestAmt;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Double getTotalActPayAmt() {
		return totalActPayAmt;
	}

	public void setTotalActPayAmt(Double totalActPayAmt) {
		this.totalActPayAmt = totalActPayAmt;
	}

	public Integer getLangId() {
		return langId;
	}

	public void setLangId(Integer langId) {
		this.langId = langId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public List<WaterTaxDetailsDto> getWaterTaxDetailList() {
		return waterTaxDetailList;
	}

	public void setWaterTaxDetailList(List<WaterTaxDetailsDto> waterTaxDetailList) {
		this.waterTaxDetailList = waterTaxDetailList;
	}

	public Double getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public void setAdjustmentAmount(Double adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	public Double getAdvancePaymentAmount() {
		return advancePaymentAmount;
	}

	public void setAdvancePaymentAmount(Double advancePaymentAmount) {
		this.advancePaymentAmount = advancePaymentAmount;
	}

	@Override
	public String toString() {
		return "KDMCWaterDetailsResponseDTO [status=" + status + ", orgId=" + orgId + ", connNo=" + connNo + ", connId="
				+ connId + ", ownerName=" + ownerName + ", ownerAdd=" + ownerAdd + ", totalPayAmt=" + totalPayAmt
				+ ", totalActPayAmt=" + totalActPayAmt + ", totalInterestAmt=" + totalInterestAmt + ", billNo=" + billNo
				+ ", waterTaxDetailList=" + waterTaxDetailList + ", adjustmentAmount=" + adjustmentAmount
				+ ", advancePaymentAmount=" + advancePaymentAmount + ", langId=" + langId + ", errorMsg=" + errorMsg
				+ ", errorCode=" + errorCode + ", cause=" + cause + "]";
	}
}
