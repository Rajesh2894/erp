/**
 * 
 */
package com.abm.mainet.adh.dto;

import java.io.Serializable;

/**
 * @author anwarul.hassan
 * @since 02-Mar-2021
 */
public class ADHPublicNoticeDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String noticeNo;
    private String noticeDate;
    private String appDate;

    private Long noOfDays;
    private String agencyOwner;
    private String agencyAddress;
    private String licenceNo;
    private Double amount;

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public Long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Long noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getAgencyOwner() {
        return agencyOwner;
    }

    public void setAgencyOwner(String agencyOwner) {
        this.agencyOwner = agencyOwner;
    }

    public String getAgencyAddress() {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress = agencyAddress;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
