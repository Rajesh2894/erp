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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Kavali.Kiran
 * @since 03 Dec 2014
 */
@Entity
@Table(name = "TB_PORTAL_SMS_MAIL_TEMPLATE")
public class SmsAndMailTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
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
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "SMS_BODY", precision = 7, scale = 0, nullable = true)
    private String smsBody;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date lmodDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

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
    private Long extTemplate;
    
    @Column(name = "EXT_TMPLT_REG")
    private Long extTemplateReg;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

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
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
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
    public long getId() {
        return getTpId();
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
        return new String[] { "AUT", "TB_PORTAL_SMS_MAIL_TEMPLATE", "TP_ID" };
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

    @Override
    public int getLangId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setLangId(int langId) {
        // TODO Auto-generated method stub

    }

	public Long getExtTemplate() {
		return extTemplate;
	}

	public void setExtTemplate(Long extTemplate) {
		this.extTemplate = extTemplate;
	}

	public Long getExtTemplateReg() {
		return extTemplateReg;
	}

	public void setExtTemplateReg(Long extTemplateReg) {
		this.extTemplateReg = extTemplateReg;
	}

}