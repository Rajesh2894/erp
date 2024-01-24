package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.RequestDTO;

public class ReceivableDemandEntryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int taxCount;
    private Long billId;
    private Long faYearId;
    private Long serviceId;
    private Long deptId;
    private String refNumber;
    private Long custId;
    private String locName;
    private String caseNo;
    private Long receiptId;
    private Long applicationId;
    private String idnNo;
    private Long billDispId; // Reference for Bill Dispute
    private String isAdjust;// Deposit Adjustment Flag
    private String isRefund;// Deposit Refund Flag

    private String sliStatus;

    private Date billigDate;
    private String billNo;
    private Date billDate;
    private Date billPostingDate;
    private BigDecimal billAmount;
    private BigDecimal paidBillAmount;
    private BigDecimal receivedAmount;
    private BigDecimal balancePayAmount;
    
    private Date receiptDate;
    private String receiptNo;
    private boolean checkBillPay;

    private String refFlag;
    private boolean isNewCust;
    private String wardIdnPattern;
    private String newIdn;

    private String deptName;
    private String wardAddress;
    private String orgGSTIN;
    private String orgAddress;
    private String custFullAddress;
    private String empName;
    private String billAmountStr;
    private String custTypeCode;
    private String actionMode;

    private Long createdBy;
    private Date createdDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Long orgid;
    private Long updatedBy;
    private Date updatedDate;
    private BigDecimal totalAmount;
    private List<ReceivableDemandEntryDetailsDTO> rcvblDemandList = new ArrayList<>();
    private RequestDTO customerDetails = new RequestDTO();

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public int getTaxCount() {
        return taxCount;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public void setTaxCount(int taxCount) {
        this.taxCount = taxCount;
    }

    public Long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(Long faYearid) {
        this.faYearId = faYearid;
    }

    public Date getBilligDate() {
        return billigDate;
    }

    public void setBilligDate(Date billigDate) {
        this.billigDate = billigDate;
    }

    public boolean isNewCust() {
        return isNewCust;
    }

    public void setNewCust(boolean isNewCust) {
        this.isNewCust = isNewCust;
    }

    public String getIdnNo() {
        return idnNo;
    }

    public void setIdnNo(String idnNo) {
        this.idnNo = idnNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getBillPostingDate() {
        return billPostingDate;
    }

    public void setBillPostingDate(Date billPostingDate) {
        this.billPostingDate = billPostingDate;
    }

    public BigDecimal getPaidBillAmount() {
        return paidBillAmount;
    }

    public void setPaidBillAmount(BigDecimal paidBillAmount) {
        this.paidBillAmount = paidBillAmount;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public String getSliStatus() {
        return sliStatus;
    }

    public void setSliStatus(String sliStatus) {
        this.sliStatus = sliStatus;
    }

    public List<ReceivableDemandEntryDetailsDTO> getRcvblDemandList() {
        return rcvblDemandList;
    }

    public void setRcvblDemandList(List<ReceivableDemandEntryDetailsDTO> rcvblDemandList) {
        this.rcvblDemandList = rcvblDemandList;
    }

    public String getRefFlag() {
        return refFlag;
    }

    public void setRefFlag(String refFlag) {
        this.refFlag = refFlag;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWardAddress() {
        return wardAddress;
    }

    public void setWardAddress(String empAddress) {
        this.wardAddress = empAddress;
    }

    public String getBillAmountStr() {
        return billAmountStr;
    }

    public void setBillAmountStr(String billAmountStr) {
        this.billAmountStr = billAmountStr;
    }

    public String getOrgGSTIN() {
        return orgGSTIN;
    }

    public String getCustTypeCode() {
        return custTypeCode;
    }

    public void setCustTypeCode(String custTypeCode) {
        this.custTypeCode = custTypeCode;
    }

    public void setOrgGSTIN(String orgGSTIN) {
        this.orgGSTIN = orgGSTIN;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getCustFullAddress() {
        return custFullAddress;
    }

    public void setCustFullAddress(String custFullAddress) {
        this.custFullAddress = custFullAddress;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getBalancePayAmount() {
        return balancePayAmount;
    }

    public void setBalancePayAmount(BigDecimal balancePayAmount) {
        this.balancePayAmount = balancePayAmount;
    }

    public RequestDTO getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(RequestDTO customerDetails) {
        this.customerDetails = customerDetails;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getActionMode() {
        return actionMode;
    }

    public void setActionMode(String actionMode) {
        this.actionMode = actionMode;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public boolean isCheckBillPay() {
        return checkBillPay;
    }

    public void setCheckBillPay(boolean checkBillPay) {
        this.checkBillPay = checkBillPay;
    }

    public String getWardIdnPattern() {
        return wardIdnPattern;
    }

    public void setWardIdnPattern(String wardIdnPattern) {
        this.wardIdnPattern = wardIdnPattern;
    }

    public String getNewIdn() {
        return newIdn;
    }

    public void setNewIdn(String newIdn) {
        this.newIdn = newIdn;
    }

    public Long getBillDispId() {
        return billDispId;
    }

    public void setBillDispId(Long billDispId) {
        this.billDispId = billDispId;
    }

    public String getIsAdjust() {
        return isAdjust;
    }

    public void setIsAdjust(String isAdjust) {
        this.isAdjust = isAdjust;
    }

    public String getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(String isRefund) {
        this.isRefund = isRefund;
    } 
    
    

}
