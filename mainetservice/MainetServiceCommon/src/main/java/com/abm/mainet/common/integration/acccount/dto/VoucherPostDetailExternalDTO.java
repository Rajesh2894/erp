package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.math.BigDecimal;

//External System Voucher Posting Details DTO 
public class VoucherPostDetailExternalDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4110258326946481940L;

	//Mandatory - transaction amount
	private BigDecimal voucherAmount;
	
	//mandatory - account head code
	private String acHeadCode;
	
	//this is applicable for only receivable demand collection Ex:- 1 (2017-2018). //optional
    //private Long yearId;
	
    //To Identify to debit/credit account heads. //optional Ex:- 'Y'
    private String accountHeadFlag;
    
    //this is applicable for only receivable demand collection Ex:- Prefix - 'TDP' -> 'DMD','DMP' 1 (2017-2018). //optional
    private String demandTypeCode;

	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public String getAcHeadCode() {
		return acHeadCode;
	}

	public void setAcHeadCode(String acHeadCode) {
		this.acHeadCode = acHeadCode;
	}

	/*public Long getYearId() {
		return yearId;
	}

	public void setYearId(Long yearId) {
		this.yearId = yearId;
	}*/

	public String getAccountHeadFlag() {
		return accountHeadFlag;
	}

	public void setAccountHeadFlag(String accountHeadFlag) {
		this.accountHeadFlag = accountHeadFlag;
	}

	public String getDemandTypeCode() {
		return demandTypeCode;
	}

	public void setDemandTypeCode(String demandTypeCode) {
		this.demandTypeCode = demandTypeCode;
	}
	
}
