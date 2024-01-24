package com.abm.mainet.property.dto;

import java.io.Serializable;

public class ReceiptPrintDetailDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4769421962877196457L;
    private String detailsV;
    private String paymentAmtV;;
    private String receivedAmtV;

    public String getDetailsV() {
        return detailsV;
    }

    public void setDetailsV(String detailsV) {
        this.detailsV = detailsV;
    }

    public String getPaymentAmtV() {
        return paymentAmtV;
    }

    public void setPaymentAmtV(String paymentAmtV) {
        this.paymentAmtV = paymentAmtV;
    }

    public String getReceivedAmtV() {
        return receivedAmtV;
    }

    public void setReceivedAmtV(String receivedAmtV) {
        this.receivedAmtV = receivedAmtV;
    }
}
