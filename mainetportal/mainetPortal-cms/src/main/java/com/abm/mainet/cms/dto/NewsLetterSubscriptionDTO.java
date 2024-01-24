package com.abm.mainet.cms.dto;

import java.io.Serializable;
import java.util.Date;

public class NewsLetterSubscriptionDTO implements Serializable {

    private static final long serialVersionUID = -8601015179868714821L;

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

    private Long subscriptionID;

    private Long orgid;

    private String emailId;

    private String status;

    private Date subscriptionStartDate;

    private Date subscriptionEndtDate;
}
