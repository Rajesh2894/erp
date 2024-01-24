package com.abm.mainet.payment.domain;

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
@Table(name = "TB_ONL_TRAN_MAS_PORTAL")
public class PaymentTransactionMas implements Serializable {

    private static final long serialVersionUID = 5380776844395809116L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TRAN_CM_ID", precision = 12, scale = 0, nullable = false)
    private long tranCmId;

    @Column(name = "REFERENCE_ID", precision = 16, scale = 0, nullable = true)
    private String apmApplicationId;

    @Column(name = "REFERENCE_DATE", nullable = true)
    private Date apmApplicationDate;

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

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private Integer langId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @Column(name = "UPDATED_BY", nullable = false, updatable = true)
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

    @Transient
    private long cbBankid;

    /**
     * @return the tranCmId
     */
    public long getTranCmId() {
        return tranCmId;
    }

    /**
     * @param tranCmId the tranCmId to set
     */
    public void setTranCmId(long tranCmId) {
        this.tranCmId = tranCmId;
    }

    /**
     * @return the apmApplicationId
     */
    public String getApmApplicationId() {
        return apmApplicationId;
    }

    /**
     * @param apmApplicationId the apmApplicationId to set
     */
    public void setApmApplicationId(String apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    /**
     * @return the apmApplicationDate
     */
    public Date getApmApplicationDate() {
        return apmApplicationDate;
    }

    /**
     * @param apmApplicationDate the apmApplicationDate to set
     */
    public void setApmApplicationDate(Date apmApplicationDate) {
        this.apmApplicationDate = apmApplicationDate;
    }

    /**
     * @return the smServiceId
     */
    public Long getSmServiceId() {
        return smServiceId;
    }

    /**
     * @param smServiceId the smServiceId to set
     */
    public void setSmServiceId(Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    /**
     * @return the cfcMode
     */
    public String getCfcMode() {
        return cfcMode;
    }

    /**
     * @param cfcMode the cfcMode to set
     */
    public void setCfcMode(String cfcMode) {
        this.cfcMode = cfcMode;
    }

    /**
     * @return the tDesc
     */
    public String gettDesc() {
        return tDesc;
    }

    /**
     * @param tDesc the tDesc to set
     */
    public void settDesc(String tDesc) {
        this.tDesc = tDesc;
    }

    /**
     * @return the sendUrl
     */
    public String getSendUrl() {
        return sendUrl;
    }

    /**
     * @param sendUrl the sendUrl to set
     */
    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    /**
     * @return the sendKey
     */
    public String getSendKey() {
        return sendKey;
    }

    /**
     * @param sendKey the sendKey to set
     */
    public void setSendKey(String sendKey) {
        this.sendKey = sendKey;
    }

    /**
     * @return the sendProductinfo
     */
    public String getSendProductinfo() {
        return sendProductinfo;
    }

    /**
     * @param sendProductinfo the sendProductinfo to set
     */
    public void setSendProductinfo(String sendProductinfo) {
        this.sendProductinfo = sendProductinfo;
    }

    /**
     * @return the sendFirstname
     */
    public String getSendFirstname() {
        return sendFirstname;
    }

    /**
     * @param sendFirstname the sendFirstname to set
     */
    public void setSendFirstname(String sendFirstname) {
        this.sendFirstname = sendFirstname;
    }

    /**
     * @return the sendEmail
     */
    public String getSendEmail() {
        return sendEmail;
    }

    /**
     * @param sendEmail the sendEmail to set
     */
    public void setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
    }

    /**
     * @return the sendPhone
     */
    public String getSendPhone() {
        return sendPhone;
    }

    /**
     * @param sendPhone the sendPhone to set
     */
    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    /**
     * @return the sendSurl
     */
    public String getSendSurl() {
        return sendSurl;
    }

    /**
     * @param sendSurl the sendSurl to set
     */
    public void setSendSurl(String sendSurl) {
        this.sendSurl = sendSurl;
    }

    /**
     * @return the sendFurl
     */
    public String getSendFurl() {
        return sendFurl;
    }

    /**
     * @param sendFurl the sendFurl to set
     */
    public void setSendFurl(String sendFurl) {
        this.sendFurl = sendFurl;
    }

    /**
     * @return the sendSalt
     */
    public String getSendSalt() {
        return sendSalt;
    }

    /**
     * @param sendSalt the sendSalt to set
     */
    public void setSendSalt(String sendSalt) {
        this.sendSalt = sendSalt;
    }

    /**
     * @return the sendHash
     */
    public String getSendHash() {
        return sendHash;
    }

    /**
     * @param sendHash the sendHash to set
     */
    public void setSendHash(String sendHash) {
        this.sendHash = sendHash;
    }

    /**
     * @return the recvStatus
     */
    public String getRecvStatus() {
        return recvStatus;
    }

    /**
     * @param recvStatus the recvStatus to set
     */
    public void setRecvStatus(String recvStatus) {
        this.recvStatus = recvStatus;
    }

    /**
     * @return the recvBankRefNum
     */
    public String getRecvBankRefNum() {
        return recvBankRefNum;
    }

    /**
     * @param recvBankRefNum the recvBankRefNum to set
     */
    public void setRecvBankRefNum(String recvBankRefNum) {
        this.recvBankRefNum = recvBankRefNum;
    }

    /**
     * @return the recvMihpayid
     */
    public String getRecvMihpayid() {
        return recvMihpayid;
    }

    /**
     * @param recvMihpayid the recvMihpayid to set
     */
    public void setRecvMihpayid(String recvMihpayid) {
        this.recvMihpayid = recvMihpayid;
    }

    /**
     * @return the recvNetAmountDebit
     */
    public Double getRecvNetAmountDebit() {
        return recvNetAmountDebit;
    }

    /**
     * @param recvNetAmountDebit the recvNetAmountDebit to set
     */
    public void setRecvNetAmountDebit(Double recvNetAmountDebit) {
        this.recvNetAmountDebit = recvNetAmountDebit;
    }

    /**
     * @return the recvErrm
     */
    public String getRecvErrm() {
        return recvErrm;
    }

    /**
     * @param recvErrm the recvErrm to set
     */
    public void setRecvErrm(String recvErrm) {
        this.recvErrm = recvErrm;
    }

    /**
     * @return the recvMode
     */
    public String getRecvMode() {
        return recvMode;
    }

    /**
     * @param recvMode the recvMode to set
     */
    public void setRecvMode(String recvMode) {
        this.recvMode = recvMode;
    }

    /**
     * @return the pgType
     */
    public String getPgType() {
        return pgType;
    }

    /**
     * @param pgType the pgType to set
     */
    public void setPgType(String pgType) {
        this.pgType = pgType;
    }

    /**
     * @return the pgSource
     */
    public String getPgSource() {
        return pgSource;
    }

    /**
     * @param pgSource the pgSource to set
     */
    public void setPgSource(String pgSource) {
        this.pgSource = pgSource;
    }

    /**
     * @return the recvHash
     */
    public String getRecvHash() {
        return recvHash;
    }

    /**
     * @param recvHash the recvHash to set
     */
    public void setRecvHash(String recvHash) {
        this.recvHash = recvHash;
    }

    /**
     * @return the finyear
     */
    public Long getFinyear() {
        return finyear;
    }

    /**
     * @param finyear the finyear to set
     */
    public void setFinyear(Long finyear) {
        this.finyear = finyear;
    }

    /**
     * @return the redirectUrl
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * @param redirectUrl the redirectUrl to set
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    /**
     * @return the menuprm1
     */
    public String getMenuprm1() {
        return menuprm1;
    }

    /**
     * @param menuprm1 the menuprm1 to set
     */
    public void setMenuprm1(String menuprm1) {
        this.menuprm1 = menuprm1;
    }

    /**
     * @return the menuprm2
     */
    public String getMenuprm2() {
        return menuprm2;
    }

    /**
     * @param menuprm2 the menuprm2 to set
     */
    public void setMenuprm2(String menuprm2) {
        this.menuprm2 = menuprm2;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    public Integer getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the challanServiceType
     */
    public String getChallanServiceType() {
        return challanServiceType;
    }

    /**
     * @param challanServiceType the challanServiceType to set
     */
    public void setChallanServiceType(String challanServiceType) {
        this.challanServiceType = challanServiceType;
    }

    /**
     * @return the documentUploaded
     */
    public String getDocumentUploaded() {
        return documentUploaded;
    }

    /**
     * @param documentUploaded the documentUploaded to set
     */
    public void setDocumentUploaded(String documentUploaded) {
        this.documentUploaded = documentUploaded;
    }

    /**
     * @return the feeIds
     */
    public String getFeeIds() {
        return feeIds;
    }

    /**
     * @param feeIds the feeIds to set
     */
    public void setFeeIds(String feeIds) {
        this.feeIds = feeIds;
    }

    /**
     * @return the cbBankid
     */
    public long getCbBankid() {
        return cbBankid;
    }

    /**
     * @param cbBankid the cbBankid to set
     */
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

    public String[] getPkValues() {
        return new String[] { "COM", "TB_CM_ONL_TRAN_MAS_PORTAL", "TRAN_CM_ID" };
    }
}