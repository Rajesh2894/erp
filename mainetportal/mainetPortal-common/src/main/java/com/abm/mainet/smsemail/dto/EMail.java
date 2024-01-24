package com.abm.mainet.smsemail.dto;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * E-mail parameters
 */
public class EMail {

    private String[] to;

    private String subject;

    private String message;

    private String sender;

    private List<File> files;

    private String[] cc;

    private String[] bcc;

    private Date sendDate;

    private boolean background = true;

    private boolean sendStatus = false;

    private String unsubscribeLink;

    /**
     * @return the to
     */
    public String[] getTo() {
        return to;
    }

    public String getUnsubscribeLink() {
        return unsubscribeLink;
    }

    public void setUnsubscribeLink(String unsubscribeLink) {
        this.unsubscribeLink = unsubscribeLink;
    }

    /**
     * @param to (Receiver Mail-Id)
     */
    public void setTo(final String[] to) {
        this.to = to;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject (Mail Subject)
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message (Mail message)
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender (Mail sender Not Mandatory)
     */
    public void setSender(final String sender) {
        this.sender = sender;
    }

    /**
     * @return the cc
     */
    public String[] getCc() {
        return cc;
    }

    /**
     * @param cc (CC Receiver Mail-Id)
     */
    public void setCc(final String[] cc) {
        this.cc = cc;
    }

    /**
     * @return the bcc
     */
    public String[] getBcc() {
        return bcc;
    }

    /**
     * @param bcc (BCC Receiver Mail-Id)
     */
    public void setBcc(final String[] bcc) {
        this.bcc = bcc;
    }

    /**
     * @return the sendDate
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * @param sendDate the sendDate to set
     */
    public void setSendDate(final Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * @return the files
     */
    public List<File> getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(final List<File> files) {
        this.files = files;
    }

    /**
     * @return the background
     */
    public boolean isBackground() {
        return background;
    }

    /**
     * @param background the background to set
     */
    public void setBackground(final boolean background) {
        this.background = background;
    }

    /**
     * @return the sendStatus
     */
    public boolean isSendStatus() {
        return sendStatus;
    }

    /**
     * @param sendStatus the sendStatus to set
     */
    public void setSendStatus(final boolean sendStatus) {
        this.sendStatus = sendStatus;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("EMail [to=");
        builder.append(Arrays.toString(to));
        builder.append(", subject=");
        builder.append(subject);
        builder.append(", message=");
        builder.append(message);
        builder.append(", sender=");
        builder.append(sender);
        builder.append(", files=");
        builder.append(files);
        builder.append(", cc=");
        builder.append(Arrays.toString(cc));
        builder.append(", bcc=");
        builder.append(Arrays.toString(bcc));
        builder.append(", sendDate=");
        builder.append(sendDate);
        builder.append(", background=");
        builder.append(background);
        builder.append(", sendStatus=");
        builder.append(sendStatus);
        builder.append(MainetConstants.operator.LEFT_SQUARE_BRACKET);
        return builder.toString();
    }
}
