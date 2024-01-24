package com.abm.mainet.workManagement.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ExpiryItemsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long expiryId;
	
	private Long storeId;
	
	private String movementNo;
	
	@NotNull
	private Date movementDate;
	
	private Long movementBy;
	
	@NotNull
	private Date expiryCheck;
	
	private String status;

	@NotNull
	private Long orgId;

	@NotNull
	private Long userId;

	@NotNull
	private int langId;

	@NotNull
	private Date lmodDate;

	private Long updatedBy;

	private Date updatedDate;

	List<ExpiryItemDetailsDto> expiryItemDetailsDtoList = new ArrayList<>();

	@JsonIgnore
	@Size(max = 100)
	private String lgIpMac;

	@JsonIgnore
	@Size(max = 100)
	private String lgIpMacUpd;

	private String storeName;

	private Long workFlowLevel1;
	private Long workFlowLevel2;
	private Long workFlowLevel3;
	private Long workFlowLevel4;
	private Long workFlowLevel5;
	private Long actualTaskId;
	private Character checkerAuthorization;
	private String checkerRemarks;

	private Date fromDate;

	private Date toDate;

	private String movementByName;

	// ********** from Disposal ************
	private String scrapNo;

	private Date scrapDate;

	private Long initiator;

	private Long vendorId;

	private Long vendorName;

	private Long workorderId;

	private Date disposedDate;

	private Character paymentFlag;

	private Double receiptAmt;

	private String mode;

	private Long bankId;

	private Long instrumentNo;

	private Date instrumentDate;

	private Double instrumentAmt;

    private Long applicationId;

    private Double totalDisposedQuantity;
        
	private Long department ; 

	private List<Long> expiryIds =new ArrayList<>();
	
	private String redirectTender;

	private String isContractDone;

	
	public Long getExpiryId() {
		return expiryId;
	}

	public void setExpiryId(Long expiryId) {
		this.expiryId = expiryId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getMovementNo() {
		return movementNo;
	}

	public void setMovementNo(String movementNo) {
		this.movementNo = movementNo;
	}

	public Date getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(Date movementDate) {
		this.movementDate = movementDate;
	}

	public Long getMovementBy() {
		return movementBy;
	}

	public void setMovementBy(Long movementBy) {
		this.movementBy = movementBy;
	}

	public Date getExpiryCheck() {
		return expiryCheck;
	}

	public void setExpiryCheck(Date expiryCheck) {
		this.expiryCheck = expiryCheck;
	}

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
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

	public List<ExpiryItemDetailsDto> getExpiryItemDetailsDtoList() {
		return expiryItemDetailsDtoList;
	}

	public void setExpiryItemDetailsDtoList(List<ExpiryItemDetailsDto> expiryItemDetailsDtoList) {
		this.expiryItemDetailsDtoList = expiryItemDetailsDtoList;
	}

	public Long getWorkFlowLevel1() {
		return workFlowLevel1;
	}

	public void setWorkFlowLevel1(Long workFlowLevel1) {
		this.workFlowLevel1 = workFlowLevel1;
	}

	public Long getWorkFlowLevel2() {
		return workFlowLevel2;
	}

	public void setWorkFlowLevel2(Long workFlowLevel2) {
		this.workFlowLevel2 = workFlowLevel2;
	}

	public Long getWorkFlowLevel3() {
		return workFlowLevel3;
	}

	public void setWorkFlowLevel3(Long workFlowLevel3) {
		this.workFlowLevel3 = workFlowLevel3;
	}

	public Long getWorkFlowLevel4() {
		return workFlowLevel4;
	}

	public void setWorkFlowLevel4(Long workFlowLevel4) {
		this.workFlowLevel4 = workFlowLevel4;
	}

	public Long getWorkFlowLevel5() {
		return workFlowLevel5;
	}

	public void setWorkFlowLevel5(Long workFlowLevel5) {
		this.workFlowLevel5 = workFlowLevel5;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getActualTaskId() {
		return actualTaskId;
	}

	public void setActualTaskId(Long actualTaskId) {
		this.actualTaskId = actualTaskId;
	}

	public Character getCheckerAuthorization() {
		return checkerAuthorization;
	}

	public void setCheckerAuthorization(Character checkerAuthorization) {
		this.checkerAuthorization = checkerAuthorization;
	}

	public String getCheckerRemarks() {
		return checkerRemarks;
	}

	public void setCheckerRemarks(String checkerRemarks) {
		this.checkerRemarks = checkerRemarks;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getMovementByName() {
		return movementByName;
	}

	public void setMovementByName(String movementByName) {
		this.movementByName = movementByName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getScrapNo() {
		return scrapNo;
	}

	public void setScrapNo(String scrapNo) {
		this.scrapNo = scrapNo;
	}

	public Date getScrapDate() {
		return scrapDate;
	}

	public void setScrapDate(Date scrapDate) {
		this.scrapDate = scrapDate;
	}

	public Long getInitiator() {
		return initiator;
	}

	public void setInitiator(Long initiator) {
		this.initiator = initiator;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Long getVendorName() {
		return vendorName;
	}

	public void setVendorName(Long vendorName) {
		this.vendorName = vendorName;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Date getDisposedDate() {
		return disposedDate;
	}

	public void setDisposedDate(Date disposedDate) {
		this.disposedDate = disposedDate;
	}

	public Character getPaymentFlag() {
		return paymentFlag;
	}

	public void setPaymentFlag(Character paymentFlag) {
		this.paymentFlag = paymentFlag;
	}

	public Double getReceiptAmt() {
		return receiptAmt;
	}

	public void setReceiptAmt(Double receiptAmt) {
		this.receiptAmt = receiptAmt;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(Long instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public Date getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(Date instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

	public Double getInstrumentAmt() {
		return instrumentAmt;
	}

	public void setInstrumentAmt(Double instrumentAmt) {
		this.instrumentAmt = instrumentAmt;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Double getTotalDisposedQuantity() {
		return totalDisposedQuantity;
	}

	public void setTotalDisposedQuantity(Double totalDisposedQuantity) {
		this.totalDisposedQuantity = totalDisposedQuantity;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public List<Long> getExpiryIds() {
		return expiryIds;
	}

	public void setExpiryIds(List<Long> expiryIds) {
		this.expiryIds = expiryIds;
	}

	public String getRedirectTender() {
		return redirectTender;
	}

	public void setRedirectTender(String redirectTender) {
		this.redirectTender = redirectTender;
	}

	public String getIsContractDone() {
		return isContractDone;
	}

	public void setIsContractDone(String isContractDone) {
		this.isContractDone = isContractDone;
	}

	@Override
	public String toString() {
		return "ExpiryItemsDto [expiryId=" + expiryId + ", storeId=" + storeId + ", movementNo=" + movementNo
				+ ", movementDate=" + movementDate + ", movementBy=" + movementBy + ", expiryCheck=" + expiryCheck
				+ ", status=" + status + ", orgId=" + orgId + ", userId=" + userId + ", langId=" + langId
				+ ", lmodDate=" + lmodDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac="
				+ lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
	}

}
