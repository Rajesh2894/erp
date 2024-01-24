package com.abm.mainet.water.dto;

import java.io.Serializable;

/**
 * @author Arun.Chavda
 *
 */
public class PlumLicenseRenewalSchDTO implements Serializable {

    private static final long serialVersionUID = -5277917365735324415L;

    private String licenseNo;
    private String licenseFromDate;
    private String licenseToDate;

    /**
     * @return the licenseNo
     */
    public String getLicenseNo() {
        return licenseNo;
    }

    /**
     * @param licenseNo the licenseNo to set
     */
    public void setLicenseNo(final String licenseNo) {
        this.licenseNo = licenseNo;
    }

    /**
     * @return the licenseFromDate
     */
    public String getLicenseFromDate() {
        return licenseFromDate;
    }

    /**
     * @param licenseFromDate the licenseFromDate to set
     */
    public void setLicenseFromDate(final String licenseFromDate) {
        this.licenseFromDate = licenseFromDate;
    }

    /**
     * @return the licenseToDate
     */
    public String getLicenseToDate() {
        return licenseToDate;
    }

    /**
     * @param licenseToDate the licenseToDate to set
     */
    public void setLicenseToDate(final String licenseToDate) {
        this.licenseToDate = licenseToDate;
    }

}
