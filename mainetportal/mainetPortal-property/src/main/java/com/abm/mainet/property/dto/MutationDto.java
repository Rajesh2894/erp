package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;

public class MutationDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long transferType;

    private Date actualTransferDate;

    private Double marketValue;

    private Double salesDeedValue;

    public Long getTransferType() {
        return transferType;
    }

    public void setTransferType(Long transferType) {
        this.transferType = transferType;
    }

    public Date getActualTransferDate() {
        return actualTransferDate;
    }

    public void setActualTransferDate(Date actualTransferDate) {
        this.actualTransferDate = actualTransferDate;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Double getSalesDeedValue() {
        return salesDeedValue;
    }

    public void setSalesDeedValue(Double salesDeedValue) {
        this.salesDeedValue = salesDeedValue;
    }

}