package com.abm.mainet.account.dto;

import java.util.Date;

import javax.validation.constraints.Size;

public class AccountBudgetCodeUploadDto {

    private String function;
    private String primaryHead;
    private String budgetHeadCodeDescription;
    private Long orgid;
    private Long userId;
    private Long langId;
    private Date lmoddate;
    private String cpdIdStatusFlag;
    @Size(max = 100)
    private String lgIpMac;
    private String secondaryStatus;

    public String getCpdIdStatusFlag() {
        return cpdIdStatusFlag;
    }

    public void setCpdIdStatusFlag(String cpdIdStatusFlag) {
        this.cpdIdStatusFlag = cpdIdStatusFlag;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getPrimaryHead() {
        return primaryHead;
    }

    public void setPrimaryHead(String primaryHead) {
        this.primaryHead = primaryHead;
    }

    public String getBudgetHeadCodeDescription() {
        return budgetHeadCodeDescription;
    }

    public void setBudgetHeadCodeDescription(String budgetHeadCodeDescription) {
        this.budgetHeadCodeDescription = budgetHeadCodeDescription;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getSecondaryStatus() {
        return secondaryStatus;
    }

    public void setSecondaryStatus(String secondaryStatus) {
        this.secondaryStatus = secondaryStatus;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((budgetHeadCodeDescription == null) ? 0 : budgetHeadCodeDescription.hashCode());
        result = prime * result + ((function == null) ? 0 : function.hashCode());
        result = prime * result + ((primaryHead == null) ? 0 : primaryHead.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountBudgetCodeUploadDto other = (AccountBudgetCodeUploadDto) obj;
        if (budgetHeadCodeDescription == null) {
            if (other.budgetHeadCodeDescription != null)
                return false;
        } else if (!budgetHeadCodeDescription.equals(other.budgetHeadCodeDescription))
            return false;
        if (function == null) {
            if (other.function != null)
                return false;
        } else if (!function.equals(other.function))
            return false;
        if (primaryHead == null) {
            if (other.primaryHead != null)
                return false;
        } else if (!primaryHead.equals(other.primaryHead))
            return false;
        return true;
    }

}
