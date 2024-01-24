package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;

public class SubSchemeDetailsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long subSchemeDtlId;
	private Long subSchemeName;
	private Long noBeneficiary;
	private Long amount;
	private Long totalAmount;
	private String isschemeDetActive;
	
	public Long getSubSchemeDtlId() {
		return subSchemeDtlId;
	}
	public void setSubSchemeDtlId(Long subSchemeDtlId) {
		this.subSchemeDtlId = subSchemeDtlId;
	}
	public Long getSubSchemeName() {
		return subSchemeName;
	}
	public void setSubSchemeName(Long subSchemeName) {
		this.subSchemeName = subSchemeName;
	}
	public Long getNoBeneficiary() {
		return noBeneficiary;
	}
	public void setNoBeneficiary(Long noBeneficiary) {
		this.noBeneficiary = noBeneficiary;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getIsschemeDetActive() {
		return isschemeDetActive;
	}
	public void setIsschemeDetActive(String isschemeDetActive) {
		this.isschemeDetActive = isschemeDetActive;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
