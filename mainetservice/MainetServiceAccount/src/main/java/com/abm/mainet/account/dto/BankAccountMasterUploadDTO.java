package com.abm.mainet.account.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

public class BankAccountMasterUploadDTO {

    private String bankName;
    private String banktype;
    private String accNum;
    private String accName;
    private String type;
    private String function;
    private String primaryHead;
    private String location;
    private String fund;
    private Long orgid;
    private Long userId;
    private Long langId;
    private Date lmoddate;
    private Long accStatus;
    private String accountHead;
    @Size(max = 100)
    private String lgIpMac;
    private String accOldHeadCode;

    public Long getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(Long accStatus) {
        this.accStatus = accStatus;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
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

    public String getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(String accountHead) {
        this.accountHead = accountHead;
    }

    public String getAccOldHeadCode() {
		return accOldHeadCode;
	}

	public void setAccOldHeadCode(String accOldHeadCode) {
		this.accOldHeadCode = accOldHeadCode;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accName == null) ? 0 : accName.hashCode());
        result = prime * result + ((accNum == null) ? 0 : accNum.hashCode());
        result = prime * result + ((accountHead == null) ? 0 : accountHead.hashCode());
        result = prime * result + ((bankName == null) ? 0 : bankName.hashCode());
        result = prime * result + ((banktype == null) ? 0 : banktype.hashCode());
        result = prime * result + ((function == null) ? 0 : function.hashCode());
        result = prime * result + ((fund == null) ? 0 : fund.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((primaryHead == null) ? 0 : primaryHead.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        BankAccountMasterUploadDTO other = (BankAccountMasterUploadDTO) obj;
        if (accName == null) {
            if (other.accName != null)
                return false;
        } else if (!accName.equals(other.accName))
            return false;
        if (accNum == null) {
            if (other.accNum != null)
                return false;
        } else if (!accNum.equals(other.accNum))
            return false;
        if (accountHead == null) {
            if (other.accountHead != null)
                return false;
        } else if (!accountHead.equals(other.accountHead))
            return false;
        if (bankName == null) {
            if (other.bankName != null)
                return false;
        } else if (!bankName.equals(other.bankName))
            return false;
        if (banktype == null) {
            if (other.banktype != null)
                return false;
        } else if (!banktype.equals(other.banktype))
            return false;
        if (function == null) {
            if (other.function != null)
                return false;
        } else if (!function.equals(other.function))
            return false;
        if (fund == null) {
            if (other.fund != null)
                return false;
        } else if (!fund.equals(other.fund))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (primaryHead == null) {
            if (other.primaryHead != null)
                return false;
        } else if (!primaryHead.equals(other.primaryHead))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
