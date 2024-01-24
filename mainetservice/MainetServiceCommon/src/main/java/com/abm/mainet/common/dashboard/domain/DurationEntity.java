package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DurationEntity {
	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "Duration")
	private String duration;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "DurationEntity [id=" + id + ", duration=" + duration + "]";
	}

}
