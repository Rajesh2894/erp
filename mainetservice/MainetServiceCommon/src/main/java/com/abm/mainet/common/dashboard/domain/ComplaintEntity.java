package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ComplaintEntity {
	@Id
	@Column(name = "num")
	private int id;
	@Column(name = "Department")
	private String deptName;
	@Column(name = "Zone")
	private String zone;
	@Column(name = "Ward")
	private String ward;
	@Column(name = "category")
	private String category;
	@Column(name = "sub_category")
	private String sub_category;

	@Column(name = "PENDING")
	private String PENDING;
	@Column(name = "CLOSED")
	private String CLOSED;
	@Column(name = "REJECTED")
	private String REJECTED;
	@Column(name = "HOLD")
	private String HOLD;

	public String getDeptName() {
		return deptName;
	}

	public String getZone() {
		return zone;
	}

	public String getWard() {
		return ward;
	}

	public String getCategory() {
		return category;
	}

	public String getSub_category() {
		return sub_category;
	}

	public String getPENDING() {
		return PENDING;
	}

	public String getCLOSED() {
		return CLOSED;
	}

	public String getREJECTED() {
		return REJECTED;
	}

	public String getHOLD() {
		return HOLD;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSub_category(String sub_category) {
		this.sub_category = sub_category;
	}

	public void setPENDING(String pENDING) {
		PENDING = pENDING;
	}

	public void setCLOSED(String cLOSED) {
		CLOSED = cLOSED;
	}

	public void setREJECTED(String rEJECTED) {
		REJECTED = rEJECTED;
	}

	public void setHOLD(String hOLD) {
		HOLD = hOLD;
	}

	@Override
	public String toString() {
		return "CommonComplaintEntity [deptName=" + deptName + ", zone=" + zone + ", ward=" + ward + ", category="
				+ category + ", sub_category=" + sub_category + ", PENDING=" + PENDING + ", CLOSED=" + CLOSED
				+ ", REJECTED=" + REJECTED + ", HOLD=" + HOLD + "]";
	}
}
