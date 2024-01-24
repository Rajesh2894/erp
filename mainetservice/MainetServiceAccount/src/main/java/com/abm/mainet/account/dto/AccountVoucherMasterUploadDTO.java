package com.abm.mainet.account.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

public class AccountVoucherMasterUploadDTO {

    private String tranDate;
    private String tranRefNo;
    private String voucherType;
    private String voucherSubType;
    private String narration;
    private Long orgid;
    private Long userId;
    private Long langId;
    private Date lmoddate;
    @Size(max = 100)
    private String lgIpMac;
    private List<AccountVoucherDetailsUploadDTO> details;

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

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranRefNo() {
        return tranRefNo;
    }

    public void setTranRefNo(String tranRefNo) {
        this.tranRefNo = tranRefNo;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherSubType() {
        return voucherSubType;
    }

    public void setVoucherSubType(String voucherSubType) {
        this.voucherSubType = voucherSubType;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public List<AccountVoucherDetailsUploadDTO> getDetails() {
        return details;
    }

    public void setDetails(List<AccountVoucherDetailsUploadDTO> details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((narration == null) ? 0 : narration.hashCode());
        result = prime * result + ((tranDate == null) ? 0 : tranDate.hashCode());
        result = prime * result + ((tranRefNo == null) ? 0 : tranRefNo.hashCode());
        result = prime * result + ((voucherSubType == null) ? 0 : voucherSubType.hashCode());
        result = prime * result + ((voucherType == null) ? 0 : voucherType.hashCode());
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
        AccountVoucherMasterUploadDTO other = (AccountVoucherMasterUploadDTO) obj;
        if (narration == null) {
            if (other.narration != null)
                return false;
        } else if (!narration.equals(other.narration))
            return false;
        if (tranDate == null) {
            if (other.tranDate != null)
                return false;
        } else if (!tranDate.equals(other.tranDate))
            return false;
        if (tranRefNo == null) {
            if (other.tranRefNo != null)
                return false;
        } else if (!tranRefNo.equals(other.tranRefNo))
            return false;
        if (voucherSubType == null) {
            if (other.voucherSubType != null)
                return false;
        } else if (!voucherSubType.equals(other.voucherSubType))
            return false;
        if (voucherType == null) {
            if (other.voucherType != null)
                return false;
        } else if (!voucherType.equals(other.voucherType))
            return false;
        return true;
    }

}
