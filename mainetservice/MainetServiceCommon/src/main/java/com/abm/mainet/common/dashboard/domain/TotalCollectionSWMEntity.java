package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TotalCollectionSWMEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "O_NLS_ORGNAME")
	private String orgName;

	@Column(name = "SUM_AMOUNT")
	private int sumAmount;

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

	public int getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(int sumAmount) {
		this.sumAmount = sumAmount;
	}

	@Override
	public String toString() {
		return "TotalCollectionSWMEntity [id=" + id + ", orgName=" + orgName + ", sumAmount=" + sumAmount + "]";
	}

}
