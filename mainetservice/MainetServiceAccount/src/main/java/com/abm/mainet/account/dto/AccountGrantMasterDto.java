package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountGrantMasterDto implements Serializable {
	private static final long serialVersionUID = -7934088838518931975L;

	private Long grntId;

	private String grtNo;

	private Date grtDate;

	private String grtName;

	private String grtType;

	private Long fromPerd;

	private Long toPerd;

	private String sactNo;

	private String sanctionAuth;

	private Date sanctionDate;

	private BigDecimal santionAmt;

	private BigDecimal receivedAmt;

	private String grtNature;

	private Long fundId;

	private String usStatus;

	private Long langId;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long categoryTypeId;
	
	private String categoryType;
	
	private Date sliDate;
	
	private String fromYear;
	
	private String toYear;
	
	private String refundDate;
	
	private String refundAmt;
	
	private String paymentNo;
	
	private String paymentDate;
	
	private String paymentAmount;
	
	private String paymentNurration;
	
	private String amountAgainstGRD;//Grand Received Advanced
	
	private String dateAgainstGRD;//Grand Received Advanced
	
	private List<PaymentEntryDto> PaymentList=new ArrayList<>();
	
	
	private List<String[]> adavancedGrandDetail;
	
	private List<String[]> BillDetailAgainstUtilize;
	
	private List<String[]> BillDetailAgainstRefund;

	private BigDecimal openingBalance;
	
	public List<String[]> getBillDetailAgainstUtilize() {
		return BillDetailAgainstUtilize;
	}

	public void setBillDetailAgainstUtilize(List<String[]> billDetailAgainstUtilize) {
		BillDetailAgainstUtilize = billDetailAgainstUtilize;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(String refundAmt) {
		this.refundAmt = refundAmt;
	}

	public Date getSliDate() {
		return sliDate;
	}

	public void setSliDate(Date sliDate) {
		this.sliDate = sliDate;
	}

	public Long getGrntId() {
		return grntId;
	}

	public void setGrntId(Long grntId) {
		this.grntId = grntId;
	}

	public String getGrtNo() {
		return grtNo;
	}

	public void setGrtNo(String grtNo) {
		this.grtNo = grtNo;
	}

	public Date getGrtDate() {
		return grtDate;
	}

	public void setGrtDate(Date grtDate) {
		this.grtDate = grtDate;
	}

	public String getGrtName() {
		return grtName;
	}

	public void setGrtName(String grtName) {
		this.grtName = grtName;
	}

	public String getGrtType() {
		return grtType;
	}

	public void setGrtType(String grtType) {
		this.grtType = grtType;
	}

	public Long getFromPerd() {
		return fromPerd;
	}

	public void setFromPerd(Long fromPerd) {
		this.fromPerd = fromPerd;
	}

	public Long getToPerd() {
		return toPerd;
	}

	public void setToPerd(Long toPerd) {
		this.toPerd = toPerd;
	}

	public String getSactNo() {
		return sactNo;
	}

	public void setSactNo(String sactNo) {
		this.sactNo = sactNo;
	}

	public String getSanctionAuth() {
		return sanctionAuth;
	}

	public void setSanctionAuth(String sanctionAuth) {
		this.sanctionAuth = sanctionAuth;
	}

	public Date getSanctionDate() {
		return sanctionDate;
	}

	public void setSanctionDate(Date sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	public BigDecimal getSantionAmt() {
		return santionAmt;
	}

	public void setSantionAmt(BigDecimal santionAmt) {
		this.santionAmt = santionAmt;
	}

	public BigDecimal getReceivedAmt() {
		return receivedAmt;
	}

	public void setReceivedAmt(BigDecimal receivedAmt) {
		this.receivedAmt = receivedAmt;
	}

	public String getGrtNature() {
		return grtNature;
	}

	public void setGrtNature(String grtNature) {
		this.grtNature = grtNature;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public String getUsStatus() {
		return usStatus;
	}

	public void setUsStatus(String usStatus) {
		this.usStatus = usStatus;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
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

	public Long getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(Long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getFromYear() {
		return fromYear;
	}

	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentNurration() {
		return paymentNurration;
	}

	public void setPaymentNurration(String paymentNurration) {
		this.paymentNurration = paymentNurration;
	}

	public String getAmountAgainstGRD() {
		return amountAgainstGRD;
	}

	public void setAmountAgainstGRD(String amountAgainstGRD) {
		this.amountAgainstGRD = amountAgainstGRD;
	}

	public String getDateAgainstGRD() {
		return dateAgainstGRD;
	}

	public void setDateAgainstGRD(String dateAgainstGRD) {
		this.dateAgainstGRD = dateAgainstGRD;
	}

	public List<PaymentEntryDto> getPaymentList() {
		return PaymentList;
	}

	public void setPaymentList(List<PaymentEntryDto> paymentList) {
		PaymentList = paymentList;
	}

	public List<String[]> getAdavancedGrandDetail() {
		return adavancedGrandDetail;
	}

	public void setAdavancedGrandDetail(List<String[]> adavancedGrandDetail) {
		this.adavancedGrandDetail = adavancedGrandDetail;
	}

	public List<String[]> getBillDetailAgainstRefund() {
		return BillDetailAgainstRefund;
	}

	public void setBillDetailAgainstRefund(List<String[]> billDetailAgainstRefund) {
		BillDetailAgainstRefund = billDetailAgainstRefund;
	}

	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	
	
}
