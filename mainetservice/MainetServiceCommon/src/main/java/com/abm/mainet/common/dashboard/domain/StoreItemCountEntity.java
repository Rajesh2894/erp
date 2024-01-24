package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StoreItemCountEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "Name")
	private String name;

	@Column(name = "CNT")
	private Integer count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "StoreItemCountEntity [id=" + id + ", name=" + name + ", count=" + count + "]";
	}

}
