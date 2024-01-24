package com.abm.mainet.common.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Component
public class TemplateLookUp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String mailSubject;
    private String mailHeader;
    private String mailFooter;
    private String mailBody;
    private String smsBody;
    private long templateId;
    private String alertType;

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(final String alertType) {
        this.alertType = alertType;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(final String mailSubject) {
        this.mailSubject = mailSubject;
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

    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(final String mailBody) {
        this.mailBody = mailBody;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public void setSmsBody(final String smsBody) {
        this.smsBody = smsBody;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(final long templateId) {
        this.templateId = templateId;
    }

}
