package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_BILL_MAS_HIST")
public class ReceivableDemandEntryHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_BM_ID", nullable = false)
    private Long billIdH;

    @Column(name = "BM_ID")
    private Long billId;

    @Column(name = "FA_YEARID")
    private Long faYearId;

    @Column(name = "DP_DEPTID")
    private Long deptId;

    @Column(name = "SM_SERVICEID")
    private Long serviceId;

    @Column(name = "RECEIPT_ID")
    private Long receiptId;

    @Column(name = "APM_APPLICATION_ID")
    private Long applicationId;

    @Column(name = "BM_BILLGENDATE")
    private Date billigDate;

    @Column(name = "BM_BILLNO")
    private String billNo;
    
    @Column(name = "CASE_NO")
    private String caseNo;

    @Column(name = "BM_BIILLDATE")
    private Date billDate;

    @Column(name = "BM_TYPE")
    private String refFlag;

    @Column(name = "BM_POSTINGDATE")
    private Date billPostingDate;

    @Column(name = "BM_AMOUNT")
    private BigDecimal billAmount;

    @Column(name = "BM_PAID_AMT")
    private BigDecimal paidBillAmount;

    @Column(name = "CM_REFNO")
    private String refNumber;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "BM_ISADJUST")
    private String isAdjust;

    @Column(name = "BM_ISREFUND")
    private String isRefund;
    
    @Column(name = "BM_BILL_DISP_ID")
    private Long billDispId; 

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    public Long getBillIdH() {
        return billIdH;
    }

    public void setBillIdH(Long billIdH) {
        this.billIdH = billIdH;
    }
    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getRefFlag() {
        return refFlag;
    }

    public void setRefFlag(String refFlag) {
        this.refFlag = refFlag;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(Long faYearid) {
        this.faYearId = faYearid;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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

    public Date getBilligDate() {
        return billigDate;
    }

    public void setBilligDate(Date billigDate) {
        this.billigDate = billigDate;
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

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public BigDecimal getPaidBillAmount() {
        return paidBillAmount;
    }

    public void setPaidBillAmount(BigDecimal paidBillAmount) {
        this.paidBillAmount = paidBillAmount;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

	public Long getBillDispId() {
		return billDispId;
	}

	public void setBillDispId(Long billDispId) {
		this.billDispId = billDispId;
	}

	public String[] getPkValues() {

        return new String[] { "AC", "TB_BILL_MAS_HIST", "BM_ID_H" };
    }
}
