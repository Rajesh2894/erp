package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.List;

//External System Voucher Posting DTO
public class VoucherPostExternalDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4460245271693695541L;

	//receipt date/payment entry date //mandatory - Ex:- 11/10/2018
	private String voucherDate;
	
	//from TDP prefix - LookUpCode Ex- DSR,RV,DS,BI,BTE,DHC,RRE,etc. //mandatory
	private String vousubTypeCode;
	
	//mandatory - department code Ex:- AC,AST,COM,WMS....etc
	private String departmentCode;
	
	//mandatory - ulb short code Ex:- ABM,BMC,AMC,RAIP,JMC...etc
	private String ulbCode;
	
	//mandatory for voucher entry reference, no wise
	private String voucherReferenceNo; //who will validate next time come same value (duplicate) - ask to samadhan sir
	
	//mandatory for voucher entry reference, date wise - Ex:- 11/10/2018
	private String voucherReferenceDate; 
	
	//mandatory - description or content.
	private String narration;
	
	//Location from where transaction occur , location of employee //mandatory Ex:- Head Office...etc. //Field-CompositeCode Only.
	private String locationDescription;
	
	//optional - name
	private String payerOrPayee;
	
	//optional - we can get id's value in local machine administrator - user id directly.
	private String createdBy;
	
	//mandatory - system ip address Ex:- 192.168.100.200.
	//private String lgIpMac;
	
	//mandatory - 'VET' - Prefix LookUpCode //Internal or External transaction Identification. Ex:- INS,EXS,MAN,UPL..etc.
	private String entryCode;
	
	//from 'PAY' prefix - LookUpCode Ex- C,Q,D,T,B (cash,cheque,Demand Draft,Transfer,Bank) and etc. //optional - only for both levels acheadcode is available & remaining cases mandatory. 
	private String payModeCode;
    
    //To Identify the voucher posting where both level sacheadId is required. Ex:- 'Y'
  	private String entryFlag; //optional
    
  	//this is applicable for only receivable demand posting. //mandatory
    private Long financialYearId; //optional
    
  	//voucher posting external system details DTO
  	private List<VoucherPostDetailExternalDTO> voucherExtDetails;

	public String getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}

	public String getVousubTypeCode() {
		return vousubTypeCode;
	}

	public void setVousubTypeCode(String vousubTypeCode) {
		this.vousubTypeCode = vousubTypeCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getUlbCode() {
		return ulbCode;
	}

	public void setUlbCode(String ulbCode) {
		this.ulbCode = ulbCode;
	}

	public String getVoucherReferenceNo() {
		return voucherReferenceNo;
	}

	public void setVoucherReferenceNo(String voucherReferenceNo) {
		this.voucherReferenceNo = voucherReferenceNo;
	}

	public String getVoucherReferenceDate() {
		return voucherReferenceDate;
	}

	public void setVoucherReferenceDate(String voucherReferenceDate) {
		this.voucherReferenceDate = voucherReferenceDate;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String getPayerOrPayee() {
		return payerOrPayee;
	}

	public void setPayerOrPayee(String payerOrPayee) {
		this.payerOrPayee = payerOrPayee;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/*public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}*/

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getPayModeCode() {
		return payModeCode;
	}

	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode;
	}

	public String getEntryFlag() {
		return entryFlag;
	}

	public void setEntryFlag(String entryFlag) {
		this.entryFlag = entryFlag;
	}
	
	public Long getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(Long financialYearId) {
		this.financialYearId = financialYearId;
	}

	public List<VoucherPostDetailExternalDTO> getVoucherExtDetails() {
		return voucherExtDetails;
	}

	public void setVoucherExtDetails(List<VoucherPostDetailExternalDTO> voucherExtDetails) {
		this.voucherExtDetails = voucherExtDetails;
	}
	
}
