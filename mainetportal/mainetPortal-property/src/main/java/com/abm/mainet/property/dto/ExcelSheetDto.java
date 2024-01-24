package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ExcelSheetDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4131871122202410342L;
    private String srNo;
    private String taxName;
    private List<String> taxAmount = new LinkedList<>();

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public List<String> getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(List<String> taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

}
