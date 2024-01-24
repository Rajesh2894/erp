package com.abm.mainet.cms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_NEWSLETTER_SCUBSCRIPTION_DET")
public class NewsLetterSubscription implements Serializable {

    private static final long serialVersionUID = -5592249512269793806L;

    @Id
    @Column(name = "SUB_ID")
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    private Long subscriptionID;

    private Long orgid;

    private String emailId;

    @Column(name = "SUB_STATUS")
    private String status;

    @Column(name = "SUB_SDATE")
    private Date subscriptionStartDate;

    @Column(name = "SUB_VIEW_DATE")
    private Date viewDate;

    @Column(name = "SUB_EDATE")
    private Date subscriptionEndtDate;

    public Long getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(Long subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(Date subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public Date getSubscriptionEndtDate() {
        return subscriptionEndtDate;
    }

    public void setSubscriptionEndtDate(Date subscriptionEndtDate) {
        this.subscriptionEndtDate = subscriptionEndtDate;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    public String[] getPkValues() {

        return new String[] { "ONL", "TB_NEWSLETTER_SCUBSCRIPTION_DET", "SUB_ID" };
    }
}
