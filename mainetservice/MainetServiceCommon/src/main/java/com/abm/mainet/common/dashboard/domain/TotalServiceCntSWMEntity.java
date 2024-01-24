package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TotalServiceCntSWMEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "O_NLS_ORGNAME")
	private String orgName;

	@Column(name = "O_NLS_ORGNAME_MAR")
	private String orgNameMar;

	@Column(name = "ServiceCount")
	private int serviceCount;

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

	public int getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
	}

	@Override
	public String toString() {
		return "TotalServiceCntSWMEntity [id=" + id + ", orgName=" + orgName + ", orgNameMar=" + orgNameMar
				+ ", serviceCount=" + serviceCount + "]";
	}

}
