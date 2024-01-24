/**
 * 
 */
package com.abm.mainet.account.domain;

import java.io.Serializable;
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

/**
 * Persistent class for entity stored in table "TB_AC_CHEQUEBOOKLEAF_DET_HIST"
 * @author Anwarul.Hassan
 * @since 23-Dec-2019
 */
@Entity
@Table(name = "TB_AC_CHEQUEBOOKLEAF_DET_HIST")
public class TbAcChequebookleafDetHistoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CHEQUE_ID", nullable = false)
    private Long cheque_id;

    @Column(name = "CHEQUEBOOK_DETID")
    private Long chequebookDetid;

    @Column(name = "CHEQUEBOOK_ID")
    private Long chequebookId;

    @Column(name = "CHEQUE_NO", nullable = false, length = 12)
    private String chequeNo;

    @Column(name = "CPD_IDSTATUS")
    private Long cpdIdstatus;

    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    @Column(name = "REMARK", length = 500)
    private String remark;

    @Column(name = "STOP_PAY_ORDER_NO")
    private Long stopPayOrderNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "STOP_PAY_ORDER_DATE")
    private Date stopPayOrderDate;

    @Column(name = "STOP_PAY_FLAG", length = 1)
    private String stopPayFlag;

    @Column(name = "STOP_PAY_REMARK", length = 500)
    private String stopPayRemark;

    @Temporal(TemporalType.DATE)
    @Column(name = "STOPPAY_DATE")
    private Date stoppayDate;

    @Column(name = "PAYMENT_TYPE", length = 1)
    private String paymentType;

    @Temporal(TemporalType.DATE)
    @Column(name = "ISSUANCE_DATE")
    private Date issuanceDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "CANCELLATION_DATE")
    private Date cancellationDate;

    @Column(name = "CANCELLATION_REASON", length = 1000)
    private String cancellationReason;

    @Column(name = "NEW_ISSUE_CHEQUEBOOK_DETID")
    private Long newIssueCheqeuBookDetId;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long userId;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "FI04_N1")
    private Long fi04N1;

    @Column(name = "FI04_V1")
    private String fi04V1;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    @Column(name = "H_STATUS", length = 1)
    private String hSataus;

    public TbAcChequebookleafDetHistoryEntity() {
        super();
    }

    public Long getCheque_id() {
        return cheque_id;
    }

    public void setCheque_id(Long cheque_id) {
        this.cheque_id = cheque_id;
    }

    public Long getChequebookDetid() {
        return chequebookDetid;
    }

    public void setChequebookDetid(Long chequebookDetid) {
        this.chequebookDetid = chequebookDetid;
    }

    public Long getChequebookId() {
        return chequebookId;
    }

    public void setChequebookId(Long chequebookId) {
        this.chequebookId = chequebookId;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public Long getCpdIdstatus() {
        return cpdIdstatus;
    }

    public void setCpdIdstatus(Long cpdIdstatus) {
        this.cpdIdstatus = cpdIdstatus;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getStopPayOrderNo() {
        return stopPayOrderNo;
    }

    public void setStopPayOrderNo(Long stopPayOrderNo) {
        this.stopPayOrderNo = stopPayOrderNo;
    }

    public Date getStopPayOrderDate() {
        return stopPayOrderDate;
    }

    public void setStopPayOrderDate(Date stopPayOrderDate) {
        this.stopPayOrderDate = stopPayOrderDate;
    }

    public String getStopPayFlag() {
        return stopPayFlag;
    }

    public void setStopPayFlag(String stopPayFlag) {
        this.stopPayFlag = stopPayFlag;
    }

    public String getStopPayRemark() {
        return stopPayRemark;
    }

    public void setStopPayRemark(String stopPayRemark) {
        this.stopPayRemark = stopPayRemark;
    }

    public Date getStoppayDate() {
        return stoppayDate;
    }

    public void setStoppayDate(Date stoppayDate) {
        this.stoppayDate = stoppayDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(Date issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Long getNewIssueCheqeuBookDetId() {
        return newIssueCheqeuBookDetId;
    }

    public void setNewIssueCheqeuBookDetId(Long newIssueCheqeuBookDetId) {
        this.newIssueCheqeuBookDetId = newIssueCheqeuBookDetId;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
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

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04N1(Long fi04n1) {
        fi04N1 = fi04n1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V1(String fi04v1) {
        fi04V1 = fi04v1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String gethSataus() {
        return hSataus;
    }

    public void sethSataus(String hSataus) {
        this.hSataus = hSataus;
    }

    public String[] getPkValues() {
        return new String[] { "AC", "TB_AC_CHEQUEBOOKLEAF_DET", "CHEQUEBOOK_DETID" };
    }
}
