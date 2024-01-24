package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class CommonComplaintEntity {

	@Id
	@Column(name = "num")
	private int id;
	@Column(name = "Name")
	private String name;
	@Column(name = "PENDING")
	private String PENDING;
	@Column(name = "CLOSED")
	private String CLOSED;
	@Column(name = "REJECTED")
	private String REJECTED;
	@Column(name = "HOLD")
	private String HOLD;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
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
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
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
		return "CommonComplaintEntity [id=" + id + ", name=" + name + ", PENDING=" + PENDING + ", CLOSED=" + CLOSED
				+ ", REJECTED=" + REJECTED + ", HOLD=" + HOLD + "]";
	}

}
