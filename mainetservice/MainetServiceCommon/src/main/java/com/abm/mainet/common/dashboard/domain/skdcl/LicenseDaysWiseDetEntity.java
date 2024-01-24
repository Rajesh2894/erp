package com.abm.mainet.common.dashboard.domain.skdcl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LicenseDaysWiseDetEntity {
	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "HDATE")
	private String hDate;

	@Column(name = "OTHER")
	private long other;

	@Column(name = "Hotel_Restaurant")
	private long hotel_restrurant;

	public int getId() {
		return id;
	}

	public String gethDate() {
		return hDate;
	}

	public long getOther() {
		return other;
	}

	public long getHotel_restrurant() {
		return hotel_restrurant;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void sethDate(String hDate) {
		this.hDate = hDate;
	}

	public void setOther(long other) {
		this.other = other;
	}

	public void setHotel_restrurant(long hotel_restrurant) {
		this.hotel_restrurant = hotel_restrurant;
	}

	@Override
	public String toString() {
		return "LicenseDaysWiseDetEntity [id=" + id + ", hDate=" + hDate + ", other=" + other + ", hotel_restrurant="
				+ hotel_restrurant + "]";
	}

}
