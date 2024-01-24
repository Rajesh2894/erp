package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServiceStatusWiseCountEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "orgName")
	private String orgName;

	@Column(name = "orgName_Reg")
	private String orgNameReg;

	@Column(name = "orgid")
	private long orgId;

	@Column(name = "RECEIVED")
	private int received;

	@Column(name = "PENDING")
	private int pending;

	@Column(name = "CLOSED")
	private int closed;

	@Column(name = "EXPIRED")
	private int expired;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgNameReg() {
		return orgNameReg;
	}

	public void setOrgNameReg(String orgNameReg) {
		this.orgNameReg = orgNameReg;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public int getReceived() {
		return received;
	}

	public void setReceived(int received) {
		this.received = received;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public int getClosed() {
		return closed;
	}

	public void setClosed(int closed) {
		this.closed = closed;
	}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		return "ServiceStatusWiseCountEntity [id=" + id + ", orgName=" + orgName + ", orgNameReg=" + orgNameReg
				+ ", orgId=" + orgId + ", received=" + received + ", pending=" + pending + ", closed=" + closed
				+ ", expired=" + expired + "]";
	}

}
