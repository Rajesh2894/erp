package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_ACKDETAILS")
public class TdsAcknowledgementPaymentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ACK_ID", precision = 12, scale = 0, nullable = false)
    private Long ackId;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_ID", referencedColumnName = "PAYMENT_ID")
    private AccountPaymentMasterEntity paymentId;

    @Column(name = " CHALLAN_NO", precision = 50, scale = 0, nullable = false)
    private String challanNo;

    @Column(name = " CHALLAN_DATE", nullable = false)
    private Date challanDate;

    @Column(name = "ACK_NO", precision = 12, scale = 0, nullable = false)
    private String ackNo;

    @Column(name = "ACK_DATE", nullable = true)
    private Date ackDate;

    @Column(name = " ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CPD_ID_QTR", precision = 12, scale = 0, nullable = false)
    private Long cpIdQtr;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
    private Long createdBy;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", precision = 12, scale = 0, nullable = false)
    private String lgIpMac;

    @Column(name = " UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = " UPDATED_BY", nullable = true)
    private Long updatedBy;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    public Long getAckId() {
        return ackId;
    }

    public void setAckId(Long ackId) {
        this.ackId = ackId;
    }

    public AccountPaymentMasterEntity getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(AccountPaymentMasterEntity paymentId) {
        this.paymentId = paymentId;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public Date getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(Date challanDate) {
        this.challanDate = challanDate;
    }

    public String getAckNo() {
        return ackNo;
    }

    public void setAckNo(String ackNo) {
        this.ackNo = ackNo;
    }

    public Date getAckDate() {
        return ackDate;
    }

    public void setAckDate(Date ackDate) {
        this.ackDate = ackDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCpIdQtr() {
        return cpIdQtr;
    }

    public void setCpIdQtr(Long cpIdQtr) {
        this.cpIdQtr = cpIdQtr;
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public TdsAcknowledgementPaymentEntity() {
        super();
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_ACKDETAILS", "ACK_ID" };
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "TdsAcknowledgementPaymentEntity [ackId=" + ackId + ", paymentId=" + paymentId + ", challanNo="
                + challanNo + ", challanDate=" + challanDate + ", ackNo=" + ackNo + ", ackDate=" + ackDate + ", orgId="
                + orgId + ", cpIdQtr=" + cpIdQtr + ", createdDate=" + createdDate + ", createdBy=" + createdBy
                + ", lgIpMac=" + lgIpMac + ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy + ", lgIpMacUpd="
                + lgIpMacUpd + "]";
    }

}
