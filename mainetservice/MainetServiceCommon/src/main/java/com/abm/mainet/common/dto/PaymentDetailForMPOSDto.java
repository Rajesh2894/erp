/**
 * 
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * @author cherupelli.srikanth
 * @since 18 Jan 2023
 */
public class PaymentDetailForMPOSDto implements Serializable {

	
	private static final long serialVersionUID = -762231796062448826L;

	private String billIdentifier;
	
	private Long plTransactionId;
	
	private Long paymentMode;
	
	private String transactionTime;
	
	private Long transactionAmount;
	
	private CashModeInfoDto cashModeInfo = null;
	
	private CardModeInfoDto cardModeInfo = null;
	
	private ChequeModeInfoDto chequeModeInfo = null;
	
	private QrBasedModeInfoDto qrBasedModeInfo = null;
	
	private VoucherModeInfoDto voucherModeInfo = null;
	
	private WalletModeInfoDto walletModeInfo = null;
	
	private SodexoModeInfoDto sodexoModeInfo = null;
	
	private String mobileNo;

	public String getBillIdentifier() {
		return billIdentifier;
	}

	public void setBillIdentifier(String billIdentifier) {
		this.billIdentifier = billIdentifier;
	}

	public Long getPlTransactionId() {
		return plTransactionId;
	}

	public void setPlTransactionId(Long plTransactionId) {
		this.plTransactionId = plTransactionId;
	}

	public Long getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(Long paymentMode) {
		this.paymentMode = paymentMode;
	}
	

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public Long getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Long transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public CashModeInfoDto getCashModeInfo() {
		return cashModeInfo;
	}

	public void setCashModeInfo(CashModeInfoDto cashModeInfo) {
		this.cashModeInfo = cashModeInfo;
	}

	public CardModeInfoDto getCardModeInfo() {
		return cardModeInfo;
	}

	public void setCardModeInfo(CardModeInfoDto cardModeInfo) {
		this.cardModeInfo = cardModeInfo;
	}

	public ChequeModeInfoDto getChequeModeInfo() {
		return chequeModeInfo;
	}

	public void setChequeModeInfo(ChequeModeInfoDto chequeModeInfo) {
		this.chequeModeInfo = chequeModeInfo;
	}

	public QrBasedModeInfoDto getQrBasedModeInfo() {
		return qrBasedModeInfo;
	}

	public void setQrBasedModeInfo(QrBasedModeInfoDto qrBasedModeInfo) {
		this.qrBasedModeInfo = qrBasedModeInfo;
	}

	public VoucherModeInfoDto getVoucherModeInfo() {
		return voucherModeInfo;
	}

	public void setVoucherModeInfo(VoucherModeInfoDto voucherModeInfo) {
		this.voucherModeInfo = voucherModeInfo;
	}

	public WalletModeInfoDto getWalletModeInfo() {
		return walletModeInfo;
	}

	public void setWalletModeInfo(WalletModeInfoDto walletModeInfo) {
		this.walletModeInfo = walletModeInfo;
	}

	public SodexoModeInfoDto getSodexoModeInfo() {
		return sodexoModeInfo;
	}

	public void setSodexoModeInfo(SodexoModeInfoDto sodexoModeInfo) {
		this.sodexoModeInfo = sodexoModeInfo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

}