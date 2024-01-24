package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HRMSCategoryWiseEmpCntEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "CNT")
	private int count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String groupOrStatus) {
		this.category = groupOrStatus;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "HRMSCategoryWiseEmpCntEntity [id=" + id + ", category=" + category + ", count=" + count + "]";
	}

}
