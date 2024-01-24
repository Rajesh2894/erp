package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_sw_new_bill_mas database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 18-Jun-2018
 */
@Entity
@Table(name = "TB_SW_BILL_MAS_HIST")
public class SolidWasteBillMasterHistory implements Serializable {

    private static final long serialVersionUID = -1945723063130792775L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SW_BM_IDNO_HIST_ID", unique = true, nullable = false)
    private Long swBmIdnoH;

    @Column(name = "SW_BM_IDNO")
    private Long swBmIdno;

    @Column(name = "BILL_AMOUNT", precision = 10, scale = 2)
    private BigDecimal billAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BILL_FROM_DATE")
    private Date billFromDate;

    @Column(name = "BILL_PAID_FLG", length = 1)
    private String billPaidFlg;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BILL_TO_DATE")
    private Date billToDate;

    @Column(name = "CREATED_BY")
    private BigInteger createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "FIN_YEARID")
    private BigInteger finYearid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_PAYMENT_UPTO")
    private Date lastPaymentUpto;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "MONTHLY_CHARGES", precision = 10, scale = 2)
    private BigDecimal monthlyCharges;

    private BigInteger orgid;

    @Column(name = "RECEIPT_UPLOAD_INFO", length = 500)
    private String receiptUploadInfo;

    @Column(name = "SW_BM_BILLNO")
    private BigInteger swBmBillno;

    @Column(name = "SW_NEW_CUST_ID", length = 100)
    private String swNewCustId;

    @Column(name = "UPDATED_BY")
    private BigInteger updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "REGISTRATION_ID")
    private Long registrationId;

    @Column(name = "MANUAL_RECEIPT_NO")
    private String manualReceiptNo;
    
    @Column(name = "MANUAL_BOOKNO")
    private String manualReceiptBookNo;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    public SolidWasteBillMasterHistory() {
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

    public BigInteger getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigInteger getFinYearid() {
        return this.finYearid;
    }

    public void setFinYearid(BigInteger finYearid) {
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

    public BigInteger getOrgid() {
        return this.orgid;
    }

    public void setOrgid(BigInteger orgid) {
        this.orgid = orgid;
    }

    public String getReceiptUploadInfo() {
        return this.receiptUploadInfo;
    }

    public void setReceiptUploadInfo(String receiptUploadInfo) {
        this.receiptUploadInfo = receiptUploadInfo;
    }

    public BigInteger getSwBmBillno() {
        return this.swBmBillno;
    }

    public void setSwBmBillno(BigInteger swBmBillno) {
        this.swBmBillno = swBmBillno;
    }

    public String getSwNewCustId() {
        return this.swNewCustId;
    }

    public void setSwNewCustId(String swNewCustId) {
        this.swNewCustId = swNewCustId;
    }

    public BigInteger getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(BigInteger updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getSwBmIdnoH() {
        return swBmIdnoH;
    }

    public void setSwBmIdnoH(Long swBmIdnoH) {
        this.swBmIdnoH = swBmIdnoH;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getManualReceiptNo() {
        return manualReceiptNo;
    }

    public void setManualReceiptNo(String manualReceiptNo) {
        this.manualReceiptNo = manualReceiptNo;
    }

    public String getManualReceiptBookNo() {
        return manualReceiptBookNo;
    }

    public void setManualReceiptBookNo(String manualReceiptBookNo) {
        this.manualReceiptBookNo = manualReceiptBookNo;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_BILL_MAS_HIST", "SW_BM_IDNO_HIST_ID" };
    }

}