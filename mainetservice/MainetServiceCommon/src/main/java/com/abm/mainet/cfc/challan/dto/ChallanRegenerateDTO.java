package com.abm.mainet.cfc.challan.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rahul.Yadav
 *
 */
public class ChallanRegenerateDTO implements Serializable {

    private static final long serialVersionUID = -1699786102440819111L;

    private Map<Long, Double> feeIds = new HashMap<>(
            0);
    private Map<Long, Long> billDetIds = new HashMap<>(
            0);
    private Double amountToPay;

    public Map<Long, Double> getFeeIds() {
        return feeIds;
    }

    public void setFeeIds(final Map<Long, Double> feeIds) {
        this.feeIds = feeIds;
    }

    public Map<Long, Long> getBillDetIds() {
        return billDetIds;
    }

    public void setBillDetIds(final Map<Long, Long> billDetIds) {
        this.billDetIds = billDetIds;
    }

    public Double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(final Double amountToPay) {
        this.amountToPay = amountToPay;
    }

}
