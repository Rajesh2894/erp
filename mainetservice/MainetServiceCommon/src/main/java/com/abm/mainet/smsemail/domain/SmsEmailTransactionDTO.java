package com.abm.mainet.smsemail.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kavali.kiran
 * @since 08 Dec 2014
 */
public class SmsEmailTransactionDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private long smsId;

    private Long sessionid;

    private String senderOrg;

    private String mobileNo;

    private String msgSub;

    private String msgText;

    private Date sentDt;

    private String sentBy;

    private String status;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgId;

    private Long userId;

    private int langId;

    private Date lmodDate;

    private String remarks;

    private String refId1;

    private String refId2;

    private String refId3;

    private String refId4;

    private String emailsubject;

    private String emailbody;

    private String emailId;

    private String mobileNumbers;

    private Long serviceId;

    private Long applicationId;

    public String getMobileNumbers() {
        return mobileNumbers;
    }

    public void setMobileNumbers(final String mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public long getSmsId() {
        return smsId;
    }

    public void setSmsId(final long smsId) {
        this.smsId = smsId;
    }

    public Long getSessionid() {
        return sessionid;
    }

    public void setSessionid(final Long sessionid) {
        this.sessionid = sessionid;
    }

    public String getSenderOrg() {
        return senderOrg;
    }

    public void setSenderOrg(final String senderOrg) {
        this.senderOrg = senderOrg;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMsgSub() {
        return msgSub;
    }

    public void setMsgSub(final String msgSub) {
        this.msgSub = msgSub;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(final String msgText) {
        this.msgText = msgText;
    }

    public Date getSentDt() {
        return sentDt;
    }

    public void setSentDt(final Date sentDt) {
        this.sentDt = sentDt;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(final String sentBy) {
        this.sentBy = sentBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
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

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRefId1() {
        return refId1;
    }

    public void setRefId1(String refId1) {
        this.refId1 = refId1;
    }

    public String getRefId2() {
        return refId2;
    }

    public void setRefId2(String refId2) {
        this.refId2 = refId2;
    }

    public String getRefId3() {
        return refId3;
    }

    public void setRefId3(String refId3) {
        this.refId3 = refId3;
    }

    public String getRefId4() {
        return refId4;
    }

    public void setRefId4(String refId4) {
        this.refId4 = refId4;
    }

    public String getEmailsubject() {
        return emailsubject;
    }

    public void setEmailsubject(String emailsubject) {
        this.emailsubject = emailsubject;
    }

    public String getEmailbody() {
        return emailbody;
    }

    public void setEmailbody(String emailbody) {
        this.emailbody = emailbody;
    }

    
  
}