package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LocAndCategoryWiseLicenseCntEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "TRD_WARD1")
	private long wardNo;

	@Column(name = "ZONE")
	private String zone;

	@Column(name = "LICENSE_CATAGORY")
	private String licenseCategory;

	@Column(name = "CNT")
	private int count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getWardNo() {
		return wardNo;
	}

	public void setWardNo(long wardNo) {
		this.wardNo = wardNo;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getLicenseCategory() {
		return licenseCategory;
	}

	public void setLicenseCategory(String licenseCategory) {
		this.licenseCategory = licenseCategory;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "LocAndCategoryWiseLicenseCntEntity [id=" + id + ", wardNo=" + wardNo + ", zone=" + zone
				+ ", licenseCategory=" + licenseCategory + ", count=" + count + "]";
	}

}
