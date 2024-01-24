package com.abm.mainet.rnl.dto;

import java.io.Serializable;

public class EstateContPrintDTO implements Serializable {

    private static final long serialVersionUID = 2508988323480422316L;
    private String contId;
    private String orgName;
    private String orgCode;
    private String orgAddress;
    private String representedBy;
    private String desgName;
    private String vendorName;
    private String vendorAddress;
    private String tendorNo;
    private String rsoNo;
    private String rsoDate;
    private String fromDate;
    private String toDate;
    private String tendorDate;
    private String amount;
    private String deptLoc;
    private String orgNameReg;

    public String getContId() {
        return contId;
    }

    public void setContId(final String contId) {
        this.contId = contId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(final String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(final String orgCode) {
        this.orgCode = orgCode;
    }

    public String getRepresentedBy() {
        return representedBy;
    }

    public void setRepresentedBy(final String representedBy) {
        this.representedBy = representedBy;
    }

    public String getDesgName() {
        return desgName;
    }

    public void setDesgName(final String desgName) {
        this.desgName = desgName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(final String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getTendorNo() {
        return tendorNo;
    }

    public void setTendorNo(final String tendorNo) {
        this.tendorNo = tendorNo;
    }

    public String getRsoNo() {
        return rsoNo;
    }

    public void setRsoNo(final String rsoNo) {
        this.rsoNo = rsoNo;
    }

    public String getRsoDate() {
        return rsoDate;
    }

    public void setRsoDate(final String rsoDate) {
        this.rsoDate = rsoDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }

    public String getTendorDate() {
        return tendorDate;
    }

    public void setTendorDate(final String tendorDate) {
        this.tendorDate = tendorDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public String getDeptLoc() {
        return deptLoc;
    }

    public void setDeptLoc(final String deptLoc) {
        this.deptLoc = deptLoc;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(final String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgNameReg() {
        return orgNameReg;
    }

    public void setOrgNameReg(final String orgNameReg) {
        this.orgNameReg = orgNameReg;
    }
}
