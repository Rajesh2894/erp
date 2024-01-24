package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TotalTransCntSWMEntity {
	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "O_NLS_ORGNAME")
	private String orgName;
	
	@Column(name = "O_NLS_ORGNAME_MAR")
	private String orgNameMar;

	@Column(name = "COUNT_RM_RCPTNO")
	private int countRcpt;

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

	public String getOrgNameMar() {
		return orgNameMar;
	}

	public void setOrgNameMar(String orgNameMar) {
		this.orgNameMar = orgNameMar;
	}

	public int getCountRcpt() {
		return countRcpt;
	}

	public void setCountRcpt(int countRcpt) {
		this.countRcpt = countRcpt;
	}

	@Override
	public String toString() {
		return "TotalTransCntSWMEntity [id=" + id + ", orgName=" + orgName + ", orgNameMar=" + orgNameMar
				+ ", countRcpt=" + countRcpt + "]";
	}

}
