package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountVoucherDetailsUploadDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String tranDate;
    private String tranRefNo;
    private String voucherType;
    private String drOrCr;
    private String accHead;
    private BigDecimal amount;

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

    public String getDrOrCr() {
        return drOrCr;
    }

    public void setDrOrCr(String drOrCr) {
        this.drOrCr = drOrCr;
    }

    public String getAccHead() {
        return accHead;
    }

    public void setAccHead(String accHead) {
        this.accHead = accHead;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
