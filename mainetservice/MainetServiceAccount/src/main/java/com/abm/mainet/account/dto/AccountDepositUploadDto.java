package com.abm.mainet.account.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

public class AccountDepositUploadDto {

    private String typeOfDeposit;
    private String depositDate;
    private String depositerName;
    private String department;
    private String depositHead;
    private BigDecimal depositAmount;
    private BigDecimal balAmount;
    private String refNo;
    private String narration;
    private Long vendorId;
    private Long orgid;
    private Long userId;
    private Long langId;
    private Date lmoddate;
    @Size(max = 100)
    private String lgIpMac;

    public String getTypeOfDeposit() {
        return typeOfDeposit;
    }

    public void setTypeOfDeposit(String typeOfDeposit) {
        this.typeOfDeposit = typeOfDeposit;
    }

    public String getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(String depositDate) {
        this.depositDate = depositDate;
    }

    public String getDepositerName() {
        return depositerName;
    }

    public void setDepositerName(String depositerName) {
        this.depositerName = depositerName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepositHead() {
        return depositHead;
    }

    public void setDepositHead(String depositHead) {
        this.depositHead = depositHead;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getBalAmount() {
        return balAmount;
    }

    public void setBalAmount(BigDecimal balAmount) {
        this.balAmount = balAmount;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((balAmount == null) ? 0 : balAmount.hashCode());
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((depositAmount == null) ? 0 : depositAmount.hashCode());
        result = prime * result + ((depositDate == null) ? 0 : depositDate.hashCode());
        result = prime * result + ((depositHead == null) ? 0 : depositHead.hashCode());
        result = prime * result + ((depositerName == null) ? 0 : depositerName.hashCode());
        result = prime * result + ((narration == null) ? 0 : narration.hashCode());
        result = prime * result + ((refNo == null) ? 0 : refNo.hashCode());
        result = prime * result + ((typeOfDeposit == null) ? 0 : typeOfDeposit.hashCode());
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
        AccountDepositUploadDto other = (AccountDepositUploadDto) obj;
        if (balAmount == null) {
            if (other.balAmount != null)
                return false;
        } else if (!balAmount.equals(other.balAmount))
            return false;
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
            return false;
        if (depositAmount == null) {
            if (other.depositAmount != null)
                return false;
        } else if (!depositAmount.equals(other.depositAmount))
            return false;
        if (depositDate == null) {
            if (other.depositDate != null)
                return false;
        } else if (!depositDate.equals(other.depositDate))
            return false;
        if (depositHead == null) {
            if (other.depositHead != null)
                return false;
        } else if (!depositHead.equals(other.depositHead))
            return false;
        if (depositerName == null) {
            if (other.depositerName != null)
                return false;
        } else if (!depositerName.equals(other.depositerName))
            return false;
        if (narration == null) {
            if (other.narration != null)
                return false;
        } else if (!narration.equals(other.narration))
            return false;
        if (refNo == null) {
            if (other.refNo != null)
                return false;
        } else if (!refNo.equals(other.refNo))
            return false;
        if (typeOfDeposit == null) {
            if (other.typeOfDeposit != null)
                return false;
        } else if (!typeOfDeposit.equals(other.typeOfDeposit))
            return false;
        return true;
    }

}
