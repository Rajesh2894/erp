package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class LicenseIssuedCntAndRevenueEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "LICENSE_CATEGORY")
	private String licenseCategory;

	@Column(name = "LICENCE_ISSUED_CNT")
	private long issuedCount;

	@Column(name = "REVENUE")
	private long revenue;

	public long getRevenue() {
		return revenue;
	}

	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}
	public int getId() {
		return id;
	}

	public String getLicenseCategory() {
		return licenseCategory;
	}

	public long getIssuedCount() {
		return issuedCount;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLicenseCategory(String licenseCategory) {
		this.licenseCategory = licenseCategory;
	}

	public void setIssuedCount(long issuedCount) {
		this.issuedCount = issuedCount;
	}

	@Override
	public String toString() {
		return "LicenseIssuedCntAndRevenueEntity [id=" + id + ", licenseCategory=" + licenseCategory + ", issuedCount="
				+ issuedCount + ", revenue=" + revenue + "]";
	}

}
