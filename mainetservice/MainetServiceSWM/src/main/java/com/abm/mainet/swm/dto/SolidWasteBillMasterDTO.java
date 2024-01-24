package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 18-Jun-2018
 */
public class SolidWasteBillMasterDTO implements Serializable {

    private static final long serialVersionUID = -3706718622852368959L;

    private Long swBmIdno;

    private BigDecimal billAmount;

    private BigDecimal outStandingAmount;

    private BigDecimal advanceAmount;

    private Date billFromDate;

    private String billPaidFlg;

    private Date billToDate;

    private Long createdBy;

    private Date creationDate;

    private Long finYearid;

    private Date lastPaymentUpto;

    private String lgIpMac;

    private String lgIpMacUpd;

    private BigDecimal monthlyCharges;

    private Long orgid;

    private String receiptUploadInfo;

    private Long swBmBillno;

    private String swNewCustId;

    private Long registrationId;

    private Long updatedBy;

    private Date updatedDate;

    private String payMode;

    private Long receiptNo;

    private String customerName;

    private String customerMobile;
    
    private String manualReceiptBookNo;
    
    private String serviceShortCode;
    
    @JsonIgnore
    private SolidWasteConsumerMasterDTO tbSwNewRegistration;

    private List<DocumentDetailsVO> documentList;

    private String manualReceiptNo;

    public String getManualReceiptNo() {
        return manualReceiptNo;
    }

    public void setManualReceiptNo(String manualReceiptNo) {
        this.manualReceiptNo = manualReceiptNo;
    }

    public SolidWasteBillMasterDTO() {
    }

    public Long getSwBmIdno() {
        return this.swBmIdno;
    }

    public void setSwBmIdno(Long swBmIdno) {
        this.swBmIdno = swBmIdno;
    }

    public BigDecimal getBillAmount() {
        return this.billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public Date getBillFromDate() {
        return this.billFromDate;
    }

    public void setBillFromDate(Date billFromDate) {
        this.billFromDate = billFromDate;
    }

    public String getBillPaidFlg() {
        return this.billPaidFlg;
    }

    public void setBillPaidFlg(String billPaidFlg) {
        this.billPaidFlg = billPaidFlg;
    }

    public Date getBillToDate() {
        return this.billToDate;
    }

    public void setBillToDate(Date billToDate) {
        this.billToDate = billToDate;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getFinYearid() {
        return this.finYearid;
    }

    public void setFinYearid(Long finYearid) {
        this.finYearid = finYearid;
    }

    public Date getLastPaymentUpto() {
        return this.lastPaymentUpto;
    }

    public void setLastPaymentUpto(Date lastPaymentUpto) {
        this.lastPaymentUpto = lastPaymentUpto;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public BigDecimal getMonthlyCharges() {
        return this.monthlyCharges;
    }

    public void setMonthlyCharges(BigDecimal monthlyCharges) {
        this.monthlyCharges = monthlyCharges;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getReceiptUploadInfo() {
        return this.receiptUploadInfo;
    }

    public void setReceiptUploadInfo(String receiptUploadInfo) {
        this.receiptUploadInfo = receiptUploadInfo;
    }

    public Long getSwBmBillno() {
        return this.swBmBillno;
    }

    public void setSwBmBillno(Long swBmBillno) {
        this.swBmBillno = swBmBillno;
    }

    public String getSwNewCustId() {
        return this.swNewCustId;
    }

    public void setSwNewCustId(String swNewCustId) {
        this.swNewCustId = swNewCustId;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public SolidWasteConsumerMasterDTO getTbSwNewRegistration() {
        return this.tbSwNewRegistration;
    }

    public void setTbSwNewRegistration(SolidWasteConsumerMasterDTO tbSwNewRegistration) {
        this.tbSwNewRegistration = tbSwNewRegistration;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public BigDecimal getOutStandingAmount() {
        return outStandingAmount;
    }

    public void setOutStandingAmount(BigDecimal outStandingAmount) {
        this.outStandingAmount = outStandingAmount;
    }

    public BigDecimal getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(BigDecimal advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public Long getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(Long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public List<DocumentDetailsVO> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<DocumentDetailsVO> documentList) {
        this.documentList = documentList;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getManualReceiptBookNo() {
        return manualReceiptBookNo;
    }

    public void setManualReceiptBookNo(String manualReceiptBookNo) {
        this.manualReceiptBookNo = manualReceiptBookNo;
    }

	public String getServiceShortCode() {
		return serviceShortCode;
	}

	public void setServiceShortCode(String serviceShortCode) {
		this.serviceShortCode = serviceShortCode;
	}
}