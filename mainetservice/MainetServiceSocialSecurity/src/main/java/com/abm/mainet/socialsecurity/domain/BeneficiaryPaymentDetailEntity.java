package com.abm.mainet.socialsecurity.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_SWD_RTGS_PAYMENT")
public class BeneficiaryPaymentDetailEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8276566233498028027L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RTGS_TRANS_ID", nullable = false)
    private Long rtgsTransId;

    @Column(name = "WORK_ORDER_NUMBER", nullable = false)
    private String workOrderNumber;

    @Column(name = "WORK_ORDER_DATE", nullable = false)
    private Date workOrdrerDate;

    @Column(name = "SDSCH_SER_ID", nullable = false)
    private Long schemeId;

    @Column(name = "SDSCHE_PAYSCH", nullable = false)
    private Long paymentScheduleId;

    @Column(name = "APM_APPLICATION_ID", nullable = false)
    private String applicationNumber;

    @Column(name = "BENEFICIARY_NUMBER", nullable = false)
    private String beneficiaryNumber;

    @Column(name = "BENEFICIARY_NAME", nullable = false)
    private String beneficiaryName;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "BANK_ID", nullable = false)
    private Long bankId;

    @Column(name = "SAPI_IFSC_ID", nullable = false)
    private String ifscCode;

    @Column(name = "SAPI_ACCOUNTID", nullable = false)
    private Long accountNumber;

    @Column(name = "REMARK", nullable = false)
    private String remark;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    @Column(name = "RTGS_STATU", nullable = true)
    private String rtgsStatus;

    @Column(name = "RTGS_POSTST", nullable = true)
    private String rtgsPostst;

    @Column(name = "RTGS_BILLNO", nullable = true)
    private String rtgsBillno;

    public static String[] getPkValues() {
        return new String[] { "SWD", "TB_SWD_RTGS_PAYMENT", "RTGS_TRANS_ID" };
    }

    public Long getRtgsTransId() {
        return rtgsTransId;
    }

    public void setRtgsTransId(Long rtgsTransId) {
        this.rtgsTransId = rtgsTransId;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public Date getWorkOrdrerDate() {
        return workOrdrerDate;
    }

    public void setWorkOrdrerDate(Date workOrdrerDate) {
        this.workOrdrerDate = workOrdrerDate;
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public Long getPaymentScheduleId() {
        return paymentScheduleId;
    }

    public void setPaymentScheduleId(Long paymentScheduleId) {
        this.paymentScheduleId = paymentScheduleId;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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

    public String getBeneficiaryNumber() {
        return beneficiaryNumber;
    }

    public void setBeneficiaryNumber(String beneficiaryNumber) {
        this.beneficiaryNumber = beneficiaryNumber;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getRtgsStatus() {
        return rtgsStatus;
    }

    public void setRtgsStatus(String rtgsStatus) {
        this.rtgsStatus = rtgsStatus;
    }

    public String getRtgsPostst() {
        return rtgsPostst;
    }

    public void setRtgsPostst(String rtgsPostst) {
        this.rtgsPostst = rtgsPostst;
    }

    public String getRtgsBillno() {
        return rtgsBillno;
    }

    public void setRtgsBillno(String rtgsBillno) {
        this.rtgsBillno = rtgsBillno;
    }

}
