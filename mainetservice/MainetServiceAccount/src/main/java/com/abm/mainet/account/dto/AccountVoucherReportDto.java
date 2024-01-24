package com.abm.mainet.account.dto;

import java.io.Serializable;

public class AccountVoucherReportDto implements Serializable {

    private static final long serialVersionUID = 2260570120085539235L;

    private String narration;
    private String voucherType;

    String getNarration() {
        return narration;
    }

    public void setNarration(final String narration) {
        this.narration = narration;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(final String voucherType) {
        this.voucherType = voucherType;
    }

}
