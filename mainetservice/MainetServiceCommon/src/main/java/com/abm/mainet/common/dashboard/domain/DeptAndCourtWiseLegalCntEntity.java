package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DeptAndCourtWiseLegalCntEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "CNT")
	private int count;

	@Column(name = "NAME")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DeptAndCourtWiseLegalCntEntity [id=" + id + ", count=" + count + ", name=" + name + "]";
	}

}
