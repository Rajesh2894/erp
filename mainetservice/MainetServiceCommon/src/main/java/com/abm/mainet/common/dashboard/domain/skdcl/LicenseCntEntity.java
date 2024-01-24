package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LicenseCntEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "HDATE")
	private String year;

	@Column(name = "STORAGE_LICENSE")
	private long storageLicenseCount;

	@Column(name = "TRADE_LICENSE")
	private long tradeLicenseCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public long getStorageLicenseCount() {
		return storageLicenseCount;
	}

	public void setStorageLicenseCount(long storageLicenseCount) {
		this.storageLicenseCount = storageLicenseCount;
	}

	public long getTradeLicenseCount() {
		return tradeLicenseCount;
	}

	public void setTradeLicenseCount(long tradeLicenseCount) {
		this.tradeLicenseCount = tradeLicenseCount;
	}

	@Override
	public String toString() {
		return "LicenseCntEntity [id=" + id + ", year=" + year + ", storageLicenseCount=" + storageLicenseCount
				+ ", tradeLicenseCount=" + tradeLicenseCount + "]";
	}

}
