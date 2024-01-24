package com.abm.mainet.common.integration.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_ONL_TRAN_MAS_SERVICE")
public class PaymentTransactionMas implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5380776844395809116L;

    @Id
    @GenericGenerator(name = "generator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "generator")
    @Column(name = "TRAN_CM_ID", precision = 12, scale = 0, nullable = false)
    private long tranCmId;

    @Column(name = "REFERENCE_ID", precision = 16, scale = 0, nullable = true)
    private String referenceId;

    @Column(name = "REFERENCE_DATE", nullable = true)
    private Date referenceDate;

    @Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = true)
    private Long smServiceId;

    @Column(name = "CFC_MODE", length = 5, nullable = true)
    private String cfcMode;

    @Column(name = "T_DESC", length = 500, nullable = true)
    private String tDesc;

    @Column(name = "SEND_URL", length = 30, nullable = false)
    private String sendUrl;

    @Column(name = "SEND_KEY", length = 50, nullable = false)
    private String sendKey;

    @Column(name = "SEND_AMOUNT", precision = 2, scale = 0, nullable = false)
    private BigDecimal sendAmount;

    @Column(name = "SEND_PRODUCTINFO", length = 50, nullable = false)
    private String sendProductinfo;

    @Column(name = "SEND_FIRSTNAME", length = 500, nullable = false)
    private String sendFirstname;

    @Column(name = "SEND_EMAIL", length = 50, nullable = false)
    private String sendEmail;

    @Column(name = "SEND_PHONE", length = 20, nullable = false)
    private String sendPhone;

    @Column(name = "SEND_SURL", length = 500, nullable = false)
    private String sendSurl;

    @Column(name = "SEND_FURL", length = 500, nullable = false)
    private String sendFurl;

    @Column(name = "SEND_SALT", length = 100, nullable = false)
    private String sendSalt;

    @Column(name = "SEND_HASH", length = 4000, nullable = false)
    private String sendHash;

    @Column(name = "RECV_STATUS", length = 20, nullable = true)
    private String recvStatus;

    @Column(name = "RECV_BANK_REF_NUM", length = 100, nullable = true)
    private String recvBankRefNum;

    @Column(name = "RECV_MIHPAYID", length = 500, nullable = true)
    private String recvMihpayid;

    @Column(name = "RECV_NET_AMOUNT_DEBIT", length = 100, nullable = true)
    private Double recvNetAmountDebit;

    @Column(name = "RECV_ERRM", length = 1000, nullable = true)
    private String recvErrm;

    @Column(name = "RECV_MODE", length = 100, nullable = true)
    private String recvMode;

    @Column(name = "PG_TYPE", length = 30, nullable = true)
    private String pgType;

    @Column(name = "PG_SOURCE", length = 30, nullable = true)
    private String pgSource;

    @Column(name = "RECV_HASH", length = 4000, nullable = true)
    private String recvHash;

    @Column(name = "FINYEAR", precision = 0, scale = 0, nullable = true)
    private Long finyear;

    @Column(name = "REDIRECT_URL", length = 500, nullable = true)
    private String redirectUrl;

    @Column(name = "MENUPRM1", length = 5, nullable = true)
    private String menuprm1;

    @Column(name = "MENUPRM2", length = 5, nullable = true)
    private String menuprm2;

    @JsonIgnore
    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @JsonIgnore
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "CHALLAN_SERVICE_TYPE", length = 50, nullable = true)
    private String challanServiceType;

    @Column(name = "DOCUMENT_UPLOADED", length = 1, nullable = true)
    private String documentUploaded;

    @Column(name = "FEE_IDS", length = 100, nullable = true)
    private String feeIds;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private Integer langId;
    @Column(name = "PROP_FLAT_NO", length = 50, nullable = true)
    private String flatNo;

    @Transient
    private long cbBankid;

    public long getTranCmId() {
        return tranCmId;
    }

    public void setTranCmId(long tranCmId) {
        this.tranCmId = tranCmId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public String getCfcMode() {
        return cfcMode;
    }

    public void setCfcMode(String cfcMode) {
        this.cfcMode = cfcMode;
    }

    public String gettDesc() {
        return tDesc;
    }

    public void settDesc(String tDesc) {
        this.tDesc = tDesc;
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public String getSendKey() {
        return sendKey;
    }

    public void setSendKey(String sendKey) {
        this.sendKey = sendKey;
    }

    public String getSendProductinfo() {
        return sendProductinfo;
    }

    public void setSendProductinfo(String sendProductinfo) {
        this.sendProductinfo = sendProductinfo;
    }

    public String getSendFirstname() {
        return sendFirstname;
    }

    public void setSendFirstname(String sendFirstname) {
        this.sendFirstname = sendFirstname;
    }

    public String getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public String getSendSurl() {
        return sendSurl;
    }

    public void setSendSurl(String sendSurl) {
        this.sendSurl = sendSurl;
    }

    public String getSendFurl() {
        return sendFurl;
    }

    public void setSendFurl(String sendFurl) {
        this.sendFurl = sendFurl;
    }

    public String getSendSalt() {
        return sendSalt;
    }

    public void setSendSalt(String sendSalt) {
        this.sendSalt = sendSalt;
    }

    public String getSendHash() {
        return sendHash;
    }

    public void setSendHash(String sendHash) {
        this.sendHash = sendHash;
    }

    public String getRecvStatus() {
        return recvStatus;
    }

    public void setRecvStatus(String recvStatus) {
        this.recvStatus = recvStatus;
    }

    public String getRecvBankRefNum() {
        return recvBankRefNum;
    }

    public void setRecvBankRefNum(String recvBankRefNum) {
        this.recvBankRefNum = recvBankRefNum;
    }

    public String getRecvMihpayid() {
        return recvMihpayid;
    }

    public void setRecvMihpayid(String recvMihpayid) {
        this.recvMihpayid = recvMihpayid;
    }

    public Double getRecvNetAmountDebit() {
        return recvNetAmountDebit;
    }

    public void setRecvNetAmountDebit(Double recvNetAmountDebit) {
        this.recvNetAmountDebit = recvNetAmountDebit;
    }

    public String getRecvErrm() {
        return recvErrm;
    }

    public void setRecvErrm(String recvErrm) {
        this.recvErrm = recvErrm;
    }

    public String getRecvMode() {
        return recvMode;
    }

    public void setRecvMode(String recvMode) {
        this.recvMode = recvMode;
    }

    public String getPgType() {
        return pgType;
    }

    public void setPgType(String pgType) {
        this.pgType = pgType;
    }

    public String getPgSource() {
        return pgSource;
    }

    public void setPgSource(String pgSource) {
        this.pgSource = pgSource;
    }

    public String getRecvHash() {
        return recvHash;
    }

    public void setRecvHash(String recvHash) {
        this.recvHash = recvHash;
    }

    public Long getFinyear() {
        return finyear;
    }

    public void setFinyear(Long finyear) {
        this.finyear = finyear;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getMenuprm1() {
        return menuprm1;
    }

    public void setMenuprm1(String menuprm1) {
        this.menuprm1 = menuprm1;
    }

    public String getMenuprm2() {
        return menuprm2;
    }

    public void setMenuprm2(String menuprm2) {
        this.menuprm2 = menuprm2;
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

    public String getChallanServiceType() {
        return challanServiceType;
    }

    public void setChallanServiceType(String challanServiceType) {
        this.challanServiceType = challanServiceType;
    }

    public String getDocumentUploaded() {
        return documentUploaded;
    }

    public void setDocumentUploaded(String documentUploaded) {
        this.documentUploaded = documentUploaded;
    }

    public String getFeeIds() {
        return feeIds;
    }

    public void setFeeIds(String feeIds) {
        this.feeIds = feeIds;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public long getCbBankid() {
        return cbBankid;
    }

    public void setCbBankid(long cbBankid) {
        this.cbBankid = cbBankid;
    }

    public BigDecimal getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(BigDecimal sendAmount) {
        this.sendAmount = sendAmount;
        if (sendAmount != null) {
            this.sendAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String[] getPkValues() {
        return new String[] { "COM", "TB_MOBILE_ONL_PAYMENT_TRANS", "TRAN_CM_ID" };
    }

}