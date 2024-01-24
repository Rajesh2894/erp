package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LicenseIssuedDetEntity {
	@Id
	@Column(name = "num")
	private int id;
	
	@Column(name = "LICENSE_CATEGORY")
	private String licenseCategory;

	@Column(name = "UPTO_LAST_MONTH")
	private long uptoLastMonth;

	@Column(name = "CURRENT_MONTH")
	private long currentMonth;

	@Column(name = "TILL_MONTH")
	private long tillMonth;

	public String getLicenseCategory() {
		return licenseCategory;
	}

	public long getUptoLastMonth() {
		return uptoLastMonth;
	}

	public long getCurrentMonth() {
		return currentMonth;
	}

	public long getTillMonth() {
		return tillMonth;
	}

	public void setLicenseCategory(String licenseCategory) {
		this.licenseCategory = licenseCategory;
	}

	public void setUptoLastMonth(long uptoLastMonth) {
		this.uptoLastMonth = uptoLastMonth;
	}

	public void setCurrentMonth(long currentMonth) {
		this.currentMonth = currentMonth;
	}

	public void setTillMonth(long tillMonth) {
		this.tillMonth = tillMonth;
	}

	@Override
	public String toString() {
		return "LicenseIssuedDetEntity [licenseCategory=" + licenseCategory + ", uptoLastMonth=" + uptoLastMonth
				+ ", currentMonth=" + currentMonth + ", tillMonth=" + tillMonth + "]";
	}
}
