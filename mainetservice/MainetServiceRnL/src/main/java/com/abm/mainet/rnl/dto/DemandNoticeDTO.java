package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DemandNoticeDTO implements Serializable {

    private static final long serialVersionUID = -1326214959816068647L;

    private String taxName;
    private BigDecimal arrearsAmt;
    private BigDecimal currentAmt;
    private BigDecimal totalAmt;
    private String propertyNameAndAddress;
    private String contractNo;

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public BigDecimal getArrearsAmt() {
        return arrearsAmt;
    }

    public void setArrearsAmt(BigDecimal arrearsAmt) {
        this.arrearsAmt = arrearsAmt;
    }

    public BigDecimal getCurrentAmt() {
        return currentAmt;
    }

    public void setCurrentAmt(BigDecimal currentAmt) {
        this.currentAmt = currentAmt;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getPropertyNameAndAddress() {
        return propertyNameAndAddress;
    }

    public void setPropertyNameAndAddress(String propertyNameAndAddress) {
        this.propertyNameAndAddress = propertyNameAndAddress;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

}
