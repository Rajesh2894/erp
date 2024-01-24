package com.abm.mainet.account.dto;

import java.util.Date;

import javax.validation.constraints.Size;

public class AccountBudgetOpenBalanceUploadDto {

    private String financialYear;
    private String headCategory;
    private String accountHeads;
    private String openingBal;
    private String drOrCr;
    private Long orgid;
    private Long userId;
    private Long langId;
    private Date lmoddate;
    @Size(max = 100)
    private String lgIpMac;
    private String dupAccountHead;
    private Long headCategoryId;

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getHeadCategory() {
        return headCategory;
    }

    public void setHeadCategory(String headCategory) {
        this.headCategory = headCategory;
    }

    public String getAccountHeads() {
        return accountHeads;
    }

    public void setAccountHeads(String accountHeads) {
        this.accountHeads = accountHeads;
    }

    public String getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(String openingBal) {
        this.openingBal = openingBal;
    }

    public String getDrOrCr() {
        return drOrCr;
    }

    public void setDrOrCr(String drOrCr) {
        this.drOrCr = drOrCr;
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

    public String getDupAccountHead() {
        return dupAccountHead;
    }

    public void setDupAccountHead(String dupAccountHead) {
        this.dupAccountHead = dupAccountHead;
    }

    public Long getHeadCategoryId() {
        return headCategoryId;
    }

    public void setHeadCategoryId(Long headCategoryId) {
        this.headCategoryId = headCategoryId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountHeads == null) ? 0 : accountHeads.hashCode());
        result = prime * result + ((drOrCr == null) ? 0 : drOrCr.hashCode());
        result = prime * result + ((financialYear == null) ? 0 : financialYear.hashCode());
        result = prime * result + ((headCategory == null) ? 0 : headCategory.hashCode());
        result = prime * result + ((openingBal == null) ? 0 : openingBal.hashCode());
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
        AccountBudgetOpenBalanceUploadDto other = (AccountBudgetOpenBalanceUploadDto) obj;
        if (accountHeads == null) {
            if (other.accountHeads != null)
                return false;
        } else if (!accountHeads.equals(other.accountHeads))
            return false;
        if (drOrCr == null) {
            if (other.drOrCr != null)
                return false;
        } else if (!drOrCr.equals(other.drOrCr))
            return false;
        if (financialYear == null) {
            if (other.financialYear != null)
                return false;
        } else if (!financialYear.equals(other.financialYear))
            return false;
        if (headCategory == null) {
            if (other.headCategory != null)
                return false;
        } else if (!headCategory.equals(other.headCategory))
            return false;
        if (openingBal == null) {
            if (other.openingBal != null)
                return false;
        } else if (!openingBal.equals(other.openingBal))
            return false;
        return true;
    }

}
