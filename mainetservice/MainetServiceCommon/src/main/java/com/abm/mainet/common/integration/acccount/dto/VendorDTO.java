package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class VendorDTO implements Serializable {

    private static final long serialVersionUID = 1125145928602487731L;

    private Long vmVendorid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    @Size(max = 200)
    private String vmVendorname;

    @Size(max = 100)
    private String emailId;

    @Size(max = 10)
    private String mobileNo;

    private Long orgid;

    public Long getVmVendorid() {
        return vmVendorid;
    }

    public void setVmVendorid(final Long vmVendorid) {
        this.vmVendorid = vmVendorid;
    }

    public String getVmVendorname() {
        return vmVendorname;
    }

    public void setVmVendorname(final String vmVendorname) {
        this.vmVendorname = vmVendorname;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    @Override
    public String toString() {
        return "VendorDTO [vmVendorid=" + vmVendorid + ", vmVendorname="
                + vmVendorname + ", emailId=" + emailId + ", mobileNo=" + mobileNo + ", orgid=" + orgid + "]";
    }

}
