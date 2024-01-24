package com.abm.mainet.smsemail.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author kavali.kiran
 * @since 08 Dec 2014
 */
@Entity
@Table(name = "TB_SMS_TRANSACTION")
public class SmsEmailTransaction extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SMS_ID", precision = 12, scale = 0, nullable = false)
    private long smsId;

    @Column(name = "SESSIONID", precision = 12, scale = 0, nullable = true)
    private Long sessionid;

    @Column(name = "SENDER_ORG", length = 100, nullable = true)
    private String senderOrg;

    @Column(name = "MOBILE_NO", nullable = true)
    private String mobileNo;

    @Column(name = "MSG_SUB", length = 1024, nullable = true)
    private String msgSub;

    @Column(name = "MSG_TEXT", length = 1024, nullable = true)
    private String msgText;

    @Column(name = "SENT_DT", nullable = true)
    private Date sentDt;

    @Column(name = "SENT_BY", length = 100, nullable = true)
    private String sentBy;

    @Column(name = "STATUS", length = 50, nullable = false)
    private String status;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;

    @Column(name = "LMODDATE", nullable = false)
    private Date lmodDate;

    @Column(name = "REMARKS", length = 1000, nullable = true)
    private String remarks;

    @Column(name = "REF_ID1", length = 1000, nullable = true)
    private String refId1;

    @Column(name = "REF_ID2", length = 1000, nullable = true)
    private String refId2;

    @Column(name = "REF_ID3", length = 1000, nullable = true)
    private String refId3;

    @Column(name = "REF_ID4", length = 1000, nullable = true)
    private String refId4;

    @Column(name = "EMAILSUBJECT", length = 1000, nullable = true)
    private String emailsubject;

    @Column(name = "EMAILBODY", length = 2000, nullable = true)
    private String emailbody;

    @Column(name = "EMAILID", length = 200, nullable = true)
    private String emailId;

    @Column(name = "mob_numbers", length = 200, nullable = true)
    private String mobileNumbers;

    @Column(name = "service_id", nullable = true)
    private Long serviceId;

    @Column(name = "application_id", nullable = true)
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

    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    @Override
    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    @Override
    public int getLangId() {
        return langId;
    }

    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    public String getRefId1() {
        return refId1;
    }

    public void setRefId1(final String refId1) {
        this.refId1 = refId1;
    }

    public String getRefId2() {
        return refId2;
    }

    public void setRefId2(final String refId2) {
        this.refId2 = refId2;
    }

    public String getRefId3() {
        return refId3;
    }

    public void setRefId3(final String refId3) {
        this.refId3 = refId3;
    }

    public String getRefId4() {
        return refId4;
    }

    public void setRefId4(final String refId4) {
        this.refId4 = refId4;
    }

    public String getEmailsubject() {
        return emailsubject;
    }

    public void setEmailsubject(final String emailsubject) {
        this.emailsubject = emailsubject;
    }

    public String getEmailbody() {
        return emailbody;
    }

    public void setEmailbody(final String emailbody) {
        this.emailbody = emailbody;
    }

    @Override
    public long getId() {

        return getSmsId();
    }

    @Override
    public Employee getUpdatedBy() {
        return null;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {

    }

    @Override
    public Date getUpdatedDate() {
        return null;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {

    }

    @Override
    public String getIsDeleted() {
        return null;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {

    }

    @Override
    public String[] getPkValues() {
        return new String[] { "COM", "TB_SMS_TRANSACTION", "SMS_ID" };
    }
}