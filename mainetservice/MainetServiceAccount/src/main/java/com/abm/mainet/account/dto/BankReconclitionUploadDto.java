package com.abm.mainet.account.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

public class BankReconclitionUploadDto {

	private String transactionDate;
	private String transactionNo;
	private String transactionMode;
	private String transactionType;
	private String chequeNo;
	private String instrumentDate;
	private BigDecimal amount;
	private String realizationDate;

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(String instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRealizationDate() {
		return realizationDate;
	}

	public void setRealizationDate(String realizationDate) {
		this.realizationDate = realizationDate;
	}

	@Override
	public String toString() {
		return "BankReconclitionUploadDto [transactionDate=" + transactionDate + ", transactionNo=" + transactionNo
				+ ", transactionMode=" + transactionMode + ", transactionType=" + transactionType + ", chequeNo="
				+ chequeNo + ", instrumentDate=" + instrumentDate + ", amount=" + amount + ", realizationDate="
				+ realizationDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((chequeNo == null) ? 0 : chequeNo.hashCode());
		result = prime * result + ((instrumentDate == null) ? 0 : instrumentDate.hashCode());
		result = prime * result + ((realizationDate == null) ? 0 : realizationDate.hashCode());
		result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
		result = prime * result + ((transactionMode == null) ? 0 : transactionMode.hashCode());
		result = prime * result + ((transactionNo == null) ? 0 : transactionNo.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankReconclitionUploadDto other = (BankReconclitionUploadDto) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (chequeNo == null) {
			if (other.chequeNo != null)
				return false;
		} else if (!chequeNo.equals(other.chequeNo))
			return false;
		if (instrumentDate == null) {
			if (other.instrumentDate != null)
				return false;
		} else if (!instrumentDate.equals(other.instrumentDate))
			return false;
		if (realizationDate == null) {
			if (other.realizationDate != null)
				return false;
		} else if (!realizationDate.equals(other.realizationDate))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!transactionDate.equals(other.transactionDate))
			return false;
		if (transactionMode == null) {
			if (other.transactionMode != null)
				return false;
		} else if (!transactionMode.equals(other.transactionMode))
			return false;
		if (transactionNo == null) {
			if (other.transactionNo != null)
				return false;
		} else if (!transactionNo.equals(other.transactionNo))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

}
