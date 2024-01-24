package com.abm.mainet.swm.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserChargeCollectionDTO {

    private String consumerName;
    private Long consumerMobNo;
    private String consumerAddress;
    private String wardReg;
    private String wardEng;
    private BigDecimal monthlyCharges;
    private BigDecimal totalAmount;
    private Date billDate;
    private String receiptNo;
    private String manualReceiptNo;
    private String receiptBookNo;
    private String chargesNameEng;
    private String chargesNameReg;
    private String monthNo;
    private String statusFlag;
    private BigDecimal sumAmount;
    private List<UserChargeCollectionDTO> usercollections;

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public Long getConsumerMobNo() {
        return consumerMobNo;
    }

    public void setConsumerMobNo(Long consumerMobNo) {
        this.consumerMobNo = consumerMobNo;
    }

    public String getConsumerAddress() {
        return consumerAddress;
    }

    public void setConsumerAddress(String consumerAddress) {
        this.consumerAddress = consumerAddress;
    }

    public String getWardReg() {
        return wardReg;
    }

    public void setWardReg(String wardReg) {
        this.wardReg = wardReg;
    }

    public String getWardEng() {
        return wardEng;
    }

    public void setWardEng(String wardEng) {
        this.wardEng = wardEng;
    }

    public BigDecimal getMonthlyCharges() {
        return monthlyCharges;
    }

    public void setMonthlyCharges(BigDecimal monthlyCharges) {
        this.monthlyCharges = monthlyCharges;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getReceiptBookNo() {
        return receiptBookNo;
    }

    public void setReceiptBookNo(String receiptBookNo) {
        this.receiptBookNo = receiptBookNo;
    }

    public String getChargesNameEng() {
        return chargesNameEng;
    }

    public void setChargesNameEng(String chargesNameEng) {
        this.chargesNameEng = chargesNameEng;
    }

    public String getChargesNameReg() {
        return chargesNameReg;
    }

    public void setChargesNameReg(String chargesNameReg) {
        this.chargesNameReg = chargesNameReg;
    }

    public List<UserChargeCollectionDTO> getUsercollections() {
        return usercollections;
    }

    public void setUsercollections(List<UserChargeCollectionDTO> usercollections) {
        this.usercollections = usercollections;
    }

    public String getMonthNo() {
        return monthNo;
    }

    public void setMonthNo(String monthNo) {
        this.monthNo = monthNo;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    /**
     * @return the manualReceiptNo
     */
    public String getManualReceiptNo() {
        return manualReceiptNo;
    }

    /**
     * @param manualReceiptNo the manualReceiptNo to set
     */
    public void setManualReceiptNo(String manualReceiptNo) {
        this.manualReceiptNo = manualReceiptNo;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }
    


}
