package com.abm.mainet.adh.dto;

import java.io.Serializable;

/**
 * @author cherupelli.srikanth
 * @since 06 aug 2019
 */
public class LicenseLetterDto implements Serializable {

    private static final long serialVersionUID = -6098580316861626889L;

    private Long sNo;

    private String licFromDate;

    private String licToDate;

    public Long getsNo() {
        return sNo;
    }

    public void setsNo(Long sNo) {
        this.sNo = sNo;
    }

    public String getLicFromDate() {
        return licFromDate;
    }

    public void setLicFromDate(String licFromDate) {
        this.licFromDate = licFromDate;
    }

    public String getLicToDate() {
        return licToDate;
    }

    public void setLicToDate(String licToDate) {
        this.licToDate = licToDate;
    }

    @Override
    public String toString() {
        return "LicenseLetterDto [sNo=" + sNo + ", licFromDate=" + licFromDate + ", licToDate=" + licToDate + "]";
    }

}
