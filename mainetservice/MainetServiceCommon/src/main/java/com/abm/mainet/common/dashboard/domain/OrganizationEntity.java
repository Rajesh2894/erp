package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrganizationEntity {
	@Id
	@Column(name = "ORGID")
	private long orgid;

	@Column(name = "O_NLS_ORGNAME")
	private String orgName;

	@Column(name = "O_NLS_ORGNAME_MAR")
	private String orgNameMar;

	public long getOrgid() {
		return orgid;
	}

	public void setOrgid(long orgid) {
		this.orgid = orgid;
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

	@Override
	public String toString() {
		return "OrganizationEntity [orgid=" + orgid + ", orgName=" + orgName + ", orgNameMar=" + orgNameMar + "]";
	}

}
