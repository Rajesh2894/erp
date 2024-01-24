package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RTIApplicationCountEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "COUNT")
	private int count;

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

	@Override
	public String toString() {
		return "RTIApplicationCountEntity [id=" + id + ", count=" + count + "]";
	}

}
