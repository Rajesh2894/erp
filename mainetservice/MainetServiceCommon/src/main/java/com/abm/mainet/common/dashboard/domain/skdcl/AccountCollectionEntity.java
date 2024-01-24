package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountCollectionEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "DP_DEPTDESC")
	private String deptName;

	@Column(name = "CASH_MODE")
	private String cashMode;

	@Column(name = "CHEQUE_MODE")
	private String chequeMode;

	@Column(name = "DEMAND_DRAFT_MODE")
	private String demandDraftMode;

	@Column(name = "BANK_MODE")
	private String bankMode;

	@Column(name = "TRANSFER_MODE")
	private String transferMode;

	@Column(name = "PAY_ORDER_MODE")
	private String payOrderMode;

	@Column(name = "ADJUSTMENT_MODE")
	private String adjustmentMode;

	@Column(name = "REBATE_MODE")
	private String rebateMode;

	@Column(name = "USER_ADJUSTMENT_MODE")
	private String userAdjustmentMode;

	@Column(name = "USER_DISCOUNT_MODE")
	private String userDiscountMode;

	@Column(name = "WEB_MODE")
	private String webMode;

	@Column(name = "RTGS_MODE")
	private String rtgsMode;

	@Column(name = "NEFT_MODE")
	private String neftMode;

	@Column(name = "FDR_MODE")
	private String fdrMode;

	@Column(name = "POS_MODE")
	private String posMode;

	@Column(name = "PETTY_CASH_MODE")
	private String pettyCashMode;

	@Column(name = "ALL_MODE")
	private String allMode;

	@Column(name = "MONEY_RECEIPT_MODE")
	private String moneyReceiptMode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCashMode() {
		return cashMode;
	}

	public void setCashMode(String cashMode) {
		this.cashMode = cashMode;
	}

	public String getChequeMode() {
		return chequeMode;
	}

	public void setChequeMode(String chequeMode) {
		this.chequeMode = chequeMode;
	}

	public String getDemandDraftMode() {
		return demandDraftMode;
	}

	public void setDemandDraftMode(String demandDraftMode) {
		this.demandDraftMode = demandDraftMode;
	}

	public String getBankMode() {
		return bankMode;
	}

	public void setBankMode(String bankMode) {
		this.bankMode = bankMode;
	}

	public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}

	public String getPayOrderMode() {
		return payOrderMode;
	}

	public void setPayOrderMode(String payOrderMode) {
		this.payOrderMode = payOrderMode;
	}

	public String getAdjustmentMode() {
		return adjustmentMode;
	}

	public void setAdjustmentMode(String adjustmentMode) {
		this.adjustmentMode = adjustmentMode;
	}

	public String getRebateMode() {
		return rebateMode;
	}

	public void setRebateMode(String rebateMode) {
		this.rebateMode = rebateMode;
	}

	public String getUserAdjustmentMode() {
		return userAdjustmentMode;
	}

	public void setUserAdjustmentMode(String userAdjustmentMode) {
		this.userAdjustmentMode = userAdjustmentMode;
	}

	public String getUserDiscountMode() {
		return userDiscountMode;
	}

	public void setUserDiscountMode(String userDiscountMode) {
		this.userDiscountMode = userDiscountMode;
	}

	public String getWebMode() {
		return webMode;
	}

	public void setWebMode(String webMode) {
		this.webMode = webMode;
	}

	public String getRtgsMode() {
		return rtgsMode;
	}

	public void setRtgsMode(String rtgsMode) {
		this.rtgsMode = rtgsMode;
	}

	public String getNeftMode() {
		return neftMode;
	}

	public void setNeftMode(String neftMode) {
		this.neftMode = neftMode;
	}

	public String getFdrMode() {
		return fdrMode;
	}

	public void setFdrMode(String fdrMode) {
		this.fdrMode = fdrMode;
	}

	public String getPosMode() {
		return posMode;
	}

	public void setPosMode(String posMode) {
		this.posMode = posMode;
	}

	public String getPettyCashMode() {
		return pettyCashMode;
	}

	public void setPettyCashMode(String pettyCashMode) {
		this.pettyCashMode = pettyCashMode;
	}

	public String getAllMode() {
		return allMode;
	}

	public void setAllMode(String allMode) {
		this.allMode = allMode;
	}

	public String getMoneyReceiptMode() {
		return moneyReceiptMode;
	}

	public void setMoneyReceiptMode(String moneyReceiptMode) {
		this.moneyReceiptMode = moneyReceiptMode;
	}

	@Override
	public String toString() {
		return "AccountCollectionEntity [id=" + id + ", deptName=" + deptName + ", cashMode=" + cashMode
				+ ", chequeMode=" + chequeMode + ", demandDraftMode=" + demandDraftMode + ", bankMode=" + bankMode
				+ ", transferMode=" + transferMode + ", payOrderMode=" + payOrderMode + ", adjustmentMode="
				+ adjustmentMode + ", rebateMode=" + rebateMode + ", userAdjustmentMode=" + userAdjustmentMode
				+ ", userDiscountMode=" + userDiscountMode + ", webMode=" + webMode + ", rtgsMode=" + rtgsMode
				+ ", neftMode=" + neftMode + ", fdrMode=" + fdrMode + ", posMode=" + posMode + ", pettyCashMode="
				+ pettyCashMode + ", allMode=" + allMode + ", moneyReceiptMode=" + moneyReceiptMode + "]";
	}

}
