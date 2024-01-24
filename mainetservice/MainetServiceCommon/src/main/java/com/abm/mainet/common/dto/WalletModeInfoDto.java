package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author cherupelli.srikanth
 * @since 31 March 2023
 */
public class WalletModeInfoDto implements Serializable {

	private static final long serialVersionUID = 3558416377059064280L;
	private String walletMid;
	private String walletMobileNumber;
	private String walletRrn;
	private String walletTid;
	private Long walletTxnAmount;
	private String walletCardHolderName;
	private String walletMaskPan;

	public String getWalletMid() {
		return walletMid;
	}

	public void setWalletMid(String walletMid) {
		this.walletMid = walletMid;
	}

	public String getWalletMobileNumber() {
		return walletMobileNumber;
	}

	public void setWalletMobileNumber(String walletMobileNumber) {
		this.walletMobileNumber = walletMobileNumber;
	}

	public String getWalletRrn() {
		return walletRrn;
	}

	public void setWalletRrn(String walletRrn) {
		this.walletRrn = walletRrn;
	}
	

	public String getWalletTid() {
		return walletTid;
	}

	public void setWalletTid(String walletTid) {
		this.walletTid = walletTid;
	}

	public Long getWalletTxnAmount() {
		return walletTxnAmount;
	}

	public void setWalletTxnAmount(Long walletTxnAmount) {
		this.walletTxnAmount = walletTxnAmount;
	}

	public String getWalletCardHolderName() {
		return walletCardHolderName;
	}

	public void setWalletCardHolderName(String walletCardHolderName) {
		this.walletCardHolderName = walletCardHolderName;
	}

	public String getWalletMaskPan() {
		return walletMaskPan;
	}

	public void setWalletMaskPan(String walletMaskPan) {
		this.walletMaskPan = walletMaskPan;
	}

}
