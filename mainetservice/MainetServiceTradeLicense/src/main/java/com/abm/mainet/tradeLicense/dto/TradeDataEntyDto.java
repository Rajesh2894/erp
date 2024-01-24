package com.abm.mainet.tradeLicense.dto;

public class TradeDataEntyDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6816027960395838977L;
	/**
	 * 
	 */
	private String licenseType;
	private String trdOldlicno;
	private String trdLicno;
	private String licenseFromDate;
	private String licenseToDate;
	private String trdBusnm;
	private String trdBusadd;
	private String trdId;

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getTrdOldlicno() {
		return trdOldlicno;
	}

	public void setTrdOldlicno(String trdOldlicno) {
		this.trdOldlicno = trdOldlicno;
	}

	public String getTrdLicno() {
		return trdLicno;
	}

	public void setTrdLicno(String trdLicno) {
		this.trdLicno = trdLicno;
	}

	public String getLicenseFromDate() {
		return licenseFromDate;
	}

	public void setLicenseFromDate(String licenseFromDate) {
		this.licenseFromDate = licenseFromDate;
	}

	public String getLicenseToDate() {
		return licenseToDate;
	}

	public void setLicenseToDate(String licenseToDate) {
		this.licenseToDate = licenseToDate;
	}

	public String getTrdBusnm() {
		return trdBusnm;
	}

	public void setTrdBusnm(String trdBusnm) {
		this.trdBusnm = trdBusnm;
	}

	public String getTrdBusadd() {
		return trdBusadd;
	}

	public void setTrdBusadd(String trdBusadd) {
		this.trdBusadd = trdBusadd;
	}

	public String getTrdId() {
		return trdId;
	}

	public void setTrdId(String trdId) {
		this.trdId = trdId;
	}

	@Override
	public String toString() {
		return "TradeDataEntyDto [licenseType=" + licenseType + ", trdOldlicno=" + trdOldlicno + ", trdLicno="
				+ trdLicno + ", licenseFromDate=" + licenseFromDate + ", licenseToDate=" + licenseToDate + ", trdBusnm="
				+ trdBusnm + ", trdBusadd=" + trdBusadd + "]";
	}

}
