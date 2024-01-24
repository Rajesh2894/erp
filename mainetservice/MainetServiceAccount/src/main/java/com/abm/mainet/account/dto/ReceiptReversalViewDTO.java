package com.abm.mainet.account.dto;

import java.util.List;

/**
 *
 * @author Vivek.Kumar
 * @since 24 May 2017
 */
public class ReceiptReversalViewDTO {

    private long receiptNo;
    private String receiptDate;
    private String receivedFrom;
    private String payeeName;
    private String mobileNo;
    private String emailId;
    private String manualReceiptNo;
    private String narration;
    private String receiptHead;
    private String receiptAmount;
    private String mode;
    private String modeShortCode;
    private String bankName;
    private String trnNo;
    private String trnDate;
    private String totalAmount;
    private Long deptId;
    private String  rmReceiptNo;
    private List<ReceiptReversalViewDTO> collectionDetails;

    public long getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(final long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(final String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceivedFrom() {
        return receivedFrom;
    }

    public void setReceivedFrom(final String receivedFrom) {
        this.receivedFrom = receivedFrom;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(final String payeeName) {
        this.payeeName = payeeName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getManualReceiptNo() {
        return manualReceiptNo;
    }

    public void setManualReceiptNo(final String manualReceiptNo) {
        this.manualReceiptNo = manualReceiptNo;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(final String narration) {
        this.narration = narration;
    }

    public String getReceiptHead() {
        return receiptHead;
    }

    public void setReceiptHead(final String receiptHead) {
        this.receiptHead = receiptHead;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(final String receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public String getTrnNo() {
        return trnNo;
    }

    public void setTrnNo(final String trnNo) {
        this.trnNo = trnNo;
    }

    public String getTrnDate() {
        return trnDate;
    }

    public void setTrnDate(final String trnDate) {
        this.trnDate = trnDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(final String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getRmReceiptNo() {
		return rmReceiptNo;
	}

	public void setRmReceiptNo(String rmReceiptNo) {
		this.rmReceiptNo = rmReceiptNo;
	}

	public List<ReceiptReversalViewDTO> getCollectionDetails() {
        return collectionDetails;
    }

    public void setCollectionDetails(final List<ReceiptReversalViewDTO> collectionDetails) {
        this.collectionDetails = collectionDetails;
    }

    public String getModeShortCode() {
        return modeShortCode;
    }

    public void setModeShortCode(final String modeShortCode) {
        this.modeShortCode = modeShortCode;
    }

}
