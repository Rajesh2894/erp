package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TotalCollectionAmtSWMEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "O_NLS_ORGNAME")
	private String orgName;

	@Column(name = "SUM_RM_AMT")
	private int sumRmAmt;

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

	public int getSumRmAmt() {
		return sumRmAmt;
	}

	public void setSumRmAmt(int sumRmAmt) {
		this.sumRmAmt = sumRmAmt;
	}

	@Override
	public String toString() {
		return "TotalCollectionAmtSWMEntity [id=" + id + ", orgName=" + orgName + ", sumRmAmt=" + sumRmAmt + "]";
	}

}
