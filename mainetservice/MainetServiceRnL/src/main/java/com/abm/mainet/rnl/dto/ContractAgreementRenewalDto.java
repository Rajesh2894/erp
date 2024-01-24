package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.Date;

public class ContractAgreementRenewalDto implements Serializable {

    private static final long serialVersionUID = -3038752874074352755L;

    private long contractId;
    private long contractId1;
    private String vendorName;
    private Date fromDate;
    private Date toDate;

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public long getContractId1() {
        return contractId1;
    }

    public void setContractId1(long contractId1) {
        this.contractId1 = contractId1;
    }

}
