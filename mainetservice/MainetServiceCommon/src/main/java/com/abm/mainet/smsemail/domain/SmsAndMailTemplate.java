package com.abm.mainet.smsemail.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Kavali.Kiran
 * @since 03 Dec 2014
 */
@Entity
@Table(name = "TB_PORTAL_SMS_MAIL_TEMPLATE")
public class SmsAndMailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TP_ID", precision = 12, scale = 0, nullable = false)
    private long tpId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SE_ID", referencedColumnName = "SE_ID", nullable = false)
    @ForeignKey(name = "FK_TMPLT_ID")
    private SMSAndEmailInterface seId;

    @Column(name = "MAIL_SUB", length = 2000, nullable = true)
    private String mailSub;

    @Column(name = "MAIL_HEADER", length = 2000, nullable = true)
    private String mailHeader;

    @Column(name = "MAIL_FOOTER", length = 2000, nullable = true)
    private String mailFooter;

    @Column(name = "MAIL_BODY", length = 2000, nullable = true)
    private String mailBody;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private Employee userId;

    @Column(name = "SMS_BODY", precision = 7, scale = 0, nullable = true)
    private String smsBody;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CREATED_DATE")
    private Date lmodDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "MESSAGE_TYPE", length = 100, nullable = true)
    private String messageType;

    @Column(name = "MAIL_SUB_REG", nullable = true)
    private String mailSubReg;

    @Column(name = "MAIL_BODY_REG", nullable = true)
    private String mailBodyReg;

    @Column(name = "SMS_BODY_REG", nullable = true)
    private String smsBodyReg;
    
    @Column(name = "EXT_TMPLT")
    private String extTemplate;
    
    @Column(name = "EXT_TMPLT_REG")
    private String extTemplateReg;

    public long getTpId() {
        return tpId;
    }

    public void setTpId(final long tpId) {
        this.tpId = tpId;
    }

    public String getMailSub() {
        return mailSub;
    }

    public void setMailSub(final String mailSub) {
        this.mailSub = mailSub;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public void setSmsBody(final String smsBody) {
        this.smsBody = smsBody;
    }

    public String getMailHeader() {
        return mailHeader;
    }

    public void setMailHeader(final String mailHeader) {
        this.mailHeader = mailHeader;
    }

    public String getMailFooter() {
        return mailFooter;
    }

    public void setMailFooter(final String mailFooter) {
        this.mailFooter = mailFooter;
    }

    public SMSAndEmailInterface getSeId() {
        return seId;
    }

    public void setSeId(final SMSAndEmailInterface seId) {
        this.seId = seId;
    }

    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(final String mailBody) {
        this.mailBody = mailBody;
    }

    public Organisation getOrgId() {
        return orgId;
    }

    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    public Employee getUserId() {
        return userId;
    }

    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getMailSubReg() {
        return mailSubReg;
    }

    public void setMailSubReg(final String mailSubReg) {
        this.mailSubReg = mailSubReg;
    }

    public String getMailBodyReg() {
        return mailBodyReg;
    }

    public void setMailBodyReg(final String mailBodyReg) {
        this.mailBodyReg = mailBodyReg;
    }

    public String getSmsBodyReg() {
        return smsBodyReg;
    }

    public void setSmsBodyReg(final String smsBodyReg) {
        this.smsBodyReg = smsBodyReg;
    }

    public long getId() {
        return getTpId();
    }

    public String getIsDeleted() {
        return null;
    }

    public void setIsDeleted(final String isDeleted) {

    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

	public String getExtTemplate() {
		return extTemplate;
	}

	public void setExtTemplate(String extTemplate) {
		this.extTemplate = extTemplate;
	}

	public String getExtTemplateReg() {
		return extTemplateReg;
	}

	public void setExtTemplateReg(String extTemplateReg) {
		this.extTemplateReg = extTemplateReg;
	}

	public String[] getPkValues() {
        return new String[] { "COM", "TB_PORTAL_SMS_MAIL_TEMPLATE", "TP_ID" };
    }

}