package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BillMasDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2538308493791754001L;

	private long mnBmIdno;
	
	private Long mnAssId;
	
	private String mnPropNo;

	private Long mnBmYear;
	
	private Date mnBilldt;

	private Date mnFromdt;
	
	private Date mnTodt;
	
	private Date mnDuedate;

	private BigDecimal mnTotalAmount;
	
	private BigDecimal mnTotalBalAmount;

	private BigDecimal mnTotalArrears;
	
	private BigDecimal mnTotalOutstanding;

	private BigDecimal mnTotalArrearsWithoutInt;

	private BigDecimal mnTotalCumIntArrears;
	
	private BigDecimal mnToatlInt;

	private BigDecimal mnLastRcptamt;
	
	private Date mnLastRcptdt;
	
	private BigDecimal mnToatlRebate;
	
	private String mnPaidFlag;
	
	private String mnFlagJvPost;
	
	private Date mnDistDate;
	
	private String mnRemarks;

	private Date mnPrintdate;
	
	private String mnArrearsBill;

	private BigDecimal mnTotpayamtAftdue;	

	private BigDecimal mnIntamtAftdue;
	
	private String mnEntryFlag;
	
	private Long orgId;

	private Long createdBy;

	private Date createdDate;
	
	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private Date mnIntFrom;

	private Date mnIntTo;
	
	private String mnNo;

	private BigDecimal mnIntValue;
	
	private Long mnAuthBmIdNo;
	
	private List<BillDetDto> billDetDtoList;

	public long getMnBmIdno() {
		return mnBmIdno;
	}

	public void setMnBmIdno(long mnBmIdno) {
		this.mnBmIdno = mnBmIdno;
	}

	public Long getMnAssId() {
		return mnAssId;
	}

	public void setMnAssId(Long mnAssId) {
		this.mnAssId = mnAssId;
	}

	public String getMnPropNo() {
		return mnPropNo;
	}

	public void setMnPropNo(String mnPropNo) {
		this.mnPropNo = mnPropNo;
	}

	public Long getMnBmYear() {
		return mnBmYear;
	}

	public void setMnBmYear(Long mnBmYear) {
		this.mnBmYear = mnBmYear;
	}

	public Date getMnBilldt() {
		return mnBilldt;
	}

	public void setMnBilldt(Date mnBilldt) {
		this.mnBilldt = mnBilldt;
	}

	public Date getMnFromdt() {
		return mnFromdt;
	}

	public void setMnFromdt(Date mnFromdt) {
		this.mnFromdt = mnFromdt;
	}

	public Date getMnTodt() {
		return mnTodt;
	}

	public void setMnTodt(Date mnTodt) {
		this.mnTodt = mnTodt;
	}

	public Date getMnDuedate() {
		return mnDuedate;
	}

	public void setMnDuedate(Date mnDuedate) {
		this.mnDuedate = mnDuedate;
	}

	public BigDecimal getMnTotalAmount() {
		return mnTotalAmount;
	}

	public void setMnTotalAmount(BigDecimal mnTotalAmount) {
		this.mnTotalAmount = mnTotalAmount;
	}

	public BigDecimal getMnTotalBalAmount() {
		return mnTotalBalAmount;
	}

	public void setMnTotalBalAmount(BigDecimal mnTotalBalAmount) {
		this.mnTotalBalAmount = mnTotalBalAmount;
	}

	public BigDecimal getMnTotalArrears() {
		return mnTotalArrears;
	}

	public void setMnTotalArrears(BigDecimal mnTotalArrears) {
		this.mnTotalArrears = mnTotalArrears;
	}

	public BigDecimal getMnTotalOutstanding() {
		return mnTotalOutstanding;
	}

	public void setMnTotalOutstanding(BigDecimal mnTotalOutstanding) {
		this.mnTotalOutstanding = mnTotalOutstanding;
	}

	public BigDecimal getMnTotalArrearsWithoutInt() {
		return mnTotalArrearsWithoutInt;
	}

	public void setMnTotalArrearsWithoutInt(BigDecimal mnTotalArrearsWithoutInt) {
		this.mnTotalArrearsWithoutInt = mnTotalArrearsWithoutInt;
	}

	public BigDecimal getMnTotalCumIntArrears() {
		return mnTotalCumIntArrears;
	}

	public void setMnTotalCumIntArrears(BigDecimal mnTotalCumIntArrears) {
		this.mnTotalCumIntArrears = mnTotalCumIntArrears;
	}

	public BigDecimal getMnToatlInt() {
		return mnToatlInt;
	}

	public void setMnToatlInt(BigDecimal mnToatlInt) {
		this.mnToatlInt = mnToatlInt;
	}

	public BigDecimal getMnLastRcptamt() {
		return mnLastRcptamt;
	}

	public void setMnLastRcptamt(BigDecimal mnLastRcptamt) {
		this.mnLastRcptamt = mnLastRcptamt;
	}

	public Date getMnLastRcptdt() {
		return mnLastRcptdt;
	}

	public void setMnLastRcptdt(Date mnLastRcptdt) {
		this.mnLastRcptdt = mnLastRcptdt;
	}

	public BigDecimal getMnToatlRebate() {
		return mnToatlRebate;
	}

	public void setMnToatlRebate(BigDecimal mnToatlRebate) {
		this.mnToatlRebate = mnToatlRebate;
	}

	public String getMnPaidFlag() {
		return mnPaidFlag;
	}

	public void setMnPaidFlag(String mnPaidFlag) {
		this.mnPaidFlag = mnPaidFlag;
	}

	public String getMnFlagJvPost() {
		return mnFlagJvPost;
	}

	public void setMnFlagJvPost(String mnFlagJvPost) {
		this.mnFlagJvPost = mnFlagJvPost;
	}

	public Date getMnDistDate() {
		return mnDistDate;
	}

	public void setMnDistDate(Date mnDistDate) {
		this.mnDistDate = mnDistDate;
	}

	public String getMnRemarks() {
		return mnRemarks;
	}

	public void setMnRemarks(String mnRemarks) {
		this.mnRemarks = mnRemarks;
	}

	public Date getMnPrintdate() {
		return mnPrintdate;
	}

	public void setMnPrintdate(Date mnPrintdate) {
		this.mnPrintdate = mnPrintdate;
	}

	public String getMnArrearsBill() {
		return mnArrearsBill;
	}

	public void setMnArrearsBill(String mnArrearsBill) {
		this.mnArrearsBill = mnArrearsBill;
	}

	public BigDecimal getMnTotpayamtAftdue() {
		return mnTotpayamtAftdue;
	}

	public void setMnTotpayamtAftdue(BigDecimal mnTotpayamtAftdue) {
		this.mnTotpayamtAftdue = mnTotpayamtAftdue;
	}

	public BigDecimal getMnIntamtAftdue() {
		return mnIntamtAftdue;
	}

	public void setMnIntamtAftdue(BigDecimal mnIntamtAftdue) {
		this.mnIntamtAftdue = mnIntamtAftdue;
	}

	public String getMnEntryFlag() {
		return mnEntryFlag;
	}

	public void setMnEntryFlag(String mnEntryFlag) {
		this.mnEntryFlag = mnEntryFlag;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Date getMnIntFrom() {
		return mnIntFrom;
	}

	public void setMnIntFrom(Date mnIntFrom) {
		this.mnIntFrom = mnIntFrom;
	}

	public Date getMnIntTo() {
		return mnIntTo;
	}

	public void setMnIntTo(Date mnIntTo) {
		this.mnIntTo = mnIntTo;
	}

	public String getMnNo() {
		return mnNo;
	}

	public void setMnNo(String mnNo) {
		this.mnNo = mnNo;
	}

	public BigDecimal getMnIntValue() {
		return mnIntValue;
	}

	public void setMnIntValue(BigDecimal mnIntValue) {
		this.mnIntValue = mnIntValue;
	}

	public List<BillDetDto> getBillDetDtoList() {
		return billDetDtoList;
	}

	public void setBillDetDtoList(List<BillDetDto> billDetDtoList) {
		this.billDetDtoList = billDetDtoList;
	}

	public Long getMnAuthBmIdNo() {
		return mnAuthBmIdNo;
	}

	public void setMnAuthBmIdNo(Long mnAuthBmIdNo) {
		this.mnAuthBmIdNo = mnAuthBmIdNo;
	}

}
