package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SLAAnalysisEntity {
	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "BeyondRejectSLA")
	private int beyondRejectSLA;

	@Column(name = "WithinRejectSLA")
	private int withinRejectSLA;

	@Column(name = "BeyondCloseSLA")
	private int beyondCloseSLA;

	@Column(name = "WithinCloseSLA")
	private int withinCloseSLA;

	@Column(name = "BeyondPendingSLA")
	private int beyondPendingSLA;

	@Column(name = "WithinPendingSLA")
	private int withinPendingSLA;

	@Column(name = "BeyondSLA")
	private int beyondSLA;

	@Column(name = "WithinSLA")
	private int withinSLA;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBeyondRejectSLA() {
		return beyondRejectSLA;
	}

	public void setBeyondRejectSLA(int beyondRejectSLA) {
		this.beyondRejectSLA = beyondRejectSLA;
	}

	public int getWithinRejectSLA() {
		return withinRejectSLA;
	}

	public void setWithinRejectSLA(int withinRejectSLA) {
		this.withinRejectSLA = withinRejectSLA;
	}

	public int getBeyondCloseSLA() {
		return beyondCloseSLA;
	}

	public void setBeyondCloseSLA(int beyondCloseSLA) {
		this.beyondCloseSLA = beyondCloseSLA;
	}

	public int getWithinCloseSLA() {
		return withinCloseSLA;
	}

	public void setWithinCloseSLA(int withinCloseSLA) {
		this.withinCloseSLA = withinCloseSLA;
	}

	public int getBeyondPendingSLA() {
		return beyondPendingSLA;
	}

	public void setBeyondPendingSLA(int beyondPendingSLA) {
		this.beyondPendingSLA = beyondPendingSLA;
	}

	public int getWithinPendingSLA() {
		return withinPendingSLA;
	}

	public void setWithinPendingSLA(int withinPendingSLA) {
		this.withinPendingSLA = withinPendingSLA;
	}

	public int getBeyondSLA() {
		return beyondSLA;
	}

	public void setBeyondSLA(int beyondSLA) {
		this.beyondSLA = beyondSLA;
	}

	public int getWithinSLA() {
		return withinSLA;
	}

	public void setWithinSLA(int withinSLA) {
		this.withinSLA = withinSLA;
	}

	@Override
	public String toString() {
		return "SLAAnalysisEntity [id=" + id + ", beyondRejectSLA=" + beyondRejectSLA + ", withinRejectSLA="
				+ withinRejectSLA + ", beyondCloseSLA=" + beyondCloseSLA + ", withinCloseSLA=" + withinCloseSLA
				+ ", beyondPendingSLA=" + beyondPendingSLA + ", withinPendingSLA=" + withinPendingSLA + ", beyondSLA="
				+ beyondSLA + ", withinSLA=" + withinSLA + "]";
	}

}
